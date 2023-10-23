/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.helper.MetodeHelper;
import si.vsrs.cif.managers.ImetnikRepoManager;
import si.vsrs.cif.managers.SeznamCitalcevSigovcaRepoManager;
import si.vsrs.cif.managers.ZahtevekRepoManager;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.SeznamCitalcevSigovca;
import si.vsrs.cif.mod.Zahtevek;

/**
 *
 * @author Uporabnik
 */
@Controller
public class DodelovanjeOpremeController {

    @Autowired
    SeznamCitalcevSigovcaRepoManager seznamCitalcevSigovcaRepoManager;
    @Autowired
    ImetnikRepoManager imetnikRepoManager;
    @Autowired
    ZahtevekRepoManager zahtevekRepoManager;
    @Autowired
    MetodeHelper metodeHelper;

    @RequestMapping(value = "/dodelovanje/citalci")
    public ModelAndView dodeljevanjeCitalci() {
        ModelAndView modelAndView = new ModelAndView("dodeliCitalec");
        return modelAndView;
    }

    @RequestMapping(value = "/dodelovanje/citalci/iskanje")
    public ModelAndView dodeljevanjeCitalciIskanje(@RequestParam(value = "iskanoC") String iskanoC, @RequestParam(value = "iskanoI") String iskanoI) {
        List<SeznamCitalcevSigovca> citalci = new ArrayList();
        List<Imetnik> imetniki = new ArrayList();

        if (!iskanoC.isEmpty()) {
            citalci = seznamCitalcevSigovcaRepoManager.findCitalecSigovcaZaDodeljevanje(iskanoC);
        }
        if (!iskanoI.isEmpty()) {
            List<Imetnik> temp = imetnikRepoManager.getImetnikeSearchAdmin(iskanoI);
            for (Imetnik i : temp) {
                if (metodeHelper.imetnikStatus(i, "03", "05", "07")) {
                    imetniki.add(i);
                }
            }
            //imetniki = imetnikRepoManager.getImetnikeSearchAdmin(iskanoI);
        }
        ModelAndView modelAndView = new ModelAndView("dodeliCitalec");
        modelAndView.addObject("imetniki", imetniki);
        modelAndView.addObject("citalci", citalci);
        modelAndView.addObject("iskalniNizC", iskanoC);
        modelAndView.addObject("iskalniNizI", iskanoI);
        String msg = "";
        if (imetniki.isEmpty()) {
            msg += "\nNi najdenih imetnikov.";
        }
        if (citalci.isEmpty()) {
            msg += "\nNi najdenih citalcev.";
        }
        modelAndView.addObject("info", msg);
        return modelAndView;
    }

    @RequestMapping(value = "/dodelovanje/citalci/dodeli")
    public ModelAndView dodeljevanjeCitalciDodeli(@RequestParam(value = "idCitalci") String idCitalci, @RequestParam(value = "idImetniki") String idImetniki) {
        ModelAndView modelAndView = new ModelAndView("dodeliCitalec");
        if (idCitalci.isEmpty() || idImetniki.isEmpty()) {
            modelAndView.addObject("info", "Imetnik ali čitalec ni izbran.");
            return modelAndView;
        }
        SeznamCitalcevSigovca citalec = seznamCitalcevSigovcaRepoManager.findById(Long.parseLong(idCitalci));
        SeznamCitalcevSigovca citalecNew = new SeznamCitalcevSigovca();
        Imetnik imetnik = imetnikRepoManager.getImetnik(Long.parseLong(idImetniki));
        citalecNew.setImetnik(imetnik);
        citalecNew.setDatumIzdaje(new Date());
        citalecNew.setImeInPriimek(imetnik.getIme() + " " + imetnik.getPriimek());
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(zahtevekRepoManager.getZahtevekIDFromImetnikID(imetnik.getId()));
        citalecNew.setImeOrganizacije(zahtevek.getImeOrganizacije());
        citalecNew.setOznaka(citalec.getOznaka());
        citalecNew.setTip(citalec.getTip());
        citalecNew.setSifraSodisca(zahtevek.getSifraOrganizacije());
        seznamCitalcevSigovcaRepoManager.add(citalecNew);

        modelAndView.addObject("info", "Čitalec uspešno dodeljen.");
        return modelAndView;
    }
}
