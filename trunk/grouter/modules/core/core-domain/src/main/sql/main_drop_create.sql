BEGIN;

set foreign_key_checks = 0;

DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS router;
DROP TABLE IF EXISTS node_type;
DROP TABLE IF EXISTS node;
DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS receiver;
DROP TABLE IF EXISTS receiver_message;
DROP TABLE IF EXISTS sender;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS property;
DROP TABLE IF EXISTS endpoint;
DROP TABLE IF EXISTS endpoint_type;
-- DROP TABLE IF EXISTS endpoint_filereader;
DROP TABLE IF EXISTS filter_type;

set foreign_key_checks = 1;

select 'address.sql';
\. address.sql
select 'property.sql';
\. property.sql
select 'user.sql';
\. user.sql
select 'role.sql';
\. role.sql
select 'user_role.sql';
\. user_role.sql
select 'router.sql';
\. router.sql
select 'node_type.sql';
\. node_type.sql
select 'node.sql';
\. node.sql
select 'message.sql';
\. message.sql
select 'receiver.sql';
\. receiver.sql
select 'receiver_message.sql';
\. receiver_message.sql
select 'sender.sql';
\. sender.sql
select 'endpoint_type.sql';
\. endpoint_type.sql
select 'endpoint.sql';
\. endpoint.sql
select 'filter_type.sql';
\. filter_type.sql



COMMIT;


