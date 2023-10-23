@14_zahtevek_za_preklic_certifikata @ALL @Test @14
Feature: 14_zahtevek_za_preklic_certifikata

  #Predpogoj => @02, @03, @04, @09
  Scenario: 14.01 Dodajanje zahtevkov za preklic certifikata LJ
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Na strani za dodajanje zahtevka za preklic certifikata išči "" in preveri število rezultatov "5"
    * Na strani za dodajanje zahtevka za preklic certifikata išči "Marko Oman" in potrdi dodajanje zahtevka "zahtevekPreklicLJ1", ter preveri, da je odprta stran za podroben pregled zahtevka
    * Na strani za dodajanje zahtevka za preklic certifikata išči "Marko Oman" in potrdi dodajanje zahtevka, ter preveri sporočilo "Zahtevek za to serijsko številko certifikata je že v pripravi. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov."
    * Brisi zahtevek za preklic certifikata "zahtevekPreklicLJ1"
    * Na strani za dodajanje zahtevka za preklic certifikata išči "Marko Oman" in potrdi dodajanje zahtevka "zahtevekPreklicLJ2", ter preveri, da je odprta stran za podroben pregled zahtevka
    * Potrdi zahtevek za preklic certifikata "zahtevekPreklicLJ2"
    * Preveri, da je zahtevek za preklic certifikata "zahtevekPreklicLJ2" v statusu "Zaključen"
    * Posreduj na SIGOV-CA zahtevek za preklic certifikata "zahtevekPreklicLJ2" in preveri sporočilo "Prosim, da natisnite vlogo preden jo posredujete na SIGOV-CA."
    * Natisni zahtevek za preklic certifikata "zahtevekPreklicLJ2"
    * Posreduj na SIGOV-CA zahtevek za preklic certifikata "zahtevekPreklicLJ2"
    * Preveri, da je zahtevek za preklic certifikata "zahtevekPreklicLJ2" v statusu "Posredovano na SIGOV-CA"
    * Odkleni zahtevek za preklic certifikata "zahtevekPreklicLJ2"
    * Preveri, da je zahtevek za preklic certifikata "zahtevekPreklicLJ2" v statusu "V pripravi"
    * Potrdi zahtevek za preklic certifikata "zahtevekPreklicLJ2"
    * Natisni zahtevek za preklic certifikata "zahtevekPreklicLJ2"
    * Posreduj na SIGOV-CA zahtevek za preklic certifikata "zahtevekPreklicLJ2"
    * Preveri, da je zahtevek za preklic certifikata "zahtevekPreklicLJ2" v statusu "Posredovano na SIGOV-CA"

  Scenario: 14.02 Dodajanje zahtevkov za preklic certifikata MB
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    * Na strani za dodajanje zahtevka za preklic certifikata išči "" in preveri število rezultatov "2"
    * Na strani za dodajanje zahtevka za preklic certifikata išči "Jože Rihtaršič" in potrdi dodajanje zahtevka "zahtevekPreklicMB1", ter preveri, da je odprta stran za podroben pregled zahtevka
    * Na strani za dodajanje zahtevka za preklic certifikata išči "Jože Rihtaršič" in potrdi dodajanje zahtevka, ter preveri sporočilo "Zahtevek za to serijsko številko certifikata je že v pripravi. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov."
    * Brisi zahtevek za preklic certifikata "zahtevekPreklicMB1"
    * Na strani za dodajanje zahtevka za preklic certifikata išči "Jože Rihtaršič" in potrdi dodajanje zahtevka "zahtevekPreklicMB2", ter preveri, da je odprta stran za podroben pregled zahtevka
    * Potrdi zahtevek za preklic certifikata "zahtevekPreklicMB2"
    * Preveri, da je zahtevek za preklic certifikata "zahtevekPreklicMB2" v statusu "Zaključen"
    * Posreduj na SIGOV-CA zahtevek za preklic certifikata "zahtevekPreklicMB2" in preveri sporočilo "Prosim, da natisnite vlogo preden jo posredujete na SIGOV-CA."
    * Natisni zahtevek za preklic certifikata "zahtevekPreklicMB2"
    * Posreduj na SIGOV-CA zahtevek za preklic certifikata "zahtevekPreklicMB2"
    * Preveri, da je zahtevek za preklic certifikata "zahtevekPreklicMB2" v statusu "Posredovano na SIGOV-CA"
    * Odkleni zahtevek za preklic certifikata "zahtevekPreklicMB2"
    * Preveri, da je zahtevek za preklic certifikata "zahtevekPreklicMB2" v statusu "V pripravi"
    * Potrdi zahtevek za preklic certifikata "zahtevekPreklicMB2"
    * Natisni zahtevek za preklic certifikata "zahtevekPreklicMB2"
    * Posreduj na SIGOV-CA zahtevek za preklic certifikata "zahtevekPreklicMB2"
    * Preveri, da je zahtevek za preklic certifikata "zahtevekPreklicMB2" v statusu "Posredovano na SIGOV-CA"


  Scenario: 14.03 Dodajanje zahtevkov za preklic certifikata ADMIN
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Na strani za dodajanje zahtevka za preklic certifikata išči "" in preveri število rezultatov "7"
    * Na strani za dodajanje zahtevka za preklic certifikata išči "Boštjan Vidmar" in potrdi dodajanje zahtevka "zahtevekPreklicAdmnMB1", ter preveri, da je odprta stran za podroben pregled zahtevka
    * Na strani za dodajanje zahtevka za preklic certifikata išči "Boštjan Vidmar" in potrdi dodajanje zahtevka, ter preveri sporočilo "Zahtevek za to serijsko številko certifikata je že v pripravi. Zahtevek lahko pregledate in urejate na strani za pregled zahtevkov."
    * Brisi zahtevek za preklic certifikata "zahtevekPreklicAdmnMB1"
    * Na strani za dodajanje zahtevka za preklic certifikata išči "Boštjan Vidmar" in potrdi dodajanje zahtevka "zahtevekPreklicAdmnMB2", ter preveri, da je odprta stran za podroben pregled zahtevka
    * Potrdi zahtevek za preklic certifikata "zahtevekPreklicAdmnMB2"
    * Natisni zahtevek za preklic certifikata "zahtevekPreklicAdmnMB2"
    * Posreduj na SIGOV-CA zahtevek za preklic certifikata "zahtevekPreklicAdmnMB2"
    * Preveri, da je zahtevek za preklic certifikata "zahtevekPreklicAdmnMB2" v statusu "Posredovano na SIGOV-CA"
    * Na strani za dodajanje zahtevka za preklic certifikata išči "tomaz.tomsic@sodisce.si" in potrdi dodajanje zahtevka "zahtevekPreklicAdmnLJ1", ter preveri, da je odprta stran za podroben pregled zahtevka
    * Potrdi zahtevek za preklic certifikata "zahtevekPreklicAdmnLJ1"
    * Natisni zahtevek za preklic certifikata "zahtevekPreklicAdmnLJ1"
    * Posreduj na SIGOV-CA zahtevek za preklic certifikata "zahtevekPreklicAdmnLJ1"
    * Preveri, da je zahtevek za preklic certifikata "zahtevekPreklicAdmnLJ1" v statusu "Posredovano na SIGOV-CA"
