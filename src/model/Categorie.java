package model;

import java.util.Vector;

public class Categorie {
    private final String[] categories = {
        "A1", "A2", "A", "B", "B+E", "C", "C+E", "D", "D+E", "B1", "AM"
    };

    private String categorie;
    private Vector<Modele> modeles;
    private Vector<Client> clients;

    public Categorie(String categorie) {
        if (categorie == null ) {
            throw new IllegalArgumentException("La catégorie ne peut pas être vide.");
        }
        if (!estCategorieValide(categorie)) {
            throw new IllegalArgumentException("Catégorie invalide.");
        }
        this.categorie = categorie;
        this.modeles = new Vector<>();
        this.clients = new Vector<>();
    }

    private boolean estCategorieValide(String categorie) {
        for (String cat : categories) {
            if (cat.equalsIgnoreCase(categorie)) {
                return true;
            }
        }
        return false;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        if (!estCategorieValide(categorie)) {
            throw new IllegalArgumentException("Catégorie invalide.");
        }
        this.categorie = categorie;
    }

    public void ajouterModele(Modele m) {
        if (m == null) {
            throw new IllegalArgumentException("Le modèle ne peut pas être null.");
        }
        if (!modeles.contains(m)) {
            modeles.add(m);
        }
    }

    public Vector<Modele> getModeles() {
        return new Vector<>(modeles);
    }

    public void ajouterClient(Client c) {
        if (c == null) {
            throw new IllegalArgumentException("Le client ne peut pas être null.");
        }
        if (!clients.contains(c)) {
            clients.add(c);
        }
    }

    public Vector<Client> getClients() {
        return new Vector<>(clients);
    }

    public String toString() {
        return "Catégorie : " + categorie + "\n" +
               "Nombre de modèles : " + modeles.size() + "\n" +
               "Nombre de clients : " + clients.size() + "\n" +
               "------------------------------------------------------";
    }
}
