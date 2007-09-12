create table user_state
 (
     id bigint(20) not null,
     name varchar(255),
     primary key (id)
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

 insert into user_state (id, name) values (1,'NEW');
 insert into user_state (id, name) values (2,'ACTIVATION_PENDING');
 insert into user_state (id, name) values (3,'ACTIVE');
 insert into user_state (id, name) values (4,'BLOCKED');



