package view;

import javax.swing.*;
import java.awt.*;

public class RetourView extends JFrame {
    private JTable retourTable;
    private JButton mettreAJourButton;
    private JButton searchButton;
    private JButton deleteButton;
    private JTextField searchField;
    private JTextField dateFilterField;
    private JButton filterButton;

    public RetourView() {
        setTitle("Gestion des Retours");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dateFilterField = new JTextField(10);
        filterButton = new JButton("Filtrer");

        filterPanel.add(new JLabel("Date de retour (AAAA-MM-JJ):"));
        filterPanel.add(dateFilterField);
        filterPanel.add(filterButton);
        add(filterPanel, BorderLayout.NORTH);

        // Initialiser le tableau
        retourTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(retourTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Panneau d'actions
        JPanel actionPanel = new JPanel(new BorderLayout());

        // Panneau de recherche
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchButton = new JButton("Rechercher");
        // recherche juste par id location
        searchPanel.add(new JLabel("Recherche :"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        actionPanel.add(searchPanel, BorderLayout.NORTH);

        // Panneau des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        mettreAJourButton = new JButton("Mettre à jour");
        deleteButton = new JButton("Supprimer");
        buttonPanel.add(mettreAJourButton);
        buttonPanel.add(deleteButton);
        actionPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(actionPanel, BorderLayout.SOUTH);
    }

    // Getters pour accéder aux composants
    public JTable getRetourTable() {
        return retourTable;
    }

    public JButton getMettreAJourButton() {
        return mettreAJourButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getFilterButton() {
        return filterButton;
    }
    
    public JTextField getDateFilterField() {
        return dateFilterField;
    }


}