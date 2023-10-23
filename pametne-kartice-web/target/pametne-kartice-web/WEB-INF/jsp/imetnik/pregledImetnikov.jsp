<%-- 
    Document   : izpisPodatkov
    Created on : 13.8.2013, 8:46:23
    Author     : uporabnik
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="text-align:center;">
    <h3>Pregled imetnikov</h3>
</div>

<div class="container" style="text-align:center;">
    <div class="row">
        <div class="span1"></div>
        <div class="span9">
            <%-- <form action="${pageContext.request.contextPath}/pregledImetnikov/iskanje">     --%>
            <sec:authorize access="hasAnyRole('002,005,006')">
                <form id="adminForm" method="POST" action="${pageContext.request.contextPath}/pregledImetnikovAdmin/1">  
                </sec:authorize>
                <sec:authorize access="hasRole('001')">
                    <form method="POST" action="${pageContext.request.contextPath}/pregledImetnikov/1">  
                    </sec:authorize>
                    <table>
                        <tr>                  
                            <td>
                                <label>Črtna koda/Davčna št./E-naslov/EMŠO:</label>
                            </td>
                            <td>
                                <input type="text" id="iskano" name="iskano" value="${iskano}" autofocus="true"/> 
                            </td>
                            <td>
                                <input id="Isci" type="submit" class="btn" style="width:100px; margin-bottom: 10px;" value="Išči" onclick="showHideDiv('loadingDiv');"/>
                            </td>
                        </tr>
                    </table> 
                </form>
        </div>
    </div>
</div>


<div class="container" style="text-align:center;">
    <sec:authorize access="hasAnyRole('002,005,006')">
        <sf:form method="POST" commandName="AdminIskanjeStatusImetnik" modelAttribute="AdminIskanjeStatusImetnik" action="${pageContext.request.contextPath}/AdminConfirmIskanjeImetnik">
            <table class="table">
                <tr>
                    <td><strong>Status:</strong></td>                    
                    <td><sf:checkbox id="vPripravi" path="vPripravi"></sf:checkbox>V pripravi</td>
                    <td><sf:checkbox path="zakljucen"></sf:checkbox>Zaključen</td>
                    <td><sf:checkbox path="posredovanoNaCIF"></sf:checkbox>Posredovano na CIF</td>
                    <td><sf:checkbox path="izbrisano"></sf:checkbox>Izbrisano</td>
                    <td><sf:checkbox id="potrjeno" path="potrjeno"></sf:checkbox>Potrjeno</td>
                    <td><sf:checkbox path="zavrnjen"></sf:checkbox>Zavrnjeno</td>
                    <td><sf:checkbox path="posredovanoNaSIGOVCA"></sf:checkbox>Posredovano na SIGOV-CA</td>
                    <td><sf:checkbox path="karticaOdpremljena"></sf:checkbox>Kartica odpremljena</td>
                    <td><sf:checkbox path="karticaPrevzeta"></sf:checkbox>Kartica prevzeta</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td colspan="2"><sf:checkbox path="imetnikPrenesen"></sf:checkbox>Imetnik prenesen</td>
                    </tr>
                </table>  
            <sf:hidden name="iskano" value="${iskano}" path=""/>
            <sf:button name="Potrdi" class="btn">Potrdi</sf:button>

        </sf:form>
    </sec:authorize>
</div>               


<%-- STRANI --%>
<c:if test="${fn:length(imetniki)>0}">
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
                    <sec:authorize access="hasAnyRole('002,005,006')">
                        <%--  <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikovAdmin/1/?iskano=${iskano}"><<&nbsp;</a> 
                          <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikovAdmin/${pageNum-1}/?iskano=${iskano}">Prejšnja&nbsp;</a> 
                        --%>
                        <a class="btn" onclick="submitStrani('1');"><<</a>
                        <a class="btn" onclick="submitStrani('${pageNum-1}');">Prejšnja&nbsp;</a>
                    </sec:authorize>
                    <sec:authorize access="hasRole('001')">
                        <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikov/1/?iskano=${iskano}"><<&nbsp;</a> 
                        <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikov/${pageNum-1}/?iskano=${iskano}">Prejšnja&nbsp;</a> 

                    </sec:authorize>
                </c:when>
                <c:otherwise>
                    <div class="btn disabled"> << </div>
                    <div class="btn disabled"> Prejšnja&nbsp; </div>
                </c:otherwise>
            </c:choose>  

            <c:forEach var="num" begin='${zacetek}' end='${konec}' >
                <c:if test="${pageNum ne num}">
                    <sec:authorize access="hasAnyRole('002,005,006')">
                        <%--  <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikovAdmin/${num}/?iskano=${iskano}">${num}</a> --%>
                        <a class="btn" onclick="submitStrani('${num}');">${num}</a>
                    </sec:authorize>
                    <sec:authorize access="hasRole('001')">
                        <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikov/${num}/?iskano=${iskano}">${num}</a> 
                    </sec:authorize>
                </c:if>
                <c:if test="${pageNum == num}">
                    <div class="btn btn-info disabled"> ${num}</div>
                </c:if>
            </c:forEach>

            <c:choose>
                <c:when test="${pageNum!=izpisCount}">
                    <sec:authorize access="hasAnyRole('002,005,006')">
                        <%--  <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikovAdmin/${pageNum+1}/?iskano=${iskano}">&nbsp;Naslednja</a> --%>                      
                        <a class="btn" onclick="submitStrani('${pageNum+1}');">&nbsp;Naslednja</a>
                        <a class="btn" onclick="submitStrani(<fmt:formatNumber value="${izpisCount}" minFractionDigits="0" maxFractionDigits="1"/>);">&nbsp;>></a>

                    </sec:authorize>
                    <sec:authorize access="hasRole('001')">
                        <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikov/${pageNum+1}/?iskano=${iskano}">&nbsp;Naslednja</a> 
                        <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikov/<fmt:formatNumber value="${izpisCount}" minFractionDigits="0" maxFractionDigits="1"/>/?iskano=${iskano}">&nbsp;>></a> 
                    </sec:authorize>
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
    <br>
    <table class="table">
        <c:set var="j" value="1" scope="page" />
        <c:forEach var="imetniki" items="${imetniki}">
            <tr style="background-color:${imetniki.status.barvaSIm}">  
                <td style="text-align:left;">
                    <strong>${j+(pageNum-1)*prikazovNaStran}. ${imetniki.crtnaKoda}</strong>
                    <%-- ==================== --%> 
                    <div class="info" style="color:black;"><a style="color:black; cursor:pointer;" class="" onclick="getZadnjaSpremembaImetnik('${imetniki.id}');">Zadnja sprememba</a></div>
                    <span id="zadnjaSprememba${imetniki.id}" class="zadnjaSprememba" style="display: none; position: absolute; background-color: beige; z-index: 999; border-width: 1px; border-style: solid;">
                        <table>
                            <tr style='font-size:10px;'>
                                <td>Zadnja sprememba</td>
                                <td style='text-align:right;'><a style='cursor:pointer;' onclick="$('#zadnjaSprememba${imetniki.id}').hide(10);">X</a></td>
                            </tr>
                            <tr>
                                <td style='border-bottom: 1px solid black;'>Datum:</td>
                                <td id="datum${imetniki.id}"></td>

                            </tr>
                            <tr>
                                <td>Opis statusa:</td>
                                <td id="status${imetniki.id}"></td>
                            </tr>                          
                        </table>
                    </span> 
                    <script>
                                    function getZadnjaSpremembaImetnik(idI) {
                                        $(".zadnjaSprememba").hide(1000);

                                        $.ajax({
                                            url: '${pageContext.request.contextPath}/getZadnjaSpremembaImetnik',
                                            data: "idImetnik=" + idI,
                                            success: function(result) {
                                                var zs = $("#zadnjaSprememba" + idI);
                                                var datum = new Date(result.datumI);
                                                $("#datum" + idI).html(datum.getDate() + "." + (datum.getMonth() + 1) + "." + datum.getFullYear() + "&nbsp;&nbsp;" + datum.getHours() + ":" + datum.getMinutes() + ":" + datum.getSeconds());
                                                $("#status" + idI).html(result.opis);
                                                zs.show(10);
                                            },
                                            error: function(e) {
                                                alert("Prišlo je do napake:" + e);
                                            }
                                        });
                                    }
                    </script>
                    <%-- ==================== --%> 

                </td>
                <td></td>
                <td style="text-align:right;">
                    <a id="pregledPodrobno${imetniki.id}" href="${pageContext.request.contextPath}/pregledImetnikovPodrobno/${imetniki.id}" onclick="showHideDiv('loadingDiv');">
                        <button class="btn">
                            Podrobno
                        </button>
                    </a>
                </td>  
            </tr> 

            <tr>
                <td>Ime in priimek:</td>
                <td style='border-bottom: 1px solid black;'>${imetniki.ime} ${imetniki.priimek}</td>
                <td></td>
            </tr>
            <tr>
                <td>Status:</td>
                <td>${imetniki.status.opisSIm}</td>
                <td></td>
            </tr>
            <c:set var="j" value="${j + 1}" scope="page"/>

        </c:forEach>
    </table>
</div>
<br>
<%-- STRANI --%>
<c:if test="${fn:length(imetniki)>0}">
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
                    <sec:authorize access="hasAnyRole('002,005,006')">
                        <%--  <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikovAdmin/1/?iskano=${iskano}"><<&nbsp;</a> 
                          <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikovAdmin/${pageNum-1}/?iskano=${iskano}">Prejšnja&nbsp;</a> 
                        --%>
                        <a class="btn" onclick="submitStrani('1');"><<</a>
                        <a class="btn" onclick="submitStrani('${pageNum-1}');">Prejšnja&nbsp;</a>
                    </sec:authorize>
                    <sec:authorize access="hasRole('001')">
                        <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikov/1/?iskano=${iskano}"><<&nbsp;</a> 
                        <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikov/${pageNum-1}/?iskano=${iskano}">Prejšnja&nbsp;</a> 

                    </sec:authorize>
                </c:when>
                <c:otherwise>
                    <div class="btn disabled"> << </div>
                    <div class="btn disabled"> Prejšnja&nbsp; </div>
                </c:otherwise>
            </c:choose>  

            <c:forEach var="num" begin='${zacetek}' end='${konec}' >
                <c:if test="${pageNum ne num}">
                    <sec:authorize access="hasAnyRole('002,005,006')">
                        <%--  <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikovAdmin/${num}/?iskano=${iskano}">${num}</a> --%>
                        <a class="btn" onclick="submitStrani('${num}');">${num}</a>
                    </sec:authorize>
                    <sec:authorize access="hasRole('001')">
                        <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikov/${num}/?iskano=${iskano}">${num}</a> 
                    </sec:authorize>
                </c:if>
                <c:if test="${pageNum == num}">
                    <div class="btn btn-info disabled"> ${num}</div>
                </c:if>
            </c:forEach>

            <c:choose>
                <c:when test="${pageNum!=izpisCount}">
                    <sec:authorize access="hasAnyRole('002,005,006')">
                        <%--  <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikovAdmin/${pageNum+1}/?iskano=${iskano}">&nbsp;Naslednja</a> --%>                      
                        <a class="btn" onclick="submitStrani('${pageNum+1}');">&nbsp;Naslednja</a>
                        <a class="btn" onclick="submitStrani(<fmt:formatNumber value="${izpisCount}" minFractionDigits="0" maxFractionDigits="1"/>);">&nbsp;>></a>

                    </sec:authorize>
                    <sec:authorize access="hasRole('001')">
                        <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikov/${pageNum+1}/?iskano=${iskano}">&nbsp;Naslednja</a> 
                        <a class="btn" href="${pageContext.request.contextPath}/pregledImetnikov/<fmt:formatNumber value="${izpisCount}" minFractionDigits="0" maxFractionDigits="1"/>/?iskano=${iskano}">&nbsp;>></a> 
                    </sec:authorize>
                </c:when>
                <c:otherwise>
                    <div class="btn disabled">&nbsp;Naslednja</div>
                    <div class="btn disabled">>></div>
                </c:otherwise>
            </c:choose>  
        </div>
    </div>
</c:if>
<br><br>
<%-- STRANI --%>



<script>
                                    function submitStrani(pageNum) {
                                        var form = document.getElementById("adminForm");
                                        form.action = "${pageContext.request.contextPath}/pregledImetnikovAdmin/" + pageNum;
                                        form.submit();
                                    }
</script>