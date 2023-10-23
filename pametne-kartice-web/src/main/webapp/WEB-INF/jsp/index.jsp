<%-- 
    Document   : index
    Created on : 9.8.2013, 10:21:54
    Author     : uporabnik
--%>

<%@page import="java.util.Date"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<br><br>



<sec:authorize access="hasAnyRole('001,002,005,006')">   
    <c:set var="size" value="9" scope="page" />

    <c:if test="${opombeSt < 10}">
        <c:set var="size" value="${opombeSt-1}" scope="page" />

    </c:if>

    <c:if test="${opombeSt != 0}">
        <div class="container" >  
            <table class="table">
                <tr>
                    <td colspan="4" style="text-align:center;"><strong>Zadnjih 10 sporočil</strong></td>
                </tr>
                <tr>
                    <td>#</td>
                    <td>Naslov</td>
                    <td>Sporočilo</td>
                    <td></td>
                </tr>

                <c:forEach var="num" begin="0" end='${size}' >

                    <tr>
                        <td>${num+1}</td>
                        <td style="color:${opombe[num].naslovBarva};">${opombe[num].naslov}<br><p class="muted">${opombe[num].uporabnikSodisce}: ${opombe[num].uporabnikIme} ${opombe[num].uporabnikPriimek}</p></td>
                        <td><div id="vsebina+${num}">${opombe[num].vsebina}</div><p class="muted"> <fmt:formatDate value="${opombe[num].datum}" pattern="dd.MM.yyyy" /></p></td>
                        <td> <a id="podrobno${opombe[num].id}" href="${pageContext.request.contextPath}/izpisPodrobno/${opombe[num].id}" onclick="showHideDiv('loadingDiv');"><button class="btn">Podrobnosti</button></a></td>
                    </tr>  
                    <script>
                            var te = document.getElementById("vsebina+${num}").innerHTML;
                            if (te.length > 70) {
                                te = te.substring(0, 70) + "...";
                            }
                            document.getElementById("vsebina+${num}").innerHTML = te;

                    </script>
                </c:forEach>
            </table>
        </div>
    </c:if>
</sec:authorize>
