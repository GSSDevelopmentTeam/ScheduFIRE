<%@ page import="control.* "%>
<%
	String ruolo = (String) session.getAttribute("ruolo");
%>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

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
 		<a><form action="Logout" method="POST">
 		<button class="inversesubmit" style="float:inherit;">Logout</button>
 		</form></a>
 		<% if(ruolo.equalsIgnoreCase("capoturno")){ %>
 		<a><form>
 		<span> <!-- <button class="nl"><img src="IMG/notizia.png" style="height:50px; width:50px"></button> -->
 		
 		<div class="dd">
  <button type="button" class="db" >
    <img src="IMG/notizia.png" style="height:50px; width:50px ">
  </button>
  <div class="ddc">
    <button class="dropdown-item" type="button">Notifica 1</button>
    <button class="dropdown-item" type="button">Notifica 2</button>
    <button class="dropdown-item" type="button">Notifica 3</button>
  </div>
</div>
</span>
 		</form></a>
 		<%} %>
 	</div>
 	<%} %>
 	
</div>
