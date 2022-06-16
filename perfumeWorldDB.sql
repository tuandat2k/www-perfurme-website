create database perfumeWorldDB

use perfumeWorldDB

create table category
(
    id         int identity
        primary key,
    created_at datetime,
    deleted_at datetime,
    updated_at datetime,
    name       nvarchar(255)
)
go

create table role
(
    id         int identity
        primary key,
    created_at datetime,
    deleted_at datetime,
    updated_at datetime,
    name       varchar(255)
)
go

create table [user]
(
    id         int identity
        primary key,
    created_at datetime,
    deleted_at datetime,
    updated_at datetime,
    account    varchar(255),
    email      varchar(255),
    fullname   nvarchar(255),
    otpCode    varchar(255),
    password   varchar(255),
    phone      varchar(255),
    verify     bit,
    role_id    int
        constraint FK_qleu8ddawkdltal07p8e6hgva
            references role
)
go

create table product
(
    id          int identity
        primary key,
    created_at  datetime,
    deleted_at  datetime,
    updated_at  datetime,
    content     nvarchar(2000),
    count       int,
    discount    float,
    image       varchar(255),
    name        nvarchar(255),
    price       numeric(19),
    category_id int
        constraint FK_rlaghtegr0yx2c1q1s6nkqjlh
            references category
)
go

create table [order]
(
    id              int identity
        primary key,
    created_at      datetime,
    deleted_at      datetime,
    updated_at      datetime,
    content         nvarchar(255),
    delivey_address nvarchar(255),
    phone           varchar(255),
    receiver        nvarchar(255),
    status          bit,
    total_money     numeric(19),
    user_id         int
        constraint FK_mh40cn97o5svvy5c32ws9tnvp
            references [user]
)
go


create table product_order
(
    count      int,
    price      numeric(19),
    product_id int not null
        constraint FK_5uw5nxqovigv7mf1gmbos1rl8
            references product,
    odder_id   int not null
        constraint FK_jto6sam1lif6fkov7aiksuj5u
            references [order],
    primary key (product_id, odder_id)
)
go



INSERT INTO perfumeWorldDB.dbo.role ( created_at, deleted_at, updated_at, name) VALUES ( null, null, null, N'admin');
INSERT INTO perfumeWorldDB.dbo.role ( created_at, deleted_at, updated_at, name) VALUES ( null, null, null, N'user');

INSERT INTO perfumeWorldDB.dbo.[user] ( created_at, deleted_at, updated_at, account, email, fullname, otpCode, password, phone, verify, role_id) VALUES ( N'2021-05-29 21:18:25.087', null, N'2021-05-29 21:18:25.087', N'vuongnguen', N'vuongnguyen@gmail.com', N'Nguyen Van Vuong', N'870466', N'123456789', N'0987654321', 1, 2);
INSERT INTO perfumeWorldDB.dbo.[user] ( created_at, deleted_at, updated_at, account, email, fullname, otpCode, password, phone, verify, role_id) VALUES ( N'2021-05-30 17:21:35.387', null, N'2021-05-30 17:21:35.387', N'vuongng', N'vuongnguyen@gmail.com', N'Vuong Nguyen', N'413860', N'1234567890', N'0987165454', 1, 1);

INSERT INTO perfumeWorldDB.dbo.category ( created_at, deleted_at, updated_at, name) VALUES ( N'2021-05-22 20:27:00.723', null, N'2021-05-22 20:27:08.007', N'Nước hoa nam');
INSERT INTO perfumeWorldDB.dbo.category ( created_at, deleted_at, updated_at, name) VALUES ( N'2021-05-22 20:27:00.723', null, N'2021-05-22 20:27:08.007', N'Nước hoa nữ');
INSERT INTO perfumeWorldDB.dbo.category ( created_at, deleted_at, updated_at, name) VALUES ( N'2021-05-22 20:27:00.723', null, N'2021-05-22 20:27:08.007', N'Nước hoa UniSex');
INSERT INTO perfumeWorldDB.dbo.category ( created_at, deleted_at, updated_at, name) VALUES ( N'2021-05-22 20:27:00.723', null, N'2021-05-22 20:27:08.007', N'Nước hoa Niche');
INSERT INTO perfumeWorldDB.dbo.category ( created_at, deleted_at, updated_at, name) VALUES ( N'2021-05-22 20:27:00.723', null, N'2021-05-22 20:27:08.007', N'Nước hoa Mini');
INSERT INTO perfumeWorldDB.dbo.category ( created_at, deleted_at, updated_at, name) VALUES ( N'2021-05-22 20:27:00.723', null, N'2021-05-22 20:27:08.007', N'Nước hoa doanh nhân');




INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 10, 5, N'https://cdn.vuahanghieu.com/unsafe/0x500/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/02/nuoc-hoa-nam-chanel-bleu-edp-100ml-620600b02a082-11022022132240.jpg', N'Nước Hoa Nam Chanel Blue EDP,100ml', 399000, 1);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-30 16:54:47.537', N'', 40, 15, N'https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2019/10/nuoc-hoa-versace-eros-cho-nam-say-dam-phai-nu-minisize-5ml-5db936dcd37ed-30102019140812.jpg', N'Nước Hoa Nam Versace Eros Man EDT 5ml', 399000, 1);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-30 16:28:24.270', N'', 67, 5, N'https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2019/05/nuoc-hoa-armaf-club-de-nuit-intense-for-man-105ml-5cecf9ac49316-28052019160444.jpg', N'Nước Hoa Nam Armaf Club De Nuit, 105ml', 399000, 1);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-30 09:36:20.777', N'', 18, 5, N'https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/02/nuoc-hoa-nam-calvin-klein-ck-free-for-men-100ml-6209c4683fdb5-14022022095432.jpg', N'Nước Hoa Nam Calvin CK Free For Men 100ml', 389000, 1);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-30 16:57:30.570', N'', 92, 0.5, N'https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/02/nuoc-hoa-nu-gucci-bloom-gocce-di-fiori-edt-100ml-621847410c88e-25022022100433.jpg', N'Nước Hoa Nữ Gucci Bloom Gocce Di Fiori EDT 100ml', 599000, 2);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-30 16:57:30.847', N'', 116, 0.5, N'https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/Laurent%20Libre/Laurent%20Libre.jpg', N'Nước Hoa Nữ Yves Saint Laurent Libre EDP 90ml', 380000, 2);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-30 16:57:30.713', N'', 127, 0.5, N'https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/02/nuoc-hoa-nu-chopard-wish-edp-75ml-6209d0788b760-14022022104600.jpg', N'Nước Hoa Nữ Chopard Wish EDP 75ml', 227000, 2);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-30 16:57:30.387', N'', 137, 0.5, N'https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/02/nuoc-hoa-marc-jacobs-daisy-love-cho-nu-100ml-62061711dd892-11022022145809.jpg', N'Nước Hoa Marc Jacobs Daisy Love Cho Nữ, 100ml', 190000, 2);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 18, 0.5, N'https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/02/nuoc-hoa-unisex-lattafa-bade-e-al-oud-oud-for-glory-edp-100ml-621848112c909-25022022100801.jpg', N'Nước Hoa Unisex Lattafa Bade Al Oud Oud For Glory EDP 100ml', 399000, 3);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 120, 0.5, N'https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/02/nuoc-hoa-unisex-diptyque-eau-des-sens-edt-100ml-6218486eeb155-25022022100934.jpg', N'Nước Hoa Unisex Diptyque Eau Des Sens EDT 100ml', 399000, 3);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 105, 0.5, N'https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/02/nuoc-hoa-unisex-le-labo-santal-33-100ml-6206023c71b8e-11022022132916.jpg', N'Nước Hoa Unisex Le Labo Santal 33 100ml', 399000, 3);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 105, 0.5, N'https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/02/nuoc-hoa-unisex-tom-ford-ombre-leather-edp-100ml-621848e61a9e2-25022022101134.jpg', N'Nước Hoa Unisex Tom Ford Ombré Leather EDP 100ml', 399000, 3);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 100, 0.5, N'https://orchard.vn/wp-content/uploads/2020/04/club-de-nuit-intense-man-limited-edition-edp-orchard.vn-1.jpg', N'Nước Hoa Armaf Club De Nuit Intense Man Limited Edition EDP', 399000, 4);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 100, 0.5, N'https://orchard.vn/wp-content/uploads/2018/11/tom-ford-ombre-leather-orchard.vn_.jpg', N'Nước Hoa Tom Ford Ombre Leather', 399000, 4);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 98, 0.5, N'https://orchard.vn/wp-content/uploads/2021/06/kilian_good_girl_gone_bad_orchardvn_avatar.jpg', N'Nước Hoa Kilian Good Girl Gone Bad', 399000, 4);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 85, 0.5, N'https://orchard.vn/wp-content/uploads/2020/06/maison-francis-kurkdjian-baccarat-rouge-540-extrait-de-parfum-orchard.vn_.jpg', N'Nước Hoa Maison Francis Kurkdjian Baccarat Rouge 540 Extrait De Parfum', 69000, 4);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 100, 15, N'https://orchard.vn/wp-content/uploads/2014/06/kenzo_flower_eau_de_parfum_refillable_spray_100ml_1.png', N'Nước Hoa Mini Kenzo Flower EDP', 349000, 5);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 100, 5, N'https://orchard.vn/wp-content/uploads/2014/06/dior-jadore.jpg', N'Nước Hoa Mini Dior J’Adore', 349000, 5);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 100, 5, N'https://orchard.vn/wp-content/uploads/2014/06/Burberry-Sport-For-Men_1.jpg', N'Nước Hoa Mini Burberry Sport For Men', 349000, 5);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 100, 5, N'https://orchard.vn/wp-content/uploads/2014/06/Tommy-Girl.jpg', N'Nước Hoa Mini Tommy Girl', 349000, 5);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 150, 20, N'https://orchard.vn/wp-content/uploads/2014/06/Giorgio-Armani-Acqua-Di-Gi%C3%B2-Pour-Homme_1.jpg', N'Giorgio Armani Acqua Di Gio Pour Homme For Mensic Sáng Tạo', 419000, 6);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 130, 5, N'https://orchard.vn/wp-content/uploads/2014/06/Versace-Pour-Homme_1.jpg', N'Versace Pour Homme', 644000, 6);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 120, 40, N'https://orchard.vn/wp-content/uploads/2019/09/3348901486392_672fa1db2d985714658c181fc5f0ac72.jpg', N'Dior Sauvage Parfum', 1339000, 6);
INSERT INTO perfumeWorldDB.dbo.product ( created_at, deleted_at, updated_at, content, count, discount, image, name, price, category_id) VALUES ( N'2021-05-22 20:27:37.033', null, N'2021-05-22 20:27:37.033', N'', 120, 30, N'https://orchard.vn/wp-content/uploads/2016/03/11b7e5aeba005056ae6a4c_rtsc-00.jpg', N'Dior Sauvage EDT', 129000, 6);



INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-29 22:21:08.330', null, N'2021-05-29 22:21:08.330', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 596005);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-29 23:06:06.273', null, N'2021-05-29 23:06:06.273', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 369550);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-29 23:11:33.007', null, N'2021-05-29 23:11:33.007', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 379050);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-29 23:13:59.497', null, N'2021-05-29 23:13:59.497', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 596005);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-29 23:20:43.820', null, N'2021-05-29 23:20:43.820', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 339150);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-29 23:23:06.317', null, N'2021-05-29 23:23:06.317', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 339150);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-29 23:44:34.827', null, N'2021-05-29 23:44:34.827', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 339150);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-29 23:46:18.657', null, N'2021-05-29 23:46:18.657', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 339150);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-29 23:49:54.767', null, N'2021-05-29 23:49:54.767', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 1, 379050);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-30 09:20:07.970', null, N'2021-05-30 09:20:07.970', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 596005);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-30 09:36:20.553', null, N'2021-05-30 09:36:20.553', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 369550);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-30 16:27:02.657', null, N'2021-05-30 16:27:02.657', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 339150);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-30 16:28:24.067', null, N'2021-05-30 16:28:24.067', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 1057350);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-30 16:33:47.917', null, N'2021-05-30 16:33:47.917', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 678300);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-30 16:35:07.583', null, N'2021-05-30 16:35:07.583', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 1163155);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-30 16:51:25.277', null, N'2021-05-30 16:51:25.277', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 596005);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-30 16:54:47.417', null, N'2021-05-30 16:54:47.417', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 339150);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-30 16:56:38.430', null, N'2021-05-30 16:56:38.430', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 0, 596005);
INSERT INTO perfumeWorldDB.dbo.[order] ( created_at, deleted_at, updated_at, content, delivey_address, phone, receiver, status, total_money) VALUES ( N'2021-05-30 16:57:30.040', null, N'2021-05-30 16:57:30.040', N'hahaha', N'hahaah', N'0987654321', N'Vuong Nguyen', 1, 3382005);



