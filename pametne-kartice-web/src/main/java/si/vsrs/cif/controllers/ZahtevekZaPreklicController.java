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
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.helper.GenerateReportHelper;
import si.vsrs.cif.helper.MetodeHelper;
import si.vsrs.cif.helper.NastavitveHelper;
import si.vsrs.cif.managers.CertifikatRepoManager;
import si.vsrs.cif.managers.StatusLogPreklicRepoManager;
import si.vsrs.cif.managers.StatusRepoManager;
import si.vsrs.cif.managers.ZahtevekRepoManager;
import si.vsrs.cif.managers.ZahtevekTempRepoManager;
import si.vsrs.cif.managers.ZahtevekZaPreklicRepoManager;
import si.vsrs.cif.managers.ZahtevekZaPrenosRepoManager;
import si.vsrs.cif.mod.Certifikat;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.StatusLogPreklic;
import si.vsrs.cif.mod.Zahtevek;
import si.vsrs.cif.mod.ZahtevekTemp;
import si.vsrs.cif.mod.ZahtevekZaPreklic;
import si.vsrs.cif.mod.web.Uporabnik;

/**
 *
 * @author Uporabnik
 */
@Controller
public class ZahtevekZaPreklicController {

    @Autowired
    CertifikatRepoManager certifikatRepoManager;
    @Autowired
    ZahtevekRepoManager zahtevekRepoManager;
    @Autowired
    ZahtevekTempRepoManager zahtevekTempRepoManager;
    @Autowired
    ZahtevekZaPreklicRepoManager zahtevekZaPreklicRepoManager;
    @Autowired
    MetodeHelper metodeHelper;
    @Autowired
    StatusLogPreklicRepoManager statusLogPreklicRepoManager;
    @Autowired
    NastavitveHelper nastavitveHelper;
    @Autowired
    StatusRepoManager statusRepoManager;
    @Autowired
    GenerateReportHelper generateReportHelper;
    @Autowired
    ZahtevekZaPrenosRepoManager zahtevekZaPrenosRepoManager;

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

    @RequestMapping(value = "/ZahtevekZaPreklic")
    public ModelAndView zahtevekZaPreklic() {
        ModelAndView modelAndView = new ModelAndView("dodajZahtevekZaPreklic");
        return modelAndView;
    }

    @RequestMapping(value = "/ZahtevekZaPreklic/iskanje", produces = "text/plain;charset=UTF-8")
    public ModelAndView zahtevekZaPreklicIskanje(@RequestParam("iskano") String iskano, HttpSession session) {
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        List<Certifikat> certifikati = certifikatRepoManager.getCertifikatZapreklic(iskano, uporabnik);
        ModelAndView modelAndView = new ModelAndView("dodajZahtevekZaPreklic");
        if (certifikati.isEmpty()) {
            modelAndView.addObject("error", "Ni zadetkov.");
        } else {
            if (certifikati.size() == 1) {
                return new ModelAndView("redirect:/ZahtevekZaPreklic/dodaj/" + certifikati.get(0).getId());
            } else {
                modelAndView.addObject("certifikati", certifikati);
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "/ZahtevekZaPreklic/dodaj/{idC}")
    public ModelAndView zahtevekZaPreklicDodaj(@PathVariable Integer idC) {
        ModelAndView modelAndView = new ModelAndView("dodajZahtevekZaPreklic");
        Certifikat certifikat = certifikatRepoManager.findById(idC);
        Imetnik imetnik = certifikat.getImetnik();

        //Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(zahtevekRepoManager.getZahtevekIDFromImetnikID(imetnik.getId()));

        Long zahtevekId = zahtevekRepoManager.getZahtevekIDFromImetnikID(imetnik.getId());
         if (zahtevekId == null && imetnik.getPrenesenImetnik()) {
            if(imetnik.getPrenesenImetnik()){
                zahtevekId = zahtevekZaPrenosRepoManager.getZahtevekIdByNovImetnikId(imetnik.getId());
            }else{
                throw new RuntimeException("Napaka v poslovni logiki!");
            }           
        } 
        
        // ZahtevekTemp zahtevekTemp = zahtevekTempRepoManager.getZahtevekTemp(zahtevek.getSifraOrganizacije());
        ZahtevekTemp zahtevekTemp = zahtevekTempRepoManager.getZahtevekTemp(imetnik.getSifraOrganizacije());
        ZahtevekZaPreklic zahtevekZaPreklic = new ZahtevekZaPreklic(certifikat, imetnik, zahtevekTemp, zahtevekId);
        modelAndView.addObject("zahtevekZaPreklic", zahtevekZaPreklic);
        return modelAndView;
    }

    @RequestMapping(value = "/ZahtevekZaPreklic/dodaj/process")
    public ModelAndView zahtevekZaPreklicDodajProcess(@Valid @ModelAttribute ZahtevekZaPreklic zahtevekZaPreklic, BindingResult bindingResult, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("dodajZahtevekZaPreklic");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("zahtevekZaPreklic", zahtevekZaPreklic);
            return modelAndView;
        }
        List<ZahtevekZaPreklic> zahtevkiZe = zahtevekZaPreklicRepoManager.findBySerijskaStevilkaAndStatusSifraNot(zahtevekZaPreklic.getSerijskaStevilka());

        if (!zahtevkiZe.isEmpty()) {
            modelAndView.addObject("info", "Zahtevek za to serijsko številko certifikata je že v pripravi. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov.");
        } else {
            Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
            if (uporabnik.getIzbranaVlogaSodisce().getVloga().getId().compareTo("002") == 0 || zahtevekZaPreklic.getSifraOrganizacije().compareTo(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId()) == 0) {
                zahtevekZaPreklicRepoManager.addZahtevekZaPreklic(zahtevekZaPreklic);
                metodeHelper.insertInStatusLogZaPreklic(uporabnik, "00", "01", zahtevekZaPreklic.getId(), statusLogPreklicRepoManager, "Zahtevek kreiran.");
                return new ModelAndView("redirect:/ZahtevekZaPreklic/podrobno/" + zahtevekZaPreklic.getId());
            } else {
                return new ModelAndView("redirect:/index");
            }
        }
        modelAndView.addObject("zahtevekZaPreklic", zahtevekZaPreklic);
        return modelAndView;
    }

    @RequestMapping(value = "/IzpisPodatkovPreklic/{pageNum}")
    public ModelAndView izpisPodatkov(HttpSession session, @PathVariable Integer pageNum) {
        ModelAndView modelAndView = new ModelAndView("izpisPodatkovPreklic");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        List<ZahtevekZaPreklic> zahtevkiZapreklic = zahtevekZaPreklicRepoManager.getZahtevkeZaPreklic(pageNum, uporabnik);
        modelAndView.addObject("zahtevkiZaPreklic", zahtevkiZapreklic);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("izpisCount", (Math.ceil(zahtevekZaPreklicRepoManager.getZahtevkeZaPreklicCount(uporabnik) / (nastavitveHelper.getPrikazovNaStran() * 1.0))));
        modelAndView.addObject("prikazovNaStran", nastavitveHelper.getPrikazovNaStran());
        return modelAndView;
    }

    @RequestMapping(value = "/ZahtevekZaPreklic/podrobno/{idZ}")
    public ModelAndView zahtevekZaPreklicPodrobno(@PathVariable Integer idZ) {
        ModelAndView modelAndView = new ModelAndView("izpisPodrobnoPreklic");
        modelAndView.addObject("zahtevekZaPreklic", zahtevekZaPreklicRepoManager.findById(idZ));
        return modelAndView;
    }

    //Posreduj na SIGOV-CA
    @RequestMapping(value = "/ZahtevekZaPreklic/posreduj/{idZ}")
    public ModelAndView zahtevekZaPreklicPosreduj(@PathVariable Integer idZ, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("izpisPodrobnoPreklic");
        ZahtevekZaPreklic zahtevekZaPreklic = zahtevekZaPreklicRepoManager.findById(idZ);
        if (zahtevekZaPreklic.getStatus().getSifra().compareTo("02") == 0) {
            if (zahtevekZaPreklic.getNatisnjeno() != null && zahtevekZaPreklic.getNatisnjeno()) {
                zahtevekZaPreklic.setStatus(statusRepoManager.getStatus("07"));
                zahtevekZaPreklicRepoManager.updateZahtevekZaPreklic(zahtevekZaPreklic);
                Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
                metodeHelper.insertInStatusLogZaPreklic(uporabnik, "02", "07", zahtevekZaPreklic.getId(), statusLogPreklicRepoManager, "Zahtevek posredovan na SIGOV-CA.");
            } else {
                modelAndView.addObject("error", "Prosim, da natisnite vlogo preden jo posredujete na SIGOV-CA.");
            }

            modelAndView.addObject("zahtevekZaPreklic", zahtevekZaPreklic);
            return modelAndView;
        }
        return new ModelAndView("redirect:/ZahtevekZaPreklic/podrobno/" + idZ);
    }

    @RequestMapping(value = "/ZahtevekZaPreklic/potrdi/{idZ}")
    public ModelAndView zahtevekZaPreklicPotrdi(@PathVariable Integer idZ, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("izpisPodrobnoPreklic");
        ZahtevekZaPreklic zahtevekZaPreklic = zahtevekZaPreklicRepoManager.findById(idZ);
        if (zahtevekZaPreklic.getStatus().getSifra().compareTo("01") == 0) {
            zahtevekZaPreklic.setStatus(statusRepoManager.getStatus("02"));
            zahtevekZaPreklicRepoManager.updateZahtevekZaPreklic(zahtevekZaPreklic);
            Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
            metodeHelper.insertInStatusLogZaPreklic(uporabnik, "01", "02", zahtevekZaPreklic.getId(), statusLogPreklicRepoManager, "Zahtevek potrjen.");
            modelAndView.addObject("zahtevekZaPreklic", zahtevekZaPreklic);
            return modelAndView;
        }
        return new ModelAndView("redirect:/ZahtevekZaPreklic/podrobno/" + idZ);
    }

    @RequestMapping(value = "/ZahtevekZaPreklic/brisi/{idZ}")
    public String zahtevekZaPreklicBrisi(@PathVariable Integer idZ, HttpSession session) {
        ZahtevekZaPreklic zahtevekZaPreklic = zahtevekZaPreklicRepoManager.findById(idZ);
        if (zahtevekZaPreklic.getStatus().getSifra().compareTo("01") == 0) {
            Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
            zahtevekZaPreklic.setStatus(statusRepoManager.getStatus("04"));
            zahtevekZaPreklicRepoManager.updateZahtevekZaPreklic(zahtevekZaPreklic);
            metodeHelper.insertInStatusLogZaPreklic(uporabnik, "01", "04", zahtevekZaPreklic.getId(), statusLogPreklicRepoManager, "Zahtevek izbrisan.");
        }

        return "redirect:/IzpisPodatkovPreklic/1";
    }

    @RequestMapping(value = "/ZahtevekZaPreklic/odkleni/{idZ}")
    public ModelAndView zahtevekZaPreklicOdkleni(@PathVariable Integer idZ, HttpSession session) {
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        ZahtevekZaPreklic zahtevekZaPreklic = zahtevekZaPreklicRepoManager.findById(idZ);
        String statusSifra = zahtevekZaPreklic.getStatus().getSifra();
        if (statusSifra.compareTo("04") == 0 || statusSifra.compareTo("02") == 0 || statusSifra.compareTo("07") == 0) {
            zahtevekZaPreklic.setStatus(statusRepoManager.getStatus("01"));
            zahtevekZaPreklic.setNatisnjeno(false);
            zahtevekZaPreklicRepoManager.updateZahtevekZaPreklic(zahtevekZaPreklic);
            metodeHelper.insertInStatusLogZaPreklic(uporabnik, statusSifra, "01", zahtevekZaPreklic.getId(), statusLogPreklicRepoManager, "Zahtevek odklenjen.");
            ModelAndView modelAndView = new ModelAndView("izpisPodrobnoPreklic");
            modelAndView.addObject("zahtevekZaPreklic", zahtevekZaPreklic);
            return modelAndView;
        }
        return new ModelAndView("redirect:/ZahtevekZaPreklic/podrobno/" + idZ);
    }

    @RequestMapping(value = "/ZahtevekZaPreklic/natisni/{idZ}")
    public void zahtevekZaPreklicNatisni(@PathVariable Integer idZ, HttpServletResponse response) throws Exception {
        ZahtevekZaPreklic zahtevekZaPreklic = zahtevekZaPreklicRepoManager.findById(idZ);
        if (zahtevekZaPreklic.getStatus().getSifra().compareTo("02") == 0) {
            zahtevekZaPreklic.setNatisnjeno(true);
            zahtevekZaPreklicRepoManager.updateZahtevekZaPreklic(zahtevekZaPreklic);
            ModelAndView modelAndView = new ModelAndView("izpisPodrobnoPreklic");
            modelAndView.addObject("zahtevekZaPreklic", zahtevekZaPreklic);
            ByteArrayOutputStream outPutStrem = generateReportHelper.createReportZahtevekZaPreklic(zahtevekZaPreklic);
            metodeHelper.setHeaderPDF(response, zahtevekZaPreklic.getCrtnaKoda(), outPutStrem);
        }
    }

    @RequestMapping(value = "/ZahtevekZaPreklic/zgodovina/{idZ}")
    public ModelAndView zahtevekZaPreklicZgodovina(@PathVariable Integer idZ) {
        ModelAndView modelAndView = new ModelAndView("pregledLogZaPreklic");
        ZahtevekZaPreklic zahtevekZaPreklic = zahtevekZaPreklicRepoManager.findById(idZ);
        List<StatusLogPreklic> statusLog = statusLogPreklicRepoManager.getStatusLogPreklicByZahtevekId(zahtevekZaPreklic.getId());
        modelAndView.addObject("statusLog", statusLog);
        modelAndView.addObject("zahtevek", zahtevekZaPreklic);
        return modelAndView;
    }
}
