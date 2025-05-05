package controller;

import model.Client;
import model.Parc;
import view.ClientView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class ClientController implements ActionListener {
    private ClientView view;
    private Parc parc;

    public ClientController(ClientView view, Parc parc) {
        this.view = view;
        this.parc = parc;

        // Attacher les écouteurs aux boutons
        view.getAddButton().addActionListener(this);
        view.getEditButton().addActionListener(this);
        view.getDeleteButton().addActionListener(this);
        view.getSearchButton().addActionListener(this);

        // Charger les données initiales
        updateClientTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getAddButton()) {
            ajouterClient();
        } else if (e.getSource() == view.getEditButton()) {
            modifierClient();
        } else if (e.getSource() == view.getDeleteButton()) {
            supprimerClient();
        } else if (e.getSource() == view.getSearchButton()) {
            rechercherClient();
        }
    }

    private void updateClientTable() {
        String[] columns = {"ID", "Nom", "Prénom", "Date de naissance", "Age" , "Adresse"};
        Vector<Client> clients = parc.getClients();
        String[][] data = new String[clients.size()][columns.length];

        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            data[i][0] = String.valueOf(client.getNumPermis());
            data[i][1] = client.getNom();
            data[i][2] = client.getPrenom();
            data[i][3] = String.valueOf(client.getDateNaissance());
            data[i][4] = String.valueOf(client.getAge());
            data[i][5] = client.getAdresse();
        }

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        view.getClientTable().setModel(model);
    }

    private void ajouterClient() {
        JOptionPane.showMessageDialog(view, "Ajouter un client (à implémenter)");
        // TODO: Implémenter la logique pour ajouter un client
    }

    private void modifierClient() {
        JOptionPane.showMessageDialog(view, "Modifier un client (à implémenter)");
        // TODO: Implémenter la logique pour modifier un client
    }

    private void supprimerClient() {
        JOptionPane.showMessageDialog(view, "Supprimer un client (à implémenter)");
        // TODO: Implémenter la logique pour supprimer un client
    }

    private void rechercherClient() {
        String searchText = view.getSearchField().getText().toLowerCase();
        JOptionPane.showMessageDialog(view, "Rechercher un client : " + searchText);
        // TODO: Implémenter la logique pour rechercher un client
    }
}