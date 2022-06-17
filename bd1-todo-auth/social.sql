
DROP DATABASE IF EXISTS todosocial;
CREATE DATABASE IF NOT EXISTS todosocial;
use todosocial;

-- Creating user
-- ----------------------------------------------
Drop user if exists 'todosocialuser'@'localhost';
CREATE USER IF NOT EXISTS 'todosocialuser'@'localhost' IDENTIFIED BY 'todosocialpass';
GRANT ALL PRIVILEGES ON *.* TO 'todosocialuser'@'localhost' WITH GRANT OPTION;
-- ----------------------------------------------



create table if not exists rating(
	id int primary key auto_increment,
    ratingvalue int not null,
    todoid varchar(50) not null,
    clientid varchar(50) not null,
    createdat datetime not null

);
-------------------------------------------------------------------------------------------------------------------------

create table if not exists review(
	id int primary key auto_increment,
    todoid varchar(50) not null,
    createdat datetime not null,
    comentario varchar(200) not null,
    clientid varchar(50)
);
-------------------------------------------------------------------------------------------------------------------------



drop procedure if exists calculate_average_rating;
delimiter $$
create procedure  calculate_average_rating(in todoidParam varchar(50))
begin
	select avg(ratingvalue) as ratingAvg from rating where todoid = todoidparam;
end$$
-- -------------------------------------------------------------------------------------------------------------------------


drop procedure if exists insert_rating;
delimiter $$
create procedure  insert_rating(in ratingvalue_param int,
									todoid_param varchar(50),
									clientid_param varchar(50),
									createdat_param datetime)
begin
	insert into rating(ratingvalue,todoid,clientid,createdat) values (ratingvalue_param,todoid_param,clientid_param,now());
end$$
-------------------------------------------------------------------------------------------------------------------------
drop procedure if exists insert_review;
delimiter $$
create procedure  insert_review(in todoid_param varchar(50),
									createdat_param datetime,
									comentario_param varchar(200),
									clientid_param varchar(50))
begin
	insert into review(todoid,createdat,comentario,clientid) values (todoid_param, createdat_param, comentario_param, clientid_param);
end$$
-------------------------------------------------------------------------------------------------------------------------

select id, todoid, createdat, comentario, clientid from review where todoid = 'hola';

call insert_review('todo',now(),'muy bueno','jonko');

call insert_rating(5,'todo1','jonko',now());
call calculate_average_rating('todo');


select * from rating;
select * from review;

delete from rating where todoid = 'todo1' and clientid = 'jonko';



Error Code: 1175. You are using safe update mode and you tried to update a table without a WHERE that uses a KEY column.  To disable safe mode, toggle the option in Preferences -> SQL Editor and reconnect.

Error Code: 1064 You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'Error Code: 1175. You are using safe update mode and you tried to update a table' at line 1

