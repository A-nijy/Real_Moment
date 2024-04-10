-- Grade 등급 더미데이터

INSERT INTO grade (grade_name, grade_price, reward_rate) VALUES
('WHITE', 0, 1),
('SILVER', 100000, 2),
('GOLD',500000, 3),
('VIP', 1000000, 4),
('VVIP', 5000000, 5);



-- Member 회원 더미데이터  ( 비밀번호 : Password@1 ~ 5 )

INSERT INTO member (grade_id, login_id, login_password, email, name, tel, birth_date, gender, this_year_pay, point, is_delete, roles, created_date, last_modified_date, recently_login)
VALUES
(2, 'member111', '$2a$10$zkoF4XDZ7PylcEWdnx61Ae/6RawQo0PYkQHK7R1gTtW3eZAM42Zt.', 'member1@gmail.com', 'A', '010-1111-1111', '1990-01-09', 'MAN', 100000, 100, false, 'ROLE_USER', '2020-10-01 12:00:00', '2023-11-01 12:00:00', '2023-11-01 12:00:00'),
(4, 'member222', '$2a$10$iCih5MD1ovMQ5xbw3t//F.0bzT6LwhDsS63W2lEIlMoAsP76TGrK6', 'member2@gmail.com', 'B', '010-2222-2222', '1993-05-06', 'WOMAN', 1000000, 1000, false, 'ROLE_USER', '2023-01-01 12:00:00', '2023-11-01 12:00:00', '2023-11-01 12:00:00'),
(3, 'member333', '$2a$10$2ZMkE.ASSYOSfJk1FysW.e.murDSExI9CdtFbHwkDkb2y7e0W/Jaq', 'member3@gmail.com', 'C', '010-3333-3333', '1995-11-01', 'WOMAN', 500000, 500, false, 'ROLE_USER', '2019-04-01 12:00:00', '2023-11-01 12:00:00', '2023-11-01 12:00:00'),
(5, 'member444', '$2a$10$C0HySi8ebNzPGPDl1/vOXOF5k2kQJxDE0.iWqo0wNO6xhA42loBaq', 'member4@gmail.com', 'D', '010-4444-4444', '2000-01-01', 'MAN', 12500000, 12500, true, 'ROLE_USER', '2020-07-01 12:00:00', '2023-11-01 12:00:00', '2023-11-01 12:00:00'),
(5, 'member555', '$2a$10$kL5exOvbUoKL9utaJ4DFe.OcOfJlFPxtt142iYUe87Tz8ofndpapK', 'member5@gmail.com', 'E', '010-5555-5555', '1998-04-28', 'MAN', 10000000, 10000, false, 'ROLE_USER', '2021-06-01 12:00:00', '2023-11-01 12:00:00', '2023-11-01 12:00:00');



-- Address 주소 더미데이터

INSERT INTO address (member_id, name, main_address, det_address, is_default)
VALUES
(1, 'A', '대전 유성구', '101동 503호', true),
(1, 'A-2', '대전 화구', '202동 703호', FALSE),
(2, 'B', '서울 파구', '301동 507호', FALSE),
(3, 'C', '광주 피구', '601동 604호', FALSE),
(3, 'C-2', '서울 뭐구', '201동 203호', FALSE),
(4, 'D', '대전 서구', '801동 503호', TRUE),
(5, 'E', '대전 당구', '1101동 603호', FALSE),
(5, 'E-2', '부산 바다구', '301동 201호', FALSE);



-- Orders 주문 더미데이터

INSERT INTO orders (member_id, ordered_date, delivery_date, total_price, total_discount_price, use_point, get_point, buy_price, name, main_address, det_address, request_text, tel, status, merchant_uid, imp_uid, reason_text)
VALUES
(1, '2022-01-01 12:30:00', '2022-01-03 12:30:00',32000, 3200, 0, 288, 28800, 'A', '대전 유성구', '101동 503호', '깨질 수 있으니 조심해주세요', '010-1111-1111', 'DONE', NULL, NULL, NULL),
(1, '2022-02-01 12:30:00', '2022-02-04 12:30:00', 80000, 72000, 0, 80, 8000, 'A-2', '대전 화구', '202동 703호', '문앞에 두시면 됩니다.', '010-1111-1111', 'DELIVERY_DONE', NULL, NULL, NULL),
(2, '2022-03-01 12:30:00', '2022-03-03 12:30:00', 24000, 800, 0, 232, 23200, 'B', '서울 파구', '301동 507호', '깨질 수 있으니 조심해주세요', '010-2222-2222', 'DELIVERY_DONE', NULL, NULL, NULL),
(2, '2022-04-01 12:30:00', '2022-04-04 12:30:00', 40000, 13600, 0, 264, 26400, 'B', '서울 파구', '301동 507호', '문앞에 두시면 됩니다.', '010-2222-2222', 'DELIVERY_DONE', NULL, NULL, NULL),
(2, '2022-05-01 12:30:00', '2022-05-05 12:30:00', 15000, 0, 0, 150, 15000, 'B', '서울 파구', '301동 507호', '문앞에 두시면 됩니다.', '010-2222-2222', 'REFUND_DONE', NULL, NULL, '필요없어짐'),
(3, '2022-06-01 12:30:00', '2022-06-03 12:30:00', 50000, 36000, 0, 140, 14000, 'C', '광주 피구', '601동 604호', '깨질 수 있으니 조심해주세요', '010-3333-3333', 'REFUND_DONE', NULL, NULL, '불량 발견'),
(3, '2022-07-01 12:30:00', NULL, 26000, 0, 0, 260, 26000, 'C', '광주 피구', '601동 604호', '문앞에 두시면 됩니다.', '010-3333-3333', 'CANCEL', NULL, NULL, NULL),
(4, '2022-08-01 12:30:00', NULL, 24000, 1600, 0, 224, 22400, 'D', '대전 서구', '801동 503호', '깨질 수 있으니 조심해주세요', '010-4444-4444', 'DELIVERY_DOING', NULL, NULL, NULL),
(5, '2022-09-01 12:30:00', NULL, 16000, 1600, 0, 144, 14400, 'E', '대전 당구', '1101동 603호', '문앞에 두시면 됩니다.', '010-5555-5555', 'DELIVERY_DOING', NULL, NULL, NULL),
(5, '2022-10-01 12:30:00', NULL, 8000, 0, 0, 80, 8000, 'E-2', '부산 바다구', '301동 201호', '깨질 수 있으니 조심해주세요', '010-5555-5555', 'DELIVERY_DOING', NULL, NULL, NULL),
(5, '2022-11-01 12:30:00', NULL, 10000, 0, 0, 100, 10000, 'E', '대전 당구', '1101동 603호', '깨질 수 있으니 조심해주세요', '010-5555-5555', 'DELIVERY_DOING', NULL, NULL, NULL);



-- Admin 관리자 더미데이터  ( 비밀번호 : Password@1 ~ 4 )

INSERT INTO admin (login_id, login_password, email, name, roles, is_delete, created_date, last_modified_date)
VALUES
('admin111', '$2a$10$zkoF4XDZ7PylcEWdnx61Ae/6RawQo0PYkQHK7R1gTtW3eZAM42Zt.', 'admin1@gmail.com', '대표', 'ROLE_REPRESENTATIVE', FALSE, '2022-01-03 12:30:00', '2022-01-03 12:30:00'),
('admin222', '$2a$10$iCih5MD1ovMQ5xbw3t//F.0bzT6LwhDsS63W2lEIlMoAsP76TGrK6', 'admin2@gmail.com', '이사', 'ROLE_OPERATOR', FALSE, '2022-01-03 12:30:00', '2022-01-03 12:30:00'),
('admin333', '$2a$10$2ZMkE.ASSYOSfJk1FysW.e.murDSExI9CdtFbHwkDkb2y7e0W/Jaq', 'admin3@gmail.com', '사원', 'ROLE_CUSTOMER', FALSE, '2022-01-03 12:30:00', '2022-01-03 12:30:00'),
('admin444', '$2a$10$C0HySi8ebNzPGPDl1/vOXOF5k2kQJxDE0.iWqo0wNO6xhA42loBaq', 'admin4@gmail.com', '사원', 'ROLE_ADMIN', FALSE, '2022-01-03 12:30:00', '2022-01-03 12:30:00');



-- Announcements 공지사항 더미데이터

INSERT INTO announcement (admin_id, title, content, is_fix, view_count, created_date, last_modified_date, created_by, last_modified_by)
VALUES
(2, 'Announcement title1', 'Announcement content1', FALSE, 100, '2022-01-05 10:00:00', '2022-01-05 10:00:00', '이사', '이사'),
(1, 'Announcement title2', 'Announcement content2', TRUE, 500, '2023-01-06 10:00:00', '2023-01-08 10:00:00', '대표', '대표'),
(3, 'Announcement title3', 'Announcement content3', FALSE, 20, '2023-02-07 10:00:00', '2023-02-07 10:00:00', '직원A', '직원A'),
(3, 'Announcement title4', 'Announcement content4', FALSE, 100, '2023-04-08 10:00:00', '2023-04-08 10:00:00', '직원A', '직원A'),
(4, 'Announcement title5', 'Announcement content5', TRUE, 800, '2023-08-09 10:00:00', '2023-08-09 10:00:00', '직원B', '직원B');



--Category 카테고리 더미데이터

INSERT INTO category (name, parent_id) VALUES
('화장', NULL),
('관리', NULL),
('립스틱', 1),
('틴트', 1),
('파우더', 1),
('마스크 팩', 2),
('수분 크림', 2),
('클랜징 오일', 2);



-- Item 상품 더미데이터


INSERT INTO item (category_id, name, content, price, discount_rate, discount_price, sell_price, created_date, last_modified_date, stock, sell_count, revenue, is_sell, is_delete,created_by, last_modified_by)
VALUES
(3, 'A립스틱', 'item content1', 8000, 10, 800, 7200, '2022-01-29 10:00:00', '2022-01-29 10:00:00', 50, 0, 0, TRUE, FALSE, '직원A', '직원A'),
(3, 'B립스틱', 'item content2', 8000, 0, 0, 8000, '2022-05-29 10:00:00', '2022-05-29 10:00:00', 10, 0, 0, TRUE, FALSE, '직원A', '직원A'),
(3, 'C립스틱', 'item content3', 8000, 10, 800, 7200, '2023-08-29 10:00:00', '2023-08-29 10:00:00', 150, 0, 0, TRUE, FALSE, '직원A', '직원A'),
(3, 'D립스틱', 'item content4', 8000, 0, 0, 8000, '2023-08-29 10:00:00', '2023-08-29 10:00:00', 250, 0, 0, FALSE, TRUE, '직원A', '직원A'),
(4, 'A틴트', 'item content5', 10000, 20, 2000, 8000, '2021-05-19 10:00:00', '2023-09-02 11:30:00', 40, 0, 0, TRUE, FALSE, '직원A', '직원B'),
(4, 'B틴트', 'item content6', 8000, 10, 800, 7200, '2022-08-29 10:00:00', '2022-08-29 10:00:00', 240, 0, 0, FALSE, FALSE, '직원A', '직원A'),
(5, 'A파우더', 'item content7', 8000, 10, 800, 7200, '2021-03-29 10:00:00', '2023-09-02 11:30:00', 150, 0, 0, TRUE, FALSE, '직원A', '직원A'),
(6, 'A마스크 팩', 'item content8', 8000, 10, 800, 7200, '2022-08-29 10:00:00', '2023-09-02 11:30:00', 360, 0, 0, TRUE, FALSE, '직원A', '직원A'),
(6, 'B마스크 팩', 'item content9', 8000, 0, 0, 8000, '2023-08-29 10:00:00', '2023-09-02 11:30:00', 420, 0, 0, TRUE, FALSE, '직원A', '직원B'),
(7, 'A수분 크림', 'item content10', 8000, 0, 0, 8000, '2021-08-29 10:00:00', '2021-08-29 10:00:00', 10, 0, 0, TRUE, FALSE, '직원A', '직원A'),
(7, 'B수분 크림', 'item content11', 8000, 0, 0, 8000, '2022-08-29 10:00:00', '2023-09-02 11:30:00', 570, 0, 0, TRUE, FALSE, '직원A', '직원A'),
(7, 'C수분 크림', 'item content12', 8000, 50, 4000, 4000, '2023-08-29 10:00:00', '2023-09-02 11:30:00', 0, 0, 0, FALSE, FALSE, '직원A', '직원A'),
(8, 'A클랜징 오일', 'item content13', 8000, 90, 7200, 800, '2022-08-29 10:00:00', '2022-08-29 10:00:00', 5, 0, 0, TRUE, FALSE, '직원A', '직원A'),
(8, 'B클랜징 오일', 'item content14', 15000, 0, 0, 15000, '2023-08-29 10:00:00', '2023-08-29 10:00:00', 10, 0, 0, FALSE, TRUE, '직원A', '직원A'),
(8, 'C클랜징 오일', 'item content15', 5000, 0, 0, 5000, '2023-08-29 10:00:00', '2023-08-29 10:00:00', 20, 0, 0, TRUE, FALSE, '직원A', '직원A');



-- Wish 찜 더미데이터

INSERT INTO wish (member_id, item_id)
VALUES
(1, 2),
(1, 4),
(5, 13),
(5, 12),
(4, 2);



-- Cart 장바구니 더미데이터

INSERT INTO cart (member_id, item_id, count) VALUES
(1, 1, 1),
(1, 13, 10),
(2, 3, 2),
(4, 6, 2),
(4, 9, 2),
(4, 13, 1);



-- OrderDetail 주문 상세 더미데이터

INSERT INTO order_detail (item_id, order_id, price, discount_rate, discount_price, sell_price, item_count, total_price)
VALUES
(1, 1, 8000, 10, 800, 7200, 2, 14400),
(6, 1, 8000, 10, 800, 7200, 2, 14400),
(13, 2, 8000, 90, 7200, 800, 10, 8000),
(1, 3, 8000, 10, 800, 7200, 1, 7200),
(4, 3, 8000, 0, 0, 8000, 2, 16000),
(8, 4, 8000, 10, 800, 7200, 2, 14400),
(12, 4, 8000, 50, 4000, 4000, 3, 12000),
(14, 5, 15000, 0, 0, 15000, 1, 15000),
(15, 6, 5000, 0, 0, 5000, 2, 10000),
(13, 6, 8000, 90, 7200, 800, 5, 4000),
(11, 7, 8000, 0, 0, 8000, 2, 16000),
(15, 7, 5000, 0, 0, 5000, 2, 10000),
(1, 8, 8000, 10, 800, 7200, 1, 7200),
(2, 8, 8000, 0, 0, 8000, 1, 8000),
(3, 8, 8000, 10, 800, 7200, 1, 7200),
(1, 9, 8000, 10, 800, 7200, 1, 7200),
(7, 9, 8000, 10, 800, 7200, 1, 7200),
(10, 10, 8000, 0, 0, 8000, 1, 8000),
(15, 11, 5000, 0, 0, 5000, 2, 10000);



-- Review 리뷰 더미데이터

INSERT INTO review (member_id, item_id, title, content, star, created_date, last_modified_date)
VALUES
(1, 4, 'review title1', 'review content1', 5, '2023-01-05 12:00:00', '2023-01-05 12:00:00'),
(1, 13, 'review title2', 'review content2', 4, '2023-02-05 12:00:00', '2023-02-05 12:00:00'),
(4, 8, 'review title3', 'review content3', 3, '2023-03-05 12:00:00', '2023-03-05 12:00:00'),
(5, 10, 'review title4', 'review content4', 5, '2023-04-05 12:00:00', '2023-04-05 12:00:00'),
(5, 15, 'review title5', 'review content5', 5, '2023-05-05 12:00:00', '2023-05-05 12:00:00');



-- ItemQA 상품 문의 더미데이터

INSERT INTO itemqa (item_id, member_id, title, content, is_answer, created_date, last_modified_date)
VALUES
(1, 1, 'QA title1', 'QA content1', true, '2023-01-05 10:30:00', '2023-01-05 11:00:00'),
(6, 1, 'QA title2', 'QA content2', true, '2023-02-05 10:30:00', '2023-02-05 11:00:00'),
(1, 3, 'QA title3', 'QA content3', true, '2023-03-05 10:30:00', '2023-03-05 11:00:00'),
(1, 4, 'QA title4', 'QA content4', true, '2023-04-05 10:30:00', '2023-04-05 11:00:00'),
(1, 5, 'QA title5', 'QA content5', false, '2023-05-05 10:30:00', '2023-05-05 11:00:00'),
(15, 5, 'QA title6', 'QA content6', false, '2023-06-05 10:30:00', '2023-06-07 11:00:00');



-- QAComment 상품 문의 댓글 더미데이터

INSERT INTO qacomment (admin_id, item_QA_id, content, created_date, last_modified_date, created_by, last_modified_by)
VALUES
(1, 1, 'QA_comments content1', '2023-03-07 12:00:00', '2023-03-07 12:00:00', '대표', '대표'),
(4, 2, 'QA_comments content2', '2023-03-07 12:00:00', '2023-03-07 12:00:00', '직원B', '직원B'),
(3, 3, 'QA_comments content3', '2023-03-07 12:00:00', '2023-03-07 12:00:00', '직원A', '직원A'),
(2, 4, 'QA_comments content4', '2023-04-07 12:00:00', '2023-04-07 12:00:00', '이사', '이사');



-- OneOnOne 1대1 문의 더미데이터

INSERT INTO one_on_one (member_id, title, content, is_answer, created_date, last_modified_date)
VALUES
(1, '질문1', '내용1', true, '2023-03-07 12:00:00', '2023-03-07 12:00:00'),
(1, '질문2', '내용2', false, '2023-02-07 12:00:00', '2023-02-07 12:00:00'),
(2, '질문3', '내용3', true, '2023-01-07 12:00:00', '2023-01-07 12:00:00'),
(3, '질문4', '내용4', true, '2023-07-07 12:00:00', '2023-07-07 12:00:00'),
(3, '질문5', '내용5', false, '2023-03-07 12:00:00', '2023-03-07 12:00:00'),
(3, '질문6', '내용6', true, '2023-09-07 12:00:00', '2023-09-07 12:00:00');



-- Comment 1대1 문의 댓글 더미데이터

INSERT INTO comment (one_on_one_id, admin_id, content, created_date, last_modified_date, created_by, last_modified_by)
VALUES
(1, 3, '내용1', '2023-03-07 12:00:00', '2023-03-07 12:00:00', '직원A', '직원A'),
(3, 4, '내용2', '2023-02-07 12:00:00', '2023-02-07 12:00:00', '직원B', '직원B'),
(4, 3, '내용3', '2023-01-07 12:00:00', '2023-01-07 12:00:00', '직원A', '직원A'),
(6, 3, '내용4', '2023-07-07 12:00:00', '2023-07-07 12:00:00', '직원A', '직원A');