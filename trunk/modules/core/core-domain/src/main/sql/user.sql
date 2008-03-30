CREATE TABLE user (
  id bigint(20) NOT NULL auto_increment,
  username varchar(255) default NULL,
  firstname varchar(50) default NULL,
  lastname varchar(50) default NULL,
  password varchar(255) default NULL,
  address_fk bigint(20) default NULL,
  createdon datetime ,
  modifiedon datetime ,
  createdby bigint(20) ,
  modifiedby bigint(20),
  expireson datetime,
  remaininglogonattempts int,
  user_state_fk bigint(20) not null,
  locale_fk bigint(20),
  PRIMARY KEY  (id),
  FOREIGN KEY (createdby) REFERENCES  user (id),
  FOREIGN KEY (modifiedby) REFERENCES  user (id),
  FOREIGN KEY (address_fk) REFERENCES  address (id),
  FOREIGN KEY (locale_fk) REFERENCES  locale (id),
  FOREIGN KEY (user_state_fk) REFERENCES  user_state (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into user (id, username, user_state_fk, createdon , modifiedon) values (1, 'system', 3, NOW() ,NOW());
insert into user (id, username, password, user_state_fk, createdon , modifiedon) values (2, 'admin', 'nimda', 3, NOW() ,NOW());
insert into user (id, username, password, user_state_fk, createdon , modifiedon) values (3, 'view', 'view', 3, NOW() ,NOW());

