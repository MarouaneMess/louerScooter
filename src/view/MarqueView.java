package view;

import model.Parc;
import javax.swing.*;
import java.awt.*;

public class MarqueView extends JPanel {
    private Parc parc;
    private JTextArea marqueArea;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;

    public MarqueView(Parc parc) {
        this.parc = parc;
        setLayout(new BorderLayout());

        marqueArea = new JTextArea();
        marqueArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(marqueArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Ajouter Marque");
        modifyButton = new JButton("Modifier Marque");
        deleteButton = new JButton("Supprimer Marque");

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void updateMarqueList(String marques) {
        marqueArea.setText(marques);
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