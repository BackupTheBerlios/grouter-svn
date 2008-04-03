CREATE TABLE if not exists jobgroup
(
  id bigint(20) NOT NULL auto_increment,
    createdon timestamp,
    modifiedon timestamp,
    createdby bigint(20),
    modifiedby bigint(20),
  displayname varchar(255) NOT NULL,
  PRIMARY KEY  (id),
  KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- create index index_job_name on job (name);
