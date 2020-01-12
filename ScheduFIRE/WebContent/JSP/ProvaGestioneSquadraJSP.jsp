<%@page import="java.util.HashMap"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.bean.*, model.dao.*"%>
<!DOCTYPE html>
<html>
<jsp:include page="StandardJSP.jsp" />
<head>
<link type="text/css" rel="stylesheet" href="../CSS/CTHomeCSS.css">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
.fr {
	height: 38px;
	width: 38px;
	float: left;
}

h2 {
	color: #B60000;
}
</style>
</head>

<body>

	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />

	<!-- MODAL MODIFICA VF -->

	<div class="modal fade" id="aggiungiVF" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true"
		style="display: none">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content contenutiModal">
				<div class="modal-header">
					<h5 class="modal-title" id="titoloAggiuntaFerie">Personale
						Disponibile</h5>
				</div>
				<form action="ModificaComposizioneSquadreServlet" method="POST">
					<!-- Nel form verranno passate l'email del VF da sostituire con nome "email" e quella del VF da inserire con nome "VFNew" -->
					<div class="modal-body" id="elenco">
						<div id="appendiElenco"></div>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-outline-danger"
							data-dismiss="modal">Annulla</button>
						<button class="btn btn-outline-secondary">Aggiungi</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- ELENCO SQUADRE  -->

	<div class="d-flex justify-content-center">
		<form action="GeneraSquadreServlet" method=post>
			<button type="button" class="btn btn-success btn-lg" value="Conferma"
				name="Conferma" style="margin: 3px;">Conferma Squadre</button>
		</form>
		<a href="#Giorno"><button type="button"
				class="btn btn-danger btn-lg" style="margin: 3px;">Sqaudra
				Diurna</button></a> <a href="#Notte"><button type="button"
				class="btn btn-danger btn-lg" style="margin: 3px;">Squadra
				Notturna</button></a>
	</div>
	<br>


	<div class="d-flex justify-content-center">
		<h2 id="Giorno" style="font-weight: bold; font-size: 36px;">Squadra
			Diurna</h2>
	</div>
	<div class="d-flex justify-content-center">
		<img src="../Icon/caserma.png" class="fr">
		<h2>Sala Operativa</h2>
	</div>

	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Modifica</th>
				</tr>
			</thead>

			<tbody>


				<tr>
					<td class="text-center"><img src="Grado/Esperto.png"
						style="height: 25%"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center">Nome</td>
					<td class="text-center">Cognome</td>
					<td class="text-center">Mansione</td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiVF" id="aggiungiVF"
							onClick='apriFormVF("Email VF")'>Sostituisci</button></td>
					</td>
				</tr>


			</tbody>
		</table>
	</div>


	<div class="d-flex justify-content-center">
		<img src="../Icon/sirena.png" class="fr">
		<h2>Prima Partenza</h2>
	</div>
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Modifica</th>
				</tr>
			</thead>

			<tbody>


				<tr>
					<td class="text-center"><img src="Grado/Coordinatore.png"
						style="height: 25%"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center">Nome</td>
					<td class="text-center">Cognome</td>
					<td class="text-center">Mansione</td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiVF" id="aggiungiVF"
							onClick='apriFormVF("Email VF")'>Sostituisci</button></td>
					</td>
				</tr>


			</tbody>
		</table>
	</div>

	<div class="d-flex justify-content-center">
		<img src="../Icon/autoscala.png" class="fr">
		<h2>Auto Scala</h2>
	</div>
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Modifica</th>
				</tr>
			</thead>

			<tbody>


				<tr>
					<td class="text-center"><img src="Grado/Qualificato.png"
						style="height: 25%"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center">Nome</td>
					<td class="text-center">Cognome</td>
					<td class="text-center">Mansione</td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiVF" id="aggiungiVF"
							onClick='apriFormVF("Email VF")'>Sostituisci</button></td>
					</td>
				</tr>


			</tbody>
		</table>
	</div>

	<div class="d-flex justify-content-center">
		<img src="../Icon/idrante.png" class="fr">
		<h2>Auto Botte</h2>
	</div>
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Modifica</th>
				</tr>
			</thead>

			<tbody>

				<tr>
					<td class="text-center"><img src="Grado/Esperto.png"
						style="height: 25%"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center">Nome</td>
					<td class="text-center">Cognome</td>
					<td class="text-center">Mansione</td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiVF" id="aggiungiVF"
							onClick='apriFormVF("Email VF")'>Sostituisci</button></td>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="d-flex justify-content-center">
		<h2 id="Notte" style="font-weight: bold; font-size: 36px;">Squadra
			Notturna</h2>
	</div>
	<div class="d-flex justify-content-center">
		<img src="../Icon/caserma.png" class="fr">
		<h2>Sala Operativa</h2>
	</div>

	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Modifica</th>
				</tr>
			</thead>

			<tbody>


				<tr>
					<td class="text-center"><img src="Grado/Esperto.png"
						style="height: 25%"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center">Nome</td>
					<td class="text-center">Cognome</td>
					<td class="text-center">Mansione</td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiVF" id="aggiungiVF"
							onClick='apriFormVF("Email VF")'>Sostituisci</button></td>
					</td>
				</tr>


			</tbody>
		</table>
	</div>


	<div class="d-flex justify-content-center">
		<img src="../Icon/sirena.png" class="fr">
		<h2>Prima Partenza</h2>
	</div>
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Modifica</th>
				</tr>
			</thead>

			<tbody>


				<tr>
					<td class="text-center"><img src="Grado/Coordinatore.png"
						style="height: 25%"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center">Nome</td>
					<td class="text-center">Cognome</td>
					<td class="text-center">Mansione</td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiVF" id="aggiungiVF"
							onClick='apriFormVF("Email VF")'>Sostituisci</button></td>
					</td>
				</tr>


			</tbody>
		</table>
	</div>

	<div class="d-flex justify-content-center">
		<img src="../Icon/autoscala.png" class="fr">
		<h2>Auto Scala</h2>
	</div>
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Modifica</th>
				</tr>
			</thead>

			<tbody>


				<tr>
					<td class="text-center"><img src="Grado/Qualificato.png"
						style="height: 25%"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center">Nome</td>
					<td class="text-center">Cognome</td>
					<td class="text-center">Mansione</td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiVF" id="aggiungiVF"
							onClick='apriFormVF("Email VF")'>Sostituisci</button></td>
					</td>
				</tr>


			</tbody>
		</table>
	</div>

	<div class="d-flex justify-content-center">
		<img src="../Icon/idrante.png" class="fr">
		<h2>Auto Botte</h2>
	</div>
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Modifica</th>
				</tr>
			</thead>

			<tbody>

				<tr>
					<td class="text-center"><img src="Grado/Esperto.png"
						style="height: 25%"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center">Nome</td>
					<td class="text-center">Cognome</td>
					<td class="text-center">Mansione</td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiVF" id="aggiungiVF"
							onClick='apriFormVF("Email VF")'>Sostituisci</button></td>
					</td>
				</tr>
			</tbody>
		</table>
	</div>


	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="JS/datePicker.js"></script>
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
	<script src="https://buttons.github.io/buttons.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>

	<script>

	function apriFormVF(input) {
		//Chiamata ajax alla servlet PersonaleDisponibileAJAX
		$.ajax({
			type : "POST",//Chiamata POST
			url : "../PersonaleDisponibileAJAX",//url della servlet che devo chiamare
			data : {
				"JSON" : true,
				"aggiunta":true,
				"email" : input,
			},			
			success : function(response) {//Operazione da eseguire una volta terminata la chiamata alla servlet.
				$("#appendElenco").remove();
				$("<div id='appendElenco'></div>").appendTo("#elenco");
				$(response).appendTo("#appendElenco");					
			}
		});

	}

	</script>


</body>
</html>