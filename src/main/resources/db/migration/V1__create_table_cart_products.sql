create table carts (
    id bigint generated always as identity,
    id_user bigint not null,
    deleted boolean not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6),
    deleted_at timestamp(6),

    primary key (id)
);

create table products (
    id bigint generated always as identity,

    primary key (id)
);

create table order_item (
    id bigint generated always as identity,
    product_id bigint not null,
    cart_id boolean not null,
    amount bigint,

    primary key (id)
);
