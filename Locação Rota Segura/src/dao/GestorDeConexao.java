package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GestorDeConexao {

    public static Connection conectar() throws SQLException {

        Properties propriedades = new Properties();

        try (FileInputStream arquivo =
                     new FileInputStream("configs.txt")) {

            propriedades.load(arquivo);

        } catch (IOException erro) {
            throw new RuntimeException(
                    "Não foi possível ler o arquivo configs.txt.", erro
            );
        }

        String url = propriedades.getProperty("url");
        String usuario = propriedades.getProperty("usuario");
        String senha = propriedades.getProperty("senha");

        return DriverManager.getConnection(url, usuario, senha);
    }
}
