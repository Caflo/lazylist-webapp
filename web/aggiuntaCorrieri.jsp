<!DOCTYPE html>
<html lang="en">

<head>
  <title>Aggiungi Corriere</title>
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

  <section class="ftco-section contact-section bg-light">
    <div class="container">
      <div class="row d-flex mb-5 contact-info">
        <div class="infoAccount">
	        <form action="servletAggiuntaCorrieri" method="POST">
	            <div style="margin-top: 5%;">
	                <p>Nome del corriere:&emsp;<input style="border-radius: 10px; width: 50%;" type="text" name="nomeCorriere" value="NomeUtente"/></p>
	                <p>Cognome del corriere:&emsp;<input style="border-radius: 10px; width: 50%;" type="text" name="cognomeCorriere" value="CognomeUtente"/></p>
	                <p>Username del corriere:&emsp;<input style="border-radius: 10px; width: 50%;" type="text" name="usernameCorriere" value="Username"/></p>
	                <p>Password del corriere:&emsp;<input style="border-radius: 10px; width: 50%;" type="password" name="passwordCorriere" value="CognomeUtente"/></p>
	                <p>Mail del corriere:&emsp;<input style="border-radius: 10px; width: 50%;" type="text" name="mailCorriere" value="EmailUtente@domain.com"></input></p>
	                <input type="submit"style="border-radius: 10px; background-color: #82ae46; width: 24%; height: 10%; margin-left: 38%; margin-top: 5%; color: white;" value="Aggiungi Corriere"/>
	            </div>
	        </form>
        </div>

        <div class="logoLazy">
          <img src="images/logo.png" style="margin-left: 35%; margin-top: 10%;"/>
        </div>
      </div>


    </div>
  </section>

  <footer class="ftco-footer ftco-section">
    <div class="container">
        <!-- <div class="row">
            <div class="mouse">
                <a href="#" class="mouse-icon">
                    <div class="mouse-wheel"><span class="ion-ios-arrow-up"></span></div>
                </a>
            </div>
        </div> -->
        <div class="row mb-5">
            <div class="col-md">
                <div class="ftco-footer-widget mb-4">
                    <h2 class="ftco-heading-2">LazyList</h2>
                    <p>La spesa, a casa tua</p>

                </div>
            </div>
            <div class="col-md">
                <div class="ftco-footer-widget mb-4 ml-md-5">
                    <h2 class="ftco-heading-2">Menu</h2>
                    <ul class="list-unstyled">
                        <li><a href="#" class="py-2 d-block">Catalogo</a></li>
                        <li><a href="#" class="py-2 d-block">Carrello</a></li>
                        <li><a href="#" class="py-2 d-block">Storico Ordini</a></li>
                        <li><a href="#" class="py-2 d-block">Info Account</a></li>
                    </ul>
                </div>
            </div>
            <div class="col-md">
                <div class="ftco-footer-widget mb-4">
                    <h2 class="ftco-heading-2">Domande?</h2>
                    <div class="block-23 mb-3">
                        <ul>
                            <li><span class="icon icon-map-marker"></span><span class="text"> Viale Risorgimento 2,
                                    Bologna, Italia</span></li>
                            <li><a href="#"><span class="icon icon-phone"></span><span class="text">+39 123
                                        4567890</span></a></li>
                            <li><a href="#"><span class="icon icon-envelope"></span><span
                                        class="text">info@yourdomain.com</span></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 text-center">


            </div>
        </div>
    </div>
</footer>



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

</body>

</html>