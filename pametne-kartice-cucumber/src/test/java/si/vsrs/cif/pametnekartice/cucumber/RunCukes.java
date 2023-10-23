/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.pametnekartice.cucumber;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 *
 * @author andrej
 */
/*pobrisat Shrani_vrednosti.txt*/
@RunWith(Cucumber.class)
//@Cucumber.Options(format = {"pretty", "html:target/cucumber/"}, tags = {"@01, @02, @03, @04, @05, @06, @07, @08, @09, @10, @11, @12, @13, @14, @15, @16"},
@Cucumber.Options(format = {"pretty", "html:target/cucumber/"}, tags = {"@02, @03, @04, @09, @13, @14, @16"},
        features = {
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/00_nastavitve_za_testno_okolje.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/01_prijava.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/02_vnos_vlog_za_izdajo_kartic.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/03_admin_potrdi_zavrni_vloge.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/04_uvoz_podatkov_in_prevzem_certifikata.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/05_uvoz_in_pregled_obstojecih_podatkov.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/06_kontrole_pri_dodajanju_imetnikov.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/07_preverjanje_delovanja_vrnjene_opreme.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/08_pregled_zahtevka_za_razlicne_uporabnike.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/09_preverjanje_akcij_uporabnik.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/10_pravice_za_dostop_do_strani.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/11_pregled_zgodovine.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/12_pregled_in_vracanje_kartic_VSRS.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/13_zahtevek_za_pridobitev_kode_za_odklepanje_kartice.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/14_zahtevek_za_preklic_certifikata.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/15_prikaz_strani_po_potrditvi_imetnika.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/16_zahtevek_za_prenos_certifikata.feature",
    "src/main/resources/si/vsrs/cif/pametnekartice/cucumber/scenariji/17_vracilo_kartice_ce_se_ni_prejeto_potrdilo_o_prejemu.feature"
})
public class RunCukes {
}
