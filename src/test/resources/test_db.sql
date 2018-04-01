INSERT INTO customer (id, firstname, lastname, address) VALUES ('thanasis-1', 'Thanasis', 'Athanasiou', 'Korytsas 6');
INSERT INTO customer (id, firstname, lastname, address) VALUES ('manolis-1', 'Manolis', 'Emanouilidis', 'Xeimarras 6');
INSERT INTO customer (id, firstname, lastname, address) VALUES ('alekos-1', 'Alekos', 'Alexiou', 'M. Asias 6');

INSERT INTO product (id, name, sku, description) VALUES ('portokalia-1', 'portokalia merlin', '1234', 'Portokalia merlin syskeyasmena 1kg');
INSERT INTO product (id, name, sku, description) VALUES ('patates-1', 'patates neyrokopiou', '1235', 'Patates  neyrokopiou syskeyasmenes 1kg');
INSERT INTO product (id, name, sku, description) VALUES ('cola-1', 'generic cola', '1236', 'Generic cola 1,5 lt');

INSERT INTO order_table (id, customer_id, order_date, last_update_date, order_status) VALUES ('alekos-order-1', 'alekos-1', '07-06-18T15:55:00', '07-06-18T15:55:00', 'NEW');

INSERT INTO ORDER_PRODUCT (order_id, product_id) VALUES ('alekos-order-1', 'portokalia-1');
INSERT INTO ORDER_PRODUCT (order_id, product_id) VALUES ('alekos-order-1', 'patates-1');
INSERT INTO ORDER_PRODUCT (order_id, product_id) VALUES ('alekos-order-1', 'cola-1');
