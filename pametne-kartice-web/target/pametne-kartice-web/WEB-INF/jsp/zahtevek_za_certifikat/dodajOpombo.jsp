<%-- 
    Document   : dodajOpombo
    Created on : 23.8.2013, 8:52:30
    Author     : uporabnik
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<div class="container" style="text-align: center;">
    <div class="row">
        <div class="span10">
            <h4>Dodajanje sporočila</h4><br><br>
        </div>
    </div>
    <sf:form modelAttribute="opomba" action="${pageContext.request.contextPath}/Vloga/dodajOpombo/process/${idZ}">  
        <div class="row">
            <div class="span2">
                <sf:label path="naslov" name="naslov">Naslov:</sf:label>
                </div>
                <div class="span3">
                <sf:input path="naslov" name="naslov" maxlength="60" style="width:500px;" cssErrorClass="errorBorder"/>
                <sf:errors path="naslov" cssClass="error"/>           
            </div>
        </div>
        <div class="row">
            <div class="span2">
                Sporočilo:
            </div>
            <div class="span3">
                <sf:textarea path="vsebina" maxlength="255" style="width:500px;height:200px;" cssErrorClass="errorBorder"/>
                <sf:errors path="vsebina" cssClass="error"/>       
            </div>
        </div>
        <div class="row">
            <div class="span11" style="text-align:center;">
                <input id="potrdi" type="submit" value="Dodaj" class="btn" style="width:100px;"/>
                <a class="btn" href="${pageContext.request.contextPath}/izpisPodrobno/${idZ}">Prekliči</a>
            </div>
        </div>
    </sf:form>
</div>