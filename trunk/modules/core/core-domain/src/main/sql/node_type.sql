CREATE TABLE node_type (
  id bigint(20) NOT NULL,
      createdon datetime,
      modifiedon datetime,
      createdby bigint(20),
      modifiedby bigint(20),
    name varchar(255) default NULL,
  position bigint(20) default NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into node_type (id, createdon, modifiedon,createdby,modifiedby,name, position) values (1,NOW(),NOW(),1,1, 'File to file', 1);
insert into node_type (id, createdon, modifiedon,createdby,modifiedby, name, position) values (2, NOW(),NOW(),1,1,'File to email', 2);
