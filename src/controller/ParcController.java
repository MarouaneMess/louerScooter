package controller;
import model.*;
import view.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParcController {
    private Parc model;
    private ParcView view;

    public ParcController(Parc model, ParcView view) {
        this.model = model;
        this.view = view;

        // Ajout des listeners pour les boutons
        view.getResumeButton().addActionListener(e -> updateResume());
        view.getScootersButton().addActionListener(e -> afficherScooters());
        view.getClientsButton().addActionListener(e -> afficherClients());
        view.getModelesButton().addActionListener(e -> afficherModeles());
        view.getMarquesButton().addActionListener(e -> afficherMarques());
        // view.getLocationsButton().addActionListener(e -> afficherLocations());
        // view.getRetoursButton().addActionListener(e -> afficherRetours());
    }

    // Méthodes pour gérer les actions de l'utilisateur
    public void updateResume() {
        view.afficherResumeParc();
    }

    public void afficherScooters() {
        view.afficherScootersView();
    }

    public void afficherClients() {
        view.afficherClientsView();
    }

    public void afficherModeles() {
        view.afficherModelesView();
    }

    public void afficherMarques() {
        view.afficherMarquesView();
    }

    // public void afficherLocations() {
    //     view.afficherLocationsView();
    // }

    // public void afficherRetours() {
    //     view.afficherRetoursView();
    // }

    // Ajoutez d'autres méthodes pour gérer les interactions
}