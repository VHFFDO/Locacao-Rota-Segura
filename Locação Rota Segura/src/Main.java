import gui.TelaPrincipal;

import javax.swing.SwingUtilities;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}



/////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
        SistemaLocacao sistema = new SistemaLocacao();


        model.Veiculo novoPopular = new model.Popular("Chevrolet", "Onix", 2024, "Prata", "ABC1D23");

        model.Veiculo novoSedan = new model.Sedan("Toyota", "Corolla", 2023, "Preto", "DEF4G56");

        model.Veiculo novoSUV = new model.Suv("Jeep", "Compass", 2025, "Branco", "HIJ7K89");

        sistema.cadastrarVeiculo(novoPopular);
        sistema.cadastrarVeiculo(novoSedan);
        sistema.cadastrarVeiculo(novoSUV);

        model.Cliente novoCliente = new model.Cliente("Gustavo da Silva", "12345678900", "14999999999");

        sistema.cadastroCliente(novoCliente);

        model.Contrato novaLoacacao = new model.Contrato(novoCliente, novoPopular, LocalDate.of(2026, 10, 7), LocalDate.of(2026, 10, 10));

        sistema.registrarLocacao(novaLoacacao);

        System.out.println(novaLoacacao.gerar());

        model.Recibo recibo = sistema.fecharLocacao(novaLoacacao);

        recibo.imprimir();
*/
/////////////////////////////////////////////////////////////////////////////////////////////////////////
/* 
        System.out.println("\n--- TESTE: VEÍCULO OCUPADO ---");

        try {

            model.Contrato segundaLocacao = new model.Contrato(
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

            model.Contrato contratoInvalido = new model.Contrato(
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
            model.Cliente clienteInvalido = new model.Cliente(
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
        }

}
*/
