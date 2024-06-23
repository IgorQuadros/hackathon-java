package view;

import model.Idoso;
import service.IdosoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaIdososForm extends JFrame {
    private IdosoService idosoService;
    private JTable tabela;

    public ListaIdososForm() {
        idosoService = new IdosoService();

        setTitle("Lista de Idosos Cadastrados");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400); // Ajuste o tamanho conforme necessário

        getContentPane().add(montarPainelSaida(), BorderLayout.CENTER);
        getContentPane().add(montarPainelBotoes(), BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private JPanel montarPainelSaida() {
        JPanel painelSaida = new JPanel(new BorderLayout());

        tabela = new JTable();
        tabela.setDefaultEditor(Object.class, null);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setModel(carregarDados());

        JScrollPane scrollPane = new JScrollPane(tabela);

        painelSaida.add(scrollPane, BorderLayout.CENTER);
        return painelSaida;
    }

    private JPanel montarPainelBotoes() {
        JPanel painelBotoes = new JPanel();

        JButton botaoAtualizar = new JButton("Atualizar");
        botaoAtualizar.addActionListener(e -> atualizarDados());
        painelBotoes.add(botaoAtualizar);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.addActionListener(e -> executarBotaoVoltar());
        painelBotoes.add(botaoVoltar);

        return painelBotoes;
    }

    private DefaultTableModel carregarDados() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Telefone");
        model.addColumn("Email");
        model.addColumn("Cuidador ID");

        List<Idoso> idosos = idosoService.listarIdososComCuidadores();
        for (Idoso idoso : idosos) {
            model.addRow(new Object[]{
                    idoso.getId(), idoso.getNome(), idoso.getTelefone(), idoso.getEmail(),
                    idoso.getCuidador() != null ? idoso.getCuidador().getId() : "Não possui cuidador"
            });
        }

        return model;
    }

    private void atualizarDados() {
        tabela.setModel(carregarDados());
    }

    private void executarBotaoVoltar() {
        this.dispose();
        MenuForm menuForm = new MenuForm();
        menuForm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ListaIdososForm form = new ListaIdososForm();
            form.setVisible(true);
        });
    }
}

