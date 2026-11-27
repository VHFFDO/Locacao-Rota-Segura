package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Contrato implements Documento {

    private int id;
    private Cliente clienteResponsavel;
    private Veiculo veiculoAlugado;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean encerrado;

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu");

    public Contrato(Cliente clienteResponsavel,
                    Veiculo veiculoAlugado,
                    LocalDate dataInicio,
                    LocalDate dataFim) {

        if (clienteResponsavel == null || veiculoAlugado == null) {
            throw new IllegalArgumentException(
                    "Selecione um cliente e um veículo."
            );
        }

        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("Informe as datas.");
        }

        if (!dataFim.isAfter(dataInicio)) {
            throw new IllegalArgumentException(
                    "A data final deve ser posterior à data inicial."
            );
        }

        this.clienteResponsavel = clienteResponsavel;
        this.veiculoAlugado = veiculoAlugado;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.encerrado = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getClienteResponsavel() {
        return clienteResponsavel;
    }

    public Veiculo getVeiculoAlugado() {
        return veiculoAlugado;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public void marcarEncerrado() {
        encerrado = true;
    }

    public long calcularDias() {
        return ChronoUnit.DAYS.between(dataInicio, dataFim);
    }

    public double calcularValorDiarias() {
        return calcularDias() * veiculoAlugado.calcularDiaria();
    }

    public double calcularValorSeguro() {
        return calcularDias() * veiculoAlugado.calcularSeguro();
    }

    public double calcularValorManutencao() {
        return calcularDias() * veiculoAlugado.calcularManutencao();
    }

    public double calcularValorTotal() {
        return calcularValorDiarias()
                + calcularValorSeguro()
                + calcularValorManutencao();
    }

    public void encerrarLocacao() {
        if (encerrado) {
            throw new IllegalStateException("Locação já encerrada.");
        }

        veiculoAlugado.devolver();
        encerrado = true;
    }

    @Override
    public String gerar() {
        return "===== CONTRATO DE LOCAÇÃO =====\n"
                + "Número: " + id + "\n"
                + "Cliente: " + clienteResponsavel.getNome() + "\n"
                + "CPF: " + clienteResponsavel.getCpf() + "\n"
                + "Veículo: " + veiculoAlugado.getModelo() + "\n"
                + "Placa: " + veiculoAlugado.getPlaca() + "\n"
                + "Data de início: " + dataInicio.format(FORMATO_DATA) + "\n"
                + "Data de fim: " + dataFim.format(FORMATO_DATA) + "\n"
                + "Dias: " + calcularDias() + "\n"
                + String.format(
                Locale.forLanguageTag("pt-BR"),
                "Total: R$ %.2f",
                calcularValorTotal()
        )
                + "\n================================";
    }

    @Override
    public void imprimir() {
        System.out.println(gerar());
    }

    @Override
    public String toString() {
        return "Contrato " + id + " - "
                + clienteResponsavel.getNome()
                + " - " + veiculoAlugado.getPlaca();
    }
}