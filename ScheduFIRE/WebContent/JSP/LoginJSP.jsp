<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login</title>
<link rel="stylesheet" 	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link type="text/css" rel="stylesheet" href="../CSS/LoginCSS.css">
<link rel="icon" href="../IMG/logoSF.png">
</head>
<body>

	<!-- Barra Navigazione -->
	<%@ include file="NavBarJSP.jsp"%>

	<!-- Page content -->
	<div class="w3-content w3-padding" style="max-width: 1564px">


		<!--  Tabella Accesso -->
		<div
			class="table th  w3-auto w3-display-middle w3-center w3-margin-bottom  w3-hide-small">


				<h1>         Benvenuto</h1>
				
				<p>Effettua l'accesso con le tue credenziali</p>

				<div class="tableRow ">
					<div class="input-container">
						<i class="fa fa-user icon"></i> <input class="input-field"
							type="text" id="user1" placeholder="Username" name="Username">
					</div>
				</div>
				<br>

				<div class="tableRow ">
					<div class="input-container">
						<i class="fa fa-key icon"></i> <input class="input-field"
							type="password" placeholder="Password" name="Password"
							id="myInput">
					</div>
				</div>
				<br> <input type="checkbox" onclick="myFunction()"
					class="center">Show Password
					<br><br><a href="" style="color:#BECDD1">Hai Dimenticato la tua password?</a>
				<script>
						function myFunction() {
	  						var x = document.getElementById("myInput");
	  						if (x.type === "password") {
	    						x.type = "text";
	  						} else {
	    						x.type = "password";}}
						</script>
				<br>
				<br> <input type="submit" class="submit center" value="Accedi">

		</div>
	</div>


</body>
</html>