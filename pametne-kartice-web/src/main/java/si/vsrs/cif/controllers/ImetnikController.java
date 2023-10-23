/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.controllers;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.helper.GenerateReportHelper;
import si.vsrs.cif.helper.MetodeHelper;
import si.vsrs.cif.helper.NastavitveHelper;
import si.vsrs.cif.managers.CertifikatRepoManager;
import si.vsrs.cif.managers.ImetnikRepoManager;
import si.vsrs.cif.managers.KarticaRepoManager;
import si.vsrs.cif.managers.SeznamCitalcevSigovcaRepoManager;
import si.vsrs.cif.managers.SeznamKarticSigovcaRepoManager;
import si.vsrs.cif.managers.StatusCertifikatRepoManager;
import si.vsrs.cif.managers.StatusImetnikRepoManager;
import si.vsrs.cif.managers.StatusLogImetnikRepoManager;
import si.vsrs.cif.managers.ZahtevekRepoManager;
import si.vsrs.cif.managers.ZahtevekZaPrenosRepoManager;
import si.vsrs.cif.mod.AdminIskanjeStatusImetnik;
import si.vsrs.cif.mod.Certifikat;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.Kartica;
import si.vsrs.cif.mod.Opomba;
import si.vsrs.cif.mod.SeznamCitalcevSigovca;
import si.vsrs.cif.mod.SeznamKarticSigovca;
import si.vsrs.cif.mod.StatusLogImetnik;
import si.vsrs.cif.mod.Zahtevek;
import si.vsrs.cif.mod.ZahtevekZaPrenos;
import si.vsrs.cif.mod.web.Uporabnik;

/**
 *
 * @author uporabnik
 */
@Controller
@PreAuthorize("hasAnyRole('001,002')")
public class ImetnikController {

    @Autowired
    ZahtevekRepoManager zahtevekRepoManager;
    @Autowired
    ImetnikRepoManager imetnikRepoManager;
    @Autowired
    StatusLogImetnikRepoManager statusLogImetnikRepoManager;
    @Autowired
    StatusImetnikRepoManager statusImetnikRepoManager;
    @Autowired
    CertifikatRepoManager certifikatRepoManager;
    @Autowired
    KarticaRepoManager karticaRepoManager;
    @Autowired
    GenerateReportHelper generateReportHelper;
    @Autowired
    StatusCertifikatRepoManager statusCertifikatRepoManager;
    @Autowired
    private LdapTemplate ldapTemplate;
    @Autowired
    NastavitveHelper nastavitveHelper;
    @Autowired
    MetodeHelper metodeHelper;
    @Autowired
    SeznamKarticSigovcaRepoManager seznamKarticSigovcaRepoManager;
    @Autowired
    SeznamCitalcevSigovcaRepoManager seznamCitalcevSigovcaRepoManager;
    @Autowired
    ZahtevekZaPrenosRepoManager zahtevekZaPrenosRepoManager;

    @InitBinder
    private void initBinder(HttpServletRequest request, DataBinder binder) {
        for (String param : request.getParameterMap().keySet()) {
            if (param.endsWith("ime") || param.endsWith("priimek") || param.endsWith("emso") || param.endsWith("davcna")) {
                binder.registerCustomEditor(String.class, param, new CustomTextEditor(false));
            }
            if (param.endsWith("ENaslov")) {
                binder.registerCustomEditor(String.class, param, new CustomTextEditor(true));
            }
        }
    }

    /*Pregled*/
    @RequestMapping(value = "/pregledImetnikov/{pageNum}")
    public ModelAndView pregledImetnikov(HttpSession session, @PathVariable Integer pageNum, @RequestParam(value = "iskano", required = false) String iskano) {
        ModelAndView modelAndView = new ModelAndView("pregledImetnikov");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        List<Imetnik> imetniki = new ArrayList();
        int izpisCount = 0;
        if (iskano != null && !iskano.isEmpty()) {
            imetniki = imetnikRepoManager.getImetnikeSearchUser(iskano, uporabnik, pageNum);
            izpisCount = imetnikRepoManager.countImetnikeSearchUser(iskano, uporabnik);
        } else {
            imetniki = imetnikRepoManager.getImetnike(pageNum, uporabnik);
            izpisCount = imetnikRepoManager.getImetnikCount(uporabnik);
        }
        modelAndView.addObject("imetniki", imetniki);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("izpisCount", (Math.ceil(izpisCount / (nastavitveHelper.getPrikazovNaStran() * 1.0))));
        modelAndView.addObject("prikazovNaStran", nastavitveHelper.getPrikazovNaStran());
        modelAndView.addObject("iskano", iskano);
        return modelAndView;
    }

    @RequestMapping(value = "/pregledImetnikovPodrobno/{idI}")
    public ModelAndView izpisPodatkovPodrobno(HttpSession session, @PathVariable Integer idI) {
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        String rola = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        Imetnik imetnik = imetnikRepoManager.getImetnik(idI);
        if (metodeHelper.lahkoDostopaDoStrani(rola, imetnik, new String[]{"04", "10"}, new String[]{"00"})) {
            return new ModelAndView("redirect:/index");
        }
        String sifraOrganizacije;
        Long zahtevekId;
        if (imetnik.getPrenesenImetnik() != null && imetnik.getPrenesenImetnik()) {
            zahtevekId = zahtevekZaPrenosRepoManager.getZahtevekZaPrenosIdsByImetnikId(idI);
            sifraOrganizacije = zahtevekZaPrenosRepoManager.getSifraOrganizacijeById(zahtevekId);
        } else {
            zahtevekId = zahtevekRepoManager.getZahtevekIDFromImetnikID(idI);
            sifraOrganizacije = zahtevekRepoManager.getSifraOrganizacijeById(zahtevekId);
        }


        ModelAndView modelAndView = new ModelAndView("pregledImetnikovPodrobno");
        List<Certifikat> certifikati = certifikatRepoManager.findByENaslovIgnoreCaseAndKarticaIsNull(imetnik.getENaslov());
        certifikati = metodeHelper.updateCertifikatStatus(certifikati, statusCertifikatRepoManager, certifikatRepoManager);
        List<Kartica> kartice = karticaRepoManager.getKarticaByImetnikID(idI);
        List<SeznamKarticSigovca> seznamKarticSigovca = seznamKarticSigovcaRepoManager.findBySifraSodiscaAndDatumVrnitveIsNullAndImeInPriimekIgnoreCase(sifraOrganizacije, imetnik.getIme() + " " + imetnik.getPriimek());
        List<SeznamCitalcevSigovca> seznamCitalcevSigovca = seznamCitalcevSigovcaRepoManager.findBySifraSodiscaAndDatumVrnitveIsNullAndImeInPriimekIgnoreCase(sifraOrganizacije, imetnik.getIme() + " " + imetnik.getPriimek());

        if (rola.compareTo("002") == 0 || rola.compareTo("006") == 0) {
            if (certifikatRepoManager.obstajajoNeVrnjeneKartice(imetnik.getDavcna(), imetnik.getENaslov())) {
                imetnik.setImaKarticoVSRSBrezCertf("DA");
            }
        }


        modelAndView.addObject("imetnik", imetnik);
        modelAndView.addObject("certifikati", certifikati);
        modelAndView.addObject("kartice", kartice);
        modelAndView.addObject("opomba", new Opomba());
        modelAndView.addObject("zahtevekID", zahtevekId);
        modelAndView.addObject("seznamKarticSigovca", seznamKarticSigovca);
        modelAndView.addObject("seznamCitalcevSigovca", seznamCitalcevSigovca);


        return modelAndView;
    }

    @RequestMapping(value = "/AdminConfirmIskanjeImetnik", method = RequestMethod.POST)
    public ModelAndView adminConfirmIskanje(HttpSession session, @ModelAttribute AdminIskanjeStatusImetnik status, @RequestParam(value = "iskano", required = false) String iskano) {
        session.setAttribute("AdminIskanjeStatusImetnik", status);
        return pregledImetnikovAdmin(session, status, 1, iskano);
    }

    @RequestMapping(value = "/pregledImetnikovAdmin/{pageNum}")
    public ModelAndView pregledImetnikovAdmin(HttpSession session, @ModelAttribute AdminIskanjeStatusImetnik status, @PathVariable Integer pageNum, @RequestParam(value = "iskano", required = false) String iskano) {
        ModelAndView modelAndView = new ModelAndView("pregledImetnikov");
        if (session.getAttribute("AdminIskanjeStatusImetnik") == null) {
            session.setAttribute("AdminIskanjeStatusImetnik", status);
        } else {
            status = (AdminIskanjeStatusImetnik) session.getAttribute("AdminIskanjeStatusImetnik");
        }

        int izpisCount = 0;
        List<Imetnik> imetniki;
        if (iskano != null && !iskano.isEmpty()) {
            imetniki = imetnikRepoManager.getImetnikeSearchAdminPages(iskano, pageNum, status);
            izpisCount = imetnikRepoManager.countImetnikeSearchAdmin(iskano, status);
        } else {
            imetniki = imetnikRepoManager.getImetnikeAdmin(pageNum, status);
            izpisCount = imetnikRepoManager.getImetnikeCountAdmin(status);
        }
        modelAndView.addObject("imetniki", imetniki);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("izpisCount", (Math.ceil(izpisCount / (nastavitveHelper.getPrikazovNaStran() * 1.0))));
        modelAndView.addObject("prikazovNaStran", nastavitveHelper.getPrikazovNaStran());
        modelAndView.addObject("AdminIskanjeStatusImetnik", status);
        modelAndView.addObject("iskano", iskano);
        return modelAndView;
    }

    /*Dodajanje, urejanje*/
    @RequestMapping(value = "/Vloga/imetnik/dodaj/{idZ}")
    public ModelAndView addImetnik(HttpSession session, @PathVariable Integer idZ) {
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        String vloga = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(idZ);
        if (vloga.compareTo("001") == 0 && !metodeHelper.zahtevekStatus(zahtevek, "01")) {
            return new ModelAndView("redirect:/index");
        }
        if (vloga.compareTo("001") != 0 && !metodeHelper.zahtevekStatus(zahtevek, "01", "02", "03")) {
            return new ModelAndView("redirect:/index");
        }
        ModelAndView modelAndView = new ModelAndView("dodajImetnik");
        modelAndView.addObject("imetnik", new Imetnik());
        modelAndView.addObject("idZ", idZ);
        return modelAndView;
    }

    @RequestMapping(value = "/Vloga/imetnik/uredi/{idZ}/{idI}")
    public ModelAndView editImetnik(HttpSession session, @PathVariable Integer idZ, @PathVariable Integer idI) {
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        String vloga = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        if (vloga.compareTo("001") == 0 && !metodeHelper.zahtevekStatus(zahtevekRepoManager.getZahtevek(idZ), "01")) {
            return new ModelAndView("redirect:/index");
        }
        if (vloga.compareTo("001") != 0 && !metodeHelper.zahtevekStatus(zahtevekRepoManager.getZahtevek(idZ), "01", "02", "03")) {
            return new ModelAndView("redirect:/index");
        }

        ModelAndView modelAndView = new ModelAndView("dodajImetnik");
        Imetnik imetnik = imetnikRepoManager.getImetnik(idI);
        modelAndView.addObject("imetnik", imetnik);
        modelAndView.addObject("idZ", idZ);

        return modelAndView;
    }

    @RequestMapping(value = "/Vloga/imetnik/process/{idZ}")
    public ModelAndView addImetnikProcess(@Valid @ModelAttribute Imetnik imetnik, BindingResult bindingResult, HttpSession session, @PathVariable Integer idZ) {
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        String vloga = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(idZ);
        if (!metodeHelper.lahkoDostopaDoStrani(vloga, zahtevek, new String[]{"01"}, new String[]{"01", "02", "03"})) {
            return new ModelAndView("redirect:/index");
        }
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("dodajImetnik");
            modelAndView.addObject("imetnik", imetnik);
            modelAndView.addObject("idZ", idZ);
            return modelAndView;
        }
        boolean lahkoDoda = true;
        //Ce e-naslov obstaja v LDAP-u
        if (!metodeHelper.obstajaVLDAP(imetnik.getENaslov(), ldapTemplate)) {
            lahkoDoda = false;
            bindingResult.rejectValue("eNaslov", null, "Elektronski naslov ne obstaja v imeniku osebja (LDAP).");
        }

        //Ce ze ima certifikat       
        if (!metodeHelper.zeImaCertifikat(certifikatRepoManager.findByEMail(imetnik.getENaslov()), nastavitveHelper.getKontrolaCasDoPotekaCertifikata() * 24 * 60 * 60 * 1000)) {
            lahkoDoda = false;
            bindingResult.rejectValue("eNaslov", null, "Imetnik s tem elektronskim naslovom že ima veljaven certifikat. Zahtevo za nov certifikat lahko oddate " + nastavitveHelper.getKontrolaCasDoPotekaCertifikata() + " dni pred potekom certifikata.");
        }

        //Preveri ali imetnik s tem e-naslovom ze obstaja
        if (metodeHelper.imetnikZeObstaja(imetnikRepoManager.getImetnikByENaslov(imetnik.getENaslov()), imetnik)) {
            lahkoDoda = false;
            bindingResult.rejectValue("eNaslov", null, "Imetnik s tem elektronskim naslovom ima že oddano aktivno vlogo in ni končana.");

        }
        //Preverjanje EMSO po mod 11
        if (imetnik.getEmso() != null && !imetnik.getEmso().isEmpty() && !metodeHelper.checkEMSOMOD11(imetnik.getEmso())) {
            lahkoDoda = false;
            bindingResult.rejectValue("emso", null, "EMŠO ni pravilne oblike.");
        }
        /*Preverjanje, ce imetnik ima citalec in kartico SIGOV-CA*/
        /*--------------------------------*/

        String sifraSodisca = uporabnik.getIzbranaVlogaSodisce().getSodisce().getId();

        if (imetnik.getImaCitalec().compareTo("NE") == 0) {
            List<SeznamCitalcevSigovca> citalci = seznamCitalcevSigovcaRepoManager.findBySifraSodiscaAndDatumVrnitveIsNullAndImeInPriimekIgnoreCase(sifraSodisca, imetnik.getIme() + " " + imetnik.getPriimek());
            if (citalci != null && citalci.size() > 0) {
                lahkoDoda = false;
                bindingResult.rejectValue("imaCitalec", null, "Označili ste, da ne potrebujete čitalca SIGOV-CA, vendar je zabeleženo, da imate v lasti čitalec SIGOV-CA. Prosim ustrezno izberite podatke o opremi.");
            }
        }

        if (imetnik.getImaPametnoKartico() == null || imetnik.getImaPametnoKartico().compareTo("NE") == 0) {
            List<SeznamKarticSigovca> kartice = seznamKarticSigovcaRepoManager.findBySifraSodiscaAndDatumVrnitveIsNullAndImeInPriimekIgnoreCase(sifraSodisca, imetnik.getIme() + " " + imetnik.getPriimek());
            if (kartice != null && kartice.size() > 0) {
                lahkoDoda = false;
                bindingResult.rejectValue("imaPametnoKartico", null, "Niste označili, da imate v lasti pametno kartico SIGOV-CA, vendar je zabeleženo, da imate v lasti pametno kartico SIGOV-CA. Prosim ustrezno izberite podatke o opremi.");
            }
        }

        /*--------------------------------*/
        if (!lahkoDoda) {
            ModelAndView modelAndView = new ModelAndView("dodajImetnik");
            modelAndView.addObject("imetnik", imetnik);
            modelAndView.addObject("idZ", idZ);
            return modelAndView;
        }

        if (imetnik.getImaPametnoKartico() == null) {
            imetnik.setImaPametnoKartico("NE");
        }
        if (imetnik.getImaPametnoKarticoVSRS() == null) {
            imetnik.setImaPametnoKarticoVSRS("NE");
        }
        String statusPred = "00";
        String opis = "Imetnik kreiran";
        String novStatus = "01";
        if (imetnik.getId() != null) {
            Imetnik imetnikTemp1 = imetnikRepoManager.getImetnik(imetnik.getId());
            novStatus = imetnikTemp1.getStatus().getSifraSIm();
            statusPred = imetnikTemp1.getStatus().getSifraSIm();
            opis = "Imetnik popravljen";
            imetnik.setStatus(statusImetnikRepoManager.getStatusImetnik(novStatus));
        } else {
            imetnik.setStatus(statusImetnikRepoManager.getStatusImetnik("01"));
        }
        imetnikRepoManager.addImetnik(imetnik, idZ);
        metodeHelper.insertInStatusLogImetnik(uporabnik, statusPred, novStatus, imetnik.getId(), statusLogImetnikRepoManager, opis);

        session.setAttribute("info", "Imetnik shranjen");
        return new ModelAndView("redirect:/Vloga/uredi/" + idZ);
    }

    @RequestMapping(value = "/Vloga/brisiImetnik/{idZahtevek}/{idImetnik}")
    public ModelAndView deleteImetnik(@PathVariable long idZahtevek, HttpSession session, @PathVariable long idImetnik) {
        session.setAttribute("info", "");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        String rola = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        if (!metodeHelper.lahkoDostopaDoStrani(rola, zahtevekRepoManager.getZahtevek(idZahtevek), new String[]{"01"}, new String[]{"01", "02", "03"})) {
            return new ModelAndView("redirect:/index");
        }
        String st = imetnikRepoManager.getImetnik(idImetnik).getStatus().getSifraSIm();
        metodeHelper.insertInStatusLogImetnik(uporabnik, st, "04", idImetnik, statusLogImetnikRepoManager, "Imetnik izbrisan");
        imetnikRepoManager.deleteImetnik(idImetnik);
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(idZahtevek);
        ModelAndView modelAndView = new ModelAndView("dodajZahtevek");
        zahtevek = metodeHelper.updateDeletedImetniki(zahtevek);
        modelAndView.addObject("zahtevek", zahtevek);
        return modelAndView;
    }

    //Tiskanje
    @RequestMapping(value = "/printImetnike/{id}", method = RequestMethod.GET)
    public void printImetnike(HttpSession session, HttpServletResponse response, @PathVariable long id) throws Exception {
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(id);
        if (metodeHelper.zahtevekStatus(zahtevek, "02")) {
            zahtevek.setImetniki(metodeHelper.spremeniImetnikiNatisnjeno(zahtevek.getImetniki(), true));
            zahtevekRepoManager.updateZahtevek(zahtevek);
            zahtevek = metodeHelper.updateDeletedImetniki(zahtevek);
            List<Imetnik> imetniki = zahtevek.getImetniki();
            ByteArrayOutputStream outPutStrem = generateReportHelper.createReportImetnik(imetniki);
            metodeHelper.setHeaderPDF(response, zahtevek.getCrtnaKoda(), outPutStrem);
        }
    }

    //Izpis enega imetnika
    @RequestMapping(value = "/Vloga/printImetnik/{idZ}/{idI}")
    public void printImetnik(HttpSession session, @PathVariable long idZ, HttpServletResponse response, @PathVariable long idI) throws Exception {
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(idZ);
        if (metodeHelper.zahtevekStatus(zahtevek, "02")) {
            zahtevek.setImetniki(metodeHelper.spremeniImetnikNatisnjeno(zahtevek.getImetniki(), idI, true));
            zahtevekRepoManager.updateZahtevek(zahtevek);
            List<Imetnik> imetniki = new ArrayList();
            imetniki.add(imetnikRepoManager.getImetnik(idI));
            ByteArrayOutputStream outPutStrem = generateReportHelper.createReportImetnik(imetniki);
            metodeHelper.setHeaderPDF(response, zahtevek.getCrtnaKoda(), outPutStrem);
        }
    }

    @RequestMapping(value = "/getZadnjaSpremembaImetnik", produces = "application/json")
    @ResponseBody
    public StatusLogImetnik getZadnjaSprememba(@RequestParam("idImetnik") Long idImetnika) {
        return statusLogImetnikRepoManager.getLastChangeByImetnik(idImetnika);
    }
}
