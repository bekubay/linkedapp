INSERT INTO linkedapp.ROLE(role_id, role) VALUES (1, 'ROLE_USER');
INSERT INTO linkedapp.ROLE(role_id, role) VALUES (2, 'ROLE_ADMIN');

insert into linkedapp.user(id, username, password, firstname, lastname, email, dob,active)
values(null,'user1', 'pass1', 'Brhane', 'Bahrishum','bteka@mum.edu','1990-10-10',1);
insert into linkedapp.user(id, username, password, firstname, lastname, email, dob,active)
values(null,'user2', 'pass2', 'Ekubay', 'Tafere','etafere@mum.edu','1999-10-10',1);
insert into linkedapp.user(id, username, password, firstname, lastname, email, dob,active)
values(null,'user3', 'pass3', 'Girmay', 'Haile','ghaile@mum.edu','1995-10-10',1);
insert into linkedapp.user(id, username, password, firstname, lastname, email, dob,active)
values(null,'user4', 'pass4', 'Jipeng', 'Xao','jxao@mum.edu','1996-11-10',1);
insert into linkedapp.user(id, username, password, firstname, lastname, email, dob,active)
values(null,'user5', 'pass5', 'Alula', 'Halefom','bteka@mum.edu','1990-10-10',1);
insert into linkedapp.user(id, username, password, firstname, lastname, email, dob,active)
values(null,'adm1', 'pass1', 'Haben', 'Haile','etafere@mum.edu','1999-10-10',1);
insert into linkedapp.user(id, username, password, firstname, lastname, email, dob,active)
values(null,'adm2', 'pass2', 'Teklay', 'Tadele','ghaile@mum.edu','1995-10-10',1);
insert into linkedapp.user(id, username, password, firstname, lastname, email, dob,active)
values(null,'adm3', 'pass3', 'Tewelde', 'Shushay','jxao@mum.edu','1996-11-10',1);

insert into linkedapp.user_role(user_id, role_id) values (1, 1);
insert into linkedapp.user_role(user_id, role_id) values (2, 1);
insert into linkedapp.user_role(user_id, role_id) values (3, 1);
insert into linkedapp.user_role(user_id, role_id) values (4, 1);
insert into linkedapp.user_role(user_id, role_id) values (5, 1);
insert into linkedapp.user_role(user_id, role_id) values (6, 2);
insert into linkedapp.user_role(user_id, role_id) values (7, 2);
insert into linkedapp.user_role(user_id, role_id) values (8, 2);
