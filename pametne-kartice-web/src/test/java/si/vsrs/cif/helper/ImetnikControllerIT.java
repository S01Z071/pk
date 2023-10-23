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
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.controllers.ImetnikController;
import si.vsrs.cif.managers.ImetnikRepoManager;
import si.vsrs.cif.managers.StatusRepoManager;
import si.vsrs.cif.mod.AdminIskanjeStatusImetnik;
import si.vsrs.cif.mod.Certifikat;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.Kartica;
import si.vsrs.cif.mod.KontaktnaOseba;
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
public class ImetnikControllerIT extends Assert {

    @Autowired
    private ImetnikController imetnikController;
    @Autowired
    private ImetnikRepoManager imetnikRepoManager;
    @Autowired
    private MetodeHelper metodeHelper;
    @Autowired
    private NastavitveHelper nastavitveHelper;
    @Autowired
    private StatusRepoManager statusRepoManager;
    private Uporabnik uporabnikUser = new Uporabnik();
    private Uporabnik uporabnikAdmin = new Uporabnik();

    public ImetnikController getImetnikController() {
        return imetnikController;
    }

    public void setImetnikController(ImetnikController imetnikController) {
        this.imetnikController = imetnikController;
    }

    public ImetnikRepoManager getImetnikRepoManager() {
        return imetnikRepoManager;
    }

    public void setImetnikRepoManager(ImetnikRepoManager imetnikRepoManager) {
        this.imetnikRepoManager = imetnikRepoManager;
    }

    public MetodeHelper getMetodeHelper() {
        return metodeHelper;
    }

    public void setMetodeHelper(MetodeHelper metodeHelper) {
        this.metodeHelper = metodeHelper;
    }

    public ImetnikControllerIT() {
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
    public void pregledImetnikovTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = imetnikController.pregledImetnikov(httpSession, 1, null);
        assertEquals("pregledImetnikov", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");

        assertEquals(4, imetniki.size());
        checkImetnik(imetniki.get(0), "Andrej", "03");
        checkImetnik(imetniki.get(1), "Marko", "03");
        checkImetnik(imetniki.get(2), "Andrej", "01");
        checkImetnik(imetniki.get(3), "Miha", "01");

        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1.0, modelAndView.getModel().get("izpisCount"));
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));

        context.assertIsSatisfied();
    }

    @Test
    public void pregledImetnikovIskanjePraznaUserTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = imetnikController.pregledImetnikov(httpSession, 1, "");
        assertEquals("pregledImetnikov", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");
        assertEquals(4, imetniki.size());
        assertEquals(1, modelAndView.getModel().get("pageNum"));

        context.assertIsSatisfied();
    }

    @Test
    public void pregledImetnikovIskanjePraznaAdminTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        ModelAndView modelAndView = imetnikController.pregledImetnikov(httpSession, 1, "");
        assertEquals("pregledImetnikov", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");
        assertEquals(4, imetniki.size());
        assertEquals(1, modelAndView.getModel().get("pageNum"));

        context.assertIsSatisfied();
    }

    @Test
    public void pregledImetnikovIskanjeEmsoUserTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = imetnikController.pregledImetnikov(httpSession, 1, "1234567890123");
        assertEquals("pregledImetnikov", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");
        assertEquals(1, imetniki.size());
        checkImetnik(imetniki.get(0), "Andrej", "01");
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        context.assertIsSatisfied();
    }

    @Test
    public void pregledImetnikovIskanjeEmsoAdminTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final AdminIskanjeStatusImetnik status = new AdminIskanjeStatusImetnik();
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("AdminIskanjeStatusImetnik");
                will(returnValue(status));
                oneOf(httpSession).getAttribute("AdminIskanjeStatusImetnik");
                will(returnValue(status));
            }
        });
        ModelAndView modelAndView = imetnikController.pregledImetnikovAdmin(httpSession, status, 1, "1234567890123");
        assertEquals("pregledImetnikov", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");
        assertEquals(2, imetniki.size());
        checkImetnik(imetniki.get(0), "Andrej", "01");
        checkImetnik(imetniki.get(1), "Marko", "04");
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        context.assertIsSatisfied();
    }

    @Test
    public void pregledImetnikovIskanjeCrtnaUserTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = imetnikController.pregledImetnikov(httpSession, 1, "IS013020930720831");
        assertEquals("pregledImetnikov", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");
        assertEquals(1, imetniki.size());
        checkImetnik(imetniki.get(0), "Andrej", "01");
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        context.assertIsSatisfied();
    }

    @Test
    public void pregledImetnikovIskanjeCrtnaAdminTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final AdminIskanjeStatusImetnik status = new AdminIskanjeStatusImetnik();
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("AdminIskanjeStatusImetnik");
                will(returnValue(status));
                oneOf(httpSession).getAttribute("AdminIskanjeStatusImetnik");
                will(returnValue(status));

            }
        });
        ModelAndView modelAndView = imetnikController.pregledImetnikovAdmin(httpSession, status, 1, "IS014043330170831");
        assertEquals("pregledImetnikov", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");
        assertEquals(1, imetniki.size());
        checkImetnik(imetniki.get(0), "Marko", "04");
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        context.assertIsSatisfied();
    }

    @Test
    public void pregledImetnikovIskanjeDavcnaUserTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = imetnikController.pregledImetnikov(httpSession, 1, "44444444");
        assertEquals("pregledImetnikov", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");
        assertEquals(1, imetniki.size());
        checkImetnik(imetniki.get(0), "Andrej", "01");
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        context.assertIsSatisfied();
    }

    @Test
    public void pregledImetnikovIskanjeDavcnaAdminTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        ModelAndView modelAndView = imetnikController.pregledImetnikov(httpSession, 1, "44444444");
        assertEquals("pregledImetnikov", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");
        assertEquals(1, imetniki.size());
        checkImetnik(imetniki.get(0), "Andrej", "01");
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        context.assertIsSatisfied();
    }

    @Test
    public void pregledImetnikovIskanjeENaslovUserTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = imetnikController.pregledImetnikov(httpSession, 1, "marko.oman@sodisce.si");
        assertEquals("pregledImetnikov", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");
        assertEquals(0, imetniki.size());
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        context.assertIsSatisfied();
    }

    @Test
    public void pregledImetnikovIskanjeENaslovAdminTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final AdminIskanjeStatusImetnik status = new AdminIskanjeStatusImetnik();
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("AdminIskanjeStatusImetnik");
                will(returnValue(status));
                oneOf(httpSession).getAttribute("AdminIskanjeStatusImetnik");
                will(returnValue(status));
            }
        });
        ModelAndView modelAndView = imetnikController.pregledImetnikovAdmin(httpSession, status, 1, "marko.oman@sodisce.si");//pregledImetnikovIskanje(httpSession, "marko.oman@sodisce.si");
        assertEquals("pregledImetnikov", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");
        assertEquals(2, imetniki.size());
        checkImetnik(imetniki.get(0), "Marko", "04");
        checkImetnik(imetniki.get(1), "Marko", "04");
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        context.assertIsSatisfied();
    }

    @Test
    public void izpisPodatkovPodrobnoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        assertEquals("01", imetnikRepoManager.getImetnik(10).getStatus().getSifraSIm());
        ModelAndView modelAndView = imetnikController.izpisPodatkovPodrobno(httpSession, 10);
        assertEquals("pregledImetnikovPodrobno", modelAndView.getViewName());
        Imetnik imetnik = (Imetnik) modelAndView.getModel().get("imetnik");
        checkImetnik(imetnik, "Miha", "01");
        assertNotNull(modelAndView.getModel().get("opomba"));
        assertNotNull(modelAndView.getModel().get("seznamKarticSigovca"));
        assertNotNull(modelAndView.getModel().get("seznamCitalcevSigovca"));
        assertEquals(Long.valueOf(10), modelAndView.getModel().get("zahtevekID"));
        List<Certifikat> certifikati = (List<Certifikat>) modelAndView.getModel().get("certifikati");
        List<Kartica> kartice = (List<Kartica>) modelAndView.getModel().get("kartice");
        assertEquals(1, certifikati.size());
        assertEquals("1234512890321", certifikati.get(0).getSerijskaStevilka());
        assertEquals(2, kartice.size());
        assertEquals("570223512088E30B3B17EEEE", kartice.get(0).getSerijskaStevilkaKartice());
        assertEquals("570113512088EAAA3B17EEEE", kartice.get(1).getSerijskaStevilkaKartice());
        context.assertIsSatisfied();
    }

    @Test
    public void izpisPodatkovPodrobnoUserNeDovoljenoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        assertEquals("04", imetnikRepoManager.getImetnik(13).getStatus().getSifraSIm());
        ModelAndView modelAndView = imetnikController.izpisPodatkovPodrobno(httpSession, 13);
        assertEquals("redirect:/index", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void izpisPodatkovPodrobnoAdminDovoljenoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        assertEquals("04", imetnikRepoManager.getImetnik(13).getStatus().getSifraSIm());
        ModelAndView modelAndView = imetnikController.izpisPodatkovPodrobno(httpSession, 13);
        assertEquals("pregledImetnikovPodrobno", modelAndView.getViewName());
        Imetnik imetnik = (Imetnik) modelAndView.getModel().get("imetnik");
        checkImetnik(imetnik, "Marko", "04");
        assertNotNull(modelAndView.getModel().get("opomba"));
        assertNotNull(modelAndView.getModel().get("seznamKarticSigovca"));
        assertNotNull(modelAndView.getModel().get("seznamCitalcevSigovca"));
        assertEquals(Long.valueOf(10), modelAndView.getModel().get("zahtevekID"));
        List<Certifikat> certifikati = (List<Certifikat>) modelAndView.getModel().get("certifikati");
        List<Kartica> kartice = (List<Kartica>) modelAndView.getModel().get("kartice");
        assertEquals(0, certifikati.size());
        assertEquals(0, kartice.size());
        context.assertIsSatisfied();
    }

    @Test
    public void adminConfirmIskanjeTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final AdminIskanjeStatusImetnik status = new AdminIskanjeStatusImetnik();
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("AdminIskanjeStatusImetnik", status);
                oneOf(httpSession).getAttribute("AdminIskanjeStatusImetnik");
                will(returnValue(status));
                oneOf(httpSession).getAttribute("AdminIskanjeStatusImetnik");
                will(returnValue(status));
            }
        });
        ModelAndView modelAndView = imetnikController.adminConfirmIskanje(httpSession, status, null);
        assertEquals("pregledImetnikov", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void pregledImetnikovAdminTestNotNull() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final AdminIskanjeStatusImetnik status = new AdminIskanjeStatusImetnik();
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("AdminIskanjeStatusImetnik");
                will(returnValue(status));
                oneOf(httpSession).getAttribute("AdminIskanjeStatusImetnik");
                will(returnValue(status));
            }
        });
        ModelAndView modelAndView = imetnikController.pregledImetnikovAdmin(httpSession, status, 1, null);
        assertEquals("pregledImetnikov", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");

        assertEquals(8, imetniki.size());
        checkImetnik(imetniki.get(0), "Miha", "01");
        checkImetnik(imetniki.get(1), "Andrej", "01");
        checkImetnik(imetniki.get(2), "Marko", "04");
        checkImetnik(imetniki.get(3), "Marko", "04");
        checkImetnik(imetniki.get(4), "Miha", "04");
        checkImetnik(imetniki.get(5), "Marko", "03");
        checkImetnik(imetniki.get(6), "Andrej", "03");
        checkImetnik(imetniki.get(7), "Miha2", "04");
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1.0, modelAndView.getModel().get("izpisCount"));
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));
        context.assertIsSatisfied();
    }

    @Test
    public void pregledImetnikovAdminTestNull() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final AdminIskanjeStatusImetnik status = new AdminIskanjeStatusImetnik();
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("AdminIskanjeStatusImetnik");
                will(returnValue(null));
                oneOf(httpSession).setAttribute("AdminIskanjeStatusImetnik", status);
            }
        });
        ModelAndView modelAndView = imetnikController.pregledImetnikovAdmin(httpSession, status, 1, null);
        assertEquals("pregledImetnikov", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");

        assertEquals(8, imetniki.size());
        checkImetnik(imetniki.get(0), "Miha", "01");
        checkImetnik(imetniki.get(1), "Andrej", "01");
        checkImetnik(imetniki.get(2), "Marko", "04");
        checkImetnik(imetniki.get(3), "Marko", "04");
        checkImetnik(imetniki.get(4), "Miha", "04");
        checkImetnik(imetniki.get(5), "Marko", "03");
        checkImetnik(imetniki.get(6), "Andrej", "03");
        checkImetnik(imetniki.get(7), "Miha2", "04");
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1.0, modelAndView.getModel().get("izpisCount"));
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));
        context.assertIsSatisfied();
    }

    @Test
    public void pregledImetnikovAdminTestStatusBrezIzbrisanih() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final AdminIskanjeStatusImetnik status = new AdminIskanjeStatusImetnik();
        status.setIzbrisano(false);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("AdminIskanjeStatusImetnik");
                will(returnValue(status));
                oneOf(httpSession).getAttribute("AdminIskanjeStatusImetnik");
                will(returnValue(status));
            }
        });
        ModelAndView modelAndView = imetnikController.pregledImetnikovAdmin(httpSession, status, 1, null);
        assertEquals("pregledImetnikov", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");

        assertEquals(4, imetniki.size());
        checkImetnik(imetniki.get(0), "Miha", "01");
        checkImetnik(imetniki.get(1), "Andrej", "01");
        checkImetnik(imetniki.get(2), "Marko", "03");
        checkImetnik(imetniki.get(3), "Andrej", "03");

        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1.0, modelAndView.getModel().get("izpisCount"));
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));
        context.assertIsSatisfied();
    }

    @Test
    public void addImetnikTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });

        ModelAndView modelAndView = imetnikController.addImetnik(httpSession, 10);
        assertEquals("dodajImetnik", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("imetnik"));
        assertEquals(10, modelAndView.getModel().get("idZ"));
        context.assertIsSatisfied();
    }

    @Test
    public void editImetnikTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });

        ModelAndView modelAndView = imetnikController.editImetnik(httpSession, 10, 10);
        assertEquals("dodajImetnik", modelAndView.getViewName());
        Imetnik imetnik = (Imetnik) modelAndView.getModel().get("imetnik");
        assertNotNull(imetnik);
        checkImetnik(imetnik, "Miha", "01");
        assertEquals(10, modelAndView.getModel().get("idZ"));
        context.assertIsSatisfied();
    }

    @Test
    public void addImetnikProcessUspesnoDodajTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        Imetnik imetnik = new Imetnik("Ime", "Priimek", "tomaz.tomsic@sodisce.si", "11111111", null, "geslo");
        final BindingResult bindingResult = context.mock(BindingResult.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));

                allowing(bindingResult).hasErrors();
                will(returnValue(false));

                oneOf(httpSession).setAttribute("info", "Imetnik shranjen");
            }
        });
        ModelAndView modelAndView = imetnikController.addImetnikProcess(imetnik, bindingResult, httpSession, 10);
        assertEquals("redirect:/Vloga/uredi/10", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void addImetnikProcessValidationErrorTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        Imetnik imetnik = new Imetnik("Ime", "Priimek", "ime.priimek@sodisce.si", "11111111", null, "geslo");
        final BindingResult bindingResult = context.mock(BindingResult.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));

                allowing(bindingResult).hasErrors();
                will(returnValue(true));
            }
        });
        ModelAndView modelAndView = imetnikController.addImetnikProcess(imetnik, bindingResult, httpSession, 10);
        assertEquals("dodajImetnik", modelAndView.getViewName());
        Imetnik imetnikRet = (Imetnik) modelAndView.getModel().get("imetnik");
        assertEquals(imetnik, imetnikRet);
        assertEquals(10, modelAndView.getModel().get("idZ"));
        context.assertIsSatisfied();
    }

    /* @Test
     public void addImetnikProcessMailNiVLdapTest() {
     Mockery context = new Mockery();
     context.setImposteriser(ClassImposteriser.INSTANCE);
     final HttpSession httpSession = context.mock(HttpSession.class);
     Imetnik imetnik = new Imetnik("Ime", "Priimek", "ime.priimek@sodisce.si", "11111111", null, "geslo");
     final BindingResult bindingResult = context.mock(BindingResult.class);    
     context.checking(new Expectations() {
     {
     oneOf(httpSession).getAttribute("uporabnik");
     will(returnValue(uporabnikUser));                
                
     allowing(bindingResult).hasErrors();
     will(returnValue(true));      

     }
     });        
     ModelAndView modelAndView = imetnikController.addImetnikProcess(imetnik, bindingResult, httpSession, 10);
     Imetnik imetnikRet = (Imetnik) modelAndView.getModel().get("imetnik");
     assertEquals("dodajImetnik", modelAndView.getViewName());
     assertEquals(10, modelAndView.getModel().get("idZ")); 
     assertEquals(imetnik, imetnikRet);    
     context.assertIsSatisfied();       
     }
     */
    /* bindingResult.rejectValue  @Test
     public void addImetnikProcessMailNiVLdapTest() {
         
     }
     */
    /*----*/
    @Test
    public void deleteImetnikTest() {
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
        ModelAndView modelAndView = imetnikController.deleteImetnik(10, httpSession, 12);
        assertEquals("dodajZahtevek", modelAndView.getViewName());
        Zahtevek zahtevek = (Zahtevek) modelAndView.getModel().get("zahtevek");
        checkZahtevek(zahtevek, "Vrhovno sodisce RS", 1, "01");
        checkImetnik(zahtevek.getImetniki().get(0), "Miha", "01");
        context.assertIsSatisfied();
    }

    @Test
    public void printImetnikeTest() {
    }

    @Test
    public void printImetnikTest() {
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
