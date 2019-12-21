<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
    		import="java.util.ArrayList, model.bean.*, model.dao.*" 
    		%>


<section>
	<h2>Personale Disponibile</h2>
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
				<% 	ArrayList<VigileDelFuocoBean> vigili=(ArrayList<VigileDelFuocoBean>)request.getAttribute("vigili");
					for(VigileDelFuocoBean vigile : vigili){	%>
					<tr>
						<td><%=vigile.getGrado() %></td>
						<td><%=vigile.getNome() %></td>
						<td><%=vigile.getCognome() %></td>
						<td><%=vigile.getEmail() %></td>
						<td><%=vigile.getMansione() %></td>
						<td>ToDo</td>
					</tr>
					<%} %>
			</tbody>
		</table>
	</section>

