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
            <h4>Pregled certifikatov za prevzem</h4><br><br>
        </div>
    </div>
    <div class="row">
        <div class="error">
            ${error}                 
        </div>
    </div>
</div>        
<c:if test='${fn:length(certifikati)>0}'>
    <table class="table">
        <tr><td colspan="14"></td></tr>
        <tr>
            <td>#</td>
            <td>Ime organizacije</td>
            <td>Ime in priimek</td>
            <td>Naslov</td>           
            <td>Referenčna št.</td>
            <td>Avtorizacijska št.</td>
            <td></td>                    
            <td>Serijska št.</td>
            <td>Tip potrdila</td>

            <td>E-naslov</td>
        </tr>


        <c:set var="i" value="1" scope="page" />
        <c:forEach var="certifikati" items="${certifikati}">               
            <c:if test='${not empty certifikati.imetnik}'>
                <tr id="TR${certifikati.id}" style="background-color:#d8ab63;">
                </c:if>
                <c:if test='${empty certifikati.imetnik}'>
                <tr id="TR${certifikati.id}" style="background-color:#b75f5f;">
                </c:if>                  
                <td>${i}.</td>
                <td>${certifikati.imeOrganizacije}</td>
                <td>${certifikati.imeInPriimek}</td>                            
                <td>${certifikati.naslov} ${certifikati.posta} ${certifikati.kraj} ${certifikati.drzava}</td>
                <td>${certifikati.referencnaSt}</span> &nbsp; <img class="imgHover" src="${pageContext.request.contextPath}/resources/copy.PNG" onclick="copyToClipboard('${certifikati.referencnaSt}')" title="Kopiraj"/></td>  
                <td>${certifikati.avtorizacijskaSt}</td>   
                <td><img class="imgHover" src="${pageContext.request.contextPath}/resources/copy.PNG" onclick="copyToClipboard('${certifikati.avtorizacijskaSt}')" title="Kopiraj"/></td>
              
                <td>${certifikati.serijskaStevilka} </td>
                <td>${certifikati.tipPotrdila}</td>

                <td>${certifikati.ENaslov}</td>
                <td>
                    <%-- "https://www.sigov-ca.gov.si/cda-cgi/clientcgi?action=getBrowserCert&authcode=${certifikati.avtorizacijskaSt}&refNo=${certifikati.referencnaSt}&step=Step_1 " target="_blank" --%>
                    <c:if test='${not empty certifikati.imetnik}'>
                        <%-- <a href="https://www.sigov-ca.gov.si/cda-cgi/clientcgi?action=getBrowserCert&authcode=${certifikati.avtorizacijskaSt}&refNo=${certifikati.referencnaSt}&step=Step_1 " target="_blank">
                        --%>    <button class="btn" onclick="prevzemPotrdila('${certifikati.id}', '${certifikati.avtorizacijskaSt}', '${certifikati.referencnaSt}');">Prevzem potrdila</button>
                        <%--  </a> --%>
                        <button class="btn" disabled="true">Poveži</button>

                    </c:if>
                    <c:if test='${empty certifikati.imetnik}'>
                        <button class="btn" disabled="true">Prevzem potrdila</button>
                        <a href="${pageContext.request.contextPath}/poveziCertfImetnik/${certifikati.id}"><button class="btn">Poveži</button></a>
                    </c:if>
                </td>

            </tr>
            <script>
    if (getCookie('${certifikati.id}') != null) {
        var elem = document.getElementById("TR${certifikati.id}");
        elem.style.background = '#62af81';
    }
            </script>
            <c:set var="i" value="${i+1}" scope="page" />
        </c:forEach>  


    </table>
</c:if>


