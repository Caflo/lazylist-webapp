<!DOCTYPE html>
<html lang="en">
<!-- import di classi Java -->
<%@ page import="model.ordine.*"%>
<%@ page import="model.prodottoECarrello.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.DecimalFormat"%>

<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<head>
<title>Checkout</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link
	href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Lora:400,400i,700,700i&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Amatic+SC:400,700&display=swap"
	rel="stylesheet">

<link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
<link rel="stylesheet" href="css/animate.css">
<script src="scripts/getFasceOrarie.js" type="text/javascript"></script>
<!-- AJAX -->
<script src="scripts/setNuovoCostoOrdine.js" type="text/javascript"></script>
<!-- AJAX -->
<script src="scripts/utils.js" type="text/javascript"></script>

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
	<%@ include file="fragments/headerCliente.html"%>

	<%
		Carrello c = (Carrello) session.getAttribute("carrello"); //quando accedo per la prima volta all'ordine sicuramente ce l'ho
		DecimalFormat decF = new DecimalFormat("0.00");
<<<<<<< HEAD
	 %>

	<section class="ftco-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-xl-7 ftco-animate">
					<form action="ordineController" method="post" class="billing-form">
						<h3 class="mb-4 billing-heading">Dettaglio Ordine</h3>
						<div class="row align-items-end">
							<div class="col-md-6">
								<div class="form-group">
									<label for="firstname">Email</label> <input type="text"
										name="email" class="form-control" placeholder="">
=======
	%>

	<form action="ordineController" method="post" class="billing-form">
		<section class="ftco-section">
			<div class="container">
				<div class="row justify-content-center">
					<div class="col-xl-7 ftco-animate">
						<h3 class="mb-4 billing-heading">Dettaglio Ordine</h3>
						<div class="row align-items-end">
							<div class="col-md-6">
								<div class="form-group">
									<label for="firstname">Email</label> <input type="text"
										name="email" class="form-control" placeholder="">
								</div>
							</div>

							<div class="w-100"></div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="phone">Indirizzo</label> <input type="text"
										name="indirizzo" class="form-control" placeholder="">
								</div>
							</div>
							<div class="w-100"></div>

							<div class="w-100"></div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="phone">CAP</label> <input type="text" name="CAP"
										class="form-control" placeholder="">
								</div>
							</div>
							<div class="w-100"></div>

							<div class="w-100"></div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="streetaddress">Data di consegna</label> <input
										type="text" name="data" id="data" class="form-control"
										placeholder="gg/mm/aaaa"> <input type="button"
										class="btn btn-primary py-3 px-4"
										value="Cerca fasce disponibili" style="margin-top: 3%;"
										onclick="cercaFasceDisponibili(myGetElementById('data').value, myGetElementById('comboBoxFasce'))">

								</div>
							</div>

							<div class="w-100"></div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="country">Fascia Oraria</label>
									<div class="select-wrap">
										<div class="icon">
											<span class="ion-ios-arrow-down"></span>
										</div>
										<select id="comboBoxFasce" name="fascia" class="form-control"
											onchange="calcolaNuovoCosto()">

										</select>
									</div>
								</div>
							</div>
							<div class="w-100"></div>
							<div class="col-md-12">
								<div class="form-group mt-4"></div>
							</div>
						</div>
						<!-- END -->
					</div>
					<div class="col-xl-5">
						<div class="row mt-5 pt-3">
							<div class="col-md-12 d-flex mb-5">
								<div class="cart-detail cart-total p-3 p-md-4">
									<h3 class="billing-heading mb-4">Totale ordine</h3>
									<p class="d-flex">
										<span>Subtotale carrello</span> &euro;<span id="subtotale">7.55</span>
									</p>
									<p class="d-flex">
										<span>Spedizione</span> &euro;<span id="costoSpedizione"></span>
									</p>
									<hr>
									<p class="d-flex total-price">
										<span>Totale</span> &euro;<span name="totale" id="costoTotale">7.55</span>
									</p>
>>>>>>> branch 'master' of https://github.com/Caflo/lazylist-webapp
								</div>
							</div>
<<<<<<< HEAD

							<div class="w-100"></div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="phone">Indirizzo</label> <input type="text"
										name="indirizzo" class="form-control" placeholder="">
								</div>
							</div>
							<div class="w-100"></div>

							<div class="w-100"></div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="phone">CAP</label> <input type="text" name="CAP"
										class="form-control" placeholder="">
								</div>
							</div>
							<div class="w-100"></div>

							<div class="w-100"></div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="streetaddress">Data di consegna</label> <input
										type="text" id="data" class="form-control"
										placeholder="gg/mm/aaaa"> <input type="button"
										class="btn btn-primary py-3 px-4"
										value="cerca fasce disponibili"
										onclick="cercaFasceDisponibili(myGetElementById('data').value, myGetElementById('comboBoxFasce'))">

								</div>
							</div>

							<div class="w-100"></div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="country">Fascia Oraria</label>
									<div class="select-wrap">
										<div class="icon">
											<span class="ion-ios-arrow-down"></span>
										</div>
										<select id="comboBoxFasce" class="form-control"
											onchange="calcolaNuovoCosto()">

										</select>
									</div>
								</div>
							</div>
							<div class="w-100"></div>
							<div class="col-md-12">
								<div class="form-group mt-4"></div>
							</div>
						</div>
					</form>
					<!-- END -->
				</div>
				<div class="col-xl-5">
					<div class="row mt-5 pt-3">
						<div class="col-md-12 d-flex mb-5">
							<div class="cart-detail cart-total p-3 p-md-4">
								<h3 class="billing-heading mb-4">Totale ordine</h3>
								<p class="d-flex">
									<span>Subtotale carrello</span> &euro;<span id="subtotale">7.55</span>
								</p>
								<p class="d-flex">
									<span>Spedizione</span> &euro;<span id="costoSpedizione"></span>
								</p>
								<hr>
								<p class="d-flex total-price">
									<span>Totale</span> &euro;<span id="costoTotale">7.55</span>
								</p>
							</div>
						</div>
						<div class="col-md-12">
							<div class="cart-detail p-3 p-md-4">
								<h3 class="billing-heading mb-4">Metodo Di Pagamento</h3>
								<div class="form-group">
									<div class="col-md-12">
										<div class="radio">
											<label><input type="radio" name="optradio"
												class="mr-2">POS</label>
=======
							<div class="col-md-12">
								<div class="cart-detail p-3 p-md-4">
									<h3 class="billing-heading mb-4">Metodo Di Pagamento</h3>
									<div class="form-group">
										<div class="col-md-12">
											<div class="radio">
												<label><input type="radio" name="tipoPagamento"
													class="mr-2">POS</label>
											</div>
>>>>>>> branch 'master' of https://github.com/Caflo/lazylist-webapp
										</div>
									</div>
<<<<<<< HEAD
								</div>
								<div class="form-group">
									<div class="col-md-12">
										<div class="radio">
											<label><input type="radio" name="optradio"
												class="mr-2">Contante</label>
=======
									<div class="form-group">
										<div class="col-md-12">
											<div class="radio">
												<label><input type="radio" name="tipoPagamento"
													class="mr-2">Contanti</label>
											</div>
>>>>>>> branch 'master' of https://github.com/Caflo/lazylist-webapp
										</div>
									</div>
<<<<<<< HEAD
=======
									<p>
										<a href="#" class="btn btn-primary py-3 px-4">Effettua
											ordine</a>
									</p>
>>>>>>> branch 'master' of https://github.com/Caflo/lazylist-webapp
								</div>
<<<<<<< HEAD
								<p>
									<a href="#" class="btn btn-primary py-3 px-4">Effettua
										ordine</a>
								</p>
							</div>
						</div>
					</div>
				</div>
				<!-- .col-md-8 -->
			</div>
		</div>
	</section>
=======
							</div>
						</div>
					</div>
					<!-- .col-md-8 -->
				</div>
			</div>
		</section>
	</form>
>>>>>>> branch 'master' of https://github.com/Caflo/lazylist-webapp
	<!-- .section -->

	<%@ include file="fragments/footerCliente.html"%>

	<!-- loader -->
	<div id="ftco-loader" class="show fullscreen">
		<svg class="circular" width="48px" height="48px">
			<circle class="path-bg" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke="#eeeeee" />
			<circle class="path" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" /></svg>
	</div>


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
<<<<<<< HEAD
		$(document).ready(function(){
=======
		$(document).ready(function() {
>>>>>>> branch 'master' of https://github.com/Caflo/lazylist-webapp

			var quantitiy = 0;
			$('.quantity-right-plus').click(function(e) {

				// Stop acting like a button
				e.preventDefault();
				// Get the field name
				var quantity = parseInt($('#quantity').val());

				// If is not undefined

				$('#quantity').val(quantity + 1);

				// Increment

			});

			$('.quantity-left-minus').click(function(e) {
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