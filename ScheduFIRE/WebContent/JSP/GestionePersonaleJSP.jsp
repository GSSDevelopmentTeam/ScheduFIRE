<%@ page language="java" 
	pageEncoding="ISO-8859-1" import="java.util.*, model.bean.*"%>
<%

Collection<VigileDelFuocoBean> vigili = null;
Object attributoVigili = request.getAttribute("vigili");
if(attributoVigili instanceof Collection) 
	  vigili = (Collection<VigileDelFuocoBean>) attributoVigili;

Object ordinamentoObj = request.getAttribute("ordinamento");
String ordinamento = null;
if(ordinamentoObj.getClass().getSimpleName().equals("String"))
	ordinamento = (String) ordinamentoObj;

%>

<!DOCTYPE html>

<html>

<%@ include file="StandardJSP.jsp"%>


<link type="text/css" rel="stylesheet"
	href="./CSS/GestionePersonaleCSS.css">

<style>
			
.table td, .table th {
    padding: 1.5px!important;
    vertical-align: top;
    border-top: 1px solid #dee2e6;
}
.back-up{
	border-radius: 50px;
    font-size: 30px;
    width: 60px;
    position: fixed;
    bottom: 5%;
    right: 5%;
    background-color:#FFFFFF;
	box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0
		rgba(0, 0, 0, 0.19);
}
</style>
<body>
	<jsp:include page="HeaderJSP.jsp" />

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

	<script type="text/javascript">
		
		function mostraFormModifica(id) {

			$("#modifica" + id).toggle("slow");
			
			var button = document.getElementById(id);

			if( button.innerHTML === "Annulla" ) {
				button.innerHTML = "Modifica";
			} 
			else {
				button.innerHTML = "Annulla";
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
		
		function alertInsuccesso(input) {
			$("#rimozioneNoOk span").text(input);
			$("#rimozioneNoOk").fadeTo(4000, 500).slideUp(500, function() {
				$("#success-alert").slideUp(500);
			});

		}

		function alertSuccesso(input) {
			$("#rimozioneOk span").text(input);
			$("#rimozioneOk").fadeTo(4000, 500).slideUp(500, function() {
				$("#success-alert").slideUp(500);
			});
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
			
			if( (nome.value === "") || (nome.value === "undefined") || 
					!nome.value.test("^[A-Z]{1}[a-z]{0,19}+$") ) {
				
				nome.focus();
				alert("Nome errato!");
				return false;
				
			}
			else if( (cognome.value === "") || (cognome.value === "undefined") ||
					!cognome.value.test("^[A-Z]{1}[a-z]{0,19}+$") ) {

				cognome.focus();
				alert("Cognome errato!");
				return false;
				
			}
			else if( (email.value === "") || (email.value === "undefined") || 
					!email.value.test("[A-Za-z]+([1-9][0-9]*)?\.[A-Za-z]+") ) {

				email.focus();
				alert("Email errata!");
				return false;
				
			}
			else if( mansione1.value.checked !== "true" || mansione2.value.checked !== "true" 
					|| mansione3.value.checked !== "true") {

				alert("Mansione errata!");
				return false;
				
			}
			else if( ( mansione2.value.checked === "true" ||  mansione3.value.checked === "true" ) &&
					( grado1.value.checked !== "true" || grado2.value.checked !== "true" ||
						grado3.value.checked !== "true") ) {

				alert("Grado errato!");
				return false;
				
			}
			
			return true;
			
		}
		
	</script>

	<section>

		<br>
<a href="#titolo"><button class=" back-up btn btn-outline-secondary"> ^ </button></a>
		<h2 id="titolo">Gestione Personale</h2>

		<form id="ordinamento" action="./GestionePersonaleServlet">
			<div id="divOrdinamento">
				Ordina per: <select id="selectOrdinamento" name="ordinamento"
					class = "custom-select" onchange="this.form.submit()" style = "width: 65%">

					<%
					if( ordinamento != null ) {
						if( ordinamento.equals("nome") ) {
					%>
					<option value="nome" selected>Nome</option>
					<option value="cognome">Cognome</option>
					<option value="caricoLavoro">Carico di lavoro</option>
					<option value="giorniFerieAnnoCorrente">Ferie anno corrente</option>
					<option value="giorniFerieAnnoPrecedente">Ferie anni precedenti</option>
					<%
						} else if( ordinamento.equals("cognome") ) {		
						%>
					<option value="nome">Nome</option>
					<option value="cognome" selected>Cognome</option>
					<option value="caricoLavoro">Carico di lavoro</option>
					<option value="giorniFerieAnnoCorrente">Ferie anno corrente</option>
					<option value="giorniFerieAnnoPrecedente">Ferie anni precedenti</option>
					<%
						} else if( ordinamento.equals("caricoLavoro") ) {		
						%>
					<option value="nome">Nome</option>
					<option value="cognome">Cognome</option>
					<option value="caricoLavoro" selected>Carico di lavoro</option>
					<option value="giorniFerieAnnoCorrente">Ferie anno corrente</option>
					<option value="giorniFerieAnnoPrecedente">Ferie anni precedenti</option>
					<%
						} else if( ordinamento.equals("giorniFerieAnnoCorrente") ) {		
						%>
					<option value="nome">Nome</option>
					<option value="cognome">Cognome</option>
					<option value="caricoLavoro">Carico di lavoro</option>
					<option value="giorniFerieAnnoCorrente" selected>Ferie anno corrente</option>
					<option value="giorniFerieAnnoPrecedente">Ferie anni precedenti</option>
					<%
						} else if( ordinamento.equals("giorniFerieAnnoPrecedente") ) {		
						%>
					<option value="nome">Nome</option>
					<option value="cognome">Cognome</option>
					<option value="caricoLavoro">Carico di lavoro</option>
					<option value="giorniFerieAnnoCorrente">Ferie anno corrente</option>
					<option value="giorniFerieAnnoPrecedente" selected>Ferie anni precedenti</option>
					<%
						} else {		
						%>
					<option value="nome">Nome</option>
					<option value="cognome">Cognome</option>
					<option value="caricoLavoro">Carico di lavoro</option>
					<option value="giorniFerieAnnoCorrente">Ferie anno corrente</option>
					<option value="giorniFerieAnnoPrecedente">Ferie anni precedenti</option>
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
		
			<!--------- Alert Ok----------------->
	
		<div
			class="alert alert-success flex alert-dismissible fade in text-center fixed-top"
			id="rimozioneOk"
			style="display: none; position: fixed; z-index: 99999; width: 100%">
			<strong>Operazione riuscita!</strong> <span>Rimozione ferie
				avvenuta con successo..</span>
		</div>
	
		<!-- ----------------------- -->
	
		<!--------- Alert NON Ok ----------------->
	
		<div
			class="alert alert-danger flex alert-dismissible fade in text-center fixed-top"
			id="rimozioneNoOk"
			style="display: none; position: fixed; z-index: 99999; width: 100%">
			<strong>Errore!</strong> <span>Rimozione ferie non avvenuta..</span>
		</div>
	
		<!-- ----------------------- -->
		

		<% 
				
			if(vigili != null) {
				
		%>
		<div class="table-responsive">
			<table class="table  table-hover" id="listaVigili">
				<thead class="thead-dark">
					<tr>
						<th class="text-center" style = "width: 10%">Grado</th>
						<th class="text-center">Nome</th>
						<th class="text-center">Cognome</th>
						<th class="text-center">Email</th>
						<th class="text-center">Mansione</th>
						<th class="text-center">Carico lavorativo</th>
						<th class="text-center">Ferie anno<br>corrente</th>
						<th class="text-center">Ferie anni<br>precedenti</th>
						<th class="text-center">Modifica</th>
						<th class="text-center">Cancella</th>
					</tr>
				</thead>

				<%		
					
					int id = 1;
					for(VigileDelFuocoBean vf: vigili) {
					
				%>

				<tbody>

					<tr id=<%= "vigile" + id %> class="vigile">
					
						<td class="text-center">

						<img src="Grado/<%=vf.getMansione().equals("Capo Squadra") && 
						vf.getGrado().equals("Esperto")?"EspertoCapoSquadra":vf.getGrado() %>.png" 
						width=40% onerror="this.parentElement.innerHTML='Non disponibile';"
						title = <%= vf.getGrado() %>>
						
						</td>

						<td class="text-center"><%= vf.getNome() %></td>

						<td class="text-center"><%= vf.getCognome() %></td>

						<td class="text-center"><%= vf.getEmail() %></td>

						<td class="text-center"><%= vf.getMansione() %></td>

						<td class="text-center"><%= vf.getCaricoLavoro() %></td>

						<td class="text-center"><%= vf.getGiorniFerieAnnoCorrente() %></td>

						<td class="text-center"><%= vf.getGiorniFerieAnnoPrecedente() %></td>

						<td>

							<button id=<%= id %> type="button" class="btn btn-outline-secondary"
							data-toggle="modal" onclick="mostraFormModifica(this.id)">
								<%= "Modifica" %>
							</button>

						</td>

						<td>
						
							<div id = <%= "divPopupFormEliminazioneVF" + id %> class="divPopupFormEliminazioneVF">
								
								<br> <br> <br> <br> <br> 
								
								<form id = <%= "formEliminazioneVF" + id %> class="form-popup" action="./EliminaVFServlet">
								
									<br>
									
									<label id = "labelCancellazione">
										Sei sicuro di cancellare <%= vf.getCognome() %> <%= vf.getNome() %> ?
									</label>
									
									<br> <br>
	
									<input type="hidden" name="nome" value=<%= vf.getNome() %>>
	
									<input type="hidden" name="cognome" value=<%= vf.getCognome() %>>
	
									<input type="hidden" name="email" value= <%= vf.getEmail().
									substring( 0 , vf.getEmail().indexOf("@") ) %>>
	
									<input type="hidden" name="turno" value=<%= vf.getTurno() %>>
	
									<input type="hidden" name="mansione"
										value=<%= vf.getMansione() %>> <input type="hidden"
										name="giorniFerieAnnoCorrente"
										value=<%= vf.getGiorniFerieAnnoCorrente() %>> <input
										type="hidden" name="giorniFerieAnnoPrecedente"
										value=<%= vf.getGiorniFerieAnnoPrecedente() %>> <input
										type="hidden" name="caricoLavoro"
										value=<%= vf.getCaricoLavoro() %>> <input type="hidden"
										name="adoperabile" value=<%= vf.isAdoperabile() %>> <input
										type="hidden" name="grado" value=<%= vf.getGrado() %>>
	
									<input type="hidden" name="username" 
										value=<%= vf.getUsername() %>>
										
									<input id = "buttonCancella" type="submit" class="btn btn-outline-success" 
										data-toggle="modal" class="button" value="Sì">
									
									&ensp;
										
									<input type = "button" id = "buttonCancellaNo" class = "btn btn-outline-danger"
										data-toggle="modal" value = "No" onclick = "chiudiFormCancellazione(this.parentNode)">
										
									<br> <br>
									
								</form>
								
							</div>
							
							<button id = <%= id * (-1) %> class = "btn btn-outline-danger" onclick = "mostraFormCancellazione(this.id)">
								Cancella
							</button>

						</td>

					</tr>

					<tr id=<%= "modifica" + id %> class="modifica">

						<td colspan="10">

							<form id=<%= "modificaVF" + id %> action="./ModificaVFServlet"
								class="modificaVF" onsubmit="return validazioneForm(this.id)">
								<br> <label> Nome: <input
									id=<%= "modificaVF" + id + "Nome" %> type="text"
									name="nomeNuovo" value=<%= vf.getNome() %>>
								</label> &ensp; <label> Cognome: <input
									id=<%= "modificaVF" + id + "Cognome" %> type="text"
									name="cognomeNuovo" value=<%= vf.getCognome() %>>
								</label> <br> <br> <label> Email: <input
									id=<%= "modificaVF" + id + "Email" %> type="text"
									name="emailNuova" value= <%= vf.getEmail().substring( 0 , vf.getEmail().indexOf("@") ) %> >@vigilfuoco.it
								</label> <br> <br> 
								
								<% if( vf.getMansione().equals("Capo Squadra") ) { %>
								
									<div class = "mansione">
			
										Mansione: <br>
										
									 	<input id=<%= "modificaVF" + id + "Mansione1" %> type = "radio" name = "mansioneNuova"
									 	 value = "Capo Squadra" onclick = "sceltaMansione(this)" checked > Capo Squadra <br>
										<input id=<%= "modificaVF" + id + "Mansione2" %>  type = "radio" name = "mansioneNuova"
										 value = "Autista" onclick = "sceltaMansione(this)"> Autista <br>
										<input id=<%= "modificaVF" + id + "Mansione3" %>  type = "radio" name = "mansioneNuova"
										 value = "Vigile" onclick = "sceltaMansione(this)"> Vigile <br>
						
									</div> <br> 
								
								<% } else if ( vf.getMansione().equals("Autista") ) { %>
								
									<div class = "mansione">
			
										Mansione: <br>
										
									 	<input id=<%= "modificaVF" + id + "Mansione1" %> type = "radio" name = "mansioneNuova"
									 	 value = "Capo Squadra" onclick = "sceltaMansione(this)"> Capo Squadra <br>
										<input id=<%= "modificaVF" + id + "Mansione2" %>  type = "radio" name = "mansioneNuova"
										 value = "Autista" onclick = "sceltaMansione(this)" checked> Autista <br>
										<input id=<%= "modificaVF" + id + "Mansione3" %>  type = "radio" name = "mansioneNuova"
										 value = "Vigile" onclick = "sceltaMansione(this)"> Vigile <br>
						
									</div> <br> 
								
								<% } else if ( vf.getMansione().equals("Vigile") ) { %>
								
									<div class = "mansione">
			
										Mansione: <br>
										
									 	<input id=<%= "modificaVF" + id + "Mansione1" %> type = "radio" name = "mansioneNuova"
									 	 value = "Capo Squadra" onclick = "sceltaMansione(this)"> Capo Squadra <br>
										<input id=<%= "modificaVF" + id + "Mansione2" %>  type = "radio" name = "mansioneNuova"
										 value = "Autista" onclick = "sceltaMansione(this)"> Autista <br>
										<input id=<%= "modificaVF" + id + "Mansione3" %>  type = "radio" name = "mansioneNuova"
										 value = "Vigile" onclick = "sceltaMansione(this)" checked> Vigile <br>
						
									</div> <br> 
								
								<% } else { %>
									
									<div class = "mansione">
			
										Mansione: <br>
										
									 	<input id=<%= "modificaVF" + id + "Mansione1" %> type = "radio" name = "mansioneNuova"
									 	 value = "Capo Squadra" onclick = "sceltaMansione(this)"> Capo Squadra <br>
										<input id=<%= "modificaVF" + id + "Mansione2" %>  type = "radio" name = "mansioneNuova"
										 value = "Autista" onclick = "sceltaMansione(this)"> Autista <br>
										<input id=<%= "modificaVF" + id + "Mansione3" %>  type = "radio" name = "mansioneNuova"
										 value = "Vigile" onclick = "sceltaMansione(this)"> Vigile <br>
						
									</div> <br> 
									
								<% } %>
								
								<% if( vf.getGrado().equals("Semplice") && vf.getMansione().equals("Capo Squadra") ) { %>
								
									 <div id =  <%= "modificaVF" + id + "GradoCapoSquadra" %> class = "grado" style = "display: block">
								
										Grado: <br>
										
									 	<input id=<%= "modificaVF" + id + "Grado0" %>  type = "checkbox"
									 	 name = "gradoNuovo" value = "Esperto"> Esperto <br>
						
								 	</div> 
									 
									 <div id =  <%= "modificaVF" + id + "Gradi" %> class = "grado" style = "display: hidden" >
									 
									 	Grado: <br>
									 
										<input id=<%= "modificaVF" + id + "Grado1" %>  type = "radio" 
										name = "gradoNuovo" value = "Esperto"> Esperto <br>
										<input id=<%= "modificaVF" + id + "Grado2" %>  type = "radio" 
										name = "gradoNuovo" value = "Qualificato"> Qualificato <br>
										<input id=<%= "modificaVF" + id + "Grado3" %>  type = "radio" 
										name = "gradoNuovo" value = "Coordinatore"> Coordinatore <br>
								
									 </div>
								
								<% } else if( vf.getGrado().equals("Esperto") && vf.getMansione().equals("Capo Squadra") ) { %>

									<div id =  <%= "modificaVF" + id + "GradoCapoSquadra" %> class = "grado" style = "display: block">
								
										Grado: <br>
										
									 	<input id=<%= "modificaVF" + id + "Grado0" %>  type = "checkbox"
									 	 name = "gradoNuovo" value = "Esperto" checked> Esperto <br>
						
								 	</div> 
									 
									 <div id =  <%= "modificaVF" + id + "Gradi" %> class = "grado" style = "display: hidden" >
									 
									 	Grado: <br>
									 
										<input id=<%= "modificaVF" + id + "Grado1" %>  type = "radio" 
										name = "gradoNuovo" value = "Esperto"> Esperto <br>
										<input id=<%= "modificaVF" + id + "Grado2" %>  type = "radio" 
										name = "gradoNuovo" value = "Qualificato"> Qualificato <br>
										<input id=<%= "modificaVF" + id + "Grado3" %>  type = "radio" 
										name = "gradoNuovo" value = "Coordinatore"> Coordinatore <br>
								
									 </div>

								<% } else if( vf.getGrado().equals("Qualificato") && ( vf.getMansione().equals("Autista")
										|| vf.getMansione().equals("Vigile") ) ) { %>
										
									<div id =  <%= "modificaVF" + id + "GradoCapoSquadra" %> class = "grado" style = "display: hidden">
								
										Grado: <br>
										
									 	<input id=<%= "modificaVF" + id + "Grado0" %>  type = "checkbox"
									 	 name = "gradoNuovo" value = "Esperto"> Esperto <br>
						
								 	</div> 
									 
									 <div id =  <%= "modificaVF" + id + "Gradi" %> class = "grado" style = "display: block" >
									 
									 	Grado: <br>
									 
										<input id=<%= "modificaVF" + id + "Grado1" %>  type = "radio" 
										name = "gradoNuovo" value = "Esperto"> Esperto <br>
										<input id=<%= "modificaVF" + id + "Grado2" %>  type = "radio" 
										name = "gradoNuovo" value = "Qualificato" checked> Qualificato <br>
										<input id=<%= "modificaVF" + id + "Grado3" %>  type = "radio" 
										name = "gradoNuovo" value = "Coordinatore"> Coordinatore <br>
								
									 </div>
								
								
								<% } else if( vf.getGrado().equals("Coordinatore") && ( vf.getMansione().equals("Autista")
										|| vf.getMansione().equals("Vigile") ) ) { %>
			
									<div id =  <%= "modificaVF" + id + "GradoCapoSquadra" %> class = "grado" style = "display: hidden">
								
										Grado: <br>
										
									 	<input id=<%= "modificaVF" + id + "Grado0" %>  type = "checkbox"
									 	 name = "gradoNuovo" value = "Esperto"> Esperto <br>
						
								 	</div> 
									 
									 <div id =  <%= "modificaVF" + id + "Gradi" %> class = "grado" style = "display: block" >
									 
									 	Grado: <br>
									 
										<input id=<%= "modificaVF" + id + "Grado1" %>  type = "radio" 
										name = "gradoNuovo" value = "Esperto"> Esperto <br>
										<input id=<%= "modificaVF" + id + "Grado2" %>  type = "radio" 
										name = "gradoNuovo" value = "Qualificato" > Qualificato <br>
										<input id=<%= "modificaVF" + id + "Grado3" %>  type = "radio" 
										name = "gradoNuovo" value = "Coordinatore" checked> Coordinatore <br>
								
									 </div>
			
								<% } else if( vf.getGrado().equals("Esperto") && ( vf.getMansione().equals("Autista")
										|| vf.getMansione().equals("Vigile") ) ) { %>
										
									<div id =  <%= "modificaVF" + id + "GradoCapoSquadra" %> class = "grado" style = "display: hidden">
								
										Grado: <br>
										
									 	<input id=<%= "modificaVF" + id + "Grado0" %>  type = "checkbox"
									 	 name = "gradoNuovo" value = "Esperto"> Esperto <br>
						
								 	</div> 
									 
									 <div id =  <%= "modificaVF" + id + "Gradi" %> class = "grado" style = "display: block" >
									 
									 	Grado: <br>
									 
										<input id=<%= "modificaVF" + id + "Grado1" %>  type = "radio" 
										name = "gradoNuovo" value = "Esperto" checked> Esperto <br>
										<input id=<%= "modificaVF" + id + "Grado2" %>  type = "radio" 
										name = "gradoNuovo" value = "Qualificato" > Qualificato <br>
										<input id=<%= "modificaVF" + id + "Grado3" %>  type = "radio" 
										name = "gradoNuovo" value = "Coordinatore" > Coordinatore <br>
								
									 </div>	
										
								<% } else { %>
								
									<div id =  <%= "modificaVF" + id + "GradoCapoSquadra" %> class = "grado" style = "display: block">
								
										Grado: <br>
										
									 	<input id=<%= "modificaVF" + id + "Grado0" %>  type = "checkbox"
									 	 name = "gradoNuovo" value = "Esperto"> Esperto <br>
						
								 	</div> 
									 
									 <div id =  <%= "modificaVF" + id + "Gradi" %> class = "grado" style = "display: block" >
									 
									 	Grado: <br>
									 
										<input id=<%= "modificaVF" + id + "Grado1" %>  type = "radio" 
										name = "gradoNuovo" value = "Esperto"> Esperto <br>
										<input id=<%= "modificaVF" + id + "Grado2" %>  type = "radio" 
										name = "gradoNuovo" value = "Qualificato" > Qualificato <br>
										<input id=<%= "modificaVF" + id + "Grado3" %>  type = "radio" 
										name = "gradoNuovo" value = "Coordinatore" > Coordinatore <br>
								
									 </div>	
								
								<% } %>
			
								<br> 

								<label> Giorni di ferie dell'anno
									corrente: <input type="number"
									name=giorniFerieAnnoCorrenteNuovi
									value=<%= vf.getGiorniFerieAnnoCorrente() %> min="0">
								</label> <br> <br> <label> Giorni di ferie degli anni
									precedenti: <input type="number"
									name="giorniFerieAnnoPrecedenteNuovi"
									value=<%= vf.getGiorniFerieAnnoPrecedente() %> min="0">
								</label> <br> <br> <input type="hidden" name="emailVecchia"
									value= <%= vf.getEmail().substring( 0 , vf.getEmail().indexOf("@") ) %> > 
									
									<Button type="submit" class="btn btn-outline-success"
									data-toggle="modal" class="button" value="Conferma" onsubmit="validazioneForm()">
										Conferma
									</Button>
									
								<br> <br>

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

		<h5>Nessun Vigile del Fuoco presente!</h5>

		<%
		
		}
		
		%>

	</section>

	<div id="divPopup" class="popup">

		<form id="aggiungiVF" class="form-popup" action="./AggiungiVFServlet"
			onsubmit="return validazioneForm(this.id)">

			<h2>Aggiungi un vigile del fuoco</h2>

			<br> <label> Nome: <input id="aggiungiVFNome"
				type="text" name="nome">
			&ensp;
			</label> <label> Cognome: <input id="aggiungiVFCognome" type="text"
				name="cognome">
			</label> <br> <br> <label> Email: <input
				id="aggiungiVFEmail" type="text" name="email">@vigilfuoco.it
			</label> <br> <br>
			
			<div class = "mansione">
			
				Mansione: <br>
				
			 	<input id = "aggiungiVFMansione1" type = "radio" name = "mansione"
			 	 value = "Capo Squadra" onclick = "sceltaMansione(this)"> Capo Squadra <br>
				<input id = "aggiungiVFMansione2" type = "radio" name = "mansione"
				 value = "Autista" onclick = "sceltaMansione(this)"> Autista <br>
				<input id = "aggiungiVFMansione3" type = "radio" name = "mansione"
				 value = "Vigile" onclick = "sceltaMansione(this)"> Vigile <br>
	
			</div> <br> 
			
				
			 
			 <div id = "aggiungiVFGradoCapoSquadra" class = "grado">
			
				Grado: <br>
				
			 	<input id = "aggiungiVFGrado0" type = "checkbox" name = "grado" value = "Esperto"> Esperto <br>

			 </div> 
			 
			 <div id = "aggiungiVFGradi" class = "grado">
			 
			 	Grado: <br>
			 
				<input id = "aggiungiVFGrado1" type = "radio" name = "grado" value = "Esperto"> Esperto <br>
				<input id = "aggiungiVFGrado2" type = "radio" name = "grado" value = "Qualificato"> Qualificato <br>
				<input id = "aggiungiVFGrado3" type = "radio" name = "grado" value = "Coordinatore"> Coordinatore <br>
		
			 </div> 
			
			<br>
			
			<label> Giorni di ferie dell'anno
				corrente: <input type="number" name="giorniFerieAnnoCorrente"
				min="0" value="0">
			</label> <br> <br> <label> Giorni di ferie degli anni
				precedenti: <input type="number" name="giorniFerieAnnoPrecedente"
				min="0" value="0">
			</label> <br> <br>
			
				<input id="buttonFormAggiungiVF" type="submit"
				class="btn btn-outline-success" data-toggle="modal"
				class="button" value="Aggiungi">
				&nbsp;
				<input type = "button" value="Chiudi"
				class="btn btn-outline-danger" data-toggle="modal" class="button"
				onclick="chiudiFormAggiunta()">
			<br> <br>

		</form>
		
		<br> <br> <br>

	</div>

</body>
</html>