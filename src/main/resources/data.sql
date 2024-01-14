-- Grade 등급 더미데이터

INSERT INTO grade (grade, reward_rate) VALUES
('WHITE', 1),
('SILVER', 2),
('GOLD', 3),
('VIP', 4),
('VVIP', 5);


-- Member 회원 더미데이터

INSERT INTO member (grade_id, id, password, email, name, nickname, tel, birth_date, gender, point, created_date, modified_date, recently_login, is_member_status, is_login_status)
VALUES
(1, 'member1', 'password1', 'member1@gmail.com', 'A', 'aaa', '010-1111-1111', '1990-01-09', 'M', 100, '2020-10-01 12:00:00', '2023-11-01 12:00:00', '2023-11-01 12:00:00', TRUE, FALSE),
(2, 'member2', 'password2', 'member2@gmail.com', 'B', 'bbb', '010-2222-2222', '1993-05-06', 'M', 1000, '2023-01-01 12:00:00', '2023-11-01 12:00:00', '2023-11-01 12:00:00', TRUE, FALSE),
(2, 'member3', 'password3', 'member3@gmail.com', 'C', 'ccc', '010-3333-3333', '1995-11-01', 'F', 500, '2019-04-01 12:00:00', '2023-11-01 12:00:00', '2023-11-01 12:00:00', TRUE, FALSE),
(4, 'member4', 'password4', 'member4@gmail.com', 'D', 'ddd', '010-4444-4444', '2000-01-01', 'F', 12500, '2020-07-01 12:00:00', '2023-11-01 12:00:00', '2023-11-01 12:00:00', TRUE, FALSE),
(3, 'member5', 'password5', 'member5@gmail.com', 'E', 'eee', '010-5555-5555', '1998-04-28', 'M', 10000, '2021-06-01 12:00:00', '2023-11-01 12:00:00', '2023-11-01 12:00:00', TRUE, FALSE);




