create table if not exists greenbuy.green_user (
    id          bigint not null auto_increment ,
    password    varchar(255) not null ,
    role        varchar(255) not null ,
    username    varchar(255) not null ,
    primary key (id) ,
    unique key (username)
) ENGINE = InnoDB ;

create table if not exists greenbuy.money (
    id          bigint not null auto_increment ,
    dollars     int null,
    user_id     bigint null,
    primary key (id) ,
    key FKq4c9hf9x4dacdo5rswryu2pej (user_id)
) ENGINE = InnoDB ;

create table if not exists greenbuy.bid (
    id                  bigint  not null    auto_increment ,
    offer               int     null,
    sellable_item_id    bigint  null,
    user_id             bigint  null,
    primary key (id) ,
    key FKp1rgqs72fnlgxx9jy7qmw1t4h (sellable_item_id),
    key FKra6wtdxfved0g0wg1dvkil9ud (user_id)
) ENGINE = InnoDB ;


create table if not exists greenbuy.sellable_item (
    id             bigint auto_increment,
    description    varchar(255) null,
    name           varchar(255) null,
    photo_url      varchar(255) null,
    purchase_price int          null,
    sold           bit(1)          not null,
    starting_price int          null,
    user_id        bigint       null,
    primary key(id),
    key FKskx6q5fim0n7lfgapvhq5ihgb (user_id)
)ENGINE = InnoDB ;



