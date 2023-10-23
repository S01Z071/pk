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
    <a class="btn" href="<%=request.getHeader("referer")%>">Nazaj</a>
</div>

<br><br>  

<div class="container">
    <table class="table">

        <tr><td colspan="3"></td></tr>
        <tr><td colspan="3" style="background-color:#e6ebed; text-align:center"><h3>Imetnik - ${imetnik.crtnaKoda}</h3></td></tr>
        <tr>
            <td colspan="3">
                <c:if test="${imetnik.prenesenImetnik}">
                    <a href="${pageContext.request.contextPath}/zahtevekZaPrenos/podrobno/${zahtevekID}"><button class="btn" id="PregledZahtevka">Pregled zahtevka</button></a>
                </c:if>
                <c:if test="${!imetnik.prenesenImetnik}">
                    <a href="${pageContext.request.contextPath}/izpisPodrobno/${zahtevekID}"><button class="btn" id="PregledZahtevka">Pregled zahtevka</button></a>
                </c:if>


                <sec:authorize access="hasAnyRole('002,006')">                    
                    <c:if test="${imetnik.imaKarticoVSRSBrezCertf !='DA'}">
                        <a href="${pageContext.request.contextPath}/PotrdiSamoImetnik/${imetnik.id}"><button disabled="true" class="btn"  id="potrdiImetnik${imetnik.id}">Potrdi</button></a>
                    </c:if>
                    <c:if test="${imetnik.imaKarticoVSRSBrezCertf =='DA'}">
                        <button disabled="true" class="btn" id="potrdiImetnik${imetnik.id}" data-toggle="modal" href="#PotrdiImetnik">Potrdi</button>

                        <div id="PotrdiImetnik" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                <h4 id="myModalLabel" style="text-align:center;">Ali ste prepričani, da želite potrditi imetnika?</h4>
                                <div style="text-align:center;">Imetnik ima še vedno v lasti zlato kartico VSRS brez veljavnega certifikata.</div>
                                <br>
                            </div>
                            <div class="modal-footer" style="text-align:center;">                        
                                <a href="${pageContext.request.contextPath}/PotrdiSamoImetnik/${imetnik.id}"><button class="btn btn-primary">Potrdi</button></a>                    
                                <button  class="btn" data-dismiss="modal" aria-hidden="true">Prekliči</button>
                            </div>

                        </div>
                    </c:if>

                    <button data-toggle="modal" href="#razlogZavrnitve${imetnik.id}" class="btn" disabled="true" id="zavrniImetnik${imetnik.id}">Zavrni</button>
                    <a href="${pageContext.request.contextPath}/izvozImetnik/${zahtevek.id}/${imetnik.id}"><button disabled="true" class="btn"  id="izvozImetnik${imetnik.id}">Izvoz</button></a>

                    <div id="razlogZavrnitve${imetnik.id}" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h3 id="myModalLabel" style="text-align:center;">Zavrnitev vloge</h3>
                        </div>
                        <div class="modal-body">
                            <div style="float:left; padding-left:3%;">Razlog za zavrnitev:
                            </div>

                            <sf:form modelAttribute="opomba" action="${pageContext.request.contextPath}/ZavrniSamoImetnik/${imetnik.id}" onsubmit="return(preveriOpombo())"> 
                                <sf:textarea id="opombaTextArea" path="vsebina" maxlength="255" style="width:500px;height:200px;"/>
                                <sf:errors path="vsebina" cssClass="error"/> 

                            </div>
                            <div class="modal-footer">                                   
                                <input type="submit" value="Potrdi" class="btn btn-primary" style="width:100px;"/>
                                <button class="btn" data-dismiss="modal" aria-hidden="true">Prekliči</button>
                            </sf:form>
                        </div>
                    </div>
                    <script>
                        function preveriOpombo() {
                            var tekst = document.getElementById("opombaTextArea").value;
                            if (tekst.length > 2 && tekst.length <= 255) {
                                return true;
                            }
                            alert("*Vnesti morate vsaj 3 znake in največ 255 znakov.");
                            return false;

                        }
                    </script>
                    <script>
                        if ("${zahtevek.status.sifra}" == "05" && "${imetnik.status.sifraSIm}" != "06") {
                            document.getElementById("izvozImetnik${imetnik.id}").disabled = false;
                        }
                        if (("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "002" || "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "006") && "${imetnik.status.sifraSIm}" == "03") {
                            document.getElementById("potrdiImetnik${imetnik.id}").disabled = false;
                            document.getElementById("zavrniImetnik${imetnik.id}").disabled = false;

                        }
                    </script>
                </sec:authorize>
            </td>
            <sec:authorize access="hasAnyRole('002,006')">
                <c:if test="${imetnik.imaKarticoVSRSBrezCertf =='DA'}">
                <tr style="background-color:red;">
                    <td colspan="2" style="text-align:center;">
                        IMETNIK IMA V LASTI ZLATO KARTICO VSRS BREZ VELJAVNEGA CERTIFIKATA
                    </td>                   
                </tr>
            </c:if>
        </sec:authorize>
        </tr>
        <tr>           
            <td>Status:</td>
            <td>${imetnik.status.opisSIm}</td>          
        </tr> 
        <tr>
            <td colspan="2"><strong>Osnovni podatki:</strong></td>           
        </tr>
        <tr>
            <td>Ime in priimek:</td>
            <td>${imetnik.ime} ${imetnik.priimek}</td>
        </tr>
        <tr>
            <td>E-naslov:</td>
            <td>${imetnik.ENaslov}</td>
        </tr>
        <tr>
            <td>Davčna številka:</td>
            <td>
                <c:choose>
                    <c:when test="${imetnik.status.sifraSIm == '07' || imetnik.status.sifraSIm == '08' || imetnik.status.sifraSIm == '09'}">
                        ********
                    </c:when>
                    <c:otherwise>
                        ${imetnik.davcna}
                    </c:otherwise>
                </c:choose>  
            </td>

        </tr>
        <tr>
            <td>EMŠO:</td>
            <td>
                <c:choose>
                    <c:when test="${imetnik.status.sifraSIm == '07' || imetnik.status.sifraSIm == '08' || imetnik.status.sifraSIm == '09'}">
                        ********
                    </c:when>
                    <c:otherwise>
                        ${imetnik.emso}
                    </c:otherwise>
                </c:choose>  
            </td>
        </tr>

        <tr>
            <td colspan="2"><strong>Podatki o digitalnem potrdilu:</strong></td>           
        </tr>
        <tr>
            <td>Želim pridobiti potrdilo:</td>
            <td>${imetnik.potrdilo}</td>
        </tr>
        <tr>
            <td>Geslo za preklic:</td>
            <td>                
                <c:choose>
                    <c:when test="${imetnik.status.sifraSIm == '07' || imetnik.status.sifraSIm == '08' || imetnik.status.sifraSIm == '09'}">
                        ******
                    </c:when>
                    <c:otherwise>
                        ${imetnik.gesloZaPreklic}
                    </c:otherwise>
                </c:choose>     
            </td>           
        </tr>
        <tr>
            <td>Želim pridobiti digitalno potrdilo na pametni kartici:</td>
            <td>${imetnik.potNaPametnikKartici}</td>
        </tr>

        <tr>
            <td colspan="2"><strong>Podatki o opremi SIGOV-CA:</strong></td>
        </tr>
        <tr>
            <td>Čitalec:</td>
            <td>
                <c:if test="${imetnik.imaCitalec == 'DA'}">
                    Imam čitalec SIGOV-CA
                </c:if>
                <c:if test="${imetnik.imaCitalec == 'NE'}">
                    Ne potrebujem čitalca SIGOV-CA
                </c:if>
            </td>
        </tr>

        <tr>
            <td>Pametna kartica:</td>
            <td>
                <c:if test="${imetnik.imaPametnoKartico == 'DA'}">
                    Imam pametno kartico SIGOV-CA z vgrajenim čipom
                </c:if>
                <c:if test="${imetnik.imaPametnoKartico == 'NE'}">
                    Nimam pametne kartice SIGOV-CA z vgrajenim čipom
                </c:if>
            </td>
        </tr>

        <tr>
            <td colspan="2"><strong>Podatki o opremi VSRS:</strong></td>
        </tr>
        <tr>
            <td>Čitalec:</td>
            <td>
                <c:if test="${imetnik.imaCitalecVSRS == 'NE'}">
                    Potrebujem čitalec, ki naj ga zagotovi VSRS
                </c:if>
                <c:if test="${imetnik.imaCitalecVSRS == 'DA'}">
                    Ne potrebujem čitalca, ker je vgrajen v tipkovnici
                </c:if>
            </td>
        </tr>
        <tr>
            <td>Pametna kartica:</td>
            <td>
                <c:if test="${imetnik.imaKarticoVSRSBrezCertf == 'DA'}">
                    Imam pametno kartico VSRS z vgrajenim čipom
                </c:if>
                <c:if test="${imetnik.imaKarticoVSRSBrezCertf == 'NE'}">
                    Nimam pametne kartice VSRS z vgrajenim čipom
                </c:if>
            </td>
        </tr>



        <%-- KARTICE SIGOV-CA --%>
        <c:if test="${fn:length(seznamKarticSigovca)>0}">
            <tr>
                <td colspan="2"><strong style="color:#e55149;">Seznam kartic SIGOV-CA, ki jih ima imetnik v lasti</strong></td>
            </tr>
            <c:set var="kart" value="1" scope="page" />
            <c:forEach var="seznamKarticSigovca" items="${seznamKarticSigovca}">
                <tr>
                    <td>${kart}. Tip in oznaka:</td>
                    <td>${seznamKarticSigovca.tip} - ${seznamKarticSigovca.oznaka}</td>
                </tr>
                <c:set var="kart" value="${kart+1}" scope="page" />
            </c:forEach>
        </c:if>
        <c:if test="${imetnik.status.sifraSIm != '04'}">
            <%-- CITALCI SIGOV-CA --%>
            <c:if test="${fn:length(seznamCitalcevSigovca)>0}">
                <tr>
                    <td colspan="2"><strong style="color:#e55149;">Seznam čitalcev SIGOV-CA, ki jih ima imetnik v lasti</strong></td>
                </tr>
                <c:set var="cit" value="1" scope="page" />
                <c:forEach var="seznamCitalcevSigovca" items="${seznamCitalcevSigovca}">
                    <tr>
                        <td>${cit}. Tip in oznaka:</td>
                        <td>${seznamCitalcevSigovca.tip} - ${seznamCitalcevSigovca.oznaka}</td>
                    </tr>
                    <c:set var="cit" value="${cit+1}" scope="page" />
                </c:forEach>
            </c:if>


            <tr>
                <td colspan="2"><strong>Podatki o karticah in certifikatih:</strong></td>           
            </tr>


            <c:set var="k" value="1" scope="page" />
            <c:forEach var="kartice" items="${kartice}">
                <tr style="background-color:#6675a0;">
                    <td colspan="2">#${k}:Kartica: ${kartice.serijskaStevilkaKartice}</td>
                </tr>
                <tr>
                    <td>Črtna koda:</td>
                    <td>${kartice.barcode}</td>
                </tr>
                <tr>
                    <td>Datum inicializacije:</td>
                    <td><fmt:formatDate value="${kartice.datumInit}" pattern="dd.MM.yyyy" /></td>
                </tr>
                <tr>
                    <td>Datum vrnitve:</td>
                    <td><fmt:formatDate value="${kartice.datumVrnitve}" pattern="dd.MM.yyyy" /></td>
                </tr>
                <tr>
                    <td colspan="2"><strong>Certifikati na kartici:</strong></td>
                </tr>

                <tr>
                    <td colspan="2">
                        <div class="container" style="text-align:center; font-weight: 600;">
                            <div class="row">
                                <div class="span2">
                                    Serijska številka
                                </div>                            
                                <div class="span2">
                                    Datum prevzema
                                </div>
                                <div class="span2">
                                    Datum poteka
                                </div>
                                <div class="span2">
                                    Datum preklica
                                </div>
                                <div class="span2">
                                    Status
                                </div>
                                <div class="span1">                               
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>


                <c:forEach var="certifikati" items="${kartice.certifikat}">                  
                    <tr>
                        <td colspan="2">
                            <div class="container" style="text-align:center;">
                                <div class="row">
                                    <div class="span2">
                                        ${certifikati.serijskaStevilka}
                                    </div>                               
                                    <div class="span2">
                                        <fmt:formatDate value="${certifikati.datumPrevzema}" pattern="dd.MM.yyyy" />
                                    </div>
                                    <div class="span2">
                                        <fmt:formatDate value="${certifikati.datumPoteka}" pattern="dd.MM.yyyy" />
                                    </div>
                                    <div class="span2">
                                        <fmt:formatDate value="${certifikati.datumPreklica}" pattern="dd.MM.yyyy" />
                                    </div>
                                    <div class="span2">
                                        ${certifikati.status.opis}                               
                                    </div>
                                    <div class="span1">                                                                    
                                        <a style="float:right;" href="${pageContext.request.contextPath}/ZahtevekZaPreklic/dodaj/${certifikati.id}"><button disabled="true" class="btn" id="preklicCButton${certifikati.id}">Preklic</button></a>
                                    </div>
                                    <script>
                                        if ("${certifikati.status.sifra}" == "03") {
                                            document.getElementById("preklicCButton${certifikati.id}").disabled = false;
                                        }
                                    </script>

                                </div>
                            </div>
                        </td>

                    </tr>

                </c:forEach>

                <c:set var="k" value="${k+1}" scope="page" />
            </c:forEach>
            <c:if test="${fn:length(certifikati)>0}">
                <tr>
                    <td colspan="2"><strong>Podatki o certifikatih brez kartice:</strong></td>           
                </tr>
                <tr>
                    <td colspan="2">
                        <div class="container" style="text-align:center; font-weight: 600;">
                            <div class="row">
                                <div class="span2">
                                    Serijska številka
                                </div>                            
                                <div class="span2">
                                    Datum prevzema
                                </div>
                                <div class="span2">
                                    Datum poteka
                                </div>
                                <div class="span2">
                                    Datum preklica
                                </div>
                                <div class="span2">
                                    Status
                                </div>                            
                                <div class="span1">                               
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>

                <c:forEach var="certifikati" items="${certifikati}">            
                    <c:choose>    
                        <c:when test="${certifikati.status.sifra=='03'}">
                            <tr style="background-color:#e56b6b">
                            </c:when>
                            <c:otherwise>
                            <tr>
                            </c:otherwise> 
                        </c:choose>
                        <td colspan="2">
                            <div class="container" style="text-align:center;">
                                <div class="row">
                                    <div class="span2">
                                        ${certifikati.serijskaStevilka}
                                    </div>
                                    <div class="span2">
                                        <fmt:formatDate value="${certifikati.datumPrevzema}" pattern="dd.MM.yyyy" />
                                    </div>
                                    <div class="span2">
                                        <fmt:formatDate value="${certifikati.datumPoteka}" pattern="dd.MM.yyyy" />
                                    </div>
                                    <div class="span2">
                                        <fmt:formatDate value="${certifikati.datumPreklica}" pattern="dd.MM.yyyy" />
                                    </div>
                                    <div class="span2">
                                        ${certifikati.status.opis}                                    
                                    </div>
                                    <div class="span1">
                                        <a style="float:right;" href="${pageContext.request.contextPath}/preklicCertifikata/${certifikati.imetnik.id}/${certifikati.id}"><button class="btn" id="preklicCButton" disabled="true">Preklic</button></a>
                                    </div>
                                </div>
                            </div>
                        </td>

                    </tr>

                </c:forEach>
            </c:if>
        </c:if>



    </table>

</div>





