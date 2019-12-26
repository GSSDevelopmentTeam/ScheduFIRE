<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="it">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, minimum-scale=1.0">
  <meta name="description"
    content="Date range picker - lightweight, no dependencies. Date Range Picker can be attached to input element to pop up one or more calendars for selecting dates.">
  <meta name="google-site-verification" content="nERiBNqxg1sFpetHtzwMVydsumihA9YnyytsjfzYEGI" />
  <title>Litepicker - Date range picker - lightweight, no dependencies</title>
  <link rel="icon" type="image/png" sizes="192x192" href="https://wakirin.github.io/Litepicker/favicon-192x192.png">
  <link rel="icon" type="image/png" sizes="128x128" href="https://wakirin.github.io/Litepicker/favicon-128x128.png">
  <link rel="apple-touch-icon" sizes="180x180" href="https://wakirin.github.io/Litepicker/apple-touch-icon.png">
  <link rel="icon" type="image/png" sizes="32x32" href="https://wakirin.github.io/Litepicker/favicon-32x32.png">
  <link rel="icon" type="image/png" sizes="16x16" href="https://wakirin.github.io/Litepicker/favicon-16x16.png">
  <link rel="icon" type="image/x-icon" href="https://wakirin.github.io/Litepicker/favicon.ico">
  <link href="https://wakirin.github.io/Litepicker/css/style.css" rel="stylesheet" />
  <link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
</head>

<body>
  

      <div id="demo">

        <div id="demo-preview">
          <div id="demo-preview-sticky2">
            <input id="litepickerr" />
			<input id="litepickerEnd"/>
 
          </div>

        </div>
      </div>
	  
	  
	  
	<script src="https://cdn.jsdelivr.net/npm/litepicker@1.0.19/dist/js/main.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
	<script async defer src="https://buttons.github.io/buttons.js"></script>
	
	<script>
	var array=[ ['2020-01-01', '2020-01-11'], '2020-01-31' ];
	var picker = new Litepicker({ 
		element: document.getElementById('litepickerr') ,
		elementEnd: document.getElementById('litepickerEnd'),
		singleMode:false,
		format:'DD-MM-YYYY',
		lang:'it',
		numberOfMonths:2,
		numberOfColumns:2,
		minDate:new Date(),
		disallowLockDaysInRange: true,
		tooltipText:{
			one: 'giorno',
			other: 'giorni',
		},
		onError: function(error) { alert("Nel periodo selezionato risultano già dei giorni di ferie"); },
		onHide: function() { 	
			var giornoLavorativo=moment("24/12/2019",'DD/MM/YYYY');
			var lavorativo=0;
			var inizio = moment(document.getElementById("litepickerr").value,'DD/MM/YYYY');
			var fine = moment(document.getElementById("litepickerEnd").value,'DD/MM/YYYY');
			console.log(inizio);
			console.log(fine);

			while(inizio.diff(fine)<=0){
				var differenza=inizio.diff(giornoLavorativo,'days');
				var resto=differenza%4;
				if(resto==0 || resto==1)
					lavorativo++;
				
				inizio.add(1, 'days').format("DD");
			}
			alert("giorni di ferie effettivi: "+lavorativo+ "\n ToDo verifica che giorni di ferie presi non superino quelli disponibili");
			
		 }
		
		
		
		
	});
	picker.setLockDays(array);
	picker.setLockDays(['2020-01-29']);
	
	</script>
	
</body>

</html>