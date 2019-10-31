<!DOCTYPE html>
<html lang="en">
<!-- import di classi Java -->
<%@ page import="model.prodottoECarrello.*"%>
<%@ page import="java.util.*"%>
<%@ page import="crud.*"%>
<%@ page import="java.text.DecimalFormat"%>

<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<head>
	<title>Gestione Prodotti</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800&display=swap"
		rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Lora:400,400i,700,700i&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Amatic+SC:400,700&display=swap" rel="stylesheet">

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
	<link rel="stylesheet" href="css/icomoon.css">
	<link rel="stylesheet" href="css/style.css">
</head>

<body class="goto-here">
	
	<%@ include file="fragments/headerMagazzino.html"%>	

	<br><br>
	
	<section class="ftco-section ftco-cart">
		<div class="container" id="cont">
			<div class="row">
				<div class="col-sm">Info prodotto</div>
				<div class="col-sm">Provenienza</div>
				<div class="col-sm">Prezzo</div>
				<div class="col-sm">Sconto</div>
				<div class="col-sm">Disponibilita'</div>
				<div class="col-sm">Rimanenze</div>
				<div class="col-sm">Azione</div>
			</div>
						
			<%
										ReadManager crud = new ReadManager();
											Magazzino m = (Magazzino) crud.readProdotti();
											DecimalFormat decF = new DecimalFormat("0.00");
											for(Prodotto p : m.getProdotti()) {
									%>
			<!-- La tabella l'ho fatta con bootstrap dato che la table in HTML non ammette il form, quindi ho dovuto rifare la pagina -->
			<form action="gestioneProdottiController" method="post">					
				<div class="row" style="border: 1px solid; border-color: #79eb15">
				
					<!-- l'id del prodotto mi serve, lo metto nascosto -->
					<input type="text" name="id" value=<%= p.get_id() %> hidden/></p>
					
					<div class="col-sm">
						<p style="text-align: left;">Nome: <input type="text" name="nome" style="border-radius: 10px; width: 100%" value="<%= p.getNome() %>"/></p>
						<p style="text-align: left;">Categoria: <input type="text" name="categoria" style="border-radius: 10px; width: 100%" value="<%= p.getCategoria() %>"/></p>
						<p style="text-align: left;">Marca: <input type="text" name="marca" style="border-radius: 10px; width: 100%" value="<%= p.getMarca() %>"/></p>
					</div>
					
					<div class="col-sm" style="margin-top: 2.3%">
						<input type="text" name="provenienza" style="border-radius: 10px; width: 80%" value="<%= p.getProvenienza() %>"/></p>
					</div>
					
					<div class="col-sm" style="margin-top: 2.3%">
						&euro;<input type="text" name="prezzo" style="border-radius: 10px; width: 70%;" value="<%= decF.format(p.getPrezzo()) %>"/>
					</div>
					
					<div class="col-sm" style="margin-top: 2.3%" >
						<input type="text" name="sconto" style="border-radius: 10px; width: 70%; text-align: right" value="<%= p.getSconto() * 100 %>"/>&nbsp;%
					</div>
					
					<div class="col-sm" style="margin-top: 2.3%">
					
							<input type="checkbox" name="disponibile" value="<%= p.getDisponibile().booleanValue() %>" checked/>
					
					
					</div>
					
					<div class="col-sm" style="margin-top: 2.3%">
						<input type="text" name="unitaDisponibili" style="border-radius: 10px; width: 70%; text-align: right" value="<%= p.getUnitaDisponibili() %>"/>
					</div>
					
					<div class="col-sm" style="margin-top: 2.3%">
						<input type="submit" name="tipoOperazione" value="Salva" style="border-radius: 10px; background-color: #82ae46; color: white;"/>
						<input type="submit" name="tipoOperazione" value="Elimina" style="border-radius: 10px; background-color: #82ae46; color: white;" value="Elimina"/>
					</div>				
				</div> <!-- END ROW -->
			</form>
			<%
				}
			%>
		</div>
		<div>
			<input type="button" onclick="addNewLine()" style="border-radius: 10px; width: 70%; margin-bottom:5%; margin-left: 15%; background-color: #82ae46; color: white;" value="Aggiungi un Prodotto"/>
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
		function addNewLine() {
			//nome, categoria, marca
			var col1 = '<div class="col-sm"><p style="text-align: left;">Nome: <input type="text" name="nome" style="border-radius: 10px; width: 100%" "/></p> <p style="text-align: left;">Categoria: <input type="text" name="categoria" style="border-radius: 10px; width: 100%" value=""/></p> <p style="text-align: left;">Marca: <input type="text" name="marca" style="border-radius: 10px; width: 100%" value=""/></p> </div>';
			//provenienza
			var col2 = '<div class="col-sm" style="margin-top: 2.3%"><input type="text" name="provenienza" style="border-radius: 10px; width: 80%" value=""/></p> </div>';
			//prezzo
			var col3 = '<div class="col-sm" style="margin-top: 2.3%">&euro;<input type="text" name="prezzo" style="border-radius: 10px; width: 70%;" value=""/></div>';
			//sconto
			var col4 = '<div class="col-sm" style="margin-top: 2.3%" ><input type="text" name="sconto" style="border-radius: 10px; width: 70%; text-align: right" value=""/>&nbsp;%</div>';
			//disponibilita'
			var col5 = '<div class="col-sm" style="margin-top: 2.3%"><input type="checkbox" name="disponibile" value="" checked/></div>';
			//rimanenze
			var col6 = '<div class="col-sm" style="margin-top: 2.3%"><input type="text" name="unitaDisponibili" style="border-radius: 10px; width: 70%; text-align: right" value=""/></div>';
			//azione
			var col7 = '<div class="col-sm" style="margin-top: 2.3%"><input type="submit" name="tipoOperazione" value="Salva" style="border-radius: 10px; background-color: #82ae46; color: white;"/><input type="submit" name="tipoOperazione" value="Elimina" style="border-radius: 10px; background-color: #82ae46; color: white;" value="Elimina"/></div>';
		
			var form = document.createElement('form');
			form.action = "gestioneProdottiController";
			form.method = "post";
			
			var div = document.createElement('div');
			div.className = "row";
			div.style = "border: 1px solid; border-color: #79eb15";
			div.innerHTML = col1 + col2 + col3 + col4 + col5 + col6 + col7;
			form.appendChild(div);
			myGetElementById('cont').appendChild(form);
		}
	</script>
	
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