package view;

import javax.swing.*;
import java.awt.*;

public class MarqueView extends JFrame {
    private JTable marqueTable;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField searchField;

    public MarqueView() {
        setTitle("Gestion des Marques");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialiser le tableau
        marqueTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(marqueTable);
        add(tableScrollPane, BorderLayout.CENTER);

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
        addButton = new JButton("Ajouter");
        editButton = new JButton("Modifier");
        deleteButton = new JButton("Supprimer");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        actionPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(actionPanel, BorderLayout.SOUTH);
    }

    // Getters pour accéder aux composants
    public JTable getMarqueTable() {
        return marqueTable;
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

    public JButton getSearchButton() {
        return searchButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }
}