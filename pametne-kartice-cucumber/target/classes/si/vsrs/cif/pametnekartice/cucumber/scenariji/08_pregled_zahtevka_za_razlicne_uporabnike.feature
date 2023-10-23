@08_pregled_zahtevka_za_razlicne_uporabnike @ALL @Test @08
Feature: 08_pregled_zahtevka_za_razlicne_uporabnike

  Scenario: 08.01 Vnos zahtevka uporabnik OJ Ljubljana
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Vnesi novo vlogo "vlogaOJL1"
      | imeOrganizacije             | naselje   | ulica       | hisnaStevilka | postnaStevilka | nazivPoste | kontaktnaOseba.imeK | kontaktnaOseba.priimekK | kontaktnaOseba.funkcijaK | kontaktnaOseba.telefonK | kontaktnaOseba.eNaslovK | predstojnik.imeP | predstojnik.priimekP | predstojnik.funkcijaP | predstojnik.eNaslovP |
      | Okrajno sodišče v Ljubljani | Ljubljana | Miklošičeva | 10            | 1000           | Ljubljana  | ime                 | priimek                 | funkcija                 | 123234234               | naslov@naslov.si        | ime              | priimek              | funkcija              | naslov@naslov.si     |
    * Dodaj novega imetnika "imetnikOJL1" na zahtevek "vlogaOJL1"
      | ime    | priimek | davcna   | emso | ENaslov                  | gesloZaPreklic |
      | Renata | Mikeln  | 12344321 |      | renata.mikeln@sodisce.si | gelo12334      |

  Scenario: 08.02 Vnos zahtevka uporabnik OJ Maribor
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    * Vnesi novo vlogo "vlogaOJM1"
      | imeOrganizacije            | naselje | ulica  | hisnaStevilka | postnaStevilka | nazivPoste | kontaktnaOseba.imeK | kontaktnaOseba.priimekK | kontaktnaOseba.funkcijaK | kontaktnaOseba.telefonK | kontaktnaOseba.eNaslovK | predstojnik.imeP | predstojnik.priimekP | predstojnik.funkcijaP | predstojnik.eNaslovP |
      | Okrajno sodišče v Mariboru | Maribor | Cafova | 1             | 2508           | Maribor    | ime                 | priimek                 | funkcija                 | 123234234               | naslov@naslov.si        | ime              | priimek              | funkcija              | naslov@naslov.si     |
    * Dodaj novega imetnika "imetnikOJM1" na zahtevek "vlogaOJM1"
      | ime      | priimek | davcna   | emso | ENaslov                  | gesloZaPreklic |
      | Severina | Ušen    | 43211234 |      | severina.usen@sodisce.si | geslo123435    |

  Scenario: 08.03 Vnos zahtevka uporabnik OJ Murska Sobota
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S25-OJ v Murski Soboti"
    * Vnesi novo vlogo "vlogaOJMS1"
      | kontaktnaOseba.imeK | kontaktnaOseba.priimekK | kontaktnaOseba.funkcijaK | kontaktnaOseba.telefonK | kontaktnaOseba.eNaslovK  | predstojnik.imeP | predstojnik.priimekP | predstojnik.funkcijaP | predstojnik.eNaslovP     |
      | imeK                | priimekK                | funkcijaK                | 123234234               | imeK.priimekK@sodisce.si | imeP             | priimekP             | funkcija              | imeP.priimekP@sodisce.si |
    * Dodaj novega imetnika "imetnikOJMS1" na zahtevek "vlogaOJMS1"
      | ime    | priimek | davcna   | emso | ENaslov               | gesloZaPreklic | imaPametnoKartico1 |
      | Angela | Pok     | 87653459 |      | angela.pok@sodisce.si | 4575dsf        | KLIKNI             |

  Scenario: 08.04 Pregled zahtevkov in imetnikov uporabnik OJ Ljubljana
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Uspešno poišči zahtevek "vlogaOJL1"
    * Uspešno poišči imetnika "imetnikOJL1"
    * Neuspešno poišči zahtevek "vlogaOJM1"
    * Neuspešno poišči imetnika "imetnikOJM1"
    * Neuspešno poišči zahtevek "vlogaOJMS1"
    * Neuspešno poišči imetnika "imetnikOJMS1"

  Scenario: 08.05 Pregled zahtevkov in imetnikov uporabnik OJ Maribor
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    * Uspešno poišči zahtevek "vlogaOJM1"
    * Uspešno poišči imetnika "imetnikOJM1"
    * Neuspešno poišči zahtevek "vlogaOJL1"
    * Neuspešno poišči imetnika "imetnikOJL1"
    * Neuspešno poišči zahtevek "vlogaOJMS1"
    * Neuspešno poišči imetnika "imetnikOJMS1"

  Scenario: 08.06 Pregled zahtevkov in imetnikov uporabnik OJ Murska Sobota
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S25-OJ v Murski Soboti"
    * Uspešno poišči zahtevek "vlogaOJMS1"
    * Uspešno poišči imetnika "imetnikOJMS1"
    * Neuspešno poišči zahtevek "vlogaOJL1"
    * Neuspešno poišči imetnika "imetnikOJL1"
    * Neuspešno poišči zahtevek "vlogaOJM1"
    * Neuspešno poišči imetnika "imetnikOJM1"

  Scenario: 08.07 Pregled zahtevkov in imetnikov administrator VSRS
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Uspešno poišči zahtevek "vlogaOJMS1"
    * Uspešno poišči imetnika "imetnikOJMS1"
    * Uspešno poišči zahtevek "vlogaOJL1"
    * Uspešno poišči imetnika "imetnikOJL1"
    * Uspešno poišči zahtevek "vlogaOJM1"
    * Uspešno poišči imetnika "imetnikOJM1"

