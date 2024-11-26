CREATE TABLE accidents (
  id serial primary key,
  name text,
  description text,
  address text,
  type_id int references accident_types(id)
);