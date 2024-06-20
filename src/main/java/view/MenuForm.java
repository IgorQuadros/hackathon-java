package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuForm extends JFrame {
    private JLabel labelPrincipal;
    private JLabel labelCuidador;
    private JButton botaoCuidador;

    public MenuForm(){
        setTitle("Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;


        labelPrincipal = new JLabel("Escolha o que deseja fazer:");
        labelPrincipal.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(labelPrincipal, constraints);

        labelCuidador = new JLabel("Acessar CRUD de Cuidador");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(labelCuidador, constraints);

        botaoCuidador = new JButton("Cuidador");
        botaoCuidador.addActionListener(e -> abrirCuidadorForm());
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(botaoCuidador, constraints);

        add(panel);
        setLocationRelativeTo(null);
    }

    private void abrirCuidadorForm() {
        CuidadorForm cuidadorForm = new CuidadorForm();
        cuidadorForm.setVisible(true);
        dispose();
    }
}
