insert into router (startedon, uptime, name, id) values ('2006-10-13 15:51:53', 100000000, 'TESTROUTER', 'routerid_1');
insert into node (modifiedon, router_fk, name, id) values ('2006-10-13 15:51:53', 'routerid_1', 'FILE-TO-FILE-NODE-1', 'nodeid_1');
insert into node (modifiedon, router_fk, name, id) values ('2006-10-13 15:51:53',  'routerid_1', 'FILE-TO-FILE-NODE-1', 'nodeid_2');
insert into sender (NAME, ID) values ('SENDERNAME1', 'senderid_1');
insert into message (creationtimestamp, sender_fk, node_fk, content, id) values ( NOW(), 'senderid_1', 'nodeid_1', 'A message 1', 'msgid_1');
insert into message (creationtimestamp, sender_fk, node_fk, content, id) values ( NOW(), 'senderid_1', 'nodeid_1', 'A message 2', 'msgid_2');
insert into message (creationtimestamp, sender_fk, node_fk, content, id) values ( NOW(), 'senderid_1', 'nodeid_1', 'A message 3', 'msgid_3');
insert into message (creationtimestamp, sender_fk, node_fk, content, id) values ( NOW(), 'senderid_1', 'nodeid_1', 'A message 4', 'msgid_4');
insert into message (creationtimestamp, sender_fk, node_fk, content, id) values ( NOW(), 'senderid_1', 'nodeid_1', 'A message 5', 'msgid_5');
insert into message (creationtimestamp, sender_fk, node_fk, content, id) values ( NOW(), 'senderid_1', 'nodeid_1', 'A message 6', 'msgid_6');

