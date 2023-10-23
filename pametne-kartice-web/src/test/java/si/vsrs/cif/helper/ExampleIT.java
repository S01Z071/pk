package si.vsrs.cif.helper;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.List;
import javax.servlet.http.HttpSession;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.controllers.ImetnikController;
import si.vsrs.cif.controllers.ZahtevekController;
import si.vsrs.cif.managers.StatusRepoManager;
import si.vsrs.cif.mod.KontaktnaOseba;
import si.vsrs.cif.mod.Predstojnik;
import si.vsrs.cif.mod.Status;
import si.vsrs.cif.mod.Zahtevek;
import si.vsrs.cif.mod.datajpa.repository.StatusRepository;
import si.vsrs.cif.mod.datajpa.repository.ZahtevekRepository;
import si.vsrs.cif.mod.web.Sodisce;
import si.vsrs.cif.mod.web.Uporabnik;
import si.vsrs.cif.mod.web.Vloga;
import si.vsrs.cif.mod.web.VlogaSodisce;

/**
 *
 * @author andrej
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:/spring/spring-servlet_it.xml",
    "classpath:/spring/dao-context_it.xml",
    "classpath:/spring/dao-context.xml",
    "classpath:/spring/app-context.xml",
    "classpath:/spring/dao-context_test.xml",
    "classpath:/spring/app-context_test.xml"
})
*/ 
@Transactional
public class ExampleIT extends Assert {

    @Autowired
    private ZahtevekController zahtevekController;
    @Autowired
    private ImetnikController imetnikController;
    @Autowired
    StatusRepoManager statusRepoManager;

    public ZahtevekController getZahtevekController() {
        return zahtevekController;
    }

    public void setZahtevekController(ZahtevekController zahtevekController) {
        this.zahtevekController = zahtevekController;
    }

    public ExampleIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createReportZahtevek method, of class GenerateReportHelper.
     */
    
    @Test
    @Ignore
    public void exampleTest() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        final HttpSession httpSession = context.mock(HttpSession.class);
        final Uporabnik uporabnik = context.mock(Uporabnik.class);
        final VlogaSodisce vlogaSodisce = context.mock(VlogaSodisce.class);
        final Sodisce sodisce = context.mock(Sodisce.class);
        context.checking(new Expectations() {
            {
                oneOf(httpSession).setAttribute("info", "");
                oneOf(httpSession).getAttribute("uporabnik");
                //allowing(httpSession);
                will(returnValue(uporabnik));
                oneOf(uporabnik).getIzbranaVlogaSodisce();
                will(returnValue(vlogaSodisce));
                oneOf(vlogaSodisce).getSodisce();
                will(returnValue(sodisce));
                oneOf(sodisce).getId();
                will(returnValue("S23"));
            }
        });

        ModelAndView modelAndView = zahtevekController.addVloga(httpSession);
        assertNotNull(modelAndView.getModel().get("zahtevek"));
        context.assertIsSatisfied();
    }

  
    @Autowired
    ZahtevekRepository repository;
    @Autowired
    StatusRepository statusRepository;

    @Test
    @Ignore
    public void testTest() {
        Status status = new Status();
        status.setSifra("03");
        statusRepository.save(status);

        for (int i = 0; i < 5; i++) {
            Zahtevek z = new Zahtevek();
            z.setHisnaStevilka("9");
            z.setImeOrganizacije("ime organizacije");
            //z.setImetnikStatus(null);
            KontaktnaOseba kontaktnaOseba = new KontaktnaOseba();
            kontaktnaOseba.setFunkcijaK("funkcija K");
            kontaktnaOseba.setImeK("ime k");
            kontaktnaOseba.setPriimekK("priimek k");
            kontaktnaOseba.setTelefonK("899809");
            kontaktnaOseba.seteNaslovK("andrej@sodisce.si");
            z.setKontaktnaOseba(kontaktnaOseba);
            z.setNaselje("nasleje");
            z.setNazivPoste("naziv poste");
            z.setPostnaStevilka("1000");
            Predstojnik predstojnik = new Predstojnik();
            predstojnik.setFunkcijaP("funkcija P");
            predstojnik.setPriimekP("priimek P");
            predstojnik.setImeP("ime");
            predstojnik.seteNaslovP("andrej@sodisce.si");
            z.setPredstojnik(predstojnik);
            z.setSifraOrganizacije("sifra organizacije");
            z.setCrtnaKoda("" + i);
            z.setUlica("ulica");
            z.setStatus(status);

            repository.save(z);

        }


        //zahtevekDAO.getStatus("03");
        statusRepoManager.getStatus("03");
        Pageable p = new PageRequest(2, 2);

        Page<Zahtevek> zahtevekPage = repository.findAll(p);
        for (Zahtevek zahtevek1 : zahtevekPage.getContent()) {
            System.out.println("Zahtevek111111: " + zahtevek1.toString());
            System.out.println("status: " + zahtevek1.getStatus().getSifra());
        }


        List<Zahtevek> zahtevek = repository.findByUlicaContains("ulic");
        for (Zahtevek zahtevek1 : zahtevek) {
            System.out.println("Zahtevek: " + zahtevek1.toString());
            System.out.println("status: " + zahtevek1.getStatus().getSifra());
        }
        System.out.println("Z: " + repository.getZahtevekCount("sifra organizacije"));
    }
}