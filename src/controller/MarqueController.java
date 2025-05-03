package controller;

import view.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;

public class MarqueController {
    private MarqueView view;
    private Parc parc;

    public MarqueController(MarqueView view, Parc parc) {
        this.view = view;
        this.parc = parc;
        updateMarqueList();
    }

    //   Met à jour l'affichage des marques dans la table
    public void updateMarqueList() {
        view.updateMarqueTable(parc.getMarques());
    }

    //   Affiche la boîte de dialogue pour ajouter une marque
    public void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(view), "Ajouter une Marque", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(view);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel titleLabel = new JLabel("Nouvelle Marque");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Nom
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel nomLabel = new JLabel("Nom :");
        panel.add(nomLabel, gbc);
        
        gbc.gridx = 1;
        JTextField nomField = new JTextField(20);
        panel.add(nomField, gbc);

        // Boutons
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);

        dialog.add(panel);

        //   Action du bouton Enregistrer
        saveButton.addActionListener(e -> {
            try {
                String nom = nomField.getText().trim();
                if (nom.isEmpty()) {
                    throw new Exception("Le nom ne peut pas être vide");
                }

                // Vérifier si la marque existe déjà
                if (parc.rechercherMarque(nom) != null) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Cette marque existe déjà", 
                        "Erreur", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Marque marque = new Marque(parc, nom);
                parc.ajouterMarque(marque);
                updateMarqueList();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Erreur lors de l'ajout de la marque: " + ex.getMessage(), 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    //   Affiche la boîte de dialogue pour modifier une marque
    public void showEditDialog() {
        String selectedNom = view.getSelectedMarqueNom();
        if (selectedNom == null) return;

        Marque marque = parc.rechercherMarque(selectedNom);
        if (marque == null) {
            JOptionPane.showMessageDialog(view, 
                "Marque non trouvée", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(view), "Modifier une Marque", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(view);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel titleLabel = new JLabel("Modifier la Marque");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Nom
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel nomLabel = new JLabel("Nom :");
        panel.add(nomLabel, gbc);
        
        gbc.gridx = 1;
        JTextField nomField = new JTextField(marque.getNom(), 20);
        nomField.setEditable(false);  // Le nom ne peut pas être modifié
        panel.add(nomField, gbc);

        // Boutons
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton closeButton = new JButton("Fermer");
        buttonPanel.add(closeButton);
        panel.add(buttonPanel, gbc);

        closeButton.addActionListener(e -> dialog.dispose());

        dialog.add(panel);
        dialog.setVisible(true);
    }

    //   Supprime la marque sélectionnée
    public void deleteMarque() {
        String selectedNom = view.getSelectedMarqueNom();
        if (selectedNom == null) return;

        int confirm = JOptionPane.showConfirmDialog(view,
            "Êtes-vous sûr de vouloir supprimer cette marque ?",
            "Confirmation de suppression",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                parc.supprimerMarque(selectedNom);
                updateMarqueList();
                JOptionPane.showMessageDialog(view,
                    "Marque supprimée avec succès",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,
                    "Erreur lors de la suppression: " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //   Recherche des marques
    public void searchMarques(String searchText) {
        if (searchText.trim().isEmpty()) {
            updateMarqueList();
            return;
        }

        Vector<Marque> filteredMarques = new Vector<>();
        for (Marque marque : parc.getMarques()) {
            if (marque.getNom().toLowerCase().contains(searchText.toLowerCase())) {
                filteredMarques.add(marque);
            }
        }
        view.updateMarqueTable(filteredMarques);
    }
} 