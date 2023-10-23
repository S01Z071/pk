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
import si.vsrs.cif.controllers.LinkController;
import si.vsrs.cif.mod.Opomba;
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
public class LinkControllerIT extends Assert {

    @Autowired
    private LinkController linkController;
    private Uporabnik uporabnikUser = new Uporabnik();

    public LinkControllerIT() {
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
    }

    @After
    public void tearDown() {
    }

    @Test
    public void mainPageTest() {
        assertEquals("redirect:index", linkController.mainPage());
    }

    @Test
    public void mainPageIndexUporabnikNullTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(null));
            }
        });
        ModelAndView modelAndView = linkController.mainPageIndex(httpSession);
        assertEquals("/login", modelAndView.getViewName());
        context.assertIsSatisfied();
    }

    @Test
    public void mainPageIndexUporabnikNotNullTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = linkController.mainPageIndex(httpSession);
        assertEquals("index", modelAndView.getViewName());
        List<Opomba> opombe = (List<Opomba>) modelAndView.getModel().get("opombe");
        assertEquals("Opomba1", opombe.get(0).getNaslov());
        assertEquals("Opomba2", opombe.get(1).getNaslov());
        assertEquals(2, modelAndView.getModel().get("opombeSt"));
        context.assertIsSatisfied();
    }

    @Test
    public void mainPageIndex1Test() {
        assertEquals("redirect:/izpisPodrobno/10", linkController.mainPageIndex1(10));
    }

    @Test
    public void mainPageIndex2Test() {
        assertEquals("redirect:/index", linkController.mainPageIndex2());
    }   
}
