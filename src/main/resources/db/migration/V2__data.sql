

INSERT INTO color (value) VALUES
('white'),
('blue'),
('dark-blue'),
('graphit'),
('mint');

INSERT INTO size (value) VALUES
('42'),
('44'),
('46'),
('48'),
('50'),
('52'),
('54'),
('56');

INSERT INTO item (category, price, quantity_in_stock, description, name, photo_url)
VALUES
('Блуза', 3690, 1500, 'Легкая, удлиненная, свободная блуза с рукавом до локтя и потайной молнией на спинке.', 'М1 Блуза свободного кроя',
 'https://sun9-2.userapi.com/LtU5bJZHp7Aab_GmD5ws9VPi1YIsxucycmTXSw/okbHt0T0loU.jpg'
);

INSERT INTO item (category, price, quantity_in_stock, description, name, photo_url)
VALUES (
'Блуза',
 3450,
 1500,
 'Прямые линии, разные углы запаха, пояс для двойного оборота вокруг талии - все это сливается в одной моделе, делая ее стильной и комфортной.',
 'М2 Кимоно',
 'https://sun9-77.userapi.com/8F0oxfWqCLq5M3N2w38vrUi_OyAvSdkdRCFMsg/7TmNwFK9MrA.jpg'
);

INSERT INTO item_size(item_id, size_id)
VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8);

INSERT INTO item_color(item_id, color_id)
VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5);








