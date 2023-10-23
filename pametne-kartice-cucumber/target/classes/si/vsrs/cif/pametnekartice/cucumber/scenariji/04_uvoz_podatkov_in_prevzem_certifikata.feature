@04_uvoz_podatkov_in_prevzem_certifikata @ALL @Test @04
Feature: 04_uvoz_podatkov_in_prevzem_certifikata

  Scenario: 04.01 Uvoz podatkov za prevzem
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Naloži datoteko s podatki o certifikatih za prevzem "src/main/resources/Podatki za uvoz/Od_SIGOV-CA_Uvoz_podatkov_za_prevzem.csv"
    * Naloži datoteko s podatki o karticah "src/main/resources/Podatki za uvoz/Uvoz_podatkov_iz_kartice_default.db"

  Scenario: 04.02 Odpremljanje kartic
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Poišči kartico po črtni kodi "078323" in jo odpremi
    * Poišči kartico po črtni kodi "078324" in jo odpremi
    * Iz strani za pregled pripravljenih kartic odpremi kartico s črtno kodo "078338" in preveri obvestilo "Pred zaključkom je potrebno natisniti podatke o imetniku."
    * Natisni obvestilo o prejemu za kartico s črtno kodo "078338"
    * Iz strani za pregled pripravljenih kartic odpremi kartico s črtno kodo "078338"
    * Natisni obvestilo o prejemu za kartico s črtno kodo "078340"
    * Iz strani za pregled pripravljenih kartic odpremi kartico s črtno kodo "078340"
    * Poišči kartico po črtni kodi "078341" in jo odpremi
    * Poišči kartico po črtni kodi "074340" in jo odpremi

  Scenario: 04.03 Potrdila o prejetju kartice
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Poišči imetnika "imetnik2" po črtni kodi in potrdi potrdilo o prejeti kartici dne "34.1233.34" in preveri sporočilo "Datum je napačne oblike."
    * Poišči imetnika "imetnik2" po črtni kodi in potrdi potrdilo o prejeti kartici dne "19.11.2013"
    * Preveri status zahtevka "vloga1" - "Posredovano na SIGOV-CA"
    * Preveri status imetnika "imetnik2" - "Pametna kartica prevzeta"
    * Potrdi potrdilo o prejeti kartici za imetnika "imetnik6" dne "34.13123.34" in preveri sporočilo "Datum je napačne oblike."
    * Potrdi potrdilo o prejeti kartici za imetnika "imetnik6" dne "19.11.2013"
    * Preveri status zahtevka "vloga1" - "Posredovano na SIGOV-CA"
    * Preveri status imetnika "imetnik6" - "Pametna kartica prevzeta"
    * Poišči imetnika "imetnik3" po črtni kodi in potrdi potrdilo o prejeti kartici dne "19.11.2013"
    * Preveri status zahtevka "vloga1" - "Posredovano na SIGOV-CA"
    * Preveri status imetnika "imetnik3" - "Pametna kartica prevzeta"
    * Poišči imetnika "imetnik7" po črtni kodi in potrdi potrdilo o prejeti kartici dne "19.11.2013"
    * Preveri status zahtevka "vloga1" - "Posredovano na SIGOV-CA"
    * Preveri status imetnika "imetnik7" - "Pametna kartica prevzeta"
    * Potrdi potrdilo o prejeti kartici za imetnika "imetnik8" dne "19.11.2013"
    * Preveri status zahtevka "vloga1" - "Posredovano na SIGOV-CA"
    * Preveri status imetnika "imetnik8" - "Pametna kartica prevzeta"
    * Potrdi potrdilo o prejeti kartici za imetnika "imetnik4" dne "19.11.2013"
    * Preveri status zahtevka "vloga1" - "Dokončan"
    * Preveri status imetnika "imetnik4" - "Pametna kartica prevzeta"

  Scenario: 04.04 Izvoz podatkov o karticah
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Izberi podatke o karticah za izvoz od "10123.234" do "123123" in preveri število rezultatov "0" ter sporočilo "Datum je napačne oblike."
    * Izberi podatke o karticah za izvoz od "16.12.2013" do "16.12.2013" in preveri število rezultatov "0" ter sporočilo "Ni najdenih rezultatov."
    * Izberi podatke o karticah za izvoz od "1" do "10.12.2013" in preveri število rezultatov "6"
  0.09.2013