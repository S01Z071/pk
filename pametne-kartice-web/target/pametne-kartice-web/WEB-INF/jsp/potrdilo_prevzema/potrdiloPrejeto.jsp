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
            <h4>Potrdila o prejetih karticah</h4><br><br>
        </div>
    </div>
</div>    

<div class="row">
    <div class="span6">
        <form method="GET" name="iskanje" action="${pageContext.request.contextPath}/potrdiloPrejeto/process">
            <strong>Črtna koda imetnika:</strong>                   
            <input type="text" name="barCode" id="barCode"/>
            <input id="isci" class="btn" type="submit" value="Išči"/>
        </form>
    </div>
</div>

<div id="error" class="errorLogin container" style="text-align:center;">
    <c:out value="${sessionScope.errorMessage}"/>
    <%request.getSession().getAttribute("errorMessage");%>
    <%request.getSession().setAttribute("errorMessage", "");%>
    ${error}
</div>

<c:if test="${fn:length(certifikati)>0}">
    <table class="table">
        <tr><td colspan="14"></td></tr>
        <tr>
            <td>Serijska št. kartice</td>
            <td>Ime in priimek imetnika</td>
            <td>Črtna koda imetnika</td>
            <td>Črtna koda kartice</td>  
            <td>Datum</td>
        </tr>


        <c:forEach var="certifikati" items="${certifikati}">
            <sf:form method="GET" name="iskanje" action="${pageContext.request.contextPath}/potrdiloPrejeto/process/${certifikati.id}">
                <tr>
                    <td>${certifikati.kartica.serijskaStevilkaKartice}</td>
                    <td>${certifikati.imetnik.ime} ${certifikati.imetnik.priimek}</td>
                    <td>${certifikati.imetnik.crtnaKoda}</td>
                    <td>${certifikati.kartica.barcode}</td>
                    <%--<td><input type="text" name="datum" id="datum"/></td>--%>                        
                    <td><div id="datepicker-container"><input autocomplete="off" style="width:90px;" type="text" id="datum" name="datum"/></div></td>
                    <td><input id="Potrdi" type="submit" class="btn" value="Potrdi"/></td>
                </tr>
            </sf:form>
        </c:forEach>  


    </table>
</c:if>

<script>
    document.getElementById('barCode').focus();
</script>


