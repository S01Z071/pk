function showHideDiv(objectID) {
    var theElement = document.getElementById(objectID);
    if (theElement.style.display == "none") {
        theElement.style.display = "block";
    }
    else {
        theElement.style.display = "none";
    }
}

function hideDiv(objectID){
    var theElement = document.getElementById(objectID);
    theElement.style.display = "none";
}

function removeModalBackground(objectID) {
    var theElement = document.getElementById(objectID);
    theElement.style.display = "none";
    var elements = document.getElementsByClassName("modal-backdrop");
    for (var i = 0; i < elements.length; i++) {
        elements[i].className = "";
    }
    showHideDiv("loadingDiv");

}

function copyToClipboard(text) {
    window.prompt("Copy to clipboard: Ctrl+C, Enter", text);
}


function mouseOverDisplaySpan(id) {
    var spanI = document.getElementById(id);
    spanI.style.display = 'block';

}

function mouseOutDisplaySpan(id) {
    var spanI = document.getElementById(id);
    spanI.style.display = 'none';

}


function prevzemPotrdila(id, avtorSt, refSt) {
    var conf = confirm("Pred nadaljevanjem vstavite pametno kartico.");
    if (conf) {
        setCookie(id, "true", 5);
        var elem = document.getElementById("TR" + id);
        elem.style.background = '#62af81';
        document.location.reload(true);
        window.open("https://www.sigov-ca.gov.si/cda-cgi/clientcgi?action=getBrowserCert&authcode=" + avtorSt + "&refNo=" + refSt + "&step=Step_1 ");
    }
}


function setCookie(c_name, value, exdays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var c_value = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
    document.cookie = c_name + "=" + c_value;
}

function getCookie(c_name) {
    var c_value = document.cookie;
    var c_start = c_value.indexOf(" " + c_name + "=");
    if (c_start == -1) {
        c_start = c_value.indexOf(c_name + "=");
    }
    if (c_start == -1) {
        c_value = null;
    }
    else {
        c_start = c_value.indexOf("=", c_start) + 1;
        var c_end = c_value.indexOf(";", c_start);
        if (c_end == -1) {
            c_end = c_value.length;
        }
        c_value = unescape(c_value.substring(c_start, c_end));
    }
    return c_value;
}

function automaticEmail(imeId, priimekId, eNaslovId) {
    var ime = document.getElementById(imeId).value;
    var priimek = document.getElementById(priimekId).value;
    if (ime.trim() != "" && priimek.trim() != "") {
        var naslov = ime.trim() + "." + priimek.trim() + "@sodisce.si";
        naslov = naslov.toLowerCase();
        naslov = naslov.replace(/č|ć/g, "c");
        naslov = naslov.replace(/š/g, "s");
        naslov = naslov.replace(/ž/g, "z");
        naslov = naslov.replace(/đ/g, "d");
        document.getElementById(eNaslovId).value = naslov;
    }
}

function clearEmail(eNaslovId) {
    document.getElementById(eNaslovId).value = "";
}

function expandCollapse(id) {
    var trElement = document.getElementsByClassName(id);
    var idNe = id + ".0";
    var aElement = document.getElementById("expandCollapseSign" + id);
    if (aElement.innerHTML == "+") {
        for (var i = 0; i < trElement.length; i++) {
            if (trElement[i].getAttribute("id") != idNe) {
                trElement[i].style.display = "table-row";
            }

        }
        aElement.innerHTML = "-";
    } else {
        for (var i = 0; i < trElement.length; i++) {
            if (trElement[i].getAttribute("id") != idNe) {
                trElement[i].style.display = "none";
            }
        }
        aElement.innerHTML = "+";
    }
}

function disableById(id) {
    document.getElementById(id).disabled = true;
}

function blurIfNotAdmin(obj, vloga) {
    if (vloga != "002") {
        obj.blur();
    }
}

function removeClassIfAdmin(obj, vloga) {
    if (vloga == "002") {
        obj.className = "";
    }
}



