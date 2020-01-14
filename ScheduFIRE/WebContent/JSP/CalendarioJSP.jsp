<%@page import="java.time.LocalDate"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.sql.Date"%>
<%@page import="util.GiornoLavorativo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>

<%@ page import="control.* "%>

<%
	String ruolo = "";
	ruolo = (String) session.getAttribute("ruolo");
%>
<!DOCTYPE html>
<html>
<head>

<%@ include file="StandardJSP.jsp"%>
<link type="text/css" rel="stylesheet" href="CSS/CalendarioCSS.css"></link>

<title>ScheduFIRE</title>
<%
	String empty = " ";
	String[] days = {"  Lunedi'  ", " Martedi'  ", "Mercoledi' ", " Giovedi'  ", " Venerdi'  ", "  Sabato  ",
			"   Domenica "};
	String[] month = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto",
			"Settembre", "Ottobre", "Novembre", "Dicembre"};
	//giorno, mese e anno correnti
	int giorno = (Integer) request.getAttribute("giorno");
	int mese = (Integer) request.getAttribute("mese");
	int anno = (Integer) request.getAttribute("anno");

	java.sql.Date data = (java.sql.Date) request.getAttribute("date");
	int primoGiorno = (Integer) request.getAttribute("primo_giorno");
	int len = (Integer) request.getAttribute("len");
	int anno_corrente = Integer.parseInt((String) request.getAttribute("anno_corrente"));
	int mese_corrente = Integer.parseInt((String) request.getAttribute("mese_corrente"));
	String mese_stringa = (String) request.getAttribute("meseStringa");
	int[] days_month = (int[]) request.getAttribute("days_month");
	int[] days_work = (int[]) request.getAttribute("days_work");
	String[] days_turno = (String[]) request.getAttribute("days_turno");
%>
</head>
<body>
	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />

	<h2 class="modal-title" style="color: #B60000 !Important; font-size:45px;"id="titolo">
	Calendario</h2>
	<br><br><br>
  
	<!-- START: Container per calendario e schedulazione -->
	<div class="containerAll" id="inizio">

		<!-- START: Container per il calendaio -->
		<div class="container-calendar">

			<!-- START: container per (<-) anno (->) -->
			<div class="container-year">
				<a class="altroAnno"
					href="CalendarioServlet?mese=<%=mese%>&anno=<%=anno - 1%>"> <img
					src="IMG/arrow/left-arrow-p.png" style="margin-right: 10px"
					onmouseover="this.src='IMG/arrow/left-arrow-d.png'"
					onmouseout="this.src='IMG/arrow/left-arrow-p.png'" />
				</a> <span id="annoVisualizzato"> <%=anno%>
				</span> <a class="altroAnno"
					href="CalendarioServlet?mese=<%=mese%>&anno=<%=anno + 1%>"> <img
					src="IMG/arrow/right-arrow-p.png" style="margin-left: 5px"
					onmouseover="this.src='IMG/arrow/right-arrow-d.png'"
					onmouseout="this.src='IMG/arrow/right-arrow-p.png'" />
				</a>
			</div>
			<!-- END: container per (<-) anno (->) -->

			<!-- START: container per la griglia dei mesi -->
			<div class="grid-chose-month">
				<div class="dropdown">
					<input type="hidden" id="meseVisualizzato" value="<%=mese%>">
					<button class="dropbtn" id="meseStringa"><%=month[mese - 1]%>
						<img src="IMG/arrow/dropdown.png" />
					</button>
					<div class="dropdown-content">
						<%
							for (int k = 0; k <= 11; k++) {
						%>
						<a class="dropdown-item"
							href="CalendarioServlet?mese=<%=k + 1%>&anno=<%=anno%>"><%=month[k]%></a>
						<%
							}
						%>
					</div>
				</div>
			</div>
			<!-- AND: container per la griglia dei mesi -->

			<!-- START: contailer dei giorni del mese -->
			<div class="grid-container">
				<%
					for (int j = 0; j <= 6; j++) {
				%>
				<div class="grid-days"><%=days[j]%></div>
				<%
					}
					int day = 0;
					int i, j;
					String id = "";
					String img = "";
					String onClick = "";

					
					for (i = 0; i< days_month.length-len; i++) {
						if (days_month[i] < 0) {
				%>
				<div class="item-empty"><%=empty%></div>
				<%
					} else{
							day++;

							if (giorno == day && mese_corrente == mese && anno_corrente == anno) {
								id = "giornoCorrente";
							}

							if (days_work[i] == 1) {
								id = "giornoLavorativoDiurno";
								img = "diurno.png";
								onClick = "dayClicked($(this).text())";
							}

							if (days_work[i] == 2) {
								id = "giornoLavorativoNotturno";
								img = "notturno.png";
								onClick = "dayClicked($(this).text())";
							}

							if (giorno == day && mese_corrente == mese && anno_corrente == anno && days_work[i] == 1) {
								id = "giornoCorrenteLavorativoDiurno";
								img = "diurno.png";
							}

							if (giorno == day && mese_corrente == mese && anno_corrente == anno && days_work[i] == 2) {
								id = "giornoCorrenteLavorativoNotturno";
								img = "notturno.png";
							}
				%>
				<div class="grid-item" id="<%=id%>" onClick="<%=onClick%>"
					style="cursor: pointer;">
					<img id="imgMoonSun" src="IMG/<%=img%>" alt=" "
						onerror="this.parentElement.innerHTML = '<%=day%>';" />
					<%=day%>

					<p id="turno"><%=days_turno[i]%></p>
				</div>

				<%
							id = "";
							img = "";
							onClick = "";
						}
					}
				%>

			</div>
			<!-- END: container dei giorni del mese -->

		</div>
		<!-- AND container per il calendario -->

		<div class="container-schedul" id="visilibity">
			<a class="info" id="informazione"></a>

			<div>


				<form action="GeneraSquadreServlet" method="POST">
					<button type="submit" id="bottoneGeneraSquadra" class="edit">
						Genera squadre <br> per il turno successivo
					</button>
				</form>

				<form id="modData" action="" method="POST">
					<button type="submit" id="bottoneModificaSquadra" class="edit">
						Modifica Squadre</button>
				</form>

			</div>

			<div class="wrapper" id="schedulazione">

				<div class="mansione">
					<p>SALA OPERATIVA</p>
				</div>
				<div class="vigili">

					<table id="SalaOperativa" class="table"></table>
				</div>


				<div class="mansione">
					<p>PRIMA PARTENZA</p>
				</div>
				<div class="vigili">

					<table id="PrimaPartenza" class="table"></table>
				</div>


				<div class="mansione">
					<p>AUTO SCALA</p>
				</div>
				<div class="vigili">

					<table id="AutoScala" class="table"></table>
				</div>


				<div class="mansione">
					<p>AUTO BOTTE</p>
				</div>
				<div class="vigili">

					<table id="AutoBotte" class="table"></table>
				</div>

			</div>
		</div>




	</div>
	<!-- AND: container per calendario e schedulazione -->

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js">
	</script>

	<!-- START: script per la funzione dayClicked() -->
	<script>
	<%if (request.getParameter("giorno")!=null){
	%>
	$( document ).ready(function() {
	    dayClicked(<%=request.getParameter("giorno")%>);
	    imgMoonSun();
	});
		
	<%
	}else if (GiornoLavorativo.isLavorativo(data)) {%>
		$( document ).ready(function() {
		    dayClicked(<%=giorno%>);
		    imgMoonSun();
		});
	<%}%>
	

		function dayClicked(input) {
			
		var v = document.getElementById('visilibity');
		v.style.display ='block';
		
		var schedulazione = document.getElementById('schedulazione');
		var bottoneGeneraSquadra = document.getElementById('bottoneGeneraSquadra');
		var bottoneModificaSquadra = document.getElementById('bottoneModificaSquadra');
		
		bottoneModificaSquadra.style.display ='none';
		bottoneGeneraSquadra.style.display ='none';

		var salaOperativa = $("#SalaOperativa");
		var primaPartenza = $("#PrimaPartenza");
		var autoScala = $("#AutoScala");
		var autoBotte = $("#AutoBotte");
		

		var giorno = input;
		if(giorno.toString().trim().indexOf("B")>0){
			var i = giorno.toString().trim().indexOf("B");
			giorno = giorno.toString().trim().substring(0,i);
		}
		var mese=$("#meseVisualizzato").val();
		var anno=$("#annoVisualizzato").text();
		
		var meseStringa = $("#meseStringa").text();
		
		$.ajax({
			type:"POST",
			url: "AjaxCalendario",
			data : {
				"giorno": giorno,
				"mese":mese,
				"anno":anno,
			},
			dataType: "json",
			async: true,
			success: function(response) {
								
				salaOperativa.empty();
				primaPartenza.empty();
				autoScala.empty();
				autoBotte.empty();

				var len = response.length;
				var isModificabile = response[0];
				var isGenerabile = response[1];	
				$("#informazione").text("Squadre relative al giorno "+giorno+"/ "+mese+" /"+anno);
	
				if(len<=2){
					$("#informazione").text("Non sono ancora state generate squadre per il  giorno "+giorno+"/ "+mese+" /"+anno);
					schedulazione.style.display ='none';
				}else{
					schedulazione.style.display ='block';
				}
					
				
				<%if(ruolo.equalsIgnoreCase("capoturno")){%>
				if(isModificabile){
					bottoneModificaSquadra.style.display ='block';
				}
				if(isGenerabile){
					bottoneGeneraSquadra.style.display ='block';
				}
				<%}%>
				
				for (var i = 2; i < len; i++) {
				vigile=response[i];
					
						var rigaTabella = document.createElement("TR");
						if(vigile.tipologia=="Sala Operativa"){
						  salaOperativa.append(rigaTabella);
						}
						else if(vigile.tipologia=="Prima Partenza"){
						  primaPartenza.append(rigaTabella);
						}
						else if(vigile.tipologia=="Auto Scala"){
							  autoScala.append(rigaTabella);
							}
						else if(vigile.tipologia=="Auto Botte"){
							  autoBotte.append(rigaTabella);
							}
						  var colonnaNome = document.createElement("TD");
						  var nome=document.createTextNode(vigile.nome);
						   colonnaNome.appendChild(nome);
						  rigaTabella.appendChild(colonnaNome);
						  
						  var colonnaCognome = document.createElement("TD");
						  var cognome=document.createTextNode(vigile.cognome);
						   colonnaCognome.appendChild(cognome);
						  rigaTabella.appendChild(colonnaCognome);
					
				}
				var red = 'ModificaComposizioneSquadreServlet?tiposquadra=3&data=' + anno.trim() + '-' + mese.trim() + '-' + giorno.trim();
				$("#modData").attr("action", red);

			} 
		});
	}
	</script>
	<!-- AND: script per la funzione dayClicked() -->

</body>
</html>