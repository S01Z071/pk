@16_zahtevek_za_prenos_certifikata @ALL @Test @16
Feature: 16_zahtevek_za_prenos_certifikata

  Scenario: 16.01 Dodajanje novega zahtevka in imetnikov OJ ljubljana
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Vnesi novo vlogo "vlogaPrenosLJ1"
      | imeOrganizacije             | naselje   | ulica       | hisnaStevilka | postnaStevilka | nazivPoste | kontaktnaOseba.imeK | kontaktnaOseba.priimekK | kontaktnaOseba.funkcijaK | kontaktnaOseba.telefonK | kontaktnaOseba.eNaslovK | predstojnik.imeP | predstojnik.priimekP | predstojnik.funkcijaP | predstojnik.eNaslovP |
      | Okrajno sodišče v Ljubljani | Ljubljana | Miklošičeva | 10            | 1000           | Ljubljana  | ime                 | priimek                 | funkcija                 | 123234234               | naslov@naslov.si        | ime              | priimek              | funkcija              | naslov@naslov.si     |
    * Dodaj novega imetnika "imetnikPrenosLJ1" na zahtevek "vlogaPrenosLJ1"
      | ime      | priimek | davcna   | emso | ENaslov                   | gesloZaPreklic |
      | Natalija | Mimić   | 08756214 |      | natalija.mimic@sodisce.si | gelo123        |
    * Dodaj novega imetnika "imetnikPrenosLJ2" na zahtevek "vlogaPrenosLJ1"
      | ime    | priimek | davcna   | emso | ENaslov                 | gesloZaPreklic |
      | Nataša | Fesel   | 66655511 |      | natasa.fesel@sodisce.si | gelo542        |
    * Potrdi vlogo "vlogaPrenosLJ1"
    * Natisni vse imetnike na zahtevku "vlogaPrenosLJ1"
    * Natisni zahtevek "vlogaPrenosLJ1"
    * Posreduj na CIF zahtevek "vlogaPrenosLJ1"

  Scenario: 16.02 Dodajanje novega zahtevka in imetnika OJ Maribor
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    * Vnesi novo vlogo "vlogaPrenosMB1"
      | imeOrganizacije            | naselje | ulica  | hisnaStevilka | postnaStevilka | nazivPoste | kontaktnaOseba.imeK | kontaktnaOseba.priimekK | kontaktnaOseba.funkcijaK | kontaktnaOseba.telefonK | kontaktnaOseba.eNaslovK | predstojnik.imeP | predstojnik.priimekP | predstojnik.funkcijaP | predstojnik.eNaslovP |
      | Okrajno sodišče v Mariboru | Maribor | Cafova | 1             | 2508           | Maribor    | ime                 | priimek                 | funkcija                 | 123234234               | naslov@naslov.si        | ime              | priimek              | funkcija              | naslov@naslov.si     |
    * Dodaj novega imetnika "imetnikPrenosMB1" na zahtevek "vlogaPrenosMB1"
      | ime    | priimek | davcna   | emso | ENaslov                  | gesloZaPreklic |
      | Simona | Brumec  | 54390321 |      | simona.brumec@sodisce.si | gelo000        |
    * Potrdi vlogo "vlogaPrenosMB1"
    * Natisni vse imetnike na zahtevku "vlogaPrenosMB1"
    * Natisni zahtevek "vlogaPrenosMB1"
    * Posreduj na CIF zahtevek "vlogaPrenosMB1"

  Scenario: 16.03 Admin potrdi in prevzemi certifikate za oddana zahtevka
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Potrdi vse imetnike na zahtevku "vlogaPrenosLJ1"
    * Potrdi zahtevek "vlogaPrenosLJ1"
    * Izvozi zahtevek "vlogaPrenosLJ1"
    * Potrdi vse imetnike na zahtevku "vlogaPrenosMB1"
    * Potrdi zahtevek "vlogaPrenosMB1"
    * Izvozi zahtevek "vlogaPrenosMB1"
    * Posreduj na SIGOV-CA vse zahtevke in preveri "success" sporočilo "Število zahtevkov, ki so bili posredovani na SIGOV-CA:2"

  Scenario: 16.04 Prevzemi, odpremi in potrdi prevzem certifikatov
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Naloži datoteko s podatki o certifikatih za prevzem "src/main/resources/Podatki za uvoz/Od_SIGOV-CA_Uvoz_podatkov_za_prevzem_test-16.csv"
    * Naloži datoteko s podatki o karticah "src/main/resources/Podatki za uvoz/Uvoz_podatkov_iz_kartice_default_test-16.db"
    * Poišči kartico po črtni kodi "278323" in jo odpremi
    * Poišči kartico po črtni kodi "278324" in jo odpremi
    * Poišči kartico po črtni kodi "278338" in jo odpremi
    * Poišči imetnika "imetnikPrenosLJ1" po črtni kodi in potrdi potrdilo o prejeti kartici dne "19.11.2014"
    * Poišči imetnika "imetnikPrenosLJ2" po črtni kodi in potrdi potrdilo o prejeti kartici dne "19.11.2014"
    * Poišči imetnika "imetnikPrenosMB1" po črtni kodi in potrdi potrdilo o prejeti kartici dne "19.11.2014"

  Scenario: 16.05 Dodajanje zahtevka za prenos OJ Maribor
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    * Na strani za dodajanje zahtevka za prenos certifikata išči "278323" in potrdi dodajanje zahtevka "zahtevekPrenosMB1", ter preveri, da je odprta stran za podroben pregled zahtevka
    * Na strani za dodajanje zahtevka za prenos certifikata išči "278323" in potrdi dodajanje zahtevka, ter preveri sporočilo "Zahtevek za to serijsko številko certifikata je že v pripravi. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov."
    * Brisi zahtevek za prenos certifikata "zahtevekPrenosMB1"
    * Na strani za dodajanje zahtevka za prenos certifikata išči "278323" in potrdi dodajanje zahtevka "zahtevekPrenosMB2", ter preveri, da je odprta stran za podroben pregled zahtevka
    * Preveri, da je na strani za podroben pregled zahtevka za prenos certifikata "zahtevekPrenosMB2" gumb za pregled imetnika neaktiven
    * Potrdi zahtevek za prenos certifikata "zahtevekPrenosMB2"
    * Preveri, da je zahtevek za prenos certifikata "zahtevekPrenosMB2" v statusu "Zaključen"
    * Preveri, da je na strani za podroben pregled zahtevka za prenos certifikata "zahtevekPrenosMB2" gumb za pregled imetnika neaktiven
    * Posreduj na SIGOV-CA zahtevek za prenos certifikata "zahtevekPrenosMB2" in preveri sporočilo "Prosim, da natisnite vlogo preden jo posredujete na SIGOV-CA."
    * Natisni zahtevek za prenos certifikata "zahtevekPrenosMB2"
    * Odkleni zahtevek za prenos certifikata "zahtevekPrenosMB2"
    * Preveri, da je zahtevek za prenos certifikata "zahtevekPrenosMB2" v statusu "V pripravi"
    * Preveri, da je na strani za podroben pregled zahtevka za prenos certifikata "zahtevekPrenosMB2" gumb za pregled imetnika neaktiven
    * Potrdi zahtevek za prenos certifikata "zahtevekPrenosMB2"
    * Natisni zahtevek za prenos certifikata "zahtevekPrenosMB2"
    * Posreduj na SIGOV-CA zahtevek za prenos certifikata "zahtevekPrenosMB2" in si zapomni novega imetnika kot "imetnikPrenosNovMB1"
    * Preveri, da je zahtevek za prenos certifikata "zahtevekPrenosMB2" v statusu "Posredovano na SIGOV-CA"
    * Preveri, da je na strani za podroben pregled zahtevka za prenos certifikata "zahtevekPrenosMB2" gumb odkleni neaktiven
    * Preveri, da je na strani za podroben pregled zahtevka za prenos certifikata "zahtevekPrenosMB2" gumb za pregled imetnika aktiven
    * Uspešno poišči imetnika "imetnikPrenosNovMB1"
    * Uspešno poišči imetnika "imetnikPrenosMB1"
    * Neuspešno poišči imetnika "imetnikPrenosLJ1"
    * Neuspešno poišči imetnika "imetnikPrenosLJ2"

  Scenario: 16.06 Pregled prenesenega zahtevka OJ Ljubljana
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Neuspešno poišči imetnika "imetnikPrenosLJ1"
    * Uspešno poišči imetnika "imetnikPrenosLJ2"
    * Neuspešno poišči imetnika "imetnikPrenosNovMB1"
    * Neuspešno poišči imetnika "imetnikPrenosMB1"

  Scenario: 16.07 Pregled prenesenega zahtevka ADMIN
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Uspešno poišči imetnika "imetnikPrenosLJ1"
    * Uspešno poišči imetnika "imetnikPrenosLJ2"
    * Uspešno poišči imetnika "imetnikPrenosNovMB1"
    * Uspešno poišči imetnika "imetnikPrenosMB1"
    * Preveri status imetnika "imetnikPrenosLJ1" - "Imetnik prenešen na drugo sodišče"
    * Preveri status imetnika "imetnikPrenosLJ2" - "Pametna kartica prevzeta"
    * Preveri status imetnika "imetnikPrenosNovMB1" - "Pametna kartica prevzeta"
    * Preveri status imetnika "imetnikPrenosMB1" - "Pametna kartica prevzeta"

  Scenario: 16.07 Dodaj zahtevek za pridobitev kode za odklepanje kartice za prenešenega imetnika
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    * Na strani za dodajanje zahtevka za pridobitev kode za odklepanje kartice išči "natalija.mimic@sodisce.si" in potrdi dodajanje zahtevka "zahtevekKodaLJ_P_1", ter preveri, da je odprta stran za podroben pregled zahtevka

  Scenario: 16.08 Dodaj zahtevek za preklic digitalnega potrdila za prenešenega imetnika
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    * Na strani za dodajanje zahtevka za preklic certifikata išči "natalija.mimic@sodisce.si" in potrdi dodajanje zahtevka "zahtevekPreklic_P_1", ter preveri, da je odprta stran za podroben pregled zahtevka
