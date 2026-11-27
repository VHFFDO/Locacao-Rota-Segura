package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratoDAO implements DAO<Contrato> {

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final VeiculoDAO veiculoDAO = new VeiculoDAO();

    @Override
    public void inserir(Contrato contrato) {

        String atualizarVeiculo =
                "UPDATE veiculos SET disponivel=FALSE "
                        + "WHERE placa=? AND disponivel=TRUE";

        String inserirContrato =
                "INSERT INTO contratos "
                        + "(cpf_cliente, placa_veiculo, data_inicio, data_fim, encerrado) "
                        + "VALUES (?, ?, ?, ?, FALSE)";

        try (Connection conexao = GestorDeConexao.conectar()) {

            conexao.setAutoCommit(false);

            try {
                try (PreparedStatement comando =
                             conexao.prepareStatement(atualizarVeiculo)) {

                    comando.setString(
                            1, contrato.getVeiculoAlugado().getPlaca()
                    );

                    if (comando.executeUpdate() != 1) {
                        throw new IllegalStateException(
                                "Veículo não encontrado ou já alugado."
                        );
                    }
                }

                try (PreparedStatement comando =
                             conexao.prepareStatement(
                                     inserirContrato,
                                     Statement.RETURN_GENERATED_KEYS
                             )) {

                    comando.setString(
                            1, contrato.getClienteResponsavel().getCpf()
                    );
                    comando.setString(
                            2, contrato.getVeiculoAlugado().getPlaca()
                    );
                    comando.setDate(
                            3, Date.valueOf(contrato.getDataInicio())
                    );
                    comando.setDate(
                            4, Date.valueOf(contrato.getDataFim())
                    );

                    comando.executeUpdate();

                    try (ResultSet chaves = comando.getGeneratedKeys()) {
                        if (chaves.next()) {
                            contrato.setId(chaves.getInt(1));
                        }
                    }
                }

                conexao.commit();

            } catch (Exception erro) {
                conexao.rollback();
                throw erro;
            }

        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Erro ao registrar locação: " + erro.getMessage(), erro
            );
        }
    }

    public void fecharLocacao(int idContrato) {

        String consultar =
                "SELECT placa_veiculo, encerrado "
                        + "FROM contratos WHERE id=?";

        String atualizarContrato =
                "UPDATE contratos SET encerrado=TRUE "
                        + "WHERE id=? AND encerrado=FALSE";

        String devolverVeiculo =
                "UPDATE veiculos SET disponivel=TRUE WHERE placa=?";

        try (Connection conexao = GestorDeConexao.conectar()) {

            conexao.setAutoCommit(false);

            try {
                String placa;
                boolean encerrado;

                try (PreparedStatement comando =
                             conexao.prepareStatement(consultar)) {

                    comando.setInt(1, idContrato);

                    try (ResultSet resultado = comando.executeQuery()) {
                        if (!resultado.next()) {
                            throw new IllegalArgumentException(
                                    "Contrato não encontrado."
                            );
                        }

                        placa = resultado.getString("placa_veiculo");
                        encerrado = resultado.getBoolean("encerrado");
                    }
                }

                if (encerrado) {
                    throw new IllegalStateException(
                            "Esta locação já foi encerrada."
                    );
                }

                try (PreparedStatement comando =
                             conexao.prepareStatement(atualizarContrato)) {

                    comando.setInt(1, idContrato);

                    if (comando.executeUpdate() != 1) {
                        throw new SQLException(
                                "Não foi possível encerrar o contrato."
                        );
                    }
                }

                try (PreparedStatement comando =
                             conexao.prepareStatement(devolverVeiculo)) {

                    comando.setString(1, placa);

                    if (comando.executeUpdate() != 1) {
                        throw new SQLException(
                                "Não foi possível devolver o veículo."
                        );
                    }
                }

                conexao.commit();

            } catch (Exception erro) {
                conexao.rollback();
                throw erro;
            }

        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Erro ao fechar locação: " + erro.getMessage(), erro
            );
        }
    }

    @Override
    public void atualizar(String id, Contrato contrato) {

        String sql = "UPDATE contratos SET cpf_cliente=?, placa_veiculo=?, "
                + "data_inicio=?, data_fim=? WHERE id=? AND encerrado=FALSE";

        try (Connection conexao = GestorDeConexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, contrato.getClienteResponsavel().getCpf());
            comando.setString(2, contrato.getVeiculoAlugado().getPlaca());
            comando.setDate(3, Date.valueOf(contrato.getDataInicio()));
            comando.setDate(4, Date.valueOf(contrato.getDataFim()));
            comando.setInt(5, Integer.parseInt(id));

            if (comando.executeUpdate() == 0) {
                throw new IllegalArgumentException(
                        "Contrato não encontrado ou já encerrado."
                );
            }

        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Erro ao atualizar contrato: " + erro.getMessage(), erro
            );
        }
    }

    @Override
    public void excluir(String id) {

        String consultar =
                "SELECT placa_veiculo, encerrado FROM contratos WHERE id=?";

        String excluir = "DELETE FROM contratos WHERE id=?";

        try (Connection conexao = GestorDeConexao.conectar()) {

            conexao.setAutoCommit(false);

            try {
                String placa;
                boolean encerrado;

                try (PreparedStatement comando =
                             conexao.prepareStatement(consultar)) {

                    comando.setInt(1, Integer.parseInt(id));

                    try (ResultSet resultado = comando.executeQuery()) {
                        if (!resultado.next()) {
                            throw new IllegalArgumentException(
                                    "Contrato não encontrado."
                            );
                        }

                        placa = resultado.getString("placa_veiculo");
                        encerrado = resultado.getBoolean("encerrado");
                    }
                }

                try (PreparedStatement comando =
                             conexao.prepareStatement(excluir)) {

                    comando.setInt(1, Integer.parseInt(id));
                    comando.executeUpdate();
                }

                if (!encerrado) {
                    try (PreparedStatement comando =
                                 conexao.prepareStatement(
                                         "UPDATE veiculos SET disponivel=TRUE "
                                                 + "WHERE placa=?"
                                 )) {

                        comando.setString(1, placa);
                        comando.executeUpdate();
                    }
                }

                conexao.commit();

            } catch (Exception erro) {
                conexao.rollback();
                throw erro;
            }

        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Erro ao excluir contrato: " + erro.getMessage(), erro
            );
        }
    }

    @Override
    public Contrato buscarPorId(String id) {

        String sql = "SELECT * FROM contratos WHERE id=?";

        try (Connection conexao = GestorDeConexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, Integer.parseInt(id));

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return montarContrato(resultado);
                }
            }

            return null;

        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Erro ao buscar contrato: " + erro.getMessage(), erro
            );
        }
    }

    @Override
    public List<Contrato> buscarTodos() {

        List<Contrato> contratos = new ArrayList<>();

        String sql = "SELECT * FROM contratos ORDER BY id DESC";

        try (Connection conexao = GestorDeConexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                contratos.add(montarContrato(resultado));
            }

            return contratos;

        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Erro ao listar contratos: " + erro.getMessage(), erro
            );
        }
    }

    private Contrato montarContrato(ResultSet resultado) throws SQLException {

        Cliente cliente = clienteDAO.buscarPorId(
                resultado.getString("cpf_cliente")
        );

        Veiculo veiculo = veiculoDAO.buscarPorId(
                resultado.getString("placa_veiculo")
        );

        if (cliente == null || veiculo == null) {
            throw new SQLException(
                    "Cliente ou veículo do contrato não encontrado."
            );
        }

        Contrato contrato = new Contrato(
                cliente,
                veiculo,
                resultado.getDate("data_inicio").toLocalDate(),
                resultado.getDate("data_fim").toLocalDate()
        );

        contrato.setId(resultado.getInt("id"));

        if (resultado.getBoolean("encerrado")) {
            contrato.marcarEncerrado();
        }

        return contrato;
    }
}
