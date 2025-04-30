package view;

import model.*;
import controller.*;
import java.time.LocalDate;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        // Initialisation du parc
        Parc parc = new Parc("123 Rue des Scooters");
        
        // Création des catégories
        Categorie catA = new Categorie("A");
        Categorie catB = new Categorie("B");
        
        // Création des marques
        Marque yamaha = new Marque(parc, "Yamaha");
        Marque honda = new Marque(parc, "Honda");
        parc.ajouterMarque(yamaha);
        parc.ajouterMarque(honda);
        
        // Création des modèles
        Vector<Categorie> categoriesYamaha = new Vector<>();
        categoriesYamaha.add(catA);
        Modele nmax = new Modele("NMAX", yamaha, 125, categoriesYamaha);
        yamaha.ajouterModele(nmax);
        
        Vector<Categorie> categoriesHonda = new Vector<>();
        categoriesHonda.add(catB);
        Modele pcx = new Modele("PCX", honda, 160, categoriesHonda);
        honda.ajouterModele(pcx);
        
        // Création des scooters
        Scooter scooter1 = new Scooter(1, nmax, LocalDate.of(2022, 1, 1), true, 0, 25.0, parc);
        Scooter scooter2 = new Scooter(2, pcx, LocalDate.of(2023, 1, 1), true, 0, 30.0, parc);
        parc.ajouterScooter(scooter1);
        parc.ajouterScooter(scooter2);
        
        // Création des clients
        Vector<Categorie> categoriesClient1 = new Vector<>();
        categoriesClient1.add(catA);
        Client client1 = new Client(parc, 12345, "Dupont", "Jean", 
            LocalDate.of(1990, 5, 15), "1 Rue des Clients", categoriesClient1);
        
        Vector<Categorie> categoriesClient2 = new Vector<>();
        categoriesClient2.add(catB);
        Client client2 = new Client(parc, 67890, "Martin", "Marie", 
            LocalDate.of(1985, 8, 20), "2 Avenue des Clients", categoriesClient2);
        
        parc.ajouterClient(client1);
        parc.ajouterClient(client2);
        
        // Création de la vue et du contrôleur
        ParcView parcView = new ParcView(parc);
        ParcController controller = new ParcController(parc, parcView);
        
        // Affichage de la vue
        parcView.setVisible(true);
        
        // Afficher le résumé initial
        parcView.afficherResumeParc();
    }
}