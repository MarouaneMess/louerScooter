package view;

import model.Parc;
import model.Modele;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class ModeleView extends JPanel {
    private Parc parc;
    private JTextArea modeleArea;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;

    public ModeleView(Parc parc) {
        this.parc = parc;
        setLayout(new BorderLayout());

        modeleArea = new JTextArea();
        modeleArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(modeleArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Ajouter Modèle");
        modifyButton = new JButton("Modifier Modèle");
        deleteButton = new JButton("Supprimer Modèle");

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void updateModeleList(Vector<Modele> modeles) {
        StringBuilder sb = new StringBuilder();
        for (Modele modele : modeles) {
            sb.append(modele.toString()).append("\n");
        }
        modeleArea.setText(sb.toString());
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