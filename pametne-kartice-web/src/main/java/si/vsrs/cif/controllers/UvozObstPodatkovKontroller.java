/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.controllers;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import si.vsrs.cif.helper.MetodeHelper;
import si.vsrs.cif.helper.NastavitveHelper;
import si.vsrs.cif.managers.CertifikatRepoManager;
import si.vsrs.cif.managers.KarticaRepoManager;
import si.vsrs.cif.managers.SeznamCitalcevSigovcaRepoManager;
import si.vsrs.cif.managers.SeznamKarticSigovcaRepoManager;
import si.vsrs.cif.mod.Certifikat;
import si.vsrs.cif.mod.SeznamCitalcevSigovca;
import si.vsrs.cif.mod.SeznamKarticSigovca;

/**
 *
 * @author uporabnik
 */
@Controller
@PreAuthorize("hasRole('002')")
public class UvozObstPodatkovKontroller {

    @Autowired
    NastavitveHelper nastavitveHelper;
    @Autowired
    MetodeHelper metodeHelper;
    @Autowired
    CertifikatRepoManager certifikatRepoManager;
    @Autowired
    KarticaRepoManager karticaRepoManager;
    @Autowired
    SeznamKarticSigovcaRepoManager seznakKarticSigovcaRepoManager;
    @Autowired
    SeznamCitalcevSigovcaRepoManager seznamCitalcevSigovcaRepoManager;


    /*-------------------------CERTIFIKATI-------------------------*/
    @RequestMapping(value = "/vnosCertfObstj")
    public ModelAndView vnosCertfObstj(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("vnosCertifikataObstj");
        List<Certifikat> certifikatiBrezSodisca = certifikatRepoManager.findBySifraSodiscaIsNull();
        modelAndView.addObject("certifikatiBrezSodisca", certifikatiBrezSodisca);
        return modelAndView;
    }

    @RequestMapping(value = "/vnosCertfObstj/process", method = RequestMethod.POST)
    public ModelAndView vnosCertfObstjProcess(@RequestParam("myfile") MultipartFile myFile, HttpSession session) throws FileNotFoundException, IOException, InterruptedException {
         ModelAndView modelAndView = new ModelAndView("vnosCertifikataObstj");
        CSVReader reader = new CSVReader(new InputStreamReader(myFile.getInputStream(), "windows-1250"), ';');
        int usp = 0, neusp = 0;
        List<String[]> entries = reader.readAll();
        List<Certifikat> certifikati = new ArrayList();
        if (entries.size() > 1 && nastavitveHelper.getCsvVrednostCertifikatObstj().split(";").length == entries.get(1).length) {
            for (int i = 1; i < entries.size(); i++) {
                Certifikat certifikat = setDataFromCSVCertifikatObstj(entries.get(i));
                if (certifikat != null && certifikat.getSerijskaStevilka() != null && !certifikat.getSerijskaStevilka().isEmpty() && !metodeHelper.isSerijskaIn(certifikati, certifikat.getSerijskaStevilka())) {                  
                    certifikati.add(certifikat);
                } else {
                    neusp++;
                }
            }
        } else {
            modelAndView.addObject("error", "Napaka pri branju datoteke");
            return modelAndView;
        }
        List<Integer> uspNeusp = certifikatRepoManager.addCertifikatFlushAndClear(certifikati);
        usp += uspNeusp.get(0);
        neusp += uspNeusp.get(1);
        int neNajdenih = uspNeusp.get(2);
        modelAndView.addObject("uspesno", usp);
        modelAndView.addObject("neuspesno", neusp);
        modelAndView.addObject("neNajdenih", neNajdenih);
        List<Certifikat> certifikatiBrezSodisca = certifikatRepoManager.findBySifraSodiscaIsNull();
        modelAndView.addObject("certifikatiBrezSodisca", certifikatiBrezSodisca);
        return modelAndView;
    }
    /*---------------------------------------------------------*/

    /*-------------------------KARTICE-------------------------*/
    @RequestMapping(value = "/vnosKartObstj")
    public ModelAndView vnosKartObstj(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("vnosKartObstj");
        List<SeznamKarticSigovca> seznamKarticBrezSodisca = seznakKarticSigovcaRepoManager.findBySifraSodiscaIsNull();
        modelAndView.addObject("seznamKarticSigovca", seznamKarticBrezSodisca);
        return modelAndView;
    }

    @RequestMapping(value = "/vnosKartObstj/process", method = RequestMethod.POST)
    public ModelAndView vnosKartObstjProcess(@RequestParam("myfile") MultipartFile myFile, HttpSession session) throws FileNotFoundException, IOException, InterruptedException {
        ModelAndView modelAndView = new ModelAndView("vnosKartObstj");
        CSVReader reader = new CSVReader(new InputStreamReader(myFile.getInputStream(), "windows-1250"), ';');
        int usp = 0, neusp = 0;
        List<String[]> entries = reader.readAll();
        List<SeznamKarticSigovca> seznamKarticSigovca = new ArrayList();
        if (entries.size() > 1 && nastavitveHelper.getCsvVrednostKarticaObstj().split(";").length == entries.get(1).length) {
            for (int i = 1; i < entries.size(); i++) {
                SeznamKarticSigovca kartica = setDataFromCSVKarticaObstj(entries.get(i));
                if (kartica != null) {
                    seznamKarticSigovca.add(kartica);
                } else {
                    neusp++;
                }
            }
        } else {
            modelAndView.addObject("error", "Napaka pri branju datoteke");
            return modelAndView;
        }
        List<Integer> uspNeusp = seznakKarticSigovcaRepoManager.addKarticaSigovcaFlushAndClear(seznamKarticSigovca);
        usp += uspNeusp.get(0);
        neusp += uspNeusp.get(1);
        int neNajdenih = uspNeusp.get(2);
        modelAndView.addObject("uspesno", usp);
        modelAndView.addObject("neuspesno", neusp);
        modelAndView.addObject("neNajdenih", neNajdenih);
        List<SeznamKarticSigovca> seznamKarticBrezSodisca = seznakKarticSigovcaRepoManager.findBySifraSodiscaIsNull();
        modelAndView.addObject("seznamKarticSigovca", seznamKarticBrezSodisca);
        return modelAndView;
    }

    /*---------------------------------------------------------*/
    /*-------------------------CITALCI-------------------------*/
    @RequestMapping(value = "/vnosCitalcevObstj")
    public ModelAndView vnosCitalcevObstj(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("vnosCitalcevObstj");
        List<SeznamCitalcevSigovca> seznamCitalcevBrezSodisca = seznamCitalcevSigovcaRepoManager.findBySifraSodiscaIsNull();
        modelAndView.addObject("seznamCitalcevSigovca", seznamCitalcevBrezSodisca);

        return modelAndView;
    }

    @RequestMapping(value = "/vnosCitalcevObstj/process", method = RequestMethod.POST)
    public ModelAndView vnosCitalcevObstjProcess(@RequestParam("myfile") MultipartFile myFile, HttpSession session) throws FileNotFoundException, IOException, InterruptedException {
        ModelAndView modelAndView = new ModelAndView("vnosCitalcevObstj");
        CSVReader reader = new CSVReader(new InputStreamReader(myFile.getInputStream(), "windows-1250"), ';');
        int usp = 0, neusp = 0;
        List<String[]> entries = reader.readAll();
        List<SeznamCitalcevSigovca> seznamCitalcevSigovca = new ArrayList();

        if (entries.size() > 1 && nastavitveHelper.getCsvVrednostCitalecObstj().split(";").length == entries.get(1).length) {
            for (int i = 1; i < entries.size(); i++) {
                SeznamCitalcevSigovca citalec = setDataFromCSVCitalecObstj(entries.get(i));
                if (citalec != null) {
                    seznamCitalcevSigovca.add(citalec);
                } else {
                    neusp++;
                }
            }
        } else {
            modelAndView.addObject("error", "Napaka pri branju datoteke");
            return modelAndView;
        }
        List<Integer> uspNeusp = seznamCitalcevSigovcaRepoManager.addCitalecSigovcaFlushAndClear(seznamCitalcevSigovca);
        usp += uspNeusp.get(0);
        neusp += uspNeusp.get(1);
        int neNajdenih = uspNeusp.get(2);
        modelAndView.addObject("uspesno", usp);
        modelAndView.addObject("neuspesno", neusp);
        modelAndView.addObject("neNajdenih", neNajdenih);
        List<SeznamCitalcevSigovca> seznamCitalcevBrezSodisca = seznamCitalcevSigovcaRepoManager.findBySifraSodiscaIsNull();
        modelAndView.addObject("seznamCitalcevSigovca", seznamCitalcevBrezSodisca);
        return modelAndView;
    }

    /*---------------------------------------------------------*/
    /*-------------------------METODE-------------------------*/
    private Certifikat setDataFromCSVCertifikatObstj(String[] podatki) {
        String[] polja = nastavitveHelper.getCsvVrednostCertifikatObstj().split(";");
        Certifikat certifikat = new Certifikat();
        podatki = increaseTableSize(podatki, polja.length);
        try {
            certifikat = (Certifikat) metodeHelper.setDataFromCSV(podatki, polja, "Certifikat", nastavitveHelper.getCertifikatPackage());
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return certifikat;
    }

    private SeznamKarticSigovca setDataFromCSVKarticaObstj(String[] podatki) {
        String[] polja = nastavitveHelper.getCsvVrednostKarticaObstj().split(";");
        SeznamKarticSigovca seznamKarticSigovca = new SeznamKarticSigovca();
        podatki = increaseTableSize(podatki, polja.length);
        try {
            seznamKarticSigovca = (SeznamKarticSigovca) metodeHelper.setDataFromCSV(podatki, polja, "SeznamKarticSigovca", nastavitveHelper.getKarticaObstjPackage());
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return seznamKarticSigovca;
    }

    private SeznamCitalcevSigovca setDataFromCSVCitalecObstj(String[] podatki) {
        String[] polja = nastavitveHelper.getCsvVrednostCitalecObstj().split(";");
        SeznamCitalcevSigovca seznamCitalcevSigovca = new SeznamCitalcevSigovca();
        podatki = increaseTableSize(podatki, polja.length);
        try {
            seznamCitalcevSigovca = (SeznamCitalcevSigovca) metodeHelper.setDataFromCSV(podatki, polja, "SeznamCitalcevSigovca", nastavitveHelper.getCitalecObstjPackage());
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return seznamCitalcevSigovca;
    }

    private String[] increaseTableSize(String[] tabela, int size) {
        String[] ret = new String[size];
        for (int i = 0; i < ret.length; i++) {
            if (i < tabela.length) {
                ret[i] = tabela[i];
            } else {
                ret[i] = "";
            }
        }
        return ret;
    }
}
