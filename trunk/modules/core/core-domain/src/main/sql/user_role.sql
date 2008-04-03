CREATE TABLE user_role (
  id bigint(20) NOT NULL auto_increment,
--  idno varchar(36) not null,
  createdon timestamp,
  modifiedon timestamp,
  createdby bigint(20),
  modifiedby bigint(20),
  user_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL,
  PRIMARY KEY  (id),
--  KEY (user_id),
--  KEY (role_id),
  FOREIGN KEY (user_id) REFERENCES user (id),
  FOREIGN KEY (role_id) REFERENCES role(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Add all admin users
insert into user_role(user_id, role_id) values( ( select id from user where username = 'admin' ), 1);
-- Add all reviewers
insert into user_role(user_id, role_id) values( ( select id from user where username = 'admin' ), 2);
-- Add all super reviewers
insert into user_role(user_id, role_id) values( ( select id from user where username = 'admin' ), 3);
