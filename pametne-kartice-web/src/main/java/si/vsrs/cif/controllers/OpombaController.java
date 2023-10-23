/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.controllers;

import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.managers.OpombaRepoManager;
import si.vsrs.cif.managers.ZahtevekRepoManager;
import si.vsrs.cif.mod.Opomba;
import si.vsrs.cif.mod.web.Uporabnik;

/**
 *
 * @author uporabnik
 */
@Controller
@PreAuthorize("hasAnyRole('001,002,005')")
public class OpombaController {

    @Autowired
    OpombaRepoManager opombaRepoManager;
    @Autowired
    ZahtevekRepoManager zahtevekRepoManager;
    
    @RequestMapping(value = "/Vloga/dodajOpombo/{idZ}")
    public ModelAndView addOpombaPage(@PathVariable Integer idZ) {
        if(zahtevekRepoManager.getZahtevek(idZ).getStatus().getSifra().compareTo("04")==0){
            return new ModelAndView("redirect:/index");
        }
        ModelAndView modelAndView = new ModelAndView("dodajOpombo");
        modelAndView.addObject("opomba", new Opomba());
        modelAndView.addObject("idZ", idZ);
        return modelAndView;
    }

    @RequestMapping(value = "/Vloga/dodajOpombo/process/{idZ}")
    public ModelAndView addOpomba(@Valid @ModelAttribute Opomba opomba, BindingResult bindingResult, HttpSession session, @PathVariable Integer idZ) {
        if(opomba.getNaslov() == null || opomba.getVsebina() == null){
            return new ModelAndView("redirect:/index");
        }
        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("dodajOpombo");
            modelAndView.addObject("opomba", opomba);
            modelAndView.addObject("idZ", idZ);
            return modelAndView;
        }        
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        opomba.setUporabnik(uporabnik.getKadrovskStevilka());
        opomba.setDatum(new Date());
        opomba.setUporabnikIme(uporabnik.getIme());
        opomba.setUporabnikPriimek(uporabnik.getPriimek());
        opomba.setUporabnikSodisce(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId());
        opombaRepoManager.insertOpomba(opomba, idZ.longValue());
        return new ModelAndView("redirect:/izpisPodrobno/" + idZ);
        
    }
}
