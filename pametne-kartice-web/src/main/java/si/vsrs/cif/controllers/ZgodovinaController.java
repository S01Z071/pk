/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.managers.CertifikatRepoManager;
import si.vsrs.cif.managers.ImetnikRepoManager;
import si.vsrs.cif.managers.KarticaRepoManager;
import si.vsrs.cif.managers.StatusLogImetnikRepoManager;
import si.vsrs.cif.managers.StatusLogPreklicRepoManager;
import si.vsrs.cif.managers.ZahtevekRepoManager;
import si.vsrs.cif.managers.ZahtevekZaKodoRepoManager;
import si.vsrs.cif.managers.ZahtevekZaPreklicRepoManager;
import si.vsrs.cif.managers.ZahtevekZaPrenosRepoManager;
import si.vsrs.cif.mod.Certifikat;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.Kartica;
import si.vsrs.cif.mod.StatusLogImetnik;
import si.vsrs.cif.mod.StatusLogPreklic;
import si.vsrs.cif.mod.Zahtevek;
import si.vsrs.cif.mod.ZahtevekZaKodo;
import si.vsrs.cif.mod.ZahtevekZaPreklic;
import si.vsrs.cif.mod.ZgodovinaImetnik;

/**
 *
 * @author Uporabnik
 */
@Controller
public class ZgodovinaController {

    @Autowired
    ImetnikRepoManager imetnikRepoManager;
    @Autowired
    ZahtevekRepoManager zahtevekRepoManager;
    @Autowired
    KarticaRepoManager karticaRepoManager;
    @Autowired
    CertifikatRepoManager certifikatRepoManager;
    @Autowired
    StatusLogImetnikRepoManager statusLogImetnikRepoManager;
    @Autowired
    ZahtevekZaPreklicRepoManager zahtevekZaPreklicRepoManager;
    @Autowired
    ZahtevekZaKodoRepoManager zahtevekZaKodoRepoManager;
    @Autowired
    ZahtevekZaPrenosRepoManager zahtevekZaPrenosRepoManager;

    @RequestMapping(value = "/zgodovina/imetnik")
    public ModelAndView zgodovinaImetnik() {
        ModelAndView modelAndView = new ModelAndView("zgodovinaImetnik");
        return modelAndView;
    }

    @RequestMapping(value = "/zgodovina/imetnik/pregledZahtevka/{idI}")
    public String zgodovinaImetnikPregledZahtevka(@PathVariable Long idI) {
        boolean isPrenesen = imetnikRepoManager.isImetnikPrenesen(idI);
        if (isPrenesen) {
            Long zahtevekId = zahtevekZaPrenosRepoManager.getZahtevekZaPrenosIdsByImetnikId(idI);
            return "redirect:/zahtevekZaPrenos/podrobno/" + zahtevekId;
        } else {
            Long zahtevekId = zahtevekRepoManager.getZahtevekIDFromImetnikID(idI);
            return "redirect:/izpisPodrobno/" + zahtevekId;
        }
    }

    @RequestMapping(value = "/zgodovina/imetnik/iskanje", produces = "text/plain;charset=UTF-8")
    public ModelAndView zgodovinaImetnikIskanje(@RequestParam String iskano, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("zgodovinaImetnik");

        List<Imetnik> imetniki = imetnikRepoManager.findByEnaslovOrDavcnaSt(iskano, iskano);

        if (imetniki == null || imetniki.isEmpty()) {
            modelAndView.addObject("error", "Ni najdenih imetnikov.");
            return modelAndView;
        }

        List<ZgodovinaImetnik> zgodovinaImetniks = new ArrayList();
        for (Imetnik i : imetniki) {
            ZgodovinaImetnik zgodovinaImetnik = new ZgodovinaImetnik();
            zgodovinaImetnik.setImetnik(i);
            zgodovinaImetnik.setKartice(karticaRepoManager.getKarticaByImetnikID(i.getId()));
            zgodovinaImetnik.setCertifikati(certifikatRepoManager.getCertifikatByImetnikID(i.getId()));
            zgodovinaImetnik.setStatusLogImetniks(statusLogImetnikRepoManager.getStatusLogByImetnikId(i.getId()));
            List<ZahtevekZaPreklic> zahtevekZaPreklics = zahtevekZaPreklicRepoManager.findByImetnikID(i.getId());

            zgodovinaImetnik.setZahtevekZaPreklics(zahtevekZaPreklics);
            List<ZahtevekZaKodo> zahtevekZaKodo = zahtevekZaKodoRepoManager.findByImetnikId(i.getId());
            zgodovinaImetnik.setZahtevekZaKodos(zahtevekZaKodo);

            zgodovinaImetniks.add(zgodovinaImetnik);

        }
        modelAndView.addObject("zgodovinaImetniks", zgodovinaImetniks);
        return modelAndView;
    }

    @RequestMapping(value = "/zgodovina/kartica")
    public ModelAndView zgodovinaKartica() {
        ModelAndView modelAndView = new ModelAndView("zgodovinaKartica");
        return modelAndView;
    }

    @RequestMapping(value = "/zgodovina/kartica/iskanje", produces = "text/plain;charset=UTF-8")
    public ModelAndView zgodovinaKarticaIskanje(@RequestParam String iskano, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("zgodovinaKartica");

        List<Kartica> kartice = karticaRepoManager.findBySerijskaStevilkaKarticeOrBarcode(iskano, iskano);

        if (kartice == null || kartice.isEmpty()) {
            modelAndView.addObject("error", "Ni najdenih imetnikov.");
            return modelAndView;
        }
        modelAndView.addObject("kartice", kartice);
        return modelAndView;
    }
}
