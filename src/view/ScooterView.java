package view;

import model.Parc;
import javax.swing.*;
import java.awt.*;

public class ScooterView extends JPanel {
    private Parc parc;
    private JTextArea scooterArea;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;

    public ScooterView(Parc parc) {
        this.parc = parc;
        setLayout(new BorderLayout());

        scooterArea = new JTextArea();
        scooterArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(scooterArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Ajouter Scooter");
        modifyButton = new JButton("Modifier Scooter");
        deleteButton = new JButton("Supprimer Scooter");
        
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void updateScooterList(String scooters) {
        scooterArea.setText(scooters);
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getModifyButton() {
        return modifyButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }
}