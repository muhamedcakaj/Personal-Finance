create database Bilanci
use bilanci
create table bilanci(
id int primary key identity(1,1),
bilanci float not null,
username varchar(30) unique not null,
)

Select *
from bilanci

Select MAX(b.bilanci)
from bilanci b

Select TOP 1 b.username,b.bilanci
from bilanci b
where b.bilanci >=( Select MAX(b.bilanci)
from bilanci b)

Select TOP 1 b.username,b.bilanci
from bilanci b
where b.bilanci <=( Select min(b.bilanci)
from bilanci b)

DELETE FROM Bilanci WHERE LOWER(username) = LOWER('meti_cakaj')