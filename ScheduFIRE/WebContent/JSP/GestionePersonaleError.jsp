<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="CSS/HeaderCSS.css">
<link rel="icon" href="IMG/logoSF.png">
<link type="text/css" rel="stylesheet" href="CSS/ErrorCSS.css">
<body>

	<!-- Barra Navigazione -->
	<jsp:include page="StandardJSP.jsp" />
	<jsp:include page="HeaderJSP.jsp" />
	<h1 class="text-center">
		<img src="IMG/fire.png" class="fr">Errore<img src="IMG/fire.png"
			class="fr">
	</h1>
	<p class="text-center">
		<%= exception.getMessage() %>
	</p>
	<script>
		
			$(document).ready(function() {
				setTimeout(function(){ window.location.replace("GestionePersonaleServlet"); }, 5000);
			});
	
		</script>
</body>
</html>