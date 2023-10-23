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
            <h4>Iskanje pripravljenih kartic</h4><br><br>
        </div>
    </div>
</div>    

<div class="row">
    <div class="span6">
        <form method="GET" name="iskanje" action="${pageContext.request.contextPath}/iskPripKartic/process">
            <strong>Črtna koda kartice:</strong>                   
            <input type="text" name="barCode" id="barCode"/>
            <input id="isci" class="btn" type="submit" value="Išči"/>
        </form>
    </div>
</div>

<div class="errorLogin container" style="text-align:center;">
    <c:out value="${sessionScope.errorMessage}"/>
    <%request.getSession().getAttribute("errorMessage");%>
    <%request.getSession().setAttribute("errorMessage", "");%>
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
                <td><a href="${pageContext.request.contextPath}/printObvestilo/${certifikati.id}"><button class="btn">Natisni obvestilo o prejemu</button></a>
               <a id="PKOdpremljena" href="${pageContext.request.contextPath}/odpremljenaIskanje/${certifikati.id}"><button class="btn">PK odpremljena</button></a></td>
                 <td>
                    <div class="error">                      
                        <c:forEach var="citalec" items="${certifikati.imetnik.seznamCitalcevSigovca}">                      
                            Oznaka: ${citalec.oznaka}<br>                  
                        </c:forEach>
                    </div>
                </td>
            </tr>

            <c:if test='${natisni eq 1}'>
                <script>
                    //Pri iskanju avtomatsko vrne .pdf dokument
                    window.location = "${pageContext.request.contextPath}/printObvestilo/${certifikati.id}";
                </script>
            </c:if>


        </c:forEach>  


    </table>
</c:if>

<script>
    document.getElementById('barCode').focus();
</script>
