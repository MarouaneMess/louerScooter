package controller;

import model.Parc;
import model.Location;
import view.LocationView;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LocationController {
    private Parc parc;
    private LocationView view;

    public LocationController(Parc parc, LocationView view) {
        this.parc = parc;
        this.view = view;
        initController();
    }

    private void initController() {
        view.getAddButton().addActionListener(e -> addLocation());
        view.getModifyButton().addActionListener(e -> modifyLocation());
        view.getDeleteButton().addActionListener(e -> deleteLocation());
        updateView();
    }

    private void addLocation() {
        // Implémentez la logique pour ajouter une location
        JOptionPane.showMessageDialog(null, "Ajout de location non implémenté.");
        updateView();
    }

    private void modifyLocation() {
        // Implémentez la logique pour modifier une location
        JOptionPane.showMessageDialog(null, "Modification de location non implémentée.");
    }

    private void deleteLocation() {
        // Implémentez la logique pour supprimer une location
        JOptionPane.showMessageDialog(null, "Suppression de location non implémentée.");
    }

    private void updateView() {
        // Mettez à jour la liste des locations
        view.updateLocationList(new Vector<>(parc.getLocations())); // Assurez-vous que Parc a une méthode getLocations()
    }
}