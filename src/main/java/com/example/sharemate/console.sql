create table users
(
    id             bigserial primary key,
    login          varchar(200) not null unique,
    password       varchar(200) not null unique,
    items_store_id bigint references items_store (id)
);
create table items_store
(
    id      bigserial primary key,
    user_id bigint not null references users (id),
    item_id bigint not null references items (id)
);
create table items
(
    id          bigserial primary key,
    name        varchar(200) not null,
    description varchar(600),
    available   boolean      not null
);
create table item_request
(
    id      bigserial primary key,
    user_id bigint       not null references users (id),
    name    varchar(200) not null,
    find_it boolean      not null default false
);
create table feed_back
(
    id       bigserial primary key,
    item_id  bigint not null references items (id),
    rate     int    not null check (rate between 1 and 5),
    feedback varchar(600),
    user_id  bigint not null references users (id)
);
create table booking
(
    id             bigserial primary key,
    user_id        bigint  not null references users (id),
    item_id        bigint  not null references items (id),
    item_available boolean not null references items (available),
    rent_start     date    not null,
    rent_end       date    not null
)