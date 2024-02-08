
SET character_set_client = utf8;
SET character_set_connection = utf8;
SET character_set_results = utf8;
SET collation_connection = utf8_general_ci;

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

-- data SQL

INSERT INTO users (id, id_user, nom, prenom, email, adresse, ville, code_postal, password, status) values
    (1, "0000000000", "paymybuddy", "paymybuddy", "paymybuddy@email.com", "adress oficiel", "ville", "00000", "", 3),
    (2, "3214567890", "Bourbon", "Bruno", "bruno@gmail.com", null, null, null, "$2a$10$dESmo36FYedwTv0riMH/guYMvJLnfSKXVz0x.iiXJkmnZre5gqtTW", 1),
    (3, "4865793152", "Dupont", "Robert", "robert@gmail.com", null, null, null, "$2a$10$6wCyKv.v/Zj/4dS5B.Ew6.2oFKk6vvTANd43UdYTgmHFSf82pOV6i", 1),
    (4, "9876543210", "Boubaka", "Boubou", "boubou@gmail.com", null, null, null, "$2a$10$UyW09l43ok.j1qW0sPRV.uoxGEe1QHAKNm1OWy31oVdZ12mvmID2G", 1),
    (5, "8793697412", "Da Silva", "Antonia", "antonia@gmail.com", null, null, null, "$2a$10$jdwxojCQMWtZdnRFepkzyuN47YwEmLp6jeK1cQkYeEy4lPlpuU25.", 1),
    (6, "8527410963", "Santos", "Maria", "maria@gmail.com", null, null, null, "$2a$10$WP/U/LOj1iyMR2CwhLG9guEdxLO/KUy9.u1ZBPvCHsZX5wg.g0AWu", 1);

-- Mot de Pass
-- Bourbon 123456789
-- Dupont abcdefgh
-- Boubaka 123abc456def
-- Da Silva 123123123
-- Santos bounjoubonsoir

INSERT INTO portefeuilles (id, id_user, solde, update_date) values
    (1, 1, 0.00, "2022-01-01"),
    (2, 2, 0.00, "2023-01-21"),
    (3, 3, 0.00, "2023-01-21"),
    (4, 4, 0.00, "2023-01-21"),
    (5, 5, 0.00, "2023-01-21"),
    (6, 6, 0.00, "2023-01-21");

INSERT INTO tokens_auth_email  (id, id_user, token, update_date) values
    (1, 2, "12312312", "2024-01-01"),
    (2, 3, "ABCDEFGH", "2024-01-02"),
    (3, 4, "ABCDABCD", "2024-01-03"),
    (4, 5, "12341234", "2024-01-04"),
    (5, 6, "1234ABCD", "2024-01-05");

INSERT INTO cartes (id, id_portefeuille, nom_carte, numero_carte, mois_carte, cryptogramme) values
    (1, 2, "Alexandre Kerestes", "4798437001960501", "2027/09/01", 126),
    (2, 2, "Alexandre Kerestes", "5455956639088873", "2025/01/01", 753),
    (3, 3, "Robert Dupont", "5147284391738583", "2024/03/01", 852),
    (4, 4, "Bouboub Boubaka", "348276175855854", "2026/10/01", 126),
    (5, 4, "Tanka Boubaka", "4048668312774813", "2025/04/01", 534),
    (6, 5, "Antonia Da Silva", "5496489978414287", "2029/06/01", 806),
    (7, 6, "Maria Santos", "4897003295626842", "2029/12/01", 967);

INSERT INTO ibans (id, id_portefeuille, code_banque, code_guichet, num_compte, cle_rib, iban, bic) values
    (1, 2, "17569", "00050", "2853625595Z", "94", "FR19", "AGRIRERX" ),
    (2, 4, "10096", "00050", "7173765113R", "50", "FR63", "BAMYFR22"),
    (3, 6, "12739", "00050", "6743545458X", "79", "FR57", "PARBFRPP"),
    (4, 6, "17569", "00040", "6156389269V", "39", "FR34", "SOGEFRPP");

INSERT INTO depots (id, id_portefeuille, id_carte, id_iban, montant_total, date_depot) values
    (1, 2, 2, null, 200.35, "2023-10-05"),
    (2, 2, 1, null, 350.50, "2023-11-10"),
    (3, 2, null, 1, 350.50, "2023-11-10"),
    (4, 3, 3, null, 1000.00, "2023-07-23"),
    (5, 3, 3, null, 50.00, "2023-06-05"),
    (6, 4, 5, null, 500.00, "2024-01-05"),
    (7, 4, null, 2, 500.00, "2024-01-05"),
    (8, 5, 6, null, 250.55, "2023-12-25"),
    (9, 6, 7, null, 1500, "2023-10-05"),
    (10, 6, null, 4, 200.00, "2023-08-30");

INSERT INTO connection_buddys (id, id_user1, id_user2) values
    (1, 2, 3),
    (2, 2, 5),
    (3, 5, 3),
    (4, 6, 2),
    (5, 4, 3),
    (6, 3, 6),
    (7, 5, 6);

 INSERT INTO transaction_buddys (id, id_portefeuille_origine, id_portefeuille_destination, montant_total, date_transaction) values
    (1, 2, 3, 200.21, "2024-01-10"),
    (2, 2, 5, 103.77, "2024-01-11"),
    (3, 5, 3, 50.33, "2024-01-12"),
    (4, 6, 2, 699.99, "2024-01-13"),
    (5, 4, 3, 392.87, "2024-01-14"),
    (6, 3, 6, 123.45, "2024-01-15"),
    (7, 5, 6, 102.21, "2024-01-16");

INSERT INTO retraits (id, id_portefeuille, id_iban, montant_total, date_retrait) values
     (1, 2, 1, 297.36, "2024-01-17"),
     (2, 4, 2, 569.95, "2024-01-17"),
     (3, 6, 3, 200.67, "2024-01-17"),
     (4, 6, 4, 500.25, "2024-01-17");

