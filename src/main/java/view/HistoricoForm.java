package view;

import model.Historico;
import model.Idoso;
import service.HistoricoService;
import service.IdosoService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

import static javax.swing.JOptionPane.*;

public class HistoricoForm extends JFrame {
    private IdosoService idosoService;
    private HistoricoService historicoService;
    private JLabel labelId;
    private JTextField campoId;
    private JLabel labelIdosoId;
    private JComboBox<Idoso> comboBoxIdosoId;
    private JLabel labelDoencasPreexistentes;
    private JTextField campoDoencasPreexistentes;
    private JButton botaoSalvar;
    private JButton botaoCancelar;
    private JButton botaoDeletar;
    private JButton botaoVoltar;
    private JTable tabela;

    public HistoricoForm() {
        idosoService = new IdosoService();
        historicoService = new HistoricoService();

        setTitle("Histórico de Saúde do Idoso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 550);

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

        labelIdosoId = new JLabel("ID do idoso:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        painelEntrada.add(labelIdosoId, constraints);

        comboBoxIdosoId = new JComboBox<>();
        carregarIdososNoComboBox();
        constraints.gridx = 1;
        constraints.gridy = 1;
        painelEntrada.add(comboBoxIdosoId, constraints);

        labelDoencasPreexistentes = new JLabel("Doenças ou condições:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        painelEntrada.add(labelDoencasPreexistentes, constraints);

        campoDoencasPreexistentes = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        painelEntrada.add(campoDoencasPreexistentes, constraints);

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
        tabela.getSelectionModel().addListSelectionListener(this::selecionarHistorico);

        JScrollPane scrollPane = new JScrollPane(tabela);

        painelSaida.add(scrollPane, BorderLayout.CENTER);
        return painelSaida;
    }

    private void carregarIdososNoComboBox() {
        try {
            List<Idoso> idosos = idosoService.listarIdosos();
            comboBoxIdosoId.removeAllItems();
            for (Idoso idoso : idosos) {
                comboBoxIdosoId.addItem(idoso);
            }
        } catch (Exception e) {
            showMessageDialog(this,
                    "Erro ao carregar lista de idosos: " + e.getMessage(),
                    "Erro", ERROR_MESSAGE);
        }
    }

    private DefaultTableModel carregarDados() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Idoso id");
        model.addColumn("Doenças preexistentes");

        historicoService.listarHistoricos().forEach(historico -> model.addRow(new Object[]{
                historico.getId(), historico.getIdoso_id(), historico.getDoencas_preexistentes()}));

        return model;
    }

    private void executarBotaoSalvar() {
        try {
            if (comboBoxIdosoId.getSelectedItem() == null) {
                throw new Exception("Selecione um idoso.");
            }

            historicoService.salvar(construirHistorico());
            limparCampos();
            tabela.setModel(carregarDados());
        } catch (Exception e) {
            showMessageDialog(this,
                    "Erro ao salvar o histórico: " + e.getMessage(),
                    "Erro", ERROR_MESSAGE);
        }
    }

    private void executarBotaoDeletar() {
        if (campoId.getText().isEmpty()) {
            showMessageDialog(
                    this, "É necessário selecionar um item para que se possa deletar!",
                    "Aviso", INFORMATION_MESSAGE);
            return;
        }

        int id = Integer.parseInt(campoId.getText());
        try {
            historicoService.excluir(id);
            limparCampos();
            tabela.setModel(carregarDados());
        } catch (SQLException e) {
            showMessageDialog(
                    this, "Erro ao tentar deletar o histórico deste idoso!: " + e.getMessage(),
                    "Erro", ERROR_MESSAGE);
        }
    }

    private void executarBotaoVoltar() {
        this.dispose();
        MenuForm menuForm = new MenuForm();
        menuForm.setVisible(true);
    }

    private void limparCampos() {
        comboBoxIdosoId.setSelectedItem(null);
        campoDoencasPreexistentes.setText("");
        campoId.setText("");
    }

    private Historico construirHistorico() throws Exception {
        Idoso idosoSelecionado = (Idoso) comboBoxIdosoId.getSelectedItem();
        if (idosoSelecionado == null) {
            throw new Exception("Selecione um idoso.");
        }

        String doencasPreexistentes = campoDoencasPreexistentes.getText().trim();

        return campoId.getText().isEmpty()
                ? new Historico(idosoSelecionado.getId(), doencasPreexistentes)
                : new Historico(Integer.parseInt(campoId.getText()), idosoSelecionado.getId(), doencasPreexistentes);
    }

    private void selecionarHistorico(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow != -1) {
                var id = (Integer) tabela.getValueAt(selectedRow, 0);
                var idoso = (Integer) tabela.getValueAt(selectedRow, 1);
                var doencasPreexistentes = (String) tabela.getValueAt(selectedRow, 2);

                campoId.setText(id.toString());
                campoDoencasPreexistentes.setText(doencasPreexistentes);

                for (int i = 0; i < comboBoxIdosoId.getItemCount(); i++) {
                    Idoso item = comboBoxIdosoId.getItemAt(i);
                    if (item != null && item.getId() == idoso) {
                        comboBoxIdosoId.setSelectedItem(item);
                        break;
                    }
                }
            }
        }
    }

}
