<%-- 
    Document   : dodeliCitalec
    Created on : 20.3.2014, 14:15:13
    Author     : Uporabnik
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="text-align:center;">
    <div class="span8">
        <h3>Dodeljevanje čitalcev</h3>
    </div>
</div>
<br>
<div class="container" style="text-align:center;">             
    <div class="row">
        <div class="span9 error">    
            ${info}
        </div>
    </div>
    <div class="row">

        <div class="span9">
            <form method="POST" action="${pageContext.request.contextPath}/dodelovanje/citalci/iskanje">
                <table style="float:left;">
                    <tr>
                        <td colspan="2"><h4>Čitalec</h4></td>
                    </tr>                         
                    <tr>                   
                        <td>
                            Oznaka/tip: <input type="text" id="iskanoC" name="iskanoC" value="${iskalniNizC}"/> 
                        </td>
                        <td>
                            <!--<input id="IsciC" type="submit" class="btn" style="margin-bottom: 10px;" value="Išči" onclick="showHideDiv('loadingDiv');"/>-->
                        </td>            
                    </tr>
                </table> 
                <table style="float:right; margin-right:17%;">
                    <tr>               
                        <td colspan="2"><h4>Imetnik</h4></td>
                    </tr>                         
                    <tr>            
                        <td>                           
                            E-naslov/Ime: <input type="text" id="iskanoI" name="iskanoI" value="${iskalniNizI}"/> 
                        </td>
                        <td>
                            <input id="IsciI" type="submit" class="btn" style="margin-bottom: 10px;" value="Išči" onclick="showHideDiv('loadingDiv');"/>
                        </td>
                    </tr>
                </table> 
            </form>
        </div>
    </div>
    <br>


    <sf:form id="form" method="POST" name="dodelitevOpreme" action="">
        <div style="display:none;">          
            <input type="number" id="idCitalci" name="idCitalci"/>
            <input type="number" id="idImetniki" name="idImetniki" />
        </div>
    </sf:form>

    <div class="row">

        <div class="span9">
            <c:if test="${fn:length(citalci)>0}">
                <table class="tabela" style="float:left;"> 
                    <tr>
                        <td>Oznaka</td>
                        <td>Tip</td>
                        <td style="width:50px;"></td>
                    </tr>
                    <c:forEach var="citalci" items="${citalci}">
                        <tr>               
                            <td>${citalci.oznaka}</td>
                            <td>${citalci.tip}</td>  
                            <td style="width:50px;"><input checked="true" type="radio" name="radioCitalci" onclick="changeCitalciId('${citalci.id}');"></td>
                        </tr>
                        <script>
                                document.getElementById("idCitalci").value = "${citalci.id}";
                        </script>
                    </c:forEach>
                </table>
            </c:if>
            <c:if test="${fn:length(imetniki)>0}">
                <table class="tabela" style="float:right;"> 
                    <tr>
                        <td style="width:50px;"></td>
                        <td>Ime in priimek</td>
                        <td></td>
                    </tr>
                    <c:forEach var="imetniki" items="${imetniki}">
                        <tr>      
                            <td style="width:50px;"><input checked="true" type="radio" name="radioImetnik" id="" onclick="changeImetnikiId('${imetniki.id}');"></td>
                            <td>${imetniki.ime} ${imetniki.priimek}</td>
                            <td style="text-align:right;">
                                <a target="_blank" id="pregledPodrobno${imetniki.id}" href="${pageContext.request.contextPath}/pregledImetnikovPodrobno/${imetniki.id}">
                                    <button class="btn">
                                        Podrobno
                                    </button>
                                </a>
                            </td> 
                        <script>
                                document.getElementById("idImetniki").value = "${imetniki.id}";
                        </script>
                        </tr>
                    </c:forEach>
                </table><br>
            </c:if>
            <script>
                function changeImetnikiId(id) {
                    document.getElementById("idImetniki").value = id;
                }
                function changeCitalciId(id) {
                    document.getElementById("idCitalci").value = id;
                }
            </script>

        </div>
    </div> 
    <br><br>
    <div class="row">
        <div class="span9">
            <c:if test="${fn:length(imetniki)>0 && fn:length(citalci)>0}">
                <button class="btn btn-primary" onclick="submitForm();">Dodeli</button>
            </c:if>
        </div>
    </div>
    <script>
                function submitForm() {
                    var forma = document.getElementById("form");
                    forma.setAttribute("action", "${pageContext.request.contextPath}/dodelovanje/citalci/dodeli");
                    forma.submit();
                }
    </script>
</div>

