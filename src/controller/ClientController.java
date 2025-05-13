package controller;

import view.ClientView;
import model.Client;
import model.Parc;
import model.Categorie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Vector;

public class ClientController implements ActionListener {
    private ClientView view;
    private Parc parc;

    public ClientController(ClientView view, Parc parc) {
        this.view = view;
        this.parc = parc;

        // Attacher les écouteurs aux boutons
        view.getMettreAJourButton().addActionListener(this);
        view.getAddButton().addActionListener(this);
        view.getEditButton().addActionListener(this);
        view.getDeleteButton().addActionListener(this);
        view.getSearchButton().addActionListener(this);

        // Charger les données initiales
        updateClientTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getMettreAJourButton()) {
            updateClientTable();
        } else if (e.getSource() == view.getAddButton()) {
            ajouterClient();
        } else if (e.getSource() == view.getEditButton()) {
            modifierClient();
        } else if (e.getSource() == view.getDeleteButton()) {
            supprimerClient();
        } else if (e.getSource() == view.getSearchButton()) {
            rechercherClient();
        }
    }

    private void updateClientTable() {
        String[] columns = {"Numéro Permis", "Nom", "Prénom", "Date de Naissance", "Adresse", "Catégories"};
        Vector<String[]> data = new Vector<>();

        for (Client client : parc.getClients()) {
            Vector<String> categories = new Vector<>();
            for (Categorie categorie : client.getCategories()) {
                categories.add(categorie.getCategorie());
            }

            data.add(new String[]{
                String.valueOf(client.getNumPermis()),
                client.getNom(),
                client.getPrenom(),
                client.getDateNaissance().toString(),
                client.getAdresse(),
                categories.toString()
            });
        }

        String[][] tableData = data.toArray(new String[0][]);

        DefaultTableModel model = new DefaultTableModel(tableData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêcher l'édition des cellules
            }
        };

        view.getClientTable().setModel(model);
    }

    private void ajouterClient() {
        JTextField numPermisField = new JTextField();
        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField dateNaissanceField = new JTextField("AAAA-MM-JJ");
        JTextField adresseField = new JTextField();

        JPanel categoriesPanel = new JPanel(new GridLayout(1, 4));
        JCheckBox[] categoriesCheckBoxes = new JCheckBox[Categorie.categories.length];

        for (int i = 0; i < Categorie.categories.length; i++) {
            categoriesCheckBoxes[i] = new JCheckBox(Categorie.categories[i]);
            categoriesPanel.add(categoriesCheckBoxes[i]);
        }

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Numéro Permis :"));
        panel.add(numPermisField);
        panel.add(new JLabel("Nom :"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom :"));
        panel.add(prenomField);
        panel.add(new JLabel("Date de Naissance :"));
        panel.add(dateNaissanceField);
        panel.add(new JLabel("Adresse :"));
        panel.add(adresseField);
        panel.add(new JLabel("Catégories :"));
        panel.add(categoriesPanel);

        int result = JOptionPane.showConfirmDialog(view, panel, "Ajouter un Client", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int numPermis = Integer.parseInt(numPermisField.getText());
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                LocalDate dateNaissance = LocalDate.parse(dateNaissanceField.getText());
                String adresse = adresseField.getText();

                Vector<Categorie> categories = new Vector<>();
                for (int i = 0; i < categoriesCheckBoxes.length; i++) {
                    if (categoriesCheckBoxes[i].isSelected()) {
                        categories.add(new Categorie(Categorie.categories[i]));
                    }
                }

                Client client = new Client(parc, numPermis, nom, prenom, dateNaissance, adresse, categories);
                parc.ajouterClient(client);
                updateClientTable();
                JOptionPane.showMessageDialog(view, "Client ajouté avec succès !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierClient() {
        int selectedRow = view.getClientTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un client à modifier.");
            return;
        }

        try {
            int numPermis = Integer.parseInt((String) view.getClientTable().getValueAt(selectedRow, 0));
            Client client = parc.rechercherClient(numPermis);

            JTextField nomField = new JTextField(client.getNom());
            JTextField prenomField = new JTextField(client.getPrenom());
            JTextField dateNaissanceField = new JTextField(client.getDateNaissance().toString());
            JTextField adresseField = new JTextField(client.getAdresse());

            JPanel categoriesPanel = new JPanel(new GridLayout(1, 4));
            JCheckBox[] categoriesCheckBoxes = new JCheckBox[Categorie.categories.length];

            for (int i = 0; i < Categorie.categories.length; i++) {
                categoriesCheckBoxes[i] = new JCheckBox(Categorie.categories[i]);
                for (Categorie categorie : client.getCategories()) {
                    if (categorie.getCategorie().equals(Categorie.categories[i])) {
                        categoriesCheckBoxes[i].setSelected(true);
                        break;
                    }
                }
                categoriesPanel.add(categoriesCheckBoxes[i]);
            }

            JPanel panel = new JPanel(new GridLayout(6, 2));
            panel.add(new JLabel("Nom :"));
            panel.add(nomField);
            panel.add(new JLabel("Prénom :"));
            panel.add(prenomField);
            panel.add(new JLabel("Date de Naissance :"));
            panel.add(dateNaissanceField);
            panel.add(new JLabel("Adresse :"));
            panel.add(adresseField);
            panel.add(new JLabel("Catégories :"));
            panel.add(categoriesPanel);

            int result = JOptionPane.showConfirmDialog(view, panel, "Modifier un Client", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                client.setNom(nomField.getText());
                client.setPrenom(prenomField.getText());
                client.setDateNaissance(LocalDate.parse(dateNaissanceField.getText()));
                client.setAdresse(adresseField.getText());

                Vector<Categorie> categories = new Vector<>();
                for (int i = 0; i < categoriesCheckBoxes.length; i++) {
                    if (categoriesCheckBoxes[i].isSelected()) {
                        categories.add(new Categorie(Categorie.categories[i]));
                    }
                }
                client.setCategories(categories);

                updateClientTable();
                JOptionPane.showMessageDialog(view, "Client modifié avec succès !");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerClient() {
        int selectedRow = view.getClientTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un client à supprimer.");
            return;
        }

        try {
            int numPermis = Integer.parseInt((String) view.getClientTable().getValueAt(selectedRow, 0));
            Client client = parc.rechercherClient(numPermis);

            int confirm = JOptionPane.showConfirmDialog(view,
                    "Êtes-vous sûr de vouloir supprimer le client " + client.getNom() + " " + client.getPrenom() + " ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                parc.supprimerClient(numPermis);
                updateClientTable();
                JOptionPane.showMessageDialog(view, "Client supprimé avec succès !");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rechercherClient() {
        String searchText = view.getSearchField().getText().toLowerCase().trim();

        if (searchText.isEmpty()) {
            updateClientTable();
            return;
        }

        String[] columns = {"Numéro Permis", "Nom", "Prénom", "Date de Naissance", "Adresse", "Catégories"};
        Vector<String[]> resultats = new Vector<>();

        for (Client client : parc.getClients()) {
            if (client.getNom().toLowerCase().contains(searchText) ||
                client.getPrenom().toLowerCase().contains(searchText) ||
                String.valueOf(client.getNumPermis()).equals(searchText)) {

                Vector<String> categories = new Vector<>();
                for (Categorie categorie : client.getCategories()) {
                    categories.add(categorie.getCategorie());
                }

                resultats.add(new String[]{
                    String.valueOf(client.getNumPermis()),
                    client.getNom(),
                    client.getPrenom(),
                    client.getDateNaissance().toString(),
                    client.getAdresse(),
                    categories.toString()
                });
            }
        }

        if (resultats.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Aucun client trouvé pour la recherche : " + searchText);
            return;
        }

        String[][] tableData = resultats.toArray(new String[0][]);

        DefaultTableModel model = new DefaultTableModel(tableData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        view.getClientTable().setModel(model);
    }
}