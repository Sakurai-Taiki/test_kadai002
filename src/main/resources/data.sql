--categoriesテーブル
INSERT IGNORE INTO category(id, category_name)VALUE(1, '創作料理');
INSERT IGNORE INTO category(id, category_name)VALUE(2, 'アジアン居酒屋');

--storesテーブル
INSERT IGNORE INTO stores (id, category_id, store_name, photo_name, description, min_budget, max_budget, open_hour, close_hour, store_post_code, store_address, store_phone_number, close_day, seats) VALUES (1, 1, '居酒屋ぽんぬ', 'store01.jpg',  '居酒屋の説明文項目', 1000, 5000, '11:00', '22:00', '073-0145', '北海道砂川市西五条南X-XX-XX', '012-345-678', '不定休',30);