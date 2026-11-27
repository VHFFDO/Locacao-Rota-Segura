package service;

import dao.ClienteDAO;
import dao.VeiculoDAO;
import dao.ContratoDAO;

import model.*;

import java.util.List;

public class SistemaLocacao {

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final VeiculoDAO veiculoDAO = new VeiculoDAO();
    private final ContratoDAO contratoDAO = new ContratoDAO();

    // =========================
    // CLIENTES
    // =========================

    public void cadastrarCliente(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente inválido.");
        }

        if (clienteDAO.buscarPorId(cliente.getCpf()) != null) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        clienteDAO.inserir(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.buscarTodos();
    }

    public Cliente buscarClientePorCpf(String cpf) {
        return clienteDAO.buscarPorId(cpf.replaceAll("\\D", ""));
    }

    public void editarCliente(String cpfOriginal, Cliente atualizado) {

        Cliente original = buscarClientePorCpf(cpfOriginal);

        if (original == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }

        Cliente existente = buscarClientePorCpf(atualizado.getCpf());

        if (existente != null
                && !existente.getCpf().equals(original.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        clienteDAO.atualizar(original.getCpf(), atualizado);
    }

    public void excluirCliente(String cpf) {

        Cliente cliente = buscarClientePorCpf(cpf);

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }

        for (Contrato contrato : contratoDAO.buscarTodos()) {
            if (contrato.getClienteResponsavel().getCpf()
                    .equals(cliente.getCpf())) {
                throw new IllegalStateException(
                        "Não é possível excluir cliente com histórico de locações."
                );
            }
        }

        clienteDAO.excluir(cliente.getCpf());
    }

    // =========================
    // VEÍCULOS
    // =========================

    public void cadastrarVeiculo(Veiculo veiculo) {

        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo inválido.");
        }

        if (veiculoDAO.buscarPorId(veiculo.getPlaca()) != null) {
            throw new IllegalArgumentException("Placa já cadastrada.");
        }

        veiculoDAO.inserir(veiculo);
    }

    public List<Veiculo> listarVeiculos() {
        return veiculoDAO.buscarTodos();
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {
        return veiculoDAO.buscarPorId(placa);
    }

    public void editarVeiculo(String placaOriginal, Veiculo atualizado) {

        Veiculo original = buscarVeiculoPorPlaca(placaOriginal);

        if (original == null) {
            throw new IllegalArgumentException("Veículo não encontrado.");
        }

        if (!original.isDisponivel()) {
            throw new IllegalStateException(
                    "Não é possível editar um veículo alugado."
            );
        }

        Veiculo existente = buscarVeiculoPorPlaca(atualizado.getPlaca());

        if (existente != null
                && !existente.getPlaca().equalsIgnoreCase(placaOriginal)) {
            throw new IllegalArgumentException("Placa já cadastrada.");
        }

        for (Contrato contrato : contratoDAO.buscarTodos()) {
            if (contrato.getVeiculoAlugado().getPlaca()
                    .equalsIgnoreCase(placaOriginal)
                    && !placaOriginal.equalsIgnoreCase(atualizado.getPlaca())) {
                throw new IllegalStateException(
                        "Não altere a placa de um veículo que possui histórico de locações."
                );
            }
        }

        atualizado.setDisponivelPersistido(original.isDisponivel());
        veiculoDAO.atualizar(placaOriginal, atualizado);
    }

    public void excluirVeiculo(String placa) {

        Veiculo veiculo = buscarVeiculoPorPlaca(placa);

        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não encontrado.");
        }

        if (!veiculo.isDisponivel()) {
            throw new IllegalStateException(
                    "Não é possível excluir um veículo alugado."
            );
        }

        for (Contrato contrato : contratoDAO.buscarTodos()) {
            if (contrato.getVeiculoAlugado().getPlaca()
                    .equalsIgnoreCase(placa)) {
                throw new IllegalStateException(
                        "Não é possível excluir veículo com histórico de locações."
                );
            }
        }

        veiculoDAO.excluir(placa);
    }

    // =========================
    // LOCAÇÕES
    // =========================

    public void registrarLocacao(Contrato contrato) {

        if (contrato == null) {
            throw new IllegalArgumentException("Locação não informada.");
        }

        Cliente cliente = buscarClientePorCpf(
                contrato.getClienteResponsavel().getCpf()
        );

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "Cliente não cadastrado."
            );
        }

        Veiculo veiculo = buscarVeiculoPorPlaca(
                contrato.getVeiculoAlugado().getPlaca()
        );

        if (veiculo == null) {
            throw new IllegalArgumentException(
                    "Veículo não cadastrado."
            );
        }

        if (!veiculo.isDisponivel()) {
            throw new IllegalStateException(
                    "Este veículo não está disponível."
            );
        }

        contratoDAO.inserir(contrato);
    }

    public List<Contrato> listarContratos() {
        return contratoDAO.buscarTodos();
    }

    public List<Contrato> listarLocacoesAtivas() {

        List<Contrato> ativas = new java.util.ArrayList<>();

        for (Contrato contrato : contratoDAO.buscarTodos()) {
            if (!contrato.isEncerrado()) {
                ativas.add(contrato);
            }
        }

        return ativas;
    }

    public Recibo fecharLocacao(Contrato contrato) {

        if (contrato == null) {
            throw new IllegalArgumentException("Selecione uma locação.");
        }

        if (contrato.isEncerrado()) {
            throw new IllegalStateException("Esta locação já foi encerrada.");
        }

        contratoDAO.fecharLocacao(contrato.getId());

        // Atualiza também o objeto que está na tela.
        contrato.encerrarLocacao();

        return new Recibo(contrato);
    }
}
