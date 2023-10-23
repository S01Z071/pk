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
            <h4>Iskanje kartic po črtni kodi ali serijski številki</h4><br><br>
        </div>        
    </div>  

    <div class="row">
        <div class="span2"></div>
        <div class="span19">
            <form action="${pageContext.request.contextPath}/zgodovina/kartica/iskanje">
                <table>
                    <tr>
                        <td>Črtna koda/serijska št.:</td>
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


    <c:if test="${fn:length(kartice)>0}">   
        <table class="tabela">

            <c:forEach var="kartica" items="${kartice}" varStatus="j">

                <tr style="background-color:gray;">
                    <td>Črtna koda</td>
                    <td>Datum dodelitve</td>
                    <td>Datum vrnitve</td>
                    <td>Imetnik</td>
                    <td></td>
                    <td></td>
                </tr>
                <tr style="background-color:white;">
                    <td>${kartica.barcode}</td>
                    <td><fmt:formatDate value="${kartica.datumInit}" pattern="dd.MM.yyyy"/></td>
                    <td><fmt:formatDate value="${kartica.datumVrnitve}" pattern="dd.MM.yyyy"/></td>
                    <td>${kartica.imetnik.ime} ${kartica.imetnik.priimek}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/pregledImetnikovPodrobno/${kartica.imetnik.id}" onclick="showHideDiv('loadingDiv');"><button class="btn">Pregled imetnika</button></a>    
                    </td>
                    <td style="width:150px;">
                        <a href="${pageContext.request.contextPath}/zgodovina/imetnik/iskanje?iskano=${kartica.imetnik.davcna}" onclick="showHideDiv('loadingDiv');"><button class="btn">Zgodovina imetnika</button></a>    

                    </td>
                </tr>
                <c:if test="${fn:length(kartica.certifikat)>0}"> 
                    <div class="accordion" id="accordion2">
                        <c:forEach var="certifikat" items="${kartica.certifikat}" varStatus="i">
                            <tr>
                                <td colspan="6" style="text-align:center;">
                                    <div class="accordion-group">
                                        <div class="accordion-heading">
                                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapse${certifikat.id}">
                                                ${i.index+1}. Certifikat na kartici
                                            </a>
                                        </div>
                                        <div id="collapse${certifikat.id}" class="accordion-body collapse">
                                            <div class="accordion-inner">
                                                <table>
                                                    <tr>
                                                        <td></td>
                                                        <td>Serijska št.</td>
                                                        <td>Datum prevzema</td>
                                                        <td>Status</td>
                                                        <td></td>
                                                    </tr>  
                                                    <tr>
                                                        <td></td>
                                                        <td>${certifikat.serijskaStevilka}</td>
                                                        <td><fmt:formatDate value="${certifikat.datumPrevzema}" pattern="dd.MM.yyyy"/></td>
                                                        <td>${certifikat.status.opis}</td>
                                                        <td></td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </div>

                </c:if>
            </c:forEach>  
        </table>
        <br><br><br>
    </c:if>
</div>



