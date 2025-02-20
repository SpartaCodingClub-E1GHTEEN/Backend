/*유저 생성 (master - master)*/
INSERT INTO p_users(id, username, user_password, user_nickname, user_phone, user_address, email, role,
                    sign_up_type, is_deleted, created_by, created_at, modified_by, modified_at)
VALUES ('628ca582-d4f4-4e98-8f4b-0334006d1422', 'master',
        '{bcrypt}$2a$12$ARe9SGhs0KYLORumIhzXwOwb2yjpngN9gLG6qVM1YJRNCBKjUjNku', '마스터', '010-3333-3333', '서울시 광진구',
        'test4@test.io', 'MASTER', 'SERVICE', FALSE, '50ce7d0a-4ae2-4c47-9842-56bdc29c060d', NOW(),
        '50ce7d0a-4ae2-4c47-9842-56bdc29c060d', NOW());


/* 식당 생성 */
INSERT INTO p_stores (is_deleted, store_delivery_price, store_rating, created_at, deleted_at, modified_at, id, user_id,
                      created_by, deleted_by, modified_by, store_category, store_desc, store_img_url, store_name,
                      store_region)
VALUES (false, 1000, 0.0, now(), NULL, NULL, '15e584b0-8bbf-4fa6-aee2-cd14e71ec23e',
        '3a503cd9-83b2-4374-9405-e2dee8c56b1c', '3a503cd9-83b2-4374-9405-e2dee8c56b1c', NULL, NULL, 'DESSERT',
        '요거트 아이스크림의 정석', '--', '요아정', '서울 광화문');
INSERT INTO p_stores (is_deleted, store_delivery_price, store_rating, created_at, deleted_at, modified_at, id,
                      user_id,
                      created_by, deleted_by, modified_by, store_category, store_desc, store_img_url, store_name,
                      store_region)
VALUES (false, 500, 0.0, now(), NULL, NULL, 'e40c180c-aa9c-4f0d-872f-6bbe1106009c',
        '3a503cd9-83b2-4374-9405-e2dee8c56b1c', '3a503cd9-83b2-4374-9405-e2dee8c56b1c', NULL, NULL, 'SNACK_FOOD',
        '맛있게 매콤한 동대문 엽기 떡볶이', '--', '엽떡', '서울 광화문');
INSERT INTO p_stores (is_deleted, store_delivery_price, store_rating, created_at, deleted_at, modified_at, id, user_id,
                      created_by, deleted_by, modified_by, store_category, store_desc, store_img_url, store_name,
                      store_region)
VALUES (false, 1500, 0.0, now(), NULL, NULL, 'fe0df7e9-62f4-4d4f-91f1-97ea6075cb50',
        '3a503cd9-83b2-4374-9405-e2dee8c56b1c', '3a503cd9-83b2-4374-9405-e2dee8c56b1c', NULL, NULL, 'FAST_FOOD',
        '햄버거 드시러 오세요', '--', '맥도날드', '서울 광화문');


/* 음식 생성 */
INSERT INTO p_foods
(food_order_count, food_price, food_status, is_deleted, is_recommended, created_at, deleted_at,
 modified_at, id, store_id, created_by, deleted_by, food_desc, food_image_url, food_name, modified_by)
VALUES (0, 4500, 'AVAILABLE', false, false, now(), null, null, '330ba338-0b1d-48cd-93f8-5bd53046ba90',
        '15e584b0-8bbf-4fa6-aee2-cd14e71ec23e', '3a503cd9-83b2-4374-9405-e2dee8c56b1c', '', '음식 설명',
        'https://example.com/1', '아메리카노', '');
INSERT INTO p_foods
(food_order_count, food_price, food_status, is_deleted, is_recommended, created_at, deleted_at,
 modified_at, id, store_id, created_by, deleted_by, food_desc, food_image_url, food_name, modified_by)
VALUES (0, 5500, 'AVAILABLE', false, true, now(), null, null, '2d8dda4a-5643-49cd-b146-fcb8ba482d2b',
        '15e584b0-8bbf-4fa6-aee2-cd14e71ec23e', '3a503cd9-83b2-4374-9405-e2dee8c56b1c', '', '음식 설명',
        'https://example.com/2', '카페라떼', '');

/* 음식 옵션 생성 */
INSERT INTO p_food_options
(is_deleted, option_price, created_at, deleted_at, modified_at, food_id, id, created_by, deleted_by, modified_by,
 option_name)
VALUES (false, 100, now(), null, now(), '330ba338-0b1d-48cd-93f8-5bd53046ba90', '5041f782-dd83-4574-b6bd-01c56959c174',
        '3a503cd9-83b2-4374-9405-e2dee8c56b1c', '', '', '아이스로 변경');

/*주문 생성*/
INSERT INTO p_food_options
(is_deleted, option_price, created_at, deleted_at, modified_at, food_id, id, created_by, deleted_by, modified_by,
 option_name)
VALUES (false, 500, now(), null, null, '330ba338-0b1d-48cd-93f8-5bd53046ba90', '60a79ba2-1f93-44f8-9933-ca81de421d89',
        '3a503cd9-83b2-4374-9405-e2dee8c56b1c', '', '', '시럽 추가');

INSERT INTO p_orders (is_deleted, is_store_order, total_price, created_at, deleted_at, modified_at, order_time,
                      id, store_id, user_id, created_by, deleted_by, modified_by, note_to_delivery,
                      note_to_store, status)
VALUES (false, false, 0, now(), NULL, now(), now(),
        '4314ce28-3ea1-4fe5-b0b3-8e6394b18c2e', '15e584b0-8bbf-4fa6-aee2-cd14e71ec23e',
        'fccf3448-03c7-47a4-a108-ce6c39815a37', 'fccf3448-03c7-47a4-a108-ce6c39815a37', NULL,
        'fccf3448-03c7-47a4-a108-ce6c39815a37', '빨리 와주세요', '맛있게 해주세요', 'PENDING'),
       (false, false, 0, now(), NULL, now(), now(),
        '3cc47522-6d78-425c-bf4e-0b58d43fdec5', '15e584b0-8bbf-4fa6-aee2-cd14e71ec23e',
        'fccf3448-03c7-47a4-a108-ce6c39815a37', 'fccf3448-03c7-47a4-a108-ce6c39815a37', NULL,
        'fccf3448-03c7-47a4-a108-ce6c39815a37', '문고리에 걸어주세요', '따뜻하게 주세요', 'PENDING');

INSERT INTO p_order_details (amount, food_price, food_id, id, order_id, food_name)
VALUES (1, 4500, '330ba338-0b1d-48cd-93f8-5bd53046ba90', '78fd7147-b0af-45f3-8372-8986aeda52e2',
        '4314ce28-3ea1-4fe5-b0b3-8e6394b18c2e', '아메리카노'),
       (1, 4500, '330ba338-0b1d-48cd-93f8-5bd53046ba90', '64790350-de55-45db-8942-0adfb374b908',
        '3cc47522-6d78-425c-bf4e-0b58d43fdec5', '아메리카노');

INSERT INTO p_order_details_options (option_price, food_option_id, id, order_detail_id, option_name)
VALUES (100, '5041f782-dd83-4574-b6bd-01c56959c174', 'e9dba50f-d537-4600-ad6c-63d9cfe70699',
        '78fd7147-b0af-45f3-8372-8986aeda52e2', '아이스로 변경'),
       (100, '5041f782-dd83-4574-b6bd-01c56959c174', '53d0e0b8-4e06-4974-a2db-773afd6209bc',
        '64790350-de55-45db-8942-0adfb374b908', '아이스로 변경'),
       (500, '60a79ba2-1f93-44f8-9933-ca81de421d89', '1d14bb1d-7bd0-47d3-a468-58fd1b5fbf1b',
        '64790350-de55-45db-8942-0adfb374b908', '시럽 추가');
