CREATE TABLE if not exists job (
  id bigint(20) NOT NULL,
  displayname varchar(255) NOT NULL,
  cron_expression varchar(255) default NULL,
  job_state_fk bigint(20) NOT NULL,
  job_type_fk bigint(20) NOT NULL,
  router_fk varchar(36) NOT NULL,
  instance varchar(255) NOT NULL,
  class text NOT NULL,
  PRIMARY KEY  (id),
  KEY (id),
  FOREIGN KEY (job_type_fk) REFERENCES job_type (id),
  FOREIGN KEY (job_state_fk) REFERENCES job_state (id),
  FOREIGN KEY (router_fk) REFERENCES router (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- create index index_job_name on job (name);
