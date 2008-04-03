create table filter_type
(
    id bigint(20) not null,
    name varchar(255),
    createdon timestamp,
    modifiedon timestamp,
    createdby bigint(20),
    modifiedby bigint(20),    
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into filter_type (id, name) values (1,'fileFilter');

