create table nodestatus
(
    id bigint(20) not null,
    createdon datetime,
    modifiedon datetime,
    createdby bigint(20),
    modifiedby bigint(20),
    name varchar(255),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into nodestatus (id, createdon, modifiedon,createdby,modifiedby,name) values (1,NOW(),NOW(),1,1,'NOTSTARTED');
insert into nodestatus (id, createdon, modifiedon,createdby,modifiedby,name) values (2,NOW(),NOW(),1,1,'SCHEDULED_TO_START');
insert into nodestatus (id, createdon, modifiedon,createdby,modifiedby,name) values (3,NOW(),NOW(),1,1,'RUNNING');
insert into nodestatus (id, createdon, modifiedon,createdby,modifiedby,name) values (4,NOW(),NOW(),1,1,'RESCHEDULED_TO_START');
insert into nodestatus (id, createdon, modifiedon,createdby,modifiedby,name) values (5,NOW(),NOW(),1,1,'STOPPED');
insert into nodestatus (id, createdon, modifiedon,createdby,modifiedby,name) values (6,NOW(),NOW(),1,1,'ERROR');
insert into nodestatus (id, createdon, modifiedon,createdby,modifiedby,name) values (7,NOW(),NOW(),1,1,'NOT_CONFIGURED_TO_START');


