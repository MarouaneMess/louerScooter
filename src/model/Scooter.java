package model;

import java.time.LocalDate;
import java.util.Vector;

public class Scooter {
    private String immatriculation;
    private Modele modele;
    private LocalDate annee_sortie;
    private boolean disponible;
    private double km;
    private double prix_Jour;
    private Vector<Location> locations;
    private Parc parc;


 
    public Scooter(String immatriculation, Modele modele, LocalDate annee_sortie, boolean disponible, double km, double prix_Jour, Parc parc) {
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
        modele.ajouterScooter(this);
    
    }

    public void setId(String id ){
        this.immatriculation=id;
    }
    public String getId() {
        return immatriculation;
    }
    public void setModele(Modele mdl){
        this.modele=mdl;
    }

    public Modele getModele() {
        return modele;
    }

    public int getAnneeSortie() {
        return annee_sortie.getYear();
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

    public void retirerLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("La location ne peut pas être null.");
        }
        if (locations.remove(location)) {
            location.getClient().retirerLocation(location); 
        } else {
            throw new IllegalArgumentException("La location n'existe pas dans la liste des locations de ce scooter.");
        }
    }

    
    public Vector<Location> getLocations() {
        return locations;
    }
    public void setParc(Parc p){
        this.parc = p;
    }
    public Parc getParc() {
        return parc;
    }
    @Override
    public String toString() {
        return "SCOOTER N° " + getId() + "\n" +
               "      - MODELE        : " + getModele().getNom() + "\n" +
               "      - ANNEE         : " + getAnneeSortie() + "\n" +
               "      - KILOMETRAGE   : " + getKm() + " Km\n" +
               "      - PRIX/JOUR     : " + getPrixJour() + " Euros\n" +
               "      - DISPONIBLE    : " + (isDisponible() ? "OUI" : "NON") ;
    }
      

    
}