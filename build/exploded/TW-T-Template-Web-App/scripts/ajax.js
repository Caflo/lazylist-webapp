function callback(theXhr, target) {
	if (theXhr.readyState === 2) {
		// theElement.innerHTML = "Richiesta inviata...";
	} else if (theXhr.readyState === 3) {
		// theElement.innerHTML = "Ricezione della risposta...";
	} else if (theXhr.readyState === 4) {
		if (theXhr.status === 200) {
			if (theXhr.responseText) {
				var fasce = parsificaJSONArray(theXhr.responseText);
				for (var i = 0; i < fasce.length; i++) {
					target.innerHTML += "<option value=\""+fasce[i]+"\">"+fasce[i]+"</option>"
				}
			} else {
				// non faccio niente
			}
		} else {
			// errore di caricamento non faccio niente nemmeno qui
		}
	}
}

function eseguiAJAXPost(uri, body, target, xhr) {

	xhr.onreadystatechange = function() {
		callback(xhr, target);
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
	
	var uri = "./ordineController?tipoOperazione=cercaFasce&data="+data;
	var xhr = myGetXmlHttpRequest();
	if (xhr)
		eseguiAJAXPost(uri, target, xhr);
}

function parsificaJSON(jsonText) {
	var something = JSON.parse(jsonText);
	var risultato = "<b>" + something.message + "</b>";
	return risultato;
}

function parsificaJSONArray(jsonText) {
	var something = JSON.parse(jsonText);
	var risultato = "";
	for (var i = 0; i < something.length; i++) {
		if (something[i] != null)
			risultato += "<li>" + something[i].message + "</li>";
	}
	return risultato;
}

function leggiContenutoNode(item, nomeNodo) {
	return item.getElementsByTagName(nomeNodo).item(0).firstChild.nodeValue;
}