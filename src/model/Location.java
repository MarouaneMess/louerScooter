package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Location {
    private static int compteurId = 0; 
    private int id;
    private LocalDate dateDebut;
    private LocalDate dateRetourPrevue;
    private Retour retour; 
    private Client client;
    private Scooter scooter;
    

    public Location(Client client, Scooter scooter, LocalDate dateDebut, LocalDate dateRetourPrevue) {
        if (client == null) {
            throw new IllegalArgumentException("Le client ne peut pas être null.");
        }
        if (scooter == null) {
            throw new IllegalArgumentException("Le scooter ne peut pas être null.");
        }
        if (!scooter.isDisponible()) {
            throw new IllegalArgumentException("Le scooter est déjà en location.");
        }
        if (dateDebut == null || dateDebut.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de début ne peut pas être dans le futur.");
        }
        if (dateRetourPrevue == null || dateRetourPrevue.isBefore(dateDebut)) {
            throw new IllegalArgumentException("La date de retour prévue ne peut pas être avant la date de début.");
        }

        this.id = ++compteurId;
        this.client = client;
        this.scooter = scooter;
        this.dateDebut = dateDebut;
        this.dateRetourPrevue = dateRetourPrevue;
        this.retour = null; 

        scooter.setDisponible(false);
        scooter.ajouterLocation(this);
        client.ajouterLocation(this);
    }

    public int getId() {
        return id;
    }
    
    public Client getClient() {
        return client;
    }

    public Scooter getScooter() {
        return scooter;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public Retour getRetour() {
        return retour;
    }


    public void enregistrerRetour(double kmEffectue, LocalDate dateRetourEffective) {
        if (dateRetourEffective == null || dateRetourEffective.isBefore(dateDebut)) {
            throw new IllegalArgumentException("La date de retour effective ne peut pas être avant la date de début.");
        }
        if (kmEffectue < 0) {
            throw new IllegalArgumentException("Le kilométrage effectué ne peut pas être négatif.");
        }

        this.retour = new Retour(kmEffectue,this,dateRetourEffective);

        scooter.setKm(scooter.getKm() + kmEffectue);
        scooter.setDisponible(true);
    }


    public double getPrixPinalite() {
        if (retour == null || retour.getDateRetourEffective() == null) {
            throw new IllegalStateException("Le retour doit être enregistré avant de calculer les pénalités.");
        }
    
        if (retour.getDateRetourEffective().isAfter(dateRetourPrevue)) {
            long nbrJ = ChronoUnit.DAYS.between(dateRetourPrevue, retour.getDateRetourEffective());
            // le prix total de nbr du jours depasses *35%
            double penalite = nbrJ * scooter.getPrixJour() * 0.35;
            return (nbrJ * scooter.getPrixJour()) + penalite;
        }
        return 0;
    }


    // le prix total de la location
    // ChronoUnit is an enumeration in the java.time.temporal package that provides a standard set of date and time units
    public double getPrixLocation() {
        if (retour == null) {
            throw new IllegalStateException("Le retour n'a pas encore été enregistré.");
        }

        long nbrJours = ChronoUnit.DAYS.between(dateDebut, dateRetourPrevue);
        return nbrJours * scooter.getPrixJour() + getPrixPinalite();
    }

    public String toString() {
        return "LOCATION N°" + id + "\n" +
               "      - DATE DEBUT        : " + dateDebut + "\n" +
               "      - DATE RETOUR PREVUE: " + dateRetourPrevue + "\n" +
               "      - RETOUR            : " + (retour != null ? retour : "Non retourné") ;  
    }

  
}
