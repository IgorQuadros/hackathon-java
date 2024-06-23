package view;

import model.Vacinas;
import service.VacinaService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static javax.swing.JOptionPane.*;

public class VacinasForm extends JFrame {
    private VacinaService service;
    private JLabel labelId;
    private JTextField campoId;
    private JLabel labelNomeVacina;
    private JTextField campoNomeVacina;
    private JLabel labelDataValidade;
    private JTextField campoDataValidade;
    private JButton botaoSalvar;
    private JButton botaoCancelar;
    private JButton botaoDeletar;
    private JButton botaoVoltar;
    private JTable tabela;

    public VacinasForm() {
        service = new VacinaService();

        setTitle("Cadastro de Vacinas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);

        getContentPane().add(montarPainelEntrada(), BorderLayout.NORTH);
        getContentPane().add(montarPainelSaida(), BorderLayout.CENTER);

        setLocationRelativeTo(null);
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

        labelNomeVacina = new JLabel("Nome da Vacina:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        painelEntrada.add(labelNomeVacina, constraints);

        campoNomeVacina = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        painelEntrada.add(campoNomeVacina, constraints);

        labelDataValidade = new JLabel("Data de Validade:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        painelEntrada.add(labelDataValidade, constraints);

        campoDataValidade = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        painelEntrada.add(campoDataValidade, constraints);

        montarPainelBotoes(constraints, painelEntrada);

        return painelEntrada;
    }

    private void montarPainelBotoes(GridBagConstraints constraints, JPanel painelEntrada) {
        JPanel painelBotoes = new JPanel(new GridBagLayout());
        GridBagConstraints botoesConstraints = new GridBagConstraints();
        botoesConstraints.insets = new Insets(5, 5, 5, 5);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(e -> executarBotaoSalvar());
        botoesConstraints.gridx = 0;
        botoesConstraints.gridy = 0;
        painelBotoes.add(botaoSalvar, botoesConstraints);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> limparCampos());
        botoesConstraints.gridx = 1;
        botoesConstraints.gridy = 0;
        painelBotoes.add(botaoCancelar, botoesConstraints);

        botaoDeletar = new JButton("Deletar");
        botaoDeletar.addActionListener(e -> executarBotaoDeletar());
        botoesConstraints.gridx = 2;
        botoesConstraints.gridy = 0;
        painelBotoes.add(botaoDeletar, botoesConstraints);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.addActionListener(e -> executarBotaoVoltar());
        botoesConstraints.gridx = 3;
        botoesConstraints.gridy = 0;
        painelBotoes.add(botaoVoltar, botoesConstraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        painelEntrada.add(painelBotoes, constraints);
    }

    private JPanel montarPainelSaida() {
        JPanel painelSaida = new JPanel(new BorderLayout());

        tabela = new JTable();
        tabela.setDefaultEditor(Object.class, null);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setModel(carregarDados());
        tabela.getSelectionModel().addListSelectionListener(this::selecionarVacina);

        JScrollPane scrollPane = new JScrollPane(tabela);

        painelSaida.add(scrollPane, BorderLayout.CENTER);
        return painelSaida;
    }

    private DefaultTableModel carregarDados() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nome da Vacina");
        model.addColumn("Data de Validade");

        service.listarTodos().forEach(vacina -> model.addRow(new Object[]{
                vacina.getId(), vacina.getNome(), vacina.getDataValidade()}));

        return model;
    }

    private void executarBotaoSalvar() {
        try {
            Vacinas vacina = construirVacina();
            service.salvar(vacina);
            limparCampos();
            tabela.setModel(carregarDados());
        } catch (Exception e) {
            showMessageDialog(this,
                    "Erro ao salvar a vacina: " + e.getMessage(),
                    "Erro", ERROR_MESSAGE);
        }
    }

    private void executarBotaoDeletar() {
        if (campoId.getText().isEmpty()) {
            showMessageDialog(
                    this, "É necessário selecionar um item para deletar!",
                    "Aviso", INFORMATION_MESSAGE);
            return;
        }

        int id = Integer.parseInt(campoId.getText());
        try {
            service.excluir(id);
            limparCampos();
            tabela.setModel(carregarDados());
        } catch (SQLException e) {
            showMessageDialog(
                    this, "Erro ao tentar deletar a vacina: " + e.getMessage(),
                    "Erro", ERROR_MESSAGE);
        }
    }

    private void executarBotaoVoltar() {
        this.dispose();
        MenuForm menuForm = new MenuForm();
        menuForm.setVisible(true);
    }

    private void limparCampos() {
        campoNomeVacina.setText("");
        campoDataValidade.setText("");
        campoId.setText("");
    }

    private Vacinas construirVacina() throws Exception {
        String nome = campoNomeVacina.getText().trim();
        String dataValidadeStr = campoDataValidade.getText().trim();

        if (nome.isEmpty()) {
            throw new Exception("O campo Nome da Vacina é obrigatório.");
        }

        Date dataValidade = null;
        if (!dataValidadeStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = sdf.parse(dataValidadeStr);
                dataValidade = new Date(utilDate.getTime());
            } catch (ParseException e) {
                throw new Exception("Formato inválido para Data de Validade. Use yyyy-MM-dd.");
            }
        }

        return campoId.getText().isEmpty()
                ? new Vacinas(nome, dataValidade)
                : new Vacinas(Integer.parseInt(campoId.getText()), nome, dataValidade);
    }

    private void selecionarVacina(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow != -1) {
                var id = (Integer) tabela.getValueAt(selectedRow, 0);
                var nome = (String) tabela.getValueAt(selectedRow, 1);
                var dataValidade = (Date) tabela.getValueAt(selectedRow, 2);

                campoId.setText(id.toString());
                campoNomeVacina.setText(nome);
                campoDataValidade.setText(dataValidade.toString());
            }
        }
    }

}
