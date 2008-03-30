CREATE TABLE if not exists job_type
(
  id bigint(20) NOT NULL,
  idno varchar(36) not null,
    createdon datetime,
    modifiedon datetime,
  name varchar(255) default NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into job_type (id, name) values (1, 'backup'); 
