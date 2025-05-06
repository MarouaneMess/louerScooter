package controller;

import view.ScooterView;
import model.*;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import java.time.LocalDate;

public class ScooterController implements ActionListener {
    private ScooterView view;
    private Parc parc;

    public ScooterController(ScooterView view, Parc parc) {
        this.view = view;
        this.parc = parc;

        // Attacher les écouteurs aux boutons
        view.getMettreAJourButton().addActionListener(this);
        view.getAddButton().addActionListener(this);
        view.getEditButton().addActionListener(this);
        view.getDeleteButton().addActionListener(this);
        view.getSearchButton().addActionListener(this);

        // Charger les données initiales
        updateScooterTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == view.getMettreAJourButton()) {
            updateScooterTable();
        } else if (e.getSource() == view.getAddButton()) {
            ajouterScooter();
        } else if (e.getSource() == view.getEditButton()) {
            modifierScooter();
        } else if (e.getSource() == view.getDeleteButton()) {
            supprimerScooter();
        } else if (e.getSource() == view.getSearchButton()) {
            rechercherScooter();
        }
    }

    private void updateScooterTable() {
        String[] columns = {"ID", "Modèle", "Année", "Kilométrage", "Prix/Jour", "Disponible"};
        Vector<Scooter> scooters = parc.getScooters();
        String[][] data = new String[scooters.size()][columns.length];

        for (int i = 0; i < scooters.size(); i++) {
            Scooter scooter = scooters.get(i);
            data[i][0] = scooter.getId();
            data[i][1] = scooter.getModele().getNom();
            data[i][2] = String.valueOf(scooter.getAnneeSortie()); // Correction ici
            data[i][3] = String.format("%.2f km", scooter.getKm());
            data[i][4] = String.format("%.2f €", scooter.getPrixJour());
            data[i][5] = scooter.isDisponible() ? "Oui" : "Non";
        }

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêcher l'édition des cellules
            }
        };

        view.getScooterTable().setModel(model);
    }

    private void ajouterScooter() {
        JTextField idField = new JTextField();
        JComboBox<String> modeleComboBox = new JComboBox<>();
        JSpinner anneeSpinner = new JSpinner(new SpinnerNumberModel(2023, 1900, 2025, 1)); // Année limitée à 2025
        JTextField kmField = new JTextField();
        JTextField prixField = new JTextField();
        JCheckBox disponibleCheckBox = new JCheckBox("Disponible", true); // Case cochée par défaut

        // Remplir le JComboBox avec les noms des modèles disponibles
        for (Marque marque : parc.getMarques()) {
            for (Modele modele : marque.getModeles()) {
                modeleComboBox.addItem(modele.getNom());
            }
        }

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("ID (Immatriculation) :"));
        panel.add(idField);
        panel.add(new JLabel("Modèle :"));
        panel.add(modeleComboBox);
        panel.add(new JLabel("Année :"));
        panel.add(anneeSpinner);
        panel.add(new JLabel("Kilométrage :"));
        panel.add(kmField);
        panel.add(new JLabel("Prix/Jour :"));
        panel.add(prixField);
        panel.add(new JLabel("Disponible :"));
        panel.add(disponibleCheckBox);

        int result = JOptionPane.showConfirmDialog(view, panel, "Ajouter un Scooter", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String id = idField.getText();
                String modeleNom = (String) modeleComboBox.getSelectedItem();
                int annee = (int) anneeSpinner.getValue();
                double km = Double.parseDouble(kmField.getText());
                double prix = Double.parseDouble(prixField.getText());
                boolean disponible = disponibleCheckBox.isSelected();

                // Trouver le modèle correspondant au nom sélectionné
                Modele modele = null;
                for (Marque marque : parc.getMarques()) {
                    for (Modele m : marque.getModeles()) {
                        if (m.getNom().equals(modeleNom)) {
                            modele = m;
                            break;
                        }
                    }
                    if (modele != null) break;
                }

                if (modele == null) {
                    throw new Exception("Modèle introuvable !");
                }

                // Convertir l'année en LocalDate
                LocalDate anneeSortie = LocalDate.of(annee, 1, 1);

                // Ajouter le scooter
                Scooter scooter = new Scooter(id, modele, anneeSortie, disponible, km, prix, parc);
                parc.ajouterScooter(scooter);

                updateScooterTable();
                JOptionPane.showMessageDialog(view, "Scooter ajouté avec succès !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierScooter() {
        int selectedRow = view.getScooterTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un scooter à modifier.");
            return;
        }

        // Récupérer le scooter sélectionné
        Scooter scooter = parc.getScooters().get(selectedRow);

        // Préparer les champs pour la modification
        JTextField idField = new JTextField(scooter.getId());
        idField.setEditable(false); // L'ID ne peut pas être modifié
        JTextField modeleField = new JTextField(scooter.getModele().getNom());
        modeleField.setEditable(false); // Le modèle ne peut pas être modifié
        JSpinner anneeSpinner = new JSpinner(new SpinnerNumberModel(scooter.getAnneeSortie(), 1900, 2025, 1));
        anneeSpinner.setEnabled(false); // L'année ne peut pas être modifiée
        JTextField kmField = new JTextField(String.valueOf(scooter.getKm()));
        JTextField prixField = new JTextField(String.valueOf(scooter.getPrixJour()));
        JCheckBox disponibleCheckBox = new JCheckBox("Disponible", scooter.isDisponible());

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("ID (Immatriculation) :"));
        panel.add(idField);
        panel.add(new JLabel("Modèle :"));
        panel.add(modeleField);
        panel.add(new JLabel("Année :"));
        panel.add(anneeSpinner);
        panel.add(new JLabel("Kilométrage :"));
        panel.add(kmField);
        panel.add(new JLabel("Prix/Jour :"));
        panel.add(prixField);
        panel.add(new JLabel("Disponible :"));
        panel.add(disponibleCheckBox);

        int result = JOptionPane.showConfirmDialog(view, panel, "Modifier un Scooter", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                double km = Double.parseDouble(kmField.getText());
                double prix = Double.parseDouble(prixField.getText());
                boolean disponible = disponibleCheckBox.isSelected();

                // Mettre à jour les informations du scooter
                scooter.setKm(km);
                scooter.setPrixJour(prix);
                scooter.setDisponible(disponible);

                updateScooterTable();
                JOptionPane.showMessageDialog(view, "Scooter modifié avec succès !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerScooter() {
        int selectedRow = view.getScooterTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un scooter à supprimer.");
            return;
        }

        // Récupérer le scooter sélectionné
        Scooter scooter = parc.getScooters().get(selectedRow);

        // Demander confirmation avant de supprimer
        int confirm = JOptionPane.showConfirmDialog(view,
                "Êtes-vous sûr de vouloir supprimer le scooter \"" + scooter.getId() + "\" ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Supprimer le scooter du parc
                parc.supprimerScooter(scooter);

                // Mettre à jour la table des scooters
                updateScooterTable();

                JOptionPane.showMessageDialog(view, "Scooter supprimé avec succès !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erreur lors de la suppression : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void rechercherScooter() {
        String searchText = view.getSearchField().getText().toLowerCase().trim();

        if (searchText.isEmpty()) {
           updateScooterTable();
            return;
        }

        // Définir les colonnes de la table
        String[] columns = {"ID", "Modèle", "Année", "Kilométrage", "Prix/Jour", "Disponible"};
        Vector<Scooter> scooters = parc.getScooters();
        Vector<Scooter> resultats = new Vector<>();

        // Filtrer les scooters en fonction du texte saisi
        for (Scooter scooter : scooters) {
            if (scooter.getId().toLowerCase().equals(searchText) ||
                scooter.getModele().getNom().toLowerCase().equals(searchText)) {
                resultats.add(scooter);
            }
        }

        if (resultats.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Aucun scooter trouvé pour la recherche : " + searchText);
            return;
        }

        // Remplir les données pour les résultats filtrés
        String[][] data = new String[resultats.size()][columns.length];
        for (int i = 0; i < resultats.size(); i++) {
            Scooter scooter = resultats.get(i);
            data[i][0] = scooter.getId();
            data[i][1] = scooter.getModele().getNom();
            data[i][2] = String.valueOf(scooter.getAnneeSortie());
            data[i][3] = String.format("%.2f km", scooter.getKm());
            data[i][4] = String.format("%.2f €", scooter.getPrixJour());
            data[i][5] = scooter.isDisponible() ? "Oui" : "Non";
        }

        // Mettre à jour la table avec les résultats de la recherche
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        view.getScooterTable().setModel(model);
    }
}