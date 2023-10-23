<%-- 
    Document   : zgodovinaImetnik
    Created on : 14.4.2015, 9:15:36
    Author     : Uporabnik
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<div class="container" style="text-align: center;">
    <div class="row">
        <div class="span10">
            <h4>Iskanje imetnikov po e-naslovu ali davčni številki</h4><br><br>
        </div>        
    </div>  

    <div class="row">
        <div class="span2"></div>
        <div class="span19">
            <form action="${pageContext.request.contextPath}/zgodovina/imetnik/iskanje">
                <table>
                    <tr>
                        <td>Davčna št./e-naslov:</td>
                        <td><input type="text" id="iskano" name="iskano" autofocus="true"></td>
                        <td><input id="Isci" type="submit" class="btn" style="width:100px; margin-bottom:10px;" value="Išči" onclick="showHideDiv('loadingDiv');"/></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="error" id="error">
            ${error}
        </div>
    </div>


    <c:if test="${fn:length(zgodovinaImetniks)>0}">
        <div class="accordion" id="accordion2">
            <c:forEach var="zgodovinaImetnik" items="${zgodovinaImetniks}" varStatus="i">
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapse${zgodovinaImetnik.imetnik.id}">
                            ${i.index+1}. ${zgodovinaImetnik.imetnik.ime} ${zgodovinaImetnik.imetnik.priimek}
                        </a>
                    </div>
                    <div id="collapse${zgodovinaImetnik.imetnik.id}" class="accordion-body collapse">
                        <div class="accordion-inner">
                            <table class="tableZgodovinaImetnik table">
                                <tr>
                                    <td>Črtna koda:</td>
                                    <td>${zgodovinaImetnik.imetnik.crtnaKoda}</td>
                                </tr>
                                <tr>
                                    <td>Ime in priimek:</td>
                                    <td>${zgodovinaImetnik.imetnik.ime} ${zgodovinaImetnik.imetnik.priimek}</td>
                                </tr>
                                <tr>
                                    <td>E-naslov:</td>
                                    <td>${zgodovinaImetnik.imetnik.ENaslov}</td>
                                </tr>
                                <tr>
                                    <td>Davčna številka:</td>
                                    <td>${zgodovinaImetnik.imetnik.davcna}</td>
                                </tr>
                                <tr>
                                    <td>Status:</td>
                                    <td>${zgodovinaImetnik.imetnik.status.opisSIm}</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/pregledImetnikovPodrobno/${zgodovinaImetnik.imetnik.id}" onclick="showHideDiv('loadingDiv');"><button class="btn">Podroben pregled</button></a>    
                                        <a href="${pageContext.request.contextPath}/zgodovina/imetnik/pregledZahtevka/${zgodovinaImetnik.imetnik.id}" onclick="showHideDiv('loadingDiv');"><button class="btn">Pregled zahtevka</button></a>    
                                    </td>
                                    <td></td>
                                </tr>
                            </table>
                            <%--ZGODOVINA --%>
                            <table class="table">                                        
                                <tr>
                                    <td colspan="3"><strong>Zgodovina imetnika</strong></td>
                                </tr>
                                <tr style="background-color:ghostwhite;">
                                    <td>Datum</td>
                                    <td>Uporabnik</td>
                                    <td>Opis</td>
                                </tr>
                                <c:forEach var="statusLogImetnik" items="${zgodovinaImetnik.statusLogImetniks}">
                                    <tr>
                                        <td><fmt:formatDate value="${statusLogImetnik.datumI}" pattern="dd.MM.yyyy - HH:mm:ss" /></td>
                                        <td>${statusLogImetnik.uporabnikI}</td>
                                        <td>${statusLogImetnik.opis}</td>                                        
                                    </tr>
                                </c:forEach>
                            </table>          
                            <%-- ZGODOVINA END --%>
                            <%-- ZAHTEVKI ZA PREKLIC --%>
                            <c:if test="${fn:length(zgodovinaImetnik.zahtevekZaPreklics)>0}">
                                <table class="table">
                                    <tr>
                                        <td style="text-align:center;"><strong>Oddani zahtevki za preklic certifikata:</strong></td>
                                    </tr>
                                </table>
                                <div class="accordion" id="accordionCert">
                                    <c:forEach var="zahtevekZaPreklic" items="${zgodovinaImetnik.zahtevekZaPreklics}" varStatus="l">
                                        <div class="accordion-group">
                                            <c:if test="${zahtevekZaPreklic.status.sifra=='07'}">
                                                <div class="accordion-heading" style="background-color:green;">
                                                </c:if>
                                                <c:if test="${zahtevekZaPreklic.status.sifra!='07'}">
                                                    <div class="accordion-heading">
                                                    </c:if>                                               
                                                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordionCert" href="#collapseC${zahtevekZaPreklic.id}">
                                                        ${l.index+1}. Zahtevek za preklic
                                                    </a>
                                                </div>
                                                <div id="collapseC${zahtevekZaPreklic.id}" class="accordion-body collapse">
                                                    <div class="accordion-inner">
                                                        <table class="tableZgodovinaImetnik table">
                                                            <tr>
                                                                <td>Serijska št. potrdila</td>
                                                                <td>${zahtevekZaPreklic.serijskaStevilka}</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Razlog za preklic</td>
                                                                <td>${zahtevekZaPreklic.razlogPreklica}</td>
                                                            </tr>                                                       
                                                            <tr>
                                                                <td>Status</td>
                                                                <td>${zahtevekZaPreklic.status.opis}</td>
                                                            </tr> 
                                                            <tr>
                                                                <td>  
                                                                    <a href="${pageContext.request.contextPath}/ZahtevekZaPreklic/podrobno//${zahtevekZaPreklic.id}" onclick="showHideDiv('loadingDiv');"><button class="btn">Pregled zahtevka</button></a>    
                                                                </td>
                                                                <td></td>
                                                            </tr> 

                                                        </table>
                                                    </div>
                                                </div>
                                            </div>      
                                        </c:forEach>  
                                    </div>
                                </c:if>
                                <%-- ZAHTEVKI ZA PREKLIC END --%>
                                <%-- ZAHTEVKI ZA PRIDOBITEV KODE --%>
                                <c:if test="${fn:length(zgodovinaImetnik.zahtevekZaKodos)>0}">
                                    <table class="table">
                                        <tr>
                                            <td style="text-align:center;"><strong>Oddani zahtevki za pridobitev kode za odklepanje kartice:</strong></td>
                                        </tr>
                                    </table>
                                    <div class="accordion" id="accordionCert">
                                        <c:forEach var="zahtevekZaKodo" items="${zgodovinaImetnik.zahtevekZaKodos}" varStatus="l">
                                            <div class="accordion-group">
                                                <c:if test="${zahtevekZaKodo.status.sifra=='07'}">
                                                    <div class="accordion-heading" style="background-color:green;">
                                                    </c:if>
                                                    <c:if test="${zahtevekZaKodo.status.sifra!='07'}">
                                                        <div class="accordion-heading">
                                                        </c:if>       

                                                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordionCert" href="#collapseCK${zahtevekZaKodo.id}">
                                                            ${l.index+1}. Zahtevek za pridobitev kode
                                                        </a>
                                                    </div>
                                                    <div id="collapseCK${zahtevekZaKodo.id}" class="accordion-body collapse">
                                                        <div class="accordion-inner">
                                                            <table class="tableZgodovinaImetnik table">
                                                                <tr>
                                                                    <td>Serijska št. kartice</td>
                                                                    <td>${zahtevekZaKodo.serijskaStevilka}</td>
                                                                </tr>                                                                                                              
                                                                <tr>
                                                                    <td>Status</td>
                                                                    <td>${zahtevekZaKodo.status.opis}</td>
                                                                </tr> 
                                                                <tr>
                                                                     <td>  
                                                                    <a href="${pageContext.request.contextPath}/ZahtevekZaKodo/podrobno//${zahtevekZaKodo.id}" onclick="showHideDiv('loadingDiv');"><button class="btn">Pregled zahtevka</button></a>    
                                                                </td>
                                                                <td></td>
                                                                </tr>

                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>      
                                            </c:forEach>  
                                        </div>
                                    </c:if>
                                    <%-- ZAHTEVKI ZA PRIDOBITEV KODE END --%>
                                    <%-- KARTICE --%>
                                    <c:if test="${fn:length(zgodovinaImetnik.kartice)>0}">
                                        <table class="table">
                                            <tr>
                                                <td style="text-align:center;"><strong>Kartice s katerimi je imetnik povezan:</strong></td>
                                            </tr>
                                        </table>
                                        <div class="accordion" id="accordionKartica">
                                            <c:forEach var="kartica" items="${zgodovinaImetnik.kartice}" varStatus="j">
                                                <div class="accordion-group">
                                                    <div class="accordion-heading">
                                                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordionKartica" href="#collapseK${kartica.id}">
                                                            ${j.index+1}. Kartica VSRS
                                                        </a>
                                                    </div>
                                                    <div id="collapseK${kartica.id}" class="accordion-body collapse">
                                                        <div class="accordion-inner">
                                                            <table class="tableZgodovinaImetnik table">
                                                                <tr>
                                                                    <td>Serijska št.</td>
                                                                    <td>${kartica.serijskaStevilkaKartice}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Črtna koda</td>
                                                                    <td>${kartica.barcode}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Datum inicializacije</td>
                                                                    <td><fmt:formatDate value="${kartica.datumInit}" pattern="dd.MM.yyyy"/></td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Datum vrnitve</td>
                                                                    <td><fmt:formatDate value="${kartica.datumVrnitve}" pattern="dd.MM.yyyy"/></td>
                                                                </tr>                                                        
                                                            </table>
                                                            <%--CERTIFIKATI NA KARTICI --%>
                                                            <c:if test="${fn:length(kartica.certifikat)>0}">
                                                                <div class="accordion" id="accordionKarticaCert">
                                                                    <c:forEach var="certifikat" items="${kartica.certifikat}" varStatus="k">
                                                                        <div class="accordion-group">
                                                                            <div class="accordion-heading">
                                                                                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordionKarticaCert" href="#collapseKC${certifikat.id}">
                                                                                    ${k.index+1}. Certifikat na kartici
                                                                                </a>
                                                                            </div>
                                                                            <div id="collapseKC${certifikat.id}" class="accordion-body collapse">
                                                                                <div class="accordion-inner">
                                                                                    <table class="tableZgodovinaImetnik table">
                                                                                        <tr>
                                                                                            <td>Ime organizacije</td>
                                                                                            <td>${certifikat.imeOrganizacije}</td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>Serijska št.</td>
                                                                                            <td>${certifikat.serijskaStevilka}</td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>Datum prevzema</td>
                                                                                            <td><fmt:formatDate value="${certifikat.datumPrevzema}" pattern="dd.MM.yyyy"/></td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>Datum poteka</td>
                                                                                            <td><fmt:formatDate value="${certifikat.datumPoteka}" pattern="dd.MM.yyyy"/></td>
                                                                                        </tr>  
                                                                                        <tr>
                                                                                            <td>Status</td>
                                                                                            <td>${certifikat.status.opis}</td>
                                                                                        </tr>  
                                                                                    </table>
                                                                                </div>
                                                                            </div>
                                                                        </div>      
                                                                    </c:forEach>  
                                                                </div>
                                                            </c:if>
                                                            <%--CERTIFIKATI NA KARTICI END --%>                                                        
                                                        </div>
                                                    </div>
                                                </div>      
                                            </c:forEach>  
                                        </div>
                                    </c:if>
                                    <%-- KARTICE END --%>
                                    <%-- CERTIFIKATI BREZ KARTICE --%>
                                    <c:if test="${fn:length(zgodovinaImetnik.certifikati)>0}">
                                        <table class="table">
                                            <tr>
                                                <td style="text-align:center;"><strong>Certifikati brez kartice VSRS s katerimi je imetnik povezan:</strong></td>
                                            </tr>
                                        </table>
                                        <div class="accordion" id="accordionCert">
                                            <c:forEach var="certifikat" items="${zgodovinaImetnik.certifikati}" varStatus="l">
                                                <div class="accordion-group">
                                                    <div class="accordion-heading">
                                                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordionCert" href="#collapseC${certifikat.id}">
                                                            ${l.index+1}. Certifikat
                                                        </a>
                                                    </div>
                                                    <div id="collapseC${certifikat.id}" class="accordion-body collapse">
                                                        <div class="accordion-inner">
                                                            <table class="tableZgodovinaImetnik table">
                                                                <tr>
                                                                    <td>Ime organizacije</td>
                                                                    <td>${certifikat.imeOrganizacije}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Serijska št.</td>
                                                                    <td>${certifikat.serijskaStevilka}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Datum prevzema</td>
                                                                    <td><fmt:formatDate value="${certifikat.datumPrevzema}" pattern="dd.MM.yyyy"/></td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Datum poteka</td>
                                                                    <td><fmt:formatDate value="${certifikat.datumPoteka}" pattern="dd.MM.yyyy"/></td>
                                                                </tr>  
                                                                <tr>
                                                                    <td>Status</td>
                                                                    <td>${certifikat.status.opis}</td>
                                                                </tr>  
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>      
                                            </c:forEach>  
                                        </div>
                                    </c:if>
                                    <%-- CERTIFIKATI BREZ KARTICE END --%>

                                </div>
                            </div>
                        </div>   
                    </c:forEach>
                </div>
            </c:if>
        </div>



