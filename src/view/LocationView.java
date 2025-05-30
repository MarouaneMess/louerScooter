package view;

import javax.swing.*;
import java.awt.*;

public class LocationView extends JFrame {
    private JTable locationTable;
    private JButton mettreAJourButton;
    private JButton addButton;
    private JButton saveReturnButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField searchField;
    private JComboBox<String> filterComboBox;

    public LocationView() {
        setTitle("Gestion des Locations");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialiser le tableau
        locationTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(locationTable);
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new BorderLayout());
        // // Panneau de recherche
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchButton = new JButton("Rechercher");
        filterComboBox = new JComboBox<>(new String[]{"Par Id Client", "Par id Scooter", "Par Id Location"});
        searchPanel.add(new JLabel("Recherche :"));
        searchPanel.add(searchField);
        searchPanel.add(filterComboBox);
        searchPanel.add(searchButton);
        actionPanel.add(searchPanel, BorderLayout.NORTH);

        // // Panneau des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        mettreAJourButton = new JButton("Mettre à jour");
        addButton = new JButton("Ajouter");
        saveReturnButton = new JButton("Enregistrer Retour");
        deleteButton = new JButton("Supprimer");
        buttonPanel.add(mettreAJourButton);
        buttonPanel.add(addButton);
        buttonPanel.add(saveReturnButton);
        buttonPanel.add(deleteButton);
        actionPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(actionPanel, BorderLayout.SOUTH);
    }

    // Getters pour accéder aux composants
    public JTable getLocationTable() {
        return locationTable;
    }

    public JButton getMettreAJourButton() {
        return mettreAJourButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getSaveReturnButton(){
        return saveReturnButton;
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

    public JComboBox<String> getFilterComboBox() {
        return filterComboBox;
    }

}