<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
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

<script type="text/javascript">


    jQuery(document).ready(function($) {
        $('#tabs').tab();
    });

    if ("${tabStatus}" == 1) {
        $(function() {
            $('#tabs li:eq(0) a').tab('show');
        });
    } else {
        $(function() {
            $('#tabs li:eq(1) a').tab('show');
        });
    }

</script>




<div class="container" style="text-align:center;">
    <h3>Pregled zahtevkov</h3>
</div>
<div class="container" style="text-align:center; width:75%;">
    <ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
        <li id="zahtevkiTab" class="active"><a href="#zahtevki" data-toggle="tab">Pregled zahtevkov za pridobitev certifikata</a></li>
        <!--li id="zahtevkiPTab" class=""><a href="#zahtevkiP" data-toggle="tab">Pregled zahtevkov za preklic certifikata</a></li-->     
        <li id="zahtevkiKodaTab" class=""><a href="#zahtevkiKoda" data-toggle="tab">Pregled zahtevkov za pridobitev kode za odklepanje kartice</a></li>  
        <li id="zahtevkiPreklicTab" class=""><a href="${pageContext.request.contextPath}/IzpisPodatkovPreklic/1">Pregled zahtevkov za preklic digitalnega potrdila</a></li>  
        <li id="zahtevkiPrenosTab" class=""><a href="${pageContext.request.contextPath}/izpisPodatkovPrenos/1">Pregled zahtevkov za prenos digitalnega potrdila</a></li>  

    </ul>
</div>

<div id="my-tab-content" class="tab-content">
    <div class="tab-pane active" id="zahtevki">
        <div class="container" style="text-align:center;">
            <sec:authorize access="hasAnyRole('002,005,006')">
                <sf:form method="POST" commandName="adminIskanjeStatus" modelAttribute="adminIskanjeStatus" action="${pageContext.request.contextPath}/AdminConfirmIskanje/${pageNumKoda}/1">
                    <table class="table">
                        <tr>
                            <td><strong>Status zahtevka:</strong></td>                    
                            <td><sf:checkbox id="vPripravi" path="vPripravi"></sf:checkbox>V pripravi</td>
                            <td><sf:checkbox path="zakljucen"></sf:checkbox>Zaključen</td>
                            <td><sf:checkbox path="posredovanoNaCIF"></sf:checkbox>Posredovano na CIF</td>
                            <td><sf:checkbox path="izbrisano"></sf:checkbox>Izbrisano</td>
                            <td><sf:checkbox id="potrjeno" path="potrjeno"></sf:checkbox>Potrjeno</td>
                            <td><sf:checkbox path="zavrnjen"></sf:checkbox>Zavrnjeno</td>
                            <td><sf:checkbox path="posredovanoNaSIGOVCA"></sf:checkbox>Posredovano na SIGOV-CA</td>
                            <td><sf:checkbox path="dokoncano"></sf:checkbox>Dokončano</td>
                            </tr>
                        </table>
                    <sf:button name="Potrdi" class="btn">Potrdi</sf:button>
                </sf:form>
            </sec:authorize>
        </div>



        <%-- STRANI --%>
        <c:if test="${fn:length(zahtevki)>0}">
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
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/1/1/${pageNumKoda}"><<&nbsp;</a> 
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/${pageNum-1}/${pageNumKoda}/1">Prejšnja&nbsp;</a> 

                            </sec:authorize>
                            <sec:authorize access="hasRole('001')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/1/${pageNumKoda}/1"><<&nbsp;</a> 
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/${pageNum-1}/${pageNumKoda}/1">Prejšnja&nbsp;</a> 

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
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/${num}/${pageNumKoda}/1">${num}</a> 
                            </sec:authorize>
                            <sec:authorize access="hasRole('001')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/${num}/${pageNumKoda}/1">${num}</a> 
                            </sec:authorize>
                        </c:if>
                        <c:if test="${pageNum == num}">
                            <div class="btn btn-info disabled"> ${num}</div>
                        </c:if>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${pageNum!=izpisCount}">
                            <sec:authorize access="hasAnyRole('002,005,006')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/${pageNum+1}/${pageNumKoda}/1">&nbsp;Naslednja</a> 
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/<fmt:formatNumber value="${izpisCount}" minFractionDigits="0" maxFractionDigits="1"/>/${pageNumKoda}/1">&nbsp;>></a> 
                            </sec:authorize>
                            <sec:authorize access="hasRole('001')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/${pageNum+1}/${pageNumKoda}/1">&nbsp;Naslednja</a> 
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/<fmt:formatNumber value="${izpisCount}" minFractionDigits="0" maxFractionDigits="1"/>/${pageNumKoda}/1">&nbsp;>></a> 
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

            <c:set var="j" value="1" scope="page" />
            <table class="table">
                <c:forEach var="zahtevki" items="${zahtevki}">
                    <tr style="background-color:${zahtevki.status.barva}">  
                        <td style="text-align:left;"><strong>#${j+(pageNum-1)*prikazovNaStran}. Zahtevek: ${zahtevki.crtnaKoda}</strong>
                            <div class="info" style="color:black;"><a style="color:black; cursor:pointer;" class="" onclick="getZadnjaSprememba('${zahtevki.id}');">Zadnja sprememba</a></div>
                            <span id="zadnjaSprememba${zahtevki.id}" class="zadnjaSprememba" style="display: none; position: absolute; background-color: beige; z-index: 999; border-width: 1px; border-style: solid;">
                                <table>
                                    <tr style='font-size:10px;'>
                                        <td>Zadnja sprememba</td>
                                        <td style='text-align:right;'><a style='cursor:pointer;' onclick="$('#zadnjaSprememba${zahtevki.id}').hide(10);">X</a></td>
                                    </tr>
                                    <tr>
                                        <td>Datum:</td>
                                        <td id="datum${zahtevki.id}"></td>
                                    </tr>
                                    <tr>
                                        <td>Opis statusa:</td>
                                        <td id="status${zahtevki.id}"></td>
                                    </tr>

                                </table>
                            </span>         
                        </td>
                    <script>
    function getZadnjaSprememba(idZ) {
        $(".zadnjaSprememba").hide(1000);

        $.ajax({
            url: '${pageContext.request.contextPath}/getZadnjaSprememba',
            data: "idZahtevka=" + idZ,
            success: function(result) {
                var zs = $("#zadnjaSprememba" + idZ);
                var datum = new Date(result.datum);
                $("#datum" + idZ).html(datum.getDate() + "." + (datum.getMonth() + 1) + "." + datum.getFullYear() + "&nbsp;&nbsp;" + datum.getHours() + ":" + datum.getMinutes() + ":" + datum.getSeconds());
                $("#status" + idZ).html(result.opis);
                zs.show(10);
            },
            error: function(e) {
                alert("Prišlo je do napake:" + e);
            }
        });
    }
                    </script>

                    <td>Sporočila:${fn:length(zahtevki.opombe)}</td>
                    <td style="text-align:left;">                          
                        <a id="zahtevekPodrobno${zahtevki.id}" href="${pageContext.request.contextPath}/izpisPodrobno/${zahtevki.id}" onclick="showHideDiv('loadingDiv');"><button class="btn">Podrobno</button></a>
                    </td>

                    </tr>
                    <tr>
                        <td>Ime organizacije:</td>
                        <td>${zahtevki.imeOrganizacije}</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Status zahtevka: </td>
                        <td> ${zahtevki.status.opis} </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Število imetnikov:</td>
                        <td>${fn:length(zahtevki.imetniki)}</td>
                        <td></td>
                    </tr>                   
                    <c:set var="j" value="${j + 1}" scope="page"/>   

                </c:forEach>
            </table>
        </div>
        <br>

        <%-- STRANI --%>
        <c:if test="${fn:length(zahtevki)>0}">
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
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/1/1/${pageNumKoda}"><<&nbsp;</a> 
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/${pageNum-1}/${pageNumKoda}/1">Prejšnja&nbsp;</a> 

                            </sec:authorize>
                            <sec:authorize access="hasRole('001')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/1/${pageNumKoda}/1"><<&nbsp;</a> 
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/${pageNum-1}/${pageNumKoda}/1">Prejšnja&nbsp;</a> 

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
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/${num}/${pageNumKoda}/1">${num}</a> 
                            </sec:authorize>
                            <sec:authorize access="hasRole('001')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/${num}/${pageNumKoda}/1">${num}</a> 
                            </sec:authorize>
                        </c:if>
                        <c:if test="${pageNum == num}">
                            <div class="btn btn-info disabled"> ${num}</div>
                        </c:if>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${pageNum!=izpisCount}">
                            <sec:authorize access="hasAnyRole('002,005,006')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/${pageNum+1}/${pageNumKoda}/1">&nbsp;Naslednja</a> 
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/<fmt:formatNumber value="${izpisCount}" minFractionDigits="0" maxFractionDigits="1"/>/${pageNumKoda}/1">&nbsp;>></a> 
                            </sec:authorize>
                            <sec:authorize access="hasRole('001')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/${pageNum+1}/${pageNumKoda}/1">&nbsp;Naslednja</a> 
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/<fmt:formatNumber value="${izpisCount}" minFractionDigits="0" maxFractionDigits="1"/>/${pageNumKoda}/1">&nbsp;>></a> 
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
    </div>

    <%-- .............. --%>


    <div class="tab-pane" id="zahtevkiKoda">
        <%-- STRANI --%>
        <c:if test="${fn:length(zahtevekZaKodo)>0}">
            <div class="container" style="text-align:center;"> 
                <c:set var="zacetekKoda" value="1" scope="page" />
                <c:set var="konecKoda" value='${izpisCountKoda}' scope="page" />
                <c:if test='${izpisCountKoda>5}'>
                    <c:set var="zacetekKoda" value='${pageNumKoda-2}' scope="page" />
                    <c:set var="konecKoda" value='${pageNumKoda+2}' scope="page" />
                    <c:if test='${zacetekKoda<1}'>
                        <c:set var="zacetekKoda" value="1" scope="page" />
                        <c:set var="konecKoda" value="5" scope="page" />
                    </c:if>
                    <c:if test='${konecKoda>izpisCountKoda}'>
                        <c:set var="zacetekKoda" value='${izpisCountKoda-4}' scope="page" />
                        <c:set var="konecKoda" value='${izpisCountKoda}' scope="page" />
                    </c:if>
                </c:if>
                <div style="font-size: 16px; font-weight: bold;">
                    <c:choose>
                        <c:when test="${pageNumKoda!=1}">
                            <sec:authorize access="hasAnyRole('002,005,006')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/${pageNum}/1/2"><<&nbsp;</a> 
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/${pageNum}/${pageNumKoda-1}/2">Prejšnja&nbsp;</a> 

                            </sec:authorize>
                            <sec:authorize access="hasRole('001')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/${pageNum}/1/2"><<&nbsp;</a> 
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/${pageNum}/${pageNumKoda-1}/2">Prejšnja&nbsp;</a> 

                            </sec:authorize>
                        </c:when>
                        <c:otherwise>
                            <div class="btn disabled"> << </div>
                            <div class="btn disabled"> Prejšnja&nbsp; </div>
                        </c:otherwise>
                    </c:choose>  

                    <c:forEach var="numKoda" begin='${zacetekKoda}' end='${konecKoda}' >
                        <c:if test="${pageNumKoda ne numKoda}">
                            <sec:authorize access="hasAnyRole('002,005,006')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/${pageNum}/${numKoda}/2">${numKoda}</a> 
                            </sec:authorize>
                            <sec:authorize access="hasRole('001')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/${pageNum}/${numKoda}/2">${numKoda}</a> 
                            </sec:authorize>
                        </c:if>
                        <c:if test="${pageNumKoda == numKoda}">
                            <div class="btn btn-info disabled"> ${numKoda}</div>
                        </c:if>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${pageNumKoda!=izpisCountKoda}">
                            <sec:authorize access="hasAnyRole('002,005,006')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/${pageNum}/${pageNumKoda+1}/2">&nbsp;Naslednja</a> 
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/${pageNum}/<fmt:formatNumber value="${izpisCountKoda}" minFractionDigits="0" maxFractionDigits="1"/>/2">&nbsp;>></a> 
                            </sec:authorize>
                            <sec:authorize access="hasRole('001')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/${pageNum}/${pageNumKoda+1}/2">&nbsp;Naslednja</a> 
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/${pageNum}/<fmt:formatNumber value="${izpisCountKoda}" minFractionDigits="0" maxFractionDigits="1"/>/2">&nbsp;>></a> 
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
            <c:set var="j" value="1" scope="page" />
            <table class="table">
                <c:forEach var="zahtevekZaKodo" items="${zahtevekZaKodo}"> 
                    <tr style="background-color:${zahtevekZaKodo.status.barva}">  
                        <td style="text-align:left;"><strong>#${j+(pageNumKoda-1)*prikazovNaStran}. Zahtevek: ${zahtevekZaKodo.crtnaKoda}</strong></td>
                        <td></td>
                        <td style="text-align:left;"><a href="${pageContext.request.contextPath}/ZahtevekZaKodo/podrobno/${zahtevekZaKodo.id}" onclick="showHideDiv('loadingDiv');"><button class="btn">Podrobno</button></a>
                        </td>
                    </tr>
                    <tr>
                        <td>Ime organizacije:</td>
                        <td>${zahtevekZaKodo.imeOrganizacije}</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Status zahtevka: </td>
                        <td> ${zahtevekZaKodo.status.opis} </td>
                        <td></td>
                    </tr>                                   
                    <c:set var="j" value="${j + 1}" scope="page"/>   
                </c:forEach>
            </table>
        </div>
        <br>
        <%-- STRANI --%>
        <c:if test="${fn:length(zahtevekZaKodo)>0}">
            <div class="container" style="text-align:center;"> 
                <c:set var="zacetekKoda" value="1" scope="page" />
                <c:set var="konecKoda" value='${izpisCountKoda}' scope="page" />
                <c:if test='${izpisCountKoda>5}'>
                    <c:set var="zacetekKoda" value='${pageNumKoda-2}' scope="page" />
                    <c:set var="konecKoda" value='${pageNumKoda+2}' scope="page" />
                    <c:if test='${zacetekKoda<1}'>
                        <c:set var="zacetekKoda" value="1" scope="page" />
                        <c:set var="konecKoda" value="5" scope="page" />
                    </c:if>
                    <c:if test='${konecKoda>izpisCountKoda}'>
                        <c:set var="zacetekKoda" value='${izpisCountKoda-4}' scope="page" />
                        <c:set var="konecKoda" value='${izpisCountKoda}' scope="page" />
                    </c:if>
                </c:if>
                <div style="font-size: 16px; font-weight: bold;">
                    <c:choose>
                        <c:when test="${pageNumKoda!=1}">
                            <sec:authorize access="hasAnyRole('002,005,006')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/${pageNum}/1/2"><<&nbsp;</a> 
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/${pageNum}/${pageNumKoda-1}/2">Prejšnja&nbsp;</a> 

                            </sec:authorize>
                            <sec:authorize access="hasRole('001')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/${pageNum}/1/2"><<&nbsp;</a> 
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/${pageNum}/${pageNumKoda-1}/2">Prejšnja&nbsp;</a> 
                            </sec:authorize>
                        </c:when>
                        <c:otherwise>
                            <div class="btn disabled"> << </div>
                            <div class="btn disabled"> Prejšnja&nbsp; </div>
                        </c:otherwise>
                    </c:choose>  

                    <c:forEach var="numKoda" begin='${zacetekKoda}' end='${konecKoda}' >
                        <c:if test="${pageNumKoda ne numKoda}">
                            <sec:authorize access="hasAnyRole('002,005,006')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/${pageNum}/${numKoda}/2">${numKoda}</a> 
                            </sec:authorize>
                            <sec:authorize access="hasRole('001')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/${pageNum}/${numKoda}/2">${numKoda}</a> 
                            </sec:authorize>
                        </c:if>
                        <c:if test="${pageNumKoda == numKoda}">
                            <div class="btn btn-info disabled"> ${numKoda}</div>
                        </c:if>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${pageNumKoda!=izpisCountKoda}">
                            <sec:authorize access="hasAnyRole('002,005,006')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/${pageNum}/${pageNumKoda+1}/2">&nbsp;Naslednja</a> 
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/${pageNum}/<fmt:formatNumber value="${izpisCountKoda}" minFractionDigits="0" maxFractionDigits="1"/>/2">&nbsp;>></a> 
                            </sec:authorize>
                            <sec:authorize access="hasRole('001')">
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/${pageNum}/${pageNumKoda+1}/2">&nbsp;Naslednja</a> 
                                <a class="btn" href="${pageContext.request.contextPath}/izpisPodatkov/${pageNum}/<fmt:formatNumber value="${izpisCountKoda}" minFractionDigits="0" maxFractionDigits="1"/>/2">&nbsp;>></a> 
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
        <br>
        <%-- STRANI --%>

    </div>
</div>



