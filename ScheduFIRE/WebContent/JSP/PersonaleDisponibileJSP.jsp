<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
    		import="java.util.ArrayList,java.util.Iterator, model.bean.*, model.dao.*" 
    		%>


<section>
	<h2>Personale Disponibile</h2>
	<h5><%=request.getAttribute("titolo")==null ? "" : request.getAttribute("titolo") %></h5>
		<table>
			<thead>
				<tr>
					<th>Grado</th>
					<th>Nome</th>
					<th>Cognome</th>
					<th>Email</th>
					<th>Mansione</th>
					<th>Squadra</th>
				</tr>
			</thead>

			<tbody>
				<% 	ArrayList<ComponenteDellaSquadraBean> componenti=(ArrayList<ComponenteDellaSquadraBean>)request.getAttribute("componenti");
					ArrayList<VigileDelFuocoBean> vigili=(ArrayList<VigileDelFuocoBean>)request.getAttribute("vigili");
					for(ComponenteDellaSquadraBean componente : componenti){
						for(Iterator<VigileDelFuocoBean> i=vigili.iterator();i.hasNext();){
							VigileDelFuocoBean vigile=i.next();
							if (vigile.getEmail().equals(componente.getEmailVF())){		
				%>

					<tr>
						<td><%=vigile.getGrado() %></td>
						<td><%=vigile.getNome() %></td>
						<td><%=vigile.getCognome() %></td>
						<td><%=vigile.getEmail() %></td>
						<td><%=vigile.getMansione() %></td>
						<td><%=componente.getTipologiaSquadra() %></td>
					</tr>
					<% ; i.remove(); } } }
					for(VigileDelFuocoBean vigile: vigili){
					%>
					<tr>
						<td><%=vigile.getGrado() %></td>
						<td><%=vigile.getNome() %></td>
						<td><%=vigile.getCognome() %></td>
						<td><%=vigile.getEmail() %></td>
						<td><%=vigile.getMansione() %></td>
						<td> / </td>
					</tr>

					<% } %>
					
			</tbody>
		</table>
	</section>

