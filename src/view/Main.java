package view;

import controller.*;
import model.*;

import java.time.LocalDate;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        // Création du parc
        Parc parc = new Parc("123 Rue des Scooters");

        // Ajout de marques
        Marque yamaha = new Marque(parc, "Yamaha");
        Marque honda = new Marque(parc, "Honda");

        // Création des catégories
        Categorie categorieA1 = new Categorie("A1");
        Categorie categorieA2 = new Categorie("A2");

        // Ajout des catégories aux modèles
        Vector<Categorie> categoriesYamahaXmax = new Vector<>();
        categoriesYamahaXmax.add(categorieA1);
        categoriesYamahaXmax.add(categorieA2);

        Vector<Categorie> categoriesHondaPcx = new Vector<>();
        categoriesHondaPcx.add(categorieA2);

        // Création des modèles avec catégories
        Modele modele1 = new Modele("XMAX", yamaha, 125, categoriesYamahaXmax);
        Modele modele2 = new Modele("PCX", honda, 150, categoriesHondaPcx);
        Modele modele3 = new Modele("Mrwn", honda, 100, categoriesHondaPcx);
        // Ajout des modèles aux marques
        yamaha.ajouterModele(modele1);
        honda.ajouterModele(modele2);
        honda.ajouterModele(modele3);

        // Ajout des marques au parc
        parc.ajouterMarque(yamaha);
        parc.ajouterMarque(honda);

        // Ajout de scooters fictifs
        Scooter scooter1 =new Scooter("123ABC", modele1, LocalDate.of(2022, 1, 1), true, 5000, 25, parc);
        Scooter scooter2 =new Scooter("456DEF", modele2, LocalDate.of(2021, 1, 1), false, 10000, 30, parc);
        Scooter scooter3 =new Scooter("1111", modele2, LocalDate.of(2023, 1, 1), true, 10000, 300, parc);
        parc.ajouterScooter(scooter1);
        parc.ajouterScooter(scooter2);
        parc.ajouterScooter(scooter3);

        Vector<Categorie> categoriesClient1 = new Vector<>();
        categoriesClient1.add(categorieA1);
        
        Vector<Categorie> categoriesClient2 = new Vector<>();
        categoriesClient2.add(categorieA2);
        
        Vector<Categorie> categoriesClient3 = new Vector<>();
        categoriesClient3.add(categorieA1);
        categoriesClient3.add(categorieA2);
        
        // Ajout de clients fictifs
        Client client1 = new Client(parc, 12345, "John", "Doe", LocalDate.of(1990, 5, 15), "123 Rue A", categoriesClient1);
        Client client2 = new Client(parc, 67890, "Jane", "Smith", LocalDate.of(1985, 8, 20), "456 Rue B", categoriesClient2);
        Client client3 = new Client(parc, 11223, "Alice", "Brown", LocalDate.of(2000, 3, 10), "789 Rue C", categoriesClient3);
        
        // Ajout des clients au parc
        parc.ajouterClient(client1);
        parc.ajouterClient(client2);
        parc.ajouterClient(client3);

        // Création de la vue
        ParcView parcView = new ParcView(parc, 800, 400);

        // Création du contrôleur
        ParcController controller = new ParcController(parcView, parc);
        
        // Affichage de la vue
        parcView.setVisible(true);

        
        // Mise à jour initiale des données
        controller.updateResumeData();
    }
}