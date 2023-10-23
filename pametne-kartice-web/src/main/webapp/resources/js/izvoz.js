function izvoz() {
    var tabela = document.getElementsByName("oznaci");
    var rez = "";
    for (var i = 0; i < tabela.length; i++) {
        var elem = tabela[i];
        var id = elem.id.split("oznaci")[1];
        if (elem.checked) {
            rez += id + "S";
        }
    }
    //pagecontext ne dela tukaj
    if (rez != "") {
      izvozFinal(rez);
    }
}

function oznaciOdoznaciVse() {
    var oznaceno = document.getElementById("oznaciVse").checked;
    var tabela = document.getElementsByName("oznaci");
    for (var i = 0; i < tabela.length; i++) {
        if (!tabela[i].disabled) {
            tabela[i].checked = oznaceno;
        }
    }
}

function preveriOznaceno(ObjectID) {
    if (!ObjectID.checked) {
        document.getElementById("oznaciVse").checked = false;
    }
}

