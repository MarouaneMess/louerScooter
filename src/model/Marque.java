package model;

import java.util.Vector;

public class Marque {
    private String nom;
    private Parc parc;
    private Vector<Modele> modeles; 


    public Marque(Parc parc,String nom) {
        if (nom == null ) {
            throw new IllegalArgumentException("Nom de marque non valide");
        }
        if (parc == null){
            throw new IllegalArgumentException ("Le parc ne doit pas etre null");
        }
        this.parc=parc;
        this.nom = nom;
        this.modeles = new Vector<>();
    }
    public void setParc(Parc parc){
        if ( parc==null) {
            throw new IllegalArgumentException ("Le parc ne doit pas etre null");
        }
        this.parc=parc;
    }
    public Parc getParc(){
        return parc;
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


    public void setModeles(Vector<Modele> mdl) {
        this.modeles = mdl;
    }

    public Vector<Modele> getModeles() {
        return modeles;
    }

    public void ajouterModele(Modele modele) {
        if (modele == null) {
            throw new IllegalArgumentException("Le modèle ne peut pas être null");
        }
        if (modeles.contains(modele)){
            throw new IllegalArgumentException ("Le modele existe deja ");
        }
        modeles.add(modele);
    }
    public void retirerModele(Modele modele) {
        if (modele == null) {
            throw new IllegalArgumentException("Le modèle ne peut pas être null");
        }
        if (!modeles.remove(modele)) {
            throw new IllegalArgumentException("Le modèle n'existe pas dans la liste");
        }
    }

    public String toString() {
        return "Marque : " + nom + " | Nombre de modèles : " + modeles.size();
    }
}
