<%@ page import="control.* "%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.bean.*, model.dao.*,util.*"%>
<%
	String ruolo = (String) session.getAttribute("ruolo");	
%>

<script src="https:maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<div class="topnav" id="myTopnav">
<% if(ruolo!= null && ruolo.equalsIgnoreCase("capoturno")) {
%>
  <form action="HomeCTServlet" method="POST">
  <a><button class="round"><img src="IMG/logoScheduFIRE.png" class="logo">
  </button></a></form><%} else {
	  %>   <a><button class="round"><img src="IMG/logoScheduFIRE.png" class="logo"></button></a>
 <%} if(ruolo!= null){
	  %>	
 	<div class="rf">
 		
 		<% if(ruolo.equalsIgnoreCase("capoturno")){ 
 		
	Notifiche nt = (Notifiche) session.getAttribute("notifiche");
	List<Notifica> note = nt.getListaNotifiche();
	int dim = note.size();%>
 		<a><div class="dd" >
  <button type="button" class="dn" >
    <img src="IMG/notizia.png" style="height:50px; width:50px "><span class="badge"><%if (dim!=0)%><%=dim %></span>
  </button>
  <div class="ddc">
  <%for (Notifica n:note){ %>
    <form action="" method="POST">
  	<span class="bdgdel"><button tye="submit" class="nn" value="<%=n%>" name="notifica"><img src="IMG/delete.png" class="del"></button></span>
  </form>
  <form action="<%=n.getPath()%>" method="POST">
  	<button class="ntf <% if(n.getSeverita()==1){%>gr<%}else{ if(n.getSeverita()==2){%>yl<%}else{%>rd<%}}%>"><%=n.getTesto() %></button>
  </form> 
  <%} %> 
  </div>
</div></a>

<a><div class="dd">
  <button type="button" class="db" >
    <img src="IMG/men.png" style="height:50px; width:50px ">
  </button>
  <div class="ddc">
   <form action="GeneraSquadreServlet" method="POST">
    <button class="cmd"><img src="Icon/CavallettoColorato.png" class="btl"><span class="rtlg">Gestione Squadra</span> </button>
  </form>
  <form action="CalendarioServlet" method="POST">
	<button class="cmd"><img src="Icon/calendarioColori.png" class="btl"><span class="rtlg">Visualizza Calendario</span></button>
  </form>
  <form action="GestionePersonaleServlet" method="POST">
	<button class="cmd"><img src="Icon/ominoVF.png"	class="btl"><span class="rtlg">Gestione Personale</span></button>
  </form>
  <form action="GestioneFerieServlet" method="POST">
   	<button class="cmd"><img src="Icon/solecolore.png" class="btl"><span class="rtlg">Gestione Ferie</span></button>
  </form>
  <form action="PeriodiDiMalattiaServlet" method="POST">
	<button class="cmd"><img src="Icon/MalattieColore.png" class="btl" ><span class="rtlg">Gestione Malattia</span></button>
  </form>
  <form action="PersonaleDisponibile" method="POST">
	<button class="cmd"><img src="Icon/ominiVF.png" class="btl"	><span class="rtlg">Personale Disponibile</button>
  </form>
  </div>
 
</div></a>
 		<%} %>
 		<a><form action="Logout" method="POST">
 		<button class="btn btn-outline-light btn-lg lgt">Logout</button>
 		</form></a>
 	</div>
 	<%} %>
 	
</div>
