create table sender
(
    id bigint(20) NOT NULL auto_increment,
    createdon datetime,
    modifiedon datetime,
    createdby bigint(20),
    modifiedby bigint(20),
    name varchar(255),
    address_fk bigint(20),
    primary key (id),
    foreign key (address_fk) references address (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


