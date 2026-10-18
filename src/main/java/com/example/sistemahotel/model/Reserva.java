package com.example.sistemahotel.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Reserva {
    private final Hospede hospede;
    private final Quarto quarto;
    private final LocalDateTime dataEntrada;
    private final LocalDateTime dataSaida;
    private long quantidadeDiarias;
    private double valorTotal;
    private LocalDateTime horarioLiberacao;
    private boolean paga;
    private FormaPagamento formaPagamento;
    private boolean cancelada;
    private LocalDateTime horarioCheckOut;

    public Reserva(Hospede hospede, Quarto quarto, LocalDateTime dataEntrada, LocalDateTime dataSaida) {
        if (hospede == null || quarto == null || dataEntrada == null || dataSaida == null)
            throw new IllegalArgumentException("Os dados da reserva são obrigatórios.");
        if (!dataEntrada.isBefore(dataSaida))
            throw new IllegalArgumentException("A entrada deve ser anterior à saída.");
        this.hospede = hospede;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.horarioLiberacao = dataSaida.plusHours(2);
        this.paga = false;
        this.cancelada = false;
        calcularDiarias();
        calcularValorTotal();
    }

    public void calcularDiarias() {
        long dias = ChronoUnit.DAYS.between(dataEntrada.toLocalDate(), dataSaida.toLocalDate());
        quantidadeDiarias = Math.max(1, dias);
    }

    public void calcularValorTotal() { valorTotal = quantidadeDiarias * quarto.getValorDiaria(); }

    public boolean marcarComoPaga(FormaPagamento formaPagamento) {
        if (formaPagamento == null || paga || cancelada) return false;
        this.formaPagamento = formaPagamento;
        this.paga = true;
        return true;
    }

    public void cancelar() {
        if (horarioCheckOut != null) throw new IllegalStateException("Não é possível cancelar após o checkout.");
        cancelada = true;
    }

    public boolean registrarCheckOut(LocalDateTime momento) {
        if (momento == null || horarioCheckOut != null || cancelada || momento.isBefore(dataEntrada)) return false;
        horarioCheckOut = momento;
        horarioLiberacao = momento.plusHours(2);
        recalcularValorSaidaAntecipada();
        return true;
    }

    public void recalcularValorSaidaAntecipada() {
        if (horarioCheckOut == null) return;
        long minutos = Math.max(0, Duration.between(dataEntrada, horarioCheckOut).toMinutes());
        quantidadeDiarias = Math.max(1, (minutos + (24 * 60) - 1) / (24 * 60));
        calcularValorTotal();
    }

    public Hospede getHospede() { return hospede; }
    public Quarto getQuarto() { return quarto; }
    public LocalDateTime getDataEntrada() { return dataEntrada; }
    public LocalDateTime getDataSaida() { return dataSaida; }
    public long getQuantidadeDiarias() { return quantidadeDiarias; }
    public double getValorTotal() { return valorTotal; }
    public LocalDateTime getHorarioLiberacao() { return horarioLiberacao; }
    public Boolean getPaga() { return paga; }
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public Boolean getCancelada() { return cancelada; }
    public LocalDateTime getHorarioCheckOut() { return horarioCheckOut; }
    public boolean jaFezCheckout() { return horarioCheckOut != null; }
}
