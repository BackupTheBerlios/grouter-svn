create table nodestatus
(
    id bigint(20) not null,
    idno varchar(36) not null,
    createdon datetime,
    modifiedon datetime,
    createdby bigint(20),
    modifiedby bigint(20),
    name varchar(255),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into nodestatus (id, name) values (1,'NOTSTARTED');
insert into nodestatus (id, name) values (2,'SCHEDULED_TO_START');
insert into nodestatus (id, name) values (3,'RUNNING');
insert into nodestatus (id, name) values (4,'RESCHEDULED_TO_START');
insert into nodestatus (id, name) values (5,'STOPPED');
insert into nodestatus (id, name) values (6,'ERROR');
insert into nodestatus (id, name) values (7,'NOT_CONFIGURED_TO_START');


