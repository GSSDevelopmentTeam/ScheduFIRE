<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ScheduFIRE</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link type="text/css" rel="stylesheet" href="../CSS/LoginCSS.css">
<link rel="icon" href="../IMG/logoSF.png">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css" integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>
</head>
<body>

	<!-- Barra Navigazione -->
	<%@ include file="NavBarJSP.jsp"%>

	<!-- Page content -->
	<div class="w3-content w3-padding" style="max-width: 1564px">


		<!--  Tabella Accesso -->
		<div
			class="table th  w3-auto w3-display-middle w3-center w3-margin-bottom  w3-hide-small">


			<h1 style="font-size: 35px">Benvenuto</h1>

			<p style="font-size: 23px">Effettua l'accesso con le tue
				credenziali</p>

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
			<br> <input type="checkbox" onclick="show()" class="center">Mostra
			Password <br>
			<br>
			<!-- Button trigger modal -->
			<button type="button" class="pass btn-primary" data-toggle="modal"
				data-target="#staticBackdrop">Hai dimenticato la tua
				password?</button>
			<script>
						function show() {
	  						var x = document.getElementById("myInput");
	  						if (x.type === "password") {
	    						x.type = "text";
	  						} else {
	    						x.type = "password";}}
						</script>
			<br> <br> <input type="submit" class="submit center"
				value="Accedi">

		</div>
	</div>

<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel">Recupera Password</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <div class="input-container">
						<i class="fa fa-envelope icon"></i> <input class="input-field"
							type="text"  placeholder="E-Mail" name="Email">
					</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="inversesubmit btn-secondary" data-dismiss="modal">Chiudi</button>
        <button type="button" class="submit btn-primary">Recupera Password</button>
      </div>
    </div>
  </div>
</div>	

</body>
</html>