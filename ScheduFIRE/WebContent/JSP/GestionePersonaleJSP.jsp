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
<link type="text/css" rel="stylesheet" href="../CSS/GestionePersonaleCSS.css">

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
	
	<form action="../AggiungiVFServlet"> 
	
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
			Giorni di ferie degli anni precedenti: <input type = "text" name = "giorniFeriAnnoPrecedente">
		</label> <br> <br>
		
		<input type = "submit" value = "Aggiungi"> <br> <br>
		
	</form>
	
</body>
</html>