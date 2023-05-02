INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('user', 'user@gmail.com', '{noop}user'),
       ('admin', 'admin@gmail.com', '{noop}admin'),
       ('user2', 'user2@gmail.com', '{noop}user2'),
       ('user3', 'user3@gmail.com', '{noop}user3'),
       ('user4', 'user4@gmail.com', '{noop}user4'),
       ('user5', 'user5@gmail.com', '{noop}user5'),
       ('guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4),
       ('USER', 5),
       ('USER', 6);

INSERT INTO RESTAURANT (NAME, DESCRIPTION)
VALUES ('Astoria', 'Russian kitchen.'),
       ('Prival', 'Asia kitchen.'),
       ('Ohotnik', 'European kitchen.');

INSERT INTO VOTE (VOTE_DATE, RESTAURANT_ID, USER_ID)
VALUES (CURRENT_DATE, 1, 1),
       (CURRENT_DATE, 2, 2),
       (CURRENT_DATE, 1, 4),
       ('2023-01-22', 3, 3),
       ('2023-01-22', 1, 6);

INSERT INTO DISH (NAME, DISH_DATE, PRICE, RESTAURANT_ID)
VALUES ('Борщ Русский', CURRENT_DATE, 100, 1),
       ('Суп Харчо', CURRENT_DATE, 120, 1),
       ('Рис отварной', CURRENT_DATE, 50, 1),
       ('Спагетти', CURRENT_DATE, 130, 1),
       ('Гуляш говяжий', CURRENT_DATE, 200, 1),
       ('Хлеб', CURRENT_DATE, 15, 1),
       ('Чай с лимоном', CURRENT_DATE, 60, 1),
       ('Суп гороховый', CURRENT_DATE, 100, 2),
       ('Рис отварной', CURRENT_DATE, 50, 2),
       ('Спагетти', CURRENT_DATE, 130, 2),
       ('Гуляш Свиной', CURRENT_DATE, 180, 2),
       ('Хлеб', CURRENT_DATE, 15, 2),
       ('Капучино', CURRENT_DATE, 100, 2),
       ('Суп "Весенний"', CURRENT_DATE, 140, 3),
       ('Гречка', CURRENT_DATE, 50, 3),
       ('Салат "Цезарь"', CURRENT_DATE, 130, 3),
       ('Гуляш Свиной', CURRENT_DATE, 180, 3),
       ('Хлеб', CURRENT_DATE, 15, 3),
       ('Компот', CURRENT_DATE, 60, 3),
       ('Макароны "по флотски"', '2023-01-22', 130, 1),
       ('Борщ', '2023-01-22', 100, 2),
       ('Картофель "по деревенски"', '2023-01-22', 50, 3);