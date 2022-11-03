DROP DATABASE IF EXISTS pickandroll;
CREATE DATABASE pickandroll;
USE pickandroll;

-- CREATE USER 'dev'@'localhost' IDENTIFIED BY '******************';
-- GRANT ALL PRIVILEGES ON * . * TO 'dev'@'localhost';

-- Crear taula usuaris
-- ---------------------------
-- DNI not unique only for testing (?)
-- -------------------------------------
CREATE TABLE pickandroll.user (
	id INT NOT NULL AUTO_INCREMENT,
    dni VARCHAR(9) NOT NULL,
    name VARCHAR(45) NOT NULL,
    surname VARCHAR(45) NOT NULL,
    email VARCHAR(40) NOT NULL,
    password VARCHAR(128) NOT NULL,
	phone VARCHAR(128) NULL,
    reset_password_token VARCHAR(30) NULL,
	PRIMARY KEY (id)
);

-- Crear taula rols
CREATE TABLE pickandroll.role (
	id INT NOT NULL AUTO_INCREMENT,
	name ENUM('admin', 'customer'),
	PRIMARY KEY (id)
);

-- Crear taula rols d'usuari
CREATE TABLE pickandroll.users_roles (
	user_id INT,
    role_id INT,    
    FOREIGN KEY (user_id) REFERENCES user(id), 
    FOREIGN KEY (role_id) REFERENCES role(id),
    UNIQUE (user_id, role_id)
);

-- Crear taula mòduls
CREATE TABLE pickandroll.module (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    path VARCHAR(40) not null,
    icon VARCHAR(100) NOT NULL,
    description VARCHAR (250) NOT NULL,
    enable BOOLEAN DEFAULT false NOT NULL,
	PRIMARY KEY (id)
);

-- Crear taula vehicles
CREATE TABLE pickandroll.vehicle (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL,
    type VARCHAR(20) NOT NULL,
    description VARCHAR(200) NOT NULL,
    price DOUBLE NOT NULL,
    enabled BOOLEAN,
	PRIMARY KEY (id)
);

-- Crear taula comanda
CREATE TABLE pickandroll.product_order (
	id INT NOT NULL AUTO_INCREMENT,
	rent_days INT NOT NULL,
    start_date VARCHAR(15) NOT NULL,
    picked_date DATE,
    returned_date DATE,
    total_price DOUBLE,
    returned BOOLEAN DEFAULT FALSE,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id),
	PRIMARY KEY (id)
);

-- Crear taula vehicles a la comanda
CREATE TABLE pickandroll.orders_vehicles (
	order_id INT,
	vehicle_id INT,
    FOREIGN KEY (order_id) REFERENCES pickandroll.product_order(id), 
    FOREIGN KEY (vehicle_id) REFERENCES pickandroll.vehicle(id)
);

-- Crear taula cistell
CREATE TABLE pickandroll.user_cart (
	user_id INT,
    vehicle_id INT,    
    FOREIGN KEY (user_id) REFERENCES user(id), 
    FOREIGN KEY (vehicle_id) REFERENCES vehicle(id),
    UNIQUE (user_id, vehicle_id)
);

-- Usuaris
INSERT INTO pickandroll.user (dni, name, surname, email, password, phone) VALUES ('12345678Z', 'Raúl', 'Jiménez Carrillo', 'raul@pickandroll.com', '$2a$10$KYE.1AgIvujAeYziqGhJNuhOvxbSzbVmonViAswrIsGMnMVVhwKCi', '000000000');
INSERT INTO pickandroll.user (dni, name, surname, email, password, phone) VALUES ('12345678Z', 'Xavi', 'Carne Plandolit', 'xavi@pickandroll.com', '$2a$10$KYE.1AgIvujAeYziqGhJNuhOvxbSzbVmonViAswrIsGMnMVVhwKCi', '111111111');
INSERT INTO pickandroll.user (dni, name, surname, email, password, phone) VALUES ('12345678Z', 'Santi', 'Alves Pizzorno', 'santia@pickandroll.com', '$2a$10$KYE.1AgIvujAeYziqGhJNuhOvxbSzbVmonViAswrIsGMnMVVhwKCi', '664033984');

-- Rols
INSERT INTO pickandroll.role (name, id) VALUES ('admin', 1);
INSERT INTO pickandroll.role (name, id) VALUES ('customer', 2);

-- Rols als usuaris
INSERT INTO pickandroll.users_roles (user_id, role_id) VALUES (1, 1); -- ID usuario (raul) -> ID rol (admin)
INSERT INTO pickandroll.users_roles (user_id, role_id) VALUES (2, 2); -- ID usuario (xavi) -> ID rol (customer)
INSERT INTO pickandroll.users_roles (user_id, role_id) VALUES (3, 1); -- ID usuario (santi) -> ID rol (admin)

-- Mòduls
INSERT INTO pickandroll.module (name, path, icon, description, enable) VALUES("Comandes", "/orders", "bi bi-cart3 mt-2 bi-xl", "Mòdul el qual permet visualitzar les comandes realitzades pels clients.", true); 
INSERT INTO pickandroll.module (name, path, icon, description, enable) VALUES("Usuaris", "/users", "bi bi-person mt-2 bi-xl", "Mòdul el qual permet gestionar els usuaris registrats", true); 
INSERT INTO pickandroll.module (name, path, icon, description, enable) VALUES("Vehicles", "/vehicles", "bi bi-bicycle mt-2 bi-xl", "Mòdul el qual permet gestionar els vehicles", true); 

-- Vehicles
INSERT INTO pickandroll.vehicle (name, type, description, price, enabled) VALUES ('Rock Rider ST 120', 'Bicicleta', 'Una BTT tècnica i còmoda! Pilotant la BTT ST 120, sentiràs la precisió i la lleugeresa que ofereixen el seu monoplat (1x9 velocitats) i els seus frens de disc mecànics.', 10, true);
INSERT INTO pickandroll.vehicle (name, type, description, price, enabled) VALUES ('Xiaomi 1S', 'Patinet', 'Rutes regulars de mitjana distància (fins a 25 km) a la ciutat. Només 3 segons per a desplegar-ho i usar-ho.', 5, true);

-- Bunch of users for testing
DELIMITER $$
CREATE PROCEDURE addUsers()
BEGIN
DECLARE i INT Default 1 ;
DECLARE curr_email VARCHAR(20);
DECLARE curr_id INT;
	simple_loop: LOOP		
		SET i = i + 1;
        SET curr_email = CONCAT(i, '@pickandroll.com');
		INSERT INTO pickandroll.user (dni, name, surname, email, password, phone) VALUES ('12345678Z', 'Test', 'Test', curr_email, '$2a$10$KYE.1AgIvujAeYziqGhJNuhOvxbSzbVmonViAswrIsGMnMVVhwKCi', '000000000');
        
		SET curr_id = (SELECT id FROM user WHERE email LIKE curr_email LIMIT 1);
        INSERT INTO pickandroll.users_roles (user_id, role_id) VALUES (curr_id, 2);
        IF i = 30 THEN
			LEAVE simple_loop;
		END IF;
	END LOOP simple_loop;
END $$
CALL addUsers();