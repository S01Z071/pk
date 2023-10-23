<%-- 
    Document   : dodajZahtevek
    Created on : 13.8.2013, 9:16:35
    Author     : uporabnik
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="text-align: center;">
    <div class="span10" style="text-align:center;">
        <h3>Dodajanje zahtevka za prenos digitalnega potrdila na drugo sodišče</h3>
        <div class="info">
            ${info}
        </div>
    </div>
    <c:if test='${empty zahtevekZaPrenos}'>
        <div class="row">
            <div class="span2"></div>
            <div class="span19">
                <form action="${pageContext.request.contextPath}/zahtevekZaPrenos/iskanje" method="POST">
                    <table>
                        <tr>
                            <td>Črtna koda kartice/Serijska št potrdila:</td>
                            <td><input type="text" id="iskano" name="iskano" autofocus="true"></td>
                            <td><input id="Isci" type="submit" class="btn" style="width:100px; margin-bottom:10px;" value="Išči" onclick="showHideDiv('loadingDiv');"/></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </c:if>
    <div class="error" id="error">
        ${error}
    </div> 
</div>

<c:if test='${not empty zahtevekZaPrenos}'>
    <br>
    <div class="container">
        <sf:form method="POST" modelAttribute="zahtevekZaPrenos" commandName="zahtevekZaPrenos" action="${pageContext.request.contextPath}/zahtevekZaPrenos/dodaj/process">
            <sf:hidden path="sifraOrganizacije"/>  
            <sf:hidden path="zahtevekID"/>  
            <sf:hidden path="certifikatID"/>  
            <sf:hidden path="imetnikID"/> 
            <sf:hidden path="karticaID"/>  
            <sf:hidden path="zahtevekPrenosID"/> 
            <div class="row">           
                <h4>Podatki o organizaciji, kamor se prenaša kvalificirano digitalno potrdilo</h4>
            </div>
            <div class="row">
                <div class="span4">
                    <sf:label path="imeOrganizacije">Polno ime organizacije:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input name="imeOrganizacije" path="imeOrganizacije"/>
                    <sf:errors path="imeOrganizacije" cssClass="error"/>
                </div>   
            </div>
            <div class="row">
                <div class="span4">
                    <label>Šifra sodišča:</label>
                </div>
                <div class="span3">
                    <strong>${zahtevekZaPrenos.sifraOrganizacije}</strong>
                </div>   
            </div>
            <br>
            <div class="row">
                <strong>Naslov organizacije</strong>
            </div>
            <br>
            <div class="row">
                <div class="span4">
                    <sf:label path="naselje">Naselje:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input name="naselje" path="naselje"/>
                    <sf:errors path="naselje" cssClass="error"/>
                </div>              
            </div>

            <div class="row">
                <div class="span4">
                    <sf:label path="ulica">Ulica:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input name="ulica" path="ulica"/>
                    <sf:errors path="ulica" cssClass="error"/>
                </div>                
            </div>

            <div class="row">
                <div class="span4">
                    <sf:label path="hisnaStevilka">Hišna številka:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input name="hisnaStevilka" path="hisnaStevilka"/>
                    <sf:errors path="hisnaStevilka" cssClass="error"/>
                </div>               
            </div>

            <div class="row">
                <div class="span4">
                    <sf:label path="postnaStevilka">Poštna številka:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input name="postnaStevilka" path="postnaStevilka"/>
                    <sf:errors path="postnaStevilka" cssClass="error"/>
                </div>               
            </div>

            <div class="row">
                <div class="span4">
                    <sf:label path="nazivPoste">Pošta:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input name="nazivPoste" path="nazivPoste"/>
                    <sf:errors path="nazivPoste" cssClass="error"/>
                </div>               
            </div>
            <br>
            <div class="row">
                <strong>Podatki o predstojniku</strong>
            </div>
            <br>
            <div class="row">
                <div class="span1">
                    <sf:label path="predstojnikIme">Ime:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input id="predstojnikIme" name="predstojnikIme" path="predstojnikIme"/>
                    <sf:errors path="predstojnikIme" cssClass="error"/>
                </div>                

                <div class="span1">
                    <sf:label path="predstojnikPriimek">Priimek:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input id="predstojnikPriimek" name="predstojnikPriimek" path="predstojnikPriimek"/>
                    <sf:errors path="predstojnikPriimek" cssClass="error"/>
                </div>               
            </div> 
            <div class="row">
                <div class="span1">
                    <sf:label path="predstojnikFunkcija">Funkcija:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input id="predstojnikFunkcija" name="predstojnikFunkcija" path="predstojnikFunkcija"/>
                    <sf:errors path="predstojnikFunkcija" cssClass="error"/>
                </div>                

                <div class="span1">
                    <sf:label path="predstojnikENaslov">E-naslov:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input id="predstojnikENaslov" name="predstojnikENaslov" path="predstojnikENaslov"/>
                    <sf:errors path="predstojnikENaslov" cssClass="error"/>
                </div>               
            </div>  
            <br>

            <div class="row">
                <strong>Podatki o imetniku in digitalnem potrdilu:</strong>
            </div>
            <br>
            <div class="row">
                <div class="span1" style="width:100px;">
                    <sf:label path="imetnikIme">Ime:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input class="inputBlur" id="imetnikIme" name="imetnikIme" path="imetnikIme" onmouseover="removeClassIfAdmin(this, '${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}');" onfocus="blurIfNotAdmin(this, '${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}');"/>
                </div>

                <div class="span1">
                    <sf:label path="imetnikPriimek">Priimek:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input class="inputBlur" id="imetnikPriimek" name="imetnikPriimek" path="imetnikPriimek" onmouseover="removeClassIfAdmin(this, '${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}');" onfocus="blurIfNotAdmin(this, '${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}');"/>
                </div>                
            </div>

            <div class="row">
                <div class="span1" style="width:100px;">
                    <sf:label path="imetnikEMSO">EMŠO:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input class="inputBlur" name="imetnikEMSO" path="imetnikEMSO" cssErrorClass="errorBorder" onmouseover="removeClassIfAdmin(this, '${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}');" onfocus="blurIfNotAdmin(this, '${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}');"/>                   
                    <sf:errors path="imetnikEMSO" cssClass="error"/>
                </div>               

                <div class="span1">
                    <sf:label path="imetnikEnaslov">E-naslov:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input class="inputBlur" name="imetnikEnaslov" path="imetnikEnaslov" onmouseover="removeClassIfAdmin(this, '${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}');" onfocus="blurIfNotAdmin(this, '${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}');"/>
                </div>               
            </div>
            <br>
            <div class="row" style="padding-left:30px;">
                <div class="span1"></div>
                <div class="span2">
                    Prenašam potrdilo: 
                </div>
                <div class="span1">
                    <sf:radiobutton id="tipPotrdila" path="tipPotrdila" value="Spletno"/>Spletno
                </div>
                <div class="span1">
                    <sf:radiobutton id="tipPotrdila" path="tipPotrdila" value="Posebno" onclick="this.checked=false; document.getElementById('tipPotrdila').checked=true;"/>Posebno
                </div>

            </div>
            <br>   
            <br>

            <div class="row">
                <div class="span2">
                    <sf:label path="serijskaStevilka"> Serijska številka potrdila:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input style="width:100%;" class="inputBlur" id="serijskaStevilka" name="serijskaStevilka" path="serijskaStevilka" onmouseover="removeClassIfAdmin(this, '${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}');" onfocus="blurIfNotAdmin(this, '${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}');"/>
                </div>               
            </div>    
            <br>

            <div class="row">
                <div class="span2">                    
                    <sf:label path="obrazlozitevPrenosa">Obrazložitev (neobvezno):</sf:label>
                    </div>
                    <div class="span6">
                    <sf:input style="width:100%;" id="obrazlozitevPrenosa" name="obrazlozitevPrenosa" path="obrazlozitevPrenosa"/>
                </div>   
            </div>
            <br>

            <br><br><br>
            <div class="row">
                <div class="span10" style="text-align:center;">
                    <input id="potrdi" class="btn btn-primary" style="width:100px;" type="submit" value="Potrdi" onclick="showHideDiv('loadingDiv')"/> 
                    <a href="${pageContext.request.contextPath}/ZahtevekZaPreklic" id="prekliciButton" style="width:80px;" class="btn">Prekliči</a>
                </div>
            </div>
        </sf:form> 
    </div>
</c:if>

<br>
