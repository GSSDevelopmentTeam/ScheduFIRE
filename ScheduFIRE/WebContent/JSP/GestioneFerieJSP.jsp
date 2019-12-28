<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, model.bean.*, model.dao.*"%>

<html>
<head>
<jsp:include page="StandardJSP.jsp" />
<link href="https://wakirin.github.io/Litepicker/css/style.css"
	rel="stylesheet" />
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<body>

	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />
	<h2 class="d-flex justify-content-center">Gestione Ferie</h2>






	<!-- Modal di aggiunta ferie-->
	<div class="modal fade" id="aggiungiFerie" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true"
		style="display: none">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content contenutiModal">
				<div class="modal-header">
					<h5 class="modal-title" id="titoloAggiuntaFerie">Aggiunta
						ferie</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<input type="hidden" name="email" id="emailAggiuntaFerie">
					<div class=" row justify-content-center">
						<input id="dataInizio" placeholder="Giorno iniziale" readonly
							size="34" /> <input id="dataFine" placeholder="Giorno finale"
							readonly size="34" />

					</div>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Annulla</button>
					<button type="button" class="btn btn-primary">Aggiungi
						ferie</button>
				</div>
			</div>
		</div>
	</div>



	<!-- Modal di rimozione ferie-->
	<div class="modal fade" id="rimuoviFerie" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true"
		style="display: none">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content contenutiModal">
				<div class="modal-header">
					<h5 class="modal-title" id="titoloRimuoviFerie">Rimuovi ferie</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<input type="hidden" name="email" id="emailRimozioneFerie">
					<div class=" row justify-content-center">
					<div class="table-responsive">
		<table class="table table-hover" id="tabellaRimozioneFerie">
			<thead class="thead-dark">
				<tr>
					<th>Data Inizio</th>
					<th>Data Fine</th>
					<th>Elimina</th>
					
				</tr>
			</thead>
			<tbody>
			</tbody>
			</table>

					</div>

				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Annulla</button>
					<button type="button" class="btn btn-primary">Salva
						modifiche</button>
				</div>
			</div>
		</div>
	</div>
</div>




	<div class="table-responsive">
		<table class="table  table-hover">
			<thead class="thead-dark">
				<tr>
					<th>Grado</th>
					<th>Nome</th>
					<th>Cognome</th>
					<th>Email</th>
					<th>Mansione</th>
					<th>Ferie anno <br> corrente
					</th>
					<th>Ferie anno <br> precedente
					</th>
					<th>Inserisci <br> periodo di ferie
					</th>
					<th>Rimuovi <br>periodo di ferie
					</th>
				</tr>
			</thead>

			<tbody>
				<%
					ArrayList<VigileDelFuocoBean> listaVigili;
					listaVigili = (ArrayList<VigileDelFuocoBean>) request.getAttribute("listaVigili");

					for (int i = 0; i < listaVigili.size(); i++) {
						VigileDelFuocoBean vigile = listaVigili.get(i);
				%>

				<tr>
					<td><%=vigile.getGrado()%></td>
					<td><%=vigile.getNome()%></td>
					<td><%=vigile.getCognome()%></td>
					<td><%=vigile.getEmail()%></td>
					<td><%=vigile.getMansione()%></td>
					<td><%=vigile.getGiorniFerieAnnoCorrente()%></td>
					<td><%=vigile.getGiorniFerieAnnoCorrente()%></td>
					<td><button type="button" class="btn btn-outline-primary"
							data-toggle="modal" data-target="#aggiungiFerie"
							onClick='apriFormAggiunta("<%=vigile.getEmail()%>")'>Aggiungi
							Ferie</button></td>
					<td><button type="button" class="btn btn-outline-primary"
							data-toggle="modal" data-target="#rimuoviFerie"
							onClick='apriFormRimozione("<%=vigile.getEmail()%>")'>Rimuovi
							Ferie</button></td>
				</tr>

				<%
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

			$.ajax({
				type : "POST",
				url : "GestioneFerieServlet",
				data : {
					"JSON" : true,
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

		function apriFormRimozione(input) {
			console.log("parte funzione apriformRimozione di " + input);
			var cognome = $(".table td:contains('" + input + "')").prev('td');
			var nome = $(cognome).prev('td');
			console.log("cognome: " + cognome.text() + " nome: " + nome.text());
			$("#titoloRimuoviFerie").text(
					"Rimuovi ferie per " + nome.text() + " " + cognome.text());

			$.ajax({
				type : "POST",
				url : "GestioneFerieServlet",
				data : {
					"JSON" : true,
					"email" : input,
				},
				dataType : "json",
				async : false,
				success : function(response) {
					$("#emailRimozioneFerie").val(input);
					$(".contenutiModal").css('background-color', '#e6e6e6');
					var tabella=$("#tabellaRimozioneFerie");
					
					for(var i in response) {
						var range=response[i];
						var dataIniziale=range[0];
						var dataFinale=range[1];
						var rigaTabella = document.createElement("TR");
						  tabella.append(rigaTabella);
						  var colonnaDataIniziale = document.createElement("TD");
						  var nome=document.createTextNode(dataIniziale);
						   colonnaDataIniziale.appendChild(nome);
						  rigaTabella.appendChild(colonnaDataIniziale);
						  
						  var colonnaCognome = document.createElement("TD");
						  var cognome=document.createTextNode(dataFinale);
						   colonnaCognome.appendChild(cognome);
						  rigaTabella.appendChild(colonnaCognome);
    					console.log("data iniziale: "+dataIniziale+" ,data finale: "+dataFinale);
    				}
					
					
				}
			});
		}
	</script>
</body>
</html>