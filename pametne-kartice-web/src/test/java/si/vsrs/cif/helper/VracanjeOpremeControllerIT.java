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
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.controllers.VracanjeOpremeController;
import si.vsrs.cif.mod.Kartica;
import si.vsrs.cif.mod.SeznamCitalcevSigovca;
import si.vsrs.cif.mod.SeznamKarticSigovca;
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
    "classpath:/spring/dao-context_kartCertfTest.xml",
    "classpath:/spring/dao-context.xml",
    "classpath:/spring/app-context.xml",
    //"classpath:/spring/dao-context_test.xml",
    "classpath:/spring/app-context_test.xml"})
@Transactional
public class VracanjeOpremeControllerIT extends Assert {

    @Autowired
    private VracanjeOpremeController vracanjeOpremeController;

    private Uporabnik uporabnikAdmin = new Uporabnik();
    
    public VracanjeOpremeControllerIT() {
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
    public void vracanjeKartCitTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCit();
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
    }

    @Test
    public void vracanjeKartCitIskanjeNiZadetkovTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitIskanje("niZadetkoviskanje");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        assertEquals("Ni zadetkov.", modelAndView.getModel().get("error"));
    }

    @Test
    public void vracanjeKartCitIskanjeKarticaZadetekOznakaTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitIskanje("21121042202322");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(1, kartice.size());
        assertEquals(0, citalci.size());
        assertEquals(0, karticeVSRS.size());
        assertEquals("21121042202322", modelAndView.getModel().get("iskalniNiz"));

    }

    @Test
    public void vracanjeKartCitIskanjeKarticaZadetekImeInPriimekTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitIskanje("Janez Novak");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(1, kartice.size());
        assertEquals(0, citalci.size());
        assertEquals(0, karticeVSRS.size());
        assertEquals("Janez Novak", modelAndView.getModel().get("iskalniNiz"));
    }

    @Test
    public void vracanjeKartCitIskanjeCitalecZadetekOznakaTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitIskanje("21120648201501");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(1, kartice.size());
        assertEquals(1, citalci.size());
        assertEquals(0, karticeVSRS.size());
        assertEquals("21120648201501", modelAndView.getModel().get("iskalniNiz"));

    }

    @Test
    public void vracanjeKartCitIskanjeCitalecZadetekImeInPriimekTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitIskanje("Tattjana Premk");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(0, kartice.size());
        assertEquals(1, citalci.size());
        assertEquals(0, karticeVSRS.size());
        assertEquals("Tattjana Premk", modelAndView.getModel().get("iskalniNiz"));
    }

    @Test
    public void vracanjeKartCitIskanjeKarticaVSRSZadetekBarCodeTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitIskanje("078333");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(0, kartice.size());
        assertEquals(0, citalci.size());
        assertEquals(1, karticeVSRS.size());
        assertEquals("078333", modelAndView.getModel().get("iskalniNiz"));

    }

    @Test
    public void vracanjeKartCitIskanjeKarticaVSRSZadetekSerijskaTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitIskanje("512088E30");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(0, kartice.size());
        assertEquals(0, citalci.size());
        assertEquals(1, karticeVSRS.size());
        assertEquals("512088E30", modelAndView.getModel().get("iskalniNiz"));
    }

    @Test
    public void vracanjeKartCitIskanjeKarticaVSRSZadetekImeTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitIskanje("Miha");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(0, kartice.size());
        assertEquals(0, citalci.size());
        assertEquals(1, karticeVSRS.size());
        assertEquals("Miha", modelAndView.getModel().get("iskalniNiz"));
    }

    @Test
    public void vracanjeKartCitIskanjeKarticaVSRSZadetekPriimekTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitIskanje("Koncilja");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(0, kartice.size());
        assertEquals(0, citalci.size());
        assertEquals(1, karticeVSRS.size());
        assertEquals("Koncilja", modelAndView.getModel().get("iskalniNiz"));
    }

    @Test
    public void vracanjeKartCitIskanjeKarticaVSRSZadetekImeInPriimekTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitIskanje("Marko Oman");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(0, kartice.size());
        assertEquals(0, citalci.size());
        assertEquals(1, karticeVSRS.size());
        assertEquals("Marko Oman", modelAndView.getModel().get("iskalniNiz"));
    }

    @Test
    public void vracanjeKartCitIskanjeKarticaVSRSZadetekENaslovTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitIskanje("povezan.certifikat@sodisce.si");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(0, kartice.size());
        assertEquals(0, citalci.size());
        assertEquals(1, karticeVSRS.size());
        assertEquals("povezan.certifikat@sodisce.si", modelAndView.getModel().get("iskalniNiz"));
    }

    @Test
    public void vracanjeKartCitIskanjeVelikostZnakovInDelnoIskanjeTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitIskanje("12088");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(0, kartice.size());
        assertEquals(0, citalci.size());
        assertEquals(2, karticeVSRS.size());
        assertEquals("12088", modelAndView.getModel().get("iskalniNiz"));

        //
        modelAndView = vracanjeOpremeController.vracanjeKartCitIskanje("064");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(1, kartice.size());
        assertEquals(3, citalci.size());
        assertEquals(0, karticeVSRS.size());
        assertEquals("064", modelAndView.getModel().get("iskalniNiz"));

        //
        modelAndView = vracanjeOpremeController.vracanjeKartCitIskanje("JANA");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(1, kartice.size());
        assertEquals(1, citalci.size());
        assertEquals(0, karticeVSRS.size());
        assertEquals("JANA", modelAndView.getModel().get("iskalniNiz"));

        //
        modelAndView = vracanjeOpremeController.vracanjeKartCitIskanje("MaRkO OmAN");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(0, kartice.size());
        assertEquals(0, citalci.size());
        assertEquals(1, karticeVSRS.size());
        assertEquals("MaRkO OmAN", modelAndView.getModel().get("iskalniNiz"));
    }

    @Test
    public void vracanjeKartCitfProcessKarticaNapacenDatumTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitfProcessKartica("234.345.2014", 10, "21121042202322");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(1, kartice.size());
        assertEquals(0, citalci.size());
        assertEquals(0, karticeVSRS.size());
        assertEquals("21121042202322", modelAndView.getModel().get("iskalniNiz"));
        assertEquals("Datum je napačne oblike.", modelAndView.getModel().get("error"));
    }

    @Test
    public void vracanjeKartCitfProcessKarticaUspesnoTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitfProcessKartica("10.10.2013", 10, "21121042202322");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(0, kartice.size());
        assertEquals(0, citalci.size());
        assertEquals(0, karticeVSRS.size());
        assertEquals("21121042202322", modelAndView.getModel().get("iskalniNiz"));
        assertEquals("Datum vrnitve kartice shranjen.", modelAndView.getModel().get("error"));
    }

    @Test
    public void vracanjeKartCitfProcessCitalecNapacenDatumTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitfProcessCitalec("234.345.2014", 11, "2112064820150");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(1, kartice.size());
        assertEquals(3, citalci.size());
        assertEquals(0, karticeVSRS.size());
        assertEquals("2112064820150", modelAndView.getModel().get("iskalniNiz"));
        assertEquals("Datum je napačne oblike.", modelAndView.getModel().get("error"));
    }

    @Test
    public void vracanjeKartCitfProcessCitalecaUspesnoTest() {
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitfProcessCitalec("10.10.2013", 11, "2112064820150");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(1, kartice.size());
        assertEquals(2, citalci.size());
        assertEquals(0, karticeVSRS.size());
        assertEquals("2112064820150", modelAndView.getModel().get("iskalniNiz"));
        assertEquals("Datum vrnitve čitalca shranjen.", modelAndView.getModel().get("error"));
    }

    @Test
    public void vracanjeKartCitfProcessKarticaVSRSNapacenDatumTest() {       
        ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitfProcessKarticaVSRS(null,"234.345.2014", 10, "07");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(0, kartice.size());
        assertEquals(0, citalci.size());
        assertEquals(2, karticeVSRS.size());
        assertEquals("07", modelAndView.getModel().get("iskalniNiz"));
        assertEquals("Datum je napačne oblike.", modelAndView.getModel().get("error"));
    }
    
    
    @Test
     public void vracanjeKartCitfProcessKarticaVSRSUspesnoTest() {
         Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikAdmin));
            }
        });
        
        
         ModelAndView modelAndView = vracanjeOpremeController.vracanjeKartCitfProcessKarticaVSRS(httpSession,"10.10.2013", 10, "07");
        assertEquals("vracanjeKartCit", modelAndView.getViewName());
        List<SeznamKarticSigovca> kartice = (List<SeznamKarticSigovca>) modelAndView.getModel().get("seznamKarticSigovca");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("seznamCitalcevSigovca");
        List<Kartica> karticeVSRS = (List<Kartica>) modelAndView.getModel().get("karticeVSRS");

        assertEquals(0, kartice.size());
        assertEquals(0, citalci.size());
        assertEquals(1, karticeVSRS.size());
        assertEquals("07", modelAndView.getModel().get("iskalniNiz"));
        assertEquals("Datum vrnitve kartice shranjen.", modelAndView.getModel().get("error"));
    }
}
