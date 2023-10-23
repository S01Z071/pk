<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : izpisPodatkov
    Created on : 13.8.2013, 8:46:23
    Author     : uporabnik
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>


<script>
    window.onload = function() {
        window.scrollTo(0, '${sessionScope.scrollTop}');
    };

</script>
<%session.removeAttribute("scrollTop");%> 

<div style="padding:2px;">
    <%-- <a class="btn" href="<%=request.getHeader("referer")%>">Nazaj</a> --%>
    <a class="btn" href="${sessionScope.referer}">Nazaj</a>

</div>



<div id="error" class="errorLogin container" style="text-align:center;">
    <c:out value="${sessionScope.errorMessage}"/>
    <%--request.getSession().getAttribute("errorMessage");%>
    <%request.getSession().setAttribute("errorMessage", "");--%>
</div>

<br><br>  

<div class="container">
    <table class="table">
        <tr>
            <td colspan="3" style="background-color:#e6ebed;text-align: center;">
                <h4>Sporočila:</h4>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <a href="${pageContext.request.contextPath}/Vloga/dodajOpombo/${zahtevek.id}" onclick="showHideDiv('loadingDiv');"><button id="dodajSporociloButton" class="btn" style="">Dodaj sporočilo</button></a> 
            </td>
        </tr>

        <c:forEach var="opombe" items="${opombe}">
            <tr style="background-color:#d0e6ed; text-align:left;">
                <td colspan="3" style="color:${opombe.naslovBarva};">
                    ${opombe.naslov} 
                </td>
            </tr>           
            <tr>
                <td colspan="3">
                    <p class="muted">${opombe.uporabnikSodisce}:${opombe.uporabnikIme} ${opombe.uporabnikPriimek} -
                        <fmt:formatDate value="${opombe.datum}" pattern="dd.MM.yyyy" /></p>
                    <div id="opombeVsebina${k}">${opombe.vsebina}</div></td>
            </tr>
        </c:forEach>
        <tr><td colspan="3"></td></tr>
        <tr><td colspan="3" style="background-color:#e6ebed; text-align:center"><h3>Vloga - ${zahtevek.crtnaKoda}</h3></td></tr>
        <tr>
            <td colspan="3">
                <a href="${pageContext.request.contextPath}/printImetnike/${zahtevek.id}"><button disabled="true" class="btn" id="printIButton">Natisni vse imetnike</button></a>
                <a href="${pageContext.request.contextPath}/printZahtevek/${zahtevek.id}"><button disabled="true" class="btn" id="printButton">Natisni vlogo</button></a>
                <sec:authorize access="hasRole('001')">
                    <a href="${pageContext.request.contextPath}/odkleniZahtevek/${zahtevek.id}"><button disabled="true" class="btn" id="dopolniButton">Dopolni zahtevek</button></a>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('002,006')">



                    <a href="${pageContext.request.contextPath}/potrdiZahtevek/${zahtevek.id}" ><button disabled="true" class="btn" id="potrdiZahtevekButton">Potrdi zahtevek</button></a>






                    <%-- edited --%>

                    <button data-toggle="modal" href="#razlogZavrnitveZ" class="btn" disabled="true" id="zavrniZahtevekButton">Zavrni zahtevek</button>

                    <div id="razlogZavrnitveZ" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h3 id="myModalLabel" style="text-align:center;">Zavrnitev zahtevka</h3>
                        </div>
                        <div class="modal-body">
                            <div style="float:left;">Razlog za zavrnitev zahtevka:
                            </div>

                            <sf:form modelAttribute="opomba" action="${pageContext.request.contextPath}/zavrniZahtevek/${zahtevek.id}" onsubmit="return(preveriOpomboZ())"> 
                                <sf:textarea id="opombaTextAreaZ" path="vsebina" maxlength="255" style="width:500px;height:200px;"/>
                                <sf:errors path="vsebina" cssClass="error"/> 

                            </div>
                            <div class="modal-footer">                                   
                                <input id="zavrniZahtevekPotrdi" type="submit" value="Potrdi" class="btn btn-primary" style="width:100px;"/>
                                <button class="btn" data-dismiss="modal" aria-hidden="true">Prekliči</button>
                            </sf:form>
                        </div>
                    </div>
                    <script>
    function preveriOpomboZ() {
        var tekst = document.getElementById("opombaTextAreaZ").value;
        if (tekst.length > 2 && tekst.length <= 255) {
            return true;
        }

        alert("*Vnesti morate vsaj 3 znake in največ 255 znakov.");
        return false;

    }
                    </script>


                    <a href="${pageContext.request.contextPath}/izvozZahtevekVse/${zahtevek.id}"><button disabled="true" class="btn" id="izvozButton">Izvozi vse</button></a>
                    <a href="${pageContext.request.contextPath}/posredovanoNaSigovCA/${zahtevek.id}"><button disabled="true" class="btn" id="posredovanoNaSigovCAButton">Posredovano na SIGOV-CA</button></a>

                </sec:authorize>
                <sec:authorize access="hasAnyRole('001,002,006,005')">
                    <a href="${pageContext.request.contextPath}/pregledZgodovine/${zahtevek.id}"><button class="btn" id="pregledZgodovineButton">Pregled zgodovine</button></a>
                </sec:authorize>
                <div class="info" id="potrdiSporocilo" style="display:block;">Pred tiskanjem morate vlogo potrditi</div>
            </td>
        </tr>
        <tr>           
            <td><strong>Polno ime organizacije:</strong><br><br>Status:</td>
            <td>${zahtevek.imeOrganizacije}<br><br>${zahtevek.status.opis}</td>
            <td>
                <a href="${pageContext.request.contextPath}/Vloga/uredi/${zahtevek.id}" onclick="showHideDiv('loadingDiv');"><button disabled="true" class="btn" style="width:100%;" id="editButton">Uredi</button></a><br>
                <button data-toggle="modal" href="#brisiVlogo" class="btn" disabled="true" style="width:100%; float:right;" id="deleteButton">Briši</button> 
                <br> 

                <div id="brisiVlogo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3 id="myModalLabel" style="text-align:center;">Ali ste prepričani, da želite izbrisati vlogo?</h3>
                    </div>
                    <div class="modal-footer" style="text-align:center;">  
                        <a href="${pageContext.request.contextPath}/brisiVlogo/${zahtevek.id}" onclick="showHideDiv('loadingDiv')">
                            <button id="potrdiBrisanje" class="btn btn-primary">Potrdi</button>
                        </a>
                        <button id="prekliciBrisanje" class="btn" data-dismiss="modal" aria-hidden="true">Prekliči</button>
                    </div>

                </div>

            </td>
        </tr>
        <tr>
            <td>Naslov organizacije:</td>
            <td>${zahtevek.ulica} ${zahtevek.hisnaStevilka}<br> ${zahtevek.naselje}<br> ${zahtevek.postnaStevilka} ${zahtevek.nazivPoste}</td>
            <td></td>
        </tr>
        <tr>
            <td colspan="3"><strong>Podatki o kontaktni osebi institucije:</strong></td> 
        </tr>
        <tr>
            <td>Ime in priimek:</td>
            <td>${zahtevek.kontaktnaOseba.imeK} ${zahtevek.kontaktnaOseba.priimekK}</td>
            <td></td>
        </tr>
        <tr>
            <td>Funkcija:</td>
            <td>${zahtevek.kontaktnaOseba.funkcijaK}</td>
            <td></td>
        </tr>
        <tr>
            <td>Telefon:</td>
            <td>${zahtevek.kontaktnaOseba.telefonK}</td>
            <td></td>
        </tr>
        <tr>
            <td>E-naslov:</td>
            <td>${zahtevek.kontaktnaOseba.eNaslovK}</td>
            <td></td>
        </tr>
        <tr>
            <td colspan="3"><strong>Podatki o predstojniku institucije:</strong></td>
        </tr>
        <tr>
            <td>Ime in priimek:</td>
            <td>${zahtevek.predstojnik.imeP} ${zahtevek.predstojnik.priimekP}</td>
            <td></td>
        </tr>
        <tr>
            <td>Funkcija:</td>
            <td>${zahtevek.predstojnik.funkcijaP}</td>
            <td></td>
        </tr>
        <tr>
            <td>E-naslov:</td>
            <td>${zahtevek.predstojnik.eNaslovP}</td>
            <td></td>
        </tr>

        <tr>
            <td colspan="3" align="center"><strong>Podatki o bodočih imetnikih</strong></td>
        </tr>
        <c:set var="i" value="1" scope="page" />
        <c:forEach var="imetniki" items="${zahtevek.imetniki}">           
            <tr>
                <td colspan="2" style="text-align:center;"><div style="background-color:${imetniki.status.barvaSIm};"><br>Imetnik številka: #${i} - ${imetniki.status.opisSIm}<br><br></div></td>
                <td style="text-align:right; width:100px;">
                    <a href="${pageContext.request.contextPath}/Vloga/printImetnik/${zahtevek.id}/${imetniki.id}"><button disabled="true" class="btn" style="float:right; width:100%;" id="printImetnik${imetniki.id}">Natisni</button></a>
                    <a href="${pageContext.request.contextPath}/pregledImetnikovPodrobno/${imetniki.id}"><button class="btn" style="float:right; width:100%;">Podrobno</button></a>
                    <sec:authorize access="hasAnyRole('002,006')">
                        <br><a href="${pageContext.request.contextPath}/izvozImetnik/${zahtevek.id}/${imetniki.id}"><button disabled="true" class="btn" style="float:right; width:100%;" id="izvozImetnik${imetniki.id}">Izvoz</button></a>
                        <%--<br><a href="${pageContext.request.contextPath}/PotrdiImetnik/${zahtevek.id}/${imetniki.id}/123"><button onclick="redirectWithScrollTop();" disabled="true" class="btn" style="float:right; width:100%;" id="potrdiImetnik${imetniki.id}">Potrdi</button></a>
                        --%>
                        <br>
                        <%--button onclick="redirectWithScrollTop('${zahtevek.id}', '${imetniki.id}');" disabled="true" class="btn" style="float:right; width:100%;" id="potrdiImetnik${imetniki.id}">Potrdi</button>
                        --%>
                        <c:if test="${imetniki.imaKarticoVSRSBrezCertf !='DA'}">
                            <button onclick="redirectWithScrollTop('${zahtevek.id}', '${imetniki.id}');" disabled="true" class="btn" style="float:right; width:100%;" id="potrdiImetnik${imetniki.id}">Potrdi</button>

                        </c:if>
                        <c:if test="${imetniki.imaKarticoVSRSBrezCertf =='DA'}">
                            <button disabled="true" class="btn" style="float:right; width:100%;" id="potrdiImetnik${imetniki.id}" data-toggle="modal" href="#PotrdiImetnik">Potrdi</button>



                            <div id="PotrdiImetnik" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                    <h4 id="myModalLabel" style="text-align:center;">Ali ste prepričani, da želite potrditi imetnika?</h4>
                                    <div style="text-align:center;">Imetnik ima še vedno v lasti zlato kartico VSRS brez veljavnega certifikata.</div>
                                    <br>
                                </div>
                                <div class="modal-footer" style="text-align:center;">                        
                                    <button class="btn btn-primary" onclick="redirectWithScrollTop('${zahtevek.id}', '${imetniki.id}');">Potrdi</button>                      
                                    <button  class="btn" data-dismiss="modal" aria-hidden="true">Prekliči</button>
                                </div>

                            </div>
                        </c:if>


                        <script>
    function redirectWithScrollTop(zahtevekId, imetnikiId) {
        window.location = "${pageContext.request.contextPath}/PotrdiImetnik/" + zahtevekId + "/" + imetnikiId + "/" + $(document).scrollTop();

    }
                        </script>

                        <br><button data-toggle="modal" href="#razlogZavrnitve${imetniki.id}" class="btn" disabled="true" style="float:right; width:100%;" id="zavrniImetnik${imetniki.id}">Zavrni</button>

                        <div id="razlogZavrnitve${imetniki.id}" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                <h3 id="myModalLabel" style="text-align:center;">Zavrnitev vloge</h3>
                            </div>
                            <div class="modal-body">
                                <div style="float:left; padding-left:3%;">Razlog za zavrnitev:
                                </div>

                                <sf:form modelAttribute="opomba" action="${pageContext.request.contextPath}/ZavrniImetnik/${zahtevek.id}/${imetniki.id}" onsubmit="return(preveriOpombo(${imetniki.id}))"> 
                                    <sf:textarea id="opombaTextArea${imetniki.id}" path="vsebina" maxlength="255" style="width:500px;height:200px;"/>
                                    <sf:errors path="vsebina" cssClass="error"/>
                                </div>
                                <div class="modal-footer">                                   
                                    <input id="zavrniImetnikPotrditev${imetniki.id}" type="submit" value="Potrdi" class="btn btn-primary" style="width:100px;"/>
                                    <button class="btn" data-dismiss="modal" aria-hidden="true">Prekliči</button>
                                </sf:form>
                            </div>
                        </div>
                        <script>
                            function preveriOpombo(id) {
                                var tekst = document.getElementById("opombaTextArea" + id).value;
                                if (tekst.length > 2 && tekst.length <= 255) {
                                    return true;
                                }
                                alert("*Vnesti morate vsaj 3 znake in največ 255 znakov.");
                                return false;

                            }
                        </script>
                    </sec:authorize>
                </td>
                <sec:authorize access="hasAnyRole('002,006')">
                    <c:if test="${imetniki.imaKarticoVSRSBrezCertf =='DA'}">
                    <tr style="background-color:red;">
                        <td colspan="2" style="text-align:center;">
                            IMETNIK IMA V LASTI ZLATO KARTICO VSRS BREZ VELJAVNEGA CERTIFIKATA
                        </td>                   
                    </tr>
                </c:if>
            </sec:authorize>
            </tr>
            <tr>
                <td>Ime:</td>
                <td>${imetniki.ime}</td> 
                <td></td>
            </tr>           
            <tr>
                <td>Priimek:</td>
                <td>${imetniki.priimek}</td> 
                <td></td>
            </tr>
            <tr>
                <td>E-naslov:</td>
                <td>${imetniki.ENaslov}</td>
                <td></td>
            </tr>
            <tr>
                <td>Davčna številka:</td>  
                <td> 
                    <c:choose>
                        <c:when test="${imetniki.status.sifraSIm == '07' || imetniki.status.sifraSIm == '08' || imetniki.status.sifraSIm == '09'}">
                            ********
                        </c:when>
                        <c:otherwise>
                            ${imetniki.davcna}
                        </c:otherwise>
                    </c:choose>                         

                </td>
                <td></td>
            </tr>
            <tr>
                <td>EMŠO:</td>               
                <td>
                    <c:choose>
                        <c:when test="${imetniki.status.sifraSIm == '07' || imetniki.status.sifraSIm == '08' || imetniki.status.sifraSIm == '09'}">
                            *************
                        </c:when>
                        <c:otherwise>
                            ${imetniki.emso}
                        </c:otherwise>
                    </c:choose>                
                </td>
                <td></td>
            </tr>
            <tr>
                <td>Želim pridobiti potrdilo:</td>
                <td>${imetniki.potrdilo}</td>
                <td></td>
            </tr>
            <tr>
                <td>Geslo za preklic potrdila:</td>
                <td>
                    <c:choose>
                        <c:when test="${imetniki.status.sifraSIm == '07' || imetniki.status.sifraSIm == '08' || imetniki.status.sifraSIm == '09'}">
                            ******
                        </c:when>
                        <c:otherwise>
                            ${imetniki.gesloZaPreklic}
                        </c:otherwise>
                    </c:choose>     
                </td>
                <td></td>
            </tr>
            <tr>
                <td>Želim pridobiti digitalno potrdilo na pametni kartici:</td>
                <td>${imetniki.potNaPametnikKartici}</td>
                <td></td>
            </tr>

            <tr>
                <td colspan="3"><strong>Podatki o opremi SIGOV-CA:</strong></td>
            </tr>
            <tr>
                <td>Čitalec:</td>
                <td>
                    <c:if test="${imetniki.imaCitalec == 'DA'}">
                        Imam čitalec SIGOV-CA
                    </c:if>
                    <c:if test="${imetniki.imaCitalec == 'NE'}">
                        Ne potrebujem čitalca SIGOV-CA
                    </c:if>
                </td>
                <td></td>
            </tr>
            <tr>
                <td>Pametna kartica:</td>
                <td>
                    <c:if test="${imetniki.imaPametnoKartico == 'DA'}">
                        Imam pametno kartico SIGOV-CA z vgrajenim čipom
                    </c:if>
                    <c:if test="${imetniki.imaPametnoKartico == 'NE'}">
                        Nimam pametne kartice SIGOV-CA z vgrajenim čipom
                    </c:if>
                </td>
                <td></td>
            </tr>

            <tr>
                <td colspan="3"><strong>Podatki o opremi VSRS:</strong></td>
            </tr>
            <tr>
                <td>Čitalec:</td>
                <td>
                    <c:if test="${imetniki.imaCitalecVSRS == 'NE'}">
                        Potrebujem čitalec, ki naj ga zagotovi VSRS
                    </c:if>
                    <c:if test="${imetniki.imaCitalecVSRS == 'DA'}">
                        Ne potrebujem čitalca, ker je vgrajen v tipkovnici
                    </c:if>
                </td>
                <td></td>
            </tr>
            <tr>
                <td>Pametna kartica:</td>
                <td>
                    <c:if test="${imetniki.imaKarticoVSRSBrezCertf == 'DA'}">
                        Imam pametno kartico VSRS z vgrajenim čipom
                    </c:if>
                    <c:if test="${imetniki.imaKarticoVSRSBrezCertf == 'NE'}">
                        Nimam pametne kartice VSRS z vgrajenim čipom
                    </c:if>
                </td>
                <td></td>
            </tr>

            <c:set var="i" value="${i + 1}" scope="page"/>
            <script>
                if ("${zahtevek.status.sifra}" == "02") {
                    document.getElementById("printImetnik${imetniki.id}").disabled = false;
                }
                if ("${zahtevek.status.sifra}" == "05" && "${imetniki.status.sifraSIm}" != "06") {
                    document.getElementById("izvozImetnik${imetniki.id}").disabled = false;
                }
                if (("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "002" || "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "006") && "${imetniki.status.sifraSIm}" == "03") {
                    document.getElementById("potrdiImetnik${imetniki.id}").disabled = false;
                    document.getElementById("zavrniImetnik${imetniki.id}").disabled = false;

                }
            </script>
        </c:forEach>
        <tr>
            <td colspan="3"> 
                <a href="${pageContext.request.contextPath}/potrdiVlogo/${zahtevek.id}" onclick="showHideDiv('loadingDiv');"><button disabled="true" class="btn" id="confirmButton">Potrdi vlogo</button></a>
                <a href="${pageContext.request.contextPath}/odkleniZahtevek/${zahtevek.id}" onclick="showHideDiv('loadingDiv');"><button disabled="true" class="btn" id="unlockButton">Odkleni</button></a>

                <a href=""><button data-toggle="modal" href="#PosredujNaCIF" class="btn" disabled="true" id="sendButton">Posreduj na CIF</button> </a>

                <%-- <a href="${pageContext.request.contextPath}/posredujZahtevek/${zahtevek.id}" onclick="showHideDiv('loadingDiv');"><button disabled="true" class="btn" id="sendButton">Posreduj na CIF</button></a>
                --%>            
                <br> 

                <div id="PosredujNaCIF" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 id="myModalLabel" style="text-align:center;">Ali ste prepričani, da želite vlogo posredovati na CIF?</h4>
                        <div style="text-align:center;">Po posredovanju vloge ne boste mogli več urejati ali tiskati.</div>
                        <br>
                    </div>
                    <div class="modal-footer" style="text-align:center;">                          
                        <a href="${pageContext.request.contextPath}/posredujZahtevek/${zahtevek.id}" onclick="showHideDiv('loadingDiv')">
                            <button id="potrdiPosredovanje" class="btn btn-primary">Potrdi</button>
                        </a>
                        <button id="prekliciPosredovanje" class="btn" data-dismiss="modal" aria-hidden="true">Prekliči</button>
                    </div>

                </div>


            </td>
        </tr>
    </table>
    <script>
                //Opomba

                if ("${zahtevek.status.sifra}" == "04") {
                    document.getElementById("dodajSporociloButton").disabled = true;
                }


                if (("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "002" || "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "006") && "${zahtevek.status.sifra}" == "03") {
                    document.getElementById("zavrniZahtevekButton").disabled = false;
                    document.getElementById("potrdiZahtevekButton").disabled = false;
                }

                if ("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "001" && "${zahtevek.status.sifra}" == "06")
                    document.getElementById("dopolniButton").disabled = false;
                //Tiskanje
                //if ("${zahtevek.status.sifra}" == "02") {
                if ("${zahtevek.status.sifra}" == "02" && ("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "001" || "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "002" || "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "006")) {
                    document.getElementById("printButton").disabled = false;
                    document.getElementById("printIButton").disabled = false;
                }

                //Izvoz v datoteko
                if (("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "002" || "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "006") && "${zahtevek.status.sifra}" == "05") {
                    document.getElementById("izvozButton").disabled = false;
                }
                //Uredi&Brisi
                if ("${zahtevek.status.sifra}" == "01" && ("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "001" || "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "002")) {
                    document.getElementById("editButton").disabled = false;
                    document.getElementById("deleteButton").disabled = false;
                }
                if ("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "002" && ("${zahtevek.status.sifra}" == "02" || "${zahtevek.status.sifra}" == "03")) {
                    document.getElementById("editButton").disabled = false;
                    document.getElementById("deleteButton").disabled = false;
                }
                if ("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "006" && ("${zahtevek.status.sifra}" == "02" || "${zahtevek.status.sifra}" == "03")) {
                    document.getElementById("editButton").disabled = false;
                }
                //Potrdi
                if ("${zahtevek.status.sifra}" == "01" && ("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "001" || "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "002")) {
                    document.getElementById("confirmButton").disabled = false;
                }
                if ("${zahtevek.status.sifra}" != "01") {
                    document.getElementById("potrdiSporocilo").style.display = "none";
                }
                //Odkleni  
                if ("${zahtevek.status.sifra}" == "02") {
                    document.getElementById("unlockButton").disabled = false;
                }
                if (("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "002" || "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "005") && "${zahtevek.status.sifra}" == "03") {
                    document.getElementById("unlockButton").disabled = false;
                }
                //Posreduj
                if ("${zahtevek.status.sifra}" == "02" && ("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "001" || "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "002")) {
                    document.getElementById("sendButton").disabled = false;
                }
                //Posredovano na SIGOV-CA
                if (("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "002" || "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "006") && "${zahtevek.status.sifra}" == "05") {
                    document.getElementById("posredovanoNaSigovCAButton").disabled = false;
                }

    </script>
</div>







