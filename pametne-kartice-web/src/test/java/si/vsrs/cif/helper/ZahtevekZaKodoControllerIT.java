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
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.controllers.ZahtevekZaKodoController;
import si.vsrs.cif.managers.ZahtevekZaKodoRepoManager;
import si.vsrs.cif.mod.Kartica;
import si.vsrs.cif.mod.StatusLogZahtevekZaKodo;
import si.vsrs.cif.mod.ZahtevekZaKodo;
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
public class ZahtevekZaKodoControllerIT {

    @Autowired
    ZahtevekZaKodoController zahtevekZaKodoController;
    @Autowired
    ZahtevekZaKodoRepoManager zahtevekZaKodoRepoManager;
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
    public void zahtevekZaKodoTest() {
        ModelAndView modelAndView = zahtevekZaKodoController.zahtevekZaKodo();
        assertEquals("dodajZahtevekZaKodo", modelAndView.getViewName());
    }

    @Test
    public void zahtevekZaKodoIskanjeAdminTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        ModelAndView modelAndView = zahtevekZaKodoController.zahtevekZaKodoIskanje("078", httpSession);
        assertEquals("dodajZahtevekZaKodo", modelAndView.getViewName());
        List<Kartica> kartice = (List<Kartica>) modelAndView.getModel().get("kartice");
        assertEquals(2, kartice.size());
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaKodoIskanjeUserTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser1));
            }
        });
        ModelAndView modelAndView = zahtevekZaKodoController.zahtevekZaKodoIskanje("078", httpSession);
        assertEquals("dodajZahtevekZaKodo", modelAndView.getViewName());
        assertEquals("Ni zadetkov.", modelAndView.getModel().get("error"));
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaKodoIskanjeNeuspesnoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        ModelAndView modelAndView = zahtevekZaKodoController.zahtevekZaKodoIskanje("255555", httpSession);
        assertEquals("dodajZahtevekZaKodo", modelAndView.getViewName());
        assertEquals("Ni zadetkov.", modelAndView.getModel().get("error"));
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaKodoIskanjeEnRezultatTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        ModelAndView modelAndView = zahtevekZaKodoController.zahtevekZaKodoIskanje("078333", httpSession);
        assertEquals("redirect:/ZahtevekZaKodo/dodaj/10", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaKodoDodajTest() {
        ModelAndView modelAndView = zahtevekZaKodoController.zahtevekZaKodoDodaj(10);
        assertEquals("dodajZahtevekZaKodo", modelAndView.getViewName());
        ZahtevekZaKodo zahtevek = (ZahtevekZaKodo) modelAndView.getModel().get("zahtevekZaKodo");
        checkZahtevekZaKodo(zahtevek, "Vrhovno sodisce RS", "miha.koncilja@sodisce.si", "Miha", "Koncilja", Long.valueOf("10"), "570223512088E30B3B17EEEE");
    }

    @Test
    public void zahtevekZaKodoDodajProcessTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ZahtevekZaKodo zahtevekZaKodo = (ZahtevekZaKodo) zahtevekZaKodoController.zahtevekZaKodoDodaj(10).getModel().get("zahtevekZaKodo");
        ModelAndView modelAndView = zahtevekZaKodoController.zahtevekZaKodoDodajProcess(zahtevekZaKodo, httpSession);
        assertEquals("redirect:/ZahtevekZaKodo/podrobno/1", modelAndView.getViewName()); //Se doda nov zahtevek (ID = 1)
        /*  ZahtevekZaKodo zahtevek = (ZahtevekZaKodo) modelAndView.getModel().get("zahtevekZaKodo");
         checkZahtevekZaKodo(zahtevek, "Vrhovno sodisce RS", "miha.koncilja@sodisce.si", "Miha", "Koncilja", Long.valueOf("10"), "570223512088E30B3B17EEEE");
         assertEquals("Zahtevek shranjen. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov.", modelAndView.getModel().get("info"));
         */
        ModelAndView modelAndView1 = zahtevekZaKodoController.zahtevekZaKodoDodajProcess(zahtevekZaKodo, httpSession);
        assertEquals("dodajZahtevekZaKodo", modelAndView1.getViewName());
        assertEquals("Zahtevek za to oznako kartice je že v pripravi. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov.", modelAndView1.getModel().get("info"));

        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaKodoPodrobnoTest() {
        ModelAndView modelAndView = zahtevekZaKodoController.zahtevekZaKodoPodrobno(10);
        assertEquals("izpisPodrobnoKoda", modelAndView.getViewName());
        ZahtevekZaKodo zahtevek = (ZahtevekZaKodo) modelAndView.getModel().get("zahtevekZaKodo");
        checkZahtevekZaKodo(zahtevek, "Vrhovno sodisce RS", "marko.oman@sodisce.si", "Marko", "Oman", Long.valueOf("13"), "570117512088E30B3B17FFFF");
    }

    @Test
    public void zahtevekZaKodoPosredujNiNatisnjenoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
            }
        });

        ModelAndView modelAndView = zahtevekZaKodoController.zahtevekZaKodoPosreduj(10, httpSession);
        assertEquals("izpisPodrobnoKoda", modelAndView.getViewName());
        ZahtevekZaKodo zahtevek = (ZahtevekZaKodo) modelAndView.getModel().get("zahtevekZaKodo");
        checkZahtevekZaKodo(zahtevek, "Vrhovno sodisce RS", "marko.oman@sodisce.si", "Marko", "Oman", Long.valueOf("13"), "570117512088E30B3B17FFFF");
        assertEquals("Prosim, da natisnite vlogo preden jo posredujete na SIGOV-CA.", modelAndView.getModel().get("error"));
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaKodoPosredujJeNatisnjenoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });

        ModelAndView modelAndView = zahtevekZaKodoController.zahtevekZaKodoPosreduj(12, httpSession);
        assertEquals("izpisPodrobnoKoda", modelAndView.getViewName());
        ZahtevekZaKodo zahtevek = (ZahtevekZaKodo) modelAndView.getModel().get("zahtevekZaKodo");
        checkZahtevekZaKodo(zahtevek, "Vrhovno sodisce RS", "marko.oman1@sodisce.si", "Marko", "Oman", Long.valueOf("13"), "570117333088E30B3B17FFFF");
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaKodoPosredujNiVStatusuTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
            }
        });
        ModelAndView modelAndView = zahtevekZaKodoController.zahtevekZaKodoPosreduj(11, httpSession);
        assertEquals("redirect:/ZahtevekZaKodo/podrobno/11", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaKodoBrisiUporabnikTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        assertEquals("redirect:/izpisPodatkov/1/1/2", zahtevekZaKodoController.zahtevekZaKodoBrisi(12, httpSession));
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaKodoBrisiAdminTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        assertEquals("redirect:/izpisPodatkovAdmin/1/1/2", zahtevekZaKodoController.zahtevekZaKodoBrisi(12, httpSession));
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaKodoOdkleniUporabnikTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        assertEquals("redirect:/ZahtevekZaKodo/podrobno/12", zahtevekZaKodoController.zahtevekZaKodoOdkleni(12, httpSession).getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaKodoOdkleniAdminNeuspesnoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        assertEquals("redirect:/ZahtevekZaKodo/podrobno/10", zahtevekZaKodoController.zahtevekZaKodoOdkleni(10, httpSession).getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void zahtevekZaKodoOdkleniAdminUspesnoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        ModelAndView modelAndView = zahtevekZaKodoController.zahtevekZaKodoOdkleni(11, httpSession);
        assertEquals("izpisPodrobnoKoda", modelAndView.getViewName());
        ZahtevekZaKodo zahtevek = (ZahtevekZaKodo) modelAndView.getModel().get("zahtevekZaKodo");
        checkZahtevekZaKodo(zahtevek, "Vrhovno sodisce RS", "andrej.koncilja@sodisce.si", "Andrej", "Koncilja", Long.valueOf("12"), "570114512088E30H3B17EEEE");
        context.assertIsSatisfied();
    }

    //@Test
    public void zahtevekZaKodoNatisniTest() {
    }

    @Test
    public void zahtevekZaKodoZgodovinaTest() {
        ModelAndView modelAndView = zahtevekZaKodoController.zahtevekZaKodoZgodovina(12);
        assertEquals("pregledLogZaKodo", modelAndView.getViewName());
        ZahtevekZaKodo zahtevek = (ZahtevekZaKodo) modelAndView.getModel().get("zahtevek");
        List<StatusLogZahtevekZaKodo> statusLog = (List<StatusLogZahtevekZaKodo>) modelAndView.getModel().get("statusLog");
        checkZahtevekZaKodo(zahtevek, "Vrhovno sodisce RS", "marko.oman1@sodisce.si", "Marko", "Oman", Long.valueOf("13"), "570117333088E30B3B17FFFF");
        assertNotNull(statusLog);
    }

    /*-----------------------*/
    private void checkZahtevekZaKodo(ZahtevekZaKodo zahtevek, String imeOrganizacije, String eNaslov, String imetnikIme, String imetnikPriimek, Long imetnikID, String serijskaStevilka) {
        assertEquals(imeOrganizacije, zahtevek.getImeOrganizacije());
        assertEquals(eNaslov, zahtevek.getImetnikEnaslov());
        assertEquals(imetnikIme, zahtevek.getImetnikIme());
        assertEquals(imetnikPriimek, zahtevek.getImetnikPriimek());
        assertEquals(imetnikID, zahtevek.getImetnikID());
        assertEquals(serijskaStevilka, zahtevek.getSerijskaStevilka());
    }
}
