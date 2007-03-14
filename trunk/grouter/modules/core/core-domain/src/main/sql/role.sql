CREATE TABLE role (
  id bigint(20) NOT NULL ,
  name varchar(255) NOT NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into role (id, name) values (1, 'ROLE_ADMIN');
insert into role (id, name) values (2, 'ROLE_REVIEWER');
insert into role (id, name) values (3, 'ROLE_SUPER_REVIEWER');
insert into role (id, name) values (4, 'ROLE_EDITOR');