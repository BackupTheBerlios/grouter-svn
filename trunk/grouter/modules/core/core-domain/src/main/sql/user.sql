CREATE TABLE user (
  id bigint(20) NOT NULL auto_increment,
  username varchar(255) default NULL,
  firstname varchar(50) default NULL,
  lastname varchar(50) default NULL,
  password varchar(255) default NULL,
  address_fk bigint(20) default NULL,
  createdon datetime,
  modifiedon datetime,
  expireson datetime,
  remaininglogonattempts int,
  user_state_fk bigint(20),
  createdby bigint(20) default NULL,
  PRIMARY KEY  (id),
  FOREIGN KEY (createdby) REFERENCES  user (id),
  FOREIGN KEY (address_fk) REFERENCES  address (id),
  FOREIGN KEY (user_state_fk) REFERENCES  user_state (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



insert into user (id, username, password) values (1, 'admin', 'nimda');
insert into user (id, username, password) values (2, 'view', 'view');

