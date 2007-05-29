CREATE TABLE user_role (
  id bigint(20) NOT NULL auto_increment,
  user_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL,
  PRIMARY KEY  (id),
--  KEY (user_id),
--  KEY (role_id),
  FOREIGN KEY (user_id) REFERENCES user (id),
  FOREIGN KEY (role_id) REFERENCES role(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- admin
insert into user_role(user_id, role_id) values( ( select id from user where username = 'admin' ), 1);

-- super reviewers
insert into user_role(user_id, role_id) values( ( select id from user where username = 'admin' ), 3);

-- reviewers
insert into user_role(user_id, role_id) values( ( select id from user where username = 'admin' ), 2);
