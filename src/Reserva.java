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

    public Reserva(Hospede hospede, Quarto quarto, LocalDateTime dataEntrada, LocalDateTime dataSaida) {
        this.hospede = hospede;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.horarioLiberacao = dataSaida.plusHours(2);
    }

    public void calcularDiarias() {
        quantidadeDiarias = ChronoUnit.DAYS.between(dataEntrada, dataSaida);
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
}