create table receiver
(
    id bigint(20) NOT NULL auto_increment,
    name varchar(255),
    address_fk bigint(20),
    primary key (id),
    foreign key (address_fk) references address (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;