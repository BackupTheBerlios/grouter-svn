CREATE TABLE if not exists property
(
  id BIGINT(20) not null auto_increment,
  environment VARCHAR(255) default null,
  keyprop VARCHAR(255) default null,
  value VARCHAR(255) default null,
  description VARCHAR(255) default null,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

create index keyindex on property ( keyprop );


insert into property (keyprop, value) values ('test', '3000');
