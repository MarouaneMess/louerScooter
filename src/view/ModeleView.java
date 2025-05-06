package view;

import javax.swing.*;
import java.awt.*;

public class ModeleView extends JFrame {
    private JTable modeleTable;
    private JButton mettreAJourButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField searchField;
    private JButton searchButton;

    public ModeleView() {
        setTitle("Gestion des Modèles");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout principal
        setLayout(new BorderLayout());

        // Table des modèles
        modeleTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(modeleTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Panel pour les boutons et la recherche
        JPanel actionPanel = new JPanel(new BorderLayout());

        // Champ de recherche
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchButton = new JButton("Rechercher");
        searchPanel.add(new JLabel("Recherche :"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        actionPanel.add(searchPanel, BorderLayout.NORTH);

        // Boutons d'action
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        mettreAJourButton = new JButton("Mettre à jour");
        addButton = new JButton("Ajouter");
        editButton = new JButton("Modifier");
        deleteButton = new JButton("Supprimer");
        buttonPanel.add(mettreAJourButton);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        actionPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(actionPanel, BorderLayout.SOUTH);
    }

    // Getters pour accéder aux composants
    public JTable getModeleTable() {
        return modeleTable;
    }

    public JButton getMettreAJourButton() {
        return mettreAJourButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }
}