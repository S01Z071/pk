function setPovezavaAction() {
    var tabelaCert = document.getElementsByName("izbiraCert");
    var certID = "0";
    for (var i = 0; i < tabelaCert.length; i++) {
        var elemC = tabelaCert[i];
        if (elemC.checked) {
            certID = elemC.id.split(".")[1];
            break;
        }
    }

    var tabelaKart = document.getElementsByName("izbiraKart");
    var kartID = "0";
    for (var i = 0; i < tabelaKart.length; i++) {
        var elemK = tabelaKart[i];
        if (elemK.checked) {
            kartID = elemK.id.split(".")[1];
            break;
        }
    }
    
    setPovezavaActionSubmit(certID,kartID);
    //var submitForm = document.getElementById("formPovezava");
    // submitForm.action = "${pageContext.request.contextPath}/povezavaKartInCertf/" + ${imetnik.id} + "/" + certID + "/" + kartID;
   // submitForm.submit();
}

