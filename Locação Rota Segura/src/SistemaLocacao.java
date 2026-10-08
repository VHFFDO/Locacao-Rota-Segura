import java.util.ArrayList;
import java.util.List;

public class SistemaLocacao {
    //Inicio Atributos
    private List<Veiculo> frota;
    private List<Cliente> cadastroClientes;
    private List<Contrato> historicoLocacoes;
    private ArquivoLocacoes arquivoLocacoes;
    //Fim Atributos


    //Inicio Construtor
    public SistemaLocacao() {
        frota = new ArrayList<>();
        cadastroClientes = new ArrayList<>();
        historicoLocacoes = new ArrayList<>();
        arquivoLocacoes = new ArquivoLocacoes();
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
        if (novaLocacao == null) {
            throw new IllegalArgumentException("Locação não informada");
        }
        
        historicoLocacoes.add(novaLocacao);
        arquivoLocacoes.salvar(novaLocacao);
    }
    //Inicio do Metodo do Registro da Locação

    //Inicio do Metodo do Recibo
    public Recibo fecharLocacao(Contrato contrato) {
        if (contrato == null) {
            throw new IllegalArgumentException("Contrato não informado");
        }

        contrato.encerrarLocacao();

        Recibo recibo = new Recibo(contrato);

        return recibo;
    }
    //Fim do Metodo do Recibo

}
