-- Drop bank user if it exits (run as admin)
DROP USER project1 CASCADE;

-- Create the bank user that all the Project 0 tables will be under (ran as admin)
CREATE USER project1
IDENTIFIED BY project1pass
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA 10M ON users;

GRANT connect to project1;
GRANT resource to project1;
GRANT create session TO project1;
GRANT create table TO project1;
GRANT create view TO project1;

conn project1/project1pass