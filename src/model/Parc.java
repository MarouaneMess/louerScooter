package model;

import java.util.Vector;
import java.time.LocalDate;

public class Parc {
    private static int auto_inc=0;
    private int id ;
    private String adresse;
    private Vector<Scooter> scooters;
    private Vector<Client> clients;
    private Vector<Marque> marques;

    public Parc( String adresse) {
        if (adresse == null || adresse.isEmpty()) {
            throw new IllegalArgumentException("L'adresse du parc ne peut pas être vide.");
        }

        this.adresse = adresse;
        this.id = ++auto_inc;
        this.scooters = new Vector<>();
        this.clients = new Vector<>();
        this.marques = new Vector<>();
    }

    public int getId() {
        return id;
    }
    public void setAdresse(String adresse) {
        if (adresse == null ) {
            throw new IllegalArgumentException("L'adresse du parc ne peut pas être vide.");
        }
        this.adresse = adresse;
    }
    public String getAdresse() {
        return adresse;
    }

    public Vector<Scooter> getScooters() {
        return scooters;
    }

    public Vector<Client> getClients() {
        return clients;
    }

    public Vector<Marque> getMarques() {
        return marques;
    }

    public void ajouterScooter(Scooter scooter) {
        if (scooter == null) {
            throw new IllegalArgumentException("Le scooter ne peut pas être null.");
        }
        scooters.add(scooter);
    }

    public void ajouterClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Le client ne peut pas être null.");
        }
        clients.add(client);
    }

    public void ajouterMarque(Marque marque) {
        if (marque == null) {
            throw new IllegalArgumentException("La marque ne peut pas être null.");
        }
        marques.add(marque);
    }

    public void afficherScooters() {
        if (scooters.isEmpty()) {
            System.out.println("Aucun scooter dans le parc.");
        } else {
            for (Scooter s : scooters) {
                System.out.println(s);
            }
        }
    }
    public Scooter rechercherScooter(int idScooter) {
        for (Scooter scooter : scooters) {
            if (scooter.getId() == idScooter) {
                return scooter;
            }
        }
        return null;
    }

    public void afficherClients() {
        if (clients.isEmpty()) {
            System.out.println("Aucun client enregistré.");
        } else {
            for (Client c : clients) {
                System.out.println(c);
            }
        }
    }

    public void afficherMarques() {
        if (marques.isEmpty()) {
            System.out.println("Aucune marque enregistrée.");
        } else {
            for (Marque m : marques) {
                System.out.println("- " + m.getNom());
            }
        }
    }


    ////// des fonctionnalites a revoir 
    public boolean louerScooter(int idScooter, int idClient, LocalDate dateDebut, LocalDate dateRetour) {
    for (Scooter scooter : scooters) {
        if (scooter.getId() == idScooter) {
            if (!scooter.isDisponible()) {
                System.out.println("Ce scooter est déjà en location.");
                return false;
            }
            for (Client client : clients) {
                if (client.getNumPermis() == idClient) {
                    Location location = new Location(client, scooter, dateDebut, dateRetour);
                    scooter.setDisponible(false);
                    return true;
                }
            }
            System.out.println("Client introuvable.");
            return false;
        }
    }
    System.out.println("Scooter introuvable.");
    return false;
}

public boolean retournerScooter(int idScooter, double kmEffectue) {
    for (Scooter scooter : scooters) {
        if (scooter.getId() == idScooter) {
            if (scooter.isDisponible()) {
                System.out.println("Ce scooter n'est pas en location.");
                return false;
            }
            

            return true;
        }
    }
    System.out.println("Scooter introuvable.");
    return false;
}



public void afficherResumeParc() {
    int total = scooters.size();
    int enLocation = 0;
    double kmTotal = 0;

    System.out.println("\n=== État Résumé du Parc ===");
    for (Scooter scooter : scooters) {
        kmTotal += scooter.getKm();
        if (!scooter.isDisponible()) enLocation++;
    }
    System.out.println("PARC - Adresse : " + adresse );
    System.out.println("    - Nombre de clients  : " + clients.size() );
    System.out.println("    - Nombre de marques  : " + marques.size() );
    System.out.println("    -Nombre total de scooters : " + total);
    System.out.println("    -Nombre de scooters en location : " + enLocation);
    System.out.println("    -Nombre de scooters disponibles : " + (total - enLocation));
    System.out.println("    -Kilométrage moyen : " + (total > 0 ? kmTotal / total : 0));
}

}
