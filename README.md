![logo](/img/paymaybuddy.png)
# Pay My Buddy - une appli de paiement pratique 

L'application Pay My Buddy vous permets d'organizer votre portefeuille d'une manier simple, intuitive et efficace.
Avec un clique vous pouvez faire une dépôt, un retrait ou vire de l'argent à un ami.

## Docker

C'est possible de demarrer l'application par le biais du Docker, pour autant il faut:

* Génerer le fichier .jar avec la commande
    * mvn install (dans le paque /spring.api)
* Lancer le composer de container
    * docker compose up --build

## La structure de l'application

L'application a été développée en Angular 17 et Java Spring 3.2.

Dans ce contexte, l'application est divisée en FrontEnd et BackEnd et le deux se communique par WebService RESTful.

### Les Endpoints disponiples

1. Les endpoints ouverts
    
    1. GET -> /registration
    2. POST -> /registration/confirm
    3. POST -> /login

2. Les endpoints fermés

    1. Transactions Endpoints
        
        1. GET -> /transfer-list
        2. POST -> /make-transfer

    2. Buddys Endpoints
        
        1. GET -> /buddy/buddy-list
        2. POST -> /buddy/trouver-buddy
        3. POST -> /buddy/add-buddy

    3. Portefeuille Endpoints

        1. GET -> /portefeuille

    4. Retrait Endpoints

        1. GET -> /retrait
        2. POST -> /retrait/make-retrait

    5. Depot Endpoints

        1. GET -> /depot
        2. POST -> /depot/make-depot

    6. User Endpoints

        1. GET -> /user
        2. POST -> /user

### La protection du système

Pour proteger le système quelques mesures ont été prises.

* Authentification par Token (JWT)
* Sécurisation de routes protegées dans le frontend (Angular Guard)
* Traitement des données sensibles dans le backend (Classe DTO)
* Sécurisation de la base de données (Variables d'environnement)

### La base de données

La base de données a été conçue pour garantir l'intégrité de certains données, par exemple, les soldes des comptes. En ce sens, certains déclencheurs (Trigger) ont été créés pour gérer les taux et les soldes.

Voici le modèle physique de données:

![logo](/img/bd-1.png)

### Les diagrammes UML

Le système traite la donnée en deux niveaux, le niveau d'entrée et d'accès à la base de données et le niveau de traitement, sécurisation et réponse.

Dans le premier niveau, nous avons les classes standards, où il n'y a aucune suppression de données ou traitement.

Cependant, nous ne pourrions pas renvoyer le solde ou l'adresse d'un utilisateur vers un autre parce qu'ils ont une connexion, dans ce cas, les données sont traitées dans les classes DTO.

Cette séparation nous permet de s'assurer que d'un côté, il n'y aura pas un problème d'accès à la base de données, et de l'autre, que les données seront toujours traitées avant d'être renvoyées.

#### Classes Standards

![logo](/img/classeStandard.png)

#### Classes DTO

![logo](/img/classeDTO.png)
