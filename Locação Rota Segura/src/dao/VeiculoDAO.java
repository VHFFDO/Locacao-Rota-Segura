package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO implements DAO<Veiculo> {

    @Override
    public void inserir(Veiculo veiculo) {

        String sql = "INSERT INTO veiculos "
                + "(placa, marca, modelo, ano, cor, categoria, disponivel) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = GestorDeConexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, veiculo.getPlaca());
            comando.setString(2, veiculo.getMarca());
            comando.setString(3, veiculo.getModelo());
            comando.setInt(4, veiculo.getAno());
            comando.setString(5, veiculo.getCor());
            comando.setString(6, veiculo.getCategoria());
            comando.setBoolean(7, veiculo.isDisponivel());

            comando.executeUpdate();

        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Erro ao cadastrar veículo: " + erro.getMessage(), erro
            );
        }
    }

    @Override
    public void atualizar(String placaOriginal, Veiculo veiculo) {

        String sql = "UPDATE veiculos SET placa=?, marca=?, modelo=?, "
                + "ano=?, cor=?, categoria=? WHERE placa=?";

        try (Connection conexao = GestorDeConexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, veiculo.getPlaca());
            comando.setString(2, veiculo.getMarca());
            comando.setString(3, veiculo.getModelo());
            comando.setInt(4, veiculo.getAno());
            comando.setString(5, veiculo.getCor());
            comando.setString(6, veiculo.getCategoria());
            comando.setString(7, placaOriginal);

            if (comando.executeUpdate() == 0) {
                throw new IllegalArgumentException("Veículo não encontrado.");
            }

        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Erro ao atualizar veículo: " + erro.getMessage(), erro
            );
        }
    }

    @Override
    public void excluir(String placa) {

        String sql = "DELETE FROM veiculos WHERE placa=?";

        try (Connection conexao = GestorDeConexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, placa);

            if (comando.executeUpdate() == 0) {
                throw new IllegalArgumentException("Veículo não encontrado.");
            }

        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Não foi possível excluir o veículo. "
                            + "Verifique se existe histórico de locações.",
                    erro
            );
        }
    }

    @Override
    public Veiculo buscarPorId(String placa) {

        String sql = "SELECT * FROM veiculos WHERE placa=?";

        try (Connection conexao = GestorDeConexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, placa);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return montarVeiculo(resultado);
                }
            }

            return null;

        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Erro ao buscar veículo: " + erro.getMessage(), erro
            );
        }
    }

    @Override
    public List<Veiculo> buscarTodos() {

        List<Veiculo> veiculos = new ArrayList<>();

        String sql = "SELECT * FROM veiculos ORDER BY modelo";

        try (Connection conexao = GestorDeConexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                veiculos.add(montarVeiculo(resultado));
            }

            return veiculos;

        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Erro ao listar veículos: " + erro.getMessage(), erro
            );
        }
    }

    private Veiculo montarVeiculo(ResultSet resultado) throws SQLException {

        String categoria = resultado.getString("categoria");

        String marca = resultado.getString("marca");
        String modelo = resultado.getString("modelo");
        int ano = resultado.getInt("ano");
        String cor = resultado.getString("cor");
        String placa = resultado.getString("placa");

        Veiculo veiculo;

        switch (categoria) {
            case "Popular":
                veiculo = new Popular(marca, modelo, ano, cor, placa);
                break;

            case "Sedan":
                veiculo = new Sedan(marca, modelo, ano, cor, placa);
                break;

            case "SUV":
                veiculo = new Suv(marca, modelo, ano, cor, placa);
                break;

            default:
                throw new SQLException(
                        "Categoria de veículo desconhecida: " + categoria
                );
        }

        veiculo.setDisponivelPersistido(
                resultado.getBoolean("disponivel")
        );

        return veiculo;
    }
}
