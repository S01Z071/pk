@13_zahtevek_za_pridobitev_kode_za_odklepanje_kartice @ALL @Test @13
Feature: 13_zahtevek_za_pridobitev_kode_za_odklepanje_kartice

 #Predpogoj => @02, @03, @04, @09
  Scenario: 13.01 Dodajanje zahtevka za pridobitev kode za odklepanje kartice LJ
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Na strani za dodajanje zahtevka za pridobitev kode za odklepanje kartice išči "" in preveri število rezultatov "5"
    * Na strani za dodajanje zahtevka za pridobitev kode za odklepanje kartice išči "0783" in preveri število rezultatov "4"
    * Na strani za dodajanje zahtevka za pridobitev kode za odklepanje kartice išči "0783" in izberi kartico s črtno kodo "078338", ter potrdi dodajanje zahtevka "zahtevekKodaLJ1"
    #* Na strani za dodajanje zahtevka za pridobitev kode za odklepanje kartice išči "marko oman" in potrdi dodajanje zahtevka "zahtevekKodaLJ2", ter preveri sporočilo "Zahtevek shranjen. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov."
    * Na strani za dodajanje zahtevka za pridobitev kode za odklepanje kartice išči "marko oman" in potrdi dodajanje zahtevka "zahtevekKodaLJ2", ter preveri, da je odprta stran za podroben pregled zahtevka
    * Na strani za dodajanje zahtevka za pridobitev kode za odklepanje kartice išči "marko oman" in potrdi dodajanje zahtevka "zahtevekKodaLJ2", ter preveri sporočilo "Zahtevek za to oznako kartice je že v pripravi. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov."

  Scenario: 13.02 Dodajanje zahtevka za pridobitev kode za odklepanje kartice MB
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    # * Na strani za dodajanje zahtevka za pridobitev kode za odklepanje kartice išči "jože rihtaršič" in potrdi dodajanje zahtevka "zahtevekKodaMB1", ter preveri sporočilo "Zahtevek shranjen. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov."
    * Na strani za dodajanje zahtevka za pridobitev kode za odklepanje kartice išči "jože rihtaršič" in potrdi dodajanje zahtevka "zahtevekKodaMB1", ter preveri, da je odprta stran za podroben pregled zahtevka
    * Na strani za dodajanje zahtevka za pridobitev kode za odklepanje kartice išči "jože rihtaršič" in potrdi dodajanje zahtevka "zahtevekKodaMB1", ter preveri sporočilo "Zahtevek za to oznako kartice je že v pripravi. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov."
    # * Na strani za dodajanje zahtevka za pridobitev kode za odklepanje kartice išči "074340" in potrdi dodajanje zahtevka "zahtevekKodaMB2", ter preveri sporočilo "Zahtevek shranjen. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov."
    * Na strani za dodajanje zahtevka za pridobitev kode za odklepanje kartice išči "074340" in potrdi dodajanje zahtevka "zahtevekKodaMB2", ter preveri, da je odprta stran za podroben pregled zahtevka

  Scenario: 13.03 Pregled zahtevkov uporabnik LJ
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Uspešno poišči zahtevek za kodo "zahtevekKodaLJ1"
    * Uspešno poišči zahtevek za kodo "zahtevekKodaLJ2"
    * Neuspešno poišči zahtevek za kodo "zahtevekKodaMB1"
    * Neuspešno poišči zahtevek za kodo "zahtevekKodaMB2"
    * Izbriši zahtevek za kodo "zahtevekKodaLJ2"
    * Neuspešno poišči zahtevek za kodo "zahtevekKodaLJ2"

  Scenario: 13.04 Pregled zahtevkov uporabnik MB
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    * Uspešno poišči zahtevek za kodo "zahtevekKodaMB1"
    * Uspešno poišči zahtevek za kodo "zahtevekKodaMB2"
    * Neuspešno poišči zahtevek za kodo "zahtevekKodaLJ1"
    * Neuspešno poišči zahtevek za kodo "zahtevekKodaLJ1"
    * Izbriši zahtevek za kodo "zahtevekKodaMB2"
    * Neuspešno poišči zahtevek za kodo "zahtevekKodaMB2"

  Scenario: 13.05 Pregled zahtevkov admin
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Uspešno poišči zahtevek za kodo "zahtevekKodaLJ1"
    * Uspešno poišči zahtevek za kodo "zahtevekKodaLJ2"
    * Uspešno poišči zahtevek za kodo "zahtevekKodaMB1"
    * Uspešno poišči zahtevek za kodo "zahtevekKodaMB2"

  Scenario: 13.06 Posredovanje zahtevka na SIGOV-CA uporabnik LJ
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Posreduj na SIGOV-CA zahtevek za kodo "zahtevekKodaLJ1" in preveri sporočilo "Prosim, da natisnite vlogo preden jo posredujete na SIGOV-CA."
    * Natisni zahtevek za kodo "zahtevekKodaLJ1"
    * Posreduj na SIGOV-CA zahtevek za kodo "zahtevekKodaLJ1"

  Scenario: 13.07 Posredovanje zahtevka na SIGOV-CA uporabnik MB
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    * Posreduj na SIGOV-CA zahtevek za kodo "zahtevekKodaMB1" in preveri sporočilo "Prosim, da natisnite vlogo preden jo posredujete na SIGOV-CA."
    * Natisni zahtevek za kodo "zahtevekKodaMB1"
    * Posreduj na SIGOV-CA zahtevek za kodo "zahtevekKodaMB1"

