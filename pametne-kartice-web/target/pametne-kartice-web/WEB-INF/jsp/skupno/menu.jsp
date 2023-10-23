<%-- 
    Document   : menu
    Created on : 13.8.2013, 8:53:39
    Author     : uporabnik
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>




<c:if test="${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id == null}">    
    <a href="<s:eval expression="@nastavitveHelper.navodila"></s:eval>" target="_blank"> <img style="width:16px;height:16px; margin-top:10px; margin-right:10px; float:right;" src="${pageContext.request.contextPath}/resources/readme.png" title="Navodila za uporabo"/></a>
    </c:if>
    <c:if test="${sessionScope.uporabnik.izbranaVlogaSodisce.vloga.id != null}">    
    <p class="muted" style="float:right; padding-right:5px;">Prijavljen(a) kot: ${sessionScope.uporabnik.ime} ${sessionScope.uporabnik.priimek}
        <br>Verzija: <s:eval expression="@nastavitveHelper.verzija"></s:eval>
        <a target="_blank" href="<s:eval expression="@nastavitveHelper.navodila"></s:eval>"> <img style="width:16px;height:16px; margin-left:10px;" src="${pageContext.request.contextPath}/resources/readme.png" title="Navodila za uporabo"/></a> 
        </p> 
    <sec:authorize access="hasAnyRole('002,003,004,006')">   
        <ul class="nav nav-pills" style="padding-left:10px;">            
            <sec:authorize access="hasRole('002')">   
                <li class="dropdown">
                    <a class="dropdown-toggle"
                       data-toggle="dropdown"
                       href="#">
                        Uvoz podatkov iz SIGOV-CA
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a id="vnosCertf" href="${pageContext.request.contextPath}/vnosCertfObstj">Uvoz obstoječih certifikatov</a>
                        </li>  
                        <li>
                            <a id="uvozObstjKartic" href="${pageContext.request.contextPath}/vnosKartObstj">Uvoz obstoječlih podatkov o karticah</a>
                        </li>
                        <li>
                            <a id="uvozObstjCit" href="${pageContext.request.contextPath}/vnosCitalcevObstj">Uvoz obstoječih podatkov o čitalcih</a>
                        </li>

                    </ul>
                </li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('002,006')">
                <li>
                    <a id="PosredovanjeNaSigovcaMenu" href="${pageContext.request.contextPath}/posredovanjeNaSIG">Posredovanje na SIGOV-CA</a>
                </li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('002,004')">   
                <li class="dropdown">
                    <a id="prevzemanjeCertifikatov" class="dropdown-toggle"
                       data-toggle="dropdown"
                       href="#">
                        Prevzemanje certifikatov
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a id="uvozPodatkovZaPrevzem" href="${pageContext.request.contextPath}/vnosCertf">Uvoz podatkov za prevzem</a>
                        </li> 
                        <li>
                            <a href="${pageContext.request.contextPath}/certfPrevzem">Certifikati za prevzem</a>
                        </li> 
                        <li>
                            <a id="uvozPodatkovIzKartice" href="${pageContext.request.contextPath}/vnosKart">Uvoz podatkov iz kartice</a>
                        </li>

                    </ul>
                </li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('002,003')">   
                <li class="dropdown">
                    <a id="odpremljanjeKartic" class="dropdown-toggle"
                       data-toggle="dropdown"
                       href="#">
                        Odpremljanje kartic
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">

                        <li>
                            <a id="pregledPripravljenihKartic" href="${pageContext.request.contextPath}/pregledPripKartic">Pregled pripravljenih kartic</a>
                        </li>     
                        <li >
                            <a id="iskanjePripravljenihKartic" href="${pageContext.request.contextPath}/iskPripKartic">Iskanje pripravljenih kartic</a>
                        </li>  
                    </ul>
                </li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('002,003')">   
                <li class="dropdown">
                    <a id="potrdila" class="dropdown-toggle"
                       data-toggle="dropdown"
                       href="#">
                        Potrdila
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a id="pregledImetnikovZaPotrditev" href="${pageContext.request.contextPath}/potrdiloPrejetoPregled">Pregled imetnikov za potrditev</a>
                        </li> 
                        <li>
                            <a id="iskanjeImetnikaZaPotrditev" href="${pageContext.request.contextPath}/potrdiloPrejeto">Iskanje imetnika za potrditev</a>
                        </li> 

                    </ul>
                </li>
            </sec:authorize>           

            <sec:authorize access="hasAnyRole('002,006')">
                <li>
                    <a href="${pageContext.request.contextPath}/vracanjeKartCit">Vrnjena oprema</a>
                </li> 
            </sec:authorize>
            <sec:authorize access="hasRole('002')">  
                <li>
                    <a id="izvozPodatkovKartice" href="${pageContext.request.contextPath}/izvozPodatkovKartice">Izvoz podatkov o karticah</a>
                </li>   
                <li>
                    <a id="updateCertificateJob" href="${pageContext.request.contextPath}/updateCertificateJob/1">Posodobitev certifikatov</a>
                </li>  
            </sec:authorize>
        </ul>      


    </sec:authorize>
    <ul class="nav nav-pills" style="padding-left:10px;">
        <li>
            <a id="prvaStran" href="${pageContext.request.contextPath}/index" onclick="showHideDiv('loadingDiv');">Prva stran</a>
        </li>
        <sec:authorize access="hasRole('001')">
            <li>
                <a id="pregledZahtevkov" href="${pageContext.request.contextPath}/izpisPodatkov/1/1/1" onclick="showHideDiv('loadingDiv');">Pregled zahtevkov</a>
            </li>
        </sec:authorize>
        <sec:authorize access="hasRole('001')">
            <li>
                <a id="pregledImetnikov" href="${pageContext.request.contextPath}/pregledImetnikov/1" onclick="showHideDiv('loadingDiv');">Pregled imetnikov</a>
            </li>
        </sec:authorize>           
        <sec:authorize access="hasAnyRole('002,005,006')">
            <li>
                <a id="pregledZahtevkov" href="${pageContext.request.contextPath}/izpisPodatkovAdmin/1/1/1" onclick="showHideDiv('loadingDiv');">Pregled zahtevkov</a>
            </li>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('002,005,006')">
            <li>
                <a id="pregledImetnikov" href="${pageContext.request.contextPath}/pregledImetnikovAdmin/1" onclick="showHideDiv('loadingDiv');">Pregled imetnikov</a>
            </li>
        </sec:authorize>  
        <sec:authorize access="hasAnyRole('001,002,005,006')">
            <li class="dropdown">
                <a class="dropdown-toggle"
                   data-toggle="dropdown"
                   href="#">
                    Pregled certifikatov in opreme
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a id="pregledCertifikatov" href="${pageContext.request.contextPath}/pregledCertf" onclick="showHideDiv('loadingDiv');">Pregled certifikatov</a>
                    </li>    
                    <li>
                        <a id="pregledKartic" href="${pageContext.request.contextPath}/pregledKarticSigovca" onclick="showHideDiv('loadingDiv');">Pregled kartic izdanih od SIGOV-CA</a>
                    </li>  
                    <li>
                        <a id="pregledCitalcev" href="${pageContext.request.contextPath}/pregledCitalcevSigovca" onclick="showHideDiv('loadingDiv');">Pregled čitalcev izdanih od SIGOV-CA</a>
                    </li>  
                    <sec:authorize access="hasRole('002')">
                        <li>
                            <a id="pregledKarticVSRS" href="${pageContext.request.contextPath}/pregledKarticVSRS/1" onclick="showHideDiv('loadingDiv');">Pregled kartic izdanih od VSRS</a>
                        </li> 
                    </sec:authorize>
                </ul>
            </li>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('001, 002')">
            <li class="dropdown">
                <a class="dropdown-toggle"
                   data-toggle="dropdown"
                   href="#">
                    Nova vloga
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a id="novaVloga" href="${pageContext.request.contextPath}/Vloga/dodaj" onclick="showHideDiv('loadingDiv');">Zahtevek za pridobitev certifikata</a>
                    </li> 
                    <li>
                        <a id="novaVlogaKoda" href="${pageContext.request.contextPath}/ZahtevekZaKodo" onclick="showHideDiv('loadingDiv');">Zahtevek za pridobitev kode za odklepanje kartice</a>
                    </li> 
                    <li>
                        <a id="novaVlogaPreklic" href="${pageContext.request.contextPath}/ZahtevekZaPreklic" onclick="showHideDiv('loadingDiv');">Zahtevek za preklic digitalnega potrdila</a>
                    </li> 
                     <li>
                        <a id="novaVlogaPrenos" href="${pageContext.request.contextPath}/zahtevekZaPrenos" onclick="showHideDiv('loadingDiv');">Zahtevek za prenos digitalnega potrdila</a>
                    </li> 
                </ul>
            </li>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('002,006')">             
            <li>
                <a id="iskanje" href="${pageContext.request.contextPath}/iskanje">Iskanje</a>
            </li>           
        </sec:authorize>   
        <sec:authorize access="hasRole('002')">     
            <li>
                <a id="statistika" href="${pageContext.request.contextPath}/pregledStatistike">Statistika</a>
            </li>              
        </sec:authorize>   
        <sec:authorize access="hasRole('002')">
            <li class="dropdown">
                <a class="dropdown-toggle"
                   data-toggle="dropdown"
                   href="#">
                    Dodeljevanje opreme
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a id="dodeliCitalec" href="${pageContext.request.contextPath}/dodelovanje/citalci" onclick="showHideDiv('loadingDiv');">Dodeljevanje čitalcev</a>
                    </li>                    
                </ul>
            </li>
        </sec:authorize>
        <sec:authorize access="hasRole('002')">
            <li class="dropdown">
                <a class="dropdown-toggle"
                   data-toggle="dropdown"
                   href="#">
                    Zgodovina
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a id="zgodovinaImetnik" href="${pageContext.request.contextPath}/zgodovina/imetnik" onclick="showHideDiv('loadingDiv');">Zgodovina imetnika</a>
                    </li>  
                     <li>
                        <a id="zgodovinaKartica" href="${pageContext.request.contextPath}/zgodovina/kartica" onclick="showHideDiv('loadingDiv');">Zgodovina kartice</a>
                    </li>  
                </ul>
            </li>
        </sec:authorize>

        <li>
            <a id="odjava" href="${pageContext.request.contextPath}/j_spring_security_logout" >Odjava</a>
        </li>    
    </ul>


</c:if>
<%--  <button onclick="removeCSSClassA();">asd</button>

  <script>
      function removeCSSClassA(){
          var tabelaElem = document.getElementsByTagName("a");
          for(var i=0;i<tabelaElem.length;i++){
              tabelaEleme[i].
          }
        
      }
      
      </script>
--%>