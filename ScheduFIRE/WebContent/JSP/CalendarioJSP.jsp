<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>


<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link type="text/css" rel="stylesheet" href="CSS/CalendarioCSS.css"></link>
<link rel="stylesheet" href="CSS/NavBarCSS.css">
<link rel="icon" href="../IMG/logoSF.png">

<title>ScheduFIRE</title>
<%
	String[] days = { "Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica" };
	String[] month = { "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto",
			"Settembre", "Ottobre", "Novembre", "Dicembre" };
	int giorno = Integer.parseInt((String) request.getAttribute("giorno"));
	int mese = Integer.parseInt((String) request.getAttribute("mese"));
	int anno = Integer.parseInt((String) request.getAttribute("anno"));
	String mese_stringa = (String) request.getAttribute("meseStringa");
	int[] days_month = (int[]) request.getAttribute("days_month");
	String empty = " ";

	//print per controllare se i dati passati dalla servlet sono giusti!
	System.out.println("CalendarioJSP -> " + giorno + "/" + mese + "/" + anno + " -- " + mese_stringa);
%>
</head>
<body>
	<!-- Barra Navigazione -->
	<%@ include file="HeaderJSP.jsp"%>

	<div class="container">
		<div class="container-calendar">
			<div class="container-month-year">
				<%=mese_stringa%>
				<%=anno%>
			</div>
			<div class="grid-chose-month">
				<div class="dropdown">
					<button class="dropbtn">Scegli un altro mese</button>
					<div class="dropdown-content">
						<%
							for (int k = 0; k <= 11; k++) {
						%>
						<a href="#"><%=month[k]%></a>
						<%
							}
						%>
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
					for (int i = 0; i < days_month.length; i++) {
						if (days_month[i] <= 0) {
				%>
				<div class="grid-item"><%=empty%></div>
				<%
					} else {
				%>
				<div class="grid-item" onClick="dayClicked()"><%=days_month[i]%></div>
				<%
					}
					}
				%>


			</div>
		</div>
		<div class="container-schedul"></div>


	</div>
</body>
</html>