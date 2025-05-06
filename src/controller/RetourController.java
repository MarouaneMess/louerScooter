package controller;

import view.RetourView;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class RetourController implements ActionListener {
    private RetourView view;
    private Parc parc;

    public RetourController(RetourView view, Parc parc) {
        this.view = view;
        this.parc = parc;

        // Attacher les écouteurs aux boutons
        view.getMettreAJourButton().addActionListener(this);
        view.getSearchButton().addActionListener(this);
        view.getDeleteButton().addActionListener(this);
        view.getFilterButton().addActionListener(this);
        

        // Charger les données initiales
        updateRetourTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getMettreAJourButton()) {
            updateRetourTable();
        } else if (e.getSource() == view.getSearchButton()) {
            rechercherRetour();
        } else if (e.getSource() == view.getDeleteButton()) {
            supprimerRetour();
        } else if (e.getSource() == view.getFilterButton()) {
            filterRetourParDate();
        }
    }

    private void updateRetourTable() {
        String[] columns = {"ID Location", "Date Retour Effective", "Kilométrage Effectué", "Pénalité"};
        Vector<String[]> data = new Vector<>();

        // Parcourir tous les clients pour récupérer leurs retours
        for (Client client : parc.getClients()) {
            for (Location location : client.getLocations()) {
                Retour retour = location.getRetour();
                if (retour != null) {
                    data.add(new String[]{
                        String.valueOf(location.getId()),
                        retour.getDateRetourEffective().toString(),
                        String.format("%.2f km", retour.getKmEffectue()),
                        String.format("%.2f €", location.getPrixPinalite())
                    });
                }
            }
        }

        // Convertir les données en tableau
        String[][] tableData = data.toArray(new String[0][]);

        // Mettre à jour le modèle de la table
        DefaultTableModel model = new DefaultTableModel(tableData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêcher l'édition des cellules
            }
        };

        view.getRetourTable().setModel(model);
    }

    private void rechercherRetour() {
        String searchText = view.getSearchField().getText().toLowerCase().trim();

        if (searchText.isEmpty()) {
            updateRetourTable();
            return;
        }

        String[] columns = {"ID Location", "Date Retour Effective", "Kilométrage Effectué", "Pénalité"};
        Vector<String[]> resultats = new Vector<>();

        // Filtrer les retours
        for (Client client : parc.getClients()) {
            for (Location location : client.getLocations()) {
                Retour retour = location.getRetour();
                if (retour != null) {
                    if (String.valueOf(location.getId()).equals(searchText)) {
                        resultats.add(new String[]{
                            String.valueOf(location.getId()),
                            retour.getDateRetourEffective().toString(),
                            String.format("%.2f km", retour.getKmEffectue()),
                            String.format("%.2f €", location.getPrixPinalite())
                        });
                    }
                }
            }
        }

        if (resultats.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Aucun retour trouvé pour la recherche : " + searchText);
            return;
        }

        String[][] data = resultats.toArray(new String[0][]);

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        view.getRetourTable().setModel(model);
    }

    private void supprimerRetour() {
        int selectedRow = view.getRetourTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un retour à supprimer.");
            return;
        }

        try {
            // Récupérer l'ID de la location associée au retour
            int locationId = Integer.parseInt((String) view.getRetourTable().getValueAt(selectedRow, 0));

            // Parcourir les clients pour trouver et supprimer le retour
            for (Client client : parc.getClients()) {
                for (Location location : client.getLocations()) {
                    if (location.getId() == locationId && location.getRetour() != null) {
                        location.supprimerRetour();
                        updateRetourTable();
                        JOptionPane.showMessageDialog(view, "Retour supprimé avec succès !");
                        return;
                    }
                }
            }

            JOptionPane.showMessageDialog(view, "Retour introuvable.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

        private void filterRetourParDate() {
            String dateText = view.getDateFilterField().getText().trim();

            if (dateText.isEmpty()) {
                updateRetourTable();
                return;
            }

            String[] columns = {"ID Location", "Date Retour Effective", "Kilométrage Effectué", "Pénalité"};
            Vector<String[]> resultats = new Vector<>();

            // Filtrer les retours par date
            for (Client client : parc.getClients()) {
                for (Location location : client.getLocations()) {
                    Retour retour = location.getRetour();
                    if (retour != null && retour.getDateRetourEffective().toString().equals(dateText)) {
                        resultats.add(new String[]{
                            String.valueOf(location.getId()),
                            retour.getDateRetourEffective().toString(),
                            String.format("%.2f km", retour.getKmEffectue()),
                            String.format("%.2f €", location.getPrixPinalite())
                        });
                    }
                }
            }

            if (resultats.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Aucun retour trouvé pour la date : " + dateText);
                return;
            }

            String[][] data = resultats.toArray(new String[0][]);

            DefaultTableModel model = new DefaultTableModel(data, columns) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            view.getRetourTable().setModel(model);
        }
}

    
