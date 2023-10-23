<%-- 
    Document   : dodajZahtevek
    Created on : 13.8.2013, 9:16:35
    Author     : uporabnik
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="container">
    <div class="span8" style="text-align:center;">
        <h3>Dodajanje zahtevka za pridobitev certifikata</h3>
        <div class="info">
            <c:out value="${sessionScope.info}"/>
            <%request.getSession().getAttribute("info");%>
        </div>
    </div>
</div>
<div class="container">
    <sf:form method="POST" modelAttribute="zahtevek" commandName="zahtevek" action="${pageContext.request.contextPath}/Vloga/process">
        <sf:hidden path="sifraOrganizacije"/>
         <sf:hidden path="id"/>
        <div class="row">           
            <h4>Podatki o organizaciji</h4>
        </div>
        <div class="row">
            <div class="span4">
                <sf:label path="imeOrganizacije">Polno ime organizacije:</sf:label>
                </div>
                <div class="span3">
                <sf:input name="imeOrganizacije" path="imeOrganizacije" cssErrorClass="errorBorder"/>
            </div>
            <div class="span4">
                <sf:errors path="imeOrganizacije" cssClass="error"/>
            </div>
        </div>
        <div class="row">
            <strong>Naslov organizacije</strong>
        </div>

        <div class="row">
            <div class="span4">
                <sf:label path="naselje">Naselje:</sf:label>
                </div>
                <div class="span3">
                <sf:input name="naselje" path="naselje" cssErrorClass="errorBorder"/>
            </div>
            <div class="span4">
                <sf:errors path="naselje" cssClass="error"/>
            </div>
        </div>

        <div class="row">
            <div class="span4">
                <sf:label path="ulica">Ulica:</sf:label>
                </div>
                <div class="span3">
                <sf:input name="ulica" path="ulica" cssErrorClass="errorBorder"/>
            </div>
            <div class="span4">
                <sf:errors path="ulica" cssClass="error"/>
            </div>
        </div>

        <div class="row">
            <div class="span4">
                <sf:label path="hisnaStevilka">Hišna številka:</sf:label>
                </div>
                <div class="span3">
                <sf:input name="hisnaStevilka" path="hisnaStevilka" cssErrorClass="errorBorder" />
            </div>
            <div class="span4">
                <sf:errors id="hisnaStevilka" path="hisnaStevilka" cssClass="error"/>
            </div>
        </div>

        <div class="row">
            <div class="span4">
                <sf:label path="postnaStevilka">Poštna številka:</sf:label>
                </div>
                <div class="span3">
                <sf:input name="postnaStevilka" path="postnaStevilka" cssErrorClass="errorBorder"/>
            </div>
            <div class="span4">
                <sf:errors path="postnaStevilka" cssClass="error"/>
            </div>
        </div>

        <div class="row">
            <div class="span4">
                <sf:label path="nazivPoste">Pošta:</sf:label>
                </div>
                <div class="span3">
                <sf:input name="nazivPoste" path="nazivPoste" cssErrorClass="errorBorder"/>
            </div>
            <div class="span4">
                <sf:errors path="nazivPoste" cssClass="error"/>
            </div>
        </div>
        <br>
        <div class="row">
            <img style="width:15px;height:15px;" src="${pageContext.request.contextPath}/resources/question.png" title="Zaposleni institucije, ki dela na področju informatike in lahko nudi podporo pri uporabi potrdil drugim zaposlenim ter ga s svojim podpisom na zahtevku pooblasti predstojnik za komunikacijo z Overiteljem na MNZ."/>
            <strong>Podatki o kontaktni osebi institucije:</strong>
        </div>

        <div class="row">
            <div class="span1">
                <sf:label path="kontaktnaOseba.imeK">Ime:</sf:label>
                </div>
                <div class="span3">
                <sf:input id="kontaktnaOseba.imeK" name="kontaktnaOseba.imeK" path="kontaktnaOseba.imeK" cssErrorClass="errorBorder" onkeydown="clearEmail('kontaktnaOseba.eNaslovK');"/>
            </div>
            <div class="span2">
                <sf:errors path="kontaktnaOseba.imeK" cssClass="error"/>
            </div>

            <div class="span1">
                <sf:label path="kontaktnaOseba.priimekK">Priimek:</sf:label>
                </div>
                <div class="span3">
                <sf:input id="kontaktnaOseba.priimekK" name="kontaktnaOseba.priimekK" path="kontaktnaOseba.priimekK" cssErrorClass="errorBorder" onkeydown="clearEmail('kontaktnaOseba.eNaslovK');"/>
            </div>
            <div class="span2">
                <sf:errors path="kontaktnaOseba.priimekK" cssClass="error"/>
            </div> 
        </div>

        <div class="row">
            <div class="span1">
                <sf:label path="kontaktnaOseba.funkcijaK">Funkcija:</sf:label>
                </div>
                <div class="span3">
                <sf:input name="kontaktnaOseba.funkcijaK" path="kontaktnaOseba.funkcijaK" cssErrorClass="errorBorder"/>
            </div>
            <div class="span2">
                <sf:errors path="kontaktnaOseba.funkcijaK" cssClass="error"/>
            </div>

            <div class="span1">
                <sf:label path="kontaktnaOseba.telefonK">Telefon:</sf:label>
                </div>
                <div class="span3">
                <sf:input name="kontaktnaOseba.telefonK" path="kontaktnaOseba.telefonK" cssErrorClass="errorBorder"/>
            </div>
            <div class="span2">
                <sf:errors path="kontaktnaOseba.telefonK" cssClass="error"/>
            </div> 
        </div>

        <div class="row">
            <div class="span1">
                <sf:label path="kontaktnaOseba.eNaslovK">E-naslov:</sf:label>
                </div>
                <div class="span3">
                <sf:input id="kontaktnaOseba.eNaslovK" name="kontaktnaOseba.eNaslovK" path="kontaktnaOseba.eNaslovK" cssErrorClass="errorBorder"/>
                  <div id="mailAvtoVnosK" class="btn" style="margin-bottom:10px;" onclick="automaticEmail('kontaktnaOseba.imeK', 'kontaktnaOseba.priimekK', 'kontaktnaOseba.eNaslovK');" title="Avtomatsko izpolni e-naslov iz imena in priimka."><img style="width:15px;height:15px;" src="${pageContext.request.contextPath}/resources/rebuild.png"/></div>         
            </div>
            <div class="span2">
                <sf:errors path="kontaktnaOseba.eNaslovK" cssClass="error"/>
            </div>
        </div>

        <div class="row">
            <img style="width:15px;height:15px;" src="${pageContext.request.contextPath}/resources/question.png" title="Predstojnik institucije, kjer so bodoči imetniki potrdila zaposleni ali za katero delajo."/>
            <strong>Podatki o predstojniku institucije</strong>
        </div>

        <div class="row">
            <div class="span1">
                <sf:label path="predstojnik.imeP">Ime:</sf:label>
                </div>
                <div class="span3">
                <sf:input id="predstojnik.imeP" name="predstojnik.imeP" path="predstojnik.imeP" cssErrorClass="errorBorder" onkeydown="clearEmail('predstojnik.eNaslovP');"/>
            </div>
            <div class="span2">
                <sf:errors path="predstojnik.imeP" cssClass="error"/>
            </div>

            <div class="span1">
                <sf:label path="predstojnik.priimekP">Priimek:</sf:label>
                </div>
                <div class="span3">
                <sf:input id="predstojnik.priimekP" name="predstojnik.priimekP" path="predstojnik.priimekP" cssErrorClass="errorBorder" onkeydown="clearEmail('predstojnik.eNaslovP');"/>
            </div>
            <div class="span2">
                <sf:errors path="predstojnik.priimekP" cssClass="error"/>
            </div> 
        </div>

        <div class="row">
            <div class="span1">
                <sf:label path="predstojnik.funkcijaP">Funkcija:</sf:label>
                </div>
                <div class="span3">
                <sf:input name="predstojnik.funkcijaP" path="predstojnik.funkcijaP" cssErrorClass="errorBorder"/>
            </div>
            <div class="span2">
                <sf:errors path="predstojnik.funkcijaP" cssClass="error"/>
            </div>

            <div class="span1">
                <sf:label path="predstojnik.eNaslovP">E-naslov:</sf:label>
                </div>
                <div class="span3">
                <sf:input id="predstojnik.eNaslovP" name="predstojnik.eNaslovP" path="predstojnik.eNaslovP" cssErrorClass="errorBorder"/>
                <div id="mailAvtoVnosP" class="btn" style="margin-bottom:10px;" onclick="automaticEmail('predstojnik.imeP', 'predstojnik.priimekP', 'predstojnik.eNaslovP');" title="Avtomatsko izpolni e-naslov iz imena in priimka."><img style="width:15px;height:15px;" src="${pageContext.request.contextPath}/resources/rebuild.png"/></div>         
         
            </div>
            <div class="span2">
                <sf:errors path="predstojnik.eNaslovP" cssClass="error"/>
            </div> 
        </div>
        <br>
        <div class="row">  
            <div class="span8">
                <img style="width:15px;height:15px;" src="${pageContext.request.contextPath}/resources/question.png" title="Bodoči imetniki, katerim se izda potrdila na podlagi tega zahtevka."/>
                <strong>Bodoči imetniki:</strong> 
            </div>

            <div class="span1">
                <a style="float:left;" href="${pageContext.request.contextPath}/Vloga/imetnik/dodaj/${zahtevek.id}">
                    <input class="btn" id="addImetnikButton" type="button" value="Dodaj imetnika" style="float:right;" onclick="showHideDiv('loadingDiv');"/>
                </a>
                <script>
                    if ("${zahtevek.id}" == 0 || ("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" != "001" && "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" != "002")) {
                        document.getElementById("addImetnikButton").disabled = "true";
                    }
                    
                </script> 
            </div>
        </div>
        <div class="row">
            <div class="span12">
                <hr style="margin-bottom:2px;"> 
            </div>
        </div>

        <c:forEach var="imetniki" items="${zahtevek.imetniki}">
            <div class="row"> 
                <div class="span2">
                    Ime in priimek:
                </div>
                <div class="span5">
                    ${imetniki.ime} ${imetniki.priimek}   
                </div>
                <div class="span4">
                    <a class="btn" style="width:70px;" href="${pageContext.request.contextPath}/Vloga/imetnik/uredi/${zahtevek.id}/${imetniki.id}" onclick="showHideDiv('loadingDiv');">Uredi</a>
                    <button data-toggle="modal" href="#brisiImetnikP${imetniki.id}" class="btn" style="width:100px;" id="brisiImetnik${imetniki.id}">Briši</button>
                     <script>
                    if (("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" != "001" && "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" != "002")) {
                        document.getElementById("brisiImetnik${imetniki.id}").disabled = "true";
                    }
                </script> 
                    <br>

                    <div id="brisiImetnikP${imetniki.id}" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h3 id="myModalLabel" style="text-align:center;">Ali ste prepričani, da želite izbrisati imetnika?</h3>
                        </div>

                        <div class="modal-footer" style="text-align:center;"> 
                            <a id="brisiPotrdiImetnik${imetniki.id}" class="btn btn-primary" href="${pageContext.request.contextPath}/Vloga/brisiImetnik/${zahtevek.id}/${imetniki.id}" onclick="showHideDiv('loadingDiv');">Potrdi</a>
                            <button id="brisiPrekliciImetnik${imetniki.id}" class="btn" data-dismiss="modal" aria-hidden="true">Prekliči</button>
                        </div>
                    </div>
                </div>
            </div>
               <hr style="margin-top:2px;margin-bottom:2px;">
        </c:forEach>
        <br>
        <div class="row">
            <div class="span10" style="text-align:center;">
                <input id="potrdi" class="btn btn-primary" style="width:100px;" type="submit" value="Potrdi" onclick="showHideDiv('loadingDiv')"/> 
                   <a href="${pageContext.request.contextPath}/indexRedirect/${zahtevek.id}" id="prekliciButton" style="width:80px;" class="btn">Prekliči</a>
              
                <script>
                      if ("${zahtevek.id}" != 0) {
                         document.getElementById("prekliciButton").disabled = true; 
                      }
                      
                </script>
            </div>
        </div>
    </sf:form> 
</div>