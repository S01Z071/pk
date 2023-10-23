<%-- 
    Document   : iskanje
    Created on : 29.8.2013, 11:43:08
    Author     : uporabnik
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
            <h4>Iskanje kartic ali čitalcev po oznaki:</h4><br><br>
        </div>        
    </div>  

    <div class="row">
        <div class="span3"></div>
        <div class="span19">
            <form action="${pageContext.request.contextPath}/vracanjeKartCit/iskanje" method="POST">
                <table>
                    <tr>
                        <td>Oznaka/Ime in priimek:</td>
                        <td><input type="text" id="oznaka" name="oznaka" autofocus="true" value="${iskalniNiz}"></td>
                        <td><input id="Isci" type="submit" class="btn" style="width:100px; margin-bottom:10px;" value="Išči" onclick="showHideDiv('loadingDiv');"/></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="error">
            ${error}
        </div>
    </div>
    <div id="datepicker-container">
        <!--kartice VSRS -->
        <c:if test="${fn:length(karticeVSRS)>0}">
            <table class="table">
                <tr>
                    <td colspan="6">
                        <strong>Kartice VSRS: </strong>
                    </td>
                </tr>
                <tr style="background-color:gainsboro;">
                    <td>Ime in priimek</td>
                    <td>Črtna koda imetnika</td>
                    <td>E-naslov imetnika</td>
                    <td>Črtna koda</td>                     
                    <td>Serijska št.</td>
                    <td>Datum inicializacije</td>
                    <td>Datum prejema kartice</td>
                    <td>Datum vrnitve</td>
                    <td></td>
                </tr>

                <c:forEach var="karticeVSRS" items="${karticeVSRS}" varStatus="i">
                    <tr>
                        <td>${karticeVSRS.imetnik.ime} ${karticeVSRS.imetnik.priimek}</td>
                        <td>${karticeVSRS.imetnik.crtnaKoda}</td>
                        <td>${karticeVSRS.imetnik.ENaslov}</td>
                        <td>${karticeVSRS.barcode}</td>  
                        <c:if test="${fn:length(karticeVSRS.serijskaStevilkaKartice) > 10}">
                            <td onmouseover="mouseOverDisplaySpan('spanS${i.index}');" onmouseout="mouseOutDisplaySpan('spanS${i.index}')">
                                <span id="spanS${i.index}" style="display:none; position: absolute; background-color:beige; z-index: 999; cursor: help; border-width: 1px; border-style: solid;">
                                    ${karticeVSRS.serijskaStevilkaKartice}
                                </span> 
                                ${fn:substring(karticeVSRS.serijskaStevilkaKartice, 0, 10)}...   
                            </c:if>
                            <c:if test="${fn:length(karticeVSRS.serijskaStevilkaKartice) <= 10}"> 
                            <td>
                                ${karticeVSRS.serijskaStevilkaKartice}
                            </c:if>
                        </td>   

                        <td><fmt:formatDate value="${karticeVSRS.datumInit}" pattern="dd.MM.yyyy" /></td>
                        <td><fmt:formatDate value="${karticeVSRS.imetnik.datumPrejemaOpreme}" pattern="dd.MM.yyyy" /></td>
                        <td><input autocomplete="off" style="width:90px;" type="text" id="datum2${karticeVSRS.id}" value="<fmt:formatDate value="${karticeVSRS.datumVrnitve}" pattern="d.M.yyyy" />"/></td>
                        <td> <button class="btn" onclick="submitForm2('${karticeVSRS.id}');">Potrdi</button></td>
                    </tr>                   
                </c:forEach>  
            </table>
            <script>
                            function submitForm2(id) {
                                document.getElementById("datum").value = document.getElementById("datum2" + id).value;
                                document.getElementById("idK").value = id;
                                var forma = document.getElementById("form");
                                forma.setAttribute("action", "${pageContext.request.contextPath}/vracanjeKartCit/process/karticaVSRS");
                                forma.submit();
                            }


            </script>
        </c:if>




        <!--kartice SIGOV-CA -->
        <c:if test="${fn:length(seznamKarticSigovca)>0}">
            <table class="table">
                <tr>
                    <td colspan="6">
                        <strong>Kartice SIGOV-CA: </strong>
                    </td>
                </tr>
                <tr style="background-color:gainsboro;">
                    <td>Ime organizacije</td>
                    <td>Ime in priimek</td>                     
                    <td>Tip</td>
                    <td>Oznaka</td>
                    <td>Datum izdaje</td>  
                    <td>Datum vrnitve</td>
                    <td></td>
                </tr>

                <c:forEach var="seznamKarticSigovca" items="${seznamKarticSigovca}">
                    <tr>
                        <td>${seznamKarticSigovca.imeOrganizacije}</td>
                        <td>${seznamKarticSigovca.imeInPriimek}</td>  
                        <td>${seznamKarticSigovca.tip} </td>
                        <td>${seznamKarticSigovca.oznaka}</td>
                        <td> <fmt:formatDate value="${seznamKarticSigovca.datumIzdaje}" pattern="dd.MM.yyyy" /></td>
                        <td><input autocomplete="off" style="width:90px;" type="text" id="datum${seznamKarticSigovca.id}" value="<fmt:formatDate value="${seznamKarticSigovca.datumVrnitve}" pattern="d.M.yyyy" />"/></td>
                        <td> <button class="btn" onclick="submitForm('${seznamKarticSigovca.id}');">Potrdi</button></td>
                    </tr>
                </c:forEach>  
            </table>
            <script>
                            function submitForm(id) {
                                document.getElementById("datum").value = document.getElementById("datum" + id).value;
                                document.getElementById("idK").value = id;
                                var forma = document.getElementById("form");
                                forma.setAttribute("action", "${pageContext.request.contextPath}/vracanjeKartCit/process/kartica");
                                forma.submit();
                            }
            </script>
        </c:if>

        <sf:form id="form" method="POST" name="iskanje" action="">
            <div style="display:none;">
                <input autocomplete="off" type="text" name="datum" id="datum"/>
                <input type="text" id="iskalniNiz" name="iskalniNiz" value="${iskalniNiz}"/>
                <input type="number" id="idK" name="idK" />
            </div>
        </sf:form>

        <!-- citalci SIGOV-CA -->

        <c:if test="${fn:length(seznamCitalcevSigovca)>0}">
            <table class="table">
                <tr>
                    <td colspan="6">
                        <strong>Čitalci SIGOV-CA:</strong>
                    </td>
                </tr>
                <tr style="background-color:gainsboro;">
                    <td>Ime organizacije</td>
                    <td>Ime in priimek</td>                     
                    <td>Tip</td>
                    <td>Oznaka</td>
                    <td>Datum izdaje</td>     
                    <td>Datum vrnitve</td>
                    <td></td>
                </tr>

                <c:forEach var="seznamCitalcevSigovca" items="${seznamCitalcevSigovca}">
                    <tr>
                        <td>${seznamCitalcevSigovca.imeOrganizacije}</td>
                        <td>${seznamCitalcevSigovca.imeInPriimek}</td>  
                        <td>${seznamCitalcevSigovca.tip} </td>
                        <td>${seznamCitalcevSigovca.oznaka}</td>
                        <td> <fmt:formatDate value="${seznamCitalcevSigovca.datumIzdaje}" pattern="dd.MM.yyyy" /></td>  

                        <td><input autocomplete="off" style="width:90px;" type="text" id="datum1${seznamCitalcevSigovca.id}" value="<fmt:formatDate value="${seznamCitalcevSigovca.datumVrnitve}" pattern="d.M.yyyy" />"/></td>

                        <td> <button class="btn" onclick="submitForm1('${seznamCitalcevSigovca.id}');">Potrdi</button></td>
                    </tr>
                </c:forEach>

            </table>

            <script>
                            function submitForm1(id) {
                                document.getElementById("datum").value = document.getElementById("datum1" + id).value;
                                document.getElementById("idK").value = id;
                                var forma = document.getElementById("form");
                                forma.setAttribute("action", "${pageContext.request.contextPath}/vracanjeKartCit/process/citalec");
                                forma.submit();
                            }

            </script>
        </c:if>

    </div>
</div>

