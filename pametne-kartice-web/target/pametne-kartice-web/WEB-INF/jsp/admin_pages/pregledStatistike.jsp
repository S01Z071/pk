<%-- 
    Document   : izpisPodatkov
    Created on : 13.8.2013, 8:46:23
    Author     : uporabnik
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="text-align:center;">
    <h3>Pregled statistike</h3>
</div>

<div class="container">
    <br>
    <div style="float: left;">
        <table border ="1">
            <tr>
                <td colspan="3" style="text-align:center;"><h4>Zahtevki</h4></td>        
            </tr>            
            <tr>         
                <td style="width:220px;"><strong>Status</strong></td>
                <td style="width:50px;"><strong>Šifra</strong></td>
                <td style="width:50px;"><strong>Število</strong></td>
            </tr>
            <tr>
                <td>V pripravi</td>
                <td>01</td>
                <td>${zahtevkiVPripravi}</td>
            </tr>
            <tr>
                <td>Zaključen</td>
                <td>02</td>
                <td>${zahtevkiZakljucen}</td>
            </tr>
            <tr>
                <td>Posredovano na CIF</td>
                <td>03</td>
                <td>${zahtevkiPosredovanoNaCif}</td>
            </tr>
            <tr>
                <td>Izbrisano</td>
                <td>04</td>
                <td>${zahtevkiIzbrisano}</td>
            </tr>
            <tr>
                <td>Potrjen</td>
                <td>05</td>
                <td>${zahtevkiPotrjeno}</td>
            </tr>
            <tr>
                <td>Zavrnjen</td>
                <td>06</td>
                <td>${zahtevkiZavrnjeno}</td>
            </tr>
            <tr>
                <td>Posredovano na SIGOV-CA</td>
                <td>07</td>
                <td>${zahtevkiPosredovanoNaSigovca}</td>
            </tr>
            <tr>
                <td>Dokončan</td>
                <td>08</td>
                <td>${zahtevkiDokoncan}</td>
            </tr>             
            <tr style="background-color:gainsboro">
                <td>Skupaj</td>
                <td>/</td>
                <td>${zahtevkiSkupaj}</td>
            </tr> 


        </table>
    </div>

    <div style="float: left; padding-left:10px;">
        <table border ="1">
            <tr>
                <td colspan="3" style="text-align:center;"><h4>Imetniki</h4></td>        
            </tr>
            <tr>         
                <td style="width:220px;"><strong>Status</strong></td>
                <td style="width:50px;"><strong>Šifra</strong></td>
                <td style="width:50px;"><strong>Število</strong></td>
            </tr>
            <tr>
                <td>V pripravi</td>
                <td>01</td>
                <td>${imetnikiVPripravi}</td>
            </tr>
            <tr>
                <td>Zaključen</td>
                <td>02</td>
                <td>${imetnikiZakljucen}</td>
            </tr>
            <tr>
                <td>Posredovano na CIF</td>
                <td>03</td>
                <td>${imetnikiPosredovanoNaCif}</td>
            </tr>
            <tr>
                <td>Izbrisano</td>
                <td>04</td>
                <td>${imetnikiIzbrisano}</td>
            </tr>
            <tr>
                <td>Potrjen</td>
                <td>05</td>
                <td>${imetnikiPotrjeno}</td>
            </tr>
            <tr>
                <td>Zavrnjen</td>
                <td>06</td>
                <td>${imetnikiZavrnjeno}</td>
            </tr>
            <tr>
                <td>Posredovano na SIGOV-CA</td>
                <td>07</td>
                <td>${imetnikiPosredovanoNaSigovca}</td>
            </tr>
            <tr>
                <td>Pametna kartica odpremljena</td>
                <td>08</td>
                <td>${imetnikiPametnaKarticaOdpremljena}</td>
            </tr> 
            <tr>
                <td>Pametna kartica prevzeta</td>
                <td>09</td>
                <td>${imetnikiPametnaKarticaPrevzeta}</td>
            </tr> 
            <tr style="background-color:gainsboro">
                <td>Skupaj</td>
                <td>/</td>
                <td>${imetnikiSkupaj}</td>
            </tr> 
        </table>
    </div>
    <div style="float: left; padding-left:10px;">       
        <table border ="1">
            <tr>
                <td colspan="3" style="text-align:center;"><h4>Certifikati in kartice</h4></td>        
            </tr>
            <tr>         
                <td style="width:240px;"><strong>Status</strong></td>       
                <td colspan="2" style="width:50px;"><strong>Število</strong></td>
            </tr>
            <tr>
                <td>V pripravi</td>             
                <td>${certifikatiVPripravi}</td>
            </tr>
            <tr>
                <td>Prevzet na VSRS</td>            
                <td>${certifikatiPrevzetNaVSRS}</td>
            </tr>
            <tr>
                <td>Prevzet</td>     
                <td>${certifikatiPrevzet}</td>
            </tr> 
            <tr>
                <td>Preklican</td>         
                <td>${certifikatPreklican}</td>
            </tr>
            <tr>
                <td>Pretekel</td>         
                <td>${certifikatPretekel}</td>
            </tr>  
            <tr>
                <td><a id="plusMinus" style="cursor: pointer;" onclick="showDetail('${karticaBrezCertifikata}', 'plusMinus', 'kartice');">+</a>Kartice brez certifikatov</td>
                <td>${karticaBrezCertifikata}</td>
            </tr>            
            <c:forEach var="kartice" items="${kartice}" varStatus="i">
                <tr style="display:none" id="kartice${i.index}">                   
                    <td style="padding-left:30px; background-color:#f7f6f4;">
                        ${kartice.barcode}       
                        <c:if test='${not empty kartice.imetnik.id}'>
                            <a href="${pageContext.request.contextPath}/pregledImetnikovPodrobno/${kartice.imetnik.id}" class="" style="height:10px; font-size:10px;">Pregled imetnika</a>
                        </c:if>
                    </td> 

                </tr>
            </c:forEach>

            <tr>
                <td><a id="plusMinus1" style="cursor: pointer;" onclick="showDetail('${karticeBrezImetnika}', 'plusMinus1', 'karticeI');">+</a>Kartice brez imetnika</td>
                <td>${karticeBrezImetnika}</td>
            </tr> 
            <c:forEach var="karticeI" items="${karticeI}" varStatus="i">
                <tr style="display:none" id="karticeI${i.index}">                   
                    <td style="padding-left:30px; background-color:#f7f6f4;">
                        ${karticeI.barcode}                       
                        <%-- <a href="${pageContext.request.contextPath}/pregledImetnikovPodrobno/${kartice.imetnik.id}" class="" style="height:10px; font-size:10px;">Pregled imetnika</a>
                        --%>
                    </td> 

                </tr>
            </c:forEach>

            <tr>
                <td><a id="plusMinus2" style="cursor: pointer;" onclick="showDetail('${neVeljavniCertifikatiBrezVrnjeneKartice}', 'plusMinus2', 'certifikat');">+</a>Kartice z neveljavnim certifikatom</td>
                <td>${neVeljavniCertifikatiBrezVrnjeneKartice}</td>
            </tr> 

            <c:forEach var="certifikat" items="${certifikatiK}" varStatus="i">
                <tr style="display:none" id="certifikat${i.index}">                   
                    <td style="padding-left:30px; background-color:#f7f6f4;">
                        ${certifikat.kartica.barcode}     
                        <c:if test='${not empty certifikat.imetnik.davcna}'>
                            <a href="${pageContext.request.contextPath}/zgodovina/imetnik/iskanje?iskano=${certifikat.imetnik.davcna}" class="" style="height:10px; font-size:10px;">Zgodovina imetnika</a>
                        </c:if>

                    </td> 

                </tr>
            </c:forEach>



            <script>
                    function showDetail(stElements, controlElId, basicElName) {
                        var elPlusMinus = document.getElementById(controlElId);
                        if (elPlusMinus.innerHTML == '+') {
                            for (var i = 0; i < stElements; i++) {
                                var el = document.getElementById(basicElName + i);
                                el.style.display = "";
                            }
                            elPlusMinus.innerHTML = "-";
                        } else {
                            for (var i = 0; i < stElements; i++) {
                                var el = document.getElementById(basicElName + i);
                                el.style.display = "none";
                            }
                            elPlusMinus.innerHTML = "+";
                        }
                    }
            </script>
            <tr>
                <td>Skupaj izdanih kartic</td>
                <td>${karticaSkupaj}</td>

            </tr> 

        </table>
    </div>

</div>

