<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<jsp:include page="StandardJSP.jsp" />
<link type="text/css" rel="stylesheet" href="CSS/CTHomeCSS.css">
<body>

	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />

	<div class="rw" style="margin: 50px;">
		<div class="clm">
			<form action="GeneraSquadreServlet" method="POST">
				<button class="btop">
					<img src="Icon/CavallettoColorato.png"><span class="rht">Gestione
						<br> Squadra
					</span>
				</button>
			</form>
		</div>
		<div class="clm">
			<form action="CalendarioServlet" method="POST">
				<button class="btop">
					<img src="Icon/calendarioColori.png"> <span class="rht">
						Visualizza <br> Calendario
					</span>
				</button>
			</form>
		</div>
		<div class="clm">
			<form action="PeriodiDiMalattiaServlet" method="POST">
				<button class="btop">
					<img src="Icon/MalattieColore.png"> <span class="rht">Gestione<br>
						Malattia
					</span>
				</button>
			</form>
		</div>

		<div class="clm">
			<form action="GestioneFerieServlet" method="POST">
				<button class="btop">
					<img src="Icon/solecolore.png"> <span class="rht">Gestione<br>
						Ferie
					</span>
				</button>
			</form>
		</div>
		<div class="clm">
			<form action="GestionePersonaleServlet" method="POST">
				<button class="btop">
					<img src="Icon/ominoVF.png"> <span class="rht">Gestione<br>
						Personale
					</span>
				</button>
			</form>
		</div>
		<div class="clm">
			<form action="PersonaleDisponibile" method="POST">
				<button class="btop">
					<img src="Icon/ominiVF.png"> <span class="rht">
						Personale<br> Disponibile
				</button>
			</form>
		</div>
	</div>

</body>
</html>