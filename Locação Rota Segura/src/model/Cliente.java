package model;

public class Cliente {

    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Cliente(String nome, String cpf, String telefone, String email) {
        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
        setEmail(email);
    }

    // Construtor antigo, mantido temporariamente
    public Cliente(String nome, String cpf, String telefone) {
        this(nome, cpf, telefone, "nao-informado@exemplo.com");
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o nome.");
        }
        this.nome = nome.trim();
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o CPF.");
        }

        String cpfLimpo = cpf.replaceAll("\\D", "");

        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException("O CPF deve conter 11 números.");
        }

        this.cpf = cpfLimpo;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o telefone.");
        }
        this.telefone = telefone.trim();
    }

    public void setEmail(String email) {
        if (email == null ||
                !email.trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Informe um e-mail válido.");
        }
        this.email = email.trim();
    }

    @Override
    public String toString() {
        return nome + " - " + cpf;
    }
}
