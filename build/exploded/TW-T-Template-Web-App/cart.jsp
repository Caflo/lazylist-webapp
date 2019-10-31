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
    <title>Carrello</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800&display=swap" rel="stylesheet">
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
	<%@ include file="fragments/headerCliente.jsp"%>
    <section class="ftco-section ftco-cart">
			<div class="container">
				<div class="row">
	    			<div class="col-md-12 ftco-animate">
	    				<div class="cart-list">
		    				<table class="table">
							    <thead class="thead-primary">
							      <tr class="text-center">
							        <th>&nbsp;</th>
							        <th>&nbsp;</th>
							        <th>Nome Prodotto</th>
							        <th>Prezzo unitario</th>
							        <th>Quantita</th>
									<th>Sconto</th>
									<th>Subtotale riga</th>
							      </tr>
							    </thead>
							    <tbody>
							    <%
							    	ReadManager crud = new ReadManager();
	    						    Carrello c = crud.readCarrello();
	    							DecimalFormat decF = new DecimalFormat("0.00");
	    							DecimalFormat decFSconto = new DecimalFormat("#.##");

	    					    	if (c != null) { //altrimenti niente
	    								for (RigaCarrello r : c.getRighe()) {
							    %>
							      <tr class="text-center">
							        <td class="product-remove">
										<form action="carrelloController" method="POST">
											<input type="text" name="nomeProdotto" value="<%= r.getNomeProdotto() %>" hidden/>					
											<button style="background-color: Transparent; background-repeat:no-repeat; border: none; cursor:pointer; overflow: hidden; width: 27.23px; height: 30;" type="submit" name="tipoOperazione" value="eliminaDalCarrello">
												<a>
													<span class="ion-ios-close"></span>
												</a>
											</button>									
										</form>
									</td>
							        
							        <td class="image-prod"><div class="img" style="background-image:url(images/foodImage.png);"></div></td>
							        
							        <td class="product-name">
							        	<h3><%= r.getNomeProdotto() %></h3>
							        </td>
							        
							        <td class="price">&euro; <%= decF.format(r.getPrezzoUnitario()) %></td>
							        
							        <td class="quantity">
							        	<div class="input-group mb-3">
							        	<form action="carrelloController" method="POST">
							        		<input type="text" name="nomeProdotto" value="<%= r.getNomeProdotto() %>" hidden/>					        	
						             		<input type="text" name="quantity" class="quantity form-control input-number" value="<%= r.getQuantitaScelta() %>" >
						             		<br />
											<button class="btn btn-primary py-3 px-4" type="submit" name="tipoOperazione" value="modificaQuantita">Modifica
											</button>									
										</form>
						          	</div>
						          </td>
								  	<td class="sconto"><%= decFSconto.format(r.getSconto() * 100) %> &percnt;</td>
								  	<!--  Subtotale riga: non esiste nel modello del dominio e ce lo calcoliamo -->
							        <td class="total">&euro; <%= decF.format(r.getPrezzoUnitario() * (1 - r.getSconto()) * r.getQuantitaScelta()) %></td>
							      </tr><!-- END TR-->
							      
							      <%
							      		}
							    	}
							      %>
							      
							    </tbody>
							  </table>
						  </div>
					</div>
    			</div>
    			<div class="col-lg-4 mt-5 cart-wrap ftco-animate">
    				<div class="cart-total mb-3">
    					<p class="d-flex total-price">
    						<span>Subtotale carrello</span>
    						<span>&euro; <%= decF.format(c.getSubTotale()) %></span>
    					</p>
    				</div>
    				<p><a href="ordineController" class="btn btn-primary py-3 px-4">Procedi all'ordine</a></p>
    			</div>
			</div>
		</section>
   
	<%@ include file="fragments/footerCliente.html"%>
   
  <!-- loader -->
  <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>


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
  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
  <script src="js/google-map.js"></script>
  <script src="js/main.js"></script>

  <script>
		$(document).ready(function(){

		var quantitiy=0;
		   $('.quantity-right-plus').click(function(e){
		        
		        // Stop acting like a button
		        e.preventDefault();
		        // Get the field name
		        var quantity = parseInt($('#quantity').val());
		        
		        // If is not undefined
		            
		            $('#quantity').val(quantity + 1);

		          
		            // Increment
		        
		    });

		     $('.quantity-left-minus').click(function(e){
		        // Stop acting like a button
		        e.preventDefault();
		        // Get the field name
		        var quantity = parseInt($('#quantity').val());
		        
		        // If is not undefined
		      
		            // Increment
		            if(quantity>0){
		            $('#quantity').val(quantity - 1);
		            }
		    });
		    
		});
	</script>
    
  </body>
</html>