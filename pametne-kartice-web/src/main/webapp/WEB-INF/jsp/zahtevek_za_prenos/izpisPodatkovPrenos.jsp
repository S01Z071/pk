<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
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

<script type="text/javascript">


    jQuery(document).ready(function($) {
        $('#tabs').tab();
    });

    if ("${tabStatus}" == 1) {
        $(function() {
            $('#tabs li:eq(0) a').tab('show');
        });
    } else {
        $(function() {
            $('#tabs li:eq(1) a').tab('show');
        });
    }

</script>




<div class="container" style="text-align:center;">
    <h3>Pregled zahtevkov</h3>
</div>
<div class="container" style="text-align:center;width:75%;">
    <ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
        <sec:authorize access="hasRole('001')">
            <li id="zahtevkiTab" class=""><a href="${pageContext.request.contextPath}/izpisPodatkov/1/1/1">Pregled zahtevkov za pridobitev certifikata</a></li> 
            <li id="zahtevkiKodaTab" class=""><a href="${pageContext.request.contextPath}/izpisPodatkov/1/1/2">Pregled zahtevkov za pridobitev kode za odklepanje kartice</a></li> 
            </sec:authorize>
            <sec:authorize access="hasAnyRole('002,005,006')">
            <li id="zahtevkiTab" class=""><a href="${pageContext.request.contextPath}/izpisPodatkovAdmin/1/1/1">Pregled zahtevkov za pridobitev certifikata</a></li>          
            <li id="zahtevkiKodaTab" class=""><a href="${pageContext.request.contextPath}/izpisPodatkovAdmin/1/1/2">Pregled zahtevkov za pridobitev kode za odklepanje kartice</a></li> 

        </sec:authorize>
        <li id="zahtevkiPreklicTab" class=""><a href="${pageContext.request.contextPath}/IzpisPodatkovPreklic/1">Pregled zahtevkov za preklic digitalnega potrdila</a></li>  
        <li id="zahtevkiPrenosTab" class="active"><a href="#">Pregled zahtevkov za prenos digitalnega potrdila</a></li>  
    </ul>
</div>

<%-- STRANI --%>
<c:if test="${fn:length(zahtevkiZaPrenos)>0}">
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
                    <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovPrenos/1"><<&nbsp;</a> 
                    <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovPrenos/${pageNum-1}">Prejšnja&nbsp;</a>                         
                </c:when>
                <c:otherwise>
                    <div class="btn disabled"> << </div>
                    <div class="btn disabled"> Prejšnja&nbsp; </div>
                </c:otherwise>
            </c:choose>  

            <c:forEach var="num" begin='${zacetek}' end='${konec}' >
                <c:if test="${pageNum ne num}">                         
                    <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovPrenos/${num}">${num}</a>                       
                </c:if>
                <c:if test="${pageNum == num}">
                    <div class="btn btn-info disabled"> ${num}</div>
                </c:if>
            </c:forEach>

            <c:choose>
                <c:when test="${pageNum!=izpisCount}">                       
                    <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovPrenos/${pageNum+1}">&nbsp;Naslednja</a> 
                    <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovPrenos/<fmt:formatNumber value="${izpisCount}" minFractionDigits="0" maxFractionDigits="1"/>">&nbsp;>></a> 
                </c:when>
                <c:otherwise>
                    <div class="btn disabled">&nbsp;Naslednja</div>
                    <div class="btn disabled">>></div>
                </c:otherwise>
            </c:choose>  
        </div>
    </div>
</c:if>
<%-- STRANI --%>

<br>
<div class="container">
    <br>
    <c:set var="j" value="1" scope="page" />
    <table class="table">

        <c:forEach var="zahtevekZaPrenos" items="${zahtevkiZaPrenos}"> 
            <tr style="background-color:${zahtevekZaPrenos.status.barva}">  
                <td style="text-align:left;"><strong>#${j+(pageNum-1)*prikazovNaStran}. Zahtevek: ${zahtevekZaPrenos.crtnaKoda}</strong></td>
                <td></td>
                <td style="text-align:left;"><a href="${pageContext.request.contextPath}/zahtevekZaPrenos/podrobno/${zahtevekZaPrenos.id}" onclick="showHideDiv('loadingDiv');"><button class="btn">Podrobno</button></a>
                </td>
            </tr>
            <tr>
                <td>Ime organizacije:</td>
                <td>${zahtevekZaPrenos.imeOrganizacije}</td>
                <td></td>
            </tr>
            <tr>
                <td>Status zahtevka: </td>
                <td> ${zahtevekZaPrenos.status.opis} </td>
                <td></td>
            </tr>                                   
            <c:set var="j" value="${j + 1}" scope="page"/>   
        </c:forEach>
    </table>
</div>
<br>     


<%-- STRANI --%>
<c:if test="${fn:length(zahtevkiZaPrenos)>0}">
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
                    <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovPrenos/1"><<&nbsp;</a> 
                    <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovPrenos/${pageNum-1}">Prejšnja&nbsp;</a>                         
                </c:when>
                <c:otherwise>
                    <div class="btn disabled"> << </div>
                    <div class="btn disabled"> Prejšnja&nbsp; </div>
                </c:otherwise>
            </c:choose>  

            <c:forEach var="num" begin='${zacetek}' end='${konec}' >
                <c:if test="${pageNum ne num}">                         
                    <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovPrenos/${num}">${num}</a>                       
                </c:if>
                <c:if test="${pageNum == num}">
                    <div class="btn btn-info disabled"> ${num}</div>
                </c:if>
            </c:forEach>

            <c:choose>
                <c:when test="${pageNum!=izpisCount}">                       
                    <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovPrenos/${pageNum+1}">&nbsp;Naslednja</a> 
                    <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovPrenos/<fmt:formatNumber value="${izpisCount}" minFractionDigits="0" maxFractionDigits="1"/>">&nbsp;>></a> 
                </c:when>
                <c:otherwise>
                    <div class="btn disabled">&nbsp;Naslednja</div>
                    <div class="btn disabled">>></div>
                </c:otherwise>
            </c:choose>  
        </div>
    </div>
</c:if>
<%-- STRANI --%>



