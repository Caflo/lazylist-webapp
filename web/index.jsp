<!DOCTYPE html>
<html lang="en">

<head>
	<title>LazyList - La spesa direttamente a casa tua</title>
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

	<script type="text/javascript">
		function addToCart(nomeProdotto){

			alert(nomeProdotto);

		}
		function myGetElementById(idElemento) {

			// elemento da restituire
			var elemento;

			// se esiste il metodo getElementById questo if sara'� 
			// diverso da false, null o undefined
			// e sara'� quindi considerato valido, come un true
			if ( document.getElementById )
				elemento = document.getElementById(idElemento);

			// altrimenti e' necessario usare un vecchio sistema
			else
				elemento = document.all[idElemento];

			// restituzione elemento
			return elemento;

			} // myGetElementById()
	</script>

</head>

<body class="goto-here">
	<%@ include file="fragments/headerCliente.html"%>
	<section class="ftco-section">
		<div class="container">
			<div class="row justify-content-center mb-3 pb-3">
				<div class="col-md-12 heading-section text-center ftco-animate">
					<span class="subheading">Prodotti Offerti</span>
					<h2 class="mb-4">Il nostro catalogo</h2>
					<p>Prodotti tipici di prima qualità direttamente a casa sua</p>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="row">
				
				<div class="col-md-6 col-lg-3 ftco-animate">
					<div class="product">
						<a href="#" class="img-prod"><img class="img-fluid" src="images/product-1.jpg"
								alt="Colorlib Template">
							<span class="status">25%</span>
							<span class="flag"><img class="flagImage" height="32" width="32"
									src="images/italia.jpg"></span>
							<div class="overlay"></div>
						</a>
						<div class="text py-3 pb-4 px-3 text-center">
							<h3 id="nomeProdotto">Peperoni</h3>
							<div class="d-flex">
								<div class="pricing">
									<p class="price"><span class="mr-2 price-dc">€2.50/Kg</span><span
											class="price-sale">€1.90</span></p>
								</div>
							</div>
							<div class="bottom-area d-flex px-3">
								<div class="m-auto d-flex">
									<form id="aggiuntaCarrello" action="carrelloController" method="POST">
										<input type="text" name="nomeProdotto" value="Peperoni" hidden/>
										<button style="background-color: Transparent; background-repeat:no-repeat; border: none; cursor:pointer; overflow: hidden;" type="submit">
											<a class="buy-now d-flex justify-content-center align-items-center mx-1">
												<span><i class="ion-ios-cart"></i></span>
											</a>
										</button>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div><!--end div-->
				<div class="col-md-6 col-lg-3 ftco-animate">
					<div class="product">
						<a href="#" class="img-prod"><img class="img-fluid" src="images/latteGranarolo.jpg"
								alt="Colorlib Template">
							<span class="flag"><img class="flagImage" height="32" width="32"
									src="images/italia.jpg"></span>
							<div class="overlay"></div>
						</a>
						<div class="text py-3 pb-4 px-3 text-center">
							<h3>Latte Granarolo</h3>
							<div class="d-flex">
								<div class="pricing">
									<p class="price"><span>€1.65</span></p>
								</div>
							</div>
							<div class="bottom-area d-flex px-3">
								<div class="m-auto d-flex">
									<form id="aggiuntaCarrello" action="nomeServlet.java" method="POST">
										<input type="text" name="nomeProdotto" value="Latte Granarolo" hidden/>
										<button style="background-color: Transparent; background-repeat:no-repeat; border: none; cursor:pointer; overflow: hidden;" type="submit">
											<a class="buy-now d-flex justify-content-center align-items-center mx-1">
												<span><i class="ion-ios-cart"></i></span>
											</a>
										</button>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div><!--end div-->
				<div class="col-md-6 col-lg-3 ftco-animate">
					<div class="product">
						<a href="#" class="img-prod"><img class="img-fluid" src="images/ancoraUno.jpg"
								alt="Colorlib Template">
							<span class="flag"><img class="flagImage" height="32" width="32"
									src="images/italia.jpg"></span>
							<div class="overlay"></div>
						</a>
						<div class="text py-3 pb-4 px-3 text-center">
							<h3><a href="#">Ancora Uno Tre Marie</a></h3>
							<div class="d-flex">
								<div class="pricing">
									<p class="price"><span>€3.00</span></p>
								</div>
							</div>
							<div class="bottom-area d-flex px-3">
								<div class="m-auto d-flex">
									<form id="aggiuntaCarrello" action="nomeServlet.java" method="POST">
										<input type="text" name="nomeProdotto" value="Ancora Uno Tre Marie" hidden/>
										<button style="background-color: Transparent; background-repeat:no-repeat; border: none; cursor:pointer; overflow: hidden;" type="submit">
											<a class="buy-now d-flex justify-content-center align-items-center mx-1">
												<span><i class="ion-ios-cart"></i></span>
											</a>
										</button>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div><!--end div-->
				<div class="col-md-6 col-lg-3 ftco-animate">
					<div class="product">
						<a href="#" class="img-prod"><img class="img-fluid" src="images/prosciuttoParma.jpg"
								alt="Colorlib Template">
							<span class="flag"><img class="flagImage" height="32" width="32"
									src="images/italia.jpg"></span>
							<div class="overlay"></div>
						</a>
						<div class="text py-3 pb-4 px-3 text-center">
							<h3><a href="#">Prosciutto di Parma</a></h3>
							<div class="d-flex">
								<div class="pricing">
									<p class="price"><span>€4.00</span></p>
								</div>
							</div>
							<div class="bottom-area d-flex px-3">
								<div class="m-auto d-flex">
									<a href="#"
										class="add-to-cart d-flex justify-content-center align-items-center text-center">
										<span><i class="ion-ios-menu"></i></span>
									</a>
									<a href="#" class="buy-now d-flex justify-content-center align-items-center mx-1">
										<span><i class="ion-ios-cart"></i></span>
									</a>
									<a href="#" class="heart d-flex justify-content-center align-items-center ">
										<span><i class="ion-ios-heart"></i></span>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div><!--end div-->
				<div class="col-md-6 col-lg-3 ftco-animate">
					<div class="product">
						<a href="#" class="img-prod"><img class="img-fluid" src="images/product-5.jpg"
								alt="Colorlib Template">
							<span class="status">30%</span>
							<span class="flag"><img class="flagImage" height="32" width="32"
									src="images/italia.jpg"></span>
							<div class="overlay"></div>
						</a>
						<div class="text py-3 pb-4 px-3 text-center">
							<h3><a href="#">Pomodoro</a></h3>
							<div class="d-flex">
								<div class="pricing">
									<p class="price"><span class="mr-2 price-dc">€3.00</span><span
											class="price-sale">€2.00</span></p>
								</div>
							</div>
							<div class="bottom-area d-flex px-3">
								<div class="m-auto d-flex">
									<a href="#" class="buy-now d-flex justify-content-center align-items-center mx-1">
										<span><i class="ion-ios-cart"></i></span>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div><!--end div-->
				<div class="col-md-6 col-lg-3 ftco-animate">
					<div class="product">
						<a href="#" class="img-prod"><img class="img-fluid" src="images/salsicce.jpg"
								alt="Colorlib Template">
							<span class="flag"><img class="flagImage" height="32" width="32"
									src="images/italia.jpg"></span>
							<div class="overlay"></div>
						</a>
						<div class="text py-3 pb-4 px-3 text-center">
							<h3><a href="#">Salsicce</a></h3>
							<div class="d-flex">
								<div class="pricing">
									<p class="price"><span>€6.00</span></p>
								</div>
							</div>
							<div class="bottom-area d-flex px-3">
								<div class="m-auto d-flex">
									<a href="#"
										class="add-to-cart d-flex justify-content-center align-items-center text-center">
										<span><i class="ion-ios-menu"></i></span>
									</a>
									<a href="#" class="buy-now d-flex justify-content-center align-items-center mx-1">
										<span><i class="ion-ios-cart"></i></span>
									</a>
									<a href="#" class="heart d-flex justify-content-center align-items-center ">
										<span><i class="ion-ios-heart"></i></span>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div><!--end div-->
				<div class="col-md-6 col-lg-3 ftco-animate">
					<div class="product">
						<a href="#" class="img-prod"><img class="img-fluid" src="images/acquaSanBernardo.jpg"
								alt="Colorlib Template">
							<span class="flag"><img class="flagImage" height="32" width="32"
									src="images/italia.jpg"></span>
							<div class="overlay"></div>
						</a>
						<div class="text py-3 pb-4 px-3 text-center">
							<h3><a href="#">Acqua San Bernardo (x6)</a></h3>
							<div class="d-flex">
								<div class="pricing">
									<p class="price"><span>€1.75</span></p>
								</div>
							</div>
							<div class="bottom-area d-flex px-3">
								<div class="m-auto d-flex">
									<a href="#"
										class="add-to-cart d-flex justify-content-center align-items-center text-center">
										<span><i class="ion-ios-menu"></i></span>
									</a>
									<a href="#" class="buy-now d-flex justify-content-center align-items-center mx-1">
										<span><i class="ion-ios-cart"></i></span>
									</a>
									<a href="#" class="heart d-flex justify-content-center align-items-center ">
										<span><i class="ion-ios-heart"></i></span>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div><!--end div-->
				<div class="col-md-6 col-lg-3 ftco-animate">
					<div class="product">
						<!-- <span class="flag"><img class="flagImage" src="images/italia.jpg"></span> -->
						<a href="#" class="img-prod"><img class="img-fluid" src="images/pane.jpg"
								alt="Colorlib Template">
							<span class="flag"><img class="flagImage" height="32" width="32"
									src="images/italia.jpg"></span>
							<span class="status">30%</span>
							<div class="overlay"></div>
						</a>
						<div class="text py-3 pb-4 px-3 text-center">
							<h3><a href="#">Pane Pugliese</a></h3>
							<p>500gr</p>
							<div class="d-flex">
								<div class="pricing">
									<p class="price"><span>€2.30</span></p>
								</div>
							</div>
							<div class="bottom-area d-flex px-3">
								<div class="m-auto d-flex">
									<a href="#"
										class="add-to-cart d-flex justify-content-center align-items-center text-center">
										<span><i class="ion-ios-menu"></i></span>
									</a>
									<a href="#" class="buy-now d-flex justify-content-center align-items-center mx-1">
										<span><i class="ion-ios-cart"></i></span>
									</a>
									<a href="#" class="heart d-flex justify-content-center align-items-center ">
										<span><i class="ion-ios-heart"></i></span>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div> <!--end div-->
			</div>
		</div>
	</section>

	<%@ include file="fragments/footerCliente.html"%>
	<hr>


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

</body>

</html>