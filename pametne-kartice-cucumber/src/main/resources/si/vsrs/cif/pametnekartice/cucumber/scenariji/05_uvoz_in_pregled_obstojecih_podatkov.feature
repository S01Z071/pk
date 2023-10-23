@05_uvoz_in_pregled_obstojecih_podatkov @ALL @Test @05
Feature: 05_uvoz_obstojecih_podatkov

#Mozne napake zaradi datumov v datoteki
#SUMNIKI!
  Scenario: 05.01 Uvoz obstoječih podatkov
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Uvozi obstoječe certifikate iz datoteke "src/main/resources/Podatki za uvoz/seznam_potrdil_sodicse_04102013.csv" in preveri sporočila "Uspešno vnešenih vrstic v bazo:513", "Neuspešno vnešenih vrstic v bazo:15", "Certifikatov, ki jih ni bilo mogoče povezati s šifro sodišča:75"
    * Uvozi obstoječe podatke o karticah iz datoteke "src/main/resources/Podatki za uvoz/seznam_kartic_sodisce_04102013.csv" in preveri sporočila "Uspešno vnešenih vrstic v bazo:437"
    * Uvozi obstoječe podatke o čitalcih iz datoteke "src/main/resources/Podatki za uvoz/seznam_citalcev_sodisce_04102013.csv" in preveri sporočila "Uspešno vnešenih vrstic v bazo:247"

  Scenario: 05.02 Pregled certifikatov admin
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Poišči certifikate za "s25" in preveri število prikazanih certifikatov "37"
    * Poišči certifikate za "S01" in preveri število prikazanih certifikatov "86"
    * Poišči certifikate za "s23" in preveri število prikazanih certifikatov "0"

  Scenario: 05.03 Pregled certifikatov uporabnik
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S25-OJ v Murski Soboti"
    * Pojdi na pregled certifikatov in preveri število prikazanih certifikatov "29"

  Scenario: 05.04 Pregled kartic admin
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Poišči "vrnjene" kartice za "s25" in preveri število prikazanih kartic "0"
    * Poišči "izdane" kartice za "S25" in preveri število prikazanih kartic "44"
    * Poišči "vse" kartice za "s25" in preveri število prikazanih kartic "44"
    * Poišči "izdane" kartice za "S23" in preveri število prikazanih kartic "26"
    * Poišči "vrnjene" kartice za "S01" in preveri število prikazanih kartic "2"
    * Poišči "vse" kartice za "S27" in preveri število prikazanih kartic "0"

  Scenario: 05.05 Pregled kartic uporabnik
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S25-OJ v Murski Soboti"
    * Pojdi na pregled kartic in preveri število prikazanih kartic "44"

  Scenario: 05.06 Pregled čitalcev admin
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Poišči "vrnjene" čitalce za "s25" in preveri število prikazanih čitalcev "5"
    * Poišči "izdane" čitalce za "S25" in preveri število prikazanih čitalcev "35"
    * Poišči "vse" čitalce za "s25" in preveri število prikazanih čitalcev "40"
    * Poišči "izdane" čitalce za "S23" in preveri število prikazanih čitalcev "21"
    * Poišči "vrnjene" čitalce za "S01" in preveri število prikazanih čitalcev "0"
    * Poišči "vse" čitalce za "S27" in preveri število prikazanih čitalcev "2"

  Scenario: 05.07 Pregled čitalcev uporabnik
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S25-OJ v Murski Soboti"
    * Pojdi na pregled čitalcev in preveri število prikazanih čitalcev "35"
