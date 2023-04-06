--liquibase formatted sql

--changeset manitaggarwal:1 runOnChange:true
create table program
(
    id          serial,
    name        varchar unique not null,
    wallet_type varchar
);

--changeset manitaggarwal:2 runOnChange:true
create table wallet
(
    user_id        varchar,
    earned         integer,
    spent          integer,
    expired        integer,
    wallet_status  varchar,
    program        integer,
    expiry_date    timestamp,
    transaction_id varchar unique,
    id             serial
        primary key
);

--changeset manitaggarwal:3 runOnChange:true
create table wallet.wallet_summary
(
    user_id varchar,
    balance integer,
    program integer,
    status  varchar,
    id      serial
        primary key
);

