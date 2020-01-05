<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
    		import="java.util.ArrayList,java.util.Iterator, model.bean.*, model.dao.*" 
    		%>
<!DOCTYPE html>
<html>
<jsp:include page="StandardJSP.jsp" />


<body>
<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />
	

<section>
	
	<h2 class="d-flex justify-content-center" style="color:#B60000!Important">Personale Disponibile</h2>
	<h5 class="d-flex justify-content-center" ><%=request.getAttribute("titolo")==null ? "" : request.getAttribute("titolo") %></h5>
		<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili" >
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Email</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Squadra</th>
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
						<td class="text-center"><%=vigile.getEmail() %></td>
						<td class="text-center"><%=vigile.getMansione() %></td>
						<td class="text-center"><%=componente.getTipologiaSquadra() %></td>
					</tr>
					<% ; i.remove(); } } }
					for(VigileDelFuocoBean vigile: vigili){
					%>
					<tr>
						<td class="text-center"><img src="Grado/<%=vigile.getGrado() %>.png" style="height:25%" onerror="this.parentElement.innerHTML='Non disponibile';"></td>
						<td class="text-center"><%=vigile.getNome() %></td>
						<td class="text-center"><%=vigile.getCognome() %></td>
						<td class="text-center"><%=vigile.getEmail() %></td>
						<td class="text-center"><%=vigile.getMansione() %></td>
						<td class="text-center"></td>
					</tr>

					<% } %>
					
			</tbody>
		</table>
		</div>
	</section>
	</body>
</html>