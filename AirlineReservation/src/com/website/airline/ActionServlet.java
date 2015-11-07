package com.website.airline;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ActionServlet
 */
@WebServlet("/ActionServlet")
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		RequestDispatcher requestDispatcher = null;
		int errCnt=0;
		try{
			String requestType=request.getParameter("type");
			
			if("flightdetails".equalsIgnoreCase(requestType))
			{
			String departureAirportCode = request.getParameter("dac");
			String arrivalAirportCode = request.getParameter("aac");
			
			if(departureAirportCode==null || arrivalAirportCode==null)
				requestDispatcher = request.getRequestDispatcher("error.jsp");
			else
			{
				departureAirportCode=departureAirportCode.trim();
				arrivalAirportCode=arrivalAirportCode.trim();
				AirlineDAO dao = new AirlineDAO();
				String stops[] = request.getParameterValues("stops");
				ArrayList<FlightDetailBean> directflights = null;
				ArrayList<FlightBean> oneStopFlight = null;
				ArrayList<FlightBean> twoStopsFlight = null;
				
				for(int i=0;i<stops.length;i++)
				{
					if("0".equals(stops[i]))
					{
						directflights=dao.getFlightDetails(arrivalAirportCode, departureAirportCode);
						request.setAttribute("direct", "yes");
					}
					if("1".equals(stops[i]))
					{
						directflights=dao.getFlightDetails(arrivalAirportCode, departureAirportCode);
						oneStopFlight=dao.findFlightDetailsOneStop(departureAirportCode, arrivalAirportCode);
						request.setAttribute("one", "yes");
					}
					if("2".equals(stops[i]))
					{
						directflights=dao.getFlightDetails(arrivalAirportCode, departureAirportCode);
						oneStopFlight=dao.findFlightDetailsOneStop(departureAirportCode, arrivalAirportCode);
						twoStopsFlight=dao.findFlightDetailsTwoStops(departureAirportCode, arrivalAirportCode);
						request.setAttribute("two", "yes");
					}
				}
				
															
					
					request.setAttribute("departureCode", departureAirportCode);
					request.setAttribute("arrivalCode", arrivalAirportCode);					
					request.setAttribute("flightDetails", directflights);
					request.setAttribute("oneStopFlight", oneStopFlight);
					request.setAttribute("twoStopsFlight", twoStopsFlight);
					
					if((directflights==null||directflights.size()==0) && (oneStopFlight==null||oneStopFlight.size()==0) && (twoStopsFlight==null||twoStopsFlight.size()==0))
					{
						request.setAttribute("errorMsg", "There are no flights between the given two airports");
						requestDispatcher = request.getRequestDispatcher("error.jsp");	
					}
					else
					{
						requestDispatcher = request.getRequestDispatcher("ConnectingFlights.jsp");
					}
				}
			
			
			
			
			
			}
			
			else if ("availseats".equalsIgnoreCase(requestType)) {
				
				String flightNumber = request.getParameter("flightNum");
				String date = request.getParameter("date");
				
				if(flightNumber==null || date==null)
					requestDispatcher = request.getRequestDispatcher("error.jsp");
				else
				{
					flightNumber=flightNumber.trim();
					date=date.trim();
					
				}
				
				AirlineDAO dao = new AirlineDAO();
				int availSeats = dao.getAvailableSeats(Integer.parseInt(flightNumber), date);																
				
				if(availSeats==-1)
				{
					request.setAttribute("errorMsg", "No data available for this flight on this day");
					requestDispatcher= request.getRequestDispatcher("error.jsp");
				}
				
				else{
				request.setAttribute("flightNum", flightNumber);
				request.setAttribute("date", date);
				request.setAttribute("availSeats", availSeats);
				requestDispatcher= request.getRequestDispatcher("showSeats.jsp");
				}
			}
			
			
			
			
			else if ("getfare".equalsIgnoreCase(requestType)) {
				
				String flightNumber = request.getParameter("flightNum");
				
				
				if(flightNumber==null )
					requestDispatcher = request.getRequestDispatcher("error.jsp");
				else
				{
					flightNumber=flightNumber.trim();
					
					
				}
				
				AirlineDAO dao = new AirlineDAO();
				ArrayList<FareBean> fareDetails = dao.getFareDetails(Integer.parseInt(flightNumber));
				
				request.setAttribute("fareDetails", fareDetails);
				
			    
				if(fareDetails==null || fareDetails.size()==0)
				{
					request.setAttribute("errorMsg", "There is no fare information for this flight");
					requestDispatcher = request.getRequestDispatcher("error.jsp");
				}
					
				else
					requestDispatcher= request.getRequestDispatcher("showFareInfo.jsp");
				
				
				
			}
			
			
			
			else if ("getpassengerlist".equalsIgnoreCase(requestType)) {
				
				String flightNumber = request.getParameter("flightNum");
				
				
				if(flightNumber==null )
					requestDispatcher = request.getRequestDispatcher("error.jsp");
				else
				{
					flightNumber=flightNumber.trim();
										
				}
				
				AirlineDAO dao = new AirlineDAO();
				String date = request.getParameter("date");
				ArrayList<PassengerBean> passengerDetails = dao.getPassengerDetails(Integer.parseInt(flightNumber),date);
				if(passengerDetails!=null && passengerDetails.size()>0)
				{
				request.setAttribute("passengerDetails", passengerDetails);
				request.setAttribute("flightNum", flightNumber);
				request.setAttribute("arrTime", passengerDetails.get(0).getArrTime());
				request.setAttribute("departTime", passengerDetails.get(0).getDepartTime());
				request.setAttribute("departAir", passengerDetails.get(0).getDeptAirport());
				request.setAttribute("arrAir", passengerDetails.get(0).getArrAirport());
				}
				if(passengerDetails==null || passengerDetails.size()==0)
				{
					request.setAttribute("errorMsg", "No data exists for this flight");
					requestDispatcher = request.getRequestDispatcher("error.jsp");
				}
					
				else
					requestDispatcher= request.getRequestDispatcher("showPassengerInfo.jsp");
	
			}
							
			
			else if ("getflightlist".equalsIgnoreCase(requestType)) {
				
				String passengerName = request.getParameter("passengerName");
				
				
				if(passengerName==null )
					requestDispatcher = request.getRequestDispatcher("error.jsp");
				else
				{
					passengerName=passengerName.trim();
										
				}
				
				AirlineDAO dao = new AirlineDAO();
				ArrayList<PassengerBean> flightDetails = dao.getFlightInstances(passengerName);
				
				request.setAttribute("flightDetails", flightDetails);
				request.setAttribute("passengerName", passengerName);
			    
				if(flightDetails==null || flightDetails.size()==0)
				{
					requestDispatcher = request.getRequestDispatcher("error.jsp");
					request.setAttribute("errorMsg", "No data exists for this passenger");
				}
					
				else
					requestDispatcher= request.getRequestDispatcher("showFlightInstanceInfo.jsp");
	
			}
																
			requestDispatcher.forward(request, response);
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			request.setAttribute("errorMsg", "Invalid data provided. Please correct the input data");
			requestDispatcher = request.getRequestDispatcher("error.jsp");
			requestDispatcher.forward(request, response);
		}
	}

}
