-- Grade 등급 더미데이터

INSERT INTO grade (grade_name, reward_rate) VALUES
('WHITE', 1),
('SILVER', 2),
('GOLD', 3),
('VIP', 4),
('VVIP', 5);



-- Member 회원 더미데이터

INSERT INTO member (grade_id, login_id, login_password, email, name, tel, birth_date, gender, point, created_date, last_modified_date, recently_login)
VALUES
(1, 'member11', 'password1@', 'member1@gmail.com', 'A', '010-1111-1111', '1990-01-09', '남', 100, '2020-10-01 12:00:00', '2023-11-01 12:00:00', '2023-11-01 12:00:00'),
(2, 'member22', 'password2@', 'member2@gmail.com', 'B', '010-2222-2222', '1993-05-06', '여', 1000, '2023-01-01 12:00:00', '2023-11-01 12:00:00', '2023-11-01 12:00:00'),
(2, 'member33', 'password3@', 'member3@gmail.com', 'C', '010-3333-3333', '1995-11-01', '여', 500, '2019-04-01 12:00:00', '2023-11-01 12:00:00', '2023-11-01 12:00:00'),
(4, 'member44', 'password4@', 'member4@gmail.com', 'D', '010-4444-4444', '2000-01-01', '여', 12500, '2020-07-01 12:00:00', '2023-11-01 12:00:00', '2023-11-01 12:00:00'),
(3, 'member55', 'password5@', 'member5@gmail.com', 'E', '010-5555-5555', '1998-04-28', '남', 10000, '2021-06-01 12:00:00', '2023-11-01 12:00:00', '2023-11-01 12:00:00');



-- Address 주소 더미데이터

INSERT INTO address (member_id, name, address, det_address, is_def_address)
VALUES
(1, 'A', '대전 유성구', '101동 503호', FALSE),
(1, 'A-2', '대전 화구', '202동 703호', FALSE),
(2, 'B', '서울 파구', '301동 507호', FALSE),
(3, 'C', '광주 피구', '601동 604호', FALSE),
(3, 'C-2', '서울 뭐구', '201동 203호', FALSE),
(4, 'D', '대전 서구', '801동 503호', FALSE),
(5, 'E', '대전 당구', '1101동 603호', FALSE),
(5, 'E-2', '부산 바다구', '301동 201호', FALSE);



-- Orders 주문 더미데이터

INSERT INTO orders (member_id, ordered_date, shipped_date, price, name, address, det_address, request, tel, status, refound_text)
VALUES
(1, '2022-01-01 12:30:00', '2022-01-03 12:30:00', 5000, 'A', '대전 유성구', '101동 503호', '깨질 수 있으니 조심해주세요', '010-1111-1111', '배송완료', NULL),
(1, '2022-02-01 12:30:00', '2022-02-04 12:30:00', 15000, 'A-2', '대전 화구', '202동 703호', '문앞에 두시면 됩니다.', '010-1111-1111', '배송완료', NULL),
(2, '2022-03-01 12:30:00', '2022-03-03 12:30:00', 2000, 'B', '서울 파구', '301동 507호', '깨질 수 있으니 조심해주세요', '010-2222-2222', '배송완료', NULL),
(2, '2022-04-01 12:30:00', '2022-04-04 12:30:00', 30000, 'B', '서울 파구', '301동 507호', '문앞에 두시면 됩니다.', '010-2222-2222', '배송완료', NULL),
(2, '2022-05-01 12:30:00', '2022-05-05 12:30:00', 25000, 'B', '서울 파구', '301동 507호', '문앞에 두시면 됩니다.', '010-2222-2222', '환불완료', '필요없어짐'),
(3, '2022-06-01 12:30:00', '2022-06-03 12:30:00', 9000, 'C', '광주 피구', '601동 604호', '깨질 수 있으니 조심해주세요', '010-3333-3333', '환불완료', '불량 발견'),
(3, '2022-07-01 12:30:00', NULL, 11000, 'C', '광주 피구', '601동 604호', '문앞에 두시면 됩니다.', '010-3333-3333', '주문취소', NULL),
(4, '2022-08-01 12:30:00', NULL, 20000, 'D', '대전 서구', '801동 503호', '깨질 수 있으니 조심해주세요', '010-4444-4444', '배송중', NULL),
(5, '2022-09-01 12:30:00', NULL, 45000, 'E', '대전 당구', '1101동 603호', '문앞에 두시면 됩니다.', '010-5555-5555', '배송중', NULL),
(5, '2022-10-01 12:30:00', NULL, 65000, 'E-2', '부산 바다구', '301동 201호', '깨질 수 있으니 조심해주세요', '010-5555-5555', '배송중', NULL),
(5, '2022-11-01 12:30:00', NULL, 20000, 'E', '대전 당구', '1101동 603호', '깨질 수 있으니 조심해주세요', '010-5555-5555', '배송중', NULL);



-- Admin 관리자 더미데이터

INSERT INTO admin (id, password, email, name, grade, created_date, last_modified_date)
VALUES
('admin1', 'password1', 'admin1@gmail.com', '대표', 1, '2022-01-03 12:30:00', '2022-01-03 12:30:00'),
('admin2', 'password2', 'admin2@gmail.com', '이사', 2, '2022-01-03 12:30:00', '2022-01-03 12:30:00'),
('admin3', 'password3', 'admin3@gmail.com', '사원', 4, '2022-01-03 12:30:00', '2022-01-03 12:30:00'),
('admin4', 'password4', 'admin4@gmail.com', '사원', 4, '2022-01-03 12:30:00', '2022-01-03 12:30:00');



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


INSERT INTO item (category_id, name, content, price, discount_rate, sell_price, created_date, last_modified_date, stock, is_sell_check, is_delete_check, main_img, serve_img, created_by, last_modified_by)
VALUES
(3, 'A립스틱', 'item content1', 8000, 10, 7200, '2022-01-29 10:00:00', '2022-01-29 10:00:00', 50, TRUE, FALSE, 'main1.jpg', 'serve1.jpg', '직원A', '직원A'),
(3, 'B립스틱', 'item content2', 8000, 0, 8000, '2022-05-29 10:00:00', '2022-05-29 10:00:00', 10, TRUE, FALSE, 'main2.jpg', 'serve2.jpg', '직원A', '직원A'),
(3, 'C립스틱', 'item content3', 8000, 10, 7200, '2023-08-29 10:00:00', '2023-08-29 10:00:00', 150, TRUE, FALSE, 'main3.jpg', 'serve3.jpg', '직원A', '직원A'),
(3, 'D립스틱', 'item content4', 8000, 0, 8000, '2023-08-29 10:00:00', '2023-08-29 10:00:00', 250, TRUE, FALSE, 'main4.jpg', 'serve4.jpg', '직원A', '직원A'),
(4, 'A틴트', 'item content5', 10000, 20, 8000, '2021-05-19 10:00:00', '2023-09-02 11:30:00', 40, TRUE, FALSE, 'main5.jpg', 'serve5.jpg', '직원A', '직원B'),
(4, 'B틴트', 'item content6', 8000, 10, 7200, '2022-08-29 10:00:00', '2022-08-29 10:00:00', 240, FALSE, FALSE, 'main6.jpg', 'serve6.jpg', '직원A', '직원A'),
(5, 'A파우더', 'item content7', 8000, 10, 7200, '2021-03-29 10:00:00', '2023-09-02 11:30:00', 150, TRUE, FALSE, 'main7.jpg', 'serve7.jpg', '직원A', '직원A'),
(6, 'A마스크 팩', 'item content8', 8000, 10, 7200, '2022-08-29 10:00:00', '2023-09-02 11:30:00', 360, TRUE, FALSE, 'main8.jpg', 'serve8.jpg', '직원A', '직원A'),
(6, 'B마스크 팩', 'item content9', 8000, 0, 8000, '2023-08-29 10:00:00', '2023-09-02 11:30:00', 420, TRUE, FALSE, 'main9.jpg', 'serve9.jpg', '직원A', '직원B'),
(7, 'A수분 크림', 'item content10', 8000, 0, 8000, '2021-08-29 10:00:00', '2021-08-29 10:00:00', 10, TRUE, FALSE, 'main10.jpg', 'serve10.jpg', '직원A', '직원A'),
(7, 'B수분 크림', 'item content11', 8000, 0, 8000, '2022-08-29 10:00:00', '2023-09-02 11:30:00', 570, TRUE, FALSE, 'main11.jpg', 'serve11.jpg', '직원A', '직원A'),
(7, 'C수분 크림', 'item content12', 8000, 50, 4000, '2023-08-29 10:00:00', '2023-09-02 11:30:00', 0, FALSE, FALSE, 'main12.jpg', 'serve12.jpg', '직원A', '직원A'),
(8, 'A클랜징 오일', 'item content13', 8000, 90, 800, '2022-08-29 10:00:00', '2022-08-29 10:00:00', 5, TRUE, FALSE, 'main13.jpg', 'serve13.jpg', '직원A', '직원A'),
(8, 'B클랜징 오일', 'item content14', 15000, 0, 15000, '2023-08-29 10:00:00', '2023-08-29 10:00:00', 10, TRUE, FALSE, 'main14.jpg', 'serve14.jpg', '직원A', '직원A'),
(8, 'C클랜징 오일', 'item content15', 5000, 0, 5000, '2023-08-29 10:00:00', '2023-08-29 10:00:00', 20, TRUE, FALSE, 'main15.jpg', 'serve15.jpg', '직원A', '직원A');



-- Wishlist 찜 더미데이터

INSERT INTO wish_list (member_id, item_id)
VALUES
(1, 2),
(1, 6),
(5, 13),
(5, 12),
(4, 2);



-- Cart 장바구니 더미데이터

INSERT INTO cart (member_id, item_id, stock, price, is_check) VALUES
(1, 1, 1, 7200, TRUE),
(1, 13, 10, 8000, TRUE),
(2, 3, 2, 14400, TRUE),
(4, 6, 2, 14400, TRUE),
(4, 9, 2, 16000, TRUE),
(4, 13, 1, 800, TRUE);



-- OrderDetail 주문 상세 더미데이터

INSERT INTO order_detail (item_id, order_id, fixed_price, discount_rate, sell_price, item_count)
VALUES
(1, 1, 8000, 10, 7200, 2),
(6, 1, 8000, 10, 7200, 2),
(13, 2, 8000, 90, 800, 10),
(1, 3, 8000, 10, 7200, 1),
(4, 3, 8000, 0, 8000, 2),
(8, 4, 8000, 10, 7200, 2),
(12, 4, 8000, 50, 4000, 3),
(14, 5, 15000, 0, 15000, 1),
(15, 6, 5000, 0, 5000, 2),
(13, 6, 8000, 90, 800, 5),
(11, 7, 8000, 0, 8000, 2),
(15, 7, 5000, 0, 5000, 2),
(1, 8, 8000, 10, 7200, 1),
(2, 8, 8000, 0, 8000, 1),
(3, 8, 8000, 10, 7200, 1),
(1, 9, 8000, 10, 7200, 1),
(7, 9, 8000, 10, 7200, 1),
(10, 10, 8000, 0, 8000, 1),
(15, 11, 5000, 0, 5000, 2);



-- Review 리뷰 더미데이터

INSERT INTO review (member_id, item_id, title, content, star, created_date, last_modified_date)
VALUES
(1, 1, 'review title1', 'review content1', 5, '2023-01-05 12:00:00', '2023-01-05 12:00:00'),
(1, 13, 'review title2', 'review content2', 4, '2023-02-05 12:00:00', '2023-02-05 12:00:00'),
(4, 8, 'review title3', 'review content3', 3, '2023-03-05 12:00:00', '2023-03-05 12:00:00'),
(5, 10, 'review title4', 'review content4', 5, '2023-04-05 12:00:00', '2023-04-05 12:00:00'),
(5, 15, 'review title5', 'review content5', 5, '2023-05-05 12:00:00', '2023-05-05 12:00:00');



-- ItemQA 상품 문의 더미데이터

INSERT INTO itemqa (item_id, member_id, title, content, created_date, last_modified_date)
VALUES
(1, 1, 'QA title1', 'QA content1', '2023-01-05 10:30:00', '2023-01-05 11:00:00'),
(6, 1, 'QA title2', 'QA content2', '2023-02-05 10:30:00', '2023-02-05 11:00:00'),
(1, 3, 'QA title3', 'QA content3', '2023-03-05 10:30:00', '2023-03-05 11:00:00'),
(1, 4, 'QA title4', 'QA content4', '2023-04-05 10:30:00', '2023-04-05 11:00:00'),
(1, 5, 'QA title5', 'QA content5', '2023-05-05 10:30:00', '2023-05-05 11:00:00'),
(15, 5, 'QA title6', 'QA content6', '2023-06-05 10:30:00', '2023-06-07 11:00:00');



-- QAComment 상품 문의 댓글 더미데이터

INSERT INTO qacomment (admin_id, item_QA_id, content, created_date, last_modified_date, created_by, last_modified_by)
VALUES
(1, 1, 'QA_comments content1', '2023-03-07 12:00:00', '2023-03-07 12:00:00', '대표', '대표'),
(4, 2, 'QA_comments content2', '2023-03-07 12:00:00', '2023-03-07 12:00:00', '직원B', '직원B'),
(3, 3, 'QA_comments content3', '2023-03-07 12:00:00', '2023-03-07 12:00:00', '직원A', '직원A'),
(2, 4, 'QA_comments content4', '2023-04-07 12:00:00', '2023-04-07 12:00:00', '이사', '이사');