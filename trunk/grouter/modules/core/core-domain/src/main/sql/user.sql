CREATE TABLE user (
  id bigint(20) NOT NULL auto_increment,
  username varchar(255) default NULL,
  pwd varchar(255) default NULL,
  email varchar(255) default NULL,
  fullname varchar(50) default NULL,
  address_fk bigint(20) default NULL,
  createdby bigint(20) default NULL,
  PRIMARY KEY  (id),
  FOREIGN KEY (createdby) REFERENCES  user (id),
  FOREIGN KEY (address_fk) REFERENCES  address (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into user (id, username, pwd, email, fullname) values (10000, 'admin', 'nimda', '','Administrator');
insert into user (id, username, pwd, email, fullname) values (10001, 'view', 'view', '','Viewer');
