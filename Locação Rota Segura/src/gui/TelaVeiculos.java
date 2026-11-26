package gui;

import model.*;
import service.SistemaLocacao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaVeiculos extends JFrame {
    private JTextField txtMarca;
    private JPanel painelPrincipal;
    private JTextField txtModelo;
    private JTextField txtAno;
    private JTextField txtCor;
    private JTextField txtPlaca;
    private JComboBox<String> cmbCategoria;
    private JButton btnCadastrar;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnLimpar;
    private JTable tabelaVeiculos;

    private final SistemaLocacao sistema;
    private DefaultTableModel modelo;
    private String placaSelecionada;

    public TelaVeiculos(SistemaLocacao sistema) {
        this.sistema = sistema;

        setTitle("Cadastro de Veículos");
        setContentPane(painelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel(
                new Object[]{"Categoria", "Marca", "Modelo",
                        "Ano", "Cor", "Placa", "Situação"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaVeiculos.setModel(modelo);

        cmbCategoria.removeAllItems();
        cmbCategoria.addItem("Popular");
        cmbCategoria.addItem("Sedan");
        cmbCategoria.addItem("SUV");

        btnCadastrar.addActionListener(e -> cadastrar());
        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limpar());

        tabelaVeiculos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selecionarVeiculo();
            }
        });

        atualizarTabela();
    }

    private Veiculo criarVeiculo() {
        String marca = txtMarca.getText();
        String modelo = txtModelo.getText();
        int ano = Integer.parseInt(txtAno.getText());
        String cor = txtCor.getText();
        String placa = txtPlaca.getText();

        String categoria = cmbCategoria.getSelectedItem().toString();

        switch (categoria) {
            case "Popular":
                return new Popular(marca, modelo, ano, cor, placa);
            case "Sedan":
                return new Sedan(marca, modelo, ano, cor, placa);
            case "SUV":
                return new Suv(marca, modelo, ano, cor, placa);
            default:
                throw new IllegalArgumentException("Categoria inválida.");
        }
    }

    private void cadastrar() {
        try {
            Veiculo veiculo = criarVeiculo();
            sistema.cadastrarVeiculo(veiculo);

            atualizarTabela();
            limpar();

            JOptionPane.showMessageDialog(this, "Veículo cadastrado!");

        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(
                    this, erro.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void editar() {
        try {
            if (placaSelecionada == null) {
                throw new IllegalArgumentException(
                        "Selecione um veículo na tabela."
                );
            }

            Veiculo atualizado = criarVeiculo();

            if (!atualizado.getCategoria().equals(
                    sistema.buscarVeiculoPorPlaca(placaSelecionada)
                            .getCategoria())) {
                throw new IllegalArgumentException(
                        "Para alterar a categoria, exclua e cadastre o veículo novamente."
                );
            }

            sistema.editarVeiculo(placaSelecionada, atualizado);

            atualizarTabela();
            limpar();

            JOptionPane.showMessageDialog(this, "Veículo atualizado!");

        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(
                    this, erro.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void excluir() {
        try {
            if (placaSelecionada == null) {
                throw new IllegalArgumentException(
                        "Selecione um veículo na tabela."
                );
            }

            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja excluir este veículo?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if (resposta == JOptionPane.YES_OPTION) {
                sistema.excluirVeiculo(placaSelecionada);
                atualizarTabela();
                limpar();

                JOptionPane.showMessageDialog(this, "Veículo excluído!");
            }

        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(
                    this, erro.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void selecionarVeiculo() {
        int linha = tabelaVeiculos.getSelectedRow();

        if (linha >= 0) {
            cmbCategoria.setSelectedItem(modelo.getValueAt(linha, 0));
            txtMarca.setText(modelo.getValueAt(linha, 1).toString());
            txtModelo.setText(modelo.getValueAt(linha, 2).toString());
            txtAno.setText(modelo.getValueAt(linha, 3).toString());
            txtCor.setText(modelo.getValueAt(linha, 4).toString());
            txtPlaca.setText(modelo.getValueAt(linha, 5).toString());

            placaSelecionada = modelo.getValueAt(linha, 5).toString();
        }
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);

        for (Veiculo veiculo : sistema.listarVeiculos()) {
            modelo.addRow(new Object[]{
                    veiculo.getCategoria(),
                    veiculo.getMarca(),
                    veiculo.getModelo(),
                    veiculo.getAno(),
                    veiculo.getCor(),
                    veiculo.getPlaca(),
                    veiculo.isDisponivel() ? "Disponível" : "Alugado"
            });
        }
    }

    private void limpar() {
        txtMarca.setText("");
        txtModelo.setText("");
        txtAno.setText("");
        txtCor.setText("");
        txtPlaca.setText("");
        cmbCategoria.setSelectedIndex(0);
        placaSelecionada = null;
        tabelaVeiculos.clearSelection();
    }
}
