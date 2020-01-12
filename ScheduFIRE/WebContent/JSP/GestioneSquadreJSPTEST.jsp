<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.bean.*, model.dao.*"%>
<!DOCTYPE html>
<html>
<jsp:include page="StandardJSP.jsp" />
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
.fr {
	height: 38px;
	width: 38px;
	float: left;
}

h2 {
	color: #B60000;
}
</style>
</head>

<body>

	<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />


	<!-- ELENCO SQUADRE  -->


	<h5>Sala operativa</h5>
	<%
	HashMap<VigileDelFuocoBean, String> squadraDiurno = (HashMap<VigileDelFuocoBean, String>) session.getAttribute("squadraDiurno");

	Iterator it = squadraDiurno.entrySet().iterator();
	while (it.hasNext()) {
        Map.Entry coppia = (Map.Entry) it.next();

        VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();

        if (coppia.getValue().equals("Sala Operativa")) {	

    %>
	<div><%=membro.getNome()%>
		,
		<%=membro.getCognome()%>
		,
		<%=membro.getMansione()%>,
		<%=membro.getEmail()%></div>
	<% 
	}  
    }%>


	<h5>Prima partenza</h5>

	<%it = squadraDiurno.entrySet().iterator();
	while (it.hasNext()) {
        Map.Entry coppia = (Map.Entry) it.next();
        VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();
        if (coppia.getValue().equals("Prima Partenza")) {	
    %>
	<div><%=membro.getNome()%>
		,
		<%=membro.getCognome()%>
		,
		<%=membro.getMansione()%>,
		<%=membro.getEmail()%></div>
	<% 
	}  
    }%>




	<h5>Auto Scala</h5>

	<%it = squadraDiurno.entrySet().iterator();
	while (it.hasNext()) {
        Map.Entry coppia = (Map.Entry) it.next();
        VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();
        if (coppia.getValue().equals("Auto Scala") || coppia.getValue().equals("Autoscala")) {	
    %>
	<div><%=membro.getNome()%>
		,
		<%=membro.getCognome()%>
		,
		<%=membro.getMansione()%>,
		<%=membro.getEmail()%></div>
	<% 

	}  
    }%>




	<h5>Auto Botte</h5>

	<%it = squadraDiurno.entrySet().iterator();
	while (it.hasNext()) {
        Map.Entry coppia = (Map.Entry) it.next();
        VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();
        if (coppia.getValue().equals("Auto Botte") || coppia.getValue().equals("Autobotte")) {	
    %>
	<div><%=membro.getNome()%>
		,
		<%=membro.getCognome()%>
		,
		<%=membro.getMansione()%>,
		<%=membro.getEmail()%></div>
	<% 
	}  
    }%>







	<h5>Sala operativa</h5>
	<%
	HashMap<VigileDelFuocoBean, String> squadraNotturno = (HashMap<VigileDelFuocoBean, String>) session.getAttribute("squadraNotturno");
	it = squadraNotturno.entrySet().iterator();
	while (it.hasNext()) {
        Map.Entry coppia = (Map.Entry) it.next();
        VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();
        if (coppia.getValue().equals("Sala Operativa")) {	
    %>
	<div><%=membro.getNome()%>
		,
		<%=membro.getCognome()%>
		,
		<%=membro.getMansione()%>,
		<%=membro.getEmail()%></div>
	<% 
	}  
    }%>


	<h5>Prima partenza</h5>

	<%it = squadraNotturno.entrySet().iterator();
	while (it.hasNext()) {
        Map.Entry coppia = (Map.Entry) it.next();
        VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();
        if (coppia.getValue().equals("Prima Partenza")) {	
    %>
	<div><%=membro.getNome()%>
		,
		<%=membro.getCognome()%>
		,
		<%=membro.getMansione()%>,
		<%=membro.getEmail()%></div>
	<% 
	}  
    }%>




	<h5>Auto Scala</h5>

	<%it = squadraNotturno.entrySet().iterator();
	while (it.hasNext()) {
        Map.Entry coppia = (Map.Entry) it.next();
        VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();
        if (coppia.getValue().equals("Auto Scala") || coppia.getValue().equals("Autoscala")) {	
    %>
	<div><%=membro.getNome()%>
		,
		<%=membro.getCognome()%>
		,
		<%=membro.getMansione()%>,
		<%=membro.getEmail()%></div>
	<% 
	}  
    }%>




	<h5>Auto Botte</h5>

	<%it = squadraNotturno.entrySet().iterator();
	while (it.hasNext()) {
        Map.Entry coppia = (Map.Entry) it.next();
        VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();
        if (coppia.getValue().equals("Auto Botte") || coppia.getValue().equals("Autobotte") ) {	
    %>
	<div><%=membro.getNome()%>
		,
		<%=membro.getCognome()%>
		,
		<%=membro.getMansione()%>,
		<%=membro.getEmail()%></div>
	<% 
	}  
    }%>



	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="JS/datePicker.js"></script>
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
	<script src="https://buttons.github.io/buttons.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>

	<script>

	function apriFormVF(input) {
		//Chiamata ajax alla servlet PersonaleDisponibileAJAX
		$.ajax({
			type : "POST",//Chiamata POST
			url : "PersonaleDisponibileAJAX",//url della servlet che devo chiamare
			data : {
				"JSON" : true,
				"aggiunta":true,
				"email" : input,
			},			
			success : function(response) {//Operazione da eseguire una volta terminata la chiamata alla servlet.
				$("#appendElenco").remove();
				$("<div id='appendElenco'></div>").appendTo("#elenco");
				$(response).appendTo("#appendElenco");					
			}
		});

	}

	</script>


</body>
</html>