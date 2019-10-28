<!DOCTYPE html>
<html lang="en">

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
									<th>Info Prodotto</th>
                                    <th>Altri dettagli</th>
                                    <th>Prezzo</th>
                                    <th>Sconto</th>
                                    <th>Disponibilità</th>
                                    <th>Rimanenze</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<tr class="text-center">
                                    <td class="price" style="width: 20%;">
                                        <p style="text-align: left;">Nome:&emsp;<input type="text" style="border-radius: 10px; width: 50%" value="Banana"/></p>
                                        <p style="text-align: left;">Categoria:&emsp;<input type="text" style="border-radius: 10px; width: 50%" value="Frutta"/></p>
                                        <p style="text-align: left;">Marca:&emsp;<input type="text" style="border-radius: 10px; width: 50%" value="Chiquita"/></p>
                                    </td>
                                    <td class="price" style="width: 15%;">
                                        <a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Altri Dettagli</a>
                                        <div class="dropdown-menu" aria-labelledby="dropdown04">
                                            <p style="text-align: left;">Provenienza:&emsp;<input type="text" style="border-radius: 10px;" value="Italia"/></p>
                                            <p style="text-align: left;">Peso:&emsp;<input type="text" style="border-radius: 10px;" value="500gr"/></p>
                                        </div>
                                    </td>
                                    <td class="price" style="width: 12%;">€&nbsp;<input type="text" style="border-radius: 10px; width: 50%;" value="3.50"/></td>                                    
                                    <td class="price" style="width: 12%;"><input type="text" style="border-radius: 10px; width: 50%; text-align: right" value="20"/>&nbsp;%</td> 
                                    <td class="price"><input type="checkbox" checked/></td>
                                    <td class="price" style="width: 12%;"><input type="text" style="border-radius: 10px; width: 50%; text-align: right" value="15"/></td> 
									<td><input type="button" style="border-radius: 10px; background-color: #82ae46; color: white;" value="Salva"/><input type="button" style="border-radius: 10px; background-color: #82ae46; color: white;" value="Elimina"/></td>
								</tr><!-- END TR-->
                                <tr class="text-center">
                                        <td class="price" style="width: 20%;">
                                            <p style="text-align: left;">Nome:&emsp;<input type="text" style="border-radius: 10px; width: 50%" value="Prosciutto Crudo di Parma"/></p>
                                            <p style="text-align: left;">Categoria:&emsp;<input type="text" style="border-radius: 10px; width: 50%" value="Salumi"/></p>
                                            <p style="text-align: left;">Marca:&emsp;<input type="text" style="border-radius: 10px; width: 50%" value="Citterio"/></p>
                                        </td>
                                        <td class="price" style="width: 15%;">
                                            <a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Altri Dettagli</a>
                                            <div class="dropdown-menu" aria-labelledby="dropdown04">
                                                <p style="text-align: left;">Provenienza:&emsp;<input type="text" style="border-radius: 10px;" value="Italia"/></p>
                                                <p style="text-align: left;">Peso:&emsp;<input type="text" style="border-radius: 10px;" value="200gr"/></p>
                                            </div>
                                        </td>
                                        <td class="price" style="width: 12%;">€&nbsp;<input type="text" style="border-radius: 10px; width: 50%;" value="3.50"/></td>                                    
                                        <td class="price" style="width: 12%;"><input type="text" style="border-radius: 10px; width: 50%; text-align: right" value=""/>&nbsp;%</td> 
                                        <td class="price"><input type="checkbox" checked/></td>
                                        <td class="price" style="width: 12%;"><input type="text" style="border-radius: 10px; width: 50%; text-align: right;" value="20"/></td> 
                                        <td><input type="button" style="border-radius: 10px; background-color: #82ae46; color: white;" value="Salva"/><input type="button" style="border-radius: 10px; background-color: #82ae46; color: white;" value="Elimina"/></td>
                                    </tr><!-- END TR-->
                                    <tr class="text-center">
                                            <td class="price" style="width: 20%;">
                                                <p style="text-align: left;">Nome:&emsp;<input type="text" style="border-radius: 10px; width: 50%" value="Mozzarella di bufala"/></p>
                                                <p style="text-align: left;">Categoria:&emsp;<input type="text" style="border-radius: 10px; width: 50%" value="Laticini"/></p>
                                                <p style="text-align: left;">Marca:&emsp;<input type="text" style="border-radius: 10px; width: 50%" value="Vallelata"/></p>
                                            </td>
                                            <td class="price" style="width: 15%;">
                                                <a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Altri Dettagli</a>
                                                <div class="dropdown-menu" aria-labelledby="dropdown04">
                                                    <p style="text-align: left;">Provenienza:&emsp;<input type="text" style="border-radius: 10px;" value="Italia"/></p>
                                                    <p style="text-align: left;">Peso:&emsp;<input type="text" style="border-radius: 10px;" value="300gr"/></p>
                                                </div>
                                            </td>
                                            <td class="price" style="width: 12%;">€&nbsp;<input type="text" style="border-radius: 10px; width: 50%;" value="4.50"/></td>                                    
                                            <td class="price" style="width: 12%;"><input type="text" style="border-radius: 10px; width: 50%; text-align: right" value=""/>&nbsp;%</td> 
                                            <td class="price"><input type="checkbox" checked/></td>
                                            <td class="price" style="width: 12%;"><input type="text" style="border-radius: 10px; width: 50%; text-align: right" value="10"/></td> 
                                            <td><input type="button" style="border-radius: 10px; background-color: #82ae46; color: white;" value="Salva"/><input type="button" style="border-radius: 10px; background-color: #82ae46; color: white;" value="Elimina"/></td>
                                        </tr><!-- END TR-->
							</tbody>
						</table>
						<div>
							<input type="button"  style="border-radius: 10px; width: 16%; margin-bottom:5%; margin-left: 42%; background-color: #82ae46; color: white;" value="Aggiungi un Prodotto"/>
						</div>
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