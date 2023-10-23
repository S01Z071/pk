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
            <h4>Posodobitev preklicanih certifikatov</h4><br><br>
        </div>
    </div>
    <div class="row">
        <div class="error">
            ${error}                 
        </div>
    </div>
    <div class="row">
        <div class="span12" style="text-align:center;">
            <a href="${pageContext.request.contextPath}/updateCertificateJob/process" onclick="showHideDiv('loadingDiv'); disableById('test');">
            <button id="test" class="btn">Posodobi zdaj</button>
            </a>
        </div>
    </div>
</div>
<br>

<%-- STRANI --%>
<c:if test="${fn:length(jobLog)>0}">
    <div class="container" style="text-align:center;"> 
        <c:set var="zacetek" value="1" scope="page" />
        <c:set var="konec" value='${izpisCount}' scope="page" />
        <c:if test='${izpisCount>5}'>
            <c:set var="zacetek" value='${pageNum-2}' scope="page" />
            <c:set var="konec" value='${pageNum+2}' scope="page" />
            <c:if test='${zacetek<1}'>
                <c:set var="zacetek" value="1" scope="page" />
                <c:set var="konec" value="5" scope="page" />
            </c:if>
            <c:if test='${konec>izpisCount}'>
                <c:set var="zacetek" value='${izpisCount-4}' scope="page" />
                <c:set var="konec" value='${izpisCount}' scope="page" />
            </c:if>
        </c:if>
        <div style="font-size: 16px; font-weight: bold;">
            <c:choose>
                <c:when test="${pageNum!=1}">                       
                    <a class="btn" href="${pageContext.request.contextPath}/updateCertificateJob/1"><<&nbsp;</a> 
                    <a class="btn" href="${pageContext.request.contextPath}/updateCertificateJob/${pageNum-1}">Prejšnja&nbsp;</a> 
                </c:when>
                <c:otherwise>
                    <div class="btn disabled"> << </div>
                    <div class="btn disabled"> Prejšnja&nbsp; </div>
                </c:otherwise>
            </c:choose>  

            <c:forEach var="num" begin='${zacetek}' end='${konec}' >
                <c:if test="${pageNum ne num}">                         
                    <a class="btn" href="${pageContext.request.contextPath}/updateCertificateJob/${num}">${num}</a>                    
                </c:if>
                <c:if test="${pageNum == num}">
                    <div class="btn btn-info disabled"> ${num}</div>
                </c:if>
            </c:forEach>
            <c:choose>
                <c:when test="${pageNum!=izpisCount}">                        
                    <a class="btn" href="${pageContext.request.contextPath}/updateCertificateJob/${pageNum+1}">&nbsp;Naslednja</a> 
                    <a class="btn" href="${pageContext.request.contextPath}/updateCertificateJob/<fmt:formatNumber value="${izpisCount}" minFractionDigits="0" maxFractionDigits="1"/>">&nbsp;>></a> 
                </c:when>
                <c:otherwise>
                    <div class="btn disabled">&nbsp;Naslednja</div>
                    <div class="btn disabled">>></div>
                </c:otherwise>
            </c:choose>  
        </div>
    </div>
</c:if>
<%-- STRANI --%>


<br>
<div class="container">
    <c:if test='${fn:length(jobLog)>0}'>
        <table class="table">        
            <tr style="background-color:gainsboro;">
                <td>ID</td>              
                <td>Začetek</td>
                <td>Konec</td>           
                <td>Število posodobitev</td>
                <td>Status</td>  
                <td> </td>
            </tr>      
            <c:forEach var="jobLog" items="${jobLog}" varStatus="i">  
                <c:if test="${jobLog.status eq 'COMPLETED' or jobLog.status eq 'COMPLETED - ROCNO'}">
                    <tr style="background-color:#62af81;">
                    </c:if>
                    <c:if test="${jobLog.status eq 'STARTED'}">
                    <tr class="${i.index}" style="background-color:#d8ab63;">
                    </c:if>  
                    <c:if test="${jobLog.status eq 'FAILED'}">
                    <tr class="${i.index}" style="background-color:#b75f5f;">
                    </c:if>                    
                   
                    <td>${jobLog.id}</td>
                    <td><fmt:formatDate value="${jobLog.startDate}" pattern="dd.MM.yyyy HH:mm:ss" /></td>                            
                    <td><fmt:formatDate value="${jobLog.endDate}" pattern="dd.MM.yyyy HH:mm:ss" /></td>
                    <td>${jobLog.numberOfUpdates}</td>  
                    <td>${jobLog.status}</td>   
                    <td> </td>
                    <td><c:if test="${fn:length(jobLog.certifikati)>0}"><a id="expandCollapseSign${i.index}" onclick="expandCollapse('${i.index}');" style="cursor: pointer; color:#8e0004;">+</a></c:if></td>
                    
                </tr>    
                <c:if test="${fn:length(jobLog.certifikati)>0}">
                    <tr class="${i.index}" style="background-color:white; display:none; opacity:0.8;">
                        <td style="background-color: #dceaf4; border-color: #dceaf4;"></td>
                        <td>Ime in priimek</td>
                        <td>Datum prevzema</td>
                        <td>Datum preklica</td>                      
                        <td>Serijska št.</td>
                        <td>Črtna koda kartice</td>
                        <td>Stanje</td>
                    </tr>
                    <c:forEach var="certifikati" items="${jobLog.certifikati}">
                        <tr class="${i.index}" style="background-color:#f7f6f4; display:none; opacity:0.5;">
                            <td style="background-color: #dceaf4; border-color: #dceaf4;"></td>
                            <td>${certifikati.imeInPriimek}</td>
                            <td><fmt:formatDate value="${certifikati.datumPrevzema}" pattern="dd.MM.yyyy" /></td>
                            <td><fmt:formatDate value="${certifikati.datumPreklica}" pattern="dd.MM.yyyy" /></td>
                         
                            <td>${certifikati.serijskaStevilka}</td>
                            <td>${certifikati.kartica.barcode}</td>
                            <td>${certifikati.stanje}</td>
                        </tr>
                    </c:forEach>
                </c:if>
            </c:forEach>  
        </table>
    </c:if>
</div>
<br>
<%-- STRANI --%>
<c:if test="${fn:length(jobLog)>0}">
    <div class="container" style="text-align:center;"> 
        <c:set var="zacetek" value="1" scope="page" />
        <c:set var="konec" value='${izpisCount}' scope="page" />
        <c:if test='${izpisCount>5}'>
            <c:set var="zacetek" value='${pageNum-2}' scope="page" />
            <c:set var="konec" value='${pageNum+2}' scope="page" />
            <c:if test='${zacetek<1}'>
                <c:set var="zacetek" value="1" scope="page" />
                <c:set var="konec" value="5" scope="page" />
            </c:if>
            <c:if test='${konec>izpisCount}'>
                <c:set var="zacetek" value='${izpisCount-4}' scope="page" />
                <c:set var="konec" value='${izpisCount}' scope="page" />
            </c:if>
        </c:if>
        <div style="font-size: 16px; font-weight: bold;">
            <c:choose>
                <c:when test="${pageNum!=1}">                       
                    <a class="btn" href="${pageContext.request.contextPath}/updateCertificateJob/1"><<&nbsp;</a> 
                    <a class="btn" href="${pageContext.request.contextPath}/updateCertificateJob/${pageNum-1}">Prejšnja&nbsp;</a> 
                </c:when>
                <c:otherwise>
                    <div class="btn disabled"> << </div>
                    <div class="btn disabled"> Prejšnja&nbsp; </div>
                </c:otherwise>
            </c:choose>  

            <c:forEach var="num" begin='${zacetek}' end='${konec}' >
                <c:if test="${pageNum ne num}">                         
                    <a class="btn" href="${pageContext.request.contextPath}/updateCertificateJob/${num}">${num}</a>                    
                </c:if>
                <c:if test="${pageNum == num}">
                    <div class="btn btn-info disabled"> ${num}</div>
                </c:if>
            </c:forEach>
            <c:choose>
                <c:when test="${pageNum!=izpisCount}">                        
                    <a class="btn" href="${pageContext.request.contextPath}/updateCertificateJob/${pageNum+1}">&nbsp;Naslednja</a> 
                    <a class="btn" href="${pageContext.request.contextPath}/updateCertificateJob/<fmt:formatNumber value="${izpisCount}" minFractionDigits="0" maxFractionDigits="1"/>">&nbsp;>></a> 
                </c:when>
                <c:otherwise>
                    <div class="btn disabled">&nbsp;Naslednja</div>
                    <div class="btn disabled">>></div>
                </c:otherwise>
            </c:choose>  
        </div>
    </div>
</c:if>
<%-- STRANI --%>
<br>



