insert into settings(id) values ( -1 );
insert into settings_context(id,keyname, value,settings_fk) values ( -1,'key1','value1',-1);

insert into router (id, startedon, uptime, displayname, homepath , description ,  settings_fk) values ('--rid_1', '2006-10-13 15:51:53', 100000000, 'ROUTER_TEST', '/homeofrouter'  ,'a description', -1);

insert into endpoint (id, uri, clazzname, cron, endpoint_type_fk) values ( '-1', 'file://temp/in', 'clazzname.FileReader','* * * * * ', 1);
insert into endpoint (id, uri, clazzname, cron, endpoint_type_fk) values ( '-2', 'file://temp/out', 'clazzname.FilWriter','* * * * * ', 2);
insert into endpoint (id, uri, clazzname, cron, endpoint_type_fk) values ( '-3', 'ftp://127.0.0.1/in', 'clazzname.FtpReader','* * * * * ', 3);
insert into endpoint (id, uri, clazzname, cron, endpoint_type_fk) values ( '-4', 'ftp://127.0.0.1/out', 'clazzname.FtpWriter','* * * * * ', 2);

insert into endpoint_context (id, keyname, value, endpoint_fk) values (-1000, 'ftpHost', 'localhost', '-3');
insert into endpoint_context (id, keyname, value, endpoint_fk) values (-1001, 'ftpPort', '12345', '-3');
insert into endpoint_context (id, keyname, value, endpoint_fk) values (-1002, 'fileList', 'file1,file2', '-3');

insert into node (id, inbound_endpoint_fk, outbound_endpoint_fk, modifiedon, router_fk, displayname, nodestatus_fk) values ('-nid_1', '-1', '-2', '2006-10-13 15:51:53', '--rid_1', 'FILE-TO-FILE1',2);
insert into node (id, inbound_endpoint_fk, outbound_endpoint_fk, modifiedon, router_fk, displayname, nodestatus_fk) values ('-nid_2', '-3', '-4', '2006-10-14 15:51:53',  '--rid_1', 'FTP-TO-FILE', 1);

insert into sender (name, id) values ('SENDERNAME1', '-senderid_1');

insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( '-msgid_1', NOW(), '-senderid_1', '-nid_1', 'A message 1');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( '-msgid_2', NOW(), '-senderid_1', '-nid_1', 'A message 2');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( '-msgid_3', NOW(), '-senderid_1', '-nid_1', 'A message 3');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( '-msgid_4', NOW(), '-senderid_1', '-nid_1', 'A message 4');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( '-msgid_5', NOW(), '-senderid_1', '-nid_1', 'A message 5');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( '-msgid_6', NOW(), '-senderid_1', '-nid_1', 'A message 6');

insert into receiver (id, name ) values ( '-receiverid_1', 'A receiver');
insert into receiver (id, name ) values ( '-receiverid_2', 'A receiver');
insert into receiver_message (receiver_fk , message_fk) values ( '-receiverid_1',  '-msgid_1' );

insert into address (id, phone, mobilephone, street, zip, city, fax, homepageurl, country, companyname, email) values (1,  '0046 8 12345', '0046 701 12345', 'GeorgesStreet', '12345', 'El Stockholm', '12345', 'www.polyzois.se', 'Sweden', 'Denada', 'gepo01@yahoo.com');
insert into user (id, username, password, firstname, lastname, address_fk) values (10002, 'gepo', 'gepo','Georges', 'Poly',  1);
insert into user_role (id, user_id, role_id) values (10000, 10002, 1);
insert into user_role (id, user_id, role_id) values (10001, 10002, 2);
insert into user_role (id, user_id, role_id) values (10002, 10002, 3);


-- Jobs

-- ADDDATE( CURDATE(), 1 )
insert into job (id,displayname,cron_expression,started_on, finished_at, job_state_fk,job_type_fk,router_fk) values ( 1,'displayname','* * * * *', Now(), ADDDATE( CURDATE(), 1 ) ,1,1,'rid_1' );
insert into job (id,displayname,cron_expression,started_on, finished_at, job_state_fk,job_type_fk,router_fk) values ( 2,'a name','* * * * *',Now(), ADDDATE( CURDATE(), 1 ), 2,1,'rid_1' );
insert into job (id,displayname,cron_expression,started_on, finished_at, job_state_fk,job_type_fk,router_fk) values ( 3,'another name','* * * * *', Now(), Now(), 3,1,'rid_1' );

