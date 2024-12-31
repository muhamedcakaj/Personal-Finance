create database Transactions

use Transactions

create table transactions(
id int primary key identity(1,1),
userUsername varchar(30)not null,
purpose varchar(35) not null,
category varchar(9) not null,
sum float not null,
transaction_date DATETIME NOT NULL
)

insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meticakaj','ssss','Food,',20.5,'2024-1-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meticakaj','mmmm','Bills,',20.5,'2024-2-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meti_cakaj','aaa','Car',20.5,'2024-3-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meti_cakaj','aaa','Food',20.5,'2024-4-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meti_cakaj','aaa','Car',20.5,'2024-1-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meti_cakaj','aaa','Bills',20.5,'2024-2-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meti_cakaj','aaa','Shopping',20.5,'2024-6-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meti_cakaj','aaa','Others',20.5,'2024-5-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meti_cakaj','aaa','Shopping',60.5,'2024-7-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meti_cakaj','aaa','Bills',20.5,'2024-8-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meti_cakaj','aaa','Car',30.5,'2024-9-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meti_cakaj','aaa','Food',25.5,'2024-9-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meti_cakaj','aaa','Bills',22.5,'2024-10-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meti_cakaj','aaa','Shopping',15.5,'2024-10-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meti_cakaj','aaa','Shopping',15.5,'2024-11-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meti_cakaj','aaa','Shopping',15.5,'2024-12-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meti_cakaj','aaa','Shopping',15.5,'2024-5-24 00:00:00.000')
insert into transactions(userUsername,purpose,category,sum,transaction_date) values('meti_cakaj','aaa','Shopping',15.5,'2024-3-24 00:00:00.000')

Select *
from transactions

SELECT COUNT(*) as 'Count'
FROM transactions
WHERE DATEPART(yyyy, transaction_date) = 2024
GROUP BY DATEPART(mm, transaction_date)

--Grumbullo transaksionet qe jane bere varsisht nga muaji
Select COUNT(*) as 'Count'
from transactions
group by transaction_date

--Gjej userin qe ka bere me se shumti transaksione
Select TOP 1 MAX(t.userUsername) as userName,COUNT(t.userUsername) as Number
from Transactions t
where t.userUsername = (Select MAX(userUsername)
                        from transactions)
group by t.userUsername

--Gjej userin qe ka bere me se pakti transaksione
Select TOP 1 MIN(t.userUsername) as userName,COUNT(t.userUsername) as Number
from Transactions t
where t.userUsername = (Select MIN(userUsername)
                        from transactions)
group by t.userUsername

--Gjej kategorin e transkasionit me te perdorur
Select TOP 1 t.category,COUNT(*) as number
from transactions t
group by t.category
having COUNT(*) >=(Select TOP 1 Count(t.category)
                 from transactions t
                 group by t.category)


--Gjej kategorin e transaksionit me pak te perdorur
Select TOP 1 t.category,COUNT(*) as number
from transactions t
group by t.category
having COUNT(*) <(Select TOP 1 Count(t.category)
                 from transactions t
                 group by t.category)


--Gjej numrin e transaksioneve
Select count(*) as totalNumberOfTransactions
from transactions

--Delete nga transkasionet
DELETE FROM Transactions WHERE LOWER(userUsername) = LOWER('meticakaj')