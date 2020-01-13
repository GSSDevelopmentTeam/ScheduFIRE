<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<jsp:include page="StandardJSP.jsp" />
<link type="text/css" rel="stylesheet" href="CSS/LoginCSS.css">
<body>

	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />

	<!--------- Alert NON Ok ----------------->

	<div
		class="alert alert-danger flex alert-dismissible fade in text-center fixed-top"
		id="erroreLogin"
		style="display: none; position: fixed; z-index: 99999; width: 100%">
		<strong>Errore!</strong> <span>Errore..</span>
	</div>

	<!-- ----------------------- -->
	<!-- Page content -->
	<br>
	<br>
	<div class="w3-content w3-padding center"
		style="max-width: 1564px; text-align: center">


		<!--  Tabella Accesso -->
		<br>
		<br>
		<div
			class="tb th  w3-auto w3-display-middle w3-center w3-margin-bottom  w3-hide-small">

			<form action="Login" method="POST">

				<h1 style="font-size: 35px">Benvenuto</h1>

				<p style="font-size: 23px">Effettua l'accesso con le tue
					credenziali</p>

				<div class="d-flex justify-content-center">
					<div class="input-container">
						<i class="fa fa-user icon"></i> <input class="input-field"
							type="text" id="user1" placeholder="Username" name="Username"
							required>
					</div>
				</div>
				<br>
				<div class="d-flex justify-content-center">
					<div class="input-container">
						<i class="fa fa-key icon"></i> <input class="input-field"
							type="password" placeholder="Password" name="Password"
							id="myInput" required>
					</div>
				</div>
				<input type="checkbox" onclick="show()" class="center">Mostra
				Password<br>

				<script>
					function show() {
						var x = document.getElementById("myInput");
						if (x.type === "password") {
							x.type = "text";
						} else {
							x.type = "password";
						}
					}
				</script>
				<br> <input type="submit"
					class="btn btn-outline-secondary btn-lg" value="Accedi">
			</form>
			<br>
			<!--  <button
				onclick="document.getElementById('id01').style.display='block'"
				class="pass">Hai dimenticato la tua password?</button>
				-->

		</div>
	</div>





	<div id="id01" class="modal">

		<!-- <form class="modal-content" action="/..." method="post"> -->


		<div class="tbPss w3-display-middle"
			style="border-radius: 10px; margin-left: 50px;">
			<div class="d-flex justify-content-center">
				<div class="input-container">
					<i class="fa fa-envelope icon"></i> <input class="input-field"
						type="text" placeholder="E-Mail" name="Email">
				</div>
			</div>
			<br>
			<button type="submit" class="btn btn-outline-secondary">Recupera
				Password</button>
			<br> <span style="margin-left: 5px;"> <br>
			<button type="button"
					onclick="document.getElementById('id01').style.display='none'"
					class="btn btn-outline-danger">Annulla</button>
			</span>
		</div>
		<!-- </form> -->
	</div>

	<script>
		// Get the modal
		var modal = document.getElementById('id01');

		// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
			if (event.target == modal) {
				modal.style.display = "none";
			}
		}
		
		
		function alertInsuccesso(input){
			$("#erroreLogin span").text(input);
			$("#erroreLogin").fadeTo(4000, 500).slideUp(500, function(){
			    $("#success-alert").slideUp(500);
			});
			
		}
		
		
		$( document ).ready(function() {
		   
		
		<% if(request.getAttribute("usernameErrato")!=null) { %>
		alertInsuccesso("Username errato!");
		<% } %>
		
		
		<% if(request.getAttribute("passwordErrata")!=null) { %>
		alertInsuccesso("Password errata!");
		<% } %>
		});
	</script>


</body>
</html>