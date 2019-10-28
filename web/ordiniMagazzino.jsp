<!DOCTYPE html>
<html lang="en">

<head>
	<title>Ordini Magazzino</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800&display=swap"
		rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Lora:400,400i,700,700i&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Amatic+SC:400,700&display=swap" rel="stylesheet">

	<link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
	<link rel="stylesheet" href="css/animate.css">

	<link rel="stylesheet" href="css/owl.carousel.min.css">
	<link rel="stylesheet" href="css/owl.theme.default.min.css">
	<link rel="stylesheet" href="css/magnific-popup.css">

	<link rel="stylesheet" href="css/aos.css">

	<link rel="stylesheet" href="css/ionicons.min.css">

	<link rel="stylesheet" href="css/bootstrap-datepicker.css">
	<link rel="stylesheet" href="css/jquery.timepicker.css">


	<link rel="stylesheet" href="css/flaticon.css">
	<link rel="stylesheet" href="css/icomoon.css">
	<link rel="stylesheet" href="css/style.css">
</head>

<body class="goto-here">
		<%@ include file="fragments/headerMagazzino.html"%>


	<section class="ftco-section ftco-cart">
		<div class="container">
			<div class="row">
				<div class="col-md-12 ftco-animate">
					<div class="cart-list">
						<table class="table">
							<thead class="thead-primary">
								<tr class="text-center">
									<th>ID Ordine</th>
									<th>Info Cliente</th>
									<th>Status Ordine</th>
									<th>Prezzo </th>
									<th>Info consegna</th>
									<th>Dettaglio Ordine</th>
								</tr>
							</thead>
							<tbody>
								<tr class="text-center">
									<td class="price">1</td>
									<td class="price">
										<a class="nav-link dropdown-toggle" href="#" id="dropdown04"
											data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Mostra
											info consegna</a>
										<div class="dropdown-menu" aria-labelledby="dropdown04">
											<a class="dropdown-item">
												<h7>Nome Utente: </h7> &emsp;Luigi</span>
												<a class="dropdown-item">
													<h7>Cognome Utente: </h7> &emsp;Bianchi </span>
													<a class="dropdown-item">
														<h7>Email: </h7> &emsp;luigibianchi@gmail.com </span>
										</div>
									</td>
									<td class="price">
										<p>In Attesa Di Conferma</p>
										<div class="form-group">
											<div class="col-md-12">
												<div class="radio">
													<button
														style=" border-radius: 10px; padding: 10px 15px; background-color: #82AE46; color: white;">Accetta</button>

												</div>
											</div>
										</div>
										<div class="col-md-12">
											<div class="radio">
												<button
													style=" border-radius: 10px; padding: 10px 15px; background-color: #82AE46; color: white;">Rifiuta</button>

											</div>
										</div>
					</div>
					</td>
					<td class="price">€3.75</td>
					<td class="price">
						<a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">Mostra dettaglio</a>
						<div class="dropdown-menu" aria-labelledby="dropdown04">
							<a class="dropdown-item">
								<h7>Indirizzo consegna:</h7> &emsp;Via larga
								<a class="dropdown-item">
									<h7>Data di consegna:</h7> &emsp;15/11/2019
									<a class="dropdown-item">
										<h7>Fascia oraria:</h7> &emsp;10.00-11.00
										<a class="dropdown-item">
											<h7>Costo di consegna:</h7> &emsp;€2.00
											<a class="dropdown-item">
												<h7>Metodo di pagamento:</h7> &emsp;POS
						</div>
					</td>
					<td class="price">
						<a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">Mostra dettaglio</a>
						<div class="dropdown-menu" aria-labelledby="dropdown04">
							<span class="dropdown-item">Acqua San Bernardo&emsp;1&emsp;€1.75 </span>
						</div>
					</td>
					</tr><!-- END TR-->
					<tr class="text-center">
						<td class="price">2</td>
						<td class="price">
							<a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">Mostra info cliente</a>
							<div class="dropdown-menu" aria-labelledby="dropdown04">
								<a class="dropdown-item">
									<h7>Nome Utente: </h7> &emsp;Francesco</span>
									<a class="dropdown-item">
										<h7>Cognome Utente: </h7> &emsp;Neri </span>
										<a class="dropdown-item">
											<h7>Email: </h7> &emsp;francesconeri@libero.it </span>
							</div>
						</td>
						<td class="price">
							<p>In Consegna</p>
							<div class="form-group">
								<div class="col-md-12">
									<div class="radio">
										<button
											style="border-radius: 10px; padding: 10px 15px; background-color: #82AE46; color: white;">Aggiorna
											stato</button>

									</div>
								</div>
							</div>
						</td>
						<td class="price">€16.50</td>
						<td class="price">
							<a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">Mostra info consegna</a>
							<div class="dropdown-menu" aria-labelledby="dropdown04">
								<a class="dropdown-item">
									<h7>Indirizzo consegna:</h7> &emsp;Via andre costa
									<a class="dropdown-item">
										<h7>Data di consegna:</h7> &emsp;17/11/2019
										<a class="dropdown-item">
											<h7>Fascia oraria:</h7> &emsp;9.00-10.00
											<a class="dropdown-item">
												<h7>Costo di consegna:</h7> &emsp;€2.50
												<a class="dropdown-item">
													<h7>Metodo di pagamento:</h7> &emsp;POS
							</div>
						</td>
						<td class="price">
							<a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">Mostra dettaglio</a>
							<div class="dropdown-menu" aria-labelledby="dropdown04">
								<span class="dropdown-item">Salsicce&emsp;1&emsp;€6.00 </span>
								<span class="dropdown-item">Prosciutto Di Parma&emsp;2&emsp;€4.00 </span>
							</div>
						</td>
					</tr><!-- END TR-->

					<tr class="text-center">
						<td class="price">3</td>
						<td class="price">
							<a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">Mostra info cliente</a>
							<div class="dropdown-menu" aria-labelledby="dropdown04">
								<a class="dropdown-item">
									<h7>Nome Utente: </h7> &emsp;Mario</span>
									<a class="dropdown-item">
										<h7>Cognome Utente: </h7> &emsp;Rossi </span>
										<a class="dropdown-item">
											<h7>Email: </h7> &emsp;mariorossi@icloud.com </span>
							</div>
						</td>
						<td class="price">
							<p>In Preparazione</p>
							<div class="form-group">
								<div class="col-md-12">
									<div class="radio">
										<button
											style="border-radius: 10px; padding: 10px 15px; background-color: #82AE46; color: white;">Aggiorna
											stato</button>

									</div>
								</div>
							</div>
						</td>
						<td class="price">€12.75</td>
						<td class="price">
							<a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">Mostra info consegna</a>
							<div class="dropdown-menu" aria-labelledby="dropdown04">
								<a class="dropdown-item">
									<h7>Indirizzo consegna:</h7> &emsp;Via murri
									<a class="dropdown-item">
										<h7>Data di consegna:</h7> &emsp;15/11/2019
										<a class="dropdown-item">
											<h7>Fascia oraria:</h7> &emsp;11.00-12.00
											<a class="dropdown-item">
												<h7>Costo di consegna:</h7> &emsp;€3.00
												<a class="dropdown-item">
													<h7>Metodo di pagamento:</h7> &emsp;POS
							</div>
						</td>
						<td class="price">
							<a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">Mostra dettaglio</a>
							<div class="dropdown-menu" aria-labelledby="dropdown04">
								<span class="dropdown-item">Acqua San Bernardo&emsp;1&emsp;€1.75 </span>
								<span class="dropdown-item">Prosciutto Di Parma&emsp;2&emsp;€4.00 </span>
							</div>
						</td>
					</tr>
					<!-- END TR-->
					<tr class="text-center">
						<td class="price">4</td>
						<td class="price">
							<a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">Mostra info cliente</a>
							<div class="dropdown-menu" aria-labelledby="dropdown04">
								<a class="dropdown-item">
									<h7>Nome Utente: </h7> &emsp;Elena</span>
									<a class="dropdown-item">
										<h7>Cognome Utente: </h7> &emsp;Gialli </span>
										<a class="dropdown-item">
											<h7>Email: </h7> &emsp;elenagialli@icloud.com </span>
							</div>
						</td>
						<td class="price">
							<p>In Consegna</p>
							<div class="form-group">
								<div class="col-md-12">
									<div class="radio">
										<button
											style=" border-radius: 10px; padding: 10px 15px; background-color: #82AE46; color: white;">Aggiorna
											stato</button>

									</div>
								</div>
							</div>
						</td>
						<td class="price">€9.00</td>
						<td class="price">
							<a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">Mostra info consegna</a>
							<div class="dropdown-menu" aria-labelledby="dropdown04">
								<a class="dropdown-item">
									<h7>Indirizzo consegna:</h7> &emsp;Via zanardi
									<a class="dropdown-item">
										<h7>Data di consegna:</h7> &emsp;15/11/2019
										<a class="dropdown-item">
											<h7>Fascia oraria:</h7> &emsp;15.00-16.00
											<a class="dropdown-item">
												<h7>Costo di consegna:</h7> &emsp;€3.00
												<a class="dropdown-item">
													<h7>Metodo di pagamento:</h7> &emsp;Contante
							</div>
						</td>
						<td class="price">
							<a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">Mostra dettaglio</a>
							<div class="dropdown-menu" aria-labelledby="dropdown04">
								<span class="dropdown-item">Salsicce&emsp;1&emsp;€6.00 </span>
							</div>
						</td>
					</tr>
					</tbody>
					</table>
				</div>
			</div>
		</div>
		</div>
	</section>
	<!-- loader -->
	<div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px">
			<circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee" />
			<circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10"
				stroke="#F96D00" /></svg></div>


	<script src="js/jquery.min.js"></script>
	<script src="js/jquery-migrate-3.0.1.min.js"></script>
	<script src="js/popper.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.easing.1.3.js"></script>
	<script src="js/jquery.waypoints.min.js"></script>
	<script src="js/jquery.stellar.min.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/jquery.magnific-popup.min.js"></script>
	<script src="js/aos.js"></script>
	<script src="js/jquery.animateNumber.min.js"></script>
	<script src="js/bootstrap-datepicker.js"></script>
	<script src="js/scrollax.min.js"></script>
	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
	<script src="js/google-map.js"></script>
	<script src="js/main.js"></script>

	<script>
		$(document).ready(function () {

			var quantitiy = 0;
			$('.quantity-right-plus').click(function (e) {

				// Stop acting like a button
				e.preventDefault();
				// Get the field name
				var quantity = parseInt($('#quantity').val());

				// If is not undefined

				$('#quantity').val(quantity + 1);


				// Increment

			});

			$('.quantity-left-minus').click(function (e) {
				// Stop acting like a button
				e.preventDefault();
				// Get the field name
				var quantity = parseInt($('#quantity').val());

				// If is not undefined

				// Increment
				if (quantity > 0) {
					$('#quantity').val(quantity - 1);
				}
			});

		});
	</script>

</body>

</html>