insert into user_account (user_id, user_password, user_name, user_email, user_phone_number, created_at, created_by, modified_at, modified_by, role_type)
values ('user1', '1234', 'kwon', 'b@naver.com', '010-1111-2222', now(), 'kwon', now(), 'kwon', 'MANAGER');

insert into user_account (user_id, user_password, user_name, user_email, user_phone_number, created_at, created_by, modified_at, modified_by, role_type)
values ('user2', '1234', 'kwon2', 'b2@naver.com', '010-1111-2222', now(), 'kwon2', now(), 'kwon2', 'ADMIN');

insert into user_account (user_id, user_password, user_name, user_email, user_phone_number, created_at, created_by, modified_at, modified_by, role_type)
values ('user3', '1234', 'kwon3', 'b3@naver.com', '010-1111-2222', now(), 'kwon3', now(), 'kwon3', 'USER');

insert into user_account (user_id, user_password, user_name, user_email, user_phone_number, created_at, created_by, modified_at, modified_by, role_type)
values ('user4', '1234', 'kwon4', 'b2@naver.com', '010-1111-2222', now(), 'kwon4', now(), 'kwon4', 'ADMIN');

insert into user_account (user_id, user_password, user_name, user_email, user_phone_number, created_at, created_by, modified_at, modified_by, role_type)
values ('user5', '1234', 'kwon5', 'b2@naver.com', '010-1111-2222', now(), 'kwon5', now(), 'kwon5', 'ADMIN');

insert into user_account (user_id, user_password, user_name, user_email, user_phone_number, created_at, created_by, modified_at, modified_by, role_type)
values ('user6', '1234', 'kwon6', 'b2@naver.com', '010-1111-2222', now(), 'kwon6', now(), 'kwon6', 'ADMIN');

insert into category (category_name)
values ('문구'),
       ('사무용품');

insert into supplier (supplier_name, supplier_code)
values ('배송업체1', 'A12'),
       ('배송업체2', 'B12'),
       ('배송업체3', 'C12'),
       ('배송업체4', 'D12'),
       ('배송업체5', 'E12'),
       ('배송업체6', 'F12'),
       ('배송업체7', 'G12');

insert into product (user_id, product_name, category_id, supplier_id, product_detail, product_cost, product_price, product_stock_quantity, product_sales_quantity, created_at, created_by, modified_at, modified_by)
values ('user2', '볼펜', 1, 1, '볼펜이다', 100, 1000, 100, 10, now(), 'user2', now(), 'user2'),
       ('user2', '종이', 2, 2, '종이다', 100, 1000, 100, 10, now(), 'user2', now(), 'user2'),
       ('user2', '마우스', 2, 3, '마우스다', 100, 1000, 100, 10, now(), 'user2', now(), 'user2'),
       ('user4', '색연필', 2, 3, '색연필이다', 100, 1000, 100, 10, now(), 'user4', now(), 'user4'),
       ('user4', '교과서', 2, 3, '교과서다', 100, 1000, 100, 10, now(), 'user4', now(), 'user4'),
       ('user4', '공책', 2, 3, '공책이다', 100, 1000, 100, 10, now(), 'user4', now(), 'user4'),
       ('user4', '샤프', 2, 3, '샤프다', 100, 1000, 100, 10, now(), 'user4', now(), 'user4'),
       ('user5', '샤프심', 2, 3, '샤프심다', 100, 1000, 100, 10, now(), 'user5', now(), 'user5'),
       ('user5', '연필', 2, 3, '연필이다', 100, 1000, 100, 10, now(), 'user5', now(), 'user5'),
       ('user5', '연습장', 2, 3, '연습장이다', 100, 1000, 100, 10, now(), 'user5', now(), 'user5'),
       ('user5', '책갈피', 2, 3, '책갈피다', 100, 1000, 100, 10, now(), 'user5', now(), 'user5'),
       ('user5', '가위', 2, 3, '가위다', 100, 1000, 100, 10, now(), 'user5', now(), 'user5'),
       ('user6', '모나미', 2, 3, '모나미다', 100, 1000, 100, 10, now(), 'user6', now(), 'user6'),
       ('user6', '커터칼', 2, 3, '커터칼이다', 100, 1000, 100, 10, now(), 'user6', now(), 'user6');

insert into product_image (image_path, image_type, original_name, save_name, product_id)
values ('C:\\workspace\\springbootwork\\SI-Track\\Si-Track-Frontend\\public\\img', 'Thumbnail', 'no01.png', 'no01.png', 1),
       ('C:\\workspace\\springbootwork\\SI-Track\\Si-Track-Frontend\\public\\img', 'Thumbnail', 'no02.png', 'no02.png', 2),
       ('C:\\workspace\\springbootwork\\SI-Track\\Si-Track-Frontend\\public\\img', 'Thumbnail', 'no03.png', 'no03.png', 3),
       ('C:\\workspace\\springbootwork\\SI-Track\\Si-Track-Frontend\\public\\img', 'Thumbnail', 'no04.png', 'no04.png', 4),
       ('C:\\workspace\\springbootwork\\SI-Track\\Si-Track-Frontend\\public\\img', 'Thumbnail', 'no05.png', 'no05.png', 5),
       ('C:\\workspace\\springbootwork\\SI-Track\\Si-Track-Frontend\\public\\img', 'Thumbnail', 'no06.png', 'no06.png', 6),
       ('C:\\workspace\\springbootwork\\SI-Track\\Si-Track-Frontend\\public\\img', 'Thumbnail', 'no07.png', 'no07.png', 7),
       ('C:\\workspace\\springbootwork\\SI-Track\\Si-Track-Frontend\\public\\img', 'Thumbnail', 'no08.png', 'no08.png', 8),
       ('C:\\workspace\\springbootwork\\SI-Track\\Si-Track-Frontend\\public\\img', 'Thumbnail', 'no09.png', 'no09.png', 9),
       ('C:\\workspace\\springbootwork\\SI-Track\\Si-Track-Frontend\\public\\img', 'Thumbnail', 'no10.png', 'no10.png', 10),
       ('C:\\workspace\\springbootwork\\SI-Track\\Si-Track-Frontend\\public\\img', 'Thumbnail', 'no11.png', 'no11.png', 11),
       ('C:\\workspace\\springbootwork\\SI-Track\\Si-Track-Frontend\\public\\img', 'Thumbnail', 'no12.png', 'no12.png', 12),
       ('C:\\workspace\\springbootwork\\SI-Track\\Si-Track-Frontend\\public\\img', 'Thumbnail', 'no13.png', 'no13.png', 13),
       ('C:\\workspace\\springbootwork\\SI-Track\\Si-Track-Frontend\\public\\img', 'Thumbnail', 'no14.png', 'no14.png', 14);

