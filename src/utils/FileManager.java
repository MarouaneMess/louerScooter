package utils;

import java.io.*;
import java.util.Vector;

public class FileManager {

    // Méthode pour sauvegarder les données dans un fichier
    public static <T> void saveToFile(String fileName, Vector<T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des données : " + e.getMessage());
        }
    }

    // Méthode pour charger les données depuis un fichier
    @SuppressWarnings("unchecked")
    public static <T> Vector<T> loadFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Vector<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Fichier non trouvé : " + fileName + ". Un nouveau fichier sera créé.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement des données : " + e.getMessage());
        }
        return new Vector<>();
    }
}
