@01_priajva @ALL @Test @01
Feature: 01_prijava

  Scenario: 01.01.  Prijava z eno vlogo in enim sodiscem
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200002" in geslom "T200002"
    * Preveri naslov strani "Index"

  Scenario: 01.02. Prijava z vecimi vlogami ali sodisci - prijava
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003"
    * Preveri naslov strani "Izbira"

  Scenario: 01.03. Prijava z vecimi vlogami ali sodisci - izbira
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Preveri naslov strani "Index"

  Scenario: 01.04. Prijava brez vloge - ni pravic za vstop v aplikacijo
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200001" in geslom "T200001"
    * Preveri naslov strani "Prijava"
