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
INSERT INTO ers_users VALUES (0, 'gsimms','65bikee','Garry-Garth','Simmons','garth@asdf.com',1);

-- TICKET --VALUES(id, amount, time submitted, time resolved, descr, recipt, author, resolver, status id, type id)
INSERT INTO ers_reimbursement VALUES (0, 9.99, NULL, NULL,'here is a description',NULL,1,NULL,0,2);
INSERT INTO ers_reimbursement VALUES (0, 39.99, NULL, NULL,'hahaha',NULL,2,3,2,4);
INSERT INTO ers_reimbursement VALUES (0, 99.57, NULL, NULL,'timestamp test',NULL,1,null,0,2);
INSERT INTO ers_reimbursement VALUES (0, 54.99, NULL, NULL,'hahaha',NULL,2,NULL,0,3);
INSERT INTO ers_reimbursement VALUES (0, 99.57, NULL, NULL,'manager selfaprove test',NULL,3,null,0,2);
-- checking that update trigger works to add resolved timestamp when updating the status of the ticket
UPDATE ers_reimbursement 
SET reimb_status_id = 1, reimb_resolver = 3
WHERE reimb_id = 41;

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
        SELECT reimb_id, reimb_amount, reimb_submitted, reimb_resolved , reimb_description, reimb_receipt, s.reimb_status, t.reimb_type,
        u.user_first_name as author_fn, u.user_last_name as author_ln, u.ers_user_id as author_id,
        ad.user_first_name as resolver_fn, ad.user_last_name as resolver_ln, ad.ers_user_id as resolver_id
            FROM ers_reimbursement reimb
            JOIN ers_reimbursement_type t
                ON reimb.reimb_type_id = t.reimb_type_id
            JOIN ers_reimbursement_status s
                ON reimb.reimb_status_id = s.reimb_status_id
            JOIN ers_users u
            ON reimb.reimb_author = u.ers_user_id
            LEFT OUTER JOIN ers_users ad
            ON reimb.reimb_resolver = ad.ers_user_id
        WHERE u.ers_user_id = user_id
        
        ORDER BY reimb_submitted DESC;
END;
/
--get all reimbursements

CREATE OR REPLACE PROCEDURE get_all_reimbursements
                    (this_cursor OUT SYS_REFCURSOR)
IS
BEGIN
    OPEN this_cursor FOR
    SELECT reimb_id, reimb_amount, reimb_submitted, reimb_resolved , reimb_description, reimb_receipt, s.reimb_status, t.reimb_type,
        u.user_first_name as author_fn, u.user_last_name as author_ln, u.ers_user_id as author_id,
        ad.user_first_name as resolver_fn, ad.user_last_name as resolver_ln, ad.ers_user_id as resolver_id
    FROM ers_reimbursement reimb
    JOIN ers_reimbursement_type t
        ON reimb.reimb_type_id = t.reimb_type_id
    JOIN ers_reimbursement_status s
        ON reimb.reimb_status_id = s.reimb_status_id
    JOIN ers_users u
    ON reimb.reimb_author = u.ers_user_id
    LEFT OUTER JOIN ers_users ad
    ON reimb.reimb_resolver = ad.ers_user_id
    
    ORDER BY reimb_submitted DESC;
END;
/

CREATE OR REPLACE PROCEDURE get_by_reimb_id (
    ticket_id     IN            NUMBER,
    this_cursor   OUT           SYS_REFCURSOR
) IS
BEGIN
    OPEN this_cursor FOR SELECT
                             reimb_id,
                             reimb_amount,
                             reimb_submitted,
                             reimb_resolved,
                             reimb_description,
                             reimb_receipt,
                             s.reimb_status,
                             t.reimb_type,
                             u.user_first_name    AS author_fn,
                             u.user_last_name     AS author_ln,
                             ad.user_first_name   AS resolver_fn,
                             ad.user_last_name    AS resolver_ln
                         FROM
                             ers_reimbursement          reimb
                             JOIN ers_reimbursement_type     t ON reimb.reimb_type_id = t.reimb_type_id
                             JOIN ers_reimbursement_status   s ON reimb.reimb_status_id = s.reimb_status_id
                             JOIN ers_users                  u ON reimb.reimb_author = u.ers_user_id
                             LEFT OUTER JOIN ers_users      ad ON reimb.reimb_resolver = ad.ers_user_id
                         WHERE
                             reimb.reimb_id = ticket_id;
                    

END;
/
