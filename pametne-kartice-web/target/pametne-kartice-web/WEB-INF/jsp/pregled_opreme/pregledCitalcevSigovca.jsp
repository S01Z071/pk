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
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div class="container" style="text-align: center;">
    <div class="row">
        <div class="span10">
            <h4>Pregled čitalcev izdanih od SIGOV-CA</h4><br><br>
        </div>
    </div>

    <div class="row">
        <div class="span6">
            <sec:authorize access="hasAnyRole('002,005,006')">

                <form method="POST" name="iskanje" action="${pageContext.request.contextPath}/AdminIskanjeCitSig/">
                    <table>
                        <tr>
                            <td><strong>Šifra sodišča/oznaka/ime in priimek:</strong></td>
                            <td><input type="text" name="sif" id="iskano"/></td>
                            <td><input class="btn" type="submit" value="Išči"/></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <div style="float: left; padding-left:10%;"><input type="checkbox" name="vrnjeno" id="vrnjeno" value="1"/> Vrnjeno</div>
                                <div style="float:right; padding-right:10%;"> <input type="checkbox" name="izdano" id="izdano" value="1"/> Izdano</div>
                            </td>
                            <td> </td>
                        </tr>
                    </table>
                </form>

                <script>
                    if ("${sessionScope.sodisceSifra}") {
                        document.getElementById("iskano").value = "${sessionScope.sodisceSifra}";
                    }
                    if (("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "002" || "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "005" || "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "006") &&  "${vrnjeno}" == "true") {
                        document.getElementById("vrnjeno").checked = true;
                    }
                    if (("${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "002" || "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "005" || "${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id}" == "006") &&  "${izdano}" == "true") {
                        document.getElementById("izdano").checked = true;
                    }
                </script>

            </div>
        </div>

    </sec:authorize>
</div>  

<c:if test="${fn:length(seznamCitalcevSigovca)>0}">
    <table class="table">
        <tr>
            <td colspan="6">
                <strong>Skupaj: ${skupajCitalcev} </strong>
            </td>
        </tr>          
        <tr style="background-color:gainsboro;">    
            <td>Ime in priimek: ${seznamCitalcevSigovca.key}</td>                 
            <td>Ime organizacije</td>
            <td>Tip</td>
            <td>Oznaka</td>
            <td>Datum izdaje</td>
            <sec:authorize access="hasAnyRole('002,005,006')">
                <td>Datum vrnitve</td>
            </sec:authorize>
        </tr>
        <c:forEach var="seznamCitalcevSigovca" items="${seznamCitalcevSigovca}" varStatus="i">
            <c:forEach var="citalci" items="${seznamCitalcevSigovca.value}" varStatus="j">
                <c:choose> 
                    <c:when test="${i.index%2==0}">
                        <tr id="${i.index}.${j.index}" class="${i.index}" style="background-color:white;">
                        </c:when>
                        <c:otherwise>
                        <tr id="${i.index}.${j.index}" class="${i.index}" style="background-color:#f7f6f4;">     
                        </c:otherwise>  
                    </c:choose>
                    <c:if test="${j.index==0}">
                        <td style="font-weight: bold;">
                              <c:if test="${fn:length(seznamCitalcevSigovca.value)>1}"><a id="expandCollapseSign${i.index}" onclick="expandCollapse('${i.index}');" style="cursor: pointer;">-</a></c:if> 
                            <c:if test="${fn:length(seznamCitalcevSigovca.value)<=1}"><a></a></c:if> 
                          
                            ${seznamCitalcevSigovca.key}</td>
                    </c:if>
                    <c:if test="${j.index!=0}">
                        <td></td>
                    </c:if>
                    <td>${citalci.imeOrganizacije}</td>
                    <td>${citalci.tip}</td>
                    <td>${citalci.oznaka}</td>
                    <td><fmt:formatDate value="${citalci.datumIzdaje}" pattern="dd.MM.yyyy" /></td>
                    <sec:authorize access="hasAnyRole('002,005,006')">
                        <td><fmt:formatDate value="${citalci.datumVrnitve}" pattern="dd.MM.yyyy" /></td>
                    </sec:authorize>
                </tr>
            </c:forEach>

        </c:forEach>  
    </table>
</c:if>
