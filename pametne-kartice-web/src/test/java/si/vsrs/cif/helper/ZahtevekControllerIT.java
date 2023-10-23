/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.helper;

import java.util.List;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
import org.jmock.Mockery;
import org.jmock.lib.concurrent.Synchroniser;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.controllers.ZahtevekController;
import si.vsrs.cif.managers.ZahtevekRepoManager;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.KontaktnaOseba;
import si.vsrs.cif.mod.Opomba;
import si.vsrs.cif.mod.Predstojnik;
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
    "classpath:/spring/dao-context_it.xml",
    "classpath:/spring/dao-context.xml",
    "classpath:/spring/app-context.xml",
    //"classpath:/spring/dao-context_test.xml",
    "classpath:/spring/app-context_test.xml"})
@Transactional
public class ZahtevekControllerIT extends Assert {

    @Autowired
    private ZahtevekController zahtevekController;
    @Autowired
    private ZahtevekRepoManager zahtevekRepoManager;
    @Autowired
    private NastavitveHelper nastavitveHelper;
    @Autowired
    private MetodeHelper metodeHelper;
    private Uporabnik uporabnikUser = new Uporabnik();
    private Uporabnik uporabnikAdmin = new Uporabnik();

    public ZahtevekController getZahtevekController() {
        return zahtevekController;
    }

    public void setZahtevekController(ZahtevekController zahtevekController) {
        this.zahtevekController = zahtevekController;
    }

    public ZahtevekRepoManager getZahtevekRepoManager() {
        return zahtevekRepoManager;
    }

    public void setZahtevekRepoManager(ZahtevekRepoManager zahtevekRepoManager) {
        this.zahtevekRepoManager = zahtevekRepoManager;
    }

    public MetodeHelper getMetodeHelper() {
        return metodeHelper;
    }

    public void setMetodeHelper(MetodeHelper metodeHelper) {
        this.metodeHelper = metodeHelper;
    }

    public ZahtevekControllerIT() {
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
    public void izpisPodatkovTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = zahtevekController.izpisPodatkov(httpSession, 1, 1, 1);
        assertEquals("izpisPodatkov", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("zahtevki"));
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1.0, modelAndView.getModel().get("izpisCount"));
        assertNotNull(modelAndView.getModel().get("adminIskanjeStatus"));
        assertEquals(1, modelAndView.getModel().get("tabStatus"));
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));
        assertNotNull(modelAndView.getModel().get("zahtevekZaKodo"));
        assertEquals(1, modelAndView.getModel().get("pageNumKoda"));
        assertEquals(1.0, modelAndView.getModel().get("izpisCountKoda"));
        context.assertIsSatisfied();
    }

    @Test
    public void izpisPodatkovPodrobnoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final HttpServletRequest request = context.mock(HttpServletRequest.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
                oneOf(httpSession).setAttribute("info", "");

                oneOf(request).getHeader("referer");
                will(returnValue(""));
            }
        });
        ModelAndView modelAndView = zahtevekController.izpisPodatkovPodrobno(httpSession, 10, request);
        assertEquals("izpisPodrobno", modelAndView.getViewName());
        Zahtevek zahtevek = (Zahtevek) modelAndView.getModel().get("zahtevek");
        List<Opomba> opombe = (List<Opomba>) modelAndView.getModel().get("opombe");
        List<Imetnik> imetniki = zahtevek.getImetniki();
        checkZahtevek(zahtevek, "Vrhovno sodisce RS", 2, "01");
        assertNotNull(opombe);
        assertNotNull(modelAndView.getModel().get("opomba"));
        assertEquals(2, opombe.size());
        checkImetnik(imetniki.get(0), "Miha", "01");
        checkImetnik(imetniki.get(1), "Andrej", "01");
        context.assertIsSatisfied();
    }

    @Test
    public void izpisPodatkovPodrobnoAdminTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final HttpServletRequest request = context.mock(HttpServletRequest.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));

                oneOf(httpSession).setAttribute("info", "");

                oneOf(request).getHeader("referer");
                will(returnValue(""));
            }
        });

        ModelAndView modelAndView = zahtevekController.izpisPodatkovPodrobno(httpSession, 15, request);
        assertEquals("izpisPodrobno", modelAndView.getViewName());
        Zahtevek zahtevek = (Zahtevek) modelAndView.getModel().get("zahtevek");
        List<Opomba> opombe = (List<Opomba>) modelAndView.getModel().get("opombe");
        List<Imetnik> imetniki = zahtevek.getImetniki();
        checkZahtevek(zahtevek, "Vrhovno sodisce RS Izbrisan", 2, "04");
        assertNotNull(opombe);
        assertNotNull(modelAndView.getModel().get("opomba"));
        checkImetnik(imetniki.get(0), "Marko", "04");
        checkImetnik(imetniki.get(1), "Miha", "04");
        context.assertIsSatisfied();
    }

    @Test
    public void addVlogaTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("info", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = zahtevekController.addVloga(httpSession);
        assertEquals("dodajZahtevek", modelAndView.getViewName());
        Zahtevek zahtevek = (Zahtevek) modelAndView.getModel().get("zahtevek");
        assertNotNull(zahtevek);
        assertEquals("Sodnik", zahtevek.getKontaktnaOseba().getFunkcijaK());
        assertEquals("Sodnik II", zahtevek.getPredstojnik().getFunkcijaP());
        context.assertIsSatisfied();
    }

    @Test
    public void editVlogaTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = zahtevekController.editVloga(httpSession, 10);
        assertEquals("dodajZahtevek", modelAndView.getViewName());
        Zahtevek zahtevek = (Zahtevek) modelAndView.getModel().get("zahtevek");
        checkZahtevek(zahtevek, "Vrhovno sodisce RS", 2, "01");
        context.assertIsSatisfied();
    }

    @Test
    public void addingVlogaUspesnoTest() {
        Mockery context = new Mockery() {
            {
                setThreadingPolicy(new Synchroniser());
            }
        };
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final BindingResult bindingResult = context.mock(BindingResult.class);
        final Zahtevek zahtevek = setZahtevek();

        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("info", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
                allowing(bindingResult).hasErrors();
                will(returnValue(false));
                oneOf(httpSession).setAttribute("info", "Zahtevek je bil shranjen.");
            }
        });
        ModelAndView modelAndView = zahtevekController.addingVloga(zahtevek, bindingResult, httpSession);
        assertEquals("redirect:/Vloga/uredi/1", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void addingVlogaNeUspesnoTest() {
        Mockery context = new Mockery() {
            {
                setThreadingPolicy(new Synchroniser());
            }
        };
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final BindingResult bindingResult = context.mock(BindingResult.class);
        final Zahtevek zahtevek = setZahtevek();

        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("info", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
                allowing(bindingResult).hasErrors();
                will(returnValue(true));
            }
        });
        ModelAndView modelAndView = zahtevekController.addingVloga(zahtevek, bindingResult, httpSession);
        assertEquals("dodajZahtevek", modelAndView.getViewName());
        Zahtevek zahtevekRet = (Zahtevek) modelAndView.getModel().get("zahtevek");
        assertEquals(zahtevek, zahtevekRet);
        context.assertIsSatisfied();
    }

    @Test
    public void deleteVlogaTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        String vrnjeno = zahtevekController.deleteVloga(httpSession, 12);
        assertEquals("redirect:/izpisPodatkov/1/1/1", vrnjeno);
        context.assertIsSatisfied();
    }

    @Test
    public void deleteVlogaAdminTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));

            }
        });
        String vrnjeno = zahtevekController.deleteVloga(httpSession, 13);
        assertEquals("redirect:/izpisPodatkovAdmin/1/1/1", vrnjeno);
    }

    @Test
    public void potrdiVlogoBrezImetnikovTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
                oneOf(httpSession).setAttribute("errorMessage", "Vnesite vsaj enega imetnika.");
            }
        });
        ModelAndView modelAndView = zahtevekController.potrdiVlogo(httpSession, 14);
        assertEquals("redirect:/izpisPodrobno/14", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void potrdiVlogoZImetnikiTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);

        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = zahtevekController.potrdiVlogo(httpSession, 10);
        assertEquals("redirect:/izpisPodrobno/10", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    //@Test
    public void printZahtevekTest() {
    }

    @Test
    public void odkleniZahtevekTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = zahtevekController.odkleniZahtevek(httpSession, 14);
        assertEquals("redirect:/izpisPodrobno/14", modelAndView.getViewName());
        context.assertIsSatisfied();

    }

    @Test
    public void posredujZahtevekNiNatisnjenoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
                oneOf(httpSession).setAttribute("errorMessage", "Prosim, da natisnite vse bodoče imetnike preden vlogo posredujete na CIF.");
            }
        });
        potrdiVlogoZImetnikiTest();
        ModelAndView modelAndView = zahtevekController.posredujZahtevek(httpSession, 10);
        assertEquals("redirect:/izpisPodrobno/10", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void posredujZahtevekSamoImetnikiNatisnjeniTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(10);
        zahtevek.setImetniki(metodeHelper.spremeniImetnikiNatisnjeno(zahtevek.getImetniki(), true));
        zahtevekRepoManager.updateZahtevek(zahtevek);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
                oneOf(httpSession).setAttribute("errorMessage", "Prosim, da natisnite vlogo preden jo posredujete na CIF.");

            }
        });
        potrdiVlogoZImetnikiTest();
        ModelAndView modelAndView = zahtevekController.posredujZahtevek(httpSession, 10);
        assertEquals("redirect:/izpisPodrobno/10", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void posredujZahtevekVseNatisnjenoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(10);
        zahtevek.setNatisnjeno(true);
        zahtevek.setImetniki(metodeHelper.spremeniImetnikiNatisnjeno(zahtevek.getImetniki(), true));
        zahtevekRepoManager.updateZahtevek(zahtevek);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = zahtevekController.posredujZahtevek(httpSession, 10);
        assertEquals("redirect:/izpisPodrobno/10", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    public void checkZahtevek(Zahtevek zahtevek, String imeOrganizacije, int stImetnikov, String statusSifra) {
        assertNotNull(zahtevek);
        assertEquals(imeOrganizacije, zahtevek.getImeOrganizacije());
        assertEquals(stImetnikov, zahtevek.getImetniki().size());
        assertEquals(statusSifra, zahtevek.getStatus().getSifra());
    }

    public void checkImetnik(Imetnik imetnik, String ime, String stausSifra) {
        assertNotNull(imetnik);
        assertEquals(ime, imetnik.getIme());
        assertEquals(stausSifra, imetnik.getStatus().getSifraSIm());
    }

    public Zahtevek setZahtevek() {
        Predstojnik predstojnik = new Predstojnik("Franc", "Novak", "franc.novak@sodisce.si", "Sodnik");
        KontaktnaOseba kontaktnaOseba = new KontaktnaOseba("Janez", "Potočnik", "janez.potocnik@sodisce.si", "Programer aplikacij V", "01546345");
        return new Zahtevek("S23", "Okrajno sodišče v Ljubljani", "Ljubljana", "Miklošičeva", "10", "1000", "Ljubljana", kontaktnaOseba, predstojnik);
    }
}
