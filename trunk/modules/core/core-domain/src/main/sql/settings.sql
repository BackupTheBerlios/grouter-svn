create table settings
(
--    id varchar(36) not null,
    id bigint(20) NOT NULL auto_increment,
--    idno varchar(36) not null,
    createdon datetime,
    modifiedon datetime,
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

