package model;

import java.io.Serializable;
import java.util.Vector;

public class Categorie implements Serializable {
    private static final long serialVersionUID = 1L; // Version de la classe pour la sérialisation
    public static final String[] categories = {
        "A1", "A2", "A", "AM"
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
    public void setCategorie(String categorie) {
        if (categorie == null || !estCategorieValide(categorie)) {
            throw new IllegalArgumentException("Catégorie invalide.");
        }
        this.categorie = categorie;
    }

    public String getCategorie() {
        return categorie;
    } 


    public void ajouterModele(Modele m) {
        if (m == null) {
            throw new IllegalArgumentException("Le modèle ne peut pas être null.");
        }
        if (!modeles.contains(m)) {
            modeles.add(m);
        }
    }
    public void retirerModele(Modele m) {
        if (m == null) {
            throw new IllegalArgumentException("Le modèle ne peut pas être null.");
        }
        if (!modeles.remove(m)) {
            throw new IllegalArgumentException("Le modèle n'existe pas dans la liste.");
        }
    }

    public void setModeles(Vector<Modele> mdl) {
        this.modeles = mdl;
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
    public void retirerClient(Client c) {
        if (c == null) {
            throw new IllegalArgumentException("Le client ne peut pas être null.");
        }
        if (!clients.remove(c)) {
            throw new IllegalArgumentException("Le client n'existe pas dans la liste.");
        }
    }

    public void setClients(Vector<Client> clt) {
        this.clients = clt;
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
