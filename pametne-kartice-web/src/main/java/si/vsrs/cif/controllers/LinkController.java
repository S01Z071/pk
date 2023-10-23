/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.helper.MetodeHelper;
import si.vsrs.cif.managers.ZahtevekRepoManager;
import si.vsrs.cif.mod.Opomba;
import si.vsrs.cif.mod.Zahtevek;
import si.vsrs.cif.mod.web.Uporabnik;

/**
 *
 * @author uporabnik
 */
@Controller
@PreAuthorize("denyAll")
public class LinkController {

    @Autowired
    ZahtevekRepoManager zahtevekRepoManager;
    @Autowired
    MetodeHelper metodeHelper;

    @PreAuthorize("permitAll")
    @RequestMapping(value = "/")
    public String mainPage() {
        return "redirect:index";
    }

    @PreAuthorize("permitAll")
    @RequestMapping(value = "/index")
    public ModelAndView mainPageIndex(HttpSession session) {
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        if (uporabnik == null) {
            return new ModelAndView("/login");
        }
        ModelAndView modelAndView = new ModelAndView("index");
        List<Zahtevek> zahtevki = zahtevekRepoManager.getZahtevkeOpomba(uporabnik);
        List<Opomba> opombe = new ArrayList<>();

        for (int i = 0; i < zahtevki.size(); i++) {
            for (Opomba op : zahtevki.get(i).getOpombe()) {
                if (!opombe.contains(op)) {
                    op.setId(zahtevki.get(i).getId());
                    opombe.add(op);
                }
            }
        }
        opombe = metodeHelper.sortOpombe(opombe);
        modelAndView.addObject("opombeSt", zahtevki.size());
        modelAndView.addObject("opombe", opombe);        
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/indexRedirect/{idZ}")
    public String mainPageIndex1(@PathVariable Integer idZ) {
        return "redirect:/izpisPodrobno/" + idZ;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/indexRedirect")
    public String mainPageIndex2() {
        return "redirect:/index";
    }

   
}
