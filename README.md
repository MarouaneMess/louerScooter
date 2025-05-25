# LouerScooter

Application Java Swing pour la gestion d’un parc de scooters, des clients, des locations et des retours.

## Fonctionnalités principales

- **Gestion des scooters** : ajout, modification, suppression, recherche, affichage de la disponibilité.
- **Gestion des clients** : ajout, modification, suppression, historique des locations.
- **Gestion des locations** : création de location, suivi des dates, prix, gestion des retours et des retards.
- **Gestion des modèles et marques** : association des scooters à leurs modèles et marques.
- **Sauvegarde et chargement des données** : sérialisation des objets Java (`Serializable`) dans des fichiers binaires.
- **Interface graphique** : application basée sur Swing avec des tables dynamiques et des dialogues interactifs.

## Structure du projet

- `src/controller/` : Contrôleurs (logique métier, gestion des actions utilisateur)
- `src/model/` : Modèles de données (`Scooter`, `Client`, `Location`, `Retour`, etc.)
- `src/view/` : Interfaces graphiques Swing
- `src/utils/` : Utilitaires (`FileManager`, `DataManager`, etc.)
- `src/data/` : Fichiers de données sérialisés (`*.dat`)

## Sérialisation et Généricité

- Les classes de données implémentent l’interface `Serializable` pour permettre la sauvegarde et la restauration automatique des objets.
- Les méthodes utilitaires utilisent la généricité (`<T>`) pour gérer différents types de données de façon flexible et réutilisable.

## Lancer le projet

1. Ouvrir le projet dans un IDE Java (VS Code, IntelliJ, Eclipse…).
2. Compiler tous les fichiers Java dans le dossier `src/`.
3. Exécuter la classe principale (`Main.java`).
4. Les données sont automatiquement chargées et sauvegardées dans le dossier `src/data/`.

## Dépendances

- Java 8 ou supérieur
- Swing (inclus dans le JDK)

## Exemple d’utilisation

- Ajouter un scooter : bouton « Ajouter », remplir le formulaire, valider.
- Louer un scooter : sélectionner un client et un scooter disponible, choisir les dates, valider.
- Retourner un scooter : sélectionner la location, enregistrer le retour, voir si le retour est en retard.

## Auteurs

- Messafri Marouane
- Nekiche Adel

---

**Remarque :**  
Les données sont stockées dans des fichiers binaires pour garantir la persistance entre les sessions.  
Le projet est conçu pour être facilement extensible et maintenable grâce à l’utilisation de la POO, de la généricité et de la sérialisation.
