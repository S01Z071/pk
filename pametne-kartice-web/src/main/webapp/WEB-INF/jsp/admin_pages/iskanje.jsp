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
            <h4>Iskanje zahtevkov in imetnikov po črtni kodi</h4><br><br>
        </div>        
    </div>  

    <div class="row">
        <div class="span3"></div>
        <div class="span19">
            <form action="${pageContext.request.contextPath}/iskanjeCK">
                <table>
                    <tr>
                        <td>Črtna koda:</td>
                        <td><input type="text" id="iskano" name="iskano" autofocus="true"></td>
                        <td><input id="Isci" type="submit" class="btn" style="width:100px; margin-bottom:10px;" value="Išči" onclick="showHideDiv('loadingDiv');"/></td>
                    </tr>
                </table>
            </form>
            <sec:authorize access="hasAnyRole('002,005,006')">
                <form id="adminForm" method="POST" action="${pageContext.request.contextPath}/pregledImetnikovAdmin/1">
                </sec:authorize>
                <sec:authorize access="hasRole('001')">
                    <form method="POST" action="${pageContext.request.contextPath}/pregledImetnikov/1">
                    </sec:authorize>
                    <table>
                        <tr>
                            <td>
                                Imetnik - Črtna koda/Davčna št./E-naslov/EMŠO:
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
    <div class="row">
        <div class="error" id="error">
            ${error}
        </div>
    </div>
</div>



