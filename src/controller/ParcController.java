package controller;

import view.ClientView;
import view.LocationView;
import view.MarqueView;
import view.ModeleView;
import view.ParcView;
import view.RetourView;
import view.ScooterView;
import java.awt.event.ActionListener;

import model.Parc;
import utils.DataManager;

import java.awt.event.ActionEvent;

public class ParcController implements ActionListener {
    private ParcView view;
    private Parc parc; 
    // Initialiser le gestionnaire de données
    DataManager dataManager = new DataManager();

    public ParcController(ParcView view, Parc parc) { 
        this.view = view;
        this.parc = parc;

        // Charger les données dans le parc
        parc.setClients(dataManager.getClients());
        parc.setScooters(dataManager.getScooters());
        parc.setMarques(dataManager.getMarques());
        initializeListeners();
        updateResumeData();

    }

    private void initializeListeners() {
        view.getAfficherResumeBtn().addActionListener(this);
        view.getGestionScootersBtn().addActionListener(this);
        view.getGestionClientsBtn().addActionListener(this);
        view.getGestionModelesBtn().addActionListener(this);
        view.getGestionMarquesBtn().addActionListener(this);
        view.getGestionLocationsBtn().addActionListener(this);
        view.getGestionRetoursBtn().addActionListener(this);
        view.getExitBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getAfficherResumeBtn()) {
            afficherResume();
        } else if (e.getSource() == view.getGestionScootersBtn()) {
            ouvrirGestionScooters();
        } else if (e.getSource() == view.getGestionClientsBtn()) {
            ouvrirGestionClients();
        } else if (e.getSource() == view.getGestionModelesBtn()) {
            ouvrirGestionModeles();
        } else if (e.getSource() == view.getGestionMarquesBtn()) {
            ouvrirGestionMarques();
        } else if (e.getSource() == view.getGestionLocationsBtn()) {
            ouvrirGestionLocations();
        } else if (e.getSource() == view.getGestionRetoursBtn()) {
            ouvrirGestionRetours();
        } else if (e.getSource() == view.getExitBtn()) {
            quitterProgramme();
        }
    }

    // Méthode pour afficher le résumé
    private void afficherResume() {
        updateResumeData();
    }

    // Méthode pour ouvrir la gestion des scooters
    private void ouvrirGestionScooters() {
        ScooterView scooterView = new ScooterView();
        new ScooterController(scooterView, parc);
        scooterView.setVisible(true);
    }

    // Méthode pour ouvrir la gestion des clients
    private void ouvrirGestionClients() {
        ClientView clientView = new ClientView();
        new ClientController(clientView, parc);
        clientView.setVisible(true);
    }

    // Méthode pour ouvrir la gestion des modèles
    private void ouvrirGestionModeles() {
        ModeleView modeleView = new ModeleView();
        new ModeleController(modeleView, parc);
        modeleView.setVisible(true);
    }

    // Méthode pour ouvrir la gestion des marques
    private void ouvrirGestionMarques() {
       MarqueView marqueView = new MarqueView();
       new MarqueController(marqueView,parc);
       marqueView.setVisible(true);
    }

    // Méthode pour ouvrir la gestion des locations
    private void ouvrirGestionLocations() {
       LocationView locationView = new LocationView();
       new LocationController(locationView, parc );
       locationView.setVisible(true);
    }

    // Méthode pour ouvrir la gestion des retours
    private void ouvrirGestionRetours() {
         RetourView retourView = new RetourView();
         new RetourController(retourView, parc);
         retourView.setVisible(true);
    }

    // Méthode pour mettre à jour les données du résumé
    public void updateResumeData() {
        Object[] resumeData = parc.getResumeParc();

        int totalScooters = (int) resumeData[0];
        int scootersDisponibles = (int) resumeData[1];
        int scootersEnLocation = (int) resumeData[2];
        int nombreClients = (int) resumeData[3];
        double kmMoyen = (double) resumeData[4];
        // int mdl = (int) resumeData[5];
        // System.out.println((int) resumeData[5]);

        // view.updateResume(totalScooters, scootersDisponibles, scootersEnLocation, nombreClients, kmMoyen,mdl);
        view.updateResume(totalScooters, scootersDisponibles, scootersEnLocation, nombreClients, kmMoyen);
    }

    private void quitterProgramme() {
    // Sauvegarder les données avant de quitter
    dataManager.sauvegarderDonnees();
    System.out.println("Données sauvegardées avec succès !");
    
    // Quitter le programme
    System.exit(0);
}
}
