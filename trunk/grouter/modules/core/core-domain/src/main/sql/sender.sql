create table sender
(
    id varchar(36) not null,
--    idno varchar(36) not null,
    createdon datetime,
    modifiedon datetime,
    name varchar(255),
    address_fk bigint(20),
    primary key (id),
    foreign key (address_fk) references address (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


