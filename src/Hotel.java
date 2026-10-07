import java.util.ArrayList;
import java.time.LocalDateTime;

public class Hotel {
    private ArrayList<Quarto> quartos;
    private ArrayList<Reserva> reservas;
    private ArrayList<Hospede> hospedes;
    private ArrayList<Divida> dividas;

    public Hotel() {
        quartos = new ArrayList<>();
        reservas = new ArrayList<>();
        hospedes = new ArrayList<>();
        dividas = new ArrayList<>();
    }
    public boolean cadastrarQuarto(Quarto quarto) {
        if (quarto == null) {
            return false;
        }
        if (quarto.getTipo() == null || quarto.getTipo().isBlank()) {
            return false;
        }
        if (quarto.getNumero() == null || quarto.getNumero() <= 0) {
            return false;
        }
        if (quarto.getCapacidade() == null || quarto.getCapacidade() <= 0) {
            return false;
        }
        if (quarto.getValorDiaria() == null || quarto.getValorDiaria() <= 0) {
            return false;
        }
        for (Quarto q : quartos) {
            if (q.getNumero().equals(quarto.getNumero())) {
                return false;
            }
        }
        quartos.add(quarto);
        return true;
    }
    public boolean cadastrarReserva(Reserva reserva) {
        if (reserva == null) {
            return false;
        }
        reservas.add(reserva);
        return true;
    }
    public boolean cadastrarDivida(Divida divida){
        if (divida == null) {
            return false;
        }
        dividas.add(divida);
        return true;
    }
    public boolean cadastrarHospede(Hospede hospede) {
        if (hospede == null) {
            return false;
        }
        if (hospede.getNome() == null || hospede.getNome().isBlank()) {
            return false;
        }
        if (hospede.getCpf() == null || hospede.getCpf().isBlank()) {
            return false;
        }
        for (Hospede h : hospedes) {
            if (h.getCpf().equals(hospede.getCpf())) {
                return false;
            }
        }
        hospedes.add(hospede);
        return true;
    }
    public boolean quartoDisponivel(Quarto quarto, LocalDateTime entrada, LocalDateTime saida) {
        if (quarto == null || entrada == null || saida == null) {
            return false;
        }
        if (!entrada.isBefore(saida)) {
            return false;
        }
        if (quarto.getStatusLimpeza() != StatusLimpeza.LIMPO){
            return false;
        }
        for (Reserva reserva : reservas) {
            if (!reserva.getCancelada() && reserva.getQuarto().getNumero().equals(quarto.getNumero())) {
                if (entrada.isBefore(reserva.getHorarioLiberacao()) && saida.isAfter(reserva.getDataEntrada())) {
                    return false;
                }
            }
        }
        return true;
    }
    public ArrayList<Reserva> consultarReservasPorHospede(Hospede hospede) {
        if (hospede == null) {
            return new ArrayList<>();
        }
        ArrayList<Reserva> resultado = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getHospede().getCpf().equals(hospede.getCpf())) {
                resultado.add(reserva);
            }
        }
        return resultado;
    }
    public ArrayList<Reserva> consultarReservasPorQuarto(Quarto quarto) {
        if (quarto == null) {
            return new ArrayList<>();
        }
        ArrayList<Reserva> resultado = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getQuarto().getNumero().equals(quarto.getNumero())) {
                resultado.add(reserva);
            }
        }
        return resultado;
    }
    public ArrayList<Quarto> listarQuartos() {
        return new ArrayList<>(quartos);
    }
    public ArrayList<Hospede> listarHospedes() {
        return new ArrayList<>(hospedes);
    }
    public ArrayList<Reserva> listarReservas() {
        return new ArrayList<>(reservas);
    }
    public ArrayList<Divida> listarDividas() {
        return new ArrayList<>(dividas);
    }
    public Hospede buscarHospedePorCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            return null;
        }
        for (Hospede hospede : hospedes) {
            if (hospede.getCpf().equals(cpf)) {
                return hospede;
            }
        }
        return null;
    }
    public Quarto buscarQuartoPorNumero(Integer numero) {
        if (numero == null || numero <= 0) {
            return null;
        }
        for (Quarto quarto : quartos) {
            if (quarto.getNumero().equals(numero)) {
                return quarto;
            }
        }
        return null;
    }
    public ArrayList<Divida> consultarDividasPorHospede(Hospede hospede) {
        if (hospede == null) {
            return new ArrayList<>();
        }
        ArrayList<Divida> resultado = new ArrayList<>();
        for (Divida divida : dividas) {
            if (divida.getHospede().getCpf().equals(hospede.getCpf())) {
                resultado.add(divida);
            }
        }
        return resultado;
    }
    public Double calcularTotalDevidoPorHospede(Hospede hospede){
        if (hospede == null) {
            return 0.0;
        }
        Double total = 0.0;
        for (Divida divida : dividas) {
            if (divida.getHospede().getCpf().equals(hospede.getCpf())) {
                total += divida.calcularSaldo();
            }
        }
        return total;
    }
}
