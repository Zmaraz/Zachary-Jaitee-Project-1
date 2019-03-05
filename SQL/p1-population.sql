--stuff
INSERT INTO ers_reimbursement_status VALUES (0, 'PENDING');
INSERT INTO ers_reimbursement_status VALUES (1, 'APPROVED');
INSERT INTO ers_reimbursement_status VALUES (2, 'DENIED');

INSERT INTO ers_reimbursement_type VALUES (1, 'LODGING');
INSERT INTO ers_reimbursement_type VALUES (2, 'TRAVEL');
INSERT INTO ers_reimbursement_type VALUES (3, 'FOOD');
INSERT INTO ers_reimbursement_type VALUES (4, 'OTHER');

INSERT INTO ers_user_roles VALUES (1, 'MANAGER');
INSERT INTO ers_user_roles VALUES (2, 'EMPLOYEE');

COMMIT;

--USER------------------VALUES (id, username, password, fname, lname, email, role
INSERT INTO ers_users VALUES (0, 'asdf','asdf','asdf','asdf','asdf@asdf.com',2);
INSERT INTO ers_users VALUES (0, 'jpitts','12345','Jaitee','Pitts','jpitts@asdf.com',2);

-- TICKET --VALUES(id, amount, time submitted, time resolved, descr, recipt, author, resolver, status id, type id)
INSERT INTO ers_reimbursement 
    VALUES (0, 
        9.99,
        null,
        null,
        'here is a description',
        null,
        1,
        null, 
        0,
        1);

--get all info from users
SELECT *
FROM ers_users u
JOIN ers_user_roles r
ON u.user_role_id = r.ers_user_role_id;

--get all info about reimbursement
--maybe make a procedure

SELECT reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_receipt, s.reimb_status, t.reimb_type
    FROM ers_reimbursement reimb
    JOIN ers_reimbursement_type t
        ON reimb.reimb_type_id = t.reimb_type_id
    JOIN ers_reimbursement_status s
        ON reimb.reimb_status_id = s.reimb_status_id;

SELECT reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_receipt, 
    u.user_first_name as author_fn, u.user_last_name as author_ln
FROM ers_reimbursement r
JOIN ers_users u
ON r.reimb_author = u.ers_user_id;

--Reimbursement with status, type, and user author
SELECT reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_receipt, s.reimb_status, t.reimb_type,
    u.user_first_name as author_fn, u.user_last_name as author_ln
    FROM ers_reimbursement reimb
    JOIN ers_reimbursement_type t
        ON reimb.reimb_type_id = t.reimb_type_id
    JOIN ers_reimbursement_status s
        ON reimb.reimb_status_id = s.reimb_status_id
    JOIN ers_users u
    ON reimb.reimb_author = u.ers_user_id;
    
----
SELECT reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_receipt, s.reimb_status, t.reimb_type,
    u.user_first_name as author_fn, u.user_last_name as author_ln, ad.user_first_name, ad.user_last_name
    FROM ers_reimbursement reimb
    JOIN ers_reimbursement_type t
        ON reimb.reimb_type_id = t.reimb_type_id
    JOIN ers_reimbursement_status s
        ON reimb.reimb_status_id = s.reimb_status_id
    JOIN ers_users u
    ON reimb.reimb_author = u.ers_user_id
    JOIN ers_users ad
    ON reimb.reimb_author = ad.ers_user_id;