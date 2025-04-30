package view;
import model.*;
import controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class ScooterView extends JFrame {
    private Parc parc;
    private JTable scooterTable;
    private JScrollPane scrollPane;
    private JButton addButton, editButton, deleteButton;
    private JTextField searchField;
    private DefaultTableModel tableModel;
    private ScooterController controller;


    // Couleurs et polices
    private static final Color PRIMARY_COLOR = new Color(63, 81, 181);
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public ScooterView(Parc parc) {
        this.parc = parc;
        initializeUI();
        controller = new ScooterController(this, parc);
    }

    private void initializeUI() {
        setTitle("Gestion des Scooters");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // En-tête
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel titleLabel = new JLabel("Gestion des Scooters");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Barre de recherche
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(BACKGROUND_COLOR);
        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        JButton searchButton = createStyledButton("Rechercher");
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        // Table des scooters
        String[] columns = {"ID", "Modèle", "Année", "Kilométrage", "Prix/Jour", "Disponible", "Parc"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scooterTable = new JTable(tableModel);
        scooterTable.setRowHeight(30);
        scooterTable.getTableHeader().setFont(BUTTON_FONT);
        scrollPane = new JScrollPane(scooterTable);
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
        deleteButton.addActionListener(e -> controller.deleteScooter());
        searchButton.addActionListener(e -> controller.searchScooters(searchField.getText()));

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

    public void updateScooterTable(Vector<Scooter> scooters) {
        tableModel.setRowCount(0);
        for (Scooter scooter : scooters) {
            Object[] row = {
                scooter.getId(),
                scooter.getModele().getNom(),
                scooter.getAnneeSortie(),
                scooter.getKm() + " km",
                scooter.getPrixJour() + " €",
                scooter.isDisponible() ? "Oui" : "Non",
                scooter.getParc().getAdresse()
            };
            tableModel.addRow(row);
        }
    }

    public int getSelectedScooterId() {
        int selectedRow = scooterTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un scooter", 
                "Attention", 
                JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        return (int) tableModel.getValueAt(selectedRow, 0);
    }
} 