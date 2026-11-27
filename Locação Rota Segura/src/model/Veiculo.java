package model;

public abstract class Veiculo {

    private String marca;
    private String modelo;
    private int ano;
    private String cor;
    private String placa;
    private boolean disponivel;

    public Veiculo(String marca, String modelo, int ano,
                   String cor, String placa) {
        setMarca(marca);
        setModelo(modelo);
        setAno(ano);
        setCor(cor);
        setPlaca(placa);
        this.disponivel = true;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getCategoria() {
        if (this instanceof Popular) return "Popular";
        if (this instanceof Sedan) return "Sedan";
        if (this instanceof Suv) return "SUV";
        return "Não identificada";
    }

    public int getAno() {
        return ano;
    }

    public String getCor() {
        return cor;
    }

    public String getPlaca() {
        return placa;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    // Usado pelo DAO ao recuperar a situação salva no banco.
    public void setDisponivelPersistido(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe a marca.");
        }
        this.marca = marca.trim();
    }

    public void setModelo(String modelo) {
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o modelo.");
        }
        this.modelo = modelo.trim();
    }

    public void setAno(int ano) {
        if (ano < 1900 || ano > 2100) {
            throw new IllegalArgumentException("Ano inválido.");
        }
        this.ano = ano;
    }

    public void setCor(String cor) {
        if (cor == null || cor.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe a cor.");
        }
        this.cor = cor.trim();
    }

    public void setPlaca(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe a placa.");
        }
        this.placa = placa.trim().toUpperCase();
    }

    public abstract double calcularDiaria();

    public abstract double calcularSeguro();

    public abstract double calcularManutencao();

    public void alugar() {
        if (!disponivel) {
            throw new IllegalStateException("O veículo está indisponível.");
        }
        disponivel = false;
    }

    public void devolver() {
        if (disponivel) {
            throw new IllegalStateException("O veículo já está disponível.");
        }
        disponivel = true;
    }
}
