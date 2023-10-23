@00_nastavitve_za_tesno_okolje @ALL @Test @00
Feature: 00_nastavitve_za_tesno_okolje

#delete from CERTIFIKATI;
#delete from KARTICA;
#delete from IMETNIK;
#delete from OPOMBA;
#delete from STATUS_LOG;
#delete from STATUS_LOG_IMETNIK;
#delete from ZAHTEVEK;
#delete from ZAHTEVEK_TEMP;
#delete from ZAHTEVEK_ZA_KODO;
#delete from ZAHTEVEK_ZA_PREKLIC;
#delete from ZAHTEVEK_ZA_PRENOS;
#delete from STATUS_LOG_ZAHTEVEK_ZA_KODO;
#delete from JOBLOG;
#delete from STATUS_LOG_PREKLIC;
#delete from STATUS_LOG_PRENOS;
#delete from ZAHTEVEK_TEMP;

Scenario: 00.01 Nastavitve za testno okolje
   * Nastavi spletno stran "http://rigel.sodisce.si:18031/pametne-kartice-web/login"
# * Nastavi spletno stran "http://10.48.24.210:8080/pametne-kartice-web/login"
    * Namesto uporabniškega imena "*" uporabi uporabniško ime "S011274"
    * Namesto gesla "*" uporabi geslo ""
    * Namesto vloge "002:S23-OJ v Ljubljani" uporabi vlogo "002:S01-Vrhovno sodišče RS"