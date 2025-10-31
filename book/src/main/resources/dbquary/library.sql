-- 0. 외래 키 제약조건을 잠시 비활성화 (테이블 초기화를 위함)
SET FOREIGN_KEY_CHECKS = 0;

-- 1. 기존 데이터 초기화 (ID를 1부터 다시 시작)
TRUNCATE TABLE rental;
TRUNCATE TABLE member;
TRUNCATE TABLE book;

-- 2. Member (회원) 데이터 삽입
INSERT INTO member (name, phone, address) VALUES
('홍길동', '010-1111-1111', '서울시 강남구'),  -- ID = 1 (Q2. '홍길동' 테스트용)
('이순신', '010-2222-2222', '서울시 중구'),    -- ID = 2
('유관순', '010-3333-3333', '경기도 수원시');   -- ID = 3

-- 3. Book (도서) 데이터 삽입
INSERT INTO book (title, author, publisher, price, pub_year) VALUES
('JPA 프로그래밍', '김영한', '에이콘', 30000, '2021'),  -- ID = 1 (Q1: 2020년 이상, Q5: 최고가)
('스프링 부트 입문', '홍개발', 'IT출판', 22000, '2023'),  -- ID = 2 (Q1: 2020년 이상)
('SQL 기초', '이데이터', '데이터북', 18000, '2019'),    -- ID = 3 (Q1: 2020년 미만)
('알고리즘 A', '박코딩', '코딩출판', 20000, '2020'),    -- ID = 4 (Q1: 2020년)
('클린 코드', '로버트 마틴', '인사이트', 25000, '2018'); -- ID = 5 (Q1: 2020년 미만, 0회 대출)

-- 4. Rental (대출) 데이터 삽입
-- (member_id, book_id, rent_date, return_date)
INSERT INTO rental (member_id, book_id, rent_date, return_date) VALUES
(1, 1, '2025-10-01', '2025-10-10'), -- 홍길동(1)이 JPA(1) 대출/반납 (Q2)
(1, 3, '2025-10-05', '2025-10-15'), -- 홍길동(1)이 SQL기초(3) 대출/반납 (Q2)
(2, 2, '2025-10-20', NULL),           -- 이순신(2)이 스프링부트(2) 대출 (Q3: 미반납)
(3, 1, '2025-10-22', '2025-10-30'), -- 유관순(3)이 JPA(1) 대출/반납 (Q4: JPA 2회 대출)
(2, 4, '2025-10-25', NULL);           -- 이순신(2)이 알고리즘(4) 대출 (Q3: 미반납)

-- 5. 외래 키 제약조건 다시 활성화
SET FOREIGN_KEY_CHECKS = 1;