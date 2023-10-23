/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import si.sodisce.skupni.personnel.SCPersonnelRolesForPersonnelRequest;
import si.sodisce.skupni.personnel.SCPersonnelRolesForPersonnelResponse;
import si.sodisce.skupni.personnel.co.Court;
import si.sodisce.skupni.personnel.co.Role;
import si.sodisce.skupni.personnel.co.User;
import si.vsrs.cif.helper.NastavitveHelper;
import si.vsrs.cif.helper.SkupniServisiHelper;
import si.vsrs.cif.mod.web.Uporabnik;
import si.vsrs.cif.mod.web.Vloga;
import si.vsrs.cif.mod.web.VlogaSodisce;
import si.vsrs.cif.web.auth.AuthenticationWrapper;

/**
 *
 * @author andrej
 */
@Controller
@PreAuthorize("denyAll")
public class LoginController {

    @Autowired
    private SkupniServisiHelper skupniServisiHelper;
    @Autowired
    NastavitveHelper nastavitveHelper;
    @Autowired
    Properties okoljeHelper;

    public SkupniServisiHelper getSkupniServisiHelper() {
        return skupniServisiHelper;
    }

    public void setSkupniServisiHelper(SkupniServisiHelper skupniServisiHelper) {
        this.skupniServisiHelper = skupniServisiHelper;
    }

    @PreAuthorize("permitAll")
    @RequestMapping(value = "/login")
    public String login(HttpSession session, Principal principal) {
        boolean sejaZe = true; //Da ni takoj na zacetku sporocila o napaki pri prijavi
        if (session.getAttribute("error") == null || session.getAttribute("error").toString().isEmpty()) {
            sejaZe = false;
        }
        session.setAttribute("error", " ");
        if (principal == null || principal.getName() == null || principal.getName().isEmpty()) {
            if (sejaZe) {
                session.setAttribute("error", "Vnesite pravilno uporabniško ime (kadrovska številka) in geslo.");
            }
            return "/login";
        }


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AuthenticationWrapper)) {
            ArrayList<GrantedAuthority> newlRoles = new ArrayList<GrantedAuthority>();

            ////////          
            SCPersonnelRolesForPersonnelRequest request = new SCPersonnelRolesForPersonnelRequest();
            request.setData(new SCPersonnelRolesForPersonnelRequest.Data());
            request.getData().setApplCode("PAMKAT");
            request.getData().setPersonnelId(principal.getName().toUpperCase());
            request.getData().setOnlyActive(Boolean.TRUE);
            SCPersonnelRolesForPersonnelResponse response = skupniServisiHelper.getWsPersonnel().rolesForPersonnel(request);

            ///////        
            if (response.getSCRControl() == null || response.getSCRControl().getReturnValue() == 3) {
                session.setAttribute("error", "Prišlo je do napake pri preverjanju pooblastil uporabnika.");
                return "/login";
            }

            if (response.getRData() == null || response.getRData().getUser() == null
                    || response.getRData().getUser().getRoles() == null || response.getRData().getUser().getRoles().getRole().isEmpty()) {
                session.setAttribute("error", "Nimate ustreznih pooblastil za vstop v aplikacijo.");
                return "/login";
            }
            //////////////
            ArrayList<VlogaSodisce> vlogaSodisces = new ArrayList<>();
            User user = response.getRData().getUser();
            for (Role role : user.getRoles().getRole()) {
                for (Court court : role.getCourts().getCourt()) {
                    Vloga vloga = new Vloga();
                    si.vsrs.cif.mod.web.Sodisce sodisce = new si.vsrs.cif.mod.web.Sodisce();
                    VlogaSodisce vlogaSodisce = new VlogaSodisce();
                    vlogaSodisce.setSodisce(sodisce);
                    vlogaSodisce.setVloga(vloga);
                    vloga.setId(role.getRoleCode());
                    sodisce.setId(court.getCourtCode());
                    sodisce.setOpis(court.getShortName());
                    vlogaSodisces.add(vlogaSodisce);
                }
            }
            //---------//
            Uporabnik uporabnik = new Uporabnik();
            uporabnik.setKadrovskStevilka(principal.getName());
            uporabnik.setIme(user.getFirstName());//response.getRData().getUser().get(0).getIme());
            uporabnik.setPriimek(user.getLastName());//response.getRData().getUser().get(0).getPriimek());
            uporabnik.setDodeljeneVlogeSodisce(vlogaSodisces);

            //Samo ena rola na enem sodiscu
            if (vlogaSodisces.size() == 1) {
                final String roleID = vlogaSodisces.get(0).getVloga().getId();
                GrantedAuthority g1 = new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return roleID;
                    }
                };
                newlRoles.add(g1);
                createWrapper(newlRoles);

                if (newlRoles.isEmpty()) {
                    session.setAttribute("error", "Nimate ustreznih pooblastil za vstop v aplikacijo.");
                    return "/login";
                }
                uporabnik.setIzbranaVlogaSodisce(vlogaSodisces.get(0));
                session.setAttribute("uporabnik", uporabnik);
                session.setAttribute("error", null);
                return "redirect:/";


            } else { //Vec role ali vec sodisc
                session.setAttribute("uporabnik", uporabnik);
                return "redirect:/loginIzbira";
            }
            ///
        } else {
            session.setAttribute("error", "Vnesite pravilno uporabniško ime (kadrovska številka) in geslo.");
            return "/login";

        }

    }

    /////////IZBIRA
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/loginIzbira")
    public ModelAndView loginIzbira(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("loginIzbira");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        modelAndView.addObject("uporabnik", uporabnik);

        List<String> seznam = new ArrayList<>();
        for (int i = 0; i < uporabnik.getDodeljeneVlogeSodisce().size(); i++) {
            String elem = "";
            elem += uporabnik.getDodeljeneVlogeSodisce().get(i).getVloga().getId() + ":";
            elem += uporabnik.getDodeljeneVlogeSodisce().get(i).getSodisce().getId() + "-";
            elem += uporabnik.getDodeljeneVlogeSodisce().get(i).getSodisce().getOpis();
            seznam.add(elem);
        }

        modelAndView.addObject("seznam", seznam);

        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/loginIzbira/process")
    public String loginIzbiraProcess(HttpSession session, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("index");

        String izbira = request.getParameter("izbiranje");
        //
        //ArrayList<VlogaSodisce> vlogaSodisca = new ArrayList<>();
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        ArrayList<GrantedAuthority> newlRoles = new ArrayList<GrantedAuthority>();

        String[] splitano = izbira.split(":");
        String role = splitano[0];
        String sodisceSifra = splitano[1].split("-")[0];
        String sodisceNaziv = splitano[1].split("-")[1];

        Vloga vloga = new Vloga();
        si.vsrs.cif.mod.web.Sodisce sodisce = new si.vsrs.cif.mod.web.Sodisce();
        VlogaSodisce vlogaSodisce = new VlogaSodisce();
        vlogaSodisce.setSodisce(sodisce);
        vlogaSodisce.setVloga(vloga);
        vloga.setId(role);
        sodisce.setId(sodisceSifra);
        sodisce.setOpis(sodisceNaziv);


        //
        final String roleID = role;
        GrantedAuthority g1 = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return roleID;
            }
        };
        newlRoles.add(g1);
        createWrapper(newlRoles);

        //
        if (newlRoles.isEmpty()) {
            session.setAttribute("error", "Nimate ustreznih pooblastil za vstop v aplikacijo");
            return "/login";
        }
        //
        uporabnik.setIzbranaVlogaSodisce(vlogaSodisce);
        session.setAttribute("uporabnik", uporabnik);
        session.setAttribute("ezkError", null);
        //
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("error");
        session.removeAttribute("uporabnik");
        return "redirect:/static/j_spring_security_logout";
    }

    private void createWrapper(ArrayList<GrantedAuthority> newlRoles) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthenticationWrapper wrapper = new AuthenticationWrapper(auth, newlRoles);
        SecurityContextHolder.getContext().setAuthentication(wrapper);
    }
}
