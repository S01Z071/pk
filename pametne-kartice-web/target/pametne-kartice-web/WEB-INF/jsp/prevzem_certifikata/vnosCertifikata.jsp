<%-- 
    Document   : vnosCertifikata
    Created on : 17.9.2013, 8:55:38
    Author     : uporabnik
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="container" style="text-align: center;">
    <div class="row">
        <div class="span10">
            <h4>Izbira datoteke s podatki o certifikatih</h4><br><br>
        </div>
    </div>
    <div class="row">
        <div class="error">
            ${error} 
            <br>                     
        </div>
        <br>
        <c:if test='${not empty uspesnoBranje && uspesnoBranje!=0}'>
            <div class="success">          
                Uspešno prebranih vrstic:${uspesnoBranje}<br>           
            </div><br>
        </c:if>
        <c:if test='${not empty neuspesnoBranje && neuspesnoBranje!=0}'>
            <div class="success">          
                Neuspešno prebranih vrstic:${neuspesnoBranje}<br>           
            </div><br>
        </c:if>
        <c:if test='${not empty uspesno && uspesno!=0}'>
            <div class="success">          
                Uspešno vnešenih v bazo:${uspesno}<br>           
            </div><br>
        </c:if>
        <c:if test='${not empty neuspesno && neuspesno!=0}'>
            <div class="error">
                Neuspešno vnešenih v bazo:${neuspesno}<br>
            </div><br>
        </c:if>
        <c:if test='${not empty povezanih && povezanih!=0}'>
            <div class="success">      
                Uspešno povezanih certifikatov z imetnikom:${povezanih}<br>            
            </div><br>
        </c:if>
        <c:if test='${not empty nepovezanih && nepovezanih!=0}'>
            <div class="error">
                Neuspešno povezanih certifikatov z imetnikom:${nepovezanih}
            </div>
        </c:if>
            <div class="error" id="errorPodvojeni"></div>
    </div>
</div>
<br>
<div class="container">
    <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/vnosCertf/process">   
        <div class="row">
            <div class="span7">
                Datoteka:  <input id="browseClick" type="file" name="myfile"/><br/>

            </div>
        </div>
        <br>
        <div class="row">
            <div class="span3">
                <button id="naloziPodatke" class="btn" type="submit">Naloži</button>
            </div>
        </div>       
    </form>
</div>  
<c:if test="${fn:length(seznamCertifikatov.certifikati)>0}">
    <table class="table">
        <tr>
            <td>Ime organizacije</td>
            <td>Ime in priimek</td>
            <td>Naslov</td>           
            <td>Referenčna št.</td>
            <td>Avtorizacijska št.</td>
            <td>Strežnik</td>           
            <td>Tip potrdila</td>
            <td>Serijska št.</td>
            <td>E-naslov</td>
        </tr>
        <sf:form modelAttribute="seznamCertifikatov" action="${pageContext.request.contextPath}/vnosCertf/process/confirm" id="seznamCertifikatov">
            <c:forEach var="certifikati" items="${seznamCertifikatov.certifikati}" varStatus="i">   
                <c:if test='${not empty certifikati.imetnik}'>
                    <tr style="background-color:#62af81;">
                    </c:if>
                    <c:if test='${empty certifikati.imetnik}'>
                    <tr style="background-color:#91a870;">
                    </c:if>                        

                    <td>${certifikati.imeOrganizacije}</td>
                    <td>${certifikati.imeInPriimek}</td>                            
                    <td>${certifikati.naslov} ${certifikati.posta} ${certifikati.kraj} ${certifikati.drzava}</td>
                    <td>${certifikati.referencnaSt}</td>      
                    <td>${certifikati.avtorizacijskaSt}</td>   
                    <td>${certifikati.streznik}</td>                    
                    <td>${certifikati.tipPotrdila}</td>
                    <td>${certifikati.serijskaStevilka}</td>
                    <td>${certifikati.ENaslov}</td>
                    <td>  

                    </td>

                    <td>
                        <sf:hidden path="certifikati[${i.index}].imeOrganizacije"/>
                        <sf:hidden path="certifikati[${i.index}].imeInPriimek"/>   
                        <sf:hidden path="certifikati[${i.index}].naslov"/>
                        <sf:hidden path="certifikati[${i.index}].posta"/>
                        <sf:hidden path="certifikati[${i.index}].kraj"/>
                        <sf:hidden path="certifikati[${i.index}].drzava"/>
                        <sf:hidden path="certifikati[${i.index}].referencnaSt"/>
                        <sf:hidden path="certifikati[${i.index}].avtorizacijskaSt"/>
                        <sf:hidden path="certifikati[${i.index}].streznik"/>                        
                        <sf:hidden path="certifikati[${i.index}].tipPotrdila"/>
                        <sf:hidden path="certifikati[${i.index}].serijskaStevilka"/>   
                        <sf:hidden path="certifikati[${i.index}].ENaslov"/>       

                    </td>
                </tr>
            </c:forEach>

            <tr>                        
                <td colspan="14" style="text-align: center;">
                    <input id="uvoziPodatke" class="btn btn-primary" style="width:100px;" type="submit" value="Uvozi" onclick="showHideDiv('loadingDiv');"/>
                </td>
            </tr>
        </sf:form>
    </table>
</c:if>


<script>
   
    var elementi = document.getElementsByTagName("tr");
    for(var i=0;i<elementi.length;i++){
        var element = elementi[i];        
        var elementiTD = element.getElementsByTagName("td");
        var podvojeni = 0;
        if(elementiTD.length>=9){          
            var elementTD = elementiTD[8];
            for(var k=0;k<elementi.length;k++){
                if(elementi[k].getElementsByTagName("td").length>=9 && elementTD.innerHTML == elementi[k].getElementsByTagName("td")[8].innerHTML){
                    podvojeni++;
                }
            }
        }
      
      if(podvojeni>1){
          //element.className = "certifikatPodvojen";
          element.setAttribute("style","background-color:#c93232;");
         // alert(element.innerHTML);
         document.getElementById("errorPodvojeni").innerHTML = "Rdeče obarvane vrstice imajo enake E-naslove.";
      }
      
    }
   
</script>