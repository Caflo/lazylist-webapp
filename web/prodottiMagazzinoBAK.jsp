<!DOCTYPE html>
<html lang="en">
<!-- import di classi Java -->
<%@ page import="model.prodottoECarrello.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.DecimalFormat"%>

<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- PAGINA HTML -->
<head>
<title>Gestione Prodotti</title>
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
						<table class="table" id="tabella">
							<thead class="thead-primary">
								<tr class="text-center">
									<th>Info Prodotto</th>
									<th>Altri dettagli</th>
									<th>Prezzo</th>
									<th>Sconto</th>
									<th>Disponibilita'</th>
									<th>Rimanenze</th>
									<th>Azione</th>
								</tr>
							</thead>
							<form action="gestioneProdottiController" method="POST">
								<div>
							<tbody id="lineeProdotti">
								<%
									Magazzino m = (Magazzino) session.getAttribute("prodottiMagazzino");
									DecimalFormat decF = new DecimalFormat("0.00");
									for(Prodotto p : m.getProdotti()){
								%>
								<!-- Mi serve per raccogliere l'id, non si vede -->

								<tr class="text-center">
									<td class="price" style="width: 20%;">
										<p style="text-align: left;">
											Nome:&emsp;<input type="text" name="nome"
												style="border-radius: 10px; width: 50%"
												value="<%= p.getNome()%>" />
										</p>
										<p style="text-align: left;">
											Categoria:&emsp;<input type="text" name="categoria"
												style="border-radius: 10px; width: 50%"
												value="<%= p.getCategoria() %>" />
										</p>
										<p style="text-align: left;">
											Marca:&emsp;<input type="text" name="marca"
												style="border-radius: 10px; width: 50%"
												value="<%= p.getMarca() %>" />
										</p>
									</td>
									<td class="price" style="width: 15%;"><a
										class="nav-link dropdown-toggle" href="#" id="dropdown04"
										data-toggle="dropdown" aria-haspopup="true"
										aria-expanded="false">Altri Dettagli</a>
										<div class="dropdown-menu" aria-labelledby="dropdown04">
											<p style="text-align: left;">
												Provenienza:&emsp;<input type="text" name="provenienza"
													style="border-radius: 10px;"
													value="<%= p.getProvenienza() %>" />
											</p>
										</div></td>
									<td class="price" style="width: 12%;">&euro;<input
										type="text" name="prezzo"
										style="border-radius: 10px; width: 50%;"
										value="<%= decF.format(p.getPrezzo()) %>" /></td>
									<td class="price" style="width: 12%;"><input type="text"
										name="sconto"
										style="border-radius: 10px; width: 50%; text-align: right"
										value="<%= p.getSconto() * 100 %>" />&nbsp;%</td>
									<%if(p.getDisponibile() == true) {%>
									<td class="price"><input id="disponibilita"
										name="disponibilita" type="checkbox" checked /></td>
									<% }else{%>
									<td class="price"><input id="disponibilita"
										name="disponibilita" type="checkbox" /></td>
									<%} %>
									<td class="price" style="width: 12%;"><input type="text"
										name="unitaDisponibili"
										style="border-radius: 10px; width: 50%; text-align: right"
										value="<%= p.getUnitaDisponibili() %>" /></td>
									<td><input type="submit"
										style="border-radius: 10px; background-color: #82ae46; color: white;"
										name="tipoOperazione" value="Salva" /> <input type="submit"
										style="border-radius: 10px; background-color: #82ae46; color: white;"
										name="tipoOperazione" value="Elimina" /></td>
								</tr>
								<!-- END TR-->

								<%
								}
								%>
							</tbody>
							</div>
							</form>
						</table>
						<div>
							<input type="button"
								style="border-radius: 10px; width: 16%; margin-bottom: 5%; margin-left: 42%; background-color: #82ae46; color: white;"
								value="Aggiungi un Prodotto" onclick="addNewLine()" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

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
		
		function addNewLine() {
			var table = document.getElementById("tabella");
			var lastRow = table.rows.length;
		  	var row = table.insertRow(lastRow);
		  	var cell0 = row.insertCell(0);
		  	var cell1 = row.insertCell(1);
		  	var cell2 = row.insertCell(2);
		  	var cell3 = row.insertCell(3);
		  	var cell4 = row.insertCell(4);
		  	var cell5 = row.insertCell(5);
		  	var cell6 = row.insertCell(6);
		  	
		  	//olio di gomito
		  	cell0.innerHTML = "<td class=\"price\" style=\"width: 20%;\"> <p style=\"text-align: left;\">Nome:&emsp;<input type=\"text\" name=\"nome\" style=\"border-radius: 10px; width: 50%\" value=\"\"/></p><p style=\"text-align: left;\">Categoria:&emsp;<input type=\"text\" name=\"categoria\" style=\"border-radius: 10px; width: 50%\" value=\"\"/></p><p style=\"text-align: left;\">Marca:&emsp;<input type=\"text\" name=\"marca\" style=\"border-radius: 10px; width: 50%\" value=\"\"/></p></td>"
		  	cell1.innerHTML = "<td class=\"price\" style=\"width: 15%;\"> <a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"dropdown04\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">Altri Dettagli</a><div class=\"dropdown-menu\" aria-labelledby=\"dropdown04\">\n <p style=\"text-align: left;\">Provenienza:&emsp;<input type=\"text\" name=\"provenienza\" style=\"border-radius: 10px;\" value=\"\"/></p></div>\n </td>";
			cell2.innerHTML = "<td class=\"price\" style=\"width: 12%;\">&euro;<input type=\"text\" name=\"prezzo\" style=\"border-radius: 10px; width: 50%;\" value=\"\"/></td>"
			cell3.innerHTML = "<td class=\"price\" style=\"width: 12%;\"><input type=\"text\" name=\"sconto\" style=\"border-radius: 10px; width: 50%; text-align: right\" value=\"\"/>&nbsp;%</td>"
			cell4.innerHTML = "<td class=\"price\"><input id=\"disponibilita\" type=\"checkbox\" name=\"disponibilita\" checked/></td>"
			cell5.innerHTML = "<td class=\"price\" style=\"width: 12%;\"><input type=\"text\" name=\"unitaDisponibili\" style=\"border-radius: 10px; width: 50%; text-align: right\" value=\"\"/></td>"
			cell6.innerHTML = "<td>\n <input type=\"submit\" style=\"border-radius: 10px; background-color: #82ae46; color: white;\" name=\"tipoOperazione\" value=\"Salva\"/><input type=\"submit\" style=\"border-radius: 10px; background-color: #82ae46; color: white;\" name=\"tipoOperazione\" value=\"Elimina\"/></td>\n "	
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