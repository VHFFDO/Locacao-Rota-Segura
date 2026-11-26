package service;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class SistemaLocacao {
    //Inicio Atributos
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Veiculo> veiculos = new ArrayList<>();
    private final List<Contrato> contratos = new ArrayList<>();
    //Fim Atributos

    public void cadastrarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente inválido.");
        }

        if (buscarClientePorCpf(cliente.getCpf()) != null) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        clientes.add(cliente);
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }

    public Cliente buscarClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf.replaceAll("\\D", ""))) {
                return cliente;
            }
        }
        return null;
    }

    public void excluirCliente(String cpf) {
        Cliente cliente = buscarClientePorCpf(cpf);

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }

        for (Contrato contrato : contratos) {
            if (contrato.getClienteResponsavel() == cliente) {
                throw new IllegalStateException(
                        "Não é possível excluir cliente com histórico de locações."
                );
            }
        }

        clientes.remove(cliente);
    }

    public void editarCliente(String cpfOriginal, Cliente atualizado) {
        Cliente original = buscarClientePorCpf(cpfOriginal);

        if (original == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }

        Cliente cpfExistente = buscarClientePorCpf(atualizado.getCpf());

        if (cpfExistente != null && cpfExistente != original) {
            throw new IllegalArgumentException("CPF já pertence a outro cliente.");
        }

        original.setNome(atualizado.getNome());
        original.setCpf(atualizado.getCpf());
        original.setTelefone(atualizado.getTelefone());
    }

    // VEÍCULOS

    public void cadastrarVeiculo(Veiculo veiculo) {
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo inválido.");
        }

        if (buscarVeiculoPorPlaca(veiculo.getPlaca()) != null) {
            throw new IllegalArgumentException("Placa já cadastrada.");
        }

        veiculos.add(veiculo);
    }

    public List<Veiculo> listarVeiculos() {
        return new ArrayList<>(veiculos);
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa.trim())) {
                return veiculo;
            }
        }
        return null;
    }

    public void excluirVeiculo(String placa) {
        Veiculo veiculo = buscarVeiculoPorPlaca(placa);

        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não encontrado.");
        }

        if (!veiculo.isDisponivel()) {
            throw new IllegalStateException("Veículo está alugado.");
        }

        for (Contrato contrato : contratos) {
            if (contrato.getVeiculoAlugado() == veiculo) {
                throw new IllegalStateException(
                        "Veículo possui histórico de locações."
                );
            }
        }

        veiculos.remove(veiculo);
    }

    public void editarVeiculo(String placaOriginal, Veiculo atualizado) {
        Veiculo original = buscarVeiculoPorPlaca(placaOriginal);

        if (original == null) {
            throw new IllegalArgumentException("Veículo não encontrado.");
        }

        if (!original.isDisponivel()) {
            throw new IllegalStateException("Não edite um veículo alugado.");
        }

        Veiculo placaExistente = buscarVeiculoPorPlaca(atualizado.getPlaca());

        if (placaExistente != null && placaExistente != original) {
            throw new IllegalArgumentException("Placa já cadastrada.");
        }

        original.setMarca(atualizado.getMarca());
        original.setModelo(atualizado.getModelo());
        original.setAno(atualizado.getAno());
        original.setCor(atualizado.getCor());
        original.setPlaca(atualizado.getPlaca());
    }

    // LOCAÇÕES

    public void registrarLocacao(Contrato contrato) {
        if (contrato == null) {
            throw new IllegalArgumentException("Locação não informada.");
        }

        if (!clientes.contains(contrato.getClienteResponsavel())) {
            throw new IllegalArgumentException("Cliente não cadastrado.");
        }

        Veiculo veiculo = contrato.getVeiculoAlugado();

        if (!veiculos.contains(veiculo)) {
            throw new IllegalArgumentException("Veículo não cadastrado.");
        }

        if (!veiculo.isDisponivel()) {
            throw new IllegalStateException("Veículo indisponível.");
        }

        veiculo.alugar();
        contratos.add(contrato);
    }

    public List<Contrato> listarContratos() {
        return new ArrayList<>(contratos);
    }

    public List<Contrato> listarLocacoesAtivas() {
        List<Contrato> ativos = new ArrayList<>();

        for (Contrato contrato : contratos) {
            if (!contrato.isEncerrado()) {
                ativos.add(contrato);
            }
        }

        return ativos;
    }

    public Recibo fecharLocacao(Contrato contrato) {
        if (contrato == null || !contratos.contains(contrato)) {
            throw new IllegalArgumentException("Locação não encontrada.");
        }

        if (contrato.isEncerrado()) {
            throw new IllegalStateException("Locação já encerrada.");
        }

        contrato.getVeiculoAlugado().devolver();
        contrato.marcarEncerrado();

        return new Recibo(contrato);
    }
}