CREATE TABLE role (
  id bigint(20) NOT NULL auto_increment,
  name varchar(255) NOT NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into role (name) values ('ROLE_ADMIN'); -- 1
insert into role (name) values ('ROLE_VIEWER'); -- 2
