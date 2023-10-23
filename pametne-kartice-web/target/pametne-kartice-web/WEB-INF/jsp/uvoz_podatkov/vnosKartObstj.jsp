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
            <h4>Izbira datoteke s podatki o karticah</h4><br><br>
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
                Kartice, ki jih ni bilo mogoče povezati s šifro sodišča:${neNajdenih}<br>           
            </div><br>
        </c:if>   
    </div>
</div>
<br>
<div class="container">
    <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/vnosKartObstj/process">   
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


    <c:if test="${fn:length(seznamKarticSigovca)>0}">
        <table class="table">
            <tr>
                <td colspan="6" style="text-align:center;"><h4>Seznam kartic v bazi brez šifre sodišča (${fn:length(seznamKarticSigovca)})</h4></td>
            </tr>
            <tr style="background-color:gainsboro;">
                <td>Ime organizacije</td>
                <td>Ime in priimek</td>                     
                <td>Tip</td>
                <td>Oznaka</td>
                <td>Datum izdaje</td>            
                <td>Datum vrnitve</td> 
            </tr>
            <c:forEach var="seznamKarticSigovca" items="${seznamKarticSigovca}">
                <tr>
                    <td>${seznamKarticSigovca.imeOrganizacije}</td>
                    <td>${seznamKarticSigovca.imeInPriimek}</td>  
                    <td>${seznamKarticSigovca.tip} </td>
                    <td>${seznamKarticSigovca.oznaka}</td>
                    <td> <fmt:formatDate value="${seznamKarticSigovca.datumIzdaje}" pattern="dd.MM.yyyy" /></td>
                    <td> <fmt:formatDate value="${seznamKarticSigovca.datumVrnitve}" pattern="dd.MM.yyyy" /></td>                  
                </tr>
            </c:forEach>  
        </table>

    </c:if>
</div>                 
