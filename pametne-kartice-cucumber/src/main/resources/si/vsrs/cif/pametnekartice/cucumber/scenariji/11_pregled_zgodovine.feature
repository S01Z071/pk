@11_pregled_zgodovine @ALL @Test @11
Feature: 11_pregled_zgodovine

  Scenario: 11.01 Preverjanje zgodovine zahtevkov - admin
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Preveri zgodovino za zahtevek "vloga1"
      | Datum | Uporabnik | Opis                            | Prejšnji status | Nov status |
      | TODAY | T200003   | Zahtevek kreiran                | 00              | 01         |
      | TODAY | T200003   | Zahtevek zaključen              | 01              | 02         |
      | TODAY | T200003   | Zahtevek odklenjen              | 02              | 01         |
      | TODAY | T200003   | Zahtevek zaključen              | 01              | 02         |
      | TODAY | T200003   | Zahtevek posredovan na CIF      | 02              | 03         |
      | TODAY | T200003   | Zahtevek potrjen                | 03              | 05         |
      | TODAY | T200003   | Zahtevek posredovan na SIGOV-CA | 05              | 07         |
      | TODAY | T200003   | Vse pametne kartice prevzete.   | 07              | 08         |
    * Preveri zgodovino za zahtevek "vloga2"
      | Datum | Uporabnik | Opis              | Prejšnji status | Nov status |
      | TODAY | T200003   | Zahtevek kreiran  | 00              | 01         |
      | TODAY | T200003   | Zahtevek izbrisan | 01              | 04         |
    * Preveri zgodovino za zahtevek "vloga3"
      | Datum | Uporabnik | Opis              | Prejšnji status | Nov status |
      | TODAY | T200004   | Zahtevek kreiran  | 00              | 01         |
      | TODAY | T200004   | Zahtevek izbrisan | 01              | 04         |
    * Preveri zgodovino za zahtevek "vloga4"
      | Datum | Uporabnik | Opis                                | Prejšnji status | Nov status |
      | TODAY | T200004   | Zahtevek kreiran                    | 00              | 01         |
      | TODAY | T200004   | Zahtevek zaključen                  | 01              | 02         |
      | TODAY | T200004   | Zahtevek odklenjen                  | 02              | 01         |
      | TODAY | T200004   | Zahtevek zaključen                  | 01              | 02         |
      | TODAY | T200004   | Zahtevek posredovan na CIF          | 02              | 03         |
      | TODAY | T200003   | Zahtevek zavrnjen                   | 03              | 06         |
      | TODAY | T200004   | Zahtevek odklenjen za dopolnjevanje | 06              | 01         |
      | TODAY | T200004   | Zahtevek zaključen                  | 01              | 02         |
      | TODAY | T200004   | Zahtevek posredovan na CIF          | 02              | 03         |
      | TODAY | T200003   | Zahtevek potrjen                    | 03              | 05         |
      | TODAY | T200003   | Zahtevek posredovan na SIGOV-CA     | 05              | 07         |
      | TODAY | T200003   | Vse pametne kartice prevzete.       | 07              | 08         |
    * Preveri zgodovino za zahtevek "vloga5"
      | Datum | Uporabnik | Opis                       | Prejšnji status | Nov status |
      | TODAY | T200004   | Zahtevek kreiran           | 00              | 01         |
      | TODAY | T200004   | Zahtevek zaključen         | 01              | 02         |
      | TODAY | T200004   | Zahtevek posredovan na CIF | 02              | 03         |
      | TODAY | T200003   | Zahtevek zavrnjen          | 03              | 06         |

  Scenario: 11.02 Preverjanje zgodovine za imetnike - admin
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "002:S23-OJ v Ljubljani"
    * Preveri zgodovino za imetnika "imetnik1"
      | Datum | Uporabnik | Opis             | Prejšnji status | Nov status |
      | TODAY | T200003   | Imetnik kreiran  | 00              | 01         |
      | TODAY | T200003   | Imetnik izbrisan | 01              | 04         |
    * Preveri zgodovino za imetnika "imetnik2"
      | Datum | Uporabnik | Opis                                | Prejšnji status | Nov status |
      | TODAY | T200003   | Imetnik kreiran                     | 00              | 01         |
      | TODAY | T200003   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200003   | Imetnik odklenjen                   | 02              | 01         |
      | TODAY | T200003   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200003   | Imetnik posredovan na CIF           | 02              | 03         |
      | TODAY | T200003   | Imetnik potrjen                     | 03              | 05         |
      | TODAY | T200003   | Imetnik posredovan na SIGOV-CA      | 05              | 07         |
      | TODAY | T200003   | Kartica odpremljena.                | 07              | 08         |
      | TODAY | T200003   | Prejeto potrdilo o prejemu kartice. | 08              | 09         |
    * Preveri zgodovino za imetnika "imetnik3"
      | Datum | Uporabnik | Opis                                | Prejšnji status | Nov status |
      | TODAY | T200003   | Imetnik kreiran                     | 00              | 01         |
      | TODAY | T200003   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200003   | Imetnik odklenjen                   | 02              | 01         |
      | TODAY | T200003   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200003   | Imetnik posredovan na CIF           | 02              | 03         |
      | TODAY | T200003   | Imetnik potrjen                     | 03              | 05         |
      | TODAY | T200003   | Imetnik posredovan na SIGOV-CA      | 05              | 07         |
      | TODAY | T200003   | Kartica odpremljena.                | 07              | 08         |
      | TODAY | T200003   | Prejeto potrdilo o prejemu kartice. | 08              | 09         |
    * Preveri zgodovino za imetnika "imetnik4"
      | Datum | Uporabnik | Opis                                | Prejšnji status | Nov status |
      | TODAY | T200003   | Imetnik kreiran                     | 00              | 01         |
      | TODAY | T200003   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200003   | Imetnik odklenjen                   | 02              | 01         |
      | TODAY | T200003   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200003   | Imetnik posredovan na CIF           | 02              | 03         |
      | TODAY | T200003   | Imetnik potrjen                     | 03              | 05         |
      | TODAY | T200003   | Imetnik posredovan na SIGOV-CA      | 05              | 07         |
      | TODAY | T200003   | Kartica odpremljena.                | 07              | 08         |
      | TODAY | T200003   | Prejeto potrdilo o prejemu kartice. | 08              | 09         |
    * Preveri zgodovino za imetnika "imetnik5"
      | Datum | Uporabnik | Opis             | Prejšnji status | Nov status |
      | TODAY | T200003   | Imetnik kreiran  | 00              | 01         |
      | TODAY | T200003   | Imetnik izbrisan | 01              | 04         |
    * Preveri zgodovino za imetnika "imetnik6"
      | Datum | Uporabnik | Opis                                | Prejšnji status | Nov status |
      | TODAY | T200004   | Imetnik kreiran                     | 00              | 01         |
      | TODAY | T200004   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200004   | Imetnik odklenjen                   | 02              | 01         |
      | TODAY | T200004   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200004   | Imetnik posredovan na CIF           | 02              | 03         |
      | TODAY | T200003   | Imetnik zavrnjen                    | 03              | 06         |
      | TODAY | T200004   | Imetnik odklenjen za dopolnjevanje  | 06              | 01         |
      | TODAY | T200004   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200004   | Imetnik posredovan na CIF           | 02              | 03         |
      | TODAY | T200003   | Imetnik potrjen                     | 03              | 05         |
      | TODAY | T200003   | Imetnik posredovan na SIGOV-CA      | 05              | 07         |
      | TODAY | T200003   | Kartica odpremljena.                | 07              | 08         |
      | TODAY | T200003   | Prejeto potrdilo o prejemu kartice. | 08              | 09         |

  Scenario: 11.03 Preverjanje zgodovine zahtevkov - uporabnik LJ
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Preveri zgodovino za zahtevek "vloga1"
      | Datum | Uporabnik | Opis                            | Prejšnji status | Nov status |
      | TODAY | T200003   | Zahtevek kreiran                | 00              | 01         |
      | TODAY | T200003   | Zahtevek zaključen              | 01              | 02         |
      | TODAY | T200003   | Zahtevek odklenjen              | 02              | 01         |
      | TODAY | T200003   | Zahtevek zaključen              | 01              | 02         |
      | TODAY | T200003   | Zahtevek posredovan na CIF      | 02              | 03         |
      | TODAY | T200003   | Zahtevek potrjen                | 03              | 05         |
      | TODAY | T200003   | Zahtevek posredovan na SIGOV-CA | 05              | 07         |
      | TODAY | T200003   | Vse pametne kartice prevzete.   | 07              | 08         |

  Scenario: 11.04. Preverjanje zgodovine zahtevkov - uporabnik MB
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    * Preveri zgodovino za zahtevek "vloga4"
      | Datum | Uporabnik | Opis                                | Prejšnji status | Nov status |
      | TODAY | T200004   | Zahtevek kreiran                    | 00              | 01         |
      | TODAY | T200004   | Zahtevek zaključen                  | 01              | 02         |
      | TODAY | T200004   | Zahtevek odklenjen                  | 02              | 01         |
      | TODAY | T200004   | Zahtevek zaključen                  | 01              | 02         |
      | TODAY | T200004   | Zahtevek posredovan na CIF          | 02              | 03         |
      | TODAY | T200003   | Zahtevek zavrnjen                   | 03              | 06         |
      | TODAY | T200004   | Zahtevek odklenjen za dopolnjevanje | 06              | 01         |
      | TODAY | T200004   | Zahtevek zaključen                  | 01              | 02         |
      | TODAY | T200004   | Zahtevek posredovan na CIF          | 02              | 03         |
      | TODAY | T200003   | Zahtevek potrjen                    | 03              | 05         |
      | TODAY | T200003   | Zahtevek posredovan na SIGOV-CA     | 05              | 07         |
      | TODAY | T200003   | Vse pametne kartice prevzete.       | 07              | 08         |
    * Preveri zgodovino za zahtevek "vloga5"
      | Datum | Uporabnik | Opis                       | Prejšnji status | Nov status |
      | TODAY | T200004   | Zahtevek kreiran           | 00              | 01         |
      | TODAY | T200004   | Zahtevek zaključen         | 01              | 02         |
      | TODAY | T200004   | Zahtevek posredovan na CIF | 02              | 03         |
      | TODAY | T200003   | Zahtevek zavrnjen          | 03              | 06         |

  Scenario: 11.05 Preverjanje zgodovine za imetnike - uporabnik LJ
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200003" in geslom "T200003" ter izberi sodišče in vlogo "001:S23-OJ v Ljubljani"
    * Preveri zgodovino za imetnika "imetnik2"
      | Datum | Uporabnik | Opis                                | Prejšnji status | Nov status |
      | TODAY | T200003   | Imetnik kreiran                     | 00              | 01         |
      | TODAY | T200003   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200003   | Imetnik odklenjen                   | 02              | 01         |
      | TODAY | T200003   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200003   | Imetnik posredovan na CIF           | 02              | 03         |
      | TODAY | T200003   | Imetnik potrjen                     | 03              | 05         |
      | TODAY | T200003   | Imetnik posredovan na SIGOV-CA      | 05              | 07         |
      | TODAY | T200003   | Kartica odpremljena.                | 07              | 08         |
      | TODAY | T200003   | Prejeto potrdilo o prejemu kartice. | 08              | 09         |
    * Preveri zgodovino za imetnika "imetnik3"
      | Datum | Uporabnik | Opis                                | Prejšnji status | Nov status |
      | TODAY | T200003   | Imetnik kreiran                     | 00              | 01         |
      | TODAY | T200003   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200003   | Imetnik odklenjen                   | 02              | 01         |
      | TODAY | T200003   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200003   | Imetnik posredovan na CIF           | 02              | 03         |
      | TODAY | T200003   | Imetnik potrjen                     | 03              | 05         |
      | TODAY | T200003   | Imetnik posredovan na SIGOV-CA      | 05              | 07         |
      | TODAY | T200003   | Kartica odpremljena.                | 07              | 08         |
      | TODAY | T200003   | Prejeto potrdilo o prejemu kartice. | 08              | 09         |
    * Preveri zgodovino za imetnika "imetnik4"
      | Datum | Uporabnik | Opis                                | Prejšnji status | Nov status |
      | TODAY | T200003   | Imetnik kreiran                     | 00              | 01         |
      | TODAY | T200003   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200003   | Imetnik odklenjen                   | 02              | 01         |
      | TODAY | T200003   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200003   | Imetnik posredovan na CIF           | 02              | 03         |
      | TODAY | T200003   | Imetnik potrjen                     | 03              | 05         |
      | TODAY | T200003   | Imetnik posredovan na SIGOV-CA      | 05              | 07         |
      | TODAY | T200003   | Kartica odpremljena.                | 07              | 08         |
      | TODAY | T200003   | Prejeto potrdilo o prejemu kartice. | 08              | 09         |

  Scenario: 11.06 Preverjanje zgodovine za imetnike - uporabnik MB
    * Odpri spletno stran "http://localhost:8080/pametne-kartice-web/" in se prijavi z uporabniškim imenom "T200004" in geslom "T200004" ter izberi sodišče in vlogo "001:S24-OJ v Mariboru"
    * Preveri zgodovino za imetnika "imetnik6"
      | Datum | Uporabnik | Opis                                | Prejšnji status | Nov status |
      | TODAY | T200004   | Imetnik kreiran                     | 00              | 01         |
      | TODAY | T200004   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200004   | Imetnik odklenjen                   | 02              | 01         |
      | TODAY | T200004   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200004   | Imetnik posredovan na CIF           | 02              | 03         |
      | TODAY | T200003   | Imetnik zavrnjen                    | 03              | 06         |
      | TODAY | T200004   | Imetnik odklenjen za dopolnjevanje  | 06              | 01         |
      | TODAY | T200004   | Imetnik zaključen                   | 01              | 02         |
      | TODAY | T200004   | Imetnik posredovan na CIF           | 02              | 03         |
      | TODAY | T200003   | Imetnik potrjen                     | 03              | 05         |
      | TODAY | T200003   | Imetnik posredovan na SIGOV-CA      | 05              | 07         |
      | TODAY | T200003   | Kartica odpremljena.                | 07              | 08         |
      | TODAY | T200003   | Prejeto potrdilo o prejemu kartice. | 08              | 09         |
