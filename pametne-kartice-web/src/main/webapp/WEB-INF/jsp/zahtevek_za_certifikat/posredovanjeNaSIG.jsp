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


<div class="container" style="text-align:center;">
    <h3>Pregled zahtevkov za posredovanje na SIGOV-CA</h3>
    <div class="row">       
        <c:if test='${not empty uspesno && uspesno!=0}'>
            <div id="success" class="success">          
               Število zahtevkov, ki so bili posredovani na SIGOV-CA:${uspesno}<br>           
            </div><br>
        </c:if>
        <c:if test='${not empty neuspesno && neuspesno!=0}'>
            <div id="error" class="error">          
                Število zahtevkov, ki še niso bili izvoženi:${neuspesno}<br>           
            </div><br>
        </c:if> 
       
    </div>
</div>

        <div class="container">         
            <table class="table">
                <c:set var="j" value="1" scope="page" />
                <sec:authorize access="hasAnyRole('002,006')">
                    <c:if test="${fn:length(zahtevki)>0}">
                        <tr>
                            <td>&nbsp;</td>                          
                            <td style="text-align:right;">
                                <a id="izvoziVsePotrjene" href="${pageContext.request.contextPath}/izvozVse"><button class="btn" style="font-size: 12px;">Izvozi vse potrjene zahtevke</button></a>                      
                                <a id="posredujNaSigovca" href="${pageContext.request.contextPath}/posredovanoNaSIG/process"><button class="btn" style="font-size: 12px;">Posreduj na SIGOV-CA</button> </a>
                                
                            </td>
                            <td>&nbsp;
                              
                            </td>

                        </tr>
                    </c:if>
                </sec:authorize>
                <c:forEach var="zahtevki" items="${zahtevki}">
                    <tr style="background-color:${zahtevki.status.barva}">  
                        <td style="text-align:left;"><strong>#${j+(pageNum-1)*prikazovNaStran}. Zahtevek: ${zahtevki.crtnaKoda}</strong></td>
                        <td>Sporočila:${fn:length(zahtevki.opombe)}</td>
                        <td style="text-align:left;"><a href="${pageContext.request.contextPath}/izpisPodrobno/${zahtevki.id}" onclick="showHideDiv('loadingDiv');"><button class="btn">Podrobno</button></a>
                        </td>
                    </tr>
                    <tr>
                        <td>Ime organizacije:</td>
                        <td>${zahtevki.imeOrganizacije}</td>

                    </tr>
                    <tr>
                        <td>Status zahtevka: </td>
                        <td> ${zahtevki.status.opis} </td>
                    </tr>
                    <tr>
                        <td>Število imetnikov:</td>
                        <td>${fn:length(zahtevki.imetniki)}</td>
                    </tr>
                    <c:set var="j" value="${j + 1}" scope="page"/>
                 
                </c:forEach>
            </table>
        </div>
    






