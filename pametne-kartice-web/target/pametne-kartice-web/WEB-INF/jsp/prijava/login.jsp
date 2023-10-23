<%-- 
    Document   : userBody
    Created on : 10.11.2008, 8:40:15
    Author     : Andrej
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="container">
    <div class="row">
        <div class="span6" style="text-align:left;">
            <div class="errorLogin">
                <c:out value="${sessionScope.error}"/>
                <%request.getSession().getAttribute("error");%>
            </div> 
        </div>
    </div>
    <br>
    <div class="row" style="text-align:center;">
        <div class="span6"><strong>Prijava</strong></div>
    </div>
    <br>
    <s:url var="authUrl"
           value="/j_spring_security_check" />
    <sf:form method="post" action="${authUrl}" >
        <div class="row">
            <div class="span2"> <label for="username">Uporabniško ime:</label></div>
            <div class="span2"> <input id="username" name="j_username" type="text"></input></div>
        </div>
        <div class="row">
            <div class="span2">
                <label for="password">Geslo:</label>
            </div>
            <div class="span2">
                <input id="password"

                       name="j_password"

                       type="password" />
            </div>
        </div>
        <div class="row">
            <div class="span5" style="text-align:center;">
                <input id="prijava" name="commit" type="submit" value="Prijava" class="btn" />
            </div>
        </div>


    </sf:form>

    <script type="text/javascript">
        document.getElementById('username').focus();
    </script>
</div>
