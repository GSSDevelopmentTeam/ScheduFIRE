/**
 * 
 */



	function calcolaTurno(data){
	var dataOra=new Date();
	var giorno=data.getDate();
	var mese=data.getMonth() + 1; // mesi indicizzati da 0
	var anno=data.getFullYear();
	//console.log("calcolaTurno(giorno,mese,anno) "+giorno+" "+mese+" "+anno);
	var stringaData=""+giorno+"/"+mese+"/"+anno;
	var turnoB=nomeTurnoB(stringaData);
	//console.log("turno B: "+turnoB);
	rimuoviTurno();
	if(turnoB!=""){
		//resetColore();
		rimuoviTurno();
		$("#messaggioTurno").html("Per il giorno "+giorno+" il turno &egrave; "+turnoB);
		//sostituisciNumero(giorno,turnoB);
		
	}
}



function rimuoviTurno(){ //equivalente di onmouseleave
	//resetColore();
	$("#messaggioTurno").html("&nbsp;");
	//resettaNumeriCalendario();	
	//resetColore();


	
}

function resetColore(){
	$('a.day-item').each(function() {
		console.log("prop "+$(this).css("color"));
		if ($(this).css("color") != "rgb(158, 158, 158)") {
			console.log("colore verde!!");
			$(this).prop( "style", "color:#007bff" );

}
	})
}
function sostituisciNumero(giorno,turnoB){
		console.log("giorno "+giorno+" turnoB "+turnoB);

		$('a.day-item').each(function() {
			if ($(this).text() == giorno) {
				$(this).text(turnoB);
				$(this).css( "color", "#47d147" );
			}
		})

	
}


function calcolaDifferenza(data) {
	var inizio = moment("20/12/2019", 'DD/MM/YYYY');
	var fine = moment(data,
	'DD/MM/YYYY');
	var differenza = fine.diff(inizio,'days');
	//console.log("differenza: "+differenza);
	return differenza;
}

function nomeTurnoB(data){
	var turno="";
	var differenza=calcolaDifferenza(data);
	var resto=differenza%32;
	if(differenza>=0) {
		if(resto==0 || resto==1)
			turno="B7";
		else if(resto==4 || resto==5)
			turno="B8";
		else if(resto==8 || resto==9)
			turno="B1";
		else if(resto==12 || resto==13)
			turno="B2";
		else if(resto==16 || resto==17)
			turno="B3";
		else if(resto==20 || resto==21)
			turno="B4";
		else if(resto==24 || resto==25)
			turno="B5";
		else if(resto==28 || resto==29)
			turno="B6";
	}else {
		differenza = (-differenza)+1;
		resto=differenza%32;
		if(resto==0 || resto==1)
			turno="B7";
		else if(resto==4 || resto==5)
			turno="B6";
		else if(resto==8 || resto==9)
			turno="B5";
		else if(resto==12 || resto==13)
			turno="B4";
		else if(resto==16 || resto==17)
			turno="B3";
		else if(resto==20 || resto==21)
			turno="B2";
		else if(resto==24 || resto==25)
			turno="B1";
		else if(resto==28 || resto==29)
			turno="B8";
	}	 
	return turno;
};

function resettaNumeriCalendario(){
	console.log("resetta numeri calendario partita");
	var giorno=1;
	$('a.day-item').each(function() {
		$(this).text(giorno);
		giorno++;
	})

}


function modificaNumeriCalendario(mese, anno){
	resettaNumeriCalendario();
	for(var giorno=1;giorno<=31;giorno++){
		var giornoFormat=giorno<=9?"0"+giorno:giorno;
		var meseFormat=mese.length<2?"0"+mese:mese;
		var stringaData=""+giornoFormat+"/"+meseFormat+"/"+anno;
		//console.log("stringaData "+stringaData);
		var turnoB=nomeTurnoB(stringaData);
		//console.log("turnoB "+turnoB);
		if(turnoB!="")	{
			//$("a.day-item:contains('" + giorno + "')").text(" "+giorno+" \n\n "+turnoB+" ");
			$('a.day-item').each(function() {
				if ($(this).text() == giorno) {
					$(this).html(" "+giorno+"<br>  "+turnoB+" ");
				}
			})
		}
		//$("a.day-item:contains('" + giorno + "')").text(" "+giorno+" \n\n "+turnoB+" ");
	}

	/*
	//Modifica

	var data=new n.DateTime();
	var mese=data.getMonth() + 1; // mesi indicizzati da 0
	var anno=data.getFullYear();
	mese=mese<=9?"0"+mese:mese;
	modificaNumeriCalendario(mese, anno);
	console.log("onmouseLeave eseguito");


	//fine modifica
	 */
}
