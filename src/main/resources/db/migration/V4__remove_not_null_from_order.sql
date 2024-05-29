ALTER TABLE order_table ALTER COLUMN address DROP NOT NULL;
ALTER TABLE order_table ALTER COLUMN phone_number DROP NOT NULL;
ALTER TABLE order_table ALTER COLUMN comment DROP NOT NULL;

ALTER TABLE order_table ADD COLUMN city varchar;
ALTER TABLE order_table ADD COLUMN full_name varchar;

