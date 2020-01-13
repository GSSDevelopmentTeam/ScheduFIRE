<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.bean.*, model.dao.*, java.sql.Date"%>
<!DOCTYPE html>
<html>
<jsp:include page="StandardJSP.jsp" />
<head>
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
</style>
</head>

<body>
	<div id="inizio"></div>
	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />



	<!-- Modal di avviso operazione effettuata correttamente-->

	<button type="button" data-toggle="modal" id="buttonModalAvviso"
		data-target="#modalAvviso" style="display: none">Bottone
		per modal di Avviso riuscita operazione</button>

	<div class="modal fade" id="modalAvviso" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-sm modal-dialog-centered"
			role="document">
			<div class="modal-content" style="border: 3px solid #5be94b;">
				<p hidden="hidden" name="ricarica" id="ifRicarica"></p>
				<div class="modal-body" style="align: center;">
					<img src="IMG/fire.png" class="rounded mx-auto d-block">
					<h4 class="modal-title text-center" id="titoloModalAvviso">Operazione
						effettuata con successo</h4>
				</div>
				<div class="modal-footer">

					<button type="button" class="btn btn-outline-success"
						data-dismiss="modal">OK</button>

				</div>
			</div>
		</div>
	</div>





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
						<button class="btn btn-outline-success" id="agg" disabled>Aggiungi</button>
						<button type="button" class="btn btn-outline-danger"
							data-dismiss="modal">Annulla</button>

					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- ELENCO SQUADRE  -->
	<%
		HashMap<VigileDelFuocoBean, String> squadraD = (HashMap<VigileDelFuocoBean, String>) session
				.getAttribute("squadraDiurno");
		HashMap<VigileDelFuocoBean, String> squadraN = (HashMap<VigileDelFuocoBean, String>) session
				.getAttribute("squadraNotturno");
		Date giorno = (Date) request.getAttribute("dataDiurno");
		Date notte = (Date) request.getAttribute("dataNotturno");
		
		Iterator in = squadraN.entrySet().iterator();
		while (in.hasNext()) {
			Map.Entry coppia = (Map.Entry) in.next();
			VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();
		}
	%>


	<a href="#inizio" class=" back-up"><img
		src="IMG/arrow/up-arrow-p.png" style="margin-left: 5px;"
		onmouseover="this.src='IMG/arrow/up-arrow-d.png'"
		onmouseout="this.src='IMG/arrow/up-arrow-p.png'" /></a>
	<input type="hidden" id="day" value="<%=giorno%>">
	<input type="hidden" id="night" value="<%=notte%>">
	<h2 class="d-flex justify-content-center"
		style="color: #B60000 !Important; margin-top: 3%; font-size: 45px;">Gestione
		Squadre</h2>

	<br>
	<div class="d-flex justify-content-center">
		<form action="GeneraSquadreServlet?salva=true" method=post>
			<button type="submit" class="btn btn-outline-success btn-lg"
				value="salva" name="salva" style="margin: 3px;">Conferma
				Squadre</button>
		</form>

		 <a href="#Notte" class="btn btn-outline-secondary btn-lg" style="margin: 3px;">Squadra Notturna</a>
		<a href="#Disp"	class="btn btn-outline-secondary btn-lg" style="margin: 3px;">Personale Disponibile</a>

	</div>
	<br>

	<!-- SQUADRA DIURNA -->
	<div class="d-flex justify-content-center">
		<h2 id="Giorno" style="font-weight: bold; font-size: 36px;">
			Squadra Diurna del
			<%=giorno.toLocalDate().format(DateTimeFormatter.ofPattern("dd MMMM YYYY", new Locale("it", "IT"))) %></h2>
	</div>
	<p class="d-flex justify-content-center"></p>
	<div class="d-flex justify-content-center">
		<img src="Icon/caserma.png" class="fr">
		<h2>Sala Operativa</h2>
	</div>

	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Modifica</th>
				</tr>
			</thead>

			<tbody>
				<%
					Iterator it = squadraD.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry coppia = (Map.Entry) it.next();
						VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();
						if (coppia.getValue().equals("Sala Operativa")) {
				%>

				<tr>
					<td class="text-center"><img
						src="Grado/<%=membro.getMansione().equals("Capo Squadra") && membro.getGrado().equals("Esperto")?"EspertoCapoSquadra":membro.getGrado() %>.png"
						title="<%=membro.getGrado() %>"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center"><%=membro.getMansione()%></td>
					<td class="text-center"><strong><%=membro.getNome()%></strong></td>
					<td class="text-center"><strong><%=membro.getCognome()%></strong></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiVF" id="aggiungiVF"
							onClick='apriFormVF("<%=membro.getEmail()%>","<%=membro.getMansione()%>","1","<%=giorno%>","<%=notte%>")'>Sostituisci</button></td>
					</td>
				</tr>
				<%
					}
					}
				%>

			</tbody>
		</table>
	</div>

	<p class="d-flex justify-content-center"></p>
	<div class="d-flex justify-content-center">
		<img src="Icon/sirena.png" class="fr">
		<h2>Prima Partenza</h2>
	</div>
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Modifica</th>
				</tr>
			</thead>

			<tbody>
				<%
					it = squadraD.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry coppia = (Map.Entry) it.next();
						VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();
						if (coppia.getValue().equals("Prima Partenza")) {
				%>

				<tr>
					<td class="text-center"><img
						src="Grado/<%=membro.getMansione().equals("Capo Squadra") && membro.getGrado().equals("Esperto")?"EspertoCapoSquadra":membro.getGrado() %>.png"
						title="<%=membro.getGrado() %>"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center"><%=membro.getMansione()%></td>
					<td class="text-center"><strong><%=membro.getNome()%></strong></td>
					<td class="text-center"><strong><%=membro.getCognome()%></strong></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiVF" id="aggiungiVF"
							onClick='apriFormVF("<%=membro.getEmail()%>","<%=membro.getMansione()%>","1","<%=giorno%>","<%=notte%>")'>Sostituisci</button></td>
					</td>
				</tr>
				<%
					}
					}
				%>

			</tbody>
		</table>
	</div>

	<p class="d-flex justify-content-center"></p>
	<div class="d-flex justify-content-center">
		<img src="Icon/autoscala.png" class="fr">
		<h2>Auto Scala</h2>
	</div>
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Modifica</th>
				</tr>
			</thead>

			<tbody>
				<%
					it = squadraD.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry coppia = (Map.Entry) it.next();
						VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();
						if (coppia.getValue().equals("Auto Scala")) {
				%>

				<tr>
					<td class="text-center"><img
						src="Grado/<%=membro.getMansione().equals("Capo Squadra") && membro.getGrado().equals("Esperto")?"EspertoCapoSquadra":membro.getGrado() %>.png"
						title="<%=membro.getGrado() %>"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center"><%=membro.getMansione()%></td>
					<td class="text-center"><strong><%=membro.getNome()%></strong></td>
					<td class="text-center"><strong><%=membro.getCognome()%></strong></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiVF" id="aggiungiVF"
							onClick='apriFormVF("<%=membro.getEmail()%>","<%=membro.getMansione()%>","1","<%=giorno%>","<%=notte%>")'>Sostituisci</button></td>
					</td>
				</tr>
				<%
					}
					}
				%>

			</tbody>
		</table>
	</div>

	<p class="d-flex justify-content-center"></p>
	<div class="d-flex justify-content-center">
		<img src="Icon/idrante.png" class="fr">
		<h2>Auto Botte</h2>
	</div>
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Modifica</th>
				</tr>
			</thead>

			<tbody>
				<%
					it = squadraD.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry coppia = (Map.Entry) it.next();
						VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();
						if (coppia.getValue().equals("Auto Botte")) {
				%>

				<tr>
					<td class="text-center"><img
						src="Grado/<%=membro.getMansione().equals("Capo Squadra") && membro.getGrado().equals("Esperto")?"EspertoCapoSquadra":membro.getGrado() %>.png"
						title="<%=membro.getGrado() %>"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center"><%=membro.getMansione()%></td>
					<td class="text-center"><strong><%=membro.getNome()%></strong></td>
					<td class="text-center"><strong><%=membro.getCognome()%></strong></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiVF" id="aggiungiVF" id="agg"
							onClick='apriFormVF("<%=membro.getEmail()%>","<%=membro.getMansione()%>","1","<%=giorno%>","<%=notte%>")'>Sostituisci</button></td>
					</td>
				</tr>
				<%
					}
					}
				%>

			</tbody>
		</table>
	</div>

	<br>
	<div class="d-flex justify-content-center">
		<form action="GeneraSquadreServlet?salva=true" method=post>
			<a href="#Giorno" class="btn btn-outline-secondary btn-lg"
				style="margin: 3px;">Squadra Diurna</a> <a href="#Disp"
				class="btn btn-outline-secondary btn-lg" style="margin: 3px;">Personale
				Disponibile</a>
	</div>
	<br>

	<!-- SQUADRA NOTTURNA -->
	<div class="d-flex justify-content-center">
		<h2 id="Notte" style="font-weight: bold; font-size: 36px;">
			Squadra Notturna del
			<%=notte.toLocalDate().format(DateTimeFormatter.ofPattern("dd MMMM YYYY", new Locale("it", "IT"))) %></h2>
	</div>
	<p class="d-flex justify-content-center"></p>
	<div class="d-flex justify-content-center">
		<img src="Icon/caserma.png" class="fr">
		<h2>Sala Operativa</h2>
	</div>

	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Modifica</th>
				</tr>
			</thead>

			<tbody>
				<%
					in = squadraN.entrySet().iterator();
					while (in.hasNext()) {
						Map.Entry coppia = (Map.Entry) in.next();
						VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();
						if (coppia.getValue().equals("Sala Operativa")) {
				%>

				<tr>
					<td class="text-center"><img
						src="Grado/<%=membro.getMansione().equals("Capo Squadra") && membro.getGrado().equals("Esperto")?"EspertoCapoSquadra":membro.getGrado() %>.png"
						title="<%=membro.getGrado() %>"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center"><%=membro.getMansione()%></td>
					<td class="text-center"><strong><%=membro.getNome()%></strong></td>
					<td class="text-center"><strong><%=membro.getCognome()%></strong></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiVF" id="aggiungiVF"
							onClick='apriFormVF("<%=membro.getEmail()%>","<%=membro.getMansione()%>","2","<%=notte%>","<%=giorno%>")'>Sostituisci</button></td>
					</td>
				</tr>
				<%
					}
					}
				%>

			</tbody>
		</table>
	</div>

	<p class="d-flex justify-content-center"></p>
	<div class="d-flex justify-content-center">
		<img src="Icon/sirena.png" class="fr">
		<h2>Prima Partenza</h2>
	</div>
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Modifica</th>
				</tr>
			</thead>

			<tbody>
				<%
					in = squadraN.entrySet().iterator();
					while (in.hasNext()) {
						Map.Entry coppia = (Map.Entry) in.next();
						VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();
						if (coppia.getValue().equals("Prima Partenza")) {
				%>

				<tr>
					<td class="text-center"><img
						src="Grado/<%=membro.getMansione().equals("Capo Squadra") && membro.getGrado().equals("Esperto")?"EspertoCapoSquadra":membro.getGrado() %>.png"
						title="<%=membro.getGrado() %>"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center"><%=membro.getMansione()%></td>
					<td class="text-center"><strong><%=membro.getNome()%></strong></td>
					<td class="text-center"><strong><%=membro.getCognome()%></strong></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiVF" id="aggiungiVF"
							onClick='apriFormVF("<%=membro.getEmail()%>","<%=membro.getMansione()%>","2","<%=notte%>","<%=giorno%>")'>Sostituisci</button></td>
					</td>
				</tr>
				<%
					}
					}
				%>

			</tbody>
		</table>
	</div>

	<p class="d-flex justify-content-center"></p>
	<div class="d-flex justify-content-center">
		<img src="Icon/autoscala.png" class="fr">
		<h2>Auto Scala</h2>
	</div>
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Modifica</th>
				</tr>
			</thead>

			<tbody>
				<%
					in = squadraN.entrySet().iterator();
					while (in.hasNext()) {
						Map.Entry coppia = (Map.Entry) in.next();
						VigileDelFuocoBean mb = (VigileDelFuocoBean) coppia.getKey();
						if (coppia.getValue().equals("Auto Scala")) {
				%>

				<tr>
					<td class="text-center"><img
						src="Grado/<%=mb.getMansione().equals("Capo Squadra") && mb.getGrado().equals("Esperto")?"EspertoCapoSquadra":mb.getGrado() %>.png"
						title="<%=mb.getGrado() %>"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center"><%=mb.getMansione()%></td>
					<td class="text-center"><strong><%=mb.getNome()%></strong></td>
					<td class="text-center"><strong><%=mb.getCognome()%></strong></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiVF" id="aggiungiVF"
							onClick='apriFormVF("<%=mb.getEmail()%>","<%=mb.getMansione()%>","2","<%=notte%>","<%=giorno%>")'>Sostituisci</button></td>
					</td>
				</tr>
				<%
					}
					}
				%>

			</tbody>
		</table>
	</div>

	<p class="d-flex justify-content-center"></p>
	<div class="d-flex justify-content-center">
		<img src="Icon/idrante.png" class="fr">
		<h2>Auto Botte</h2>
	</div>
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Modifica</th>
				</tr>
			</thead>

			<tbody>
				<%
					in = squadraN.entrySet().iterator();
					while (in.hasNext()) {
						Map.Entry coppia = (Map.Entry) in.next();
						VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();
						if (coppia.getValue().equals("Auto Botte")) {
				%>

				<tr>
					<td class="text-center"><img
						src="Grado/<%=membro.getMansione().equals("Capo Squadra") && membro.getGrado().equals("Esperto")?"EspertoCapoSquadra":membro.getGrado() %>.png"
						title="<%=membro.getGrado() %>"
						onerror="this.parentElement.innerHTML='Non disponibile';"></td>
					<td class="text-center"><%=membro.getMansione()%></td>
					<td class="text-center"><strong><%=membro.getNome()%></strong></td>
					<td class="text-center"><strong><%=membro.getCognome()%></strong></td>
					<td class="text-center"><button type="button"
							class="btn btn-outline-secondary" data-toggle="modal"
							data-target="#aggiungiVF" id="aggiungiVF"
							onClick='apriFormVF("<%=membro.getEmail()%>","<%=membro.getMansione()%>","2","<%=notte%>","<%=giorno%>")'>Sostituisci</button></td>
					</td>
				</tr>
				<%
					}
					}
				%>

			</tbody>
		</table>
	</div>
	<div class="d-flex justify-content-center">
		<form action="GeneraSquadreServlet?salva=true" method=post>
			<a href="#Giorno" class="btn btn-outline-secondary btn-lg"
				style="margin: 3px;">Squadra Diurna</a> <a href="#Notte"
				class="btn btn-outline-secondary btn-lg" style="margin: 3px;">Squadra
				Notturna</a>
	</div>
	<br>
	<div class="d-flex justify-content-center" id="Disp">
		<h2>Personale Disponibile</h2>
	</div>
	<div id="personale"></div>


	<script src="JS/datePicker.js"></script>
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
	<script src="https://buttons.github.io/buttons.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>

	<script>
	$(document).ready(function(){
		
		var day = $("#day").val();
		var night =  $("#night").val();
		console.log("giorno "+day+" notte "+night);
		caricoPersonale(day,night);
		<% if (request.getParameter("squadraSalvata")!=null){%>
		$('#buttonModalAvviso').trigger('click');
		$("#titoloModalAvviso").text("Squadra salvata con successo");
		<%}%>
	});
	
	function caricoPersonale(giorno, notte) {
		//Chiamata ajax alla servlet PersonaleDisponibileAJAX
		$.ajax({
			type : "POST",//Chiamata POST
			url : "/ScheduFIRE/PersonaleServlet",//url della servlet che devo chiamare
			data : {
				"JSON" : true,
				"aggiunta" : true,
				"dataDiurno" : giorno,
				"dataNotturno" : notte
			},
			success : function(response) {//Operazione da eseguire una volta terminata la chiamata alla servlet.
					$(response).appendTo("#personale");

			}
		});
	}
		function apriFormVF(input, rule, sq, dt,ot) {
			//Chiamata ajax alla servlet PersonaleDisponibileAJAX
			$.ajax({
				type : "POST",//Chiamata POST
				url : "/ScheduFIRE/PersonaleDisponibileAJAX",//url della servlet che devo chiamare
				data : {
					"JSON" : true,
					"aggiunta" : true,
					"email" : input,
					"mansione" : rule,
					"tiposquadra" : sq,
					"dataModifica" : dt,
					"altroturno" : ot
				},
				success : function(response) {//Operazione da eseguire una volta terminata la chiamata alla servlet.
					$("#agg").prop("disabled",true);
					$("#appendElenco").remove();
					$("<div id='appendElenco'></div>").appendTo("#elenco");
					$(response).appendTo("#appendElenco");
				}
			});
		}
		
		
		
		function attivapulsante(){
			$("#agg").prop("disabled",false);
		}
		

	</script>


</body>
</html>