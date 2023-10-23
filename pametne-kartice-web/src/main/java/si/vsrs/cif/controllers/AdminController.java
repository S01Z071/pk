/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.controllers;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cucumber.api.java.en.Given;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.helper.GenerateReportHelper;
import si.vsrs.cif.helper.MetodeHelper;
import si.vsrs.cif.helper.NastavitveHelper;
import si.vsrs.cif.managers.CertifikatRepoManager;
import si.vsrs.cif.managers.ImetnikRepoManager;
import si.vsrs.cif.managers.JobLogManager;
import si.vsrs.cif.managers.KarticaRepoManager;
import si.vsrs.cif.managers.OpombaRepoManager;
import si.vsrs.cif.managers.SeznamCitalcevSigovcaRepoManager;
import si.vsrs.cif.managers.StatusCertifikatRepoManager;
import si.vsrs.cif.managers.StatusImetnikRepoManager;
import si.vsrs.cif.managers.StatusLogImetnikRepoManager;
import si.vsrs.cif.managers.StatusLogRepoManager;
import si.vsrs.cif.managers.StatusRepoManager;
import si.vsrs.cif.managers.ZahtevekRepoManager;
import si.vsrs.cif.managers.ZahtevekZaKodoRepoManager;
import si.vsrs.cif.managers.ZahtevekZaPreklicRepoManager;
import si.vsrs.cif.managers.ZahtevekZaPrenosRepoManager;
import si.vsrs.cif.mod.AdminIskanjeStatus;
import si.vsrs.cif.mod.Certifikat;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.ImetnikPregledLogStatus;
import si.vsrs.cif.mod.IskalniPogoji;
import si.vsrs.cif.mod.JobLog;
import si.vsrs.cif.mod.Kartica;
import si.vsrs.cif.mod.Opomba;
import si.vsrs.cif.mod.OpremaZaObrazec;
import si.vsrs.cif.mod.SeznamCertifikatov;
import si.vsrs.cif.mod.SeznamCitalcevSigovca;
import si.vsrs.cif.mod.StatusLog;
import si.vsrs.cif.mod.StatusLogImetnik;
import si.vsrs.cif.mod.Zahtevek;
import si.vsrs.cif.mod.ZahtevekZaKodo;
import si.vsrs.cif.mod.ZahtevekZaPreklic;
import si.vsrs.cif.mod.ZahtevekZaPrenos;
import si.vsrs.cif.mod.web.Uporabnik;

/**
 *
 * @author uporabnik
 */
@Controller
@PreAuthorize("hasRole('002')")
public class AdminController {

    @Autowired
    ZahtevekRepoManager zahtevekRepoManager;
    @Autowired
    ImetnikRepoManager imetnikRepoManager;
    @Autowired
    StatusRepoManager statusRepoManager;
    @Autowired
    StatusLogRepoManager statusLogRepoManager;
    @Autowired
    StatusLogImetnikRepoManager statusLogImetnikRepoManager;
    @Autowired
    StatusImetnikRepoManager statusImetnikRepoManager;
    @Autowired
    KarticaRepoManager karticaRepoManager;
    @Autowired
    CertifikatRepoManager certifikatRepoManager;
    @Autowired
    OpombaRepoManager opombaRepoManager;
    @Autowired
    NastavitveHelper nastavitveHelper;
    @Autowired
    GenerateReportHelper generateReportHelper;
    @Autowired
    StatusCertifikatRepoManager statusCertifikatRepoManager;
    @Autowired
    MetodeHelper metodeHelper;
    @Autowired
    SimpleJobLauncher jobLauncher;
    @Autowired
    Job updateCertfJob;
    @Autowired
    JobLogManager jobLogManager;
    @Autowired
    ZahtevekZaKodoRepoManager zahtevekZaKodoRepoManager;
    @Autowired
    SeznamCitalcevSigovcaRepoManager seznamCitalcevSigovcaRepoManager;
    @Autowired
    ZahtevekZaPreklicRepoManager zahtevekZaPreklicRepoManager;
    @Autowired
    ZahtevekZaPrenosRepoManager zahtevekZaPrenosRepoManager;

    @RequestMapping(value = "/iskanje")
    public ModelAndView iskanjePage() {
        ModelAndView modelAndView = new ModelAndView("iskanje");
        modelAndView.addObject(new IskalniPogoji());
        return modelAndView;
    }

     /*-------------------------------------------*/
    @RequestMapping(value = "/AdminConfirmIskanje/{pageNumP}/{tabS}")
    public String adminConfirmIskanje(HttpSession session, @ModelAttribute AdminIskanjeStatus status, @PathVariable Integer pageNumP, @PathVariable Integer tabS) {
        session.setAttribute("AdminIskanjeStatus", status);
        return "redirect:/izpisPodatkovAdmin/1/" + pageNumP + "/" + tabS;
    }

    @RequestMapping(value = "/izpisPodatkovAdmin/{pageNum}/{pageNumKoda}/{tabS}")
    public ModelAndView izpisPodatkovAdmin(HttpSession session, @ModelAttribute AdminIskanjeStatus status, @PathVariable Integer pageNum, @PathVariable Integer pageNumKoda, @PathVariable Integer tabS) {
        ModelAndView modelAndView = new ModelAndView("izpisPodatkov");
        if (session.getAttribute("AdminIskanjeStatus") == null) {
            session.setAttribute("AdminIskanjeStatus", status);
        } else {
            status = (AdminIskanjeStatus) session.getAttribute("AdminIskanjeStatus");
        }
        List<Zahtevek> zahtevki = zahtevekRepoManager.getZahtevkeAdmin(pageNum, status);
        zahtevki = metodeHelper.updateDeletedImetniki(zahtevki);
        modelAndView.addObject("zahtevki", zahtevki);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("izpisCount", (Math.ceil(zahtevekRepoManager.getZahtevekCountAdmin(status) / (nastavitveHelper.getPrikazovNaStran() * 1.0))));
        modelAndView.addObject("adminIskanjeStatus", status);

        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        List<ZahtevekZaKodo> zahtevekZaKodo = zahtevekZaKodoRepoManager.getZahtevkeZaKodo(pageNumKoda, uporabnik);
        modelAndView.addObject("zahtevekZaKodo", zahtevekZaKodo);
        modelAndView.addObject("pageNumKoda", pageNumKoda);
        modelAndView.addObject("izpisCountKoda", (Math.ceil(zahtevekZaKodoRepoManager.getZahtevkeZaKodoCount(uporabnik) / (nastavitveHelper.getPrikazovNaStran() * 1.0))));

        modelAndView.addObject("prikazovNaStran", nastavitveHelper.getPrikazovNaStran());
        modelAndView.addObject("tabStatus", tabS);

        return modelAndView;
    }


    @RequestMapping(value = "/iskanjeCK", produces = "text/plain;charset=UTF-8")
    public ModelAndView iskanjeCK(@RequestParam String iskano, HttpSession session) {
        Imetnik imetnik = imetnikRepoManager.findByCrtnaKoda(iskano);
        session.setAttribute("nazajNaIskanje", "true");
        if (imetnik != null) {
            return new ModelAndView("redirect:/pregledImetnikovPodrobno/" + imetnik.getId());
        }
        Zahtevek zahtevek = zahtevekRepoManager.findByCrtnaKoda(iskano);
        if (zahtevek != null) {
            return new ModelAndView("redirect:/izpisPodrobno/" + zahtevek.getId());
        }
        ZahtevekZaKodo zahtevekZaKodo = zahtevekZaKodoRepoManager.findByCrtnaKoda(iskano);
        if (zahtevekZaKodo != null) {
            return new ModelAndView("redirect:/ZahtevekZaKodo/podrobno/" + zahtevekZaKodo.getId());
        }
        ZahtevekZaPreklic zahtevekZaPreklic = zahtevekZaPreklicRepoManager.findByCrtnaKoda(iskano);
        if (zahtevekZaPreklic != null) {
            return new ModelAndView("redirect:/ZahtevekZaPreklic/podrobno/" + zahtevekZaPreklic.getId());
        }
        ZahtevekZaPrenos zahtevekZaPrenos = zahtevekZaPrenosRepoManager.findByCrtnaKoda(iskano);
        if (zahtevekZaPrenos != null) {
            return new ModelAndView("redirect:/zahtevekZaPrenos/podrobno/" + zahtevekZaPrenos.getId());
        }
        ModelAndView modelAndView = new ModelAndView("iskanje");
        modelAndView.addObject("error", "Ni zadetkov.");
        session.removeAttribute("nazajNaIskanje");
        return modelAndView;

    }

    @RequestMapping(value = "/PotrdiImetnik/{idZ}/{idI}/{scrollTop}", method = RequestMethod.GET)
    public String potrdiImetnik(HttpSession session, @PathVariable Integer idZ, @PathVariable Integer idI, @PathVariable Integer scrollTop) {
        session.setAttribute("errorMessage", "");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        potrdiImetnikPosebej(idI, uporabnik);
        session.setAttribute("scrollTop", scrollTop);
        return "redirect:/izpisPodrobno/" + idZ;
    }

    @RequestMapping(value = "/PotrdiSamoImetnik/{idI}", method = RequestMethod.GET)
    public String potrdiSamoImetnik(HttpSession session, @PathVariable Integer idI) {
        String nazajNaIskanje = (String) session.getAttribute("nazajNaIskanje");
        session.setAttribute("errorMessage", "");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        potrdiImetnikPosebej(idI, uporabnik);
        if (nazajNaIskanje != null && nazajNaIskanje.compareTo("true") == 0) {
            session.removeAttribute("nazajNaIskanje");
            return "redirect:/iskanje";
        }
        return "redirect:/pregledImetnikovAdmin/1";
    }

    private void potrdiImetnikPosebej(Integer idI, Uporabnik uporabnik) {
        Imetnik imetnik = imetnikRepoManager.getImetnik(idI);
        if (imetnik.getStatus().getSifraSIm().compareTo("03") == 0) {
            String stI = imetnik.getStatus().getSifraSIm();
            imetnik.setStatus(statusImetnikRepoManager.getStatusImetnik("05"));
            imetnikRepoManager.updateImetnik(imetnik);
            metodeHelper.insertInStatusLogImetnik(uporabnik, stI, "05", idI, statusLogImetnikRepoManager, "Imetnik potrjen");
        }
    }



    @RequestMapping(value = "/ZavrniImetnik/{idZ}/{idI}")
    public String zavrniImetnik(HttpSession session, @ModelAttribute Opomba opomba, @PathVariable Long idZ, @PathVariable Integer idI) {
        session.setAttribute("errorMessage", "");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        zavrniImetnikPosebej(idI, opomba, uporabnik, idZ);
        return "redirect:/izpisPodrobno/" + idZ;
    }

    @RequestMapping(value = "/ZavrniSamoImetnik/{idI}")
    public String zavrniSamoImetnik(HttpSession session, @ModelAttribute Opomba opomba, @PathVariable Integer idI) {
        session.setAttribute("errorMessage", "");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        zavrniImetnikPosebej(idI, opomba, uporabnik, zahtevekRepoManager.getZahtevekIDFromImetnikID(idI));
        return "redirect:/pregledImetnikovAdmin/1";
    }

    private void zavrniImetnikPosebej(Integer idI, Opomba opomba, Uporabnik uporabnik, Long idZ) {
        Imetnik imetnik = imetnikRepoManager.getImetnik(idI);
        if (imetnik.getStatus().getSifraSIm().compareTo("03") == 0) {
            String stI = imetnik.getStatus().getSifraSIm();
            imetnik.setStatus(statusImetnikRepoManager.getStatusImetnik("06"));
            imetnikRepoManager.updateImetnik(imetnik);
            opomba = metodeHelper.getOpombaZavrnitev(uporabnik, opomba, "Vloga za " + imetnik.getIme() + " " + imetnik.getPriimek() + " je bila zavrnjena.");
            opomba.setNaslovBarva("#FF0000");
            opombaRepoManager.insertOpomba(opomba, idZ);
            metodeHelper.insertInStatusLogImetnik(uporabnik, stI, "06", idI, statusLogImetnikRepoManager, "Imetnik zavrnjen");
        }
    }

    //Ce so vsi imetniki zavrnjeni(06)
    @RequestMapping(value = "/zavrniZahtevek/{idZ}")
    public String zavrniZahtevek(HttpSession session, @ModelAttribute Opomba opomba, @PathVariable Integer idZ) {
        session.setAttribute("errorMessage", "");
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(idZ);
        if (zahtevek.getStatus().getSifra().compareTo("03") != 0) {
            return "redirect:/index";
        }
        if (metodeHelper.vsiImetnikiStatus(zahtevek, "06", "05", "04") && metodeHelper.vsajEnImetnikStatus(zahtevek, "06")) {
            Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
            String stZ = zahtevek.getStatus().getSifra();
            zahtevek.setStatus(statusRepoManager.getStatus("06"));
            zahtevekRepoManager.updateZahtevek(zahtevek);
            metodeHelper.insertInStatusLog(uporabnik, stZ, "06", idZ, statusLogRepoManager, "Zahtevek zavrnjen");
            opomba = metodeHelper.getOpombaZavrnitev(uporabnik, opomba, "Zahtevek je bil zavrnjen.");
            opomba.setNaslovBarva("#FF0000");
            opombaRepoManager.insertOpomba(opomba, idZ);
        } else {
            session.setAttribute("errorMessage", "Noben imetnik ni zavrnjen.");
        }
        return "redirect:/izpisPodrobno/" + idZ;
    }





    /*

    @RequestMapping(value = "/potrdiZahtevek/{idZ}", method = RequestMethod.GET)
    public String potrdiZahtevek(HttpSession session, @PathVariable Integer idZ) {
        session.setAttribute("errorMessage", "");
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(idZ);
        if (zahtevek.getStatus().getSifra().compareTo("03") != 0) {
            return "redirect:/index";
        }
        if (metodeHelper.vsiImetnikiStatus(zahtevek, "05", "04")) {
            String stZ = zahtevek.getStatus().getSifra();
            zahtevek.setStatus(statusRepoManager.getStatus("05"));
            zahtevekRepoManager.updateZahtevek(zahtevek);
            metodeHelper.insertInStatusLog((Uporabnik) session.getAttribute("uporabnik"), stZ, "05", idZ, statusLogRepoManager, "Zahtevek potrjen");
        } else {
            session.setAttribute("errorMessage", "Vsi imetniki morajo biti potrjeni.");
        }
        return "redirect:/izpisPodrobno/" + idZ;
    }

     */


    @RequestMapping(value = "/potrdiZahtevek/{idZ}", method = RequestMethod.GET)
    public String potrdiZahtevek(HttpSession session, @PathVariable Integer idZ) {
        session.setAttribute("errorMessage", "");
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(idZ);
        if (zahtevek.getStatus().getSifra().compareTo("03") != 0) {
            return "redirect:/index";
        }

        // Confirm Imetnik statuses
        if (confirmAllImetnikiStatus(zahtevek, session)) {
            String stZ = zahtevek.getStatus().getSifra();
            zahtevek.setStatus(statusRepoManager.getStatus("05"));
            zahtevekRepoManager.updateZahtevek(zahtevek);
            metodeHelper.insertInStatusLog((Uporabnik) session.getAttribute("uporabnik"), stZ, "05", idZ, statusLogRepoManager, "Zahtevek potrjen");
        } else {
            session.setAttribute("errorMessage", "Vsi imetniki morajo biti potrjeni.");
        }
        return "redirect:/izpisPodrobno/" + idZ;
    }

    //potrdi status imetnikov na 05
    private boolean confirmAllImetnikiStatus(Zahtevek zahtevek, HttpSession session) {
        List<Imetnik> imetniki = zahtevek.getImetniki();

        boolean allImetnikiConfirmed = true;
        for (Imetnik imetnik : imetniki) {
            if (!"05".equals(imetnik.getStatus().getSifraSIm())) {
                // If the status is not "05", update it to "05"
                imetnik.setStatus(statusImetnikRepoManager.getStatusImetnik("05"));
                imetnikRepoManager.updateImetnik(imetnik);

                // Insert status log for this imetnik
                String stI = imetnik.getStatus().getSifraSIm();
                metodeHelper.insertInStatusLogImetnik((Uporabnik) session.getAttribute("uporabnik"), stI, "05", imetnik.getId(), statusLogImetnikRepoManager, "Imetnik potrjen");
            } else {
                // If any imetnik is not confirmed, set the flag to false
                allImetnikiConfirmed = false;
            }
        }

        return allImetnikiConfirmed;
    }



    /**
     * *******************IZVOZ**********************************
     */
    //Vse, ki imajo status '05'
    @RequestMapping(value = "/izvozVse")
    public void izvozVse(HttpServletResponse response) throws IOException {
        List<Zahtevek> zahtevki = zahtevekRepoManager.findByStatusSifra("05");
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        byte[] buf = new byte[1024];
        InputStream in;
        for (int j = 0; j < zahtevki.size(); j++) {
            Zahtevek zahtevek = zahtevki.get(j);
            zahtevek = metodeHelper.setIzvoziImetniki(zahtevek, true);
            zahtevek.setIzvozi(true);
            zahtevekRepoManager.updateZahtevek(zahtevek);
            zahtevek = metodeHelper.updateDeletedImetniki(zahtevek);
            List<Imetnik> imetniki = zahtevek.getImetniki();
            in = vrniFileImetniki(zahtevek, imetniki);
            out.putNextEntry(new ZipEntry(zahtevek.getCrtnaKoda() + ".csv"));
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
        out.close();
        setResponseZIP(response, String.valueOf(new Date().getTime()));
    }

    //generira zip z N .csv datotekami => vse imetnike od enegea zahtevka
    @RequestMapping(value = "/izvozZahtevekVse/{idZ}")
    public void izvozZahtevekVse(@PathVariable Integer idZ, HttpServletResponse response) throws IOException {
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        byte[] buf = new byte[1024];
        InputStream in;
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(idZ);
        zahtevek = metodeHelper.setIzvoziImetniki(zahtevek, true);
        zahtevek.setIzvozi(true);
        zahtevekRepoManager.updateZahtevek(zahtevek);
        zahtevek = metodeHelper.updateDeletedImetniki(zahtevek);
        in = vrniFileImetniki(zahtevek, zahtevek.getImetniki());
        out.putNextEntry(new ZipEntry(zahtevek.getCrtnaKoda() + ".csv"));

        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.closeEntry();
        in.close();
        out.close();
        setResponseZIP(response, String.valueOf(new Date().getTime()));
    }

    //Generira zip z enim .csv
    @RequestMapping(value = "/izvozImetnik/{idZ}/{idI}")
    public void izvozImetnik(@PathVariable Integer idZ, @PathVariable Integer idI, HttpServletResponse response) throws IOException {
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        byte[] buf = new byte[1024];
        InputStream in;
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(idZ);
        List<Imetnik> imetniki = new ArrayList();
        Imetnik imetnik = zahtevek.getImetnikById(Long.valueOf(idI));
        imetniki.add(imetnik);
        imetnik.setIzvozi(true);
        imetnikRepoManager.updateImetnik(imetnik);
        if (metodeHelper.checkIzvoziImetnik(zahtevek)) {
            zahtevek.setIzvozi(true);
            zahtevekRepoManager.updateZahtevek(zahtevek);
        }
        in = vrniFileImetniki(zahtevek, imetniki);
        out.putNextEntry(new ZipEntry(zahtevek.getCrtnaKoda() + ".csv"));
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.closeEntry();
        in.close();
        out.close();
        setResponseZIP(response, String.valueOf(new Date().getTime()));
    }

    @RequestMapping(value = "/posredovanoNaSigovCA/{idZ}", method = RequestMethod.GET)
    public String posredovanoNaSigovCA(HttpSession session, @PathVariable Integer idZ) {
        session.setAttribute("errorMessage", "");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(idZ);
        if (zahtevek.getStatus().getSifra().compareTo("05") != 0) {
            return "redirect:/index";
        }
        // zahtevek = metodeHelper.updateDeletedImetniki(zahtevek);
        if (metodeHelper.checkIzvoziImetnik(zahtevek)) {
            String st = zahtevek.getStatus().getSifra();
            zahtevek.setStatus(statusRepoManager.getStatus("07"));
            String zahtevekOpis = "Zahtevek posredovan na SIGOV-CA";
            for (int i = 0; i < zahtevek.getImetniki().size(); i++) {
                Imetnik imetnik = zahtevek.getImetniki().get(i);
                if (imetnik.getStatus().getSifraSIm().compareTo("04") != 0) {
                    String stI = imetnik.getStatus().getSifraSIm();
                    long idImetnik = imetnik.getId();
                    String imetnikOpis = "Imetnik posredovan na SIGOV-CA";
                    metodeHelper.insertInStatusLogImetnik(uporabnik, stI, "07", idImetnik, statusLogImetnikRepoManager, imetnikOpis);
                }
            }
            zahtevek.setImetnikStatus(statusImetnikRepoManager.getStatusImetnik("07"), "04");
            zahtevekRepoManager.updateZahtevek(zahtevek);
            metodeHelper.insertInStatusLog(uporabnik, st, "07", idZ, statusLogRepoManager, zahtevekOpis);
        } else {
            session.setAttribute("errorMessage", "Pred posredovanjem na SIGOV-CA je potrebno vse imetnike izvoziti v .csv datoteko.");
        }
        return "redirect:/izpisPodrobno/" + idZ;
    }

    @RequestMapping(value = "/posredovanjeNaSIG")
    public ModelAndView posredovanjeNaSIG() {
        List<Zahtevek> zahtevki = zahtevekRepoManager.findByStatusSifra("05");
        ModelAndView modelAndView = new ModelAndView("posredovanjeNaSIG");
        modelAndView.addObject("zahtevki", zahtevki);
        return modelAndView;
    }

    @RequestMapping(value = "/posredovanoNaSIG/process", method = RequestMethod.GET)
    public ModelAndView posredovanoNaSIGProcess(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("posredovanjeNaSIG");
        List<Zahtevek> zahtevki = zahtevekRepoManager.findByStatusSifra("05");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        int usp = 0, neusp = 0;
        for (int i = 0; i < zahtevki.size(); i++) {
            Zahtevek zahtevek = zahtevki.get(i);
            if (metodeHelper.checkIzvoziImetnik(zahtevek)) {
                String st = zahtevek.getStatus().getSifra();
                zahtevek.setStatus(statusRepoManager.getStatus("07"));
                String zahtevekOpis = "Zahtevek posredovan na SIGOV-CA";
                for (int j = 0; j < zahtevek.getImetniki().size(); j++) {
                    Imetnik imetnik = zahtevek.getImetniki().get(j);
                    if (imetnik.getStatus().getSifraSIm().compareTo("04") != 0) {
                        String stI = imetnik.getStatus().getSifraSIm();
                        long idImetnik = imetnik.getId();
                        String imetnikOpis = "Imetnik posredovan na SIGOV-CA";
                        metodeHelper.insertInStatusLogImetnik(uporabnik, stI, "07", idImetnik, statusLogImetnikRepoManager, imetnikOpis);
                    }
                }
                zahtevek.setImetnikStatus(statusImetnikRepoManager.getStatusImetnik("07"), "04");
                zahtevekRepoManager.updateZahtevek(zahtevek);
                metodeHelper.insertInStatusLog(uporabnik, st, "07", zahtevek.getId(), statusLogRepoManager, zahtevekOpis);
                usp++;
            } else {
                neusp++;
            }
        }
        modelAndView.addObject("zahtevki", zahtevekRepoManager.findByStatusSifra("05"));
        modelAndView.addObject("uspesno", usp);
        modelAndView.addObject("neuspesno", neusp);
        return modelAndView;

    }

    //Pregled log
    @RequestMapping(value = "/pregledZgodovine/{idZ}")
    public ModelAndView pregledZgodovine(@PathVariable Integer idZ) {
        ModelAndView modelAndView = new ModelAndView("pregledLog");
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(idZ);
        List<StatusLog> statusLog = statusLogRepoManager.getStatusLogByZahtevekId(zahtevek.getId());
        modelAndView.addObject("statusLog", statusLog);
        List<ImetnikPregledLogStatus> imetnikPregledLogStatus = new ArrayList();
        for (int i = 0; i < zahtevek.getImetniki().size(); i++) {
            Imetnik imetnik = zahtevek.getImetniki().get(i);
            List<StatusLogImetnik> statusLogImetnik = statusLogImetnikRepoManager.getStatusLogByImetnikId(imetnik.getId());
            ImetnikPregledLogStatus impTemp = new ImetnikPregledLogStatus();
            impTemp.setStatusLogImetnik(statusLogImetnik);
            impTemp.setImetnikIme(imetnik.getIme());
            impTemp.setImetnikPriimek(imetnik.getPriimek());
            impTemp.setImetnikCrtnaKoda(imetnik.getCrtnaKoda());
            imetnikPregledLogStatus.add(impTemp);
        }
        modelAndView.addObject("imetnikPregledLogStatus", imetnikPregledLogStatus);
        modelAndView.addObject("zahtevek", zahtevek);
        return modelAndView;
    }

    @RequestMapping(value = "/printObvestilo/{idC}", method = RequestMethod.GET)
    public void printObvestilo(HttpServletResponse response, @PathVariable Integer idC) throws Exception {
        Certifikat certifikat = certifikatRepoManager.findById(idC);
        if (certifikat.getStatus().getSifra().compareTo("02") == 0) {
            Imetnik imetnik = certifikat.getImetnik();
            Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(zahtevekRepoManager.getZahtevekIDFromImetnikID(imetnik.getId()));
            imetnik.setNatisnjenoKonco(true);
            imetnikRepoManager.updateImetnik(imetnik);

            List<OpremaZaObrazec> oprema = new ArrayList();
            OpremaZaObrazec opremaKartica = new OpremaZaObrazec("Kartica zaposlenega v sodstvu", certifikat.getKartica().getSerijskaStevilkaKartice());
            oprema.add(opremaKartica);
            List<SeznamCitalcevSigovca> citalci = seznamCitalcevSigovcaRepoManager.findByImetnikIdAndDatumVrnitveIsNull(imetnik.getId());
            for (SeznamCitalcevSigovca c : citalci) {
                OpremaZaObrazec opremaC = new OpremaZaObrazec("Čitalec - " + c.getTip(), c.getOznaka());
                oprema.add(opremaC);
            }

            ByteArrayOutputStream outPutStrem = generateReportHelper.createReportObvestiloPotrdiloOPrejemu_1_4(zahtevek, imetnik, certifikat, oprema);
            //ByteArrayOutputStream outPutStrem = generateReportHelper.createReportObvestiloPotrdiloOPrejemu(zahtevek, imetnik, certifikat);
            metodeHelper.setHeaderPDF(response, certifikat.getKartica().getBarcode(), outPutStrem);
        }
    }

    @RequestMapping(value = "/odpremljena/{idC}", method = RequestMethod.GET)
    public String zakljuci(HttpSession session, @PathVariable Integer idC) {
        session.setAttribute("errorMessage", "");
        Certifikat certifikat = certifikatRepoManager.findById(idC);
        if (certifikat.getStatus().getSifra().compareTo("02") != 0) {
            return "redirect:/index";
        }
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        if (!zakljuci(certifikat, uporabnik)) {
            session.setAttribute("errorMessage", "Pred zaključkom je potrebno natisniti podatke o imetniku.");
        }
        return "redirect:/pregledPripKartic";
    }

    @RequestMapping(value = "/odpremljenaIskanje/{idC}", method = RequestMethod.GET)
    public String zakljuciIskanje(HttpSession session, @PathVariable Integer idC) {
        session.setAttribute("errorMessage", "");
        Certifikat certifikat = certifikatRepoManager.findById(idC);
        if (certifikat.getStatus().getSifra().compareTo("02") != 0) {
            return "redirect:/index";
        }
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        if (!zakljuci(certifikat, uporabnik)) {
            session.setAttribute("errorMessage", "Pred zaključkom je potrebno natisniti podatke o imetniku.");
        }
        return "redirect:/iskPripKartic";
    }

    private boolean zakljuci(Certifikat certifikat, Uporabnik uporabnik) {
        Imetnik imetnik = certifikat.getImetnik();
        if (imetnik.getNatisnjenoKonco() != null && imetnik.getNatisnjenoKonco()) {
            String stI = imetnik.getStatus().getSifraSIm();
            imetnik.setStatus(statusImetnikRepoManager.getStatusImetnik("08"));
            imetnikRepoManager.updateImetnik(imetnik);
            metodeHelper.insertInStatusLogImetnik(uporabnik, stI, "08", imetnik.getId(), statusLogImetnikRepoManager, "Kartica odpremljena.");
            certifikat.setStatus(statusCertifikatRepoManager.getStatusCertifikat("03"));
            certifikatRepoManager.updateCertifikat(certifikat);
            return true;
        } else {
            return false;
        }
    }

    //Ce je pregled true je prisel iz strani za pregled in ne iskanje
    @RequestMapping(value = "/potrdiloPrejeto/process/{idC}", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView potrdiloPrejetoProcess(HttpSession session, @RequestParam String datum, @RequestParam(value = "pregled", required = false) Boolean pregled, @PathVariable Integer idC) {
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        Certifikat certifikat = certifikatRepoManager.findById(idC);
        if (certifikat.getStatus().getSifra().compareTo("03") != 0) {
            return new ModelAndView("redirect:/index");
        }
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date datum1 = null;
        boolean shrani = true;
        if (!metodeHelper.isDateOk(datum)) {
            shrani = false;
        } else {
            try {
                datum1 = df.parse(datum);
            } catch (ParseException ex) {
                //Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                shrani = false;
            }
        }
        if (!shrani) {
            ModelAndView modelAndView;
            List<Certifikat> certifikati = new ArrayList();
            if (pregled != null && pregled) {
                certifikati = certifikatRepoManager.findByImetnikStatusSifraSImOrderByIdAsc("08");
                modelAndView = new ModelAndView("potrdiloPrejetoPregled");
            } else {
                modelAndView = new ModelAndView("potrdiloPrejeto");
                certifikati = certifikatRepoManager.findByImetnikCrtnaKodaAndImetnikStatusSifraSIm(certifikat.getImetnik().getCrtnaKoda(), "08");
            }
            modelAndView.addObject("certifikati", certifikati);
            modelAndView.addObject("error", "Datum je napačne oblike.");
            return modelAndView;
        }


        Imetnik imetnik = certifikat.getImetnik();

        metodeHelper.setImetnikDatumPrejemaOpreme(imetnik, statusImetnikRepoManager, imetnikRepoManager, datum1, statusLogImetnikRepoManager, uporabnik, "Prejeto potrdilo o prejemu kartice.");
        /* String stI = imetnik.getStatus().getSifraSIm();
         imetnik.setStatus(statusImetnikRepoManager.getStatusImetnik("09"));
         imetnik.setDatumPrejemaOpreme(datum1);
         imetnikRepoManager.updateImetnik(imetnik);
         metodeHelper.insertInStatusLogImetnik(uporabnik, stI, "09", imetnik.getId(), statusLogImetnikRepoManager, "Prejeto potrdilo o prejemu kartice.");*/

        metodeHelper.changeStatusZahtevekIfVsiImetnikiPrejetoPotrdiloOPrejetjuKartice(imetnik.getId(), zahtevekRepoManager, statusRepoManager, statusLogRepoManager, uporabnik);
        /*Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(zahtevekRepoManager.getZahtevekIDFromImetnikID(imetnik.getId()));
         if (metodeHelper.vsiImetnikiStatus(zahtevek, "09", "04", "10")) {
         String zSt = zahtevek.getStatus().getSifra();
         zahtevek.setStatus(statusRepoManager.getStatus("08"));
         zahtevekRepoManager.updateZahtevek(zahtevek);
         metodeHelper.insertInStatusLog(uporabnik, zSt, "08", zahtevek.getId(), statusLogRepoManager, "Vse pametne kartice prevzete.");
         }*/
        if (pregled != null && pregled) {
            return new ModelAndView("redirect:/potrdiloPrejetoPregled");
        }
        return new ModelAndView("redirect:/potrdiloPrejeto");

    }

    @RequestMapping(value = "/izvozPodatkovKartice")
    public ModelAndView izvozPodatkovKartice(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("izvozPodatkovKartice");
        if (session != null && session.getAttribute("izvoziNeizvozeneMsg") != null) {
            modelAndView.addObject("error", session.getAttribute("izvoziNeizvozeneMsg"));
            session.removeAttribute("izvoziNeizvozeneMsg");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/izvozPodatkovKartice/iskanje")
    public ModelAndView izvozPodatkovKarticeIskanje(@RequestParam String datumOd, @RequestParam String datumDo) {
        ModelAndView modelAndView = new ModelAndView("izvozPodatkovKartice");
        if (!metodeHelper.isDateOk(datumOd) || !metodeHelper.isDateOk(datumDo)) {
            modelAndView.addObject("error", "Datum je napačne oblike.");
            return modelAndView;
        }

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        List<Certifikat> certifikati = null;
        try {
            certifikati = certifikatRepoManager.findByKarticaDatumInitBetweenAndStatusSifraOrderByKarticaDatumInitAsc(df.parse(datumOd), df.parse(datumDo));
        } catch (ParseException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            modelAndView.addObject("error", "Napaka pri pretvorbi datuma.");
            return modelAndView;
        }

        if (certifikati == null || certifikati.isEmpty()) {
            modelAndView.addObject("error", "Ni najdenih rezultatov.");
        }
        modelAndView.addObject("certifikati", certifikati);
        modelAndView.addObject("datumOd", datumOd);
        modelAndView.addObject("datumDo", datumDo);
        return modelAndView;
    }

    @RequestMapping(value = "/izvozPodatkovKartice/process", method = RequestMethod.POST)
    public void izvozPodatkovKarticeProcess(HttpServletResponse response, @RequestParam String datumOd, @RequestParam String datumDo, HttpSession session) throws IOException {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        List<Certifikat> certifikati = new ArrayList();
        try {
            certifikati = certifikatRepoManager.findByKarticaDatumInitBetweenAndStatusSifraOrderByKarticaDatumInitAsc(df.parse(datumOd), df.parse(datumDo));
        } catch (ParseException e) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, e);
        }
        generateZipForIzvoz(response, certifikati);
        setResponseZIP(response, String.valueOf(new Date().getTime()));
    }

    @RequestMapping(value = "/izvozPodatkovKartice/prikaziNeizvozene", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView izvozPodatkovKarticePrikaziNeizvozene(HttpServletResponse response, HttpSession session, HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView("izvozPodatkovKartice");
        List<Certifikat> certifikati = certifikatRepoManager.getNeizvozene();
        if (!certifikati.isEmpty()) {
            modelAndView.addObject("certifikati", certifikati);
            modelAndView.addObject("neizvozeni", true);
        } else {
            modelAndView.addObject("error", "Ni neizvoženih certifikatov.");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/izvozPodatkovKartice/processNeizvozene", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void izvozPodatkovKarticeProcessNeizvozene(HttpServletResponse response, HttpSession session, HttpServletRequest request) throws IOException {
        List<Certifikat> certifikati = certifikatRepoManager.getNeizvozene();
        if (!certifikati.isEmpty()) {
            generateZipForIzvoz(response, certifikati);
            for (Certifikat c : certifikati) {
                // if (c.getStatus().getSifra().compareTo("03") == 0 && c.getKartica() != null) { //Prevzet
                c.setIzvozPin3(true);
                c.setDatumIzvoza(new Date());
                certifikatRepoManager.updateCertifikat(c);
                // }
            }
            setResponseZIP(response, String.valueOf(new Date().getTime()));
        } else {
            session.setAttribute("izvoziNeizvozeneMsg", "Ni najdenih podatkov.");
            response.sendRedirect(request.getContextPath() + "/izvozPodatkovKartice");
        }

    }

    @RequestMapping(value = "/pregledStatistike")
    public ModelAndView pregledStatistike() {
        /*Zahtevki*/
        ModelAndView modelAndView = new ModelAndView("pregledStatistike");
        int zahtevkiVPripravi = zahtevekRepoManager.countByStatusSifra("01");
        int zahtevkiZakljucen = zahtevekRepoManager.countByStatusSifra("02");
        int zahtevkiPosredovanoNaCif = zahtevekRepoManager.countByStatusSifra("03");
        int zahtevkiIzbrisano = zahtevekRepoManager.countByStatusSifra("04");
        int zahtevkiPotrjeno = zahtevekRepoManager.countByStatusSifra("05");
        int zahtevkiZavrnjeno = zahtevekRepoManager.countByStatusSifra("06");
        int zahtevkiPosredovanoNaSigovca = zahtevekRepoManager.countByStatusSifra("07");
        int zahtevkiDokoncan = zahtevekRepoManager.countByStatusSifra("08");
        int zahtevkiSkupaj = zahtevkiVPripravi + zahtevkiZakljucen + zahtevkiPosredovanoNaCif + zahtevkiIzbrisano + zahtevkiPotrjeno + zahtevkiZavrnjeno + zahtevkiPosredovanoNaSigovca + zahtevkiDokoncan;

        modelAndView.addObject("zahtevkiVPripravi", zahtevkiVPripravi);
        modelAndView.addObject("zahtevkiZakljucen", zahtevkiZakljucen);
        modelAndView.addObject("zahtevkiPosredovanoNaCif", zahtevkiPosredovanoNaCif);
        modelAndView.addObject("zahtevkiIzbrisano", zahtevkiIzbrisano);
        modelAndView.addObject("zahtevkiPotrjeno", zahtevkiPotrjeno);
        modelAndView.addObject("zahtevkiZavrnjeno", zahtevkiZavrnjeno);
        modelAndView.addObject("zahtevkiPosredovanoNaSigovca", zahtevkiPosredovanoNaSigovca);
        modelAndView.addObject("zahtevkiDokoncan", zahtevkiDokoncan);
        modelAndView.addObject("zahtevkiSkupaj", zahtevkiSkupaj);

        /*Imetniki*/
        int imetnikiVPripravi = imetnikRepoManager.countByStatusSifra("01");
        int imetnikiZakljucen = imetnikRepoManager.countByStatusSifra("02");
        int imetnikiPosredovanoNaCif = imetnikRepoManager.countByStatusSifra("03");
        int imetnikiIzbrisano = imetnikRepoManager.countByStatusSifra("04");
        int imetnikiPotrjeno = imetnikRepoManager.countByStatusSifra("05");
        int imetnikiZavrnjeno = imetnikRepoManager.countByStatusSifra("06");
        int imetnikiPosredovanoNaSigovca = imetnikRepoManager.countByStatusSifra("07");
        int imetnikiPametnaKarticaOdpremljena = imetnikRepoManager.countByStatusSifra("08");
        int imetnikiPametnaKarticaPrevzeta = imetnikRepoManager.countByStatusSifra("09");
        int imetnikiSkupaj = imetnikiVPripravi + imetnikiZakljucen + imetnikiPosredovanoNaCif + imetnikiIzbrisano + imetnikiPotrjeno + imetnikiZavrnjeno + imetnikiPosredovanoNaSigovca + imetnikiPametnaKarticaOdpremljena + imetnikiPametnaKarticaPrevzeta;


        modelAndView.addObject("imetnikiVPripravi", imetnikiVPripravi);
        modelAndView.addObject("imetnikiZakljucen", imetnikiZakljucen);
        modelAndView.addObject("imetnikiPosredovanoNaCif", imetnikiPosredovanoNaCif);
        modelAndView.addObject("imetnikiIzbrisano", imetnikiIzbrisano);
        modelAndView.addObject("imetnikiPotrjeno", imetnikiPotrjeno);
        modelAndView.addObject("imetnikiZavrnjeno", imetnikiZavrnjeno);
        modelAndView.addObject("imetnikiPosredovanoNaSigovca", imetnikiPosredovanoNaSigovca);
        modelAndView.addObject("imetnikiPametnaKarticaOdpremljena", imetnikiPametnaKarticaOdpremljena);
        modelAndView.addObject("imetnikiPametnaKarticaPrevzeta", imetnikiPametnaKarticaPrevzeta);
        modelAndView.addObject("imetnikiSkupaj", imetnikiSkupaj);

        /*Certifikati*/
        int certifikatiVPripravi = certifikatRepoManager.countByStatusSifra("01");
        int certifikatiPrevzetNaVSRS = certifikatRepoManager.countByStatusSifra("02");
        int certifikatiPrevzet = certifikatRepoManager.countByStatusSifra("03");
        int certifikatPreklican = certifikatRepoManager.countByStatusSifra("04");
        int certifikatPretekel = certifikatRepoManager.countByStatusSifra("05");
        int karticaSkupaj = karticaRepoManager.countKarticaWithImetnik();



        List<Kartica> karticeBrezCertifikata = karticaRepoManager.getKarticeWithoutCertifikat();
        List<Kartica> karticeBrezImetnika = karticaRepoManager.getKarticeWithoutImetnik();
        List<Certifikat> neVeljavniCertifikatiBrezVrnjeneKartice = certifikatRepoManager.findNeveljavneCertifikateAndKarticaNiVrnjena();


        modelAndView.addObject("certifikatiVPripravi", certifikatiVPripravi);
        modelAndView.addObject("certifikatiPrevzetNaVSRS", certifikatiPrevzetNaVSRS);
        modelAndView.addObject("certifikatiPrevzet", certifikatiPrevzet);
        modelAndView.addObject("certifikatPreklican", certifikatPreklican);
        modelAndView.addObject("certifikatPretekel", certifikatPretekel);

        modelAndView.addObject("karticaSkupaj", karticaSkupaj);
        modelAndView.addObject("karticaBrezCertifikata", karticeBrezCertifikata.size());
        modelAndView.addObject("kartice", karticeBrezCertifikata);
        modelAndView.addObject("karticeBrezImetnika", karticeBrezImetnika.size());
        modelAndView.addObject("karticeI", karticeBrezImetnika);

        modelAndView.addObject("neVeljavniCertifikatiBrezVrnjeneKartice", neVeljavniCertifikatiBrezVrnjeneKartice.size());
        modelAndView.addObject("certifikatiK", neVeljavniCertifikatiBrezVrnjeneKartice);



        return modelAndView;
    }

    @RequestMapping(value = "/updateCertificateJob/{pageNum}")
    public ModelAndView updateCertificate(@PathVariable Integer pageNum) {
        ModelAndView modelAndView = new ModelAndView("updateCertificateJob");
        modelAndView.addObject("jobLog", jobLogManager.findAll(pageNum));
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("izpisCount", (Math.ceil(jobLogManager.getCount() / (nastavitveHelper.getPrikazovNaStran() * 1.0))));
        modelAndView.addObject("prikazovNaStran", nastavitveHelper.getPrikazovNaStran());
        return modelAndView;
    }

    @RequestMapping(value = "/updateCertificateJob/process")
    public String updateCertificateProcess() {

        JobLog jobLog = new JobLog();
        jobLogManager.addJobLog(jobLog);

        String dateParam = new Date().toString();
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("date", dateParam);
        jobParametersBuilder.addLong("id", jobLog.getId());
        //JobParameters param = new JobParametersBuilder().addString("date", dateParam).toJobParameters();

        JobParameters param = jobParametersBuilder.toJobParameters();

        JobExecution execution = null;
        try {
            execution = jobLauncher.run(updateCertfJob, param);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (execution.getStartTime() != null) {
            jobLog.setStartDate(execution.getStartTime());
        }
        if (execution.getEndTime() != null) {
            jobLog.setEndDate(execution.getEndTime());
        }
        if (execution.getJobId() != null) {
            jobLog.setJobId(execution.getJobId());
        }
        if (execution.getStatus() != null) {
            jobLog.setStatus(execution.getStatus().toString());
        } else {
            jobLog.setStatus("FAILED");
        }
        if (execution.getStepExecutions() != null) {
            jobLog.setNumberOfUpdates(Long.parseLong(Integer.toString(execution.getStepExecutions().iterator().next().getWriteCount())));
        }
        jobLogManager.updateJobLog(jobLog);
        return "redirect:/updateCertificateJob/1";
    }

    /*  @RequestMapping(value = "/iskanje/process/")
     public ModelAndView iskanjeProcess(HttpSession session, @RequestParam(value = "iskano") String iskano, @ModelAttribute IskalniPogoji iskalniPogoji) {
     System.out.println("ISKANO:" + iskano);
     System.out.println("=>" + iskalniPogoji.getImetnikCrtnaKoda());        
     return new ModelAndView("iskanje");
     }
     */
    /*----------------------*/
    /*Metode*/
    /*----------------------*/
    public InputStream vrniFileCertifikatKartica(List<Certifikat> certifikati) throws IOException {
        StringWriter wr = new StringWriter();
        CSVWriter writer = new CSVWriter(wr, ';', '\0');
        String zapisGlava = nastavitveHelper.getCsvKarticaVrednostGlava();
        writer.writeNext(zapisGlava.split(";"));

        for (int i = 0; i < certifikati.size(); i++) {
            if (certifikati.get(i).getStatus().getSifra().compareTo("03") == 0 && certifikati.get(i).getKartica() != null) { //Prevzet
                String zapis = getCSVEntriesKartica(certifikati.get(i));
                String[] entries = zapis.split(";");
                writer.writeNext(entries);
            }
        }
        writer.close();
        ByteArrayInputStream input = new ByteArrayInputStream(wr.toString().getBytes(nastavitveHelper.getCsvIzvozEncoding()));
        return input;
    }

    private String getCSVEntriesKartica(Certifikat certifikat) {
        String retValue = "";
        Kartica kartica = certifikat.getKartica();
        try {
            String[] polja = nastavitveHelper.getCsvKarticaVrednostVsebina().split(";");
            for (int i = 0; i < polja.length; i++) {
                if (polja[i].compareTo("VRSTAOPREME") == 0) {
                    retValue += "kartica;";
                } else if (polja[i].compareTo("TIPOPREME") == 0) {
                    retValue += "Gemalto .NET VSRS;";
                } else {
                    if (polja[i].split(",").length == 2) {
                        String[] pol = polja[i].split(",")[0].split("\\.");
                        retValue += getRetValue(certifikat, kartica, pol, i) + ",";
                        pol = polja[i].split(",")[1].split("\\.");
                        retValue += getRetValue(certifikat, kartica, pol, i) + ";";
                    } else {
                        retValue += getRetValue(certifikat, kartica, polja[i].split("\\."), i) + ";";
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return retValue.substring(0, retValue.length() - 1);
    }

    private String getRetValue(Certifikat certifikat, Kartica kartica, String[] pol, int i) throws Exception {
        String getMetoda = metodeHelper.getMethodName(pol[1]);
        Object object;
        if (pol[0].compareTo("certifikat") == 0) {
            Method method = certifikat.getClass().getMethod(getMetoda, (Class<?>[]) null);
            object = method.invoke(certifikat, (Object[]) null);
        } else {
            Method method = kartica.getClass().getMethod(getMetoda, (Class<?>[]) null);
            object = method.invoke(kartica, (Object[]) null);
        }
        if (getMetoda.length() >= 8 && getMetoda.substring(0, 8).compareTo("getDatum") == 0) {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            String d = df.format(object);
            object = df.format(object);
        }

        return object.toString();
    }

    public InputStream vrniFileImetniki(Zahtevek zahtevek, List<Imetnik> imetniki) throws IOException {
        StringWriter wr = new StringWriter();
        CSVWriter writer = new CSVWriter(wr, ';', '\0');
        String zapisGlava = nastavitveHelper.getCsvVrednostGlava();
        writer.writeNext(zapisGlava.split(";"));
        for (int i = 0; i < imetniki.size(); i++) {
            if (imetniki.get(i).getStatus().getSifraSIm().compareTo("06") != 0) { //Da ni zavrnen
                String zapis = getCSVEntries(zahtevek, imetniki.get(i));
                String[] entries = zapis.split(";");
                writer.writeNext(entries);
            }
        }
        writer.close();
        ByteArrayInputStream input = new ByteArrayInputStream(wr.toString().getBytes(nastavitveHelper.getCsvIzvozEncoding()));
        return input;
    }

    private void setResponseZIP(HttpServletResponse response, String ime) throws IOException {
        response.setContentType("application/zip;charset=UTF-16");
        response.addHeader("Content-Disposition", "attachment; filename=" + ime + ".zip");
        response.addHeader("Content-Transfer-Encoding", "binary");
    }

    private void generateZipForIzvoz(HttpServletResponse response, List<Certifikat> certifikati) throws IOException {
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        byte[] buf = new byte[1024];
        InputStream in;

        in = vrniFileCertifikatKartica(certifikati);
        out.putNextEntry(new ZipEntry(new Date().getTime() + ".csv"));
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.closeEntry();
        in.close();
        out.close();
    }

    //Iz nastavitev prebere katera polja so v CSV datoteki in generira string s temi vrednostmi
    //Ce polje vsebuje piko, vzame podatek iz zahtevka => zahtevek.crtnaKoda = crtna koda iz zahtevka
    private String getCSVEntries(Zahtevek zahtevek, Imetnik imetnik) {
        String retValue = "";
        try {
            String[] polja = nastavitveHelper.getCsvVrednostVsebina().split(";");
            for (int i = 0; i < polja.length; i++) {
                String pol = polja[i];
                if (pol.contains(".")) {
                    pol = pol.split("\\.")[1];
                    String getMetoda = metodeHelper.getMethodName(pol);
                    Method method = zahtevek.getClass().getMethod(getMetoda, (Class<?>[]) null);
                    Object object = method.invoke(zahtevek, (Object[]) null);
                    retValue += object + ";";
                } else {
                    String getMetoda = metodeHelper.getMethodName(pol);
                    Method method = imetnik.getClass().getMethod(getMetoda, (Class<?>[]) null);
                    Object object = method.invoke(imetnik, (Object[]) null);
                    //PotrebujeCitalec v datoteki je vedno NE
                    if (getMetoda.compareTo("getImaCitalec") == 0) {
                        object = "NE";
                    }
                    retValue += object + ";";
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return retValue.substring(0, retValue.length() - 1);
    }
}
