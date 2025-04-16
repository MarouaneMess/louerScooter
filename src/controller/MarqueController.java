package controller;

import model.Parc;
import model.Marque;
import view.MarqueView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarqueController {
    private Parc parc;
    private MarqueView view;

    public MarqueController(Parc parc, MarqueView view) {
        this.parc = parc;
        this.view = view;
        initController();
    }

    private void initController() {
        view.getAddButton().addActionListener(e -> addMarque());
        view.getModifyButton().addActionListener(e -> modifyMarque());
        view.getDeleteButton().addActionListener(e -> deleteMarque());
        updateView();
    }

    private void addMarque() {
        String nom = JOptionPane.showInputDialog("Nom de la Marque:");
        if (nom != null && !nom.trim().isEmpty()) {
            parc.ajouterMarque(new Marque(nom));
            updateView();
        }
    }

    private void modifyMarque() {
        // Implémentez la logique pour modifier une marque
        JOptionPane.showMessageDialog(null, "Modification non implémentée.");
    }

    private void deleteMarque() {
        // Implémentez la logique pour supprimer une marque
        JOptionPane.showMessageDialog(null, "Suppression non implémentée.");
    }

    private void updateView() {
        StringBuilder sb = new StringBuilder();
        for (Marque marque : parc.getMarques()) {
            sb.append(marque.toString()).append("\n");
        }
        view.updateMarqueList(sb.toString());
    }
}