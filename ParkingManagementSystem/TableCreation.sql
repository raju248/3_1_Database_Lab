create table Users
(
	UserId int primary key identity(1,1),
	Name varchar(50) NOT NULL,
	MobileNo varchar(11) unique NOT NULL CHECK(len(MobileNo)=11 and MobileNo NOT LIKE '% %' and MobileNo LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' ) ,
	Password varchar(100) NOT NULL CHECK(len(Password)>=6)
)


create table Vehicle
(
	VehicleId int primary key identity(1,1),

	--VehicleLicenseNumber
	VehicleLicensePlateCity varchar(20) NOT NULL,
	VehicleLicensePlateClassLetter varchar(10) NOT NULL,
	VehicleLicensePlateNumber int NOT NULL,
	VechicleNumber int NOT NULL unique,

	UserId int NOT NULL foreign key references Users(UserId),
)