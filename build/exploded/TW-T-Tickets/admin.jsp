<jsp:include page="loginservlet" />

<%-- uso della sessione --%>
<%@ page session="true"%>

<%-- info pagina --%>
<%@ page info="Pagina jsp"%>
<%@ page language="java" import="java.net.*"%>

<%-- import java --%>
<%@ page import="java.util.List, java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat, java.text.DateFormat"%>
<%@ page import="esame.beans.GestoreGruppi"%>
<%@ page import="esame.beans.Sessione"%>
<%@ page import="esame.beans.Utente"%>
<%@ page import="java.util.*"%>


<%-- pagina per la gestione di errori --%>
<%@ page errorPage="errors/failure.jsp"%>


	scope="application" />


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Example jsp</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/styles/mystyle.css"
	type="text/css" />
</head>
<body>
	<div id="container">
		<%@ include file="fragments/header.html"%>

		<%
			//gli autenti non autenticati saranno in LoginInfo salvati con username e password = null
			GestoreGruppi gestoreUtenti = (GestoreGruppi) getServletContext().getAttribute("gestoreUtenti");
		%>

		<div id="body">
		<p>Statistiche</p>
		<hr />
		<script src="scripts/forms.js" type="text/javascript"></script>
		<ol id="timestamp_sessioni">
		<%
			for (Map.Entry<Utente,Sessione> entry : gestoreUtenti.getUtentiLoggati().entrySet()) {
		%>
		<li><%= entry.getValue().getOraInizio().toString() + entry.getValue().getOraFine().toString() %></li>
		<%
			}
		%>
		</ol>
		<ol id="risultati_sessioni">
		<%
			for (Map.Entry<Utente,Sessione> entry : gestoreUtenti.getUtentiLoggati().entrySet()) {
		%>
		<li><%= entry.getValue().getRisultati().toString() %></li>
		<%
			}
		%>
		</ol>
		<ol id="sessioni_correnti"> 
		<%
			for (Sessione s : gestoreUtenti.getSessioniCorrentiUtentiNonAutenticati()) {
		%>
		<li><%= s.getId() + s.getOraInizio() %></li>
		<%
			}
		%>
		</ol>
		<ol id="sessioni_recenti">
		<%
			for (Sessione s : gestoreUtenti.getSessioniRecentiUtentiNonAutenticati()) {
		%>
		<li><%= s.getId() + s.getOraInizio() + s.getOraFine() %></li>
		<%
			}
		%>
		</ol>
		</div>
		<%@ include file="fragments/footer.html"%>
	</div>
</body>
</html>
