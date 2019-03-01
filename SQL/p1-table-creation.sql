CREATE TABLE ers_user_roles(
	ers_user_role_id	NUMBER,
	user_role			VARCHAR2(10),
	
	CONSTRAINT ers_user_roles_pk
	PRIMARY KEY (ers_user_role_id)
);

CREATE TABLE ers_users(
	ers_users_id		NUMBER,
	ers_username		VARCHAR2(50) UNIQUE,
	ers_password		VARCHAR2(50),
	user_first_name		VARCHAR2(100),
	user_last_name		VARCHAR2(100),
	user_email			VARCHAR2(150) UNIQUE,
	user_role_id		NUMBER,
	
	CONSTRAINT ers_user_pk
	PRIMARY KEY (ers_users_id),
	
	CONSTRAINT user_roles_fk
	FOREIGN KEY (user_role_id)
	REFERENCES ers_user_roles(ers_user_role_id)
);