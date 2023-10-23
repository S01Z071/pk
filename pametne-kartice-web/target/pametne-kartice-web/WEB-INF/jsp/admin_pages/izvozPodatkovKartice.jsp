<%-- 
    Document   : iskanje
    Created on : 29.8.2013, 11:43:08
    Author     : uporabnik
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<div class="container" style="text-align: center;">
    <div class="row">
        <div class="span10">
            <h4>Izvoz podatkov o karticah</h4><br><br>
        </div>        
    </div> 
    <div class="row">
        <div id="error" class="span8 error">
            ${error}
            <c:out value="${sessionScope.izvoziNeizvozeneMsg}"/>   
        </div>
    </div>
    <br>

    <div class="row">            
        <div style="padding-left:250px;" class="span8">     
            <form action="${pageContext.request.contextPath}/izvozPodatkovKartice/prikaziNeizvozene" method="POST">
                <input style="margin-right:250px;" class="btn btn-primary" id="izvoziNeizvozene" type="submit" class="btn" value="Prikaži vse neizvožene"  />
            </form>
        </div>

    </div>

    <div class="row">            
        <div style="padding-left:250px;" class="span8">                              
            <form method="GET" action="${pageContext.request.contextPath}/izvozPodatkovKartice/iskanje/">   
                <div style="float:left;" id="datepicker-container">   
                    Datum inicializacije od:
                    <input autocomplete="off" style="width:90px;" type="text" id="datumOd" name="datumOd"/>
                    do:
                    <input autocomplete="off"  style="width:90px;" type="text" id="datumDo" name="datumDo"/>
                </div>   
                <input style="margin-right:250px;" id="potrdi" type="submit" class="btn" value="Potrdi"  />
            </form>
        </div>

    </div>           
    <c:if test="${fn:length(certifikati)>0}">
        <table class="table">
            <tr>
                <td>#</td>
                <td>Ime organizacije</td>
                <td>Ime in priimek</td>
                <td>Naslov</td>         
                <td>Serijska št. certifikata</td>
                <td>E-naslov</td>
                <td>Serijska št. kartice</td>
                <td>Črtna koda kartice</td>
                <td>Datum inicializacije</td>
            </tr>     
            <c:forEach var="certifikati" items="${certifikati}" varStatus="i">   
                <c:if test='${not empty certifikati.imetnik}'>
                    <tr style="background-color:#62af81;">
                    </c:if>
                    <c:if test='${empty certifikati.imetnik}'>
                    <tr style="background-color:#91a870;">
                    </c:if>
                    <td>${i.index+1}</td>
                    <td>${certifikati.imeOrganizacije}</td>
                    <td>${certifikati.imeInPriimek}</td>                            
                    <td>${certifikati.naslov} ${certifikati.posta} ${certifikati.kraj} ${certifikati.drzava}</td>

                    <td>${certifikati.serijskaStevilka}</td>
                    <td>${certifikati.ENaslov}</td>
                    <td>${certifikati.kartica.serijskaStevilkaKartice}</td>
                    <td>${certifikati.kartica.barcode}</td>
                    <td> <fmt:formatDate value="${certifikati.kartica.datumInit}" pattern="dd.MM.yyyy" /></td>

                    <td>  

                    </td>
                </tr>
            </c:forEach>

            <c:if test="${not empty neizvozeni}">              

                <form action="${pageContext.request.contextPath}/izvozPodatkovKartice/processNeizvozene" method="POST" onsubmit="document.getElementById('izvozNeizvozenih').disabled = true;">
                    <tr>                        
                        <td colspan="14" style="text-align: center;">
                            <input id="izvozNeizvozenih" class="btn btn-primary" style="width:250px;" type="submit" value="Izvozi in označi kot izvožene" />
                        </td>
                    </tr>
                </form>
            </c:if>
            <c:if test="${empty neizvozeni}">
                <form action="${pageContext.request.contextPath}/izvozPodatkovKartice/process" method="POST">
                    <input type="hidden" id="datumOd1" name="datumOd" value="${datumOd}"/>
                    <input type="hidden" id="datumDo1" name="datumDo" value="${datumDo}"/>

                    <tr>                        
                        <td colspan="14" style="text-align: center;">
                            <input id="izvozi" class="btn btn-primary" style="width:100px;" type="submit" value="Izvozi"/>
                        </td>
                    </tr>
                </form>
            </c:if>





        </table>
    </c:if>



