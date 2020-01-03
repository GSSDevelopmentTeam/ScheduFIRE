<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
    		import="java.util.ArrayList,java.util.Iterator, model.bean.*, model.dao.*" 
%>



<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">   </th>
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
						<td class="text-center"><img src="Grado/<%=vigile.getGrado() %>.png" style="height:25%" onerror="this.parentElement.innerHTML='Non disponibile';"></td>
						<td class="text-center"><%=vigile.getNome() %></td>
						<td class="text-center"><%=vigile.getCognome() %></td>
						<td class="text-center"><%=vigile.getMansione() %></td>
						<td class="text-center"> <input type="radio" value="<%=vigile.getEmail() %>" name="VFNew"></td>
					</tr>
					<% ; i.remove(); } } }
					for(VigileDelFuocoBean vigile: vigili){
					%>
					<tr>
						<td class="text-center"><%=vigile.getGrado() %></td>
						<td class="text-center"><%=vigile.getNome() %></td>
						<td class="text-center"><%=vigile.getCognome() %></td>
						<td class="text-center"><%=vigile.getMansione() %></td>
						<td class="text-center"> <input type="radio" value="<%=vigile.getEmail() %>" name="VFNew"> </td>
					</tr>

					<% } %>
					
			</tbody>
		</table>
		
<% String email = (String) request.getParameter("email"); %>
	<input type="hidden" name="email" value="<%=email%>">
</div>