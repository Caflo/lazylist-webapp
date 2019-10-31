
function callbackCalcolaNuovoCosto(theXhr, costoSpedizione, costoTotale) {
	if (theXhr.readyState === 2) {
		// theElement.innerHTML = "Richiesta inviata...";
	} else if (theXhr.readyState === 3) {
		// theElement.innerHTML = "Ricezione della risposta...";
	} else if (theXhr.readyState === 4) {
		if (theXhr.status === 200) {
			if (theXhr.responseText) {
				var result = JSON.parse(theXhr.responseText); //costospedizione
				costoSpedizione.textContent = parseFloat(Math.round(result * 100) / 100).toFixed(2);
				//adesso mi ricalcolo il totale (ho bisogno del campo del subtotale)
				var subtotale = myGetElementById('subtotale').textContent;
				subtotale = subtotale.replace(",", "."); //da 3,60 passa a 3.60 cosi' riesco a leggerlo
				var tot = parseFloat(result) + parseFloat(subtotale);
				costoTotale.textContent = parseFloat(Math.round(tot * 100) / 100).toFixed(2);;
			} else {
				// non faccio niente
			}
		} else {
			// errore di caricamento non faccio niente nemmeno qui
		}
	}
}

function eseguiCalcolaNuovoCostoAJAXPost(uri, body, costoSpedizione, costoTotale, xhr) {

	xhr.onreadystatechange = function() {
		callbackCalcolaNuovoCosto(xhr, costoSpedizione, costoTotale);
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

function calcolaNuovoCosto() { //callback dell'evento
	
	var fasce = myGetElementById('comboBoxFasce');
	var fasciaSelezionata = fasce.options[fasce.selectedIndex].text;
	var costoSpedizione = myGetElementById('costoSpedizione');
	var costoTotale = myGetElementById('costoTotale');
	var subtotale = myGetElementById('subtotale').textContent;
	subtotale = subtotale.replace(",", ".");
	
	
	var uri = "./ordineController";
	var body="tipoOperazione=calcolaCosto&fascia="+fasciaSelezionata+"&subtotale="+parseFloat(subtotale);
	var xhr = myGetXmlHttpRequest();
	if (xhr)
		eseguiCalcolaNuovoCostoAJAXPost(uri, body, costoSpedizione, costoTotale, xhr);
}

/*Con questa funzione mi metto in attesa di eventi onchange sul tag <select> siccome non riesce a prenderli quando con ajax cambio
 * le opzioni dinamicamente */


function leggiContenutoNode(item, nomeNodo) {
	return item.getElementsByTagName(nomeNodo).item(0).firstChild.nodeValue;
}