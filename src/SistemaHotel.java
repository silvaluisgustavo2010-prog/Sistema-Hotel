    import java.time.LocalDateTime;
    import java.util.ArrayList;

    public class SistemaHotel {
        private Hotel hotel;

        public SistemaHotel(Hotel hotel) {
            this.hotel = hotel;
        }
        public boolean cadastrarHospede(String nome, String cpf) {
            Hospede hospede = new Hospede(nome, cpf);
            return hotel.cadastrarHospede(hospede);
        }
        public boolean cadastrarQuarto(String tipo, Integer numero, Integer capacidade, Double valorDiaria) {
            Quarto quarto = new Quarto(tipo, numero, capacidade, valorDiaria);
            return hotel.cadastrarQuarto(quarto);
        }
        public boolean fazerReserva(Hospede hospede, Quarto quarto, LocalDateTime entrada, LocalDateTime saida) {
            if (hospede == null || quarto == null || entrada == null || saida == null) {
                return false;
            }
            Hospede hospedeCadastrado = hotel.buscarHospedePorCpf(hospede.getCpf());
            Quarto quartoCadastrado = hotel.buscarQuartoPorNumero(quarto.getNumero());
            if (hospedeCadastrado == null || quartoCadastrado == null) {
                return false;
            }
            if (!entrada.isBefore(saida)) {
                return false;
            }
            if (!hotel.quartoDisponivel(quartoCadastrado, entrada, saida)) {
                return false;
            }
            Reserva reserva = new Reserva(hospedeCadastrado, quartoCadastrado, entrada, saida);
            reserva.calcularDiarias();
            reserva.calcularValorTotal();
            return hotel.cadastrarReserva(reserva);
        }
        public ArrayList<Hospede> listarHospedes() {
            return hotel.listarHospedes();
        }
        public ArrayList<Quarto> listarQuartos() {
            return hotel.listarQuartos();
        }
        public boolean pagarReserva(Reserva reserva, FormaPagamento formaPagamento) {
            if (reserva == null) {
                return false;
            }
            if (formaPagamento == null) {
                return false;
            }
            if (reserva.getPaga()) {
                return false;
            }
            reserva.marcarComoPaga(formaPagamento);
            return true;
        }
        public ArrayList<Reserva> consultarReservasPorHospede(Hospede hospede) {
            if (hospede == null) {
                return new ArrayList<>();
            }
            return hotel.consultarReservasPorHospede(hospede);
        }
        public ArrayList<Reserva> consultarReservasPorQuarto(Quarto quarto) {
            if (quarto == null) {
                return new ArrayList<>();
            }
            return hotel.consultarReservasPorQuarto(quarto);
        }
        public ArrayList<Reserva> listarReservas() {
            return hotel.listarReservas();
        }
    }