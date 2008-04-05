CREATE TABLE address (
      id bigint(20) NOT NULL auto_increment,
      idno varchar(36),
      createdon datetime,
      modifiedon datetime,
      createdby bigint(20),
      modifiedby bigint(20),
  firstname varchar(255) default NULL,
  lastname varchar(255) default NULL,
  phone varchar(255) default NULL,
  mobilephone varchar(255) default NULL,
  street varchar(255) default NULL,
  zip varchar(255) default NULL,
  city varchar(255) default NULL,
  fax varchar(255) default NULL,
  homepageurl varchar(255) default NULL,
  country varchar(255) default NULL,
  companyname varchar(255) default NULL,
  email varchar(255) default NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


