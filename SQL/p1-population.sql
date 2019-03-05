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
INSERT INTO ers_users VALUES (0, 'zmaraz','5678','Zachary','Marazita','zmaraz@asdf.com',1);

-- TICKET --VALUES(id, amount, time submitted, time resolved, descr, recipt, author, resolver, status id, type id)
INSERT INTO ers_reimbursement VALUES (
    1, 9.99, NULL, NULL,'here is a description',NULL,32,NULL,0,2);
INSERT INTO ers_reimbursement VALUES (
    0, 39.99, NULL, NULL,'hahaha',NULL,37,23,2,4);

--get all info from users
SELECT ers_user_id, ers_username, ers_password, user_first_name, user_last_name, user_email, r.user_role
FROM ers_users u
JOIN ers_user_roles r
ON u.user_role_id = r.ers_user_role_id;

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

--Reimbursement with status, type, and user author and resolver
SELECT reimb_id, reimb_amount, reimb_submitted, reimb_resolved , reimb_description, reimb_receipt, s.reimb_status, t.reimb_type,
        u.user_first_name as author_fn, u.user_last_name as author_ln, 
        ad.user_first_name as resolver_fn, ad.user_last_name as resolver_ln
    FROM ers_reimbursement reimb
    JOIN ers_reimbursement_type t
        ON reimb.reimb_type_id = t.reimb_type_id
    JOIN ers_reimbursement_status s
        ON reimb.reimb_status_id = s.reimb_status_id
    JOIN ers_users u
    ON reimb.reimb_author = u.ers_user_id
    LEFT OUTER JOIN ers_users ad
    ON reimb.reimb_resolver = ad.ers_user_id;
    
CREATE OR REPLACE PROCEDURE get_reimbursement_by_id
                            (user_id IN NUMBER,
                            this_cursor OUT SYS_REFCURSOR)
IS
BEGIN
    OPEN this_cursor FOR
        SELECT reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_receipt, s.reimb_status, t.reimb_type,
        u.user_first_name as author_fn, u.user_last_name as author_ln
            FROM ers_reimbursement reimb
            JOIN ers_reimbursement_type t
                ON reimb.reimb_type_id = t.reimb_type_id
            JOIN ers_reimbursement_status s
                ON reimb.reimb_status_id = s.reimb_status_id
            JOIN ers_users u
            ON reimb.reimb_author = u.ers_user_id
        WHERE u.ers_user_id = user_id;
END;
/

--get all reimbursements

CREATE OR REPLACE PROCEDURE get_all_reimbursements
                    (this_cursor OUT SYS_REFCURSOR)
IS
BEGIN
    OPEN this_cursor FOR
    SELECT reimb_id, reimb_amount, reimb_submitted, reimb_resolved , reimb_description, reimb_receipt, s.reimb_status, t.reimb_type,
        u.user_first_name as author_fn, u.user_last_name as author_ln, 
        ad.user_first_name as resolver_fn, ad.user_last_name as resolver_ln
    FROM ers_reimbursement reimb
    JOIN ers_reimbursement_type t
        ON reimb.reimb_type_id = t.reimb_type_id
    JOIN ers_reimbursement_status s
        ON reimb.reimb_status_id = s.reimb_status_id
    JOIN ers_users u
    ON reimb.reimb_author = u.ers_user_id
    LEFT OUTER JOIN ers_users ad
    ON reimb.reimb_resolver = ad.ers_user_id;
END;
/
    
----
