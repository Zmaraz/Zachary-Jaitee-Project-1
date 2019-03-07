DROP TABLE ERS_REIMBURSEMENT;
DROP TABLE ERS_USERS;
DROP TABLE ERS_USER_ROLES;
DROP TABLE ERS_REIMBURSEMENT_STATUS;
DROP TABLE ERS_REIMBURSEMENT_TYPE;

DROP SEQUENCE user_id_seq;
DROP SEQUENCE reimb_id_seq;

CREATE TABLE ers_user_roles(
	ers_user_role_id	NUMBER,
	user_role			VARCHAR2(10),
	
	CONSTRAINT ers_user_roles_pk
	PRIMARY KEY (ers_user_role_id)
);

CREATE TABLE ers_reimbursement_status(
	reimb_status_id		NUMBER,
	reimb_status		VARCHAR2(10),
	
	CONSTRAINT reimb_status_pk
	PRIMARY KEY  (reimb_status_id)
);

CREATE TABLE ers_reimbursement_type(
	reimb_type_id		NUMBER,
	reimb_type		VARCHAR2(10),
	
	CONSTRAINT reimb_type_pk
	PRIMARY KEY  (reimb_type_id)
);


CREATE TABLE ers_users(
	ers_user_id		NUMBER,
	ers_username		VARCHAR2(50) UNIQUE,
	ers_password		VARCHAR2(50),
	user_first_name		VARCHAR2(100),
	user_last_name		VARCHAR2(100),
	user_email			VARCHAR2(150) UNIQUE,
	user_role_id		NUMBER,
	
	CONSTRAINT ers_user_pk
	PRIMARY KEY (ers_user_id),
	
	CONSTRAINT user_roles_fk
	FOREIGN KEY (user_role_id)
	REFERENCES ers_user_roles(ers_user_role_id)
    ON DELETE CASCADE
);
--ALTER TABLE ers_users
--DROP constraint user_roles_fk;
--
--ALTER TABLE ers_users
--    ADD	CONSTRAINT user_roles_fk
--	FOREIGN KEY (user_role_id)
--	REFERENCES ers_user_roles(ers_user_role_id)
--    ON DELETE CASCADE;
--ALTER TABLE child_table_name 
--  ADD CONSTRAINT fk_name 
--  FOREIGN KEY (child_column_name) 
--  REFERENCES parent_table_name(parent_column_name) 
--  ON DELETE CASCADE;

CREATE TABLE ers_reimbursement(
	reimb_id			NUMBER,
	reimb_amount		NUMBER,
	reimb_submitted		TIMESTAMP,
	reimb_resolved		TIMESTAMP,
	reimb_description	VARCHAR2(250),
	reimb_receipt		BLOB,
	reimb_author		NUMBER,
	reimb_resolver		NUMBER,
	reimb_status_id		NUMBER,
	reimb_type_id		NUMBER,
	
	CONSTRAINT ers_reimbursement_pk
	PRIMARY KEY (reimb_id),
	
	CONSTRAINT ers_user_fk_auth
	FOREIGN KEY (reimb_author)
	REFERENCES ERS_USERS(ers_user_id)
    ON DELETE CASCADE,
	
	CONSTRAINT ers_users_fk_reslvr
	FOREIGN KEY (reimb_resolver)
	REFERENCES ers_users(ers_user_id)
    ON DELETE CASCADE,
	
	CONSTRAINT ers_reimbursement_status_fk
	FOREIGN KEY (reimb_status_id)
	REFERENCES ers_reimbursement_status(reimb_status_id)
    ON DELETE CASCADE,
	
	CONSTRAINT ers_reimbursement_type_fk
	FOREIGN KEY (reimb_type_id)
	REFERENCES ers_reimbursement_type(reimb_type_id)
    ON DELETE CASCADE
);
--ALTER TABLE ers_reimbursement
--DROP constraint ers_user_fk_auth;
--ALTER TABLE ers_reimbursement
--DROP constraint ers_users_fk_reslvr;
--ALTER TABLE ers_reimbursement
--DROP constraint ers_reimbursement_status_fk;
--ALTER TABLE ers_reimbursement
--DROP constraint ers_reimbursement_type_fk;
--
--ALTER TABLE ers_reimbursement
--    ADD	CONSTRAINT ers_user_fk_auth
--	FOREIGN KEY (reimb_author)
--	REFERENCES ERS_USERS(ers_user_id)
--    ON DELETE CASCADE;
--ALTER TABLE ers_reimbursement
--    ADD	CONSTRAINT ers_users_fk_reslvr
--	FOREIGN KEY (reimb_resolver)
--	REFERENCES ers_users(ers_user_id)
--    ON DELETE CASCADE;
--ALTER TABLE ers_reimbursement
--    ADD	CONSTRAINT ers_reimbursement_status_fk
--	FOREIGN KEY (reimb_status_id)
--	REFERENCES ers_reimbursement_status(reimb_status_id)
--    ON DELETE CASCADE;
--ALTER TABLE ers_reimbursement
--    ADD	CONSTRAINT ers_reimbursement_type_fk
--	FOREIGN KEY (reimb_type_id)
--	REFERENCES ers_reimbursement_type(reimb_type_id)
--    ON DELETE CASCADE;


CREATE SEQUENCE user_id_seq
MINVALUE 1
MAXVALUE 99999999999999999
INCREMENT BY 1
START WITH 1;


CREATE SEQUENCE reimb_id_seq
MINVALUE 1
MAXVALUE 99999999999999999
INCREMENT BY 1
START WITH 1;

CREATE OR REPLACE TRIGGER user_id_trigger
BEFORE INSERT ON ers_users
FOR EACH row
BEGIN
    SELECT user_id_seq.nextval
    INTO :new.ers_user_id
    FROM dual;
END;
/

CREATE OR REPLACE TRIGGER reimb_id_trigger
BEFORE INSERT ON ers_reimbursement
FOR EACH row
BEGIN
    SELECT reimb_id_seq.nextval
    INTO :new.reimb_id
    FROM dual;
    SELECT sysdate
    INTO :new.reimb_submitted
    FROM dual;
END;
/

CREATE OR REPLACE TRIGGER reimb_resolved_trigger
BEFORE UPDATE ON ers_reimbursement
FOR EACH row
BEGIN
    SELECT sysdate
    INTO :new.reimb_resolved
    FROM dual;
END;
/
--CREATE OR REPLACE TRIGGER transaction_trigger
--BEFORE INSERT ON transactions
--FOR EACH ROW
--BEGIN
--    SELECT transaction_pk_seq.NEXTVAL
--    INTO :new.transaction_id
--    FROM dual;
--    SELECT 
--        sysdate
--    INTO :new.time_added
--    FROM dual;
--END;
--/