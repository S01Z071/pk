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


<div id="error" class="errorLogin container" style="text-align:center;">
    <c:out value="${sessionScope.errorMessage}"/>
    <%request.getSession().getAttribute("errorMessage");%>
    <%request.getSession().setAttribute("errorMessage", "");%>
    ${error}
</div>
<div id="datepicker-container">
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
            <sf:form id="form" method="GET" name="iskanje" action="">
                <div style="display:none;">
                    <input type="text" name="datum" id="datum"/>
                    <input type="checkbox" name="pregled" id="pregled" value="1" checked="true"/>
                </div>
            </sf:form>
            <c:forEach var="certifikati" items="${certifikati}">            
                <tr>
                    <td>${certifikati.kartica.serijskaStevilkaKartice}</td>
                    <td>${certifikati.imetnik.ime} ${certifikati.imetnik.priimek}</td>
                    <td>${certifikati.imetnik.crtnaKoda}</td>
                    <td>${certifikati.kartica.barcode}</td>
                    <td><input autocomplete="off" style="width:90px;" type="text" id="datum${certifikati.id}"/></td>
                    <td>
                        <button id="potrdi${certifikati.id}" class="btn" onclick="submitForm('${certifikati.id}');">Potrdi</button>
                    </td>
                </tr>

            </c:forEach>  


        </table>

        <script>
                            function submitForm(id) {
                                document.getElementById("datum").value = document.getElementById("datum" + id).value;
                                var forma = document.getElementById("form");
                                forma.setAttribute("action", "${pageContext.request.contextPath}/potrdiloPrejeto/process/" + id);
                                forma.submit();
                            }
        </script>
    </c:if>
</div>



