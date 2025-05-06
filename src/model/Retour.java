package model;

import java.time.LocalDate;

public class Retour { 
    private final int idLoc;
    private final Location location;
    private final double kmEffectue;
    private final LocalDate dateRetourEffective;

    public Retour(double kmEffectue, Location location, LocalDate dateRetourEffective) {
        if (location == null) {
            throw new IllegalArgumentException("LOCATION NULLE");
        }
        if (kmEffectue < 0) {
            throw new IllegalArgumentException("Le kilométrage effectué ne peut pas être négatif.");
        }
        if (dateRetourEffective == null) {
            throw new IllegalArgumentException("La date de retour effective ne peut pas être null.");
        }
        if (dateRetourEffective.isBefore(location.getDateDebut()) ) {
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

    public Location getLocation() {
        return location;
    }

    public double getKmEffectue() {
        return kmEffectue;
    }

    public LocalDate getDateRetourEffective() {
        return dateRetourEffective;
    }

    public String toString() {
        return "RETOUR N°" + getId() + "\n" +
               "      - KILOMÉTRAGE EFFECTUÉ : " + kmEffectue + " km\n" +
               "      - DATE RETOUR EFFECTIVE : " + dateRetourEffective + "\n" +
               "------------------------------------------------------";
    }
}