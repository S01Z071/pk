/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.controllers.KartCertfController;
import si.vsrs.cif.managers.CertifikatRepoManager;
import si.vsrs.cif.managers.ImetnikRepoManager;
import si.vsrs.cif.managers.KarticaRepoManager;
import si.vsrs.cif.managers.StatusCertifikatRepoManager;
import si.vsrs.cif.managers.StatusRepoManager;
import si.vsrs.cif.managers.ZahtevekRepoManager;
import si.vsrs.cif.mod.Certifikat;
import si.vsrs.cif.mod.Kartica;
import si.vsrs.cif.mod.KarticaInCertifikat;
import si.vsrs.cif.mod.SeznamCertifikatov;
import si.vsrs.cif.mod.SeznamCitalcevSigovca;
import si.vsrs.cif.mod.SeznamKarticInCertifikatov;
import si.vsrs.cif.mod.SeznamKarticSigovca;
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
    "classpath:/spring/dao-context_kartCertfTest.xml",
    "classpath:/spring/dao-context.xml",
    "classpath:/spring/app-context.xml",
    //"classpath:/spring/dao-context_test.xml",
    "classpath:/spring/app-context_test.xml"})
@Transactional
public class KartCertfControllerIT extends Assert {

    @Autowired
    private KartCertfController kartCertfController;
    @Autowired
    private NastavitveHelper nastavitveHelper;
    @Autowired
    KarticaRepoManager karticaRepoManager;
    @Autowired
    ImetnikRepoManager imetnikRepoManager;
    @Autowired
    CertifikatRepoManager certifikatRepoManager;
    @Autowired
    StatusCertifikatRepoManager statusCertifikatRepoManager;
    @Autowired
    ZahtevekRepoManager zahtevekRepoManager;
    @Autowired
    StatusRepoManager statusRepoManager;
    private Uporabnik uporabnikUser = new Uporabnik();
    private Uporabnik uporabnikAdmin = new Uporabnik();

    public KartCertfControllerIT() {
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
        uporabnikUser.setKadrovskStevilka("T200003");

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
    public void vnosKarticeTest() {
        assertEquals("vnosKartice", kartCertfController.vnosKartice().getViewName());
    }

    @Test
    public void vnosCertifikataTest() {
        assertEquals("vnosCertifikata", kartCertfController.vnosCertifikata().getViewName());
    }

    //@Test
    public void vnosKartTest() {
    }

    @Test
    public void vnosConfirmKarticaKarticeZeTest() {
        SeznamKarticInCertifikatov seznamKarticInCertifikatov = new SeznamKarticInCertifikatov();
        KarticaInCertifikat karticaInCertifikat = new KarticaInCertifikat();
        karticaInCertifikat.setCertifikat(new Certifikat());
        karticaInCertifikat.setKartica(karticaRepoManager.getKarticaByID(10));
        List<KarticaInCertifikat> karticeInCertifikati = new ArrayList();
        karticeInCertifikati.add(karticaInCertifikat);
        seznamKarticInCertifikatov.setKarticaInCertifikat(karticeInCertifikati);
        ModelAndView modelAndView = kartCertfController.vnosConfirmKartica(seznamKarticInCertifikatov);
        assertEquals("vnosKartice", modelAndView.getViewName());
        assertEquals(0, modelAndView.getModel().get("uspesno"));
        assertEquals(1, modelAndView.getModel().get("karticaZe"));
        assertEquals(0, modelAndView.getModel().get("imetnikNi"));
        assertEquals(0, modelAndView.getModel().get("certfNe"));
        assertEquals(0, modelAndView.getModel().get("avtorStNapaka"));
    }

    @Test
    public void vnosConfirmKarticaKarticeVrnjena() {
        SeznamKarticInCertifikatov seznamKarticInCertifikatov = new SeznamKarticInCertifikatov();
        KarticaInCertifikat karticaInCertifikat = new KarticaInCertifikat();
        karticaInCertifikat.setCertifikat(new Certifikat());
        karticaInCertifikat.setKartica(karticaRepoManager.getKarticaByID(10));
        karticaInCertifikat.getKartica().setDatumVrnitve(new Date());
        List<KarticaInCertifikat> karticeInCertifikati = new ArrayList();
        karticeInCertifikati.add(karticaInCertifikat);
        seznamKarticInCertifikatov.setKarticaInCertifikat(karticeInCertifikati);
        ModelAndView modelAndView = kartCertfController.vnosConfirmKartica(seznamKarticInCertifikatov);
        assertEquals("vnosKartice", modelAndView.getViewName());
        assertEquals(0, modelAndView.getModel().get("uspesno"));
        assertEquals(0, modelAndView.getModel().get("karticaZe"));
        assertEquals(1, modelAndView.getModel().get("imetnikNi"));
        assertEquals(0, modelAndView.getModel().get("certfNe"));
        assertEquals(0, modelAndView.getModel().get("avtorStNapaka"));
    }

    @Test
    public void vnosConfirmKarticaImetnikNiTest() {
        SeznamKarticInCertifikatov seznamKarticInCertifikatov = new SeznamKarticInCertifikatov();
        KarticaInCertifikat karticaInCertifikat = new KarticaInCertifikat();
        karticaInCertifikat.setCertifikat(new Certifikat());
        karticaInCertifikat.setKartica(new Kartica());
        List<KarticaInCertifikat> karticeInCertifikati = new ArrayList();
        karticeInCertifikati.add(karticaInCertifikat);
        seznamKarticInCertifikatov.setKarticaInCertifikat(karticeInCertifikati);
        ModelAndView modelAndView = kartCertfController.vnosConfirmKartica(seznamKarticInCertifikatov);
        assertEquals("vnosKartice", modelAndView.getViewName());
        assertEquals(0, modelAndView.getModel().get("uspesno"));
        assertEquals(0, modelAndView.getModel().get("karticaZe"));
        assertEquals(1, modelAndView.getModel().get("imetnikNi"));
        assertEquals(0, modelAndView.getModel().get("certfNe"));
        assertEquals(0, modelAndView.getModel().get("avtorStNapaka"));
    }

    @Test
    public void vnosConfirmKarticaUspesnoTest() {
        Certifikat certifikat = new Certifikat("44444444", "123456", "andrej.koncilja@sodisce.si", statusCertifikatRepoManager.getStatusCertifikat("01"));
        Kartica kartica = new Kartica();
        kartica.setSerijskaStevilkaKartice("5555");
        kartica.setUserPass("XXXX-XXXX-XXXX");

        SeznamKarticInCertifikatov seznamKarticInCertifikatov = new SeznamKarticInCertifikatov();
        KarticaInCertifikat karticaInCertifikat = new KarticaInCertifikat();
        karticaInCertifikat.setCertifikat(certifikat);
        karticaInCertifikat.setKartica(kartica);
        List<KarticaInCertifikat> karticeInCertifikati = new ArrayList();
        karticeInCertifikati.add(karticaInCertifikat);
        seznamKarticInCertifikatov.setKarticaInCertifikat(karticeInCertifikati);
        ModelAndView modelAndView = kartCertfController.vnosConfirmKartica(seznamKarticInCertifikatov);
        assertEquals("vnosKartice", modelAndView.getViewName());
        assertEquals(1, modelAndView.getModel().get("uspesno"));
        assertEquals(0, modelAndView.getModel().get("karticaZe"));
        assertEquals(0, modelAndView.getModel().get("imetnikNi"));
        assertEquals(0, modelAndView.getModel().get("certfNe"));
        assertEquals(0, modelAndView.getModel().get("avtorStNapaka"));
    }

    @Test
    public void vnosConfirmKarticaCertfNeTest() {
        Certifikat certifikat = new Certifikat("44444444", "654321", "andrej.koncilja@sodisce.si", statusCertifikatRepoManager.getStatusCertifikat("01"));
        Kartica kartica = new Kartica();
        kartica.setSerijskaStevilkaKartice("6666");
        SeznamKarticInCertifikatov seznamKarticInCertifikatov = new SeznamKarticInCertifikatov();
        KarticaInCertifikat karticaInCertifikat = new KarticaInCertifikat();
        karticaInCertifikat.setCertifikat(certifikat);
        karticaInCertifikat.setKartica(kartica);
        List<KarticaInCertifikat> karticeInCertifikati = new ArrayList();
        karticeInCertifikati.add(karticaInCertifikat);
        seznamKarticInCertifikatov.setKarticaInCertifikat(karticeInCertifikati);

        ModelAndView modelAndView = kartCertfController.vnosConfirmKartica(seznamKarticInCertifikatov);
        assertEquals("vnosKartice", modelAndView.getViewName());
        assertEquals(0, modelAndView.getModel().get("uspesno"));
        assertEquals(0, modelAndView.getModel().get("karticaZe"));
        assertEquals(0, modelAndView.getModel().get("imetnikNi"));
        assertEquals(1, modelAndView.getModel().get("certfNe"));
        assertEquals(0, modelAndView.getModel().get("avtorStNapaka"));
    }

    @Test
    public void vnosConfirmKarticaAvtorStNapakaTest() {
        Certifikat certifikat = new Certifikat("44444444", "123456", "andrej.koncilja@sodisce.si", statusCertifikatRepoManager.getStatusCertifikat("01"));
        Kartica kartica = new Kartica();
        kartica.setSerijskaStevilkaKartice("5555");
        kartica.setUserPass("XX-XX-XX");   //Da se ne ujema  
        SeznamKarticInCertifikatov seznamKarticInCertifikatov = new SeznamKarticInCertifikatov();
        KarticaInCertifikat karticaInCertifikat = new KarticaInCertifikat();
        karticaInCertifikat.setCertifikat(certifikat);
        karticaInCertifikat.setKartica(kartica);
        List<KarticaInCertifikat> karticeInCertifikati = new ArrayList();
        karticeInCertifikati.add(karticaInCertifikat);
        seznamKarticInCertifikatov.setKarticaInCertifikat(karticeInCertifikati);
        ModelAndView modelAndView = kartCertfController.vnosConfirmKartica(seznamKarticInCertifikatov);
        assertEquals("vnosKartice", modelAndView.getViewName());
        assertEquals(0, modelAndView.getModel().get("uspesno"));
        assertEquals(0, modelAndView.getModel().get("karticaZe"));
        assertEquals(0, modelAndView.getModel().get("imetnikNi"));
        assertEquals(0, modelAndView.getModel().get("certfNe"));
        assertEquals(1, modelAndView.getModel().get("avtorStNapaka"));
    }

    //@Test
    public void vnosCertfTest() {
    }

    @Test
    public void vnosConfirmCertifikatNeuspesnoTest() {
        SeznamCertifikatov seznamCertifikatov = new SeznamCertifikatov();
        List<Certifikat> certifikati = new ArrayList();
        Certifikat certifikat = new Certifikat("44444444", "123456", "andrej.koncilja@sodisce.si", statusCertifikatRepoManager.getStatusCertifikat("01"));
        certifikati.add(certifikat);;
        seznamCertifikatov.setCertifikati(certifikati);

        ModelAndView modelAndView = kartCertfController.vnosConfirmCertifikat(seznamCertifikatov);
        assertEquals("vnosCertifikata", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("seznamCertifikatov"));
        assertEquals(0, modelAndView.getModel().get("uspesno"));
        assertEquals(1, modelAndView.getModel().get("neuspesno"));
        assertEquals(0, modelAndView.getModel().get("povezanih"));
        assertEquals(0, modelAndView.getModel().get("nepovezanih"));
    }

    @Test
    public void vnosConfirmCertifikatPovezanihTest() {
        SeznamCertifikatov seznamCertifikatov = new SeznamCertifikatov();
        List<Certifikat> certifikati = new ArrayList();
        Certifikat certifikat = new Certifikat("44444444", "987789", "andrej.koncilja@sodisce.si", statusCertifikatRepoManager.getStatusCertifikat("01"));
        certifikati.add(certifikat);;
        seznamCertifikatov.setCertifikati(certifikati);

        ModelAndView modelAndView = kartCertfController.vnosConfirmCertifikat(seznamCertifikatov);
        assertEquals("vnosCertifikata", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("seznamCertifikatov"));
        assertEquals(1, modelAndView.getModel().get("uspesno"));
        assertEquals(0, modelAndView.getModel().get("neuspesno"));
        assertEquals(1, modelAndView.getModel().get("povezanih"));
        assertEquals(0, modelAndView.getModel().get("nepovezanih"));
    }

    @Test
    public void vnosConfirmCertifikatNepovezanihTest() {
        SeznamCertifikatov seznamCertifikatov = new SeznamCertifikatov();
        List<Certifikat> certifikati = new ArrayList();
        Certifikat certifikat = new Certifikat("44444444", "987789", "andrej1.koncilja1@sodisce.si", statusCertifikatRepoManager.getStatusCertifikat("01"));
        certifikati.add(certifikat);;
        seznamCertifikatov.setCertifikati(certifikati);

        ModelAndView modelAndView = kartCertfController.vnosConfirmCertifikat(seznamCertifikatov);
        assertEquals("vnosCertifikata", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("seznamCertifikatov"));
        assertEquals(1, modelAndView.getModel().get("uspesno"));
        assertEquals(0, modelAndView.getModel().get("neuspesno"));
        assertEquals(0, modelAndView.getModel().get("povezanih"));
        assertEquals(1, modelAndView.getModel().get("nepovezanih"));
    }

    @Test
    public void poveziCertfImetnikZePovezanTest() {
        ModelAndView modelAndView = kartCertfController.poveziCertfImetnik(10);
        assertEquals("certfPrevzem", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("certifikati"));
        assertEquals("Certifikat je že povezan.", modelAndView.getModel().get("error"));
    }

    @Test
    public void poveziCertfNiImetnikaTest() {
        ModelAndView modelAndView = kartCertfController.poveziCertfImetnik(11);
        assertEquals("certfPrevzem", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("certifikati"));
        assertEquals("Imetnik ni vnešen ali pa ni v statusu posredovano na SIGOV-CA.", modelAndView.getModel().get("error"));
    }

    @Test
    public void poveziCertfZePrevzetTest() {
        ModelAndView modelAndView = kartCertfController.poveziCertfImetnik(12);
        assertEquals("certfPrevzem", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("certifikati"));
        assertEquals("Certifikat je že prevzet.", modelAndView.getModel().get("error"));
    }

    @Test
    public void poveziCertfPoveziUspesnoTest() {
        ModelAndView modelAndView = kartCertfController.poveziCertfImetnik(13);
        assertEquals("certfPrevzem", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("certifikati"));
        assertNull(modelAndView.getModel().get("error"));
    }

    @Test
    public void certfPrevzemTest() {
        ModelAndView modelAndView = kartCertfController.certfPrevzem();
        assertEquals("certfPrevzem", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("certifikati"));
    }

    @Test
    public void pregledPripKarticTest() {
        ModelAndView modelAndView = kartCertfController.pregledPripKartic();
        assertEquals("pregledPripKartic", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("certifikati"));
    }

    @Test
    public void iskPripKarticTest() {
        assertEquals("iskPripKartic", kartCertfController.iskPripKartic().getViewName());
    }

    @Test
    public void iskPripKarticProcessTest() {
        ModelAndView modelAndView = kartCertfController.iskPripKarticProcess("077221");
        assertEquals("iskPripKartic", modelAndView.getViewName());
        assertEquals(1, modelAndView.getModel().get("natisni"));
        assertNotNull(modelAndView.getModel().get("certifikati"));
    }

    @Test
    public void potrdiloPrejetoTest() {
        assertEquals("potrdiloPrejeto", kartCertfController.potrdiloPrejeto().getViewName());
    }

    @Test
    public void potrdiloPrejetoProcessTest() {
        ModelAndView modelAndView = kartCertfController.potrdiloPrejetoProcess("12345");
        assertEquals("potrdiloPrejeto", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("certifikati"));
    }

    @Test
    public void potrdiloPrejetoPregledTest() {
        ModelAndView modelAndView = kartCertfController.potrdiloPrejetoPregled();
        assertEquals("potrdiloPrejetoPregled", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("certifikati"));
    }

    @Test
    public void pregledCertfUserTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = kartCertfController.pregledCertf(httpSession);
        assertEquals("pregledCertf", modelAndView.getViewName());
        List<Certifikat> certifikat = (List<Certifikat>) modelAndView.getModel().get("certifikati");
        assertEquals(1, certifikat.size());
        context.assertIsSatisfied();
    }

    @Test
    public void pregledCertfAdminSessionNullTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
                oneOf(httpSession).getAttribute("sodisceSifra");
                will(returnValue(null));
                oneOf(httpSession).setAttribute("sodisceSifra", "S01");
                oneOf(httpSession).getAttribute("sodisceSifra");
                will(returnValue("S01"));
            }
        });
        ModelAndView modelAndView = kartCertfController.pregledCertf(httpSession);
        assertEquals("pregledCertf", modelAndView.getViewName());
        List<Certifikat> certifikat = (List<Certifikat>) modelAndView.getModel().get("certifikati");
        assertEquals(4, certifikat.size());

        context.assertIsSatisfied();
    }

    @Test
    public void pregledCertfAdminSessionNotNullTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
                oneOf(httpSession).getAttribute("sodisceSifra");
                will(returnValue("S02"));
                oneOf(httpSession).getAttribute("sodisceSifra");
                will(returnValue("S02"));
            }
        });
        ModelAndView modelAndView = kartCertfController.pregledCertf(httpSession);
        assertEquals("pregledCertf", modelAndView.getViewName());
        List<Certifikat> certifikat = (List<Certifikat>) modelAndView.getModel().get("certifikati");
        assertEquals(1, certifikat.size());

        context.assertIsSatisfied();
    }

    //@Test
    public void adminConfirmIskanjeTest() {
    }

    @Test
    public void pregledKarticSigovcaUserTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = kartCertfController.pregledKarticSigovca(httpSession);
        assertEquals("pregledKarticSigovca", modelAndView.getViewName());
        Map<String, List<SeznamKarticSigovca>> seznamKarticSigovca = (Map<String, List<SeznamKarticSigovca>>) modelAndView.getModel().get("seznamKarticSigovca");
        assertEquals(1, seznamKarticSigovca.size());
        assertEquals(1, seznamKarticSigovca.get("Janez Novak").size());
        context.assertIsSatisfied();
    }

    @Test
    public void pregledKarticSigovcaAdminVseTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
                oneOf(httpSession).getAttribute("sodisceSifra");
                will(returnValue("S23"));
                oneOf(httpSession).getAttribute("sodisceSifra");
                will(returnValue("S23"));
                oneOf(httpSession).getAttribute("vrnjeno");
                will(returnValue("1"));
                oneOf(httpSession).getAttribute("vrnjeno");
                will(returnValue("1"));
                oneOf(httpSession).getAttribute("izdano");
                will(returnValue("1"));
                oneOf(httpSession).getAttribute("izdano");
                will(returnValue("1"));
            }
        });
        ModelAndView modelAndView = kartCertfController.pregledKarticSigovca(httpSession);
        assertEquals("pregledKarticSigovca", modelAndView.getViewName());
        Map<String, List<SeznamKarticSigovca>> seznamKarticSigovca = (Map<String, List<SeznamKarticSigovca>>) modelAndView.getModel().get("seznamKarticSigovca");
        assertEquals(1, seznamKarticSigovca.size());
        assertEquals(2, seznamKarticSigovca.get("Jana Kavsek").size());
        assertTrue((Boolean) modelAndView.getModel().get("vrnjeno"));
        assertTrue((Boolean) modelAndView.getModel().get("izdano"));
        context.assertIsSatisfied();
    }

    @Test
    public void pregledKarticSigovcaAdminVrnjenoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
                oneOf(httpSession).getAttribute("sodisceSifra");
                will(returnValue("S23"));
                oneOf(httpSession).getAttribute("sodisceSifra");
                will(returnValue("S23"));
                oneOf(httpSession).getAttribute("vrnjeno");
                will(returnValue("1"));
                oneOf(httpSession).getAttribute("vrnjeno");
                will(returnValue("1"));
                oneOf(httpSession).getAttribute("izdano");
                will(returnValue(null));
            }
        });
        ModelAndView modelAndView = kartCertfController.pregledKarticSigovca(httpSession);
        assertEquals("pregledKarticSigovca", modelAndView.getViewName());
        Map<String, List<SeznamKarticSigovca>> seznamKarticSigovca = (Map<String, List<SeznamKarticSigovca>>) modelAndView.getModel().get("seznamKarticSigovca");
        assertEquals(1, seznamKarticSigovca.size());
        assertEquals(1, seznamKarticSigovca.get("Jana Kavsek").size());
        assertTrue((Boolean) modelAndView.getModel().get("vrnjeno"));
        assertFalse((Boolean) modelAndView.getModel().get("izdano"));
        context.assertIsSatisfied();
    }

    @Test
    public void pregledKarticSigovcaAdminIzdanoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
                oneOf(httpSession).getAttribute("sodisceSifra");
                will(returnValue("S23"));
                oneOf(httpSession).getAttribute("sodisceSifra");
                will(returnValue("S23"));
                oneOf(httpSession).getAttribute("vrnjeno");
                will(returnValue(null));
                oneOf(httpSession).getAttribute("izdano");
                will(returnValue("1"));
                oneOf(httpSession).getAttribute("izdano");
                will(returnValue("1"));
            }
        });
        ModelAndView modelAndView = kartCertfController.pregledKarticSigovca(httpSession);
        assertEquals("pregledKarticSigovca", modelAndView.getViewName());
        Map<String, List<SeznamKarticSigovca>> seznamKarticSigovca = (Map<String, List<SeznamKarticSigovca>>) modelAndView.getModel().get("seznamKarticSigovca");
        assertEquals(1, seznamKarticSigovca.size());
        assertEquals(1, seznamKarticSigovca.get("Jana Kavsek").size());
        assertFalse((Boolean) modelAndView.getModel().get("vrnjeno"));
        assertTrue((Boolean) modelAndView.getModel().get("izdano"));
        context.assertIsSatisfied();
    }

    //@Test
    public void AdminIskanjeKartSigTest() {
    }

    @Test
    public void pregledCitalcevSigovcaUserTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = kartCertfController.pregledCitalcevSigovca(httpSession);
        assertEquals("pregledCitalcevSigovca", modelAndView.getViewName());
        Map<String, List<SeznamCitalcevSigovca>> seznamCitalcevSigovca = (Map<String, List<SeznamCitalcevSigovca>>) modelAndView.getModel().get("seznamCitalcevSigovca");
        assertEquals(1, seznamCitalcevSigovca.size());
        assertEquals(1, seznamCitalcevSigovca.get("Tattjana Premk").size());
        context.assertIsSatisfied();
    }

    @Test
    public void pregledCitalcevSigovcaAdminVseTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
                oneOf(httpSession).getAttribute("sodisceSifra");
                will(returnValue("S23"));
                oneOf(httpSession).getAttribute("sodisceSifra");
                will(returnValue("S23"));
                oneOf(httpSession).getAttribute("vrnjeno");
                will(returnValue("1"));
                oneOf(httpSession).getAttribute("vrnjeno");
                will(returnValue("1"));
                oneOf(httpSession).getAttribute("izdano");
                will(returnValue("1"));
                oneOf(httpSession).getAttribute("izdano");
                will(returnValue("1"));
            }
        });
        ModelAndView modelAndView = kartCertfController.pregledCitalcevSigovca(httpSession);
        assertEquals("pregledCitalcevSigovca", modelAndView.getViewName());
        Map<String, List<SeznamCitalcevSigovca>> seznamCitalcevSigovca = (Map<String, List<SeznamCitalcevSigovca>>) modelAndView.getModel().get("seznamCitalcevSigovca");
        assertEquals(4, seznamCitalcevSigovca.size());
        assertEquals(1, seznamCitalcevSigovca.get("Sasa Miketic").size());
        assertEquals(1, seznamCitalcevSigovca.get("Lidija Leskosek Nikolic").size());
        assertEquals(1, seznamCitalcevSigovca.get("Magda Teppey").size());
        assertEquals(1, seznamCitalcevSigovca.get("Mojca Kumalic").size());
        assertTrue((Boolean) modelAndView.getModel().get("vrnjeno"));
        assertTrue((Boolean) modelAndView.getModel().get("izdano"));
        context.assertIsSatisfied();
    }

    @Test
    public void pregledCitalcevSigovcaAdminVrnjenoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
                oneOf(httpSession).getAttribute("sodisceSifra");
                will(returnValue("S23"));
                oneOf(httpSession).getAttribute("sodisceSifra");
                will(returnValue("S23"));
                oneOf(httpSession).getAttribute("vrnjeno");
                will(returnValue("1"));
                oneOf(httpSession).getAttribute("vrnjeno");
                will(returnValue("1"));
                oneOf(httpSession).getAttribute("izdano");
                will(returnValue(null));
            }
        });
        ModelAndView modelAndView = kartCertfController.pregledCitalcevSigovca(httpSession);
        assertEquals("pregledCitalcevSigovca", modelAndView.getViewName());
        Map<String, List<SeznamCitalcevSigovca>> seznamCitalcevSigovca = (Map<String, List<SeznamCitalcevSigovca>>) modelAndView.getModel().get("seznamCitalcevSigovca");
        assertEquals(2, seznamCitalcevSigovca.size());
        System.out.println("INFOOO:" + seznamCitalcevSigovca.keySet());
        assertEquals(1, seznamCitalcevSigovca.get("Magda Teppey").size());
        assertEquals(1, seznamCitalcevSigovca.get("Mojca Kumalic").size());
        assertTrue((Boolean) modelAndView.getModel().get("vrnjeno"));
        assertFalse((Boolean) modelAndView.getModel().get("izdano"));
        context.assertIsSatisfied();
    }

    @Test
    public void pregledCitalcevSigovcaAdminIzdanoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
                oneOf(httpSession).getAttribute("sodisceSifra");
                will(returnValue("S23"));
                oneOf(httpSession).getAttribute("sodisceSifra");
                will(returnValue("S23"));
                oneOf(httpSession).getAttribute("vrnjeno");
                will(returnValue(null));
                oneOf(httpSession).getAttribute("izdano");
                will(returnValue("1"));
                oneOf(httpSession).getAttribute("izdano");
                will(returnValue("1"));
            }
        });
        ModelAndView modelAndView = kartCertfController.pregledCitalcevSigovca(httpSession);
        assertEquals("pregledCitalcevSigovca", modelAndView.getViewName());
        Map<String, List<SeznamCitalcevSigovca>> seznamCitalcevSigovca = (Map<String, List<SeznamCitalcevSigovca>>) modelAndView.getModel().get("seznamCitalcevSigovca");
        assertEquals(2, seznamCitalcevSigovca.size());
        assertEquals(1, seznamCitalcevSigovca.get("Sasa Miketic").size());
        assertEquals(1, seznamCitalcevSigovca.get("Lidija Leskosek Nikolic").size());
        assertFalse((Boolean) modelAndView.getModel().get("vrnjeno"));
        assertTrue((Boolean) modelAndView.getModel().get("izdano"));
        context.assertIsSatisfied();
    }

    //@Test
    public void AdminIskanjeCitSigTest() {
    }

    @Test
    public void pregledKarticVSRSTest() {
        ModelAndView modelAndView = kartCertfController.pregledKarticVSRS(1);
        assertEquals("pregledKarticVSRS", modelAndView.getViewName());
        List<Kartica> kartice = (List<Kartica>) modelAndView.getModel().get("kartice");
        assertEquals(2, kartice.size());
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1.0, modelAndView.getModel().get("izpisCount"));
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));


    }

    @Test
    public void pregledKarticVSRSIskanjeNiZadetkovTest() {
        ModelAndView modelAndView = kartCertfController.pregledKarticVSRSIskanje("niZadetka", null, null);
        assertEquals("pregledKarticVSRS", modelAndView.getViewName());
        List<Kartica> kartice = (List<Kartica>) modelAndView.getModel().get("kartice");
        assertEquals(0, kartice.size());
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1, modelAndView.getModel().get("izpisCount"));
        assertEquals("Ni zadetkov.", modelAndView.getModel().get("error"));
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));
    }

    @Test
    public void pregledKarticVSRSIskanjeSerijskaStTest() {
        ModelAndView modelAndView = kartCertfController.pregledKarticVSRSIskanje("570223512088E30B3B17EEEE", null, null);
        assertEquals("pregledKarticVSRS", modelAndView.getViewName());
        List<Kartica> kartice = (List<Kartica>) modelAndView.getModel().get("kartice");
        assertEquals(1, kartice.size());
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1, modelAndView.getModel().get("izpisCount"));
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));
    }

    @Test
    public void pregledKarticVSRSIskanjeCrtnaKodaKarticeTest() {
        ModelAndView modelAndView = kartCertfController.pregledKarticVSRSIskanje("078333", null, null);
        assertEquals("pregledKarticVSRS", modelAndView.getViewName());
        List<Kartica> kartice = (List<Kartica>) modelAndView.getModel().get("kartice");
        assertEquals(1, kartice.size());
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1, modelAndView.getModel().get("izpisCount"));
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));
    }

    @Test
    public void pregledKarticVSRSIskanjeCrtnaKodaImetnikaTest() {
        ModelAndView modelAndView = kartCertfController.pregledKarticVSRSIskanje("IS011246305862831", null, null);
        assertEquals("pregledKarticVSRS", modelAndView.getViewName());
        List<Kartica> kartice = (List<Kartica>) modelAndView.getModel().get("kartice");
        assertEquals(1, kartice.size());
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1, modelAndView.getModel().get("izpisCount"));
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));
    }

    @Test
    public void pregledKarticVSRSIskanjeENaslovTest() {
        ModelAndView modelAndView = kartCertfController.pregledKarticVSRSIskanje("miha.koncilja@sodisce.si", null, null);
        assertEquals("pregledKarticVSRS", modelAndView.getViewName());
        List<Kartica> kartice = (List<Kartica>) modelAndView.getModel().get("kartice");
        assertEquals(1, kartice.size());
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1, modelAndView.getModel().get("izpisCount"));
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));
    }

    @Test
    public void pregledKarticVSRSIskanjeImeInPriimekTest() {
        ModelAndView modelAndView = kartCertfController.pregledKarticVSRSIskanje("miha koncilja", null, null);
        assertEquals("pregledKarticVSRS", modelAndView.getViewName());
        List<Kartica> kartice = (List<Kartica>) modelAndView.getModel().get("kartice");
        assertEquals(1, kartice.size());
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1, modelAndView.getModel().get("izpisCount"));
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));
    }

    @Test
    public void pregledKarticVSRSIskanjeVseTest() {
        ModelAndView modelAndView = kartCertfController.pregledKarticVSRSIskanje("*", null, null);
        assertEquals("pregledKarticVSRS", modelAndView.getViewName());
    }

    @Test
    public void pregledKarticVSRSIskanjeTestiranjeDatuma() {
        ModelAndView modelAndView = kartCertfController.pregledKarticVSRSIskanje("test", "01.01.2013", "01.01.2015");
        assertEquals("pregledKarticVSRS", modelAndView.getViewName());
        List<Kartica> kartice = (List<Kartica>) modelAndView.getModel().get("kartice");
        assertEquals("01.01.2013", modelAndView.getModel().get("datumOd"));
        assertEquals("01.01.2015", modelAndView.getModel().get("datumDo"));
        assertEquals("test", modelAndView.getModel().get("iskano"));
        assertEquals(0, kartice.size());
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1, modelAndView.getModel().get("izpisCount"));
    }
}
