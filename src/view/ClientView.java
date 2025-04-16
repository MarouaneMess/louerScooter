package view;

import model.Parc;
import javax.swing.*;
import java.awt.*;

public class ClientView extends JPanel {
    private Parc parc;
    private JTextArea clientArea;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;

    public ClientView(Parc parc) {
        this.parc = parc;
        setLayout(new BorderLayout());

        clientArea = new JTextArea();
        clientArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(clientArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Ajouter Client");
        modifyButton = new JButton("Modifier Client");
        deleteButton = new JButton("Supprimer Client");

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void updateClientList(String clients) {
        clientArea.setText(clients);
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