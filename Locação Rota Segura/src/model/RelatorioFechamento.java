package model;

import java.util.Locale;

public class RelatorioFechamento implements Documento {

    private Contrato contratoFinalizado;

    public RelatorioFechamento(Contrato contratoFinalizado) {
        if (contratoFinalizado == null) {
            throw new IllegalArgumentException(
                    "Contrato não informado."
            );
        }

        this.contratoFinalizado = contratoFinalizado;
    }

    @Override
    public String gerar() {
        return "======= RELATÓRIO DE FECHAMENTO =======\n"
                + "Cliente: "
                + contratoFinalizado.getClienteResponsavel().getNome() + "\n"
                + "Veículo: "
                + contratoFinalizado.getVeiculoAlugado().getModelo() + "\n"
                + "Placa: "
                + contratoFinalizado.getVeiculoAlugado().getPlaca() + "\n"
                + "Data de início: "
                + contratoFinalizado.getDataInicio().format(
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/uuuu")
        ) + "\n"
                + "Data de fim: "
                + contratoFinalizado.getDataFim().format(
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/uuuu")
        ) + "\n"
                + "Dias de locação: "
                + contratoFinalizado.calcularDias() + "\n"
                + String.format(
                Locale.forLanguageTag("pt-BR"),
                "Valor das diárias: R$ %.2f%n",
                contratoFinalizado.calcularValorDiarias()
        )
                + String.format(
                Locale.forLanguageTag("pt-BR"),
                "Valor do seguro: R$ %.2f%n",
                contratoFinalizado.calcularValorSeguro()
        )
                + String.format(
                Locale.forLanguageTag("pt-BR"),
                "Valor da manutenção: R$ %.2f%n",
                contratoFinalizado.calcularValorManutencao()
        )
                + String.format(
                Locale.forLanguageTag("pt-BR"),
                "VALOR TOTAL: R$ %.2f%n",
                contratoFinalizado.calcularValorTotal()
        )
                + "=======================================";
    }

    @Override
    public void imprimir() {
        System.out.println(gerar());
    }
}