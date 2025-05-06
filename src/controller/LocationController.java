package controller;

import view.LocationView;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Vector;

public class LocationController implements ActionListener {
    private LocationView view;
    private Parc parc;

    public LocationController(LocationView view, Parc parc) {
        this.view = view;
        this.parc = parc;

        view.getAddButton().addActionListener(this);
        view.getDeleteButton().addActionListener(this);
        view.getSearchButton().addActionListener(this);

        // Charger les données initiales
        updateLocationTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getAddButton()) {
            ajouterLocation();
        } else if (e.getSource() == view.getDeleteButton()) {
            supprimerLocation();
        } else if (e.getSource() == view.getSearchButton()) {
            rechercherLocation();
        }
    }

    private void updateLocationTable() {
        String[] columns = {"ID", "Num Permis Client", "Nom Client", "Scooter", "Date Début", "Date Retour Prévue", "Retour"};
        Vector<String[]> data = new Vector<>();

        // Parcourir tous les clients pour récupérer leurs locations
        for (Client client : parc.getClients()) {
            for (Location location : client.getLocations()) {
                data.add(new String[]{
                    String.valueOf(location.getId()),
                    String.valueOf(client.getNumPermis()),
                    client.getNom() +" "+ client .getPrenom(),
                    location.getScooter().getId(),
                    location.getDateDebut().toString(),
                    location.getDateRetourPrevue().toString(),
                    (location.getRetour() != null) ? "Oui" : "Non"
                });
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

        view.getLocationTable().setModel(model);
    }

    private void ajouterLocation() {
        try {
            // Sélectionner un client
            Client client = (Client) JOptionPane.showInputDialog(
                    view,
                    "Sélectionnez un client :",
                    "Ajouter une Location",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    parc.getClients().toArray(),
                    null
            );

            if (client == null) {
                JOptionPane.showMessageDialog(view, "Aucun client sélectionné.");
                return;
            }

            // Sélectionner un scooter disponible
            Scooter scooter = (Scooter) JOptionPane.showInputDialog(
                    view,
                    "Sélectionnez un scooter disponible :",
                    "Ajouter une Location",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    parc.getScootersDispo().toArray(),
                    null
            );

            if (scooter == null) {
                JOptionPane.showMessageDialog(view, "Aucun scooter sélectionné.");
                return;
            }

            // Saisir la date de début
            String dateDebutStr = JOptionPane.showInputDialog(view, "Entrez la date de début (AAAA-MM-JJ) :");
            
            LocalDate dateDebut = LocalDate.parse(dateDebutStr);

            // Saisir la date de retour prévue
            String dateRetourPrevueStr = JOptionPane.showInputDialog(view, "Entrez la date de retour prévue (AAAA-MM-JJ) :");
            
            LocalDate dateRetourPrevue = LocalDate.parse(dateRetourPrevueStr);

            

            // Créer la location
            new Location(client, scooter, dateDebut, dateRetourPrevue);
            
            // Mettre à jour la table des locations
            updateLocationTable();
            JOptionPane.showMessageDialog(view, "Location ajoutée avec succès !");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerLocation() {
        int selectedRow = view.getLocationTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner une location à supprimer.");
            return;
        }

        try {
            // Récupérer l'ID de la location sélectionnée
            int locationId = Integer.parseInt((String) view.getLocationTable().getValueAt(selectedRow, 0));

            // Confirmer la suppression
            int confirm = JOptionPane.showConfirmDialog(view,
                    "Êtes-vous sûr de vouloir supprimer la location ID " + locationId + " ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Parcourir tous les clients pour trouver et supprimer la location
                for (Client client : parc.getClients()) {
                    for (Location location : client.getLocations()) {
                        if (location.getId() == locationId) {
                            // Supprimer la location de la liste des locations du client
                            client.retirerLocation(location);
                            // Mettre à jour la table des locations
                            updateLocationTable();
                            JOptionPane.showMessageDialog(view, "Location supprimée avec succès !");
                            return;
                        }
                    }
                }

                // Si la location n'est pas trouvée
                JOptionPane.showMessageDialog(view, "Location introuvable.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rechercherLocation() {
        String searchText = view.getSearchField().getText().toLowerCase().trim();
        String filterType = (String) view.getFilterComboBox().getSelectedItem();

        if (searchText.isEmpty()) {
            updateLocationTable();
            return;
        }

        String[] columns = {"ID", "Num Permis Client", "Nom Client", "Scooter", "Date Début", "Date Retour Prévue", "Retour"};
        Vector<String[]> resultats = new Vector<>();

        // Filtrer par client ou scooter
        if (filterType.equals("Par Id Client")) {
            for (Client client : parc.getClients()) {
                if(client.getNumPermis()==Integer.parseInt(searchText)) {
                    for (Location location : client.getLocations()) {
                        resultats.add(formatLocation(location));
                    }
                }
            }
        } else if (filterType.equals("Par Id Scooter")) {
            for (Scooter scooter : parc.getScooters()) {
                if (scooter.getId().toLowerCase().equals(searchText)) {
                    for (Location location : scooter.getLocations()) {
                        resultats.add(formatLocation(location));
                    }
                }
            }
        }else if (filterType.equals("Par Id Location")) {
            int locationId = Integer.parseInt(searchText); 
            for (Client client : parc.getClients()) {
                for (Location location : client.getLocations()) {
                    if (location.getId() == locationId) {
                        resultats.add(formatLocation(location));
                    }
                }
            }
        }

        if (resultats.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Aucune location trouvée pour la recherche : " + searchText);
            return;
        }

        String[][] data = resultats.toArray(new String[0][]);

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        view.getLocationTable().setModel(model);
    }

    private String[] formatLocation(Location location) {
        return new String[]{
            String.valueOf(location.getId()),
            String.valueOf(location.getClient().getNumPermis()),
            location.getClient().getNom() + " " + location.getClient().getPrenom(),
            location.getScooter().getId(),
            location.getDateDebut().toString(),
            location.getDateRetourPrevue().toString(),
            (location.getRetour() != null) ? "Oui" : "Non"
        };
    }
}
