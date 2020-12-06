INSERT INTO customers (birth, name, run) VALUES ('1992-11-25', 'Juan Carlos', '19414979-4');
INSERT INTO customers (birth, name, run) VALUES ('1992-11-20', 'Danixa Fuentes', '19300979-4');
INSERT INTO customers (birth, name, run) VALUES ('1992-11-21', 'Catalina Ruiz', '19411100-4');

INSERT INTO packages (description, enable, name) VALUES ('Travel to Paris', true, 'Paris');
INSERT INTO packages (description, enable, name) VALUES ('Travel to Santiago', true, 'Santiago');
INSERT INTO packages (description, enable, name) VALUES ('Travel to Brazil', true, 'Brazil');
INSERT INTO packages (description, enable, name) VALUES ('Travel to New York', true, 'New York');
INSERT INTO packages (description, enable, name) VALUES ('Travel to My House', false, 'My House');

INSERT INTO travels (id_package, id_customer) VALUES (1,1);
INSERT INTO travels (id_package, id_customer) VALUES (1,2);
INSERT INTO travels (id_package, id_customer) VALUES (5,1);
INSERT INTO travels (id_package, id_customer) VALUES (2,1);

