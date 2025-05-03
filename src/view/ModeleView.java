package view;

import model.*;
import controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class ModeleView extends JFrame {
    private Parc parc;
    private JTable modeleTable;
    private JScrollPane scrollPane;
    private JButton addButton, editButton, deleteButton;
    private JTextField searchField;
    private DefaultTableModel tableModel;
    private ModeleController controller;

    // Couleurs et polices
    private static final Color PRIMARY_COLOR = new Color(63, 81, 181);
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public ModeleView(Parc parc) {
        this.parc = parc;
        initializeUI();
        controller = new ModeleController(this, parc);
    }

    private void initializeUI() {
        setTitle("Gestion des Modèles");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

        // En-tête
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        JLabel titleLabel = new JLabel("Gestion des Modèles");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Barre de recherche
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(BACKGROUND_COLOR);
        searchField = new JTextField(20);
        JButton searchButton = createStyledButton("Rechercher");
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        // Table des modèles
        String[] columns = {"Nom", "Marque", "Puissance", "Catégories", "Nombre de Scooters"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeleTable = new JTable(tableModel);
        modeleTable.setRowHeight(30);
        modeleTable.getTableHeader().setFont(BUTTON_FONT);
        scrollPane = new JScrollPane(modeleTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Panel des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        addButton = createStyledButton("Ajouter");
        editButton = createStyledButton("Modifier");
        deleteButton = createStyledButton("Supprimer");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Ajout des composants
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Ajout des listeners
        addButton.addActionListener(e -> controller.showAddDialog());
        editButton.addActionListener(e -> controller.showEditDialog());
        deleteButton.addActionListener(e -> controller.deleteModele());
        searchButton.addActionListener(e -> controller.searchModeles(searchField.getText()));

        add(mainPanel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public void updateModeleTable(Vector<Modele> modeles) {
        tableModel.setRowCount(0);
        for (Modele modele : modeles) {
            // Créer une liste des noms des catégories
            StringBuilder categoriesStr = new StringBuilder();
            for (Categorie categorie : modele.getCategories()) {
                if (categoriesStr.length() > 0) {
                    categoriesStr.append(", ");
                }
                categoriesStr.append(categorie.getCategorie());
            }

            Object[] row = {
                modele.getNom(),
                modele.getMarque().getNom(),
                modele.getPuissance() + " CV",
                categoriesStr.toString(),
                modele.getScooters().size()
            };
            tableModel.addRow(row);
        }
    }


    public String getSelectedModeleNom() {
        int selectedRow = modeleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un modèle", 
                "Attention", 
                JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return (String) tableModel.getValueAt(selectedRow, 0);  // Retourne le nom du modèle
    }
}