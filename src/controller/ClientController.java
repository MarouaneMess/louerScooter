package controller;

import model.Parc;
import model.Client;
import view.ClientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientController {
    private Parc parc;
    private ClientView view;

    public ClientController(Parc parc, ClientView view) {
        this.parc = parc;
        this.view = view;
        initController();
    }

    private void initController() {
        view.getAddButton().addActionListener(e -> addClient());
        view.getModifyButton().addActionListener(e -> modifyClient());
        view.getDeleteButton().addActionListener(e -> deleteClient());
        updateView();
    }

    private void addClient() {
        String nom = JOptionPane.showInputDialog("Nom du Client:");
        if (nom != null && !nom.trim().isEmpty()) {
            // Exemple: parc.ajouterClient(new Client(...));
            updateView();
        }
    }

    private void modifyClient() {
        // Implémentez la logique pour modifier un client
        JOptionPane.showMessageDialog(null, "Modification non implémentée.");
    }

    private void deleteClient() {
        // Implémentez la logique pour supprimer un client
        JOptionPane.showMessageDialog(null, "Suppression non implémentée.");
    }

    private void updateView() {
        StringBuilder sb = new StringBuilder();
        for (Client client : parc.getClients()) {
            sb.append(client.toString()).append("\n");
        }
        view.updateClientList(sb.toString());
    }
}