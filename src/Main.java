import java.time.LocalDate;

public class Main {
    
    public static void main(String[] args) {
        SistemaLocacao sistema = new SistemaLocacao();

        Veiculo novoPopular = new Popular("Chevrolet", "Onix", 2024, "Prata", "ABC1D23");

        Veiculo novoSedan = new Sedan("Toyota", "Corolla", 2023, "Preto", "DEF4G56");

        Veiculo novoSUV = new Suv("Jeep", "Compass", 2025, "Branco", "HIJ7K89");

        sistema.cadastrarVeiculo(novoPopular);
        sistema.cadastrarVeiculo(novoSedan);
        sistema.cadastrarVeiculo(novoSUV);

        Cliente novoCliente = new Cliente("Gustavo da Silva", "12345678900", "14999999999");

        sistema.cadastroCliente(novoCliente);

        Contrato novaLoacacao = new Contrato(novoCliente, novoPopular, LocalDate.of(2026, 10, 7), LocalDate.of(2026, 10, 10));

        sistema.registrarLocacao(novaLoacacao);

        System.out.println(novaLoacacao.gerar());

        Recibo recibo = sistema.fecharLocacao(novaLoacacao);

        recibo.imprimir();
    }

}
