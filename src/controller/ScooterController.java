package controller;
import view.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.*;
import java.util.*;


public class ScooterController {
    private ScooterView view;
    private Parc parc;

    public ScooterController(ScooterView view, Parc parc) {
        this.view = view;
        this.parc = parc;
        updateScooterList();
    }

    // Nouvelle méthode pour obtenir tous les modèles
    private Vector<Modele> getAllModeles() {
        Vector<Modele> tousLesModeles = new Vector<>();
        for (Marque marque : parc.getMarques()) {
            tousLesModeles.addAll(marque.getModeles());
        }
        return tousLesModeles;
    }

    public void updateScooterList() {
        view.updateScooterTable(parc.getScooters());
    }

    public void showAddDialog() {
        JDialog dialog = new JDialog(view, "Ajouter un Scooter", true);
        dialog.setSize(400, 550);
        dialog.setLocationRelativeTo(view);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel titleLabel = new JLabel("Nouveau Scooter");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Immatriculation
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel immatLabel = new JLabel("Immatriculation :");
        panel.add(immatLabel, gbc);
        
        gbc.gridx = 1;
        JSpinner immatSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999999999, 1));
        panel.add(immatSpinner, gbc);

        // Modèle
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel modeleLabel = new JLabel("Modèle :");
        panel.add(modeleLabel, gbc);
        
        gbc.gridx = 1;
        JComboBox<Modele> modeleCombo = new JComboBox<>(getAllModeles().toArray(new Modele[0]));
        modeleCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Modele) {
                    Modele modele = (Modele) value;
                    setText(modele.getNom() + " (" + modele.getMarque().getNom() + ")");
                }
                return this;
            }
        });
        panel.add(modeleCombo, gbc);

        // Année
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel anneeLabel = new JLabel("Année :");
        panel.add(anneeLabel, gbc);
        
        gbc.gridx = 1;
        JSpinner anneeSpinner = new JSpinner(new SpinnerNumberModel(2025, 2000, 2025, 1));
        panel.add(anneeSpinner, gbc);

        // Kilométrage
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel kmLabel = new JLabel("Kilométrage :");
        panel.add(kmLabel, gbc);
        
        gbc.gridx = 1;
        JSpinner kmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100000, 100));
        panel.add(kmSpinner, gbc);

        // Prix par jour
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel prixLabel = new JLabel("Prix/Jour (€) :");
        panel.add(prixLabel, gbc);
        
        gbc.gridx = 1;
        JSpinner prixSpinner = new JSpinner(new SpinnerNumberModel(25, 0, 1000, 5));
        panel.add(prixSpinner, gbc);

        // Disponibilité
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel dispoLabel = new JLabel("Disponible :");
        panel.add(dispoLabel, gbc);
        
        gbc.gridx = 1;
        JCheckBox dispoCheck = new JCheckBox();
        dispoCheck.setSelected(true);
        panel.add(dispoCheck, gbc);

        // Boutons
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);

        // Ajout du panel au dialog
        dialog.add(panel);

        // Action du bouton Enregistrer
        saveButton.addActionListener(e -> {
            try {
                int immatriculation = (Integer) immatSpinner.getValue();

                // Vérifier si l'immatriculation existe déjà
                if (parc.rechercherScooter(immatriculation) != null) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Cette immatriculation existe déjà. Veuillez en saisir une autre.", 
                        "Erreur", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Modele modele = (Modele) modeleCombo.getSelectedItem();
                LocalDate annee = LocalDate.of((Integer) anneeSpinner.getValue(), 1, 1);
                int km = (Integer) kmSpinner.getValue();
                int prix = (Integer) prixSpinner.getValue();
                boolean disponible = dispoCheck.isSelected();

                Scooter scooter = new Scooter(immatriculation, modele, annee, disponible, km, prix, parc);
                parc.ajouterScooter(scooter);
                updateScooterList();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Erreur lors de l'ajout du scooter: " + ex.getMessage(), 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    public void showEditDialog() {
        int selectedId = view.getSelectedScooterId();
        if (selectedId == -1) return;

        Scooter scooter = parc.rechercherScooter(selectedId);
        if (scooter == null) {
            JOptionPane.showMessageDialog(view, 
                "Scooter non trouvé", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(view, "Modifier un Scooter", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(view);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Champs du formulaire pré-remplis
        JTextField idField = new JTextField(String.valueOf(scooter.getId()));
        idField.setEditable(false); // L'ID ne peut pas être modifié
        JTextField modeleField = new JTextField(String.valueOf(scooter.getModele().getNom()));
        modeleField.setEditable(false); // Le modele ne peut pas être modifié
        JTextField anneeField = new JTextField(String.valueOf(scooter.getAnneeSortie()));
        anneeField.setEditable(false);
        
        JTextField kmField = new JTextField(String.valueOf(scooter.getKm()));
        JTextField prixField = new JTextField(String.valueOf(scooter.getPrixJour()));
        JCheckBox disponibleCheck = new JCheckBox("Disponible", scooter.isDisponible());

        // Ajout des composants
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Modèle:"), gbc);
        gbc.gridx = 1;
        panel.add(modeleField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Année:"), gbc);
        gbc.gridx = 1;
        panel.add(anneeField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Kilométrage:"), gbc);
        gbc.gridx = 1;
        panel.add(kmField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Prix/Jour:"), gbc);
        gbc.gridx = 1;
        panel.add(prixField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Disponible:"), gbc);
        gbc.gridx = 1;
        panel.add(disponibleCheck, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        saveButton.addActionListener(e -> {
            try {
                double km = Double.parseDouble(kmField.getText());
                double prix = Double.parseDouble(prixField.getText());
                boolean disponible = disponibleCheck.isSelected();

                scooter.setKm(km);
                scooter.setPrixJour(prix);
                scooter.setDisponible(disponible);

                updateScooterList();
                dialog.dispose();
                JOptionPane.showMessageDialog(dialog, 
                    "Scooter modifié avec succès", 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Erreur: " + ex.getMessage(), 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    public void deleteScooter() {
        int selectedId = view.getSelectedScooterId();
        if (selectedId == -1) return;

        int confirm = JOptionPane.showConfirmDialog(view,
            "Êtes-vous sûr de vouloir supprimer ce scooter ?",
            "Confirmation de suppression",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                parc.supprimerScooter(selectedId);
                updateScooterList();
                JOptionPane.showMessageDialog(view,
                    "Scooter supprimé avec succès",
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

    public void searchScooters(String searchText) {
        if (searchText.trim().isEmpty()) {
            updateScooterList();
            return;
        }

        Vector<Scooter> filteredScooters = new Vector<>();
        for (Scooter scooter : parc.getScooters()) {
            if (String.valueOf(scooter.getId()).contains(searchText) ||
                scooter.getModele().getNom().toLowerCase().contains(searchText.toLowerCase())) {
                filteredScooters.add(scooter);
            }
        }
        view.updateScooterTable(filteredScooters);
    }
}