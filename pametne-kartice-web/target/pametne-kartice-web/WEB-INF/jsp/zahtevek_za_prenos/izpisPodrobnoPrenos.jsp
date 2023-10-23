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


<div style="padding:2px;">
    <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovPrenos/1">Nazaj</a>
</div>



<div id="error" class="errorLogin container" style="text-align:center;">
    ${error}
</div>

<br><br>  

<div class="container">
    <table class="table">    

        <tr><td colspan="3" style="background-color:#e6ebed; text-align:center"><h3>Vloga - ${zahtevekZaPrenos.crtnaKoda}</h3></td></tr>
        <tr>
            <td colspan="3">
                <button data-toggle="modal" href="#natisniVlogo" class="btn" disabled="true" id="printButton">Natisni vlogo</button> 
                <div id="natisniVlogo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-header">                      
                        <h3 id="myModalLabel" style="text-align:center;"></h3>
                        <div style="text-align:center;">Natisnjen obrazec podpišite in posredujte na SIGOV-CA ali CIF, zahtevek pa označite, da je posredovan na SIGOV-CA (gumb <b>Posreduj na SIGOV-CA</b>).</div>
                    </div>
                    <div class="modal-footer" style="text-align:center;">  
                        <a href="${pageContext.request.contextPath}/zahtevekZaPrenos/natisni/${zahtevekZaPrenos.id}" onclick="showHideDiv('loadingDiv')">
                            <button id="potrdiTiskanje" class="btn btn-primary" onclick="removeModalBackground('natisniVlogo')">Ok</button>
                        </a>

                    </div>

                </div>

                <a href="${pageContext.request.contextPath}/zahtevekZaPrenos/zgodovina/${zahtevekZaPrenos.id}"><button class="btn" id="pregledZgodovineButton">Pregled zgodovine</button></a>
            </td>
        </tr>
        <tr>
            <td colspan="3"><strong>Podatki o organizaciji, kamor se prenaša kvalificirano digitalno potrdilo</strong></td>
        </tr>
        <tr>           
            <td>Polno ime organizacije:<br><br>Status:</td>
            <td>${zahtevekZaPrenos.imeOrganizacije}<br><br>${zahtevekZaPrenos.status.opis}</td>
            <td>
                <button data-toggle="modal" href="#brisiVlogo" class="btn" disabled="true" style="width:100%; float:right;" id="deleteButton">Briši</button> 
                <br> 
                <div id="brisiVlogo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3 id="myModalLabel" style="text-align:center;">Ali ste prepričani, da želite izbrisati vlogo?</h3>
                    </div>
                    <div class="modal-footer" style="text-align:center;">  
                        <a href="${pageContext.request.contextPath}/zahtevekZaPrenos/brisi/${zahtevekZaPrenos.id}" onclick="showHideDiv('loadingDiv')">
                            <button id="potrdiBrisanje" class="btn btn-primary">Potrdi</button>
                        </a>
                        <button id="prekliciBrisanje" class="btn" data-dismiss="modal" aria-hidden="true">Prekliči</button>
                    </div>

                </div>

            </td>
        </tr>
        <tr>
            <td>Naslov organizacije:</td>
            <td>${zahtevekZaPrenos.ulica} ${zahtevekZaPrenos.hisnaStevilka}<br> ${zahtevekZaPrenos.naselje}<br> ${zahtevekZaPrenos.postnaStevilka} ${zahtevekZaPrenos.nazivPoste}</td>
            <td></td>
        </tr>
        <tr>
            <td colspan="3"><strong>Podatki o imetniku in digitalnem potrdilu:</strong></td> 
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <c:if test="${not empty zahtevekZaPrenos.imetnik.id}">
                    <a href="${pageContext.request.contextPath}/pregledImetnikovPodrobno/${zahtevekZaPrenos.imetnik.id}"><button class="btn" id="pregledImetnika">Pregled imetnika</button></a>
                </c:if>
                <c:if test="${empty zahtevekZaPrenos.imetnik.id}">
                    <button class="btn" id="pregledImetnika" disabled="true">Pregled imetnika</button>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>Ime in priimek:</td>
            <td>${zahtevekZaPrenos.imetnikIme} ${zahtevekZaPrenos.imetnikPriimek}</td>
            <td></td>
        </tr>
        <tr>
            <td>EMŠO:</td>
            <td>${zahtevekZaPrenos.imetnikEMSO}</td>
            <td></td>
        </tr>
        <tr>
            <td>E-naslov:</td>
            <td>${zahtevekZaPrenos.imetnikEnaslov}</td>
            <td></td>
        </tr>
        <tr>
            <td>Serijska številka potrdila:</td>
            <td>${zahtevekZaPrenos.serijskaStevilka}</td>
            <td></td>
        </tr>
        <tr>
            <td>Tip potrdila:</td>
            <td>${zahtevekZaPrenos.tipPotrdila}</td>
            <td></td>
        </tr>  
        <tr>
            <td>Obrazložitev prenosa:</td>
            <td>${zahtevekZaPrenos.obrazlozitevPrenosa}</td>
            <td></td>
        </tr> 
        <tr>
            <td colspan="3"><strong>Podatki o predstojniku:</strong></td>
        </tr>
        <tr>
            <td>Ime in priimek:</td>
            <td>${zahtevekZaPrenos.predstojnikIme} ${zahtevekZaPrenos.predstojnikPriimek}</td>
            <td></td>
        </tr>
        <tr>
            <td>Funkcija:</td>
            <td>${zahtevekZaPrenos.predstojnikFunkcija}</td>
            <td></td>
        </tr>
        <tr>
            <td>E-naslov:</td>
            <td>${zahtevekZaPrenos.predstojnikENaslov}</td>
            <td></td>
        </tr>


        <tr>
            <td colspan="3"> 

                <a href="${pageContext.request.contextPath}/zahtevekZaPrenos/potrdi/${zahtevekZaPrenos.id}" onclick="showHideDiv('loadingDiv')">
                    <button id="confirmButton" class="btn" disabled="true">Potrdi vlogo</button>
                </a>

                <a href="${pageContext.request.contextPath}/zahtevekZaPrenos/posreduj/${zahtevekZaPrenos.id}" onclick="showHideDiv('loadingDiv')">
                    <button id="sendButton" class="btn" disabled="true">Posreduj na SIGOV-CA</button>
                </a>
                <a href="${pageContext.request.contextPath}/zahtevekZaPrenos/odkleni/${zahtevekZaPrenos.id}" onclick="showHideDiv('loadingDiv');"><button disabled="true" class="btn" id="unlockButton">Odkleni</button></a>

                <br> 

            </td>
        </tr>
    </table>                    
    <script>
                            if ("${zahtevekZaPrenos.status.sifra}" == "01") {
                                document.getElementById("deleteButton").disabled = false;
                                document.getElementById("confirmButton").disabled = false;
                            }
                            //Odkleni  
                            if (("${zahtevekZaPrenos.status.sifra}" == "04" || "${zahtevekZaPrenos.status.sifra}" == "02")) {
                                document.getElementById("unlockButton").disabled = false;
                            }
                            //Posreduj na SIGOVCA
                            if (("${zahtevekZaPrenos.status.sifra}" == "02")) {
                                document.getElementById("sendButton").disabled = false;
                                document.getElementById("printButton").disabled = false;
                            }

    </script>
</div>





