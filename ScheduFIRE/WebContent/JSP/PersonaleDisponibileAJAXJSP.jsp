<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	import="java.util.ArrayList,java.util.Iterator, model.bean.*, model.dao.*, java.sql.Date"%>



<div class="table-responsive">
	<table class="table  table-hover" id="listaVigili">
		<thead class="thead-dark">
			<tr>
				<th class="text-center">Grado</th>
				<th class="text-center">Mansione</th>
				<th class="text-center">Nome</th>
				<th class="text-center">Cognome</th>
				<th class="text-center"></th>
			</tr>
		</thead>

		<tbody>
			<%	ArrayList<VigileDelFuocoBean> vigili=(ArrayList<VigileDelFuocoBean>)request.getAttribute("vigili");
				if(vigili==null){
					%>
			<h2>Non ci sono Vigili del fuoco disponibili</h2>
			<%} else{
				for (VigileDelFuocoBean vigile : vigili){	
				%>

			<tr>
				<td class="text-center"><img
					src="Grado/<%=vigile.getMansione().equals("Capo Squadra") && vigile.getGrado().equals("Esperto")?"EspertoCapoSquadra":vigile.getGrado() %>.png"
					title="<%=vigile.getGrado() %>"
					onerror="this.parentElement.innerHTML='Non disponibile';"></td>
				<td class="text-center"><%=vigile.getMansione() %></td>
				<td class="text-center"><strong><%=vigile.getNome() %></strong></td>
				<td class="text-center"><strong><%=vigile.getCognome() %></strong></td>
				<td class="text-center"><input type="radio"
					onClick='attivapulsante()' value="<%=vigile.getEmail() %>"
					name="VFNew"></td>
			</tr>
			<% } }
					%>
		</tbody>
	</table>

	<% String email = (String) request.getParameter("email"); 
	String tipo = (String) request.getParameter("tiposquadra");
	Date data = (Date) request.getAttribute("dataModifica");
	Date other = (Date) request.getAttribute("altroturno");%>
	<input type="hidden" name="email" value="<%=email%>"> <input
		type="hidden" name="tiposquadra" value="<%=tipo%>"> <input
		type="hidden" name="dataModifica" value="<%=data%>"> <input
		type="hidden" name="altroturno" value="<%=other%>">
</div>