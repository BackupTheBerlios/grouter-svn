CREATE TABLE role (
  id bigint(20) NOT NULL ,
  idno varchar(36) not null,
  createdon datetime,
  modifiedon datetime,
  name varchar(255) NOT NULL,
  displayname varchar(255) NOT NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into role (id, name, displayname) values (1, 'ROLE_ADMIN', 'admin');
insert into role (id, name, displayname) values (2, 'ROLE_REVIEWER', 'reviewer');
insert into role (id, name, displayname) values (3, 'ROLE_SUPER_REVIEWER', 'superreviewer');
insert into role (id, name, displayname) values (4, 'ROLE_EDITOR', 'editor');