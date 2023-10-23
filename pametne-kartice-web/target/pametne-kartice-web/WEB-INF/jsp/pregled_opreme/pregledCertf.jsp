<%-- 
    Document   : vnosCertifikata
    Created on : 17.9.2013, 8:55:38
    Author     : uporabnik
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div class="container" style="text-align: center;">
    <div class="row">
        <div class="span10">
            <h4>Pregled certifikatov</h4><br><br>
        </div>
    </div>

    <div class="row">
        <div class="span6">
            <sec:authorize access="hasAnyRole('002,005,006')">
                <form method="GET" name="iskanje" action="${pageContext.request.contextPath}/AdminIskanjeCertf/">
                    <strong>Iskanje po šifri sodišča:</strong>                   
                    <input type="text" name="sif" id="iskano"/>
                    <input id="isci" class="btn" type="submit" value="Išči"/>
                </form>
            </div>
        </div>
        <script>
            if ("${sessionScope.sodisceSifra}") {
                document.getElementById("iskano").value = "${sessionScope.sodisceSifra}";
            }
        </script>

    </sec:authorize>
</div>        
<c:if test="${fn:length(certifikati)>0}">
    <table class="table">
        <tr style="background-color:gainsboro;">
            <td>Ime organizacije</td>
            <td>Ime in priimek</td>                     
            <td>Serijska št.</td>
            <td>Datum Prevzema</td>
            <td>Datum Poteka</td>
            <td>Status</td>
        </tr>
        <c:forEach var="certifikati" items="${certifikati}">
            <tr>
                <td>${certifikati.imeOrganizacije}</td>
                <td>${certifikati.imeInPriimek}</td>  
                <td>${certifikati.serijskaStevilka} </td>
                <td> <fmt:formatDate value="${certifikati.datumPrevzema}" pattern="dd.MM.yyyy" /></td>
                <td> <fmt:formatDate value="${certifikati.datumPoteka}" pattern="dd.MM.yyyy" /></td>   
                <td>${certifikati.status.opis}</td>
            </tr>
        </c:forEach>  
    </table>

</c:if>



