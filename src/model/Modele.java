package model;

import java.util.Vector;

public class Modele {
    private String nom;
    private Marque marque;
    private int puissance; // en chevaux
    private Vector<Categorie> listCategories; 
    private Vector<Scooter> scooters; 

    public Modele(String nom, Marque marque, int puissance, Vector<Categorie> categories) {
        if (nom == null ) {
            throw new IllegalArgumentException("Le nom du modèle ne peut pas être vide.");
        }
        if (marque == null) {
            throw new IllegalArgumentException("La marque ne peut pas être null.");
        }
        if (puissance <= 0) {
            throw new IllegalArgumentException("La puissance doit être positive.");
        }
        if (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException("Un modèle doit être associé à au moins une catégorie.");
        }

        this.nom = nom;
        this.marque = marque;
        this.puissance = puissance;
        this.listCategories = new Vector<>();
        this.scooters = new Vector<>();

        //ajouter les modeles au categories et le contraire
        for (Categorie c : categories) {
            ajouterCategorie(c);
        }
        // marque.ajouterModele(this);
    }

    public String getNom() {
        return nom;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setPuissance(int puissance) {
        if (puissance <= 0) {
            throw new IllegalArgumentException("La puissance doit être positive.");
        }
        this.puissance = puissance;
    }

    public int getPuissance() {
        return puissance;
    }

    public void ajouterCategorie(Categorie c) {
        if (c == null) {
            throw new IllegalArgumentException("La catégorie ne peut pas être null.");
        }
        if (!listCategories.contains(c)) {
            listCategories.add(c);
            c.ajouterModele(this); // synchronisation inverse
        }
    }
    public void retirerCategorie(Categorie c) {
        if (c == null) {
            throw new IllegalArgumentException("La catégorie ne peut pas être null.");
        }
        if (listCategories.remove(c)) {
            c.getModeles().remove(this); // synchronisation inverse
        } else {
            throw new IllegalArgumentException("La catégorie n'existe pas dans la liste.");
        }
    }

    public Vector<Categorie> getCategories() {
        return listCategories;
    }

    public void setCategories(Vector<Categorie> categories) {
        if (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException("Un modèle doit être associé à au moins une catégorie.");
        }

        // Nettoyage ancien lien
        for (Categorie c : this.listCategories) {
            c.getModeles().remove(this); // pour supprimer le modele de chaque categorie 
        }

        this.listCategories = new Vector<>();
        for (Categorie c : categories) {
            ajouterCategorie(c);
        }
    }

    public void setScooters(Vector<Scooter> scooters) {
        if (scooters == null) {
            throw new IllegalArgumentException("La liste des scooters ne peut pas être null.");
        }
        this.scooters = scooters;
        for (Scooter scooter : scooters) {
            scooter.setModele(this); // Synchroniser chaque scooter avec ce modèle
        }
    }

    public Vector<Scooter> getScooters() {
        return scooters;
    }

    public void ajouterScooter(Scooter s) {
        if (s == null) {
            throw new IllegalArgumentException("Le scooter ne peut pas être null.");
        }
        this.scooters.add(s);
    }
    public void retirerScooter(Scooter s) {
        if (s == null) {
            throw new IllegalArgumentException("Le scooter ne peut pas être null.");
        }
        if (!scooters.remove(s)) {
            throw new IllegalArgumentException("Le scooter n'existe pas dans la liste.");
        }
    }

    public String toString() {
        return nom + "\n" +
               "          - MARQUE         : " + marque.getNom() + "\n" +
               "          - PUISSANCE      : " + getPuissance() + " CV\n" +
               "          - CATEGORIES     : " + getCategories() + "\n" +
               "------------------------------------------------------";
    }
}
