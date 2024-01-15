insert into user_account (user_id, user_password, user_name, user_email, user_phone_number, created_at, created_by, modified_at, modified_by, role_type)
values ('user1', '1234', 'kwon', 'b@naver.com', '010-1111-2222', now(), 'kwon', now(), 'kwon', 'MANAGER');

insert into user_account (user_id, user_password, user_name, user_email, user_phone_number, created_at, created_by, modified_at, modified_by, role_type)
values ('user2', '1234', 'kwon2', 'b2@naver.com', '010-1111-2222', now(), 'kwon', now(), 'kwon', 'ADMIN');

insert into user_account (user_id, user_password, user_name, user_email, user_phone_number, created_at, created_by, modified_at, modified_by, role_type)
values ('user3', '1234', 'kwon3', 'b3@naver.com', '010-1111-2222', now(), 'kwon', now(), 'kwon', 'USER');

insert into category (category_name)
values ('문구'),
       ('사무용품');

insert into supplier (supplier_name, supplier_code)
values ('배송업체1', 'A12'),
       ('배송업체2', 'B12'),
       ('배송업체3', 'C12');

insert into product (user_id, product_name, category_id, supplier_id, product_detail, product_cost, product_price, product_stock_quantity, product_sales_quantity, created_at, created_by, modified_at, modified_by)
values ('user2', '볼펜', 1, 1, '볼펜이다', 100, 1000, 100, 10, now(), 'kwon', now(), 'kwon'),
       ('user2', '종이', 2, 2, '종이다', 100, 1000, 100, 10, now(), 'kwon', now(), 'kwon'),
       ('user2', '마우스', 2, 3, '마우스다', 100, 1000, 100, 10, now(), 'kwon', now(), 'kwon');
