@06_kontrole_pri_dodajanju_imetnikov @ALL @Test @06
Feature: 06_kontrole_pri_dodajanju_imetnikov

  Scenario: 06.01 Dodajanje imetnika z obstoječim certifikatom
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S25-OJ v Murski Soboti"
    * Vnesi novo vlogo "vlogaMS"
      | kontaktnaOseba.imeK | kontaktnaOseba.priimekK | kontaktnaOseba.funkcijaK | kontaktnaOseba.telefonK | kontaktnaOseba.eNaslovK  | predstojnik.imeP | predstojnik.priimekP | predstojnik.funkcijaP | predstojnik.eNaslovP     |
      | imeK                | priimekK                | funkcijaK                | 123234234               | imeK.priimekK@sodisce.si | imeP             | priimekP             | funkcija              | imeP.priimekP@sodisce.si |
    * Dodaj novega imetnika na zahtevek "vlogaMS" in preveri "E-naslov" sporočilo "Imetnik s tem elektronskim naslovom že ima veljaven certifikat. Zahtevo za nov certifikat lahko oddate 30 dni pred potekom certifikata."
      | ime     | priimek   | davcna   | emso | ENaslov                      | gesloZaPreklic |
      | Andreja | Bratkovic | 11111111 |      | andreja.bratkovic@sodisce.si | geslo123       |

  Scenario: 06.02 Dodajanje imetnika, ki ima v lasti kartico in čitalec SIGOV-CA
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S25-OJ v Murski Soboti"
    * Dodaj novega imetnika na zahtevek "vlogaMS" in preveri "imaPametnoKartico" sporočilo "Niste označili, da imate v lasti pametno kartico SIGOV-CA, vendar je zabeleženo, da imate v lasti pametno kartico SIGOV-CA. Prosim ustrezno izberite podatke o opremi."
      | ime   | priimek | davcna   | emso | ENaslov                | gesloZaPreklic |
      | Silva | Titan   | 34555555 |      | silva.titan@sodisce.si | geslo123 456   |
    * Dodaj novega imetnika na zahtevek "vlogaMS" in preveri "imaCitalec" sporočilo "Označili ste, da ne potrebujete čitalca SIGOV-CA, vendar je zabeleženo, da imate v lasti čitalec SIGOV-CA. Prosim ustrezno izberite podatke o opremi."
      | ime   | priimek | davcna   | emso | ENaslov                | gesloZaPreklic | imaCitalec |
      | Silva | Titan   | 34555555 |      | silva.titan@sodisce.si | geslo123 456   | KLIKNI     |
    * Dodaj novega imetnika "imetnik1MS" na zahtevek "vlogaMS"
      | ime   | priimek | davcna   | emso | ENaslov                | gesloZaPreklic | imaPametnoKartico1 |
      | Silva | Titan   | 34555555 |      | silva.titan@sodisce.si | geslo123 456   | KLIKNI             |

  Scenario: 06.03 Pregled imetnika, ki ima v lasti kartico in čitalec SIGOV-CA - admin
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Pojdi na podroben pregled imetnika "imetnik1MS"
    * Preveri prikazano besedilo "Seznam kartic SIGOV-CA, ki jih ima imetnik v lasti"
    * Preveri prikazano besedilo "ActivCard 64k - 409061451D42280F301A"
    * Preveri prikazano besedilo "Seznam čitalcev SIGOV-CA, ki jih ima imetnik v lasti"
    * Preveri prikazano besedilo "ActivCard USB - 21120829206877"

  Scenario: 06.04 Pregled imetnika, ki ima v lasti kartico in čitalec SIGOV-CA - uporabnik
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S25-OJ v Murski Soboti"
    * Pojdi na podroben pregled imetnika "imetnik1MS"
    * Preveri prikazano besedilo "Seznam kartic SIGOV-CA, ki jih ima imetnik v lasti"
    * Preveri prikazano besedilo "ActivCard 64k - 409061451D42280F301A"
    * Preveri prikazano besedilo "Seznam čitalcev SIGOV-CA, ki jih ima imetnik v lasti"
    * Preveri prikazano besedilo "ActivCard USB - 21120829206877"

  Scenario: 06.05 Dodajanje imetnika, ki ne obstaja v LDAP-u
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Vnesi novo vlogo "vlogaLJ0605"
      | kontaktnaOseba.imeK | kontaktnaOseba.priimekK | kontaktnaOseba.funkcijaK | kontaktnaOseba.telefonK | kontaktnaOseba.eNaslovK  | predstojnik.imeP | predstojnik.priimekP | predstojnik.funkcijaP | predstojnik.eNaslovP     |
      | imeK                | priimekK                | funkcijaK                | 123234234               | imeK.priimekK@sodisce.si | imeP             | priimekP             | funkcija              | imeP.priimekP@sodisce.si |
    * Dodaj novega imetnika na zahtevek "vlogaLJ0605" in preveri "E-naslov" sporočilo "Elektronski naslov ne obstaja v imeniku osebja (LDAP)."
      | ime      | priimek | davcna   | emso | ENaslov                     | gesloZaPreklic |
      | Marko123 | Oman123 | 11111111 |      | marko123.oman123@sodisce.si | geslo123       |

  @06.06
  Scenario: 06.06 Urejanje imetnika
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    * Vnesi novo vlogo "vlogaMB0606"
      | imeOrganizacije            | naselje | ulica  | hisnaStevilka | postnaStevilka | nazivPoste | kontaktnaOseba.imeK | kontaktnaOseba.priimekK | kontaktnaOseba.funkcijaK | kontaktnaOseba.telefonK | kontaktnaOseba.eNaslovK | predstojnik.imeP | predstojnik.priimekP | predstojnik.funkcijaP | predstojnik.eNaslovP |
      | Okrajno sodišče v Mariboru | Maribor | Cafova | 1             | 2508           | Maribor    | ime                 | priimek                 | funkcija                 | 123234234               | naslov@naslov.si        | ime              | priimek              | funkcija              | naslov@naslov.si     |
    * Dodaj novega imetnika "imetnik1MB0606" na zahtevek "vlogaMB0606"
      | ime | priimek | davcna   | emso | ENaslov                | gesloZaPreklic |
      | Rok | Klement | 12345432 |      | rok.klement@sodisce.si | geslo123       |
    * Poišči imetnika "Rok Klement" po besedi "12345432"
    * Dodaj novega imetnika na zahtevek "vlogaMB0606" in preveri "E-naslov" sporočilo "Imetnik s tem elektronskim naslovom ima že oddano aktivno vlogo in ni končana."
      | ime | priimek | davcna   | emso | ENaslov                | gesloZaPreklic |
      | Rok | Klement | 12345432 |      | rok.klement@sodisce.si | geslo123       |
    * Dodaj novega imetnika "imetnik2MB0606" na zahtevek "vlogaMB0606"
      | ime    | priimek | davcna   | emso | ENaslov                | gesloZaPreklic |
      | Andrej | Luci    | 12345111 |      | andrej.luci@sodisce.si | geslo123       |
    * Poišči imetnika "Andrej Luci" po besedi "12345111"
    * Uredi imetnika "imetnik1MB0606"
      | ime | priimek | davcna   | emso | ENaslov                | gesloZaPreklic |
      | Rok | Klement | 87656785 |      | rok.klement@sodisce.si | geslo123       |
    * Poišči imetnika "Rok Klement" po besedi "87656785"
    * Uredi imetnika "imetnik2MB0606" in preveri "E-naslov" sporočilo "Imetnik s tem elektronskim naslovom ima že oddano aktivno vlogo in ni končana."
      | ime | priimek | davcna   | emso | ENaslov                | gesloZaPreklic |
      | Rok | Klement | 12345432 |      | rok.klement@sodisce.si | geslo123       |
    * Uredi imetnika "imetnik1MB0606"
      | ime       | priimek | davcna   | emso | ENaslov                     | gesloZaPreklic |
      | Gabrijela | Rupnik  | 87656111 |      | gabrijela.rupnik@sodisce.si | geslo123       |
    * Poišči imetnika "Gabrijela Rupnik" po besedi "87656111"
