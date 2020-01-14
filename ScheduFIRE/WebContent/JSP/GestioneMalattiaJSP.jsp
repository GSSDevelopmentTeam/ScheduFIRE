<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, model.bean.*, model.dao.*"%>
<!DOCTYPE html>
<html>
<jsp:include page="StandardJSP.jsp" />
<link type="text/css" rel="stylesheet"
	href="./CSS/GestionePersonaleCSS.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="JS/datePicker.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
<script src="https://buttons.github.io/buttons.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
<script src="JS/ferieJS.js"></script>
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
	<body>
	<a href="#sali" class=" back-up"><img src="IMG/arrow/up-arrow-p.png" style="margin-left: 5px;"
					onmouseover="this.src='IMG/arrow/up-arrow-d.png'"
					onmouseout="this.src='IMG/arrow/up-arrow-p.png'" /></a>
					
					
	<!-- Modal di aggiunta malattia-->
	<div class="modal fade" id="aggiungiMalattia" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalCenterTitle"
		aria-hidden="true" style="display: none">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content contenutiModal" style="min-height: 700px;">
				<div class="modal-header">
					<h5 class="modal-title" id="titoloAggiuntaMalattia">Aggiunta
						malattia</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p>Per selezionare un solo giorno, cliccare due volte sulla
						data desiderata.</p>
					<p hidden="hidden" name="email" id="emailAggiuntaMalattia"></p>
					<div class=" row justify-content-center">
						<input id="dataInizio" placeholder="Giorno iniziale" size="34" style="margin-bottom:1%;" />
						<input id="dataFine" placeholder="Giorno finale" size="34" style="margin-bottom: 2%;"/>
					</div>
					<div class="text-center" id="messaggioTurno"></div>

					<div class="text-center" id="messaggioMalattia1"></div>
					<div class="text-center" id="messaggioMalattia2"></div>

				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-outline-success"
						id="botAggiungiMalattia" data-toggle="modal"
						data-target="#modalAvviso" data-dismiss="modal"
						onClick="inserisciMalattia()" disabled>Conferma</button>
					<button type="button" class="btn btn-outline-danger"
						data-dismiss="modal">Annulla</button>

				</div>
			</div>
		</div>
	</div>



	<!-- Modal rimozione malattia -->
	<div class="modal fade" id="rimuoviMalattia" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalCenterTitle"
		aria-hidden="true" style="display: none">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content contenutiModal">
				<div class="modal-header">
					<h5 class="modal-title" id="titoloRimozioneMalattia">Rimuovi
						Malattia</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times; </span>
					</button>
				</div>
				<div class="modal-body">
					<p>Per selezionare un solo giorno, cliccare due volte sulla
						data desiderata.</p>
					<p hidden="hidden" name="email" id="emailRimozioneMalattia"></p>
					<div class=" row justify-content-center">
						<input id="rimozioneDataIniziale" placeholder="Giorno iniziale"
							readonly size="34" style="margin-bottom:1%;"/> <input id="rimozioneDataFinale"
							placeholder="Giorno finale" readonly size="34" style="margin-bottom:2%;"/>
					</div>
					<div class="text-center" id="messaggioTurno"></div>

					<div class="text-center" id="messaggioMalattia1"></div>
					<div class="text-center" id="messaggioMalattia2"></div>


				</div>
				<div class="modal-footer">

					<button type="button" class="btn btn-outline-success"
						id="bottoneRimuoviMalattia" data-toggle="modal"
						data-target="#modalAvviso" data-dismiss="modal"
						onClick="rimuoviMalattia()" disabled>Rimuovi</button>

					<button type="button" class="btn btn-outline-danger"
						data-dismiss="modal">Annulla</button>

				</div>
			</div>
		</div>

	</div>


	<!-- Modal di avviso aggiunta malattia-->
	<div class="modal fade" id="modalAvviso" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-sm modal-dialog-centered"
			role="document">
			<div class="modal-content" style="border: 3px solid #5be94b;">
				<div class="modal-body" style="align: center;">
					<img src="IMG/fire.png" class="rounded mx-auto d-block">
					<h4 class="modal-title text-center">Operazione effettuata con
						successo</h4>
				</div>
				<div class="modal-footer">


					<button type="button" class="btn btn-outline-success"
						data-dismiss="modal" onClick="ricaricaPagina()">OK</button>

				</div>
			</div>
		</div>
	</div>

	<!-- Barra Navigazione -->
	<div id="sali"></div>
	<jsp:include page="HeaderJSP.jsp" />

	<h2 class="d-flex justify-content-center"
		style="color: #B60000 !Important; margin-top: 3%; font-size: 45px;">Gestione
		Malattia</h2>

	<!-- form per l'ordinamento della lista dei VF-->
	<%
		Object ordinamentoObj = request.getAttribute("ordinamento");
		String ordinamento = (String) ordinamentoObj;
		%>
	<form action="./PeriodiDiMalattiaServlet">
		<div align="center">
			<label>Lista ordinata per</label> <select class="custom-select"
				name="ordinamento" onchange="this.form.submit()" style="width: 15%">

				<%
					if( ordinamento != null ) {
						if( ordinamento.equals("nome") ) {
					%>
				<option value="nome" selected>Nome</option>
				<option value="cognome">Cognome</option>
				<option value="grado">grado</option>

				<%
						} else if( ordinamento.equals("cognome") ) {		
						%>
				<option value="nome">Nome</option>
				<option value="cognome" selected>Cognome</option>
				<option value="grado">grado</option>
				<%
						}  else if( ordinamento.equals("grado") ) {		
						%>
				<option value="nome">Nome</option>
				<option value="cognome">Cognome</option>
				<option value="grado" selected>grado</option>
				<%}
					}%>

			</select>
		</div>
		<br>
	</form>


	<!-- body aggiungi malattia -->
	<div class="table-responsive">

		<div id="capo"></div>
		<div class="d-flex justify-content-center">
			<a href="#auto" class="btn btn-outline-secondary"
				style="margin: 3px;">Autisti</a> <a href="#vigile"
				class="btn btn-outline-secondary" style="margin: 3px;">Vigili</a>
		</div>
		<h4 class="d-flex justify-content-center" id="inizio"
			style="margin-top: 0%; color: #B60000 !Important">Capi Squadra</h4>

		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center" width=14.28%>Grado</th>
					<th class="text-center" width=14.28%>Nome</th>
					<th class="text-center" width=14.28%>Cognome</th>
					<th class="text-center" width=14.28%>Email</th>
					<th class="text-center" width=14.28%>Inserisci Malattia</th>
					<th class="text-center" width=14.28%>Rimuovi Malattia</th>
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
					<td class="text-center"><strong><%=vigile.getNome() %></strong></td>
					<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
					<td class="text-center"><%=vigile.getEmail() %></td>
					<td class="text-center"><button
							class="pass btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiMalattia"
							onClick='apriFormAggiunta("<%=vigile.getEmail()%>")'>Aggiungi
							Malattia</button></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-danger" data-toggle="modal"
							data-target="#rimuoviMalattia"
							onClick='apriFormRimozione("<%=vigile.getEmail()%>")'>Rimuovi
							Malattia</button></td>
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


		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center" width=14.28%>Grado</th>
					<th class="text-center" width=14.28%>Nome</th>
					<th class="text-center" width=14.28%>Cognome</th>
					<th class="text-center" width=14.28%>Email</th>
					<th class="text-center" width=14.28%>Inserisci Malattia</th>
					<th class="text-center" width=14.28%>Rimuovi Malattia</th>
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
					<td class="text-center"><strong><%=vigile.getNome() %></strong></td>
					<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
					<td class="text-center"><%=vigile.getEmail() %></td>
					<td class="text-center"><button
							class="pass btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiMalattia"
							onClick='apriFormAggiunta("<%=vigile.getEmail()%>")'>Aggiungi
							Malattia</button></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-danger" data-toggle="modal"
							data-target="#rimuoviMalattia"
							onClick='apriFormRimozione("<%=vigile.getEmail()%>")'>Rimuovi
							Malattia</button></td>
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


		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center" width=14.28%>Grado</th>
					<th class="text-center" width=14.28%>Nome</th>
					<th class="text-center" width=14.28%>Cognome</th>
					<th class="text-center" width=14.28%>Email</th>
					<th class="text-center" width=14.28%>Inserisci Malattia</th>
					<th class="text-center" width=14.28%>Rimuovi Malattia</th>
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
					<td class="text-center"><strong><%=vigile.getNome() %></strong></td>
					<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
					<td class="text-center"><%=vigile.getEmail() %></td>

					<td class="text-center"><button
							class="pass btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiMalattia"
							onClick='apriFormAggiunta("<%=vigile.getEmail()%>")'>Aggiungi
							Malattia</button></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-danger" data-toggle="modal"
							data-target="#rimuoviMalattia"
							onClick='apriFormRimozione("<%=vigile.getEmail()%>")'>Rimuovi
							Malattia</button></td>
				</tr>

				<%
						}
					}
				%>

			</tbody>

		</table>
	</div>



	<!--avvenuto inserimento-->

	<div
		class="alert alert-success flex alert-dismissible fade in text-center fixed-top"
		id="inserimentoOk"
		style="display: none; position: fixed; z-index: 99999; width: 100%">
		<strong>Operazione riuscita!</strong> <span>Malattia aggiunta
			correttamente..</span>
	</div>

	<!-- ----------------------- -->

	<!--------- inserimento non avvenuto ----------------->

	<div
		class="alert alert-danger flex alert-dismissible fade in text-center fixed-top"
		id="inserimentoNoOk"
		style="display: none; position: fixed; z-index: 99999; width: 100%">
		<strong>Errore!</strong> <span>Aggiunta malattia non avvenuta..</span>
	</div>




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
						disallowLockDaysInRange : true,
						showTooltip:false,
						onError : function(error) {
							$("#messaggioMalattia1").text("Nel periodo selezionato risultano già dei giorni di malattia.");
							$("#messaggioMalattia1").attr("style","color:red");
						},
						onSelect : function() {
						
							var differenza = calcolaGiorniMalattia($("#dataInizio").val(),$("#dataFine").val());
							
							$("#messaggioMalattia1").text("Periodo selezionato correttamente.");
							$("#messaggioMalattia2").text("Hai selezionato " + differenza + " giorni di malattia.");
							$("#messaggioMalattia1").attr("style","color:green");
							$("#messaggioMalattia2").attr("style","color:green");
							$('#botAggiungiMalattia').prop("disabled",false);
								}});	
			
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
							$("#messaggioMalattia1").text("Nel periodo selezionato alcuni giorni non sono di malattia.");
							$("#messaggioMalattia1").attr("style","color:red");
						},
						onSelect : function() {
							$('#bottoneRimuoviMalattia').prop("disabled", false);
							var differenza = calcolaGiorniMalattia($("#dataInizio").val(),$("#dataFine").val());
							
							$("#messaggioMalattia1").text("Periodo selezionato correttamente.");
							$("#messaggioMalattia1").attr("style","color:green");
							$('#botAggiungiMalattia').prop("disabled",false);
								},
					});
			
			function calcolaGiorniMalattia(inizale,finale) {
				//trasformo le date nel formato aaaammgg 
				anno1 = parseInt(inizale.substr(6),10);
				mese1 = parseInt(inizale.substr(3, 2),10);
				giorno1 = parseInt(inizale.substr(0, 2),10);
			     
				anno2 = parseInt(finale.substr(6),10);
				mese2 = parseInt(finale.substr(3, 2),10);
				giorno2 = parseInt(finale.substr(0, 2),10);
				
		        data1str = anno1+mese1+giorno1;
				data2str = anno2+mese2+giorno2;
				//controllo se la date sono uguali
		        if (data2str-data1str == 0) {
		            return 1;
		        }else{

				anno1 = parseInt(inizale.substr(6),10);
				mese1 = parseInt(inizale.substr(3, 2),10);
				giorno1 = parseInt(inizale.substr(0, 2),10);
			     
				anno2 = parseInt(finale.substr(6),10);
				mese2 = parseInt(finale.substr(3, 2),10);
				giorno2 = parseInt(finale.substr(0, 2),10);

			    var dataok1=new Date(anno1, mese1-1, giorno1);
				var dataok2=new Date(anno2, mese2-1, giorno2);
				
				differenza = dataok2-dataok1;    
				giorni_differenza = new String(1+differenza/86400000);
				
				return giorni_differenza;}
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
				$("#messaggioMalattia1").text("");
				$("#messaggioMalattia2").text("");
				
				$.ajax({
					type : "POST",
					url : "PeriodiDiMalattiaServlet",
					data : {
						"visMalattia" : true,
						"JSON" : true,
						"emailVF" : input,
					},
					dataType : "json",
					async : false,
					success : function(response) {
						picker.setLockDays(response);
               
        				var mail = document.getElementById("emailAggiuntaMalattia");
        				mail.innerHTML = input;
        				
        				var cognome = $(".table td:contains('" + input + "')").prev('td');
        				var nome = $(cognome).prev('td');
    
        			$("#titoloAggiuntaMalattia").text(
        						"Aggiunta malattia per " + nome.text() + " "+ cognome.text());	
					$(".contenutiModal").css('background-color', '#e6e6e6');
					}
					
				});
			}		
			
			function apriFormRimozione(input) {
				console.log("parte funzione apriformRimozione di " + input);
				document.getElementById("emailAggiuntaMalattia").innerHTML = input;
				picker2.setOptions({
					startDate : null,
					endDate : null
				});
				$("#rimozioneDataIniziale").val("");
				$("#rimozioneDataFinale").val("");
				picker.setLockDays([]);
				$('#bottoneRimuoviMalattia').prop("disabled",
						true);
				
				var inputDataIniziale = $("#rimozioneDataIniziale");
				var inputDataFinale = $("#rimozioneDataFinale");
				$(inputDataIniziale).val("");
				$(inputDataFinale).val("");
				picker.setLockDays([]);
				$("#messaggioMalattia1").text("");
				$("#messaggioMalattia2").text("");
				
				$.ajax({
					type : "POST",
					url : "PeriodiDiMalattiaServlet",
					data : {
						"JSON" : true,
						"rimozione" : true,
						"emailVF" : input,
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
						
						var mail = document.getElementById("emailRimozioneMalattia");
        				mail.innerHTML = input;
						$(".contenutiModal").css('background-color', '#e6e6e6');
        				        				
        				var cognome = $(".table td:contains('" + input + "')").prev('td');
        				var nome = $(cognome).prev('td');
    
        			$("#titoloRimozioneMalattia").text(
        						"Aggiunta malattia per " + nome.text() + " "+ cognome.text());
        				
					$(".contenutiModal").css('background-color', '#e6e6e6');
					}
				});
			}
			</script>

	<script>
			function inserisciMalattia(){
				 var emailVF = document.getElementById("emailAggiuntaMalattia").innerHTML;
				 var dataIn = $('#dataInizio').val();
				 var dataFi = $('#dataFine').val();

				 $.ajax({
				type : "POST",
				url : "AggiungiMalattiaServlet",
				data : {
					"inserisci": true, 
					"JSON" : true,
					"emailVF": emailVF,
					"dataInizio": dataIn,
					"dataFine" : dataFi
				},
						dataType : "json",
						async : false,
						
					
				 success : function(response) {
					 var Risposta=response[0];
					 if(Risposta){
					 }
					 else{
						 apriFormAggiunta();
					 }
				 },
				 error : function(jqXHR, textStatus, errorThrown) {

						$(document.body).html(jqXHR.responseText);

					},
			});	
		}
			
			function rimuoviMalattia(){
				var dataIniziale = $("#rimozioneDataIniziale").val();
				var dataFinale = $("#rimozioneDataFinale").val();
				var emailVF = document.getElementById("emailRimozioneMalattia").innerHTML;
				console.log("email = " + emailVF);
				$.ajax({
					type : "POST",
					url : "RimozioneMalattiaServlet",
					data : {
						"dataIniziale" : dataIniziale,
						"dataFinale" : dataFinale,
						"emailVF" : emailVF,
					},
					dataType : "json",
					async : true,
					success : function(response) {
						var booleanRisposta = response[0];
						if (booleanRisposta == 'true') {
		
						} else {
							
							console.log("problema rimozione ferie "
									+ dataIniziale + " " + dataFinale
									+ " di " + email);
							apriFormRimozione(email);
							//alertInsuccesso("Rimozione malattia non avvenuta a causa di un errore imprevisto.");
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
				window.location.replace("PeriodiDiMalattiaServlet");
			}
			</script>
	<script>
			
			function alertInsuccesso(input){
				$("#inserimentoNoOk span").text(input);
				$("#inserimentoNoOk").fadeTo(4000, 500).slideUp(500, function(){
				    $("#success-alert").slideUp(500);
				});
			}
			
			function alertSuccesso(input){
				$("#inserimentoOk span").text(input);
				$("#inserimentoOk").fadeTo(4000, 500).slideUp(500, function(){
				    $("#success-alert").slideUp(500);
				});
			}
			</script>

</body>
</html>