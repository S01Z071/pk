/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.helper;

import java.io.IOException;
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
import si.vsrs.cif.controllers.AdminController;
import si.vsrs.cif.managers.CertifikatRepoManager;
import si.vsrs.cif.managers.ImetnikRepoManager;
import si.vsrs.cif.managers.StatusCertifikatRepoManager;
import si.vsrs.cif.managers.StatusImetnikRepoManager;
import si.vsrs.cif.managers.StatusRepoManager;
import si.vsrs.cif.managers.ZahtevekRepoManager;
import si.vsrs.cif.mod.AdminIskanjeStatus;
import si.vsrs.cif.mod.Certifikat;
import si.vsrs.cif.mod.ImetnikPregledLogStatus;
import si.vsrs.cif.mod.JobLog;
import si.vsrs.cif.mod.Opomba;
import si.vsrs.cif.mod.SeznamCertifikatov;
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
    "classpath:/spring/app-context_test.xml"
})
@Transactional
public class AdminControllerIT extends Assert {

    @Autowired
    private AdminController adminController;
    @Autowired
    private NastavitveHelper nastavitveHelper;
    @Autowired
    ImetnikRepoManager imetnikRepoManager;
    @Autowired
    StatusImetnikRepoManager statusImetnikRepoManager;
    @Autowired
    ZahtevekRepoManager zahtevekRepoManager;
    @Autowired
    StatusRepoManager statusRepoManager;
    @Autowired
    CertifikatRepoManager certifikatRepoManager;
    @Autowired
    StatusCertifikatRepoManager statusCertifikatRepoManager;
    private Uporabnik uporabnikAdmin = new Uporabnik();

    public AdminControllerIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
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
    public void iskanjePageTest() {
        assertEquals("iskanje", adminController.iskanjePage().getViewName());
    }

    @Test
    public void adminConfirmIskanjeTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final AdminIskanjeStatus adminIskanjeStatus = new AdminIskanjeStatus();
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("AdminIskanjeStatus", adminIskanjeStatus);
            }
        });
        assertEquals("redirect:/izpisPodatkovAdmin/1/1/1", adminController.adminConfirmIskanje(httpSession, adminIskanjeStatus, 1, 1));
        context.assertIsSatisfied();
    }

    @Test
    public void izpisPodatkovAdminSessionNullTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final AdminIskanjeStatus adminIskanjeStatus = new AdminIskanjeStatus();
        adminIskanjeStatus.setvPripravi(true);
        adminIskanjeStatus.setIzbrisano(true);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("AdminIskanjeStatus");
                will(returnValue(null));

                oneOf(httpSession).setAttribute("AdminIskanjeStatus", adminIskanjeStatus);
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });

        ModelAndView modelAndView = adminController.izpisPodatkovAdmin(httpSession, adminIskanjeStatus, 1, 1, 1);
        assertEquals("izpisPodatkov", modelAndView.getViewName());
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1.0, modelAndView.getModel().get("izpisCount"));
        AdminIskanjeStatus adminIskanjeStatusRet = (AdminIskanjeStatus) modelAndView.getModel().get("adminIskanjeStatus");
        assertEquals(adminIskanjeStatus, adminIskanjeStatusRet);

        List<Zahtevek> zahtevki = (List<Zahtevek>) modelAndView.getModel().get("zahtevki");
        assertEquals(6, zahtevki.size());
        assertNotNull(modelAndView.getModel().get("zahtevekZaKodo"));
        assertEquals(1, modelAndView.getModel().get("pageNumKoda"));
        assertEquals(1.0, modelAndView.getModel().get("izpisCountKoda"));;
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));
        assertEquals(1, modelAndView.getModel().get("tabStatus"));

        context.assertIsSatisfied();
    }

    @Test
    public void izpisPodatkovAdminSessionNotNullTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final AdminIskanjeStatus adminIskanjeStatus = new AdminIskanjeStatus();
        adminIskanjeStatus.setvPripravi(false);
        adminIskanjeStatus.setIzbrisano(true);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("AdminIskanjeStatus");
                will(returnValue(adminIskanjeStatus));

                oneOf(httpSession).getAttribute("AdminIskanjeStatus");
                will(returnValue(adminIskanjeStatus));

                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });

        ModelAndView modelAndView = adminController.izpisPodatkovAdmin(httpSession, adminIskanjeStatus, 1, 1, 1);
        assertEquals("izpisPodatkov", modelAndView.getViewName());
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(1.0, modelAndView.getModel().get("izpisCount"));
        AdminIskanjeStatus adminIskanjeStatusRet = (AdminIskanjeStatus) modelAndView.getModel().get("adminIskanjeStatus");
        assertEquals(adminIskanjeStatus, adminIskanjeStatusRet);

        List<Zahtevek> zahtevki = (List<Zahtevek>) modelAndView.getModel().get("zahtevki");
        assertEquals(2, zahtevki.size());
        //0 imetnikov, ker naredi updateDeletedImetniki
        checkZahtevek(zahtevki.get(0), "Vrhovno sodisce RS Izbrisan", 0, "04");

        assertNotNull(modelAndView.getModel().get("zahtevekZaKodo"));
        assertEquals(1, modelAndView.getModel().get("pageNumKoda"));
        assertEquals(1.0, modelAndView.getModel().get("izpisCountKoda"));
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));
        assertEquals(1, modelAndView.getModel().get("tabStatus"));

        context.assertIsSatisfied();
    }

    @Test
    public void iskanjeCKNiZadetkovTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("nazajNaIskanje", "true");
                oneOf(httpSession).removeAttribute("nazajNaIskanje");
            }
        });
        ModelAndView modelAndView = adminController.iskanjeCK("", httpSession);
        assertEquals("iskanje", modelAndView.getViewName());
        assertEquals("Ni zadetkov.", modelAndView.getModel().get("error"));
        context.assertIsSatisfied();
    }

    @Test
    public void iskanjeCKNajdeImetnikaTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("nazajNaIskanje", "true");
            }
        });
        ModelAndView modelAndView = adminController.iskanjeCK("IS011246305862831", httpSession);
        assertEquals("redirect:/pregledImetnikovPodrobno/10", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void iskanjeCKNajdeZahtevekTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("nazajNaIskanje", "true");
            }
        });
        ModelAndView modelAndView = adminController.iskanjeCK("OS016978994862831", httpSession);
        assertEquals("redirect:/izpisPodrobno/10", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void potrdiImetnikTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
                oneOf(httpSession).setAttribute("scrollTop", 0);
            }
        });
        imetnikRepoManager.getImetnik(10).setStatus(statusImetnikRepoManager.getStatusImetnik("03"));
        assertEquals("redirect:/izpisPodrobno/10", adminController.potrdiImetnik(httpSession, 10, 10, 0));
        assertEquals("05", imetnikRepoManager.getImetnik(10).getStatus().getSifraSIm());
        context.assertIsSatisfied();
    }

    @Test
    public void potrdiSamoImetnikTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("nazajNaIskanje");
                will(returnValue(null));
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        imetnikRepoManager.getImetnik(12).setStatus(statusImetnikRepoManager.getStatusImetnik("03"));
        assertEquals("redirect:/pregledImetnikovAdmin/1", adminController.potrdiSamoImetnik(httpSession, 12));
        assertEquals("05", imetnikRepoManager.getImetnik(12).getStatus().getSifraSIm());
        context.assertIsSatisfied();
    }

    @Test
    public void potrdiSamoImetnikIzIskanjaTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("nazajNaIskanje");
                will(returnValue("true"));
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
                oneOf(httpSession).removeAttribute("nazajNaIskanje");
            }
        });
        imetnikRepoManager.getImetnik(12).setStatus(statusImetnikRepoManager.getStatusImetnik("03"));
        assertEquals("redirect:/iskanje", adminController.potrdiSamoImetnik(httpSession, 12));
        assertEquals("05", imetnikRepoManager.getImetnik(12).getStatus().getSifraSIm());
        context.assertIsSatisfied();
    }

    @Test
    public void zavrniImetnikTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        Opomba opomba = new Opomba("Zavrnitev imetnika", "Imetnik zavrnjen!");
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        assertEquals("redirect:/izpisPodrobno/16", adminController.zavrniImetnik(httpSession, opomba, Long.valueOf(16), 16));
        assertEquals("06", imetnikRepoManager.getImetnik(16).getStatus().getSifraSIm());
        context.assertIsSatisfied();
    }

    @Test
    public void zavrniSamoImetnikTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        Opomba opomba = new Opomba("Zavrnitev imetnika", "Imetnik zavrnjen!");
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });

        assertEquals("redirect:/pregledImetnikovAdmin/1", adminController.zavrniSamoImetnik(httpSession, opomba, 17));
        assertEquals("06", imetnikRepoManager.getImetnik(17).getStatus().getSifraSIm());
        context.assertIsSatisfied();
    }

    @Test
    public void zavrniZahtevekNeuspesnoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        Opomba opomba = new Opomba("Zavrnitev zahtevka", "Zahtevek zavrnjen!");
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).setAttribute("errorMessage", "Noben imetnik ni zavrnjen.");
            }
        });
        zahtevekRepoManager.getZahtevek(10).setStatus(statusRepoManager.getStatus("03"));
        assertEquals("redirect:/izpisPodrobno/10", adminController.zavrniZahtevek(httpSession, opomba, 10));
        context.assertIsSatisfied();
    }

    @Test
    public void zavrniZahtevekUspesnoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        Opomba opomba = new Opomba("Zavrnitev zahtevka", "Zahtevek zavrnjen!");
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        imetnikRepoManager.getImetnik(16).setStatus(statusImetnikRepoManager.getStatusImetnik("06"));
        imetnikRepoManager.getImetnik(17).setStatus(statusImetnikRepoManager.getStatusImetnik("06"));
        assertEquals("redirect:/izpisPodrobno/16", adminController.zavrniZahtevek(httpSession, opomba, 16));
        context.assertIsSatisfied();
    }

    @Test
    public void potrdiZahtevekNeuspesnoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).setAttribute("errorMessage", "Vsi imetniki morajo biti potrjeni.");
            }
        });
        assertEquals("redirect:/izpisPodrobno/16", adminController.potrdiZahtevek(httpSession, 16));
        context.assertIsSatisfied();
    }

    @Test
    public void potrdiZahtevekUspesnoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        zahtevekRepoManager.getZahtevek(10).setStatus(statusRepoManager.getStatus("03"));
        imetnikRepoManager.getImetnik(10).setStatus(statusImetnikRepoManager.getStatusImetnik("05"));
        imetnikRepoManager.getImetnik(12).setStatus(statusImetnikRepoManager.getStatusImetnik("05"));
        assertEquals("redirect:/izpisPodrobno/10", adminController.potrdiZahtevek(httpSession, 10));
        context.assertIsSatisfied();
    }

    //@Test
    public void izvozVseTest() throws IOException {
    }

    //@Test
    public void izvozZahtevekVseTest() {
    }

    //@Test
    public void izvozImetnikTest() {
    }

    @Test
    public void posredovanoNaSigovCANeuspesnoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
                oneOf(httpSession).setAttribute("errorMessage", "Pred posredovanjem na SIGOV-CA je potrebno vse imetnike izvoziti v .csv datoteko.");
            }
        });
        zahtevekRepoManager.getZahtevek(10).setStatus(statusRepoManager.getStatus("05"));
        assertEquals("redirect:/izpisPodrobno/10", adminController.posredovanoNaSigovCA(httpSession, 10));
        context.assertIsSatisfied();
    }

    @Test
    public void posredovanoNaSigovCAUspesnoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        zahtevekRepoManager.getZahtevek(10).setStatus(statusRepoManager.getStatus("05"));
        imetnikRepoManager.getImetnik(10).setStatus(statusImetnikRepoManager.getStatusImetnik("05"));
        imetnikRepoManager.getImetnik(10).setIzvozi(true);
        imetnikRepoManager.getImetnik(12).setStatus(statusImetnikRepoManager.getStatusImetnik("05"));
        imetnikRepoManager.getImetnik(12).setIzvozi(true);
        assertEquals("redirect:/izpisPodrobno/10", adminController.posredovanoNaSigovCA(httpSession, 10));
        context.assertIsSatisfied();
    }

    @Test
    public void posredovanjeNaSIGTest() {
        ModelAndView modelAndView = adminController.posredovanjeNaSIG();
        assertEquals("posredovanjeNaSIG", modelAndView.getViewName());
        List<Zahtevek> zahtevki = (List<Zahtevek>) modelAndView.getModel().get("zahtevki");
        assertEquals(0, zahtevki.size());
        zahtevekRepoManager.getZahtevek(10).setStatus(statusRepoManager.getStatus("05"));
        modelAndView = adminController.posredovanjeNaSIG();
        assertEquals("posredovanjeNaSIG", modelAndView.getViewName());
        zahtevki = (List<Zahtevek>) modelAndView.getModel().get("zahtevki");
        assertEquals(1, zahtevki.size());
    }

    @Test
    public void posredovanoNaSIGProcessTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        zahtevekRepoManager.getZahtevek(10).setStatus(statusRepoManager.getStatus("05"));
        imetnikRepoManager.getImetnik(10).setStatus(statusImetnikRepoManager.getStatusImetnik("05"));
        imetnikRepoManager.getImetnik(10).setIzvozi(true);
        imetnikRepoManager.getImetnik(12).setStatus(statusImetnikRepoManager.getStatusImetnik("05"));
        imetnikRepoManager.getImetnik(12).setIzvozi(true);
        ModelAndView modelAndView = adminController.posredovanoNaSIGProcess(httpSession);
        assertEquals("posredovanjeNaSIG", modelAndView.getViewName());
        assertEquals(1, modelAndView.getModel().get("uspesno"));
        assertEquals(0, modelAndView.getModel().get("neuspesno"));
        List<Zahtevek> zahtevki = (List<Zahtevek>) modelAndView.getModel().get("zahtevki");
        assertEquals(0, zahtevki.size());
        context.assertIsSatisfied();
    }

    @Test
    public void pregledZgodovineTest() {
        ModelAndView modelAndView = adminController.pregledZgodovine(10);
        assertEquals("pregledLog", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("zahtevek"));
        List<ImetnikPregledLogStatus> imetLog = (List<ImetnikPregledLogStatus>) modelAndView.getModel().get("imetnikPregledLogStatus");
        assertEquals(3, imetLog.size());
        assertNotNull(modelAndView.getModel().get("statusLog"));
    }

    //@Test
    public void printObvestiloTest() {
    }

    @Test
    public void zakljuciNeuspesnoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
                oneOf(httpSession).setAttribute("errorMessage", "Pred zaključkom je potrebno natisniti podatke o imetniku.");
            }
        });
        certifikatRepoManager.getCertifikatByID(10).setStatus(statusCertifikatRepoManager.getStatusCertifikat("02"));
        assertEquals("redirect:/pregledPripKartic", adminController.zakljuci(httpSession, 10));
        context.assertIsSatisfied();
    }

    @Test
    public void zakljuciUspesnoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        certifikatRepoManager.getCertifikatByID(10).setStatus(statusCertifikatRepoManager.getStatusCertifikat("02"));
        imetnikRepoManager.getImetnik(10).setNatisnjenoKonco(true);
        assertEquals("redirect:/pregledPripKartic", adminController.zakljuci(httpSession, 10));
        context.assertIsSatisfied();
    }

    @Test
    public void zakljuciIskanjeNeuspesnoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
                oneOf(httpSession).setAttribute("errorMessage", "Pred zaključkom je potrebno natisniti podatke o imetniku.");
            }
        });
        certifikatRepoManager.getCertifikatByID(10).setStatus(statusCertifikatRepoManager.getStatusCertifikat("02"));
        assertEquals("redirect:/iskPripKartic", adminController.zakljuciIskanje(httpSession, 10));
        context.assertIsSatisfied();
    }

    @Test
    public void zakljuciIskanjeUspesnoTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("errorMessage", "");
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        certifikatRepoManager.getCertifikatByID(10).setStatus(statusCertifikatRepoManager.getStatusCertifikat("02"));
        imetnikRepoManager.getImetnik(10).setNatisnjenoKonco(true);
        assertEquals("redirect:/iskPripKartic", adminController.zakljuciIskanje(httpSession, 10));
        context.assertIsSatisfied();
    }

    @Test
    public void potrdiloPrejetoProcessPregledTrueTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        ModelAndView modelAndView = adminController.potrdiloPrejetoProcess(httpSession, "10.10.2013", true, 10);
        assertEquals("redirect:/potrdiloPrejetoPregled", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void potrdiloPrejetoProcessPregledFalseTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });

        ModelAndView modelAndView = adminController.potrdiloPrejetoProcess(httpSession, "10.10.2013", false, 10);
        assertEquals("redirect:/potrdiloPrejeto", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void potrdiloPrejetoProcessDatumNapacneOblikePregledTrueTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });

        ModelAndView modelAndView = adminController.potrdiloPrejetoProcess(httpSession, "10-10-2013", true, 10);
        assertEquals("Datum je napačne oblike.", modelAndView.getModel().get("error"));
        assertNotNull(modelAndView.getModel().get("certifikati"));
        assertEquals("potrdiloPrejetoPregled", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void potrdiloPrejetoProcessDatumNapacneOblikePregledFalseTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });

        ModelAndView modelAndView = adminController.potrdiloPrejetoProcess(httpSession, "10-10-2013", false, 10);
        assertEquals("Datum je napačne oblike.", modelAndView.getModel().get("error"));
        assertNotNull(modelAndView.getModel().get("certifikati"));
        assertEquals("potrdiloPrejeto", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void izvozPodatkovKarticeTest() {
        ModelAndView modelAndView = adminController.izvozPodatkovKartice(null);
        assertEquals("izvozPodatkovKartice", modelAndView.getViewName());
    }

    @Test
    public void izvozPodatkovKarticeIskanjeDatumOkTest() {
        ModelAndView modelAndView = adminController.izvozPodatkovKarticeIskanje("10.08.2013", "14.10.2013");
        assertEquals("izvozPodatkovKartice", modelAndView.getViewName());
        List<Certifikat> certifikati = (List<Certifikat>) modelAndView.getModel().get("certifikati");
        assertEquals(1, certifikati.size());
    }

    @Test
    public void izvozPodatkovKarticeIskanjeDatumNiOkTest() {
        ModelAndView modelAndView = adminController.izvozPodatkovKarticeIskanje("10.08.20013", "14.10.2013");
        assertEquals("izvozPodatkovKartice", modelAndView.getViewName());
        assertNull(modelAndView.getModel().get("seznamCertifikatov"));
        assertEquals(modelAndView.getModel().get("error"), "Datum je napačne oblike.");
    }

    @Test
    public void izvozPodatkovKarticeIskanjeNiRezultatovTest() {
        ModelAndView modelAndView = adminController.izvozPodatkovKarticeIskanje("10.08.2012", "14.10.2012");
        assertEquals("izvozPodatkovKartice", modelAndView.getViewName());
        assertEquals(modelAndView.getModel().get("error"), "Ni najdenih rezultatov.");
    }

    //@Test
    public void izvozPodatkovKarticeProcessTest() {
    }

    @Test
    public void pregledStatistikeTest() {
        //SQL!
        //   ModelAndView modelAndView = adminController.pregledStatistike();
        // assertEquals("pregledStatistike", modelAndView.getViewName());
        //assertEquals(4, modelAndView.getModel().get("zahtevkiVPripravi"));
    }

    @Test
    public void updateCertificateTest() {
        ModelAndView modelAndView = adminController.updateCertificate(1);
        assertEquals("updateCertificateJob", modelAndView.getViewName());
        List<JobLog> jobLog = (List<JobLog>) modelAndView.getModel().get("jobLog");
        assertTrue(jobLog.isEmpty());
        assertEquals(1, modelAndView.getModel().get("pageNum"));
        assertEquals(0.0, modelAndView.getModel().get("izpisCount"));
        assertEquals(nastavitveHelper.getPrikazovNaStran(), modelAndView.getModel().get("prikazovNaStran"));
    }

    @Test
    public void updateCertificateProcessTest() {
        //assertEquals("redirect:/updateCertificateJob/1",adminController.updateCertificateProcess());       
    }

    /**/
    public void checkZahtevek(Zahtevek zahtevek, String imeOrganizacije, int stImetnikov, String statusSifra) {
        assertNotNull(zahtevek);
        assertEquals(imeOrganizacije, zahtevek.getImeOrganizacije());
        assertEquals(stImetnikov, zahtevek.getImetniki().size());
        assertEquals(statusSifra, zahtevek.getStatus().getSifra());
    }
}
