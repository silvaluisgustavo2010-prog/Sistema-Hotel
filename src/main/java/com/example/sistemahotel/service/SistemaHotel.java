package com.example.sistemahotel.service;

import com.example.sistemahotel.model.Divida;
import com.example.sistemahotel.model.FormaPagamento;
import com.example.sistemahotel.model.Hospede;
import com.example.sistemahotel.model.Quarto;
import com.example.sistemahotel.model.Reserva;
import com.example.sistemahotel.model.StatusLimpeza;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SistemaHotel {
    private final Hotel hotel;

    public SistemaHotel(Hotel hotel) {
        if (hotel == null) throw new IllegalArgumentException("O hotel é obrigatório.");
        this.hotel = hotel;
    }

    public boolean cadastrarHospede(String nome, String cpf) {
        return hotel.cadastrarHospede(new Hospede(nome, cpf));
    }

    public boolean cadastrarQuarto(String tipo, Integer numero, Integer capacidade, Double valorDiaria) {
        return hotel.cadastrarQuarto(new Quarto(tipo, numero, capacidade, valorDiaria));
    }

    public boolean fazerReserva(Hospede hospede, Quarto quarto, LocalDateTime entrada, LocalDateTime saida) {
        if (hospede == null || quarto == null || entrada == null || saida == null || !entrada.isBefore(saida))
            return false;
        Hospede cadastrado = hotel.buscarHospedePorCpf(hospede.getCpf());
        Quarto quartoCadastrado = hotel.buscarQuartoPorNumero(quarto.getNumero());
        if (cadastrado == null || quartoCadastrado == null) return false;
        if (hotel.calcularTotalDevidoPorHospede(cadastrado) > 0.000001) return false;
        try {
            Reserva reserva = new Reserva(cadastrado, quartoCadastrado, entrada, saida);
            return hotel.cadastrarReserva(reserva);
        } catch (IllegalArgumentException ex) {
            return false;
        }
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
        if (reserva == null || !hotel.listarReservas().contains(reserva)) return false;
        return reserva.marcarComoPaga(formaPagamento);
    }

    public ArrayList<Reserva> consultarReservasPorHospede(Hospede hospede) {
        return hotel.consultarReservasPorHospede(hospede);
    }

    public ArrayList<Reserva> consultarReservasPorQuarto(Quarto quarto) {
        return hotel.consultarReservasPorQuarto(quarto);
    }

    public ArrayList<Divida> consultarDividasPorHospede(Hospede hospede) {
        return hotel.consultarDividasPorHospede(hospede);
    }

    public Double calcularTotalDevidoPorHospede(Hospede hospede) {
        return hotel.calcularTotalDevidoPorHospede(hospede);
    }

    public boolean pagarDivida(Divida divida, Double valor, FormaPagamento formaPagamento) {
        if (divida == null || !hotel.listarDividas().contains(divida)) return false;
        return divida.registrarPagamento(valor, formaPagamento);
    }

    public boolean cancelarReserva(Reserva reserva) {
        if (reserva == null || !hotel.listarReservas().contains(reserva) || reserva.getCancelada() || reserva.jaFezCheckout())
            return false;
        if (!LocalDateTime.now().isBefore(reserva.getDataEntrada())) return false;
        reserva.cancelar();
        return true;
    }

    public boolean realizarCheckOut(Reserva reserva) {
        if (reserva == null || !hotel.listarReservas().contains(reserva) || reserva.getCancelada() || reserva.jaFezCheckout())
            return false;
        LocalDateTime agora = LocalDateTime.now();
        if (agora.isBefore(reserva.getDataEntrada())) return false;
        if (reserva.getQuarto().getStatusLimpeza() != StatusLimpeza.LIMPO) return false;

        // Valida e prepara a limpeza antes de alterar o estado da reserva.
        if (!reserva.getQuarto().marcarComoSujo()) return false;
        if (!reserva.getQuarto().inicializarLimpeza()) return false;
        if (!reserva.registrarCheckOut(agora)) return false;
        Divida divida = new Divida(reserva.getHospede(), reserva,
                "Hospedagem do quarto: " + reserva.getQuarto().getNumero(), reserva.getValorTotal());
        return hotel.cadastrarDivida(divida);
    }

    public boolean limpezaConcluida(Reserva reserva) {
        return reserva != null && reserva.jaFezCheckout()
                && !LocalDateTime.now().isBefore(reserva.getHorarioLiberacao());
    }

    public boolean finalizarLimpeza(Reserva reserva) {
        if (reserva == null || !hotel.listarReservas().contains(reserva) || !limpezaConcluida(reserva)) return false;
        return reserva.getQuarto().finalizarLimpeza();
    }

    public boolean atualizarLimpeza(Reserva reserva) {
        return finalizarLimpeza(reserva);
    }

}
