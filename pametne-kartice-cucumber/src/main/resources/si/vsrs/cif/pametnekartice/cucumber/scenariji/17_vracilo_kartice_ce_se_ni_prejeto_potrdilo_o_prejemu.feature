@17_vracilo_kartice_ce_se_ni_prejeto_potrdilo_o_prejemu @ALL @Test @17
Feature: 17_vracilo_kartice_ce_se_ni_prejeto_potrdilo_o_prejemu

  #Isti imetniki, kot pri 16_zahtevek_za_prenos_certifikata
  Scenario: 17.01 Dodajanje novega zahtevka in imetnikov OJ ljubljana
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Vnesi novo vlogo "vlogaVKLJ1"
      | imeOrganizacije             | naselje   | ulica       | hisnaStevilka | postnaStevilka | nazivPoste | kontaktnaOseba.imeK | kontaktnaOseba.priimekK | kontaktnaOseba.funkcijaK | kontaktnaOseba.telefonK | kontaktnaOseba.eNaslovK | predstojnik.imeP | predstojnik.priimekP | predstojnik.funkcijaP | predstojnik.eNaslovP |
      | Okrajno sodišče v Ljubljani | Ljubljana | Miklošičeva | 10            | 1000           | Ljubljana  | ime                 | priimek                 | funkcija                 | 123234234               | naslov@naslov.si        | ime              | priimek              | funkcija              | naslov@naslov.si     |
    * Dodaj novega imetnika "imetnikVKLJ1" na zahtevek "vlogaVKLJ1"
      | ime      | priimek | davcna   | emso | ENaslov                   | gesloZaPreklic |
      | Natalija | Mimić   | 08756214 |      | natalija.mimic@sodisce.si | gelo123        |
    * Dodaj novega imetnika "imetnikVKLJ2" na zahtevek "vlogaVKLJ1"
      | ime    | priimek | davcna   | emso | ENaslov                 | gesloZaPreklic |
      | Nataša | Fesel   | 66655511 |      | natasa.fesel@sodisce.si | gelo542        |
    * Potrdi vlogo "vlogaVKLJ1"
    * Natisni vse imetnike na zahtevku "vlogaVKLJ1"
    * Natisni zahtevek "vlogaVKLJ1"
    * Posreduj na CIF zahtevek "vlogaVKLJ1"

  Scenario: 17.02 Dodajanje novega zahtevka in imetnika OJ Maribor
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    * Vnesi novo vlogo "vlogaVKMB1"
      | imeOrganizacije            | naselje | ulica  | hisnaStevilka | postnaStevilka | nazivPoste | kontaktnaOseba.imeK | kontaktnaOseba.priimekK | kontaktnaOseba.funkcijaK | kontaktnaOseba.telefonK | kontaktnaOseba.eNaslovK | predstojnik.imeP | predstojnik.priimekP | predstojnik.funkcijaP | predstojnik.eNaslovP |
      | Okrajno sodišče v Mariboru | Maribor | Cafova | 1             | 2508           | Maribor    | ime                 | priimek                 | funkcija                 | 123234234               | naslov@naslov.si        | ime              | priimek              | funkcija              | naslov@naslov.si     |
    * Dodaj novega imetnika "imetnikVKMB1" na zahtevek "vlogaVKMB1"
      | ime    | priimek | davcna   | emso | ENaslov                  | gesloZaPreklic |
      | Simona | Brumec  | 54390321 |      | simona.brumec@sodisce.si | gelo000        |
    * Potrdi vlogo "vlogaVKMB1"
    * Natisni vse imetnike na zahtevku "vlogaVKMB1"
    * Natisni zahtevek "vlogaVKMB1"
    * Posreduj na CIF zahtevek "vlogaVKMB1"

  Scenario: 17.03 Admin potrdi in prevzemi certifikate za oddana zahtevka
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Potrdi vse imetnike na zahtevku "vlogaVKLJ1"
    * Potrdi zahtevek "vlogaVKLJ1"
    * Izvozi zahtevek "vlogaVKLJ1"
    * Potrdi vse imetnike na zahtevku "vlogaVKMB1"
    * Potrdi zahtevek "vlogaVKMB1"
    * Izvozi zahtevek "vlogaVKMB1"
    * Posreduj na SIGOV-CA vse zahtevke in preveri "success" sporočilo "Število zahtevkov, ki so bili posredovani na SIGOV-CA:2"

  Scenario: 17.04 Prevzemi certifikate in odpremi kartice
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Naloži datoteko s podatki o certifikatih za prevzem "src/main/resources/Podatki za uvoz/Od_SIGOV-CA_Uvoz_podatkov_za_prevzem_test-16.csv"
    * Naloži datoteko s podatki o karticah "src/main/resources/Podatki za uvoz/Uvoz_podatkov_iz_kartice_default_test-16.db"
    * Poišči kartico po črtni kodi "278323" in jo odpremi
    * Poišči kartico po črtni kodi "278324" in jo odpremi
    * Poišči kartico po črtni kodi "278338" in jo odpremi    

  Scenario: 17.05 Vrni kartice in preveri, da se je spremenil status imetnika
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Preveri status imetnika "imetnikVKMB1" - "Pametna kartica odpremljena"
    * Preveri status zahtevka "vlogaVKMB1" - "Posredovano na SIGOV-CA"
    * Potrdi, da je bila dne "27.01.2014" kartica VSRS z iskalnim nizom "278338" vrnjena
    * Preveri status imetnika "imetnikVKMB1" - "Pametna kartica prevzeta"
    * Preveri status zahtevka "vlogaVKMB1" - "Dokončan"
    * Preveri status zahtevka "vlogaVKLJ1" - "Posredovano na SIGOV-CA"
    * Poišči imetnika "imetnikVKLJ2" po črtni kodi in potrdi potrdilo o prejeti kartici dne "19.11.2014"
    * Preveri status imetnika "imetnikVKLJ2" - "Pametna kartica prevzeta"
    * Preveri status zahtevka "vlogaVKLJ1" - "Posredovano na SIGOV-CA"
    * Preveri status imetnika "imetnikVKLJ1" - "Pametna kartica odpremljena"
    * Potrdi, da je bila dne "27.01.2014" kartica VSRS z iskalnim nizom "278323" vrnjena
    * Preveri status imetnika "imetnikVKLJ1" - "Pametna kartica prevzeta"
    * Preveri status zahtevka "vlogaVKLJ1" - "Dokončan"
