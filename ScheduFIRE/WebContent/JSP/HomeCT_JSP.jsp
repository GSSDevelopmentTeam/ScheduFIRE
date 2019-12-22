<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@ include file="StandardJSP.jsp" %>
<link type="text/css" rel="stylesheet" href="CSS/CTHomeCSS.css">
<body>

<!-- Barra Navigazione -->
	<jsp:include page="HeaderJSP.jsp" />
	
<div class="rw" style="margin:50px;">
  <div class="clm">
    <button class="btop"><img src="Icon/CavallettoColorato.png"><span class="rht">Gestione <br> Squadra</span> </button></div>
    <div class="clm" >
	<button class="btop"><img src="Icon/calendarioColori.png"> <span class="rht"> Visualizza <br> Calendario</span></button></div>
	<div class="clm">
	<button class="btop"><img src="Icon/MalattieColore.png" >  <span class="rht">Gestione<br> Malattia</span></button></div>

 
  <div class="clm" >
   	<button class="btop"><img src="Icon/solecolore.png">  <span class="rht">Gestione<br> Ferie</span></button></div>
   	<div class="clm">
	<button class="btop"><img src="Icon/ominoVF.png"	>  <span class="rht">Gestione<br> Personale</span></button></div>
	 	<div class="clm">
	<button class="btop"><img src="Icon/ominiVF.png"	> <span class="rht"> Personale<br> Disponibile</button>
  </div>
</div>

</body>
</html>