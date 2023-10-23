@12_pregled_in_vracanje_kartic_VSRS @ALL @Test @12
Feature: 12_pregled_in_vracanje_kartic_VSRS

  # Scenario: 12.00 Pripravi tabele
  # * Pripravi bazo "src/main/resources/derby/pocisti_tabele.sql"
  # * Pripravi bazo "src/main/resources/derby/insert_za_12.sql"
  Scenario: 12.01 Pregled in iskanje kartic
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
   # * Poišči kartico VSRS po "" in preveri število rezultatov "2"
    * Poišči kartico VSRS po "Marko Oman" in preveri število rezultatov "1"
    * Poišči kartico VSRS po "Tomaž Tomšič" in preveri število rezultatov "0"
    * Poišči kartico VSRS po "570114512088E30H3B17EEEE" in preveri število rezultatov "1"
    * Poišči kartico VSRS po "078338" in preveri število rezultatov "1"
    * Poišči kartico VSRS po "MARKO oMaN" in preveri število rezultatov "1"
    * Poišči kartico VSRS po "nic" in preveri število rezultatov "0"
    * Poišči kartico VSRS po "" in datumOd "25.07.2014" in datumDo "28.07.2014" ter preveri število rezultatov "1"
    * Poišči kartico VSRS po "andrej" in datumOd "25.07.2014" in datumDo "28.07.2014" ter preveri število rezultatov "0"
    * Poišči kartico VSRS po "" in datumOd "" in datumDo "28.07.2014" ter preveri število rezultatov "1"
    * Poišči kartico VSRS po "" in datumOd "25.07.2014" in datumDo "" ter preveri število rezultatov "1"
    * Poišči kartico VSRS po "" in datumOd "" in datumDo "" ter preveri število rezultatov "2"

  Scenario: 12.02 Vračanje kartic
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Potrdi, da je bila dne "27.01.2014" kartica VSRS z iskalnim nizom "078338" vrnjena
    * Poišči kartico VSRS po "078338" in preveri datum vrnitve "27.01.2014"
