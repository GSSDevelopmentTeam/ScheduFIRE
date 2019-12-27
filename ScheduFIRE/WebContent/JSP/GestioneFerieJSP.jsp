<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import = "java.util.ArrayList, model.bean.*, model.dao.*" %>

<html>
<head>
<jsp:include page="StandardJSP.jsp" />
<link rel="stylesheet" href="CSS/TableCSS.css">
<link href="https://wakirin.github.io/Litepicker/css/style.css"
	rel="stylesheet" />
</head>
<body>

	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />
	<h2>Gestione Ferie</h2>
	
	
	
	<!-- da rendere popup come per pass dimenticata in login 
		deve partire nascosto e attivarsi al click di uno degli aggiungiFerie.
		Non rimuovete o cambiate codice , chiedete prima di agire se avete dubbi
					NON CAMBIARE GLI ID
	-->
	<div id="formAggiunta">
		<div>
			<div>
				<input type="hidden" name="email" id="emailHidden"> 
				<input id="dataInizio" readonly/> 
				<input id="dataFine" readonly/>
				<button>Aggiungi ferie</button>
				<button>Annulla</button>
					
			</div>
		</div>
	</div>
	<!-- tutto il codice qui sopra è un popup 
	 			NON CAMBIARE GLI ID
	 -->
	
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
			<%
				ArrayList<VigileDelFuocoBean> listaVigili;
				listaVigili = (ArrayList<VigileDelFuocoBean>) request.getAttribute("listaVigili");

				for (int i = 0; i < listaVigili.size(); i++) {
					VigileDelFuocoBean vigile = listaVigili.get(i);
			%>

			<tr>
				<td><%=vigile.getGrado()%></td>
				<td><%=vigile.getNome()%></td>
				<td><%=vigile.getCognome()%></td>
				<td><%=vigile.getEmail()%></td>
				<td><%=vigile.getMansione()%></td>
				<td><%=vigile.getGiorniFerieAnnoCorrente()%></td>
				<td><%=vigile.getGiorniFerieAnnoCorrente()%></td>
				<td><button type="aggiungi"
						onClick='apriFormAggiunta("<%=vigile.getEmail()%>")'>Aggiungi
						Ferie</button></td>
				<td><button type="rimuovi" src="">Rimuovi Ferie</button></td>
			</tr>

			<%
				}
			%>

		</tbody>

	</table>




	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="JS/datePicker.js"></script>
	<script type="text/javascript"src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
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
			if($("#dataInizio").val()!=""){
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
		 }
		
		
		
		
	});
	
	picker.setLockDays(['2020-01-29']);
	
	
	function apriFormAggiunta(input){
		console.log("parte funzione apriformAggiunta di "+input);
		picker.setOptions({startDate:null,endDate:null});
		$("#dataInizio").val("");
		$("#dataFine").val("");
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
				picker.setLockDays(response);
				$("#emailHidden").val(input)
				console.log("settati giorni di ferie: " +response);
			}
		});
		
		
	}
	
	</script>
</body>
</html>