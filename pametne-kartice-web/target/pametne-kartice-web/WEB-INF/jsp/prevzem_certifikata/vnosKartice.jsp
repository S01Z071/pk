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
            <h4>Izbira datoteke s podatki iz kartice</h4><br><br>
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
                Uspešno vnešenih kartic:${uspesno}<br>           
            </div><br>
        </c:if>
        <c:if test='${not empty karticaZe && karticaZe!=0}'>
            <div class="error">          
                Število kartic, ki so že v bazi:${karticaZe}<br>           
            </div><br>
        </c:if>
        <c:if test='${not empty imetnikNi && imetnikNi!=0}'>
            <div class="error">          
                Število imetnikov, ki jih ni v bazi:${imetnikNi}<br>           
            </div><br>
        </c:if>
        <c:if test='${not empty certfNe && certfNe!=0}'>
            <div class="error">
                število certifikatov, ki jih ni v bazi:${certfNe}<br>
            </div><br>
        </c:if>
         <c:if test='${not empty avtorStNapaka && avtorStNapaka!=0}'>
            <div class="error">
                število kartic pri katerih se ne ujema avtorizacijska številka in user geslo:${avtorStNapaka}<br>
            </div><br>
        </c:if>   
            

    </div>
    <br>      


</div>
<div class="container">
    <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/vnosKart/process">   
        <div class="row">
            <div class="span7">
                Datoteka:  <input id="browseClick" type="file" name="myfile"/><br/>

            </div>
        </div>
        <br>
        <div class="row">
            <div class="span3">
                <button id="naloziPodatke" class="btn" type="submit">Naloži</button>
            </div>
        </div>

    </form>
</div>  
<c:if test="${fn:length(seznamKarticInCertifikatov.karticaInCertifikat)>0}">
    <table class="table" style="font-size: 12px;">
        <tr>
            <td>Serijska št. kartice</td>
            <td>Pkcs11</td>
            <td>Admin geslo</td>           
            <td>User geslo</td>
            <td>Pin3</td>
            <td>Datum</td>    
            <td>CN certifikata</td>
            <td>Serijska št. certifikata</td>
            <td>E-naslov</td>
            <td>Datum prevzema</td>
            <td>Datum poteka</td>
            <td>Rfid1</td>
            <td>Rfid2</td>
            <td>Črtna koda</td> 
            <td>X509</td>
        </tr>

        <sf:form modelAttribute="seznamKarticInCertifikatov" action="${pageContext.request.contextPath}/vnosKart/process/confirm" id="seznamKarticInCertifikatov">
            <c:forEach var="karticaInCertifikat" items="${seznamKarticInCertifikatov.karticaInCertifikat}" varStatus="i">                           
                <tr>
                    <c:if test="${fn:length(karticaInCertifikat.kartica.serijskaStevilkaKartice) > 15}">
                        <td onmouseover="mouseOverDisplaySpan('spanS${i.index}');" onmouseout="mouseOutDisplaySpan('spanS${i.index}')">
                            <span id="spanS${i.index}" style="display:none; position: absolute; background-color:beige; z-index: 999; cursor: help; border-width: 1px; border-style: solid;">
                                ${karticaInCertifikat.kartica.serijskaStevilkaKartice}
                            </span> 
                            ${fn:substring(karticaInCertifikat.kartica.serijskaStevilkaKartice, 0, 15)}...   
                        </c:if>
                        <c:if test="${fn:length(karticaInCertifikat.kartica.serijskaStevilkaKartice) <= 15}"> 
                        <td>
                            ${karticaInCertifikat.kartica.serijskaStevilkaKartice}
                        </c:if>
                    </td>   
                                        
                    <td>${karticaInCertifikat.kartica.pkcs11}</td>   
                    <c:if test="${fn:length(karticaInCertifikat.kartica.adminPass) > 6}">
                        <td onmouseover="mouseOverDisplaySpan('span${i.index}');" onmouseout="mouseOutDisplaySpan('span${i.index}')">
                            <span id="span${i.index}" style="display:none; position: absolute; background-color:beige; z-index: 999; cursor: help; border-width: 1px; border-style: solid;">
                                ${karticaInCertifikat.kartica.adminPass}
                            </span> 
                            ${fn:substring(karticaInCertifikat.kartica.adminPass, 0, 6)}...   
                        </c:if>
                        <c:if test="${fn:length(karticaInCertifikat.kartica.adminPass) <= 6}"> 
                        <td>
                            ${karticaInCertifikat.kartica.adminPass}
                        </c:if>
                    </td>   
                    <td>${karticaInCertifikat.kartica.userPass}</td>   
                    <td>${karticaInCertifikat.kartica.pin3}</td>   
                    <td> <fmt:formatDate value="${karticaInCertifikat.kartica.datumInit}" pattern="dd.MM.yyyy HH:mm:ss" /></td>
                    <td>${karticaInCertifikat.certifikat.imeInPriimek}</td>  
                    <td>${karticaInCertifikat.certifikat.serijskaStevilka}</td>  
                    <td>${karticaInCertifikat.certifikat.ENaslov}</td>                  
                    <td> <fmt:formatDate value="${karticaInCertifikat.certifikat.datumPrevzema}" pattern="dd.MM.yyyy" /></td>
                    <td> <fmt:formatDate value="${karticaInCertifikat.certifikat.datumPoteka}" pattern="dd.MM.yyyy" /></td>
                    <td>${karticaInCertifikat.kartica.rfid1}</td>   
                    <td>${karticaInCertifikat.kartica.rfid2}</td>   
                    <td>${karticaInCertifikat.kartica.barcode}</td>   
                    <td>${karticaInCertifikat.certifikat.serialNumberX}</td>
                    <td>
                        <sf:hidden path="karticaInCertifikat[${i.index}].kartica.serijskaStevilkaKartice"/>
                        <sf:hidden path="karticaInCertifikat[${i.index}].kartica.pkcs11"/>
                        <sf:hidden path="karticaInCertifikat[${i.index}].kartica.adminPass"/>
                        <sf:hidden path="karticaInCertifikat[${i.index}].kartica.userPass"/>
                        <sf:hidden path="karticaInCertifikat[${i.index}].kartica.pin3"/>
                        <sf:hidden path="karticaInCertifikat[${i.index}].kartica.datumInit"/>
                        <sf:hidden path="karticaInCertifikat[${i.index}].kartica.rfid1"/>
                        <sf:hidden path="karticaInCertifikat[${i.index}].kartica.rfid2"/>
                        <sf:hidden path="karticaInCertifikat[${i.index}].kartica.barcode"/>
                        <sf:hidden path="karticaInCertifikat[${i.index}].certifikat.imeInPriimek"/>
                        <sf:hidden path="karticaInCertifikat[${i.index}].certifikat.serijskaStevilka"/>
                        <sf:hidden path="karticaInCertifikat[${i.index}].certifikat.ENaslov"/>
                        <sf:hidden path="karticaInCertifikat[${i.index}].certifikat.datumPrevzema"/>
                        <sf:hidden path="karticaInCertifikat[${i.index}].certifikat.datumPoteka"/>
                        <sf:hidden path="karticaInCertifikat[${i.index}].certifikat.serialNumberX"/>
                    </td>
                    
                </tr>
            </c:forEach>

            <tr>                        
                <td colspan="14" style="text-align: center;">
                    <input id="uvoziPodatke" class="btn btn-primary" style="width:100px;" type="submit" value="Uvozi" onclick="showHideDiv('loadingDiv');"/>
                </td>
            </tr>
        </sf:form>
    </table>
</c:if>
