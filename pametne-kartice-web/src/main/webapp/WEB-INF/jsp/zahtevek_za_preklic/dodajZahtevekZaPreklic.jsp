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
        <h3>Dodajanje zahtevka za preklic digitalnega potrdila</h3>
        <div class="info">
            ${info}
        </div>
    </div>
    <c:if test='${empty zahtevekZaPreklic}'>
        <div class="row">
            <div class="span2"></div>
            <div class="span19">
                <form action="${pageContext.request.contextPath}/ZahtevekZaPreklic/iskanje" method="POST">
                    <table>
                        <tr>
                            <td>Serijska št./Ime in priimek/E-naslov:</td>
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



<c:if test='${fn:length(certifikati)>0}'>
    <div class="container">
        <table class="table">     
            <tr>
                <td>#</td>
                <td>Datum prevzema</td>
                <td>Datum poteka</td>
                <td>Serijska št.</td>           
                <td>Ime in priimek</td>          
                <td>E-naslov</td>  
                <td></td>
            </tr>
            <c:set var="i" value="1" scope="page" />
            <c:forEach var="certifikat" items="${certifikati}">          
                <tr>                              
                    <td>${i}.</td>
                    <td><fmt:formatDate value="${certifikat.datumPrevzema}" pattern="dd.MM.yyyy" /></td>
                    <td><fmt:formatDate value="${certifikat.datumPoteka}" pattern="dd.MM.yyyy" /></td>                            
                    <td>${certifikat.serijskaStevilka}</td>
                    <td>${certifikat.imetnik.ime} ${certifikat.imetnik.priimek}</td>             
                    <td>${certifikat.imetnik.ENaslov}</td>    
                    <td> <a href="${pageContext.request.contextPath}/ZahtevekZaPreklic/dodaj/${certifikat.id}" onclick="showHideDiv('loadingDiv');">
                            <button class="btn">
                                Izberi
                            </button>
                        </a></td>
                </tr>          
                <c:set var="i" value="${i+1}" scope="page" />
            </c:forEach>  
        </table>
    </div>
</c:if>

<c:if test='${not empty zahtevekZaPreklic}'>
    <br>
    <div class="container">
        <sf:form method="POST" modelAttribute="zahtevekZaPreklic" commandName="zahtevekZaPreklic" action="${pageContext.request.contextPath}/ZahtevekZaPreklic/dodaj/process">
            <sf:hidden path="sifraOrganizacije"/>  
            <sf:hidden path="zahtevekID"/>  
            <sf:hidden path="certifikatID"/>  
            <sf:hidden path="imetnikID"/> 

            <div class="row">           
                <h4>Podatki o organizaciji</h4>
            </div>
            <div class="row">
                <div class="span4">
                    <sf:label path="imeOrganizacije">Polno ime organizacije:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input name="imeOrganizacije" path="imeOrganizacije"/>
                </div>               
            </div>
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
                </div>              
            </div>

            <div class="row">
                <div class="span4">
                    <sf:label path="ulica">Ulica:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input name="ulica" path="ulica"/>
                </div>                
            </div>

            <div class="row">
                <div class="span4">
                    <sf:label path="hisnaStevilka">Hišna številka:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input name="hisnaStevilka" path="hisnaStevilka"/>
                </div>               
            </div>

            <div class="row">
                <div class="span4">
                    <sf:label path="postnaStevilka">Poštna številka:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input name="postnaStevilka" path="postnaStevilka"/>
                </div>               
            </div>

            <div class="row">
                <div class="span4">
                    <sf:label path="nazivPoste">Pošta:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input name="nazivPoste" path="nazivPoste"/>
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
                </div>                

                <div class="span1">
                    <sf:label path="predstojnikPriimek">Priimek:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input id="predstojnikPriimek" name="predstojnikPriimek" path="predstojnikPriimek"/>
                </div>               
            </div> 
            <div class="row">
                <div class="span1">
                    <sf:label path="predstojnikFunkcija">Funkcija:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input id="predstojnikFunkcija" name="predstojnikFunkcija" path="predstojnikFunkcija"/>
                </div>                

                <div class="span1">
                    <sf:label path="predstojnikENaslov">E-naslov:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input id="predstojnikENaslov" name="predstojnikENaslov" path="predstojnikENaslov"/>
                </div>               
            </div>  
            <br>
            <div class="row">
                <div class="span4">
                    Podatke o preklicu poslati predstojniku na: 
                </div>
                <div class="span1">
                    <sf:radiobutton id="podatkiPreklicPoslatiP" path="podatkiPreklicPoslatiP" value="E-naslov"/>E-naslov
                </div>
                <div class="span2">
                    <sf:radiobutton id="podatkiPreklicPoslatiP" path="podatkiPreklicPoslatiP" value="Naslov institucije"/>Naslov institucije
                </div>
            </div>
            <br>
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
                    <sf:input name="imetnikEMSO" path="imetnikEMSO" cssErrorClass="errorBorder"/>                   
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
                    Preklicujem potrdilo: 
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
                <div class="span2" style="width:180px;">
                    <sf:label path="serijskaStevilka"> Serijska številka potrdila:</sf:label>
                    </div>
                    <div class="span5">
                    <sf:input style="width:100%;" class="inputBlur" id="serijskaStevilka" name="serijskaStevilka" path="serijskaStevilka" onmouseover="removeClassIfAdmin(this, '${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}');" onfocus="blurIfNotAdmin(this, '${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}');"/>
                </div>               
            </div>    
            <br>

            <div class="row">              
                <strong>Razlog za preklic potrdila:</strong>
            </div> 
            <br>
            <div class="row" style="padding-left:20px;">
                <div class="span1"></div>
                <sf:radiobutton id="razlogPreklica" path="razlogPreklica" value="Zamenjava potrdila"/>Zamenjava potrdila
            </div>
            <br>
            <div class="row" style="padding-left:20px;">
                <div class="span1"></div>
                <sf:radiobutton id="razlogPreklica" path="razlogPreklica" value="Zloraba potrdila"/>Zloraba potrdila
            </div>
            <br>
            <div class="row" style="padding-left:20px;">
                <div class="span1"></div>
                <sf:radiobutton id="razlogPreklica" path="razlogPreklica" value="Sprememba podatkov"/>Sprememba podatkov
            </div>
            <br>
            <div class="row" style="padding-left:20px;">
                <div class="span1"></div>
                <sf:radiobutton id="razlogPreklica" path="razlogPreklica" value="Prenehanje uporabe"/>Prenehanje uporabe
            </div>
            <br>
            <div class="row" style="padding-left:20px;">
                <div class="span1"></div>
                <sf:radiobutton id="razlogPreklica" path="razlogPreklica" value="Ostalo"/>Ostalo
            </div>
            <br>
            <div class="row" style="padding-left:20px;">
                <div class="span1"></div>
                <div class="span2">Obrazložitev (neobvezno):</div>
                <div class="span5">
                    <sf:input style="width:100%;" id="obrazlozitevPreklica" name="obrazlozitevPreklica" path="obrazlozitevPreklica"/>
                </div>   
            </div>
            <br>
            <div class="row">
                <div class="span4">
                    Podatke o preklicu imetniku poslati na: 
                </div>
                <div class="span1">
                    <sf:radiobutton id="podatkiPreklicPoslatiI" path="podatkiPreklicPoslatiI" value="E-naslov"/>E-naslov
                </div>
                <div class="span2">
                    <sf:radiobutton id="podatkiPreklicPoslatiI" path="podatkiPreklicPoslatiI" value="Naslov institucije"/>Naslov institucije
                </div>
            </div>
            <br>
            <div class="row">
                <div class="span4">
                    Zahtevek za preklic izdal:
                </div>
                <div class="span2">
                    <sf:radiobutton id="preklicIzdal" path="preklicIzdal" value="Imetnik potrdila"/>Imetnik potrdila
                </div>
                <div class="span2">
                    <sf:radiobutton id="preklicIzdal" path="preklicIzdal" value="Predstojnik"/>Predstojnik
                </div>
            </div>
            <br><br>
            <div class="row">
                <strong>Podatki o opremi:</strong>
            </div>
            <br>
            <div class="row">
                <div class="span1">                 
                </div>
                <div class="span2">
                    <sf:radiobutton id="oprema" path="oprema" value="Opremo vračam"/>Opremo vračam
                </div>
                <div class="span2">
                    <sf:radiobutton id="oprema" path="oprema" value="Opremo bom obdržal"/>Opremo bom obdržal
                </div>
                <div class="span2">
                    <sf:radiobutton id="oprema" path="oprema" value="Drugo"/>Drugo
                </div>
            </div>
            <br>
            <div class="row" style="padding-left:20px;">             
                <div class="span1">Obrazložitev:</div>
                <div class="span8">
                    <sf:input style="width:100%;" id="obrazlozitevOprema" name="obrazlozitevOprema" path="obrazlozitevOprema"/>
                </div>   
            </div>

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
