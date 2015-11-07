use airline_system;
DELIMITER $$
create trigger seat_reservation_check_2

after insert on seat_reservation
for each row
begin
declare number_avail_seats integer;
declare new_num_seats integer;

set number_avail_seats:= (select i.number_of_available_seats from flight_instance i where i.flight_number = new.flight_number and i.flight_date=str_to_date(new.flight_Date,'%m/%d/%Y'));
set new_num_seats:=number_avail_seats-1;

  update flight_instance i set i.number_of_available_seats=new_num_seats where i.flight_number = new.flight_number and i.flight_date=str_to_date(new.flight_Date,'%m/%d/%Y');

END $$

DELIMITER ;