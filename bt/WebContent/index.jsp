<%@ page language="java" 
contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Bettest</title>
        <meta charset="UTF-8">
        <meta id="viewport" content="width=device-width, initial-scale=1.0">
        <script src="js/jquery-latest.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script type="text/javascript" src="js/validations.js"></script>    
        <link rel="stylesheet" href="css/bootstrap.css">       
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/datepicker.css">
       	<!-- hey -->
    </head>
    <body>
    <div class="site-wrapper">
    <% if(request.getSession().getAttribute("logged-user") != null) { 
        	request.getSession().setAttribute("msg", "Welcome! Please Place Your Bet.");
        	request.getSession().setAttribute("msgstate", "alert-success");
        	response.sendRedirect("home.jsp"); } %>
        <div class="site-wrapper-inner">
            <div class="cover-container">
                <div class="masthead clearfix">
                    <div class="inner">
                        <h3 class="masthead-brand"><img src="raw/Bettest_Logo_Ball.png" class="logo mini" style="width:61px;height:48px" alt="Bettest Logo"></h3>
                        <ul class="nav masthead-nav">
                            <li class="active"><a data-target="#LoginModal" data-toggle="modal" id="open_login" href="#">Log In</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="inner cover">
                    <img src="raw/Bettest_Logo_White.png" class="logo large" style="width:600px;height:150px;float: center" alt="Bettest Logo" />
                    <p class="lead">Just place your bet, and leave the rest to us!*</p>    
                   <!--<p class="lead"><a href="#" data-toggle="modal" data-target="#LoginModal" class="btn btn-lg btn-default">Log In</a>-->
                    
                    
                </div>
                <div class="mastfoot">
                    <div class="ground_text inner">
                        <p class="lead ">New user? <a href="register" id="link_register" data-toggle="modal" data-target="#RegisterModal"> Register Now </a> </p>  
                        <p>Copyright &copy; Jake Dalli & Patrick Buhagiar, 2014.</p>
                        <p>*Risk factor determined by you.</p>
                    </div>
                </div>
            </div>
        <% if(request.getSession().getAttribute("msg") != null && request.getSession().getAttribute("msgstate") != null) { %>
            <div class="col-md-3"></div>
            <div class="col-md-6">
	            <div <% if(request.getSession().getAttribute("msgstate") == "alert-success") {%> id="msgsuccess" <%} else { %> id="msgfail" <% } %> class="alert ${sessionScope.msgstate} alert-dismissible" role="alert">
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  <strong></strong> ${sessionScope.msg}
				  <% request.getSession().removeAttribute("msg"); request.getSession().removeAttribute("msgstate"); %>
				</div>
			</div>
			<div class="col-md-3"></div>
		<% } %>
        </div>
    </div>	
    <div class="modal" id="LoginModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title info_text" id="myModalLabel">Log In</h3>
                </div>
                <div class="modal-body">
                    <form class="form" id="login" role="form" method="post" action="login">
                        <div class="form-group">
                            <input type="text" class="form-control vld_username vld_empty" name="bt_un" id="log-the-username" placeholder="Username">
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control vld_password" name="bt_pw" id="log-password" placeholder="Password">
                        </div>
                        <input type="submit" id="submit_login" value="Log In" class="btn btn-default">
                        <!--  <button type="submit" id="log-button" value="login" class="btn btn-default">Log In</button> -->
                    </form>	
                </div>
            </div>
        </div>
    </div>
    <div class="modal" id="RegisterModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title info_text" id="myModalLabel">Register</h3>
                </div>
                <div class="modal-body">
                    <form class="form" role="form" id="form_register" action="register" method="post">
                        <div class="form-group">
                            <h4 class="info_text">User Details</h4>  
                        </div>
                        
                        <div class="form-group">
                            <input type="text" class="form-control vld_username vld_empty" name="reg-username" id="reg-username" placeholder="Username">
                        </div>
                        
                        <div class="form-group">
                            <input type="password" class="form-control vld_password"  name="reg-password"  id="reg-password" placeholder="Password">
                        </div>
                        
                        <div class="form-group">
                            <input type="password" class="form-control vld_password" name="reg-confirmpassword" id="reg-confirm-password" placeholder="Confirm Password">
                        </div>
                        
                        <div class="form-group">
                            <input type="email" class="form-control vld_empty" name="reg-email" id="reg-email" placeholder="E-mail">
                        </div>
                        
                        <div class="form-group">
                            <input type="text" class="form-control vld_name vld_empty" name="reg-name"  id="reg-name" placeholder="Name">
                        </div>
                        
                        <div class="form-group">
                            <input type="text" class="form-control vld_name vld_empty" name="reg-surname" id="reg-surname" placeholder="Surname">
                        </div>
                        
                        <div class="form-group">
                            <input type="text" class="form-control vld_empty input-append date vld_dob" name="reg-dob" id="reg-dob" placeholder="Date of Birth">
                            
                        </div>
                        
                        <div class="form-group"> 
                        	<select class="form-control vld_empty" name="reg-country" id="reg-country">
                        		<option value="-1">- Country -</option>
                                <option value="1">United States</option>
                                <option value="2">United Kingdom</option>
                                <option value="3">Malta</option>
                                <option value="4">Germany</option>
                                <option value="5">France</option>
                             </select>
						</div> 
                        
                        <div class="form-group">
                            <input type="text" class="form-control vld_empty" name="reg-address" id="reg-address" placeholder="Address">                            
                        </div>
                        
                        
                         <div class="form-group row">                          
	                        <div class="form-group col-xs-3">
	                            <input type="text" class="form-control vld_empty" name="reg-city" id="reg-city" placeholder="City">                            
	                       	</div>
	                        
	                        <div class="form-group col-xs-3">
	                            <input type="text" class="form-control vld_empty" name="reg-pcode" id="reg-postcode" placeholder="Post Code">                            
	                        </div>	                      
	                     </div>
                        
                        <div class="form-group">
                            <h4 class="info_text">Credit Card Details</h4>  
                        </div>
                        
                        
                        
                        <div class="form-group">
                            <select name="reg-card" class="form-control vld_empty" id="reg-card">
                                <option value="-1">- Credit Card -</option>
                                <option value="1">American Express</option>
                                <option value="2">Mastercard</option>
                                <option value="3">VISA</option>
                            </select>
                            </div>
                        
                        
                        <div class="form-group">
                            <p>
                        </div>
                        
                        <div class="form-group">
                            <input type="text" name="reg-cardholder" class="form-control vld_empty" id="reg-card-holder-id" placeholder="Card Holder's Name">
                        </div>
                        
                        <div class="form-group">
                          <input type="text" name="reg-cardno" class="form-control vld_integer" id="reg-card-number" placeholder="Debit/Credit Card Number">                       
                        </div>
                      
                        <div class="form-group">
                          <div class="row">
                            <div class="col-xs-3">
                                <input type="text" name="reg-expdate" class="form-control vld_empty vld_exp_date" id="reg-exp-date" maxlength="5" placeholder="Expiry Date">
                            </div>
                            <div class="col-xs-3">
                                <input type="text" name="reg-ccv" class="form-control vld_int vld_empty" id="reg-ccv" placeholder="CCV" maxlength="3">
                            </div> 
                          </div>
                        </div>
                
                        <div class="form-group ">
                            <div class="row info_text col-md-8" align="left">
                                <input name="reg-acc" type="checkbox" id="reg-Acc" class="reg-account-type">Premium Account<br>
                                <p>
                            </div>
                        </div>
                        
                        <div class="form-group row">
							<div class="info_text">
                                  <div class ="form-group col-md-12" >
                                      By clicking Register, you agree to our Terms and that you have read our Data Use Policy, including our Cookie Use.
                                  </div>
                            </div>
						</div>
                        <div class="form-group">
                            <div class="form-group">
                            <input type="submit" id="submit_register" class="btn btn-default" value="Register">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script src="js/jquery-latest.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/jquery.inputmask.js"></script>
    <script type="text/javascript" src="js/jquery.inputmask.regex.extensions.js"></script>
    <script type="text/javascript" src="js/jquery.inputmask.date.extensions.js"></script>
    <script type="text/javascript" src="js/jquery.inputmask.numeric.extensions.js"></script>
    <script type="text/javascript" src="js/jquery.tipsy.js"></script>
    <script type="text/javascript" src="js/bootstrap-datepicker.js"></script> 
    <script type="text/javascript" src="js/jquery.creditCardValidator.js"></script> 
    <link rel="stylesheet" href="css/tipsy.css">
    <script>
    
        $(document).ready( function(){
           initiliase_validations();
        }); 
        
        $('#log-button').click(function(){
            if (validate_form(this)){
            
            }
            return false;
        });
       
        $('#submit_register').click(function(){
            if (validate_form($('#submit_register'))){
            
            }
            return false;
        });
        
        
       
    </script>   
</body>
</html>
