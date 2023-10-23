<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%-- 
    Document   : layout
    Created on : 9.8.2013, 10:21:11
    Author     : uporabnik
--%>
<!DOCTYPE html>
<html>
    <head>    
        <title><tiles:insertAttribute name="title" ignore="true" /></title>
      

        <link rel="stylesheet" href="<c:url value="/resources/style.css"/>" type="text/css" />  
        <link rel="stylesheet" href="<c:url value="/resources/bootstrap/bootstrap/css/bootstrap.css"/>" type="text/css" />   
        <%--
        <meta name="viewport" content="width=device-width, initial-scale=0.5">
        --%>
        <link rel="stylesheet" href="<c:url value="/resources/bootstrap/bootstrap/css/bootstrap-responsive.css"/>" type="text/css" />   

        <script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.js"></script>

        <script src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap/js/bootstrap.js"></script>

        <script src="${pageContext.request.contextPath}/resources/js/iskanje.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/izvoz.js"></script>

        <script src="${pageContext.request.contextPath}/resources/js/metode.js"></script>

        <script src="${pageContext.request.contextPath}/resources/js/povezovanje.js"></script>


        <script src="${pageContext.request.contextPath}/resources/datePicker/js/bootstrap-datepicker.js"></script>
        <link rel="stylesheet" href="<c:url value="/resources/datePicker/css/datepicker.css"/>" type="text/css" />   
        
      
    </head>
   
    <body>
        <div id="header">            
            <tiles:insertAttribute name="header"/>
        </div>
        <div class="row">
            <div class="span12"></div>
        </div>
        <div class="container-fluid" style="min-width: 1200px;">
            <br>
            <div class="row">
                <div class="span12" style="background-color:#bbd8e9;min-width:98%;min-height:54px; border-top-left-radius:20px; border-top-right-radius: 20px;">
                    <tiles:insertAttribute name="menu"/>                    
                </div>

            </div>
            <div class="row">
                <div class="span12" style="background-color:#dceaf4; min-height:500px;min-width:98%; border-bottom-left-radius: 20px; border-bottom-right-radius: 20px;">
                    <div id="loadingDiv" style="display:none; text-align: center;"><img src="${pageContext.request.contextPath}/resources/waiting.gif"/></div>  
                        <tiles:insertAttribute name="body"/>
                </div>   
            </div>
        </div>
        <div id="footer">
            <tiles:insertAttribute name="footer"/>
        </div>

        <script>
            $('#datepicker-container input').datepicker({
                format: "dd.mm.yyyy",
                weekStart: 1,
                language: "sl",
                forceParse: false,
                daysOfWeekDisabled: "0,6",
                autoclose: true,
                todayHighlight: true
            });
        </script>
    </body>
</html>
