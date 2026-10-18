package com.example.sistemahotel.model;

public class Divida {
    private final Hospede hospede;
    private final Reserva reserva;
    private final String descricao;
    private final double valor;
    private double valorPago;
    private boolean paga;
    private FormaPagamento formaPagamento;

    public Divida(Hospede hospede, Reserva reserva, String descricao, Double valor) {
        if (hospede == null || reserva == null || descricao == null || descricao.isBlank() || valor == null || valor < 0) {
            throw new IllegalArgumentException("Dados da dívida inválidos.");
        }
        this.hospede = hospede;
        this.reserva = reserva;
        this.descricao = descricao;
        this.valor = valor;
        this.valorPago = 0.0;
        this.paga = valor == 0.0;
    }

    public double calcularSaldo() { return Math.max(0.0, valor - valorPago); }

    public boolean registrarPagamento(Double valorPagamento, FormaPagamento formaPagamento) {
        if (valorPagamento == null || !Double.isFinite(valorPagamento) || valorPagamento <= 0 || formaPagamento == null || paga) return false;
        if (valorPagamento > calcularSaldo() + 0.000001) return false;
        valorPago = Math.min(valor, valorPago + valorPagamento);
        this.formaPagamento = formaPagamento;
        paga = calcularSaldo() < 0.000001;
        return true;
    }

    public Hospede getHospede() { return hospede; }
    public Reserva getReserva() { return reserva; }
    public String getDescricao() { return descricao; }
    public Double getValor() { return valor; }
    public Double getValorPago() { return valorPago; }
    public Boolean getPaga() { return paga; }
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
}
