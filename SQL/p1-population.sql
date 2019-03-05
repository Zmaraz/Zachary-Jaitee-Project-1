--stuff
INSERT INTO ers_users VALUES (0, 'asdf','asdf','asdf','asdf','asdf@asdf.com',2);
INSERT INTO ers_users VALUES (0, 'billsdfacc','billpass','billson','bobson','bil34l@bob.com',1);
INSERT INTO ers_users VALUES (0, 'billacc','billpass','billson','bobson','bill@bob.com',1);
INSERT INTO ers_users VALUES (0, 'bobacc','bobpass','bobson','billson','bob@bill.com',1);
INSERT INTO ers_users VALUES (0, 'bobacsdfc','bosdfsdfbpass','bobasdfon','biasdfson','bob@bill.sdfcom',1);
SELECT * FROM ers_users u JOIN ers_user_roles r ON u.user_role_id = r.ers_user_role_id WHERE ers_user_id = 23;
commit;
SELECT * FROM ers_users u JOIN ers_user_roles r ON u.user_role_id = r.ers_user_role_id;
SELECT * FROM ers_users;