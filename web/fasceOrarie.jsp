<!DOCTYPE html>
<html lang="en">
<!-- import di classi Java -->
<%@ page import="model.prodottoECarrello.*"%>
<%@ page import="model.ordine.*"%>
<%@ page import="crud.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.time.format.*"%>
<%@ page import="java.time.DayOfWeek"%>

<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<head>
	<title>Gestione Ordini</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800&display=swap"
		rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Lora:400,400i,700,700i&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Amatic+SC:400,700&display=swap" rel="stylesheet">

	<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="assets/css/bootstrap-responsive.css" rel="stylesheet">

	<link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
	<link rel="stylesheet" href="css/animate.css">
	
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" 
	crossorigin="anonymous">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css" integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ" crossorigin="anonymous">

	<!-- Latest compiled and minified JavaScript -->
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" 
	crossorigin="anonymous"></script>

	<link rel="stylesheet" href="css/owl.carousel.min.css">
	<link rel="stylesheet" href="css/owl.theme.default.min.css">
	<link rel="stylesheet" href="css/magnific-popup.css">

	<link rel="stylesheet" href="css/aos.css">

	<link rel="stylesheet" href="css/ionicons.min.css">

	<link rel="stylesheet" href="css/bootstrap-datepicker.css">
	<link rel="stylesheet" href="css/jquery.timepicker.css">

	<link rel="stylesheet" href="css/flaticon.css">

	<link rel="stylesheet" href="css/flaticon.css">
	<link rel="stylesheet" href="css/icomoon.css">
	<link rel="stylesheet" href="css/style.css">
		
</head>


<body class="goto-here">
	<%@ include file="fragments/headerMagazzino.html"%>



	<br><br>
	
	<section class="ftco-section ftco-cart">
		<div class="container-fluid" id="cont">
			<div class="row">
				<div class="col-sm">Giorno</div>
				<div class="col-sm">Ora inizio</div>
				<div class="col-sm">Ora fine</div>
				<div class="col-sm">Costo di consegna</div>
			</div>
			<%
				ReadManager crud = new ReadManager();
					DecimalFormat decF = new DecimalFormat("0.00");
					Set<FasciaOraria> fasce = crud.readFasceOrarie();
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					final DateTimeFormatter dtf = new DateTimeFormatterBuilder() //mi serve per avere Giovedi al posto di Thursday
					        .appendOptional(DateTimeFormatter.ofPattern("EEEE"))
					        .toFormatter(Locale.ITALIAN);
					
					//Per ogni giorno della settimana
					for (int i = 1; i <= 7; i++) {
						String giorno = DayOfWeek.of(i).getDisplayName(TextStyle.FULL, Locale.ITALY);
						Set<FasciaOraria> fasceGiorno = crud.readFasceOrariePerGiorno(giorno);
						for (FasciaOraria f : fasceGiorno) {
			%>
					<form action="gestioneFasceOrarieController" method="post">
						<div class="row">
							
							<!-- info nascosta -->
							<input type="text" name="id" value="<% f.get_id().toString() %>" hidden/>
							
							<div class="col-sm">
								<input type="text" name="oraInizio" value="<% f.getOraInizio() %>"/>
							</div>
							
							<div class="col-sm">
								<input type="text" name="oraFine" value="<% f.getOraFine() %>"/>								
							</div>
							
							<div class="col-sm">
								<input type="text" name="costoConsegna" value="<% f.getCostoConsegna() %>"/>								
							</div>
							
						</div>
						
					</form>
			<%
					}
				}
			%>
				
			
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
	<script src="scripts/utils.js"></script>
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