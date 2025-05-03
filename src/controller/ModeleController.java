package controller;

import view.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class ModeleController {
    private ModeleView view;
    private Parc parc;

    public ModeleController(ModeleView view, Parc parc) {
        this.view = view;
        this.parc = parc;
        updateModeleList();
    }

    public void updateModeleList() {
        Vector<Modele> tousLesModeles = new Vector<>();
        for (Marque marque : parc.getMarques()) {
            tousLesModeles.addAll(marque.getModeles());
        }
        view.updateModeleTable(tousLesModeles);
    }

    public void showAddDialog() {
        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(view), "Ajouter un Modèle", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(view);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel titleLabel = new JLabel("Nouveau Modèle");
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

        // Marque
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel marqueLabel = new JLabel("Marque :");
        panel.add(marqueLabel, gbc);
        
        gbc.gridx = 1;
        JComboBox<Marque> marqueCombo = new JComboBox<>(parc.getMarques().toArray(new Marque[0]));
        marqueCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Marque) {
                    setText(((Marque) value).getNom());
                }
                return this;
            }
        });
        panel.add(marqueCombo, gbc);

        // Puissance
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel puissanceLabel = new JLabel("Puissance (CV) :");
        panel.add(puissanceLabel, gbc);
        
        gbc.gridx = 1;
        JSpinner puissanceSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        panel.add(puissanceSpinner, gbc);

        // Catégories
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel categoriesLabel = new JLabel("Catégories :");
        panel.add(categoriesLabel, gbc);
        
        gbc.gridx = 1;
        // Récupérer toutes les catégories uniques à partir des modèles existants
        Vector<Categorie> categories = new Vector<>();
        for (Marque marque : parc.getMarques()) {
            for (Modele modele : marque.getModeles()) {
                for (Categorie categorie : modele.getCategories()) {
                    if (!categories.contains(categorie)) {
                        categories.add(categorie);
                    }
                }
            }
        }
        JComboBox<Categorie> categoriesCombo = new JComboBox<>(categories.toArray(new Categorie[0]));
        categoriesCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Categorie) {
                    setText(((Categorie) value).getCategorie());
                }
                return this;
            }
        });
        panel.add(categoriesCombo, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        saveButton.addActionListener(e -> {
            try {
                String nom = nomField.getText().trim();
                if (nom.isEmpty()) {
                    throw new Exception("Le nom est obligatoire");
                }

                Marque marque = (Marque) marqueCombo.getSelectedItem();
                int puissance = (Integer) puissanceSpinner.getValue();
                Categorie selectedCategorie = (Categorie) categoriesCombo.getSelectedItem();
                Vector<Categorie> selectedCategories = new Vector<>();
                selectedCategories.add(selectedCategorie);

                if (selectedCategories.isEmpty()) {
                    throw new Exception("Veuillez sélectionner au moins une catégorie");
                }

                Modele nouveauModele = new Modele(nom, marque, puissance, selectedCategories);
                marque.ajouterModele(nouveauModele);
                
                updateModeleList();
                dialog.dispose();
                
                JOptionPane.showMessageDialog(dialog,
                    "Modèle ajouté avec succès",
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

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    public void showEditDialog() {
        String selectedNom = view.getSelectedModeleNom();
        if (selectedNom == null) return;

        Modele[] modeleRef = new Modele[1];
        for (Marque marque : parc.getMarques()) {
            for (Modele m : marque.getModeles()) {
                if (m.getNom().equals(selectedNom)) {
                    modeleRef[0] = m;
                    break;
                }
            }
            if (modeleRef[0] != null) break;
        }

        if (modeleRef[0] == null) {
            JOptionPane.showMessageDialog(view,
                "Modèle non trouvé",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(view), "Modifier un Modèle", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(view);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel titleLabel = new JLabel("Modifier Modèle");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Nom (en lecture seule)
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel nomLabel = new JLabel("Nom :");
        panel.add(nomLabel, gbc);
        
        gbc.gridx = 1;
        JTextField nomField = new JTextField(modeleRef[0].getNom(), 20);
        nomField.setEditable(false);  // Rendu non modifiable
        panel.add(nomField, gbc);

        // Marque (en lecture seule)
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel marqueLabel = new JLabel("Marque :");
        panel.add(marqueLabel, gbc);
        
        gbc.gridx = 1;
        JComboBox<Marque> marqueCombo = new JComboBox<>(parc.getMarques().toArray(new Marque[0]));
        marqueCombo.setSelectedItem(modeleRef[0].getMarque());
        marqueCombo.setEnabled(false);  // Rendu non modifiable
        marqueCombo.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Marque) {
                    setText(((Marque) value).getNom());
                }
                return this;
            }
        });
        panel.add(marqueCombo, gbc);

        // Puissance
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel puissanceLabel = new JLabel("Puissance (CV) :");
        panel.add(puissanceLabel, gbc);
        
        gbc.gridx = 1;
        JSpinner puissanceSpinner = new JSpinner(new SpinnerNumberModel(modeleRef[0].getPuissance(), 1, 1000, 1));
        panel.add(puissanceSpinner, gbc);

        // Catégories
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel categoriesLabel = new JLabel("Catégories :");
        panel.add(categoriesLabel, gbc);
        
        gbc.gridx = 1;
        // Récupérer toutes les catégories uniques à partir des modèles existants
        Vector<Categorie> categories = new Vector<>();
        for (Marque marque : parc.getMarques()) {
            for (Modele m : marque.getModeles()) {
                for (Categorie categorie : m.getCategories()) {
                    if (!categories.contains(categorie)) {
                        categories.add(categorie);
                    }
                }
            }
        }
        JComboBox<Categorie> categoriesCombo = new JComboBox<>(categories.toArray(new Categorie[0]));
        categoriesCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Categorie) {
                    setText(((Categorie) value).getCategorie());
                }
                return this;
            }
        });
        panel.add(categoriesCombo, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        saveButton.addActionListener(e -> {
            try {
                int puissance = (Integer) puissanceSpinner.getValue();
                Categorie selectedCategorie = (Categorie) categoriesCombo.getSelectedItem();
                Vector<Categorie> selectedCategories = new Vector<>();
                selectedCategories.add(selectedCategorie);

                if (selectedCategories.isEmpty()) {
                    throw new Exception("Veuillez sélectionner au moins une catégorie");
                }

                modeleRef[0].setPuissance(puissance);
                modeleRef[0].setCategories(selectedCategories);
                
                updateModeleList();
                dialog.dispose();
                
                JOptionPane.showMessageDialog(dialog,
                    "Modèle modifié avec succès",
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

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    public void deleteModele() {
        String selectedNom = view.getSelectedModeleNom();
        if (selectedNom == null) return;

        int confirm = JOptionPane.showConfirmDialog(
            SwingUtilities.getWindowAncestor(view),
            "Êtes-vous sûr de vouloir supprimer ce modèle ?",
            "Confirmation de suppression",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                for (Marque marque : parc.getMarques()) {
                    for (Modele modele : marque.getModeles()) {
                        if (modele.getNom().equals(selectedNom)) {
                            marque.retirerModele(modele);
                            updateModeleList();
                            return;
                        }
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,
                    "Erreur lors de la suppression: " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void searchModeles(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            updateModeleList();
            return;
        }

        Vector<Modele> filteredModeles = new Vector<>();
        for (Marque marque : parc.getMarques()) {
            for (Modele modele : marque.getModeles()) {
                if (modele.getNom().toLowerCase().contains(searchText.toLowerCase()) ||
                    modele.getMarque().getNom().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredModeles.add(modele);
                }
            }
        }
        view.updateModeleTable(filteredModeles);
    }
}