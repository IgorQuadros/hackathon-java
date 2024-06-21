package view;

import model.AgenteDeSaude;
import model.Cuidador;
import service.AgenteDeSaudeService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;

import static javax.swing.JOptionPane.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class AgenteDeSaudeForm extends JFrame{
    private AgenteDeSaudeService service;
    private JLabel labelId;
    private JTextField campoId;
    private JLabel labelNomeCuidador;
    private JTextField campoNomeAgente;
    private JLabel labelTelefone;
    private JTextField campoTelefone;
    private JLabel labelCpf;
    private JTextField campoCpf;
    private JLabel labelEspecialidade;
    private JTextField campoEspecialidade;
    private JLabel labelDataContratacao;
    private JTextField campoDataContratacao;
    private JButton botaoSalvar;
    private JButton botaoCancelar;
    private JButton botaoDeletar;
    private JButton botaoVoltar;
    private JTable tabela;

    public AgenteDeSaudeForm(){
        service = new AgenteDeSaudeService();

        setTitle("Agente de saúde");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 550);

        getContentPane().add(montarPainelEntrada(), BorderLayout.NORTH);
        getContentPane().add(montarPainelSaida(), BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    private JPanel montarPainelEntrada(){
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

        labelNomeCuidador = new JLabel("Nome do Agente:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        painelEntrada.add(labelNomeCuidador, constraints);

        campoNomeAgente = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        painelEntrada.add(campoNomeAgente, constraints);

        labelTelefone = new JLabel("Telefone:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        painelEntrada.add(labelTelefone, constraints);

        campoTelefone = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        painelEntrada.add(campoTelefone, constraints);

        labelCpf = new JLabel("CPF:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        painelEntrada.add(labelCpf, constraints);

        campoCpf = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 3;
        painelEntrada.add(campoCpf, constraints);

        labelEspecialidade = new JLabel("Especialidade:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        painelEntrada.add(labelEspecialidade, constraints);

        campoEspecialidade = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 4;
        painelEntrada.add(campoEspecialidade, constraints);

        labelDataContratacao = new JLabel("Data da contratação:");
        constraints.gridx = 0;
        constraints.gridy = 5;
        painelEntrada.add(labelDataContratacao, constraints);

        campoDataContratacao = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 5;
        painelEntrada.add(campoDataContratacao, constraints);

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
        tabela.getSelectionModel().addListSelectionListener(this::selecionarAgente);

        JScrollPane scrollPane = new JScrollPane(tabela);

        painelSaida.add(scrollPane, BorderLayout.CENTER);
        return painelSaida;
    }

    private DefaultTableModel carregarDados() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Telefone");
        model.addColumn("CPF");
        model.addColumn("Especialidade");
        model.addColumn("Data de contratação");

        service.listarAgentes().forEach(agente -> model.addRow(new Object[]{
                agente.getId(), agente.getNome(), agente.getTelefone(),
                agente.getCpf(), agente.getEspecialidade(), agente.getDataContratacao()}));

        return model;
    }

    private void executarBotaoSalvar() {
        service.salvar(construirAgente());
        limparCampos();
        tabela.setModel(carregarDados());
    }

    private void executarBotaoDeletar() {
        if(campoId.getText().isEmpty()){
            showMessageDialog(
                    this, "É necessário selecionar um item para que se possa deletar!",
                    "Aviso", INFORMATION_MESSAGE);
            return;
        }

        int id = Integer.parseInt(campoId.getText());
        try {
            service.excluir(id);
        } catch (SQLException e) {
            showMessageDialog(
                    this, "Erro ao tentar deletar o Diretor!: " + e.getMessage(),
                    "Erro", ERROR_MESSAGE);
        }
        limparCampos();
        tabela.setModel(carregarDados());
    }

    private void executarBotaoVoltar() {
        this.dispose();
        MenuForm menuForm = new MenuForm();
        menuForm.setVisible(true);
    }

    private void limparCampos() {
        campoNomeAgente.setText("");
        campoTelefone.setText("");
        campoCpf.setText("");
        campoEspecialidade.setText("");
        campoDataContratacao.setText("");
        campoId.setText("");
    }

    private AgenteDeSaude construirAgente(){
        String nome = campoNomeAgente.getText().trim();
        String telefone = campoTelefone.getText().trim();
        String cpf = campoCpf.getText().trim();
        String especialidade = campoEspecialidade.getText().trim();
        String dataContratacaoText = campoDataContratacao.getText().trim();

        Date dataContratacao = null;
        if(!dataContratacaoText.isEmpty()){
            try {
                dataContratacao = Date.valueOf(dataContratacaoText);
            } catch (IllegalArgumentException e) {
                showMessageDialog(null,
                        "A data de contratação do agente de saúde é inválida! Deve estar no formato YYYY-MM-DD.");
            }
        }

        return campoId.getText().isEmpty()
                ? new AgenteDeSaude(nome, telefone, cpf, especialidade, dataContratacao)
                : new AgenteDeSaude(Integer.parseInt(campoId.getText()), nome, telefone, cpf, especialidade, dataContratacao);
    }

    private void selecionarAgente(ListSelectionEvent e){
        if (!e.getValueIsAdjusting()) {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow != -1) {
                var id = (Integer) tabela.getValueAt(selectedRow, 0);
                var nome = (String) tabela.getValueAt(selectedRow, 1);
                var telefone = (String) tabela.getValueAt(selectedRow, 2);
                var cpf = (String) tabela.getValueAt(selectedRow, 3);
                var especialidade = (String) tabela.getValueAt(selectedRow, 4);
                var dataContratacao = (Date) tabela.getValueAt(selectedRow, 5);

                campoId.setText(id.toString());
                campoNomeAgente.setText(nome);
                campoTelefone.setText(telefone);
                campoCpf.setText(cpf);
                campoEspecialidade.setText(especialidade);
                campoDataContratacao.setText(dataContratacao.toString());
            }
        }
    }
}
