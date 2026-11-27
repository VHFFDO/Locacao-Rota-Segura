package gui;

import model.*;
import service.SistemaLocacao;

import javax.swing.*;

public class TelaFechamento extends JFrame {

    private JComboBox<Contrato> cmbLocacao;
    private JPanel painelPrincipal;
    private JButton btnVisualizar;
    private JButton btnFechar;
    private JTextArea txtResumo;

    private final SistemaLocacao sistema;

    public TelaFechamento(SistemaLocacao sistema) {
        this.sistema = sistema;

        setTitle("Fechamento de Contrato");
        setContentPane(painelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        txtResumo.setEditable(false);

        atualizarLocacoes();

        btnVisualizar.addActionListener(e -> visualizar());
        btnFechar.addActionListener(e -> fechar());
    }

    private void atualizarLocacoes() {
        cmbLocacao.removeAllItems();

        for (Contrato contrato : sistema.listarLocacoesAtivas()) {
            cmbLocacao.addItem(contrato);
        }
    }

    private void visualizar() {
        Contrato contrato = (Contrato) cmbLocacao.getSelectedItem();

        if (contrato == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Não há locações ativas."
            );
            return;
        }

        txtResumo.setText(
                contrato.gerar()
                        + "\n\n"
                        + String.format(
                        java.util.Locale.forLanguageTag("pt-BR"),
                        "Diárias: R$ %.2f",
                        contrato.calcularValorDiarias()
                )
                        + "\n"
                        + String.format(
                        java.util.Locale.forLanguageTag("pt-BR"),
                        "Seguro: R$ %.2f",
                        contrato.calcularValorSeguro()
                )
                        + "\n"
                        + String.format(
                        java.util.Locale.forLanguageTag("pt-BR"),
                        "Manutenção: R$ %.2f",
                        contrato.calcularValorManutencao()
                )
                        + "\n"
                        + String.format(
                        java.util.Locale.forLanguageTag("pt-BR"),
                        "TOTAL: R$ %.2f",
                        contrato.calcularValorTotal()
                )
        );
    }

    private void fechar() {
        try {
            Contrato contrato = (Contrato) cmbLocacao.getSelectedItem();

            if (contrato == null) {
                throw new IllegalArgumentException(
                        "Selecione uma locação ativa."
                );
            }

            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Confirma o fechamento desta locação?",
                    "Fechar contrato",
                    JOptionPane.YES_NO_OPTION
            );

            if (resposta != JOptionPane.YES_OPTION) {
                return;
            }

            Recibo recibo = sistema.fecharLocacao(contrato);

            RelatorioFechamento relatorio =
                    new RelatorioFechamento(contrato);

            txtResumo.setText(
                    recibo.gerar()
                            + "\n\n"
                            + relatorio.gerar()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Contrato encerrado e veículo devolvido!"
            );

            atualizarLocacoes();

        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(
                    this,
                    erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}