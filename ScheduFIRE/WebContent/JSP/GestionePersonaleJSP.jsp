<%@ page language="java" pageEncoding="ISO-8859-1"
	import="java.util.*, model.bean.*"%>
<%

Collection<VigileDelFuocoBean> capiSquadra = null;
Collection<VigileDelFuocoBean> autisti = null;
Collection<VigileDelFuocoBean> vigili = null;
Object attributoCapiSquadra = request.getAttribute("capiSquadra");
if(attributoCapiSquadra instanceof Collection) 
	capiSquadra = (Collection<VigileDelFuocoBean>) attributoCapiSquadra;
Object attributoAutisti = request.getAttribute("autisti");
if(attributoAutisti instanceof Collection) 
	autisti = (Collection<VigileDelFuocoBean>) attributoAutisti;
Object attributoVigili = request.getAttribute("vigili");
if(attributoVigili instanceof Collection) 
	  vigili = (Collection<VigileDelFuocoBean>) attributoVigili;

Object ordinamentoObj = session.getAttribute("ordinamento");
String ordinamento = null;
if(ordinamentoObj.getClass().getSimpleName().equals("String"))
	ordinamento = (String) ordinamentoObj;


String risultato = (String) session.getAttribute("risultato");

session.removeAttribute("risultato");

%>

<!DOCTYPE html>

<html>

<%@ include file="StandardJSP.jsp"%>

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

h4 {
	color: #B60000;
}
</style>

<body>
	<div id="sali"></div>
	<jsp:include page="HeaderJSP.jsp" />

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

	<script type="text/javascript">
	
	
		var nome = [];
		var cognome = [];
		var email = [];
		var mansione1Check = [];
		var mansione2Check = [];
		var mansione3Check = [];
		var grado0Check = [];
		var grado1Check = [];
		var grado2Check = [];
		var grado3Check = [];
		var ferieAnnoCorrenti = [];
		var ferieAnniPrecedenti = [];
		
		function mostraFormModifica(id) {

			$("#modifica" + id).show("slow", salvaStato(id) );
			
		}
		
		function salvaStato(id) {
			
			/*
			gradoCapoSquadra = document.getElementById("modificaVF" + id + "GradoCapoSquadra");
			gradi = document.getElementById("modificaVF" + id + "Gradi");
			*/
			
			nome[id] = document.getElementById("modificaVF" + id + "Nome").value;
			cognome[id] = document.getElementById("modificaVF" + id + "Cognome").value;
			email[id] = document.getElementById("modificaVF" + id + "Email").value;
			mansione1Check[id] = document.getElementById("modificaVF" + id + "Mansione1").checked;
			mansione2Check[id] = document.getElementById("modificaVF" + id + "Mansione2").checked;
			mansione3Check[id] = document.getElementById("modificaVF" + id + "Mansione3").checked;
			grado0Check[id] = document.getElementById("modificaVF" + id + "Grado0").checked;
			grado1Check[id] = document.getElementById("modificaVF" + id + "Grado1").checked;
			grado2Check[id] = document.getElementById("modificaVF" + id + "Grado2").checked;
			grado3Check[id] = document.getElementById("modificaVF" + id + "Grado3").checked;
			ferieAnnoCorrenti[id] = document.getElementById("modificaVF" + id + "GiorniFerieAnnoCorrente").value;
			ferieAnniPrecedenti[id] = document.getElementById("modificaVF" + id + "GiorniFerieAnnoPrecedente").value;
			
		}
		
		function nascondiFormModifica(id) {
			
			$("#modific" + id).hide("slow", immettiStato(id) );
			
		}
		
		function immettiStato(id) {
			
			var newId = id.split("a");
			
			document.getElementById("modificaVF" + newId[1] + "Nome").value = nome[newId[1]];
			document.getElementById("modificaVF" + newId[1] + "Cognome").value = cognome[newId[1]];
			document.getElementById("modificaVF" + newId[1] + "Email").value = email[newId[1]];
			document.getElementById("modificaVF" + newId[1] + "Mansione1").checked = mansione1Check[newId[1]];
			document.getElementById("modificaVF" + newId[1] + "Mansione2").checked = mansione2Check[newId[1]];
			document.getElementById("modificaVF" + newId[1] + "Mansione3").checked = mansione3Check[newId[1]];
			document.getElementById("modificaVF" + newId[1] + "Grado0").checked = grado0Check[newId[1]];
			document.getElementById("modificaVF" + newId[1] + "Grado1").checked = grado1Check[newId[1]];
			document.getElementById("modificaVF" + newId[1] + "Grado2").checked = grado2Check[newId[1]];
			document.getElementById("modificaVF" + newId[1] + "Grado3").checked = grado3Check[newId[1]];
			document.getElementById("modificaVF" + newId[1] + "GiorniFerieAnnoCorrente").value = ferieAnnoCorrenti[newId[1]];
			document.getElementById("modificaVF" + newId[1] + "GiorniFerieAnnoPrecedente").value = ferieAnniPrecedenti[newId[1]];

			if(mansione1Check) {

				$("#modificaVF" + newId[1] + "Gradi").hide("slow");
				
				$("#modificaVF" + newId[1] + "GradoCapoSquadra").show("slow");
				
			} else {
				
				$("#modificaVF" + newId[1] + "GradoCapoSquadra").hide("slow");
				
				$("#modificaVF" + newId[1] + "Gradi").show("slow");
				
			}
			
		}
		
		function mostraFormAggiuta() {

			$("#divPopup").show();
			
		}
		
		function chiudiFormAggiunta() {
			
			$("#divPopup").hide();
			
		}
		
		function mostraFormCancellazione(id) {
			
			var newID = id * (-1);
			$("#divPopupFormEliminazioneVF" + newID).show();
			
		}
		
		function chiudiFormCancellazione(parent) {
			
			$("#" + parent.parentNode.id).hide();
			
		}
		
		function sceltaMansione(element) {
		
			form = element.parentNode.parentNode;
			
			idGradiCapoSquadra = form.id + "GradoCapoSquadra";
			
			idGradi = form.id + "Gradi";
			
		
			if(element.value === "Capo Squadra") {
				
				$("#" + idGradiCapoSquadra).show("slow");
				
				$("#" + idGradi).hide("slow");
				
				$("#" + form.id + "Grado1").prop("checked", false);
				$("#" + form.id + "Grado2").prop("checked", false);
				$("#" + form.id + "Grado3").prop("checked", false);
				
			} else {
				
				$("#" + idGradiCapoSquadra).hide("slow");
				
				$("#" + idGradi).show("slow");
				
				$("#" + form.id + "Grado0").prop("checked", false);
				
			}
			
		}
		
		//Validazione form
		
		/*
		Questa funzione viene invocata ad ogni submit di ogni form di modifica
		presente nella pagina ed al submit del form di aggiunta di un VF.
		Come parametro alla funzione viene passato l'id del form.
		L'id di ogni elemento del form è formato dall'id del form concatenato
		il nome dell'elemento. 
		(Es. id form = "form1", di conseguenza l'id dell'elemento che deve
		contenere il nome sara "form1Nome")
		
		L'ottenimento degli elementi funziona, ma nel momento in cui faccio
		match sulla regex, la funziona si arresta.
		*/
		
		function validazioneForm(id) {

			var nome = document.getElementById(id + "Nome");
			var cognome = document.getElementById(id + "Cognome");
			var email = document.getElementById(id + "Email");
			var mansione1 = document.getElementById(id + "Mansione1");
			var mansione2 = document.getElementById(id + "Mansione2");
			var mansione3 = document.getElementById(id + "Mansione3");
			var grado0 = document.getElementById(id + "Grado0");
			var grado1 = document.getElementById(id + "Grado1");
			var grado2 = document.getElementById(id + "Grado2");
			var grado3 = document.getElementById(id + "Grado3");
			
			if( !nome.value.test("^[A-Z]{1}[a-z]{0,19}+$") ) {
				
				nome.focus();
				alert("Nome errato!");
				return false;
				
			}
			else if( !cognome.value.test("^[A-Z]{1}[a-z]{0,19}+$") ) {

				cognome.focus();
				alert("Cognome errato!");
				return false;
				
			}
			else if( !email.value.test("[A-Za-z]+([1-9][0-9]*)?\.[A-Za-z]+") ) {

				email.focus();
				alert("Email errata!");
				return false;
				
			}
			else if( !mansione1.value.checked  || !mansione2.value.checked  
					|| !mansione3.value.checked ) {

				alert("Mansione errata!");
				return false;
				
			}
			else if( ( mansione2.value.checked ||  mansione3.value.checked ) &&
					( !grado1.value.checked || !grado2.value.checked ||
						!grado3.value.checked ) ) {

				alert("Grado errato!");
				return false;
				
			}
			
			return true;
			
		}
		
		$(document).ready(function(){
			<% if ( risultato != null ){%>
				$('#buttonModalAvviso').trigger('click');
			<%}%>
		});
		
	</script>

	<section>

		<br> <a href="#sali" class=" back-up"><img
			src="IMG/arrow/up-arrow-p.png" style="margin-left: 5px;"
			onmouseover="this.src='IMG/arrow/up-arrow-d.png'"
			onmouseout="this.src='IMG/arrow/up-arrow-p.png'" /></a>

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

		<br>

		<h2 id="titolo" class="d-flex justify-content-center"
			style="color: #B60000 !Important; margin-buttom: 3%; font-size: 45px;">Gestione
			Personale</h2>

		<form id="ordinamento" action="./GestionePersonaleServlet">
			<div id="divOrdinamento">
				Ordina per: <select id="selectOrdinamento" name="ordinamento"
					class="custom-select" onchange="this.form.submit()"
					style="width: 58%">

					<%
					if( ordinamento != null ) {
						if( ordinamento.equals("nome") ) {
					%>
					<option value="nome" selected>Nome</option>
					<option value="cognome">Cognome</option>
					<option value="caricoLavoro">Carico di lavoro</option>
					<option value="ferie">Ferie</option>
					<%
						} else if( ordinamento.equals("cognome") ) {		
						%>
					<option value="nome">Nome</option>
					<option value="cognome" selected>Cognome</option>
					<option value="caricoLavoro">Carico di lavoro</option>
					<option value="ferie">Ferie</option>
					<%
						} else if( ordinamento.equals("caricoLavoro") ) {		
						%>
					<option value="nome">Nome</option>
					<option value="cognome">Cognome</option>
					<option value="caricoLavoro" selected>Carico di lavoro</option>
					<option value="ferie">Ferie</option>
					<%
						} else if( ordinamento.equals("ferie") ) {		
						%>
					<option value="nome">Nome</option>
					<option value="cognome">Cognome</option>
					<option value="caricoLavoro">Carico di lavoro</option>
					<option value="ferie" selected>Ferie</option>
					<% 	
						} else {		
						%>
					<option value="nome">Nome</option>
					<option value="cognome">Cognome</option>
					<option value="caricoLavoro">Carico di lavoro</option>
					<option value="ferie">Ferie</option>
					<%
						}
					}
					%>

				</select>
			</div>
		</form>

		<button id="buttonAggiungi" type="button"
			class="btn btn-outline-secondary" data-toggle="modal"
			onclick="mostraFormAggiuta()">Aggiungi Vigile del Fuoco</button>

		<br> <br>

		<div id="capo"></div>
		<div class="d-flex justify-content-center">
			<a href="#auto" class="btn btn-outline-secondary"
				style="margin: 3px;">Autisti</a> <a href="#vigile"
				class="btn btn-outline-secondary" style="margin: 3px;">Vigili</a>
		</div>
		<h4 class="d-flex justify-content-center" id="inizio"
			style="margin-top: 1%; color: #B60000 !Important">Capi Squadra</h4>

		<% 
				
			int id = 1;
			if(capiSquadra != null) {
				
		%>

		<div class="table-responsive">
			<table class="table  table-hover" id="listaVigili"
				style="table-layout: fixed; width: 100%;">
				<thead class="thead-dark">
					<tr>
						<th class="text-center" style="width: 10%">Grado</th>
						<th class="text-center">Nome</th>
						<th class="text-center">Cognome</th>

						<th class="text-center">Email</th>
						<th>
						<th class="text-center">Carico lavorativo</th>
						<th class="text-center">Ferie
						<th>
						<th class="text-center">Modifica</th>
						<th class="text-center">Elimina</th>
					</tr>
				</thead>

				<%		
			
					for(VigileDelFuocoBean vf: capiSquadra) {
					
				%>

				<tbody>

					<tr id=<%= "vigile" + id %> class="vigile">

						<td class="text-center"><img
							src="Grado/<%=vf.getMansione().equals("Capo Squadra") && 
						vf.getGrado().equals("Esperto")?"EspertoCapoSquadra":vf.getGrado() %>.png"
							onerror="this.parentElement.innerHTML='Non disponibile';"
							title=<%= vf.getGrado() %>></td>

						<td class="text-center" style="font-weight: bold;"><%= vf.getNome() %></td>

						<td class="text-center" style="font-weight: bold;"><%= vf.getCognome() %></td>

						<td class="text-center"><%= vf.getEmail() %></td>

						<td>
						<td class="text-center"><%= vf.getCaricoLavoro() %></td>

						<td class="text-center"><%= vf.getGiorniFerieAnnoCorrente() + 
													vf.getGiorniFerieAnnoPrecedente() %></td>

						<td>
						<td>

							<button id=<%= id %> type="button"
								class="btn btn-outline-secondary" data-toggle="modal"
								onclick="mostraFormModifica(this.id)">
								<%= "Modifica" %>
							</button>

						</td>

						<td>

							<div id=<%= "divPopupFormEliminazioneVF" + id %>
								class="divPopupFormEliminazioneVF">

								<br> <br> <br> <br> <br>

								<form id=<%= "formEliminazioneVF" + id %> class="form-popup"
									action="./EliminaVFServlet">

									<br> <label id="labelCancellazione"> Sei sicuro di
										cancellare <%= vf.getCognome() %> <%= vf.getNome() %> ?
									</label> <br> <br> <input type="hidden" name="nome"
										value=<%= vf.getNome() %>> <input type="hidden"
										name="cognome" value=<%= vf.getCognome() %>> <input
										type="hidden" name="email"
										value=<%= vf.getEmail().
									substring( 0 , vf.getEmail().indexOf("@") ) %>>

									<input type="hidden" name="turno" value=<%= vf.getTurno() %>>

									<input type="hidden" name="mansione"
										value=<%= vf.getMansione() %>> <input type="hidden"
										name="giorniFerieAnnoCorrente"
										value=<%= vf.getGiorniFerieAnnoCorrente() %>> <input
										type="hidden" name="giorniFerieAnnoPrecedente"
										value=<%= vf.getGiorniFerieAnnoPrecedente() %>> <input
										type="hidden" name="caricoLavoro"
										value=<%= vf.getCaricoLavoro() %>> <input
										type="hidden" name="adoperabile"
										value=<%= vf.isAdoperabile() %>> <input type="hidden"
										name="grado" value=<%= vf.getGrado() %>> <input
										type="hidden" name="username" value=<%= vf.getUsername() %>>

									<input id="buttonCancella" type="submit"
										class="btn btn-outline-success" data-toggle="modal"
										class="button" value="Conferma"> &ensp; <input
										type="button" id="buttonCancellaNo"
										class="btn btn-outline-danger" data-toggle="modal"
										value="Annulla"
										onclick="chiudiFormCancellazione(this.parentNode)"> <br>
									<br>

								</form>

							</div>

							<button id=<%= id * (-1) %> class="btn btn-outline-danger"
								onclick="mostraFormCancellazione(this.id)">Elimina</button>

						</td>

					</tr>

					<tr id=<%= "modifica" + id %> class="modifica">

						<td class="riga" colspan="10">

							<form id=<%= "modificaVF" + id %> action="./ModificaVFServlet"
								class="modificaVF" onsubmit="return validazioneForm(this.id)">
								<br> <label> Nome: <input
									id=<%= "modificaVF" + id + "Nome" %> type="text"
									name="nomeNuovo" value="<%= vf.getNome() %>" required>
								</label> &ensp; <label> Cognome: <input
									id=<%= "modificaVF" + id + "Cognome" %> type="text"
									name="cognomeNuovo" value="<%= vf.getCognome() %>" required>
								</label> <br> <br> <label> Email: <input
									id=<%= "modificaVF" + id + "Email" %> type="text"
									name="emailNuova"
									value=<%= vf.getEmail().substring( 0 , vf.getEmail().indexOf("@") ) %>
									required>@vigilfuoco.it
								</label> <br> <br>

								<% if( vf.getMansione().equals("Capo Squadra") ) { %>

								<div class="mansione">

									Mansione: <br> <input
										id=<%= "modificaVF" + id + "Mansione1" %> type="radio"
										name="mansioneNuova" value="Capo Squadra"
										onclick="sceltaMansione(this)" checked> Capo Squadra
									<br> <input id=<%= "modificaVF" + id + "Mansione2" %>
										type="radio" name="mansioneNuova" value="Autista"
										onclick="sceltaMansione(this)"> Autista <br> <input
										id=<%= "modificaVF" + id + "Mansione3" %> type="radio"
										name="mansioneNuova" value="Vigile"
										onclick="sceltaMansione(this)"> Vigile <br>

								</div>
								<br>

								<% } else if ( vf.getMansione().equals("Autista") ) { %>

								<div class="mansione">

									Mansione: <br> <input
										id=<%= "modificaVF" + id + "Mansione1" %> type="radio"
										name="mansioneNuova" value="Capo Squadra"
										onclick="sceltaMansione(this)"> Capo Squadra <br>
									<input id=<%= "modificaVF" + id + "Mansione2" %> type="radio"
										name="mansioneNuova" value="Autista"
										onclick="sceltaMansione(this)" checked> Autista <br>
									<input id=<%= "modificaVF" + id + "Mansione3" %> type="radio"
										name="mansioneNuova" value="Vigile"
										onclick="sceltaMansione(this)"> Vigile <br>

								</div>
								<br>

								<% } else if ( vf.getMansione().equals("Vigile") ) { %>

								<div class="mansione">

									Mansione: <br> <input
										id=<%= "modificaVF" + id + "Mansione1" %> type="radio"
										name="mansioneNuova" value="Capo Squadra"
										onclick="sceltaMansione(this)"> Capo Squadra <br>
									<input id=<%= "modificaVF" + id + "Mansione2" %> type="radio"
										name="mansioneNuova" value="Autista"
										onclick="sceltaMansione(this)"> Autista <br> <input
										id=<%= "modificaVF" + id + "Mansione3" %> type="radio"
										name="mansioneNuova" value="Vigile"
										onclick="sceltaMansione(this)" checked> Vigile <br>

								</div>
								<br>

								<% } else { %>

								<div class="mansione">

									Mansione: <br> <input
										id=<%= "modificaVF" + id + "Mansione1" %> type="radio"
										name="mansioneNuova" value="Capo Squadra"
										onclick="sceltaMansione(this)"> Capo Squadra <br>
									<input id=<%= "modificaVF" + id + "Mansione2" %> type="radio"
										name="mansioneNuova" value="Autista"
										onclick="sceltaMansione(this)"> Autista <br> <input
										id=<%= "modificaVF" + id + "Mansione3" %> type="radio"
										name="mansioneNuova" value="Vigile"
										onclick="sceltaMansione(this)"> Vigile <br>

								</div>
								<br>

								<% } %>

								<% if( vf.getGrado().equals("Semplice") && vf.getMansione().equals("Capo Squadra") ) { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto">
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: hidden">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto">
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato">
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore"> Coordinatore <br>

								</div>

								<% } else if( vf.getGrado().equals("Esperto") && vf.getMansione().equals("Capo Squadra") ) { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto" checked>
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: hidden">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto">
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato">
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore"> Coordinatore <br>

								</div>

								<% } else if( vf.getGrado().equals("Qualificato") && ( vf.getMansione().equals("Autista")
										|| vf.getMansione().equals("Vigile") ) ) { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: hidden">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto">
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto">
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato" checked>
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore"> Coordinatore <br>

								</div>


								<% } else if( vf.getGrado().equals("Coordinatore") && ( vf.getMansione().equals("Autista")
										|| vf.getMansione().equals("Vigile") ) ) { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: hidden">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto">
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto">
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato">
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore" checked>
									Coordinatore <br>

								</div>

								<% } else if( vf.getGrado().equals("Esperto") && ( vf.getMansione().equals("Autista")
										|| vf.getMansione().equals("Vigile") ) ) { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: hidden">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto">
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto" checked>
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato">
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore"> Coordinatore
									<br>

								</div>

								<% } else { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto">
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto">
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato">
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore"> Coordinatore
									<br>

								</div>

								<% } %>

								<br> <label> Giorni di ferie dell'anno corrente: <input
									id=<%= "modificaVF" + id + "GiorniFerieAnnoCorrente" %>
									type="number" name="giorniFerieAnnoCorrenteNuovi"
									value=<%= vf.getGiorniFerieAnnoCorrente() %> min="0" max="22"
									required>
								</label> &nbsp; <label> Giorni di ferie degli anni precedenti: <input
									id=<%= "modificaVF" + id + "GiorniFerieAnnoPrecedente" %>
									type="number" name="giorniFerieAnnoPrecedenteNuovi"
									value=<%= vf.getGiorniFerieAnnoPrecedente() %> min="0"
									max="999" required>
								</label> <br> <br> <input type="hidden" name="emailVecchia"
									value=<%= vf.getEmail().substring( 0 , vf.getEmail().indexOf("@") ) %>>

								<input type="submit" class="btn btn-outline-success"
									data-toggle="modal" class="button" value="Conferma">
								&nbsp; <input id=<%= "a" + id %> type="button"
									class="btn btn-outline-danger"
									onclick="nascondiFormModifica(this.id)" data-toggle="modal"
									class="button" value="Annulla"> <br> <br>

							</form>

						</td>

					</tr>

				</tbody>

				<%
						id++;
				
					}
				
				%>

			</table>
		</div>
		<%	
			
		} else {
		
		%>

		<h5>Nessun Capo Squadra presente!</h5>

		<%
		
		}
		
		%>

		<div id="auto"></div>
		<div class="d-flex justify-content-center">
			<a href="#capo" class="btn btn-outline-secondary"
				style="margin: 3px;">Capi Squadra</a> <a href="#vigile"
				class="btn btn-outline-secondary" style="margin: 3px;">Vigili</a>
		</div>
		<h4 class="d-flex justify-content-center" id="inizio"
			style="margin-top: 1%; color: #B60000 !Important">Autisti</h4>


		<% 

			if(autisti != null) {
				
		%>
		<div class="table-responsive">
			<table class="table  table-hover" id="listaVigili"
				style="table-layout: fixed; width: 100%;">
				<thead class="thead-dark">
					<tr>
						<th class="text-center" style="width: 10%">Grado</th>
						<th class="text-center">Nome</th>
						<th class="text-center">Cognome</th>
						<th class="text-center">Email</th>
						<th>
						<th class="text-center">Carico lavorativo</th>
						<th class="text-center">Ferie
						<th>
						<th class="text-center">Modifica</th>
						<th class="text-center">Elimina</th>
					</tr>
				</thead>

				<%		
			
					for(VigileDelFuocoBean vf: autisti) {
					
				%>

				<tbody>

					<tr id=<%= "vigile" + id %> class="vigile">

						<td class="text-center"><img
							src="Grado/<%=vf.getMansione().equals("Capo Squadra") && 
						vf.getGrado().equals("Esperto")?"EspertoCapoSquadra":vf.getGrado() %>.png"
							onerror="this.parentElement.innerHTML='Non disponibile';"
							title=<%= vf.getGrado() %>></td>

						<td class="text-center" style="font-weight: bold;"><%= vf.getNome() %></td>

						<td class="text-center" style="font-weight: bold;"><%= vf.getCognome() %></td>

						<td class="text-center"><%= vf.getEmail() %></td>

						<td>
						<td class="text-center"><%= vf.getCaricoLavoro() %></td>

						<td class="text-center"><%= vf.getGiorniFerieAnnoCorrente() + 
													vf.getGiorniFerieAnnoPrecedente() %></td>

						<td>
						<td>

							<button id=<%= id %> type="button"
								class="btn btn-outline-secondary" data-toggle="modal"
								onclick="mostraFormModifica(this.id)">
								<%= "Modifica" %>
							</button>

						</td>

						<td>

							<div id=<%= "divPopupFormEliminazioneVF" + id %>
								class="divPopupFormEliminazioneVF">

								<br> <br> <br> <br> <br>

								<form id=<%= "formEliminazioneVF" + id %> class="form-popup"
									action="./EliminaVFServlet">

									<br> <label id="labelCancellazione"> Sei sicuro di
										cancellare <%= vf.getCognome() %> <%= vf.getNome() %> ?
									</label> <br> <br> <input type="hidden" name="nome"
										value=<%= vf.getNome() %>> <input type="hidden"
										name="cognome" value=<%= vf.getCognome() %>> <input
										type="hidden" name="email"
										value=<%= vf.getEmail().
									substring( 0 , vf.getEmail().indexOf("@") ) %>>

									<input type="hidden" name="turno" value=<%= vf.getTurno() %>>

									<input type="hidden" name="mansione"
										value=<%= vf.getMansione() %>> <input type="hidden"
										name="giorniFerieAnnoCorrente"
										value=<%= vf.getGiorniFerieAnnoCorrente() %>> <input
										type="hidden" name="giorniFerieAnnoPrecedente"
										value=<%= vf.getGiorniFerieAnnoPrecedente() %>> <input
										type="hidden" name="caricoLavoro"
										value=<%= vf.getCaricoLavoro() %>> <input
										type="hidden" name="adoperabile"
										value=<%= vf.isAdoperabile() %>> <input type="hidden"
										name="grado" value=<%= vf.getGrado() %>> <input
										type="hidden" name="username" value=<%= vf.getUsername() %>>

									<input id="buttonCancella" type="submit"
										class="btn btn-outline-success" data-toggle="modal"
										class="button" value="Conferma"> &ensp; <input
										type="button" id="buttonCancellaNo"
										class="btn btn-outline-danger" data-toggle="modal"
										value="Annulla"
										onclick="chiudiFormCancellazione(this.parentNode)"> <br>
									<br>

								</form>

							</div>

							<button id=<%= id * (-1) %> class="btn btn-outline-danger"
								onclick="mostraFormCancellazione(this.id)">Elimina</button>

						</td>

					</tr>

					<tr id=<%= "modifica" + id %> class="modifica">

						<td colspan="10">

							<form id=<%= "modificaVF" + id %> action="./ModificaVFServlet"
								class="modificaVF" onsubmit="return validazioneForm(this.id)">
								<br> <label> Nome: <input
									id=<%= "modificaVF" + id + "Nome" %> type="text"
									name="nomeNuovo" value="<%= vf.getNome() %>" required>
								</label> &ensp; <label> Cognome: <input
									id=<%= "modificaVF" + id + "Cognome" %> type="text"
									name="cognomeNuovo" value="<%= vf.getCognome() %>" required>
								</label> <br> <br> <label> Email: <input
									id=<%= "modificaVF" + id + "Email" %> type="text"
									name="emailNuova"
									value=<%= vf.getEmail().substring( 0 , vf.getEmail().indexOf("@") ) %>
									required>@vigilfuoco.it
								</label> <br> <br>

								<% if( vf.getMansione().equals("Capo Squadra") ) { %>

								<div class="mansione">

									Mansione: <br> <input
										id=<%= "modificaVF" + id + "Mansione1" %> type="radio"
										name="mansioneNuova" value="Capo Squadra"
										onclick="sceltaMansione(this)" checked> Capo Squadra
									<br> <input id=<%= "modificaVF" + id + "Mansione2" %>
										type="radio" name="mansioneNuova" value="Autista"
										onclick="sceltaMansione(this)"> Autista <br> <input
										id=<%= "modificaVF" + id + "Mansione3" %> type="radio"
										name="mansioneNuova" value="Vigile"
										onclick="sceltaMansione(this)"> Vigile <br>

								</div>
								<br>

								<% } else if ( vf.getMansione().equals("Autista") ) { %>

								<div class="mansione">

									Mansione: <br> <input
										id=<%= "modificaVF" + id + "Mansione1" %> type="radio"
										name="mansioneNuova" value="Capo Squadra"
										onclick="sceltaMansione(this)"> Capo Squadra <br>
									<input id=<%= "modificaVF" + id + "Mansione2" %> type="radio"
										name="mansioneNuova" value="Autista"
										onclick="sceltaMansione(this)" checked> Autista <br>
									<input id=<%= "modificaVF" + id + "Mansione3" %> type="radio"
										name="mansioneNuova" value="Vigile"
										onclick="sceltaMansione(this)"> Vigile <br>

								</div>
								<br>

								<% } else if ( vf.getMansione().equals("Vigile") ) { %>

								<div class="mansione">

									Mansione: <br> <input
										id=<%= "modificaVF" + id + "Mansione1" %> type="radio"
										name="mansioneNuova" value="Capo Squadra"
										onclick="sceltaMansione(this)"> Capo Squadra <br>
									<input id=<%= "modificaVF" + id + "Mansione2" %> type="radio"
										name="mansioneNuova" value="Autista"
										onclick="sceltaMansione(this)"> Autista <br> <input
										id=<%= "modificaVF" + id + "Mansione3" %> type="radio"
										name="mansioneNuova" value="Vigile"
										onclick="sceltaMansione(this)" checked> Vigile <br>

								</div>
								<br>

								<% } else { %>

								<div class="mansione">

									Mansione: <br> <input
										id=<%= "modificaVF" + id + "Mansione1" %> type="radio"
										name="mansioneNuova" value="Capo Squadra"
										onclick="sceltaMansione(this)"> Capo Squadra <br>
									<input id=<%= "modificaVF" + id + "Mansione2" %> type="radio"
										name="mansioneNuova" value="Autista"
										onclick="sceltaMansione(this)"> Autista <br> <input
										id=<%= "modificaVF" + id + "Mansione3" %> type="radio"
										name="mansioneNuova" value="Vigile"
										onclick="sceltaMansione(this)"> Vigile <br>

								</div>
								<br>

								<% } %>

								<% if( vf.getGrado().equals("Semplice") && vf.getMansione().equals("Capo Squadra") ) { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto">
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: hidden">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto">
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato">
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore"> Coordinatore <br>

								</div>

								<% } else if( vf.getGrado().equals("Esperto") && vf.getMansione().equals("Capo Squadra") ) { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto" checked>
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: hidden">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto">
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato">
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore"> Coordinatore <br>

								</div>

								<% } else if( vf.getGrado().equals("Qualificato") && ( vf.getMansione().equals("Autista")
										|| vf.getMansione().equals("Vigile") ) ) { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: hidden">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto">
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto">
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato" checked>
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore"> Coordinatore <br>

								</div>


								<% } else if( vf.getGrado().equals("Coordinatore") && ( vf.getMansione().equals("Autista")
										|| vf.getMansione().equals("Vigile") ) ) { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: hidden">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto">
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto">
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato">
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore" checked>
									Coordinatore <br>

								</div>

								<% } else if( vf.getGrado().equals("Esperto") && ( vf.getMansione().equals("Autista")
										|| vf.getMansione().equals("Vigile") ) ) { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: hidden">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto">
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto" checked>
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato">
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore"> Coordinatore
									<br>

								</div>

								<% } else { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto">
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto">
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato">
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore"> Coordinatore
									<br>

								</div>

								<% } %>

								<br> <label> Giorni di ferie dell'anno corrente: <input
									id=<%= "modificaVF" + id + "GiorniFerieAnnoCorrente" %>
									type="number" name="giorniFerieAnnoCorrenteNuovi"
									value=<%= vf.getGiorniFerieAnnoCorrente() %> min="0" max="22"
									required>
								</label> &nbsp; <label> Giorni di ferie degli anni precedenti: <input
									id=<%= "modificaVF" + id + "GiorniFerieAnnoPrecedente" %>
									type="number" name="giorniFerieAnnoPrecedenteNuovi"
									value=<%= vf.getGiorniFerieAnnoPrecedente() %> min="0"
									max="999" required>
								</label> <br> <br> <input type="hidden" name="emailVecchia"
									value=<%= vf.getEmail().substring( 0 , vf.getEmail().indexOf("@") ) %>>

								<input type="submit" class="btn btn-outline-success"
									data-toggle="modal" class="button" value="Conferma">
								&nbsp; <input id=<%= "a" + id %> type="button"
									class="btn btn-outline-danger"
									onclick="nascondiFormModifica(this.id)" data-toggle="modal"
									class="button" value="Annulla"> <br> <br>

							</form>

						</td>

					</tr>

				</tbody>

				<%
						id++;
				
					}
				
				%>

			</table>
		</div>
		<%	
			
		} else {
		
		%>

		<h5>Nessun Autista presente!</h5>

		<%
		
		}
		
		%>

		<div id="vigile"></div>
		<div class="d-flex justify-content-center">
			<a href="#capo" class="btn btn-outline-secondary"
				style="margin: 3px;">Capi Squadra</a> <a href="#auto"
				class="btn btn-outline-secondary" style="margin: 3px;">Autisti</a>
		</div>
		<h4 class="d-flex justify-content-center" id="inizio"
			style="margin-top: 1%; color: #B60000 !Important">Vigili</h4>


		<% 

			if(vigili != null) {
				
		%>
		<div class="table-responsive">
			<table class="table  table-hover" id="listaVigili"
				style="table-layout: fixed; width: 100%;">
				<thead class="thead-dark">
					<tr>
						<th class="text-center" style="width: 10%">Grado</th>
						<th class="text-center">Nome</th>
						<th class="text-center">Cognome</th>
						<th class="text-center">Email</th>
						<th>
						<th class="text-center">Carico lavorativo</th>
						<th class="text-center">Ferie
						<th>
						<th class="text-center">Modifica</th>
						<th class="text-center">Elimina</th>
					</tr>
				</thead>

				<%		
			
					for(VigileDelFuocoBean vf: vigili) {
					
				%>

				<tbody>

					<tr id=<%= "vigile" + id %> class="vigile">

						<td class="text-center"><img
							src="Grado/<%=vf.getMansione().equals("Capo Squadra") && 
						vf.getGrado().equals("Esperto")?"EspertoCapoSquadra":vf.getGrado() %>.png"
							onerror="this.parentElement.innerHTML='Non disponibile';"
							title=<%= vf.getGrado() %>></td>

						<td class="text-center" style="font-weight: bold;"><%= vf.getNome() %></td>

						<td class="text-center" style="font-weight: bold;"><%= vf.getCognome() %></td>

						<td class="text-center"><%= vf.getEmail() %></td>

						<td>
						<td class="text-center"><%= vf.getCaricoLavoro() %></td>

						<td class="text-center"><%= vf.getGiorniFerieAnnoCorrente() + 
													vf.getGiorniFerieAnnoPrecedente() %></td>

						<td>
						<td>

							<button id=<%= id %> type="button"
								class="btn btn-outline-secondary" data-toggle="modal"
								onclick="mostraFormModifica(this.id)">
								<%= "Modifica" %>
							</button>

						</td>

						<td>

							<div id=<%= "divPopupFormEliminazioneVF" + id %>
								class="divPopupFormEliminazioneVF">

								<br> <br> <br> <br> <br>

								<form id=<%= "formEliminazioneVF" + id %> class="form-popup"
									action="./EliminaVFServlet">

									<br> <label id="labelCancellazione"> Sei sicuro di
										cancellare <%= vf.getCognome() %> <%= vf.getNome() %> ?
									</label> <br> <br> <input type="hidden" name="nome"
										value=<%= vf.getNome() %>> <input type="hidden"
										name="cognome" value=<%= vf.getCognome() %>> <input
										type="hidden" name="email"
										value=<%= vf.getEmail().
									substring( 0 , vf.getEmail().indexOf("@") ) %>>

									<input type="hidden" name="turno" value=<%= vf.getTurno() %>>

									<input type="hidden" name="mansione"
										value=<%= vf.getMansione() %>> <input type="hidden"
										name="giorniFerieAnnoCorrente"
										value=<%= vf.getGiorniFerieAnnoCorrente() %>> <input
										type="hidden" name="giorniFerieAnnoPrecedente"
										value=<%= vf.getGiorniFerieAnnoPrecedente() %>> <input
										type="hidden" name="caricoLavoro"
										value=<%= vf.getCaricoLavoro() %>> <input
										type="hidden" name="adoperabile"
										value=<%= vf.isAdoperabile() %>> <input type="hidden"
										name="grado" value=<%= vf.getGrado() %>> <input
										type="hidden" name="username" value=<%= vf.getUsername() %>>

									<input id="buttonCancella" type="submit"
										class="btn btn-outline-success" data-toggle="modal"
										class="button" value="Conferma"> &ensp; <input
										type="button" id="buttonCancellaNo"
										class="btn btn-outline-danger" data-toggle="modal"
										value="Annulla"
										onclick="chiudiFormCancellazione(this.parentNode)"> <br>
									<br>

								</form>

							</div>

							<button id=<%= id * (-1) %> class="btn btn-outline-danger"
								onclick="mostraFormCancellazione(this.id)">Elimina</button>

						</td>

					</tr>

					<tr id=<%= "modifica" + id %> class="modifica">

						<td colspan="10">

							<form id=<%= "modificaVF" + id %> action="./ModificaVFServlet"
								class="modificaVF" onsubmit="return validazioneForm(this.id)">
								<br> <label> Nome: <input
									id=<%= "modificaVF" + id + "Nome" %> type="text"
									name="nomeNuovo" value="<%= vf.getNome() %>" required>
								</label> &ensp; <label> Cognome: <input
									id=<%= "modificaVF" + id + "Cognome" %> type="text"
									name="cognomeNuovo" value="<%= vf.getCognome() %>" required>
								</label> <br> <br> <label> Email: <input
									id=<%= "modificaVF" + id + "Email" %> type="text"
									name="emailNuova"
									value=<%= vf.getEmail().substring( 0 , vf.getEmail().indexOf("@") ) %>
									required>@vigilfuoco.it
								</label> <br> <br>

								<% if( vf.getMansione().equals("Capo Squadra") ) { %>

								<div class="mansione">

									Mansione: <br> <input
										id=<%= "modificaVF" + id + "Mansione1" %> type="radio"
										name="mansioneNuova" value="Capo Squadra"
										onclick="sceltaMansione(this)" checked> Capo Squadra
									<br> <input id=<%= "modificaVF" + id + "Mansione2" %>
										type="radio" name="mansioneNuova" value="Autista"
										onclick="sceltaMansione(this)"> Autista <br> <input
										id=<%= "modificaVF" + id + "Mansione3" %> type="radio"
										name="mansioneNuova" value="Vigile"
										onclick="sceltaMansione(this)"> Vigile <br>

								</div>
								<br>

								<% } else if ( vf.getMansione().equals("Autista") ) { %>

								<div class="mansione">

									Mansione: <br> <input
										id=<%= "modificaVF" + id + "Mansione1" %> type="radio"
										name="mansioneNuova" value="Capo Squadra"
										onclick="sceltaMansione(this)"> Capo Squadra <br>
									<input id=<%= "modificaVF" + id + "Mansione2" %> type="radio"
										name="mansioneNuova" value="Autista"
										onclick="sceltaMansione(this)" checked> Autista <br>
									<input id=<%= "modificaVF" + id + "Mansione3" %> type="radio"
										name="mansioneNuova" value="Vigile"
										onclick="sceltaMansione(this)"> Vigile <br>

								</div>
								<br>

								<% } else if ( vf.getMansione().equals("Vigile") ) { %>

								<div class="mansione">

									Mansione: <br> <input
										id=<%= "modificaVF" + id + "Mansione1" %> type="radio"
										name="mansioneNuova" value="Capo Squadra"
										onclick="sceltaMansione(this)"> Capo Squadra <br>
									<input id=<%= "modificaVF" + id + "Mansione2" %> type="radio"
										name="mansioneNuova" value="Autista"
										onclick="sceltaMansione(this)"> Autista <br> <input
										id=<%= "modificaVF" + id + "Mansione3" %> type="radio"
										name="mansioneNuova" value="Vigile"
										onclick="sceltaMansione(this)" checked> Vigile <br>

								</div>
								<br>

								<% } else { %>

								<div class="mansione">

									Mansione: <br> <input
										id=<%= "modificaVF" + id + "Mansione1" %> type="radio"
										name="mansioneNuova" value="Capo Squadra"
										onclick="sceltaMansione(this)"> Capo Squadra <br>
									<input id=<%= "modificaVF" + id + "Mansione2" %> type="radio"
										name="mansioneNuova" value="Autista"
										onclick="sceltaMansione(this)"> Autista <br> <input
										id=<%= "modificaVF" + id + "Mansione3" %> type="radio"
										name="mansioneNuova" value="Vigile"
										onclick="sceltaMansione(this)"> Vigile <br>

								</div>
								<br>

								<% } %>

								<% if( vf.getGrado().equals("Semplice") && vf.getMansione().equals("Capo Squadra") ) { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto">
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: hidden">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto">
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato">
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore"> Coordinatore <br>

								</div>

								<% } else if( vf.getGrado().equals("Esperto") && vf.getMansione().equals("Capo Squadra") ) { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto" checked>
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: hidden">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto">
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato">
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore"> Coordinatore <br>

								</div>

								<% } else if( vf.getGrado().equals("Qualificato") && ( vf.getMansione().equals("Autista")
										|| vf.getMansione().equals("Vigile") ) ) { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: hidden">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto">
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto">
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato" checked>
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore"> Coordinatore <br>

								</div>


								<% } else if( vf.getGrado().equals("Coordinatore") && ( vf.getMansione().equals("Autista")
										|| vf.getMansione().equals("Vigile") ) ) { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: hidden">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto">
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto">
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato">
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore" checked>
									Coordinatore <br>

								</div>

								<% } else if( vf.getGrado().equals("Esperto") && ( vf.getMansione().equals("Autista")
										|| vf.getMansione().equals("Vigile") ) ) { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: hidden">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto">
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto" checked>
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato">
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore"> Coordinatore
									<br>

								</div>

								<% } else { %>

								<div id=<%= "modificaVF" + id + "GradoCapoSquadra" %>
									class="grado" style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado0" %>
										type="checkbox" name="gradoNuovo" value="Esperto">
									Esperto <br>

								</div>

								<div id=<%= "modificaVF" + id + "Gradi" %> class="grado"
									style="display: block">

									Grado: <br> <input id=<%= "modificaVF" + id + "Grado1" %>
										type="radio" name="gradoNuovo" value="Esperto">
									Esperto <br> <input id=<%= "modificaVF" + id + "Grado2" %>
										type="radio" name="gradoNuovo" value="Qualificato">
									Qualificato <br> <input
										id=<%= "modificaVF" + id + "Grado3" %> type="radio"
										name="gradoNuovo" value="Coordinatore"> Coordinatore
									<br>

								</div>

								<% } %>

								<br> <label> Giorni di ferie dell'anno corrente: <input
									id=<%= "modificaVF" + id + "GiorniFerieAnnoCorrente" %>
									type="number" name="giorniFerieAnnoCorrenteNuovi"
									value=<%= vf.getGiorniFerieAnnoCorrente() %> min="0" max="22"
									required>
								</label> &nbsp; <label> Giorni di ferie degli anni precedenti: <input
									id=<%= "modificaVF" + id + "GiorniFerieAnnoPrecedente" %>
									type="number" name="giorniFerieAnnoPrecedenteNuovi"
									value=<%= vf.getGiorniFerieAnnoPrecedente() %> min="0"
									max="999" required>
								</label> <br> <br> <input type="hidden" name="emailVecchia"
									value=<%= vf.getEmail().substring( 0 , vf.getEmail().indexOf("@") ) %>>

								<input type="submit" class="btn btn-outline-success"
									data-toggle="modal" class="button" value="Conferma">
								&nbsp; <input id=<%= "a" + id %> type="button"
									class="btn btn-outline-danger"
									onclick="nascondiFormModifica(this.id)" data-toggle="modal"
									class="button" value="Annulla"> <br> <br>

							</form>

						</td>

					</tr>

				</tbody>

				<%
						id++;
				
					}
				
				%>

			</table>
		</div>
		<%	
			
		} else {
		
		%>

		<h5>Nessun Vigile presente!</h5>

		<%
		
		}
		
		%>



	</section>

	<div id="divPopup" class="popup">

		<form id="aggiungiVF" class="form-popup" action="./AggiungiVFServlet"
			onsubmit="return validazioneForm(this.id)">

			<h2>Aggiungi un vigile del fuoco</h2>

			<br> <label> Nome: <input id="aggiungiVFNome"
				type="text" name="nome" required> &ensp;
			</label> <label> Cognome: <input id="aggiungiVFCognome" type="text"
				name="cognome" required>
			</label> <br> <br> <label class="email"> Email: <input
				id="aggiungiVFEmail" type="text" name="email" required>@vigilfuoco.it
			</label> <br> <br>

			<div class="mansioneAggiungi">

				Mansione: <br> <input id="aggiungiVFMansione1" type="radio"
					name="mansione" value="Capo Squadra" onclick="sceltaMansione(this)">
				Capo Squadra <br> <input id="aggiungiVFMansione2" type="radio"
					name="mansione" value="Autista" onclick="sceltaMansione(this)">
				Autista <br> <input id="aggiungiVFMansione3" type="radio"
					name="mansione" value="Vigile" onclick="sceltaMansione(this)">
				Vigile <br>

			</div>
			<br>



			<div id="aggiungiVFGradoCapoSquadra" class="grado">

				Grado: <br> <input id="aggiungiVFGrado0" type="checkbox"
					name="grado" value="Esperto"> Esperto <br>

			</div>

			<div id="aggiungiVFGradi" class="grado">

				Grado: <br> <input id="aggiungiVFGrado1" type="radio"
					name="grado" value="Esperto"> Esperto <br> <input
					id="aggiungiVFGrado2" type="radio" name="grado" value="Qualificato">
				Qualificato <br> <input id="aggiungiVFGrado3" type="radio"
					name="grado" value="Coordinatore"> Coordinatore <br>

			</div>

			<br> <label> Giorni di ferie dell'anno corrente: <input
				type="number" name="giorniFerieAnnoCorrente" min="0" max="22"
				value="0" required>
			</label> &ensp; <label> Giorni di ferie degli anni precedenti: <input
				type="number" name="giorniFerieAnnoPrecedente" min="0" max="999"
				value="0" required>
			</label> <br> <br> <input id="buttonFormAggiungiVF" type="submit"
				class="btn btn-outline-success" data-toggle="modal" class="button"
				value="Aggiungi"> &nbsp; <input type="button" value="Chiudi"
				class="btn btn-outline-danger" data-toggle="modal" class="button"
				onclick="chiudiFormAggiunta()"> <br> <br>

		</form>

		<br> <br> <br>

	</div>

</body>
</html>