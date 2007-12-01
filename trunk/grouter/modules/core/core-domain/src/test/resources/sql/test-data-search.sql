
insert into settings(id) values ( 1 );
insert into settings_context(id,keyname, value,settings_fk) values ( 1,'key1','value1',1);

insert into router (id, startedon, uptime, displayname, homepath , description ,  settings_fk) values ('rid_1', '2006-10-13 15:51:53', 100000000, 'ROUTER_TEST', '/homeofrouter'  ,'a description', 1);

insert into endpoint (id, uri, clazzname, cron, endpoint_type_fk) values ( '1', 'file://temp/in', 'clazzname.FileReader','* * * * * ', 1);
insert into endpoint (id, uri, clazzname, cron, endpoint_type_fk) values ( '2', 'file://temp/out', 'clazzname.FilWriter','* * * * * ', 2);
insert into endpoint (id, uri, clazzname, cron, endpoint_type_fk) values ( '3', 'ftp://127.0.0.1/in', 'clazzname.FtpReader','* * * * * ', 3);
insert into endpoint (id, uri, clazzname, cron, endpoint_type_fk) values ( '4', 'ftp://127.0.0.1/out', 'clazzname.FtpWriter','* * * * * ', 2);

insert into endpoint_context (id, keyname, value, endpoint_fk) values (1000, 'ftpHost', 'localhost', 3);
insert into endpoint_context (id, keyname, value, endpoint_fk) values (1001, 'ftpPort', '12345', 3);
insert into endpoint_context (id, keyname, value, endpoint_fk) values (1002, 'fileList', 'file1,file2', 3);

insert into node (id, inbound_endpoint_fk, outbound_endpoint_fk, modifiedon, router_fk, displayname, nodestatus_fk) values ('nid_1', 1, 2, '2006-10-13 15:51:53', 'rid_1', 'FILE-TO-FILE1',2);

insert into sender (name, id) values ('SENDERNAME1', 'senderid_1');

insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_1', NOW(), 'senderid_1', 'nid_1', 'A message 1');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_2', NOW(), 'senderid_1', 'nid_1', 'A message 2');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_3', NOW(), 'senderid_1', 'nid_1', 'A message 3');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_4', NOW(), 'senderid_1', 'nid_1', 'A message 4');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_5', NOW(), 'senderid_1', 'nid_1', 'A message 5');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_6', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_7', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_8', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_9', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_10', NOW(), 'senderid_1', 'nid_1', 'A message 6 A message 6 A message 6 A message 6 A message 6 A message 6 A message 6  ');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_11', NOW(), 'senderid_1', 'nid_1', 'A message find me');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_12', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_13', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_14', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_15', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_16', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_17', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_18', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_19', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_20', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_21', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_22', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_23', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_24', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_25', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_26', NOW(), 'senderid_1', 'nid_1', 'A message 6');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_27', NOW(), 'senderid_1', 'nid_1', 'java');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_28', NOW(), 'senderid_1', 'nid_1', 'Java rocks');
insert into message (id, creationtimestamp, sender_fk, node_fk, content) values ( 'msgid_29', NOW(), 'senderid_1', 'nid_1', 'A message 6');


