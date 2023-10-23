/*function isci() {
    var iskano = document.getElementById("search").value;
    var polje = "";
    if (document.getElementById("ImePriimek").checked) {
        polje = "ImePriimek";
    }
    if (document.getElementById("crtnaKodaSearch").checked) {
        polje = "crtnaKoda";
    }
    clearSearch();
    $.ajax({
        url: "iskanje/process",
        data: "iskano=" + iskano + "&polje=" + polje,
        success: function(result) {
            $('#rezultatIskanje').html(result);
        },
        error: function(e) {
            alert("Prišlo je do napake:" + e);
        }
    });
}
*/
/*
function isci() {
    var iskano = "lčalal";
    $.ajax({
        url: "iskanjeCK",
        data: "iskano=" + iskano,
        success: function(result) {
            $('#rezultatIskanje').html(result);
        },
        error: function(e) {
            alert("Prišlo je do napake:" + e);
        }
    });
}
*/
/*
function searchAjax() {
    var iskano = document.getElementById("search").value;
    if (iskano.length >= 3) {
        var polje = "";
        if (document.getElementById("ImePriimek").checked) {
            polje = "ImePriimek";
        }
        if (document.getElementById("crtnaKodaSearch").checked) {
            polje = "crtnaKoda";
        }
        $.ajax({
            url: "iskanjeP",
            data: "iskano=" + iskano + "&polje=" + polje,
            success: function(result) {
                var splitano = result.split(";");
                var subjects = new Array();
                var elemMain = document.getElementById("iskanjeRez");
                elemMain.innerHTML = '';
                var elemUL = document.createElement("ul");

                for (var i = 0; i < splitano.length - 1; i++) {
                    subjects[i] = splitano[i];
                    var elemLi = document.createElement("li");
                    elemLi.className = "btn";
                    elemLi.innerHTML = subjects[i];
                    elemLi.onclick = function() {
                        document.getElementById("search").value = this.innerHTML;
                        hideIskanjeRez();
                    };

                    elemUL.appendChild(elemLi);
                }
                elemMain.appendChild(elemUL);

            },
            error: function(e) {
                alert("Prišlo je do napake:" + e);
            }
        });
    } else {
        hideIskanjeRez();
    }
}
*/
/*
function hideIskanjeRez() {
    document.getElementById("iskanjeRez").innerHTML = "";
}
function clearSearch() {
    document.getElementById("search").value = "";
}
*/


