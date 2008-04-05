CREATE TABLE node_type (
  id bigint(20) NOT NULL,
  idno varchar(36) not null,
      createdon datetime,
      modifiedon datetime,
      createdby bigint(20),
      modifiedby bigint(20),
    name varchar(255) default NULL,
  position bigint(20) default NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into node_type (id, name, position) values (1, 'File to file', 1);
insert into node_type (id, name, position) values (2, 'File to email', 2);
