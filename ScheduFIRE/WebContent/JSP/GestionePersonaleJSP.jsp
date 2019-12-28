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

<body>
	<%@ include file = "HeaderJSP.jsp" %>
	
	<h2>Gestione personale</h2>
	
	<h4>Vigili</h4>
		
	<% 
	
	if(vigili != null) {
		
		for(VigileDelFuocoBean vf: vigili) { 
	%>

			<div>
			
				<span>
					<%= vf.getNome() %>
				</span>
				
				<span>
					<%= vf.getCognome() %>
				</span>
				
				<span>
					Email: <%= vf.getEmail() %>
				</span>
				
				<span>
					Grado: <%= vf.getGrado() %>
				</span>
				
				<span>
					Mansione: <%= vf.getMansione() %>
				</span>
				
				<span>
					Giorni di ferie dell'anno corrente: <%= vf.getGiorniFerieAnnoCorrente() %>
				</span>
				
				<span>
					Giorni di ferie degli anni precedenti: <%= vf.getGiorniFerieAnnoPrecedente() %>
				</span>
				
				<span>
					Carico lavorativo attuale: <%= vf.getCaricoLavoro() %>
				</span>
				
				<form action="./ModificaVFServlet" class = "modificaVF">
					<br>
		
					<label>
						Nome: <input type = "text" name = "nomeNuovo" value = <%= vf.getNome() %>>
					</label> <br> <br>
					
					<label>
						Cognome: <input type = "text" name = "cognomeNuovo" value = <%= vf.getCognome() %>>
					</label> <br> <br>
					
					<label>
						Email: <input type = "text" name = "emailNuova" value = <%= vf.getEmail() %>>
					</label> <br> <br>
					
					<label>
						Grado: <input type = "text" name = "gradoNuovo" value = <%= vf.getGrado() %>>
					</label> <br> <br>
					
					<label>
						Mansione: <input type = "text" name = "mansioneNuova" value = <%= vf.getMansione() %>>
					</label> <br> <br>
					
					<label>
						Giorni di ferie dell'anno corrente: <input type = "text" name = giorniFerieAnnoCorrenteNuovi value = <%= vf.getGiorniFerieAnnoCorrente() %>>
					</label> <br> <br>
					
					<label>
						Giorni di ferie degli anni precedenti: <input type = "text" name = "giorniFerieAnnoPrecedenteNuovi" value = <%= vf.getGiorniFerieAnnoPrecedente() %>>
					</label> <br> <br>
					
					<input type = "hidden" name = "emailVecchia" value = <%= vf.getEmail() %>>
					
					<input type = "submit" value = "Modifica"> <br> <br>
					
				</form>
				
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
					
					<br>
					
					<input type = "submit" value = "Cancella">
					
					<br> <br>
					
				</form>
			
			</div>
			
	<%
		} 
		
	} else {
	
	%>
	
		<section>Nessun Vigile del Fuoco presente!</section>
	
	<%
	
	}
	
	%>
	
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
			Email: <input type = "text" name = "email">
		</label> <br> <br>
		
		<label>
			Grado: <input type = "text" name = "grado">
		</label> <br> <br>
		
		<label>
			Mansione: <input type = "text" name = "mansione">
		</label> <br> <br>
		
		<label>
			Giorni di ferie dell'anno corrente: <input type = "text" name = "giorniFerieAnnoCorrente">
		</label> <br> <br>
		
		<label>
			Giorni di ferie degli anni precedenti: <input type = "text" name = "giorniFerieAnnoPrecedente">
		</label> <br> <br>
		
		<input type = "submit" value = "Aggiungi"> <br> <br>
		
	</form>
	
</body>
</html>