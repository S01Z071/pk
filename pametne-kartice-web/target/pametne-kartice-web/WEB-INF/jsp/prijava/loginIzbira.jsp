<%-- 
    Document   : loginIzbira
    Created on : 21.8.2013, 10:14:06
    Author     : uporabnik
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form method="POST" action="${pageContext.request.contextPath}/loginIzbira/process/">
    <div class="row"><div class="span5"></div></div>
    <div class="container" >  
        <div class="row">
            <div class="span5" style="text-align:center;">
                <h4>Izbira sodišč in vlog</h4>
            </div>
        </div>
        <div class="row">
            <div class="span2">
                <label>Izbira sodiča in vloge:</label>
            </div>
            <div class="span3">
                <select name="izbiranje" id="izbiranje">
                    <c:forEach var="seznam" items="${seznam}">
                        <option value="${seznam}">${seznam}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="span5" style="text-align:center;">
                <input id="izberi" type="submit" value="Izberi" class="btn"/>
            </div>
        </div>
    </div>
</form>



