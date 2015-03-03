$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null){
       return null;
    }
    else{
       return results[1] || 0;
    }
}

var msg = $.urlParam('s');
var alert = document.getElementById('alert_msg');

if(!msg){
	console.log('Got to null');
} else if(msg === '0'){
	console.log('Got to 0');
	alert.className = "alert alert-info";
	alert.style.display = "block";
	alert.innerHTML = "<strong>INFO: SOME INFORMATION</strong>";
} else if(msg === '1'){
	console.log('Got to 1');
	alert.className = "alert alert-success";
	alert.style.display = "block";
	alert.innerHTML = "<strong>SUCCESS: BET PLACED</strong>";
} else if(msg === '2'){
	console.log('Got to 2');
	alert.className = "alert alert-warning";
	alert.style.display = "block";
	alert.innerHTML = "<strong>WARNING: SOMETHING INCORRECT</strong>";
} else if(msg === '3'){
	console.log('Got to 3');
	alert.className = "alert alert-danger";
	alert.style.display = "block";
	alert.innerHTML = "<strong>ERROR: BET NOT PLACED</strong>";
}

function getUrlParameter(sParam)
{
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) 
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) 
        {
            return sParameterName[1];
        }
    }
}
