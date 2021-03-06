INSERT INTO users (enabled, password, username) VALUES (true, '$2a$04$.Gqg1Gmqx66Vb5XgzBB3Pe4l5CGTAmtSuQ27U5dh.rSdKyuj51uo6', 'Roxana');
INSERT INTO users (enabled, password, username) VALUES (true, '$2a$04$.Gqg1Gmqx66Vb5XgzBB3Pe4l5CGTAmtSuQ27U5dh.rSdKyuj51uo6', 'Estela');
INSERT INTO users (enabled, password, username) VALUES (true, '$2a$04$.Gqg1Gmqx66Vb5XgzBB3Pe4l5CGTAmtSuQ27U5dh.rSdKyuj51uo6', 'Marta');

INSERT INTO users (enabled, password, username) VALUES (true, '$2a$04$.Gqg1Gmqx66Vb5XgzBB3Pe4l5CGTAmtSuQ27U5dh.rSdKyuj51uo6', 'Merlin');
INSERT INTO users (enabled, password, username) VALUES (true, '$2a$04$.Gqg1Gmqx66Vb5XgzBB3Pe4l5CGTAmtSuQ27U5dh.rSdKyuj51uo6', 'Martin');

INSERT INTO users (enabled, password, username) VALUES (true, '$2a$04$.Gqg1Gmqx66Vb5XgzBB3Pe4l5CGTAmtSuQ27U5dh.rSdKyuj51uo6', 'Paz');

INSERT INTO users (enabled, password, username) VALUES (true, '$2a$04$.Gqg1Gmqx66Vb5XgzBB3Pe4l5CGTAmtSuQ27U5dh.rSdKyuj51uo6', 'Maxtil');
INSERT INTO users (enabled, password, username) VALUES (true, '$2a$04$.Gqg1Gmqx66Vb5XgzBB3Pe4l5CGTAmtSuQ27U5dh.rSdKyuj51uo6', 'Esteban');


INSERT INTO authorities (authority, id_user) VALUES ('ROLE_CLIENTE', 1);
INSERT INTO authorities (authority, id_user) VALUES ('ROLE_CLIENTE', 2);
INSERT INTO authorities (authority, id_user) VALUES ('ROLE_CLIENTE', 3);

INSERT INTO authorities (authority, id_user) VALUES ('ROLE_AGENTE', 7);
INSERT INTO authorities (authority, id_user) VALUES ('ROLE_AGENTE', 8);

INSERT INTO authorities (authority, id_user) VALUES ('ROLE_GERENTE', 4);
INSERT INTO authorities (authority, id_user) VALUES ('ROLE_GERENTE', 5);

INSERT INTO authorities (authority, id_user) VALUES ('ROLE_ADMINISTRADOR', 6);

INSERT INTO customers (id,birth, name, run) VALUES (1,'1992-11-25', 'Juan Carlos', '19414979-4');
INSERT INTO customers (id,birth, name, run) VALUES (2,'1992-11-20', 'Danixa Fuentes', '19300979-4');
INSERT INTO customers (id,birth, name, run) VALUES (3,'1992-11-21', 'Catalina Ruiz', '19411100-4');

INSERT INTO staff (id,birth, name, run) VALUES (4,'1992-11-25', 'Merlin', '19414979-4');
INSERT INTO staff (id,birth, name, run) VALUES (5,'1992-11-20', 'Martin', '19300979-4');
INSERT INTO staff (id,birth, name, run) VALUES (6,'1992-11-21', 'Paz', '19411100-4');

INSERT INTO staff (id,birth, name, run) VALUES (7,'1992-11-20', 'Maxtil', '19300979-4');
INSERT INTO staff (id,birth, name, run) VALUES (8,'1992-11-21', 'Esteban', '19411100-4');

INSERT INTO cities (id, description, name) VALUES (1, 'Lugar calido', 'Pucon');
INSERT INTO cities (id, description, name) VALUES (2, 'Lugar frio', 'Las Trancas');
INSERT INTO cities (id, description, name) VALUES (3, 'Lugar lindo', 'Temuco');

INSERT INTO packages (description, enable,image, name, id_city) VALUES ('Travel to Paris', true,'image', 'Paris',1);
INSERT INTO packages (description, enable,image, name, id_city) VALUES ('Travel to Santiago', true,'image', 'Santiago',1);
INSERT INTO packages (description, enable,image, name, id_city) VALUES ('Travel to Brazil', true,'image', 'Brazil',1);
INSERT INTO packages (description, enable,image, name, id_city) VALUES ('Travel to New York', true,'image', 'New York',1);
INSERT INTO packages (description, enable,image, name, id_city) VALUES ('Travel to My House', false,'image', 'My House',1);

INSERT INTO travels (id_package, id_customer) VALUES (1,1);
INSERT INTO travels (id_package, id_customer) VALUES (1,2);
INSERT INTO travels (id_package, id_customer) VALUES (5,1);
INSERT INTO travels (id_package, id_customer) VALUES (2,1);

