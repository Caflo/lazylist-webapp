<!DOCTYPE html>
<html lang="en">
<!-- import di classi Java -->
<%@ page import="model.prodottoECarrello.*"%>
<%@ page import="java.util.*"%>
<%@ page import="crud.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.stream.*"%>

<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- PAGINA HTML -->

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
	
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
	
	<!-- AJAX -->
	<script src="scripts/aggiungiAlCarrello.js" type="text/javascript"></script>
	<!-- AJAX -->
	<script src="scripts/utils.js" type="text/javascript"></script>

	<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>	

	<script type="text/javascript">
		function addToCart(nomeProdotto){
			alert(nomeProdotto);
		}
		function myGetElementById(idElemento) {
			// elemento da restituire
			var elemento;
			// se esiste il metodo getElementById questo if sara'ï¿½ 
			// diverso da false, null o undefined
			// e sara'ï¿½ quindi considerato valido, come un true
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

	<%
		ReadManager crud = new ReadManager();
		Catalogo c = new Catalogo();
		Set<Prodotto> prodotti = (Set<Prodotto>) session.getAttribute("prodottiFiltrati");
		
		if (prodotti == null) {
			c = crud.readCatalogo();
		}
		else
			c.setProdotti(prodotti);
		DecimalFormat decF = new DecimalFormat("0.00");
		DecimalFormat decFSconto = new DecimalFormat("#.##");
		Set<RigaCarrello> righe = crud.readCarrello().getRighe();
		Integer totCarrello = 0;
		for (RigaCarrello r : righe) {
			if (r.getQuantitaScelta() != null)
				totCarrello += r.getQuantitaScelta();
		}
		
		session.setAttribute("totCarrello", totCarrello);
	%>
	
	<jsp:include page="fragments/headerCliente.jsp" />
	
	<section class="ftco-section">
		<div class="container">
			<div class="row justify-content-center mb-3 pb-3">
				<div class="col-md-12 heading-section text-center ftco-animate">
					<span class="subheading">Prodotti Offerti</span>
					<h2 class="mb-4">Il nostro catalogo</h2>
					<p>Prodotti tipici di prima qualit&agrave;  direttamente a casa tua</p>	
					</div>
			</div>
		</div>
		<div class="container">
			<div class="row justify-content-center mb-3 pb-3">
				<form action="visualizzazioneProdottiController" method="post">
					<button class="btn btn-primary btn-space" name="filtro" value="Tutti">Tutti</button>
				</form>	
				
				<%
					Set<String> categorieDiverse = new HashSet<>();
					for (Prodotto p : crud.readCatalogo().getProdotti())
						categorieDiverse.add(p.getCategoria());
					for (String s : categorieDiverse) {
				%>
					<form action="visualizzazioneProdottiController" method="post">
						<button class="btn btn-primary btn-space" name="filtro" value="<%= s %>"><%= s %></button>
					</form>
				<%
					}
				%>
				
			</div>
				
		</div>
		
		<div class="container">
		
			<!-- Modal -->
			  <div class="modal fade" id="alertAddChart" role="dialog">
			    <div class="modal-dialog">
			    
			      <!-- Modal content-->
			      <div class="modal-content">
			        <div class="modal-header">
			          <h4 class="modal-title">Carrello</h4>
			        </div>
			        <div class="modal-body">
			          <p id="alertText"></p>
			        </div>
			        <div class="modal-footer">
			          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			        </div>
			      </div>
			      
			    </div>
			  </div>
			
			
			<div class="row">
				
			<%
				for (Prodotto p : c.getProdotti()) {
			%>
	
				<div class="col-md-6 col-lg-3 ftco-animate">
					<div class="product">
						<a href="#" class="img-prod"><img class="img-fluid" src="images/foodImage.png"
								alt="Colorlib Template">
							<%
								if (p.getSconto() != 0) {
							%>
									<span class="status"><%= decFSconto.format(p.getSconto() * 100) %>&percnt;</span>
							
							<%
								}
							%>
							
							<span class="flag"><img class="flagImage" height="32" width="32"
									src="images/italia.jpg"></span>
							<div class="overlay"></div>
						</a>
						<div class="text py-3 pb-4 px-3 text-center">
							<h3 id="nomeProdotto"><%= p.getNome() %></h3>
							<p><i><%= p.getMarca() %></i></p>							
							<div class="d-flex">
								<div class="pricing">
								<%
									if (p.getSconto() != 0) {
								%>
										<p class="price"><span class="mr-2 price-dc"><%= decF.format(p.getPrezzo()) %></span>
								<%
									}
								%>
									<span class="price-sale">&euro;<%= decF.format(p.getPrezzo() * (1 - p.getSconto())) %></span></p>
								</div>
							</div>
							<div class="bottom-area d-flex px-3">
								<div class="m-auto d-flex">
									<input type="text" name="nomeProdotto" value="<%= p.getNome() %>" hidden/>
									<button id="addChartButton" class="addChartButton" style="background-color: Transparent; background-repeat:no-repeat; border: none; cursor:pointer; overflow: hidden;" onclick="aggiungiAlCarrello('<%= p.getNome() %>', myGetElementById('totCarrello'))" name="tipoOperazione" value="aggiungiAlCarrello">
										<a class="buy-now d-flex justify-content-center align-items-center mx-1">
											<span><i class="ion-ios-cart"></i></span>
										</a>
									</button>
								</div>
							</div>
						</div>
					</div>
				</div><!--end div-->
			
				<%
					}
				%>
			</div> <!-- end row -->
		</div> <!-- end container -->
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

	<script>
		$(document).ready(function(){
		  $('.addChartButton').click(function(){
		    $("#alertText").html("Prodotto aggiunto al carrello");
		      $('#alertAddChart').modal("show");
		      setTimeout(function(){
		          $("#alertAddChart").modal("hide");
		      }, 1000);
		    });
		  });
	</script>
</body>

</html>
