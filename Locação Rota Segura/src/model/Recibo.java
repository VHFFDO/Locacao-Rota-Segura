package model;

import java.util.Locale;

public class Recibo implements Documento {

    private Contrato contrato;

    public Recibo(Contrato contrato) {
        if (contrato == null) {
            throw new IllegalArgumentException(
                    "Contrato não informado."
            );
        }

        this.contrato = contrato;
    }

    @Override
    public String gerar() {
        return "========== RECIBO DE LOCAÇÃO ==========\n"
                + "Cliente: "
                + contrato.getClienteResponsavel().getNome() + "\n"
                + "Veículo: "
                + contrato.getVeiculoAlugado().getModelo() + "\n"
                + "Placa: "
                + contrato.getVeiculoAlugado().getPlaca() + "\n"
                + "Dias de locação: "
                + contrato.calcularDias() + "\n"
                + String.format(
                Locale.forLanguageTag("pt-BR"),
                "Valor das diárias: R$ %.2f%n",
                contrato.calcularValorDiarias()
        )
                + String.format(
                Locale.forLanguageTag("pt-BR"),
                "Seguro: R$ %.2f%n",
                contrato.calcularValorSeguro()
        )
                + String.format(
                Locale.forLanguageTag("pt-BR"),
                "Manutenção: R$ %.2f%n",
                contrato.calcularValorManutencao()
        )
                + String.format(
                Locale.forLanguageTag("pt-BR"),
                "TOTAL: R$ %.2f%n",
                contrato.calcularValorTotal()
        )
                + "=======================================";
    }

    @Override
    public void imprimir() {
        System.out.println(gerar());
    }
}