package view;

import model.Parc;

public class Main {
    public static void main(String[] args) {
        Parc parc = new Parc("123 Rue des Scooters");
        new VueMain(parc); 
    }
}