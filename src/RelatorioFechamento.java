public class RelatorioFechamento implements Documento {
    //Inicio Atributo
    private Contrato contratoFinalizado;
    //Fim Atributo

    public RelatorioFechamento(Contrato contratoFinalizado) {
        if (contratoFinalizado == null) {
            throw new IllegalArgumentException("Contrato não informado");
        }

        this.contratoFinalizado = contratoFinalizado;
    }

    @Override
    public String gerar() {
        return  "RELATORIO DE FECHAMENTO \n"
        + "Cliente: " + contratoFinalizado.getClienteResponsavel().getNome() + "\n"
        + "Veiculo: " +
    }

}
