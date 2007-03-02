insert into router (startedon, uptime, name, id) values ('2006-10-13 15:51:53', 100000000, 'ROUTER_TEST', 'routerid_1');
insert into node (modifiedon, router_fk, name, id) values ('2006-10-13 15:51:53', 'routerid_1', 'FILE-TO-FILE1', 'n1');
insert into node (modifiedon, router_fk, name, id) values ('2006-10-13 15:51:53',  'routerid_1', 'FILE-TO-FTP', 'nodeid_2');
insert into sender (NAME, ID) values ('A sender', 's1');
insert into message (creationtimestamp, sender_fk, node_fk, content, id) values ( NOW(), 's1', 'n1', 'A message 1', 'm1');
insert into message (creationtimestamp, sender_fk, node_fk, content, id) values ( NOW(), 's1', 'n1', 'A message 2', 'm2');
insert into message (creationtimestamp, sender_fk, node_fk, content, id) values ( NOW(), 's1', 'n1', 'A message 3', 'm3');
insert into message (creationtimestamp, sender_fk, node_fk, content, id) values ( NOW(), 's1', 'n1', 'A message 4', 'm4');
insert into message (creationtimestamp, sender_fk, node_fk, content, id) values ( NOW(), 's1', 'n1', 'A message 5', 'm5');
insert into message (creationtimestamp, sender_fk, node_fk, content, id) values ( NOW(), 's1', 'n1', 'A message 6', 'm6');

