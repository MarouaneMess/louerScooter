package controller;

import model.*;
import view.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Vector;

public class ClientController {
    private ClientView view;
    private Parc parc;

    public ClientController(ClientView view, Parc parc) {
        this.view = view;
        this.parc = parc;
    }

    // ✅ Met à jour l'affichage des clients dans la table
    public void updateClientTable() {
        Vector<Client> clients = parc.getClients();  
        view.updateClientTable(clients);
    }

    // ✅ Affiche la boîte de dialogue pour ajouter un client
    public void showAddDialog() {
        JDialog dialog = new JDialog(view, "Ajouter un Client", true);
        dialog.setSize(700, 450);
        dialog.setLocationRelativeTo(view);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel titleLabel = new JLabel("Nouveau Client");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        gbc.gridwidth = 1;

        // Numéro de permis
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Numéro Permis :"), gbc);
        gbc.gridx = 1;
        JSpinner permisSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999999, 1));
        panel.add(permisSpinner, gbc);

        // Nom
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1;
        JTextField nomField = new JTextField();
        panel.add(nomField, gbc);

        // Prénom
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Prénom :"), gbc);
        gbc.gridx = 1;
        JTextField prenomField = new JTextField();
        panel.add(prenomField, gbc);

        // Date de naissance
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Date Naissance (AAAA-MM-JJ) :"), gbc);
        gbc.gridx = 1;
        JTextField dateNaissField = new JTextField("2000-01-01");
        panel.add(dateNaissField, gbc);

        // Adresse
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Adresse :"), gbc);
        gbc.gridx = 1;
        JTextField adresseField = new JTextField();
        panel.add(adresseField, gbc);


        // Sélection des catégories (ComboBox)
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Catégories :"), gbc);
        gbc.gridx = 1;
        JComboBox<Categorie> categoriesCombo = new JComboBox<>(getAllCategories().toArray(new Categorie[0]));
        panel.add(categoriesCombo, gbc);
       
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Catégories :"), gbc);
        gbc.gridx = 1;
        Vector<Categorie> categories = getAllCategories();
        JComboBox<Categorie> categorieComboBox = new JComboBox<>(categories);

        panel.add(categoriesCombo, gbc);


        // Boutons
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);

        dialog.add(panel);

        // ✅ Action du bouton Enregistrer
        saveButton.addActionListener(e -> {
            try {
                int numPermis = (Integer) permisSpinner.getValue();
                String nom = nomField.getText().trim();
                String prenom = prenomField.getText().trim();
                LocalDate dateNaissance = LocalDate.parse(dateNaissField.getText().trim());
                String adresse = adresseField.getText().trim();

                if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty()) {
                    throw new IllegalArgumentException("Tous les champs doivent être remplis.");
                }

                // Vérifie si le permis existe déjà
                for (Client c : parc.getClients()) {
                    if (c.getNumPermis() == numPermis) {
                        JOptionPane.showMessageDialog(dialog, 
                            "Ce numéro de permis existe déjà.", 
                            "Erreur", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                Client client = new Client(parc , numPermis, nom, prenom, dateNaissance, adresse ,categories);
                parc.ajouterClient(client);
                updateClientTable();
                dialog.dispose();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Date invalide. Format attendu : AAAA-MM-JJ", 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Erreur : " + ex.getMessage(), 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    // ✅ Supprime le client sélectionné
    public void deleteClient() {
        int clientNumPermis = view.getSelectedClientNumPermis();
        if (clientNumPermis != -1) {
            Client client = findClientByNumPermis(clientNumPermis);
            if (client != null) {
                int confirm = JOptionPane.showConfirmDialog(view, 
                    "Voulez-vous vraiment supprimer ce client ?", 
                    "Confirmation", 
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    parc.supprimerClient(client.getNumPermis());
                    updateClientTable();
                }
            } else {
                JOptionPane.showMessageDialog(view, 
                    "Client introuvable.", 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void showEditDialog() {
        int selectedId = view.getSelectedClientId(); // suppose que cette méthode existe
        if (selectedId == -1) return;

        Client client = parc.rechercherClient(selectedId);
        if (client == null) {
            JOptionPane.showMessageDialog(view, 
                "Client non trouvé", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(view, "Modifier un Client", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(view);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Champs
        JTextField numPermisField = new JTextField(String.valueOf(client.getNumPermis()));
        numPermisField.setEditable(false); // le numéro de permis est fixe

        JTextField nomField = new JTextField(client.getNom());
        JTextField prenomField = new JTextField(client.getPrenom());

        // Catégories
        Vector<Categorie> allCategories = getAllCategories();
        JList<Categorie> categorieList = new JList<>(allCategories);
        categorieList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        categorieList.setVisibleRowCount(5);
        JScrollPane categorieScroll = new JScrollPane(categorieList);

        // Pré-sélectionner les catégories du client
        for (int i = 0; i < allCategories.size(); i++) {
            if (client.getCategories().contains(allCategories.get(i))) {
                categorieList.addSelectionInterval(i, i);
            }
        }

        // Ajout des champs
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Numéro Permis:"), gbc);
        gbc.gridx = 1;
        panel.add(numPermisField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1;
        panel.add(nomField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Prénom:"), gbc);
        gbc.gridx = 1;
        panel.add(prenomField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Catégories:"), gbc);
        gbc.gridx = 1;
        panel.add(categorieScroll, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        saveButton.addActionListener(e -> {
            try {
                client.setNom(nomField.getText());
                client.setPrenom(prenomField.getText());

                Vector<Categorie> selectedCategories = new Vector<>(categorieList.getSelectedValuesList());
                client.setCategories(selectedCategories);

                updateClientTable(); // méthode pour rafraîchir la liste affichée
                dialog.dispose();
                JOptionPane.showMessageDialog(dialog, 
                    "Client modifié avec succès", 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Erreur: " + ex.getMessage(), 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }


    // ✅ Recherche un client par son numéro de permis
    public  Client findClientByNumPermis(int numPermis) {
        for (Client client : parc.getClients()) {
            if (client.getNumPermis() == numPermis) {
                return client;
            }
        }
        return null;
    }
    private Vector<Categorie> getAllCategories() {
        Vector<Categorie> categorieVector = new Vector<>();
        // Parcours de la liste des catégories et création des objets Categorie
        for (String catName : Categorie.categories) {
            categorieVector.add(new Categorie(catName)); // Créer une nouvelle Categorie et l'ajouter au Vector
        }
        return categorieVector; // Retourner le Vector rempli de catégories
    }
    public void searchClients(int searchId) {
        if (searchId <= 0) {
            // Si l'ID de recherche est invalide (inférieur ou égal à 0)
            updateClientTable(); // Recharge tous les clients
            return;
        }

        Vector<Client> filteredClients = new Vector<>();
        for (Client client : parc.getClients()) {
            if (client.getNumPermis() == searchId) { // Si le numéro de permis correspond
                filteredClients.add(client);
            }
        }

        // Met à jour la vue avec les clients trouvés
        view.updateClientTable(filteredClients);
    }



}
