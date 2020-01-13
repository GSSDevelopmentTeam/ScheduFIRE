<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, model.bean.*, model.dao.*"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="StandardJSP.jsp" />
<link type="text/css" rel="stylesheet"
	href="./CSS/GestionePersonaleCSS.css">
<style>
.table td, .table th {
	padding: 1.5px !important;
	vertical-align: top;
	border-top: 1px solid #dee2e6;
}

.back-up {
	border: none;
	background: none;
	position: fixed;
	bottom: 5%;
	right: 5%;
}

.container__days {
	min-width: 270px;
}

.month-item-weekdays-row {
	min-width: 265px;
}
</style>
</head>
<body>


	<div id="sali"></div>
	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />


	<a href="#sali" class=" back-up"><img
		src="IMG/arrow/up-arrow-p.png"
		onmouseover="this.src='IMG/arrow/up-arrow-d.png'"
		onmouseout="this.src='IMG/arrow/up-arrow-p.png'" /></a>

	<h2 class="d-flex justify-content-center"
		style="color: #B60000 !Important; margin-top: 3%; font-size: 45px;">Gestione
		Ferie</h2>


	<!-- form per l'ordinamento della lista dei VF-->
	<%
		Object ordinamentoObj = request.getAttribute("ordinamento");
		String ordinamento = (String) ordinamentoObj;
		%>
	<form action="./GestioneFerieServlet">
		<div align="center">
			<label>Ordinamento lista: &nbsp&nbsp&nbsp</label> <select
				class="custom-select" name="ordinamento"
				onchange="this.form.submit()" style="width: 15%">

				<%
					if( ordinamento != null ) {
						if( ordinamento.equals("nome") ) {
					%>
				<option value="nome" selected>Nome</option>
				<option value="cognome">Cognome</option>
				<option value="grado">Grado</option>
				<option value="giorniFerie">Ferie</option>


				<%
						} else if( ordinamento.equals("cognome") ) {		
						%>
				<option value="nome">Nome</option>
				<option value="cognome" selected>Cognome</option>
				<option value="grado">Grado</option>
				<option value="giorniFerie">Ferie</option>

				<%
						} else if( ordinamento.equals("grado") ) {		
						%>
				<option value="nome">Nome</option>
				<option value="cognome">Cognome</option>
				<option value="grado" selected>Grado</option>

				<option value="giorniFerie">Ferie</option>
				<%
						} else if( ordinamento.equals("giorniFerie") ) {		
						%>
				<option value="nome">Nome</option>
				<option value="cognome">Cognome</option>
				<option value="grado">Grado</option>
				<option value="giorniFerie" selected>Ferie</option>
				<%
						} 		
						%>

				<%}
					else {%>

				<option value="nome">Nome</option>
				<option value="cognome" selected>Cognome</option>
				<option value="grado">Grado</option>

				<option value="giorniFerie">Ferie</option>
				<%} %>


			</select>
		</div>
	</form>
	<br>




	<!-- Modal di avviso ferie-->
 <div class="modal fade" id="modalAvviso" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-sm modal-dialog-centered" role="document">
    <div class="modal-content" style="border :3px solid #5be94b;">
      <div class="modal-body" style="align:center;">
        <img src="IMG/fire.png" class="rounded mx-auto d-block">
        <h4 class="modal-title text-center">Operazione effettuata con successo</h4>
      </div>
      <div class="modal-footer">

        <button type="button" class="btn btn-outline-success" 
        data-dismiss="modal" onClick="ricaricaPagina()">OK</button>


      </div>
    </div>
  </div>
</div>


	<!-- Modal di aggiunta ferie-->
	<div class="modal fade" id="aggiungiFerie" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true"
		style="display: none">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content contenutiModal" style="min-height: 670px;">
				<div class="modal-header">
					<h5 class="modal-title" id="titoloAggiuntaFerie">Aggiunta
						ferie</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<p>Per selezionare un solo giorno, cliccare due volte sulla data
					desiderata.</p>
				<div class="modal-body">
					<input type="hidden" name="email" id="emailAggiuntaFerie">
					<div class=" row justify-content-center">
						<input id="dataInizio" placeholder="Giorno iniziale" readonly
							size="34" style="margin-bottom: 1%;" /> <input id="dataFine"
							placeholder="Giorno finale" readonly size="34"
							style="margin-bottom: 2%;" />
					</div>
					<div class="text-center" id="messaggioTurno"></div>

					<div class="text-center" id="messaggioFerie1"></div>
					<div class="text-center" id="messaggioFerie2"></div>


				</div>

				<div class="modal-footer">

					<button type="button" class="btn btn-outline-success"
						id="bottoneAggiungiFerie" onclick="aggiungiFerie()" 
						data-toggle ="modal" data-target ="#modalAvviso" 
						data-dismiss="modal" disabled>Aggiungi ferie</button>

					<button type="button" class="btn btn-outline-danger"
						data-dismiss="modal">Annulla</button>

				</div>
			</div>
		</div>
	</div>



	<!--  MODAL RIMOZIONE FERIE V2 -->
	<!-- ----------------------- -->


	<div class="modal fade" id="rimuoviFerie" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true"
		style="display: none">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content contenutiModal" style="min-height: 670px;">
				<div class="modal-header">
					<h5 class="modal-title" id="titoloRimuoviFerie">Rimuovi ferie</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times; </span>
					</button>
				</div>
				<p>Per selezionare un solo giorno, cliccare due volte sulla data
					desiderata.</p>
				<div class="modal-body">
					<input type="hidden" name="email" id="emailRimozioneFerie">
					<div class=" row justify-content-center">
						<input id="rimozioneDataIniziale" placeholder="Giorno iniziale"
							readonly size="34" style="margin-bottom: 1%;" /> <input
							id="rimozioneDataFinale" placeholder="Giorno finale" readonly
							size="34" style="margin-bottom: 2%" />
					</div>
					<div class="text-center" id="messaggioTurno"></div>

					<div class="text-center" id="messaggioFerie1"></div>
					<div class="text-center" id="messaggioFerie2"></div>


				</div>
				<div class="modal-footer">

					<button type="button" class="btn btn-outline-success"
						id="bottoneRimuoviFerie" onclick="rimuoviFerie()" 
						data-toggle ="modal" data-target ="#modalAvviso" 
						data-dismiss="modal" disabled>Rimuovi ferie</button>

					<button type="button" class="btn btn-outline-danger"
						data-dismiss="modal">Annulla</button>

				</div>
			</div>
		</div>
	</div>










	<!-- Modal di rimozione ferie-->
	<div class="modal fade" id="rimuoviFerie2" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true"
		style="display: none">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content contenutiModal">
				<div class="modal-header">
					<h5 class="modal-title" id="titoloRimuoviFerie">Rimuovi ferie</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times; </span>
					</button>
				</div>
				<div class="modal-body">
					<input type="hidden" name="email" id="emailRimozioneFerie">
					<input type="hidden" name="dataIniziale" id="rimozioneDataIniziale">
					<input type="hidden" name="dataFinale" id="rimozioneDataFinale">

					<div class=" row justify-content-center">
						<div class="table-responsive">
							<table class="table table-hover" id="listaFerie">
								<thead class="thead-dark">
									<tr>
										<th>Data Inizio</th>
										<th>Data Fine</th>
										<th>Elimina</th>

									</tr>
								</thead>
								<tbody id="tabellaRimozioneFerie">
								</tbody>
							</table>

						</div>

					</div>


				</div>
			</div>
		</div>
	</div>



	<!-- ----------------------------------- -->
	<!-- Modal di avviso di sicurezza-->
	<div class="modal fade " id="menuConferma" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-sm modal-dialog-centered"
			role="document">
			<div class="modal-content">

				<div class="modal-body">
					<img src="IMG/fire.png" class="rounded mx-auto d-block">
					<h4 class="modal-title text-center">Sei sicuro?</h4>
					<p class="text-center">
						Vuoi cancellare queste ferie?<br> La procedura non può essere
						annullata.
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-outline-danger"
						data-dismiss="modal">Annulla</button>
					<button type="button" class="btn btn-outline-secondary"
						data-dismiss="modal" onClick="rimuoviFerie()">Salva
						cambiamenti</button>
				</div>
			</div>
		</div>
	</div>


	<!-- ------------------------------------ -->


	<div class="table-responsive">
		<div id="capo"></div>
		<div class="d-flex justify-content-center">
			<a href="#auto" class="btn btn-outline-secondary"
				style="margin: 3px;">Autisti</a> <a href="#vigile"
				class="btn btn-outline-secondary" style="margin: 3px;">Vigili</a>
		</div>
		<h4 class="d-flex justify-content-center"
			style="margin-top: 0%; color: #B60000 !Important">Capi Squadra</h4>

		<table class="table  table-hover listaVigili"
			style="table-layout: fixed">

			<thead class="thead-dark">
				<tr>
					<th class=" text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Email</th>
					<th class="text-center">Ferie</th>
					<th class="text-center">Inserisci ferie</th>
					<th class="text-center">Rimuovi ferie</th>
				</tr>
			</thead>

			<tbody>
				<%
					ArrayList<VigileDelFuocoBean> listaVigili;
					listaVigili = (ArrayList<VigileDelFuocoBean>) request.getAttribute("listaVigili");

					for (int i = 0; i < listaVigili.size(); i++) {
						VigileDelFuocoBean vigile = listaVigili.get(i);
						
						if(vigile.getMansione().toUpperCase().equals("CAPO SQUADRA")){
				%>
				<tr>

					<td class="text-center"><img
						src="Grado/<%=vigile.getMansione().equals("Capo Squadra") && vigile.getGrado().equals("Esperto")?"EspertoCapoSquadra":vigile.getGrado() %>.png"
						title="<%=vigile.getGrado() %>"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center"><strong><%=vigile.getNome()%></strong></td>
					<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
					<td class="text-center"><%=vigile.getEmail()%></td>
					<td class="text-center" id="ferie"><%=vigile.getGiorniFerieAnnoCorrente() + vigile.getGiorniFerieAnnoPrecedente()%></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiFerie"
							onClick='apriFormAggiunta("<%=vigile.getEmail()%>")'>Aggiungi
							Ferie</button></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-danger" data-toggle="modal"
							data-target="#rimuoviFerie"
							onClick='apriFormRimozione("<%=vigile.getEmail()%>")'>Rimuovi
							Ferie</button></td>
				</tr>

				<%
						}
					}
				%>

			</tbody>

		</table>
		<div id="auto"></div>
		<div class="d-flex justify-content-center">
			<a href="#capo" class="btn btn-outline-secondary"
				style="margin: 3px;">Capi Squadra</a> <a href="#vigile"
				class="btn btn-outline-secondary" style="margin: 3px;">Vigili</a>
		</div>
		<h4 class="d-flex justify-content-center" id="inizio"
			style="margin-top: 1%; color: #B60000 !Important">Autisti</h4>

		<table class="table  table-hover listaVigili"
			style="table-layout: fixed">
			<thead class="thead-dark">
				<tr>
					<th class=" text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Email</th>
					<th class="text-center">Ferie</th>
					<th class="text-center">Inserisci ferie</th>
					<th class="text-center">Rimuovi ferie</th>
				</tr>
			</thead>

			<tbody>
				<%
					for (int i = 0; i < listaVigili.size(); i++) {
						VigileDelFuocoBean vigile = listaVigili.get(i);
						
						if(vigile.getMansione().toUpperCase().equals("AUTISTA")){
				%>

				<tr>
					<td class="text-center"><img
						src="Grado/<%=vigile.getGrado()%>.png"
						title="<%=vigile.getGrado() %>"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center"><strong><%=vigile.getNome()%></strong></td>
					<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
					<td class="text-center"><%=vigile.getEmail()%></td>
					<td class="text-center" id="ferie"><%=vigile.getGiorniFerieAnnoCorrente() + vigile.getGiorniFerieAnnoPrecedente()%></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiFerie"
							onClick='apriFormAggiunta("<%=vigile.getEmail()%>")'>Aggiungi
							Ferie</button></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-danger" data-toggle="modal"
							data-target="#rimuoviFerie"
							onClick='apriFormRimozione("<%=vigile.getEmail()%>")'>Rimuovi
							Ferie</button></td>
				</tr>

				<%
						}
					}
				%>

			</tbody>

		</table>

		<div id="vigile"></div>
		<div class="d-flex justify-content-center">
			<a href="#capo" class="btn btn-outline-secondary"
				style="margin: 3px;">Capi Squadra</a> <a href="#auto"
				class="btn btn-outline-secondary" style="margin: 3px;">Autisti</a>
		</div>

		<h4 class="d-flex justify-content-center" id="inizio"
			style="margin-top: 1%; color: #B60000 !Important">Vigili</h4>

		<table class="table  table-hover listaVigili"
			style="table-layout: fixed">
			<thead class="thead-dark">
				<tr>
					<th class=" text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Email</th>
					<th class="text-center">Ferie</th>
					<th class="text-center">Inserisci ferie</th>
					<th class="text-center">Rimuovi ferie</th>
				</tr>
			</thead>

			<tbody>
				<%
					for (int i = 0; i < listaVigili.size(); i++) {
						VigileDelFuocoBean vigile = listaVigili.get(i);
						
						if(vigile.getMansione().toUpperCase().equals("VIGILE")){
				%>

				<tr>
					<td class="text-center"><img
						src="Grado/<%=vigile.getGrado()%>.png"
						title="<%=vigile.getGrado() %>"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center"><strong><%=vigile.getNome()%></strong></td>
					<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
					<td class="text-center"><%=vigile.getEmail()%></td>
					<td class="text-center" id="ferie"><%=vigile.getGiorniFerieAnnoCorrente() + vigile.getGiorniFerieAnnoPrecedente()%></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiFerie"
							onClick='apriFormAggiunta("<%=vigile.getEmail()%>")'>Aggiungi
							Ferie</button></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-danger" data-toggle="modal"
							data-target="#rimuoviFerie"
							onClick='apriFormRimozione("<%=vigile.getEmail()%>")'>Rimuovi
							Ferie</button></td>
				</tr>

				<%
						}
					}
				%>

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
	<script src="JS/datePickerMod.js"></script>




	<script>
	

	var picker = new Litepicker(
			{
				element : document.getElementById('dataInizio'),
				elementEnd : document.getElementById('dataFine'),
				singleMode : false,
				format : 'DD-MM-YYYY',
				lang : 'it',
				numberOfMonths : 1,
				numberOfColumns : 1,
				inlineMode : true,
				minDate : new Date(),
				disallowLockDaysInRange : false,
				showTooltip : false,
				onError : function(error) {
					alertInsuccesso("Nel periodo selezionato risultano già dei giorni di ferie.");
				},
				
				onSelect : function() {
					if ($("#dataInizio").val() != "") {

						var differenza = calcolaGiorniFerie($("#dataInizio").val(),$("#dataFine").val());

						var email = $("#emailAggiuntaFerie").val();
						var ferie = $(
								".listaVigili td:contains('" + email + "')")
								.next('td');
						var totaleFerie = parseInt(ferie.text());
						if (differenza == 0) {
							picker.setOptions({
								startDate : null,
								endDate : null
							});
							$("#dataInizio").val("");
							$("#dataFine").val("");
							$("#messaggioFerie1")
									.text(
											"Nei giorni selezionati non cade neanche un giorno lavorativo.");
							$("#messaggioFerie2").text("");
							$("#messaggioFerie1")
									.attr("style", "color:red");
							$("#messaggioFerie2")
									.attr("style", "color:red");
							$('#bottoneAggiungiFerie').prop("disabled",
									true);
							alertInsuccesso("Nei giorni selezionati non cade neanche un giorno lavorativo.");
						} else if (totaleFerie < differenza) {
							picker.setOptions({
								startDate : null,
								endDate : null
							});
							$("#dataInizio").val("");
							$("#dataFine").val("");
							$("#messaggioFerie1")
									.text(
											"Hai selezionato un periodo troppo grande.");
							$("#messaggioFerie2")
									.text(
											"Hai a disposizione "
													+ totaleFerie
													+ " giorni di ferie, ne hai selezionati "
													+ differenza + ".");
							$("#messaggioFerie1")
									.attr("style", "color:red");
							$("#messaggioFerie2")
									.attr("style", "color:red");
							$('#bottoneAggiungiFerie').prop("disabled",
									true);
							alertInsuccesso("Hai selezionato un periodo troppo grande.");

						} else {
							$("#messaggioFerie1").text(
									"Periodo selezionato correttamente.");
							$("#messaggioFerie2").text("");
							$("#messaggioFerie1").attr("style",
									"color:green");
							$("#messaggioFerie2").attr("style",
									"color:green");
							$('#bottoneAggiungiFerie').prop("disabled",
									false);
							alertSuccesso("Hai selezionato correttamente il periodo di ferie.");
						}
					}
				}
				

			});
	
	
	
	
		var picker2 = new Litepicker(
				{
					element : document.getElementById('rimozioneDataIniziale'),
					elementEnd : document.getElementById('rimozioneDataFinale'),
					singleMode : false,
					format : 'DD-MM-YYYY',
					lang : 'it',
					numberOfMonths : 1,
					numberOfColumns : 1,
					inlineMode : true,
					minDate : new Date(),
					disallowLockDaysInRange : false,
					showTooltip : false,
					onError : function(error) {
						
					},
					onSelect : function() {
						$('#bottoneRimuoviFerie').prop("disabled", false);
							}
							
							
					
						

				});

		

		
		
		
		
		
		
		

		function alertInsuccesso(input) {
			$("#rimozioneNoOk span").text(input);
			$("#rimozioneNoOk span").show();
			$("#rimozioneNoOk").fadeTo(4000, 500).slideUp(5000000, function() {
				$("#success-alert").slideUp(5000000);
			});

		}

		function alertSuccesso(input) {
			$("#rimozioneOk span").text(input);
			$("#rimozioneOk").show();
			$("#rimozioneOk").fadeTo(4000, 500).slideUp(5000000, function() {
				$("#success-alert").slideUp(5000000);
			});
		}
		
		function nascondiOk(){
			document.getElementById("rimozioneOk").style.display="none";
		}
		
		function nascondiNoOk(){
			document.getElementById("rimozioneNoOk").style.display="none";
		}

		function calcolaGiorniFerie(iniz,fin) {
			var giornoLavorativo = moment("24/12/2019", 'DD/MM/YYYY');
			var differenza = 0;
			var inizio = moment(iniz,
					'DD/MM/YYYY');
			var fine = moment(fin,
					'DD/MM/YYYY');
			console.log(inizio);
			console.log(fine);
			while (inizio.diff(fine) <= 0) {
				var diff = inizio.diff(giornoLavorativo, 'days');
				var resto = diff % 4;
				if (resto == 0 || resto == 1)
					differenza++;

				inizio.add(1, 'days').format("DD");
			}
			return differenza;
		}
		
		


		function apriFormAggiunta(input) {
			console.log("parte funzione apriformAggiunta di " + input);
			picker.setOptions({
				startDate : null,
				endDate : null
			});
			$("#dataInizio").val("");
			$("#dataFine").val("");
			picker.setLockDays([]);
			$("#messaggioFerie1").text("");
			$("#messaggioFerie2").text("");

			$.ajax({
				type : "POST",
				url : "GestioneFerieServlet",
				data : {
					"JSON" : true,
					"aggiunta" : true,
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
					$(".contenutiModal").css('background-color', '#e6e6e6');
					
					/*$( "a.day-item" ).hover(
							  function() {
								  var giorno=$(this).text();
								  var mese=$(this).parent().parent().children("div .month-item-header").children("div").text().trim();
							    console.log("hover "+giorno+ " , "+mese);
				                $("a.day-item :contains('"+giorno+"')").css({"color": "yellow", "font-size": "200%"});

							  }, function() {
								    console.log("non hover");
							  }
							);
					*/
					
					
				},
				error : function(jqXHR, textStatus, errorThrown) {

					$(document.body).html(jqXHR.responseText);

				},
			});

		}

		function apriFormRimozione(input) {
			console.log("parte funzione apriformRimozione di " + input);
			picker2.setOptions({
				startDate : null,
				endDate : null
			});
			$("#rimozioneDataIniziale").val("");
			$("#rimozioneDataFinale").val("");
			picker.setLockDays([]);
			$('#bottoneRimuoviFerie').prop("disabled",
					true);
			
			
			
			var inputDataIniziale = $("#rimozioneDataIniziale");
			var inputDataFinale = $("#rimozioneDataFinale");
			$(inputDataIniziale).val("");
			$(inputDataFinale).val("");
			var cognome = $(".listaVigili td:contains('" + input + "')").prev(
					'td');
			var nome = $(cognome).prev('td');
			console.log("cognome: " + cognome.text() + " nome: " + nome.text());
			$("#titoloRimuoviFerie").text(
					"Rimuovi ferie per " + nome.text() + " " + cognome.text());

			$.ajax({
				type : "POST",
				url : "GestioneFerieServlet",
				data : {
					"JSON" : true,
					"rimozione" : true,
					"email" : input,
				},
				dataType : "json",
				async : false,
				success : function(response) {
					picker2.setLockDays(response);
					var dataFinale;
					for ( var i in response) {
						var range = response[i];
						dataFinale = range[1];
						console.log("Data finale!!!! "+dataFinale);
					}
					picker2.setOptions({
						maxDate : dataFinale
					});

					$("#emailRimozioneFerie").val(input);
					$(".contenutiModal").css('background-color', '#e6e6e6');
					var tabella = $("#tabellaRimozioneFerie");
					tabella.empty();

					for ( var i in response) {
						var range = response[i];
						var dataIniziale = range[0];
						var dataFinale = range[1];
						var rigaTabella = document.createElement("TR");
						tabella.append(rigaTabella);

						var colonnaDataIniziale = document.createElement("TD");
						var nome = document.createTextNode(dataIniziale);
						colonnaDataIniziale.appendChild(nome);
						rigaTabella.appendChild(colonnaDataIniziale);

						var colonnaDataFinale = document.createElement("TD");
						var cognome = document.createTextNode(dataFinale);
						colonnaDataFinale.appendChild(cognome);
						rigaTabella.appendChild(colonnaDataFinale);

						var colonnaBottone = document.createElement("TD");

						var bottone = document.createElement("button");
						$(bottone).attr("class", "btn btn-outline-primary");
						$(bottone).attr("data-toggle", "modal");
						$(bottone).attr("data-target", "#menuConferma");
						$(bottone).text("Rimuovi");
						$(bottone).attr("onclick", "setDate(this)");
						colonnaBottone.appendChild(bottone);
						rigaTabella.appendChild(colonnaBottone);
						console.log("data iniziale: " + dataIniziale
								+ " ,data finale: " + dataFinale);

					}

				},
				error : function(jqXHR, textStatus, errorThrown) {

					$(document.body).html(jqXHR.responseText);

				},
			});
		}

		function setDate(input) {
			var inputDataIniziale = $("#rimozioneDataIniziale");
			var inputDataFinale = $("#rimozioneDataFinale");
			var dataFinale = $(input).parent().prev('td');
			var dataIniziale = $(dataFinale).prev('td');
			$(inputDataIniziale).val(dataIniziale.text());
			$(inputDataFinale).val(dataFinale.text());

			console.log("data inizz " + dataIniziale.text() + " datafin "
					+ dataFinale.text());
		}


		function rimuoviFerie() {
			var dataIniziale = $("#rimozioneDataIniziale").val();
			var dataFinale = $("#rimozioneDataFinale").val();
			var email = $("#emailRimozioneFerie").val();
			$
					.ajax({
						type : "POST",
						url : "RimuoviFerieServlet",
						data : {
							"dataIniziale" : dataIniziale,
							"dataFinale" : dataFinale,
							"email" : email,
						},
						dataType : "json",
						async : true,
						success : function(response) {
							var booleanRisposta = response[0];
							if (booleanRisposta) {
								var ferie = $(".listaVigili td:contains('" +email+ "')").next('td');
					            ferie.text(response[2] + response[1]);
								alertSuccesso("Rimozione ferie avvenuta con successo.");
							} else {
								
								console.log("problema rimozione ferie "
										+ dataIniziale + " " + dataFinale
										+ " di " + email);
								apriFormRimozione(email);
								alertInsuccesso("Rimozione ferie non avvenuta a causa di un errore imprevisto.");
							}
						},
						
						error : function(jqXHR, textStatus, errorThrown) {

							$(document.body).html(jqXHR.responseText);

						},
					});
		}

		function aggiungiFerie() {
			var dataIniziale = $("#dataInizio").val();
			var dataFinale = $("#dataFine").val();
			var email = $("#emailAggiuntaFerie").val();
			console.log("aggiungiFerie data: " + dataIniziale + " fino a "
					+ dataFinale + " email: " + email);
			$
					.ajax({
						type : "POST",
						url : "AggiungiFerieServlet",
						data : {
							"dataIniziale" : dataIniziale,
							"dataFinale" : dataFinale,
							"email" : email,
						},
						dataType : "json",
						async : true,
						success : function(response) {
							var booleanRisposta = response[0];
							if (booleanRisposta) {
								var riga = $(".listaVigili td:contains('"
										+ email + "')");
								console.log("inserite ferie " + dataIniziale
										+ " " + dataFinale + " di " + email);
								var ferie = $(".listaVigili td:contains('" +email+ "')").next('td');
					            ferie.text(response[2] + response[1]);
					            if(response[3]){
					            	//window.location.replace("GestioneFerieServlet");
					            }
					            alertSuccesso("Inserimento ferie avvenuto con successo.");
							} else {
								console.log("problema inserimento ferie "
										+ dataIniziale + " " + dataFinale
										+ " di " + email);
								apriFormAggiunta(email);
								alertInsuccesso("Aggiunta ferie non avvenuta a causa di un errore imprevisto.");
							}
						},
						error : function(jqXHR, textStatus, errorThrown) {

							$(document.body).html(jqXHR.responseText);

						},
					});
		}
	</script>
	<script>
		function ricaricaPagina(){
			window.location.replace("GestioneFerieServlet");
		}
	</script>
</body>
</html>