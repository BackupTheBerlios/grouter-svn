create table sender
(
    ID varchar(255) not null,
    NAME varchar(255),
    ADDRESS_FK bigint(20),
    primary key (ID),
    foreign key (ADDRESS_FK) references ADDRESS (ID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into sender (id, name) values (1,'test');

