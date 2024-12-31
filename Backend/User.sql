create database [User]
USE [User]

create table Userr(
id int primary key identity(1,1),
name varchar(25) not null,
surname varchar(25)not null,
userName varchar(30)not null Unique,
password varchar(12)not null,
email varchar(40) not null,
mfaCode int default 0,
mfaCodeExpiration DateTime,
role varchar(12) default 'ROLE_USER'
)

DROP TABLE Userr

ALTER TABLE Userr
ADD CONSTRAINT UQ_Userr_UserName UNIQUE (UserName);

Insert into Userr (name,surname,userName,password,email,mfacode,role) values('admin','admin','admin','admin10','muhametcakaj57@gmail.com',0,'ROLE_ADMIN')
Insert into Userr (name,surname,userName,password,email,mfaCode,role) values('user','user','user','user10','user57@gmail.com',0,'ROLE_USER')

ALTER TABLE Userr
ADD email varchar(40) not null;

ALTER TABLE Userr
add mfaCode int

ALTER TABLE Userr
add mfaCodeExpiration DateTime; 

Alter Table Userr
add role varchar(12)

ALTER TABLE Userr
ALTER COLUMN role VARCHAR(12);

Select *
from Userr

--Gjej numrin e te gjith userave
Select Count(*) as CurrentUserNumber
from Userr
