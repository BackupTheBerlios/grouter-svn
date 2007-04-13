create table endpoint_type
(
    id bigint(20) not null,
    name varchar(255),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into endpoint_type (id, name) values (1,'fileReader');
insert into endpoint_type (id, name) values (2,'fileWriter');
insert into endpoint_type (id, name) values (3,'ftpReader');
