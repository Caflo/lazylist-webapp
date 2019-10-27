<!-- import di classi Java -->
<%@ page import="esame.beans.Libro"%>
<%@ page import="esame.beans.ElencoLibri"%>
<%@ page import="java.util.*"%>

<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!DOCTYPE html>
<html>
<head>
<title>Login page</title>
<link rel="stylesheet" href="styles/mystyle.css" type="text/css"></link>
</head>
<body>
	<div id="container">
		<div id="header">Esame Tecnologie Web</div>
		<div id="body">
			<jsp:useBean id="elencoLibri" class="esame.beans.ElencoLibri" scope="session"/>

			<!-- <script src="scripts/forms.js" type="text/javascript"></script> -->
			<!-- Mi riprendo i dati che avevo temporaneamente salvato con save (se ci sono) -->
			<%
			Libro l = (Libro) request.getSession().getAttribute("datiTemporanei");
			String titolo = null;
			String autore = null;
			String ISBN = null;
			%>
			
			
			<!-- Creo la parte grafica -->
			<fieldset>
			<form action="nuovoLibroServlet" method="post">
				<input type="text" name="nome" value=<%= l.getTitolo() %>>
				<br />
				<input type="text" name="autore" value=<%= l.getAutore() %>>
				<br />
				<input type="number" name="isbn" value=<%= l.getISBN() %>>
				<br />
				<input type="submit" name="request" value="save">
				<br />
				<input type="submit" name="request" value="finalizza">				
			</form>
			</fieldset>
			<a href="<%= request.getContextPath() %>/elencoLibri.jsp">Vai a elencoLibri.jsp</a><br />
			<br />
			
			<!-- Aggiungo un libro al catalogo -->
			<%
			Libro l = (Libro) request.getAttribute("aggiungiAlCatalogo");
			if (l != null)
				elencoLibri.getLibri().add(l);
			%>
		</div>
		<div id="footer">
			<br />
		</div>
	</div>
</body>
</html>
