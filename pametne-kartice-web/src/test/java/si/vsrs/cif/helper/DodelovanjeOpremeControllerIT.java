/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.helper;

import java.util.List;
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
import si.vsrs.cif.controllers.DodelovanjeOpremeController;
import si.vsrs.cif.managers.SeznamCitalcevSigovcaRepoManager;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.SeznamCitalcevSigovca;
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
    "classpath:/spring/app-context_test.xml"
})
@Transactional
public class DodelovanjeOpremeControllerIT extends Assert {

    @Autowired
    DodelovanjeOpremeController dodelovanjeOpremeController;
    @Autowired
    SeznamCitalcevSigovcaRepoManager seznamCitalcevSigovcaRepoManager;
    private Uporabnik uporabnikAdmin = new Uporabnik();

    public DodelovanjeOpremeControllerIT() {
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
    public void dodeljevanjeCitalciTest() {
        assertEquals("dodeliCitalec", dodelovanjeOpremeController.dodeljevanjeCitalci().getViewName());
    }

    @Test
    public void dodeljevanjeCitalciIskanjeObaPraznaTest() {
        ModelAndView modelAndView = dodelovanjeOpremeController.dodeljevanjeCitalciIskanje("", "");
        assertEquals("dodeliCitalec", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("citalci");
        assertNotNull(imetniki);
        assertNotNull(citalci);
        assertEquals(0, imetniki.size());
        assertEquals(0, citalci.size());
        assertEquals("", modelAndView.getModel().get("iskalniNizC"));
        assertEquals("", modelAndView.getModel().get("iskalniNizI"));
    }

    @Test
    public void dodeljevanjeCitalciIskanjeCitalecPrazenTest() {
        ModelAndView modelAndView = dodelovanjeOpremeController.dodeljevanjeCitalciIskanje("", "mArKO oman");
        assertEquals("dodeliCitalec", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("citalci");
        assertNotNull(imetniki);
        assertNotNull(citalci);
        assertEquals(1, imetniki.size());
        assertEquals(0, citalci.size());
        assertEquals("", modelAndView.getModel().get("iskalniNizC"));
        assertEquals("mArKO oman", modelAndView.getModel().get("iskalniNizI"));
    }

    @Test
    public void dodeljevanjeCitalciIskanjeImetnikPrazenTest() {
        ModelAndView modelAndView = dodelovanjeOpremeController.dodeljevanjeCitalciIskanje("2112", "");
        assertEquals("dodeliCitalec", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("citalci");
        assertNotNull(imetniki);
        assertNotNull(citalci);
        assertEquals(0, imetniki.size());
        assertEquals(2, citalci.size());
        assertEquals("2112", modelAndView.getModel().get("iskalniNizC"));
        assertEquals("", modelAndView.getModel().get("iskalniNizI"));
    }

    @Test
    public void dodeljevanjeCitalciIskanjeNobenPrazenTest() {
        ModelAndView modelAndView = dodelovanjeOpremeController.dodeljevanjeCitalciIskanje("2112", "Marko Oman");
        assertEquals("dodeliCitalec", modelAndView.getViewName());
        List<Imetnik> imetniki = (List<Imetnik>) modelAndView.getModel().get("imetniki");
        List<SeznamCitalcevSigovca> citalci = (List<SeznamCitalcevSigovca>) modelAndView.getModel().get("citalci");
        assertNotNull(imetniki);
        assertNotNull(citalci);
        assertEquals(1, imetniki.size());
        assertEquals(2, citalci.size());
        assertEquals("2112", modelAndView.getModel().get("iskalniNizC"));
        assertEquals("Marko Oman", modelAndView.getModel().get("iskalniNizI"));
    }

    @Test
    public void dodeljevanjeCitalciDodeliObaPraznaTest() {
        ModelAndView modelAndView = dodelovanjeOpremeController.dodeljevanjeCitalciDodeli("", "");
        assertEquals("dodeliCitalec", modelAndView.getViewName());
        assertEquals("Imetnik ali čitalec ni izbran.", modelAndView.getModel().get("info"));

    }

    @Test
    public void dodeljevanjeCitalciDodeliCitalecPrazenTest() {
        ModelAndView modelAndView = dodelovanjeOpremeController.dodeljevanjeCitalciDodeli("", "13");
        assertEquals("dodeliCitalec", modelAndView.getViewName());
        assertEquals("Imetnik ali čitalec ni izbran.", modelAndView.getModel().get("info"));
    }

    @Test
    public void dodeljevanjeCitalciDodeliImetnikPrazenTest() {
        ModelAndView modelAndView = dodelovanjeOpremeController.dodeljevanjeCitalciDodeli("15", "");
        assertEquals("dodeliCitalec", modelAndView.getViewName());
        assertEquals("Imetnik ali čitalec ni izbran.", modelAndView.getModel().get("info"));
    }

    @Test
    public void dodeljevanjeCitalciDodeliNobenPrazenTest() {
        ModelAndView modelAndView = dodelovanjeOpremeController.dodeljevanjeCitalciDodeli("15", "13");
        assertEquals("dodeliCitalec", modelAndView.getViewName());
        assertEquals("Čitalec uspešno dodeljen.", modelAndView.getModel().get("info"));
        List<SeznamCitalcevSigovca> citalci = seznamCitalcevSigovcaRepoManager.findByImetnikIdAndDatumVrnitveIsNull(13);
        assertEquals(1, citalci.size());
        SeznamCitalcevSigovca citalec = citalci.get(0);
        assertFalse(citalec.getDatumIzdaje() == null);
        assertTrue(citalec.getDatumVrnitve() == null);
        assertEquals("Marko Oman", citalec.getImeInPriimek());
        assertEquals("Vrhovno sodisce RS", citalec.getImeOrganizacije());
        assertEquals("21121042202317", citalec.getOznaka());
        assertEquals("S01", citalec.getSifraSodisca());
        assertEquals("ActivCard USB", citalec.getTip());
    }
}
