package view;

import model.Parc;
import model.Location;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class LocationView extends JPanel {
    private Parc parc;
    private JTextArea locationArea;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;

    public LocationView(Parc parc) {
        this.parc = parc;
        setLayout(new BorderLayout());

        locationArea = new JTextArea();
        locationArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(locationArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Ajouter Location");
        modifyButton = new JButton("Modifier Location");
        deleteButton = new JButton("Supprimer Location");

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void updateLocationList(Vector<Location> locations) {
        StringBuilder sb = new StringBuilder();
        for (Location location : locations) {
            sb.append(location.toString()).append("\n");
        }
        locationArea.setText(sb.toString());
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