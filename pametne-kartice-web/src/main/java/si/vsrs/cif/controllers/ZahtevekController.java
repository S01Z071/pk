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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import si.sodisce.skupni.lists.co.Court;
import si.vsrs.cif.helper.GenerateReportHelper;
import si.vsrs.cif.helper.MetodeHelper;
import si.vsrs.cif.helper.NastavitveHelper;
import si.vsrs.cif.helper.SkupniServisiHelper;
import si.vsrs.cif.managers.CertifikatRepoManager;
import si.vsrs.cif.managers.StatusImetnikRepoManager;
import si.vsrs.cif.managers.StatusLogImetnikRepoManager;
import si.vsrs.cif.managers.StatusLogRepoManager;
import si.vsrs.cif.managers.StatusRepoManager;
import si.vsrs.cif.managers.ZahtevekRepoManager;
import si.vsrs.cif.managers.ZahtevekTempRepoManager;
import si.vsrs.cif.managers.ZahtevekZaKodoRepoManager;
import si.vsrs.cif.mod.AdminIskanjeStatus;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.Opomba;
import si.vsrs.cif.mod.Status;
import si.vsrs.cif.mod.StatusLog;
import si.vsrs.cif.mod.Zahtevek;
import si.vsrs.cif.mod.ZahtevekTemp;
import si.vsrs.cif.mod.ZahtevekZaKodo;
import si.vsrs.cif.mod.web.Uporabnik;

/**
 *
 * @author uporabnik
 */
@Controller
public class ZahtevekController {

    @InitBinder
    private void initBinder(HttpServletRequest request, DataBinder binder) {
        for (String param : request.getParameterMap().keySet()) {
            if (param.endsWith("predstojnik.eNaslovP") || param.endsWith("kontaktnaOseba.eNaslovK") || param.endsWith("imetnikEnaslov")) {
                binder.registerCustomEditor(String.class, param, new CustomTextEditor(true));
            } else if (param.endsWith("predstojnik.imeP") || param.endsWith("predstojnik.priimekP") || param.endsWith("kontaktnaOseba.imeK") || param.endsWith("kontaktnaOseba.priimekK")) {
                binder.registerCustomEditor(String.class, param, new CustomTextEditor(false));
            } else {
                binder.registerCustomEditor(String.class, param, new CustomTextEditorZahtevek());

            }
        }
    }
    @Autowired
    ZahtevekRepoManager zahtevekRepoManager;
    @Autowired
    ZahtevekTempRepoManager zahtevekTempRepoManager;
    @Autowired
    StatusLogRepoManager statusLogRepoManager;
    @Autowired
    StatusRepoManager statusRepoManager;
    @Autowired
    StatusLogImetnikRepoManager statusLogImetnikRepoManager;
    @Autowired
    StatusImetnikRepoManager statusImetnikRepoManager;
    @Autowired
    GenerateReportHelper generateReportHelper;
    @Autowired
    private SkupniServisiHelper skupniServisiHelper;
    @Autowired
    NastavitveHelper nastavitveHelper;
    @Autowired
    MetodeHelper metodeHelper;
    @Autowired
    ZahtevekZaKodoRepoManager zahtevekZaKodoRepoManager;
    @Autowired
    CertifikatRepoManager certifikatRepoManager;

    @RequestMapping(value = "/izpisPodatkov/{pageNum}/{pageNumKoda}/{tabS}")
    public ModelAndView izpisPodatkov(HttpSession session, @PathVariable Integer pageNum, @PathVariable Integer pageNumKoda, @PathVariable Integer tabS) {
        ModelAndView modelAndView = new ModelAndView("izpisPodatkov");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        List<Zahtevek> zahtevki = zahtevekRepoManager.getZahtevke(pageNum, uporabnik);
        zahtevki = metodeHelper.updateDeletedImetniki(zahtevki);
        modelAndView.addObject("zahtevki", zahtevki);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("izpisCount", (Math.ceil(zahtevekRepoManager.getZahtevekCount(uporabnik) / (nastavitveHelper.getPrikazovNaStran() * 1.0))));
        modelAndView.addObject("adminIskanjeStatus", new AdminIskanjeStatus());

        List<ZahtevekZaKodo> zahtevekZaKodo = zahtevekZaKodoRepoManager.getZahtevkeZaKodo(pageNumKoda, uporabnik);
        modelAndView.addObject("zahtevekZaKodo", zahtevekZaKodo);
        modelAndView.addObject("pageNumKoda", pageNumKoda);
        modelAndView.addObject("izpisCountKoda", (Math.ceil(zahtevekZaKodoRepoManager.getZahtevkeZaKodoCount(uporabnik) / (nastavitveHelper.getPrikazovNaStran() * 1.0))));

        modelAndView.addObject("prikazovNaStran", nastavitveHelper.getPrikazovNaStran());
        modelAndView.addObject("tabStatus", tabS);

        return modelAndView;
    }

    @RequestMapping(value = "/izpisPodrobno/{idZ}")
    public ModelAndView izpisPodatkovPodrobno(HttpSession session, @PathVariable Integer idZ, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("izpisPodrobno");
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(idZ);
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        String rola = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        if (rola.compareTo("001") == 0 && !metodeHelper.zahtevekStatus(zahtevek, "01", "02", "03", "05", "06", "07", "08")) {
            return new ModelAndView("redirect:/index");
        }
        if (rola.compareTo("001") == 0 || zahtevek.getStatus().getSifra().compareTo("04") != 0) {
            zahtevek = metodeHelper.updateDeletedImetniki(zahtevek);
        }

        zahtevek = metodeHelper.opombeNewLine(zahtevek);
        //Soritranje opomb
        List<Opomba> opombe = metodeHelper.getSortedOpombe(zahtevek.getOpombe());

        if (rola.compareTo("002") == 0 || rola.compareTo("006") == 0) {
            for (Imetnik i : zahtevek.getImetniki()) {
                if (certifikatRepoManager.obstajajoNeVrnjeneKartice(i.getDavcna(), i.getENaslov())) {
                    i.setImaKarticoVSRSBrezCertf("DA");
                }
            }
        }

        modelAndView.addObject("opombe", opombe);
        modelAndView.addObject("zahtevek", zahtevek);
        modelAndView.addObject("opomba", new Opomba());
        session.setAttribute("info", "");
        /*Nastavi url za nazaj v sejo => ne nastavi, ce pride iz strani za dodajanje opombe ali pregleda zgodovine*/
        String referer = request.getHeader("referer");
        if (referer != null && !referer.isEmpty()) {
            String[] refererSplit = referer.split("/");
            boolean flag = false;
            for (int i = 0; i < refererSplit.length; i++) {
                if (refererSplit[i].compareTo("pregledImetnikovPodrobno") == 0 || refererSplit[i].compareTo("izpisPodatkovAdmin") == 0
                        || refererSplit[i].compareTo("izpisPodatkov") == 0 || refererSplit[i].compareTo("iskanje") == 0 || refererSplit[i].compareTo("posredovanjeNaSIG") == 0) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                session.setAttribute("referer", referer);
            }
        }
        //pregledImetnikovPodrobno
        //izpisPodatkovAdmin
        //izpisPodatkov
        //iskanje
        //posredovanjeNaSIG
        /*---*/
        return modelAndView;
    }

    /*--------------------*/
    @RequestMapping(value = "/Vloga/dodaj")
    public ModelAndView addVloga(HttpSession session) {
        session.setAttribute("info", "");
        ModelAndView modelAndView = new ModelAndView("dodajZahtevek");
        Zahtevek zahtevek = new Zahtevek();
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        String sodisceID = uporabnik.getIzbranaVlogaSodisce().getSodisce().getId();
        Court court = skupniServisiHelper.getSodisceData(sodisceID);
        zahtevek.setZahtevekFromSodisce(court);   //Nastavi podatke sodisca
        //Prebere iz tabele in izpolni obrazec s podatki, ki so bili vpisani nazadnje - samo, ce podatki niso ze izpolnjeni iz "setZahtevekFromSodisce"
        zahtevek.setZahtevekFromTemp(zahtevekTempRepoManager.getZahtevekTemp(sodisceID));
        modelAndView.addObject("zahtevek", zahtevek);
        return modelAndView;
    }

    @RequestMapping(value = "/Vloga/uredi/{idZ}")
    public ModelAndView editVloga(HttpSession session, @PathVariable Integer idZ) {
        ModelAndView modelAndView = new ModelAndView("dodajZahtevek");
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(idZ);
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        String vloga = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        if (!metodeHelper.lahkoDostopaDoStrani(vloga, zahtevek, new String[]{"01"}, new String[]{"01", "02", "03"})) {
            return new ModelAndView("redirect:/index");
        }

        zahtevek = metodeHelper.updateDeletedImetniki(zahtevek);
        modelAndView.addObject("zahtevek", zahtevek);
        return modelAndView;
    }

    @RequestMapping(value = "/Vloga/process")
    public ModelAndView addingVloga(@Valid @ModelAttribute Zahtevek zahtevek, BindingResult bindingResult, HttpSession session) {
        session.setAttribute("info", "");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        ModelAndView modelAndView = new ModelAndView("dodajZahtevek");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("zahtevek", zahtevek);
            return modelAndView;
        }
        String statusPred = "00";
        String novStatus = "01";
        String opis = "Zahtevek kreiran";
        if (zahtevek.getId() != null) {
            statusPred = zahtevekRepoManager.getZahtevek(zahtevek.getId()).getStatus().getSifra();
            novStatus = statusPred;
            opis = "Zahtevek popravljen";
            zahtevek.setStatus(statusRepoManager.getStatus(statusPred));
        } else {
            zahtevek.setStatus(statusRepoManager.getStatus("01"));
        }
        zahtevekRepoManager.addZahtevek(zahtevek);
        metodeHelper.insertInStatusLog(uporabnik, statusPred, novStatus, zahtevek.getId(), statusLogRepoManager, opis);

        //Temp zahtevek - shranjevanje podatkov, ki so bli nazadnje vneseni
        ZahtevekTemp zahtevekTemp = new ZahtevekTemp();
        zahtevekTemp.setData(zahtevek);
        zahtevekTempRepoManager.addZahtevekTemp(zahtevekTemp);
        //
        session.setAttribute("info", "Zahtevek je bil shranjen.");
        return new ModelAndView("redirect:/Vloga/uredi/" + zahtevek.getId());
    }

    @RequestMapping(value = "/brisiVlogo/{id}", method = RequestMethod.GET)
    public String deleteVloga(HttpSession session, @PathVariable Integer id) {
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(id);
        String vloga = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        if (!metodeHelper.lahkoDostopaDoStrani(vloga, zahtevek, new String[]{"01"}, new String[]{"01", "02", "03"})) {
            return "redirect:/index";
        }
        String sifraS = zahtevek.getStatus().getSifra();
        Status status = statusRepoManager.getStatus("04");
        zahtevek.setStatus(status);
        for (int i = 0; i < zahtevek.getImetniki().size(); i++) {
            Imetnik imetnik = zahtevek.getImetniki().get(i);
            String st = imetnik.getStatus().getSifraSIm();
            long idImetnik = imetnik.getId();
            metodeHelper.insertInStatusLogImetnik(uporabnik, st, "04", idImetnik, statusLogImetnikRepoManager, "Imetnik izbrisan");
        }
        zahtevek.setImetnikStatus(statusImetnikRepoManager.getStatusImetnik("04"), "");
        zahtevekRepoManager.updateZahtevek(zahtevek);

        metodeHelper.insertInStatusLog(uporabnik, sifraS, "04", id, statusLogRepoManager, "Zahtevek izbrisan");

        if (uporabnik.getIzbranaVlogaSodisce().getVloga().getId().compareTo("002") == 0) {
            return "redirect:/izpisPodatkovAdmin/1/1/1";
        }
        return "redirect:/izpisPodatkov/1/1/1";
    }

    //STATUSI
    @RequestMapping(value = "/potrdiVlogo/{id}")
    public ModelAndView potrdiVlogo(HttpSession session, @PathVariable Integer id) {
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(id);
        if (zahtevek.getStatus().getSifra().compareTo("01") != 0) {
            return new ModelAndView("redirect:/izpisPodrobno/" + id);
        }
        if (zahtevek.getImetniki() != null && !zahtevek.getImetniki().isEmpty()) {
            zahtevek.setStatus(statusRepoManager.getStatus("02"));
            metodeHelper.insertInStatusLog(uporabnik, "01", "02", id, statusLogRepoManager, "Zahtevek zaključen");
            List<Imetnik> imetniki = zahtevek.getImetniki();
            for (int i = 0; i < imetniki.size(); i++) {
                Imetnik imetnik = imetniki.get(i);
                if (imetnik.getStatus().getSifraSIm().compareTo("04") != 0) {
                    String st = imetnik.getStatus().getSifraSIm();
                    long idImetnik = imetnik.getId();
                    metodeHelper.insertInStatusLogImetnik(uporabnik, st, "02", idImetnik, statusLogImetnikRepoManager, "Imetnik zaključen");
                }
            }
            zahtevek.setImetnikStatus(statusImetnikRepoManager.getStatusImetnik("02"), "04");
            zahtevekRepoManager.updateZahtevek(zahtevek);
        } else {
            session.setAttribute("errorMessage", "Vnesite vsaj enega imetnika.");
        }
        return new ModelAndView("redirect:/izpisPodrobno/" + id);
    }

    //Obrazec
    @RequestMapping(value = "/printZahtevek/{id}", method = RequestMethod.GET)
    public void printZahtevek(HttpServletResponse response, @PathVariable Integer id) throws Exception {
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(id);
        if (zahtevek.getStatus().getSifra().compareTo("02") == 0) {
            zahtevek.setNatisnjeno(true);
            zahtevekRepoManager.updateZahtevek(zahtevek);
            zahtevek = metodeHelper.updateDeletedImetniki(zahtevek);
            List<Imetnik> imetniki = zahtevek.getImetniki();
            ByteArrayOutputStream outPutStrem = generateReportHelper.createReportZahtevek(zahtevek, imetniki);
            metodeHelper.setHeaderPDF(response, zahtevek.getCrtnaKoda(), outPutStrem);
        }
    }

    //Odkleni zahtevek
    @RequestMapping(value = "/odkleniZahtevek/{id}", method = RequestMethod.GET)
    public ModelAndView odkleniZahtevek(HttpSession session, @PathVariable Integer id) {
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(id);
        String vloga = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        //Ce je 001 lahko odklene samo, ce je v statusu 02 ali 06     
        if (!metodeHelper.lahkoDostopaDoStrani(vloga, zahtevek, new String[]{"02", "06"}, new String[]{"01", "02", "03"})) {
            return new ModelAndView("redirect:/izpisPodrobno/" + id);
        }
        String st = zahtevek.getStatus().getSifra();
        zahtevek.setNatisnjeno(false);
        zahtevek.setImetniki(metodeHelper.spremeniImetnikiNatisnjeno(zahtevek.getImetniki(), false));
        zahtevek.setStatus(statusRepoManager.getStatus("01"));
        String zahtevekOpis = "Zahtevek odklenjen";
        if (st.compareTo("06") == 0) {
            zahtevekOpis = "Zahtevek odklenjen za dopolnjevanje";
        }
        for (int i = 0; i < zahtevek.getImetniki().size(); i++) {
            Imetnik imetnik = zahtevek.getImetniki().get(i);
            if (imetnik.getStatus().getSifraSIm().compareTo("04") != 0) {
                String stI = imetnik.getStatus().getSifraSIm();
                long idImetnik = imetnik.getId();
                String imetnikOpis = "Imetnik odklenjen";
                if (stI.compareTo("06") == 0) {
                    imetnikOpis = "Imetnik odklenjen za dopolnjevanje";
                }
                metodeHelper.insertInStatusLogImetnik(uporabnik, stI, "01", idImetnik, statusLogImetnikRepoManager, imetnikOpis);
            }

        }
        zahtevek.setImetnikStatus(statusImetnikRepoManager.getStatusImetnik("01"), "04");
        zahtevekRepoManager.updateZahtevek(zahtevek);
        metodeHelper.insertInStatusLog(uporabnik, st, "01", id, statusLogRepoManager, zahtevekOpis);
        return new ModelAndView("redirect:/izpisPodrobno/" + id);
    }

    //Posreduj na CIF
    @RequestMapping(value = "/posredujZahtevek/{id}", method = RequestMethod.GET)
    public ModelAndView posredujZahtevek(HttpSession session, @PathVariable Integer id) {
        session.setAttribute("errorMessage", "");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(id);
        //Ce ni v statusu 02, se ne da posredovati na cif
        if (zahtevek.getStatus().getSifra().compareTo("02") != 0) {
            return new ModelAndView("redirect:/izpisPodrobno/" + id);
        }
        if (!metodeHelper.soVsiImetnikiNatisnjeni(zahtevek.getImetniki())) {
            session.setAttribute("errorMessage", "Prosim, da natisnite vse bodoče imetnike preden vlogo posredujete na CIF.");
        } else {
            if (!zahtevek.isNatisnjeno()) {
                session.setAttribute("errorMessage", "Prosim, da natisnite vlogo preden jo posredujete na CIF.");
            } else {
                zahtevek.setStatus(statusRepoManager.getStatus("03"));
                for (int i = 0; i < zahtevek.getImetniki().size(); i++) {
                    Imetnik imetnik = zahtevek.getImetniki().get(i);
                    String st = imetnik.getStatus().getSifraSIm();
                    if (st.compareTo("04") != 0) {
                        long idImetnik = imetnik.getId();
                        metodeHelper.insertInStatusLogImetnik(uporabnik, st, "03", idImetnik, statusLogImetnikRepoManager, "Imetnik posredovan na CIF");
                    }
                }
                zahtevek.setImetnikStatus(statusImetnikRepoManager.getStatusImetnik("03"), "04");
                zahtevekRepoManager.updateZahtevek(zahtevek);
                metodeHelper.insertInStatusLog(uporabnik, "02", "03", id, statusLogRepoManager, "Zahtevek posredovan na CIF");
            }
        }
        return new ModelAndView("redirect:/izpisPodrobno/" + id);
    }

    @RequestMapping(value = "/getZadnjaSprememba", produces = "application/json")
    @ResponseBody
    public StatusLog getZadnjaSprememba(@RequestParam("idZahtevka") Long idZahtevka) {
        return statusLogRepoManager.getLastChangeByZahtevek(idZahtevka);

    }
}
