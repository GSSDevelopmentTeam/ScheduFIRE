<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import = "java.util.ArrayList, model.bean.*, model.dao.*" %>

<html>
	<jsp:include page="StandardJSP.jsp" />
	<link rel="stylesheet" href="CSS/TableCSS.css">
	<body>
	
	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />
		<h2>Gestione Ferie</h2>
			<table>
				<thead>
					<tr>
						<th>Grado</th>
						<th>Nome</th>
						<th>Cognome</th>
						<th>Email</th>
						<th>Mansione</th>
						<th>Ferie anno <br> corrente</th>
						<th>Ferie anno <br> precedente</th>
						<th>Inserisci <br> periodo di ferie</th>
						<th>Rimuovi <br>periodo di ferie</th>
					</tr>
				</thead>
				
				<tbody>
					<%	ArrayList<VigileDelFuocoBean> listaVigili;
						listaVigili= (ArrayList<VigileDelFuocoBean>) request.getAttribute("listaVigili");
						
						for(int i=0; i<listaVigili.size(); i++){
							VigileDelFuocoBean vigile = listaVigili.get(i);
					%>
					
					<tr>
						<td><%=vigile.getGrado() %></td>
						<td><%=vigile.getNome() %></td>
						<td><%=vigile.getCognome()%></td>
						<td><%=vigile.getEmail() %></td>
						<td><%=vigile.getMansione()%></td>
						<td><%=vigile.getGiorniFerieAnnoCorrente() %></td>
						<td><%=vigile.getGiorniFerieAnnoCorrente() %></td>
						<td><button type="aggiungi" src=""></button></td>
						<td><button type="rimuovi" src=""></button></td>
					</tr>
					
					<% } %>
				
				</tbody>
			
			</table>
	</body>
</html>