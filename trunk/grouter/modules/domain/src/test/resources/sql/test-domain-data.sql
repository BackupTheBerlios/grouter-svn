insert into ROUTER (STARTEDON, UPTIME, NAME, ID) values ('2006-10-13 15:51:53', 100000000, 'TESTROUTER', 'routerid_1');
insert into NODE (MODIFIEDON, MODIFIEDBYSYSTEMUSER, ROUTER_FK, NAME, ID) values ('2006-10-13 15:51:53', null, 'routerid_1', 'FILE-TO-FILE-NODE-1', 'nodeid_1');
insert into NODE (MODIFIEDON, MODIFIEDBYSYSTEMUSER, ROUTER_FK, NAME, ID) values ('2006-10-13 15:51:53', null, 'routerid_1', 'FILE-TO-FILE-NODE-1', 'nodeid_2');
insert into SENDER (NAME, ID) values ('SENDERNAME1', 'senderid_1');
insert into MESSAGE (CREATIONTIMESTAMP, SENDER_FK, NODE_FK, MESSAGE, ID) values ( NOW(), 'senderid_1', 'nodeid_2', 'A message 1', 'msgid_1');
insert into MESSAGE (CREATIONTIMESTAMP, SENDER_FK, NODE_FK, MESSAGE, ID) values ( NOW(), 'senderid_1', 'nodeid_2', 'A message 2', 'msgid_2');
insert into MESSAGE (CREATIONTIMESTAMP, SENDER_FK, NODE_FK, MESSAGE, ID) values ( NOW(), 'senderid_1', 'nodeid_2', 'A message 3', 'msgid_3');
insert into MESSAGE (CREATIONTIMESTAMP, SENDER_FK, NODE_FK, MESSAGE, ID) values ( NOW(), 'senderid_1', 'nodeid_2', 'A message 4', 'msgid_4');
insert into MESSAGE (CREATIONTIMESTAMP, SENDER_FK, NODE_FK, MESSAGE, ID) values ( NOW(), 'senderid_1', 'nodeid_2', 'A message 5', 'msgid_5');
insert into MESSAGE (CREATIONTIMESTAMP, SENDER_FK, NODE_FK, MESSAGE, ID) values ( NOW(), 'senderid_1', 'nodeid_2', 'A message 6', 'msgid_6');
insert into MESSAGE (CREATIONTIMESTAMP, SENDER_FK, NODE_FK, MESSAGE, ID) values ( NOW(), 'senderid_1', 'nodeid_2', 'A message 7', 'msgid_7');

