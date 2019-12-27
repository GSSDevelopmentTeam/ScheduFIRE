<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="java.util.*, model.bean.*, model.dao.*"%>

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
			<%
				List<VigileDelFuocoBean> squadra = (ArrayList<VigileDelFuocoBean>) request.getAttribute("squadra");
				for (VigileDelFuocoBean membro : squadra) {
			%>
			<tr>
				<td><%=membro.getGrado()%></td>
				<td><%=membro.getNome()%></td>
				<td><%=membro.getCognome()%></td>
				<td><%=membro.getEmail()%></td>
				<td><%=membro.getMansione()%></td>
				<td><%=%></td>
			</tr>
			<%
				}
			%>

		</tbody>
	</table>
	<%
		if ((boolean) request.getAttribute("nonSalvata")) {
			session.setAttribute("squadra", squadra);
	%>
	<form action="ModificaComposizioneSquadreServlet" method=post>
		<input type="Submit" value="Modifica" name="Modifica">
	</form>
	<form action="GeneraSquadreServlet" method=post>
		<input type="Submit" value="Conferma" name="Conferma">
	</form>
	<%
		}
	%>
</section>
