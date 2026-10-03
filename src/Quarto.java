public class Quarto {
    private String tipo;
    private Integer numero;
    private Integer capacidade;
    private Double valorDiaria;
    private Boolean disponivel;

    public Quarto(String tipo, Integer numero, Integer capacidade, Double valorDiaria) {
        this.tipo = tipo;
        this.numero = numero;
        this.capacidade = capacidade;
        this.valorDiaria = valorDiaria;
        this.disponivel = true;
    }

    public String getTipo() {
        return tipo;
    }

    public Integer getNumero() {
        return numero;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public Double getValorDiaria() {
        return valorDiaria;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }
}
