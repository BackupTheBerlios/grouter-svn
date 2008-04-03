create table locale
(
    id bigint(20) not null,
        createdon timestamp,
        modifiedon timestamp,
        createdby bigint(20),
        modifiedby bigint(20),    
    language varchar(255),
    country varchar(255),
    sort bigint(20),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into locale (id, language, country,sort) values (1,'English', 'UK',1);
insert into locale (id, language, country,sort) values (2,'Swedish', 'Sweden',2);
