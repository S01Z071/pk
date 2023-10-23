@07_preverjanje_delovanja_vrnjene_opreme @ALL @Test @07
Feature: 07_preverjanje_delovanja_vrnjene_opreme

  Scenario: 07.01 Vnos vrnjene opreme
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Potrdi, da je bila dne "17.12.2013" oprema "kartica" z oznako "409061451D42280F1006" vrnjena
    * Potrdi, da je bila dne "17.12.2013" oprema "čitalec" z oznako "21120829206870" vrnjena

  Scenario: 07.02 Vnos imetnika, ki je vrnil opremo
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S25-OJ v Murski Soboti"
    * Vnesi novo vlogo "vlogaMS1"
      | kontaktnaOseba.imeK | kontaktnaOseba.priimekK | kontaktnaOseba.funkcijaK | kontaktnaOseba.telefonK | kontaktnaOseba.eNaslovK  | predstojnik.imeP | predstojnik.priimekP | predstojnik.funkcijaP | predstojnik.eNaslovP     |
      | imeK                | priimekK                | funkcijaK                | 123234234               | imeK.priimekK@sodisce.si | imeP             | priimekP             | funkcija              | imeP.priimekP@sodisce.si |
    * Dodaj novega imetnika "imetnikMS2" na zahtevek "vlogaMS1"
      | ime    | priimek | davcna   | emso | ENaslov                  | gesloZaPreklic | imaCitalec |
      | Monika | Kolosa  | 11111111 |      | monika.kolosa@sodisce.si | geslo123       | KLIKNI     |
    * Pojdi na podroben pregled imetnika "imetnikMS2"
    * Preveri, da besedilo "Seznam kartic SIGOV-CA, ki jih ima imetnik v lasti" ni prikazano
    * Preveri, da besedilo "Seznam čitalcev SIGOV-CA, ki jih ima imetnik v lasti" ni prikazano

  Scenario: 07.03 Vnos vrnjene opreme in pregled imetnika, ki je opremo vrnil
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Potrdi, da je bila dne "17.12.2013" oprema "kartica" z oznako "409061451D42280F301A" vrnjena
    * Potrdi, da je bila dne "17.12.2013" oprema "čitalec" z oznako "21120829206877" vrnjena
    * Pojdi na podroben pregled imetnika "imetnik1MS"
    * Preveri, da besedilo "Seznam kartic SIGOV-CA, ki jih ima imetnik v lasti" ni prikazano
    * Preveri, da besedilo "ActivCard 64k - 409061451D42280F301A" ni prikazano
    * Preveri, da besedilo "Seznam čitalcev SIGOV-CA, ki jih ima imetnik v lasti" ni prikazano
    * Preveri, da besedilo "ActivCard USB - 21120829206877" ni prikazano

