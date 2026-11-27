package gui;

import model.Cliente;
import service.SistemaLocacao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaClientes extends JFrame {
    private JTextField txtNome;
    private JPanel painelPrincipal;
    private JTextField txtCpf;
    private JTextField txtTelefone;
    private JButton btnCadastrar;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnLimpar;
    private JTable tabelaClientes;
    private JTextField txtEmail;

    private final SistemaLocacao sistema;
    private DefaultTableModel modelo;
    private String cpfSelecionado;

    public TelaClientes(SistemaLocacao sistema) {
        this.sistema = sistema;

        setTitle("Cadastro de Clientes");
        setContentPane(painelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 550);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel(
                new Object[]{"Nome", "CPF", "Telefone", "E-mail"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaClientes.setModel(modelo);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limpar());

        tabelaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selecionarCliente();
            }
        });

        atualizarTabela();
    }

    private void cadastrar() {
        try {
            Cliente cliente = new Cliente(
                    txtNome.getText(),
                    txtCpf.getText(),
                    txtTelefone.getText(),
                    txtEmail.getText()
            );

            sistema.cadastrarCliente(cliente);

            atualizarTabela();
            limpar();

            JOptionPane.showMessageDialog(this, "Cliente cadastrado!");

        } catch (IllegalArgumentException erro) {
            JOptionPane.showMessageDialog(
                    this, erro.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void editar() {
        try {
            if (cpfSelecionado == null) {
                throw new IllegalArgumentException(
                        "Selecione um cliente na tabela."
                );
            }

            Cliente atualizado = new Cliente(
                    txtNome.getText(),
                    txtCpf.getText(),
                    txtTelefone.getText(),
                    txtEmail.getText()
            );

            sistema.editarCliente(cpfSelecionado, atualizado);

            atualizarTabela();
            limpar();

            JOptionPane.showMessageDialog(this, "Cliente atualizado!");

        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(
                    this, erro.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void excluir() {
        try {
            if (cpfSelecionado == null) {
                throw new IllegalArgumentException(
                        "Selecione um cliente na tabela."
                );
            }

            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja excluir este cliente?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if (resposta == JOptionPane.YES_OPTION) {
                sistema.excluirCliente(cpfSelecionado);
                atualizarTabela();
                limpar();
                JOptionPane.showMessageDialog(this, "Cliente excluído!");
            }

        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(
                    this, erro.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void selecionarCliente() {
        int linha = tabelaClientes.getSelectedRow();

        if (linha >= 0) {
            txtNome.setText(modelo.getValueAt(linha, 0).toString());
            txtCpf.setText(modelo.getValueAt(linha, 1).toString());
            txtTelefone.setText(modelo.getValueAt(linha, 2).toString());
            cpfSelecionado = modelo.getValueAt(linha, 1).toString();
            txtEmail.setText(modelo.getValueAt(linha, 3).toString());
        }
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);

        for (Cliente cliente : sistema.listarClientes()) {
            modelo.addRow(new Object[]{
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getTelefone(),
                    cliente.getEmail()
            });
        }
    }

    private void limpar() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        cpfSelecionado = null;
        tabelaClientes.clearSelection();
    }
}
