delete from goods_size;
delete from goods_model;
delete from size_model;
delete from brand_model;
delete from picture_model;
delete from shop_model;

insert into size_model(id, size) values
(1, 'US 9'),
(3, 'US 10'),
(4, 'UK 8'),
(11, 'US 8');

insert into brand_model(id, name) values
(1, 'Ashan'),
(3, 'Adidas');

insert into picture_model(id, url) values
(1, 'images.asos-media.com/products/columbia-vent-aero-sneakers-in-off-white-exclusive-at-asos/23019994-1-darkstoneblack');

insert into shop_model(id, title, url) values
(1, 'Asos', 'https://www.asos.com/us/men/');

insert into goods_model(id, gender, money, name, uri, brand_id,  picture_id, shop_id) values
(1, 'Men', '$67.00', 'crosovok', 'https://www.crosovok.ru/crosovok', 1, 1, 1),
(2, 'Woman', '$17.00', 'tapok', 'https://www.tapok.ru/tapok', 3, 1, 1);