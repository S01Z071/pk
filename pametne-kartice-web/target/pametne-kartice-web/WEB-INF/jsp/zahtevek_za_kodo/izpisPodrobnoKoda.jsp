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
    <%-- <a class="btn" href="<%=request.getHeader("referer")%>">Nazaj</a> --%>
    <%--<a class="btn" href="${sessionScope.referer}">Nazaj</a>--%>
    <sec:authorize access="hasRole('001')">
        <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/1/1/2">Nazaj</a>
    </sec:authorize>
    <sec:authorize access="hasRole('002')">
        <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/1/1/2">Nazaj</a>
         <%--a class="btn" href="<%=request.getHeader("referer")%>">Nazaj</a--%>
         <%--button onClick="history.go(-1);return true;" class="btn">Nazaj</button--%>
    </sec:authorize>
</div>



<div id="error" class="errorLogin container" style="text-align:center;">
    ${error}
</div>

<br><br>  

<div class="container">
    <table class="table">    

        <tr><td colspan="3" style="background-color:#e6ebed; text-align:center"><h3>Vloga - ${zahtevekZaKodo.crtnaKoda}</h3></td></tr>
        <tr>
            <td colspan="3">
                 <button data-toggle="modal" href="#natisniVlogo" class="btn" disabled="true" id="printButton">Natisni vlogo</button> 
                 <div id="natisniVlogo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-header">                      
                        <h3 id="myModalLabel" style="text-align:center;"></h3>
                         <div style="text-align:center;">Natisnjen obrazec podpišite in posredujte na SIGOV-CA, zahtevek pa označite, da je posredovan na SIGOV-CA (gumb <b>Posreduj na SIGOV-CA</b>).</div>
                    </div>
                    <div class="modal-footer" style="text-align:center;">  
                        <a href="${pageContext.request.contextPath}/ZahtevekZaKodo/natisni/${zahtevekZaKodo.id}" onclick="showHideDiv('loadingDiv')">
                            <button id="potrdiTiskanje" class="btn btn-primary" onclick="removeModalBackground('natisniVlogo')">Ok</button>
                        </a>
                      
                    </div>

                </div>
                <!--<a href="${pageContext.request.contextPath}/ZahtevekZaKodo/natisni/${zahtevekZaKodo.id}"><button disabled="true" class="btn" id="printButton">Natisni vlogo</button></a>-->
                <a href="${pageContext.request.contextPath}/ZahtevekZaKodo/zgodovina/${zahtevekZaKodo.id}"><button class="btn" id="pregledZgodovineButton">Pregled zgodovine</button></a>
            </td>
        </tr>
        <tr>           
            <td><strong>Polno ime organizacije:</strong><br><br>Status:</td>
            <td>${zahtevekZaKodo.imeOrganizacije}<br><br>${zahtevekZaKodo.status.opis}</td>
            <td>
                <button data-toggle="modal" href="#brisiVlogo" class="btn" disabled="true" style="width:100%; float:right;" id="deleteButton">Briši</button> 
                <br> 
                <div id="brisiVlogo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3 id="myModalLabel" style="text-align:center;">Ali ste prepričani, da želite izbrisati vlogo?</h3>
                    </div>
                    <div class="modal-footer" style="text-align:center;">  
                        <a href="${pageContext.request.contextPath}/ZahtevekZaKodo/brisi/${zahtevekZaKodo.id}" onclick="showHideDiv('loadingDiv')">
                            <button id="potrdiBrisanje" class="btn btn-primary">Potrdi</button>
                        </a>
                        <button id="prekliciBrisanje" class="btn" data-dismiss="modal" aria-hidden="true">Prekliči</button>
                    </div>

                </div>

            </td>
        </tr>
        <tr>
            <td>Naslov organizacije:</td>
            <td>${zahtevekZaKodo.ulica} ${zahtevekZaKodo.hisnaStevilka}<br> ${zahtevekZaKodo.naselje}<br> ${zahtevekZaKodo.postnaStevilka} ${zahtevekZaKodo.nazivPoste}</td>
            <td></td>
        </tr>
        <tr>
            <td colspan="3"><strong>Podatki o imetniku in digitalnem potrdilu:</strong></td> 
        </tr>
        <tr>
            <td>Ime in priimek:</td>
            <td>${zahtevekZaKodo.imetnikIme} ${zahtevekZaKodo.imetnikPriimek}</td>
            <td></td>
        </tr>
        <tr>
            <td>EMŠO:</td>
            <td>${zahtevekZaKodo.imetnikEMSO}</td>
            <td></td>
        </tr>
        <tr>
            <td>E-naslov:</td>
            <td>${zahtevekZaKodo.imetnikEnaslov}</td>
            <td></td>
        </tr>
        <tr>
            <td>Oznaka kartice:</td>
            <td>${zahtevekZaKodo.serijskaStevilka}</td>
            <td></td>
        </tr>
        <tr>
            <td colspan="3"><strong>Podatki o predstojniku:</strong></td>
        </tr>
        <tr>
            <td>Ime in priimek:</td>
            <td>${zahtevekZaKodo.predstojnikIme} ${zahtevekZaKodo.predstojnikPriimek}</td>
            <td></td>
        </tr>


        <tr>
            <td colspan="3"> 
                <a href=""><button data-toggle="modal" href="#PosredujNaSigovca" class="btn" disabled="true" id="sendButton">Posreduj na SIGOV-CA</button> </a>  
                <a href="${pageContext.request.contextPath}/ZahtevekZaKodo/odkleni/${zahtevekZaKodo.id}" onclick="showHideDiv('loadingDiv');"><button disabled="true" class="btn" id="unlockButton">Odkleni</button></a>

                <br> 

                <div id="PosredujNaSigovca" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 id="myModalLabel" style="text-align:center;">Ali ste prepričani, da želite vlogo posredovati na SIGOV-CA?</h4>
                        <div style="text-align:center;">Po posredovanju vloge ne bo mogoče več tiskati.</div>
                        <br>
                    </div>
                    <div class="modal-footer" style="text-align:center;">                          
                        <a href="${pageContext.request.contextPath}/ZahtevekZaKodo/posreduj/${zahtevekZaKodo.id}" onclick="showHideDiv('loadingDiv')">
                            <button id="potrdiPosredovanje" class="btn btn-primary">Potrdi</button>
                        </a>
                        <button id="prekliciPosredovanje" class="btn" data-dismiss="modal" aria-hidden="true">Prekliči</button>
                    </div>

                </div>


            </td>
        </tr>
    </table>                    
    <script>
                            if ("${zahtevekZaKodo.status.sifra}" == "01") {
                                document.getElementById("printButton").disabled = false;
                                document.getElementById("deleteButton").disabled = false;
                            }
                            //Odkleni  
                            if ("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "002" && ("${zahtevekZaKodo.status.sifra}" == "04" || "${zahtevekZaKodo.status.sifra}" == "07")) {
                                document.getElementById("unlockButton").disabled = false;
                            }
                            //Posreduj
                            if ("${zahtevekZaKodo.status.sifra}" == "01") {
                                document.getElementById("sendButton").disabled = false;
                            }

    </script>
</div>





