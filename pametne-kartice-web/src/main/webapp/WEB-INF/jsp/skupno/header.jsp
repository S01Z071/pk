<%-- 
    Document   : header
    Created on : 9.8.2013, 10:21:23
    Author     : uporabnik
--%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<div id="row" style="padding-right:50px;">  

    <img src="${pageContext.request.contextPath}/resources/logotip_vsrs.gif" style="float:left;width:184px;height:140px;"/>


    <div class="" style="text-align:center;">
        <h3>Pametne kartice</h3>
        <Strong style="color:red;"><s:eval expression="@nastavitveHelper.okolje"></s:eval> </strong>           
        </div>        
</div>

