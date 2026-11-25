package gui;

import service.SistemaLocacao;

import javax.swing.*;

public class TelaPrincipal extends JFrame {
    private JPanel painelPrincipal;
    private JButton btnClientes;
    private JButton btnVeiculos;
    private JButton btnLocacoes;
    private JButton btnSair;
    private JLabel lblTitulo;

    private final SistemaLocacao sistema;

    public TelaPrincipal() {

        sistema = new SistemaLocacao();

        setTitle("Locação Rota Segura");
        setContentPane(painelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);

        btnClientes.addActionListener(e -> {
            TelaClientes tela = new TelaClientes(sistema);
            tela.setVisible(true);
        });

        btnVeiculos.addActionListener(e -> {
            TelaVeiculos tela = new TelaVeiculos(sistema);
            tela.setVisible(true);
        });

        btnLocacoes.addActionListener(e -> {
            String[] opcoes = {
                    "Registrar / Consultar Locações",
                    "Fechar Contrato"
            };

            int escolha = JOptionPane.showOptionDialog(
                    this,
                    "O que deseja fazer?",
                    "Locações",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]
            );

            if (escolha == 0) {
                new TelaLocacoes(sistema).setVisible(true);
            } else if (escolha == 1) {
                new TelaFechamento(sistema).setVisible(true);
            }
        });

        btnSair.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente sair?",
                    "Confirmar saída",
                    JOptionPane.YES_NO_OPTION
            );

            if (resposta == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
    }
}
