package view;

import model.Cuidador;
import service.CuidadorService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;

import static javax.swing.JOptionPane.*;

public class CuidadorForm extends JFrame{
    private CuidadorService service;
    private JLabel labelId;
    private JTextField campoId;
    private JLabel labelNomeCuidador;
    private JTextField campoNomeCuidador;
    private JLabel labelTelefone;
    private JTextField campoTelefone;
    private JLabel labelCpf;
    private JTextField campoCpf;
    private JLabel labelDataRegistro;
    private JTextField campoDataRegistro;
    private JButton botaoSalvar;
    private JButton botaoCancelar;
    private JButton botaoDeletar;
    private JButton botaoVoltar;
    private JTable tabela;

    public CuidadorForm(){
        service = new CuidadorService();

        setTitle("Diretor");
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

        labelNomeCuidador = new JLabel("Nome do Cuidador:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        painelEntrada.add(labelNomeCuidador, constraints);

        campoNomeCuidador = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        painelEntrada.add(campoNomeCuidador, constraints);

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

        labelDataRegistro = new JLabel("Data do registro:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        painelEntrada.add(labelDataRegistro, constraints);

        campoDataRegistro = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 4;
        painelEntrada.add(campoDataRegistro, constraints);

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
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        painelEntrada.add(painelBotoes, constraints);
    }

    private JPanel montarPainelSaida() {
        JPanel painelSaida = new JPanel(new BorderLayout());

        tabela = new JTable();
        tabela.setDefaultEditor(Object.class, null);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setModel(carregarDados());
        tabela.getSelectionModel().addListSelectionListener(this::selecionarCuidador);

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
        model.addColumn("Data de registro");

        service.listarCuidadores().forEach(cuidador -> model.addRow(new Object[]{
                cuidador.getId(), cuidador.getNome(), cuidador.getTelefone(),
                cuidador.getCpf(), cuidador.getDataRegistro()}));

        return model;
    }

    private void executarBotaoSalvar() {
        service.salvar(construirCuidador());
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
        campoNomeCuidador.setText("");
        campoTelefone.setText("");
        campoCpf.setText("");
        campoDataRegistro.setText("");
        campoId.setText("");
    }

    private Cuidador construirCuidador(){
        String nome = campoNomeCuidador.getText().trim();
        String telefone = campoTelefone.getText().trim();
        String cpf = campoCpf.getText().trim();
        String dataRegistroText = campoDataRegistro.getText().trim();

        if(dataRegistroText.isEmpty()){
            showMessageDialog(this, "A data de registro do cuidador não pode estar vazia!");
            return null;
        }

        Date dataRegistro;
        try {
            dataRegistro = Date.valueOf(dataRegistroText);
        } catch (IllegalArgumentException e) {
            showMessageDialog(null, "A data de registro do cuidador é inválida! Deve estar no formato YYYY-MM-DD.");
            return null;
        }

        return campoId.getText().isEmpty()
                ? new Cuidador(nome, telefone, cpf, dataRegistro)
                : new Cuidador(Integer.parseInt(campoId.getText()), nome, telefone, cpf, dataRegistro);
    }

    private void selecionarCuidador(ListSelectionEvent e){
        if (!e.getValueIsAdjusting()) {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow != -1) {
                var id = (Integer) tabela.getValueAt(selectedRow, 0);
                var nome = (String) tabela.getValueAt(selectedRow, 1);
                var telefone = (Integer) tabela.getValueAt(selectedRow, 2);
                var cpf = (String) tabela.getValueAt(selectedRow, 3);
                var dataRegistro = (Date) tabela.getValueAt(selectedRow, 4);

                campoId.setText(id.toString());
                campoNomeCuidador.setText(nome);
                campoTelefone.setText(telefone.toString());
                campoCpf.setText(cpf);
                campoDataRegistro.setText(dataRegistro.toString());
            }
        }
    }

}
