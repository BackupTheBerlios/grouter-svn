create table endpoint_type
(
    id bigint(20) not null,
    createdon datetime,
    modifiedon datetime,
    createdby bigint(20),
    modifiedby bigint(20),
    name varchar(255),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into endpoint_type (id, name) values (1,'fileReader');
insert into endpoint_type (id, name) values (2,'fileWriter');
insert into endpoint_type (id, name) values (3,'ftpReader');
insert into endpoint_type (id, name) values (4,'ftpWriter');
insert into endpoint_type (id, name) values (5,'jmsReader');
insert into endpoint_type (id, name) values (6,'jmsWriter');
insert into endpoint_type (id, name) values (7,'httpReader');
