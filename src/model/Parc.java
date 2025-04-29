package model;

import java.util.Vector;
import java.time.LocalDate;

public class Parc {
    private static int auto_inc = 0;
    private int id;
    private String adresse;
    private Vector<Scooter> scooters;
    private Vector<Client> clients;
    private Vector<Marque> marques;

    public Parc(String adresse) {
        if (adresse == null) {
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
        if (adresse == null) {
            throw new IllegalArgumentException("L'adresse du parc ne peut pas être vide.");
        }
        this.adresse = adresse;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setScooters(Vector<Scooter> scooters) {
        if (scooters == null) {
            throw new IllegalArgumentException("La liste des scooters doit etre definie. ");
        }
        this.scooters = scooters;
    }

    public Vector<Scooter> getScooters() {
        return scooters;
    }

    public void setClients(Vector<Client> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("La liste des clients doit etre definie. ");
        }
        this.clients = cls;
    }

    public Vector<Client> getClients() {
        return clients;
    }

    public void setMarques(Vector<Marque> marqs) {
        if (marqs == null) {
            throw new IllegalArgumentException("La liste des marques doit etre definie. ");
        }
        this.marques = marqs;
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

    public Scooter rechercherScooter(int idScooter) {
        for (Scooter scooter : scooters) {
            if (scooter.getId() == idScooter) {
                return scooter;
            }
        }
        return null;
    }

    public Client rechercherClient(int idClient) {
        for (Client client : clients) {
            if (client.getNumPermis() == idClient) {
                return client;
            }
        }
        return null;

    }

    public Marque rechercherMarque(String nomMarq) {
        for (Marque mrq : marques) {
            if (mrq.getNom().equals(nomMarq)) {
                return mrq;
            }
        }
        return null;
    }

    public boolean supprimerClient(int idClient) {
        Client clientASupprimer = rechercherClient(idClient);
        if (clientASupprimer != null) {
            // Supprimer toutes les locations associées à ce client
            Vector<Location> locationsASupprimer = new Vector<>(clientASupprimer.getLocations());
            for (Location loc : locationsASupprimer) {
                Scooter scooter = loc.getScooter();
                if (scooter != null) {
                    scooter.setDisponible(true); // Marquer le scooter comme disponible
                }
            }
            // Supprimer le client de la liste
            clients.remove(clientASupprimer);
            return true;
        }
        System.out.println("Client introuvable.");
        return false;
    }

    public boolean supprimerScooter(int idScooter) {
        Scooter scooterASupprimer = rechercherScooter(idScooter);
        if (scooterASupprimer != null) {
            // Supprimer toutes les locations associées à ce scooter
            for (Client client : clients) {
                //ici on collecte la liste des locs avant les supprimer pour eviter "ConcurrentModificationException : exception lors de la modification du Vector pendant l'itération."
                Vector<Location> locationsASupprimer = new Vector<>();
                for (Location loc : client.getLocations()) {
                    if (loc.getScooter().equals(scooterASupprimer)) {
                        locationsASupprimer.add(loc);
                    }
                }
                // Supprimer les locations
                client.getLocations().removeAll(locationsASupprimer);
            }
            // Supprimer le scooter du parc
            scooters.remove(scooterASupprimer);
            return true;
        }
        System.out.println("Scooter introuvable.");
        return false;
    }

    public boolean supprimerMarque(String nomMarque) {
        Marque marqueASupprimer = rechercherMarque(nomMarque);
        if (marqueASupprimer != null) {
             // Supprimer tous les modèles associés à cette marque
            Vector<Modele> modeles = marqueASupprimer.getModeles();
            for (Modele modele : modeles) {
                Vector<Scooter> scootersASupprimer = new Vector<>(modele.getScooters());
                for (Scooter scooter : scootersASupprimer) {
                    supprimerScooter(scooter.getId()); // Gérer les locations et supprimer le scooter
                }
                marqueASupprimer.retirerModele(modele);
            }
            marques.remove(marqueASupprimer);
            return true;
        }
        System.out.println("Marque introuvable.");
        return false;
    }

    public boolean louerScooter(int idScooter, int idClient, LocalDate dateDebut, LocalDate dateRetourPrev) {
        Scooter scooter = rechercherScooter(idScooter);
        if (scooter == null) {
            System.out.println("Scooter introuvable.");
            return false;
        }

        if (!scooter.isDisponible()) {
            System.out.println("Ce scooter est déjà en location.");
            return false;
        }

        Client client = rechercherClient(idClient);
        if (client == null) {
            System.out.println("Client introuvable.");
            return false;
        }
        new Location(client, scooter, dateDebut, dateRetourPrev);
        scooter.setDisponible(false);
        return true;
    }

    public boolean retournerScooter(int idScooter, double kmEffectue) {
        for (Client client : clients) {
            for (Location location : client.getLocations()) {
                if (location.getScooter().getId() == idScooter) {
                    if (location.getRetour() != null) {
                        System.out.println("Le scooter a déjà été retourné.");
                        return false;
                    }

                    location.enregistrerRetour(kmEffectue, LocalDate.now());
                    return true;
                }
            }
        }
        System.out.println("Scooter introuvable.");
        return false;
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

    public void afficherLocations() {
        boolean aDesLocations = false;
        for (Client cl : clients) {
            for (Location loc : cl.getLocations()) {
                System.out.println(loc);
                aDesLocations = true;
            }
        }
        if (!aDesLocations) {
            System.out.println("Aucune location enregistrée.");
        }
    }

    public void afficherResumeParc() {
        int total = scooters.size();
        int enLocation = 0;
        double kmTotal = 0;

        System.out.println("\n=== État Résumé du Parc ===");
        for (Scooter scooter : scooters) {
            kmTotal += scooter.getKm();
            if (!scooter.isDisponible())
                enLocation++;
        }
        System.out.println("PARC - Adresse : " + adresse);
        System.out.println("    - Nombre de clients  : " + clients.size());
        System.out.println("    - Nombre de marques  : " + marques.size());
        System.out.println("    - Nombre total de scooters : " + total);
        System.out.println("    - Nombre de scooters en location : " + enLocation);
        System.out.println("    - Nombre de scooters disponibles : " + (total - enLocation));
        System.out.println("    - Kilométrage moyen : " + (total > 0 ? kmTotal / total : 0));
    }
}