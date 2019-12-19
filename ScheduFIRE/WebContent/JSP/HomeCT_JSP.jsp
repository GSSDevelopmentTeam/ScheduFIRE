<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ScheduFIRE</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link type="text/css" rel="stylesheet" href="../CSS/CTHomeCSS.css">
<link rel="stylesheet" href="../CSS/HeaderCSS.css">
<link rel="icon" href="../IMG/logoSF.png">
</head>
<body>

<!-- Barra Navigazione -->
	<%@ include file="HeaderJSP.jsp"%>
	
<div class="rw" style="margin:50px;">
  <div class="clm">
    <button class="btop"><img src="../Icon/CavallettoColorato.png"><span class="rht">Gestione <br> Squadra</span> </button></div>
    <div class="clm" >
	<button class="btop"><img src="../Icon/calendarioColori.png"> <span class="rht"> Visualizza <br> Calendario</span></button></div>
	<div class="clm">
	<button class="btop"><img src="../Icon/MalattieColore.png" >  <span class="rht">Gestione<br> Malattia</span></button></div>

 
  <div class="clm" >
   	<button class="btop"><img src="../Icon/solecolore.png">  <span class="rht">Gestione<br> Ferie</span></button></div>
   	<div class="clm">
	<button class="btop"><img src="../Icon/ominoVF.png"	>  <span class="rht">Gestione<br> Personale</span></button></div>
	 	<div class="clm">
	<button class="btop"><img src="../Icon/ominiVF.png"	> <span class="rht"> Personale<br> Disponibile</button>
  </div>
</div>

</body>
</html>