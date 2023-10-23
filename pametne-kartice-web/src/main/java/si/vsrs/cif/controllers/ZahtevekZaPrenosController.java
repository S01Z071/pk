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
import si.vsrs.cif.managers.ImetnikRepoManager;
import si.vsrs.cif.managers.KarticaRepoManager;
import si.vsrs.cif.managers.StatusImetnikRepoManager;
import si.vsrs.cif.managers.StatusLogImetnikRepoManager;
import si.vsrs.cif.managers.StatusLogPrenosRepoManager;
import si.vsrs.cif.managers.StatusRepoManager;
import si.vsrs.cif.managers.ZahtevekRepoManager;
import si.vsrs.cif.managers.ZahtevekTempRepoManager;
import si.vsrs.cif.managers.ZahtevekZaPrenosRepoManager;
import si.vsrs.cif.mod.Certifikat;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.Kartica;
import si.vsrs.cif.mod.StatusLogPrenos;
import si.vsrs.cif.mod.ZahtevekTemp;
import si.vsrs.cif.mod.ZahtevekZaPrenos;
import si.vsrs.cif.mod.web.Uporabnik;

/**
 *
 * @author Uporabnik
 */
@Controller
public class ZahtevekZaPrenosController {

    @Autowired
    CertifikatRepoManager certifikatRepoManager;
    @Autowired
    ZahtevekRepoManager zahtevekRepoManager;
    @Autowired
    ZahtevekTempRepoManager zahtevekTempRepoManager;
    @Autowired
    ZahtevekZaPrenosRepoManager zahtevekZaPrenosRepoManager;
    @Autowired
    MetodeHelper metodeHelper;
    @Autowired
    StatusLogPrenosRepoManager statusLogPrenosRepoManager;
    @Autowired
    NastavitveHelper nastavitveHelper;
    @Autowired
    StatusRepoManager statusRepoManager;
    @Autowired
    GenerateReportHelper generateReportHelper;
    @Autowired
    ImetnikRepoManager imetnikRepoManager;
    @Autowired
    StatusLogImetnikRepoManager statusLogImetnikRepoManager;
    @Autowired
    StatusImetnikRepoManager statusImetnikRepoManager;
    @Autowired
    KarticaRepoManager karticaRepoManager;
   
    
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

    @RequestMapping(value = "/zahtevekZaPrenos")
    public ModelAndView zahtevekZaPrenos() {
        return new ModelAndView("dodajZahtevekZaPrenos");       
    }

    @RequestMapping(value = "/zahtevekZaPrenos/iskanje", produces = "text/plain;charset=UTF-8")
    public ModelAndView zahtevekZaPrenosIskanje(@RequestParam("iskano") String iskano, HttpSession session) {
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");

        Certifikat certifikat = certifikatRepoManager.getCertifikatZaPrenos(iskano);
        ModelAndView modelAndView = new ModelAndView("dodajZahtevekZaPrenos");
        if (certifikat == null || certifikat.getId() == null) {
            modelAndView.addObject("error", "Ni najdenih certifikatov.");
            return modelAndView;
        }

        Imetnik imetnik = certifikat.getImetnik();
        if (imetnik.getStatus().getSifraSIm().compareTo("09") != 0) {
            modelAndView.addObject("error", "Imetnik mora biti v statusu 'Pametna kartica prevzeta'");
            return modelAndView;
        }
        String sodisceID = uporabnik.getIzbranaVlogaSodisce().getSodisce().getId();
        
        if(sodisceID.equalsIgnoreCase(imetnik.getSifraOrganizacije())){
            modelAndView.addObject("error", "Imetnik je že vnešen na tem sodišču ("+sodisceID+")");
            return modelAndView;
        }
        
        Long zahtevekId;
        Long zahtevekPrenosId;
        if (imetnik.getPrenesenImetnik() != null && imetnik.getPrenesenImetnik()) {
            zahtevekPrenosId = zahtevekZaPrenosRepoManager.getZahtevekZaPrenosIdsByImetnikId(imetnik.getId());
            zahtevekId = null;
        } else {
            zahtevekId = zahtevekRepoManager.getZahtevekIDFromImetnikID(imetnik.getId());
            zahtevekPrenosId = null;
        }

        

        ZahtevekTemp zahtevekTemp = zahtevekTempRepoManager.getZahtevekTemp(sodisceID);

        ZahtevekZaPrenos zahtevekZaPrenos = new ZahtevekZaPrenos(certifikat, imetnik, zahtevekTemp, zahtevekId, zahtevekPrenosId);
        if (zahtevekZaPrenos.getSifraOrganizacije() == null || zahtevekZaPrenos.getSifraOrganizacije().isEmpty()) {
            zahtevekZaPrenos.setSifraOrganizacije(sodisceID);
        }
        modelAndView.addObject("zahtevekZaPrenos", zahtevekZaPrenos);
        return modelAndView;
    }

    @RequestMapping(value = "/zahtevekZaPrenos/dodaj/process")
    public ModelAndView zahtevekZaPrenosDodajProcess(@Valid @ModelAttribute ZahtevekZaPrenos zahtevekZaPrenos, BindingResult bindingResult, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("dodajZahtevekZaPrenos");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("zahtevekZaPrenos", zahtevekZaPrenos);
            return modelAndView;
        }

        if (zahtevekZaPrenosRepoManager.zahtevekExistsAndNotCompleted(zahtevekZaPrenos.getSerijskaStevilka())) {
            modelAndView.addObject("info", "Zahtevek za to serijsko številko certifikata je že v pripravi. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov.");
        } else {
            Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
            if (uporabnik.getIzbranaVlogaSodisce().getVloga().getId().compareTo("002") == 0 || zahtevekZaPrenos.getSifraOrganizacije().compareTo(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId()) == 0) {
                zahtevekZaPrenosRepoManager.addZahtevekZaPrenos(zahtevekZaPrenos);
                metodeHelper.insertInStatusLogZaPrenos(uporabnik, "00", "01", zahtevekZaPrenos.getId(), statusLogPrenosRepoManager, "Zahtevek kreiran.");
                ZahtevekTemp zahtevekTemp = new ZahtevekTemp();
                zahtevekTemp.setData(zahtevekZaPrenos);
                zahtevekTempRepoManager.addZahtevekTemp(zahtevekTemp);
                return new ModelAndView("redirect:/zahtevekZaPrenos/podrobno/" + zahtevekZaPrenos.getId());
            } else {
                return new ModelAndView("redirect:/index");
            }
        }
        modelAndView.addObject("zahtevekZaPrenos", zahtevekZaPrenos);
        return modelAndView;
    }

    @RequestMapping(value = "/izpisPodatkovPrenos/{pageNum}")
    public ModelAndView izpisPodatkov(HttpSession session, @PathVariable Integer pageNum) {
        ModelAndView modelAndView = new ModelAndView("izpisPodatkovPrenos");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        List<ZahtevekZaPrenos> zahtevkiZaPrenos = zahtevekZaPrenosRepoManager.getZahtevkeZaPrenos(pageNum, uporabnik);
        modelAndView.addObject("zahtevkiZaPrenos", zahtevkiZaPrenos);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("izpisCount", (Math.ceil(zahtevekZaPrenosRepoManager.getZahtevkeZaPrenosCount(uporabnik) / (nastavitveHelper.getPrikazovNaStran() * 1.0))));
        modelAndView.addObject("prikazovNaStran", nastavitveHelper.getPrikazovNaStran());
        return modelAndView;
    }

    @RequestMapping(value = "/zahtevekZaPrenos/podrobno/{idZ}")
    public ModelAndView zahtevekZaPrenosPodrobno(@PathVariable Integer idZ) {
        ModelAndView modelAndView = new ModelAndView("izpisPodrobnoPrenos");
        modelAndView.addObject("zahtevekZaPrenos", zahtevekZaPrenosRepoManager.findById(idZ));
        return modelAndView;
    }

    @RequestMapping(value = "/zahtevekZaPrenos/posreduj/{idZ}")
    public ModelAndView zahtevekZaPrenosPosreduj(@PathVariable Integer idZ, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("izpisPodrobnoPrenos");
        ZahtevekZaPrenos zahtevekZaPrenos = zahtevekZaPrenosRepoManager.findById(idZ);
        if (zahtevekZaPrenos.getStatus().getSifra().compareTo("02") == 0) {
            if (zahtevekZaPrenos.getNatisnjeno() != null && zahtevekZaPrenos.getNatisnjeno()) {
                Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");

                Imetnik imetnik = imetnikRepoManager.getImetnik(zahtevekZaPrenos.getImetnikID());

                Imetnik imetnikNov = new Imetnik(imetnik);
                imetnikNov.setStatus(statusImetnikRepoManager.getStatusImetnik("09"));
                imetnikNov.setSifraOrganizacije(zahtevekZaPrenos.getSifraOrganizacije());
                imetnikNov.setPrenesenImetnik(true);
                imetnikRepoManager.addImetnikPrenos(imetnikNov);
                metodeHelper.insertInStatusLogImetnik(uporabnik, "00", imetnikNov.getStatus().getSifraSIm(), imetnikNov.getId(), statusLogImetnikRepoManager, "Imetnik prenešen iz drugega sodišča");


                String statusOld = imetnik.getStatus().getSifraSIm();
                imetnik.setStatus(statusImetnikRepoManager.getStatusImetnik("10"));
                imetnikRepoManager.updateImetnik(imetnik);
                metodeHelper.insertInStatusLogImetnik(uporabnik, statusOld, "10", imetnik.getId(), statusLogImetnikRepoManager, "Imetnik prenešen na drugo sodišče");


                zahtevekZaPrenos.setStatus(statusRepoManager.getStatus("07"));
                zahtevekZaPrenos.setImetnik(imetnikNov);
                zahtevekZaPrenosRepoManager.updateZahtevekZaPrenos(zahtevekZaPrenos);

                certifikatRepoManager.updateCertifikatFromZahtevekZaPrenos(zahtevekZaPrenos);
                Kartica kartica = karticaRepoManager.getKarticaByID(zahtevekZaPrenos.getKarticaID());
                kartica.setImetnik(imetnikNov);
                karticaRepoManager.update(kartica);

                metodeHelper.insertInStatusLogZaPrenos(uporabnik, "02", "07", zahtevekZaPrenos.getId(), statusLogPrenosRepoManager, "Zahtevek posredovan na SIGOV-CA.");
            } else {
                modelAndView.addObject("error", "Prosim, da natisnite vlogo preden jo posredujete na SIGOV-CA.");
            }

            modelAndView.addObject("zahtevekZaPrenos", zahtevekZaPrenos);
            return modelAndView;
        }
        return new ModelAndView("redirect:/ZahtevekZaPrenos/podrobno/" + idZ);
    }

    @RequestMapping(value = "/zahtevekZaPrenos/potrdi/{idZ}")
    public ModelAndView zahtevekZaPrenosPotrdi(@PathVariable Integer idZ, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("izpisPodrobnoPrenos");
        ZahtevekZaPrenos zahtevekZaPrenos = zahtevekZaPrenosRepoManager.findById(idZ);
        if (zahtevekZaPrenos.getStatus().getSifra().compareTo("01") == 0) {
            zahtevekZaPrenos.setStatus(statusRepoManager.getStatus("02"));
            zahtevekZaPrenosRepoManager.updateZahtevekZaPrenos(zahtevekZaPrenos);
            Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
            metodeHelper.insertInStatusLogZaPrenos(uporabnik, "01", "02", zahtevekZaPrenos.getId(), statusLogPrenosRepoManager, "Zahtevek potrjen.");
            modelAndView.addObject("zahtevekZaPrenos", zahtevekZaPrenos);
            return modelAndView;
        }
        return new ModelAndView("redirect:/zahtevekZaPrenos/podrobno/" + idZ);
    }

    @RequestMapping(value = "/zahtevekZaPrenos/brisi/{idZ}")
    public String zahtevekZaPrenosBrisi(@PathVariable Integer idZ, HttpSession session) {
        ZahtevekZaPrenos zahtevekZaPrenos = zahtevekZaPrenosRepoManager.findById(idZ);
        if (zahtevekZaPrenos.getStatus().getSifra().compareTo("01") == 0) {
            Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
            zahtevekZaPrenos.setStatus(statusRepoManager.getStatus("04"));
            zahtevekZaPrenosRepoManager.updateZahtevekZaPrenos(zahtevekZaPrenos);
            metodeHelper.insertInStatusLogZaPrenos(uporabnik, "01", "04", zahtevekZaPrenos.getId(), statusLogPrenosRepoManager, "Zahtevek izbrisan.");
        }

        return "redirect:/izpisPodatkovPrenos/1";
    }

    @RequestMapping(value = "/zahtevekZaPrenos/odkleni/{idZ}")
    public ModelAndView zahtevekZaPrenosOdkleni(@PathVariable Integer idZ, HttpSession session) {
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        ZahtevekZaPrenos zahtevekZaPrenos = zahtevekZaPrenosRepoManager.findById(idZ);

        String statusSifra = zahtevekZaPrenos.getStatus().getSifra();
        if (statusSifra.compareTo("04") == 0 || statusSifra.compareTo("02") == 0) {// || (statusSifra.compareTo("07") == 0 && uporabnik.getIzbranaVlogaSodisce().getVloga().getId().compareTo("002") == 0)) {

            /* zahtevekZaPrenos.getImetnik().setStatus(StatusImetnikRepoManager);
             Imetnik imetnik = imetnikRepoManager.getImetnik(zahtevekZaPrenos.getImetnikID());
             imetnik.setStatus(statusImetnikRepoManager.getStatusImetnik("09"));
             imetnik.setPrenesenImetnik(false);
             imetnikRepoManager.updateImetnik(imetnik);
             metodeHelper.insertInStatusLogImetnik(uporabnik, "10", "09", imetnik.getId(), statusLogImetnikRepoManager, "Imetnik odklenjen");
             */

            zahtevekZaPrenos.setStatus(statusRepoManager.getStatus("01"));
            zahtevekZaPrenos.setNatisnjeno(false);
            zahtevekZaPrenosRepoManager.updateZahtevekZaPrenos(zahtevekZaPrenos);
            metodeHelper.insertInStatusLogZaPrenos(uporabnik, statusSifra, "01", zahtevekZaPrenos.getId(), statusLogPrenosRepoManager, "Zahtevek odklenjen.");
            ModelAndView modelAndView = new ModelAndView("izpisPodrobnoPrenos");
            modelAndView.addObject("zahtevekZaPrenos", zahtevekZaPrenos);
            return modelAndView;
        }
        return new ModelAndView("redirect:/zahtevekZaPrenos/podrobno/" + idZ);
    }

    @RequestMapping(value = "/zahtevekZaPrenos/natisni/{idZ}")
    public void zahtevekZaPrenosNatisni(@PathVariable Integer idZ, HttpServletResponse response) throws Exception {
        ZahtevekZaPrenos zahtevekZaPrenos = zahtevekZaPrenosRepoManager.findById(idZ);
        if (zahtevekZaPrenos.getStatus().getSifra().compareTo("02") == 0) {
            zahtevekZaPrenos.setNatisnjeno(true);
            zahtevekZaPrenosRepoManager.updateZahtevekZaPrenos(zahtevekZaPrenos);
            ModelAndView modelAndView = new ModelAndView("izpisPodrobnoPrenos");
            modelAndView.addObject("zahtevekZaPrenos", zahtevekZaPrenos);
            ByteArrayOutputStream outPutStrem = generateReportHelper.createReportZahtevekZaPrenos(zahtevekZaPrenos);
            metodeHelper.setHeaderPDF(response, zahtevekZaPrenos.getCrtnaKoda(), outPutStrem);
        }
    }

    @RequestMapping(value = "/zahtevekZaPrenos/zgodovina/{idZ}")
    public ModelAndView zahtevekZaPrenosZgodovina(@PathVariable Integer idZ) {
        ModelAndView modelAndView = new ModelAndView("pregledLogZaPrenos");
        ZahtevekZaPrenos zahtevekZaPrenos = zahtevekZaPrenosRepoManager.findById(idZ);

        List<StatusLogPrenos> statusLog = statusLogPrenosRepoManager.getStatusLogPrenosByZahtevekId(zahtevekZaPrenos.getId());
        modelAndView.addObject("statusLog", statusLog);
        modelAndView.addObject("zahtevek", zahtevekZaPrenos);
        return modelAndView;
    }
}
