<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	import="java.util.ArrayList,java.util.Iterator, model.bean.*, model.dao.*, java.sql.Date"%>


<div class="d-flex justify-content-center">
	<h2>Diurno</h2>
</div>

<h4 class="d-flex justify-content-center" style="color: #B60000;">Capi
	Squadra</h4>
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
			<%
				ArrayList<VigileDelFuocoBean> vigili = (ArrayList<VigileDelFuocoBean>) request.getAttribute("giorno");
				if (vigili == null) {
			%>
			<h2>Non ci sono Capi Squadra disponibili</h2>
			<%
				} else {
					for (VigileDelFuocoBean vigile : vigili) {
						if (vigile.getMansione().equals("Capo Squadra")) {
			%>

			<tr>
				<td class="text-center"><img
					src="Grado/<%=vigile.getMansione().equals("Capo Squadra") && vigile.getGrado().equals("Esperto")
								? "EspertoCapoSquadra"
								: vigile.getGrado()%>.png"
					title="<%=vigile.getGrado()%>"
					onerror="this.parentElement.innerHTML='Non disponibile';"></td>
				<td class="text-center"><%=vigile.getMansione()%></td>
				<td class="text-center"><strong><%=vigile.getNome()%></strong></td>
				<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
			</tr>
			<%
				}
					}
				}
			%>
		</tbody>
	</table>
</div>
<div class="d-flex justify-content-center">
	<h2></h2>
</div>

<h4 class="d-flex justify-content-center" style="color: #B60000;">Autisti</h4>
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
			<%
					vigili = (ArrayList<VigileDelFuocoBean>) request.getAttribute("giorno");
					if (vigili == null) {
				%>
			<h2>Non ci sono Autisti disponibili</h2>
			<%
					} else {
						for (VigileDelFuocoBean vigile : vigili) {
							if (vigile.getMansione().equals("Autista")) {
				%>

			<tr>
				<td class="text-center"><img
					src="Grado/<%=vigile.getMansione().equals("Capo Squadra") && vigile.getGrado().equals("Esperto")
								? "EspertoCapoSquadra"
								: vigile.getGrado()%>.png"
					title="<%=vigile.getGrado()%>"
					onerror="this.parentElement.innerHTML='Non disponibile';"></td>
				<td class="text-center"><%=vigile.getMansione()%></td>
				<td class="text-center"><strong><%=vigile.getNome()%></strong></td>
				<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
			</tr>
			<%
					}
						}
					}
				%>
		</tbody>
	</table>
</div>
<div class="d-flex justify-content-center">
	<h2></h2>
</div>

<h4 class="d-flex justify-content-center" style="color: #B60000;">Vigili</h4>
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
			<%
					vigili = (ArrayList<VigileDelFuocoBean>) request.getAttribute("giorno");
					if (vigili == null) {
				%>
			<h2>Non ci sono Autisti disponibili</h2>
			<%
					} else {
						for (VigileDelFuocoBean vigile : vigili) {
							if (vigile.getMansione().equals("Vigile")) {
				%>

			<tr>
				<td class="text-center"><img
					src="Grado/<%=vigile.getMansione().equals("Capo Squadra") && vigile.getGrado().equals("Esperto")
								? "EspertoCapoSquadra"
								: vigile.getGrado()%>.png"
					title="<%=vigile.getGrado()%>"
					onerror="this.parentElement.innerHTML='Non disponibile';"></td>
				<td class="text-center"><%=vigile.getMansione()%></td>
				<td class="text-center"><strong><%=vigile.getNome()%></strong></td>
				<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
			</tr>
			<%
					}
						}
					}
				%>
		</tbody>
	</table>
</div>
<h2 class="d-flex justify-content-center"></h2>
<div class="d-flex justify-content-center">
	<h2>Notturno</h2>
</div>
<div class="d-flex justify-content-center">
	<h2></h2>
</div>

<h4 class="d-flex justify-content-center" style="color: #B60000;">Capi
	Squadra</h4>
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
			<%
					vigili = (ArrayList<VigileDelFuocoBean>) request.getAttribute("notte");
					if (vigili == null) {
				%>
			<h2>Non ci sono Autisti disponibili</h2>
			<%
					} else {
						for (VigileDelFuocoBean vigile : vigili) {
							if (vigile.getMansione().equals("Capo Squadra")) {
				%>

			<tr>
				<td class="text-center"><img
					src="Grado/<%=vigile.getMansione().equals("Capo Squadra") && vigile.getGrado().equals("Esperto")
								? "EspertoCapoSquadra"
								: vigile.getGrado()%>.png"
					title="<%=vigile.getGrado()%>"
					onerror="this.parentElement.innerHTML='Non disponibile';"></td>
				<td class="text-center"><%=vigile.getMansione()%></td>
				<td class="text-center"><strong><%=vigile.getNome()%></strong></td>
				<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
			</tr>
			<%
					}
						}
					}
				%>
		</tbody>
	</table>
</div>

<div class="d-flex justify-content-center">
	<h2></h2>
</div>

<h4 class="d-flex justify-content-center" style="color: #B60000;">Autisti</h4>
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
			<%
					vigili = (ArrayList<VigileDelFuocoBean>) request.getAttribute("notte");
					if (vigili == null) {
				%>
			<h2>Non ci sono Autisti disponibili</h2>
			<%
					} else {
						for (VigileDelFuocoBean vigile : vigili) {
							if (vigile.getMansione().equals("Autista")) {
				%>

			<tr>
				<td class="text-center"><img
					src="Grado/<%=vigile.getMansione().equals("Capo Squadra") && vigile.getGrado().equals("Esperto")
								? "EspertoCapoSquadra"
								: vigile.getGrado()%>.png"
					title="<%=vigile.getGrado()%>"
					onerror="this.parentElement.innerHTML='Non disponibile';"></td>
				<td class="text-center"><%=vigile.getMansione()%></td>
				<td class="text-center"><strong><%=vigile.getNome()%></strong></td>
				<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
			</tr>
			<%
					}
						}
					}
				%>
		</tbody>
	</table>
</div>
<div class="d-flex justify-content-center">
	<h2></h2>
</div>

<h4 class="d-flex justify-content-center" style="color: #B60000;">Vigili</h4>
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
			<%
					vigili = (ArrayList<VigileDelFuocoBean>) request.getAttribute("notte");
					if (vigili == null) {
				%>
			<h2>Non ci sono Autisti disponibili</h2>
			<%
					} else {
						for (VigileDelFuocoBean vigile : vigili) {
							if (vigile.getMansione().equals("Vigile")) {
				%>

			<tr>
				<td class="text-center"><img
					src="Grado/<%=vigile.getMansione().equals("Capo Squadra") && vigile.getGrado().equals("Esperto")
								? "EspertoCapoSquadra"
								: vigile.getGrado()%>.png"
					title="<%=vigile.getGrado()%>"
					onerror="this.parentElement.innerHTML='Non disponibile';"></td>
				<td class="text-center"><%=vigile.getMansione()%></td>
				<td class="text-center"><strong><%=vigile.getNome()%></strong></td>
				<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
			</tr>
			<%
					}
						}
					}
				%>
		</tbody>
	</table>
</div>
