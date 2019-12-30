<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>

<%@ page import="control.* "%>

<%  String ruolo = (String) session.getAttribute("ruolo");%>
<!DOCTYPE html>
<html>
<head>

<%@ include file="StandardJSP.jsp"%>
<link type="text/css" rel="stylesheet" href="CSS/CalendarioCSS.css"></link>

<title>ScheduFIRE</title>
<%
	String modalita_uso = "  Cliccare su un giorno per visualizzare le squadre";
	String empty = " ";
	String vero = "true";
	String falso = "false";
	String editSquadre = "   Modifica squadre";
	String[] days = {"  Lunedì  ", " Martedì  ", "Mercoledi ", " Giovedì  ", " Venerdì  ", "  Sabato  ", "   Domenica "};
	String[] month = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto",
			"Settembre", "Ottobre", "Novembre", "Dicembre"};
	int giorno = (Integer) request.getAttribute("giorno");
	int mese = (Integer) request.getAttribute("mese");
	int anno = (Integer) request.getAttribute("anno");
	int primoGiorno = (Integer) request.getAttribute("primo_giorno");
	int anno_corrente = Integer.parseInt ((String) request.getAttribute("anno_corrente"));
	int mese_corrente = Integer.parseInt ((String) request.getAttribute("mese_corrente"));
	String mese_stringa = (String) request.getAttribute("meseStringa");
	int[] days_month = (int[]) request.getAttribute("days_month");
	int[] days_work = (int[]) request.getAttribute("days_work");
	//ArrayList<String> sala_operativa = (ArrayList<String>) request.getAttribute("sala_operativa");
	//ArrayList<String> prima_partenza = (ArrayList<String>) request.getAttribute("prima_partenza");
	//ArrayList<String> autoscala = (ArrayList<String>) request.getAttribute("autoscala");
	//ArrayList<String> autobotte = (ArrayList<String>) request.getAttribute("autobotte");

	//print per controllare se i dati passati dalla servlet sono giusti!
	System.out.println("CalendarioJSP -> " + giorno + "/" + mese + "/" + anno + " -- " + mese_stringa);
%>
</head>
<body>
	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />
	<div class="container">
		<div class="container-calendar">

			<!-- Accesso effettuato dal capoturno -->
			<%//if(ruolo.equalsIgnoreCase("capoturno")){%>
			
			<a href="#" class="edit" ><img src="IMG/edit.png" /><%=editSquadre%></a>
			
			<%//} %>

			<div class="container-year">
				<a class="altroAnno"
					href="CalendarioServlet?mese=<%=mese %>&anno=<%=anno-1 %>">
					<img src="IMG/arrow/left-arrow-empty.png"
					onmouseover="this.src='IMG/arrow/left-arrow-full.png'"
					onmouseout="this.src='IMG/arrow/left-arrow-empty.png'" />
				</a>
				<span id="annoVisualizzato"><%=anno%></span>
				<a class="altroAnno"
					href="CalendarioServlet?mese=<%=mese %>&anno=<%=anno+1 %>">
					<img src="IMG/arrow/right-arrow-empty.png"
					onmouseover="this.src='IMG/arrow/right-arrow-full.png'"
					onmouseout="this.src='IMG/arrow/right-arrow-empty.png'" />
				</a>
			</div>

			<div class="grid-chose-month">
				<div class="dropdown">
				<input type="hidden" id="meseVisualizzato" value="<%=mese%>">
					<button class="dropbtn"><%=month[mese-1]%>
						<img src="IMG/arrow/arrow-down.png" />
					</button>
					<div class="dropdown-content">
						<%
							for (int k = 0; k <= 11; k++) {
						%>
						<a class="dropdown-item"
							href="CalendarioServlet?mese=<%=k+1 %>&anno=<%=anno%>"><%=month[k]%></a>
						<%	}	%>
					</div>
				</div>
			</div>

			<div class="grid-container">
				<%
					for (int j = 0; j <= 6; j++) {
				%>
				<div class="grid-days"><%=days[j]%></div>
				<%
					}
					int day = 0;
					int i = 0;
					String id = "";
					String img = "";
					for (i=0; i < days_month.length; i++) {
						if (days_month[i] < 0){
							%>
							<div class="item-empty"><%=empty%></div>
							<%
					} else
						{
							day++;
						
							if(giorno==day && mese_corrente == mese && anno_corrente == anno){
								id ="giornoCorrente";
							}
							if (days_work[i]==1){
								id = "giornoLavorativoDiurno";
								img = "diurno";
							}
							if(days_work[i]==2){
								id = "giornoLavorativoNotturno";
								img = "notturno";
							}
							if(giorno==day && mese_corrente == mese && anno_corrente == anno && days_work[i]==1){
								id = "giornoCorrenteLavorativo";
							}
							
							
							%>
							<div class="grid-item" id="<%=id%>" onClick="dayClicked(this)">

							<img src="IMG/<%=img%>.png" alt=" "/>
							<%=day%>
							</div>

							<%
							id = "";
							img = "";
						}
					}
				%>

			</div>


		</div>

		<a> <%=modalita_uso%></a>
		<div class="container-schedul">


			<div class="wrapper">
				<div class="box mansione">
					<p>SALA OPERATIVA
					<p>
				</div>
				<div class="vigili">
					<table id="SalaOperativa"></table>
				</div>


				<div class=" box mansione">
					<p>PRIMA PARTENZA</p>

				</div>
				<div class="vigili">
					<table id="PrimaPartenza"></table>
				</div>


				<div class="box mansione">
					<p>AUTO SCALA</p>
				</div>
				<div class="vigili">
					<table id="AutoScala"></table>
				</div>


				<div class="box mansione">
					<p>AUTO BOTTE</p>
				</div>
				<div class="vigili">
					<table id="AutoBotte"></table>
				</div>

			</div>

		</div>

	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

	<script>
	
		function setValore(input){
			console.log(input);
		}
		
		

		function dayClicked(input) {
		console.log("parte funzione dayClicked()");

		var salaOperativa = $("#SalaOperativa");
		var primaPartenza = $("#PrimaPartenza");
		var autoScala = $("#AutoScala");
		var autoBotte = $("#AutoBotte");

		var giorno = $(input).text();
		var mese=$("#meseVisualizzato").val();
		var anno=$("#annoVisualizzato").text();
		console.log("parametri passati");
		console.log(giorno+" mese: "+mese+" anno: "+anno);
		
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
				
				
				console.log(response);
				var len = response.length; 
				console.log(len);
				for (var i = 0; i < len; i++) {
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
			}
		});
	}

	</script>
	
</body>
</html>