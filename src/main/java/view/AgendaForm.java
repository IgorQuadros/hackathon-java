package view;

import model.Agenda;
import model.AgenteDeSaude;
import model.Idoso;
import service.AgendaService;
import service.AgenteDeSaudeService;
import service.IdosoService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import static javax.swing.JOptionPane.*;
import static javax.swing.SwingUtilities.*;

public class AgendaForm extends JFrame{
    private JFrame frame;
    private IdosoService idosoService;
    private AgenteDeSaudeService agenteDeSaudeService;
    private AgendaService agendaService;

    private JLabel labelId;
    private JTextField campoId;
    private JLabel labelIdoso;
    private JComboBox<Idoso> comboBoxIdoso;
    private JLabel labelAgente;
    private JComboBox<AgenteDeSaude> comboBoxAgente;
    private JLabel labelData;
    private JTextField campoData;
    private JLabel labelHora;
    private JTextField campoHora;
    private JLabel labelDescricao;
    private JTextField campoDescricao;

    private JButton botaoSalvar;
    private JButton botaoCancelar;
    private JButton botaoDeletar;
    private JButton botaoVoltar;
    private JTable tabela;

    public AgendaForm() {
        frame = new JFrame();
        idosoService = new IdosoService();
        agenteDeSaudeService = new AgenteDeSaudeService();
        agendaService = new AgendaService();

        frame.setTitle("Agendamento de Visitas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        frame.getContentPane().add(montarPainelEntrada(), BorderLayout.NORTH);
        frame.getContentPane().add(montarPainelSaida(), BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel montarPainelEntrada() {
        JPanel painelEntrada = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        labelId = new JLabel("ID:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        painelEntrada.add(labelId, constraints);

        campoId = new JTextField(20);
        campoId.setEnabled(false);
        constraints.gridx = 1;
        constraints.gridy = 0;
        painelEntrada.add(campoId, constraints);

        labelIdoso = new JLabel("Idoso:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        painelEntrada.add(labelIdoso, constraints);

        comboBoxIdoso = new JComboBox<>();
        carregarIdosos();
        constraints.gridx = 1;
        constraints.gridy = 1;
        painelEntrada.add(comboBoxIdoso, constraints);

        labelAgente = new JLabel("Agente de Saúde:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        painelEntrada.add(labelAgente, constraints);

        comboBoxAgente = new JComboBox<>();
        carregarAgentesDeSaude();
        constraints.gridx = 1;
        constraints.gridy = 2;
        painelEntrada.add(comboBoxAgente, constraints);

        labelData = new JLabel("Data (yyyy-mm-dd):");
        constraints.gridx = 0;
        constraints.gridy = 3;
        painelEntrada.add(labelData, constraints);

        campoData = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 3;
        painelEntrada.add(campoData, constraints);

        labelHora = new JLabel("Hora (hh:mm:ss):");
        constraints.gridx = 0;
        constraints.gridy = 4;
        painelEntrada.add(labelHora, constraints);

        campoHora = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 4;
        painelEntrada.add(campoHora, constraints);

        labelDescricao = new JLabel("Descrição:");
        constraints.gridx = 0;
        constraints.gridy = 5;
        painelEntrada.add(labelDescricao, constraints);

        campoDescricao = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 5;
        painelEntrada.add(campoDescricao, constraints);

        montarPainelBotoes(constraints, painelEntrada);

        return painelEntrada;
    }

    private void montarPainelBotoes(GridBagConstraints constraints, JPanel painelEntrada) {
        JPanel painelBotoes = new JPanel(new GridBagLayout());
        GridBagConstraints botoesConstraints = new GridBagConstraints();
        botoesConstraints.insets = new Insets(5, 5, 5, 5);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(e -> executarBotaoSalvar());
        constraints.gridx = 0;
        constraints.gridy = 0;
        painelBotoes.add(botaoSalvar, botoesConstraints);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> limparCampos());
        constraints.gridx = 1;
        constraints.gridy = 0;
        painelBotoes.add(botaoCancelar, botoesConstraints);

        botaoDeletar = new JButton("Deletar");
        botaoDeletar.addActionListener(e -> executarBotaoDeletar());
        constraints.gridx = 2;
        constraints.gridy = 0;
        painelBotoes.add(botaoDeletar, botoesConstraints);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.addActionListener(e -> executarBotaoVoltar());
        constraints.gridx = 3;
        constraints.gridy = 0;
        painelBotoes.add(botaoVoltar, botoesConstraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        painelEntrada.add(painelBotoes, constraints);
    }

    private JPanel montarPainelSaida() {
        JPanel painelSaida = new JPanel(new BorderLayout());

        tabela = new JTable();
        tabela.setDefaultEditor(Object.class, null);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setModel(carregarDados());
        tabela.getSelectionModel().addListSelectionListener(e -> selecionarAgendamento(e));

        JScrollPane scrollPane = new JScrollPane(tabela);

        painelSaida.add(scrollPane, BorderLayout.CENTER);
        return painelSaida;
    }

    private TableModel carregarDados() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Idoso");
        model.addColumn("Agente de Saúde");
        model.addColumn("Data");
        model.addColumn("Hora");
        model.addColumn("Descrição");

        agendaService.listarAgendamentos().forEach(agenda -> model.addRow(new Object[]{
                agenda.getId(), agenda.getIdosoId(), agenda.getAgenteDeSaudeId(),
                agenda.getData(), agenda.getHora(), agenda.getDescricao()}));

        return model;
    }

    private void carregarIdosos() {
        try {
            List<Idoso> idosos = idosoService.listarIdosos();
            comboBoxIdoso.removeAllItems();
            for (Idoso idoso : idosos) {
                comboBoxIdoso.addItem(idoso);
            }
        } catch (Exception e) {
            showMessageDialog(this,
                    "Erro ao carregar lista de idosos: " + e.getMessage(),
                    "Erro", ERROR_MESSAGE);
        }
    }

    private void carregarAgentesDeSaude() {
        try{
            List<AgenteDeSaude> agentes = agenteDeSaudeService.listarAgentes();
            for (AgenteDeSaude agente : agentes) {
                comboBoxAgente.addItem(agente);
            }
        }catch(Exception e){
            showMessageDialog(this, "Erro ao carregar lista de agentes de saúde: "
                    + e.getMessage() + "Erro" + ERROR_MESSAGE);
        }
    }

    private void executarBotaoSalvar() {
        try {
            if(comboBoxIdoso.getSelectedItem() == null) {
                throw new Exception("Selecione um idoso.");
            } else if (comboBoxAgente.getSelectedItem() == null) {
                throw new Exception("Selecione um agente de saúde.");
            }

            Agenda agenda = construirAgendamento();
            if (agenda != null) {
                agendaService.salvar(agenda);
                limparCampos();
                tabela.setModel(carregarDados());
            }
        } catch (Exception e) {
            showMessageDialog(frame, e.getMessage(), "Erro ao Salvar", ERROR_MESSAGE);
        }
    }

    private void executarBotaoDeletar() {
        if (campoId.getText().isEmpty()) {
            showMessageDialog(
                    frame, "É necessário selecionar um item para deletar!",
                    "Aviso", INFORMATION_MESSAGE);
            return;
        }

        int id = Integer.parseInt(campoId.getText());
        try {
            agendaService.excluir(id);
            showMessageDialog(
                    frame, "Agendamento deletado com sucesso!",
                    "Sucesso", INFORMATION_MESSAGE);
        } catch (SQLException e) {
            showMessageDialog(
                    frame, "Erro ao tentar deletar o agendamento: " + e.getMessage(),
                    "Erro", ERROR_MESSAGE);
        }
        limparCampos();
        tabela.setModel(carregarDados());
    }

    private void executarBotaoVoltar() {
        frame.dispose();
        MenuForm menuForm = new MenuForm();
        menuForm.setVisible(true);
    }

    private void limparCampos() {
        campoId.setText("");
        comboBoxIdoso.setSelectedIndex(-1);
        comboBoxAgente.setSelectedIndex(-1);
        campoData.setText("");
        campoHora.setText("");
        campoDescricao.setText("");
    }

    private Agenda construirAgendamento() {
        Idoso idosoSelecionado = (Idoso) comboBoxIdoso.getSelectedItem();
        AgenteDeSaude agenteSelecionado = (AgenteDeSaude) comboBoxAgente.getSelectedItem();
        String data = campoData.getText().trim();
        String hora = campoHora.getText().trim();
        String descricao = campoDescricao.getText().trim();

        Date dataAgenda = null;
        if (!data.isEmpty()) {
            try {
                dataAgenda = Date.valueOf(data);
            } catch (IllegalArgumentException e) {
                showMessageDialog(frame,
                        "Data de agendamento inválida! Deve estar no formato YYYY-MM-DD.",
                        "Erro de Validação", ERROR_MESSAGE);
                return null;
            }
        }

        Time horaAgenda = null;
        if (!hora.isEmpty()) {
            try {
                horaAgenda = Time.valueOf(hora);
            } catch (IllegalArgumentException e) {
                showMessageDialog(frame,
                        "Hora de agendamento inválida! Deve estar no formato HH:MM:SS.",
                        "Erro de Validação", ERROR_MESSAGE);
                return null;
            }
        }

        return campoId.getText().isEmpty()
                ? new Agenda(idosoSelecionado.getId(), agenteSelecionado.getId(), dataAgenda, horaAgenda, descricao)
                : new Agenda(Integer.parseInt(campoId.getText()), idosoSelecionado.getId(), agenteSelecionado.getId(),
                dataAgenda, horaAgenda, descricao);
    }

    private void selecionarAgendamento(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow != -1) {
                Integer id = (Integer) tabela.getValueAt(selectedRow, 0);
                Integer idosoId = (Integer) tabela.getValueAt(selectedRow, 1);
                Integer agenteId = (Integer) tabela.getValueAt(selectedRow, 2);
                Date data = (Date) tabela.getValueAt(selectedRow, 3);
                Time hora = (Time) tabela.getValueAt(selectedRow, 4);
                String descricao = (String) tabela.getValueAt(selectedRow, 5);

                campoId.setText(id.toString());
                campoData.setText(data.toString());

                for (int i = 0; i < comboBoxIdoso.getItemCount(); i++) {
                    Idoso item = comboBoxIdoso.getItemAt(i);
                    if (item != null && item.getId() == idosoId) {
                        comboBoxIdoso.setSelectedItem(item);
                        break;
                    }
                }

                for (int i = 0; i < comboBoxAgente.getItemCount(); i++) {
                    AgenteDeSaude item = comboBoxAgente.getItemAt(i);
                    if (item != null && item.getId() == agenteId) {
                        comboBoxAgente.setSelectedItem(item);
                        break;
                    }
                }

                campoHora.setText(hora.toString());
                campoDescricao.setText(descricao);
            }
        }
    }

}
