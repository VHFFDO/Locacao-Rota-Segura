package gui;

import model.*;
import service.SistemaLocacao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;

public class TelaLocacoes extends JFrame {
    private JComboBox<Cliente> cmbCliente;
    private JPanel painelPrincipal;
    private JComboBox<Veiculo> cmbVeiculo;
    private JTextField txtDataInicio;
    private JTextField txtDataFim;
    private JButton btnRegistrar;
    private JButton btnAtualizar;
    private JTable tabelaLocacoes;

    private final SistemaLocacao sistema;
    private DefaultTableModel modelo;

    public TelaLocacoes(SistemaLocacao sistema) {
        this.sistema = sistema;

        setTitle("Locações");
        setContentPane(painelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 550);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel(
                new Object[]{"Cliente", "Veículo", "Placa",
                        "Início", "Fim", "Situação"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaLocacoes.setModel(modelo);

        btnRegistrar.addActionListener(e -> registrar());
        btnAtualizar.addActionListener(e -> atualizarDados());

        atualizarDados();
    }

    private void atualizarDados() {
        cmbCliente.removeAllItems();
        cmbVeiculo.removeAllItems();

        for (Cliente cliente : sistema.listarClientes()) {
            cmbCliente.addItem(cliente);
        }

        for (Veiculo veiculo : sistema.listarVeiculos()) {
            if (veiculo.isDisponivel()) {
                cmbVeiculo.addItem(veiculo);
            }
        }

        modelo.setRowCount(0);

        for (Contrato contrato : sistema.listarContratos()) {
            modelo.addRow(new Object[]{
                    contrato.getClienteResponsavel().getNome(),
                    contrato.getVeiculoAlugado().getModelo(),
                    contrato.getVeiculoAlugado().getPlaca(),
                    contrato.getDataInicio(),
                    contrato.getDataFim(),
                    contrato.isEncerrado() ? "Encerrada" : "Ativa"
            });
        }
    }

    private void registrar() {
        try {
            Cliente cliente = (Cliente) cmbCliente.getSelectedItem();
            Veiculo veiculo = (Veiculo) cmbVeiculo.getSelectedItem();

            if (cliente == null || veiculo == null) {
                throw new IllegalArgumentException(
                        "Cadastre um cliente e um veículo disponível primeiro."
                );
            }

            LocalDate inicio = LocalDate.parse(txtDataInicio.getText());
            LocalDate fim = LocalDate.parse(txtDataFim.getText());

            Contrato contrato = new Contrato(
                    cliente, veiculo, inicio, fim
            );

            sistema.registrarLocacao(contrato);

            atualizarDados();

            txtDataInicio.setText("");
            txtDataFim.setText("");

            JOptionPane.showMessageDialog(
                    this, "Locação registrada com sucesso!"
            );

        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(
                    this,
                    "Não foi possível registrar: " + erro.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
