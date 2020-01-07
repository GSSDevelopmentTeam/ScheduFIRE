<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="CSS/HeaderCSS.css">
<link rel="icon" href="IMG/logoSF.png">
<link type="text/css" rel="stylesheet" href="CSS/ErrorCSS.css">
<style>
body{
text-align: center; 
}
</style>
<body>

	<!-- Barra Navigazione -->
	<jsp:include page="StandardJSP.jsp" />
	<jsp:include page="HeaderJSP.jsp" />
	<h1 class="d-flex justify-content-center">
		<img src="IMG/fire.png" class="fr">Errore<img src="IMG/fire.png"
			class="fr">
	</h1>
	<p class="d-flex justify-content-center">
		E' stato riscontrato un problema,<br> controlla che l'URL della
		pagina sia scritto correttamente, <br> in alternativa effettua
		nuovamente il Login.
	</p>
	<script>
	
		$(document).ready(function() {
			setTimeout(function(){ window.location.replace("Login"); }, 5000);
		});

	</script>
</body>
</html>