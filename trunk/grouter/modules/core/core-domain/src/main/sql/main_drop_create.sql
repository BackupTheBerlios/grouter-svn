BEGIN;

set foreign_key_checks = 0;

DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS router;
DROP TABLE IF EXISTS node_type;
DROP TABLE IF EXISTS node;
DROP TABLE IF EXISTS nodestatus;
DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS receiver;
DROP TABLE IF EXISTS receiver_message;
DROP TABLE IF EXISTS sender;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS user_state;
DROP TABLE IF EXISTS property;
DROP TABLE IF EXISTS endpoint;
DROP TABLE IF EXISTS endpoint_type;
DROP TABLE IF EXISTS endpoint_context;
-- DROP TABLE IF EXISTS endpoint_filereader;
DROP TABLE IF EXISTS filter_type;
DROP TABLE IF EXISTS settings;
DROP TABLE IF EXISTS settings_context;
DROP TABLE IF EXISTS jobgrup;
DROP TABLE IF EXISTS job;
DROP TABLE IF EXISTS job_context;
DROP TABLE IF EXISTS job_state;
DROP TABLE IF EXISTS job_type;
DROP TABLE IF EXISTS job_event;
DROP TABLE IF EXISTS locale;

set foreign_key_checks = 1;


select 'settings.sql';
\. settings.sql
select 'locale.sql';
\. locale.sql
select 'settings_context.sql';
\. settings_context.sql
select 'address.sql';
\. address.sql
select 'property.sql';
\. property.sql
select 'user_state.sql';
\. user_state.sql
select 'user.sql';
\. user.sql
select 'role.sql';
\. role.sql
select 'user_role.sql';
\. user_role.sql
select 'endpoint_type.sql';
\. endpoint_type.sql
select 'endpoint.sql';
\. endpoint.sql
select 'endpoint_context.sql';
\. endpoint_context.sql
select 'router.sql';
\. router.sql
select 'nodestatus.sql';
\. nodestatus.sql
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
select 'filter_type.sql';
\. filter_type.sql
select 'job_state.sql';
\. job_state.sql
select 'job_type.sql';
\. job_type.sql
select 'jobgroup.sql';
\. jobgroup.sql
select 'job.sql';
\. job.sql
select 'job_context.sql';
\. job_context.sql
select 'job_event.sql';
\. job_event.sql

COMMIT;


