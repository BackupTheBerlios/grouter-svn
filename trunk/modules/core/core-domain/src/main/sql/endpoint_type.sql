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

insert into endpoint_type (id, name, createdon, modifiedon,createdby,modifiedby) values (1,'fileReader', NOW(),NOW(),1,1);
insert into endpoint_type (id, name, createdon, modifiedon,createdby,modifiedby) values (2,'fileWriter', NOW(),NOW(),1,1);
insert into endpoint_type (id, name, createdon, modifiedon,createdby,modifiedby) values (3,'ftpReader', NOW(),NOW(),1,1);
insert into endpoint_type (id, name, createdon, modifiedon,createdby,modifiedby) values (4,'ftpWriter', NOW(),NOW(),1,1);
insert into endpoint_type (id, name, createdon, modifiedon,createdby,modifiedby) values (5,'jmsReader', NOW(),NOW(),1,1);
insert into endpoint_type (id, name, createdon, modifiedon,createdby,modifiedby) values (6,'jmsWriter', NOW(),NOW(),1,1);
insert into endpoint_type (id, name, createdon, modifiedon,createdby,modifiedby) values (7,'httpReader', NOW(),NOW(),1,1);
