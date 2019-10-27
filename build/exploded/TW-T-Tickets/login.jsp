<%@ page session="true"%>
<%@ page errorPage="errors/failure.jsp"%>
<%@ page import="esame.servlets.LoginServlet"%>

<html>
<head>
<title>Login jsp</title>
<link rel="stylesheet" href="styles/mystyle.css" type="text/css"></link>
</head>
<body>
	<div id="container">
		<%@ include file="fragments/header.html"%>
		<div id="body">
			
			<script src="scripts/forms.js" type="text/javascript"></script>
			<fieldset><legend>form to compile</legend>
			<form name="login" action="loginServlet"
				onSubmit="return validateLoginForm(this)" method="post">
				<label for="username">Username</label> <input type="text"
					name="username" placeholder="username" required> <br /> <label
					for="password">Password</label> <input type="password"
					name="password" placeholder="password" required> <br /> <input
					type="submit" value="Login">
			</form>
			<form method="get" action="loginServlet">
				<input name="enter_no_login" type="submit" value="Entra senza login">
			</form>
			</fieldset>
		</div>
		<%@ include file="fragments/footer.html"%>
	</div>
</body>
</html>
