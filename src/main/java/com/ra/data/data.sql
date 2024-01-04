create database project_md4;
use project_md4;

create table category
(
    id          int primary key auto_increment,
    name        varchar(50)  not null unique,
    description varchar(255) not null,
    status      bit(1) default 1,
    parent_id   int
);


DELIMITER //
CREATE PROCEDURE add_cat(IN name_in varchar(50), description_in varchar(255), status_in bit(1))
BEGIN
    INSERT INTO category (name, description, status) VALUES (name_in, description_in, status_in);
end //

DELIMITER //
CREATE PROCEDURE update_cat(IN name_in varchar(50), description_in varchar(255), status_in bit(1), id_in int)
BEGIN
    UPDATE category
    SET name        = name_in,
        description = description_in,
        status      = status_in
    where id = id_in;
end //

DELIMITER //
CREATE PROCEDURE show_cat()
BEGIN
    SELECT * FROM category;
end //

DELIMITER //
CREATE PROCEDURE find_by_id_cat(IN id_in int)
BEGIN
    SELECT * FROM category where id = id_in;
end //

DELIMITER //
CREATE PROCEDURE find_by_name_cat(IN name_in varchar(50))
BEGIN
    SELECT * FROM category where LCASE(name) like CONCAT('%', LCASE(name_in), '%');
end //

DELIMITER //
CREATE PROCEDURE change_status_cat(IN id_in int)
BEGIN
    UPDATE category SET status = status ^ 1 where id = id_in;
end //

DELIMITER //
CREATE PROCEDURE CAT_FIND_PAGED(
    IN name_in VARCHAR(100),
    IN limit_in INT,
    IN current_page INT, OUT total_page int)
BEGIN
    DECLARE offset_val INT;

    SET offset_val = (current_page - 1) * limit_in;
    SELECT *
    FROM category
    WHERE LCASE(name) LIKE CONCAT('%', LCASE(name_in), '%')
       OR id = name_in
    LIMIT limit_in OFFSET offset_val;
    SET total_page = ceil((select count(*)
                           from category
                           WHERE LCASE(name) LIKE CONCAT('%', LCASE(name_in), '%')
                              OR id = name_in) / limit_in);
END //
DELIMITER ;




create table product
(
    id          int primary key auto_increment,
    name        varchar(50) not null unique,
    category_id int,
    image       varchar(255),
    foreign key (category_id) REFERENCES category (id),
    description text,
    price       double      not null check ( price > 0 ),
    stock       int         not null,
    status      bit(1) default 1
);

DELIMITER //
CREATE PROCEDURE add_product(IN name_in varchar(50), category_id_in int, image_in varchar(255), description_in text,
                             price_in double, stock_in int, status_in bit(1), OUT product_id_add int)
BEGIN
    INSERT INTO product (name, category_id, image, description, price, stock, status)
    VALUES (name_in, category_id_in, image_in, description_in, price_in, stock_in, status_in);
    SELECT LAST_INSERT_ID() INTO product_id_add;
end //

DELIMITER //
CREATE PROCEDURE delete_product(in id_in int)
BEGIN
    DELETE FROM product where id = id_in;
end //


DELIMITER //
CREATE PROCEDURE edit_product(IN name_in varchar(50), category_id_in int, image_in varchar(255), description_in text,
                              price_in double, stock_in int, status_in bit(1), id_in int)
BEGIN
    UPDATE product
    SET name       = name_in,
        category_id=category_id_in,
        image=image_in,
        description=description_in,
        price=price_in,
        stock=stock_in,
        status=status_in
    where id = id_in;
end //

DELIMITER //
CREATE PROCEDURE show_product()
BEGIN
    SELECT * FROM product;
end //

DELIMITER //
CREATE PROCEDURE find_by_id_product(IN id_in int)
BEGIN
    SELECT * FROM product where id = id_in;
end //

DELIMITER //
CREATE PROCEDURE find_by_name_product(IN name_in varchar(50))
BEGIN
    SELECT * FROM product where LCASE(name) like CONCAT('%', LCASE(name_in), '%');
end //

create table image
(
    id         int primary key auto_increment,
    name       varchar(255),
    product_id int,
    foreign key (product_id) REFERENCES product (id)
);

DELIMITER //
CREATE PROCEDURE add_image(IN name_in varchar(50), product_id_in int)
BEGIN
    INSERT INTO image (name, product_id) VALUES (name_in, product_id_in);
end //

DELIMITER //
CREATE PROCEDURE edit_image(IN name_in varchar(50), product_id_in int, id_in int)
BEGIN
    UPDATE image
    SET name       = name_in,
        product_id = product_id_in
    where id = id_in;
end //

DELIMITER //
CREATE PROCEDURE show_image()
BEGIN
    SELECT * FROM image;
end //

DELIMITER //
CREATE PROCEDURE find_by_id_image(IN id_in int)
BEGIN
    SELECT * FROM image where id = id_in;
end //

DELIMITER //
CREATE PROCEDURE delete_image(in id_in int)
BEGIN
    DELETE FROM image where product_id = id_in;
end //

DELIMITER //
CREATE PROCEDURE show_users()
BEGIN
    SELECT * FROM user;
end //

DELIMITER //
CREATE PROCEDURE update_user(IN name_in varchar(50), email_in varchar(100), password_in varchar(255),
                             avatar_in varchar(255), phoneNumber_in varchar(20), address_in text, status_in bit(1),
                             role_in bit(1), id_in int)
BEGIN
    UPDATE user
    SET name        = name_in,
        email       = email_in,
        password    = password_in,
        avatar      = avatar_in,
        phoneNumber = phoneNumber_in,
        address     = address_in,
        status      = status_in,
        role        = role_in
    where id = id_in;
end //

DELIMITER //
CREATE PROCEDURE find_by_id_user(IN id_in int)
BEGIN
    SELECT * FROM user where id = id_in;
end //

DELIMITER //
CREATE PROCEDURE find_by_name_user(IN name_in varchar(50))
BEGIN
    SELECT * FROM user where LCASE(name) like CONCAT('%', LCASE(name_in), '%');
end //

DELIMITER //
CREATE PROCEDURE change_status_user(IN id_in int)
BEGIN
    UPDATE user SET status = status ^ 1 where id = id_in;
end //

DELIMITER //
CREATE PROCEDURE change_role_user(IN id_in int)
BEGIN
    UPDATE user SET role = user.role ^ 1 where id = id_in;
end //

DELIMITER //
CREATE PROCEDURE check_email_user(IN email_in varchar(100))
BEGIN
    SELECT * FROM user WHERE email = email_in;
end //



create table cart
(
    id      int primary key auto_increment,
    user_id int,
    foreign key (user_id) references user (id)
);

delimiter //
create procedure show_cart()
begin
    select * from cart;
end //

delimiter //
create procedure find_cart_by_id(in cart_id int)
begin
    select * from cart where id = cart_id;
end //

delimiter //
create procedure delete_cart(in cart_id int)
begin
    delete from cart where id = cart_id;
end //

create
    definer = root@localhost procedure add_cart(IN user_id_cart int, OUT cart_id int)
begin
    insert into cart(user_id) values (user_id_cart);
    SET cart_id = LAST_INSERT_ID();
end;

drop procedure add_cart;

call add_cart(2, @cart_id);
select @cart_id;

delimiter //
create procedure update_cart(in user_id_cart int, cart_id_in int)
begin
    update cart set user_id=user_id_cart where id = cart_id_in;
end //

delimiter //
create procedure find_cart_by_user_id(in user_id_in int)
begin
    select * from cart where user_id = user_id_in;
end //

create table cart_item
(
    id       int auto_increment primary key,
    pro_id   int,
    foreign key (pro_id) references product (id),
    cart_id  int,
    foreign key (cart_id) references cart (id),
    quantity int
);

drop table cart_item;

delimiter //
create procedure delete_cart_item(in id_in int)
begin
    delete from cart_item where id = id_in;
end //

delimiter //
create procedure add_to_cart_item(in pro_id_in int, cart_id_in int, quantity_in int)
begin
    insert into cart_item(pro_id, cart_id, quantity) values (pro_id_in, cart_id_in, quantity_in);
end //

delimiter //
create procedure update_cart_item(in pro_id_in int, cart_id_in int, quantity_in int, id_in int)
begin
    update cart_item set pro_id=pro_id_in, cart_id=cart_id_in, quantity=quantity_in where id = id_in;
end //

delimiter //
create procedure show_cart_item()
begin
    select * from cart_item;
end //

delimiter //
create procedure find_cart_item_by_id(in id_in int)
begin
    select * from cart_item where id = id_in;
end //

delimiter //
create procedure find_cart_item_by_cart_id(in cart_id_in int)
begin
    select * from cart_item where cart_id = cart_id_in;
end //

delimiter //
create procedure check_pro_id_in_cart_item(in pro_id_in int)
begin
    select * from cart_item where pro_id = pro_id_in;
end //

delimiter //
create procedure delete_cart_item_by_cart_id(in cart_id_in int)
begin
    delete from cart_item where cart_id = cart_id_in;
end //

create table orders
(
    id       int primary key auto_increment,
    user_id  int,
    foreign key (user_id) references user (id),
    total    double,
    status   int  default 0,
    order_at date default (date(now())),
    address  text,
    note     text
);

delimiter //
create procedure show_orders()
begin
    select * from orders;
end //

delimiter //
create procedure add_orders(IN user_id_in int, total_in double, status_in int, address_in text, note_in text)
begin
    INSERT INTO orders (user_id, total, status, address, note)
    values (user_id_in, total_in, status_in, address_in, note_in);
end //

delimiter //
create procedure change_status_orders(in status_in int, id_in int)
begin
    update orders
    set status = status_in
    where id = id_in;
end //

delimiter //
create procedure find_order_by_order_id(in id_in int)
begin
    select * from orders where id = id_in;
end //

delimiter //
create procedure find_order_by_user_id(in user_id_in int)
begin
    select * from orders where user_id = user_id_in;
end //

create table order_detail
(
    order_id   int,
    foreign key (order_id) references orders (id),
    product_id int,
    foreign key (product_id) references product (id),
    quantity   int,
    price      double
);

delimiter //
create procedure show_order_detail()
begin
    select * from order_detail;
end //

delimiter //
create procedure add_order_detail(In order_id_in int, product_id_in int, quantity_in int, price_in double)
begin
    insert into order_detail(order_id, product_id, quantity, price)
    values (order_id_in, product_id_in, quantity_in, price_in);
end //

delimiter //
create procedure find_order_detail_by_order_id(IN order_id_in int)
begin
    select * from order_detail where order_id = order_id_in;
end //