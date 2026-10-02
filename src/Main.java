import javax.swing.*;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        SistemaHotel sistema = new SistemaHotel(hotel);
        boolean hospedeCadastrado = sistema.cadastrarHospede("João", "12345678900");
        boolean quartoCadastrado = sistema.cadastrarQuarto("Standard", 101, 2, 200.0);
        Hospede hospede = hotel.buscarHospedePorCpf("12345678900");
        Quarto quarto = hotel.buscarQuartoPorNumero(101);
        boolean reservaFeita = sistema.fazerReserva(hospede, quarto, LocalDateTime.of(2026, 10, 5, 14, 0), LocalDateTime.of(2026, 10, 7, 12, 0));
        boolean segundaReserva = sistema.fazerReserva(hospede, quarto, LocalDateTime.of(2026, 10, 6, 14, 0), LocalDateTime.of(2026, 10, 8, 12, 0));
        boolean terceiraReserva = sistema.fazerReserva(hospede, quarto, LocalDateTime.of(2026, 10, 7, 14, 0), LocalDateTime.of(2026, 10, 9, 12, 0));
        System.out.println("Terceira reserva feita: " + terceiraReserva);
        System.out.println("Segunda reserva feita: " + segundaReserva);
        System.out.println("Hóspede cadastrado: " + hospedeCadastrado);
        System.out.println("Quarto cadastrado: " + quartoCadastrado);
        System.out.println("Reserva feita: " + reservaFeita);
        for (Reserva reserva : sistema.listarReservas()) {
            System.out.println("Hóspede: " + reserva.getHospede().getNome());
            System.out.println("Quarto: " + reserva.getQuarto().getNumero());
            System.out.println("Diárias: " + reserva.getQuantidadeDiarias());
            System.out.println("Valor: R$ " + reserva.getValorTotal());
            System.out.println("Paga: " + reserva.getPaga());
        }
        Reserva reserva = sistema.listarReservas().get(0);

        boolean pagamento =
                sistema.pagarReserva(
                        reserva,
                        FormaPagamento.PIX
                );

        System.out.println("Pagamento feito: " + pagamento);
        System.out.println("Reserva paga: " + reserva.getPaga());
        System.out.println("Forma de pagamento: " + reserva.getFormaPagamento());
    }
}