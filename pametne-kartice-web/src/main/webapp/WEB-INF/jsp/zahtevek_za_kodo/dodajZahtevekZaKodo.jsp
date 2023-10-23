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
        <h3>Dodajanje zahtevka za pridobitev kode za odklepanje pametne kartice</h3>
        <div class="info">
            ${info}
        </div>
    </div>
    <c:if test='${empty zahtevekZaKodo}'>
        <div class="row">
            <div class="span2"></div>
            <div class="span19">
                <form action="${pageContext.request.contextPath}/ZahtevekZaKodo/iskanje" method="POST">
                    <table>
                        <tr>
                            <td>Črtna koda/Serijska št./Ime in priimek/E-naslov:</td>
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



<c:if test='${fn:length(kartice)>0}'>
    <div class="container">
        <table class="table">     
            <tr>
                <td>#</td>
                <td>Črtna koda</td>
                <td>Datum inicializacije</td>
                <td>Serijska št.</td>           
                <td>Ime in priimek</td>          
                <td>E-naslov</td>  
                <td></td>
            </tr>
            <c:set var="i" value="1" scope="page" />
            <c:forEach var="kartica" items="${kartice}">          
                <tr>                              
                    <td>${i}.</td>
                    <td>${kartica.barcode}</td>
                    <td><fmt:formatDate value="${kartica.datumInit}" pattern="dd.MM.yyyy" /></td>                            
                    <td>${kartica.serijskaStevilkaKartice}</td>
                    <td>${kartica.imetnik.ime} ${kartica.imetnik.priimek}</td>             
                    <td>${kartica.imetnik.ENaslov}</td>    
                    <td> <a href="${pageContext.request.contextPath}/ZahtevekZaKodo/dodaj/${kartica.id}" onclick="showHideDiv('loadingDiv');">
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

<c:if test='${not empty zahtevekZaKodo}'>
    <br>
    <div class="container">
        <sf:form method="POST" modelAttribute="zahtevekZaKodo" commandName="zahtevekZaKodo" action="${pageContext.request.contextPath}/ZahtevekZaKodo/dodaj/process">
            <sf:hidden path="sifraOrganizacije"/>  
             <sf:hidden path="zahtevekID"/>  
              <sf:hidden path="karticaID"/>  
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
                <strong>Podatki o imetniku in digitalnem potrdilu:</strong>
            </div>
            <br>
            <div class="row">
                <div class="span1" style="width:100px;">
                    <sf:label path="imetnikIme">Ime:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input class="inputBlur" id="imetnikIme" name="imetnikIme" path="imetnikIme" onfocus="blur();"/>
                </div>

                <div class="span1">
                    <sf:label path="imetnikPriimek">Priimek:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input class="inputBlur" id="imetnikPriimek" name="imetnikPriimek" path="imetnikPriimek" onfocus="blur();"/>
                </div>                
            </div>

            <div class="row">
                <div class="span1" style="width:100px;">
                    <sf:label path="imetnikEMSO">EMŠO:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input class="inputBlur" name="imetnikEMSO" path="imetnikEMSO" onfocus="blur();"/>
                </div>               

                <div class="span1">
                    <sf:label path="imetnikEnaslov">E-naslov:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input class="inputBlur" name="imetnikEnaslov" path="imetnikEnaslov" onfocus="blur();"/>
                </div>               
            </div>

            <div class="row">
                <div class="span1" style="width:100px;">
                    <sf:label path="serijskaStevilka">Oznaka kartice:</sf:label>
                    </div>
                    <div class="span3">
                    <sf:input class="inputBlur" id="serijskaStevilka" name="serijskaStevilka" path="serijskaStevilka" onfocus="blur();"/>
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
            <br>
            <div class="row">
                <div class="span10" style="text-align:center;">
                    <input id="potrdi" class="btn btn-primary" style="width:100px;" type="submit" value="Potrdi" onclick="showHideDiv('loadingDiv')"/> 
                    <a href="${pageContext.request.contextPath}/ZahtevekZaKodo" id="prekliciButton" style="width:80px;" class="btn">Prekliči</a>
                </div>
            </div>
        </sf:form> 
    </div>
</c:if>


<br>
