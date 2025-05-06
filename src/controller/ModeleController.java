package controller;

import model.Categorie;
import model.Marque;
import model.Modele;
import model.Parc;
import model.Scooter;
import view.ModeleView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.GridLayout;

public class ModeleController implements ActionListener {
    private ModeleView view;
    private Parc parc;

    public ModeleController(ModeleView view, Parc parc) {
        this.view = view;
        this.parc = parc;

        // Attacher les écouteurs aux boutons
        view.getMettreAJourButton().addActionListener(this);
        view.getAddButton().addActionListener(this);
        view.getEditButton().addActionListener(this);
        view.getDeleteButton().addActionListener(this);
        view.getSearchButton().addActionListener(this);

        // Charger les données initiales
        updateModeleTable();
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getMettreAJourButton()) {
            updateModeleTable();
        } else if (e.getSource() == view.getAddButton()) {
            ajouterModele();
        } else if (e.getSource() == view.getEditButton()) {
            modifierModele();
        } else if (e.getSource() == view.getDeleteButton()) {
            supprimerModele();
        } else if (e.getSource() == view.getSearchButton()) {
            rechercherModele();
        }
    }

    private void updateModeleTable() {
        String[] columns = {"Nom", "Marque", "Puissance", "Catégories"};
        Vector<String[]> data = new Vector<>();

        // Parcourir les marques pour récupérer les modèles
        for (Marque marque : parc.getMarques()) {
            for (Modele modele : marque.getModeles()) {
                String[] row = new String[columns.length];
                row[0] = modele.getNom();
                row[1] = marque.getNom();
                row[2] = String.valueOf(modele.getPuissance())+" Chevaux";
                Vector <String>catgrs= new Vector<String>();
                // boucle pour recuperer les categories 
                for (Categorie cat : modele.getCategories()){
                    catgrs.add(cat.getCategorie());
                }
                row[3] = String.valueOf(catgrs); 
                data.add(row);
            }
        }

        // Convertir data en tableau 
        String[][] tableData = data.toArray(new String[0][]);

        DefaultTableModel model = new DefaultTableModel(tableData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        view.getModeleTable().setModel(model);
    }

    private void ajouterModele() {
        JTextField nomField = new JTextField();
        JComboBox<String> marqueComboBox = new JComboBox<>();
        JSpinner puissanceSpinner = new JSpinner(new SpinnerNumberModel(50, 1, 1000, 1)); // Puissance entre 1 et 1000

        // Utiliser les catégories définies dans la classe Categorie
        JPanel categoriesPanel = new JPanel(new GridLayout(1, 4));
        JCheckBox[] categoriesCheckBoxes = new JCheckBox[Categorie.categories.length];

        for (int i = 0; i < Categorie.categories.length; i++) {
            categoriesCheckBoxes[i] = new JCheckBox(Categorie.categories[i]);
            categoriesPanel.add(categoriesCheckBoxes[i]);
        }

        // Remplir le JComboBox avec les marques disponibles
        for (Marque marque : parc.getMarques()) {
            marqueComboBox.addItem(marque.getNom());
        }

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Nom :"));
        panel.add(nomField);
        panel.add(new JLabel("Marque :"));
        panel.add(marqueComboBox);
        panel.add(new JLabel("Puissance :"));
        panel.add(puissanceSpinner);
        panel.add(new JLabel("Catégories :"));
        panel.add(new JScrollPane(categoriesPanel)); 

        int result = JOptionPane.showConfirmDialog(view, panel, "Ajouter un Modèle", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nom = nomField.getText().trim();
                String marqueNom = (String) marqueComboBox.getSelectedItem();
                int puissance = (int) puissanceSpinner.getValue();

                // Récupérer les catégories sélectionnées
                Vector<Categorie> categories = new Vector<>();
                for (int i = 0; i < categoriesCheckBoxes.length; i++) {
                    if (categoriesCheckBoxes[i].isSelected()) {
                        categories.add(new Categorie(Categorie.categories[i]));
                    }
                }

                // Trouver la marque correspondante
                Marque marque = parc.rechercherMarque(marqueNom);
                // Créer et ajouter le modèle
                Modele modele = new Modele(nom, marque, puissance, categories);
                marque.ajouterModele(modele);

                // Mettre à jour la table
                updateModeleTable();
                JOptionPane.showMessageDialog(view, "Modèle ajouté avec succès !");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierModele() {
        int selectedRow = view.getModeleTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un modèle à modifier.");
            return;
        }

        // Récupérer les informations du modèle sélectionné
        String modeleNom = (String) view.getModeleTable().getValueAt(selectedRow, 0);
        String marqueNom = (String) view.getModeleTable().getValueAt(selectedRow, 1);

        // Trouver le modèle et sa marque
        Modele modele = null;
        Marque marque = parc.rechercherMarque(marqueNom);

                for (Modele mod : marque.getModeles()) {
                    if (mod.getNom().equals(modeleNom)) {
                        modele = mod;
                        break;
                    }
                }
            
            
        

        // Préparer les champs pour la modification
        JTextField nomField = new JTextField(modele.getNom());
        nomField.setEditable(false); // Le nom n'est pas modifiable
        JTextField marqueField = new JTextField(marque.getNom());
        marqueField.setEditable(false); // La marque n'est pas modifiable
        JSpinner puissanceSpinner = new JSpinner(new SpinnerNumberModel(modele.getPuissance(), 1, 1000, 1));

        // Préparer les catégories avec des cases à cocher
        JPanel categoriesPanel = new JPanel(new GridLayout(1, 4));
        JCheckBox[] categoriesCheckBoxes = new JCheckBox[Categorie.categories.length];

        for (int i = 0; i < Categorie.categories.length; i++) {
            categoriesCheckBoxes[i] = new JCheckBox(Categorie.categories[i]);
            // Cocher les catégories déjà associées au modèle
            for (Categorie cat : modele.getCategories()) {
                if (cat.getCategorie().equals(Categorie.categories[i])) {
                    categoriesCheckBoxes[i].setSelected(true);
                    break;
                }
            }
            categoriesPanel.add(categoriesCheckBoxes[i]);
        }

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Nom :"));
        panel.add(nomField);
        panel.add(new JLabel("Marque :"));
        panel.add(marqueField);
        panel.add(new JLabel("Puissance :"));
        panel.add(puissanceSpinner);
        panel.add(new JLabel("Catégories :"));
        panel.add(new JScrollPane(categoriesPanel)); 

        int result = JOptionPane.showConfirmDialog(view, panel, "Modifier un Modèle", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int puissance = (int) puissanceSpinner.getValue();

                // Récupérer les catégories sélectionnées
                Vector<Categorie> categories = new Vector<>();
                for (int i = 0; i < categoriesCheckBoxes.length; i++) {
                    if (categoriesCheckBoxes[i].isSelected()) {
                        categories.add(new Categorie(Categorie.categories[i]));
                    }
                }

                // Mettre à jour les informations du modèle
                modele.setPuissance(puissance);
                modele.setCategories(categories);

                // Mettre à jour la table
                updateModeleTable();
                JOptionPane.showMessageDialog(view, "Modèle modifié avec succès !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerModele() {
        int selectedRow = view.getModeleTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un modèle à supprimer.");
            return;
        }

        // Récupérer les informations du modèle sélectionné
        String modeleNom = (String) view.getModeleTable().getValueAt(selectedRow, 0);
        String marqueNom = (String) view.getModeleTable().getValueAt(selectedRow, 1);

        // Trouver le modèle et sa marque
        Modele modele = null;
        Marque marque = parc.rechercherMarque(marqueNom);

        if (marque != null) {
            for (Modele mod : marque.getModeles()) {
                if (mod.getNom().equals(modeleNom)) {
                    modele = mod;
                    break;
                }
            }
        }
        // Demander confirmation avant de supprimer
        int confirm = JOptionPane.showConfirmDialog(view,
                "Êtes-vous sûr de vouloir supprimer le modèle \"" + modele.getNom() + "\" et tous les scooters associés ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Supprimer tous les scooters associés au modèle
            Vector<Scooter> scootersASupprimer = modele.getScooters();
            for (Scooter scooter : scootersASupprimer) {
                parc.supprimerScooter(scooter); // Supprime le scooter du parc et gère les locations
            }
            for (Categorie cat : modele.getCategories()){
                cat.retirerModele(modele);
            }
            // Supprimer le modèle de la liste des modèles de la marque
            marque.retirerModele(modele);

            // Mettre à jour la table des modèles
            updateModeleTable();
            
            // Synchroniser les autres fenêtres (par exemple, la fenêtre des scooters)
            JOptionPane.showMessageDialog(view, "Modèle et scooters associés supprimés avec succès !");
        }
    }

    private void rechercherModele() {
        String searchText = view.getSearchField().getText().toLowerCase().trim();
    
        if (searchText.isEmpty()) {
           updateModeleTable();
            return;
        }
    
        // Définir les colonnes de la table
        String[] columns = {"Nom", "Marque", "Puissance", "Catégories"};
        Vector<String[]> data = new Vector<>();
    
        // Parcourir les marques et modèles pour trouver les correspondances
        for (Marque marque : parc.getMarques()) {
            for (Modele modele : marque.getModeles()) {
                if (modele.getNom().toLowerCase().equals(searchText) || 
                    marque.getNom().toLowerCase().equals(searchText)) {
                    
                    String[] row = new String[columns.length];
                    row[0] = modele.getNom();
                    row[1] = marque.getNom();
                    row[2] = String.valueOf(modele.getPuissance()) + " Chevaux";
    
                    Vector<String> catgrs = new Vector<>();
                    for (Categorie cat : modele.getCategories()) {
                        catgrs.add(cat.getCategorie());
                    }
                    row[3] = String.valueOf(catgrs);
    
                    data.add(row);
                }
            }
        }
    
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Aucun modèle trouvé pour la recherche : " + searchText);
            return;
        }
    
        // Convertir les données en tableau
        String[][] tableData = data.toArray(new String[0][]);
    
        // Mettre à jour la table avec les résultats de la recherche
        DefaultTableModel model = new DefaultTableModel(tableData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêcher l'édition des cellules
            }
        };
    
        view.getModeleTable().setModel(model);
    }

   
}