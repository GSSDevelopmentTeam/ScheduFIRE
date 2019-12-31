<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, model.bean.*, model.dao.*"%>

<html>
	<jsp:include page="StandardJSP.jsp" />
	
	
	<body>
	
	
	
	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />
	<h2 class="d-flex justify-content-center" style="color:#B60000!Important">Gestione Malattie</h2>
	
	
	
	<!-- Modal di aggiunta malattia-->
	<div class="modal fade" id="aggiungiMalattia" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true" style="display: none">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content contenutiModal">
				<div class="modal-header">
					<h5 class="modal-title" id="titoloAggiuntaMalattia"
						>Aggiunta malattia</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<input type="hidden" name="email" id="emailAggiuntaMalattia"></input>
					<div class=" row justify-content-center">
						<input id="dataInizio" placeholder="Giorno iniziale" readonly
							size="34" /> <input id="dataFine" placeholder="Giorno finale"
							readonly size="34" />

					</div>
				</div>

				<div class="modal-footer">
				<button type="button" class="btn btn-outline-warning" onClick = "inserisciMalattia()">Aggiungi Malattia</button>
					<button type="button" class="btn btn-outline-danger""
						data-dismiss="modal">Annulla</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- body aggiungi malattia -->
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Email</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Grado</th>
					<th class="text-center">Inserisci Malattia</th>
				</tr>
			</thead>
				
				<tbody>
				 <%	ArrayList<VigileDelFuocoBean> listaVigili;
						listaVigili= (ArrayList<VigileDelFuocoBean>) request.getAttribute("listaVigili");
						
						for(int i=0; i<listaVigili.size(); i++){
							VigileDelFuocoBean vigile = listaVigili.get(i);
					%>
					
					<tr>
						<td class="text-center"><%=vigile.getNome() %></td>
						<td class="text-center"><%=vigile.getCognome()%></td>
						<td class="text-center"><%=vigile.getEmail() %></td>
						<td class="text-center"><%=vigile.getMansione()%></td>
						<td class="text-center"><%=vigile.getGrado() %></td>
						<td class="text-center"><button class="pass btn btn-outline-danger" 
						data-toggle="modal" data-target="#aggiungiMalattia"
						onClick='apriFormAggiunta("<%=vigile.getEmail()%>")'>Aggiungi Malattia</button></td>
					</tr>
		
<% } %> 				
				
				</tbody>
			
			</table>
		</div>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="JS/datePicker.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
	<script src="https://buttons.github.io/buttons.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
 		
		
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
						tooltipText : {
							one : 'giorno',
							other : 'giorni',
						},
						onError : function(error) {
							alert("Nel periodo selezionato risultano già dei giorni di ferie");
						},
						onHide : function() {
							if ($("#dataInizio").val() != "") {
								var giornoLavorativo = moment("24/12/2019",
										'DD/MM/YYYY');
								var lavorativo = 0;
								var inizio = moment(document
										.getElementById("dataInizio").value,
										'DD/MM/YYYY');
								var fine = moment(document
										.getElementById("dataFine").value,
										'DD/MM/YYYY');
								console.log(inizio);
								console.log(fine);

								while (inizio.diff(fine) <= 0) {
									var differenza = inizio.diff(giornoLavorativo,
											'days');
									var resto = differenza % 4;
									if (resto == 0 || resto == 1)
										lavorativo++;

									inizio.add(1, 'days').format("DD");
								}
								alert("giorni di ferie effettivi: "
										+ lavorativo
										+ "\n ToDo verifica che giorni di ferie presi non superino quelli disponibili");
							}
						}

					});

			
			
			
			function apriFormAggiunta(input) {
				console.log("parte funzione apriformAggiunta di " + input);
				picker.setOptions({
					startDate : null,
					endDate : null
				});
				$("#dataInizio").val("");
				$("#dataFine").val("");
				picker.setLockDays([]);
				
				<!--salva il valore della mail nell'elemento identificato come emailAggiuntaMalattia-->
				var mail = document.getElementById("emailAggiuntaMalattia");
				mail.innerHTML = input;
				
				
				<!--visualizza aschermo ail nome del vigile che si intende aggiungere un periodo di malattia-->
				
				var cognome = $(".table td:contains('" + input + "')")
						.prev('td');
				var nome = $(cognome).prev('td');
				
				console.log("cognome: " + cognome.text() + " nome: "
						+ nome.text());
				
				$("#titoloAggiuntaMalattia").text(
						"Aggiunta malattia per " + nome.text() + " "
								+ cognome.text());

				$.ajax({
					type : "POST",
					url : "PeriodiDiMalattiaServlet",
					data : {
						"JSON" : true,
						"aggiunta": true,
						"email" : input,
					},
					dataType : "json",
					async : false,
					success : function(response) {
						picker.setLockDays(response);
						console.log("settati giorni di malattia: " + response);
						
						$('#formAggiunta').show();
						
						$(".contenutiModal").css('background-color', '#e6e6e6');
					}
				});
			}
			</script>
			
			<script>
			function inserisciMalattia(){
				$(document).ready(function(){
					 var emailvf = document.getElementById("emailAggiuntaMalattia").innerHTML;
					 var dataIn = $('#dataInizio').val();
					 var dataFi = $('#dataFine').val();
					 
					 
					 $.ajax({
						type : "POST",
						url : "PeriodiDiMalattiaServlet",
						data : {
							"JSON" : true,
							"aggiunta": true,
							"emailVF" : emailVF,
							"dataInizio" : dataInizio,
							"dataFine" : dataFine,
						},
						dataType : "json",
						async : false,
						
						success : function(response) {
							picker.setLockDays(response);
							
							console.log("settati giorni di malattia: " + response);
							
							$('#formAggiunta').show();
							
							$(".contenutiModal").css('background-color', '#e6e6e6');
						}
					});	
				}
				
				alert(emailVF);
					}
			</script>
				
	</body>
</html>