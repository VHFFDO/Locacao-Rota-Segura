package dao;

import java.util.List;

public interface DAO<T> {

    void inserir(T objeto);

    void atualizar(String identificadorOriginal, T objeto);

    void excluir(String identificador);

    T buscarPorId(String identificador);

    List<T> buscarTodos();
}
