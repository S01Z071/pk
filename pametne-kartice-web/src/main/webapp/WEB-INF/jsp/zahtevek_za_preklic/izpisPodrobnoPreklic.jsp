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
    <a class="btn" href="${pageContext.request.contextPath}/IzpisPodatkovPreklic/1">Nazaj</a>
</div>



<div id="error" class="errorLogin container" style="text-align:center;">
    ${error}
</div>

<br><br>  

<div class="container">
    <table class="table">    

        <tr><td colspan="3" style="background-color:#e6ebed; text-align:center"><h3>Vloga - ${zahtevekZaPreklic.crtnaKoda}</h3></td></tr>
        <tr>
            <td colspan="3">

                <%-- <a href="${pageContext.request.contextPath}/ZahtevekZaPreklic/natisni/${zahtevekZaPreklic.id}" onclick="showHideDiv('loadingDiv')">
                     <button id="printButton" class="btn" disabled="true">Natisni vlogo</button>
                 </a>   
                --%>
                <button data-toggle="modal" href="#natisniVlogo" class="btn" disabled="true" id="printButton">Natisni vlogo</button> 
                <div id="natisniVlogo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-header">                      
                        <h3 id="myModalLabel" style="text-align:center;"></h3>
                        <div style="text-align:center;">Natisnjen obrazec podpišite in posredujte na SIGOV-CA, zahtevek pa označite, da je posredovan na SIGOV-CA (gumb <b>Posreduj na SIGOV-CA</b>).</div>
                    </div>
                    <div class="modal-footer" style="text-align:center;">  
                        <a href="${pageContext.request.contextPath}/ZahtevekZaPreklic/natisni/${zahtevekZaPreklic.id}" onclick="showHideDiv('loadingDiv')">
                            <button id="potrdiTiskanje" class="btn btn-primary" onclick="removeModalBackground('natisniVlogo')">Ok</button>
                        </a>

                    </div>

                </div>

                <a href="${pageContext.request.contextPath}/ZahtevekZaPreklic/zgodovina/${zahtevekZaPreklic.id}"><button class="btn" id="pregledZgodovineButton">Pregled zgodovine</button></a>
            </td>
        </tr>
        <tr>           
            <td><strong>Polno ime organizacije:</strong><br><br>Status:</td>
            <td>${zahtevekZaPreklic.imeOrganizacije}<br><br>${zahtevekZaPreklic.status.opis}</td>
            <td>
                <button data-toggle="modal" href="#brisiVlogo" class="btn" disabled="true" style="width:100%; float:right;" id="deleteButton">Briši</button> 
                <br> 
                <div id="brisiVlogo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3 id="myModalLabel" style="text-align:center;">Ali ste prepričani, da želite izbrisati vlogo?</h3>
                    </div>
                    <div class="modal-footer" style="text-align:center;">  
                        <a href="${pageContext.request.contextPath}/ZahtevekZaPreklic/brisi/${zahtevekZaPreklic.id}" onclick="showHideDiv('loadingDiv')">
                            <button id="potrdiBrisanje" class="btn btn-primary">Potrdi</button>
                        </a>
                        <button id="prekliciBrisanje" class="btn" data-dismiss="modal" aria-hidden="true">Prekliči</button>
                    </div>

                </div>

            </td>
        </tr>
        <tr>
            <td>Naslov organizacije:</td>
            <td>${zahtevekZaPreklic.ulica} ${zahtevekZaPreklic.hisnaStevilka}<br> ${zahtevekZaPreklic.naselje}<br> ${zahtevekZaPreklic.postnaStevilka} ${zahtevekZaPreklic.nazivPoste}</td>
            <td></td>
        </tr>
        <tr>
            <td colspan="3"><strong>Podatki o imetniku in digitalnem potrdilu:</strong></td> 
        </tr>
        <tr>
            <td>Ime in priimek:</td>
            <td>${zahtevekZaPreklic.imetnikIme} ${zahtevekZaPreklic.imetnikPriimek}</td>
            <td></td>
        </tr>
        <tr>
            <td>EMŠO:</td>
            <td>${zahtevekZaPreklic.imetnikEMSO}</td>
            <td></td>
        </tr>
        <tr>
            <td>E-naslov:</td>
            <td>${zahtevekZaPreklic.imetnikEnaslov}</td>
            <td></td>
        </tr>
        <tr>
            <td>Serijska številka potrdila:</td>
            <td>${zahtevekZaPreklic.serijskaStevilka}</td>
            <td></td>
        </tr>
        <tr>
            <td>Tip potrdila:</td>
            <td>${zahtevekZaPreklic.tipPotrdila}</td>
            <td></td>
        </tr>
        <tr>
            <td>Razlog za preklic potrdila:</td>
            <td>${zahtevekZaPreklic.razlogPreklica}</td>
            <td></td>
        </tr>
        <tr>
            <td>Obrazložitev preklica:</td>
            <td>${zahtevekZaPreklic.obrazlozitevPreklica}</td>
            <td></td>
        </tr>
        <tr>
            <td>Podatke o preklicu imetniku poslati na:</td>
            <td>${zahtevekZaPreklic.podatkiPreklicPoslatiI}</td>
            <td></td>
        </tr>
        <tr>
            <td>Zahtevek za preklic izdal:</td>
            <td>${zahtevekZaPreklic.preklicIzdal}</td>
            <td></td>
        </tr>

        <tr>
            <td colspan="3"><strong>Podatki o predstojniku:</strong></td>
        </tr>
        <tr>
            <td>Ime in priimek:</td>
            <td>${zahtevekZaPreklic.predstojnikIme} ${zahtevekZaPreklic.predstojnikPriimek}</td>
            <td></td>
        </tr>
        <tr>
            <td>Funkcija:</td>
            <td>${zahtevekZaPreklic.predstojnikFunkcija}</td>
            <td></td>
        </tr>
        <tr>
            <td>E-naslov:</td>
            <td>${zahtevekZaPreklic.predstojnikENaslov}</td>
            <td></td>
        </tr>
        <tr>
            <td>Podatke o preklicu predstojniku poslati na:</td>
            <td>${zahtevekZaPreklic.podatkiPreklicPoslatiP}</td>
            <td></td>
        </tr>        
        <tr>
            <td colspan="3"><strong>Podatki o opremi</strong></td> 
        </tr>
        <tr>
            <td>Opremo:</td>
            <td>${zahtevekZaPreklic.oprema}</td>
            <td></td>
        </tr>  
        <tr>
            <td>Obrazložitev:</td>
            <td>${zahtevekZaPreklic.obrazlozitevOprema}</td>
            <td></td>
        </tr> 

        <tr>
            <td colspan="3"> 

                <a href="${pageContext.request.contextPath}/ZahtevekZaPreklic/potrdi/${zahtevekZaPreklic.id}" onclick="showHideDiv('loadingDiv')">
                    <button id="confirmButton" class="btn" disabled="true">Potrdi vlogo</button>
                </a>

                <a href="${pageContext.request.contextPath}/ZahtevekZaPreklic/posreduj/${zahtevekZaPreklic.id}" onclick="showHideDiv('loadingDiv')">
                    <button id="sendButton" class="btn" disabled="true">Posreduj na SIGOV-CA</button>
                </a>
                <a href="${pageContext.request.contextPath}/ZahtevekZaPreklic/odkleni/${zahtevekZaPreklic.id}" onclick="showHideDiv('loadingDiv');"><button disabled="true" class="btn" id="unlockButton">Odkleni</button></a>

                <br> 

            </td>
        </tr>
    </table>                    
    <script>
                            if ("${zahtevekZaPreklic.status.sifra}" == "01") {
                                document.getElementById("deleteButton").disabled = false;
                                document.getElementById("confirmButton").disabled = false;
                            }
                            //Odkleni  
                            if (("${zahtevekZaPreklic.status.sifra}" == "04" || "${zahtevekZaPreklic.status.sifra}" == "07" || "${zahtevekZaPreklic.status.sifra}" == "02")) {
                                document.getElementById("unlockButton").disabled = false;
                            }
                            //Posreduj na SIGOVCA
                            if (("${zahtevekZaPreklic.status.sifra}" == "02")) {
                                document.getElementById("sendButton").disabled = false;
                                document.getElementById("printButton").disabled = false;
                            }

    </script>
</div>





