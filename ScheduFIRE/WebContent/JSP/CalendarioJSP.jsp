<%@page import="java.sql.Date"%>
<%@page import="util.GiornoLavorativo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>

<%@ page import="control.* "%>

<%  String ruolo = "";
		ruolo= (String) session.getAttribute("ruolo");%>
<!DOCTYPE html>
<html>
<head>

<%@ include file="StandardJSP.jsp"%>
<link type="text/css" rel="stylesheet" href="CSS/CalendarioCSS.css"></link>

<title>ScheduFIRE</title>
<%
	String empty = " ";
	String[] days = {"  Lunedi'  ", " Martedi'  ", "Mercoledi' ", " Giovedi'  ", " Venerdi'  ", "  Sabato  ", "   Domenica "};
	String[] month = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto",
			"Settembre", "Ottobre", "Novembre", "Dicembre"};
	//giorno, mese e anno correnti
	int giorno = (Integer) request.getAttribute("giorno");
	int mese = (Integer) request.getAttribute("mese");
	int anno = (Integer) request.getAttribute("anno");
	
	java.sql.Date data = (java.sql.Date) request.getAttribute("date");
	int primoGiorno = (Integer) request.getAttribute("primo_giorno");
	int anno_corrente = Integer.parseInt ((String) request.getAttribute("anno_corrente"));
	int mese_corrente = Integer.parseInt ((String) request.getAttribute("mese_corrente"));
	String mese_stringa = (String) request.getAttribute("meseStringa");
	int[] days_month = (int[]) request.getAttribute("days_month");
	int[] days_work = (int[]) request.getAttribute("days_work");
	String[] days_turno = (String[]) request.getAttribute("days_turno");

	//print per controllare se i dati passati dalla servlet sono giusti!
	System.out.println("CalendarioJSP, correnti-> " + giorno + "/" + mese + "/" + anno + " -- " + mese_stringa);
	
%>
</head>
<body>
	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />

	<!-- START: Container per calendario e schedulazione -->
	<div class="containerAll">

		<!-- START: Container per il calendaio -->
		<div class="container-calendar">

			<!-- START: container per (<-) anno (->) -->
			<div class="container-year">
				<a class="altroAnno"
					href="CalendarioServlet?mese=<%=mese %>&anno=<%=anno-1 %>"> <img
					src="IMG/arrow/left-arrow-p.png" style="margin-right: 10px"
					onmouseover="this.src='IMG/arrow/left-arrow-d.png'"
					onmouseout="this.src='IMG/arrow/left-arrow-p.png'" />
				</a> <span id="annoVisualizzato"> <%=anno%>
				</span> <a class="altroAnno"
					href="CalendarioServlet?mese=<%=mese %>&anno=<%=anno+1 %>"> <img
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
					<button class="dropbtn" id="meseStringa"><%=month[mese-1]%>
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
					int i,j;
					String id = "";
					String img = "";
					String onClick ="";
					
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
									onClick ="dayClicked($(this).text())";
								}
								
								if(days_work[i]==2){
									id = "giornoLavorativoNotturno";
									img = "notturno";
									onClick ="dayClicked($(this).text())";
								}
								
								if(giorno==day && mese_corrente == mese && anno_corrente == anno && days_work[i]==1){
									id = "giornoCorrenteLavorativoDiurno";
									img = "diurno";
								}
								
								if(giorno==day && mese_corrente == mese && anno_corrente == anno && days_work[i]==2){
									id = "giornoCorrenteLavorativoNotturno";
									img = "notturno";
								}							
								
								%>
								<div class="grid-item" id="<%=id%>" onClick="<%=onClick %>"
									style="cursor: pointer;">
									<img src="IMG/<%=img%>.png" alt=" "
										onerror="this.parentElement.innerHTML = '<%=day %>';" />
									<%=day%>
				
									<p id="turno"><%=days_turno[i] %></p>
								</div>

				<%
								id = "";
								img = "";
								onClick="";
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
					<%//if(ruolo.equalsIgnoreCase("capoturno")){%>
					
							<form action="GeneraSquadreServlet" method="post">
								<button type="submit" id="bottoneGeneraSquadra"
										class="edit">Genera Squadre
								</button>	
							</form>
						
						<form action="ModificaComposizioneSquadreServlet?tiposquadra=3" method="post">
							<button type="submit" id="bottoneModificaSquadra"
									class="edit">Modifica Squadre
							</button>
						</form>
					<%//} %>
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
	$( document ).ready(function() {
	    dayClicked(<%=giorno %>);
	});
	
	
		function setValore(input){
			console.log(input);
		}
		
		function dayClicked(input) {
		
		$("#informazione").text("");
			
		var v = document.getElementById('visilibity');
		v.style.display ='block';
			
		console.log("parte funzione dayClicked()");

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
				
				
				console.log("response "+response);
				var len = response.length; 
				console.log("len "+len);
				
				if(len>0){
					$("#informazione").text("Composizione della squadra del "+giorno+" "+meseStringa+" "+anno);
					
					var schedulazione = document.getElementById('schedulazione');
					schedulazione.style.display ='block';
					
					var generaSquadra = document.getElementById('bottoneGeneraSquadra');
					generaSquadra.style.display ='none';
					
					var generaSquadra = document.getElementById('bottoneModificaSquadra');
					generaSquadra.style.display ='block';
					
				}else{
					$("#informazione").text("Non sono presenti squadre per questo giorno");
					
					var schedulazione = document.getElementById('schedulazione');
					schedulazione.style.display ='none';
					
					var generaSquadra = document.getElementById('bottoneGeneraSquadra');
					generaSquadra.style.display ='block';
					
					var generaSquadra = document.getElementById('bottoneModificaSquadra');
					generaSquadra.style.display ='none';
				}
                    
					

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
	<!-- AND: script per la funzione dayClicked() -->

</body>
</html>