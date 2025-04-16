package model;

import java.util.Vector;

public class Modele {
    private String nom;
    private Marque marque;
    private int puissance; // en chevaux
    private Vector<Categorie> listCategories; // Liste des catégories nécessaires
    private Vector<Scooter> scooters; // Liste des scooters associés

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

        // Ajout du modèle à chaque catégorie et vice versa
        for (Categorie c : categories) {
            ajouterCategorie(c);
        }
    }

    public String getNom() {
        return nom;
    }

    public void setMarque(Marque marque) {
        if (marque == null) {
            throw new IllegalArgumentException("La marque ne peut pas être null.");
        }
        this.marque = marque;
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

    public Vector<Categorie> getCategories() {
        return new Vector<>(listCategories);
    }

    public void setCategories(Vector<Categorie> categories) {
        if (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException("Un modèle doit être associé à au moins une catégorie.");
        }

        // Nettoyage ancien lien
        for (Categorie c : this.listCategories) {
            c.getModeles().remove(this); // si tu gères bien ça dans Categorie, à vérifier
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
        this.scooters = new Vector<>(scooters);
    }

    public Vector<Scooter> getScooters() {
        return new Vector<>(scooters);
    }

    public void ajouterScooter(Scooter s) {
        if (s == null) {
            throw new IllegalArgumentException("Le scooter ne peut pas être null.");
        }
        this.scooters.add(s);
    }

    @Override
    public String toString() {
        return nom + "\n" +
               "          - MARQUE         : " + marque.getNom() + "\n" +
               "          - PUISSANCE      : " + getPuissance() + " CV\n" +
               "          - CATEGORIES     : " + getCategories() + "\n" +
               "------------------------------------------------------";
    }
}
