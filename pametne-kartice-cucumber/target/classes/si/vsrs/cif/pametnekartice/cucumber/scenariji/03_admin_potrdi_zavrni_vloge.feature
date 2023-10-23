@03_admin_potrdi_in_zavrni_vloge @ALL @Test @03
Feature: 03_admin_potrdi_in_zavrni_vloge


  Scenario: 03.01 Potrdi in zavrni zahtevek
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Potrdi enega imetnika na zahtevku "vloga1"
    * Potrdi zahtevek "vloga1" in preveri sporočilo "Vsi imetniki morajo biti potrjeni."
    * Potrdi vse imetnike na zahtevku "vloga1"
    * Potrdi zahtevek "vloga1"
    * Zavrni zahtevek "vloga4" z razlogom zavrnitve "Razlog za zavrnitev zahtevka." in preveri sporočilo "Noben imetnik ni zavrnjen."
    * Zavrni vse imetnike na zahtevku "vloga4" z razlogom "Razlog za zavrnitev imetnika."
    * Zavrni zahtevek "vloga4" z razlogom zavrnitve "Razlog za zavrnitev zahtevka."

  Scenario: 03.02 Uporabnik iz OJ Maribor dopolne zahtevek in vnese novega
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    * Dopolni zahtevek "vloga4"
    * Potrdi vlogo "vloga4"
    * Natisni vse imetnike na zahtevku "vloga4"
    * Natisni zahtevek "vloga4"
    * Posreduj na CIF zahtevek "vloga4"
    * Vnesi novo vlogo "vloga5"
      |  |
      |  |
    * Dodaj novega imetnika "imetnik9" na zahtevek "vloga5"
      | ime    | priimek | davcna   | emso | ENaslov                 | gesloZaPreklic |
      | Branko | Crček   | 99999999 |      | branko.crcek@sodisce.si | geslo981       |
    * Potrdi vlogo "vloga5"
    * Natisni vse imetnike na zahtevku "vloga5"
    * Natisni zahtevek "vloga5"
    * Posreduj na CIF zahtevek "vloga5"



  Scenario: 03.03 Zahtevke potrdi, zavrni in posreduj na SIGOV-CA
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Potrdi vse imetnike na zahtevku "vloga4"
    * Potrdi zahtevek "vloga4"
    * Zavrni vse imetnike na zahtevku "vloga5" z razlogom "Razlog za zavrnitev imetnika."
    * Zavrni zahtevek "vloga5" z razlogom zavrnitve "Razlog za zavrnitev zahtevka."
    * Posreduj na SIGOV-CA zahtevek "vloga1" in preveri sporočilo "Pred posredovanjem na SIGOV-CA je potrebno vse imetnike izvoziti v .csv datoteko."
    * Izvozi zahtevek "vloga1"
    * Posreduj na SIGOV-CA zahtevek "vloga1"
    #iz strani Posredovanje na SIGOV-CA
    * Posreduj na SIGOV-CA vse zahtevke in preveri "error" sporočilo "Število zahtevkov, ki še niso bili izvoženi:1"
    #iz strani Posredovanje na SIGOV-CA
    * Izvozi vse potrjene zahtevke
    #iz strani Posredovanje na SIGOV-CA
    * Posreduj na SIGOV-CA vse zahtevke in preveri "success" sporočilo "Število zahtevkov, ki so bili posredovani na SIGOV-CA:1"
