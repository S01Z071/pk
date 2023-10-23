/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.controllers;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.helper.NastavitveHelper;
import si.vsrs.cif.managers.CertifikatRepoManager;
import si.vsrs.cif.managers.ImetnikRepoManager;
import si.vsrs.cif.managers.KarticaRepoManager;
import si.vsrs.cif.managers.StatusCertifikatRepoManager;
import si.vsrs.cif.mod.Certifikat;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.Kartica;
import si.vsrs.cif.mod.SeznamCertifikatov;
import java.util.Scanner;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import si.vsrs.cif.helper.MetodeHelper;
import si.vsrs.cif.managers.SeznamCitalcevSigovcaRepoManager;
import si.vsrs.cif.managers.SeznamKarticSigovcaRepoManager;
import si.vsrs.cif.mod.KarticaInCertifikat;
import si.vsrs.cif.mod.SeznamCitalcevSigovca;
import si.vsrs.cif.mod.SeznamKarticInCertifikatov;
import si.vsrs.cif.mod.SeznamKarticSigovca;
import si.vsrs.cif.mod.web.Uporabnik;

/**
 *
 * @author uporabnik
 */
@Controller
@PreAuthorize("hasAnyRole('001,002')")
public class KartCertfController {

    @Autowired
    KarticaRepoManager karticaRepoManager;
    @Autowired
    CertifikatRepoManager certifikatRepoManager;
    @Autowired
    ImetnikRepoManager imetnikRepoManager;
    @Autowired
    NastavitveHelper nastavitveHelper;
    @Autowired
    StatusCertifikatRepoManager statusCertifikatRepoManager;
    @Autowired
    SeznamKarticSigovcaRepoManager seznamKarticSigovcaRepoManager;
    @Autowired
    SeznamCitalcevSigovcaRepoManager seznamCitalcevSigovcaRepoManager;
    @Autowired
    MetodeHelper metodeHelper;

    @RequestMapping(value = "/vnosKart")
    public ModelAndView vnosKartice() {
        ModelAndView modelAndView = new ModelAndView("vnosKartice");
        return modelAndView;
    }

    @RequestMapping(value = "/vnosCertf")
    public ModelAndView vnosCertifikata() {
        ModelAndView modelAndView = new ModelAndView("vnosCertifikata");
        return modelAndView;
    }

    @RequestMapping(value = "/vnosKart/process", method = RequestMethod.POST)
    public ModelAndView vnosKart(@RequestParam("myfile") MultipartFile myFile, HttpSession session) throws FileNotFoundException, IOException {
        ModelAndView modelAndView = new ModelAndView("vnosKartice");
        Scanner scanner = new Scanner(myFile.getInputStream());
        List<String[]> entries = new ArrayList();
        while (scanner.hasNext() == true) {
            String line = scanner.nextLine();
            String[] lineSpl = line.split("\t");
            if (lineSpl.length > 1) {
                entries.add(lineSpl);
            }
        }
        List<KarticaInCertifikat> karticeInCertifikati = new ArrayList();
        SeznamKarticInCertifikatov seznamKarticInCertifikatov = new SeznamKarticInCertifikatov();
        if (entries.size() > 0 && nastavitveHelper.getCsvVrednostKartica().split(";").length == entries.get(0).length) {
            for (int i = 0; i < entries.size(); i++) {
                try {
                    karticeInCertifikati.add(setDataFromCSVSKarticeInCertifikati(entries.get(i), nastavitveHelper.getCsvVrednostKartica().split(";")));
                } catch (Exception ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            modelAndView.addObject("error", "Napaka pri branju datoteke. Napačno število polj ali ni zapisov vrednosti. Število vrstic: " + entries.size() + ", število polj v nastavitvah: " + nastavitveHelper.getCsvVrednostKartica().split(";").length);
            return modelAndView;
        }
        seznamKarticInCertifikatov.setKarticaInCertifikat(karticeInCertifikati);
        modelAndView.addObject("seznamKarticInCertifikatov", seznamKarticInCertifikatov);
        return modelAndView;

    }

    @RequestMapping(value = "/vnosKart/process/confirm", method = RequestMethod.POST)
    public ModelAndView vnosConfirmKartica(@ModelAttribute SeznamKarticInCertifikatov seznamKarticInCertifikatov) {
        ModelAndView modelAndView = new ModelAndView("vnosKartice");
        List<KarticaInCertifikat> karticeInCertifikati = seznamKarticInCertifikatov.getKarticaInCertifikat();
        int karticaZe = 0, imetnikNi = 0, usp = 0, certfNe = 0, avtorStNapaka = 0;
        for (int i = 0; i < karticeInCertifikati.size(); i++) {
            Kartica kartica = karticeInCertifikati.get(i).getKartica();
            Certifikat certifikatTemp = karticeInCertifikati.get(i).getCertifikat();
            List<Kartica> karticaTempList = karticaRepoManager.findBySerijskaStevilkaKartice(kartica.getSerijskaStevilkaKartice());
            boolean karticaLahko = true;
            for (Kartica karticaTemp : karticaTempList) {
                if (karticaTemp != null && karticaTemp.getImetnik() != null && karticaTemp.getImetnik().getStatus().getSifraSIm().compareTo("09") != 0 && karticaTemp.getDatumVrnitve() == null) {
                    karticaZe++;
                    karticaLahko = false;
                    break;
                }
            }
            // if (karticaTemp != null && karticaTemp.getImetnik() != null && karticaTemp.getImetnik().getStatus().getSifraSIm().compareTo("09") != 0 && karticaTemp.getDatumVrnitve() == null) {
            //   karticaZe++;
            //}
            if (karticaLahko) {
                //               
                Imetnik imetnik = imetnikRepoManager.findByENaslovAndStatusSifraSIm(certifikatTemp.getENaslov(), "07");
                if (imetnik == null) {
                    imetnikNi++;
                } else {
                    if (certifikatRepoManager.findBySerijskaStevilka(certifikatTemp.getSerijskaStevilka()) != null) {
                        //User geslo in email se mora ujemati z Avtorizacijska št. in email                      
                        Certifikat certf = certifikatRepoManager.findBySerijskaStevilka(certifikatTemp.getSerijskaStevilka());
                        if (certf.getENaslov().compareTo(imetnik.getENaslov()) == 0 && kartica.getUserPass().toLowerCase().compareTo(certf.getAvtorizacijskaSt().toLowerCase()) == 0) {
                            kartica.setImetnik(imetnik);
                            karticaRepoManager.addKartica(kartica);
                            certifikatTemp.setStatus(statusCertifikatRepoManager.getStatusCertifikat("02"));
                            certifikatRepoManager.updateCertifikatFromCertifikat(certifikatTemp, kartica, imetnik);
                            usp++;
                        } else {
                            avtorStNapaka++;
                        }
                    } else {
                        certfNe++;
                    }
                }
            }
        }
        modelAndView.addObject("uspesno", usp);
        modelAndView.addObject("karticaZe", karticaZe);
        modelAndView.addObject("imetnikNi", imetnikNi);
        modelAndView.addObject("certfNe", certfNe);
        modelAndView.addObject("avtorStNapaka", avtorStNapaka);
        return modelAndView;
    }

    @RequestMapping(value = "/vnosCertf/process", method = RequestMethod.POST)
    public ModelAndView vnosCertf(@RequestParam("myfile") MultipartFile myFile, HttpSession session) throws FileNotFoundException, IOException {
        ModelAndView modelAndView = new ModelAndView("vnosCertifikata");
        int usp = 0, neusp = 0;
        CSVReader reader = new CSVReader(new InputStreamReader(myFile.getInputStream(), nastavitveHelper.getCsvVrednostCertifikatEncoding()), nastavitveHelper.getCsvVrednostCertifikatDelimiter());
        List<String[]> entries = reader.readAll();
        List<Certifikat> certifikati = new ArrayList();
        SeznamCertifikatov seznamCertifikatov = new SeznamCertifikatov();
        if (entries.size() > 1 && nastavitveHelper.getCsvVrednostCertifikat().split(";").length == entries.get(1).length) {
            for (int i = 1; i < entries.size(); i++) {
                Certifikat certifikat = setDataFromCSVCertifikat(entries.get(i));
                if (certifikat != null) {
                    certifikati.add(certifikat);
                    usp++;
                } else {
                    neusp++;
                }
            }
        } else {
            modelAndView.addObject("error", "Napaka pri branju datoteke. Napačno število polj ali ni zapisov vrednosti. Število vrstic: " + entries.size() + ", število polj v nastavitvah: " + nastavitveHelper.getCsvVrednostCertifikat().split(";").length);
            return modelAndView;
        }
        seznamCertifikatov.setCertifikati(certifikati);
        modelAndView.addObject("uspesnoBranje", usp);
        modelAndView.addObject("neuspesnoBranje", neusp);
        modelAndView.addObject("seznamCertifikatov", seznamCertifikatov);
        return modelAndView;
    }

    @RequestMapping(value = "/vnosCertf/process/confirm", method = RequestMethod.POST)
    public ModelAndView vnosConfirmCertifikat(@ModelAttribute SeznamCertifikatov seznamCertifikatov) {
        int usp = 0, neusp = 0;
        int povezanih = 0, nepovezanih = 0;
        for (int i = 0; i < seznamCertifikatov.getCertifikati().size(); i++) {
            Certifikat certifikat = seznamCertifikatov.getCertifikati().get(i);
            Imetnik imetnik = imetnikRepoManager.findByENaslovAndStatusSifraSIm(certifikat.getENaslov(), "07");
            if (certifikatRepoManager.findBySerijskaStevilka(certifikat.getSerijskaStevilka()) == null) {
                certifikat.setStatus(statusCertifikatRepoManager.getStatusCertifikat("01"));
                if (imetnik != null) {
                    certifikat.setImetnik(imetnik);
                    //nastavi sifro sodisca
                    povezanih++;
                } else {
                    nepovezanih++;
                }
                certifikatRepoManager.addCertifikat(certifikat);
                usp++;
            } else {
                neusp++;
            }
        }
        ModelAndView modelAndView = new ModelAndView("vnosCertifikata");
        modelAndView.addObject("seznamCertifikatov", seznamCertifikatov);
        modelAndView.addObject("uspesno", usp);
        modelAndView.addObject("neuspesno", neusp);
        modelAndView.addObject("povezanih", povezanih);
        modelAndView.addObject("nepovezanih", nepovezanih);
        return modelAndView;
    }

    /*Povezava imetnikov in certifikatov*/
    @RequestMapping(value = "/poveziCertfImetnik/{idC}")
    public ModelAndView poveziCertfImetnik(@PathVariable Integer idC) {
        Certifikat certifikat = certifikatRepoManager.findById(idC);
        Imetnik imetnik = imetnikRepoManager.findByENaslovAndStatusSifraSIm(certifikat.getENaslov(), "07");
        ModelAndView modelAndView = new ModelAndView("certfPrevzem");
        String msg = "";
        boolean povezi = true;
        if (povezi && certifikat.getImetnik() != null) {
            msg = "Certifikat je že povezan.";
            povezi = false;
        }
        if (povezi && imetnik == null) {
            msg = "Imetnik ni vnešen ali pa ni v statusu posredovano na SIGOV-CA.";
            povezi = false;
        }
        if (povezi && certifikat.getStatus().getSifra().compareTo("01") != 0) {
            msg = "Certifikat je že prevzet.";
            povezi = false;
        }
        if (!povezi) {
            List<Certifikat> certifikati = certifikatRepoManager.findByStatusSifraOrderByIdAsc("01");
            modelAndView.addObject("certifikati", certifikati);
            modelAndView.addObject("error", msg);
            return modelAndView;
        }
        certifikat.setImetnik(imetnik);
        certifikatRepoManager.updateCertifikat(certifikat);
        List<Certifikat> certifikati = certifikatRepoManager.findByStatusSifraOrderByIdAsc("01");
        modelAndView.addObject("certifikati", certifikati);
        return modelAndView;
    }

    /*Certifikati za prevzem*/
    @RequestMapping(value = "/certfPrevzem")
    public ModelAndView certfPrevzem() {
        ModelAndView modelAndView = new ModelAndView("certfPrevzem");
        List<Certifikat> certifikati = certifikatRepoManager.findByStatusSifraOrderByIdAsc("01");
        modelAndView.addObject("certifikati", certifikati);
        return modelAndView;
    }

    /*Pripravljene kartice*/
    @RequestMapping(value = "/pregledPripKartic")
    public ModelAndView pregledPripKartic() {
        ModelAndView modelAndView = new ModelAndView("pregledPripKartic");
        List<Certifikat> certifikati = certifikatRepoManager.findByStatusSifraOrderByIdAsc("02");
        modelAndView.addObject("certifikati", certifikati);
        return modelAndView;
    }

    @RequestMapping(value = "/iskPripKartic")
    public ModelAndView iskPripKartic() {
        ModelAndView modelAndView = new ModelAndView("iskPripKartic");
        return modelAndView;
    }

    @RequestMapping(value = "/iskPripKartic/process")
    public @ResponseBody
    ModelAndView iskPripKarticProcess(@RequestParam String barCode) {
        ModelAndView modelAndView = new ModelAndView("iskPripKartic");
        List<Certifikat> certifikati = certifikatRepoManager.findByStatusSifraAndKarticaBarcodeAndImetnikStatusSifraSIm("02", barCode, "07");
        modelAndView.addObject("certifikati", certifikati);
        modelAndView.addObject("natisni", 1);
        return modelAndView;
    }

    @RequestMapping(value = "/potrdiloPrejeto")
    public ModelAndView potrdiloPrejeto() {
        ModelAndView modelAndView = new ModelAndView("potrdiloPrejeto");
        return modelAndView;
    }

    @RequestMapping(value = "/potrdiloPrejeto/process")
    public @ResponseBody
    ModelAndView potrdiloPrejetoProcess(@RequestParam String barCode) {
        ModelAndView modelAndView = new ModelAndView("potrdiloPrejeto");
        List<Certifikat> certifikati = certifikatRepoManager.findByImetnikCrtnaKodaAndImetnikStatusSifraSIm(barCode, "08");
        modelAndView.addObject("certifikati", certifikati);
        return modelAndView;
    }

    @RequestMapping(value = "/potrdiloPrejetoPregled")
    public ModelAndView potrdiloPrejetoPregled() {
        ModelAndView modelAndView = new ModelAndView("potrdiloPrejetoPregled");
        List<Certifikat> certifikati = certifikatRepoManager.findByImetnikStatusSifraSImOrderByIdAsc("08");
        modelAndView.addObject("certifikati", certifikati);
        return modelAndView;
    }

    /*Pregled certifikatov po sodiscu*/
    @RequestMapping(value = "/pregledCertf")
    public ModelAndView pregledCertf(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("pregledCertf");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        List<String> sifre = new ArrayList();
        String sodisceSifra = uporabnik.getIzbranaVlogaSodisce().getSodisce().getId();
        String vloga = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        if (vloga.compareTo("002") == 0 || vloga.compareTo("005") == 0 || vloga.compareTo("006") == 0) {
            if (session.getAttribute("sodisceSifra") == null) {
                session.setAttribute("sodisceSifra", sodisceSifra);
            }
            sodisceSifra = session.getAttribute("sodisceSifra").toString();
            sifre.add("01");
            sifre.add("02");
            sifre.add("03");
            sifre.add("04");
            sifre.add("05");
            sifre.add("06");
            sifre.add("07");
        } else {
            sifre.add("03");
        }
        List<Certifikat> certifikati = certifikatRepoManager.findByStatusSifraInAndSifraSodisca(sifre, sodisceSifra);
        certifikati = metodeHelper.updateCertifikatStatus(certifikati, statusCertifikatRepoManager, certifikatRepoManager);
        modelAndView.addObject("certifikati", certifikati);
        return modelAndView;
    }

    @RequestMapping(value = "/AdminIskanjeCertf")
    public @ResponseBody
    ModelAndView adminConfirmIskanje(HttpSession session, @RequestParam String sif) {
        if (sif == null || sif.isEmpty()) {
            return pregledCertf(session);
        }
        session.setAttribute("sodisceSifra", sif.toUpperCase());
        return pregledCertf(session);
    }

    /*Pregled kartic sigovca po sodiscu*/
    @RequestMapping(value = "/pregledKarticSigovca")
    public ModelAndView pregledKarticSigovca(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("pregledKarticSigovca");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        String sodisceSifra = uporabnik.getIzbranaVlogaSodisce().getSodisce().getId();
        List<SeznamKarticSigovca> seznamKarticSigovca = new ArrayList();
        String vloga = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        if (vloga.compareTo("002") == 0 || vloga.compareTo("005") == 0 || vloga.compareTo("006") == 0) {
            if (session.getAttribute("sodisceSifra") == null) {
                session.setAttribute("sodisceSifra", sodisceSifra);
            }
            sodisceSifra = session.getAttribute("sodisceSifra").toString();
            boolean vrnjeno = (session.getAttribute("vrnjeno") != null && session.getAttribute("vrnjeno").toString().compareTo("1") == 0);
            boolean izdano = (session.getAttribute("izdano") != null && session.getAttribute("izdano").toString().compareTo("1") == 0);
            modelAndView.addObject("vrnjeno", vrnjeno);
            modelAndView.addObject("izdano", izdano);
            seznamKarticSigovca = seznamKarticSigovcaRepoManager.getSeznamKarticSigovca(sodisceSifra, vrnjeno, izdano);
        } else {
            seznamKarticSigovca = seznamKarticSigovcaRepoManager.getSeznamKarticSigovca(sodisceSifra, false, true);
        }
        modelAndView.addObject("skupajKartic", seznamKarticSigovca.size());
        modelAndView.addObject("seznamKarticSigovca", metodeHelper.sortKarticeSigovca(seznamKarticSigovca));
        return modelAndView;
    }

    @RequestMapping(value = "/AdminIskanjeKartSig")
    public @ResponseBody
    ModelAndView AdminIskanjeKartSig(HttpSession session, @RequestParam String sif, @RequestParam(value = "vrnjeno", required = false) Boolean vrnjeno, @RequestParam(value = "izdano", required = false) Boolean izdano) {
        session.setAttribute("vrnjeno", "0");
        session.setAttribute("izdano", "0");
        if (vrnjeno != null && vrnjeno) {
            session.setAttribute("vrnjeno", "1");
        }
        if (izdano != null && izdano) {
            session.setAttribute("izdano", "1");
        }
        if (sif == null || sif.isEmpty()) {
            return pregledKarticSigovca(session);
        }
        session.setAttribute("sodisceSifra", sif.toUpperCase());
        return pregledKarticSigovca(session);
    }

    /*Pregled citalcev sigovca po sodiscu*/
    @RequestMapping(value = "/pregledCitalcevSigovca")
    public ModelAndView pregledCitalcevSigovca(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("pregledCitalcevSigovca");
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");

        String sodisceSifra = uporabnik.getIzbranaVlogaSodisce().getSodisce().getId();
        String vloga = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        List<SeznamCitalcevSigovca> seznamCitalcevSigovca = new ArrayList();
        if (vloga.compareTo("002") == 0 || vloga.compareTo("005") == 0 || vloga.compareTo("006") == 0) {
            if (session.getAttribute("sodisceSifra") == null) {
                session.setAttribute("sodisceSifra", sodisceSifra);
            }
            sodisceSifra = session.getAttribute("sodisceSifra").toString();
            boolean vrnjeno = (session.getAttribute("vrnjeno") != null && session.getAttribute("vrnjeno").toString().compareTo("1") == 0);
            boolean izdano = (session.getAttribute("izdano") != null && session.getAttribute("izdano").toString().compareTo("1") == 0);
            modelAndView.addObject("vrnjeno", vrnjeno);
            modelAndView.addObject("izdano", izdano);
            seznamCitalcevSigovca = seznamCitalcevSigovcaRepoManager.getSeznamCitalcevSigovca(sodisceSifra, vrnjeno, izdano);
        } else {
            seznamCitalcevSigovca = seznamCitalcevSigovcaRepoManager.getSeznamCitalcevSigovca(sodisceSifra, false, true);
        }
        modelAndView.addObject("skupajCitalcev", seznamCitalcevSigovca.size());
        modelAndView.addObject("seznamCitalcevSigovca", metodeHelper.sortCitalceSigovca(seznamCitalcevSigovca));
        return modelAndView;
    }

    @RequestMapping(value = "/AdminIskanjeCitSig")
    public @ResponseBody
    ModelAndView AdminIskanjeCitSig(HttpSession session, @RequestParam String sif, @RequestParam(value = "vrnjeno", required = false) Boolean vrnjeno, @RequestParam(value = "izdano", required = false) Boolean izdano) {
        session.setAttribute("vrnjeno", "0");
        session.setAttribute("izdano", "0");
        if (vrnjeno != null && vrnjeno) {
            session.setAttribute("vrnjeno", "1");
        }
        if (izdano != null && izdano) {
            session.setAttribute("izdano", "1");
        }
        if (sif == null || sif.isEmpty()) {
            return pregledCitalcevSigovca(session);
        }
        session.setAttribute("sodisceSifra", sif.toUpperCase());
        return pregledCitalcevSigovca(session);
    }

    @RequestMapping(value = "/pregledKarticVSRS/{pageNum}")
    public ModelAndView pregledKarticVSRS(@PathVariable Integer pageNum) {
        ModelAndView modelAndView = new ModelAndView("pregledKarticVSRS");
        List<Kartica> kartice = karticaRepoManager.getKartice(pageNum);
        modelAndView.addObject("kartice", kartice);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("izpisCount", (Math.ceil(karticaRepoManager.getKarticeCount() / (nastavitveHelper.getPrikazovNaStran() * 1.0))));
        modelAndView.addObject("prikazovNaStran", nastavitveHelper.getPrikazovNaStran());
        return modelAndView;
    }

    @RequestMapping(value = "/pregledKarticVSRS/iskanje")
    public ModelAndView pregledKarticVSRSIskanje(@RequestParam(value = "iskano", required = false) String iskano, @RequestParam(value = "datumOd", required = false) String datumOd, @RequestParam(value = "datumDo", required = false) String datumDo) {
        if (iskano == null || iskano.isEmpty()) {
            return new ModelAndView("redirect:/pregledKarticVSRS/1");
        }
        ModelAndView modelAndView = new ModelAndView("pregledKarticVSRS");
        if (!metodeHelper.isDateOk(datumOd) || !metodeHelper.isDateOk(datumDo)) {
            modelAndView.addObject("error", "Datum ni pravilne oblike.");
            return modelAndView;
        }
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        if ((datumDo == null || datumDo.isEmpty()) && (datumOd != null && !datumOd.isEmpty())) {
            datumDo = df.format(new Date());
        }
        if ((datumOd == null || datumOd.isEmpty()) && (datumDo != null && !datumDo.isEmpty())) {
            datumOd = "01.10.2013";
        }

        List<Kartica> kartice = new ArrayList();
        if ((datumDo == null || datumDo.isEmpty()) && (datumOd == null || datumOd.isEmpty())) {
            kartice = karticaRepoManager.findKartica(iskano, false);
        } else {
            kartice = karticaRepoManager.findKarticaByDatumVrnitve(iskano, datumOd, datumDo);
        }

        modelAndView.addObject("kartice", kartice);
        modelAndView.addObject("pageNum", 1);
        modelAndView.addObject("izpisCount", 1);
        modelAndView.addObject("prikazovNaStran", nastavitveHelper.getPrikazovNaStran());
        modelAndView.addObject("iskano", iskano);
        modelAndView.addObject("datumOd", datumOd);
        modelAndView.addObject("datumDo", datumDo);
        if (kartice.isEmpty()) {
            modelAndView.addObject("error", "Ni zadetkov.");
        }
        return modelAndView;
    }

    /*----------------------*/
    private Certifikat setDataFromCSVCertifikat(String[] podatki) {
        String[] polja = nastavitveHelper.getCsvVrednostCertifikat().split(";");
        Certifikat certifikat = new Certifikat();
        if (polja.length != podatki.length) {
            return certifikat;
        }
        try {
            certifikat = (Certifikat) metodeHelper.setDataFromCSV(podatki, polja, "Certifikat", nastavitveHelper.getCertifikatPackage());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(AdminController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return certifikat;
    }

    private KarticaInCertifikat setDataFromCSVSKarticeInCertifikati(String[] podatki, String[] polja) {
        KarticaInCertifikat karticaInCertifikat = new KarticaInCertifikat();
        Kartica kartica = new Kartica();
        Certifikat certifikat = new Certifikat();
        try {
            for (int i = 0; i < polja.length; i++) {
                String[] polOba = polja[i].split("\\.");
                String pol = polOba[1];
                String setMetoda = metodeHelper.getMethodNameSetter(pol);
                Method method;
                if (setMetoda.length() >= 8 && setMetoda.substring(0, 8).compareTo("setDatum") == 0) {
                    String datestr = "";
                    if (i < polja.length - 1 && polja[i].compareTo(polja[i + 1]) == 0) {
                        datestr = podatki[i] + " " + podatki[i + 1];
                        i++;
                    } else {
                        datestr = podatki[i] + " 00:00:00";
                    }
                    DateFormat formatter;
                    Date date;
                    formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                    try {
                        date = (Date) formatter.parse(datestr);
                        if (polOba[0].compareTo("kartica") == 0) {
                            method = kartica.getClass().getMethod(setMetoda, Date.class);
                            method.invoke(kartica, date);
                        } else {
                            method = certifikat.getClass().getMethod(setMetoda, Date.class);
                            method.invoke(certifikat, date);
                        }
                    } catch (ParseException ex) {
                    }
                } else {
                    if (polOba[0].compareTo("kartica") == 0) {
                        method = kartica.getClass().getMethod(setMetoda, String.class);
                        method.invoke(kartica, podatki[i]);
                    } else {
                        method = certifikat.getClass().getMethod(setMetoda, String.class);
                        method.invoke(certifikat, podatki[i]);
                    }
                }
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        karticaInCertifikat.setKartica(kartica);
        karticaInCertifikat.setCertifikat(certifikat);
        return karticaInCertifikat;
    }
}
