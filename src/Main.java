create table customer (
        id int primary key,
        first_name varchar(100) not null,
        last_name varchar(100) not null,
        city varchar(100),
        country varchar(100),
        phone varchar(100)
        );
        create table supplier (
        id int primary key,
        company_name varchar(100) not null,
        contact_name varchar(100),
        contact_title varchar(100),
        city varchar(100),
        country varchar(100),
        phone varchar(100),
        fax varchar(100)
        );

        create table product (
        id int primary key,
        product_name varchar(100) not null,
        unit_price decimal(12,2) default 0,
        package varchar(100),
        is_discontinued boolean default false,
        supplier_id int references supplier(id) not null
        );

        create table orders (
        id int primary key,
        order_date timestamp default now(),
        order_number varchar(100),
        total_amount decimal(12,2) default 0,
        customer_id int references customer(id) not null
        );

        create table order_item (
        id int primary key,
        unit_price decimal(12,2) default 0,
        quantity int default 1,
        order_id int references orders(id) not null,
        product_id int references product(id) not null
        );


        select * from supplier;
        select * from product;
        select * from orders;
        select * from order_item;
        select * from customer;



        -- Вывести всех клиентов из страны Canada
        select * from customer
        where country='Canada';
        -- Вывести все страны клиентов
        select country from customer;
        -- Вывести количество всех заказов
        select count(quantity) as quantity_orders from order_item;
        -- Вывести максимальную стоимость заказа
        select max(unit_price) as max_price from order_item;
        -- Найти сумму всех заказов
        select sum(total_amount) as sum_total_amount from orders;
        -- Найти сумму всех заказов за 2014 год
        select sum(total_amount) as sum_total_amount from orders where order_date
        between to_timestamp('Jan 1 2014 ', 'MON DD YYYY') and to_timestamp('Dec 12 2014', 'MON DD YYYY');
        --Найти среднюю стоимость всех заказов
        select avg(total_amount) as average_price from orders;
        -- Найти среднюю стоимость всех заказов по каждому клиенту
        select distinct first_name, customer_id, avg(total_amount) as average_price from orders
        inner join customer c on c.id = orders.customer_id group by first_name, customer_id order by customer_id;
        --Найти всех клиентов, которые живут в Бразилии или Испании
        select first_name, country from customer where country in ('Brazil', 'Spain');
        -- Найти все заказы между 2013ым и 2014ым годами, которые стоили меньше 100.00$
        select * from orders where order_date between to_timestamp('Jan 1 2013 ', 'MON DD YYYY')
        and to_timestamp('Dec 12 2014', 'MON DD YYYY') and total_amount < 100.00;
        --Найти всех клиентов, которые в одной из стран: Испания, Италия, Германия, Франция. Отсортируйте по странам
        select first_name, country from customer where country in ('Spain', 'Italy', 'Germany', 'France') order by country;
        --Найти все страны клиентов, страны которых содержаться в таблице поставщиков
        select country from supplier where country in ('Canada', 'France', 'Italy', 'Australia', 'Finland', 'Netherlands', 'Denmark',
        'Singapore', 'USA', 'Sweden', 'Norway', 'Germany', 'Brazil','UK', 'Japan')
        GROUP BY country;
        -- Найти всех клиентов, имена которых начинаются на ‘Jo’
        select first_name from customer
        where first_name like 'Jo%';
        -- Найти всех клиентов, имена которых заканчиваются на ‘a’ и имеют длину ровно 4 символа
        select * from customer where first_name like('___a');
        --Найти количество клиентов по странам
        select count(*),country from customer
        group by country;
        -- Найти количество клиентов по странам. Вывести в порядке убывания
        select country, count(*) as count_of_customer from customer group by country order by count_of_customer;
        -- Найти общую сумму стоимости заказов и количество заказов по каждому клиенту (customer_id). Отсортировать по сумме
        select sum(total_amount) as total_amount, customer_id, count(*) as count_of_orders from orders group by customer_id order by sum(total_amount);
        -- Найти общую сумму стоимости заказов и количество заказов по каждому клиенту (customer_id), у которых общее количество заказов больше 20ти
        select sum(total_amount) as total_amount, customer_id, count(*) as count_of_orders from orders group by customer_id having count(*) > 20;
        -- Найти количество городов в каждой из стран клиентов
        select distinct city, count(*) as quantity_city from customer group by city order by quantity_city;