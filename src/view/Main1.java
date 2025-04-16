// package view; 
// import model.*;
// import java.time.LocalDate;
// import java.util.Vector;

// public class Main1 {
// //    public static void main(String[] args) {
// //        // Créer un parc de scooters
// //        Parc parc = new Parc("123 Rue des Scooters");
// //
// //        // Créer des marques
// //        Marque marqueYamaha = new Marque("Yamaha");
// //        Marque marqueHonda = new Marque("Honda");
// //
// //        // Créer des catégories
// //        Categorie categorieA1 = new Categorie("A1");
// //        Categorie categorieB = new Categorie("B");
// //
// //        // Créer des modèles en utilisant un Vector
// //        Vector<Categorie> categoriesYamaha = new Vector<>();
// //        categoriesYamaha.add(categorieA1);
// //        Modele modeleYamaha = new Modele("Yamaha XSR", marqueYamaha, 75, categoriesYamaha);
// //
// //        Vector<Categorie> categoriesHonda = new Vector<>();
// //        categoriesHonda.add(categorieB);
// //        Modele modeleHonda = new Modele("Honda CB750", marqueHonda, 90, categoriesHonda);
// //
// //        // Ajouter des marques au parc
// //        parc.ajouterMarque(marqueYamaha);
// //        parc.ajouterMarque(marqueHonda);
// //
// //        // Ajouter des modèles 
// //        marqueYamaha.ajouterModele(modeleYamaha);
// //        marqueHonda.ajouterModele(modeleHonda);
// //
// //        // Créer des clients
// //        Vector<Categorie> categoriePermisClient = new Vector<>();
// //        categoriePermisClient.add(categorieA1);
// //        Client client1 = new Client("Dupont", "Jean", LocalDate.of(1990, 1, 1), "1 Rue des Fleurs", categoriePermisClient);
// //
// //        // Ajouter un client au parc
// //        parc.ajouterClient(client1);
// //
// //        // Créer des scooters et les ajouter au parc
// //        Scooter scooter1 = new Scooter(1, modeleYamaha, LocalDate.of(2020, 1, 1), true, 5000, 25.0, parc);
// //        Scooter scooter2 = new Scooter(2, modeleHonda, LocalDate.of(2021, 1, 1), true, 2000, 30.0, parc);
// //
// //        parc.ajouterScooter(scooter1);
// //        parc.ajouterScooter(scooter2);
// //
// //        // Afficher le parc
// //        parc.afficherResumeParc();
// //
// //        // Louer un scooter
// //        LocalDate dateDebut = LocalDate.now();
// //        LocalDate dateRetour = LocalDate.now().plusDays(3);
// //        parc.louerScooter(1, client1.getId(), dateDebut, dateRetour);
// //        
// //
// //        // Afficher le parc après la location
// //        parc.afficherResumeParc();
// //    }
// 	public static void main(String[] args) {
//         // Création d'une instance de Parc
//         Parc parc = new Parc("123 Rue des Scooters");
        
//         // Création et affichage de la fenêtre principale
//         MainFrame frame = new MainFrame(parc);
//         frame.setVisible(true);
// }
// }