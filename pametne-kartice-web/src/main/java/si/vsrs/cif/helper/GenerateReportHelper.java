/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.helper;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.io.IOUtils;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.Zahtevek;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import si.vsrs.cif.mod.Certifikat;
import si.vsrs.cif.mod.OpremaZaObrazec;
import si.vsrs.cif.mod.ZahtevekZaKodo;
import si.vsrs.cif.mod.ZahtevekZaPreklic;
import si.vsrs.cif.mod.ZahtevekZaPrenos;

/**
 *
 * @author andrej
 */
@Service
public class GenerateReportHelper {

    @Autowired
    NastavitveHelper nastavitveHelper;

    public enum ObrazciURL {

        ZAHTEVEK1IN2, ZAHTEVEK3, POTRDILOOPREJEMU, KODAZAODKLEPANJE, PREKLICCERTIFIKATA, PRENOSCERTIFIKATA
    }

    public ByteArrayOutputStream createReportZahtevek(Zahtevek zahtevek, List<Imetnik> imetniki) throws Exception {
        try {
            // 1) Load ODT file by filling Freemarker template engine and cache
            // it to the registry  
            RestTemplate t = createNewRestTemplate();
            URI uri = new URI((String) nastavitveHelper.getObrazciURL().get(ObrazciURL.ZAHTEVEK1IN2.name()));
            byte[] in = (byte[]) t.getForObject(uri, byte[].class);

            //InputStream in = GenerateReportHelper.class.getResourceAsStream("zahtevek_v4_fields_ob_1_2.odt");

            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(new ByteArrayInputStream(in), TemplateEngineKind.Freemarker);

            // 2) Create fields metadata to manage lazy loop (#forech velocity) for table row.
            FieldsMetadata metadata = report.createFieldsMetadata();
            metadata.addFieldAsList("imetniki.ime");
            metadata.addFieldAsList("imetniki.priimek");

            // 3) Create context Java model
            IContext context = report.createContext();
            context.put("zahtevek", zahtevek);
            context.put("imetniki", imetniki);

            // 4) Generate report by merging Java model with the ODT
            ByteArrayOutputStream outODT = new ByteArrayOutputStream();

            report.process(context, outODT);

            ByteArrayInputStream inODT = new ByteArrayInputStream(outODT.toByteArray());
            ByteArrayOutputStream outPDF1 = convertODTToPDF(inODT);
            return outPDF1;


        } catch (IOException | XDocReportException e) {
            throw new RuntimeException(e);
        }

    }

    public ByteArrayOutputStream createReportImetnik(List<Imetnik> imetniki) throws Exception {
        try {

            RestTemplate t = createNewRestTemplate();
            URI uri = new URI((String) nastavitveHelper.getObrazciURL().get(ObrazciURL.ZAHTEVEK3.name()));
            byte[] in = (byte[]) t.getForObject(uri, byte[].class);
            //InputStream in = GenerateReportHelper.class.getResourceAsStream("zahtevek_v4_fields_ob_3.odt");

            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(new ByteArrayInputStream(in), TemplateEngineKind.Freemarker);

            IContext context = report.createContext();
            context.put("imetniki", imetniki);

            // 4) Generate report by merging Java model with the ODT
            ByteArrayOutputStream outODT = new ByteArrayOutputStream();

            report.process(context, outODT);

            ByteArrayInputStream inODT = new ByteArrayInputStream(outODT.toByteArray());
            ByteArrayOutputStream outPDF1 = convertODTToPDF(inODT);
            return outPDF1;
        } catch (IOException | XDocReportException e) {
            throw new RuntimeException(e);
        }

    }

    public ByteArrayOutputStream createReportObvestiloPotrdiloOPrejemu(Zahtevek zahtevek, Imetnik imetnik, Certifikat certifikat) throws Exception {
        try {

            // 1) Load ODT file by filling Freemarker template engine and cache
            // it to the registry  
            RestTemplate t = createNewRestTemplate();
            URI uri = new URI((String) nastavitveHelper.getObrazciURL().get(ObrazciURL.POTRDILOOPREJEMU.name()));
            byte[] in = (byte[]) t.getForObject(uri, byte[].class);

            //InputStream in = GenerateReportHelper.class.getResourceAsStream("ObvestiloPotrdiloOPrejemu.odt");

            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(new ByteArrayInputStream(in), TemplateEngineKind.Freemarker);
            //  IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);

            // 2) Create fields metadata to manage lazy loop (#forech velocity) for table row.
            // FieldsMetadata metadata = report.createFieldsMetadata();
            // metadata.addFieldAsList("imetniki.ime");
            // metadata.addFieldAsList("imetniki.priimek");

            // 3) Create context Java model
            IContext context = report.createContext();
            context.put("zahtevek", zahtevek);
            context.put("imetnik", imetnik);
            context.put("certifikat", certifikat);


            // 4) Generate report by merging Java model with the ODT
            ByteArrayOutputStream outODT = new ByteArrayOutputStream();

            report.process(context, outODT);
            // return outODT;

            ByteArrayInputStream inODT = new ByteArrayInputStream(outODT.toByteArray());

            ByteArrayOutputStream outPDF1 = convertODTToPDF(inODT);
            return outPDF1;


        } catch (IOException | XDocReportException e) {
            throw new RuntimeException(e);
        }
    }

    public ByteArrayOutputStream createReportZahtevekZaOdklepanjeKartice(ZahtevekZaKodo zahtevek) throws Exception {
        try {

            RestTemplate t = createNewRestTemplate();
            URI uri = new URI((String) nastavitveHelper.getObrazciURL().get(ObrazciURL.KODAZAODKLEPANJE.name()));
            byte[] in = (byte[]) t.getForObject(uri, byte[].class);
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(new ByteArrayInputStream(in), TemplateEngineKind.Freemarker);

            // InputStream in = GenerateReportHelper.class.getResourceAsStream("ZahtevekZaPridobitevKodeZaOdklepanje v1.0.odt");    
            // IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);


            IContext context = report.createContext();
            context.put("zahtevek", zahtevek);

            ByteArrayOutputStream outODT = new ByteArrayOutputStream();

            report.process(context, outODT);
            //return outODT;

            ByteArrayInputStream inODT = new ByteArrayInputStream(outODT.toByteArray());

            ByteArrayOutputStream outPDF1 = convertODTToPDF(inODT);
            return outPDF1;
            

        } catch (IOException | XDocReportException e) {
            throw new RuntimeException(e);
        }
    }
    
     public ByteArrayOutputStream createReportZahtevekZaPreklic(ZahtevekZaPreklic zahtevek) throws Exception {
        try {

            RestTemplate t = createNewRestTemplate();
            URI uri = new URI((String) nastavitveHelper.getObrazciURL().get(ObrazciURL.PREKLICCERTIFIKATA.name()));
            byte[] in = (byte[]) t.getForObject(uri, byte[].class);
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(new ByteArrayInputStream(in), TemplateEngineKind.Freemarker);
            
           //  InputStream in = GenerateReportHelper.class.getResourceAsStream("ZahtevekZaPreklicCertifikata_v1.0.odt");    
            // IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);

            IContext context = report.createContext();
            context.put("zahtevek", zahtevek);

            ByteArrayOutputStream outODT = new ByteArrayOutputStream();

            report.process(context, outODT);
            // return outODT;

            ByteArrayInputStream inODT = new ByteArrayInputStream(outODT.toByteArray());

            ByteArrayOutputStream outPDF1 = convertODTToPDF(inODT);
            return outPDF1;


        } catch (IOException | XDocReportException e) {
            throw new RuntimeException(e);
        }
    }
     
      public ByteArrayOutputStream createReportZahtevekZaPrenos(ZahtevekZaPrenos zahtevek) throws Exception {
        try {

            RestTemplate t = createNewRestTemplate();
            URI uri = new URI((String) nastavitveHelper.getObrazciURL().get(ObrazciURL.PRENOSCERTIFIKATA.name()));
            byte[] in = (byte[]) t.getForObject(uri, byte[].class);
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(new ByteArrayInputStream(in), TemplateEngineKind.Freemarker);
            
            //InputStream in = GenerateReportHelper.class.getResourceAsStream("ZahtevekZaPrenosCertifikata_v1.1.odt");    
            //IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);

            IContext context = report.createContext();
            context.put("zahtevek", zahtevek);

            ByteArrayOutputStream outODT = new ByteArrayOutputStream();

            report.process(context, outODT);
            // return outODT;

            ByteArrayInputStream inODT = new ByteArrayInputStream(outODT.toByteArray());

            ByteArrayOutputStream outPDF1 = convertODTToPDF(inODT);
            return outPDF1;


        } catch (IOException | XDocReportException e) {
            throw new RuntimeException(e);
        }
    }

    public ByteArrayOutputStream createReportObvestiloPotrdiloOPrejemu_1_4(Zahtevek zahtevek, Imetnik imetnik, Certifikat certifikat, List<OpremaZaObrazec> oprema) throws Exception {
        try {
            // InputStream in = GenerateReportHelper.class.getResourceAsStream("ObvestiloPotrdiloOPrejemu_v1.4.odt");
            // IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);
            RestTemplate t = createNewRestTemplate();
            URI uri = new URI((String) nastavitveHelper.getObrazciURL().get(ObrazciURL.POTRDILOOPREJEMU.name()));
            byte[] in = (byte[]) t.getForObject(uri, byte[].class);
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(new ByteArrayInputStream(in), TemplateEngineKind.Freemarker);
            
            FieldsMetadata metadata = report.createFieldsMetadata();
            metadata.addFieldAsList("oprema.serijska");
            metadata.addFieldAsList("oprema.vrstaOpreme");

            IContext context = report.createContext();

            context.put("zahtevek", zahtevek);
            context.put("imetnik", imetnik);
            context.put("certifikat", certifikat);
            context.put("oprema", oprema);
            ByteArrayOutputStream outODT = new ByteArrayOutputStream();

            report.process(context, outODT);
            //return outODT;
            
            ByteArrayInputStream inODT = new ByteArrayInputStream(outODT.toByteArray());

            ByteArrayOutputStream outPDF1 = convertODTToPDF(inODT);
            return outPDF1;

        } catch (IOException | XDocReportException e) {
            throw new RuntimeException(e);
        }
    }

    private ByteArrayOutputStream convertODTToPDF(InputStream fin) throws FileNotFoundException, IOException {

        RestTemplate t = createNewRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("inputExtension", "odt");
        form.add("FilterName", "writer_pdf_Export");
        form.add("outputExtension", "pdf");
        form.add("InputParameters", "");

        File f = File.createTempFile("temp", ".odt");
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            //f.deleteOnExit();
            OutputStream os = new FileOutputStream(f);
            IOUtils.copy(fin, os);
            os.close();

            form.add("file", new FileSystemResource(f));


            ResponseEntity<String> rest = t.postForEntity(nastavitveHelper.getServerURL(), form, String.class);

            String parseId = rest.getBody();

            bout.write(parseId.getBytes("ISO-8859-1"));
            bout.close();
        } finally {
            f.delete();
        }

        return bout;

    }

    private RestTemplate createNewRestTemplate() {
        CommonsClientHttpRequestFactory clientHttpRequestFactory = new CommonsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(5000);
        clientHttpRequestFactory.setReadTimeout(1000);
        RestTemplate t = new RestTemplate(clientHttpRequestFactory);
        SSLCertificateValidation.disable();
        NullHostnameVerifier verifier = new NullHostnameVerifier();
        final MySimpleClientHttpRequestFactory factory = new MySimpleClientHttpRequestFactory(verifier);
        t.setRequestFactory(factory);
        return t;
    }
}
