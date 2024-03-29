<!DOCTYPE html>
<html lang="en">
<!-- accesso alla sessione -->
<%@ page session="true"%>

<%
	Integer totCarrello = (Integer) session.getAttribute("totCarrello");
	if (totCarrello == null)
		totCarrello = 0;
%>

<header id="header">
    <div class="py-1 bg-primary">
        <div class="container">
            <div class="row no-gutters d-flex align-items-start align-items-center px-md-0">
                <div class="col-lg-12 d-block">
                    <div class="row d-flex">
                        <div class="col-md pr-4 d-flex topper align-items-center">
                            <div class="icon mr-2 d-flex justify-content-center align-items-center"><span
                                    class="icon-phone2"></span></div>
                            <span class="text">+39 123 4567890</span>
                        </div>
                        <div class="col-md pr-4 d-flex topper align-items-center">
                            <div class="icon mr-2 d-flex justify-content-center align-items-center"><span
                                    class="icon-paper-plane"></span></div>
                            <span class="text">mail@example.com</span>
                        </div>
                        <div class="col-md-5 pr-4 d-flex topper align-items-center text-lg-right">
                            <span class="text">Scegli la data e noi spediamo!</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
        <div class="container">
    
            <a class="navbar-brand" href="visualizzazioneProdottiController"><img heigth="100px" width="100px" src="images\logo.png"></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav"
                aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="oi oi-menu"></span> Menu
            </button>
    
    		
            <div class="collapse navbar-collapse" id="ftco-nav">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item active"><a href="visualizzazioneProdottiController" class="nav-link">Catalogo</a></li>
                    <li class="nav-item cta cta-colored"><a href="carrelloController" class="nav-link"><span
                                class="icon-shopping_cart"></span><p style="display:inline" id="totCarrello" value="<%= totCarrello %>">[<%= totCarrello %>]</p></a></li>
    
                    <li class="nav-item"><a href="storicoOrdiniController" class="nav-link">Storico Ordini</a></li>
                    <li class="nav-item"><a href="./comingSoon.jsp" class="nav-link">Info Account</a></li>
                    <li class="nav-item"><a href="./contatti.jsp" class="nav-link">Contatti</a></li>

                </ul>
            </div>
        </div>
    </nav>
</header>