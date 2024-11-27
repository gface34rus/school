create table human
(
    id          serial primary key,
    name        varchar,
    age         integer,
    has_license boolean,
    car_id      integer references car (id)
);
create table car
(
    id    serial primary key,
    brand varchar,
    model varchar,
    price integer
)