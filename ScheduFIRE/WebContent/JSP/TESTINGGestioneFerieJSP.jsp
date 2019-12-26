<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, model.bean.*, model.dao.*"%>

<html>
<head>
<jsp:include page="StandardJSP.jsp" />
<link rel="stylesheet" href="CSS/TableCSS.css">
<link rel="icon" type="image/png" sizes="192x192"
	href="https://wakirin.github.io/Litepicker/favicon-192x192.png">
<link rel="icon" type="image/png" sizes="128x128"
	href="https://wakirin.github.io/Litepicker/favicon-128x128.png">
<link rel="apple-touch-icon" sizes="180x180"
	href="https://wakirin.github.io/Litepicker/apple-touch-icon.png">
<link rel="icon" type="image/png" sizes="32x32"
	href="https://wakirin.github.io/Litepicker/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="https://wakirin.github.io/Litepicker/favicon-16x16.png">
<link rel="icon" type="image/x-icon"
	href="https://wakirin.github.io/Litepicker/favicon.ico">
<link href="https://wakirin.github.io/Litepicker/css/style.css"
	rel="stylesheet" />
<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap"
	rel="stylesheet">
</head>
<body>

	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />
	<h2>Gestione Ferie</h2>
	<div>
		<div>
			<div>
				<input id="dataInizio" /> <input id="dataFine" />
			</div>
		</div>
	</div>
	<table>
		<thead>
			<tr>
				<th>Grado</th>
				<th>Nome</th>
				<th>Cognome</th>
				<th>Email</th>
				<th>Mansione</th>
				<th>Ferie anno <br> corrente
				</th>
				<th>Ferie anno <br> precedente
				</th>
				<th>Inserisci <br> periodo di ferie
				</th>
				<th>Rimuovi <br>periodo di ferie
				</th>
			</tr>
		</thead>

		<tbody>
			<%	ArrayList<VigileDelFuocoBean> listaVigili;
						listaVigili= (ArrayList<VigileDelFuocoBean>) request.getAttribute("listaVigili");
						
						for(int i=0; i<listaVigili.size(); i++){
							VigileDelFuocoBean vigile = listaVigili.get(i);
					%>

			<tr>
				<td><%=vigile.getGrado() %></td>
				<td><%=vigile.getNome() %></td>
				<td><%=vigile.getCognome()%></td>
				<td><%=vigile.getEmail() %></td>
				<td><%=vigile.getMansione()%></td>
				<td><%=vigile.getGiorniFerieAnnoCorrente() %></td>
				<td><%=vigile.getGiorniFerieAnnoCorrente() %></td>
				<td><button type="aggiungi" onClick='apriFormAggiunta("<%=vigile.getEmail() %>")'>Aggiungi Ferie</button></td>
				<td><button type="rimuovi" src="">Rimuovi Ferie</button></td>
			</tr>

			<% } %>

		</tbody>

	</table>




	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script	src="https://cdn.jsdelivr.net/npm/litepicker@1.0.19/dist/js/main.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
	<script src="https://buttons.github.io/buttons.js"></script>

	<script>
		
	var picker = new Litepicker({ 
		element: document.getElementById('dataInizio') ,
		elementEnd: document.getElementById('dataFine'),
		singleMode:false,
		format:'DD-MM-YYYY',
		lang:'it',
		numberOfMonths:2,
		numberOfColumns:2,
		minDate:new Date(),
		disallowLockDaysInRange: true,
		tooltipText:{
			one: 'giorno',
			other: 'giorni',
		},
		onError: function(error) { alert("Nel periodo selezionato risultano già dei giorni di ferie"); },
		onHide: function() { 	
			var giornoLavorativo=moment("24/12/2019",'DD/MM/YYYY');
			var lavorativo=0;
			var inizio = moment(document.getElementById("dataInizio").value,'DD/MM/YYYY');
			var fine = moment(document.getElementById("dataFine").value,'DD/MM/YYYY');
			console.log(inizio);
			console.log(fine);

			while(inizio.diff(fine)<=0){
				var differenza=inizio.diff(giornoLavorativo,'days');
				var resto=differenza%4;
				if(resto==0 || resto==1)
					lavorativo++;
				
				inizio.add(1, 'days').format("DD");
			}
			alert("giorni di ferie effettivi: "+lavorativo+ "\n ToDo verifica che giorni di ferie presi non superino quelli disponibili");
			
		 }
		
		
		
		
	});
	
	picker.setLockDays(['2020-01-29']);
	
	
	function apriFormAggiunta(input){
		console.log("parte funzione apriformAggiunta di "+input);
		$.ajax({
			type:"POST",
			url: "GestioneFerieServlet",
			data : {
				"JSON":true,
				"email": input,
			},
			dataType: "json",
			async: false,
			success: function(response) {
				
				
				console.log(response);
				var len = response.length; 
				console.log(len);
				
			}
		});
		
		
	}
	
	
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