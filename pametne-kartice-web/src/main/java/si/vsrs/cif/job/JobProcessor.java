/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.job;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Component;
import si.vsrs.cif.helper.NastavitveHelper;
import si.vsrs.cif.managers.JobLogManager;
import si.vsrs.cif.managers.StatusCertifikatRepoManager;
import si.vsrs.cif.mod.Certifikat;

/**
 *
 * @author uporabnik
 */
@Component
public class JobProcessor implements ItemProcessor<Certifikat, Certifikat> {

    @Autowired
    private LdapTemplate ldapTemplateCertificate;
    @Autowired
    StatusCertifikatRepoManager statusCertifikatRepoManager;
    @Autowired
    JobLogManager jobLogManager;
    private long jobLogId;
    @Autowired
    NastavitveHelper nastavitveHelper;
    private Date lastCrlRefreshDate;
    private final long refreshRateMs = 1000 * 60 * 60; //Ena ura
    private static final Logger logger = Logger.getLogger(JobProcessor.class.getName());

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        jobLogId = stepExecution.getJobParameters().getLong("id");
    }
    private Set<? extends X509CRLEntry> preklicaniCertifikati;

    @Override
    public Certifikat process(Certifikat certifikat) throws Exception {
        String serialNumber = certifikat.getSerijskaStevilka();
        String serialNumberX = certifikat.getSerialNumberX();
        logger.log(Level.INFO, "Start checking if certificate is revoked for serial number [" + serialNumber + "] and serial number X [" + serialNumberX + "]");
        if (isNullOrEmpty(serialNumberX)) {
            logger.info("Serial number X is null, will try to get it from LDAP");
            serialNumberX = getSerialNumberXFromLDAP(serialNumber);
            logger.log(Level.INFO, "Got serial number X from ldap: " + serialNumberX);
        }

        logger.info("Checking if certificate is revoked...");
        if (isNullOrEmpty(serialNumberX)) {
            if (certifikat.getJobLog() == null) {
                logger.info("Serial number X is null, setting 'STANJE' and returning.");
                certifikat.setStanje("Manjka serijska številka X");
                certifikat.setJobLog(jobLogManager.findById(jobLogId));
                return certifikat;
            }
            return null;
        }

        if (isCertfInCRLList(serialNumberX)) {
            certifikat.setStatus(statusCertifikatRepoManager.getStatusCertifikat("04"));
            certifikat.setDatumPreklica(new Date());
            certifikat.setJobLog(jobLogManager.findById(jobLogId));
            certifikat.setSerialNumberX(serialNumberX);
            certifikat.setStanje("Pridobljena serijska številka X iz LDAP");
            return certifikat;
        } else if (isNullOrEmpty(certifikat.getSerialNumberX())) {
            certifikat.setSerialNumberX(serialNumberX);
            certifikat.setJobLog(jobLogManager.findById(jobLogId));
            certifikat.setStanje("Pridobljena serijska številka X iz LDAP");
            return certifikat;
        }
        return null;
    }

    private String getSerialNumberXFromLDAP(String serialNumber) throws NamingException {
        logger.log(Level.INFO, "Start getting serial number X from LDAP for serial number [" + serialNumber + "]");
        List list = ldapTemplateCertificate.search(
                "", "(&(serialNumber=" + serialNumber + ")(objectClass=person))",
                new AttributesMapper() {
            @Override
            public Object mapFromAttributes(Attributes atrbts) throws NamingException {
                return atrbts.get("siTrustX509serialNumberHex");
            }
        });
        if (list.isEmpty()) {
            logger.info("List is empty");
        } else if (!list.isEmpty() && list.get(0) == null) {
            logger.info("List is size 0");
        } else if (!list.isEmpty() && list.get(0) != null) {
            logger.info("List is ok");
            Attribute atr = (Attribute) list.get(0);
            return (String) atr.get();
        }
        return null;
    }

    private boolean isCertfInCRLList(String serialNumberX) throws MalformedURLException, IOException, CertificateException, CRLException {
        logger.log(Level.INFO, "Checking if certificate with serial number X [" + serialNumberX + "] is in CRL list");
        for (X509CRLEntry entry : getPreklicaniCertifikati()) {
            String hexSerial = DecToHex(entry.getSerialNumber());
            if (getSerialNumberXWithoutPrefixesInUpperCase(hexSerial).equalsIgnoreCase(getSerialNumberXWithoutPrefixesInUpperCase(serialNumberX))) {
                logger.info("Certificate is in CRL list, returning true");
                return true;
            }
        }
        logger.info("Certificate is not in CRL list, returning false");
        return false;
    }

    /**
     * Odstrani '00' ali '0x' na zacetku stringa in spremeni v upper case:
     * 00ac97a664 -> ac97a664, 0xac97a664 -> ac97a664
     *
     * @param serialNumberX
     * @return
     */
    protected String getSerialNumberXWithoutPrefixesInUpperCase(String serialNumberX) {
        if (isNullOrEmpty(serialNumberX)) {
            return null;
        }
        String serialNumberXTmp = serialNumberX.toUpperCase();
        if (serialNumberXTmp.startsWith("0X")) {
            return serialNumberXTmp.replaceFirst("0X", "");
        }
        if (serialNumberXTmp.startsWith("00")) {
            return serialNumberXTmp.replaceFirst("00", "");
        }
        return serialNumberXTmp;
    }

    protected boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    private Set<? extends X509CRLEntry> getPreklicaniCertifikati() throws MalformedURLException, IOException, CertificateException, CRLException {
        //if (this.preklicaniCertifikati == null) {
        if (shouldRefreshFromCRL()) {
            logger.info("Start getting revoked certificates list from CRL.");
            String[] crlSplit = nastavitveHelper.getCertificateUpdateCRL().split(";");
            for (String certificateCrl : crlSplit) {
                URL url = new URL(certificateCrl);
                URLConnection connection = url.openConnection();
                connection.setDoInput(true);
                connection.setUseCaches(false);
                DataInputStream inStream = new DataInputStream(connection.getInputStream());
                CertificateFactory cf = CertificateFactory.getInstance("X509");
                X509CRL crl = (X509CRL) cf.generateCRL(inStream);
                inStream.close();
                this.preklicaniCertifikati = merge(this.preklicaniCertifikati, crl.getRevokedCertificates());
            }
            this.lastCrlRefreshDate = new Date();
        }
        return this.preklicaniCertifikati;
    }

    private boolean shouldRefreshFromCRL() {
        if (this.preklicaniCertifikati == null) {
            return true;
        }
        if (new Date().getTime() - lastCrlRefreshDate.getTime() > refreshRateMs) {
            return true;
        }
        return false;
    }

    private <T> Set<T> merge(Collection<? extends T>... collections) {
        Set<T> newSet = new HashSet<T>();
        for (Collection<? extends T> collection : collections) {
            if (collection != null) {
                newSet.addAll(collection);
            }
        }
        return newSet;
    }

    private String DecToHex(BigInteger dec) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};
        String hex = "";
        while (dec.compareTo(BigInteger.ZERO) != 0) {
            BigInteger rem = dec.remainder(new BigInteger("16"));
            hex = hexDigits[rem.intValue()] + hex;
            dec = dec.divide(new BigInteger("16"));
        }

        return hex;
    }
}
