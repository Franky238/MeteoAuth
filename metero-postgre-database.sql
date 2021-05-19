-- drop database db;
-- drop user "user";
-- create user "user" with password 'pass';
-- create database db with template=template0 owner="user";
-- \connect db;
alter default privileges grant all on tables to "user";
alter default privileges grant all on sequences to "user";

create table users
(
    user_id           integer primary key not null,
    first_name        varchar(20)         not null,
    last_name         varchar(20)         not null,
    email             varchar(30)         not null,
    password          text                not null,
    registration_time timestamp           not null,
    city              varchar(30)         not null
);

create table station
(
    station_id        integer primary key not null,
    user_id           integer             not null,
    title             varchar(20)         not null,
    destination       varchar(50)         not null,
    model_description varchar(50)         not null,
    registration_time timestamp           not null, --todo bigint
    phone             text                not null
-- todo    CREATE DOMAIN iol.phone_number AS TEXT     CHECK(         VALUE ~ '^(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$'     );
);
alter table station
    add constraint cat_users_fk
        foreign key (user_id) references users (user_id);

create table measured_value
(
    measured_value_id integer primary key not null,
    station_id        integer             not null,
    user_id           integer             not null,
    amount            numeric(10, 2)      not null,
    note              varchar(50)         not null,
    measurement_time  bigint              not null, --todo timestamp,
    humidity          integer             not null,
    temperature       integer             not null,
    air_quality       integer             not null,
    wind_speed        integer             not null,
    wind_gusts        integer             not null,
    wind_direction    integer             not null,
    rainfall          integer             not null
);
alter table measured_value
    add constraint trans_cat_fk
        foreign key (station_id) references station (station_id);
alter table measured_value
    add constraint trans_users_fk
        foreign key (user_id) references users (user_id);

create sequence et_users_seq increment 1 start 1;
create sequence et_station_seq increment 1 start 1;
create sequence et_measured_value_seq increment 1 start 1000;