@09_preverjanje_akcij_uporabnik @ALL @Test @09
Feature: 09_preverjanje_akcij_uporabnik

  Scenario: 09.01 Vnos novega zahtevka in imetnika
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Vnesi novo vlogo "vlogaAkcije1"
      | imeOrganizacije             | naselje   | ulica       | hisnaStevilka | postnaStevilka | nazivPoste | kontaktnaOseba.imeK | kontaktnaOseba.priimekK | kontaktnaOseba.funkcijaK | kontaktnaOseba.telefonK | kontaktnaOseba.eNaslovK | predstojnik.imeP | predstojnik.priimekP | predstojnik.funkcijaP | predstojnik.eNaslovP |
      | Okrajno sodišče v Ljubljani | Ljubljana | Miklošičeva | 10            | 1000           | Ljubljana  | ime                 | priimek                 | funkcija                 | 123234234               | naslov@naslov.si        | ime              | priimek              | funkcija              | naslov@naslov.si     |
    * Dodaj novega imetnika "imetnikAkcije1" na zahtevek "vlogaAkcije1"
      | ime    | priimek | davcna   | emso | ENaslov                 | gesloZaPreklic |
      | Alenka | Urbas   | 12344321 |      | alenka.urbas@sodisce.si | gelo456        |
    * Nastavi root strani "http://localhost:8080/pametne-kartice-web/"
    #* Nastavi root strani "rigel.sodisce.si:18031/pametne-kartice-web/"

  Scenario: 09.01.01 Preverjanje prepovedanih akcij za zahtevek in imetnika v statusu 01 - Vloga 001
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Nastavi id zahtevka "vlogaAkcije1"
    * Nastavi id imetnika "imetnikAkcije1"
    * Dostopi do URL-ja "printZahtevek/idZahtevka" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "posredujZahtevek/idZahtevka" in preveri naslov strani "Podrobno"
    * Preveri status zahtevka "vlogaAkcije1" - "V pripravi"
    * Preveri status imetnika "imetnikAkcije1" - "V pripravi"
    * Dostopi do URL-ja "printImetnike/idZahtevka" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "Vloga/printImetnik/idZahtevka/idImetnika" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "index"
    * Preveri status zahtevka "vlogaAkcije1" - "V pripravi"
    * Preveri status imetnika "imetnikAkcije1" - "V pripravi"

  Scenario: 09.02 Zaključi zahtevek in imetnika
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Potrdi vlogo "vlogaAkcije1"
    * Preveri status zahtevka "vlogaAkcije1" - "Zaključen"
    * Preveri status imetnika "imetnikAkcije1" - "Zaključen"

  Scenario: 09.02.01 Preverjanje prepovedanih akcij za zahtevek in imetnika v statusu 02 - Vloga 001
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Nastavi id zahtevka "vlogaAkcije1"
    * Nastavi id imetnika "imetnikAkcije1"
    * Dostopi do URL-ja "Vloga/uredi/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "brisiVlogo/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "/Vloga/imetnik/dodaj/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/uredi/idZahtevka/idImetnika" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/process/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/brisiImetnik/idZahtevka/idImetnika" in preveri naslov strani "Index"
    * Preveri status zahtevka "vlogaAkcije1" - "Zaključen"
    * Preveri status imetnika "imetnikAkcije1" - "Zaključen"

  Scenario: 09.03 Posreduj zahtevek na CIF
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Natisni vse imetnike na zahtevku "vlogaAkcije1"
    * Natisni zahtevek "vlogaAkcije1"
    * Posreduj na CIF zahtevek "vlogaAkcije1"
    * Preveri status zahtevka "vlogaAkcije1" - "Posredovano na CIF"
    * Preveri status imetnika "imetnikAkcije1" - "Posredovano na CIF"

  Scenario: 09.03.01 Preverjanje prepovedanih akcij za zahtevek in imetnika v statusu 03 - Vloga 001
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Nastavi id zahtevka "vlogaAkcije1"
    * Nastavi id imetnika "imetnikAkcije1"
    * Dostopi do URL-ja "Vloga/uredi/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "brisiVlogo/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "potrdiVlogo/idZahtevka" in preveri naslov strani "Podrobno"
    * Dostopi do URL-ja "printZahtevek/idZahtevka" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "odkleniZahtevek/idZahtevka" in preveri naslov strani "Podrobno"
    * Dostopi do URL-ja "Vloga/imetnik/dodaj/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/uredi/idZahtevka/idImetnika" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/process/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/brisiImetnik/idZahtevka/idImetnika" in preveri naslov strani "Index"
    * Dostopi do URL-ja "printImetnike/idZahtevka" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "Vloga/printImetnik/idZahtevka/idImetnika" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "index"
    * Preveri status zahtevka "vlogaAkcije1" - "Posredovano na CIF"
    * Preveri status imetnika "imetnikAkcije1" - "Posredovano na CIF"

  Scenario: 09.04 Vnos in izbris novega zahtevka in imetnika
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Vnesi novo vlogo "vlogaAkcije2"
      | imeOrganizacije             | naselje   | ulica       | hisnaStevilka | postnaStevilka | nazivPoste | kontaktnaOseba.imeK | kontaktnaOseba.priimekK | kontaktnaOseba.funkcijaK | kontaktnaOseba.telefonK | kontaktnaOseba.eNaslovK | predstojnik.imeP | predstojnik.priimekP | predstojnik.funkcijaP | predstojnik.eNaslovP |
      | Okrajno sodišče v Ljubljani | Ljubljana | Miklošičeva | 10            | 1000           | Ljubljana  | ime                 | priimek                 | funkcija                 | 123234234               | naslov@naslov.si        | ime              | priimek              | funkcija              | naslov@naslov.si     |
    * Dodaj novega imetnika "imetnikAkcije2" na zahtevek "vlogaAkcije2"
      | ime     | priimek | davcna   | emso | ENaslov                    | gesloZaPreklic |
      | Nevenka | Pangerc | 12344321 |      | nevenka.pangerc@sodisce.si | gelo586        |
    * Preveri status zahtevka "vlogaAkcije2" - "V pripravi"
    * Preveri status imetnika "imetnikAkcije2" - "V pripravi"
    * Nastavi id zahtevka "vlogaAkcije2"
    * Nastavi id imetnika "imetnikAkcije2"
    * Izbriši zahtevek "vlogaAkcije2"
    * Neuspešno poišči zahtevek "vlogaAkcije2"
    * Neuspešno poišči imetnika "imetnikAkcije2"

  Scenario: 09.04.01 Preverjanje prepovedanih akcij za zahtevek in imetnika v statusu 04 - Vloga 001
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Dostopi do URL-ja "izpisPodrobno/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/uredi/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "brisiVlogo/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "potrdiVlogo/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "printZahtevek/idZahtevka" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "odkleniZahtevek/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "posredujZahtevek/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/dodajOpombo/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/dodaj/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/uredi/idZahtevka/idImetnika" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/process/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/brisiImetnik/idZahtevka/idImetnika" in preveri naslov strani "Index"
    * Dostopi do URL-ja "printImetnike/idZahtevka" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "Vloga/printImetnik/idZahtevka/idImetnika" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "pregledImetnikovPodrobno/idImetnika" in preveri naslov strani "Index"
    * Neuspešno poišči zahtevek "vlogaAkcije2"
    * Neuspešno poišči imetnika "imetnikAkcije2"

  Scenario: 09.05 Zavrnitev imetnika in zahtevka
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Zavrni vse imetnike na zahtevku "vlogaAkcije1" z razlogom "Razlog za zavrnitev imetnika."
    * Zavrni zahtevek "vlogaAkcije1" z razlogom zavrnitve "Razlog za zavrnitev zahtevka."
    * Preveri status zahtevka "vlogaAkcije1" - "Zavrnjen"
    * Preveri status imetnika "imetnikAkcije1" - "Zavrnjen"

  Scenario: 09.05.01 Preverjanje prepovedanih akcij za zahtevek in imetnika v statusu 06 - Vloga 001
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Nastavi id zahtevka "vlogaAkcije1"
    * Nastavi id imetnika "imetnikAkcije1"
    * Dostopi do URL-ja "Vloga/uredi/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "brisiVlogo/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "potrdiVlogo/idZahtevka" in preveri naslov strani "Podrobno"
    * Dostopi do URL-ja "printZahtevek/idZahtevka" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "posredujZahtevek/idZahtevka" in preveri naslov strani "Podrobno"
    * Dostopi do URL-ja "Vloga/imetnik/dodaj/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/uredi/idZahtevka/idImetnika" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/process/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/brisiImetnik/idZahtevka/idImetnika" in preveri naslov strani "Index"
    * Dostopi do URL-ja "printImetnike/idZahtevka" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "Vloga/printImetnik/idZahtevka/idImetnika" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "index"
    * Preveri status zahtevka "vlogaAkcije1" - "Zavrnjen"
    * Preveri status imetnika "imetnikAkcije1" - "Zavrnjen"

  Scenario: 09.06 Dopolnitev zahtevka
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Dopolni zahtevek "vlogaAkcije1"
    * Potrdi vlogo "vlogaAkcije1"
    * Natisni vse imetnike na zahtevku "vlogaAkcije1"
    * Natisni zahtevek "vlogaAkcije1"
    * Posreduj na CIF zahtevek "vlogaAkcije1"

  Scenario: 09.07 Potrdi imetnike in zahtevek
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Potrdi vse imetnike na zahtevku "vlogaAkcije1"
    * Potrdi zahtevek "vlogaAkcije1"
    * Preveri status zahtevka "vlogaAkcije1" - "Preverjen"
    * Preveri status imetnika "imetnikAkcije1" - "Potrjen"

  Scenario: 09.07.01 Preverjanje prepovedanih akcij za zahtevek in imetnika v statusu 05 - Vloga 001
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Nastavi id zahtevka "vlogaAkcije1"
    * Nastavi id imetnika "imetnikAkcije1"
    * Dostopi do URL-ja "Vloga/uredi/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "brisiVlogo/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "potrdiVlogo/idZahtevka" in preveri naslov strani "Podrobno"
    * Dostopi do URL-ja "printZahtevek/idZahtevka" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "odkleniZahtevek/idZahtevka" in preveri naslov strani "Podrobno"
    * Dostopi do URL-ja "posredujZahtevek/idZahtevka" in preveri naslov strani "Podrobno"
    * Dostopi do URL-ja "Vloga/imetnik/dodaj/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/uredi/idZahtevka/idImetnika" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/process/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/brisiImetnik/idZahtevka/idImetnika" in preveri naslov strani "Index"
    * Dostopi do URL-ja "printImetnike/idZahtevka" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "Vloga/printImetnik/idZahtevka/idImetnika" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "index"
    * Preveri status zahtevka "vlogaAkcije1" - "Preverjen"
    * Preveri status imetnika "imetnikAkcije1" - "Potrjen"

  Scenario: 09.08 Zahtevek posreduj na SIGOV-CA
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Izvozi zahtevek "vlogaAkcije1"
    * Posreduj na SIGOV-CA zahtevek "vlogaAkcije1"
    * Preveri status zahtevka "vlogaAkcije1" - "Posredovano na SIGOV-CA"
    * Preveri status imetnika "imetnikAkcije1" - "Posredovano na SIGOV-CA"

  Scenario: 09.08.01 Preverjanje prepovedanih akcij za zahtevek in imetnika v statusu 07 - Vloga 001
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Nastavi id zahtevka "vlogaAkcije1"
    * Nastavi id imetnika "imetnikAkcije1"
    * Dostopi do URL-ja "Vloga/uredi/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "brisiVlogo/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "potrdiVlogo/idZahtevka" in preveri naslov strani "Podrobno"
    * Dostopi do URL-ja "printZahtevek/idZahtevka" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "odkleniZahtevek/idZahtevka" in preveri naslov strani "Podrobno"
    * Dostopi do URL-ja "posredujZahtevek/idZahtevka" in preveri naslov strani "Podrobno"
    * Dostopi do URL-ja "Vloga/imetnik/dodaj/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/uredi/idZahtevka/idImetnika" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/process/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/brisiImetnik/idZahtevka/idImetnika" in preveri naslov strani "Index"
    * Dostopi do URL-ja "printImetnike/idZahtevka" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "Vloga/printImetnik/idZahtevka/idImetnika" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "index"
    * Preveri status zahtevka "vlogaAkcije1" - "Posredovano na SIGOV-CA"
    * Preveri status imetnika "imetnikAkcije1" - "Posredovano na SIGOV-CA"

  Scenario: 09.09 Uvoz podatkov in odprema kartice *
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Naloži datoteko s podatki o certifikatih za prevzem "src/main/resources/Podatki za uvoz/Od_SIGOV-CA_Uvoz_podatkov_za_prevzem1.csv"
    * Naloži datoteko s podatki o karticah "src/main/resources/Podatki za uvoz/Uvoz_podatkov_iz_kartice_default1.db"
    * Poišči kartico po črtni kodi "011111" in jo odpremi
    * Preveri status zahtevka "vlogaAkcije1" - "Posredovano na SIGOV-CA"
    * Preveri status imetnika "imetnikAkcije1" - "Pametna kartica odpremljena"

  #  Scenario: 09.09 Uvoz podatkov za prevzem certifikata
  #      * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
  #      * Naloži datoteko s podatki o certifikatih za prevzem "src/main/resources/Podatki za uvoz/Od_SIGOV-CA_Uvoz_podatkov_za_prevzem1.csv"
  # Scenario: 09.09.01 Preverjanje prepovedanih akcij za certifikat v statusu 01 - Vloga 003
  #     * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "003:S23-OJ v Ljubljani"
  #Scenario: 09.16.2 Uvoz podatkov iz kartice *
  #     * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
  #      * Naloži datoteko s podatki o karticah "src/main/resources/Podatki za uvoz/Uvoz_podatkov_iz_kartice_default1.db"
  #Scenario: 09.16.3 Odprema kartice *
  #  * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
  #    * Poišči kartico po črtni kodi "011111" in jo odpremi
  #  * Preveri status zahtevka "vlogaAkcije1" - "Posredovano na SIGOV-CA"
  #   * Preveri status imetnika "imetnikAkcije1" - "Pametna kartica odpremljena"
  Scenario: 09.09.01 Preverjanje prepovedanih akcij za zahtevek v statusu 07 in imetnika v statusu 08 - Vloga 001
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Nastavi id zahtevka "vlogaAkcije1"
    * Nastavi id imetnika "imetnikAkcije1"
    * Dostopi do URL-ja "Vloga/uredi/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "brisiVlogo/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "potrdiVlogo/idZahtevka" in preveri naslov strani "Podrobno"
    * Dostopi do URL-ja "printZahtevek/idZahtevka" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "odkleniZahtevek/idZahtevka" in preveri naslov strani "Podrobno"
    * Dostopi do URL-ja "posredujZahtevek/idZahtevka" in preveri naslov strani "Podrobno"
    * Dostopi do URL-ja "Vloga/imetnik/dodaj/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/uredi/idZahtevka/idImetnika" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/process/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/brisiImetnik/idZahtevka/idImetnika" in preveri naslov strani "Index"
    * Dostopi do URL-ja "printImetnike/idZahtevka" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "Vloga/printImetnik/idZahtevka/idImetnika" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "index"
    * Preveri status zahtevka "vlogaAkcije1" - "Posredovano na SIGOV-CA"
    * Preveri status imetnika "imetnikAkcije1" - "Pametna kartica odpremljena"

  Scenario: 09.10 Vnos potrdila o prejeti kartici
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Poišči imetnika "imetnikAkcije1" po črtni kodi in potrdi potrdilo o prejeti kartici dne "22.11.2013"
    * Preveri status zahtevka "vlogaAkcije1" - "Dokončan"
    * Preveri status imetnika "imetnikAkcije1" - "Pametna kartica prevzeta"

  Scenario: 09.10.01 Preverjanje prepovedanih akcij za zahtevek v statusu 08 in imetnika v statusu 09 - Vloga 001
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Nastavi id zahtevka "vlogaAkcije1"
    * Nastavi id imetnika "imetnikAkcije1"
    * Dostopi do URL-ja "Vloga/uredi/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "brisiVlogo/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "potrdiVlogo/idZahtevka" in preveri naslov strani "Podrobno"
    * Dostopi do URL-ja "printZahtevek/idZahtevka" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "odkleniZahtevek/idZahtevka" in preveri naslov strani "Podrobno"
    * Dostopi do URL-ja "posredujZahtevek/idZahtevka" in preveri naslov strani "Podrobno"
    * Dostopi do URL-ja "Vloga/imetnik/dodaj/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/uredi/idZahtevka/idImetnika" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/imetnik/process/idZahtevka" in preveri naslov strani "Index"
    * Dostopi do URL-ja "Vloga/brisiImetnik/idZahtevka/idImetnika" in preveri naslov strani "Index"
    * Dostopi do URL-ja "printImetnike/idZahtevka" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "Vloga/printImetnik/idZahtevka/idImetnika" in preveri naslov strani "PRAZNO"
    * Dostopi do URL-ja "index"
    * Preveri status zahtevka "vlogaAkcije1" - "Dokončan"
    * Preveri status imetnika "imetnikAkcije1" - "Pametna kartica prevzeta"
