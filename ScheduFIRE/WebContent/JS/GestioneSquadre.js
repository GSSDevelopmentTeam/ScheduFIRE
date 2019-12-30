$(document).ready(function () {
	
	$("#aggiungiVF").click(prova);
	
})

function prova (){
		 	$.post("PersonaleDisponibile").done(function (data) {
		    		$('#elenco').after(data);
		    		$('#elenco').unbind();
		    })		
	}


$.ajax({
	type : "POST",
	url : "PersonaleDisponibileAJAX",
	data : {
		"JSON" : true,
		"aggiunta":true,
		"email" : input,
	},
	dataType : "json",
	async : false,
	success : function(response) {
		$('#elenco').after(data);
		
		
	}
});