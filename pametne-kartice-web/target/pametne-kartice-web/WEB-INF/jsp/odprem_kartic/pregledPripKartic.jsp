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


<div class="container" style="text-align: center;">
    <div class="row">
        <div class="span10">
            <h4>Pregled pripravljenih kartic</h4><br><br>
        </div>
    </div>
</div>    


<div id="error" class="errorLogin container" style="text-align:center;">
    <c:out value="${sessionScope.errorMessage}"/>   
</div>
<c:if test="${fn:length(certifikati)>0}">
    <table class="table">  
        <tr>
            <td>Serijska št. kartice</td>
            <td>Ime in priimek imetnika</td>
            <td>Črtna koda imetnika</td>
            <td>Črtna koda kartice</td>    
            <td></td>
            <td>Dodeljeni čitalci</td>
        </tr>
        <c:forEach var="certifikati" items="${certifikati}">
            <tr>
                <td>${certifikati.kartica.serijskaStevilkaKartice}</td>
                <td>${certifikati.imetnik.ime} ${certifikati.imetnik.priimek}</td>
                <td>${certifikati.imetnik.crtnaKoda}</td>
                <td>${certifikati.kartica.barcode}</td>
                <td><a id="printObvestilo${certifikati.id}" href="${pageContext.request.contextPath}/printObvestilo/${certifikati.id}"><button class="btn">Natisni obvestilo o prejemu</button></a>
                    <a id="PKOdpremljena${certifikati.id}" href="${pageContext.request.contextPath}/odpremljena/${certifikati.id}"><button class="btn">PK odpremljena</button></a></td>            
                <td>
                    <div class="error">                      
                        <c:forEach var="citalec" items="${certifikati.imetnik.seznamCitalcevSigovca}">   
                            <c:if test="${empty citalec.datumVrnitve}">
                                Oznaka: ${citalec.oznaka}<br>   
                            </c:if>
                        </c:forEach>
                    </div>
                </td>
            </tr>
        </c:forEach>  
    </table>

</c:if>

