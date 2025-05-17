package utils;

import model.*;
import java.util.Vector;

public class DataManager {
    private static final String CLIENTS_FILE = "src/data/clients.dat";
    private static final String SCOOTERS_FILE = "src/data/scooters.dat";
    private static final String MODELES_FILE = "src/data/modeles.dat";
    private static final String LOCATIONS_FILE = "src/data/locations.dat";
    private static final String MARQUES_FILE = "src/data/marques.dat";
    private static final String RETOURS_FILE = "src/data/retours.dat";

    private Vector<Client> clients;
    private Vector<Scooter> scooters;
    private Vector<Modele> modeles;
    private Vector<Location> locations;
    private Vector<Marque> marques;
    private Vector<Retour> retours;

    public DataManager() {
        // Charger les données depuis les fichiers
        this.clients = FileManager.loadFromFile(CLIENTS_FILE);
        this.scooters = FileManager.loadFromFile(SCOOTERS_FILE);
        this.modeles = FileManager.loadFromFile(MODELES_FILE);
        this.locations = FileManager.loadFromFile(LOCATIONS_FILE);
        this.marques = FileManager.loadFromFile(MARQUES_FILE);
        this.retours = FileManager.loadFromFile(RETOURS_FILE);

        // Relier les entités entre elles
        relierEntites();
    }

    private void relierEntites() {
        // Associer chaque modèle à sa marque
        for (Modele modele : modeles) {
            for (Marque marque : marques) {
                if (modele.getMarque().getNom().equals(marque.getNom())) {
                    marque.ajouterModele(modele);
                }
            }
        }

        // Associer chaque scooter à son modèle
        for (Scooter scooter : scooters) {
            for (Modele modele : modeles) {
                if (scooter.getModele().getNom().equals(modele.getNom())) {
                    modele.ajouterScooter(scooter);
                }
            }
        }

        // Associer chaque location à son client et son scooter
        for (Location location : locations) {
            for (Client client : clients) {
                if (location.getClient().getNumPermis() == client.getNumPermis()) {
                    client.ajouterLocation(location);
                }
            }
            for (Scooter scooter : scooters) {
                if (location.getScooter().getId().equals(scooter.getId())) {
                    scooter.ajouterLocation(location);
                }
            }
        }

        // Associer chaque retour à sa location
        for (Retour retour : retours) {
            for (Location location : locations) {
                if (retour.getLocation().getId() == location.getId()) {
                    location.enregistrerRetour(retour.getKmEffectue(), retour.getDateRetourEffective());
                }
            }
        }
    }

    // Méthode pour sauvegarder toutes les données
    public void sauvegarderDonnees() {
        FileManager.saveToFile(CLIENTS_FILE, clients);
        FileManager.saveToFile(SCOOTERS_FILE, scooters);
        FileManager.saveToFile(MODELES_FILE, modeles);
        FileManager.saveToFile(LOCATIONS_FILE, locations);
        FileManager.saveToFile(MARQUES_FILE, marques);
        FileManager.saveToFile(RETOURS_FILE, retours);
    }

    // Getters pour accéder aux données
    public Vector<Client> getClients() {
        return clients;
    }

    public Vector<Scooter> getScooters() {
        return scooters;
    }

    public Vector<Modele> getModeles() {
        return modeles;
    }

    public Vector<Location> getLocations() {
        return locations;
    }

    public Vector<Marque> getMarques() {
        return marques;
    }
    public Vector<Retour> getRetours() {
        return retours;
    }
}
