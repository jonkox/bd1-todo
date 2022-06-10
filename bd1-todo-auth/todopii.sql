SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT;
SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS;
SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION;
SET NAMES utf8;
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO';
SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0; 



-- Creating database
-- ----------------------------------------------
drop database todopii;
create database todopii;
use todopii;
-- ----------------------------------------------


-- Creating user
-- ----------------------------------------------
Drop user if exists 'authappuser'@'localhost';
CREATE USER IF NOT EXISTS 'authappuser'@'localhost' IDENTIFIED BY 'authapppassword';
GRANT ALL PRIVILEGES ON *.* TO 'authappuser'@'localhost' WITH GRANT OPTION;
-- ----------------------------------------------


-- creating table clients
-- ----------------------------------------------
create table clients(
clientid varchar(50) primary key,
clientSecret varchar(50) not null
);


-- creating table sessions
-- ----------------------------------------------
create table sessions(
sessionid varchar(50) primary key,
clientid varchar (50),
createdat timestamp not null,
foreign key(clientid) references clients(clientid)
);



-- PROCEDURE CREATE SESSION
-- Recibe dos parametros: el ID del cliente y el tiempo (numero de tipo INT) para validar el tiempo maximo para que una sesion sea valida
-- ----------------------------------------------
drop procedure if exists create_session;

delimiter $$
create PROCEDURE create_session(in client_id_param varchar(50), time_valid int)
begin
	declare client_id_exists bool;
    declare session_exists bool;
    declare session_diff int;

	select if(count(*) = 1, true, false) into client_id_exists from clients where clientid = client_id_param;

	if client_id_exists = true then
		-- select "existe";
        select if(count(*) = 1, true, false) into session_exists from sessions where clientid = client_id_param;
        if session_exists then
			-- select 'cliente existe, sesion existe';
            select minute(timediff(utc_timestamp(),createdat)) into session_diff from sessions where clientid = client_id_param;
            if session_diff <= time_valid then
				select clientid,sessionid,createdat, if(minute(timediff(utc_timestamp(), createdat)) <= 30, 'ACTIVE', 'INACTIVE') as sessionStatus from sessions where clientid = client_id_param;
				-- select 'cliente existe, session existe, session activa';
			else
				-- select 'cliente existe, session existe, session inactiva';
                start transaction;
                update sessions set createdat = utc_timestamp() where clientid = client_id_param;
                commit;
                
                select clientid,sessionid,createdat, if(minute(timediff(utc_timestamp(), createdat)) <= 30, 'ACTIVE', 'INACTIVE') as sessionStatus from sessions where clientid = client_id_param;
                
			end if;
		else 
			start transaction;
			insert into sessions(clientid,sessionid,createdat) values(client_id_param, uuid(), utc_timestamp());
            commit;
			-- select 'cliente existe, sesion NO existe';
		end if;
	else
		select "no existe";
	end if;

end
$$




-- PROCEDURE VALIDATE SESSION
-- Recibe dos parametros: el ID de la sesion y el tiempo (numero de tipo INT) para validar el tiempo maximo para que una sesion sea valida
-- ----------------------------------------------
drop procedure if exists validate_session;
delimiter $$
create procedure validate_session(in session_id_param varchar(50), time_valid int)
begin
	
    declare session_exists bool;
    declare session_diff int;
    
	select if(count(*) = 1, true, false) into session_exists from sessions where sessionid = session_id_param;
    
    if session_exists = true then
    
		select clientid,sessionid,createdat, if(minute(timediff(utc_timestamp(), createdat)) <= time_valid, 'ACTIVE', 'INACTIVE') as sessionStatus from sessions where sessionid = session_id_param;
        
    else
    
		select 'Sesion NO EXISTE';
        
	end if;

end
$$




-- INSERTING INTO CLIENTS
-- ----------------------------------------------
insert into clients(clientid,clientSecret) values ('jonko','1234');
insert into clients(clientid,clientSecret) values ('allan','1234');
insert into clients(clientid,clientSecret) values ('martin','1234');
insert into clients(clientid,clientSecret) values ('sharon','1234');
insert into clients(clientid,clientSecret) values ('Daniel','1234');


insert into sessions(sessionid,clientid,createdat) values ('3','jonko',now());


select * from clients;
select * from sessions;

-- Caso 1: Sesion Invalida
call validate_session('1',30);



-- Caso 1: CLIENTE EXISTE, SESION NO EXISTE
call create_session('allan',30);
-- Caso 2: CLIENTE EXISTE, SESION EXISTE
call create_session('jonko',30);
-- Caso 3: CLIENTE NO EXISTE
call create_session('cliente-43',30);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT;
SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS;
SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION;
SET SQL_NOTES=@OLD_SQL_NOTES; 