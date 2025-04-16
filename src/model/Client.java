package model;

import java.time.LocalDate;
import java.util.Vector;

public class Client { 
    private int numPermis;
    private String nom;
    private String prenom;
    private LocalDate date_naissance;
    private String adresse;
    private Vector<Categorie> listCategories;
    private Vector<Location> locations;
    private Parc parc; 

    public Client(int numPermis,String nom, String prenom, LocalDate date_naissance, String adresse, Vector <Categorie> categories ) {
        if (numPermis<=0) {
            throw new IllegalArgumentException("le numero du permis est invalide.");
        }
        if (nom == null ) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        if (prenom == null ) {
            throw new IllegalArgumentException("Le prénom ne peut pas être vide.");
        }
        if (date_naissance == null || date_naissance.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de naissance ne peut pas être future.");
        }
        if (adresse == null ) {
            throw new IllegalArgumentException("L'adresse ne peut pas être vide.");
        }

        this.numPermis=numPermis;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.adresse = adresse;
        this.listCategories =categories;
        this.locations = new Vector<>();
    }

    public int getNumPermis() {
        return numPermis;
    }

    public void setNom(String nom) {
        if (nom == null ) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setPrenom(String prenom) {
        if (prenom == null ) {
            throw new IllegalArgumentException("Le prénom ne peut pas être vide.");
        }
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setDateNaissance(LocalDate date_naissance) {
        if (date_naissance == null || date_naissance.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de naissance ne peut pas être future.");
        }
        this.date_naissance = date_naissance;
    }

    public LocalDate getDateNaissance() {
        return date_naissance;
    }

    public int getAge() {
        return LocalDate.now().getYear() - date_naissance.getYear();
    }

    public void setAdresse(String adresse) {
        if (adresse == null ) {
            throw new IllegalArgumentException("L'adresse ne peut pas être vide.");
        }
        this.adresse = adresse;
    }

    public String getAdresse() {
        return adresse;
    }

   

    public void ajouterCategorie(Categorie c) {
        if (c == null) {
            throw new IllegalArgumentException("La catégorie ne peut pas être null.");
        }
        if (!listCategories.contains(c)) {
            listCategories.add(c);
        }
    }

    public Vector<Categorie> getCategories() {
        return new Vector<>(listCategories);
    }

    public void setCategories(Vector<Categorie> categories) {
        this.listCategories = (categories != null) ? new Vector<>(categories) : new Vector<>();
    }


    public void setLocations(Vector<Location> locs) {
        this.locations = (locs != null) ? new Vector<>(locs) : new Vector<>();
    }

    public Vector<Location> getLocations() {
        return new Vector<>(locations); //une copie pour eviter la modification de l'origin
    }

    public String toString() {
        return "CLIENT N°" + numPermis + "\n" +
               "      - NOM         : " + nom + "\n" +
               "      - PRÉNOM      : " + prenom + "\n" +
               "      - AGE         : " + getAge() + "\n" +
               "      - ADRESSE     : " + adresse + "\n" +
               "      - CATEGORIES  : " + (listCategories.isEmpty() ? "Aucune" : listCategories) + "\n" +
               "      - LOCATIONS   : " + (locations.isEmpty() ? "Aucune" : locations) + "\n" +
               "------------------------------------------------------";
    }
}