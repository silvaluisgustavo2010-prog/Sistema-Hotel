public class Divida {

    private Hospede hospede;
    private Reserva reserva;
    private String descricao;
    private Double valor;
    private Double valorPago;
    private Boolean paga;
    private FormaPagamento formaPagamento;
    public Divida(Hospede hospede, Reserva reserva, String descricao, Double valor) {
        this.hospede = hospede;
        this.reserva = reserva;
        this.descricao = descricao;
        this.valor = valor;
        this.valorPago = 0.0;
        this.paga = false;
    }
    public Double calcularSaldo() {
        return valor - valorPago;
    }
    public boolean registrarPagamento(Double valorPagamento, FormaPagamento formaPagamento) {
        if (valorPagamento == null || valorPagamento <= 0) {
            return false;
        }
        if (formaPagamento == null) {
            return false;
        }
        if (valorPagamento > calcularSaldo()) {
            return false;
        }
        valorPago += valorPagamento;
        this.formaPagamento = formaPagamento;
        if (calcularSaldo() == 0) {
            paga = true;
        }
        return true;
    }
    public Hospede getHospede() {
        return hospede;
    }
    public Reserva getReserva() {
        return reserva;
    }
    public String getDescricao() {
        return descricao;
    }
    public Double getValor() {
        return valor;
    }
    public Double getValorPago() {
        return valorPago;
    }
    public Boolean getPaga() {
        return paga;
    }
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }
}