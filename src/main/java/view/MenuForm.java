package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static javax.swing.SwingUtilities.invokeLater;

public class MenuForm extends JFrame {
    private JLabel labelPrincipal;
    private JLabel labelCuidador;
    private JButton botaoCuidador;
    private JLabel labelAgente;
    private JButton botaoAgente;
    private JLabel labelHistorico;
    private JButton botaoHistorico;
    private JLabel labelAgenda;
    private JButton botaoAgenda;
    private JLabel labelVacinas;
    private JButton botaoVacinas;
    private JLabel labelIdosos;
    private JButton botaoIdosos;

    public MenuForm(){
        setTitle("Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

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

        labelAgenda = new JLabel("Acessar Agenda de idosos");
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(labelAgenda, constraints);

        botaoAgenda = new JButton("Agenda");
        botaoAgenda.addActionListener(e -> abrirAgendaForm());
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(botaoAgenda, constraints);

        labelVacinas = new JLabel("Acessar Vacinas de idosos");
        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(labelVacinas, constraints);

        botaoVacinas = new JButton("Vacinas");
        botaoVacinas.addActionListener(e -> abrirVacinasForm());
        constraints.gridx = 1;
        constraints.gridy = 5;
        panel.add(botaoVacinas, constraints);

        labelIdosos = new JLabel("Acessar Idosos");
        constraints.gridx = 0;
        constraints.gridy = 6;
        panel.add(labelIdosos, constraints);

        botaoIdosos = new JButton("Idosos");
        botaoIdosos.addActionListener(e -> abrirIdososForm());
        constraints.gridx = 1;
        constraints.gridy = 6;
        panel.add(botaoIdosos, constraints);

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
    private void abrirAgendaForm() {
        AgendaForm form = new AgendaForm();
        form.setVisible(true);
        dispose();
    }
    private void abrirVacinasForm(){
        VacinasForm form = new VacinasForm();
        form.setVisible(true);
        dispose();
    }
    private void abrirIdososForm(){
        IdososForm form = new IdososForm();
        form.setVisible(true);
        dispose();
    }
}
