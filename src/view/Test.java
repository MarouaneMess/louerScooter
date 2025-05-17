// package view;

// import controller.ParcController;
// import model.*;
// import utils.DataManager;

// import java.time.LocalDate;
// import java.util.Vector;

// public class Test {
//     public static void main(String[] args) {
//         // Initialiser le gestionnaire de données
//         DataManager dataManager = new DataManager();

//         // Charger les données dans le parc
//         Parc parc = new Parc("123 Rue des Scooters");
//         parc.setClients(dataManager.getClients());
//         parc.setScooters(dataManager.getScooters());
//         parc.setMarques(dataManager.getMarques());

//         // Remplir les fichiers avec des données fictives si nécessaire
//         if (parc.getMarques().isEmpty()) {
//             // Création des marques
//             Marque yamaha = new Marque(parc, "Yamaha");
//             Marque honda = new Marque(parc, "Honda");
//             Marque suzuki = new Marque(parc, "Suzuki");
//             Marque kawasaki = new Marque(parc, "Kawasaki");
//             parc.ajouterMarque(yamaha);
//             parc.ajouterMarque(honda);
//             parc.ajouterMarque(suzuki);
//             parc.ajouterMarque(kawasaki);

//             // Création des catégories
//             Categorie categorieA1 = new Categorie("A1");
//             Categorie categorieA2 = new Categorie("A2");
//             Categorie categorieA = new Categorie("A");
//             Categorie categorieAM = new Categorie("AM");

//             // Création des modèles et association aux marques
//             Vector<Categorie> categoriesYamaha = new Vector<>();
//             categoriesYamaha.add(categorieA1);
//             categoriesYamaha.add(categorieA2);

//             yamaha.ajouterModele(new Modele("XMAX", yamaha, 125, categoriesYamaha));
//             yamaha.ajouterModele(new Modele("TMAX", yamaha, 500, categoriesYamaha));
//             yamaha.ajouterModele(new Modele("MT-07", yamaha, 700, categoriesYamaha));

//             Vector<Categorie> categoriesHonda = new Vector<>();
//             categoriesHonda.add(categorieA2);
//             categoriesHonda.add(categorieA);

//             honda.ajouterModele(new Modele("PCX", honda, 150, categoriesHonda));
//             honda.ajouterModele(new Modele("Forza", honda, 350, categoriesHonda));
//             honda.ajouterModele(new Modele("CBR500R", honda, 500, categoriesHonda));

//             Vector<Categorie> categoriesSuzuki = new Vector<>();
//             categoriesSuzuki.add(categorieA);

//             suzuki.ajouterModele(new Modele("Burgman", suzuki, 200, categoriesSuzuki));
//             suzuki.ajouterModele(new Modele("Hayabusa", suzuki, 1300, categoriesSuzuki));

//             Vector<Categorie> categoriesKawasaki = new Vector<>();
//             categoriesKawasaki.add(categorieAM);

//             kawasaki.ajouterModele(new Modele("Ninja", kawasaki, 300, categoriesKawasaki));
//             kawasaki.ajouterModele(new Modele("Z900", kawasaki, 900, categoriesKawasaki));
//         }

//         if (parc.getScooters().isEmpty()) {
//             // Ajout de scooters fictifs
//             parc.ajouterScooter(new Scooter("123ABC", parc.getMarques().get(0).getModeles().get(0),
//                     LocalDate.of(2022, 1, 1), true, 5000, 25, parc));
//             parc.ajouterScooter(new Scooter("456DEF", parc.getMarques().get(1).getModeles().get(0),
//                     LocalDate.of(2021, 1, 1), true, 10000, 30, parc));
//             parc.ajouterScooter(new Scooter("789GHI", parc.getMarques().get(2).getModeles().get(0),
//                     LocalDate.of(2020, 6, 15), true, 15000, 35, parc));
//             parc.ajouterScooter(new Scooter("101JKL", parc.getMarques().get(3).getModeles().get(0),
//                     LocalDate.of(2023, 3, 10), true, 2000, 40, parc));
//             parc.ajouterScooter(new Scooter("112MNO", parc.getMarques().get(0).getModeles().get(1),
//                     LocalDate.of(2022, 8, 20), true, 8000, 50, parc));
//             parc.ajouterScooter(new Scooter("131PQR", parc.getMarques().get(1).getModeles().get(1),
//                     LocalDate.of(2021, 11, 5), true, 12000, 60, parc));
//             parc.ajouterScooter(new Scooter("415STU", parc.getMarques().get(2).getModeles().get(1),
//                     LocalDate.of(2020, 4, 25), true, 25000, 70, parc));
//             parc.ajouterScooter(new Scooter("161VWX", parc.getMarques().get(3).getModeles().get(1),
//                     LocalDate.of(2023, 2, 14), true, 3000, 80, parc));
//             parc.ajouterScooter(new Scooter("718YZA", parc.getMarques().get(0).getModeles().get(2),
//                     LocalDate.of(2022, 9, 30), true, 7000, 90, parc));
//             parc.ajouterScooter(new Scooter("192BCD", parc.getMarques().get(1).getModeles().get(2),
//                     LocalDate.of(2021, 12, 1), true, 11000, 100, parc));
//             parc.ajouterScooter(new Scooter("203EFG", parc.getMarques().get(2).getModeles().get(0),
//                     LocalDate.of(2023, 5, 1), true, 4000, 25, parc));
//             parc.ajouterScooter(new Scooter("214HIJ", parc.getMarques().get(3).getModeles().get(0),
//                     LocalDate.of(2022, 7, 15), true, 6000, 30, parc));
//             parc.ajouterScooter(new Scooter("225KLM", parc.getMarques().get(0).getModeles().get(1),
//                     LocalDate.of(2021, 3, 20), true, 14000, 35, parc));
//             parc.ajouterScooter(new Scooter("236NOP", parc.getMarques().get(1).getModeles().get(1),
//                     LocalDate.of(2020, 10, 10), true, 18000, 40, parc));
//         }

//         if (parc.getClients().isEmpty()) {
//             // Création de catégories
//             Categorie categorieA1 = new Categorie("A1");
//             Categorie categorieA2 = new Categorie("A2");
//             Categorie categorieA = new Categorie("A");
//             Categorie categorieAM = new Categorie("AM");

//             // Création de clients fictifs avec des catégories
//             Vector<Categorie> categoriesClient1 = new Vector<>();
//             categoriesClient1.add(categorieA1);

//             Vector<Categorie> categoriesClient2 = new Vector<>();
//             categoriesClient2.add(categorieA2);

//             Vector<Categorie> categoriesClient3 = new Vector<>();
//             categoriesClient3.add(categorieA);

//             Vector<Categorie> categoriesClient4 = new Vector<>();
//             categoriesClient4.add(categorieAM);

//             Vector<Categorie> categoriesClient5 = new Vector<>();
//             categoriesClient5.add(categorieA1);
//             categoriesClient5.add(categorieA2);

//             Vector<Categorie> categoriesClient6 = new Vector<>();
//             categoriesClient6.add(categorieA2);
//             categoriesClient6.add(categorieA);

//             Vector<Categorie> categoriesClient7 = new Vector<>();
//             categoriesClient7.add(categorieA);

//             Vector<Categorie> categoriesClient8 = new Vector<>();
//             categoriesClient8.add(categorieAM);

//             parc.ajouterClient(
//                     new Client(parc, 12345, "John", "Doe", LocalDate.of(1990, 5, 15), "123 Rue A", categoriesClient1));
//             parc.ajouterClient(new Client(parc, 67890, "Jane", "Smith", LocalDate.of(1985, 8, 20), "456 Rue B",
//                     categoriesClient2));
//             parc.ajouterClient(new Client(parc, 11223, "Alice", "Brown", LocalDate.of(1995, 3, 10), "789 Rue C",
//                     categoriesClient3));
//             parc.ajouterClient(
//                     new Client(parc, 44556, "Bob", "White", LocalDate.of(1980, 7, 25), "101 Rue D", categoriesClient4));
//             parc.ajouterClient(new Client(parc, 77889, "Charlie", "Green", LocalDate.of(1992, 11, 5), "112 Rue E",
//                     categoriesClient5));
//             parc.ajouterClient(new Client(parc, 99001, "Diana", "Black", LocalDate.of(1988, 4, 20), "131 Rue F",
//                     categoriesClient6));
//             parc.ajouterClient(
//                     new Client(parc, 22334, "Eve", "Blue", LocalDate.of(1993, 9, 30), "415 Rue G", categoriesClient7));
//             parc.ajouterClient(new Client(parc, 55667, "Frank", "Yellow", LocalDate.of(1987, 12, 1), "161 Rue H",
//                     categoriesClient8));
//         }

//         // Afficher les données pour vérification
//         System.out.println("Liste des clients :");
//         for (Client client : parc.getClients()) {
//             System.out.println(client);
//         }

//         System.out.println("\nListe des scooters :");
//         for (Scooter scooter : parc.getScooters()) {
//             System.out.println(scooter);
//         }

//         System.out.println("\nListe des marques :");
//         for (Marque marque : parc.getMarques()) {
//             System.out.println(marque);
//         }

//         // Créer la vue et le contrôleur
//         ParcView parcView = new ParcView(parc, 800, 400);
//         ParcController parcController = new ParcController(parcView, parc);

//         // Afficher la vue
//         parcView.setVisible(true);

//     }
// }