<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>


<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link type="text/css" rel="stylesheet" href="CSS/CalendarioCSS.css"></link>
<link rel="stylesheet" href="CSS/HeaderCSS.css">
<link rel="icon" href="../IMG/logoSF.png">

<title>ScheduFIRE</title>
<%
	String[] days = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
	String[] month = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto",
			"Settembre", "Ottobre", "Novembre", "Dicembre"};
	int giorno = Integer.parseInt((String) request.getAttribute("giorno"));
	int mese = Integer.parseInt((String) request.getAttribute("mese"));
	int anno = Integer.parseInt((String) request.getAttribute("anno"));
	String mese_stringa = (String) request.getAttribute("meseStringa");
	int[] days_month = (int[]) request.getAttribute("days_month");
	ArrayList<String> sala_operativa = (ArrayList<String>) request.getAttribute("sala_operativa");
	ArrayList<String> prima_partenza = (ArrayList<String>) request.getAttribute("prima_partenza");
	ArrayList<String> autoscala = (ArrayList<String>) request.getAttribute("autoscala");
	ArrayList<String> autobotte = (ArrayList<String>) request.getAttribute("autobotte");

	String empty = " ";

	//print per controllare se i dati passati dalla servlet sono giusti!
	System.out.println("CalendarioJSP -> " + giorno + "/" + mese + "/" + anno + " -- " + mese_stringa);
%>
</head>
<body>
	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />
	<div class="container">
		<div class="container-calendar">
			<div class="container-month-year" id="mese_anno">
				<%=mese_stringa%>
				<%=anno%>
			</div>

			<div class="grid-chose-month">
				<div class="dropdown">
					<button class="dropbtn">Scegli un altro mese</button>
					<div class="dropdown-content">

						<form method="post" action="CalendarioServlet">
							<input type="hidden" name="mese" value="1">
							<input type="submit" value="Gennaio"><br>
						</form>

						<form method="post" action="CalendarioServlet">
							<input type="hidden" name="mese" value="2">
							<input type="submit" value="Febbraio"><br>
						</form>

						<form method="post" action="CalendarioServlet">
							<input type="hidden" name="mese" value="3">
							<input type="submit" value="Marzo"><br>
						</form>

						<form method="post" action="CalendarioServlet">
							<input type="hidden" name="mese" value="4">
							<input type="submit" value="Aprile"><br>
						</form>

						<form method="post" action="CalendarioServlet">
							<input type="hidden" name="mese" value="5">
							<input type="submit" value="Maggio"><br>
						</form>

						<form method="post" action="CalendarioServlet">
							<input type="hidden" name="mese" value="6">
							<input type="submit" value="Giugno"><br>
						</form>

						<form method="post" action="CalendarioServlet">
							<input type="hidden" name="mese" value="7">
							<input type="submit" value="Luglio"><br>
						</form>

						<form method="post" action="CalendarioServlet">
							<input type="hidden" name="mese" value="8">
							<input type="submit" value="Agosto"><br>
						</form>

						<form method="post" action="CalendarioServlet">
							<input type="hidden" name="mese" value="9">
							<input type="submit" value="Settembre"><br>
						</form>

						<form method="post" action="CalendarioServlet">
							<input type="hidden" name="mese" value="10">
							<input type="submit" value="Ottobre"><br>
						</form>

						<form method="post" action="CalendarioServlet">
							<input type="hidden" name="mese" value="11">
							<input type="submit" value="Novembre"><br>
						</form>

						<form method="post" action="CalendarioServlet">
							<input type="hidden" name="mese" value="12">
							<input type="submit" value="Dicembre"><br>
						</form>

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
					for (int i = 0; i < days_month.length; i++) {
						if (days_month[i] < 0) {
				%>
				<div class="item-empty"><%=empty%></div>
				<%
					} else { day++;
					String classe = "";
					if(giorno==day){
						classe ="giornoCorrente";
					}
				%>

				<div class="grid-item" id=<%=classe%> onClick="dayClicked(this)"><%=day%></div>

				<%
					}
					}
				%>


			</div>
		</div>
		<div class="container-schedul">


			<div class="wrapper">
				<div class="box mansione">
					<p>SALA OPERATIVA<p>
				</div>
				<div class="vigili">
				<%for(String s : sala_operativa){%>
					<p><%=s%></p>
				<%} %>
          <table id="SalaOperativa"></table>
				</div>


				<div class=" box mansione">
					<p>PRIMA PARTENZA</p>

				</div>
				<div class="vigili">

				<%for(String s: prima_partenza){%>
					<p><%=s%></p>
				<%} %>
        <table id="PrimaPartenza"></table>
				</div>


				<div class="box mansione">
					<p>AUTO SCALA</p>

				</div>
				<div class="vigili">

				<%for(String s: autoscala){%>
					<p><%=s%></p>
				<%} %>
          <table id="AutoScala"></table>
				</div>


				<div class="box mansione">
					<p>AUTO BOTTE</p>
				</div>
				<div class="vigili">
				<%for(String s: autobotte){%>
					<p><%=s%></p>
          	<table id="AutoBotte"></table>
				<%} %>

				</div>

			</div>

		</div>


	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

	<script>
	
	
	

		function dayClicked(input) {
		console.log("parte funzione dayClicked()");

		var salaOperativa = $("#SalaOperativa");
		var primaPartenza = $("#PrimaPartenza");
		var autoScala = $("#AutoScala");
		var autoBotte = $("#AutoBotte");

		var giorno = $(input).text();
		
		$.ajax({
			type:"POST",
			url: "AjaxCalendario",
			data : {
				"giorno": giorno,
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