DELIMITER |
    CREATE TRIGGER depot_trigger
        BEFORE INSERT
        ON depots
        FOR EACH ROW
        BEGIN
            SET
                NEW.montant_liquide = NEW.montant_total * 0.95,
                NEW.taxe = NEW.montant_total * 0.05;
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
                NEW.taxe = NEW.montant_total * 0.05;
            UPDATE
                portefeuilles SET solde = solde - NEW.montant_liquide, update_date = NEW.date_retrait WHERE id = NEW.id_portefeuille;
            UPDATE
                portefeuilles SET solde = solde + NEW.taxe WHERE id = 1;
        END |
DELIMITER ;

DELIMITER |
    CREATE TRIGGER transaction_origine_trigger
        BEFORE INSERT
        ON transaction_buddys
        FOR EACH ROW
        BEGIN
            SET
                NEW.montant_liquide = NEW.montant_total * 0.95,
                NEW.taxe = NEW.montant_total * 0.05;
            UPDATE
                portefeuilles SET solde = solde - NEW.montant_liquide, update_date = NEW.date_transaction WHERE id = NEW.id_portefeuille_origine;
            UPDATE
                portefeuilles SET solde = solde + NEW.taxe WHERE id = 1;
        END |
DELIMITER ;

DELIMITER |

    CREATE TRIGGER transaction_destination_trigger
        BEFORE INSERT
        ON transaction_buddys
        FOR EACH ROW
        BEGIN
            SET
                NEW.montant_liquide = NEW.montant_total * 0.95,
                NEW.taxe = NEW.montant_total * 0.05;
            UPDATE
                portefeuilles SET solde = solde + NEW.montant_liquide, update_date = NEW.date_transaction WHERE id = NEW.id_portefeuille_destination;
            UPDATE
                portefeuilles SET solde = solde + NEW.taxe WHERE id = 1;
        END |
DELIMITER ;