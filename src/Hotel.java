import java.util.ArrayList;
import java.time.LocalDateTime;

public class Hotel {
    private ArrayList<Quarto> quartos;
    private ArrayList<Reserva> reservas;
    private ArrayList<Hospede> hospedes;

    public Hotel() {
        quartos = new ArrayList<>();
        reservas = new ArrayList<>();
        hospedes = new ArrayList<>();
    }
    public void cadastrarQuarto(Quarto quarto) {
        quartos.add(quarto);
    }
    public void cadastrarReserva(Reserva reserva) {
        reservas.add(reserva);
    }
    public boolean cadastrarHospede(Hospede hospede) {
        for (Hospede h : hospedes) {
            if (h.getCpf().equals(hospede.getCpf())) {
                return false;
            }
        }
        hospedes.add(hospede);
        return true;
    }
    public boolean quartoDisponivel(Quarto quarto, LocalDateTime entrada, LocalDateTime saida) {
        for (Reserva reserva : reservas) {
            if (reserva.getQuarto().getNumero().equals(quarto.getNumero())) {
                if (entrada.isBefore(reserva.getHorarioLiberacao()) && saida.isAfter(reserva.getDataEntrada())) {
                    return false;
                }
            }
        }
        return true;
    }
    public ArrayList<Reserva> consultarReservasPorHospede(Hospede hospede) {
        ArrayList<Reserva> resultado = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getHospede().getCpf().equals(hospede.getCpf())) {
                resultado.add(reserva);
            }
        }
        return resultado;
    }
    public ArrayList<Reserva> consultarReservasPorQuarto(Quarto quarto) {
        ArrayList<Reserva> resultado = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getQuarto().getNumero().equals(quarto.getNumero())) {
                resultado.add(reserva);
            }
        }
        return resultado;
    }

    public ArrayList<Quarto> listarQuartos() {
        return quartos;
    }

    public ArrayList<Hospede> listarHospedes() {
        return hospedes;
    }
}
