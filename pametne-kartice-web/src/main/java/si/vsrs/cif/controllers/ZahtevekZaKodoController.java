/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.controllers;

import java.io.ByteArrayOutputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.helper.GenerateReportHelper;
import si.vsrs.cif.helper.MetodeHelper;
import si.vsrs.cif.managers.KarticaRepoManager;
import si.vsrs.cif.managers.StatusLogZahtevekZaKodoManager;
import si.vsrs.cif.managers.StatusRepoManager;
import si.vsrs.cif.managers.ZahtevekRepoManager;
import si.vsrs.cif.managers.ZahtevekTempRepoManager;
import si.vsrs.cif.managers.ZahtevekZaKodoRepoManager;
import si.vsrs.cif.managers.ZahtevekZaPrenosRepoManager;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.Kartica;
import si.vsrs.cif.mod.StatusLogZahtevekZaKodo;
import si.vsrs.cif.mod.Zahtevek;
import si.vsrs.cif.mod.ZahtevekTemp;
import si.vsrs.cif.mod.ZahtevekZaKodo;
import si.vsrs.cif.mod.ZahtevekZaPrenos;
import si.vsrs.cif.mod.web.Uporabnik;

/**
 *
 * @author Uporabnik
 */
@Controller
public class ZahtevekZaKodoController {

    @InitBinder
    private void initBinder(HttpServletRequest request, DataBinder binder) {
        for (String param : request.getParameterMap().keySet()) {
            if (param.endsWith("imetnikEnaslov")) {
                binder.registerCustomEditor(String.class, param, new CustomTextEditor(true));
            } else if (param.endsWith("imetnikIme") || param.endsWith("imetnikPriimek")) {
                binder.registerCustomEditor(String.class, param, new CustomTextEditor(false));
            } else {
                binder.registerCustomEditor(String.class, param, new CustomTextEditorZahtevek());
            }
        }
    }
    @Autowired
    ZahtevekRepoManager zahtevekRepoManager;
    @Autowired
    GenerateReportHelper generateReportHelper;
    @Autowired
    KarticaRepoManager karticaRepoManager;
    @Autowired
    ZahtevekZaKodoRepoManager zahtevekZaKodoRepoManager;
    @Autowired
    StatusRepoManager statusRepoManager;
    @Autowired
    MetodeHelper metodeHelper;
    @Autowired
    StatusLogZahtevekZaKodoManager statusLogZahtevekZaKodoManager;
    @Autowired
    ZahtevekTempRepoManager zahtevekTempRepoManager;
    @Autowired
    ZahtevekZaPrenosRepoManager zahtevekZaPrenosRepoManager;

    @RequestMapping(value = "/ZahtevekZaKodo")
    public ModelAndView zahtevekZaKodo() {
        ModelAndView modelAndView = new ModelAndView("dodajZahtevekZaKodo");
        return modelAndView;
    }

    @RequestMapping(value = "/ZahtevekZaKodo/iskanje", produces = "text/plain;charset=UTF-8")
    public ModelAndView zahtevekZaKodoIskanje(@RequestParam("iskano") String iskano, HttpSession session) {
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        List<Kartica> kartice = karticaRepoManager.getKarticeZaKodo(iskano, uporabnik);

        ModelAndView modelAndView = new ModelAndView("dodajZahtevekZaKodo");
        if (kartice.isEmpty()) {
            modelAndView.addObject("error", "Ni zadetkov.");
        } else {
            if (kartice.size() == 1) {
                return new ModelAndView("redirect:/ZahtevekZaKodo/dodaj/" + kartice.get(0).getId());
            } else {
                modelAndView.addObject("kartice", kartice);
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "/ZahtevekZaKodo/dodaj/{idK}")
    public ModelAndView zahtevekZaKodoDodaj(@PathVariable Integer idK) {
        ModelAndView modelAndView = new ModelAndView("dodajZahtevekZaKodo");
        Kartica kartica = karticaRepoManager.findById(idK);
        Imetnik imetnik = kartica.getImetnik();
        Long zahtevekId = zahtevekRepoManager.getZahtevekIDFromImetnikID(imetnik.getId());       
        if (zahtevekId == null && imetnik.getPrenesenImetnik()) {
            if(imetnik.getPrenesenImetnik()){
                zahtevekId = zahtevekZaPrenosRepoManager.getZahtevekIdByNovImetnikId(imetnik.getId());
            }else{
                throw new RuntimeException("Napaka v poslovni logiki!");
            }           
        } 
        //Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(zahtevekRepoManager.getZahtevekIDFromImetnikID(imetnik.getId()));

        //ZahtevekTemp zahtevekTemp = zahtevekTempRepoManager.getZahtevekTemp(zahtevek.getSifraOrganizacije());
        ZahtevekTemp zahtevekTemp = zahtevekTempRepoManager.getZahtevekTemp(imetnik.getSifraOrganizacije());
        ZahtevekZaKodo zahtevekZaKodo = new ZahtevekZaKodo(kartica, imetnik, zahtevekTemp, zahtevekId);
        modelAndView.addObject("zahtevekZaKodo", zahtevekZaKodo);

        return modelAndView;
    }

    @RequestMapping(value = "/ZahtevekZaKodo/dodaj/process")
    public ModelAndView zahtevekZaKodoDodajProcess(@ModelAttribute ZahtevekZaKodo zahtevekZaKodo, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("dodajZahtevekZaKodo");

        List<ZahtevekZaKodo> zahtevkiZe = zahtevekZaKodoRepoManager.findByStatusSifraAndSerijskaStevilka(zahtevekZaKodo.getSerijskaStevilka());
        if (!zahtevkiZe.isEmpty()) {
            modelAndView.addObject("info", "Zahtevek za to oznako kartice je že v pripravi. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov.");
        } else {
            Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
            if (uporabnik.getIzbranaVlogaSodisce().getVloga().getId().compareTo("002") == 0 || zahtevekZaKodo.getSifraOrganizacije().compareTo(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId()) == 0) {
                zahtevekZaKodoRepoManager.addZahtevekZaKodo(zahtevekZaKodo);
                metodeHelper.insertInStatusLogZaKodo(uporabnik, "00", "01", zahtevekZaKodo.getId(), statusLogZahtevekZaKodoManager, "Zahtevek kreiran.");
                modelAndView.addObject("info", "Zahtevek shranjen. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov.");
                return new ModelAndView("redirect:/ZahtevekZaKodo/podrobno/" + zahtevekZaKodo.getId());
            } else {
                return new ModelAndView("redirect:/index");
            }
        }
        modelAndView.addObject("zahtevekZaKodo", zahtevekZaKodo);
        return modelAndView;
    }

    @RequestMapping(value = "/ZahtevekZaKodo/podrobno/{idZ}")
    public ModelAndView zahtevekZaKodoPodrobno(@PathVariable Integer idZ) {
        ModelAndView modelAndView = new ModelAndView("izpisPodrobnoKoda");
        modelAndView.addObject("zahtevekZaKodo", zahtevekZaKodoRepoManager.getZahtevekZaKodo(idZ));
        return modelAndView;
    }

    @RequestMapping(value = "/ZahtevekZaKodo/posreduj/{idZ}")
    public ModelAndView zahtevekZaKodoPosreduj(@PathVariable Integer idZ, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("izpisPodrobnoKoda");
        ZahtevekZaKodo zahtevekZaKodo = zahtevekZaKodoRepoManager.getZahtevekZaKodo(idZ);
        if (zahtevekZaKodo.getStatus().getSifra().compareTo("01") == 0) {
            if (zahtevekZaKodo.getNatisnjeno() != null && zahtevekZaKodo.getNatisnjeno()) {
                zahtevekZaKodo.setStatus(statusRepoManager.getStatus("07"));
                zahtevekZaKodoRepoManager.updateZahtevekZaKodo(zahtevekZaKodo);
                Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
                metodeHelper.insertInStatusLogZaKodo(uporabnik, "02", "07", zahtevekZaKodo.getId(), statusLogZahtevekZaKodoManager, "Zahtevek posredovan na SIGOV-CA.");
            } else {
                modelAndView.addObject("error", "Prosim, da natisnite vlogo preden jo posredujete na SIGOV-CA.");
            }

            modelAndView.addObject("zahtevekZaKodo", zahtevekZaKodo);
            return modelAndView;
        }
        return new ModelAndView("redirect:/ZahtevekZaKodo/podrobno/" + idZ);
    }

    @RequestMapping(value = "/ZahtevekZaKodo/brisi/{idZ}")
    public String zahtevekZaKodoBrisi(@PathVariable Integer idZ, HttpSession session) {
        ZahtevekZaKodo zahtevekZaKodo = zahtevekZaKodoRepoManager.getZahtevekZaKodo(idZ);
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        if (zahtevekZaKodo.getStatus().getSifra().compareTo("01") == 0) {
            zahtevekZaKodo.setStatus(statusRepoManager.getStatus("04"));
            zahtevekZaKodoRepoManager.updateZahtevekZaKodo(zahtevekZaKodo);
            metodeHelper.insertInStatusLogZaKodo(uporabnik, "01", "04", zahtevekZaKodo.getId(), statusLogZahtevekZaKodoManager, "Zahtevek izbrisan.");
        }
        if (uporabnik.getIzbranaVlogaSodisce().getVloga().getId().compareTo("002") == 0) {
            return "redirect:/izpisPodatkovAdmin/1/1/2";
        } else {
            return "redirect:/izpisPodatkov/1/1/2";
        }
    }

    @RequestMapping(value = "/ZahtevekZaKodo/odkleni/{idZ}")
    public ModelAndView zahtevekZaKodoOdkleni(@PathVariable Integer idZ, HttpSession session) {
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        ZahtevekZaKodo zahtevekZaKodo = zahtevekZaKodoRepoManager.getZahtevekZaKodo(idZ);
        String statusSifra = zahtevekZaKodo.getStatus().getSifra();
        if (uporabnik.getIzbranaVlogaSodisce().getVloga().getId().compareTo("002") == 0 && (statusSifra.compareTo("04") == 0 || statusSifra.compareTo("07") == 0)) {
            zahtevekZaKodo.setStatus(statusRepoManager.getStatus("01"));
            zahtevekZaKodo.setNatisnjeno(false);
            zahtevekZaKodoRepoManager.updateZahtevekZaKodo(zahtevekZaKodo);
            metodeHelper.insertInStatusLogZaKodo(uporabnik, statusSifra, "01", zahtevekZaKodo.getId(), statusLogZahtevekZaKodoManager, "Zahtevek odklenjen.");
            ModelAndView modelAndView = new ModelAndView("izpisPodrobnoKoda");
            modelAndView.addObject("zahtevekZaKodo", zahtevekZaKodo);
            return modelAndView;
        }
        return new ModelAndView("redirect:/ZahtevekZaKodo/podrobno/" + idZ);
    }

    @RequestMapping(value = "/ZahtevekZaKodo/natisni/{idZ}")
    public void zahtevekZaKodoNatisni(@PathVariable Integer idZ, HttpServletResponse response) throws Exception {

        ZahtevekZaKodo zahtevekZaKodo = zahtevekZaKodoRepoManager.getZahtevekZaKodo(idZ);
        if (zahtevekZaKodo.getStatus().getSifra().compareTo("01") == 0) {
            zahtevekZaKodo.setNatisnjeno(true);
            zahtevekZaKodoRepoManager.updateZahtevekZaKodo(zahtevekZaKodo);
            ModelAndView modelAndView = new ModelAndView("izpisPodrobnoKoda");
            modelAndView.addObject("zahtevekZaKodo", zahtevekZaKodo);
            ByteArrayOutputStream outPutStrem = generateReportHelper.createReportZahtevekZaOdklepanjeKartice(zahtevekZaKodo);
            metodeHelper.setHeaderPDF(response, zahtevekZaKodo.getCrtnaKoda(), outPutStrem);

            //return modelAndView;
        }
        //return new ModelAndView("redirect:/ZahtevekZaKodo/podrobno/" + idZ);
    }

    @RequestMapping(value = "/ZahtevekZaKodo/zgodovina/{idZ}")
    public ModelAndView zahtevekZaKodoZgodovina(@PathVariable Integer idZ) {
        ModelAndView modelAndView = new ModelAndView("pregledLogZaKodo");
        ZahtevekZaKodo zahtevek = zahtevekZaKodoRepoManager.getZahtevekZaKodo(idZ);
        List<StatusLogZahtevekZaKodo> statusLog = statusLogZahtevekZaKodoManager.getStatusLogByZahtevekId(zahtevek.getId());
        modelAndView.addObject("statusLog", statusLog);
        modelAndView.addObject("zahtevek", zahtevek);
        return modelAndView;
    }
}
