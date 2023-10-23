@02_vnos_vlog_za_izdajo_kartic @ALL @Test @02
Feature: 02_vnos_vlog_za_izdajo_kartic

  # Scenario: 02.00 Pocisti tabele
  # * Pripravi bazo "src/main/resources/pocisti_tabele.sql"
  Scenario: 02.01 Vnos zahtevka OJ Ljubljana
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Vnesi novo vlogo "vloga1"
      | imeOrganizacije             | naselje   | ulica       | hisnaStevilka | postnaStevilka | nazivPoste | kontaktnaOseba.imeK | kontaktnaOseba.priimekK | kontaktnaOseba.funkcijaK | kontaktnaOseba.telefonK | kontaktnaOseba.eNaslovK | predstojnik.imeP | predstojnik.priimekP | predstojnik.funkcijaP | predstojnik.eNaslovP |
      | Okrajno sodišče v Ljubljani | Ljubljana | Miklošičeva | 10            | 1000           | Ljubljana  | ime                 | priimek                 | funkcija                 | 123234234               | naslov@naslov.si        | ime              | priimek              | funkcija              | naslov@naslov.si     |
    * Dodaj novega imetnika "imetnik1" na zahtevek "vloga1"
      | ime   | priimek | davcna   | emso | ENaslov               | gesloZaPreklic |
      | Marko | Oman    | 11111111 |      | marko.oman@sodisce.si | geslo123       |
    * Dodaj novega imetnika na zahtevek "vloga1" in preveri "E-naslov" sporočilo "Imetnik s tem elektronskim naslovom ima že oddano aktivno vlogo in ni končana."
      | ime   | priimek | davcna   | emso | ENaslov               | gesloZaPreklic |
      | Marko | Oman    | 11111111 |      | marko.oman@sodisce.si | geslo123       |
    * Dodaj novega imetnika "imetnik2" na zahtevek "vloga1"
      | ime    | priimek  | davcna   | emso | ENaslov                    | gesloZaPreklic |
      | Andrej | Koncilja | 22222222 |      | andrej.koncilja@sodisce.si | geslo321       |
    * Izbriši imetnika "Marko Oman" iz zahtevka "vloga1"
    * Dodaj novega imetnika "imetnik3" na zahtevek "vloga1"
      | ime   | priimek | davcna   | emso | ENaslov               | gesloZaPreklic |
      | Marko | Oman    | 11111111 |      | MARKO.Oman@sodisce.si | geslo123       |
    * Dodaj novega imetnika "imetnik4" na zahtevek "vloga1"
      | ime  | priimek  | davcna   | emso | ENaslov                  | gesloZaPreklic |
      | Miha | Koncilja | 33333333 |      | miha.koncilja@sodisce.si | geslo567       |
    * Vnesi novo vlogo "vloga2"
      |  |
      |  |
    * Dodaj novega imetnika "imetnik5" na zahtevek "vloga2"
      | ime   | priimek | davcna   | emso | ENaslov                 | gesloZaPreklic |
      | Tomaž | Tomšič  | 44444444 |      | tomaz.tomsic@sodisce.si | geslo765       |

  Scenario: 02.02 Vnos zahtevka OJ Maribor
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    * Vnesi novo vlogo "vloga3"
      | imeOrganizacije            | naselje | ulica  | hisnaStevilka | postnaStevilka | nazivPoste | kontaktnaOseba.imeK | kontaktnaOseba.priimekK | kontaktnaOseba.funkcijaK | kontaktnaOseba.telefonK | kontaktnaOseba.eNaslovK | predstojnik.imeP | predstojnik.priimekP | predstojnik.funkcijaP | predstojnik.eNaslovP |
      | Okrajno sodišče v Mariboru | Maribor | Cafova | 1             | 2508           | Maribor    | ime                 | priimek                 | funkcija                 | 123234234               | naslov@naslov.si        | ime              | priimek              | funkcija              | naslov@naslov.si     |
    * Vnesi novo vlogo "vloga4"
      |  |
      |  |
    * Dodaj novega imetnika "imetnik6" na zahtevek "vloga4"
      | ime  | priimek   | davcna   | emso | ENaslov                   | gesloZaPreklic |
      | Jože | Rihtaršič | 77777777 |      | Joze.Rihtarsic@sodisce.si | geslo213       |
    * Dodaj novega imetnika "imetnik7" na zahtevek "vloga4"
      | ime     | priimek | davcna   | emso | ENaslov                   | gesloZaPreklic |
      | Boštjan | Vidmar  | 88888888 |      | bostjan.vidmar@sodisce.si | geslo1903      |
    * Izbriši zahtevek "vloga3"
    * Potrdi vlogo "vloga4"
    * Odkleni zahtevek "vloga4"
    * Potrdi vlogo "vloga4"
    * Posreduj na CIF zahtevek "vloga4" in preveri sporočilo "Prosim, da natisnite vse bodoče imetnike preden vlogo posredujete na CIF."
    * Natisni vse imetnike na zahtevku "vloga4"
    * Natisni zahtevek "vloga4"
    * Posreduj na CIF zahtevek "vloga4"

  Scenario: 02.03 Brisanje zahtevka OJ Ljubljana
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Izbriši zahtevek "vloga2"

  Scenario: 02.04 Dodajanje novega imetnika in posredovanje na CIF OJ Ljubljana
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Dodaj novega imetnika "imetnik8" na zahtevek "vloga1"
      | ime   | priimek | davcna   | emso | ENaslov                 | gesloZaPreklic |
      | Tomaž | Tomšič  | 44444444 |      | tomaz.tomsic@sodisce.si | geslo765       |
    * Vnesi opombo "Vsebina opombe" z naslovom "Naslov opombe" na zahtevek "vloga1"
    * Potrdi vlogo "vloga1"
    * Odkleni zahtevek "vloga1"
    * Potrdi vlogo "vloga1"
    * Posreduj na CIF zahtevek "vloga1" in preveri sporočilo "Prosim, da natisnite vse bodoče imetnike preden vlogo posredujete na CIF."
    * Natisni vse imetnike na zahtevku "vloga1"
    * Posreduj na CIF zahtevek "vloga1" in preveri sporočilo "Prosim, da natisnite vlogo preden jo posredujete na CIF."
    * Natisni zahtevek "vloga1"
    * Posreduj na CIF zahtevek "vloga1"

  Scenario: 02.05 Testiranje pregleda imetnikov OJ Maribor
    #Poišči imetnika "" pomeni, da ni rezultata za iskano
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    * Poišči imetnika "Jože Rihtaršič" po besedi "joze.rihtarsic@sodisce.si"
    * Poišči imetnika "Boštjan Vidmar" po besedi "88888888"
    * Poišči imetnika "" po besedi "22222222"
    * Poišči imetnika "" po besedi "marko.oman@sodisce.si"

  Scenario: 02.06 Testiranje pregleda imetnikov OJ Ljubljana
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Poišči imetnika "" po besedi "joze.rihtarsic@sodisce.si"
    * Poišči imetnika "" po besedi "88888888"
    * Poišči imetnika "Andrej Koncilja" po besedi "22222222"
    * Poišči imetnika "Marko Oman" po besedi "marko.oman@sodisce.si"
