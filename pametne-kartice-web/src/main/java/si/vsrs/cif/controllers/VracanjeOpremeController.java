/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.helper.MetodeHelper;
import si.vsrs.cif.managers.ImetnikRepoManager;
import si.vsrs.cif.managers.KarticaRepoManager;
import si.vsrs.cif.managers.SeznamCitalcevSigovcaRepoManager;
import si.vsrs.cif.managers.SeznamKarticSigovcaRepoManager;
import si.vsrs.cif.managers.StatusImetnikRepoManager;
import si.vsrs.cif.managers.StatusLogImetnikRepoManager;
import si.vsrs.cif.managers.StatusLogRepoManager;
import si.vsrs.cif.managers.StatusRepoManager;
import si.vsrs.cif.managers.ZahtevekRepoManager;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.Kartica;
import si.vsrs.cif.mod.SeznamCitalcevSigovca;
import si.vsrs.cif.mod.SeznamKarticSigovca;
import si.vsrs.cif.mod.web.Uporabnik;

/**
 *
 * @author uporabnik
 */
@Controller
public class VracanjeOpremeController {

    @Autowired
    SeznamKarticSigovcaRepoManager seznamKarticSigovcaRepoManager;
    @Autowired
    SeznamCitalcevSigovcaRepoManager seznamCitalcevSigovcaRepoManager;
    @Autowired
    KarticaRepoManager karticaRepoManager;
    @Autowired
    MetodeHelper metodeHelper;
    @Autowired
    ZahtevekRepoManager zahtevekRepoManager;
    @Autowired
    StatusRepoManager statusRepoManager;
    @Autowired
    StatusLogRepoManager statusLogRepoManager;
    @Autowired
    StatusLogImetnikRepoManager statusLogImetnikRepoManager;
    @Autowired
    StatusImetnikRepoManager statusImetnikRepoManager;
    @Autowired
    ImetnikRepoManager imetnikRepoManager;

    @RequestMapping(value = "/vracanjeKartCit")
    public ModelAndView vracanjeKartCit() {
        ModelAndView modelAndView = new ModelAndView("vracanjeKartCit");
        return modelAndView;
    }

    @RequestMapping(value = "/vracanjeKartCit/iskanje")
    public @ResponseBody
    ModelAndView vracanjeKartCitIskanje(@RequestParam String oznaka) {
        ModelAndView modelAndView = new ModelAndView("vracanjeKartCit");
        if (oznaka == null || oznaka.isEmpty()) {
            return modelAndView;
        }

        List<SeznamKarticSigovca> kartice = seznamKarticSigovcaRepoManager.findKarticaSigovcaIzdane(oznaka);
        List<SeznamCitalcevSigovca> citalci = seznamCitalcevSigovcaRepoManager.findCitalecSigovcaIzdane(oznaka);
        List<Kartica> karticeVSRS = karticaRepoManager.findKartica(oznaka, true);
        if (kartice.isEmpty() && citalci.isEmpty() && karticeVSRS.isEmpty()) {
            modelAndView.addObject("error", "Ni zadetkov.");
        } else {
            modelAndView.addObject("seznamKarticSigovca", kartice);
            modelAndView.addObject("seznamCitalcevSigovca", citalci);
            modelAndView.addObject("karticeVSRS", karticeVSRS);
            modelAndView.addObject("iskalniNiz", oznaka);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/vracanjeKartCit/process/kartica")
    public @ResponseBody
    ModelAndView vracanjeKartCitfProcessKartica(@RequestParam String datum, @RequestParam Integer idK, @RequestParam String iskalniNiz) {
        ModelAndView modelAndView = new ModelAndView("vracanjeKartCit");
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date datum1 = null;
        //checkDate
        boolean shrani = true;
        if (!metodeHelper.isDateOk(datum)) {
            modelAndView.addObject("error", "Datum je napačne oblike.");
            shrani = false;
        } else {
            try {
                datum1 = df.parse(datum);
            } catch (ParseException ex) {
                modelAndView.addObject("error", "Datum je napačne oblike.");
                shrani = false;
            }
        }
        if (shrani) {
            SeznamKarticSigovca kartica = seznamKarticSigovcaRepoManager.findById(idK);
            kartica.setDatumVrnitve(datum1);
            seznamKarticSigovcaRepoManager.update(kartica);
            modelAndView.addObject("error", "Datum vrnitve kartice shranjen.");
        }

        modelAndView.addObject("seznamKarticSigovca", seznamKarticSigovcaRepoManager.findKarticaSigovcaIzdane(iskalniNiz));
        modelAndView.addObject("seznamCitalcevSigovca", seznamCitalcevSigovcaRepoManager.findCitalecSigovcaIzdane(iskalniNiz));
        modelAndView.addObject("karticeVSRS", karticaRepoManager.findKartica(iskalniNiz, true));
        modelAndView.addObject("iskalniNiz", iskalniNiz);
        return modelAndView;
    }

    @RequestMapping(value = "/vracanjeKartCit/process/citalec")
    public @ResponseBody
    ModelAndView vracanjeKartCitfProcessCitalec(@RequestParam String datum, @RequestParam Integer idK, @RequestParam String iskalniNiz) {
        ModelAndView modelAndView = new ModelAndView("vracanjeKartCit");
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date datum1 = null;
        boolean shrani = true;
        if (!metodeHelper.isDateOk(datum)) {
            modelAndView.addObject("error", "Datum je napačne oblike.");
            shrani = false;
        } else {
            try {
                datum1 = df.parse(datum);
            } catch (ParseException ex) {
                modelAndView.addObject("error", "Datum je napačne oblike.");
                shrani = false;
            }
        }
        if (shrani) {
            SeznamCitalcevSigovca citalec = seznamCitalcevSigovcaRepoManager.findById(idK);
            citalec.setDatumVrnitve(datum1);
            seznamCitalcevSigovcaRepoManager.update(citalec);
            modelAndView.addObject("error", "Datum vrnitve čitalca shranjen.");
        }
        modelAndView.addObject("seznamKarticSigovca", seznamKarticSigovcaRepoManager.findKarticaSigovcaIzdane(iskalniNiz));
        modelAndView.addObject("seznamCitalcevSigovca", seznamCitalcevSigovcaRepoManager.findCitalecSigovcaIzdane(iskalniNiz));
        modelAndView.addObject("karticeVSRS", karticaRepoManager.findKartica(iskalniNiz, true));
        modelAndView.addObject("iskalniNiz", iskalniNiz);
        return modelAndView;
    }

    @RequestMapping(value = "/vracanjeKartCit/process/karticaVSRS")
    public @ResponseBody
    ModelAndView vracanjeKartCitfProcessKarticaVSRS(HttpSession session, @RequestParam String datum, @RequestParam Integer idK, @RequestParam String iskalniNiz) {
        ModelAndView modelAndView = new ModelAndView("vracanjeKartCit");
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date datum1 = null;
        //checkDate
        boolean shrani = true;
        if (!metodeHelper.isDateOk(datum)) {
            modelAndView.addObject("error", "Datum je napačne oblike.");
            shrani = false;
        } else {
            try {
                datum1 = df.parse(datum);
            } catch (ParseException ex) {
                modelAndView.addObject("error", "Datum je napačne oblike.");
                shrani = false;
            }
        }
        if (shrani) {
            Kartica kartica = karticaRepoManager.findById(idK);
            kartica.setDatumVrnitve(datum1);

            Imetnik imetnik = kartica.getImetnik();
            if (imetnik.getStatus().getSifraSIm().compareTo("09") != 0) {
                Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
                metodeHelper.setImetnikDatumPrejemaOpreme(imetnik, statusImetnikRepoManager, imetnikRepoManager, datum1, statusLogImetnikRepoManager, uporabnik, "Pametna kartica vrnjena.");
                metodeHelper.changeStatusZahtevekIfVsiImetnikiPrejetoPotrdiloOPrejetjuKartice(imetnik.getId(), zahtevekRepoManager, statusRepoManager, statusLogRepoManager, uporabnik);
            }
            
            karticaRepoManager.update(kartica);
            modelAndView.addObject("error", "Datum vrnitve kartice shranjen.");
        }
        modelAndView.addObject("seznamKarticSigovca", seznamKarticSigovcaRepoManager.findKarticaSigovcaIzdane(iskalniNiz));
        modelAndView.addObject("seznamCitalcevSigovca", seznamCitalcevSigovcaRepoManager.findCitalecSigovcaIzdane(iskalniNiz));
        modelAndView.addObject("karticeVSRS", karticaRepoManager.findKartica(iskalniNiz, true));
        modelAndView.addObject("iskalniNiz", iskalniNiz);
        return modelAndView;
    }
}
