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
import si.vsrs.cif.controllers.OpombaController;
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
public class OpombaControllerIT extends Assert {

    @Autowired
    private OpombaController opombaController;
    private Uporabnik uporabnikUser = new Uporabnik();

    public OpombaControllerIT() {
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
    public void addOpombaPageTest() {
        ModelAndView modelAndView = opombaController.addOpombaPage(10);
        assertEquals("dodajOpombo", modelAndView.getViewName());
        assertEquals(10, modelAndView.getModel().get("idZ"));
        assertNotNull(modelAndView.getModel().get("opomba"));

    }

    @Test
    public void addOpombaValidationErrorTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final BindingResult bindingResult = context.mock(BindingResult.class);
        Opomba opomba = new Opomba("Opomba3", "Opomba3 vsebina");
        context.checking(new Expectations() {
            {
                allowing(bindingResult).hasErrors();
                will(returnValue(true));
            }
        });
        ModelAndView modelAndView = opombaController.addOpomba(opomba, bindingResult, httpSession, 10);
        assertEquals("dodajOpombo", modelAndView.getViewName());
        assertEquals(opomba, modelAndView.getModel().get("opomba"));
        assertEquals(10, modelAndView.getModel().get("idZ"));
        context.assertIsSatisfied();
    }

    @Test
    public void addOpombaTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final BindingResult bindingResult = context.mock(BindingResult.class);
        Opomba opomba = new Opomba("Opomba3", "Opomba3 vsebina");
        context.checking(new Expectations() {
            {
                allowing(bindingResult).hasErrors();
                will(returnValue(false));

                oneOf(httpSession).getAttribute("uporabnik");
                will(returnValue(uporabnikUser));
            }
        });
        ModelAndView modelAndView = opombaController.addOpomba(opomba, bindingResult, httpSession, 10);
        assertEquals("redirect:/izpisPodrobno/10", modelAndView.getViewName());
        context.assertIsSatisfied();
    }
}
