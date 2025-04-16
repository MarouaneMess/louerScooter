package controller;

import model.Parc;
import model.Scooter;
import view.ScooterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScooterController {
    private Parc parc;
    private ScooterView view;

    public ScooterController(Parc parc, ScooterView view) {
        this.parc = parc;
        this.view = view;
        initController();
    }

    private void initController() {
        view.getAddButton().addActionListener(e -> addScooter());
        view.getModifyButton().addActionListener(e -> modifyScooter());
        view.getDeleteButton().addActionListener(e -> deleteScooter());
        updateView();
    }

    private void addScooter() {
        String nom = JOptionPane.showInputDialog("Nom du scooter:");
        if (nom != null && !nom.trim().isEmpty()) {
            // Assurez-vous que vous avez un constructeur approprié pour Scooter
            // parc.ajouterScooter(new Scooter(...));
            updateView();
        }
    }

    private void modifyScooter() {
        // Implémentez la logique pour modifier un scooter
        JOptionPane.showMessageDialog(null, "Modification non implémentée.");
    }

    private void deleteScooter() {
        // Implémentez la logique pour supprimer un scooter
        JOptionPane.showMessageDialog(null, "Suppression non implémentée.");
    }

    private void updateView() {
        StringBuilder sb = new StringBuilder();
        for (Scooter scooter : parc.getScooters()) {
            sb.append(scooter.toString()).append("\n");
        }
        view.updateScooterList(sb.toString());
    }
}