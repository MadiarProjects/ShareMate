create table users
(
    id    bigserial primary key,
    name  varchar(255) not null,
    email varchar(255) not null unique
);

create table items
(
    id              bigserial primary key,
    name            varchar(255)                 not null,
    description     varchar(1000)                not null,
    available       boolean                      not null,
    owner_id        bigint references users (id) not null,
    item_request_id bigint references item_requests (id)
);
create table bookings
(
    id         bigserial primary key,
    start_date timestamp without time zone  not null,
    end_date   timestamp without time zone  not null,
    item_id    bigint references items (id) not null,
    booker_id  bigint references users (id) not null,
    status     int                          not null
);

create table comments
(
    id        bigserial primary key,
    text      varchar(1000)                not null,
    item_id   bigint references items (id) not null,
    author_id bigint references users (id) not null,
    created   timestamp without time zone  not null
);
create table item_requests
(
    id           bigserial primary key,
    description  varchar(255)                 not null,
    requestor_id bigint references users (id) not null
);
