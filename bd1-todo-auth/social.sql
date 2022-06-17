
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
-- -------------------------------------------------------------------------------------------------------------------------

create table if not exists review(
	id int primary key auto_increment,
    todoid varchar(50) not null,
    createdat datetime not null,
    comentario varchar(200) not null,
    clientid varchar(50)
);
-- -------------------------------------------------------------------------------------------------------------------------



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
-- -----------------------------------------------------------------------------------------------------------------------
drop procedure if exists insert_review;
delimiter $$
create procedure  insert_review(in todoid_param varchar(50),
									createdat_param datetime,
									comentario_param varchar(200),
									clientid_param varchar(50))
begin
	insert into review(todoid,createdat,comentario,clientid) values (todoid_param, createdat_param, comentario_param, clientid_param);
end$$
-- -----------------------------------------------------------------------------------------------------------------------



call insert_review('todo-1',now(),'muy bueno','jonko');
call insert_review('todo-2',now(),'muy malo','allan');
call insert_review('todo-3',now(),'muy bueno','martin');
call insert_review('todo-4',now(),'regular','daniel');
call insert_review('todo-5',now(),'increible','ervin');
call insert_review('todo-6',now(),'perfecto','sharon');
call insert_review('todo-7',now(),'masomenos','Fabio');
call insert_review('todo-8',now(),'bueno','Andre');
call insert_review('todo-9',now(),'muy bueno','Fab');

call insert_rating(5,'todo-1','jonko',now());
call insert_rating(4,'todo-2','allan',now());
call insert_rating(5,'todo-3','martin',now());
call insert_rating(3,'todo-4','daniel',now());
call insert_rating(1,'todo-5','ervin',now());
call insert_rating(2,'todo-6','sharon',now());
call insert_rating(4,'todo-7','fabio',now());
call insert_rating(4,'todo-8','andre',now());
call insert_rating(5,'todo-9','fab',now());

-- select * from rating;
-- select * from review;

