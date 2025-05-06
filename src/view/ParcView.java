package view;

import javax.swing.*;
import java.awt.*;
import model.Parc;
import javax.swing.table.DefaultTableModel;

public class ParcView extends JFrame {
    private JLabel titreLabel;
    private JButton afficherResumeBtn;
    private JButton gestionScootersBtn;
    private JButton gestionClientsBtn;
    private JButton gestionModelesBtn;
    private JButton gestionMarquesBtn;
    private JButton gestionLocationsBtn;
    private JButton gestionRetoursBtn;
    private JButton exitBtn;
    private Parc parc;
    private JTable resumeTable;

    public ParcView(Parc parc, int w, int h) {
        this.parc = parc;
        setPreferredSize(new Dimension(w, h));
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Gestion du Parc de Scooters - " + parc.getAdresse());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal avec BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel du titre
        JPanel titrePanel = new JPanel();
        titreLabel = new JLabel("Gestion du Parc de Scooters", SwingConstants.CENTER);
        titreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titrePanel.add(titreLabel);

        // Panel central avec BorderLayout
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Panel des boutons (fixé à gauche)
        JPanel boutonsPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        boutonsPanel.setPreferredSize(new Dimension(180, 0)); // Taille fixe pour les boutons à gauche

        // Création des boutons
        afficherResumeBtn = new JButton("Afficher Résumé");
        gestionScootersBtn = new JButton("Gestion des Scooters");
        gestionClientsBtn = new JButton("Gestion des Clients");
        gestionModelesBtn = new JButton("Gestion des Modèles");
        gestionMarquesBtn = new JButton("Gestion des Marques");
        gestionLocationsBtn = new JButton("Gestion des Locations");
        gestionRetoursBtn = new JButton("Gestion des Retours");
        exitBtn = new JButton("Quitter");
        

        // Style des boutons
        Font buttonFont = new Font("Arial", Font.PLAIN, 12);
        Dimension buttonSize = new Dimension(160, 30); // Taille réduite des boutons

        JButton[] buttons = {
            afficherResumeBtn, gestionScootersBtn, gestionClientsBtn,
            gestionModelesBtn, gestionMarquesBtn, gestionLocationsBtn, gestionRetoursBtn,
            exitBtn
        };

        for (JButton button : buttons) {
            button.setFont(buttonFont);
            button.setPreferredSize(buttonSize);
            boutonsPanel.add(button);
        }

        // Panel du résumé (au centre)
        JPanel resumePanel = new JPanel(new BorderLayout());
        String[] columns = {"Propriété", "Valeur"};
        String[][] initialData = {
            {"Nombre total de scooters", "0"},
            {"Scooters disponibles", "0"},
            {"Scooters en location", "0"},
            {"Nombre de clients", "0"},
            {"Kilométrage moyen", "0.00"}
        };

        resumeTable = new JTable(new DefaultTableModel(initialData, columns));
        resumeTable.setEnabled(false); // Empêcher l'édition
        resumeTable.setFont(new Font("Arial", Font.PLAIN, 14));
        resumeTable.setRowHeight(30);
        resumeTable.setBackground(Color.LIGHT_GRAY); // Fond gris clair
        resumeTable.setForeground(Color.BLACK); // Texte noir

        JScrollPane scrollPane = new JScrollPane(resumeTable);
        resumePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        exitPanel.add(exitBtn);

        resumePanel.add(exitPanel, BorderLayout.SOUTH);


        // Ajout des panels au panel central
        centerPanel.add(boutonsPanel, BorderLayout.WEST);
        centerPanel.add(resumePanel, BorderLayout.CENTER);

        // Ajout des panels au panel principal
        mainPanel.add(titrePanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
        pack();
    }

    // Getters pour les boutons
    public JButton getAfficherResumeBtn() {
        return afficherResumeBtn;
    }

    public JButton getGestionScootersBtn() {
        return gestionScootersBtn;
    }

    public JButton getGestionClientsBtn() {
        return gestionClientsBtn;
    }

    public JButton getGestionModelesBtn() {
        return gestionModelesBtn;
    }

    public JButton getGestionMarquesBtn() {
        return gestionMarquesBtn;
    }

    public JButton getGestionLocationsBtn() {
        return gestionLocationsBtn;
    }

    public JButton getGestionRetoursBtn() {
        return gestionRetoursBtn;
    }
    public JButton getExitBtn() {
        return exitBtn;
    }

    // Méthode pour mettre à jour le résumé
    public void updateResume(int totalScooters, int scootersDisponibles,
                             int scootersEnLocation, int nombreClients, double km){//,int mdl) {
        String[][] data = {
            {"Nombre total de scooters", String.valueOf(totalScooters)},
            {"Scooters disponibles", String.valueOf(scootersDisponibles)},
            {"Scooters en location", String.valueOf(scootersEnLocation)},
            {"Nombre de clients", String.valueOf(nombreClients)},
            {"Kilométrage moyen", String.format("%.2f", km)},
            // {"nbr de modeles", String.valueOf(mdl)}

        };

        // Mettre à jour le modèle de la table
        DefaultTableModel model = (DefaultTableModel) resumeTable.getModel();
        model.setDataVector(data, new String[]{"Propriété", "Valeur"});
    }
}
