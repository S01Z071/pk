<%-- 
    Document   : dodajImetnik
    Created on : 13.8.2013, 14:37:36
    Author     : uporabnik
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<div style="padding:2px;">
    <a class="btn" href="${pageContext.request.contextPath}/Vloga/uredi/${idZ}">Nazaj</a>
</div>
<div class="container">

    <div class="row">
        <div class="span8" style="text-align:center;">
            <h3>Podatki o bodočem imetniku</h3>        
        </div>
    </div>   
    <br>


    <sf:form method="POST" modelAttribute="imetnik" commandName="imetnik" action="${pageContext.request.contextPath}/Vloga/imetnik/process/${idZ}">
        <sf:hidden path="crtnaKoda"/> 
        <sf:hidden path="id"/>
        <sf:hidden path="sifraOrganizacije"/>

        <br>
        <div class="row">          
            <div class="span1" style="width:130px;">
                <sf:label path="ime">Ime:</sf:label>
                </div> 
                <div class="span4">
                <sf:input id="ime" name="ime" path="ime" cssErrorClass="errorBorder" onkeydown="clearEmail('ENaslov');"/>
                <sf:errors path="ime" cssClass="error"/>
            </div>          
            <div class="span1" style="width:130px;">
                <sf:label path="priimek">Priimek:</sf:label>
                </div> 
                <div class="span4">
                <sf:input id="priimek" name="priimek" path="priimek" cssErrorClass="errorBorder" onkeydown="clearEmail('ENaslov');"/>
                <sf:errors path="priimek" cssClass="error"/>
            </div>             
        </div>
        <div class="row">          
            <div class="span1" style="width:130px;">
                <sf:label path="davcna">Davčna številka: <img style="width:15px;height:15px;" src="${pageContext.request.contextPath}/resources/question.png" title="Podatek je potreben za opravljanje večine storitev na elektronski način v skladu z nameni uporabe potrdila, kot je določeno v Politiki SIGOV-CA, ni pa obvezen.1&#013Za upravljanje e-opravil v informacijskem sistemu sodstva je ta podatek obvezen."/></sf:label>
                </div> 
                <div class="span4">
                <sf:input name="davcna" path="davcna" cssErrorClass="errorBorder"/>
                <sf:errors path="davcna" cssClass="error"/>
            </div>             
            <div class="span1" style="width:130px;">
                <sf:label path="emso">EMŠO:  <img style="width:15px;height:15px;" src="${pageContext.request.contextPath}/resources/question.png" title="Podatek je potreben za opravljanje večine storitev na elektronski način v skladu z nameni uporabe potrdila, kot je določeno v Politiki SIGOV-CA, ni pa obvezen"/></sf:label>
                </div> 
                <div class="span4">
                <sf:input name="emso" path="emso" cssErrorClass="errorBorder"/>
                <sf:errors path="emso" cssClass="error"/>
            </div>           
        </div>
        <div class="row">          
            <div class="span1" style="width:130px;">
                <sf:label path="ENaslov">E-naslov:</sf:label>
                </div> 
                <div class="span5">
                <sf:input id="ENaslov" name="ENaslov" path="ENaslov" cssErrorClass="errorBorder"/>   
                <div id="mailAvtoVnos" class="btn" style="margin-bottom:10px;" onclick="automaticEmail('ime', 'priimek', 'ENaslov');" title="Avtomatsko izpolni e-naslov iz imena in priimka."><img style="width:15px;height:15px;" src="${pageContext.request.contextPath}/resources/rebuild.png"/></div>
                    <sf:errors path="eNaslov" cssClass="error"/>              

            </div>             
        </div>
        <br>
        <div class="row">  
            <strong>Podatki o digitalnem potrdilu</strong>
        </div>
        <br>
        <div class="row">
            <div class="span2">
                <sf:label path="potrdilo">Želim pridobiti potrdilo:<img style="width:15px;height:15px;" src="${pageContext.request.contextPath}/resources/question.png" title="Trenutno omogočamo samo spletno potrdilo."/></sf:label>
                </div> 
                <div class="span2">
                <sf:radiobutton id="potrdilo" path="potrdilo" value="Spletno"/>Spletno
            </div>
        </div>
        <div class="row">
            <div class="span2"></div>
            <div class="span2">
                <sf:radiobutton path="potrdilo" value="Posebno" onclick="this.checked=false; document.getElementById('potrdilo').checked=true;"/>Posebno
            </div> 
        </div>
        <br>
        <div class="row">
            <div class="span2">
                <sf:label path="gesloZaPreklic">
                    Geslo za preklic potrdila:<img style="width:15px;height:15px;" src="${pageContext.request.contextPath}/resources/question.png" title="Geslo za preklic potrdila je namenjeno preklicu po telefonu v skladu s Politiko SIGOV-CA. Dolgo je lahko do dvajset (20) alfanumeričnih znakov, sprememba gesla pa je možna le osebno pri SIGOV-CA ali z zašifriranim in digitalno podpisanim zahtevkom z veljavnim potrdilom."/>
                    &nbsp; 
                </sf:label>
            </div>
            <div class="span4">
                <sf:input name="gesloZaPreklic" path="gesloZaPreklic" cssErrorClass="errorBorder"/>
                <sf:errors path="gesloZaPreklic" cssClass="error"/>
            </div>            
        </div>
        <br>
        <div class="row">
            <div class="span4">
                <sf:label path="potrdilo">
                    Želim pridobiti digitalno potrdilo na pametni kartici:
                    <img style="width:15px;height:15px;" src="${pageContext.request.contextPath}/resources/question.png" title="Evropska in slovenska zakonodaja določata, da potrdilo, prevzeto na varno sredstvo za elektronsko podpisovanje, omogoča izdelovanje elektronskega podpisa, ki je enakovreden lastnoročnemu podpisu. Spletna in posebna kvalificirana digitalna potrdila SIGOV-CA za zaposlene in splošne nazive se praviloma izdajo kot potrdila z obvezno uporabo pametnih kartic in so na podlagi odobrenega zahtevka prevzeta neposredno na imetnikovo pametno kartico. Izjemoma lahko bodoči imetnik zahteva drugače, če uporaba pametne kartice v njegovem okolju s tehničnega vidika ni mogoča."/>
                </sf:label>
            </div>                
        </div>
        <div class="row">
            <div class="span3"></div>
            <div class="span3">
                <sf:radiobutton id="potNaPametnikKartici" path="potNaPametnikKartici" value="DA"/>DA                    
            </div>

        </div>
        <div class="row">
            <div class="span3"></div>
            <div class="span4">
                <sf:radiobutton path="potNaPametnikKartici" value="NE" onclick="this.checked=false; document.getElementById('potNaPametnikKartici').checked=true;"/>NE
            </div>

        </div>

        <br><br>
        <div class="row">
            <div class="span4">
                Podatki o opremi SIGOV-CA:
            </div>        
        </div>
        <div class="row">
            <div class="span1">
            </div>
            <div class="span5">
                <sf:radiobutton path="imaCitalec" value="DA" />Imam čitalec SIGOV-CA
                <sf:errors path="imaCitalec" cssClass="error"/>
            </div>  
        </div>
        <div class="row">
            <div class="span1">
            </div>  
            <div class="span5">
                <sf:radiobutton id="imaCitalec" path="imaCitalec" value="NE"/>Ne potrebujem čitalca SIGOV-CA
            </div>  
        </div>
        <div class="row">
            <div class="span1">
            </div>  
            <div class="span5">
                <sf:checkbox path="imaPametnoKartico" value="DA" />Imam pametno kartico SIGOV-CA z vgrajenim čipom
                <sf:errors path="imaPametnoKartico" cssClass="error"/>
            </div>
        </div>


        <br>
        <div class="row">
            <div class="span2">
                Podatki o opremi VSRS: <img style="width:15px;height:15px;" src="${pageContext.request.contextPath}/resources/question.png" title="Za pridobitev čitalca se obrnite na vašega sistemskega inženirja."/>
            </div>        
        </div>
        <div class="row">
            <div class="span1">
            </div>
            <div class="span5">
                <sf:radiobutton path="imaCitalecVSRS" value="NE" />Potrebujem čitalec, ki naj ga zagotovi VSRS
            </div>  
        </div>
        <div class="row">
            <div class="span1">
            </div>  
            <div class="span5">
                <sf:radiobutton id="imaCitalecVSRS" path="imaCitalecVSRS" value="DA"/>Ne potrebujem čitalca, ker je vgrajen v tipkovnici
            </div>  
        </div>
        <div class="row">
            <div class="span1">
            </div>  
            <div class="span5">
                <sf:checkbox id="imaPametnoKarticoVSRS" path="imaPametnoKarticoVSRS" value="DA" />Imam pametno kartico VSRS z vgrajenim čipom
            </div>
        </div>



        <br>
        <div class="row">
            <div class="span9" style="text-align:center;">
                <input id="potrdi" class="btn btn-primary" style="width:100px;" type="submit" value="Potrdi" onclick="showHideDiv('loadingDiv');"/>
                <a class="btn" style="width:80px;" href="${pageContext.request.contextPath}/Vloga/uredi/${idZ}">Prekliči</a>
            </div>
        </div>
    </sf:form>
</div>



<script>
                    function toCapitalUpperString(niz) {
                        if (niz.length < 2) {
                            return niz;
                        }
                        var tabela = niz.split(" ");
                        var novNiz = "";
                        for (var i = 0; i < tabela.length; i++) {
                            novNiz += tabela[i].substr(0, 1).toUpperCase() + tabela[i].substr(1, tabela[i].length).toLowerCase() + " ";
                        }
                        return novNiz.trim();
                    }



</script>
