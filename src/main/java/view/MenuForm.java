package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuForm extends JFrame {
    private JLabel labelPrincipal;
    private JLabel labelCuidador;
    private JButton botaoCuidador;
    private JLabel labelAgente;
    private JButton botaoAgente;
    private JLabel labelHistorico;
    private JButton botaoHistorico;

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

        labelCuidador = new JLabel("Acessar cuidadores");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(labelCuidador, constraints);

        botaoCuidador = new JButton("Cuidador");
        botaoCuidador.addActionListener(e -> abrirCuidadorForm());
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(botaoCuidador, constraints);

        labelAgente = new JLabel("Acessar Agentes de saúde");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(labelAgente, constraints);

        botaoAgente = new JButton("Agente");
        botaoAgente.addActionListener(e -> abrirAgenteForm());
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(botaoAgente, constraints);

        labelHistorico = new JLabel("Acessar Historico de idosos");
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(labelHistorico, constraints);

        botaoHistorico = new JButton("Historico");
        botaoHistorico.addActionListener(e -> abrirHistoricoForm());
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(botaoHistorico, constraints);

        add(panel);
        setLocationRelativeTo(null);
    }

    private void abrirCuidadorForm() {
        CuidadorForm cuidadorForm = new CuidadorForm();
        cuidadorForm.setVisible(true);
        dispose();
    }
    private void abrirAgenteForm() {
        AgenteDeSaudeForm agenteForm = new AgenteDeSaudeForm();
        agenteForm.setVisible(true);
        dispose();
    }
    private void abrirHistoricoForm() {
        HistoricoForm historico = new HistoricoForm();
        historico.setVisible(true);
        dispose();
    }
}
