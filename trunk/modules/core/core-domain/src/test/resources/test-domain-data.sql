--  Dates: ADDDATE( CURDATE(), 1 )

insert into settings(id, createdon, modifiedon) values ( -1 ,  Now(), Now() );
insert into settings_context(id,keyname, value,settings_fk) values ( -1,'test_key1','test_value1',-1);

insert into router (id, startedon, uptime, displayname, homepath , description ,  settings_fk) values ('--rid_1', '2006-10-13 15:51:53', 100000000, 'ROUTER_TEST', '/homeofrouter'  ,'a description', -1);

insert into endpoint (id, uri, clazzname, cron, endpoint_type_fk) values ( '-inendpoint1', 'file://temp/in', 'clazzname.FileReader','* * * * * ', 1);
insert into endpoint (id, uri, clazzname, cron, endpoint_type_fk) values ( '-outendpoint2', 'file://temp/out', 'clazzname.FilWriter','* * * * * ', 2);
insert into endpoint (id, uri, clazzname, cron, endpoint_type_fk) values ('-inendpoint3', 'ftp://127.0.0.1/in', 'clazzname.FtpReader','* * * * * ', 3);
insert into endpoint (id, uri, clazzname, cron, endpoint_type_fk) values ( '-outendpoint4', 'ftp://127.0.0.1/out', 'clazzname.FtpWriter','* * * * * ', 2);
                        
insert into endpoint_context (id, keyname, value, endpoint_fk) values (-1000, 'ftpHost', 'localhost', '-inendpoint1');
insert into endpoint_context (id, keyname, value, endpoint_fk) values (-1001, 'ftpPort', '12345', '-inendpoint1');
insert into endpoint_context (id, keyname, value, endpoint_fk) values (-1002, 'fileList', 'file1,file2', '-inendpoint1');

insert into node (id, idno, inbound_endpoint_fk, outbound_endpoint_fk, modifiedon, router_fk, displayname, nodestatus_fk, createdby, modifiedby) values ('-node1', 'nodeidno_1', '-inendpoint1', '-outendpoint2', '2006-10-13 15:51:53', '--rid_1', 'FILE-TO-FILE1',2,1,1);
insert into node (id, idno, inbound_endpoint_fk, outbound_endpoint_fk, modifiedon, router_fk, displayname, nodestatus_fk, createdby, modifiedby) values ('-node2', 'nodeidno_1', '-inendpoint3', '-outendpoint4', '2006-10-14 15:51:53',  '--rid_1', 'FTP-TO-FILE', 1,1,1);
                                        
insert into sender ( id, name) values ( -1 ,'Sender name 1');

insert into message (id, createdon, sender_fk, node_fk, content) values (-1, NOW(), -1, "-node1", 'A message 1');
insert into message (id, createdon, sender_fk, node_fk, content) values (-2, NOW(), -1, "-node1", 'A message 2');
insert into message (id, createdon, sender_fk, node_fk, content) values (-3, NOW(), -1, "-node1", 'A message 3');
insert into message (id, createdon, sender_fk, node_fk, content) values (-4, '2007-10-02 22:22:22', -1, "-node1", 'A message 4');
insert into message (id, createdon, sender_fk, node_fk, content) values (-5, '2006-01-02 22:22:22', -1, "-node1", 'A message 5');
insert into message (id, createdon, sender_fk, node_fk, content) values (-6, '2007-01-20 22:22:22', -1, "-node1", 'A message 6');
insert into message (id, createdon, sender_fk, node_fk, content) values (-7, '2007-01-03 22:22:22', -1, "-node1", 'A message 6');
insert into message (id, createdon, sender_fk, node_fk, content) values (-8, '2007-12-02 22:22:22', -1, "-node1", 'A message 6');
insert into message (id, createdon, sender_fk, node_fk, content) values (-9, '2008-01-02 22:22:22', -1, "-node1", 'A message 6');
insert into message (id, createdon, sender_fk, node_fk, content) values (-10, '2007-03-02 22:22:22', -1, "-node1", 'A message 6');
insert into message (id, createdon, sender_fk, node_fk, content) values (-11, '2007-02-02 22:22:22', -1, "-node1", 'A message 6');

insert into message (id, createdon, sender_fk, node_fk, content) values (-12, '2007-01-03 22:22:22', -1, "-node2", 'A message ');
insert into message (id, createdon, sender_fk, node_fk, content) values (-13, '2007-12-02 22:22:22', -1, "-node2", 'A message ');
insert into message (id, createdon, sender_fk, node_fk, content) values (-14, '2008-01-02 22:22:22', -1, "-node2", 'A message ');
insert into message (id, createdon, sender_fk, node_fk, content) values (-15, '2007-03-02 22:22:22', -1, "-node2", 'A message ');
insert into message (id, createdon, sender_fk, node_fk, content) values (-16, '2007-02-02 22:22:22', -1, "-node2", 'A message ');

insert into receiver (id, name ) values ( -1, 'A receiver 1');
insert into receiver (id, name ) values ( -2, 'A receiver 2');
insert into receiver_message (receiver_fk , message_fk) values ( -1,  -1 );
insert into receiver_message (receiver_fk , message_fk) values ( -2,  -1 );


-- User
insert into address (id,  phone, mobilephone, street, zip, city, fax, homepageurl, country, companyname, email) values (-1,  '0046 8 12345', '0046 701 12345', 'Kungsgatan', '12', 'Stockholm', '12345', 'http://www.stockholm.se', 'Sweden', 'Lšnneberga', 'astrid.lindgren@stockholm.se');
insert into user (id, createdon, modifiedon, createdby, modifiedby, username, password, firstname, lastname, address_fk, user_state_fk) values (-1, NOW(), NOW(), 1,1,    'ast', 'rid','Astrid', 'Lindgren',  -1, 3 );
insert into user_role (id, user_id, role_id) values (-1, -1, 1);
insert into user_role (id, user_id, role_id) values (-2, -1, 2);
insert into user_role (id, user_id, role_id) values (-3, -1, 3);

insert into user (id, createdon, modifiedon, createdby, modifiedby,username, password, firstname, lastname,user_state_fk) values (-2, NOW(), NOW(), 1,1,'ken', 'obi','Obi', 'Van',3 );
insert into user_role (id, user_id, role_id) values (-4, -2, 1);

insert into address (id, phone, mobilephone, street, zip, city, fax, homepageurl, country, companyname, email) values (-3,  '0011 8 12345', '0011 701 12345', 'Swazzistrasse', '5555', 'Nairobi', '3333', 'http://www.zurich.ch', 'Swaziland', 'Company3', 'email@gmail.com');
insert into user (id,createdon, modifiedon, createdby, modifiedby, username, password, firstname, lastname, address_fk, user_state_fk) values (-3, NOW(), NOW(), 1,1,'pla', 'ton','Platon', '',  -3, 3 );
insert into user_role (id, user_id, role_id) values (-5, -3, 1);

insert into address (id, phone, mobilephone, street, zip, city, fax, homepageurl, country, companyname, email) values (-4,  '0031 8 12345', '0031 701 12345', 'Athensroad', '6666', 'Athens', '444', 'http://www.zurich.ch', 'Greece', 'Company4', 'email@gmail.com');
insert into user (id, createdon, modifiedon, createdby, modifiedby,username, password, firstname, lastname, address_fk, user_state_fk) values (-4, NOW(), NOW(), 1,1,'sok', 'tonrates','Sokrates', '',  -4, 3 );
insert into user_role (id, user_id, role_id) values (-6, -4, 1);

-- Jobs
insert into job (id,idno,displayname,cronexpression,startedon, endedon, job_state_fk,job_type_fk,router_fk, createdby, modifiedby, createdon, modifiedon) values ( '-1','jobno1' ,'Job 1','* * * * *', Now(), ADDDATE( CURDATE(), 1 ) ,1,1,'--rid_1',1,1 , Now(), Now());
insert into job (id,idno,displayname,cronexpression,startedon, endedon, job_state_fk,job_type_fk,router_fk, createdby, modifiedby, createdon, modifiedon) values ( '-2','jobno2','Job 2','* * * * *',Now(), ADDDATE( CURDATE(), 1 ), 2,1,'--rid_1' ,1,1 , Now(), Now());
insert into job (id,idno,displayname,cronexpression,startedon, endedon, job_state_fk,job_type_fk,router_fk, createdby, modifiedby, createdon, modifiedon) values ( '-3','jobno3','Job 3','* * * * *', Now(), ADDDATE( CURDATE(), 1 ),3,1,null ,1,1 , Now(), Now());
-- insert into job (id,idno,displayname,cronexpression,job_state_fk,job_type_fk,router_fk, createdby, modifiedby, createdon, modifiedon) values ( -1,'jobno' ,'displayname','* * * * *',1,1,'--rid_1',1,1 , Now(), Now());
-- insert into job (id,idno,displayname,cronexpression,job_state_fk,job_type_fk,router_fk, createdby, modifiedby, createdon, modifiedon) values ( -2,'jobno','a name','* * * * *', 2,1,'--rid_1' ,1,1 , Now(), Now());
-- insert into job (id,idno,displayname,cronexpression,job_state_fk,job_type_fk,router_fk, createdby, modifiedby, createdon, modifiedon) values ( -3,'jobno','another name','* * * * *',  3,1,'--rid_1' ,1,1 , Now(), Now());

