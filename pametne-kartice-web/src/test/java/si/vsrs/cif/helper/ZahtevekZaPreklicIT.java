/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.helper;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.controllers.ZahtevekZaPreklicController;
import si.vsrs.cif.mod.Certifikat;
import si.vsrs.cif.mod.Kartica;
import si.vsrs.cif.mod.StatusLogPreklic;
import si.vsrs.cif.mod.ZahtevekZaPreklic;
import si.vsrs.cif.mod.web.Sodisce;
import si.vsrs.cif.mod.web.Uporabnik;
import si.vsrs.cif.mod.web.Vloga;
import si.vsrs.cif.mod.web.VlogaSodisce;

/**
 *
 * @author Uporabnik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:/spring/spring-servlet_it.xml",
    "classpath:/spring/dao-context_it.xml",
    "classpath:/spring/dao-context.xml",
    "classpath:/spring/app-context.xml",
    //"classpath:/spring/dao-context_test.xml",
    "classpath:/spring/app-context_test.xml"})
@Transactional
public class ZahtevekZaPreklicIT {

    @Autowired
    ZahtevekZaPreklicController zahtevekZaPreklicController;
    @Autowired
    NastavitveHelper nastavitveHelper;
    private Uporabnik uporabnikUser = new Uporabnik();
    private Uporabnik uporabnikUser1 = new Uporabnik();
    private Uporabnik uporabnikAdmin = new Uporabnik();

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

        uporabnikUser1 = new Uporabnik();
        VlogaSodisce vlogaSodisce2 = new VlogaSodisce();
        Sodisce sodisce2 = new Sodisce();
        Vloga vloga2 = new Vloga();
        vloga2.setId("001");
        sodisce2.setId("S25");
        vlogaSodisce2.setSodisce(sodisce2);
        vlogaSodisce2.setVloga(vloga2);
        uporabnikUser1.setIzbranaVlogaSodisce(vlogaSodisce2);
        uporabnikUser1.setKadrovskStevilka("T200001");

    }

    @After
    public void tearDown() {
    }

    @Test
    public void zahtevekZaPreklicTest() {
        ModelAndView modelAndView = zahtevekZaPreklicController.zahtevekZaPreklic();
        assertEquals("dodajZahtevekZaPreklic", modelAndView.getViewName());
    }

    @Test
    public void zahtevekZaPreklicIskanjeAdminTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });

        ModelAndView modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicIskanje("m", httpSession);
        assertEquals("dodajZahtevekZaPreklic", modelAndView.getViewName());
        List<Certifikat> certifikati = (List<Certifikat>) modelAndView.getModel().get("certifikati");
        assertEquals(2, certifikati.size());

        context.assertIsSatisfied();

        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicIskanje("ni zadetkov", httpSession);
        assertEquals("dodajZahtevekZaPreklic", modelAndView.getViewName());
        assertEquals("Ni zadetkov.", modelAndView.getModel().get("error"));

        context.assertIsSatisfied();


        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicIskanje("1234555890321", httpSession);
        assertEquals("redirect:/ZahtevekZaPreklic/dodaj/10", modelAndView.getViewName());

        context.assertIsSatisfied();

    }

    @Test
    public void zahtevekZaPreklicIskanjeUserTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });

        ModelAndView modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicIskanje("m", httpSession);
        assertEquals("dodajZahtevekZaPreklic", modelAndView.getViewName());
        List<Certifikat> certifikati = (List<Certifikat>) modelAndView.getModel().get("certifikati");
        assertEquals(2, certifikati.size());

        context.assertIsSatisfied();

        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicIskanje("ni zadetkov", httpSession);
        assertEquals("dodajZahtevekZaPreklic", modelAndView.getViewName());
        assertEquals("Ni zadetkov.", modelAndView.getModel().get("error"));

        context.assertIsSatisfied();


        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicIskanje("1234555890321", httpSession);
        assertEquals("redirect:/ZahtevekZaPreklic/dodaj/10", modelAndView.getViewName());

        context.assertIsSatisfied();

    }

    @Test
    public void zahtevekZaPreklicDodajTest() {
        ModelAndView modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicDodaj(10);
        assertEquals("dodajZahtevekZaPreklic", modelAndView.getViewName());
        ZahtevekZaPreklic zahtevek = (ZahtevekZaPreklic) modelAndView.getModel().get("zahtevekZaPreklic");
        checkZahtevekZaPreklic(zahtevek, "Vrhovno sodisce RS", "miha.koncilja@sodisce.si", "Miha", "Koncilja", Long.valueOf("10"), "1234555890321");
    }

    @Test
    public void zahtevekZaPreklicDodajProcessTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final BindingResult bindingResult = context.mock(BindingResult.class);
        context.checking(new Expectations() {
            {
                allowing(bindingResult).hasErrors();
                will(returnValue(false));

                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ZahtevekZaPreklic zahtevekZaPreklic = (ZahtevekZaPreklic) zahtevekZaPreklicController.zahtevekZaPreklicDodaj(10).getModel().get("zahtevekZaPreklic");
        ModelAndView modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicDodajProcess(zahtevekZaPreklic, bindingResult, httpSession);
        assertEquals("redirect:/ZahtevekZaPreklic/podrobno/1", modelAndView.getViewName());
        /*  ZahtevekZaPreklic zahtevek = (ZahtevekZaPreklic) modelAndView.getModel().get("zahtevekZaPreklic");
         checkZahtevekZaPreklic(zahtevek, "Vrhovno sodisce RS", "miha.koncilja@sodisce.si", "Miha", "Koncilja", Long.valueOf("10"), "1234555890321");
         assertEquals("Zahtevek shranjen. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov.", modelAndView.getModel().get("info"));
         */
        ModelAndView modelAndView1 = zahtevekZaPreklicController.zahtevekZaPreklicDodajProcess(zahtevekZaPreklic, bindingResult, httpSession);
        assertEquals("dodajZahtevekZaPreklic", modelAndView1.getViewName());
        assertEquals("Zahtevek za to serijsko številko certifikata je že v pripravi. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov.", modelAndView1.getModel().get("info"));

        context.assertIsSatisfied();
    }

    @Test
    public void izpisPodatkovAdminTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });

        ModelAndView modelAndView = zahtevekZaPreklicController.izpisPodatkov(httpSession, 1);
        List<ZahtevekZaPreklic> zahtevkiZaPreklic = (List<ZahtevekZaPreklic>) modelAndView.getModel().get("zahtevkiZaPreklic");
        assertEquals(4, zahtevkiZaPreklic.size());
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1.0, modelAndView.getModel().get("izpisCount"));
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));
        context.assertIsSatisfied();
    }

    @Test
    public void izpisPodatkovUserTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });

        ModelAndView modelAndView = zahtevekZaPreklicController.izpisPodatkov(httpSession, 1);
        List<ZahtevekZaPreklic> zahtevkiZaPreklic = (List<ZahtevekZaPreklic>) modelAndView.getModel().get("zahtevkiZaPreklic");
        assertEquals(1, zahtevkiZaPreklic.size());
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1.0, modelAndView.getModel().get("izpisCount"));
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaPreklicPodrobnoTest() {
        ModelAndView modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicPodrobno(11);
        assertEquals("izpisPodrobnoPreklic", modelAndView.getViewName());
        ZahtevekZaPreklic zahtevek = (ZahtevekZaPreklic) modelAndView.getModel().get("zahtevekZaPreklic");
        checkZahtevekZaPreklic(zahtevek, "Vrhovno sodisce RS", "miha.koncilja@sodisce.si", "Miha", "Koncilja", Long.valueOf("10"), "2234567392123");
    }

    @Test
    public void zahtevekZaPreklicPosredujNiPraviStatusTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
            }
        });

        ModelAndView modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicPosreduj(10, httpSession);
        assertEquals("redirect:/ZahtevekZaPreklic/podrobno/10", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaPreklicPosredujNiNatisnjenoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
            }
        });

        ModelAndView modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicPosreduj(12, httpSession);
        assertEquals("izpisPodrobnoPreklic", modelAndView.getViewName());
        assertEquals("Prosim, da natisnite vlogo preden jo posredujete na SIGOV-CA.", modelAndView.getModel().get("error"));
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaPreklicPosredujOkTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        ModelAndView modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicPosreduj(13, httpSession);
        assertEquals("izpisPodrobnoPreklic", modelAndView.getViewName());
        ZahtevekZaPreklic zahtevek = (ZahtevekZaPreklic) modelAndView.getModel().get("zahtevekZaPreklic");
        assertEquals("07", zahtevek.getStatus().getSifra());
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaPreklicPotrdiNiPraviStatusTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
            }
        });
        ModelAndView modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicPotrdi(13, httpSession);
        assertEquals("redirect:/ZahtevekZaPreklic/podrobno/13", modelAndView.getViewName());
        context.assertIsSatisfied();

    }

    @Test
    public void zahtevekZaPreklicPotrdiOkTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicPotrdi(10, httpSession);
        assertEquals("izpisPodrobnoPreklic", modelAndView.getViewName());
        ZahtevekZaPreklic zahtevek = (ZahtevekZaPreklic) modelAndView.getModel().get("zahtevekZaPreklic");
        assertEquals("02", zahtevek.getStatus().getSifra());
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaPreklicBrisiNiPraviStatusTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
            }
        });
        String returnValue = zahtevekZaPreklicController.zahtevekZaPreklicBrisi(13, httpSession);
        assertEquals("redirect:/IzpisPodatkovPreklic/1", returnValue);
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaPreklicBrisiOkTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        String returnValue = zahtevekZaPreklicController.zahtevekZaPreklicBrisi(10, httpSession);
        assertEquals("redirect:/IzpisPodatkovPreklic/1", returnValue);
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaPreklicOdkleniUserTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicOdkleni(10, httpSession);
        assertEquals("redirect:/ZahtevekZaPreklic/podrobno/10", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaPreklicOdkleniAdminNiPraviStatusTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        ModelAndView modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicOdkleni(10, httpSession);
        assertEquals("redirect:/ZahtevekZaPreklic/podrobno/10", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaPreklicNatisniTest() {
    }

    @Test
    public void zahtevekZaPreklicZgodovinaTest() {
        ModelAndView modelAndView = zahtevekZaPreklicController.zahtevekZaPreklicZgodovina(10);
        assertEquals("pregledLogZaPreklic", modelAndView.getViewName());
        List<StatusLogPreklic> statusLog = (List<StatusLogPreklic>) modelAndView.getModel().get("statusLog");
        ZahtevekZaPreklic zahtevekZaPreklic = (ZahtevekZaPreklic) modelAndView.getModel().get("zahtevek");
        Assert.assertNotNull(statusLog);
        Assert.assertNotNull(zahtevekZaPreklic);
    }

    /**/
    private void checkZahtevekZaPreklic(ZahtevekZaPreklic zahtevek, String imeOrganizacije, String eNaslov, String imetnikIme, String imetnikPriimek, Long imetnikID, String serijskaStevilka) {
        assertEquals(imeOrganizacije, zahtevek.getImeOrganizacije());
        assertEquals(eNaslov, zahtevek.getImetnikEnaslov());
        assertEquals(imetnikIme, zahtevek.getImetnikIme());
        assertEquals(imetnikPriimek, zahtevek.getImetnikPriimek());
        assertEquals(imetnikID, zahtevek.getImetnikID());
        assertEquals(serijskaStevilka, zahtevek.getSerijskaStevilka());
    }
}
