CREATE TABLE if not exists job_type
(
  id bigint(20) NOT NULL,
    createdon datetime,
    modifiedon datetime,
    createdby bigint(20),
    modifiedby bigint(20),
  name varchar(255) default NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into job_type (id, name) values (1, 'SYNCHRONOUS');
insert into job_type (id, name) values (2, 'ASSYNCHRONOUS'); 
