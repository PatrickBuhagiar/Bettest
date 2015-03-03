/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validate_name(e){
    e= $(e);
    var regex = new RegExp("[a-zA-Z_ ]+$");
    e.on("keypress", function(){
        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
        if (!regex.test(key)) {
            event.preventDefault();
            return false;
        }
    }); 
    
    e.on("focusout",function(){
    	 if (!regex.test(e.val())) {
    		 show_validation_error(e, "Invalid characters");
             return false;
         } else {
        	 hide_validation_error(e)
         }
    });
    
}

function validate_username(e){
	e= $(e);
    var regex = new RegExp("[a-zA-Z0-9_]+$");
    e.on("keypress", function(){
        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
        if (!regex.test(key)) {
            event.preventDefault();
            return false;
        }
    }); 
    
    e.on("focusout",function(){
    	 if (!regex.test(e.val())) {
    		 show_validation_error(e, "Invalid characters");
             return false;
         } else {
        	 hide_validation_error(e)
         }
    });
}

function validate_password(e){
    e= $(e);
    e.on("focusout", function(){
    	if (e.val().length < 8){
    		show_validation_error(e, "Password must be at least 8 characters");
    	}
    });    
}

function validate_integer(e){
    e= $(e);
    e.on("keypress", function(){
        var regex = new RegExp("[0-9]");
        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
        if (!regex.test(key)) {
            event.preventDefault();
            return false;
        }
    });    
}

function show_validation_error(e,message){
    e.addClass('vld_error'); 
    e.tipsy('enable');
    if (!e.is("select")){
    	e.text(message);
    }
    e.tipsy({fallback: message,gravity: 'w', delayOut: 1000 });
}

function hide_validation_error(e){
    e.removeClass('vld_error');  
    if (!e.is("select"))
    	e.text("");
    e.tipsy('disable');
}

function validate_empty(e){
    e.ready(function(){
        if(e.is("input")){
	    	if (!e.val()){
	            show_validation_error(e, "Cannot be empty");
	        } 
	        else {
	            hide_validation_error(e);
	        }
        } else if (e.is("select")){
        	if (e.val() == "-1"){
        		show_validation_error(e, "Please choose a selection");        		
        	}
        	else {
        		hide_validation_error(e);
        	}
        }    	
    });
}

function normalizeYear(year){
    // Century fix
    var YEARS_AHEAD = 20;
    if (year<100){
        var nowYear = new Date().getFullYear();
        year += Math.floor(nowYear/100)*100;
        if (year > nowYear + YEARS_AHEAD){
            year -= 100;
        } else if (year <= nowYear - 100 + YEARS_AHEAD) {
            year += 100;
        }
    }
    return year;
}

function validate_exp_date(e){
	//e=$(e);
	var message="";
	try{
		var match= e.val().match(/^\s*(0?[1-9]|1[0-2])\/(\d\d|\d{4})\s*$/);
	} catch(err){
		show_validation_error(e, "Invalid date");
        return; 
	}
	show_validation_error(e, "Invalid date");
    try{
    	var exp = new Date(normalizeYear(1*match[2]),1*match[1]-1,1).valueOf();
    	var now=new Date();
        var currMonth = new Date(now.getFullYear(),now.getMonth(),1).valueOf();
        if (exp<=currMonth){
            show_validation_error(e, "Invalid date");
        } else {
           hide_validation_error(e);
        };
    } catch(err){
    	show_validation_error(e, "Invalid date");
    }
	
    
}

function validate_form(e){
    e = $(e).closest('form');
    e.children().each(function(){
        $(this).find("input").focusin().focusout();
        $(this).find("select").focusin().focusout();
    });
    
    e.ready(function(){
       if (e.children().find('input:first').hasClass('vld_error') || e.children().find('select:first').hasClass('vld_error')){
           return false;
       } else {
           return true;
       } 
    });
}

function validate_decimal(e){
    e = $(e);
    e.inputmask("decimal", { rightAlign: false, digits: 2});    
}


function validate_credit_card(e){
    var x;
    var y;
    e.validateCreditCard(function(result){
        x=result.length_valid;
        y=result.luhn_valid;
    }, {accept: ['visa', 'mastercard', 'amex']});
    
    if(x && y) return true; else return false;
    
}

function validate_dob(e){
	    	var regEx = /^\d{2}\/\d{2}\/\d{4}$/;
	    	  if(e.val().match(regEx) !== null){
	    		  hide_valdiation_error(e);
	    	  } else {
	    		  show_validation_error(e, "Invalid date");
	    	  }
}

function initiliase_validations(){
    jQuery(document).ready(function(){
        $('.vld_name').on('focus', function(){
            validate_name($(this));
        });
        
        $('.vld_username').on('focus', function(){
        	validate_username($(this));
        });
        $('.vld_int').on('focus', function(){
            validate_integer($(this));
        });
        
        $('.vld_decimal').on('focus', function(){
            validate_decimal($(this));
        });
        
        $('.vld_password').on('focus', function(){
            validate_password($(this));
        });
        
         $('.vld_dob').on("focus", function(){
        	 
        	 min = new Date().getFullYear() - 120;
        	 max = new Date().getFullYear() - 18;
        	 
             $(this).inputmask("date", {yearrange: { minyear: min, maxyear: max }});              
         });
         
         $('.vld_dob').on("blur", function(){
        	 validate_dob($(this));
         });
         
        $('.vld_exp_date').on("blur", function(){
        	validate_exp_date($(this));
        });
         
        
        $('.vld_empty').on('focusout', function(){
            validate_empty($(this));
        });
        
        
        $('.vld_password').on('focusout', function(){
            
            if ($(this).val().length < 8){
                show_validation_error($(this), "Password must be at least 8 characters");
            } 
            else {
                hide_validation_error($(this));
            }
        });
        
        $('#reg-card-number').on("focusout", function(){
            if (!validate_credit_card($(this))){
                show_validation_error($(this), "Invalid card number");
            }
            else {
                hide_validation_error($(this));
            }
        });
    });
}


