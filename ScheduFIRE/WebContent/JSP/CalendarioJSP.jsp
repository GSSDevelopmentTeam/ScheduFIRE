<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>

<%@ page import="control.* "%>

<%  String ruolo = (String) session.getAttribute("ruolo");%>
<!DOCTYPE html>
<html>
<head>

<%@ include file = "StandardJSP.jsp" %>
<link type="text/css" rel="stylesheet" href="CSS/CalendarioCSS.css"></link>


<title>ScheduFIRE</title>
<%
	String modalita_uso = "Schiacchiare su un giorno per visualizzare le squadre";
	String empty = " ";
	String[] days = {"  Lunedì  ", " Martedì  ", "Mercoledì ", " Giovedì  ", " Venerdì  ", "  Sabato  ", "   Domenica "};
	String[] month = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto",
			"Settembre", "Ottobre", "Novembre", "Dicembre"};
	int giorno = (Integer) request.getAttribute("giorno");
	int mese = (Integer) request.getAttribute("mese");
	int anno = (Integer) request.getAttribute("anno");
	int anno_corrente = Integer.parseInt ((String) request.getAttribute("anno_corrente"));
	int mese_corrente = Integer.parseInt ((String) request.getAttribute("mese_corrente"));
	String mese_stringa = (String) request.getAttribute("meseStringa");
	int[] days_month = (int[]) request.getAttribute("days_month");
	ArrayList<String> sala_operativa = (ArrayList<String>) request.getAttribute("sala_operativa");
	ArrayList<String> prima_partenza = (ArrayList<String>) request.getAttribute("prima_partenza");
	ArrayList<String> autoscala = (ArrayList<String>) request.getAttribute("autoscala");
	ArrayList<String> autobotte = (ArrayList<String>) request.getAttribute("autobotte");

	//print per controllare se i dati passati dalla servlet sono giusti!
	System.out.println("CalendarioJSP -> " + giorno + "/" + mese + "/" + anno + " -- " + mese_stringa);
%>
</head>
<body>
	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />
	<div class="container">
		<div class="container-calendar">
			<div class="container-month-year">
				<a class="altroAnno" href="CalendarioServlet?mese=<%=mese %>&anno=<%=anno-1 %>"><img src="IMG/previous.png"/></a>
				<span id="annoVisualizzato"><%=anno%></span>
				<a class="altroAnno" href="CalendarioServlet?mese=<%=mese %>&anno=<%=anno+1 %>"><img src="IMG/next.png"/></a>
			</div>
			
			<div class="grid-chose-month">
				<div class="dropdown">
				<input type="hidden" id="meseVisualizzato" value="<%=mese %>">
					<button class="dropbtn"><%=month[mese-1]%>  <img src="IMG/arrow-down.png"/></button>
					<div class="dropdown-content">
							<%
							for (int k = 0; k <= 11; k++) {
						%>
						 <a class="dropdown-item" href="CalendarioServlet?mese=<%=k+1 %>&anno=<%=anno%>"><%=month[k]%></a>						
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
					for (int i = 0; i < days_month.length; i++) {
						if (days_month[i] < 0) {
				%>
				<div class="item-empty"><%=empty%></div>
				<%
					} else { day++;
					String classe = "";
					if(giorno==day && mese_corrente == mese && anno_corrente == anno){
						classe ="giornoCorrente";
					}
				%>

				<div class="grid-item" id="<%=classe%>" onClick="dayClicked(this)"><%=day%></div>

				<%
					}
					}
				%>


			</div>
					
			<!-- Accesso effettuato dal capoturno -->
			<%if(ruolo.equalsIgnoreCase("capoturno")){%>
			<div class="edit">
				<img src="IMG/edit.png"/><a href ="#">Modifica squadre</a><br>
			</div>
			<%} %>
		</div>
				
		<a><%=modalita_uso%></a>
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
				<%} %>
				<table id="AutoBotte"></table>
				</div>

			</div>

		</div>

	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

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