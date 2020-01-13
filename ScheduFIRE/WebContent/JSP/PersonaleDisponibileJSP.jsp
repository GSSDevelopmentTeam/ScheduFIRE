<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	import="java.util.ArrayList,java.util.Iterator, model.bean.*, model.dao.*"%>
<!DOCTYPE html>
<html>
<jsp:include page="StandardJSP.jsp" />

<style>
.table td, .table th {
	padding: 1.5px !important;
	vertical-align: top;
	border-top: 1px solid #dee2e6;
}

.container__days {
	min-width: 270px;
}

.month-item-weekdays-row {
	min-width: 265px;
}

.back-up {
	border: none;
	background: none;
	position: fixed;
	bottom: 5%;
	right: 5%;
}
</style>
<body>
	<div id="sali"></div>
	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />
	<a href="#sali" class=" back-up"><img
		src="IMG/arrow/up-arrow-p.png" style="margin-left: 5px;"
		onmouseover="this.src='IMG/arrow/up-arrow-d.png'"
		onmouseout="this.src='IMG/arrow/up-arrow-p.png'" /></a>

	<section>

		<h2 class="d-flex justify-content-center"
			style="color: #B60000 !Important; margin-top: 3%; font-size: 45px;">Personale
			Disponibile</h2>

		<h5 class="d-flex justify-content-center"><%=request.getAttribute("titolo") == null ? "" : request.getAttribute("titolo")%></h5>


		<!-- form per l'ordinamento della lista dei VF-->
		<%
			String ordinamento = (String) request.getAttribute("ordinamento");
		%>
		<form action="./PersonaleDisponibile" method="POST">
			<input type="hidden" name=data
				value="<%=request.getAttribute("data") != null ? (String) request.getAttribute("data") : ""%>">
			<div align="center">
				<label>Ordinamento lista: &nbsp&nbsp</label> <select
					class="custom-select" name="ordinamento"
					onchange="this.form.submit()" style="width: 15%">

					<%
						if (ordinamento != null) {
							if (ordinamento.equals("nome")) {
					%>
					<option value="nome" selected>Nome</option>
					<option value="cognome">Cognome</option>
					<option value="disponibilita">Disponibilità</option>
					<option value="grado">Grado</option>


					<%
						} else if (ordinamento.equals("cognome")) {
					%>
					<option value="nome">Nome</option>
					<option value="cognome" selected>Cognome</option>
					<option value="disponibilita">Disponibilità</option>
					<option value="grado">Grado</option>


					<%
						} else if (ordinamento.equals("disponibilita")) {
					%>
					<option value="nome">Nome</option>
					<option value="cognome">Cognome</option>
					<option value="disponibilita" selected>Disponibilità</option>
					<option value="grado">Grado</option>



					<%
						} else if (ordinamento.equals("grado")) {
							%>
					<option value="nome">Nome</option>
					<option value="cognome">Cognome</option>
					<option value="disponibilita">Disponibilità</option>
					<option value="grado" selected>Grado</option>



					<%
						}
						} else {
					%>

					<option value="nome">Nome</option>
					<option value="cognome" selected>Cognome</option>
					<option value="disponibilita">Disponibilità</option>
					<option value="grado">Grado</option>


					<%
						}
					%>


				</select>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
				<button type="button" class="btn btn-outline-secondary"
					data-toggle="modal" data-target="#modalCalendario"
					onclick="resetGiorno()">Scegli un altro giorno</button>
			</div>
		</form>




		<!--  MODAL SCELTA GIORNO -->
		<!-- ----------------------- -->

		<br>
		<form action="./PersonaleDisponibile">

			<div class="modal fade" id="modalCalendario" tabindex="-1"
				role="dialog" aria-labelledby="exampleModalCenterTitle"
				aria-hidden="true" style="display: none">
				<div class="modal-dialog modal-dialog-centered" role="document">
					<div class="modal-content contenutiModal"
						style="min-width: 500px; min-height: 550px;">
						<div class="modal-header">
							<h5 class="modal-title" id="titoloRimuoviFerie">Scelta
								giorno</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times; </span>
							</button>
						</div>
						<div class="modal-body">
							<div class=" row justify-content-center">
								<input id="litepicker" name="data"
									placeholder="Scegli il giorno" readonly size="34"
									style="margin-bottom: 1%;" />
							</div>
							<div class="text-center" id="messaggioTurno"></div>



						</div>
						<div class="modal-footer">

							<button type="submit" class="btn btn-outline-success"
								id="bottoneConferma" disabled>Conferma</button>

							<button type="button" class="btn btn-outline-danger"
								data-dismiss="modal">Annulla</button>

						</div>
					</div>
				</div>
			</div>
		</form>











		<%
			if (!ordinamento.equals("disponibilita")) {
		%>

		<div class="table-responsive">

			<div id="capo"></div>
			<div class="d-flex justify-content-center">
				<a href="#auto" class="btn btn-outline-secondary"
					style="margin: 3px;">Autisti</a> <a href="#vigile"
					class="btn btn-outline-secondary" style="margin: 3px;">Vigili</a>
			</div>


			<h4 class="d-flex justify-content-center" id="inizio"
				style="margin-top: 0%; color: #B60000 !Important">Capi Squadra</h4>

			<table class="table  table-hover listaVigili"
				style="table-layout: fixed">
				<thead class="thead-dark">
					<tr>

						<th class="text-center">Grado</th>
						<th class="text-center">Nome</th>
						<th class="text-center">Cognome</th>
						<th class="text-center">Email</th>
						<th class="text-center">Disponibilità</th>
						<th class="text-center">Squadra</th>

					</tr>
				</thead>

				<tbody>
					<%
						ArrayList<VigileDelFuocoBean> vigiliCompleti;
							vigiliCompleti = (ArrayList<VigileDelFuocoBean>) request.getAttribute("vigiliCompleti");
							ArrayList<VigileDelFuocoBean> vigiliDisponibili = (ArrayList<VigileDelFuocoBean>) request
									.getAttribute("vigiliDisponibili");
							ArrayList<VigileDelFuocoBean> vigiliFerie = (ArrayList<VigileDelFuocoBean>) request
									.getAttribute("vigiliFerie");
							ArrayList<VigileDelFuocoBean> vigiliMalattia = (ArrayList<VigileDelFuocoBean>) request
									.getAttribute("vigiliMalattia");
							ArrayList<ComponenteDellaSquadraBean> componenti = (ArrayList<ComponenteDellaSquadraBean>) request
									.getAttribute("componenti");

							for (VigileDelFuocoBean vigile : vigiliCompleti) {

								if (vigile.getMansione().toUpperCase().equals("CAPO SQUADRA")) {
									String disponibilita = "NaN";
									if (vigiliDisponibili.contains(vigile))
										disponibilita = "Disponibile";
									else if (vigiliFerie.contains(vigile))
										disponibilita = "In ferie";
									else if (vigiliMalattia.contains(vigile))
										disponibilita = "In malattia";
									String squadra = "";
									for (ComponenteDellaSquadraBean componente : componenti) {
										if (componente.getEmailVF().equals(vigile.getEmail()))
											squadra = componente.getTipologiaSquadra();
									}
					%>

					<tr
						class="<%=!disponibilita.equals("Disponibile") ? "table-warning" : ""%>">
						<td class="text-center"><img
							src="Grado/<%=vigile.getMansione().equals("Capo Squadra") && vigile.getGrado().equals("Esperto")
								? "EspertoCapoSquadra"
								: vigile.getGrado()%>.png"
							title="<%=vigile.getGrado()%>"
							onerror="this.parentElement.innerHTML='Non disponibile';"></td>
						<td class="text-center"><strong><%=vigile.getNome()%></strong></td>
						<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
						<td class="text-center"><%=vigile.getEmail()%></td>
						<td class="text-center"><%=disponibilita%></td>
						<td class="text-center"><%=squadra%></td>

					</tr>

					<%
						}
							}
					%>

				</tbody>

			</table>

			<div id="auto"></div>
			<div class="d-flex justify-content-center">
				<a href="#capo" class="btn btn-outline-secondary"
					style="margin: 3px;">Capi Squadra</a> <a href="#vigile"
					class="btn btn-outline-secondary" style="margin: 3px;">Vigili</a>
			</div>

			<h4 class="d-flex justify-content-center" id="inizio"
				style="margin-top: 1%; color: #B60000 !Important">Autisti</h4>

			<table class="table  table-hover listaVigili"
				style="table-layout: fixed">
				<thead class="thead-dark">
					<tr>
						<th class="text-center">Grado</th>
						<th class="text-center">Nome</th>
						<th class="text-center">Cognome</th>
						<th class="text-center">Email</th>
						<th class="text-center">Disponibilità</th>
						<th class="text-center">Squadra</th>
					</tr>
				</thead>

				<tbody>
					<%
						for (VigileDelFuocoBean vigile : vigiliCompleti) {

								if (vigile.getMansione().toUpperCase().equals("AUTISTA")) {
									String disponibilita = "NaN";
									if (vigiliDisponibili.contains(vigile))
										disponibilita = "Disponibile";
									else if (vigiliFerie.contains(vigile))
										disponibilita = "In ferie";
									else if (vigiliMalattia.contains(vigile))
										disponibilita = "In malattia";
									String squadra = "";
									for (ComponenteDellaSquadraBean componente : componenti) {
										if (componente.getEmailVF().equals(vigile.getEmail()))
											squadra = componente.getTipologiaSquadra();
									}
					%>

					<tr
						class="<%=!disponibilita.equals("Disponibile") ? "table-warning" : ""%>">
						<td class="text-center"><img
							src="Grado/<%=vigile.getGrado()%>.png"
							title="<%=vigile.getGrado()%>"
							onerror="this.parentElement.innerHTML='Non disponibile';"></td>
						<td class="text-center"><strong><%=vigile.getNome()%></strong></td>
						<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
						<td class="text-center"><%=vigile.getEmail()%></td>
						<td class="text-center"><%=disponibilita%></td>
						<td class="text-center"><%=squadra%></td>

					</tr>

					<%
						}
							}
					%>

				</tbody>

			</table>

			<div id="vigile"></div>
			<div class="d-flex justify-content-center">
				<a href="#capo" class="btn btn-outline-secondary"
					style="margin: 3px;">Capi Squadra</a> <a href="#auto"
					class="btn btn-outline-secondary" style="margin: 3px;">Autisti</a>
			</div>
			<h4 class="d-flex justify-content-center" id="inizio"
				style="margin-top: 1%; color: #B60000 !Important">Vigili</h4>

			<table class="table  table-hover listaVigili"
				style="table-layout: fixed">
				<thead class="thead-dark">
					<tr>

						<th class="text-center">Grado</th>
						<th class="text-center">Nome</th>
						<th class="text-center">Cognome</th>
						<th class="text-center">Email</th>
						<th class="text-center">Disponibilità</th>
						<th class="text-center">Squadra</th>

					</tr>
				</thead>

				<tbody>
					<%
						for (VigileDelFuocoBean vigile : vigiliCompleti) {

								if (vigile.getMansione().toUpperCase().equals("VIGILE")) {
									String disponibilita = "NaN";
									if (vigiliDisponibili.contains(vigile))
										disponibilita = "Disponibile";
									else if (vigiliFerie.contains(vigile))
										disponibilita = "In ferie";
									else if (vigiliMalattia.contains(vigile))
										disponibilita = "In malattia";
									String squadra = "";
									for (ComponenteDellaSquadraBean componente : componenti) {
										if (componente.getEmailVF().equals(vigile.getEmail()))
											squadra = componente.getTipologiaSquadra();
									}
					%>

					<tr
						class="<%=!disponibilita.equals("Disponibile") ? "table-warning" : ""%>">
						<td class="text-center"><img
							src="Grado/<%=vigile.getGrado()%>.png"
							title="<%=vigile.getGrado()%>"
							onerror="this.parentElement.innerHTML='Non disponibile';"></td>
						<td class="text-center"><strong><%=vigile.getNome()%></strong></td>
						<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
						<td class="text-center"><%=vigile.getEmail()%></td>
						<td class="text-center"><%=disponibilita%></td>
						<td class="text-center"><%=squadra%></td>

					</tr>

					<%
						}
							}
					%>

				</tbody>

			</table>
		</div>
		<%
			} else if (ordinamento.equals("disponibilita")) {
				ArrayList<VigileDelFuocoBean> vigiliPerDisponibilita = new ArrayList<>();
				ArrayList<VigileDelFuocoBean> vigiliCompleti = (ArrayList<VigileDelFuocoBean>) request
						.getAttribute("vigiliCompleti");
				ArrayList<VigileDelFuocoBean> vigiliDisponibili = (ArrayList<VigileDelFuocoBean>) request
						.getAttribute("vigiliDisponibili");
				ArrayList<VigileDelFuocoBean> vigiliFerie = (ArrayList<VigileDelFuocoBean>) request
						.getAttribute("vigiliFerie");
				ArrayList<VigileDelFuocoBean> vigiliMalattia = (ArrayList<VigileDelFuocoBean>) request
						.getAttribute("vigiliMalattia");
				ArrayList<ComponenteDellaSquadraBean> componenti = (ArrayList<ComponenteDellaSquadraBean>) request
						.getAttribute("componenti");

				for (ComponenteDellaSquadraBean componente : componenti) {
					for (VigileDelFuocoBean vigile : vigiliCompleti) {
						if (componente.getEmailVF().equals(vigile.getEmail())) {
							vigiliPerDisponibilita.add(vigile);
							break;
						}

					}
				}

				for (VigileDelFuocoBean vigile : vigiliDisponibili) {
					if (!vigiliPerDisponibilita.contains(vigile))
						vigiliPerDisponibilita.add(vigile);
				}
				for (VigileDelFuocoBean vigile : vigiliFerie) {
					vigiliPerDisponibilita.add(vigile);
				}
				for (VigileDelFuocoBean vigile : vigiliMalattia) {
					vigiliPerDisponibilita.add(vigile);
				}
		%>



		<div class="table-responsive">

			<div id="capo"></div>
			<div class="d-flex justify-content-center">
				<a href="#auto" class="btn btn-outline-secondary"
					style="margin: 3px;">Autisti</a> <a href="#vigile"
					class="btn btn-outline-secondary" style="margin: 3px;">Vigili</a>
			</div>
			<h4 class="d-flex justify-content-center" id="inizio"
				style="margin-top: 0%; color: #B60000 !Important">Capi Squadra</h4>

			<table class="table  table-hover listaVigili"
				style="table-layout: fixed">
				<thead class="thead-dark">
					<tr>
						<th class="text-center">Grado</th>
						<th class="text-center">Nome</th>
						<th class="text-center">Cognome</th>
						<th class="text-center">Email</th>
						<th class="text-center">Disponibilità</th>
						<th class="text-center">Squadra</th>
					</tr>
				</thead>

				<tbody>
					<%
						for (VigileDelFuocoBean vigile : vigiliPerDisponibilita) {
								if (vigile.getMansione().toUpperCase().equals("CAPO SQUADRA")) {
									String disponibilita = "NaN";
									if (vigiliDisponibili.contains(vigile))
										disponibilita = "Disponibile";
									else if (vigiliFerie.contains(vigile))
										disponibilita = "In ferie";
									else if (vigiliMalattia.contains(vigile))
										disponibilita = "In malattia";
									String squadra = "";
									vigiliCompleti.remove(vigile);
									for (ComponenteDellaSquadraBean componente : componenti) {
										if (componente.getEmailVF().equals(vigile.getEmail()))
											squadra = componente.getTipologiaSquadra();
									}
					%>
					<tr
						class="<%=!disponibilita.equals("Disponibile") ? "table-warning" : ""%>">
						<td class="text-center"><img
							src="Grado/<%=vigile.getMansione().equals("Capo Squadra") && vigile.getGrado().equals("Esperto")
								? "EspertoCapoSquadra"
								: vigile.getGrado()%>.png"
							title="<%=vigile.getGrado()%>"
							onerror="this.parentElement.innerHTML='Non disponibile';"></td>
						<td class="text-center"><strong><%=vigile.getNome()%></strong></td>
						<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
						<td class="text-center"><%=vigile.getEmail()%></td>
						<td class="text-center"><%=disponibilita%></td>
						<td class="text-center"><%=squadra%></td>

					</tr>

					<%
						}

							}
					%>

				</tbody>

			</table>

			<div id="auto"></div>
			<div class="d-flex justify-content-center">
				<a href="#capo" class="btn btn-outline-secondary"
					style="margin: 3px;">Capi Squadra</a> <a href="#vigile"
					class="btn btn-outline-secondary" style="margin: 3px;">Vigili</a>
			</div>
			<h4 class="d-flex justify-content-center" id="inizio"
				style="margin-top: 1%; color: #B60000 !Important">Autisti</h4>

			<table class="table  table-hover listaVigili"
				style="table-layout: fixed">
				<thead class="thead-dark">
					<tr>
						<th class="text-center">Grado</th>
						<th class="text-center">Nome</th>
						<th class="text-center">Cognome</th>
						<th class="text-center">Email</th>
						<th class="text-center">Disponibilità</th>
						<th class="text-center">Squadra</th>
					</tr>
				</thead>

				<tbody>
					<%
						for (VigileDelFuocoBean vigile : vigiliPerDisponibilita) {

								if (vigile.getMansione().toUpperCase().equals("AUTISTA")) {
									String disponibilita = "NaN";
									if (vigiliDisponibili.contains(vigile))
										disponibilita = "Disponibile";
									else if (vigiliFerie.contains(vigile))
										disponibilita = "In ferie";
									else if (vigiliMalattia.contains(vigile))
										disponibilita = "In malattia";
									String squadra = "";
									for (ComponenteDellaSquadraBean componente : componenti) {
										if (componente.getEmailVF().equals(vigile.getEmail()))
											squadra = componente.getTipologiaSquadra();
									}
					%>

					<tr
						class="<%=!disponibilita.equals("Disponibile") ? "table-warning" : ""%>">
						<td class="text-center"><img
							src="Grado/<%=vigile.getGrado()%>.png"
							title="<%=vigile.getGrado()%>"
							onerror="this.parentElement.innerHTML='Non disponibile';"></td>
						<td class="text-center"><strong><%=vigile.getNome()%></strong></td>
						<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
						<td class="text-center"><%=vigile.getEmail()%></td>
						<td class="text-center"><%=disponibilita%></td>
						<td class="text-center"><%=squadra%></td>

					</tr>

					<%
						}
							}
					%>

				</tbody>

			</table>

			<div id="vigile"></div>
			<div class="d-flex justify-content-center">
				<a href="#capo" class="btn btn-outline-secondary"
					style="margin: 3px;">Capi Squadra</a> <a href="#auto"
					class="btn btn-outline-secondary" style="margin: 3px;">Autisti</a>
			</div>
			<h4 class="d-flex justify-content-center" id="inizio"
				style="margin-top: 1%; color: #B60000 !Important">Vigili</h4>

			<table class="table  table-hover listaVigili"
				style="table-layout: fixed">
				<thead class="thead-dark">
					<tr>
						<th class="text-center">Grado</th>
						<th class="text-center">Nome</th>
						<th class="text-center">Cognome</th>
						<th class="text-center">Email</th>
						<th class="text-center">Disponibilità</th>
						<th class="text-center">Squadra</th>
					</tr>
				</thead>

				<tbody>
					<%
						for (VigileDelFuocoBean vigile : vigiliPerDisponibilita) {

								if (vigile.getMansione().toUpperCase().equals("VIGILE")) {
									String disponibilita = "NaN";
									if (vigiliDisponibili.contains(vigile))
										disponibilita = "Disponibile";
									else if (vigiliFerie.contains(vigile))
										disponibilita = "In ferie";
									else if (vigiliMalattia.contains(vigile))
										disponibilita = "In malattia";
									String squadra = "";
									for (ComponenteDellaSquadraBean componente : componenti) {
										if (componente.getEmailVF().equals(vigile.getEmail()))
											squadra = componente.getTipologiaSquadra();
									}
					%>

					<tr
						class="<%=!disponibilita.equals("Disponibile") ? "table-warning" : ""%>">
						<td class="text-center"><img
							src="Grado/<%=vigile.getGrado()%>.png"
							title="<%=vigile.getGrado()%>"
							onerror="this.parentElement.innerHTML='Non disponibile';"></td>
						<td class="text-center"><strong><%=vigile.getNome()%></strong></td>
						<td class="text-center"><strong><%=vigile.getCognome()%></strong></td>
						<td class="text-center"><%=vigile.getEmail()%></td>
						<td class="text-center"><%=disponibilita%></td>
						<td class="text-center"><%=squadra%></td>

					</tr>

					<%
						}
							}
					%>

				</tbody>

			</table>
		</div>







		<%
			}
		%>








	</section>




	<script>
	
	
	$(".contenutiModal").css('background-color', '#e6e6e6');

	
	
			var picker = new Litepicker({
				element : document.getElementById('litepicker'),
				inlineMode : true,
				singleMode : true,
				format : 'DD-MM-YYYY',
				lang : 'it',
				numberOfMonths : 1,
				numberOfColumns : 1,
				minDate : new Date(),
				onSelect : function(date1, date2) {
					console.log(date1, date2);
					$('#bottoneConferma').prop("disabled", false);
				},
				onChangeMonth(date, idx){
					var data=date;
					var mese=data.getMonth()+1;
					var anno=data.getFullYear();
					$.ajax({
						type : "POST",
						url : "PersonaleDisponibile",
						data : {
							"JSON" : true,
							"mese" : mese,
							"anno" : anno,
						},
						dataType : "json",
						async : false,
						success : function(response) {
							picker.setLockDays(response);
							
						}
				});
					
				}

			});
			
			function resetGiorno(){
				picker.setOptions({
					startDate : null
				});
				$("#litepicker").val("");
				var data=new Date();
				var mese=data.getMonth()+1;
				var anno=data.getFullYear();
				$.ajax({
					type : "POST",
					url : "PersonaleDisponibile",
					data : {
						"JSON" : true,
						"mese" : mese,
						"anno" : anno,
					},
					dataType : "json",
					async : false,
					success : function(response) {
						picker.setLockDays(response);
						
					}
			});
			};

		
		
		
	</script>
</body>
</html>