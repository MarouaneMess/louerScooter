package model;

import java.time.LocalDate;

public class Retour { 
    private int idLoc;
    private Location location;
    private double kmEffectue;
    private LocalDate dateRetourEffective;

    public Retour(double kmEffectue,Location location, LocalDate dateRetourEffective) {
        if (location == null) {
            throw new IllegalArgumentException("LOCATION NULLE");
        }
        if (kmEffectue < 0) {
            throw new IllegalArgumentException("Le kilométrage effectué ne peut pas être négatif.");
        }
        if (dateRetourEffective == null) {
            throw new IllegalArgumentException("La date de retour effective ne peut pas être null.");
        }
        if ( dateRetourEffective.isBefore(location.getDateDebut()) || dateRetourEffective.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de retour effective est fausse.");
        }
        

        this.idLoc = location.getId();
        this.location = location;
        this.kmEffectue = kmEffectue;
        this.dateRetourEffective = dateRetourEffective;
    }

    
    public int getId() {
        return idLoc;
    }
    public void setId(){
        this.idLoc=location.getId();
    }
    public Location getLocation() {
        return location;
    }

    public void setKmEffectue(double kmEffectue) {
        if (kmEffectue < 0) {
            throw new IllegalArgumentException("Le kilométrage effectué ne peut pas être négatif.");
        }
        this.kmEffectue = kmEffectue;
    }
    public double getKmEffectue() {
        return kmEffectue;
    }

    public void setDateRetourEffective(LocalDate dateRetourEffective) {
        if (dateRetourEffective == null) {
            throw new IllegalArgumentException("La date de retour effective ne peut pas être null.");
        }
        if (dateRetourEffective.isBefore(location.getDateDebut()) || dateRetourEffective.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de retour effective est fausse.");
        }
        this.dateRetourEffective = dateRetourEffective;
    }
    public LocalDate getDateRetourEffective() {
        return dateRetourEffective;
    }

    public String toString() {
        return "RETOUR N°" + location.getId() + "\n" +
               "      - KILOMÉTRAGE EFFECTUÉ : " + kmEffectue + " km\n" +
               "      - DATE RETOUR EFFECTIVE : " + dateRetourEffective + "\n" +
               "------------------------------------------------------";
    }
}
