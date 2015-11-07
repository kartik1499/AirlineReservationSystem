<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.website.airline.FlightBean" %>
<%@ page import="com.website.airline.FlightDetailBean" %>
<head>
      <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Flight Instances</title>
	<!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body>

    <div id="wrapper">
        <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html">Travel</a> 
            </div>
  <div style="color: white;
padding: 15px 50px 5px 50px;
float: right;
font-size: 16px;"> Welcome Guest &nbsp; <a href="Home.jsp" class="btn btn-danger square-btn-adjust">Home</a> 
										</div>
        </nav>   
        
       
    <!--/.SLIDESHOW END-->
        	<!-- /. NAV TOP  -->
                <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <section id="home" class="text-center">
         
                <div id="carousel-example" class="carousel slide" data-ride="carousel">

                    <div class="carousel-inner">
                        <div class="item active">

                            <img src="assets/img/aa.jpg" alt="" />
                            <div class="carousel-caption" >
                                <h4 class="back-light"></h4>
                            </div>
                        </div>
                        <div class="item">
                            <img src="assets/img/aa1.jpg" alt="" />
                            <div class="carousel-caption ">
                                <h4 class="back-light"></h4>
                            </div>
                        </div>
                        <div class="item">
                            <img src="assets/img/sw1.jpg" alt="" />
                            <div class="carousel-caption ">
                                <h4 class="back-light"></h4>
                            </div>
                        </div>
                    </div>

                    <ol class="carousel-indicators">
                        <li data-target="#carousel-example" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel-example" data-slide-to="1"></li>
                        <li data-target="#carousel-example" data-slide-to="2"></li>
                    </ol>
                </div>
           
       </section>
               
            </div>
            
        </nav>  
        <div id="page-wrapper" >
            <div id="page-inner">
               
                 <!-- /. ROW  -->
                 <hr />
                 
             
                
                
                
                <form action="/AirlineReservationSystem/ActionServlet" method="post">
                 <%ArrayList<FlightBean> list1 = (ArrayList<FlightBean>)request.getAttribute("oneStopFlight"); 
                     ArrayList<FlightBean> list2 = (ArrayList<FlightBean>)request.getAttribute("twoStopsFlight");
                 
                 %>
                 
                 <%ArrayList<FlightDetailBean> list = (ArrayList<FlightDetailBean>)request.getAttribute("flightDetails"); %>
                <%if(list!=null && list.size()>0){ %>
                <h2>Following are direct flights between <%=request.getAttribute("departureCode") %> and <%=request.getAttribute("arrivalCode") %></h2>
                
                <table style="width: 40%" border="1">
                <tr>
                <th >Flight Number &nbsp;&nbsp;&nbsp;
                </th>
                
                <th >&nbsp;&nbsp;&nbsp;Weekdays
                </th>
                
                 <th >&nbsp;&nbsp;&nbsp;Airline
                </th>
                </tr>
                
                <%for(int i=0;i<list.size();i++)
                	{
                	System.out.println(list.get(i).getFlightNumber());
                	%>
                <tr>
                <td align="center">
                <%=list.get(i).getFlightNumber() %>
                </td>
                
                <td align="center">
                <%=list.get(i).getWeekdays() %>
                </td>
                
                <td align="center">
                <%=list.get(i).getAirline() %>
                </td>
                
                </tr>
                <%} %>
                </table>
                <%} %>
                
                <%if("yes".equals((String)request.getAttribute("direct")) &&(list==null|| list.size()==0)){ %>
                <h2>There are no direct flights between <%=request.getAttribute("departureCode") %> and <%=request.getAttribute("arrivalCode") %></h2> 
                <%} %>
                
                <!-- <h2>Following are the connecting flights</h2> -->
                
                 <%if("yes".equals((String)request.getAttribute("one")) &&(list1==null|| list1.size()==0)){ %>
                <h2>There are no flights with one stop between <%=request.getAttribute("departureCode") %> and <%=request.getAttribute("arrivalCode") %></h2> 
                <%} %>
                
                <%if(list1!=null && list1.size()>0){ %>
                <h3>Flights with one stop :</h3>
                <table style="width: 95%" border="1">
                <tr>
                <th >First Flight Number 
                </th>
                
                <th >First Flight Weekdays 
                </th>
                
                <th >Scheduled Departure 
                </th>
                
                <th >Scheduled Arrival 
                </th>
                
                <th >Second Flight Number
                </th>
                
                <th >Second Flight Weekdays 
                </th>
                
                <th >Scheduled Departure 
                </th>
                
                <th >Scheduled Arrival 
                </th>
                
                <th >Origin Airport Code
                </th>
              
              	<th >First Stop Airport Code
                </th>
                
                <th >Destination Airport Code
                </th>
               
                </tr>
                
                <%for(int i=0;i<list1.size();i++)
                	{
                	
                	%>
                <tr>
                <td >
                <%=list1.get(i).getFirstFlightNumber() %>
                </td>
               
                <td >
                <%=list1.get(i).getFirstWeekday() %>
                </td>
                
                
                
                <td >
                <%=list1.get(i).getFirstDepart() %>
                </td>
                
                <td >
                <%=list1.get(i).getFirstArrive() %>
                </td>
                
                <td>
                <%=list1.get(i).getSecondFlightNumber() %>
                </td>
                 
                <td >
                <%=list1.get(i).getSecondWeekday() %>
                </td>
                
                
                
                <td >
                <%=list1.get(i).getSecondArrive() %>
                </td>
                
                <td >
                <%=list1.get(i).getSecondDepart() %>
                </td>
                 
                <td >
                <%=list1.get(i).getOrigin() %>
                </td>
                
                <td >
                <%=list1.get(i).getFirstStop() %>
                </td>
                
                 <td >
                <%=list1.get(i).getDestination() %>
                </td>
                                
                
                </tr>
                <%} %>
                </table>
                <%} %>
                
                
                <hr>
                
                
                 <%if("yes".equals((String)request.getAttribute("two")) && (list2==null|| list2.size()==0)){ %>
                <h2>There are no flights with two stops between <%=request.getAttribute("departureCode") %> and <%=request.getAttribute("arrivalCode") %></h2> 
                <%} %>
                
                <%if(list2!=null && list2.size()>0){ %>
                <h3>Flights with two stops :</h3>
                <table style="width: 80%" border="1">
                <tr>
                <th width="3%">First Flight Number 
                </th>
                
                <th width="3%">First Flight Weekdays 
                </th>
                
                
                <th width="5%">Scheduled Departure 
                </th>
                
                <th width="5%">Scheduled Arrival 
                </th>
                
                <th width="3%">Second Flight Number
                </th>
                
                <th width="8%">Second Flight Weekdays 
                </th>
                
                <th width="5%">Scheduled Departure 
                </th>
                
                <th width="5%">Scheduled Arrival 
                </th>
                
                <th width="3%">Third Flight Number
                </th>
                
                <th width="8%">Third Flight Weekdays 
                </th>
                
                <th width="5%">Scheduled Departure 
                </th>
                
                <th width="5%">Scheduled Arrival 
                </th>
                
                <th width="3%">Origin Airport Code
                </th>
              
              	<th width="3%">First Stop Airport Code
                </th>
                
                <th width="3%">Second Stop Airport Code
                </th>
                
                <th width="3%">Destination Airport Code
                </th>
               
                </tr>
                
                <%for(int i=0;i<list1.size();i++)
                	{
                	
                	%>
                <tr>
                <td >
                <%=list2.get(i).getFirstFlightNumber() %>
                </td>
                
                <td >
                <%=list2.get(i).getFirstWeekday() %>
                </td>
                
                
                
                <td >
                <%=list2.get(i).getFirstDepart() %>
                </td>
                
                <td >
                <%=list2.get(i).getFirstArrive() %>
                </td>
                
                <td >
                <%=list2.get(i).getSecondFlightNumber() %>
                </td>
                
                <td >
                <%=list2.get(i).getSecondWeekday() %>
                </td>
                
                
                
                <td >
                <%=list2.get(i).getSecondDepart() %>
                </td>
                
                <td >
                <%=list2.get(i).getSecondArrive() %>
                </td>
                
                <td >
                <%=list2.get(i).getThirdFlightNumber() %>
                </td>
                 
                 <td >
                <%=list2.get(i).getThirdWeekday() %>
                </td>
                
                <td >
                <%=list2.get(i).getThirdDepart() %>
                </td>
                
                <td >
                <%=list2.get(i).getThridArrive() %>
                </td>
                <td >
                <%=list2.get(i).getOrigin() %>
                </td>
                
                <td >
                <%=list2.get(i).getFirstStop() %>
                </td>
               
                <td >
                <%=list2.get(i).getSecondStop()%>
                </td>
                 
                 <td >
                <%=list2.get(i).getDestination() %>
                </td>
                                
                
                </tr>
                <%} %>
                </table>
                <%} %>
                
                <hr />
                </form>
                
              

                  <div class="row">                     
                      
                              <div class="col-md-6 col-sm-12 col-xs-12">                     
                    <div class="panel panel-default">
                 
                        <div class="panel-body">
                            <a href="AvailSeats.jsp" class="btn btn-danger square-btn-adjust">Get Available Seats Information</a>
                <br />
                <br />
                
                <a href="GetFareInfo.jsp" class="btn btn-danger square-btn-adjust">Get Fare Information</a>
                <br />
                <br />
                <a href="Passenger.jsp" class="btn btn-danger square-btn-adjust">Get Passenger List for a flight</a>
                <br />
                <br />
             
                <a href="viewFlightInstances.jsp" class="btn btn-danger square-btn-adjust">Get List of flights for a passenger</a>
              
                        </div>
                    </div>            
                </div> 
                      <div class="col-md-6 col-sm-12 col-xs-12">                     
                    <div class="panel panel-default">
                        
                        <div class="panel-body">
                        
                        <h2>Special Deals</h2>  
                                                                      
                        <h4>Fly anywhere in USA starting $99</h4>
                        <h4>Earn 1000 mileage points for each flight with American Airlines</h4>
                        </div>
                    </div>            
                </div> 
                
          
       
    
             <!-- /. PAGE INNER  -->
            
         <!-- /. PAGE WRAPPER  -->
        </div>
        </div>
        </div>
        </div>
        <script type="text/javascript" src="assets/js/canvasjs.min.js"></script>
        
     <!-- /. WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="assets/js/jquery-1.10.2.js"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="assets/js/bootstrap.min.js"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="assets/js/jquery.metisMenu.js"></script>
      <!-- CUSTOM SCRIPTS -->
    <script src="assets/js/custom.js"></script>
    
   
</body>
</html>
