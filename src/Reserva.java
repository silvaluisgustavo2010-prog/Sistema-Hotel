import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reserva {
    private Hospede hospede;
    private Quarto quarto;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private long quantidadeDiarias;
    private double valorTotal;

    public Reserva(Hospede hospede, Quarto quarto, LocalDate dataEntrada, LocalDate dataSaida) {
        this.hospede = hospede;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
    }

    public void calcularDiarias() {
        quantidadeDiarias = ChronoUnit.DAYS.between(dataEntrada, dataSaida);
    }

    public void calcularValorTotal() {
        valorTotal = quantidadeDiarias * quarto.getValorDiaria();
    }
}
