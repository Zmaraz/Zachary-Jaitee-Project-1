--stuff
INSERT INTO ers_users VALUES (0, 'asdf','asdf','asdf','asdf','asdf@asdf.com',null);
INSERT INTO ers_users VALUES (0, 'billacc','billpass','billson','bobson','bill@bob.com',1);
commit;
SELECT * FROM ers_users u JOIN ers_user_roles r ON u.user_role_id = r.ers_user_role_id;
SELECT * FROM ers_users;