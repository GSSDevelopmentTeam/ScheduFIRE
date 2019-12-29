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
	<a href="#aggiungiVF" class="btn btn-danger" style="float:right; margin-right: 30px;">Aggiungi vigile del fuoco</a> <br><br>
	
	
	<h4>Vigili del Fuoco</h4>
		
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
				<!-- 
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
				 -->
				 <div class = "modificaVF">
				<form action="./ModificaVFServlet" >
					<br>
		
					<label>
						Nome: <input type = "text" name = "nomeNuovo" value = <%= vf.getNome() %>>
					
						Cognome: <input type = "text" name = "cognomeNuovo" value = <%= vf.getCognome() %>>
					</label> <br> <br>
					
					<label>
						Email: <input type = "text" name = "emailNuova" value = <%= vf.getEmail() %>>
					
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
					
					<Button class="btn btn-outline-secondary">Modifica</Button> <br> <br>
					
				</form>
				
				<form action="./EliminaVFServlet">
				
					
					<Button class="btn btn-outline-danger">Cancella</Button>
				
					<br> <br>
					
				</form>
				
					</div>
			<br> <br>
			
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
		
		<Button class="btn btn-outline-secondary">Aggiungi</Button> <br> <br> 
		
	</form>
	<br> <br> <br><br>
</body>
</html>