@15_prikaz_strani_po_potrditvi_imetnika @ALL @Test @15
Feature: 15_prikaz_strani_po_potrditvi_imetnika

  Scenario: 15.01 Dodajanje zahtevka in imetnikov
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Vnesi novo vlogo "vloga15_1"
      | imeOrganizacije             | naselje   | ulica       | hisnaStevilka | postnaStevilka | nazivPoste | kontaktnaOseba.imeK | kontaktnaOseba.priimekK | kontaktnaOseba.funkcijaK | kontaktnaOseba.telefonK | kontaktnaOseba.eNaslovK | predstojnik.imeP | predstojnik.priimekP | predstojnik.funkcijaP | predstojnik.eNaslovP |
      | Okrajno sodišče v Ljubljani | Ljubljana | Miklošičeva | 10            | 1000           | Ljubljana  | ime                 | priimek                 | funkcija                 | 123234234               | naslov@naslov.si        | ime              | priimek              | funkcija              | naslov@naslov.si     |
    * Dodaj novega imetnika "imetnik15_1" na zahtevek "vloga15_1"
      | ime  | priimek | davcna   | emso | ENaslov                 | gesloZaPreklic |
      | Nina | Bogataj | 22222222 |      | nina.bogataj@sodisce.si | geslo321       |
    * Potrdi vlogo "vloga15_1"
    * Natisni vse imetnike na zahtevku "vloga15_1"
    * Natisni zahtevek "vloga15_1"
    * Posreduj na CIF zahtevek "vloga15_1"


  Scenario: 15.02 Potrdi vloge in preveri prikaz strani
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Iz strani za iskanje poišči in potrdi imetnika "imetnik15_1", ter preveri, da je odprta stran za iskanje