package view;

import model.Parc;
import controller.ScooterController;
import controller.MarqueController;
import controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class VueMain extends JFrame {
    private Parc parc;
    private JPanel cardPanel;
    private JTextArea resumeArea;

    public VueMain(Parc parc) {
        this.parc = parc;
        setTitle("Gestion de Location de Scooters");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création de la barre de menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Menu Parc
        JMenu parcMenu = new JMenu("Parc");
        JMenuItem resumeItem = new JMenuItem("Résumé");
        resumeItem.addActionListener(e -> showResume());
        parcMenu.add(resumeItem);
        menuBar.add(parcMenu);

        // Menu Clients
        JMenu clientsMenu = new JMenu("Clients");
        JMenuItem clientsItem = new JMenuItem("Afficher Clients");
        clientsItem.addActionListener(e -> showClients());
        clientsMenu.add(clientsItem);
        menuBar.add(clientsMenu);

        // Menu Scooters
        JMenu scootersMenu = new JMenu("Scooters");
        JMenuItem scootersItem = new JMenuItem("Afficher Scooters");
        scootersItem.addActionListener(e -> showScooters());
        scootersMenu.add(scootersItem);
        menuBar.add(scootersMenu);

        // Menu Marques
        JMenu marquesMenu = new JMenu("Marques");
        JMenuItem marquesItem = new JMenuItem("Afficher Marques");
        marquesItem.addActionListener(e -> showMarques());
        marquesMenu.add(marquesItem);
        menuBar.add(marquesMenu);

        // Panneau principal avec CardLayout
        cardPanel = new JPanel(new CardLayout());
        add(cardPanel);

        // Créer le JTextArea pour afficher le résumé
        resumeArea = new JTextArea();
        resumeArea.setEditable(false);
        resumeArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resumeArea);
        cardPanel.add(scrollPane, "Résumé");

        // Créer et ajouter les différentes vues avec leurs contrôleurs
        ScooterView scooterView = new ScooterView(parc);
        new ScooterController(parc, scooterView);
        cardPanel.add(scooterView, "Scooters");

        MarqueView marqueView = new MarqueView(parc);
        new MarqueController(parc, marqueView);
        cardPanel.add(marqueView, "Marques");

        ClientView clientView = new ClientView(parc);
        new ClientController(parc, clientView);
        cardPanel.add(clientView, "Clients");

        // Afficher le résumé du parc par défaut
        updateResume();
        showResume();

        setVisible(true);
    }

    private void updateResume() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== État Résumé du Parc ===\n");
        sb.append("Adresse : ").append(parc.getAdresse()).append("\n");
        sb.append("Nombre de clients : ").append(parc.getClients().size()).append("\n");
        sb.append("Nombre de marques : ").append(parc.getMarques().size()).append("\n");
        sb.append("Nombre de scooters : ").append(parc.getScooters().size()).append("\n");
        resumeArea.setText(sb.toString());
    }

    private void showResume() {
        CardLayout cl = (CardLayout) (cardPanel.getLayout());
        cl.show(cardPanel, "Résumé");
    }

    private void showClients() {
        CardLayout cl = (CardLayout) (cardPanel.getLayout());
        cl.show(cardPanel, "Clients");
    }

    private void showScooters() {
        CardLayout cl = (CardLayout) (cardPanel.getLayout());
        cl.show(cardPanel, "Scooters");
    }

    private void showMarques() {
        CardLayout cl = (CardLayout) (cardPanel.getLayout());
        cl.show(cardPanel, "Marques");
    }
}