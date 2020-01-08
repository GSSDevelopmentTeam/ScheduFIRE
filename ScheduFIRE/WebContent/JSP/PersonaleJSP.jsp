<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
    		import="java.util.ArrayList,java.util.Iterator, model.bean.*, model.dao.*, java.sql.Date" 
%>


<div class="d-flex justify-content-center" >
		<h2 > Diurno </h2>
	</div>
<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
				</tr>
			</thead>

			<tbody>
				<%	ArrayList<VigileDelFuocoBean> vigili=(ArrayList<VigileDelFuocoBean>)request.getAttribute("giorno");
				if(vigili==null){
					%>
					<h2> Non ci sono Vigili del fuoco disponibili</h2>
				<%} else{
				for (VigileDelFuocoBean vigile : vigili){	
				%>

					<tr>
						<td class="text-center"><img src="Grado/<%=vigile.getGrado() %>.png" style="height:25%" onerror="this.parentElement.innerHTML='Non disponibile';"></td>
						<td class="text-center"><%=vigile.getMansione() %></td>
						<td class="text-center"><strong><%=vigile.getNome() %></strong></td>
						<td class="text-center"><strong><%=vigile.getCognome() %></strong></td>
					</tr>
					<% } }
					%>					
			</tbody>
		</table>
		
		<div class="d-flex justify-content-center" >
		<h2 >  </h2>
	</div>
	<div class="d-flex justify-content-center" >
		<h2 > Notturno </h2>
	</div>
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
				</tr>
			</thead>

			<tbody>
				<%	 vigili=(ArrayList<VigileDelFuocoBean>)request.getAttribute("notte");
				if(vigili==null){
					%>
					<h2> Non ci sono Vigili del fuoco disponibili</h2>
				<%} else{
				for (VigileDelFuocoBean vigile : vigili){	
				%>

					<tr>
						<td class="text-center"><img src="Grado/<%=vigile.getGrado() %>.png" style="height:25%" onerror="this.parentElement.innerHTML='Non disponibile';"></td>
						<td class="text-center"><%=vigile.getMansione() %></td>
						<td class="text-center"><strong><%=vigile.getNome() %></strong></td>
						<td class="text-center"><strong><%=vigile.getCognome() %></strong></td>
					</tr>
					<% } }
					%>					
			</tbody>
		</table>
		
</div>