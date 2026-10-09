import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Reserva {
    private Hospede hospede;
    private Quarto quarto;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private long quantidadeDiarias;
    private double valorTotal;
    private LocalDateTime horarioLiberacao;
    private Boolean paga;
    private FormaPagamento formaPagamento;
    private Boolean cancelada;
    private LocalDateTime horarioCheckOut;

    public Reserva(Hospede hospede, Quarto quarto, LocalDateTime dataEntrada, LocalDateTime dataSaida) {
        if (hospede == null || quarto == null || dataEntrada == null || dataSaida == null) {
            throw new IllegalArgumentException("Os dados da reserva são obrigatórios.");
        }
        if (!dataEntrada.isBefore(dataSaida)) {
            throw new IllegalArgumentException("A data de entrada deve ser anterior à data de saída.");
        }
        this.hospede = hospede;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.horarioLiberacao = dataSaida.plusHours(2);
        this.paga = false;
        this.cancelada = false;
    }
    public void calcularDiarias() {
        quantidadeDiarias = ChronoUnit.DAYS.between(dataEntrada.toLocalDate(), dataSaida.toLocalDate());
    }

    public void calcularValorTotal() {
        valorTotal = quantidadeDiarias * quarto.getValorDiaria();
    }

    public long getQuantidadeDiarias() {
        return quantidadeDiarias;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public Quarto getQuarto() {
        return quarto;
    }
    public LocalDateTime getHorarioLiberacao() {
        return horarioLiberacao;
    }
    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }
    public LocalDateTime getDataSaida() {
        return dataSaida;
    }
    public Boolean getPaga() {
        return paga;
    }
    public void marcarComoPaga(FormaPagamento formaPagamento) {
        paga = true;
        this.formaPagamento = formaPagamento;
    }
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }
    public Boolean getCancelada() {
        return cancelada;
    }
    public void cancelar() {
        this.cancelada = true;
    }
    public void registrarCheckOut() {
        horarioCheckOut = LocalDateTime.now();
        horarioLiberacao = horarioCheckOut.plusHours(2);
    }
    public LocalDateTime getHorarioCheckOut() {
        return horarioCheckOut;
    }
    public boolean jaFezCheckout() {
        return horarioCheckOut != null;
    }
    public void recalcularValorSaidaAntecipada() {
        long horas = java.time.Duration.between(dataEntrada, horarioCheckOut).toHours();
        quantidadeDiarias = Math.max(1,(horas + 23) / 24);
        calcularValorTotal();
    }
}