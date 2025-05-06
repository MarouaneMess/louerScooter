package controller;

import view.MarqueView;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MarqueController implements ActionListener {
    private MarqueView view;
    private Parc parc;

    public MarqueController(MarqueView view, Parc parc) {
        this.view = view;
        this.parc = parc;

        // Attacher les écouteurs aux boutons
        view.getMettreAJourButton().addActionListener(this);
        view.getAddButton().addActionListener(this);
        view.getEditButton().addActionListener(this);
        view.getDeleteButton().addActionListener(this);
        view.getSearchButton().addActionListener(this);

        // Charger les données initiales
        updateMarqueTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getMettreAJourButton()) {
            updateMarqueTable();
        } else if (e.getSource() == view.getAddButton()) {
            ajouterMarque();
        } else if (e.getSource() == view.getEditButton()) {
            modifierMarque();
        } else if (e.getSource() == view.getDeleteButton()) {
            supprimerMarque();
        } else if (e.getSource() == view.getSearchButton()) {
            rechercherMarque();
        }
    }

    private void updateMarqueTable() {
        String[] columns = {"Nom", "Nombre de Modèles"};
        Vector<Marque> marques = parc.getMarques();
        String[][] data = new String[marques.size()][columns.length];

        for (int i = 0; i < marques.size(); i++) {
            Marque marque = marques.get(i);
            data[i][0] = marque.getNom();
            data[i][1] = String.valueOf(marque.getModeles().size());
        }

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        view.getMarqueTable().setModel(model);
    }

    private void ajouterMarque() {
        String nom = JOptionPane.showInputDialog(view, "Entrez le nom de la marque :");
        if (nom != null && !nom.trim().isEmpty()) {
            try {
                parc.ajouterMarque(new Marque(parc, nom.trim()));
                updateMarqueTable();
                JOptionPane.showMessageDialog(view, "Marque ajoutée avec succès !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierMarque() {
        int selectedRow = view.getMarqueTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner une marque à modifier.");
            return;
        }

        String ancienNom = (String) view.getMarqueTable().getValueAt(selectedRow, 0);
        String nouveauNom = JOptionPane.showInputDialog(view, "Entrez le nouveau nom de la marque :", ancienNom);

        if (nouveauNom != null ) {
            try {
                Marque marque = parc.rechercherMarque(ancienNom);
                marque.setNom(nouveauNom.trim());
                updateMarqueTable();
                JOptionPane.showMessageDialog(view, "Marque modifiée avec succès !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerMarque() {
        int selectedRow = view.getMarqueTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner une marque à supprimer.");
            return;
        }

        String nom = (String) view.getMarqueTable().getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(view,
                "Êtes-vous sûr de vouloir supprimer la marque \"" + nom + "\" ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                parc.supprimerMarque(nom);
                updateMarqueTable();
                JOptionPane.showMessageDialog(view, "Marque supprimée avec succès !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void rechercherMarque() {
        String searchText = view.getSearchField().getText().toLowerCase().trim();

        // Si le champ de recherche est vide, afficher toutes les marques
        if (searchText.isEmpty()) {
            updateMarqueTable();
            return;
        }

        String[] columns = {"Nom", "Nombre de Modèles"};
        Vector<Marque> marques = parc.getMarques();
        Vector<String[]> resultats = new Vector<>();

        // Rechercher les marques correspondant au texte saisi
        for (Marque marque : marques) {
            if (marque.getNom().toLowerCase().equals(searchText)) {
                resultats.add(new String[]{marque.getNom(), String.valueOf(marque.getModeles().size())});
            }
        }

        // Si aucun résultat n'est trouvé
        if (resultats.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Aucune marque trouvée pour la recherche : " + searchText);
            return;
        }

        // Convertir les résultats en tableau
        String[][] data = resultats.toArray(new String[0][]);

        // Mettre à jour la table avec les résultats de la recherche
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêcher l'édition des cellules
            }
        };

        view.getMarqueTable().setModel(model);
    }
}