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

        view.getMettreAJourButton().addActionListener(this);
        view.getAddButton().addActionListener(this);
        view.getSaveReturnButton().addActionListener(this);
        view.getDeleteButton().addActionListener(this);
        view.getSearchButton().addActionListener(this);

        // Charger les données initiales
        updateLocationTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getMettreAJourButton()) {
            updateLocationTable();
        } else if (e.getSource() == view.getAddButton()) {
            ajouterLocation();
        } else if (e.getSource() == view.getSaveReturnButton()) {
            enregistrerRetourLocation();
        } else if (e.getSource() == view.getDeleteButton()) {
            supprimerLocation();
        } else if (e.getSource() == view.getSearchButton()) {
            rechercherLocation();
        }
    }

    private void updateLocationTable() {
        String[] columns = {"ID", "Permis Client", "Client", "Scooter", "Date Début", "Date Retour Prévue", "Prix", "Retour tardif"};
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
                    String.valueOf(location.getPrixLocation()),
                    location.getRetour() == null ?"Pas de Retour" : 
                    (location.getRetour().getDateRetourEffective().isAfter(location.getDateRetourPrevue())) ? "Oui" : "Non"
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
    private void enregistrerRetourLocation() {
        int selectedRow = view.getLocationTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner une location à retourner.");
            return;
        }

        try {
            // Récupérer l'ID de la location sélectionnée
            int locationId = Integer.parseInt((String) view.getLocationTable().getValueAt(selectedRow, 0));

            JPanel panel = new JPanel(new java.awt.GridLayout(2, 2));
            JTextField dateRetourField = new JTextField(10);
            JTextField kmParcourusField = new JTextField(10);
            panel.add(new JLabel("Date de retour (AAAA-MM-JJ) :"));
            panel.add(dateRetourField);
            panel.add(new JLabel("Kilomètres parcourus :"));
            panel.add(kmParcourusField);
            // on doit verifier que la location existe et que le retour n'est pas encore fait
            Location location = null;
            for (Client client : parc.getClients()) {
                for (Location loc : client.getLocations()) {
                    if (loc.getId() == locationId) {
                        location = loc;
                        break;
                    }
                }  
            }
            if (location != null && location.getRetour() == null) {
                int option = JOptionPane.showConfirmDialog(view, panel, "Retour de Location", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    LocalDate dateRetour = LocalDate.parse(dateRetourField.getText());
                    int kmParcourus = Integer.parseInt(kmParcourusField.getText());
                    location.enregistrerRetour(kmParcourus, dateRetour);
                    JOptionPane.showMessageDialog(view, "Retour enregistré avec succès !");
                } else {
                    JOptionPane.showMessageDialog(view, "Retour annulé.");
                }
            } else {
                JOptionPane.showMessageDialog(view, "Retour effectué déjà.");
            }
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
