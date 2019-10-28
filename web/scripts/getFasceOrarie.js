function callbackCercaFasce(theXhr, target) {
	if (theXhr.readyState === 2) {
		// theElement.innerHTML = "Richiesta inviata...";
	} else if (theXhr.readyState === 3) {
		// theElement.innerHTML = "Ricezione della risposta...";
	} else if (theXhr.readyState === 4) {
		if (theXhr.status === 200) {
			if (theXhr.responseText) {
				var something = JSON.parse(theXhr.responseText);
				for (var i = 0; i < something.length; i++) {
					var opt = something[i];
				    var el = document.createElement("option");
				    el.textContent = something[i].oraInizio + "-" + something[i].oraFine;
				    el.value = something[i].oraInizio + "-" + something[i].oraFine;
				    target.appendChild(el);
				}
				calcolaNuovoCosto(); //per la prima fascia oraria trovata, viene calcolato il costo
			} else {
				// non faccio niente
			}
		} else {
			// errore di caricamento non faccio niente nemmeno qui
		}
	}
}

function eseguiCercaFasceAJAXPost(uri, body, target, xhr) {

	xhr.onreadystatechange = function() {
		callbackCercaFasce(xhr, target);
	};

	// impostazione richiesta asincrona in POST del file specificato
	try {
		xhr.open("post", uri, true);
	} catch (e) {
		// Exceptions are raised when trying to access cross-domain URIs
		alert(e);
	}

	// rimozione dell'header "connection" come "keep alive"
	xhr.setRequestHeader("connection", "close");
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

	// invio richiesta (es: body="fname=Henry&lname=Ford")
	xhr.send(body);
}

function cercaFasceDisponibili(data, target) {
	
	var uri = "./ordineController";
	body="tipoOperazione=cercaFasce&data="+data;
	var xhr = myGetXmlHttpRequest();
	if (xhr)
		eseguiCercaFasceAJAXPost(uri, body, target, xhr);
}

function leggiContenutoNode(item, nomeNodo) {
	return item.getElementsByTagName(nomeNodo).item(0).firstChild.nodeValue;
}