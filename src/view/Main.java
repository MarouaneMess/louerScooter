package view;

import controller.ParcController;
import model.Parc;

public class Main {
    public static void main(String[] args) {

        Parc parc = new Parc("123 Rue des Scooters");
        // Créer la vue et le contrôleur
        ParcView parcView = new ParcView(parc, 800, 400);
        new ParcController(parcView, parc);

        // Afficher la vue
        parcView.setVisible(true);
    }
}