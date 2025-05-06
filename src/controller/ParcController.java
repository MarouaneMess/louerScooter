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

import java.awt.event.ActionEvent;

public class ParcController implements ActionListener {
    private ParcView view;
    private Parc parc; 

    public ParcController(ParcView view, Parc parc) { 
        this.view = view;
        this.parc = parc;
        initializeListeners();
    }

    private void initializeListeners() {
        view.getAfficherResumeBtn().addActionListener(this);
        view.getGestionScootersBtn().addActionListener(this);
        view.getGestionClientsBtn().addActionListener(this);
        view.getGestionModelesBtn().addActionListener(this);
        view.getGestionMarquesBtn().addActionListener(this);
        view.getGestionLocationsBtn().addActionListener(this);
        view.getGestionRetoursBtn().addActionListener(this);
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
}
