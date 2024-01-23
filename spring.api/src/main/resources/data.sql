INSERT INTO users (id, id_user, nom, prenom, email, adresse, password, status) values
    (1, "3214567890", "Bourbon", "Bruno", "bruno@gmail.com", null, "$2a$10$dESmo36FYedwTv0riMH/guYMvJLnfSKXVz0x.iiXJkmnZre5gqtTW", 1),
    (2, "4865793152", "Dupont", "Robert", "robert@gmail.com", null, "$2a$10$6wCyKv.v/Zj/4dS5B.Ew6.2oFKk6vvTANd43UdYTgmHFSf82pOV6i", 1),
    (3, "9876543210", "Boubaka", "Boubou", "boubou@gmail.com", null, "$2a$10$UyW09l43ok.j1qW0sPRV.uoxGEe1QHAKNm1OWy31oVdZ12mvmID2G", 1),
    (4, "8793697412", "Da Silva", "Antonia", "antonia@gmail.com", null, "$2a$10$jdwxojCQMWtZdnRFepkzyuN47YwEmLp6jeK1cQkYeEy4lPlpuU25.", 1),
    (5, "8527410963", "Santos", "Maria", "maria@gmail.com", null, "$2a$10$WP/U/LOj1iyMR2CwhLG9guEdxLO/KUy9.u1ZBPvCHsZX5wg.g0AWu", 1);

-- Mot de Pass
-- Bourbon 123456789
-- Dupont abcdefgh
-- Boubaka 123abc456def
-- Da Silva 123123123
-- Santos bounjoubonsoir

INSERT INTO portefeuilles (id, id_user, solde, update_date) values
    (1, 1, 0.00, "2023-01-21"),
    (2, 2, 0.00, "2023-01-21"),
    (3, 3, 0.00, "2023-01-21"),
    (4, 4, 0.00, "2023-01-21"),
    (5, 5, 0.00, "2023-01-21");

INSERT INTO tokens_auth_email  (id, id_user, token, update_date) values
    (1, 1, "12312312", "2024-01-01"),
    (2, 2, "ABCDEFGH", "2024-01-02"),
    (3, 3, "ABCDABCD", "2024-01-03"),
    (4, 4, "12341234", "2024-01-04"),
    (5, 5, "1234ABCD", "2024-01-05");

INSERT INTO cartes (id, id_portefeuille, nom_carte, numero_carte, mois_carte, cryptogramme) values
    (1, 1, "Alexandre Kerestes", "4798437001960501", "2027/09/01", 126),
    (2, 1, "Alexandre Kerestes", "5455956639088873", "2025/01/01", 753),
    (3, 2, "Robert Dupont", "5147284391738583", "2024/03/01", 852),
    (4, 3, "Bouboub Boubaka", "348276175855854", "2026/10/01", 126),
    (5, 3, "Tanka Boubaka", "4048668312774813", "2025/04/01", 534),
    (6, 4, "Antonia Da Silva", "5496489978414287", "2029/06/01", 806),
    (7, 5, "Maria Santos", "4897003295626842", "2029/12/01", 967);

INSERT INTO ibans (id, id_portefeuille, code_banque, code_guichet, num_compte, cle_rib, iban, bic) values
    (1, 1, "17569", "00050", "2853625595Z", "94", "FR19", "AGRIRERX" ),
    (2, 3, "10096", "00050", "7173765113R", "50", "FR63", "BAMYFR22"),
    (3, 5, "12739", "00050", "6743545458X", "79", "FR57", "PARBFRPP"),
    (4, 5, "17569", "00040", "6156389269V", "39", "FR34", "SOGEFRPP");

INSERT INTO depots (id, id_portefeuille, id_carte, id_iban, montant, date_depot) values
    (1, 1, 2, null, 200.35, "2023-10-05"),
    (2, 1, 1, null, 350.50, "2023-11-10"),
    (3, 1, null, 1, 350.50, "2023-11-10"),
    (4, 2, 3, null, 1000.00, "2023-07-23"),
    (5, 2, 3, null, 50.00, "2023-06-05"),
    (6, 3, 5, null, 500.00, "2024-01-05"),
    (7, 3, null, 2, 500.00, "2024-01-05"),
    (8, 4, 6, null, 250.55, "2023-12-25"),
    (9, 5, 7, null, 1500, "2023-10-05"),
    (10, 5, null, 4, 200.00, "2023-08-30");

INSERT INTO connection_buddys (id, id_user1, id_user2) values
    (1, 1, 2),
    (2, 1, 4),
    (3, 4, 2),
    (4, 5, 1),
    (5, 3, 2),
    (6, 2, 5),
    (7, 4, 5);

 INSERT INTO transaction_buddys (id, id_portefeuille_origine, id_portefeuille_destination, montant, date_transaction) values
    (1, 1, 2, 200.21, "2024-01-10"),
    (2, 1, 4, 103.77, "2024-01-11"),
    (3, 4, 2, 50.33, "2024-01-12"),
    (4, 5, 1, 699.99, "2024-01-13"),
    (5, 3, 2, 392.87, "2024-01-14"),
    (6, 2, 5, 123.45, "2024-01-15"),
    (7, 4, 5, 102.21, "2024-01-16");

INSERT INTO retraits (id, id_portefeuille, id_iban, montant, date_retrait) values
     (1, 1, 1, 297.36, "2024-01-17"),
     (2, 3, 2, 569.95, "2024-01-17"),
     (3, 5, 3, 200.67, "2024-01-17"),
     (4, 5, 4, 500.25, "2024-01-17");

