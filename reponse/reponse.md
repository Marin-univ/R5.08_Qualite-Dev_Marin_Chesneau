### Tâche 1

1.

- ProductCatalogDomain : gestion du catalogue des produits
- ProductRegistryDomain : gestion du référencement des produits
- StockDomain : gestion des stocks et des réservations
- CustomerDomain : gestion des comptes clients
- CustomerNotificationDomain : gestion notification liées au clients
- NotificationDomain : gestion des notifications
- ShoppingCartDomain : gestion du panier
- OrderProcessingDomain : gestion des commandes
- BillingDomain : gestion des payments et facturation

2.

Les services implémentent les domaines métier via des microservices isolés par domaine. 
Chaque microservice possède une structure claire avec logique métier (application) et adaptateurs techniques (infra).
Le pattern CQRS sépare les écritures (Command) et les lectures optimisées (Query).
Des bibliothèques transversales partagées gèrent les entités communes, événements et contrats.
La communication REST + Outbox garantit fiabilité, indépendance et scalabilité des domaines.

3.

- apps/store-back : Gestion des commandes et des paniers client
- apps/store-front : Interface utilisateur pour le catalogue et les achats
- libs/kernel : Entités métier partagées
- apps/product-registry-domain-service : Écriture des commandes produits
- apps/product-registry-read-service : Lectures optimisées des produits
- libs/bom-platform : Versionning centralisé des dépendances
- libs/cqrs-support : Infrastructure CQRS
- libs/sql : Migrations Liquibase pour la base de données

### Tâche 2

1.

- Stockage : Les actions sont enregistrées sous forme d’événements immuables, et des vues sont générées pour la lecture.
- Transactions : Tout est fait en une seule transaction (sauvegarde + événement + outbox) pour éviter les incohérences.
- Événements métiers : Chaque événement contient les infos clés (id, type, ordre, date) et est stocké de façon fiable.
- Gestion des erreurs : L’outbox réessaie automatiquement l’envoi des événements en cas d’échec.
- Communication entre services : REST pour le temps réel et traitement asynchrone des événements en arrière-plan.

2.

- Event Sourcing : Une librairie commune gère les événements métier, stockés en base SQL via Hibernate/Liquibase.
- CQRS : Les écritures sont séparées des lectures, avec un service pour modifier les données et un autre pour les lire.
- Outbox Pattern : Les événements sont envoyés de façon asynchrone avec gestion des retries pour éviter toute perte.
- Transactions : Sauvegarde des données, des événements et de l'outbox faite en une seule transaction sécurisée.
- Communication : Les services échangent via des APIs REST, avec des DTOs et du JSON pour le format des données.

3.

libs/cqrs-support fournit les abstractions CQRS (DomainEvent, EventEnvelope, Projector) et gère la persistance immuable des événements et de l’outbox via JPA. Les agrégats métier publient des événements, les services applicatifs assurent la transactionnalité, et les services de lecture appliquent ces événements avec des Projectors pour obtenir l’état lisible.

4.

libs/bom-platform centralise les versions des dépendances (Quarkus, JUnit, ...) via Gradle, évitant les duplications et garantissant une cohérence entre tous les modules.

5.

La fiabilité du CQRS + Kernel repose sur des événements immuables (EventLogEntity append-only) avec contraintes SQL et séquence pour traçabilité, le Product aggregate valide les règles métier avant publication, les opérations sont transactionnelles atomiques (save + append + publish), et l’Outbox poller avec retry/partition garantit la livraison des événements même en cas de panne réseau.







