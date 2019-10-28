<!DOCTYPE html>
<html lang="en">
<head>
<title>Checkout</title>
	<meta charset="utf-8">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>
	<div class="jumbotron text-center">
	  <h1 class="display-3">Grazie per l'ordine!</h1>
	  <p class="lead"><strong>Ti invieremo un' e-mail quando l'ordine sar� stato accettato.</strong></p>
	  <hr>
	  <p>
	    Problemi? <a href="">Contattaci</a>
	  </p>
	  <p class="lead">
	    <a class="btn btn-primary btn-sm" href= <%= request.getContextPath() + "/index.jsp" %> role="button">Ritorna alla homepage</a>
	  </p>
	</div>
</body>