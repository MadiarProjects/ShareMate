create table if not exists users
(
    id    bigserial primary key,
    name  varchar(255) not null,
    email varchar(255) not null unique
);

create table if not exists items
(
    id          bigserial primary key,
    name        varchar(255)              not null,
    description varchar(1000)             not null,
    available   boolean                   not null,
    owner_id    bigint references users (id) not null
);

create table if not exists bookings
(
    id         bigserial primary key,
    start_date timestamp without time zone not null,
    end_date   timestamp without time zone not null,
    item_id    bigint references items (id)   not null,
    booker_id  bigint references users (id)   not null,
    status     int                         not null
);

create table if not exists comments
(
    id        bigserial primary key,
    text      varchar(1000)               not null,
    item_id   bigint references items (id)   not null,
    author_id bigint references users (id)   not null,
    created   timestamp without time zone not null
);

