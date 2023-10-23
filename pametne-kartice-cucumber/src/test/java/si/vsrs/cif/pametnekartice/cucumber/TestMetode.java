/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.pametnekartice.cucumber;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import si.vsrs.cif.pametnekartice.cucumber.at.Metode;
import static si.vsrs.cif.pametnekartice.cucumber.at.WaitMethods.waitElementById;
import static si.vsrs.cif.pametnekartice.cucumber.at.WaitMethods.waitElementToHide;

/**
 *
 * @author uporabnik
 */
public class TestMetode extends Metode {

    @Before
    public void setUp(Scenario scenario) throws Exception {
        startSelenium();
        this.scenario = scenario;


    }

    @After
    public void tearDown() throws Exception {
        // driver.close();
        // driver.quit();
        //Runtime.getRuntime().exec("taskkill /f /t /im firefox.exe");
        // selenium.close();
        driver.close();
        driver.quit();
    }

    //Nastavitve za testno okolje//
    @Given("^Nastavi spletno stran \"([^\"]*)\"$")
    public void Nastavi_spletno_stran(String spletnaStran) throws Throwable {
        Shrani.addVrednost(spletnaStran, "spletnaStran");
    }

    @Given("^Namesto uporabniškega imena \"([^\"]*)\" uporabi uporabniško ime \"([^\"]*)\"$")
    public void Namesto_uporabniškega_imena_uporabi_uporabniško_ime(String uporabniskoIme, String novoUporabniskoIme) throws Throwable {
        if (uporabniskoIme.compareTo("*") == 0) {
            Shrani.addVrednost(novoUporabniskoIme, "uporabniskoVSE");
        } else {
            Shrani.addVrednost(novoUporabniskoIme, uporabniskoIme);
        }
    }

    @Given("^Namesto gesla \"([^\"]*)\" uporabi geslo \"([^\"]*)\"$")
    public void Namesto_gesla_uporabi_geslo(String geslo, String novoGeslo) throws Throwable {
        if (geslo.compareTo("*") == 0) {
            Shrani.addVrednost(novoGeslo, "gesloVSE");
        } else {
            Shrani.addVrednost(novoGeslo, geslo);
        }
    }

    @Given("^Namesto vloge \"([^\"]*)\" uporabi vlogo \"([^\"]*)\"$")
    public void Namesto_vloge_uporabi_vlogo(String vloga, String novaVloga) throws Throwable {
        Shrani.addVrednost(novaVloga, vloga);
    }
//

    @Given("^Odpri spletno stran \"([^\"]*)\" in se prijavi z uporabniškim imenom \"([^\"]*)\" in geslom \"([^\"]*)\"$")
    public void Odpri_spletno_stran_in_se_prijavi_z_uporabniškim_imenom_in_geslom(String spletnaStran, String username, String pass) throws Throwable {
        if (Shrani.getVrednost("spletnaStran").compareTo("spletnaStran") != 0) {
            driver.get(Shrani.getVrednost("spletnaStran"));
        } else {
            driver.get(spletnaStran);
        }

        String uporabniskoIme = username;
        if (Shrani.getVrednost("uporabniskoVSE").compareTo("uporabniskoVSE") != 0) {
            uporabniskoIme = Shrani.getVrednost("uporabniskoVSE");
        } else if (Shrani.getVrednost(username).compareTo(username) != 0) {
            uporabniskoIme = Shrani.getVrednost(username);
        }

        String geslo = pass;
        if (Shrani.getVrednost("gesloVSE").compareTo("gesloVSE") != 0) {
            geslo = Shrani.getVrednost("gesloVSE");
        } else if (Shrani.getVrednost(pass).compareTo(pass) != 0) {
            geslo = Shrani.getVrednost(pass);
        }

        sendKeysById("username", uporabniskoIme);
        sendKeysById("password", geslo);
        clickById("prijava");
    }

    @Given("^Preveri naslov strani \"([^\"]*)\"$")
    public void Preveri_naslov_strani(String title) throws Throwable {
        waitElementById("header");
        Assert.assertEquals(title, driver.getTitle());
    }

    @Given("^Odpri spletno stran \"([^\"]*)\" in se prijavi z uporabniškim imenom \"([^\"]*)\" in geslom \"([^\"]*)\" ter izberi sodišče in vlogo \"([^\"]*)\"$")
    public void odpri_spletno_stran_in_se_prijavi_z_uporabniškim_imenom_in_geslom_ter_izberi_sodišče_in_vlogo(String spletnaStran, String username, String pass, String vloga) throws Throwable {
        Odpri_spletno_stran_in_se_prijavi_z_uporabniškim_imenom_in_geslom(spletnaStran, username, pass);
        String vlogaNova = vloga;
        if (Shrani.getVrednost(vloga).compareTo(vloga) != 0) {
            vlogaNova = Shrani.getVrednost(vloga);
        }
        selecValueById("izbiranje", vlogaNova);
        clickById("izberi");
    }

    //Deluje za navadnega uporabnika, ker gre na pregled zahtevkov in si zapomni crtno kodo od zadnjega vnesenega zahtevka
    @Given("^Vnesi novo vlogo \"([^\"]*)\"$")
    public void Vnesi_novo_vlogo(String kljuc, List<Map<String, String>> data) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Nova vloga')]");
        clickById("novaVloga");
        waitElementToHide("loadingDiv");
        if (data.get(0).entrySet().size() > 1) {
            for (Map.Entry entry : data.get(0).entrySet()) {
                sendKeysById(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        clickById("mailAvtoVnosP");
        clickById("mailAvtoVnosK");
        clickById("potrdi");
        zapomniSiCrtnoKodoZahtevka(kljuc);
    }

    @Given("^Dodaj novega imetnika \"([^\"]*)\" na zahtevek \"([^\"]*)\"$")
    public void Dodaj_novega_imetnika_na_zahtevek(String kljucImetnik, String kljuc, List<Map<String, String>> data) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);
        clickById("editButton");
        clickById("addImetnikButton");
        for (Map.Entry entry : data.get(0).entrySet()) {
            if (entry.getValue().toString().compareTo("KLIKNI") == 0) {
                clickById(entry.getKey().toString());
            } else {
                sendKeysById(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        clickById("mailAvtoVnos");
        clickById("potrdi");

        if (kljucImetnik != null) {
            zapomniSiCrtnoKodoImetnika(kljucImetnik);
        }

    }

    @Given("^Dodaj novega imetnika na zahtevek \"([^\"]*)\" in preveri \"([^\"]*)\" sporočilo \"([^\"]*)\"$")
    public void Dodaj_novega_imetnika_na_zahtevek_in_preveri_sporočilo(String kljuc, String text, String msg, List<Map<String, String>> data) throws Throwable {
        Dodaj_novega_imetnika_na_zahtevek(null, kljuc, data);
        String xPath = "//label[contains(text(),'" + text + "')]/../../div[2]/span |  //span[@id='" + text + ".errors']";
        checkMsgByXpath(xPath, msg);
        clickByXPath(true, "//a[contains(text(),'Prekliči')]");
    }

    @Given("^Uredi imetnika \"([^\"]*)\"$")
    public void Uredi_imetnika(String kljuc, List<Map<String, String>> data) throws Throwable {
        Pojdi_na_podroben_pregled_imetnika(kljuc);
        String xPath = "//td[contains(text(),'Ime in priimek:')]/../td[2]";
        waitElementByXPath(xPath);
        String imePriimek = driver.findElement(By.xpath(xPath)).getText();
        clickById("PregledZahtevka");
        clickById("editButton");
        clickByXPath(true, "//div[contains(text(),'" + imePriimek + "')]/../div[3]/a");

        for (Map.Entry entry : data.get(0).entrySet()) {
            if (entry.getValue().toString().compareTo("KLIKNI") == 0) {
                clickById(entry.getKey().toString());
            } else {
                sendKeysById(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        clickById("mailAvtoVnos");
        clickById("potrdi");

    }

    @Given("^Uredi imetnika \"([^\"]*)\" in preveri \"([^\"]*)\" sporočilo \"([^\"]*)\"$")
    public void Uredi_imetnika_in_preveri_sporočilo(String kljuc, String text, String msg, List<Map<String, String>> data) throws Throwable {
        Uredi_imetnika(kljuc, data);
        String xPath = "//label[contains(text(),'" + text + "')]/../../div[2]/span |  //span[@id='" + text + ".errors']";
        checkMsgByXpath(xPath, msg);
        clickByXPath(true, "//a[contains(text(),'Prekliči')]");
    }

    @Given("^Izbriši imetnika \"([^\"]*)\" iz zahtevka \"([^\"]*)\"$")
    public void Izbriši_imetnika(String ime, String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);
        clickById("editButton");
        clickByXPath(true, "//div[contains(text(),'" + ime + "')]/../div[3]/button");
        clickByXPath(true, "//div[contains(text(),'" + ime + "')]/../div[3]/div/div[2]/a");

    }

    //Iskanje cez vse strani!
    @Given("^Izbriši zahtevek \"([^\"]*)\"$")
    public void Izbriši_zahtevek(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);
        clickById("deleteButton");
        waitElementById("brisiVlogo");
        clickById("potrdiBrisanje");
        waitElementToHide("brisiVlogo");

    }

    @Given("^Vnesi opombo \"([^\"]*)\" z naslovom \"([^\"]*)\" na zahtevek \"([^\"]*)\"$")
    public void Vnesi_opombo_z_naslovom_na_zahtevek(String vsebina, String naslov, String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);
        clickById("dodajSporociloButton");
        sendKeysById("naslov", naslov);
        sendKeysById("vsebina", vsebina);
        clickById("potrdi");

    }

    @Given("^Potrdi vlogo \"([^\"]*)\"$")
    public void Potrdi_vlogo(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);
        clickById("confirmButton");
    }

    @Given("^Odkleni zahtevek \"([^\"]*)\"$")
    public void Odkleni_zahtevek(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);
        clickById("unlockButton");
    }

    @Given("^Posreduj na CIF zahtevek \"([^\"]*)\" in preveri sporočilo \"([^\"]*)\"$")
    public void Posreduj_na_CIF_zahtevek_in_preveri_sporočilo(String kljuc, String msg) throws Throwable {
        Posreduj_na_CIF_zahtevek(kljuc);
        String xPath = "//div[@id='error']";
        checkMsgByXpath(xPath, msg);
    }

    @Given("^Natisni vse imetnike na zahtevku \"([^\"]*)\"$")
    public void Natisni_vse_imetnike_na_zahtevku(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);
        clickById("printIButton");

    }

    @Given("^Natisni zahtevek \"([^\"]*)\"$")
    public void Natisni_zahtevek(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);
        clickById("printButton");
    }

    @Given("^Posreduj na CIF zahtevek \"([^\"]*)\"$")
    public void Posreduj_na_CIF_zahtevek(String kljuc) throws Throwable {
        // poisciInKlikniByXpath("//strong[contains(text(),'" + Shrani.getVrednost(kljuc) + "')]/../../td[3]/a", "Naslednja");
        odpriPodrobenPregledZahtevka(kljuc);
        clickById("sendButton");
        clickById("potrdiPosredovanje");
    }

    @Given("^Poišči imetnika \"([^\"]*)\" po besedi \"([^\"]*)\"$")
    public void Poišči_imetnika_po_besedi(String imeInPriimek, String iskanje) throws Throwable {
        clickById("pregledImetnikov");
        sendKeysById("iskano", iskanje);
        clickById("Isci");
        String xPath = "//td[contains(text(),'" + imeInPriimek + "')]";
        try {
            waitElementsByXPath(xPath);
            Assert.assertTrue(driver.findElements(By.xpath(xPath)).size() >= 1);
        } catch (Exception e) {
            if (!imeInPriimek.isEmpty()) {
                narediScreenShot(scenario);
                scenario.write("Napaka pri iskanju imetnika.");
                throw new Exception(e.getMessage());
            }
        }
    }

    @Given("^Potrdi enega imetnika na zahtevku \"([^\"]*)\"$")
    public void Potrdi_enega_imetnika_na_zahtevku(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);
        waitElementsByXPath("//button[contains(@id,'potrdiImetnik')]");
        driver.findElements(By.xpath("//button[contains(@id,'potrdiImetnik')]")).get(0).click();
        //clickByXPath("//button[contains(@id,'potrdiImetnik')][1]");
    }

    @Given("^Potrdi vse imetnike na zahtevku \"([^\"]*)\"$")
    public void Potrdi_vse_imetnike_na_zahtevku(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);
        String xPath = "//button[contains(@id,'potrdiImetnik')]";
        int i = 0;
        for (;;) {
            try {
                waitElementsByXPath(xPath);
                WebElement el = driver.findElements(By.xpath(xPath)).get(i);
                i++;
                if (el.isDisplayed() && el.isEnabled()) {
                    el.click();
                }
            } catch (Exception e) {
                break;
            }
        }
    }

    @Given("^Potrdi zahtevek \"([^\"]*)\" in preveri sporočilo \"([^\"]*)\"$")
    public void Potrdi_zahtevek_in_preveri_sporočilo(String kljuc, String msg) throws Throwable {
        Potrdi_zahtevek(kljuc);
        checkMsgByXpath("//div[@id='error']", msg);
    }


    @Given("^Potrdi zahtevek \"([^\"]*)\"$")
    public void Potrdi_zahtevek(String kljuc) throws Throwable {
        //Potrdi_vse_imetnike_na_zahtevku(kljuc);
        odpriPodrobenPregledZahtevka(kljuc);
        clickById("potrdiZahtevekButton");

    }





    /*
    for clicking all buttons to confirm all the "imetnike";

    @Given("^Potrdi zahtevek \"([^\"]*)\"$")
    public void Potrdi_zahtevek(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);

        int i = 0;
        String confirmationButtonId = "potrdiImetnik" + i;

        while (driver.findElements(By.id(confirmationButtonId)).size() > 0) {
            WebElement confirmationButton = driver.findElement(By.id(confirmationButtonId));

            // Check if the confirmation button is disabled
            if (confirmationButton.isEnabled()) {
                confirmationButton.click();
                waitElementToHide("loadingDiv");

                // Optional: Provide a confirmation reason
                sendKeysById("opombaTextArea", "Confirmation Reason");
                clickById("potrdiImetnikPotrditev");
                waitElementToHide("potrdiImetnikPotrditev");

            }

            i++;
            confirmationButtonId = "potrdiImetnik" + i;
        }

        // Continue with the rest of the "zahtevek" confirmation logic if needed
    }

     */

    @Given("^Zavrni zahtevek \"([^\"]*)\" z razlogom zavrnitve \"([^\"]*)\" in preveri sporočilo \"([^\"]*)\"$")
    public void Zavrni_zahtevek_z_razlogom_zavrnitve_in_preveri_sporočilo(String kljuc, String razlog, String msg) throws Throwable {
        Zavrni_zahtevek_z_razlogom_zavrnitve(kljuc, razlog);
        checkMsgByXpath("//div[@id='error']", msg);
    }

    @Given("^Zavrni vse imetnike na zahtevku \"([^\"]*)\" z razlogom \"([^\"]*)\"$")
    public void Zavrni_vse_imetnike_na_zahtevku_z_razlogom(String kljuc, String razlog) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);
        String xPath = "//button[contains(@id,'zavrniImetnik')]";
        int i = 0;
        for (;;) {
            try {
                waitElementsByXPath(xPath);
                WebElement el = driver.findElements(By.xpath(xPath)).get(i);
                i++;
                if (el.isDisplayed() && el.isEnabled()) {
                    el.click();
                    String id = el.getAttribute("id").substring(13, el.getAttribute("id").length());
                    sendKeysById("opombaTextArea" + id, razlog);
                    clickById("zavrniImetnikPotrditev" + id);
                    waitElementToHide("zavrniImetnikPotrditev" + id);
                }
            } catch (Exception e) {
                break;
            }
        }
    }

    @Given("^Zavrni zahtevek \"([^\"]*)\" z razlogom zavrnitve \"([^\"]*)\"$")
    public void Zavrni_zahtevek_z_razlogom_zavrnitve(String kljuc, String razlog) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);
        clickById("zavrniZahtevekButton");
        sendKeysById("opombaTextAreaZ", razlog);
        clickById("zavrniZahtevekPotrdi");
        waitElementToHide("zavrniZahtevekPotrdi");
    }

    @Given("^Dopolni zahtevek \"([^\"]*)\"$")
    public void Dopolni_zahtevek(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);
        clickById("dopolniButton");
    }

    @Given("^Posreduj na SIGOV-CA vse zahtevke in preveri \"([^\"]*)\" sporočilo \"([^\"]*)\"$")
    public void Posreduj_na_SIGOV_CA_vse_zahtevke_in_preveri_sporočilo(String id, String msg) throws Throwable {
        clickById("PosredovanjeNaSigovcaMenu");
        clickById("posredujNaSigovca");
        checkMsgByXpath("//div[@id='" + id + "']", msg);
    }

    @Given("^Izvozi vse potrjene zahtevke$")
    public void Izvozi_vse_potrjene_zahtevke() throws Throwable {
        clickById("PosredovanjeNaSigovcaMenu");
        clickById("izvoziVsePotrjene");
    }

    @Given("^Posreduj na SIGOV-CA zahtevek \"([^\"]*)\"$")
    public void Posreduj_na_SIGOV_CA_zahtevek(String kljuc) throws Throwable {
        // scenario.write("Posredovanje na SIGOV-CA"+kljuc+"="+Shrani.getVrednost(kljuc));
        odpriPodrobenPregledZahtevka(kljuc);
        clickById("posredovanoNaSigovCAButton");
    }

    @Given("^Izvozi zahtevek \"([^\"]*)\"$")
    public void Izvozi_zahtevek(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);
        clickById("izvozButton");

    }

    @Given("^Posreduj na SIGOV-CA zahtevek \"([^\"]*)\" in preveri sporočilo \"([^\"]*)\"$")
    public void Posreduj_na_SIGOV_CA_zahtevek_in_preveri_sporočilo(String kljuc, String msg) throws Throwable {
        Posreduj_na_SIGOV_CA_zahtevek(kljuc);
        checkMsgByXpath("//div[@id='error']", msg);

    }

    @Given("^Naloži datoteko s podatki o certifikatih za prevzem \"([^\"]*)\"$")
    public void Naloži_datoteko_s_podatki_o_certifikatih_za_prevzem(String datoteka) throws Throwable {
        clickById("prevzemanjeCertifikatov");
        clickById("uvozPodatkovZaPrevzem");
        File file = new File(datoteka);
        waitElementById("browseClick");
        driver.findElement(By.id("browseClick")).sendKeys(file.getAbsolutePath());
        clickById("naloziPodatke");
        clickById("uvoziPodatke");

    }

    @Given("^Naloži datoteko s podatki o karticah \"([^\"]*)\"$")
    public void Naloži_datoteko_s_podatki_o_karticah(String datoteka) throws Throwable {
        clickById("prevzemanjeCertifikatov");
        clickById("uvozPodatkovIzKartice");
        File file = new File(datoteka);
        waitElementById("browseClick");
        driver.findElement(By.id("browseClick")).sendKeys(file.getAbsolutePath());
        clickById("naloziPodatke");
        clickById("uvoziPodatke");
    }

    @Given("^Poišči kartico po črtni kodi \"([^\"]*)\" in jo odpremi$")
    public void Poišči_kartico_po_črtni_kodi_in_jo_odpremi(String crtnaKoda) throws Throwable {
        clickById("odpremljanjeKartic");
        clickById("iskanjePripravljenihKartic");
        sendKeysById("barCode", crtnaKoda);
        clickById("isci");
        clickByXPath(true, "//button[contains(text(),'PK odpremljena')]");
    }

    @Given("^Iz strani za pregled pripravljenih kartic odpremi kartico s črtno kodo \"([^\"]*)\" in preveri obvestilo \"([^\"]*)\"$")
    public void Iz_strani_za_pregled_pripravljenih_kartic_odpremi_kartico_s_črtno_kodo_in_preveri_obvestilo(String crtnaKoda, String msg) throws Throwable {
        Iz_strani_za_pregled_pripravljenih_kartic_odpremi_kartico_s_črtno_kodo(crtnaKoda);
        checkMsgByXpath("//div[@id='error']", msg);
    }

    @Given("^Natisni obvestilo o prejemu za kartico s črtno kodo \"([^\"]*)\"$")
    public void Natisni_obvestilo_o_prejemu_za_kartico_s_črtno_kodo(String crtnaKoda) throws Throwable {
        clickById("odpremljanjeKartic");
        clickById("pregledPripravljenihKartic");
        clickByXPath(true, "//td[contains(text(),'" + crtnaKoda + "')]/../td[5]/a[1]");


    }

    @Given("^Iz strani za pregled pripravljenih kartic odpremi kartico s črtno kodo \"([^\"]*)\"$")
    public void Iz_strani_za_pregled_pripravljenih_kartic_odpremi_kartico_s_črtno_kodo(String crtnaKoda) throws Throwable {
        clickById("odpremljanjeKartic");
        clickById("pregledPripravljenihKartic");
        clickByXPath(true, "//td[contains(text(),'" + crtnaKoda + "')]/../td[5]/a[2]");
    }

    @Given("^Poišči imetnika \"([^\"]*)\" po črtni kodi in potrdi potrdilo o prejeti kartici dne \"([^\"]*)\" in preveri sporočilo \"([^\"]*)\"$")
    public void Poišči_imetnika_po_črtni_kodi_in_potrdi_potrdilo_o_prejeti_kartici_dne_in_preveri_sporočilo(String kljuc, String datum, String msg) throws Throwable {
        Poišči_imetnika_po_črtni_kodi_in_potrdi_potrdilo_o_prejeti_kartici_dne(kljuc, datum);
        checkMsgByXpath("//div[@id='error']", msg);
    }

    @Given("^Poišči imetnika \"([^\"]*)\" po črtni kodi in potrdi potrdilo o prejeti kartici dne \"([^\"]*)\"$")
    public void Poišči_imetnika_po_črtni_kodi_in_potrdi_potrdilo_o_prejeti_kartici_dne(String kljuc, String datum) throws Throwable {
        clickById("potrdila");
        clickById("iskanjeImetnikaZaPotrditev");
        sendKeysById("barCode", Shrani.getVrednost(kljuc));
        clickById("isci");
        sendKeysById("datum", datum);
        clickById("Potrdi");
    }

    @Given("^Potrdi potrdilo o prejeti kartici za imetnika \"([^\"]*)\" dne \"([^\"]*)\" in preveri sporočilo \"([^\"]*)\"$")
    public void Potrdi_potrdilo_o_prejeti_kartici_za_imetnika_dne_in_preveri_sporočilo(String kljuc, String datum, String msg) throws Throwable {
        Potrdi_potrdilo_o_prejeti_kartici_za_imetnika_dne(kljuc, datum);
        checkMsgByXpath("//div[@id='error']", msg);
    }

    @Given("^Potrdi potrdilo o prejeti kartici za imetnika \"([^\"]*)\" dne \"([^\"]*)\"$")
    public void Potrdi_potrdilo_o_prejeti_kartici_za_imetnika_dne(String kljuc, String datum) throws Throwable {
        clickById("potrdila");
        clickById("pregledImetnikovZaPotrditev");
        sendKeysByXPath("//td[contains(text(),'" + Shrani.getVrednost(kljuc) + "')]/../td[5]/input", datum);
        clickByXPath(true, "//td[contains(text(),'" + Shrani.getVrednost(kljuc) + "')]/../td[6]/button");
    }

    @Given("^Izberi podatke o karticah za izvoz od \"([^\"]*)\" do \"([^\"]*)\" in preveri število rezultatov \"([^\"]*)\" ter sporočilo \"([^\"]*)\"$")
    public void Izberi_podatke_o_karticah_za_izvoz_od_do_in_preveri_število_rezultatov_ter_sporočilo(String datumOd, String datumDo, String stRez, String msg) throws Throwable {
        Izberi_podatke_o_karticah_za_izvoz_od_do_in_preveri_število_rezultatov(datumOd, datumDo, stRez);
        checkMsgByXpath("//div[@id='error']", msg);
    }

    @Given("^Izberi podatke o karticah za izvoz od \"([^\"]*)\" do \"([^\"]*)\" in preveri število rezultatov \"([^\"]*)\"$")
    public void Izberi_podatke_o_karticah_za_izvoz_od_do_in_preveri_število_rezultatov(String datumOd, String datumDo, String stRez) throws Throwable {
        clickById("izvozPodatkovKartice");
        sendKeysById("datumOd", datumOd);
        sendKeysById("datumDo", datumDo);
        clickById("potrdi");
        int rezNum = Integer.parseInt(stRez);
        try {
            if (rezNum != 0) {
                waitElementsByXPath("//tbody/tr");
                List<WebElement> elementi = driver.findElements(By.xpath("//tbody/tr"));
                Assert.assertEquals(rezNum + 2, elementi.size());
            }
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri preverjanju števila elementov.");
            throw new Exception(e.getMessage());
        }
    }

    @Given("^Uvozi obstoječe certifikate iz datoteke \"([^\"]*)\" in preveri sporočila \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void Uvozi_obstoječe_certifikate_iz_datoteke_in_preveri_sporočila_(String datoteka, String msg, String msg1, String msg2) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Uvoz podatkov iz SIGOV-CA')]");
        clickById("vnosCertf");
        File file = new File(datoteka);
        waitElementById("browseClick");
        driver.findElement(By.id("browseClick")).sendKeys(file.getAbsolutePath());
        clickByXPath(true, "//button[contains(text(),'Naloži')]");
        waitElementToHide("loadingDiv");
        checkMsgByXpath("//div[@class='success']", msg);
        checkMsgByXpath("//div[@class='error'][2]", msg1);
        checkMsgByXpath("//div[@class='error'][3]", msg2);
    }

    @Given("^Uvozi obstoječe podatke o karticah iz datoteke \"([^\"]*)\" in preveri sporočila \"([^\"]*)\"$")
    public void Uvozi_obstoječe_podatke_o_karticah_iz_datoteke_in_preveri_sporočila(String datoteka, String msg) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Uvoz podatkov iz SIGOV-CA')]");
        clickById("uvozObstjKartic");
        File file = new File(datoteka);
        waitElementById("browseClick");
        driver.findElement(By.id("browseClick")).sendKeys(file.getAbsolutePath());
        clickByXPath(true, "//button[contains(text(),'Naloži')]");
        waitElementToHide("loadingDiv");
        checkMsgByXpath("//div[@class='success']", msg);
    }

    @Given("^Uvozi obstoječe podatke o čitalcih iz datoteke \"([^\"]*)\" in preveri sporočila \"([^\"]*)\"$")
    public void Uvozi_obstoječe_podatke_o_čitalcih_iz_datoteke_in_preveri_sporočila(String datoteka, String msg) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Uvoz podatkov iz SIGOV-CA')]");
        clickById("uvozObstjCit");
        File file = new File(datoteka);
        waitElementById("browseClick");
        driver.findElement(By.id("browseClick")).sendKeys(file.getAbsolutePath());
        clickByXPath(true, "//button[contains(text(),'Naloži')]");
        waitElementToHide("loadingDiv");
        checkMsgByXpath("//div[@class='success']", msg);
    }

    @Given("^Poišči certifikate za \"([^\"]*)\" in preveri število prikazanih certifikatov \"([^\"]*)\"$")
    public void Poišči_certifikate_za_in_preveri_število_prikazanih_certifikatov(String sodisce, String stCertf) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Pregled certifikatov in opreme')]");
        clickById("pregledCertifikatov");
        sendKeysById("iskano", sodisce);
        clickById("isci");
        int st = Integer.parseInt(stCertf);
        try {
            if (st != 0) {
                waitElementsByXPath("//tbody/tr");
                List<WebElement> elementi = driver.findElements(By.xpath("//tbody/tr"));
                Assert.assertEquals(st + 1, elementi.size());
            }
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri preverjanju prikazanih certifikatov.");
            throw new Exception(e.getMessage());
        }
    }

    @Given("^Poišči \"([^\"]*)\" ([^\"]*) za \"([^\"]*)\" in preveri število prikazanih ([^\"]*) \"([^\"]*)\"$")
    public void Poišči_za_in_preveri_število_prikazanih(String katere, String tip, String sodisce, String tip1, String stKart) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Pregled certifikatov in opreme')]");
        if (tip.compareTo("kartice") == 0) {
            clickById("pregledKartic");
        } else {
            clickById("pregledCitalcev");
        }

        sendKeysById("iskano", sodisce);
        waitElementById("vrnjeno");
        waitElementById("izdano");

        //Oba se oznacita
        if (!driver.findElement(By.id("vrnjeno")).isSelected()) {
            clickById("vrnjeno");
        }
        if (!driver.findElement(By.id("izdano")).isSelected()) {
            clickById("izdano");
        }
        //vrnjeno/izdano
        if (katere.compareTo("vrnjene") == 0) {
            clickById("izdano");
        }
        if (katere.compareTo("izdane") == 0) {
            clickById("vrnjeno");
        }

        clickByXPath(true, "//input[@value='Išči']");
        int st = Integer.parseInt(stKart);
        try {
            if (st != 0) {
                waitElementsByXPath("//table[@class='table']/tbody/tr");
                List<WebElement> elementi = driver.findElements(By.xpath("//table[@class='table']/tbody/tr"));
                Assert.assertEquals(st + 2, elementi.size());
            }
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri preverjanju prikazanih kartic.");
            throw new Exception(e.getMessage());
        }
    }

    @Given("^Pojdi na pregled ([^\"]*) in preveri število prikazanih ([^\"]*) \"([^\"]*)\"$")
    public void Pojdi_na_pregled_in_preveri_število_prikazanih(String tip, String tip1, String stevilo) throws Throwable {
        int st = Integer.parseInt(stevilo);
        clickByXPath(true, "//a[contains(text(),'Pregled certifikatov in opreme')]");
        int k = 0;
        if (tip.compareTo("certifikatov") == 0) {
            clickById("pregledCertifikatov");
            k = 1;
        } else if (tip.compareTo("kartic") == 0) {
            clickById("pregledKartic");
            k = 2;
        } else {
            clickById("pregledCitalcev");
            k = 2;
        }
        try {
            if (st != 0) {
                waitElementsByXPath("//table[@class='table']/tbody/tr");
                List<WebElement> elementi = driver.findElements(By.xpath("//table[@class='table']/tbody/tr"));
                Assert.assertEquals(st + k, elementi.size());
            }
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri preverjanju prikazanih " + tip + ".");
            throw new Exception(e.getMessage());
        }
    }

    @Given("^Pojdi na podroben pregled imetnika \"([^\"]*)\"$")
    public void Pojdi_na_podroben_pregled_imetnika(String kljuc) throws Throwable {
        odpriPodrobenPregledImetnika(kljuc);
        /*clickById("pregledImetnikov");
         sendKeysById("iskano", Shrani.getVrednost(kljuc));
         clickById("Isci");
         clickByXPath(true, "//button[contains(text(),'Podrobno')]");
         */
    }

    @Given("^Preveri prikazano besedilo \"([^\"]*)\"$")
    public void Preveri_prikazano_besedilo(String besedilo) throws Throwable {
        String xPath = "//*[contains(text(),'" + besedilo + "')]";
        waitElementsByXPath(xPath);
        Assert.assertTrue(driver.findElements(By.xpath(xPath)).size() > 0);
    }

    @Given("^Potrdi, da je bila dne \"([^\"]*)\" oprema \"([^\"]*)\" z oznako \"([^\"]*)\" vrnjena$")
    public void Potrdi_da_je_bila_dne_oprema_z_oznako_vrnjena(String datum, String tip, String oznaka) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Vrnjena oprema')]");
        sendKeysById("oznaka", oznaka);
        clickById("Isci");
        sendKeysByXPath("(//td[contains(text(),'" + oznaka + "')]/../td[6]/input)[1]", datum);
        clickByXPath(true, "(//td[contains(text(),'" + oznaka + "')]/../td[7]/button)[1]");
        if (tip.compareTo("kartica") == 0) {
            checkMsgByXpath("//div[@class='error']", "Datum vrnitve kartice shranjen.");
        } else if (tip.compareTo("čitalec") == 0) {
            checkMsgByXpath("//div[@class='error']", "Datum vrnitve čitalca shranjen.");
        }
    }

    @Given("^Preveri, da besedilo \"([^\"]*)\" ni prikazano$")
    public void Preveri_da_besedilo_ni_prikazano(String besedilo) throws Throwable {
        String xPath = "//*[contains(text(),'" + besedilo + "')]";
        try {
            waitElementsByXPath(xPath);
            Assert.assertTrue(driver.findElements(By.xpath(xPath)).size() > 0);
            narediScreenShot(scenario);
            scenario.write("Napaka pri preverjanju besedila: besedilo je prikazano.");
            throw new Exception();
        } catch (Exception e) {
        }
    }

    @Given("^Uspešno poišči zahtevek \"([^\"]*)\"$")
    public void Uspešno_poišči_zahtevek(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);
        waitElementByXPath("//h3[contains(text(),'Vloga')]");
        Assert.assertEquals("Vloga - " + Shrani.getVrednost(kljuc), driver.findElement(By.xpath("//h3[contains(text(),'Vloga')]")).getText());
    }

    @Given("^Uspešno poišči imetnika \"([^\"]*)\"$")
    public void Uspešno_poišči_imetnika(String kljuc) throws Throwable {
        clickById("pregledImetnikov");
        sendKeysById("iskano", Shrani.getVrednost(kljuc));
        clickById("Isci");
        clickByXPath(true, "(//button[contains(text(),'Podrobno')])[1]");
        waitElementByXPath("//h3[contains(text(),'Imetnik')]");
        Assert.assertEquals("Imetnik - " + Shrani.getVrednost(kljuc), driver.findElement(By.xpath("//h3[contains(text(),'Imetnik')]")).getText());
    }

    @Given("^Neuspešno poišči zahtevek \"([^\"]*)\"$")
    public void Neuspešno_poišči_zahtevek(String kljuc) throws Throwable {
        clickById("pregledZahtevkov");
        waitElementToHide("loadingDiv");
        if (!driver.findElements(By.xpath("//a[contains(text(),'Uvoz podatkov iz SIGOV-CA')]")).isEmpty()) {
            oznaciVseStatuse();
            waitElementsByXPath("//table[@class='table']");
        }
        Assert.assertFalse(obstajaZahtevek(Shrani.getVrednost(kljuc)));
    }

    @Given("^Neuspešno poišči imetnika \"([^\"]*)\"$")
    public void Neuspešno_poišči_imetnika(String kljuc) throws Throwable {
        clickById("pregledImetnikov");
        sendKeysById("iskano", Shrani.getVrednost(kljuc));
        clickById("Isci");
        waitElementByXPath("//table[@class='table']");
        Assert.assertTrue(driver.findElements(By.xpath("//table[@class='table']/tbody/tr")).isEmpty());
    }

    @Given("^Nastavi id zahtevka \"([^\"]*)\"$")
    public void Nastavi_id_zahtevka(String kljuc) throws Throwable {
        //Shrani.addVrednost(idZahtevka, "idZahtevka");        
        odpriPodrobenPregledZahtevka(kljuc);
        waitElementToHide("loadingDiv");
        String url = driver.getCurrentUrl();
        String[] splitano = url.split("/");
        String id = splitano[splitano.length - 1];
        Shrani.addVrednost(id, "idZahtevka");
    }

    @Given("^Nastavi id imetnika \"([^\"]*)\"$")
    public void Nastavi_id_imetnika(String kljuc) throws Throwable {
        //Shrani.addVrednost(idImetnika, "idImetnika");
        clickById("pregledImetnikov");
        sendKeysById("iskano", Shrani.getVrednost(kljuc));
        clickById("Isci");
        clickByXPath(true, "(//button[contains(text(),'Podrobno')])[1]");
        waitElementToHide("loadingDiv");
        String url = driver.getCurrentUrl();
        String[] splitano = url.split("/");
        String id = splitano[splitano.length - 1];
        Shrani.addVrednost(id, "idImetnika");
    }

    @Given("^Nastavi root strani \"([^\"]*)\"$")
    public void Nastavi_root_strani(String rootStrani) throws Throwable {
        // Shrani.addVrednost("http://rigel.sodisce.si:18031/pametne-kartice-web/", "rootStrani");
        Shrani.addVrednost(rootStrani, "rootStrani");
    }

    @Given("^Dostopi do URL-ja \"([^\"]*)\"$")
    public void Dostopi_do_URL_ja(String url) throws Throwable {
        String urlNew = url.replaceAll("idZahtevka", Shrani.getVrednost("idZahtevka"));
        urlNew = urlNew.replaceAll("idImetnika", Shrani.getVrednost("idImetnika"));
        driver.get(Shrani.getVrednost("rootStrani") + urlNew);
    }

    @Given("^Dostopi do URL-ja \"([^\"]*)\" in preveri naslov strani \"([^\"]*)\"$")
    public void Dostopi_do_URL_ja_in_preveri_naslov_strani(String url, String title) throws Throwable {
        Dostopi_do_URL_ja(url);
        if (title.compareTo("PRAZNO") == 0) {
            Prikazana_je_prazna_stran();
        } else {
            Preveri_naslov_strani(title);
        }
    }

    @Given("^Preveri status zahtevka \"([^\"]*)\" - \"([^\"]*)\"$")
    public void Preveri_status_zahtevka_(String kljuc, String status) throws Throwable {
        odpriPodrobenPregledZahtevka(kljuc);
        String xPath = "//td[contains(text()[2],'" + status + "')]";
        waitElementByXPath(xPath);
        WebElement el = driver.findElement(By.xpath(xPath));
        Assert.assertTrue(el.getText().toLowerCase().contains(status.toLowerCase()));
    }

    @Given("^Preveri status imetnika \"([^\"]*)\" - \"([^\"]*)\"$")
    public void Preveri_status_imetnika_(String kljuc, String status) throws Throwable {
        clickById("pregledImetnikov");
        sendKeysById("iskano", Shrani.getVrednost(kljuc));
        clickById("Isci");
        clickByXPath(true, "(//button[contains(text(),'Podrobno')])[1]");

        String xPath = "//td[contains(text(),'" + status + "')]";
        waitElementByXPath(xPath);
        WebElement el = driver.findElement(By.xpath(xPath));
        Assert.assertTrue(el.getText().toLowerCase().compareTo(status.toLowerCase()) == 0);

    }

    @Given("^Prikazana je prazna stran$")
    public void Prikazana_je_prazna_stran() throws Throwable {
        Assert.assertTrue(driver.findElements(By.xpath("//title")).isEmpty());
        Assert.assertTrue(driver.findElements(By.id("header")).isEmpty());
    }

    @Given("^Dostopi do URL-ja \"([^\"]*)\" in preveri, da je dostop zavrnjen$")
    public void Dostopi_do_URL_ja_in_preveri_da_je_dostop_zavrnjen(String url) throws Throwable {
        Dostopi_do_URL_ja(url);
        try {
            waitElementByXPath("//h1[contains(text(),'HTTP Status 403 - Access is denied')]");
            Dostopi_do_URL_ja("index");
            waitElementsByXPath("//h3[contains(text(),'Pametne kartice')]");
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka: element 'H1', ki vsebuje tekst 'HTTP Status 403 - Access is denied' ni viden.");
            Dostopi_do_URL_ja("index");
            waitElementsByXPath("//h3[contains(text(),'Pametne kartice')]");
            throw new Exception(e.getMessage());
        }
    }

    @Given("^Dostopi do URL-ja \"([^\"]*)\" in preveri, da dostop ni zavrnjen$")
    public void Dostopi_do_URL_ja_in_preveri_da_dostop_ni_zavrnjen(String url) throws Throwable {
        Dostopi_do_URL_ja(url);
        try {
            waitElementByXPath("//h3[contains(text(),'Pametne kartice')] | //h1[contains(text(),'HTTP Status 500')] | //h1[contains(text(),'HTTP Status 400')] | //h1[contains(text(),'HTTP Status 405')] | //h1[contains(text(),'HTTP Status 404')]");
            Dostopi_do_URL_ja("index");
            waitElementsByXPath("//h3[contains(text(),'Pametne kartice')]");
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Do te strani ni dostopa.");
            Dostopi_do_URL_ja("index");
            waitElementsByXPath("//h3[contains(text(),'Pametne kartice')]");
            throw new Exception(e.getMessage());
        }
    }

    @Given("^Preveri zgodovino za zahtevek \"([^\"]*)\"$")
    public void Preveri_zgodovino_za_zahtevek(String kljuc, List<Map<String, String>> data) throws Exception {
        odpriPodrobenPregledZahtevka(kljuc);
        clickById("pregledZgodovineButton");
        try {
            waitElementByXPath("//h3[contains(text(),'Pregled zgodovine')]");
            int vrstica = 2;
            for (int i = 0; i < data.size(); i++) {
                for (Map.Entry entry : data.get(i).entrySet()) {
                    String xPath = "(//table[@class='table'])[1]//tbody/tr[" + Integer.toString(vrstica) + "]/td[count((//table[@class='table'])[1]//tbody/tr/td[contains(text(),'" + entry.getKey().toString() + "')]/preceding::td)+1]";
                    waitElementByXPath(xPath);
                    WebElement element = driver.findElement(By.xpath(xPath));
                    String expected = entry.getValue().toString();
                    String actual = element.getText();
                    if (expected.compareTo("TODAY") == 0) {
                        expected = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
                        actual = actual.split(" -")[0];
                    }
                    //scenario.write(entry.getKey().toString()+":"+expected+";"+actual+"=>"+xPath);
                    Assert.assertEquals(expected, actual);
                }
                vrstica += 2;
            }
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri preverjanju zgodovine zahtevka.");
            throw new Exception(e);
        }
    }

    @Given("^Preveri zgodovino za imetnika \"([^\"]*)\"$")
    public void Preveri_zgodovino_za_imetnika(String kljuc, List<Map<String, String>> data) throws Throwable {
        odpriPodrobenPregledImetnika(kljuc);
        clickById("PregledZahtevka");
        clickById("pregledZgodovineButton");
        try {
            waitElementByXPath("//h3[contains(text(),'Pregled zgodovine')]");
            String crtnaKoda = Shrani.getVrednost(kljuc);
            int vrstica = 3;
            for (int i = 0; i < data.size(); i++) {
                for (Map.Entry entry : data.get(i).entrySet()) {
                    String xPath = "//tbody/tr/td/strong[contains(text(),'" + crtnaKoda + "')]/../../../tr[" + Integer.toString(vrstica) + "]/td[count(//tbody/tr/td/strong[contains(text(),'" + crtnaKoda + "')]/../../../tr[2]/td[contains(text(),'" + entry.getKey().toString() + "')]/preceding-sibling::td)+1]";
                    waitElementByXPath(xPath);
                    WebElement element = driver.findElement(By.xpath(xPath));
                    String expected = entry.getValue().toString();
                    String actual = element.getText();
                    if (expected.compareTo("TODAY") == 0) {
                        expected = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
                        actual = actual.split(" -")[0];
                    }
                    //scenario.write(entry.getKey().toString()+":"+expected+";"+actual+"=>"+xPath);
                    Assert.assertEquals(expected, actual);
                }
                vrstica += 1;
            }

        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri preverjanju zgodovine imetnika.");
            throw new Exception(e);
        }
    }

    @Given("^Poišči kartico VSRS po \"([^\"]*)\" in preveri število rezultatov \"([^\"]*)\"$")
    public void Poišči_kartico_VSRS_po_in_preveri_število_rezultatov(String iskalniNiz, String stRezultatov) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Pregled certifikatov in opreme')]");
        clickById("pregledKarticVSRS");
        sendKeysById("iskano", iskalniNiz);
        clickById("Isci");
        int st = Integer.parseInt(stRezultatov);
        try {
            if (st != 0) {
                waitElementsByXPath("//table[@class='table']");
                List<WebElement> elementi = driver.findElements(By.xpath("//table[@class='table']/tbody/tr"));
                Assert.assertEquals(st + 1, elementi.size());
            } else {
                String xPath = "//div[@class='error']";
                checkMsgByXpath(xPath, "Ni zadetkov.");
            }
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri preverjanju prikazanih kartic VSRS.");
            throw new Exception(e.getMessage());
        }
    }

    @Given("^Poišči kartico VSRS po \"([^\"]*)\" in datumOd \"([^\"]*)\" in datumDo \"([^\"]*)\" ter preveri število rezultatov \"([^\"]*)\"$")
    public void Poišči_kartico_VSRS_po_in_datumOd_in_datumDo_ter_preveri_število_rezultatov(String iskalniNiz, String datumOd, String datumDo, String stRezultatov) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Pregled certifikatov in opreme')]");
        clickById("pregledKarticVSRS");
        sendKeysById("iskano", iskalniNiz);
        sendKeysById("datumOd", datumOd);
        sendKeysById("datumDo", datumDo);
        clickById("Isci");
        int st = Integer.parseInt(stRezultatov);
        try {
            if (st != 0) {
                waitElementsByXPath("//table[@class='table']");
                List<WebElement> elementi = driver.findElements(By.xpath("//table[@class='table']/tbody/tr"));
                Assert.assertEquals(st + 1, elementi.size());
            } else {
                String xPath = "//div[@class='error']";
                checkMsgByXpath(xPath, "Ni zadetkov.");
            }
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri preverjanju prikazanih kartic VSRS.");
            throw new Exception(e.getMessage());
        }
    }

    @Given("^Potrdi, da je bila dne \"([^\"]*)\" kartica VSRS z iskalnim nizom \"([^\"]*)\" vrnjena$")
    public void Potrdi_da_je_bila_dne_kartica_VSRS_z_iskalnim_nizom_vrnjena(String datumVrnitve, String iskalniNiz) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Vrnjena oprema')]");
        sendKeysById("oznaka", iskalniNiz);
        clickById("Isci");
        sendKeysByXPath("(//strong[contains(text(),'Kartice VSRS')]/../../../tr/td[contains(text(),'" + iskalniNiz + "')]/../td[8]/input)[1]", datumVrnitve);
        clickByXPath(true, "(//strong[contains(text(),'Kartice VSRS')]/../../../tr/td[contains(text(),'" + iskalniNiz + "')]/../td[9]/button)[1]");
        checkMsgByXpath("//div[@class='error']", "Datum vrnitve kartice shranjen.");

    }

    @Given("^Poišči kartico VSRS po \"([^\"]*)\" in preveri datum vrnitve \"([^\"]*)\"$")
    public void Poišči_kartico_VSRS_po_in_preveri_datum_vrnitve(String iskalniNiz, String datumVrnitve) throws Throwable {
        Poišči_kartico_VSRS_po_in_preveri_število_rezultatov(iskalniNiz, "1");
        String xPath = "//table[@class='table']/tbody/tr[2]/td[7]";
        checkMsgByXpath(xPath, datumVrnitve);
    }

    @Given("^Na strani za dodajanje zahtevka za pridobitev kode za odklepanje kartice išči \"([^\"]*)\" in preveri število rezultatov \"([^\"]*)\"$")
    public void Na_strani_za_dodajanje_zahtevka_za_pridobitev_kode_za_odklepanje_kartice_išči_in_preveri_število_rezultatov(String iskanje, String stRez) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Nova vloga')]");
        clickById("novaVlogaKoda");
        waitElementToHide("loadingDiv");
        sendKeysById("iskano", iskanje);
        clickById("Isci");
        waitElementToHide("loadingDiv");
        List<WebElement> tBodyTR = driver.findElements(By.xpath("//table[@class='table']/tbody/tr"));
        Assert.assertEquals(Integer.parseInt(stRez) + 1, tBodyTR.size());

    }

    @Given("^Na strani za dodajanje zahtevka za pridobitev kode za odklepanje kartice išči \"([^\"]*)\" in izberi kartico s črtno kodo \"([^\"]*)\", ter potrdi dodajanje zahtevka \"([^\"]*)\"$")
    public void Na_strani_za_dodajanje_zahtevka_za_pridobitev_kode_za_odklepanje_kartice_išči_in_izberi_kartico_s_črtno_kodo_ter_potrdi_dodajanje_zahtevka(String iskanje, String crtnaKoda, String kljuc) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Nova vloga')]");
        clickById("novaVlogaKoda");
        waitElementToHide("loadingDiv");
        sendKeysById("iskano", iskanje);
        clickById("Isci");
        waitElementToHide("loadingDiv");
        clickByXPath(true, "//td[contains(text(),'" + crtnaKoda + "')]/../td[7]/a/button");
        clickById("potrdi");
        zapomniSiCrtnoKodoZahtevkaKoda(kljuc);

    }

    @Given("^Na strani za dodajanje zahtevka za pridobitev kode za odklepanje kartice išči \"([^\"]*)\" in potrdi dodajanje zahtevka \"([^\"]*)\", ter preveri sporočilo \"([^\"]*)\"$")
    public void Na_strani_za_dodajanje_zahtevka_za_pridobitev_kode_za_odklepanje_kartice_išči_in_potrdi_dodajanje_zahtevka_ter_preveri_sporočilo(String iskanje, String kljuc, String msg) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Nova vloga')]");
        clickById("novaVlogaKoda");
        waitElementToHide("loadingDiv");
        sendKeysById("iskano", iskanje);
        clickById("Isci");
        clickById("potrdi");
        Preveri_prikazano_besedilo(msg);
        zapomniSiCrtnoKodoZahtevkaKoda(kljuc);
    }

    @Given("^Na strani za dodajanje zahtevka za pridobitev kode za odklepanje kartice išči \"([^\"]*)\" in potrdi dodajanje zahtevka \"([^\"]*)\", ter preveri, da je odprta stran za podroben pregled zahtevka$")
    public void Na_strani_za_dodajanje_zahtevka_za_pridobitev_kode_za_odklepanje_kartice_išči_in_potrdi_dodajanje_zahtevka_ter_preveri_da_je_odprta_stran_za_podroben_pregled_zahtevka(String iskanje, String kljuc) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Nova vloga')]");
        clickById("novaVlogaKoda");
        waitElementToHide("loadingDiv");
        sendKeysById("iskano", iskanje);
        clickById("Isci");
        clickById("potrdi");
        waitElementByXPath("//h3[contains(text(),'Vloga - KS')]");
        waitElementById("printButton");
        waitElementById("pregledZgodovineButton");
        waitElementById("deleteButton");
        waitElementById("sendButton");
        Assert.assertEquals("Podrobno", driver.getTitle());
        zapomniSiCrtnoKodoZahtevkaKoda(kljuc);
    }

    @Given("^Uspešno poišči zahtevek za kodo \"([^\"]*)\"$")
    public void Uspešno_poišči_zahtevek_za_kodo(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaKoda(kljuc);
        waitElementByXPath("//h3[contains(text(),'Vloga')]");
        Assert.assertEquals("Vloga - " + Shrani.getVrednost(kljuc), driver.findElement(By.xpath("//h3[contains(text(),'Vloga')]")).getText());
    }

    @Given("^Neuspešno poišči zahtevek za kodo \"([^\"]*)\"$")
    public void Neuspešno_poišči_zahtevek_za_kodo(String kljuc) throws Throwable {
        clickById("pregledZahtevkov");
        waitElementToHide("loadingDiv");
        clickByXPath(true, "//li[@id='zahtevkiKodaTab']/a");
        waitElementsByXPath("//table[@class='table']");
        Assert.assertFalse(obstajaZahtevekKoda(Shrani.getVrednost(kljuc)));
    }

    @Given("^Izbriši zahtevek za kodo \"([^\"]*)\"$")
    public void Izbriši_zahtevek_za_kodo(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaKoda(kljuc);
        clickById("deleteButton");
        waitElementById("brisiVlogo");
        clickByXPath(true, "(//button[contains(text(),'Potrdi')])[1]");
        waitElementToHide("brisiVlogo");
    }

    @Given("^Posreduj na SIGOV-CA zahtevek za kodo \"([^\"]*)\"$")
    public void Posreduj_na_SIGOV_CA_zahtevek_za_kodo(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaKoda(kljuc);
        clickById("sendButton");
        clickById("potrdiPosredovanje");
    }

    @Given("^Posreduj na SIGOV-CA zahtevek za kodo \"([^\"]*)\" in preveri sporočilo \"([^\"]*)\"$")
    public void Posreduj_na_SIGOV_CA_zahtevek_za_kodo_in_preveri_sporočilo(String kljuc, String msg) throws Throwable {
        Posreduj_na_SIGOV_CA_zahtevek_za_kodo(kljuc);
        checkMsgByXpath("//div[@id='error']", msg);
    }

    @Given("^Natisni zahtevek za kodo \"([^\"]*)\"$")
    public void Natisni_zahtevek_za_kodo(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaKoda(kljuc);
        clickById("printButton");
        clickById("potrdiTiskanje");
    }

    @Given("^Odkleni zahtevek za kodo \"([^\"]*)\"$")
    public void Odkleni_zahtevek_za_kodo(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaKoda(kljuc);
        clickById("unlockButton");
    }

    @Given("^Preveri status zahtevka za kodo \"([^\"]*)\" - \"([^\"]*)\"$")
    public void Preveri_status_zahtevka_za_kodo_(String kljuc, String status) throws Throwable {
        odpriPodrobenPregledZahtevkaKoda(kljuc);
        String xPath = "//td[contains(text()[2],'" + status + "')]";
        waitElementByXPath(xPath);
        WebElement el = driver.findElement(By.xpath(xPath));
        Assert.assertTrue(el.getText().toLowerCase().contains(status.toLowerCase()));
    }

    @Given("^Na strani za dodajanje zahtevka za preklic certifikata išči \"([^\"]*)\" in preveri število rezultatov \"([^\"]*)\"$")
    public void Na_strani_za_dodajanje_zahtevka_za_preklic_certifikata_išči_in_preveri_število_rezultatov(String iskanje, String stRez) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Nova vloga')]");
        clickById("novaVlogaPreklic");
        waitElementToHide("loadingDiv");
        sendKeysById("iskano", iskanje);
        clickById("Isci");
        List<WebElement> tBodyTR = driver.findElements(By.xpath("//table[@class='table']/tbody/tr"));
        Assert.assertEquals(Integer.parseInt(stRez) + 1, tBodyTR.size());
    }

    @Given("^Na strani za dodajanje zahtevka za preklic certifikata išči \"([^\"]*)\" in potrdi dodajanje zahtevka \"([^\"]*)\", ter preveri, da je odprta stran za podroben pregled zahtevka$")
    public void Na_strani_za_dodajanje_zahtevka_za_preklic_certifikata_išči_in_potrdi_dodajanje_zahtevka_ter_preveri_da_je_odprta_stran_za_podroben_pregled_zahtevka(String iskanje, String kljuc) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Nova vloga')]");
        clickById("novaVlogaPreklic");
        waitElementToHide("loadingDiv");
        sendKeysById("iskano", iskanje);
        clickById("Isci");
        clickById("potrdi");
        waitElementByXPath("//h3[contains(text(),'Vloga - PS')]");
        waitElementById("printButton");
        waitElementById("pregledZgodovineButton");
        waitElementById("deleteButton");
        waitElementById("sendButton");
        Assert.assertEquals("Podrobno", driver.getTitle());
        String crtnaKoda = driver.findElement(By.xpath("//h3[contains(text(),'Vloga - PS')]")).getText();
        Shrani.addVrednost(crtnaKoda.split(" ")[2], kljuc);
    }

    @Given("^Na strani za dodajanje zahtevka za preklic certifikata išči \"([^\"]*)\" in potrdi dodajanje zahtevka, ter preveri sporočilo \"([^\"]*)\"$")
    public void Na_strani_za_dodajanje_zahtevka_za_preklic_certifikata_išči_in_potrdi_dodajanje_zahtevka_ter_preveri_sporočilo(String iskanje, String msg) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Nova vloga')]");
        clickById("novaVlogaPreklic");
        waitElementToHide("loadingDiv");
        sendKeysById("iskano", iskanje);
        clickById("Isci");
        clickById("potrdi");
        Preveri_prikazano_besedilo(msg);
    }

    @Given("^Brisi zahtevek za preklic certifikata \"([^\"]*)\"$")
    public void Brisi_zahtevek_za_preklic_certifikata(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaPreklic(kljuc);
        clickById("deleteButton");
        waitElementById("brisiVlogo");
        clickById("potrdiBrisanje");
        waitElementToHide("brisiVlogo");
    }

    @Given("^Potrdi zahtevek za preklic certifikata \"([^\"]*)\"$")
    public void Potrdi_zahtevek_za_preklic_certifikata(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaPreklic(kljuc);
        clickById("confirmButton");
    }

    @Given("^Posreduj na SIGOV-CA zahtevek za preklic certifikata \"([^\"]*)\" in preveri sporočilo \"([^\"]*)\"$")
    public void Posreduj_na_SIGOV_CA_zahtevek_za_preklic_certifikata_in_preveri_sporočilo(String kljuc, String msg) throws Throwable {
        odpriPodrobenPregledZahtevkaPreklic(kljuc);
        clickById("sendButton");
        Preveri_prikazano_besedilo(msg);
    }

    @Given("^Natisni zahtevek za preklic certifikata \"([^\"]*)\"$")
    public void Natisni_zahtevek_za_preklic_certifikata(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaPreklic(kljuc);
        clickById("printButton");
        clickById("potrdiTiskanje");
        waitElementToHide("natisniVlogo");
    }

    @Given("^Posreduj na SIGOV-CA zahtevek za preklic certifikata \"([^\"]*)\"$")
    public void Posreduj_na_SIGOV_CA_zahtevek_za_preklic_certifikata(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaPreklic(kljuc);
        clickById("sendButton");
    }

    @Given("^Odkleni zahtevek za preklic certifikata \"([^\"]*)\"$")
    public void Odkleni_zahtevek_za_preklic_certifikata(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaPreklic(kljuc);
        clickById("unlockButton");
    }

    @Given("^Preveri, da je zahtevek za preklic certifikata \"([^\"]*)\" v statusu \"([^\"]*)\"$")
    public void Preveri_da_je_zahtevek_za_preklic_certifikata_v_statusu(String kljuc, String status) throws Throwable {
        odpriPodrobenPregledZahtevkaPreklic(kljuc);
        String xPath = "//td[contains(text()[2],'" + status + "')]";
        waitElementByXPath(xPath);
        WebElement el = driver.findElement(By.xpath(xPath));
        Assert.assertTrue(el.getText().toLowerCase().contains(status.toLowerCase()));
    }

    @Given("^Pripravi bazo \"([^\"]*)\"$")
    public void Pripravi_bazo(String filePath) throws Throwable {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF8"));
        String sql = "";
        String line = null;
        while ((line = reader.readLine()) != null) {
            sql += line;
        }
        String[] sqlSplit = sql.split(";");
        for (String s : sqlSplit) {
            System.out.println("SQL=>" + s);
            executeQueryOnDatabase(s);
        }
    }

    @Given("^Iz strani za iskanje poišči in potrdi imetnika \"([^\"]*)\", ter preveri, da je odprta stran za iskanje$")
    public void Iz_strani_za_iskanje_poišči_in_potrdi_imetnika_ter_preveri_da_je_odprta_stran_za_iskanje(String kljuc) throws Throwable {
        clickById("iskanje");
        sendKeysById("iskano", Shrani.getVrednost(kljuc));
        clickById("Isci");
        clickByXPath(true, "//button[contains(text(),'Potrdi')]");
        Preveri_naslov_strani("Iskanje");
    }

    //Prenos certifikata (@16)
    @Given("^Na strani za dodajanje zahtevka za prenos certifikata išči \"([^\"]*)\" in potrdi dodajanje zahtevka \"([^\"]*)\", ter preveri, da je odprta stran za podroben pregled zahtevka$")
    public void Na_strani_za_dodajanje_zahtevka_za_prenos_certifikata_išči_in_potrdi_dodajanje_zahtevka_ter_preveri_da_je_odprta_stran_za_podroben_pregled_zahtevka(String iskanje, String kljuc) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Nova vloga')]");
        clickById("novaVlogaPrenos");
        waitElementToHide("loadingDiv");
        sendKeysById("iskano", iskanje);
        clickById("Isci");
        clickById("potrdi");
        waitElementByXPath("//h3[contains(text(),'Vloga - TS')]");
        waitElementById("printButton");
        waitElementById("pregledZgodovineButton");
        waitElementById("deleteButton");
        waitElementById("sendButton");
        Assert.assertEquals("Podrobno", driver.getTitle());
        String crtnaKoda = driver.findElement(By.xpath("//h3[contains(text(),'Vloga - TS')]")).getText();
        Shrani.addVrednost(crtnaKoda.split(" ")[2], kljuc);
    }

    @Given("^Na strani za dodajanje zahtevka za prenos certifikata išči \"([^\"]*)\" in potrdi dodajanje zahtevka, ter preveri sporočilo \"([^\"]*)\"$")
    public void Na_strani_za_dodajanje_zahtevka_za_prenos_certifikata_išči_in_potrdi_dodajanje_zahtevka_ter_preveri_sporočilo(String iskanje, String msg) throws Throwable {
        clickByXPath(true, "//a[contains(text(),'Nova vloga')]");
        clickById("novaVlogaPrenos");
        waitElementToHide("loadingDiv");
        sendKeysById("iskano", iskanje);
        clickById("Isci");
        clickById("potrdi");
        Preveri_prikazano_besedilo(msg);
    }

    @Given("^Brisi zahtevek za prenos certifikata \"([^\"]*)\"$")
    public void Brisi_zahtevek_za_prenos_certifikata(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaPrenos(kljuc);
        clickById("deleteButton");
        waitElementById("brisiVlogo");
        clickById("potrdiBrisanje");
        waitElementToHide("brisiVlogo");
    }

    @Given("^Preveri, da je na strani za podroben pregled zahtevka za prenos certifikata \"([^\"]*)\" gumb za pregled imetnika neaktiven$")
    public void Preveri_da_je_na_strani_za_podroben_pregled_zahtevka_za_prenos_certifikata_gumb_za_pregled_imetnika_neaktiven(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaPrenos(kljuc);
        waitElementById("pregledImetnika");
        WebElement el = driver.findElement(By.id("pregledImetnika"));
        Assert.assertEquals(false, el.isEnabled());
    }

    @Given("^Potrdi zahtevek za prenos certifikata \"([^\"]*)\"$")
    public void Potrdi_zahtevek_za_prenos_certifikata(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaPrenos(kljuc);
        clickById("confirmButton");
    }

    @Given("^Preveri, da je zahtevek za prenos certifikata \"([^\"]*)\" v statusu \"([^\"]*)\"$")
    public void Preveri_da_je_zahtevek_za_prenos_certifikata_v_statusu(String kljuc, String status) throws Throwable {
        odpriPodrobenPregledZahtevkaPrenos(kljuc);
        String xPath = "//td[contains(text()[2],'" + status + "')]";
        waitElementByXPath(xPath);
        WebElement el = driver.findElement(By.xpath(xPath));
        Assert.assertTrue(el.getText().toLowerCase().contains(status.toLowerCase()));
    }

    @Given("^Posreduj na SIGOV-CA zahtevek za prenos certifikata \"([^\"]*)\" in preveri sporočilo \"([^\"]*)\"$")
    public void Posreduj_na_SIGOV_CA_zahtevek_za_prenos_certifikata_in_preveri_sporočilo(String kljuc, String msg) throws Throwable {
        odpriPodrobenPregledZahtevkaPrenos(kljuc);
        clickById("sendButton");
        checkMsgByXpath("//div[@id='error']", msg);
    }

    @Given("^Natisni zahtevek za prenos certifikata \"([^\"]*)\"$")
    public void Natisni_zahtevek_za_prenos_certifikata(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaPrenos(kljuc);
        clickById("printButton");
        clickById("potrdiTiskanje");
        waitElementToHide("natisniVlogo");
    }

    @Given("^Odkleni zahtevek za prenos certifikata \"([^\"]*)\"$")
    public void Odkleni_zahtevek_za_prenos_certifikata(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaPrenos(kljuc);
        clickById("unlockButton");
    }

    /* @Given("^Posreduj na SIGOV-CA zahtevek za prenos certifikata \"([^\"]*)\"$")
     public void Posreduj_na_SIGOV_CA_zahtevek_za_prenos_certifikata(String kljuc) throws Throwable {
     odpriPodrobenPregledZahtevkaPrenos(kljuc);
     clickById("sendButton");
     }*/
    @Given("^Posreduj na SIGOV-CA zahtevek za prenos certifikata \"([^\"]*)\" in si zapomni novega imetnika kot \"([^\"]*)\"$")
    public void Posreduj_na_SIGOV_CA_zahtevek_za_prenos_certifikata_in_si_zapomni_novega_imetnika_kot(String kljuc, String kljucImetnik) throws Throwable {
        odpriPodrobenPregledZahtevkaPrenos(kljuc);
        clickById("sendButton");
        if (kljucImetnik != null) {
            // zapomniSiCrtnoKodoImetnika(kljucImetnik);
            //Da se stestira delovanje gumba pregled imetnika
            waitElementToHide("loadingDiv");
            clickById("pregledImetnika");
            waitElementByXPath("//h3[contains(text(),'IS')]");
            String crtnaKoda = driver.findElement(By.xpath("//h3[contains(text(),'IS')]")).getText();
            Shrani.addVrednost(crtnaKoda.split(" ")[2], kljucImetnik);
        }

    }

    @Given("^Preveri, da je na strani za podroben pregled zahtevka za prenos certifikata \"([^\"]*)\" gumb odkleni neaktiven$")
    public void Preveri_da_je_na_strani_za_podroben_pregled_zahtevka_za_prenos_certifikata_gumb_odkleni_neaktiven(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaPrenos(kljuc);
        waitElementById("unlockButton");
        WebElement el = driver.findElement(By.id("unlockButton"));
        Assert.assertEquals(false, el.isEnabled());
    }

    @Given("^Preveri, da je na strani za podroben pregled zahtevka za prenos certifikata \"([^\"]*)\" gumb za pregled imetnika aktiven$")
    public void Preveri_da_je_na_strani_za_podroben_pregled_zahtevka_za_prenos_certifikata_gumb_za_pregled_imetnika_aktiven(String kljuc) throws Throwable {
        odpriPodrobenPregledZahtevkaPrenos(kljuc);
        waitElementById("pregledImetnika");
        WebElement el = driver.findElement(By.id("pregledImetnika"));
        Assert.assertEquals(true, el.isEnabled());
    }
//
}
