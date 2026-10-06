import java.util.ArrayList;
import java.util.List;

public class SistemaLocacao {
    //Inicio das Listas
    private List<Veiculo> frota;

    private List<Cliente> cadastroClientes;

    private List<Contrato> historicoLocacoes;
    //Fim das Listas


    //Inicio Construtor
    public SistemaLocacao() {
        frota = new ArrayList<>();
        cadastroClientes = new ArrayList<>();
        historicoLocacoes = new ArrayList<>();
    }
    //Fim Construtor

    //Inicio do Metodo de Cadastro de Veiculo
    public void cadastrarVeiculo(Veiculo novoVeiculo) {
        frota.add(novoVeiculo);
    }
    //Fim do Metodo de Cadastro de Veiculo

    //Inicio do Metodo de Cadastro de Cliente
    public void cadastroCliente(Cliente novoCliente) {
        cadastroClientes.add(novoCliente);
    }
    //Fim do Metodo de Cadastro de Cliente

    //Inicio do Metodo de Registro da Locação
    public void registrarLocacao(Contrato novaLocacao){
        historicoLocacoes.add(novaLocacao);
    }
    //Inicio do Metodo do Registro da Locação

}
