insert into Users values ('Md. Shah Faisal Raju', '01819450018','22448800')

insert into Vehicle values ('Dhaka','D',11,1000,1)



select * from Users
select * from Vehicle
drop table Vehicle
drop table Users

select Users.UserId, Name, MobileNo, VehicleLicensePlateCity
from Users 
inner join Vehicle
on Users.UserId = Vehicle.UserId