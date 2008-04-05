CREATE TABLE if not exists job (
  id bigint(20) NOT NULL,
  idno varchar(36) not null,
  createdon datetime,
  modifiedon datetime,
  createdby bigint(20),
  modifiedby bigint(20),
  displayname varchar(255) NOT NULL,
 cronexpression varchar(255) default NULL,
  job_state_fk bigint(20) NOT NULL,
  job_type_fk bigint(20) NOT NULL,
  -- a job can optionally be associated to a router, but can also run independently
  router_fk varchar(36),
  -- startedon datetime  null,
  -- endedon datetime null,
  -- jobordernumber bigint(20) not null,
  -- jobgroup_fk bigint(20) NOT NULL,
  PRIMARY KEY  (id),
  KEY (id),
  FOREIGN KEY (job_type_fk) REFERENCES job_type (id),
  --  FOREIGN KEY (createdby) REFERENCES  user (id),
  --  FOREIGN KEY (modifiedby) REFERENCES  user (id),
  FOREIGN KEY (job_state_fk) REFERENCES job_state (id)
  -- FOREIGN KEY (jobgroup_fk) REFERENCES jobgroup (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- create index index_job_name on job (name);
