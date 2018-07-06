INSERT INTO locations(id, latitude, longitude) VALUES (1, '-34.6106419', '-57.1142337');
INSERT INTO locations(id, latitude, longitude) VALUES (2, '-55.2236421', '-59.4911418');
INSERT INTO locations(id, latitude, longitude) VALUES (3, '-14.4324674', '-61.4234246');

INSERT INTO restaurants(id, address, admin_number, commercial_email, commercial_name, legal_name, logo, rating, location_id)
	VALUES (1, 'address 1', 'admin_number 1', 'commercial_email 1', 'commercial_name 1', 'legal_name 1', 'logo 1', 4, 1);
	
INSERT INTO restaurants(id, address, admin_number, commercial_email, commercial_name, legal_name, logo, rating, location_id)
	VALUES (2, 'address 2', 'admin_number 2', 'commercial_email 2', 'commercial_name 2', 'legal_name 2', 'logo 2', 5, 2);
	
INSERT INTO restaurants(id, address, admin_number, commercial_email, commercial_name, legal_name, logo, rating, location_id)
	VALUES (3, 'address 3', 'admin_number 3', 'commercial_email 3', 'commercial_name 3', 'legal_name 3', 'logo 3', 3, 3);
	
INSERT INTO reviews(id, name, rating, review, restaurant_id) VALUES (1, 'Martin Garcia', 5, 'Excelente', 1);
INSERT INTO reviews(id, name, rating, review, restaurant_id) VALUES (2, 'Natalia Gomez', 4, 'Muy bueno', 2);
INSERT INTO reviews(id, name, rating, review, restaurant_id) VALUES (3, 'Carlos Castro', 3, 'Regular',   3);
INSERT INTO reviews(id, name, rating, review, restaurant_id) VALUES (4, 'Alejandro Aiz', 4, 'Muy bueno', 1);
INSERT INTO reviews(id, name, rating, review, restaurant_id) VALUES (5, 'Roberto Carti', 3, 'Regular',   1);