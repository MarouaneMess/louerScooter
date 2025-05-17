package model;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Vector;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;// Version de la classe pour la sérialisation

    private int numPermis;
    private String nom;
    private String prenom;
    private LocalDate date_naissance;
    private String adresse;
    private Parc parc; 
    private Vector<Categorie> listCategories;
    private Vector<Location> locations;

    public Client(Parc parc,int numPermis,String nom, String prenom, LocalDate date_naissance, String adresse, Vector <Categorie> categories ) {
        if (parc == null){
            throw new IllegalArgumentException ("Le parc doit etre defini.");
        }
        if (numPermis<=0) {
            throw new IllegalArgumentException("Le numero du permis est invalide.");
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

        this.parc=parc;
        this.numPermis=numPermis;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.adresse = adresse;
        this.listCategories =categories;
        this.locations = new Vector<>();
    }

    public void setNumPermis(int numP){
        this.numPermis=numP;
    }
    public int getNumPermis() {
        return numPermis;
    }
    public void setParc(Parc parc){
        this.parc = parc;
    }
    public Parc getParc(){
        return parc;
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
            c.ajouterClient(this); // synchronisation inverse
        }
    }

    public void retirerCategorie(Categorie c) {
        if (c == null) {
            throw new IllegalArgumentException("La catégorie ne peut pas être null.");
        }
        if (listCategories.remove(c)) {
            c.retirerClient(this); // synchronisation inverse
        } else {
            throw new IllegalArgumentException("La catégorie n'existe pas dans la liste.");
        }
    }
    public void setCategories(Vector<Categorie> categories) {
        if (categories==null){
            throw new IllegalArgumentException ("La liste des categories doivent etre definies");
        }
        this.listCategories =categories;
    }

    public Vector<Categorie> getCategories() {
        return listCategories;
    }


    public void setLocation(Vector<Location> loc) {
        if (loc==null){
            throw new IllegalArgumentException ("La liste des categories doivent etre definies");
        }
        this.locations = loc;
    }

    public Vector<Location> getLocations() {
        return locations;
    }
    public void ajouterLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("La location ne peut pas être null.");
        }
        locations.add(location);
    }
    public void retirerLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("La location ne peut pas être null.");
        }
        if (locations.remove(location)) {
            // Marquer le scooter associé comme disponible
            Scooter scooter = location.getScooter();
            if (scooter != null) {
                scooter.setDisponible(true);
                scooter.getLocations().remove(location); // Supprimer la location du scooter
            }
        } else {
            throw new IllegalArgumentException("La location n'existe pas dans la liste.");
        }
    }


    public String toString() {
        return "CLIENT N°" + numPermis + "\n" +
               "      - NOM         : " + nom + "\n" +
               "      - PRÉNOM      : " + prenom + "\n" +
               "      - AGE         : " + getAge() + "\n" +
               "      - ADRESSE     : " + adresse + "\n" +
               "      - LOCATIONS   : " + (locations.isEmpty() ? "0" : locations.size()) + "\n" ;
              
    }
}