<%@ page import="control.* "%>
<%
	String ruolo = (String) session.getAttribute("ruolo");
%>

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
 		<button class="inversesubmit">Logout</button>
 		</form></a>
 		<% if(ruolo.equalsIgnoreCase("capoturno")){ %>
 		<a><form>
 		<span><button class="nl"><img src="IMG/notizia.png" style="height:50px; width:50px"></button></span>
 		</form></a>
 		<%} %>
 	</div>
 	<%} %>
 	
</div>
