<%@ page language="java" 
    contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"
    session="true"
%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <link rel="stylesheet" href="css/bootstrap.css">
        <title>Bettest</title>
        <meta charset="UTF-8">
        <meta id="viewport" content="width=device-width, initial-scale=1.0">
        <script src="js/jquery-latest.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script type="text/javascript" src="js/validations.js"></script> 
        <link rel="stylesheet" href="css/bootstrap.css">       
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/datepicker.css">   
    </head>
    <body>
        <div class="container">
        <%@ page import="java.util.ArrayList,com.bettest.site.bt.model.Bet,com.bettest.site.bt.util.Utility,com.bettest.site.bt.model.User" %>
        <% if(request.getSession().getAttribute("logged-user") == null) { 
        	request.getSession().setAttribute("msg", "Please Login.");
        	request.getSession().setAttribute("msgstate", "alert-danger");
        	response.sendRedirect("index.jsp"); } %>
            <div class="row">
            <% if(request.getSession().getAttribute("msg") != null && request.getSession().getAttribute("msgstate") != null) { %>
            <div <% if(request.getSession().getAttribute("msgstate") == "alert-success") {%> id="msgsuccess" <%} else { %> id="msgfail" <% } %> class="alert ${sessionScope.msgstate}" role="alert">${sessionScope.msg}</div>
            <% request.getSession().removeAttribute("msg"); %>
            <% request.getSession().removeAttribute("msgstate"); %>
            <% } %>
                <div class="col-md-12" style="height:90px;">
                    <div class="col-md-8" style="height:90px;"> 
                        <h3 class="masthead-brand"><img src="raw/Bettest_Logo_White.png" class="logo" style="width:300px;height:75px;float: center" alt="Bettest Logo" /></h3>
                    </div>                    
                    <div class="col-md-4" style="height:90px;">
                        <ul class="nav masthead-nav">
                        <form class="form" id="logout-form" role="form" method="post" action="logout">
		                        <li class="active"><input type="submit" id="log-logout" value="Log Out" class="btn btn-link" style="color:#FFF;border-bottom-color: #fff;font-size:16px;font-weight: bold;padding-left:0;padding-right:0;"></li>
		                </form>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12" style=" height:90px;">
                    <nav class="navbar navbar" style="height:90px;" role="navigation">
                        <div class="navbar-header">
                           <a class="navbar-brand">Sections</a>
                        </div>
                        <div>
                           <ul class="nav navbar-nav">
                              <li class="active"><a href="#">Major Leagues</a></li>
                              <li class="active"><a href="#">League Tournaments</a></li>
                              <li class="active"><a href="#">Champions League</a></li>
                              <li class="active"><a href="#">Europa League</a></li>
                              <li class="active"><a href="#">World Cup</a></li>
                              <li class="active"><a href="#">EURO 2016</a></li>
                                
                           </ul>
                        </div>
                     </nav>
                    
                </div>
            </div>
             <div class="row">
                <div class="col-md-12" style=" height:350px;">
                    <div class="row">
                        <div class="col-md-3">
                          <div class="thumbnail">
                            <img src="raw/seriaa.png" style="width:150px;height:150px;"  alt="...">
                            <div class="caption">
                              <h4>Seria A</h4>
                              <p>Italy</p>
                              <p><a class="btn btn-default" id="bet_it" data-target="#BetModal" data-toggle="modal" href="#">Bet Now!</a></p>
                            </div>
                          </div>
                        </div>
                        <div class="col-md-3">
                          <div class="thumbnail">
                            <img src="raw/premier.png" style="width:150px;height:150px;"  alt="...">
                            <div class="caption">
                              <h4>Premier League</h4>
                              <p>England</p>
                              <p><a class="btn btn-default" id="bet_en" data-target="#BetModal" data-toggle="modal" href="#">Bet Now!</a></p>
                            </div>
                          </div>
                        </div>
                        <div class="col-md-3">
                          <div class="thumbnail">
                            <img src="raw/bbva.png" style="width:150px;height:150px;"  alt="...">
                            <div class="caption">
                              <h4>La Liga</h4>
                              <p>Spain</p>
                              <p><a class="btn btn-default" id="bet_es" data-target="#BetModal" data-toggle="modal" href="#">Bet Now!</a></p>
                            </div>
                          </div>
                        </div>                     
                        <div class="col-md-3">
                          <div class="thumbnail">
                            <img src="raw/bundesliga.png" style="width:150px;height:150px;"  alt="...">
                            <div class="caption">
                              <h4>Bundesliga</h4>
                              <p>Germany</p>
                              <p><a class="btn btn-default" id="bet_de" data-target="#BetModal" data-toggle="modal" href="#">Bet Now!</a></p>
                            </div>
                          </div>
                        </div>                        
                      </div>
                </div>
            </div>
            <% ArrayList<Bet> betList = (ArrayList) request.getSession().getAttribute("logged-user-bets"); %>
            
            <div class="row">
            <% if(request.getSession().getAttribute("logged-user") != null) {  %>
	            <%  Utility u = new Utility(); %>
	            <% out.println(u.getBets((User) request.getSession().getAttribute("logged-user"))); %>
            <% } %>
            </div>
            
            <div class="modal" id="BetModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">                    
                        <div class="modal-body">
		                    <form class="form" id="bet" role="form" method="post" action="bet">
		                        <div class="form-group">
                                    <input type="text" class="form-control vld_decimal vld_empty" name="bet-amount" id="bet-amount" placeholder="Bet Amount">
                                </div>
		                        <div class="form-group">
                                    <select class="form-control vld_empty" name="bet-risk" id="bet-risk">
                                        <option value="-1">- Risk Level -</option>
                                        <option value="1">Low</option>
                                        <option value="2">Medium</option>
                                        <option value="3">High</option>
                                    </select>
                                </div>
		                        <input type="submit" id="bet-placebet" value="Place Bet" class="btn btn-default">
		                        <!--  <button type="submit" id="log-button" value="login" class="btn btn-default">Log In</button> -->
		                    </form>
		                </div>
                    </div>
                </div>
            </div>    
        </div>
        
        
    <script src="js/jquery-latest.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/jquery.inputmask.js"></script>
    <script type="text/javascript" src="js/jquery.inputmask.date.extensions.js"></script>
    <script type="text/javascript" src="js/jquery.inputmask.numeric.extensions.js"></script>
    <script type="text/javascript" src="js/jquery.tipsy.js"></script>
    <script type="text/javascript" src="js/bootstrap-datepicker.js"></script> 
    <script type="text/javascript" src="js/jquery.creditCardValidator.js"></script> 
    <link rel="stylesheet" href="css/tipsy.css"></link> 
    <script>
        $(document).ready( function(){
            initiliase_validations();
        });        
        
        $('#bet-button').click(function(){
            if (validate_form(this)){
            
            }
            return false;
        });       
    </script>   
    </body>
</html>
