<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import = "java.util.ArrayList, model.bean.*, model.dao.*" %>
<!DOCTYPE html>
<html>
<jsp:include page="StandardJSP.jsp" />

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ScheduFIRE</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="../CSS/HeaderCSS.css">
<link rel="icon" href="../IMG/logoSF.png">
<style>
	.fr{height:38px; 
    	width:38px;
    	float:left;
    	}
    h2{ color: #B60000;}
</style>
</head>

<body >
	
	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />
	
<!-- MODAL MODIFICA VF -->
<div class="modal fade" id="aggiungiVF" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true"
		style="display: none">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content contenutiModal">
				<div class="modal-header">
					<h5 class="modal-title" id="titoloAggiuntaFerie"
						>Personale Disponibile</h5>
					
				</div>
				<div class="modal-body">
					<input type="hidden" name="email" id="emailAggiuntaFerie">
					
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-outline-danger""
						data-dismiss="modal">Annulla</button>
					<button type="button" class="btn btn-outline-warning">Aggiungi</button>
				</div>
			</div>
		</div>
	</div>
	
<!-- ELENCO SQUADRE  -->
	
	
	<div class="d-flex justify-content-center"><img src="../Icon/caserma.png" class="fr"><h2>Sala Operativa </h2></div>
	
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Modifica </th>
				</tr>
			</thead>

			<tbody>
			 	<% /*	ArrayList<ComponenteDellaSquadraBean> componenti=(ArrayList<ComponenteDellaSquadraBean>)request.getAttribute("componenti");
					ArrayList<VigileDelFuocoBean> vigili=(ArrayList<VigileDelFuocoBean>)request.getAttribute("vigili");
					for(ComponenteDellaSquadraBean componente : componenti){
						for(Iterator<VigileDelFuocoBean> i=vigili.iterator();i.hasNext();){
							VigileDelFuocoBean vigile=i.next();
							if (vigile.getEmail().equals(componente.getEmailVF())){	*/	
				%>  

					<tr>
						<td class="text-center">grado</td>
						<td class="text-center">nome</td>
						<td class="text-center">cognome</td>
						<td class="text-center">mansione</td>
						<td class="text-center"><button type="button" class="btn btn-outline-secondary"
							data-toggle="modal" data-target="#aggiungiVF"
							onClick='apriFormVF(" ")'>Sostituisci</button></td>
						</td>
					</tr>
					
					
			</tbody>
		</table>
		</div>


	<div class="d-flex justify-content-center"><img src="../Icon/sirena.png" class="fr"><h2>Prima Partenza</h2></div>
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Modifica </th>
				</tr>
			</thead>

			<tbody>
			 	<% /*	ArrayList<ComponenteDellaSquadraBean> componenti=(ArrayList<ComponenteDellaSquadraBean>)request.getAttribute("componenti");
					ArrayList<VigileDelFuocoBean> vigili=(ArrayList<VigileDelFuocoBean>)request.getAttribute("vigili");
					for(ComponenteDellaSquadraBean componente : componenti){
						for(Iterator<VigileDelFuocoBean> i=vigili.iterator();i.hasNext();){
							VigileDelFuocoBean vigile=i.next();
							if (vigile.getEmail().equals(componente.getEmailVF())){	*/	
				%>  

					<tr>
						<td class="text-center">grado</td>
						<td class="text-center">nome</td>
						<td class="text-center">cognome</td>
						<td class="text-center">mansione</td>
						<td class="text-center"><button type="button" class="btn btn-outline-secondary"
							data-toggle="modal" data-target="#aggiungiVF"
							onClick='apriFormVF(" ")'>Sostituisci</button></td>
						</td>
					</tr>
					
					
			</tbody>
		</table>
		</div>

	<div class="d-flex justify-content-center"><img src="../Icon/autoscala.png" class="fr"><h2>Auto Scala</h2></div>
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Modifica </th>
				</tr>
			</thead>

			<tbody>
			 	<% /*	ArrayList<ComponenteDellaSquadraBean> componenti=(ArrayList<ComponenteDellaSquadraBean>)request.getAttribute("componenti");
					ArrayList<VigileDelFuocoBean> vigili=(ArrayList<VigileDelFuocoBean>)request.getAttribute("vigili");
					for(ComponenteDellaSquadraBean componente : componenti){
						for(Iterator<VigileDelFuocoBean> i=vigili.iterator();i.hasNext();){
							VigileDelFuocoBean vigile=i.next();
							if (vigile.getEmail().equals(componente.getEmailVF())){	*/	
				%>  

					<tr>
						<td class="text-center">grado</td>
						<td class="text-center">nome</td>
						<td class="text-center">cognome</td>
						<td class="text-center">mansione</td>
						<td class="text-center"><button type="button" class="btn btn-outline-secondary"
							data-toggle="modal" data-target="#aggiungiVF"
							onClick='apriFormVF(" ")'>Sostituisci</button></td>
						</td>
					</tr>
					
					
			</tbody>
		</table>
		</div>

	<div class="d-flex justify-content-center"><img src="../Icon/idrante.png" class="fr"><h2>Auto Botte</h2></div>
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Modifica </th>
				</tr>
			</thead>

			<tbody>
			 	<% /*	ArrayList<ComponenteDellaSquadraBean> componenti=(ArrayList<ComponenteDellaSquadraBean>)request.getAttribute("componenti");
					ArrayList<VigileDelFuocoBean> vigili=(ArrayList<VigileDelFuocoBean>)request.getAttribute("vigili");
					for(ComponenteDellaSquadraBean componente : componenti){
						for(Iterator<VigileDelFuocoBean> i=vigili.iterator();i.hasNext();){
							VigileDelFuocoBean vigile=i.next();
							if (vigile.getEmail().equals(componente.getEmailVF())){	*/	
				%>  

					<tr>
						<td class="text-center">grado</td>
						<td class="text-center">nome</td>
						<td class="text-center">cognome</td>
						<td class="text-center">mansione</td>
						<td class="text-center"><button type="button" class="btn btn-outline-secondary"
							data-toggle="modal" data-target="#aggiungiVF"
							onClick='apriFormVF(" ")'> Sostituisci</button></td>
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
	console.log("parte funzione apriformAggiunta di " + input);
	picker.setOptions({
		startDate : null,
		endDate : null
	});
	$("#dataInizio").val("");
	$("#dataFine").val("");
	picker.setLockDays([]);

	$.ajax({
		type : "POST",
		url : "GestioneFerieServlet",
		data : {
			"JSON" : true,
			"aggiunta":true,
			"email" : input,
		},
		dataType : "json",
		async : false,
		success : function(response) {
			picker.setLockDays(response);
			$("#emailAggiuntaFerie").val(input)
			console.log("settati giorni di ferie: " + response);
			var cognome = $(".table td:contains('" + input + "')")
					.prev('td');
			var nome = $(cognome).prev('td');
			console.log("cognome: " + cognome.text() + " nome: "
					+ nome.text());
			$("#titoloAggiuntaFerie").text(
					"Aggiunta ferie per " + nome.text() + " "
							+ cognome.text());
			$('#formAggiunta').show();
			$(".contenutiModal").css('background-color', '#e6e6e6');
		}
	});

}
</script>
	

</body>
</html>