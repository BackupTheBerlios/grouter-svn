insert into settings(id, createdon, modifiedon) values ( -1 ,  Now(), Now());
insert into settings_context(id,keyname, value,settings_fk) values ( -1,'test_key1','test_value1',-1);

insert into router (id, startedon, uptime, displayname, homepath , description ,  settings_fk) values ('--rid_1', '2006-10-13 15:51:53', 100000000, 'ROUTER_TEST', '/homeofrouter'  ,'a description', -1);

insert into endpoint (id, uri, clazzname, cron, endpoint_type_fk) values ( '-1', 'file://temp/in', 'clazzname.FileReader','* * * * * ', 1);
insert into endpoint (id, uri, clazzname, cron, endpoint_type_fk) values ( '-2', 'file://temp/out', 'clazzname.FilWriter','* * * * * ', 2);
insert into endpoint (id, uri, clazzname, cron, endpoint_type_fk) values ( '-3', 'ftp://127.0.0.1/in', 'clazzname.FtpReader','* * * * * ', 3);
insert into endpoint (id, uri, clazzname, cron, endpoint_type_fk) values ( '-4', 'ftp://127.0.0.1/out', 'clazzname.FtpWriter','* * * * * ', 2);

insert into endpoint_context (id, keyname, value, endpoint_fk) values (-1000, 'ftpHost', 'localhost', '-3');
insert into endpoint_context (id, keyname, value, endpoint_fk) values (-1001, 'ftpPort', '12345', '-3');
insert into endpoint_context (id, keyname, value, endpoint_fk) values (-1002, 'fileList', 'file1,file2', '-3');

insert into node (id, idno, inbound_endpoint_fk, outbound_endpoint_fk, modifiedon, router_fk, displayname, nodestatus_fk, createdby, modifiedby) values (-1, 'nodeidno_1', '-1', '-2', '2006-10-13 15:51:53', '--rid_1', 'FILE-TO-FILE1',2,1,1);
insert into node (id, idno, inbound_endpoint_fk, outbound_endpoint_fk, modifiedon, router_fk, displayname, nodestatus_fk, createdby, modifiedby) values (-2, 'nodeidno_1', '-3', '-4', '2006-10-14 15:51:53',  '--rid_1', 'FTP-TO-FILE', 1,1,1);
                                        
insert into sender (name, id) values ('SENDERNAME1', '-senderid_1');

insert into message (id, idno, creationtimestamp, sender_fk, node_fk, content) values (-1, '-msgid_1', NOW(), '-senderid_1', -1, 'A message 1');
insert into message (id, idno, creationtimestamp, sender_fk, node_fk, content) values ( -2,'-msgid_2', NOW(), '-senderid_1', -1, 'A message 2');
insert into message (id, idno,creationtimestamp, sender_fk, node_fk, content) values (-3, '-msgid_3', NOW(), '-senderid_1', -1, 'A message 3');
insert into message (id, idno,creationtimestamp, sender_fk, node_fk, content) values (-4, '-msgid_4', NOW(), '-senderid_1', -1, 'A message 4');
insert into message (id, idno,creationtimestamp, sender_fk, node_fk, content) values (-5, '-msgid_5', NOW(), '-senderid_1', -1, 'A message 5');
insert into message (id, idno,creationtimestamp, sender_fk, node_fk, content) values (-6, '-msgid_6', NOW(), '-senderid_1', -1, 'A message 6');
         
insert into receiver (id, name ) values ( '-receiverid_1', 'A receiver');
insert into receiver (id, name ) values ( '-receiverid_2', 'A receiver');
insert into receiver_message (receiver_fk , message_fk) values ( '-receiverid_1',  -1 );


-- User

insert into address (id,  phone, mobilephone, street, zip, city, fax, homepageurl, country, companyname, email) values (-1,  '0046 8 12345', '0046 701 12345', 'Kungsgatan', '12', 'Stockholm', '12345', 'http://www.stockholm.se', 'Sweden', 'Lšnneberga', 'astrid.lindgren@stockholm.se');
insert into user (id, username, password, firstname, lastname, address_fk, user_state_fk) values (-1, 'ast', 'rid','Astrid', 'Lindgren',  -1, 3 );
insert into user_role (id, user_id, role_id) values (-1, -1, 1);
insert into user_role (id, user_id, role_id) values (-2, -1, 2);
insert into user_role (id, user_id, role_id) values (-3, -1, 3);

insert into address (id, phone, mobilephone, street, zip, city, fax, homepageurl, country, companyname, email) values (-2,  '0041 8 12345', '0041 701 12345', 'Bahnohostrasse', '12345', 'Zurich', '12345', 'http://www.zurich.ch', 'Switzerland', 'Company2', 'albert.einstein@bluemail.com');
insert into user (id, username, password, firstname, lastname, address_fk, user_state_fk) values (-2, 'ken', 'obi','Obi', 'Van',  -2, 3 );
insert into user_role (id, user_id, role_id) values (-4, -2, 1);

insert into address (id, phone, mobilephone, street, zip, city, fax, homepageurl, country, companyname, email) values (-3,  '0011 8 12345', '0011 701 12345', 'Swazzistrasse', '5555', 'Nairobi', '3333', 'http://www.zurich.ch', 'Swaziland', 'Company3', 'email@gmail.com');
insert into user (id, username, password, firstname, lastname, address_fk, user_state_fk) values (-3, 'pla', 'ton','Platon', '',  -3, 3 );
insert into user_role (id, user_id, role_id) values (-5, -3, 1);

insert into address (id, phone, mobilephone, street, zip, city, fax, homepageurl, country, companyname, email) values (-4,  '0031 8 12345', '0031 701 12345', 'Athensroad', '6666', 'Athens', '444', 'http://www.zurich.ch', 'Greece', 'Company4', 'email@gmail.com');
insert into user (id, username, password, firstname, lastname, address_fk, user_state_fk) values (-4, 'sok', 'tonrates','Sokrates', '',  -4, 3 );
insert into user_role (id, user_id, role_id) values (-6, -4, 1);

-- Jobs

-- ADDDATE( CURDATE(), 1 )
insert into job (id,idno,displayname,cron_expression,started_on, finished_at, job_state_fk,job_type_fk,router_fk, createdby, modifiedby, createdon, modifiedon) values ( -1,'jobno' ,'displayname','* * * * *', Now(), ADDDATE( CURDATE(), 1 ) ,1,1,'rid_1',1,1 , Now(), Now());
insert into job (id,idno,displayname,cron_expression,started_on, finished_at, job_state_fk,job_type_fk,router_fk, createdby, modifiedby, createdon, modifiedon) values ( -2,'jobno','a name','* * * * *',Now(), ADDDATE( CURDATE(), 1 ), 2,1,'rid_1' ,1,1 , Now(), Now());
insert into job (id,idno,displayname,cron_expression,started_on, finished_at, job_state_fk,job_type_fk,router_fk, createdby, modifiedby, createdon, modifiedon) values ( -3,'jobno','another name','* * * * *', Now(), Now(), 3,1,'rid_1' ,1,1 , Now(), Now());

