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

/////////////////////////////////////////////////////////////////////////////////////////////////////////
/* 
        System.out.println("\n--- TESTE: VEÍCULO OCUPADO ---");

        try {

            Contrato segundaLocacao = new Contrato(
                novoCliente,
                novoPopular,
                LocalDate.of(2026, 10, 11),
                LocalDate.of(2026, 10, 13)
            );

        } 
        
        catch (IllegalStateException erro) {
            System.out.println(
                "Erro tratado: " + erro.getMessage()
            );
        }
*/
/////////////////////////////////////////////////////////////////////////////////////////////////////////
/* 
    System.out.println("\n--- TESTE: DATA INVÁLIDA ---");

        try {

            Contrato contratoInvalido = new Contrato(
                novoCliente,
                novoSedan,
                LocalDate.of(2026, 10, 20),
                LocalDate.of(2026, 10, 10)
            );

        } 
        
        catch (IllegalArgumentException erro) {
            System.out.println(
                "Erro tratado: " + erro.getMessage()
            );
        }
*/
/////////////////////////////////////////////////////////////////////////////////////////////////////////
/* 
        System.out.println("\n--- TESTE: CLIENTE INVÁLIDO ---");

        try {
            Cliente clienteInvalido = new Cliente(
                "",
                "123",
                "14999999999"
            );

        } 
        
        catch (IllegalArgumentException erro) {
            System.out.println(
                "Erro tratado: " + erro.getMessage()
            );
        }
*/
        }

}
