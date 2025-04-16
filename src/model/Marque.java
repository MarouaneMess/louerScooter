package model;

import java.util.Vector;

public class Marque {
    private String nom;
    private Vector<Modele> modeles; 

    public Marque(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Nom de marque non valide");
        }
        this.nom = nom;
        this.modeles = new Vector<>();
    }

    public void setNom(String nom) {
        if (nom == null ) {
            throw new IllegalArgumentException("Nom de marque non valide");
        }
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void ajouterModele(Modele modele) {
        if (modele == null) {
            throw new IllegalArgumentException("Le modèle ne peut pas être null");
        }
        modeles.add(modele);
    }

    public Vector<Modele> getModeles() {
        return modeles;
    }

    public String toString() {
        return "Marque : " + nom + " | Nombre de modèles : " + modeles.size();
    }
}
