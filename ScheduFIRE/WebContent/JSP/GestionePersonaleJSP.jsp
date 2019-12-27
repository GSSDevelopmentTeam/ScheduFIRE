<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "java.util.*, model.bean.*" %>
<%

Collection<VigileDelFuocoBean> vigili = null;
Object attributoVigili = request.getAttribute("vigili");
if(attributoVigili instanceof Collection) 
	  vigili = (Collection<VigileDelFuocoBean>) attributoVigili;

%>

<!DOCTYPE html>

<html>

<%@ include file = "StandardJSP.jsp" %>

<link type="text/css" rel="stylesheet" href="./CSS/GestionePersonaleCSS.css">
<link rel="stylesheet" href="CSS/TableCSS.css">

<body>
	<%@ include file = "HeaderJSP.jsp" %>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	
	<script type="text/javascript">
		
		function mostraFormModifica(id) {

			$("#modifica" + id).toggle("slow");
			
		}
		
	</script>
	
	<section>
	
		<h2>Gestione Personale</h2>
		
		<% 
				
			if(vigili != null) {
				
		%>
		
				<table>

					<thead>
					
						<tr>
						
							<th>Grado</th>
							
							<th>Nome</th>
							
							<th>Cognome</th>
							
							<th>Email</th>
							
							<th>Mansione</th>
							
							<th>Carico lavorativo</th>
							
							<th>Giorni di ferie dell'anno corrente</th>
							
							<th>Giorni di ferie degli anni precedenti</th>
							
							<th>Modifica</th>
							
							<th>Cancella</th>
							
						</tr>
					
					</thead>
			
				<%		
					
					int id = 0;
					for(VigileDelFuocoBean vf: vigili) {
					
				%>
					
						<tbody>
						
							<tr id = <%= "vigile" + id %> class = "vigile">
								<td>
									
									 <%= vf.getGrado() %>
									
								</td>
								
								<td>
								
									<%= vf.getNome() %>
		
								</td>
								
								<td>
								
									<%= vf.getCognome() %>
									
								</td>
								
								<td>
								
									<%= vf.getEmail() %>@vigilfuoco.it
									
								</td>
								
								<td>
								
									<%= vf.getMansione() %>
									
								</td>
								
								<td>
								
									<%= vf.getCaricoLavoro() %>
									
								</td>
								
								<td>
								
									<%= vf.getGiorniFerieAnnoCorrente() %>
									
								</td>
								
								<td>
								
									<%= vf.getGiorniFerieAnnoPrecedente() %>
									
								</td>
								
								<td>
									
									<button id = <%= id %> class = "buttonModifica" onclick = "mostraFormModifica(this.id)">
										Modifica
									</button>
									
								</td>
								
								<td>
									
									<form action="./EliminaVFServlet">
					
										<input type = "hidden" name = "nome" value = <%= vf.getNome() %>>
										
										<input type = "hidden" name = "cognome" value = <%= vf.getCognome() %>>
										
										<input type = "hidden" name = "email" value = <%= vf.getEmail() %>>
										
										<input type = "hidden" name = "turno" value = <%= vf.getTurno() %>>
										
										<input type = "hidden" name = "mansione" value = <%= vf.getMansione() %>>
										
										<input type = "hidden" name = "giorniFerieAnnoCorrente" value = <%= vf.getGiorniFerieAnnoCorrente() %>>
										
										<input type = "hidden" name = "giorniFerieAnnoPrecedente" value = <%= vf.getGiorniFerieAnnoPrecedente() %>>
										
										<input type = "hidden" name = "caricoLavoro" value = <%= vf.getCaricoLavoro() %>>
										
										<input type = "hidden" name = "adoperabile" value = <%= vf.isAdoperabile() %>>
										
										<input type = "hidden" name = "grado" value = <%= vf.getGrado() %>>
										
										<input type = "hidden" name = "username" value = <%= vf.getUsername() %>>	
										
										<input type = "submit" value = "Cancella">
										
									</form>
									
								</td>

							</tr>
							
							<tr id = <%= "modifica" + id %> class = "modifica">
							
								<td colspan = "10">
								
									<form action="./ModificaVFServlet" class = "modificaVF">
										<br>
							
										<label>
											Nome: <input type = "text" name = "nomeNuovo" value = <%= vf.getNome() %>>
										</label> <br> <br>
										
										<label>
											Cognome: <input type = "text" name = "cognomeNuovo" value = <%= vf.getCognome() %>>
										</label> <br> <br>
										
										<label>
											Email: <input type = "text" name = "emailNuova" value = <%= vf.getEmail() %>>@vigilfuoco.it
										</label> <br> <br>
										
										<label>
										
											Grado:
											
											<%
											if( "Qualificato".equals(vf.getGrado()) ) {
											%>
											
												<select name = "gradoNuovo">
													<option value = "">-</option>
													<option value = "Qualificato" selected>Qualificato</option>
													<option value = "Esperto">Esperto</option>
													<option value = "Coordinatore">Coordinatore</option>
												</select>
											
											<%
											} else if( "Esperto".equals(vf.getGrado()) ) {
											%>
											
												<select name = "gradoNuovo">
													<option value = "">-</option>
													<option value = "Qualificato">Qualificato</option>
													<option value = "Esperto" selected>Esperto</option>
													<option value = "Coordinatore">Coordinatore</option>
												</select>
											
											<%
											} else if( "Coordinatore".equals(vf.getGrado()) ) {
											%>
											
												<select name = "gradoNuovo">
													<option value = "">-</option>
													<option value = "Qualificato">Qualificato</option>
													<option value = "Esperto">Esperto</option>
													<option value = "Coordinatore" selected>Coordinatore</option>
												</select>
											
											<% 
											} else { 
											%>
											
												<select name = "gradoNuovo">
													<option value = "" selected>-</option>
													<option value = "Qualificato">Qualificato</option>
													<option value = "Esperto">Esperto</option>
													<option value = "Coordinatore">Coordinatore</option>
												</select>
											
											<% 
											} 
											%>

										</label>
			
										<label>
											
											Mansione: 
											
											<%
											if( "Capo Squadra".equals(vf.getMansione()) ) {
											%>
											
												<select name = "mansioneNuova">
													<option value = "">-</option>
													<option value = "Capo Squadra" selected>Capo Squadra</option>
													<option value = "Autista">Autista</option>
													<option value = "Vigile">Vigile</option>
												</select>
											
											<%
											} else if( "Autista".equals(vf.getMansione()) ) {
											%>
											
												<select name = "mansioneNuova">
													<option value = "">-</option>
													<option value = "Capo Squadra">Capo Squadra</option>
													<option value = "Autista" selected>Autista</option>
													<option value = "Vigile">Vigile</option>
												</select>
											
											<%
											} else if( "Vigile".equals(vf.getMansione()) ) {
											%>
											
												<select name = "mansioneNuova">
													<option value = "">-</option>
													<option value = "Capo Squadra">Capo Squadra</option>
													<option value = "Autista">Autista</option>
													<option value = "Vigile" selected>Vigile</option>
												</select>
											
											<%
											} else {
											%>
											
												<select name = "mansioneNuova">
													<option value = "" selected>-</option>
													<option value = "Capo Squadra">Capo Squadra</option>
													<option value = "Autista">Autista</option>
													<option value = "Vigile">Vigile</option>
												</select>
											
											<%
											}
											%>
											
										</label> <br> <br>
										
										<label>
											Giorni di ferie dell'anno corrente: <input type = "number" name = giorniFerieAnnoCorrenteNuovi value = <%= vf.getGiorniFerieAnnoCorrente() %>>
										</label> <br> <br>
										
										<label>
											Giorni di ferie degli anni precedenti: <input type = "number" name = "giorniFerieAnnoPrecedenteNuovi" value = <%= vf.getGiorniFerieAnnoPrecedente() %>>
										</label> <br> <br>
										
										<input type = "hidden" name = "emailVecchia" value = <%= vf.getEmail() %>>
										
										<input type = "submit" value = "Conferma"> <br> <br>
										
									</form>
								
								</td>
								
							</tr>
							
						</tbody>

				<%
						id++;
				
					}
				
				%>
		
			</table>
		
		<%	
			
		} else {
		
		%>
		
			<h5>Nessun Vigile del Fuoco presente!</h5>
			
		<%
		
		}
		
		%>

	</section>
	
	<h2>Aggiungi un vigile del fuoco</h2>
	
	<form action="./AggiungiVFServlet" id = "aggiungiVF"> 
	
		<br>
		
		<label>
			Nome: <input type = "text" name = "nome">
		</label> <br> <br>
		
		<label>
			Cognome: <input type = "text" name = "cognome">
		</label> <br> <br>
		
		<label>
			Email: <input type = "text" name = "email">@vigilfuoco.it
		</label> <br> <br>
		
		<label>
			Grado:
			<select name = "grado">
				<option value = "">-</option>
				<option value = "Qualificato">Qualificato</option>
				<option value = "Esperto">Esperto</option>
				<option value = "Coordinatore">Coordinatore</option>
			</select>
		</label> <br> <br>
		
		<label>
			Mansione: 
			<select name = "mansione">
				<option value = "">-</option>
				<option value = "Capo Squadra">Capo Squadra</option>
				<option value = "Autista">Autista</option>
				<option value = "Vigile">Vigile</option>
			</select>
		</label> <br> <br>
		
		<label>
			Giorni di ferie dell'anno corrente: <input type = "number" name = "giorniFerieAnnoCorrente">
		</label> <br> <br>
		
		<label>
			Giorni di ferie degli anni precedenti: <input type = "number" name = "giorniFerieAnnoPrecedente">
		</label> <br> <br>
		
		<input type = "submit" value = "Aggiungi"> <br> <br>
		
	</form>
	
</body>
</html>