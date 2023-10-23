/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.helper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import si.vsrs.cif.mod.Certifikat;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.Kartica;
import si.vsrs.cif.mod.KontaktnaOseba;
import si.vsrs.cif.mod.OpremaZaObrazec;
import si.vsrs.cif.mod.Predstojnik;
import si.vsrs.cif.mod.Zahtevek;
import si.vsrs.cif.mod.ZahtevekZaPreklic;

/**
 *
 * @author andrej
 */
public class GenerateReportHelperTest {

    public GenerateReportHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createReportZahtevek method, of class GenerateReportHelper.
     */
    @Test
    @Ignore
    public void testCreateReportZahtevek() throws FileNotFoundException, IOException, Exception {
        System.out.println("createReportZahtevek");
        Zahtevek zahtevek = new Zahtevek();
        zahtevek.setId(Long.valueOf(100));
        zahtevek.setNaselje("andrej naselje");
        zahtevek.setImeOrganizacije("Organizacija");
        zahtevek.setNazivPoste("Ljubljana");
        zahtevek.setHisnaStevilka("22");
        zahtevek.setPostnaStevilka("1000");
        zahtevek.setUlica("ss");
        zahtevek.setCrtnaKoda("OS235621040411831");
        zahtevek.setKontaktnaOseba(new KontaktnaOseba());
        zahtevek.getKontaktnaOseba().setImeK("Andrej");
        zahtevek.getKontaktnaOseba().setPriimekK("Koncilja");
        zahtevek.getKontaktnaOseba().setFunkcijaK("Sodnik");
        zahtevek.getKontaktnaOseba().seteNaslovK("fdd");
        zahtevek.getKontaktnaOseba().setTelefonK("2322");
        zahtevek.setPredstojnik(new Predstojnik());
        zahtevek.getPredstojnik().setFunkcijaP("Sodnik");
        zahtevek.getPredstojnik().setImeP("Andrej");
        zahtevek.getPredstojnik().setPriimekP("Novi");
        zahtevek.getPredstojnik().seteNaslovP("asdf");

        ArrayList<Imetnik> imetnikList = new ArrayList<Imetnik>();
        for (int i = 0; i < 10; i++) {
            Imetnik imetnik = new Imetnik();
            imetnik.setCrtnaKoda("123");
            imetnik.setDavcna("21312132");
            imetnik.setIme("Andrej" + i);
            imetnik.setPriimek("Koncilja");
            imetnik.setENaslov("Naslov JEEE");
            imetnik.setEmso("1234567890123");
            imetnik.setPotrdilo("Spletno");
            imetnik.setGesloZaPreklic("geslo123");
            imetnik.setPotNaPametnikKartici("DA");
            imetnik.setImaPametnoKartico("NE");
            imetnik.setImaCitalec("DA");
            imetnikList.add(imetnik);
        }

        Imetnik imetnik1 = new Imetnik();
        imetnik1.setCrtnaKoda("IS238692540411831");
        imetnik1.setDavcna("21312132");
        imetnik1.setIme("Andrej");
        imetnik1.setPriimek("Koncilja");
        imetnik1.setENaslov("Naslov JEEE");
        imetnik1.setEmso("1234567890123");
        imetnik1.setPotrdilo("Spletno");
        imetnik1.setGesloZaPreklic("geslo123");
        imetnik1.setPotNaPametnikKartici("DA");
        imetnik1.setImaPametnoKartico("NE");
        imetnik1.setImaCitalec("DA");

        Certifikat certifikat = new Certifikat();
        certifikat.setSerijskaStevilka("2205201300001");
        certifikat.setDatumPoteka(new Date());
        Kartica kartica = new Kartica();
        kartica.setSerijskaStevilkaKartice("345345443534345345");
        kartica.setBarcode("078322");
        certifikat.setKartica(kartica);

        zahtevek.setImetniki(imetnikList);
        
        List<OpremaZaObrazec> oprema = new ArrayList();
        OpremaZaObrazec oprKartica = new OpremaZaObrazec();
        oprKartica.setSerijska(certifikat.getKartica().getSerijskaStevilkaKartice());
        oprKartica.setVrstaOpreme("Kartica zaposlenega v sodstvu");
        oprema.add(oprKartica);
        
        OpremaZaObrazec oprCitalec = new OpremaZaObrazec();
        oprCitalec.setSerijska("1234567890678976");
        oprCitalec.setVrstaOpreme("Čitalec - ActiveIdentity x123");
        oprema.add(oprCitalec);
        
        GenerateReportHelper instance = new GenerateReportHelper();
        //ByteArrayOutputStream result = instance.createReportZahtevek(zahtevek, imetnikList);
        // ByteArrayOutputStream result = instance.createReportObvestiloPotrdiloOPrejemu(zahtevek, imetnik1, certifikat);
        // ByteArrayOutputStream result = instance.createReportZahtevekZaOdklepanjeKartice(zahtevek, imetnik1, kartica);
          ByteArrayOutputStream result = instance.createReportObvestiloPotrdiloOPrejemu_1_4(zahtevek, imetnik1, certifikat, oprema);
        //ByteArrayOutputStream result = instance.createReportZahtevekZaPreklic(zahtevekZaPreklic);
          
        assertNotNull(result);
        FileOutputStream file = new FileOutputStream("testPreklic.odt");
        result.writeTo(file);
        file.close();

        // TODO review the generated test code and remove the default call to fail.
    }

    @Test
    @Ignore
    public void prepareCall() throws FileNotFoundException, IOException {
        String replayMatch;
        String restCommand;
        String value = "";
        try {

            CommonsClientHttpRequestFactory clientHttpRequestFactory = new CommonsClientHttpRequestFactory();
            clientHttpRequestFactory.setConnectTimeout(5000);
            clientHttpRequestFactory.setReadTimeout(1000);

            RestTemplate t = new RestTemplate(clientHttpRequestFactory);
            SSLCertificateValidation.disable();
            NullHostnameVerifier verifier = new NullHostnameVerifier();
            final MySimpleClientHttpRequestFactory factory = new MySimpleClientHttpRequestFactory(verifier);
            t.setRequestFactory(factory);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
            form.add("inputExtension", "odt");
            form.add("FilterName", "writer_pdf_Export");
            //form.add("Content-Type", "multipart/form-data");
            form.add("outputExtension", "pdf");
            form.add("InputParameters", "");

            form.add("file", new FileSystemResource("test.odt"));
            //form.add("Content-Length", String.valueOf(new FileSystemResource("/home/andrej/A/Merila ZK - za objavo.odt").contentLength() ));
            //headers.add("Content-Length", String.valueOf(content.length()));

            HttpEntity entity = new HttpEntity(headers);
            //System.out.println("B: " + entity.toString());

            String serverURL = "https://triglav.sodisce.si:8443/doc-converter/si.vsrs.cif.oo.Application/ConverterServlet";
            ResponseEntity<String> rest = t.postForEntity(serverURL, form, String.class);


            System.out.println("P: " + rest.getStatusCode().getReasonPhrase());
            System.out.println("P: " + rest.getHeaders());
            System.out.println("P: " + rest.getStatusCode());
            System.out.println("P: " + rest.hasBody());
            String parseId = rest.getBody();
            //System.out.println("; " + parseId);
            FileOutputStream fileOut = new FileOutputStream("test.pdf");
            fileOut.write(parseId.getBytes("ISO-8859-1"));
            fileOut.close();


            /*ByteArrayOutputStream bout = new ByteArrayOutputStream();
             bout.write(parseId.getBytes("ISO-8859-1"));
             FileOutputStream fileO = new FileOutputStream("test1.pdf");
             fileO.write(bout.toByteArray());
             fileO.close();
             */
            //https://triglav.sodisce.si:8443/doc-converter/si.vsrs.cif.oo.Application/Application.html
            /*
             * inputExtension	odt
             FilterName	writer_pdf_Export
             outputExtension	pdf
             InputParameters
             * 
             * Content-Type: application/vnd.oasis.opendocument.text
             * Content-Disposition: form-data; name="FileName"; filename="zahtevekv3.odt" Content-Type: application/vnd.oasis.opendocument.text
             */
        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }


    }

    /**
     * @param filePath the name of the file to open. Not sure if it can accept
     * URLs or just filenames. Path handling could be better, and buffer sizes
     * are hardcoded
     */
    private static String readFileAsString(String filePath)
            throws java.io.IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
// insert code here..
}