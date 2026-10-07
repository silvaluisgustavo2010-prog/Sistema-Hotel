public class Quarto {
    private String tipo;
    private Integer numero;
    private Integer capacidade;
    private Double valorDiaria;
    private StatusLimpeza statusLimpeza;

    public Quarto(String tipo, Integer numero, Integer capacidade, Double valorDiaria) {
        this.tipo = tipo;
        this.numero = numero;
        this.capacidade = capacidade;
        this.valorDiaria = valorDiaria;
        this.statusLimpeza = StatusLimpeza.LIMPO;
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
    public StatusLimpeza getStatusLimpeza() {
        return statusLimpeza;
    }
    public boolean marcarComoSujo() {
        if (statusLimpeza == StatusLimpeza.SUJO) {
            return false;
        }
        statusLimpeza = StatusLimpeza.SUJO;
        return true;
    }
    public boolean inicializarLimpeza() {
        if (statusLimpeza == StatusLimpeza.EM_LIMPEZA) {
            return false;
        }
        statusLimpeza = StatusLimpeza.EM_LIMPEZA;
        return true;
    }
    public boolean finalizarLimpeza() {
        if (statusLimpeza == StatusLimpeza.LIMPO || statusLimpeza == StatusLimpeza.SUJO) {
            return false;
        }
        statusLimpeza = StatusLimpeza.LIMPO;
        return true;
    }
}
