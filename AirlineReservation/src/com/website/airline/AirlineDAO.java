package com.website.airline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;




public class AirlineDAO {
	
	
	private static Connection connection;

    public AirlineDAO() {
        connection = DbUtil.getConnection();
    }
    

    public void getAirportName(int airportCode) {
    	
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from airport");
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs!=null && rs.next())
            {
            	System.out.println(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public ArrayList<FlightDetailBean> getFlightDetails(String arrivalAirportCode, String departureAirportCode) {
    	
    	ArrayList<FlightDetailBean> flights = new ArrayList<FlightDetailBean>();
        try {        	        	
        	
        	String flightQuery = "select flight_number,weekdays,airline from flight f where upper(f.departure_airport_code)='"+departureAirportCode+"' and upper(f.arrival_airport_code)='"+arrivalAirportCode+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(flightQuery);
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs!=null && rs.next())
            {
            	FlightDetailBean bean = new FlightDetailBean();
            	bean.setFlightNumber(rs.getInt("flight_number"));
            	bean.setWeekdays(rs.getString("weekdays"));
            	bean.setAirline(rs.getString("airline"));
            	flights.add(bean);            	
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return flights;
    }
    
    
public int getAvailableSeats(int flightNum, String date) {
    	
    	
	int availSeats=0;
	int count=0;
        try {        	        	
        	
        	String seatQuery = "select a.total_number_of_seats from airplane a,flight_instance f where a.airplane_id=f.airplane_id and f.flight_number="+flightNum+" and f.flight_date=str_to_date('"+date+"','%m/%d/%Y')";
            PreparedStatement preparedStatement = connection.prepareStatement(seatQuery);
            
            ResultSet rs = preparedStatement.executeQuery();
            if(rs!=null && rs.next())
            {
            	availSeats=rs.getInt("total_number_of_seats");          	
            }
            
            
            String reservedQuery = "select count(1) from seat_reservation where flight_number="+flightNum+" and flight_date=str_to_date('"+date+"','%m/%d/%Y')";
            preparedStatement = connection.prepareStatement(reservedQuery);
            
            rs = preparedStatement.executeQuery();
            if(rs!=null && rs.next())
            {
            	count=rs.getInt(1);          	
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        if(availSeats==0 && count==0)
        	return -1;
        return availSeats-count;
    }
    
    


public ArrayList<FareBean> getFareDetails(int flightNumber) {
	
	ArrayList<FareBean> fareDetails = new ArrayList<FareBean>();
    try {        	        	
    	
    	String fareQuery = "select * from fare f where f.flight_number="+flightNumber+"";
        PreparedStatement preparedStatement = connection.prepareStatement(fareQuery);
        
        ResultSet rs = preparedStatement.executeQuery();
        while(rs!=null && rs.next())
        {
        	FareBean bean = new FareBean();
        	bean.setFlightNumber(rs.getInt("flight_number"));
        	bean.setAmount(rs.getInt("amount"));
        	bean.setFareCode(rs.getString("fare_code"));
        	bean.setRestrictions(rs.getString("restrictions"));
        	fareDetails.add(bean);
        	
        }
        
        
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return fareDetails;
    
}

public ArrayList<PassengerBean> getPassengerDetails(int flightNumber, String date) {
	
	ArrayList<PassengerBean> passengerDetails = new ArrayList<PassengerBean>();
    try {        	        	
    	
    	String passengerQuery = "select s.seat_number,f.scheduled_departure_time,f.scheduled_arrival_time,s.customer_name,s.customer_phone,(select a.name from airport a where a.airport_code=f.departure_airport_code) as depart_code, (select a.name from airport a where a.airport_code=f.arrival_airport_code) as arrival_code from seat_reservation s, flight f where s.flight_number=f.flight_number and s.flight_number="+flightNumber+" and s.flight_date=str_to_date('"+date+"','%m/%d/%Y')";
        PreparedStatement preparedStatement = connection.prepareStatement(passengerQuery);
        
        ResultSet rs = preparedStatement.executeQuery();
        while(rs!=null && rs.next())
        {
        	PassengerBean bean = new PassengerBean();
        	bean.setName(rs.getString("customer_name"));
        	bean.setArrAirport(rs.getString("arrival_code"));
        	bean.setDeptAirport(rs.getString("depart_code"));
        	bean.setPhone(rs.getString("customer_phone"));
        	bean.setSeatNo(rs.getString("seat_number"));
        	bean.setArrTime(rs.getString("scheduled_arrival_time"));
        	bean.setDepartTime(rs.getString("scheduled_departure_time"));
        	passengerDetails.add(bean);
        	
        }
        
        
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return passengerDetails;
    
}


public ArrayList<PassengerBean> getFlightInstances(String passengerName) {
	
	ArrayList<PassengerBean> passengerDetails = new ArrayList<PassengerBean>();
    try {        	        	
    	
    	String passengerQuery = "select s.flight_date,s.seat_number,f.scheduled_departure_time,f.scheduled_arrival_time,s.flight_number,s.customer_phone,f.airline,(select a.name from airport a where a.airport_code=f.departure_airport_code) as depart_code, (select a.name from airport a where a.airport_code=f.arrival_airport_code) as arrival_code from seat_reservation s, flight f where s.flight_number=f.flight_number and upper(s.customer_name)='"+passengerName.toUpperCase()+"'";
        PreparedStatement preparedStatement = connection.prepareStatement(passengerQuery);
        
        ResultSet rs = preparedStatement.executeQuery();
        while(rs!=null && rs.next())
        {
        	PassengerBean bean = new PassengerBean();
        	
        	bean.setArrAirport(rs.getString("arrival_code"));
        	bean.setDeptAirport(rs.getString("depart_code"));
        	bean.setPhone(rs.getString("customer_phone"));
        	bean.setSeatNo(rs.getString("seat_number"));
        	bean.setFlightNum(rs.getInt("flight_number"));
        	bean.setAirline(rs.getString("airline"));
        	bean.setArrTime(rs.getString("scheduled_arrival_time"));
        	bean.setDepartTime(rs.getString("scheduled_departure_time"));
        	bean.setFlightDate(rs.getString("flight_date"));
        	passengerDetails.add(bean);
        	
        }
        
        
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return passengerDetails;
    
}
    




public ArrayList<FlightBean> findFlightDetailsOneStop(String departureCode, String arrivalCode)
{
	ArrayList<FlightBean> flightList = new ArrayList<FlightBean>();

	departureCode=departureCode.toUpperCase();
	arrivalCode=arrivalCode.toUpperCase();
	
	
	try {        	        	
    	
    	String flightQuery1 = "Select DISTINCT f1.Flight_number as FirstFlightNumber, f2.Flight_number as SecondFlightNumber, f1.weekdays as firstweekday, f2.weekdays as secondweekday,  f1.Departure_airport_code as Origin,f1.Arrival_airport_code as FirstStop, f2.Arrival_airport_code as Destination from FLIGHT as f1 join FLIGHT as f2 on (f1.Arrival_airport_code=f2.Departure_airport_code) and timediff(f2.Scheduled_Departure_time,f1.Scheduled_arrival_time)>'01:00:00' where f1.Departure_airport_code='"+departureCode+"' AND f2.Arrival_airport_code='"+arrivalCode+"'";
    	
    	String flightQuery2 = "Select DISTINCT f1.Flight_number as FirstFlightNumber, f2.Flight_number as SecondFlightNumber, f1.scheduled_departure_time as first_depart, f1.scheduled_arrival_time as arr1, f2.scheduled_departure_time as depart2,f2.scheduled_arrival_time as arrival_2, f1.weekdays as firstweekday, f2.weekdays as secondweekday,  f1.Departure_airport_code as Origin,f1.Arrival_airport_code as FirstStop, f2.Arrival_airport_code as Destination from FLIGHT as f1 join FLIGHT as f2 on (f1.Arrival_airport_code=f2.Departure_airport_code) and timediff(f2.Scheduled_Departure_time,f1.Scheduled_arrival_time)>'01:00:00' where f1.Departure_airport_code='"+departureCode+"' AND f2.Arrival_airport_code='"+arrivalCode+"' and  (f1.Weekdays NOT like '%Mon%' or f2.Weekdays like '%Mon%') and (f1.Weekdays NOT like '%Tue%' or f2.Weekdays like '%Tue%') and (f1.Weekdays NOT like '%Wed%' or f2.Weekdays like '%Wed%') and (f1.Weekdays NOT like '%Thu%' or f2.Weekdays like '%Thu%') and (f1.Weekdays NOT like '%Fri%' or f2.Weekdays like '%Fri%') and (f1.Weekdays NOT like '%Sat%' or f2.Weekdays like '%Sat%') and (f1.Weekdays NOT like '%Sun%' or f2.Weekdays like '%Sun%')";
    	
    	
    	String flightQuery="Select DISTINCT f1.Flight_number as FirstFlightNumber, f2.Flight_number as SecondFlightNumber, f1.weekdays as firstweekday, f2.weekdays as secondweekday, f1.scheduled_departure_time as first_depart, f1.scheduled_arrival_time as arr1, f2.scheduled_departure_time as depart2,f2.scheduled_arrival_time as arrival_2, f1.Departure_airport_code as Origin,f1.Arrival_airport_code as FirstStop, f2.Arrival_airport_code as Destination from FLIGHT as f1 join FLIGHT as f2 on (f1.Arrival_airport_code=f2.Departure_airport_code) and timediff(f2.Scheduled_Departure_time,f1.Scheduled_arrival_time)>='01:00:00' where f1.Departure_airport_code='"+departureCode+"' AND f2.Arrival_airport_code='"+arrivalCode+"' and ( (f1.Weekdays NOT like '%Mon%' or f2.Weekdays like '%Mon%') or (f1.Weekdays NOT like '%Tue%' or f2.Weekdays like '%Tue%') or (f1.Weekdays NOT like '%Wed%' or f2.Weekdays like '%Wed%') or (f1.Weekdays NOT like '%Thu%' or f2.Weekdays like '%Thu%') and (f1.Weekdays NOT like '%Fri%' or f2.Weekdays like '%Fri%') or (f1.Weekdays NOT like '%Sat%' or f2.Weekdays like '%Sat%') or (f1.Weekdays NOT like '%Sun%' or f2.Weekdays like '%Sun%'))";
        
    	PreparedStatement preparedStatement = connection.prepareStatement(flightQuery);
        
        
        ResultSet rs = preparedStatement.executeQuery();
        while(rs!=null && rs.next())
        {
        	String firstWeekday = rs.getString("firstweekday");
        	String secondWeekday = rs.getString("secondweekday");
        	      	
        	
        	FlightBean bean = new FlightBean();
    		bean.setFirstFlightNumber(rs.getString("FirstFlightNumber"));
    		bean.setSecondFlightNumber(rs.getString("SecondFlightNumber"));
    		
    		bean.setFirstWeekday(firstWeekday);
    		bean.setSecondWeekday(secondWeekday);
    		
    		bean.setFirstArrive(rs.getString("arr1"));
    		bean.setFirstDepart(rs.getString("first_depart"));
    		bean.setSecondDepart(rs.getString("depart2"));
    		bean.setSecondArrive(rs.getString("arrival_2"));
    		
    		bean.setOrigin(rs.getString("Origin"));
    		bean.setFirstStop(rs.getString("FirstStop"));
    		bean.setDestination(rs.getString("Destination"));
    		
    		flightList.add(bean);
    		
    		
    		
        	/*
        	if(firstWeekday.length()<=secondWeekday.length() )
        	{
        	
        	String finalLCS = dao.lcs(firstWeekday, secondWeekday);
        	
        	finalLCS=finalLCS.trim();
        	if(finalLCS!=null && finalLCS.length()>1)
        	{
        		System.out.println(rs.getString("FirstFlightNumber"));
        		System.out.println(rs.getString("SecondFlightNumber"));
        		
        		FlightBean bean = new FlightBean();
        		bean.setFirstFlightNumber(rs.getString("FirstFlightNumber"));
        		bean.setSecondFlightNumber(rs.getString("SecondFlightNumber"));
        		
        		bean.setFirstWeekday(firstWeekday);
        		bean.setSecondWeekday(secondWeekday);
        		
        		bean.setOrigin(rs.getString("Origin"));
        		bean.setFirstStop(rs.getString("FirstStop"));
        		bean.setDestination(rs.getString("Destination"));
        		
        		flightList.add(bean);
        		
        		
        	}
        	else
        		continue;
        	}
        	else
        		continue;*/
        	
        	
        }
        
        
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
	
	
	return flightList;
}





public ArrayList<FlightBean> findFlightDetailsTwoStops(String departureCode, String arrivalCode)
	{
		ArrayList<FlightBean> flightList = new ArrayList<FlightBean>();
	
		departureCode=departureCode.toUpperCase();
		arrivalCode=arrivalCode.toUpperCase();
		
		
		try {        	        	
	    	
	    	String flightQuery1 = "Select DISTINCT f1.Flight_number as FirstFlightNumber, f2.Flight_number as SecondFlightNumber, f3.Flight_number as ThirdFlightNumber, f1.weekdays as firstweekday, f2.weekdays as secondweekday, f3.weekdays as thirdweekday, f1.Departure_airport_code as Origin,f1.Arrival_airport_code as FirstStop, f2.Arrival_airport_code as SecondStop , f3.Arrival_airport_code as Destination from FLIGHT as f1 join FLIGHT as f2 join FLIGHT as f3 on (f1.Arrival_airport_code=f2.Departure_airport_code AND f2.Arrival_airport_code=f3.Departure_airport_code) and timediff(f2.Scheduled_Departure_time,f1.Scheduled_arrival_time)>'01:00:00' and timediff(f3.Scheduled_Departure_time,f2.Scheduled_arrival_time)>'01:00:00' where f1.Departure_airport_code='"+departureCode+"' AND f3.Arrival_airport_code='"+arrivalCode+"'";
	    	
	    	//String flightQuery="Select DISTINCT f1.Flight_number as FirstFlightNumber, f2.Flight_number as SecondFlightNumber, f3.Flight_number as ThirdFlightNumber, f1.weekdays as firstweekday, f2.weekdays as secondweekday, f3.weekdays as thirdweekday, f1.Departure_airport_code as Origin,f1.Arrival_airport_code as FirstStop, f2.Arrival_airport_code as SecondStop , f3.Arrival_airport_code as Destination from FLIGHT as f1 join FLIGHT as f2 join FLIGHT as f3 on (f1.Arrival_airport_code=f2.Departure_airport_code AND f2.Arrival_airport_code=f3.Departure_airport_code) and timediff(f2.Scheduled_Departure_time,f1.Scheduled_arrival_time)>'01:00:00' and timediff(f3.Scheduled_Departure_time,f2.Scheduled_arrival_time)>'01:00:00' where f1.Departure_airport_code='"+departureCode+"' AND f3.Arrival_airport_code='"+arrivalCode+"' and (f1.Weekdays NOT like '%Mon%' or f2.Weekdays like '%Mon%') and (f1.Weekdays NOT like '%Tue%' or f2.Weekdays like '%Tue%') and (f1.Weekdays NOT like '%Wed%' or f2.Weekdays like '%Wed%') and (f1.Weekdays NOT like '%Thu%' or f2.Weekdays like '%Thu%') and (f1.Weekdays NOT like '%Fri%' or f2.Weekdays like '%Fri%') and (f1.Weekdays NOT like '%Sat%' or f2.Weekdays like '%Sat%') and (f1.Weekdays NOT like '%Sun%' or f2.Weekdays like '%Sun%') and (f2.Weekdays NOT like '%Mon%' or f3.Weekdays like '%Mon%') and (f2.Weekdays NOT like '%Tue%' or f3.Weekdays like '%Tue%') and (f2.Weekdays NOT like '%Wed%' or f3.Weekdays like '%Wed%') and (f2.Weekdays NOT like '%Thu%' or f3.Weekdays like '%Thu%') and (f2.Weekdays NOT like '%Fri%' or f3.Weekdays like '%Fri%') and (f2.Weekdays NOT like '%Sat%' or f3.Weekdays like '%Sat%') and (f2.Weekdays NOT like '%Sun%' or f3.Weekdays like '%Sun%')";
	    	
	        
	    	String flightQuery = "Select DISTINCT f1.Flight_number as FirstFlightNumber, f2.Flight_number as SecondFlightNumber, f3.Flight_number as ThirdFlightNumber,f1.scheduled_departure_time as first_depart, f1.scheduled_arrival_time as arr1, f2.scheduled_departure_time as depart2,f2.scheduled_arrival_time as arrival_2, f3.scheduled_departure_time as depart3, f3.scheduled_arrival_time as origin_arrival_time, f1.weekdays as firstweekday, f2.weekdays as secondweekday, f3.weekdays as thirdweekday, f1.Departure_airport_code as Origin,f1.Arrival_airport_code as FirstStop, f2.Arrival_airport_code as SecondStop , f3.Arrival_airport_code as Destination from FLIGHT as f1 join FLIGHT as f2 join FLIGHT as f3 on (f1.Arrival_airport_code=f2.Departure_airport_code AND f2.Arrival_airport_code=f3.Departure_airport_code) and timediff(f2.Scheduled_Departure_time,f1.Scheduled_arrival_time)>='01:00:00' and timediff(f3.Scheduled_Departure_time,f2.Scheduled_arrival_time)>='01:00:00' where f1.Departure_airport_code='"+departureCode+"' AND f3.Arrival_airport_code='"+arrivalCode+"' and ((f1.Weekdays NOT like '%Mon%' or f2.Weekdays like '%Mon%') or (f1.Weekdays NOT like '%Tue%' or f2.Weekdays like '%Tue%') or (f1.Weekdays NOT like '%Wed%' or f2.Weekdays like '%Wed%') or (f1.Weekdays NOT like '%Thu%' or f2.Weekdays like '%Thu%') or (f1.Weekdays NOT like '%Fri%' or f2.Weekdays like '%Fri%') or (f1.Weekdays NOT like '%Sat%' or f2.Weekdays like '%Sat%') or (f1.Weekdays NOT like '%Sun%' or f2.Weekdays like '%Sun%')) and ((f2.Weekdays NOT like '%Mon%' or f3.Weekdays like '%Mon%') or (f2.Weekdays NOT like '%Tue%' or f3.Weekdays like '%Tue%') or (f2.Weekdays NOT like '%Wed%' or f3.Weekdays like '%Wed%') or (f2.Weekdays NOT like '%Thu%' or f3.Weekdays like '%Thu%') or (f2.Weekdays NOT like '%Fri%' or f3.Weekdays like '%Fri%') or (f2.Weekdays NOT like '%Sat%' or f3.Weekdays like '%Sat%') or (f2.Weekdays NOT like '%Sun%' or f3.Weekdays like '%Sun%'))";
	    	PreparedStatement preparedStatement = connection.prepareStatement(flightQuery);
	        
	        ResultSet rs = preparedStatement.executeQuery();
	        while(rs!=null && rs.next())
	        {
	        	String firstWeekday = rs.getString("firstweekday");
	        	String secondWeekday = rs.getString("secondweekday");
	        	String thirdWeekday = rs.getString("thirdweekday");	        	
	        	
	        	FlightBean bean = new FlightBean();
        		bean.setFirstFlightNumber(rs.getString("FirstFlightNumber"));
        		bean.setSecondFlightNumber(rs.getString("SecondFlightNumber"));
        		bean.setThirdFlightNumber(rs.getString("ThirdFlightNumber"));
        		bean.setFirstWeekday(firstWeekday);
        		bean.setSecondWeekday(secondWeekday);
        		bean.setThirdWeekday(thirdWeekday);
        		
        		bean.setFirstArrive(rs.getString("arr1"));
        		bean.setFirstDepart(rs.getString("first_depart"));
        		bean.setSecondDepart(rs.getString("depart2"));
        		bean.setSecondArrive(rs.getString("arrival_2"));
        		bean.setThirdDepart(rs.getString("depart3"));
        		bean.setThridArrive(rs.getString("origin_arrival_time"));
        		
        		bean.setOrigin(rs.getString("Origin"));
        		bean.setFirstStop(rs.getString("FirstStop"));
        		bean.setSecondStop(rs.getString("SecondStop"));
        		bean.setDestination(rs.getString("Destination"));
        		
        		flightList.add(bean);
	        	
	        /*	if(firstWeekday.length()<=secondWeekday.length() && secondWeekday.length()<=thirdWeekday.length())
	        	{
	        	
	        	String lcs1 = dao.lcs(firstWeekday, secondWeekday);
	        	String finalLCS = dao.lcs(lcs1, thirdWeekday);
	        	finalLCS=finalLCS.trim();
	        	if(finalLCS!=null && finalLCS.length()>1)
	        	{
	        		System.out.println(rs.getString("FirstFlightNumber"));
	        		System.out.println(rs.getString("SecondFlightNumber"));
	        		System.out.println(rs.getString("ThirdFlightNumber"));
	        		
	        		
	        		FlightBean bean = new FlightBean();
	        		bean.setFirstFlightNumber(rs.getString("FirstFlightNumber"));
	        		bean.setSecondFlightNumber(rs.getString("SecondFlightNumber"));
	        		bean.setThirdFlightNumber(rs.getString("ThirdFlightNumber"));
	        		bean.setFirstWeekday(firstWeekday);
	        		bean.setSecondWeekday(secondWeekday);
	        		bean.setThirdWeekday(thirdWeekday);
	        		bean.setOrigin(rs.getString("Origin"));
	        		bean.setFirstStop(rs.getString("FirstStop"));
	        		bean.setSecondStop(rs.getString("SecondStop"));
	        		bean.setDestination(rs.getString("Destination"));
	        		
	        		flightList.add(bean);
	        	}
	        	else
	        		continue;
	        	}
	        	else
	        		continue;
	        	*/
	        	
	        }
	        
	        
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
		
		
		return flightList;
	}



    public static void main(String[] args) {
		
    	AirlineDAO dao = new AirlineDAO();
    	//dao.getAirportName(0);
    	
    	
    	/*BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Longest Common Subsequence Algorithm Test\n");
 
        System.out.println("\nEnter string 1");
        String str1;*/
		try {
			/*str1 = br.readLine();
			 System.out.println("\nEnter string 2");
		        String str2 = br.readLine();

		        String result = dao.lcs(str1, str2);*/
		 
		      //  System.out.println("\nLongest Common Subsequence : "+ result);
		        
		        
		       // dao.findFlightDetailsTwoStops("CLE", "PDX");
		        dao.findFlightDetailsOneStop("CLE", "PDX");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
       
 
         
    }
    	
    	
    	


    public String lcs(String str1, String str2)
    {
        int l1 = str1.length();
        int l2 = str2.length();
 
        int[][] arr = new int[l1 + 1][l2 + 1];
 
        for (int i = l1 - 1; i >= 0; i--)
        {
            for (int j = l2 - 1; j >= 0; j--)
            {
                if (str1.charAt(i) == str2.charAt(j))
                    arr[i][j] = arr[i + 1][j + 1] + 1;
                else 
                    arr[i][j] = Math.max(arr[i + 1][j], arr[i][j + 1]);
            }
        }
 
        int i = 0, j = 0;
        StringBuffer sb = new StringBuffer();
        while (i < l1 && j < l2) 
        {
            if (str1.charAt(i) == str2.charAt(j)) 
            {
                sb.append(str1.charAt(i));
                i++;
                j++;
            }
            else if (arr[i + 1][j] >= arr[i][j + 1]) 
                i++;
            else
                j++;
        }
        return sb.toString();
    }
    
    
    

}