<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
    		import="java.util.ArrayList,java.util.Iterator, model.bean.*, model.dao.*, java.sql.Date" 
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
				<%	ArrayList<VigileDelFuocoBean> vigili=(ArrayList<VigileDelFuocoBean>)request.getAttribute("vigili");
				if(vigili==null){
					%>
					<h2> Non ci sono Vigili del fuoco disponibili</h2>
				<%} else{
				for (VigileDelFuocoBean vigile : vigili){	
				%>

					<tr>
						<td class="text-center"><img src="Grado/<%=vigile.getGrado() %>.png" style="height:25%" onerror="this.parentElement.innerHTML='Non disponibile';"></td>
						<td class="text-center"><%=vigile.getNome() %></td>
						<td class="text-center"><%=vigile.getCognome() %></td>
						<td class="text-center"><%=vigile.getMansione() %></td>
						<td class="text-center"> <input type="radio" value="<%=vigile.getEmail() %>" name="VFNew"></td>
					</tr>
					<% } }
					%>					
			</tbody>
		</table>
		
<% String email = (String) request.getParameter("email"); 
	String tipo = (String) request.getParameter("tiposquadra");
	Date data = (Date) request.getAttribute("dataModifica");%>
	<input type="hidden" name="email" value="<%=email%>">
	<input type="hidden"  name="tiposquadra" value="<%=tipo%>">
	<input type="hidden"  name="dataModifica" value="<%=data%>">
</div>