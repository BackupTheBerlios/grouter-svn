CREATE TABLE user_role (
  id bigint(20) NOT NULL auto_increment,
  createdon datetime,
  modifiedon datetime,
  createdby bigint(20),
  modifiedby bigint(20),
  user_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL,
  PRIMARY KEY  (id),
  FOREIGN KEY (role_id) REFERENCES role (id),
  FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Add all admin users
insert into user_role(user_id, role_id, createdon, modifiedon,createdby,modifiedby) values( ( select id from user where username = 'admin' ), 1,NOW(),NOW(),1,1);
-- Add all reviewers
insert into user_role(user_id, role_id, createdon, modifiedon,createdby,modifiedby) values( ( select id from user where username = 'admin' ), 2,NOW(),NOW(),1,1);
-- Add all super reviewers
insert into user_role(user_id, role_id, createdon, modifiedon,createdby,modifiedby) values( ( select id from user where username = 'admin' ), 3,NOW(),NOW(),1,1);
-- Add role editor
insert into user_role(user_id, role_id, createdon, modifiedon,createdby,modifiedby) values( ( select id from user where username = 'admin' ), 4,NOW(),NOW(),1,1);
