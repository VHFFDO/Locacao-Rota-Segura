package dao;

import model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements DAO<Cliente> {

    @Override
    public void inserir(Cliente cliente) {

        String sql = "INSERT INTO clientes "
                + "(cpf, nome, telefone, email) VALUES (?, ?, ?, ?)";

        try (Connection conexao = GestorDeConexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, cliente.getCpf());
            comando.setString(2, cliente.getNome());
            comando.setString(3, cliente.getTelefone());
            comando.setString(4, cliente.getEmail());

            comando.executeUpdate();

        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Não foi possível cadastrar o cliente: "
                            + erro.getMessage(), erro
            );
        }
    }

    @Override
    public void atualizar(String cpfOriginal, Cliente cliente) {

        String sql = "UPDATE clientes SET cpf=?, nome=?, telefone=?, email=? "
                + "WHERE cpf=?";

        try (Connection conexao = GestorDeConexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, cliente.getCpf());
            comando.setString(2, cliente.getNome());
            comando.setString(3, cliente.getTelefone());
            comando.setString(4, cliente.getEmail());
            comando.setString(5, cpfOriginal);

            int linhas = comando.executeUpdate();

            if (linhas == 0) {
                throw new IllegalArgumentException("Cliente não encontrado.");
            }

        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Não foi possível atualizar o cliente: "
                            + erro.getMessage(), erro
            );
        }
    }

    @Override
    public void excluir(String cpf) {

        String sql = "DELETE FROM clientes WHERE cpf=?";

        try (Connection conexao = GestorDeConexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, cpf);
            int linhas = comando.executeUpdate();

            if (linhas == 0) {
                throw new IllegalArgumentException("Cliente não encontrado.");
            }

        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Não foi possível excluir o cliente. "
                            + "Verifique se ele possui contratos registrados.",
                    erro
            );
        }
    }

    @Override
    public Cliente buscarPorId(String cpf) {

        String sql = "SELECT * FROM clientes WHERE cpf=?";

        try (Connection conexao = GestorDeConexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, cpf);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return montarCliente(resultado);
                }
            }

            return null;

        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Erro ao buscar cliente: " + erro.getMessage(), erro
            );
        }
    }

    @Override
    public List<Cliente> buscarTodos() {

        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT * FROM clientes ORDER BY nome";

        try (Connection conexao = GestorDeConexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                clientes.add(montarCliente(resultado));
            }

            return clientes;

        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Erro ao listar clientes: " + erro.getMessage(), erro
            );
        }
    }

    private Cliente montarCliente(ResultSet resultado) throws SQLException {

        return new Cliente(
                resultado.getString("nome"),
                resultado.getString("cpf"),
                resultado.getString("telefone"),
                resultado.getString("email")
        );
    }
}
