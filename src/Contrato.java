import java.time.LocalDate;

public class Contrato {
    //Inicio Atributo
    private Cliente clienteResponsalvel;
    private Veiculo veiculoAlugado;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    //Fim Atributo

    //Inicio Construtor do Contrato
    public Contrato(Cliente clienteResponsavel, Veiculo veiculoAlugado,
        LocalDate dataInicio, LocalDate dataFim){
            if (clienteResponsavel == null) {
                throw new IllegalArgumentException("Cliente não informado");
            }

            if (veiculoAlugado == null) {
                throw new IllegalArgumentException("Veiculo não informado");
            }

            if (dataInicio == null || dataFim == null) {
                throw new IllegalArgumentException("As datas são obrigatorias");
            }

            if (dataFim.isBefore(dataInicio)) {
                throw new IllegalArgumentException("A data final não pode ser anterior a data incial");
            }

            if (!veiculoAlugado.isDisponivel()) {
                throw new IllegalStateException("O veiculo esta indisponivel para locação");
            }

            veiculoAlugado.alugar();

            this.clienteResponsalvel = clienteResponsavel;
            this.veiculoAlugado = veiculoAlugado;
            this.dataInicio = dataInicio;
            this.dataFim = dataFim;
        }
    //Fim Contrutor do Contrato



}
