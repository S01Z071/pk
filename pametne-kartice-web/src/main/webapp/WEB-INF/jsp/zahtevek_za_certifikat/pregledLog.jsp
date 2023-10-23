<%-- 
    Document   : pregledLog
    Created on : 5.9.2013, 12:10:11
    Author     : uporabnik
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div style="padding:2px;">
    <a href="${pageContext.request.contextPath}/izpisPodrobno/${zahtevek.id}" onclick="showHideDiv('loadingDiv');"><button class="btn">Nazaj</button></a>
</div>
<div class="container" style="text-align:center;">
    <h3>Pregled zgodovine za zahtevek: ${zahtevek.crtnaKoda}</h3>

</div>

<c:if test="${fn:length(statusLog)>0}">
    <table class="table">
        <tr style="background-color:gainsboro;">
            <td>Datum</td>
            <td>Uporabnik</td>
            <td>Opis</td>
            <td>Prejšnji status</td>
            <td>Nov status</td>
        </tr>
        <c:forEach var="statusLogi" items="${statusLog}">
            <tr>
                <td><fmt:formatDate value="${statusLogi.datum}" pattern="dd.MM.yyyy - HH:mm:ss" /></td>
                <td>${statusLogi.uporabnik}</td>
                <td>${statusLogi.opis}</td>
                <td>${statusLogi.prejsnjiStatus}</td>
                <td>${statusLogi.novStatus}</td>
            <tr>
            </c:forEach>

    </table>

    <c:forEach var="imetnikPregledLogStatus" items="${imetnikPregledLogStatus}">
        <table class="table">

            <tr>
                <td colspan="5"> 
                    <strong style="padding-left:5px;">Imetnik: ${imetnikPregledLogStatus.imetnikCrtnaKoda} (${imetnikPregledLogStatus.imetnikIme}  ${imetnikPregledLogStatus.imetnikPriimek})</strong>
                </td>
            </tr>       

            <tr style="background-color:ghostwhite;">
                <td>Datum</td>
                <td>Uporabnik</td>
                 <td>Opis</td>
                <td>Prejšnji status</td>
                <td>Nov status</td>
            </tr>
            <c:forEach var="statusLogImetnik" items="${imetnikPregledLogStatus.statusLogImetnik}">
                <tr>
                    <td><fmt:formatDate value="${statusLogImetnik.datumI}" pattern="dd.MM.yyyy - HH:mm:ss" /></td>
                    <td>${statusLogImetnik.uporabnikI}</td>
                    <td>${statusLogImetnik.opis}</td> 
                    <td>${statusLogImetnik.prejsnjiStatusI}</td>
                    <td>${statusLogImetnik.novStatusI}</td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</c:if>


