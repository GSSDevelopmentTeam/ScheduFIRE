<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, model.bean.*, model.dao.*"%>

<html>
	<jsp:include page="StandardJSP.jsp" />
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="JS/datePicker.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
	<script src="https://buttons.github.io/buttons.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
	
	<body>
	
	
	
	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />
	<h2 class="d-flex justify-content-center" style="color:#B60000!Important">Gestione Malattie</h2>
	
	
	
	<!-- Modal di aggiunta malattia-->
	<div class="modal fade" id="aggiungiMalattia" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true" style="display: none">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content contenutiModal">
				<div class="modal-header">
					<h5 class="modal-title" id="titoloAggiuntaMalattia"
						>Aggiunta malattia</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p type="hidden" hidden="hidden" name="email" id="emailAggiuntaMalattia"></p>
					<div class=" row justify-content-center">
						<input id="dataInizio" placeholder="Giorno iniziale" size="34" /> 
						<input id="dataFine" placeholder="Giorno finale" size="34" />
					</div>
					
				<div class="text-center" id="messaggioMalattia1"></div>
				<div class="text-center" id="messaggioMalattia2"></div>
				
				</div>

				<div class="modal-footer">
	  			<button type="button" class="btn btn-outline-warning" onClick = "inserisciMalattia()">Aggiungi Malattia</button>
					<button type="button" class="btn btn-outline-danger" data-dismiss="modal">Annulla</button>

				</div>
			</div>
		</div>
	</div>
	
	
	<!-- Modal di avviso aggiunta malattia-->
 <div class="modal fade " id="menuConferma" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-sm modal-dialog-centered" role="document">
    <div class="modal-content">

      <div class="modal-body">
        <img src="IMG/fire.png" class="rounded mx-auto d-block">
        <h4 class="modal-title text-center">Sei sicuro?</h4>
        <p class="text-center">Vuoi inserire questo il periodo di malattia?<br> La procedura non può essere annullata.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-outline-danger" data-dismiss="modal">Annulla</button>
        <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">inserisci</button>
      </div>
    </div>
  </div>
</div>
	
	
	
	
	<!-- body aggiungi malattia -->
	<div class="table-responsive">
		<table class="table  table-hover" id="listaVigili">
			<thead class="thead-dark">
				<tr>
					<th class="text-center">Grado</th>
					<th class="text-center">Nome</th>
					<th class="text-center">Cognome</th>
					<th class="text-center">Email</th>
					<th class="text-center">Mansione</th>
					<th class="text-center">Inserisci Malattia</th>
				</tr>
			</thead>
				
				<tbody>
				 <%	ArrayList<VigileDelFuocoBean> listaVigili;
						listaVigili= (ArrayList<VigileDelFuocoBean>) request.getAttribute("listaVigili");
						
						for(int i=0; i<listaVigili.size(); i++){
							VigileDelFuocoBean vigile = listaVigili.get(i);
					%>
					
					<tr>
						<td class="text-center"><img src="Grado/<%=vigile.getGrado() %>.png" style="height:25%" onerror="this.parentElement.innerHTML='Non disponibile';"></td>
						<td class="text-center"><%=vigile.getNome() %></td>
						<td class="text-center"><%=vigile.getCognome()%></td>
						<td class="text-center"><%=vigile.getEmail() %></td>
						<td class="text-center"><%=vigile.getMansione()%></td>
						<td class="text-center"><button class="pass btn btn-outline-secondary" 
						data-toggle="modal" data-target="#aggiungiMalattia"
						onClick='apriFormAggiunta("<%=vigile.getEmail()%>")'>Aggiungi Malattia</button></td>
					</tr>
		
<% } %> 				
				
				</tbody>
			
			</table>
		</div>

		
			<script>
		
			
			var picker = new Litepicker(
					{
						element : document.getElementById('dataInizio'),
						elementEnd : document.getElementById('dataFine'),
						singleMode : false,
						format : 'DD-MM-YYYY',
						lang : 'it',
						numberOfMonths : 1,
						numberOfColumns : 1,
						inlineMode : true,
						minDate : new Date(),
						disallowLockDaysInRange : true,
						showTooltip:false,
						onError : function(error) {
							alertInsuccesso("Nel periodo selezionato risultano già dei giorni di malattia");
						}
					});
				
			
			
			function apriFormAggiunta(input) {
				console.log("parte funzione apriformAggiunta di " + input);
				picker.setOptions({
					startDate : null,
					endDate : null
				});
				$("#dataInizio").val("");
				$("#dataFine").val("");
				picker.setLockDays([]);
				
				$.ajax({
					type : "POST",
					url : "PeriodiDiMalattiaServlet",
					data : {
						"visMalattia" : true,
						"JSON" : true,
						"emailVF" : input,
					},
					dataType : "json",
					async : false,
					success : function(response) {
						picker.setLockDays(response);
               
        				var mail = document.getElementById("emailAggiuntaMalattia");
        				mail.innerHTML = input;
        				
        				var cognome = $(".table td:contains('" + input + "')").prev('td');
        				var nome = $(cognome).prev('td');
        			
        				
       
        				/*$("#titoloAggiuntaMalattia").text(
        						"Aggiunta malattia per " + nome.text() + " "+ cognome.text());
						
						$('#formAggiunta').show();
						
						$(".contenutiModal").css('background-color', '#e6e6e6');*/
					}
					
				});
			}
			
			</script>
			
			<script>
			function inserisciMalattia(){
				 var emailVF = document.getElementById("emailAggiuntaMalattia").innerHTML;
				 var dataIn = $('#dataInizio').val();
				 var dataFi = $('#dataFine').val();
				
				 //alert(emailVF + ""+dataIn+""+dataFi);
				 $.ajax({
				type : "POST",
				url : "AggiungiMalattiaServlet",
				data : {
					"inserisci": true, 
					"JSON" : true,
					"emailVF": emailVF,
					"dataInizio": dataIn,
					"dataFine" : dataFi
				},
						dataType : "json",
						async : false,
						
					});	
		}
				

			</script>
			
			<script>
			function apriModal(){
				$("#sese").attr("data-target","#menuConferma");
			}
			</script>
			
	</body>
</html>