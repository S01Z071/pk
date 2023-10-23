package si.vsrs.cif.helper;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import si.vsrs.cif.managers.CertifikatRepoManager;
import si.vsrs.cif.managers.ImetnikRepoManager;
import si.vsrs.cif.managers.SifrantSodiscUvozRepoManager;
import si.vsrs.cif.managers.StatusCertifikatRepoManager;
import si.vsrs.cif.managers.StatusImetnikRepoManager;
import si.vsrs.cif.managers.StatusLogImetnikRepoManager;
import si.vsrs.cif.managers.StatusLogRepoManager;
import si.vsrs.cif.managers.StatusLogZahtevekZaKodoManager;
import si.vsrs.cif.managers.StatusRepoManager;
import si.vsrs.cif.managers.ZahtevekRepoManager;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.Certifikat;
import si.vsrs.cif.mod.Opomba;
import si.vsrs.cif.mod.SifrantSodiscUvoz;
import si.vsrs.cif.mod.Status;
import si.vsrs.cif.mod.StatusImetnik;
import si.vsrs.cif.mod.StatusLog;
import si.vsrs.cif.mod.StatusLogImetnik;
import si.vsrs.cif.mod.StatusLogZahtevekZaKodo;
import si.vsrs.cif.mod.Zahtevek;
import si.vsrs.cif.mod.web.Sodisce;
import si.vsrs.cif.mod.web.Uporabnik;
import si.vsrs.cif.mod.web.Vloga;
import si.vsrs.cif.mod.web.VlogaSodisce;

/**
 *
 * @author uporabnik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:/spring/spring-servlet_it.xml",
    //"classpath:/spring/dao-context_it.xml",
    "classpath:/spring/dao-context_metodeTest.xml",
    "classpath:/spring/dao-context.xml",
    "classpath:/spring/app-context.xml",
    //"classpath:/spring/dao-context_test.xml",
    "classpath:/spring/app-context_test.xml"
})
public class MetodeTest {

    @Autowired
    private LdapTemplate ldapTemplate;
    @Autowired
    private MetodeHelper metodeHelper;
    @Autowired
    private ZahtevekRepoManager zahtevekRepoManager;
    @Autowired
    private StatusLogRepoManager statusLogRepoManager;
    @Autowired
    private StatusLogImetnikRepoManager statusLogImetnikRepoManager;
    @Autowired
    private CertifikatRepoManager certifikatRepoManager;
    @Autowired
    private SifrantSodiscUvozRepoManager sifrantSodiscUvozRepoManager;
    @Autowired
    private StatusCertifikatRepoManager statusCertifikatRepoManager;
    @Autowired
    private ImetnikRepoManager imetnikRepoManager;
    @Autowired
    private StatusRepoManager statusRepoManager;
    @Autowired
    private StatusImetnikRepoManager statusImetnikRepoManager;
    @Autowired
    private StatusLogZahtevekZaKodoManager statusLogZahtevekZaKodoManager;
    private Uporabnik uporabnikUser = new Uporabnik();
    private Uporabnik uporabnikAdmin = new Uporabnik();

    public MetodeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        uporabnikUser = new Uporabnik();
        VlogaSodisce vlogaSodisce = new VlogaSodisce();
        Sodisce sodisce = new Sodisce();
        Vloga vloga = new Vloga();
        vloga.setId("001");
        sodisce.setId("S01");
        vlogaSodisce.setSodisce(sodisce);
        vlogaSodisce.setVloga(vloga);
        uporabnikUser.setIzbranaVlogaSodisce(vlogaSodisce);
        uporabnikUser.setKadrovskStevilka("T200002");
        uporabnikUser.setIme("UserIme");
        uporabnikUser.setPriimek("UserPriimek");

        uporabnikAdmin = new Uporabnik();
        VlogaSodisce vlogaSodisce1 = new VlogaSodisce();
        Sodisce sodisce1 = new Sodisce();
        Vloga vloga1 = new Vloga();
        vloga1.setId("002");
        sodisce1.setId("S01");
        vlogaSodisce1.setSodisce(sodisce1);
        vlogaSodisce1.setVloga(vloga1);
        uporabnikAdmin.setIzbranaVlogaSodisce(vlogaSodisce1);
        uporabnikAdmin.setKadrovskStevilka("T200003");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void insertInStatusLogTest() {
        metodeHelper.insertInStatusLog(uporabnikUser, "00", "01", 10, statusLogRepoManager, "Opis 1");
        metodeHelper.insertInStatusLog(uporabnikAdmin, "01", "02", 11, statusLogRepoManager, "Opis 2");
        StatusLog status1 = statusLogRepoManager.getStatusLogByZahtevekId(10).get(0);
        StatusLog status2 = statusLogRepoManager.getStatusLogByZahtevekId(11).get(0);
        checkStatusLog(status1, "01", "00", "Opis 1", "S01", "T200002");
        checkStatusLog(status2, "02", "01", "Opis 2", "S01", "T200003");
    }

    @Test
    public void insertInStatusLogImetnikiTest() {
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(10);
        List<Imetnik> imetniki = zahtevek.getImetniki();
        assertEquals(3, imetniki.size());
        metodeHelper.insertInStatusLogImetniki(uporabnikUser, imetniki, "02", statusLogImetnikRepoManager, "opis 1", "04");
        List<StatusLogImetnik> status1 = statusLogImetnikRepoManager.getStatusLogByImetnikId(10);
        assertEquals(1, status1.size());
        checkStatusLogImetnik(status1.get(0), "02", "01", "opis 1", "S01", "T200002");
        List<StatusLogImetnik> status2 = statusLogImetnikRepoManager.getStatusLogByImetnikId(11);
        assertEquals(1, status2.size());
        checkStatusLogImetnik(status2.get(0), "02", "01", "opis 1", "S01", "T200002");
        assertEquals(0, statusLogImetnikRepoManager.getStatusLogByImetnikId(12).size());

        metodeHelper.insertInStatusLogImetniki(uporabnikAdmin, imetniki, "03", statusLogImetnikRepoManager, "opis 2", "06");

        assertEquals(2, statusLogImetnikRepoManager.getStatusLogByImetnikId(10).size());
        assertEquals(2, statusLogImetnikRepoManager.getStatusLogByImetnikId(11).size());

        List<StatusLogImetnik> status3 = statusLogImetnikRepoManager.getStatusLogByImetnikId(12);
        assertEquals(1, status3.size());
        checkStatusLogImetnik(status3.get(0), "03", "04", "opis 2", "S01", "T200003");
    }

    @Test
    public void insertInStatusLogImetnikTest() {
        metodeHelper.insertInStatusLogImetnik(uporabnikUser, "01", "02", 13, statusLogImetnikRepoManager, "opis 5");
        List<StatusLogImetnik> status = statusLogImetnikRepoManager.getStatusLogByImetnikId(13);
        assertEquals(1, status.size());
        checkStatusLogImetnik(status.get(0), "02", "01", "opis 5", "S01", "T200002");
    }

    //@Test
    public void insertInStatusLogPreklicTest() {
    }

    @Test
    public void spremeniImetnikNatisnjenoTest() {
        // List<Imetnik> imetniki = zahtevek1.getImetniki();
        List<Imetnik> imetniki = zahtevekRepoManager.getZahtevek(10).getImetniki();
        imetniki = metodeHelper.spremeniImetnikiNatisnjeno(imetniki, false);
        imetniki = metodeHelper.spremeniImetnikNatisnjeno(imetniki, 10, true);
        for (int i = 0; i < imetniki.size(); i++) {
            if (imetniki.get(i).getId().intValue() == 10) {
                assertTrue(imetniki.get(i).isNatisnjeno());
            } else {
                assertFalse(imetniki.get(i).isNatisnjeno());
            }
        }
    }

    @Test
    public void spremeniImetnikiNatisnjenoTest() {
        List<Imetnik> imetniki = zahtevekRepoManager.getZahtevek(10).getImetniki();
        imetniki = metodeHelper.spremeniImetnikiNatisnjeno(imetniki, false);
        imetniki = metodeHelper.spremeniImetnikiNatisnjeno(imetniki, true);
        for (int i = 0; i < imetniki.size(); i++) {
            assertTrue(imetniki.get(i).isNatisnjeno());
        }
    }

    @Test
    public void soVsiImetnikiNatisnjeniTest() {
        List<Imetnik> imetniki = zahtevekRepoManager.getZahtevek(10).getImetniki();
        imetniki = metodeHelper.spremeniImetnikiNatisnjeno(imetniki, false);
        imetniki = metodeHelper.spremeniImetnikNatisnjeno(imetniki, 10, true);
        assertFalse(metodeHelper.soVsiImetnikiNatisnjeni(imetniki));
        imetniki = metodeHelper.spremeniImetnikNatisnjeno(imetniki, 11, true);
        assertTrue(metodeHelper.soVsiImetnikiNatisnjeni(imetniki));
        imetniki = metodeHelper.spremeniImetnikiNatisnjeno(imetniki, false);
        imetniki = metodeHelper.spremeniImetnikiNatisnjeno(imetniki, true);
        assertTrue(metodeHelper.soVsiImetnikiNatisnjeni(imetniki));
    }

    @Test
    public void updateDeletedImetnikiListTest() {
        List<Zahtevek> zahtevki = new ArrayList();
        zahtevki.add(zahtevekRepoManager.getZahtevek(10));
        zahtevki.add(zahtevekRepoManager.getZahtevek(11));
        zahtevki.add(zahtevekRepoManager.getZahtevek(12));
        assertEquals(3, zahtevki.size());
        assertEquals(3, zahtevki.get(0).getImetniki().size());
        assertEquals(1, zahtevki.get(1).getImetniki().size());
        assertEquals(2, zahtevki.get(2).getImetniki().size());
        zahtevki = metodeHelper.updateDeletedImetniki(zahtevki);
        assertEquals(3, zahtevki.size());
        assertEquals(2, zahtevki.get(0).getImetniki().size());
        assertEquals(1, zahtevki.get(1).getImetniki().size());
        assertEquals(1, zahtevki.get(2).getImetniki().size());
        for (int i = 0; i < zahtevki.get(0).getImetniki().size(); i++) {
            assertTrue(zahtevki.get(0).getImetniki().get(i).getStatus().getSifraSIm().compareTo("01") == 0);
        }
    }

    @Test
    public void updateDeletedImetnikiTest() {
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(10);
        assertEquals(3, zahtevek.getImetniki().size());
        zahtevek = metodeHelper.updateDeletedImetniki(zahtevek);
        assertEquals(2, zahtevek.getImetniki().size());
        for (int i = 0; i < zahtevek.getImetniki().size(); i++) {
            assertTrue(zahtevek.getImetniki().get(i).getStatus().getSifraSIm().compareTo("01") == 0);
        }
    }

    @Test
    public void opombeNewLineTest() {
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(10);
        assertEquals(1, zahtevek.getOpombe().size());
        assertEquals("Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123", zahtevek.getOpombe().iterator().next().getVsebina());
        zahtevek = metodeHelper.opombeNewLine(zahtevek);
        assertEquals(1, zahtevek.getOpombe().size());
        assertEquals("Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 <br>Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 Opomba123 <br>Opomba123 Opomba123<br>", zahtevek.getOpombe().iterator().next().getVsebina());
    }

    @Test
    public void vsiImetnikiStatusTest() {
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(10);
        assertFalse(metodeHelper.vsiImetnikiStatus(zahtevek, "01"));
        assertTrue(metodeHelper.vsiImetnikiStatus(zahtevek, "01", "04"));
    }

    @Test
    public void vsajEnImetnikStatusTest() {
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(10);
        assertTrue(metodeHelper.vsajEnImetnikStatus(zahtevek, "04"));
        assertTrue(metodeHelper.vsajEnImetnikStatus(zahtevek, "01", "04"));
        assertFalse(metodeHelper.vsajEnImetnikStatus(zahtevek, "06"));
    }

    @Test
    public void getSortedOpombeTest() {
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(11);
        Set<Opomba> opombe = zahtevek.getOpombe();
        List<Opomba> opombeSorted = metodeHelper.getSortedOpombe(opombe);
        assertEquals("Opomba2", opombeSorted.get(0).getNaslov());
        assertEquals("Opomba4", opombeSorted.get(1).getNaslov());
        assertEquals("Opomba3", opombeSorted.get(2).getNaslov());
    }

    @Test
    public void sortOpombeTest() {
        //isto kot getSortedOpombeTest()
    }

    //@Test
    public void setHeaderPDFTest() {
    }

    @Test
    public void getOpombaZavrnitevTest() {
        Opomba opomba = new Opomba();
        opomba = metodeHelper.getOpombaZavrnitev(uporabnikUser, opomba, "Naslov opombe");
        assertEquals("Naslov opombe", opomba.getNaslov());
        assertEquals("T200002", opomba.getUporabnik());
        assertEquals("UserIme", opomba.getUporabnikIme());
        assertEquals("UserPriimek", opomba.getUporabnikPriimek());
        assertEquals("S01", opomba.getUporabnikSodisce());
        assertNotNull(opomba.getDatum());
    }

    @Test
    public void getCertifikatStatusSifraFromStanjeTest() {
        assertEquals("05", metodeHelper.getCertifikatStatusSifraFromStanje("Zavrnjen"));
        assertEquals("06", metodeHelper.getCertifikatStatusSifraFromStanje("Vročen"));
        assertEquals("06", metodeHelper.getCertifikatStatusSifraFromStanje("Odobren"));
        assertEquals("06", metodeHelper.getCertifikatStatusSifraFromStanje("Zavržen"));
        assertEquals("06", metodeHelper.getCertifikatStatusSifraFromStanje("Neprevzet"));
        assertEquals("06", metodeHelper.getCertifikatStatusSifraFromStanje("Prošnja"));
        assertEquals("05", metodeHelper.getCertifikatStatusSifraFromStanje("Pretekel"));
        assertEquals("03", metodeHelper.getCertifikatStatusSifraFromStanje("Prevzet"));
        assertEquals("04", metodeHelper.getCertifikatStatusSifraFromStanje("Preklican"));
    }

    @Test
    public void getMethodNameTest() {
        assertEquals("getIme", metodeHelper.getMethodName("ime"));
        assertEquals("getIme", metodeHelper.getMethodName("Ime"));
        assertEquals("getENaslov", metodeHelper.getMethodName("eNaslov"));
    }

    @Test
    public void setIzvoziImetnikiTest() {
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(10);
        zahtevek = metodeHelper.setIzvoziImetniki(zahtevek, false);
        zahtevek = metodeHelper.setIzvoziImetniki(zahtevek, true);
        for (int i = 0; i < zahtevek.getImetniki().size(); i++) {
            assertTrue(zahtevek.getImetniki().get(i).getIzvozi());
        }
    }

    @Test
    public void checkIzvoziImetnikTest() {
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(10);
        zahtevek = metodeHelper.setIzvoziImetniki(zahtevek, false);
        assertFalse(metodeHelper.checkIzvoziImetnik(zahtevek));
        zahtevek = metodeHelper.setIzvoziImetniki(zahtevek, true);
        assertTrue(metodeHelper.checkIzvoziImetnik(zahtevek));
    }

    @Test
    public void setDataFromCSVTest() {
        try {
            String[] polja = {"ime", "priimek", "emso", "davcna", "eNaslov"};
            String[] podatki = {"Janez", "Novak", "1234567890123", "33333333", "janez.novak@sodisce.si"};
            Imetnik imetnik = (Imetnik) metodeHelper.setDataFromCSV(podatki, polja, "Imetnik", "si.vsrs.cif.mod");
            assertEquals("Janez", imetnik.getIme());
            assertEquals("Novak", imetnik.getPriimek());
            assertEquals("1234567890123", imetnik.getEmso());
            assertEquals("33333333", imetnik.getDavcna());
            assertEquals("janez.novak@sodisce.si", imetnik.getENaslov());
        } catch (Exception ex) {
            Logger.getLogger(MetodeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void getMethodNameSetterTest() {
        assertEquals("setIme", metodeHelper.getMethodNameSetter("ime"));
        assertEquals("setIme", metodeHelper.getMethodNameSetter("Ime"));
        assertEquals("setENaslov", metodeHelper.getMethodNameSetter("eNaslov"));
    }

    @Test
    public void checkEMSOMOD11Test() {
        //Pravilen emso      
        assertTrue(metodeHelper.checkEMSOMOD11("2100982511250"));
        assertTrue(metodeHelper.checkEMSOMOD11("1234564576454"));
        //Napacen emso
        assertFalse(metodeHelper.checkEMSOMOD11("1234564576459"));
        //Premalo stevilk
        assertFalse(metodeHelper.checkEMSOMOD11("234345435"));
        //Prevec stevilk
        assertFalse(metodeHelper.checkEMSOMOD11("23434543511111111111"));
    }

    @Test
    public void getDataFromLdapImePriimekTest() {
        assertEquals("S011274", metodeHelper.getDataFromLdapImePriimek("Oman Marko", "cn", ldapTemplate).get(0));
    }

    @Test
    public void getDataFromLdapENaslovTest() {
        assertEquals("S011274", metodeHelper.getDataFromLdapENaslov("marko.oman@sodisce.si", "cn", ldapTemplate).get(0));
    }

    @Test
    public void isSerijskaInTest() {
        List<Certifikat> certifikati = certifikatRepoManager.getAllCertifikatByImetnikID(15);
        assertTrue(metodeHelper.isSerijskaIn(certifikati, "1235722214016"));
        assertTrue(metodeHelper.isSerijskaIn(certifikati, "1235912314011"));
        assertTrue(metodeHelper.isSerijskaIn(certifikati, "1236666914013"));
        assertTrue(metodeHelper.isSerijskaIn(certifikati, "1231235018011"));
        assertTrue(metodeHelper.isSerijskaIn(certifikati, "1236784614019"));
        assertFalse(metodeHelper.isSerijskaIn(certifikati, "1111111111111"));
    }

    @Test
    public void getSifraSodiscaFromNazivTest() {
        List<SifrantSodiscUvoz> sifre = sifrantSodiscUvozRepoManager.getSifrantSodisc();
        assertEquals(5, sifre.size());
        assertEquals("S63", metodeHelper.getSifraSodiscaFromNaziv(sifre, "Okrajno sodisce Slovenska Bistrica"));
        assertEquals("S32", metodeHelper.getSifraSodiscaFromNaziv(sifre, "Okrajno sodisce na Ptuju"));
        assertEquals("S50", metodeHelper.getSifraSodiscaFromNaziv(sifre, "Okrajno sodisce na Jesenicah"));
        assertEquals("S72", metodeHelper.getSifraSodiscaFromNaziv(sifre, "Okrajno sodisce na Vrhniki"));
        assertEquals("S81", metodeHelper.getSifraSodiscaFromNaziv(sifre, "Delovno sodisce v Celju"));
        assertNull(metodeHelper.getSifraSodiscaFromNaziv(sifre, "sodisce 123"));
    }

    @Test
    public void updateCertifikatStatusTest() {
        List<Certifikat> certifikati = certifikatRepoManager.getAllCertifikatByImetnikID(15);
        assertEquals(5, certifikati.size());
        certifikati.get(0).setDatumPoteka(new Date(new Date().getTime() - 1000 * 60 * 60 * 24));
        certifikati.get(1).setDatumPoteka(new Date(new Date().getTime() - 1000 * 60 * 60 * 24));
        certifikati = metodeHelper.updateCertifikatStatus(certifikati, statusCertifikatRepoManager, certifikatRepoManager);
        assertTrue(certifikati.get(0).getStatus().getSifra().compareTo("05") == 0);
        assertTrue(certifikati.get(1).getStatus().getSifra().compareTo("05") == 0);
        assertTrue(certifikati.get(2).getStatus().getSifra().compareTo("03") == 0);
        assertTrue(certifikati.get(3).getStatus().getSifra().compareTo("05") == 0);
        assertTrue(certifikati.get(4).getStatus().getSifra().compareTo("04") == 0);
    }

    @Test
    public void isDateOkTest() {
        assertTrue(metodeHelper.isDateOk("01.01.2013"));
        assertTrue(metodeHelper.isDateOk("1.01.2013"));
        assertTrue(metodeHelper.isDateOk("01.1.2013"));
        assertTrue(metodeHelper.isDateOk("1.1.2013"));
        assertFalse(metodeHelper.isDateOk("122.12.1312"));
    }

    @Test
    public void zahtevekStatusTest() {
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(10);
        assertTrue(metodeHelper.zahtevekStatus(zahtevek, "01"));
        assertTrue(metodeHelper.zahtevekStatus(zahtevek, "01", "02"));
        assertTrue(metodeHelper.zahtevekStatus(zahtevek, "01", "02", "03", "04", "05", "06", "07", "08"));
        assertFalse(metodeHelper.zahtevekStatus(zahtevek, "02"));
        assertFalse(metodeHelper.zahtevekStatus(zahtevek, "02", "03", "06"));
        assertFalse(metodeHelper.zahtevekStatus(zahtevek, "02", "03", "04", "05", "06", "07", "08"));
        Status status = zahtevek.getStatus();
        status.setSifra("03");
        zahtevek.setStatus(status);
        assertTrue(metodeHelper.zahtevekStatus(zahtevek, "03"));
        assertFalse(metodeHelper.zahtevekStatus(zahtevek, "01", "02"));
        assertTrue(metodeHelper.zahtevekStatus(zahtevek, "01", "02", "03", "04", "05", "06", "07", "08"));
        assertFalse(metodeHelper.zahtevekStatus(zahtevek, "02"));
        assertFalse(metodeHelper.zahtevekStatus(zahtevek, "02", "05", "06"));
        assertFalse(metodeHelper.zahtevekStatus(zahtevek, "01", "02", "04", "05", "06", "07", "08"));
        status.setSifra("08");
        zahtevek.setStatus(status);
        assertFalse(metodeHelper.zahtevekStatus(zahtevek, "03"));
        assertFalse(metodeHelper.zahtevekStatus(zahtevek, "01", "02"));
        assertTrue(metodeHelper.zahtevekStatus(zahtevek, "01", "02", "03", "04", "05", "06", "07", "08"));
        assertFalse(metodeHelper.zahtevekStatus(zahtevek, "02", "05", "06"));
        assertFalse(metodeHelper.zahtevekStatus(zahtevek, "01", "02", "03", "04", "05", "06", "07"));
    }

    @Test
    public void obstajaVLDAPTest() {
        assertTrue(metodeHelper.obstajaVLDAP("marko.oman@sodisce.si", ldapTemplate));
        assertTrue(metodeHelper.obstajaVLDAP("andrej.koncilja@sodisce.si", ldapTemplate));
        assertFalse(metodeHelper.obstajaVLDAP("marko1.oman1@sodisce.si", ldapTemplate));
    }

    @Test
    public void zeImaCertifikatTest() {
        List<Certifikat> certifikati = certifikatRepoManager.findByEMail("zdenko.kostic@sodisce.si");
        certifikati.get(0).setDatumPoteka(new Date());
        assertTrue(metodeHelper.zeImaCertifikat(certifikati, (Long.valueOf("90") * 24 * 60 * 60 * 1000)));
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 10);

        certifikati.get(0).setDatumPoteka(c.getTime());
        assertTrue(metodeHelper.zeImaCertifikat(certifikati, (Long.valueOf("90") * 24 * 60 * 60 * 1000)));
        c.add(Calendar.DAY_OF_MONTH, 79);       
        certifikati.get(0).setDatumPoteka(c.getTime());
        assertTrue(metodeHelper.zeImaCertifikat(certifikati, (Long.valueOf("90") * 24 * 60 * 60 * 1000)));

        c.add(Calendar.DAY_OF_MONTH, 2);
        certifikati.get(0).setDatumPoteka(c.getTime());
        assertFalse(metodeHelper.zeImaCertifikat(certifikati, (Long.valueOf("90") * 24 * 60 * 60 * 1000)));
        c.add(Calendar.DAY_OF_MONTH, 100);
        certifikati.get(0).setDatumPoteka(c.getTime());
        assertFalse(metodeHelper.zeImaCertifikat(certifikati, (Long.valueOf("90") * 24 * 60 * 60 * 1000)));
    }

    @Test
    public void imetnikZeObstajaTest() {
        Imetnik imetnik = new Imetnik();
        imetnik.setENaslov("marko1.oman@sodisce.si");
        //imetnik.setStatus(statusImetnikRepoManager.getStatusImetnik("01"));
        //Dodajanje ze obstaja
        assertTrue(metodeHelper.imetnikZeObstaja(imetnikRepoManager.getImetnikByENaslov(imetnik.getENaslov()), imetnik));
        //Dodajanje ne obstaja
        imetnik.setENaslov("marko1.oman1@sodisce.si");
        assertFalse(metodeHelper.imetnikZeObstaja(imetnikRepoManager.getImetnikByENaslov(imetnik.getENaslov()), imetnik));
        //Dodajanje ze obstaja - izbrisan
        imetnik.setENaslov("marko3.oman@sodisce.si");
        assertFalse(metodeHelper.imetnikZeObstaja(imetnikRepoManager.getImetnikByENaslov(imetnik.getENaslov()), imetnik));
        //Ze obstaja - zakljucen (status 09)
        imetnik.setENaslov("marko4.oman@sodisce.si");
        assertFalse(metodeHelper.imetnikZeObstaja(imetnikRepoManager.getImetnikByENaslov(imetnik.getENaslov()), imetnik));
        //UREJANJE
        //Ze obstaja - urejanje enak eNaslov
        imetnik = imetnikRepoManager.findByCrtnaKoda("IS011113332220831");
        assertFalse(metodeHelper.imetnikZeObstaja(imetnikRepoManager.getImetnikByENaslov(imetnik.getENaslov()), imetnik));
        //Ze obstaja - urejanje spreminjanje eNaslova
        imetnik.setENaslov("marko123.oman123@sodisce.si");
        assertFalse(metodeHelper.imetnikZeObstaja(imetnikRepoManager.getImetnikByENaslov(imetnik.getENaslov()), imetnik));
        //Ze obstaja - urejanje spreminjanje eNaslova - nedovoljeno
        imetnik.setENaslov("marko2.oman@sodisce.si");
        assertTrue(metodeHelper.imetnikZeObstaja(imetnikRepoManager.getImetnikByENaslov(imetnik.getENaslov()), imetnik));
        //Ze obstaja - urejanje izbrisan imetnik
        imetnik.setENaslov("marko3.oman@sodisce.si");
        assertFalse(metodeHelper.imetnikZeObstaja(imetnikRepoManager.getImetnikByENaslov(imetnik.getENaslov()), imetnik));
        //Ze obstaja - urejanje zakljucen imetnik
        imetnik.setENaslov("marko4.oman@sodisce.si");
        assertFalse(metodeHelper.imetnikZeObstaja(imetnikRepoManager.getImetnikByENaslov(imetnik.getENaslov()), imetnik));

    }

    @Test
    public void lahkoDostopaDoStraniZahtevekTest() {
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(10);
        assertTrue(metodeHelper.lahkoDostopaDoStrani("001", zahtevek, new String[]{"01"}, new String[]{"01", "02", "03"}));
        assertFalse(metodeHelper.lahkoDostopaDoStrani("001", zahtevek, new String[]{"02"}, new String[]{"01", "02", "03"}));
        assertTrue(metodeHelper.lahkoDostopaDoStrani("002", zahtevek, new String[]{"01"}, new String[]{"01", "02", "03"}));
        assertTrue(metodeHelper.lahkoDostopaDoStrani("002", zahtevek, new String[]{"02"}, new String[]{"01", "02", "03"}));
        zahtevek.setStatus(statusRepoManager.getStatus("02"));
        assertFalse(metodeHelper.lahkoDostopaDoStrani("001", zahtevek, new String[]{"01"}, new String[]{"01", "02", "03"}));
        assertTrue(metodeHelper.lahkoDostopaDoStrani("001", zahtevek, new String[]{"02"}, new String[]{"01", "02", "03"}));
        assertTrue(metodeHelper.lahkoDostopaDoStrani("002", zahtevek, new String[]{"01"}, new String[]{"01", "02", "03"}));
        assertFalse(metodeHelper.lahkoDostopaDoStrani("002", zahtevek, new String[]{"02"}, new String[]{"01"}));
        zahtevek.setStatus(statusRepoManager.getStatus("03"));
        assertFalse(metodeHelper.lahkoDostopaDoStrani("001", zahtevek, new String[]{"01", "02"}, new String[]{"01", "02", "03"}));
        assertTrue(metodeHelper.lahkoDostopaDoStrani("001", zahtevek, new String[]{"02", "03", "04"}, new String[]{"01", "02", "03"}));
        assertTrue(metodeHelper.lahkoDostopaDoStrani("002", zahtevek, new String[]{"01"}, new String[]{"03"}));
        assertFalse(metodeHelper.lahkoDostopaDoStrani("002", zahtevek, new String[]{"02"}, new String[]{"01", "02", "04"}));
        zahtevek.setStatus(statusRepoManager.getStatus("04"));
        assertFalse(!metodeHelper.lahkoDostopaDoStrani("001", zahtevek, new String[]{"04"}, new String[]{""}));
        assertTrue(!metodeHelper.lahkoDostopaDoStrani("002", zahtevek, new String[]{"04"}, new String[]{""}));
    }

    @Test
    public void lahkoDostopaDoStraniImetnikTest() {
        Imetnik imetnik = imetnikRepoManager.getImetnik(10);
        assertTrue(metodeHelper.lahkoDostopaDoStrani("001", imetnik, new String[]{"01"}, new String[]{"01", "02", "03"}));
        assertFalse(metodeHelper.lahkoDostopaDoStrani("001", imetnik, new String[]{"02"}, new String[]{"01", "02", "03"}));
        assertTrue(metodeHelper.lahkoDostopaDoStrani("002", imetnik, new String[]{"01"}, new String[]{"01", "02", "03"}));
        assertTrue(metodeHelper.lahkoDostopaDoStrani("002", imetnik, new String[]{"02"}, new String[]{"01", "02", "03"}));
        imetnik.setStatus(statusImetnikRepoManager.getStatusImetnik("02"));
        assertFalse(metodeHelper.lahkoDostopaDoStrani("001", imetnik, new String[]{"01"}, new String[]{"01", "02", "03"}));
        assertTrue(metodeHelper.lahkoDostopaDoStrani("001", imetnik, new String[]{"02"}, new String[]{"01", "02", "03"}));
        assertTrue(metodeHelper.lahkoDostopaDoStrani("002", imetnik, new String[]{"01"}, new String[]{"01", "02", "03"}));
        assertFalse(metodeHelper.lahkoDostopaDoStrani("002", imetnik, new String[]{"02"}, new String[]{"01"}));
        imetnik.setStatus(statusImetnikRepoManager.getStatusImetnik("03"));
        assertFalse(metodeHelper.lahkoDostopaDoStrani("001", imetnik, new String[]{"01", "02"}, new String[]{"01", "02", "03"}));
        assertTrue(metodeHelper.lahkoDostopaDoStrani("001", imetnik, new String[]{"02", "03", "04"}, new String[]{"01", "02", "03"}));
        assertTrue(metodeHelper.lahkoDostopaDoStrani("002", imetnik, new String[]{"01"}, new String[]{"03"}));
        assertFalse(metodeHelper.lahkoDostopaDoStrani("002", imetnik, new String[]{"02"}, new String[]{"01", "02", "04"}));
        imetnik.setStatus(statusImetnikRepoManager.getStatusImetnik("04"));
        assertTrue(metodeHelper.lahkoDostopaDoStrani("001", imetnik, new String[]{"04"}, new String[]{""}));
        assertTrue(metodeHelper.lahkoDostopaDoStrani("002", imetnik, new String[]{"04"}, new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09"}));
    }

    @Test
    public void imetnikStatusTest() {
        Imetnik imetnik = imetnikRepoManager.getImetnik(10);
        assertTrue(metodeHelper.imetnikStatus(imetnik, "01"));
        assertTrue(metodeHelper.imetnikStatus(imetnik, "01", "02"));
        assertTrue(metodeHelper.imetnikStatus(imetnik, "01", "02", "03", "04", "05", "06", "07", "08"));
        assertFalse(metodeHelper.imetnikStatus(imetnik, "02"));
        assertFalse(metodeHelper.imetnikStatus(imetnik, "02", "03", "06"));
        assertFalse(metodeHelper.imetnikStatus(imetnik, "02", "03", "04", "05", "06", "07", "08"));
        StatusImetnik status = imetnik.getStatus();
        status.setSifraSIm("03");
        imetnik.setStatus(status);
        assertTrue(metodeHelper.imetnikStatus(imetnik, "03"));
        assertFalse(metodeHelper.imetnikStatus(imetnik, "01", "02"));
        assertTrue(metodeHelper.imetnikStatus(imetnik, "01", "02", "03", "04", "05", "06", "07", "08"));
        assertFalse(metodeHelper.imetnikStatus(imetnik, "02"));
        assertFalse(metodeHelper.imetnikStatus(imetnik, "02", "05", "06"));
        assertFalse(metodeHelper.imetnikStatus(imetnik, "01", "02", "04", "05", "06", "07", "08"));
        status.setSifraSIm("08");
        imetnik.setStatus(status);
        assertFalse(metodeHelper.imetnikStatus(imetnik, "03"));
        assertFalse(metodeHelper.imetnikStatus(imetnik, "01", "02"));
        assertTrue(metodeHelper.imetnikStatus(imetnik, "01", "02", "03", "04", "05", "06", "07", "08"));
        assertFalse(metodeHelper.imetnikStatus(imetnik, "02", "05", "06"));
        assertFalse(metodeHelper.imetnikStatus(imetnik, "01", "02", "03", "04", "05", "06", "07"));
    }

    @Test
    public void insertInStatusLogZaKodoTest() {
        metodeHelper.insertInStatusLogZaKodo(uporabnikUser, "00", "01", 8, statusLogZahtevekZaKodoManager, "Zahtevek kreiran.");
        metodeHelper.insertInStatusLogZaKodo(uporabnikUser, "01", "04", 8, statusLogZahtevekZaKodoManager, "Zahtevek izbrisan.");
        metodeHelper.insertInStatusLogZaKodo(uporabnikAdmin, "04", "01", 9, statusLogZahtevekZaKodoManager, "Zahtevek odklenjen.");
        StatusLogZahtevekZaKodo status1 = statusLogZahtevekZaKodoManager.getStatusLogByZahtevekId(8).get(0);

        StatusLogZahtevekZaKodo status2 = statusLogZahtevekZaKodoManager.getStatusLogByZahtevekId(9).get(0);
        checkStatusLogZaKodo(status1, "01", "00", "Zahtevek kreiran.", "S01", "T200002");
        checkStatusLogZaKodo(status2, "01", "04", "Zahtevek odklenjen.", "S01", "T200003");
    }
    
    @Test
    public void insertInStatusLogZaPreklicTest(){
       //assertTrue(false);
    }


    /*----*/
    private void checkStatusLog(StatusLog status, String novStatus, String prejsnjiStatus, String opis, String sodisce, String uporabnik) {
        assertEquals(novStatus, status.getNovStatus());
        assertEquals(prejsnjiStatus, status.getPrejsnjiStatus());
        assertEquals(opis, status.getOpis());
        assertEquals(sodisce, status.getSodisce());
        assertEquals(uporabnik, status.getUporabnik());
    }

    private void checkStatusLogImetnik(StatusLogImetnik status, String novStatus, String prejsnjiStatus, String opis, String sodisce, String uporabnik) {
        assertEquals(novStatus, status.getNovStatusI());
        assertEquals(prejsnjiStatus, status.getPrejsnjiStatusI());
        assertEquals(opis, status.getOpis());
        assertEquals(sodisce, status.getSodisce());
        assertEquals(uporabnik, status.getUporabnikI());
    }

    private void checkStatusLogZaKodo(StatusLogZahtevekZaKodo status, String novStatus, String prejsnjiStatus, String opis, String sodisce, String uporabnik) {
        assertEquals(novStatus, status.getNovStatus());
        assertEquals(prejsnjiStatus, status.getPrejsnjiStatus());
        assertEquals(opis, status.getOpis());
        assertEquals(sodisce, status.getSodisce());
        assertEquals(uporabnik, status.getUporabnik());
    }
}