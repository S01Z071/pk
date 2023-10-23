<%-- 
    Document   : izpisPodatkov
    Created on : 13.8.2013, 8:46:23
    Author     : uporabnik
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="text-align:center;">
    <h3>Pregled kartic VSRS</h3>
</div>

<div class="container" style="text-align:center;">
    <div class="row">
        <div class="span2"></div>
        <div class="span9">
            <table>               
                <form action="${pageContext.request.contextPath}/pregledKarticVSRS/iskanje" method="POST">     
                    <tr>                  
                        <td>
                            <label style="float: left;">Serijska št./Črtna koda/E-naslov/Ime in priimek:</label>
                        </td>
                        <td>
                            <input type="text" id="iskano" name="iskano" autofocus="true" value="${iskano}"/> 
                        </td> 
                      <%--  <td>
                            <input id="Isci" type="submit" class="btn" style="width:100px; margin-bottom: 10px;" value="Išči" onclick="showHideDiv('loadingDiv');"/>
                        </td>--%>
                    </tr>
                    <%-- --%>
                   <tr>
                        <td>
                            <label style="float: right;">Datum vrnitve od:&nbsp;</label>
                        </td>
                        <td>
                            <div id="datepicker-container">
                                <input id="datumOd" name="datumOd" autocomplete="off" style="width:90px;" type="text" value="${datumOd}"/>
                                do:&nbsp;<input id="datumDo" name="datumDo" autocomplete="off" style="width:90px;" type="text" value="${datumDo}"/>
                            </div>
                        </td>                  
                    <td>
                        &nbsp; <input id="Isci" type="submit" class="btn" style="width:100px; margin-bottom: 10px;" value="Išči" onclick="showHideDiv('loadingDiv');"/>
                    </td>
                    </tr>
                    <%--    --%>
                </form>
            </table>  
        </div>
    </div>
    <div class="row">
        <div class="error">
            ${error}
        </div>
    </div>
</div>


<c:if test="${fn:length(kartice)>0}">
    <%-- STRANI --%>

    <div class="container" style="text-align:center;"> 
        <c:set var="zacetek" value="1" scope="page" />
        <c:set var="konec" value='${izpisCount}' scope="page" />
        <c:if test='${izpisCount>5}'>
            <c:set var="zacetek" value='${pageNum-2}' scope="page" />
            <c:set var="konec" value='${pageNum+2}' scope="page" />
            <c:if test='${zacetek<1}'>
                <c:set var="zacetek" value="1" scope="page" />
                <c:set var="konec" value="5" scope="page" />
            </c:if>
            <c:if test='${konec>izpisCount}'>
                <c:set var="zacetek" value='${izpisCount-4}' scope="page" />
                <c:set var="konec" value='${izpisCount}' scope="page" />
            </c:if>
        </c:if>
        <div style="font-size: 16px; font-weight: bold;">
            <c:choose>
                <c:when test="${pageNum!=1}">                          
                    <a class="btn" href="${pageContext.request.contextPath}/pregledKarticVSRS/1"><<&nbsp;</a> 
                    <a class="btn" href="${pageContext.request.contextPath}/pregledKarticVSRS/${pageNum-1}">Prejšnja&nbsp;</a>                           
                </c:when>
                <c:otherwise>
                    <div class="btn disabled"> << </div>
                    <div class="btn disabled"> Prejšnja&nbsp; </div>
                </c:otherwise>
            </c:choose>  

            <c:forEach var="num" begin='${zacetek}' end='${konec}' >
                <c:if test="${pageNum ne num}">                         
                    <a class="btn" href="${pageContext.request.contextPath}/pregledKarticVSRS/${num}">${num}</a>                     
                </c:if>
                <c:if test="${pageNum == num}">
                    <div class="btn btn-info disabled"> ${num}</div>
                </c:if>
            </c:forEach>

            <c:choose>
                <c:when test="${pageNum!=izpisCount}">                         
                    <a class="btn" href="${pageContext.request.contextPath}/pregledKarticVSRS/${pageNum+1}">&nbsp;Naslednja</a> 
                    <a class="btn" href="${pageContext.request.contextPath}/pregledKarticVSRS/<fmt:formatNumber value="${izpisCount}" minFractionDigits="0" maxFractionDigits="1"/>">&nbsp;>></a>                           
                </c:when>
                <c:otherwise>
                    <div class="btn disabled">&nbsp;Naslednja</div>
                    <div class="btn disabled">>></div>
                </c:otherwise>
            </c:choose>  
        </div>
    </div>

    <%-- STRANI --%>


    <br>

    <div class="container">
        <br>
        <table class="table">
            <tr style="background-color:gainsboro;">
                <td>#</td>
                <td>Serijska št. kartice</td>
                <td>Admin geslo</td>
                <td>Ime in priimek imetnika</td>
                <td>Črtna koda kartice</td>
                <td>Datum inicializacije</td>
                <td>Datum vrnitve</td>
                <td>Status certf.</td>
                <td></td>
            </tr>
            <c:set var="j" value="1" scope="page" />
            <c:forEach var="kartica" items="${kartice}">           
                <tr>  
                    <td style="text-align:left;">
                        ${j+(pageNum-1)*prikazovNaStran}.
                    </td>
                    <td>${kartica.serijskaStevilkaKartice}</td>

                    <c:if test="${fn:length(kartica.adminPass) > 12}">
                        <td onmouseover="mouseOverDisplaySpan('spanS${j}');" onmouseout="mouseOutDisplaySpan('spanS${j}')">
                            <span id="spanS${j}" style="display:none; position: absolute; background-color:beige; z-index: 999; cursor: help; border-width: 1px; border-style: solid;">
                                ${kartica.adminPass}
                            </span> 
                            ${fn:substring(kartica.adminPass, 0, 12)}...   
                        </c:if>
                        <c:if test="${fn:length(kartica.adminPass) <= 12}"> 
                        <td>
                            ${kartica.adminPass}
                        </c:if>
                    </td>  


                    <td>${kartica.imetnik.ime} ${kartica.imetnik.priimek}</td>
                    <td>${kartica.barcode}</td>
                    <td><fmt:formatDate value="${kartica.datumInit}" pattern="dd.MM.yyyy" /></td>
                    <td><fmt:formatDate value="${kartica.datumVrnitve}" pattern="dd.MM.yyyy" /></td>
                    <td>
                        <c:forEach var="certifikat" items="${kartica.certifikat}"> 
                            ${certifikat.status.opis}<br>                    
                        </c:forEach>
                        
                    </td>
                    <td style="text-align:right;">
                        <a id="pregledPodrobno${kartica.imetnik.id}" href="${pageContext.request.contextPath}/pregledImetnikovPodrobno/${kartica.imetnik.id}" onclick="showHideDiv('loadingDiv');">
                            <button class="btn">
                                Pregled imetnika
                            </button>
                        </a>
                    </td>                
                </tr> 


                <c:set var="j" value="${j + 1}" scope="page"/>

            </c:forEach>
        </table>
    </div>
    <br>

    <%-- STRANI --%>
    <div class="container" style="text-align:center;"> 
        <c:set var="zacetek" value="1" scope="page" />
        <c:set var="konec" value='${izpisCount}' scope="page" />
        <c:if test='${izpisCount>5}'>
            <c:set var="zacetek" value='${pageNum-2}' scope="page" />
            <c:set var="konec" value='${pageNum+2}' scope="page" />
            <c:if test='${zacetek<1}'>
                <c:set var="zacetek" value="1" scope="page" />
                <c:set var="konec" value="5" scope="page" />
            </c:if>
            <c:if test='${konec>izpisCount}'>
                <c:set var="zacetek" value='${izpisCount-4}' scope="page" />
                <c:set var="konec" value='${izpisCount}' scope="page" />
            </c:if>
        </c:if>
        <div style="font-size: 16px; font-weight: bold;">
            <c:choose>
                <c:when test="${pageNum!=1}">                          
                    <a class="btn" href="${pageContext.request.contextPath}/pregledKarticVSRS/1"><<&nbsp;</a> 
                    <a class="btn" href="${pageContext.request.contextPath}/pregledKarticVSRS/${pageNum-1}">Prejšnja&nbsp;</a>                    
                </c:when>
                <c:otherwise>
                    <div class="btn disabled"> << </div>
                    <div class="btn disabled"> Prejšnja&nbsp; </div>
                </c:otherwise>
            </c:choose>  

            <c:forEach var="num" begin='${zacetek}' end='${konec}' >
                <c:if test="${pageNum ne num}">
                    <a class="btn" href="${pageContext.request.contextPath}/pregledKarticVSRS/${num}">${num}</a>                                     
                </c:if>
                <c:if test="${pageNum == num}">
                    <div class="btn btn-info disabled"> ${num}</div>
                </c:if>
            </c:forEach>

            <c:choose>
                <c:when test="${pageNum!=izpisCount}">                           
                    <a class="btn" href="${pageContext.request.contextPath}/pregledKarticVSRS/${pageNum+1}">&nbsp;Naslednja</a> 
                    <a class="btn" href="${pageContext.request.contextPath}/pregledKarticVSRS/<fmt:formatNumber value="${izpisCount}" minFractionDigits="0" maxFractionDigits="1"/>">&nbsp;>></a>                       
                </c:when>
                <c:otherwise>
                    <div class="btn disabled">&nbsp;Naslednja</div>
                    <div class="btn disabled">>></div>
                </c:otherwise>
            </c:choose>  
        </div>
    </div> 
    <%-- STRANI --%>  
    <br>
</c:if>