DROP TRIGGER IF EXISTS depot_trigger;
DROP TRIGGER IF EXISTS retrait_trigger;
DROP TRIGGER IF EXISTS transaction_trigger;
DROP TABLE IF EXISTS transaction_buddys;
DROP TABLE IF EXISTS retraits;
DROP TABLE IF EXISTS depots;
DROP TABLE IF EXISTS cartes;
DROP TABLE IF EXISTS ibans;
DROP TABLE IF EXISTS connection_buddys;
DROP TABLE IF EXISTS tokens_auth_email;
DROP TABLE IF EXISTS portefeuilles;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT NOT NULL AUTO_INCREMENT,
    id_user CHAR(10) NOT NULL,
    prenom VARCHAR(250) NOT NULL,
    nom VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL,
    adresse VARCHAR(250),
    ville VARCHAR(100),
    code_postal VARCHAR(10),
    status INT NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE portefeuilles(
    id INT NOT NULL AUTO_INCREMENT,
    id_user INT NOT NULL,
    solde NUMERIC(10,2) DEFAULT 0.00,
    update_date DATE,
    primary key(id)
);


CREATE TABLE tokens_auth_email (
    id INT NOT NULL AUTO_INCREMENT,
    id_user INT NOT NULL,
    token CHAR(8) NOT NULL,
    update_date DATE NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (id_user) REFERENCES users(id)
);

CREATE TABLE connection_buddys (
    id INT NOT NULL AUTO_INCREMENT,
    id_user1 INT NOT NULL,
    id_user2 INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_user1) REFERENCES users(id),
    FOREIGN KEY (id_user2) REFERENCES users(id)
);

CREATE TABLE cartes (
    id INT NOT NULL AUTO_INCREMENT,
    id_portefeuille INT NOT NULL,
    nom_carte VARCHAR(250) NOT NULL,
    numero_carte VARCHAR(20) NOT NULL,
    mois_carte DATE NOT NULL,
    cryptogramme SMALLINT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ibans (
    id INT NOT NULL AUTO_INCREMENT,
    id_portefeuille INT NOT NULL,
    code_banque VARCHAR(5) NOT NULL,
    code_guichet VARCHAR(5) NOT NULL,
    num_compte VARCHAR(20) NOT NULL,
    cle_rib CHAR(2) NOT NULL,
    iban VARCHAR(4) NOT NULL,
    bic VARCHAR(11) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE depots (
    id INT NOT NULL AUTO_INCREMENT,
    id_portefeuille INT NOT NULL,
    id_carte INT,
    id_iban INT,
    montant_total NUMERIC(10,2) NOT NULL,
    montant_liquide NUMERIC(10,2),
    taxe NUMERIC(10,2),
    date_depot DATE NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (id_portefeuille) REFERENCES portefeuilles(id),
    FOREIGN KEY (id_carte) REFERENCES cartes(id),
    FOREIGN KEY (id_iban) REFERENCES ibans(id)
);

CREATE TABLE retraits (
    id INT NOT NULL AUTO_INCREMENT,
    id_portefeuille INT NOT NULL,
    id_iban INT NOT NULL,
    montant_total NUMERIC(10,2) NOT NULL,
    montant_liquide NUMERIC(10,2),
    taxe NUMERIC(10,2),
    date_retrait DATE NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (id_portefeuille) REFERENCES portefeuilles(id),
    FOREIGN KEY (id_iban) REFERENCES ibans(id)
);

CREATE TABLE transaction_buddys(
    id INT NOT NULL AUTO_INCREMENT,
    id_portefeuille_origine INT NOT NULL,
    id_portefeuille_destination INT NOT NULL,
    montant_total NUMERIC(10,2) NOT NULL,
    montant_liquide NUMERIC(10,2),
    taxe NUMERIC(10,2),
    date_transaction DATE NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (id_portefeuille_origine) REFERENCES portefeuilles(id),
    FOREIGN KEY (id_portefeuille_destination) REFERENCES portefeuilles(id)
);

DELIMITER |
    CREATE TRIGGER depot_trigger
        BEFORE INSERT
        ON depots
        FOR EACH ROW
        BEGIN
            SET
                NEW.montant_liquide = NEW.montant_total * 0.95,
                NEW.taxe = NEW.montant_total - NEW.montant_liquide;
            UPDATE
                portefeuilles SET solde = solde + NEW.montant_liquide, update_date = NEW.date_depot WHERE id = NEW.id_portefeuille;
            UPDATE
                portefeuilles SET solde = solde + NEW.taxe WHERE id = 1;
        END |
DELIMITER ;

DELIMITER |
    CREATE TRIGGER retrait_trigger
        BEFORE INSERT
        ON retraits
        FOR EACH ROW
        BEGIN
            SET
                NEW.montant_liquide = NEW.montant_total * 0.95,
                NEW.taxe = NEW.montant_total - NEW.montant_liquide;
            UPDATE
                portefeuilles SET solde = solde - NEW.montant_liquide, update_date = NEW.date_retrait WHERE id = NEW.id_portefeuille;
            UPDATE
                portefeuilles SET solde = solde + NEW.taxe WHERE id = 1;
        END |
DELIMITER ;

DELIMITER |
    CREATE TRIGGER transaction_trigger
        BEFORE INSERT
        ON transaction_buddys
        FOR EACH ROW
        BEGIN
            SET
                NEW.montant_liquide = NEW.montant_total * 0.95,
                NEW.taxe = NEW.montant_total - NEW.montant_liquide;
            UPDATE
                portefeuilles SET solde = solde - NEW.montant_liquide, update_date = NEW.date_transaction WHERE id = NEW.id_portefeuille_origine;
            UPDATE
                portefeuilles SET solde = solde + NEW.montant_liquide, update_date = NEW.date_transaction WHERE id = NEW.id_portefeuille_destination;
            UPDATE
                portefeuilles SET solde = solde + NEW.taxe WHERE id = 1;
        END |
DELIMITER ;