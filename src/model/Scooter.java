package model;

import java.time.LocalDate;
import java.util.Vector;

public class Scooter {
    private int immatriculation;
    private Modele modele;
    private LocalDate annee_sortie;
    private boolean disponible;
    private double km;
    private double prix_Jour;
    private Vector<Location> locations;
    private Parc parc;


 
    public Scooter(int immatriculation, Modele modele, LocalDate annee_sortie, boolean disponible, double km, double prix_Jour, Parc parc) {
        if (annee_sortie.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("L'année de sortie ne peut pas être future.");
        }
        if (km < 0) {
            throw new IllegalArgumentException("Le nombre de kilomètres ne peut pas être négatif.");
        }
        if (prix_Jour < 0) {
            throw new IllegalArgumentException("Le prix du jour ne peut pas être négatif.");
        }
        if (parc == null) {
            throw new IllegalArgumentException("Le parc ne peut pas être null.");
        }

        this.immatriculation  = immatriculation;
        this.modele = modele;
        this.annee_sortie = annee_sortie;
        this.disponible = disponible;
        this.km = km;
        this.prix_Jour = prix_Jour;
        this.locations = new Vector<>();
        this.parc = parc;
    
    }

    public void setId(int id ){
        this.immatriculation=id;
    }
    public int getId() {
        return immatriculation;
    }

    public Modele getModele() {
        return modele;
    }

    public LocalDate getAnneeSortie() {
        return annee_sortie;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean isDisponible() {
        return disponible;
    }
    public void setKm(double km) {
        if (km < 0) {
            throw new IllegalArgumentException("Le nombre de kilomètres ne peut pas être négatif.");
        }
        this.km = km;
    }

    public double getKm() {
        return km;
    }

    public void setPrixJour(double p) {
        if (p < 0) {
            throw new IllegalArgumentException("Le prix du jour ne peut pas être négatif.");
        }
        this.prix_Jour = p;
    }
    public double getPrixJour() {
        return prix_Jour;
    }

    public void setLocation(Vector<Location> loc) {
        this.locations = loc;
    }
    
    public void ajouterLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("La location ne peut pas être null.");
        }
        locations.add(location);
    }

    public Vector<Location> getLocations() {
        return new Vector<>(locations);
    }
    public void setParc(Parc p){
        this.parc = p;
    }
    public Parc getParc() {
        return parc;
    }
    public String toString() {
        return 
                "SCOOTER N° " + getId() +"\n" +
        "      - MODELE        : "  + getModele() + "\n" + 
        "      - ANNEE_DE_SORTIE : "  + getAnneeSortie() +"\n" +
        "      - KILOMETRAGE : " + getKm() +" Km "+"\n" +
        "      - PRIX_JOUR : " + getPrixJour() +" Euros "+"\n"+
        "      - EN LOCATION : " + (isDisponible() ? "OUI" : "NON") + "\n" +
        "      - PARC        : " + (parc != null ? parc.getAdresse() : "Non assigné") + "\n" +
        "      - NOMBRE LOCATIONS: " + locations.size() + "\n" +
        "----------------------------------------------------------------";
    }
      

    
}