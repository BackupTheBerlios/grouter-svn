create table settings
(
    id bigint(20) NOT NULL auto_increment,
    createdon timestamp,
    modifiedon timestamp,
    createdby bigint(20),
    modifiedby bigint(20),
    primary key (id)
 --   FOREIGN KEY (createdby) REFERENCES  user (id),
--    FOREIGN KEY (modifiedby) REFERENCES  user (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

