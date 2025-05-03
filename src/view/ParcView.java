package view;
import model.*;
import controller.*;
import javax.swing.*;
import java.awt.*;
// import java.awt.event.ActionListener;

public class ParcView extends JFrame {
    private Parc parc;
    private JTextArea resumeArea;
    private JButton resumeButton, scootersButton, clientsButton, modelesButton, 
                    marquesButton, locationsButton, retoursButton;
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);    // Bleu moderne
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);  // Bleu clair
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Gris clair
    private static final Color TEXT_COLOR = new Color(44, 62, 80);         // Gris foncé
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public ParcView(Parc parc) {
        this.parc = parc;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Gestion du Parc de Scooters");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Créer un panneau principal avec un layout moderne
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panneau pour les boutons avec un design moderne
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        // Titre du menu
        JLabel menuTitle = new JLabel("Menu Principal");
        menuTitle.setFont(TITLE_FONT);
        menuTitle.setForeground(TEXT_COLOR);
        menuTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(menuTitle);
        buttonPanel.add(Box.createVerticalStrut(30));

        // Création des boutons avec un style moderne
        resumeButton = createModernButton("📊 Résumé du Parc");
        scootersButton = createModernButton("🛵 Gestion des Scooters");
        clientsButton = createModernButton("👥 Gestion des Clients");
        modelesButton = createModernButton("📋 Gestion des Modèles");
        marquesButton = createModernButton("🏢 Gestion des Marques");
        locationsButton = createModernButton("📅 Gestion des Locations");
        retoursButton = createModernButton("↩️ Gestion des Retours");

        // Ajout des boutons au panneau avec des espacements
        addButtonToPanel(buttonPanel, resumeButton);
        addButtonToPanel(buttonPanel, scootersButton);
        addButtonToPanel(buttonPanel, clientsButton);
        addButtonToPanel(buttonPanel, modelesButton);
        addButtonToPanel(buttonPanel, marquesButton);
        addButtonToPanel(buttonPanel, locationsButton);
        addButtonToPanel(buttonPanel, retoursButton);

        // Zone de texte moderne pour afficher les informations
        resumeArea = new JTextArea();
        resumeArea.setEditable(false);
        resumeArea.setFont(TEXT_FONT);
        resumeArea.setForeground(TEXT_COLOR);
        resumeArea.setBackground(Color.WHITE);
        resumeArea.setLineWrap(true);
        resumeArea.setWrapStyleWord(true);
        resumeArea.setMargin(new Insets(20, 20, 20, 20));

        // ScrollPane avec un style moderne
        JScrollPane scrollPane = new JScrollPane(resumeArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(0, 0, 0, 0),
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1)
        ));
        scrollPane.setBackground(Color.WHITE);

        // Ajout des composants au panneau principal
        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Ajout du panneau principal à la fenêtre
        add(mainPanel);

      //   Ajout du listener pour les boutons 
        scootersButton.addActionListener(e -> {
            ScooterView scooterView = new ScooterView(parc);
            scooterView.show();;
        });
        clientsButton.addActionListener(e -> {
            ClientView clientView = new ClientView(parc); 
            clientView.show();; 
        });
        modelesButton.addActionListener(e ->{
            ModeleView modeleView = new ModeleView(parc);
            modeleView.show();
        });
        marquesButton.addActionListener(e ->{
            MarqueView marqueView = new MarqueView(parc);
            marqueView.show();
        });

        
    }
    
    

    private JButton createModernButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 45));
        button.setPreferredSize(new Dimension(250, 45));
        
        // Effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(SECONDARY_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    private void addButtonToPanel(JPanel panel, JButton button) {
        panel.add(button);
        panel.add(Box.createVerticalStrut(15));
    }

    // Getters pour les boutons
    public JButton getResumeButton() { return resumeButton; }
    public JButton getScootersButton() { return scootersButton; }
    public JButton getClientsButton() { return clientsButton; }
    public JButton getModelesButton() { return modelesButton; }
    public JButton getMarquesButton() { return marquesButton; }
    public JButton getLocationsButton() { return locationsButton; }
    public JButton getRetoursButton() { return retoursButton; }

    public void afficherResumeParc() {
        StringBuilder resume = new StringBuilder();
        resume.append("=== RÉSUMÉ DU PARC ===\n\n");
        
        // Informations générales du parc
        resume.append("Adresse: ").append(parc.getAdresse()).append("\n");
        resume.append("ID du parc: ").append(parc.getId()).append("\n\n");
        
        // Statistiques des scooters
        int totalScooters = parc.getScooters().size();
        int scootersDisponibles = 0;
        double kmTotal = 0;
        for (Scooter s : parc.getScooters()) {
            if (s.isDisponible()) scootersDisponibles++;
            kmTotal += s.getKm();
        }
        
        resume.append("=== STATISTIQUES SCOOTERS ===\n");
        resume.append("Nombre total de scooters: ").append(totalScooters).append("\n");
        resume.append("Scooters disponibles: ").append(scootersDisponibles).append("\n");
        resume.append("Scooters en location: ").append(totalScooters - scootersDisponibles).append("\n");
        resume.append("Kilométrage total: ").append(String.format("%.2f", kmTotal)).append(" km\n");
        resume.append("Kilométrage moyen: ").append(String.format("%.2f", totalScooters > 0 ? kmTotal/totalScooters : 0)).append(" km\n\n");
        
        // Statistiques des clients
        resume.append("=== STATISTIQUES CLIENTS ===\n");
        resume.append("Nombre total de clients: ").append(parc.getClients().size()).append("\n");
        
        // Statistiques des marques et modèles
        resume.append("\n=== STATISTIQUES MARQUES ET MODÈLES ===\n");
        resume.append("Nombre de marques: ").append(parc.getMarques().size()).append("\n");
        int totalModeles = 0;
        for (Marque m : parc.getMarques()) {
            totalModeles += m.getModeles().size();
        }
        resume.append("Nombre total de modèles: ").append(totalModeles).append("\n\n");
        
        // Liste des marques
        resume.append("=== LISTE DES MARQUES ===\n");
        for (Marque m : parc.getMarques()) {
            resume.append("- ").append(m.getNom()).append("\n");
        }
        
        resumeArea.setText(resume.toString());
    }

    public void afficherScootersView() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== LISTE DES SCOOTERS ===\n\n");
        for (Scooter scooter : parc.getScooters()) {
            sb.append("ID: ").append(scooter.getId()).append("\n");
            sb.append("Modèle: ").append(scooter.getModele().getNom()).append("\n");
            sb.append("Marque: ").append(scooter.getModele().getMarque().getNom()).append("\n");
            sb.append("État: ").append(scooter.isDisponible()).append("\n");
            sb.append("-------------------\n");
        }
        resumeArea.setText(sb.toString());
    }

    public void afficherClientsView() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== LISTE DES CLIENTS ===\n\n");
        for (Client client : parc.getClients()) {
            sb.append("ID: ").append(client.getNumPermis()).append("\n");
            sb.append("Nom: ").append(client.getNom()).append("\n");
            sb.append("Prénom: ").append(client.getPrenom()).append("\n");
            sb.append("-------------------\n");
        }
        resumeArea.setText(sb.toString());
    }

    public void afficherModelesView() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== LISTE DES MODÈLES ===\n\n");
        for (Marque marque : parc.getMarques()){
            for (Modele modele : marque.getModeles()) {
                sb.append("Nom: ").append(modele.getNom()).append("\n");
                sb.append("Marque: ").append(modele.getMarque().getNom()).append("\n");
                sb.append("-------------------\n");
        }
    }
        resumeArea.setText(sb.toString());
    }

    public void afficherMarquesView() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== LISTE DES MARQUES ===\n\n");
        for (Marque marque : parc.getMarques()) {
            sb.append("Nom: ").append(marque.getNom()).append("\n");
            sb.append("-------------------\n");
        }
        resumeArea.setText(sb.toString());
    }

    // public void afficherLocationsView() {
    //     StringBuilder sb = new StringBuilder();
    //     sb.append("=== LOCATIONS EN COURS ===\n\n");
    //     for (Location location : parc.getLocationsEnCours()) {
    //         sb.append("ID: ").append(location.getId()).append("\n");
    //         sb.append("Client: ").append(location.getClient().getNom())
    //           .append(" ").append(location.getClient().getPrenom()).append("\n");
    //         sb.append("Scooter: ").append(location.getScooter().getId()).append("\n");
    //         sb.append("Date de début: ").append(location.getDateDebut()).append("\n");
    //         sb.append("-------------------\n");
    //     }
    //     resumeArea.setText(sb.toString());
    // }

    // public void afficherRetoursView() {
    //     StringBuilder sb = new StringBuilder();
    //     sb.append("=== RETOURS RÉCENTS ===\n\n");
    //     for (Location location : parc.getLocationsTerminees()) {
    //         sb.append("ID: ").append(location.getId()).append("\n");
    //         sb.append("Client: ").append(location.getClient().getNom())
    //           .append(" ").append(location.getClient().getPrenom()).append("\n");
    //         sb.append("Scooter: ").append(location.getScooter().getId()).append("\n");
    //         sb.append("Date de début: ").append(location.getDateDebut()).append("\n");
    //         sb.append("Date de fin: ").append(location.getDateRetourPrevue()).append("\n");
    //         sb.append("-------------------\n");
    //     }
    //     resumeArea.setText(sb.toString());
    // }
}