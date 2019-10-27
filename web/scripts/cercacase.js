function leggiContenuto(item, nomeNodo) {
	return item.getElementsByTagName(nomeNodo).item(0).firstChild.nodeValue;
}

function mostraAffitti(array, min, max, resDiv) {
	//devo parsificare l'array XML che dall'esercizio è in questa forma:
	/*<?xml version="1.0" encoding="UTF-8"?>
	<citta nome="bologna">
		<casa>
			<indirizzo>via Sant Isaia 42</indirizzo>
			<prezzo>650</prezzo>
		</casa>
		<casa>
			<indirizzo>piazza San Francesco 45</indirizzo>
			<prezzo>750</prezzo>
		</casa>
		<casa>
			<indirizzo>piazza Malpighi 40</indirizzo>
			<prezzo>720</prezzo>
		</casa>
	</citta>*/
	var caseXML = array.getElementsByTagName("casa");
	var caseResult = new Array(); //array che verrà mostrato alla pressione di "cerca"
	for (int i = 0, k = 0; i < caseXML.length; i++) { //mi prendo tutte le case dal tag XML "casa"
		var prezzo = leggiContenuto("prezzo"); //leggo prima il prezzo perche devo fare il controllo che esso sia tra min e max
		if (prezzo > eval(min) && prezzo < eval(max)) { //se è nel range richiesto leggo l'indirizzo
			var indirizzo = leggiContenuto("indirizzo");
			caseResult[k] = new Object();
			caseReuslt[k].indirizzo = indirizzo;
			caseResult[k].prezzo = prezzo;
			k++;
		}
	}
	htmlres = "<ul>";
	//adesso preparo l'html visualizzato nel div "result"
	for (int i = 0; i < caseResult.length; i++) {
		htmlres += "<li>";
		htmlres += caseResult[i].indirizzo;
		htmlres += caseResult[i].prezzo;
		htmlres += "</li>";
	}
	htmlres += "</ul>";
	resDiv.innerHTML = htmlres;
}

/*
 * Funzione di callback
 */
function cercaCaseCallback( theXhr, citta, array ) {

	// verifica dello stato
	if ( theXhr.readyState === 2 ) {
    	// non faccio niente
    	// theElement.innerHTML = "Richiesta inviata...";
	}// if 2
	else if ( theXhr.readyState === 3 ) {
    	// non faccio niente
		// theElement.innerHTML = "Ricezione della risposta...";
	}// if 3
	else if ( theXhr.readyState === 4 ) {
		// verifica della risposta da parte del server
	        if ( theXhr.status === 200 ) {
	        	// operazione avvenuta con successo	
		        if ( theXhr.responseXML ) {
		        	array[citta] = theXhr.responseXML; //nell'array c'è ancora l'XML (parsificato in mostraAffitti) delle case per ogni citta
				}
				else {
			    	// non faccio niente
				}
	        }
	        else {
	        	// errore di caricamento
	        	// non faccio niente nemmeno qui
	        }
	}// if 4
} // prodottoCallback();



/*
 * Usa tecniche AJAX attraverso la XmlHttpRequest fornita in theXhr
 */
function eseguiCercaCaseAJAX(uri, citta, array, xhr){ 
    
	// impostazione controllo e stato della richiesta
	xhr.onreadystatechange = function() { cercaCaseCallback(xhr, citta, array); };

	// impostazione richiesta asincrona in GET del file specificato
	try {
		xhr.open("get", uri, true);
	}
	catch(e) {
		// Exceptions are raised when trying to access cross-domain URIs 
		alert(e);
	}

	// rimozione dell'header "connection" come "keep alive"
	//xhr.setRequestHeader("connection", "close");

	// invio richiesta
	xhr.send(null);

} // eseguiProdottoAJAX()



function eseguiCercaCase(uri, citta, array) {

	// assegnazione oggetto XMLHttpRequest
	var xhr = myGetXmlHttpRequest();

	if ( xhr ) 
		eseguiCercaCaseAJAX(uri, citta, mapping, xhr);


}