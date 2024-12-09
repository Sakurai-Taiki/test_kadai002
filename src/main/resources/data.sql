--categoriesテーブル
INSERT IGNORE INTO category(id, category_name)VALUE(1, '創作料理');
INSERT IGNORE INTO category(id, category_name)VALUE(2, 'アジアン居酒屋');
INSERT IGNORE INTO category(id, category_name)VALUE(3, '日本酒居酒屋');

--storesテーブル
INSERT IGNORE INTO stores (id, category_id, store_name, photo_name, description, min_budget, max_budget, open_hour, close_hour, store_post_code, store_address, store_phone_number, close_day, seats) VALUES (1, 1, '居酒屋ぽんぬ', 'store01.jpg',  '居酒屋の説明文項目', 1000, 5000, '11:00', '22:00', '073-0145', '北海道砂川市西五条南X-XX-XX', '012-345-678', '不定休',30);
INSERT IGNORE INTO stores (id, category_id, store_name, photo_name, description, min_budget, max_budget, open_hour, close_hour, store_post_code, store_address, store_phone_number, close_day, seats) VALUES (2, 2, '楽々屋', 'store01.jpg',  '居酒屋の説明文項目', 2000, 4000, '11:00', '22:00', '073-0145', '北海道砂川市西五条南X-XX-XX', '012-345-678', '不定休',30);


--rolesテーブル
INSERT IGNORE INTO roles (id, name) VALUES (1, 'ROLE_GENERAL');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');


--usersテーブル
INSERT IGNORE INTO users (id, user_name, mail_address, user_password, user_post_code, user_address, user_phone_number, roles_id, enabled) VALUES (1, '櫻井　ぽんぬ', 'taro.samurai@example.com', '$2a$10$ExampleHashedPassword...', '123-4567', '長野県三条市本原11-6', '090-1234-5678', 1, 1);
INSERT IGNORE INTO users (id, user_name, mail_address, user_password, user_post_code, user_address, user_phone_number, roles_id, enabled) VALUES (2, '櫻井　らら', 'taro.samurai@example.com', '$2a$10$ExampleHashedPassword...', '123-4567', '長野県三条市本原11-6', '090-1234-5678', 1, 1);
INSERT IGNORE INTO users (id, user_name, mail_address, user_password, user_post_code, user_address, user_phone_number, roles_id, enabled) VALUES (3, '櫻井　モカ', 'moka.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', '123-4567', '長野県三条市本原11-6', '090-1234-5678', 1, 1);
INSERT IGNORE INTO users (id, user_name, mail_address, user_password, user_post_code, user_address, user_phone_number, roles_id, enabled) VALUES (4, '櫻井　キキ', 'kiki.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', '123-4567', '長野県三条市本原11-6', '090-1234-5678', 2, 1);