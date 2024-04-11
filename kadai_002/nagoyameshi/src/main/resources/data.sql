-- categoriesテーブル
INSERT IGNORE INTO categories (id, name) VALUES (1, '和食');
INSERT IGNORE INTO categories (id, name) VALUES (2, '洋食');
INSERT IGNORE INTO categories (id, name) VALUES (3, '中華');
INSERT IGNORE INTO categories (id, name) VALUES (4, '焼肉');
INSERT IGNORE INTO categories (id, name) VALUES (5, '海鮮・魚介');
INSERT IGNORE INTO categories (id, name) VALUES (6, 'パン');
INSERT IGNORE INTO categories (id, name) VALUES (7, 'ラーメン');
INSERT IGNORE INTO categories (id, name) VALUES (8, 'うどん');
INSERT IGNORE INTO categories (id, name) VALUES (9, 'カフェ');
INSERT IGNORE INTO categories (id, name) VALUES (10, '居酒屋');


-- storesテーブル
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (1, 1, '割烹旬菜', 'store01.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 7000, 8000, '333-3333', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '09:00', '17:00', '日曜日', 10, '老舗', 25);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (2, 6, 'NAGOYA BURGER 名駅店', 'store02.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 1500, 3500, '204-4168', '和歌山県三宅市山本町加藤2-10-5 ハイツ井上101号', '000-0000-0000', '06:30', '14:30', '月曜日、不定休', 77, 'ランチ', 24);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (3, 8, 'ゲード', 'store03.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 2500, 4500, '169-4458', '鳥取県松本市中島町渚5-2-4 ハイツ藤本103号', '000-0000-00000', '05:00', '13:00', '年中無休', 66, '人気', 23);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (4, 10, '大衆焼肉酒場 ホルモン屋 栄店', 'store04.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 8500, 10000, '368-7361', '神奈川県大垣市藤本町廣川4-1-3 コーポ原田103号', '000-0000-0000', '02:30', '10:30', '日曜日', 66, '宴会', 22);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (5, 5, '魚と野菜と酒 じゃばらむ', 'store05.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 3500, 5500, '523-6344', '三重県井上市山口町高橋6-10-5 ハイツ坂本110号', '000-0000-0000', '10:30', '18:30', '年中無休', 88, '個室', 21);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (6, 7, '名古屋ラーメン 栄本店', 'store06.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 6500, 8500, '569-4496', '佐賀県笹田市伊藤町鈴木4-7-6 コーポ三宅106号', '000-0000-0000', '11:30', '19:30', '月曜日', 36, 'おすすめ', 20);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (7, 7, '台湾ラーメン 田中 守山本店', 'store07.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 500, 1000, '123-4567', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '07:30', '17:30', '不定休', 43, '人気', 19);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (8, 7, 'つけ麺MENMARU', 'store08.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 2000, 4000, '505-7772', '大分県木村市山田町高橋7-7-5 コーポ佐藤103号', '000-0000-0000', '12:30', '20:30', '年中無休', 140, 'おしゃれ', 18);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (9, 1, '割烹 加藤', 'store09.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 1000, 4000, '123-4567', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '08:00', '17:30', '月、金曜日', 6, '老舗', 17);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (10, 4, 'ちょもちょもらんま', 'store10.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 6500, 8500, '123-4567', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '09:00', '18:00', '年中無休', 77, '個室', 16);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (11, 4, 'やきにく 山崎', 'store11.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 2000, 8500, '123-4567', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '6:30', '09:00', '年中無休', 12, '宴会', 15);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (12, 4, '炭火串焼コケッコ屋 半田店', 'store12.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 1500, 5000, '111-1111', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '07:30', '15:00', '火曜日', 7, '焼き鳥', 14);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (13, 5, '海鮮料理村上', 'store13.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 3000, 8000, '396-3825', '秋田県高橋市宇野町佐々木3-4-4 ハイツ渚102号', '000-0000-0000', '10:30', '19:30', '火、金、土曜日', 147, 'ディナー', 13);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (14, 2, 'カルビアータ', 'store14.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 3000, 8000, '123-4567', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '17:00', '22:00', '月曜日', 30, 'バイキング', 12);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (15, 2, 'マカロニ名駅店', 'store15.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 1000, 4000, '123-4567', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '05:00', '13:00', '水曜日', 55, 'ランチ', 11);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (16, 6, 'フランセサノ', 'store16.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 500, 2000, '123-4567', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '08:00', '15:00', '木曜日', 15, 'おしゃれ', 10);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (17, 2, 'ルマージュ', 'store17.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 1200, 5000, '123-4567', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000',  '11:00', '16:00','木曜日', 40, '人気', 9);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (18, 3, '名古屋飯店', 'store18.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 800, 3000, '123-4567', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '09:00', '13:00', '金曜日', 30, 'おすすめ', 8);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (19, 3, '町中華名古屋店', 'store19.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 1000, 5000, '123-4567', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '12:00', '18:00', '不定休', 15, '老舗', 7);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (20, 3, 'レッド餃子', 'store20.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 400, 2000, '123-4567', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '11:00', '18:00', '年中無休', 40, '喫茶店', 6);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (21, 9, 'カフェ　黄色い風船', 'store21.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 1500, 3000, '123-4567', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '08:00', '13:00', '月、火曜日', 35, 'おしゃれ', 5);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (22, 9, 'cafe de dole', 'store22.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 1800, 3800, '123-4567', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '07:00', '14:00', '木曜日、不定休', 5, '喫茶店', 4);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (23, 9, 'cafe HUJI', 'store23.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 700, 2500, '123-4567', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '11:00', '16:00', '年中無休', 40, '老舗', 3);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (24, 8, 'うどん中田', 'store24.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 600, 2500, '123-4567', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '06:00', '14:00', '年中無休', 10, 'ディナー', 2);
INSERT IGNORE INTO stores (id, category_id, name, image_name, description, min_price, max_price, postal_code, address, phone_number, open_time, close_time, holiday, capacity, search_keyword, priority) VALUES (25, 10, '地鶏居酒屋 小五郎', 'store25.jpg', '名古屋老舗のお店。老舗の味をご堪能ください。', 1200, 10000, '123-4567', '愛知県名古屋市中区栄X-XX-XX', '000-0000-0000', '17:00', '23:00', '不定休', 80, '宴会', 1);


-- rolesテーブル
INSERT IGNORE INTO roles (id, name) VALUES (1, 'ROLE_PAIDGENERAL');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'ROLE_FREEGENERAL');
INSERT IGNORE INTO roles (id, name) VALUES (3, 'ROLE_ADMIN');


-- usersテーブル
INSERT IGNORE INTO users (id, role_id, name, email, password, birthday, job, enabled) VALUES (1, 1, '侍 太郎', 'taro.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', '1990-01-01', '会社員', true);
INSERT IGNORE INTO users (id, role_id, name, email, password, birthday, job, enabled) VALUES (2, 3, '侍 花子', 'hanako.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', '1899-02-02', '会社員', true);
INSERT IGNORE INTO users (id, role_id, name, email, password, birthday, job, enabled) VALUES (3, 1, '侍 義勝', 'yoshikatsu.samurai@example.com', 'password','1995-03-03', 'アルバイト・パート', false);
INSERT IGNORE INTO users (id, role_id, name, email, password, birthday, job, enabled) VALUES (4, 2, '侍 幸美', 'sachimi.samurai@example.com', 'password', '1997-04-04', '無職', true);
INSERT IGNORE INTO users (id, role_id, name, email, password, birthday, job, enabled) VALUES (5, 2, '侍 雅', 'miyabi.samurai@example.com', 'password','2000-05-05', 'アルバイト・パート', false);
INSERT IGNORE INTO users (id, role_id, name, email, password, birthday, job, enabled) VALUES (6, 1, '侍 正保', 'masayasu.samurai@example.com', 'password', '1992-06-06', '会社員', false);
INSERT IGNORE INTO users (id, role_id, name, email, password, birthday, job, enabled) VALUES (7, 1, '侍 真由美', 'mayumi.samurai@example.com', 'password', '1888-07-07', '自営業', false);
INSERT IGNORE INTO users (id, role_id, name, email, password, birthday, job, enabled) VALUES (8, 2, '侍 安民', 'yasutami.samurai@example.com', 'password', '2001-08-08', '学生', false);
INSERT IGNORE INTO users (id, role_id, name, email, password, birthday, job, enabled) VALUES (9, 1, '侍 章緒', 'akio.samurai@example.com', 'password', '1996-09-09', '会社員', false);
INSERT IGNORE INTO users (id, role_id, name, email, password, birthday, job, enabled) VALUES (10, 2, '侍 祐子', 'yuko.samurai@example.com', 'password', '1999-10-10', 'アルバイト・パート', false);
INSERT IGNORE INTO users (id, role_id, name, email, password, birthday, job, enabled) VALUES (11, 2, '侍 秋美', 'akimi.samurai@example.com', 'password', '1991-11-11', '公務員', false);
INSERT IGNORE INTO users (id, role_id, name, email, password, birthday, job, enabled) VALUES (12, 1, '侍 信平', 'shinpei.samurai@example.com', 'password', '1899-12-12', '自営業', false);


-- reservationsテーブル
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, reservation_time, number_of_people) VALUES (1, 1, 1, '2024-04-01', '09:00', 2);
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, reservation_time, number_of_people) VALUES (2, 1, 2, '2024-04-01', '10:00', 3);
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, reservation_time, number_of_people) VALUES (3, 1, 3, '2024-04-01', '11:00', 4);
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, reservation_time, number_of_people) VALUES (4, 1, 4, '2024-04-01', '12:00', 5);
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, reservation_time, number_of_people) VALUES (5, 1, 5, '2024-04-01', '13:00', 6);
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, reservation_time, number_of_people) VALUES (6, 1, 6, '2024-04-01', '14:00', 2);
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, reservation_time, number_of_people) VALUES (7, 1, 7, '2024-04-01', '15:00', 3);
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, reservation_time, number_of_people) VALUES (8, 1, 8, '2024-04-01', '16:00', 4);
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, reservation_time, number_of_people) VALUES (9, 1, 9, '2024-04-01', '10:00', 6);
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, reservation_time, number_of_people) VALUES (10, 1, 10, '2024-04-01', '12:00', 5);
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, reservation_time, number_of_people) VALUES (11, 1, 11, '2024-04-01', '14:00', 2);


-- favoritesテーブル
INSERT IGNORE INTO favorites (id, user_id, store_id) VALUES (1, 1, 1);
INSERT IGNORE INTO favorites (id, user_id, store_id) VALUES (2, 1, 2);
INSERT IGNORE INTO favorites (id, user_id, store_id) VALUES (3, 1, 3);
INSERT IGNORE INTO favorites (id, user_id, store_id) VALUES (4, 1, 4);
INSERT IGNORE INTO favorites (id, user_id, store_id) VALUES (5, 1, 5);
INSERT IGNORE INTO favorites (id, user_id, store_id) VALUES (6, 1, 6);
INSERT IGNORE INTO favorites (id, user_id, store_id) VALUES (7, 1, 7);
INSERT IGNORE INTO favorites (id, user_id, store_id) VALUES (8, 1, 8);
INSERT IGNORE INTO favorites (id, user_id, store_id) VALUES (9, 1, 9);
INSERT IGNORE INTO favorites (id, user_id, store_id) VALUES (10, 1, 10);


-- reviewsテーブル
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (1, 1, 1, 1, 'イマイチでした。', '美味しくなかったです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (2, 1, 2, 2, '接客がよくなかった。', '普通に美味しかったですが、接客がよくなかったです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (3, 1, 3, 3, '普通に美味しかったです。', '普通に美味しかったです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (4, 3, 1, 2, '接客がよくなかった。', '普通に美味しかったですが、接客がよくなかったです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (5, 3, 2, 3, '普通に美味しかったです。', '普通に美味しかったです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (6, 3, 3, 4, 'すごく美味しかったです。', 'すごく美味しかったです。また来たいです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (7, 6, 1, 3, '普通に美味しかったです。', '普通に美味しかったです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (8, 6, 2, 4, 'すごく美味しかったです。', 'すごく美味しかったです。また来たいです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (9, 6, 3, 5, 'すごく美味しかったし、接客もよかったです。', 'すごく美味しかったし、接客もよかったです。また来たいです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (10, 7, 1, 1, 'イマイチでした。', '美味しくなかったです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (11, 7, 2, 2, '接客がよくなかった。', '普通に美味しかったですが、接客がよくなかったです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (12, 7, 3, 3, '普通に美味しかったです。', '普通に美味しかったです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (13, 9, 1, 2, '接客がよくなかった。', '普通に美味しかったですが、接客がよくなかったです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (14, 9, 2, 3, '普通に美味しかったです。', '普通に美味しかったです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (15, 9, 3, 4, 'すごく美味しかったです。', 'すごく美味しかったです。また来たいです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (16, 12, 1, 3, '普通に美味しかったです。', '普通に美味しかったです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (17, 12, 2, 4, 'すごく美味しかったです。', 'すごく美味しかったです。また来たいです。');
INSERT IGNORE INTO reviews (id, user_id, store_id, number_of_star, comment_title, comment_content) VALUES (18, 12, 3, 5, 'すごく美味しかったし、接客もよかったです。', 'すごく美味しかったし、接客もよかったです。また来たいです。');


-- profileテーブル
INSERT IGNORE INTO profile (id, name, president, birthday, postal_code, address, business) VALUES (1, '株式会社NAGOYAMESHI', '侍 太郎', '2000-01-01', '123-4567', '東京都侍区侍町1-2-3', '飲食店の検索・予約サービス');


-- termsテーブル
INSERT IGNORE INTO terms (id, content) VALUES (1, 'この利用規約（以下、「本規約」といいます。）は、NAGOYAMESHI株式会社（以下、「当社」といいます。）がこのウェブサイト上で提供するサービス（以下、「本サービス」といいます。）の利用条件を定めるものです。登録ユーザーの皆さま（以下、「ユーザー」といいます。）には、本規約に従って、本サービスをご利用いただきます。');


-- templatesテーブル
INSERT IGNORE INTO templates (id, name, subject, template) VALUES (1, 'reservation', '予約完了', '予約完了しました。ご利用ありがとうございます。');