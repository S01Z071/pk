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
            <h4>Izbira datoteke s podatki o certifikatih</h4><br><br>
        </div>
    </div>
    <div class="row">
        <div class="error">
            ${error} 
            <br>                     
        </div>
        <br>
        <c:if test='${not empty uspesno && uspesno!=0}'>
            <div class="success">          
                Uspešno vnešenih vrstic v bazo:${uspesno}<br>           
            </div><br>
        </c:if>
        <c:if test='${not empty neuspesno && neuspesno!=0}'>
            <div class="error">          
                Neuspešno vnešenih vrstic v bazo:${neuspesno}<br>           
            </div><br>
        </c:if> 
        <c:if test='${not empty neNajdenih && neNajdenih!=0}'>
            <div class="error">          
                Certifikatov, ki jih ni bilo mogoče povezati s šifro sodišča:${neNajdenih}<br>           
            </div><br>
        </c:if>   
    </div>
</div>
<br>
<div class="container">
    <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/vnosCertfObstj/process">   
        <div class="row">
            <div class="span7">
                Datoteka:  <input id="browseClick" type="file" name="myfile"/><br/>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="span3">
                <button class="btn" type="submit" onclick="showHideDiv('loadingDiv');">Naloži</button>
            </div>
        </div>


    </form>

    <c:if test="${fn:length(certifikatiBrezSodisca)>0}">
        <table class="table">
            <tr>
                <td colspan="8" style="text-align:center;"><h4>Seznam certifikatov v bazi brez šifre sodišča (${fn:length(certifikatiBrezSodisca)})</h4></td>
            </tr>
            <tr style="background-color:gainsboro;">
                <td>Ime organizacije</td>
                <td>Ime in priimek</td>                  
                <td>E-naslov</td>
                <td>Serijska številka</td>                 
                <td>Datum odobritve</td>
                <td>Datum prevzema</td>
                <td>Datum poteka</td>
                <td>Datum preklica</td>
            </tr>
            <c:forEach var="certifikatiBrezSodisca" items="${certifikatiBrezSodisca}">
                <tr>
                    <td>${certifikatiBrezSodisca.imeOrganizacije}</td>
                    <td>${certifikatiBrezSodisca.imeInPriimek}</td>  
                    <td>${certifikatiBrezSodisca.ENaslov} </td>
                    <td>${certifikatiBrezSodisca.serijskaStevilka}</td>
                    <td> <fmt:formatDate value="${certifikatiBrezSodisca.datumOdobritve}" pattern="dd.MM.yyyy" /></td>
                    <td> <fmt:formatDate value="${certifikatiBrezSodisca.datumPrevzema}" pattern="dd.MM.yyyy" /></td> 
                    <td> <fmt:formatDate value="${certifikatiBrezSodisca.datumPoteka}" pattern="dd.MM.yyyy" /></td>
                    <td> <fmt:formatDate value="${certifikatiBrezSodisca.datumPreklica}" pattern="dd.MM.yyyy" /></td> 

                </tr>
            </c:forEach>  
        </table>

    </c:if>

</div>                 
