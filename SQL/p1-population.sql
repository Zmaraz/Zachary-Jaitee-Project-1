--stuff
--USER------------------VALUES (id, username, password, fname, lname, email, role
INSERT INTO ers_users VALUES (0, 'asdf','asdf','asdf','asdf','asdf@asdf.com',null);

-- TICKET --VALUES(id, amount, time submitted, time resolved, descr, recipt, author, resolver, status id, type id)
INSERT INTO ers_reimbursement VALUES (0, 9.99,null,null,'here is a description','null',null);

--get all info from users
SELECT *
FROM ers_users u
JOIN ers_user_roles r
ON u.user_role_id = r.ers_user_role_id;

--get all info about reimbursement
--maybe make a procedure
SELECT *
FROM ers_reimbursement reimb
JOIN ers_reimbursement_type t
ON reimb.reimb_type_id = t.reimb_type_id;