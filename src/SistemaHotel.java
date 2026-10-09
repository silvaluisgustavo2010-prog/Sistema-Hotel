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
            if (hotel.calcularTotalDevidoPorHospede(hospedeCadastrado) > 0) {
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
        public ArrayList<Reserva> listarReservas() {
            return hotel.listarReservas();
        }
        public ArrayList<Divida> listarDividas() {
            return hotel.listarDividas();
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
        public ArrayList<Divida> consultarDividasPorHospede(Hospede hospede) {
            if (hospede == null) {
                return new ArrayList<>();
            }
            return hotel.consultarDividasPorHospede(hospede);
        }
        public Double calcularTotalDevidoPorHospede(Hospede hospede) {
            if (hospede == null) {
                return 0.0;
            }
            return hotel.calcularTotalDevidoPorHospede(hospede);
        }
        public boolean pagarDivida(Divida divida, Double valor, FormaPagamento formaPagamento){
            if (divida == null) {
                return false;
            }
            return divida.registrarPagamento(valor, formaPagamento);
        }
        public boolean cancelarReserva(Reserva reserva) {
            if (reserva == null) {
                return false;
            }
            if (reserva.getCancelada()) {
                return false;
            }
            if (reserva.jaFezCheckout()) {
                return false;
            }
            if (!LocalDateTime.now().isBefore(reserva.getDataEntrada())) {
                return false;
            }
            reserva.cancelar();
            return true;
        }
        /*public boolean finalizarReserva(Reserva reserva) {
            if (reserva == null) {
                return false;
            }
            if (LocalDateTime.now().isBefore(reserva.getDataEntrada())) {
                return false;
            }
            return reserva.getQuarto().inicializarLimpeza();
        }*/
        public boolean limpezaConcluida(Reserva reserva) {
            if (reserva == null) {
                return false;
            }
            return !LocalDateTime.now().isBefore(reserva.getHorarioLiberacao());
        }
        public boolean finalizarLimpeza(Reserva reserva) {
            if (reserva == null) {
                return false;
            }
            if (!limpezaConcluida(reserva)) {
                return false;
            }
            return reserva.getQuarto().finalizarLimpeza();
        }
        public boolean realizarCheckOut(Reserva reserva) {
            if (reserva == null) {
                return false;
            }
            if (reserva.getCancelada()) {
                return false;
            }
            if (reserva.jaFezCheckout()) {
                return false;
            }
            if (LocalDateTime.now().isBefore(reserva.getDataEntrada())) {
                return false;
            }
            reserva.registrarCheckOut();
            reserva.recalcularValorSaidaAntecipada();
            Divida divida = new Divida(reserva.getHospede(), reserva, "Hospedagem do quarto: " + reserva.getQuarto().getNumero(), reserva.getValorTotal());
            if (!hotel.cadastrarDivida(divida)) {
                return false;
            }
            return reserva.getQuarto().inicializarLimpeza();
        }
        public boolean atualizarLimpeza(Reserva reserva) {
            if (reserva == null) {
                return false;
            }
            if (!reserva.jaFezCheckout()){
                return false;
            }
            if (LocalDateTime.now().isBefore(reserva.getHorarioLiberacao())) {
                return false;
            }
            return reserva.getQuarto().finalizarLimpeza();
        }
    }