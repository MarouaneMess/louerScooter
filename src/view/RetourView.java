package view;

import model.Parc;
import model.Retour;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class RetourView extends JPanel {
    private Parc parc;
    private JTextArea retourArea;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;

    public RetourView(Parc parc) {
        this.parc = parc;
        setLayout(new BorderLayout());

        retourArea = new JTextArea();
        retourArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(retourArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Ajouter Retour");
        modifyButton = new JButton("Modifier Retour");
        deleteButton = new JButton("Supprimer Retour");

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void updateRetourList(Vector<Retour> retours) {
        StringBuilder sb = new StringBuilder();
        for (Retour retour : retours) {
            sb.append(retour.toString()).append("\n");
        }
        retourArea.setText(sb.toString());
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