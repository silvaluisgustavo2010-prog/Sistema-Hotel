package com.example.sistemahotel.service;

import com.example.sistemahotel.model.Divida;
import com.example.sistemahotel.model.Hospede;
import com.example.sistemahotel.model.Quarto;
import com.example.sistemahotel.model.Reserva;
import com.example.sistemahotel.model.StatusLimpeza;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Hotel {
    private final ArrayList<Quarto> quartos = new ArrayList<>();
    private final ArrayList<Reserva> reservas = new ArrayList<>();
    private final ArrayList<Hospede> hospedes = new ArrayList<>();
    private final ArrayList<Divida> dividas = new ArrayList<>();

    public boolean cadastrarQuarto(Quarto quarto) {
        if (quarto == null || quarto.getTipo() == null || quarto.getTipo().isBlank()
                || quarto.getNumero() == null || quarto.getNumero() <= 0
                || quarto.getCapacidade() == null || quarto.getCapacidade() <= 0
                || quarto.getValorDiaria() == null || !Double.isFinite(quarto.getValorDiaria()) || quarto.getValorDiaria() <= 0) return false;
        if (buscarQuartoPorNumero(quarto.getNumero()) != null) return false;
        quartos.add(quarto);
        return true;
    }

    public boolean cadastrarReserva(Reserva reserva) {
        if (reserva == null || reserva.getHospede() == null || reserva.getQuarto() == null) return false;
        if (buscarHospedePorCpf(reserva.getHospede().getCpf()) == null
                || buscarQuartoPorNumero(reserva.getQuarto().getNumero()) == null) return false;
        if (!quartoDisponivel(reserva.getQuarto(), reserva.getDataEntrada(), reserva.getDataSaida())) return false;
        reservas.add(reserva);
        return true;
    }

    public boolean cadastrarDivida(Divida divida) {
        if (divida == null) return false;
        dividas.add(divida);
        return true;
    }

    public boolean cadastrarHospede(Hospede hospede) {
        if (hospede == null || hospede.getNome() == null || hospede.getNome().isBlank()
                || hospede.getCpf() == null || hospede.getCpf().isBlank()) return false;
        if (buscarHospedePorCpf(hospede.getCpf()) != null) return false;
        hospedes.add(hospede);
        return true;
    }

    public boolean quartoDisponivel(Quarto quarto, LocalDateTime entrada, LocalDateTime saida) {
        if (quarto == null || entrada == null || saida == null || !entrada.isBefore(saida)) return false;
        Quarto cadastrado = buscarQuartoPorNumero(quarto.getNumero());
        if (cadastrado == null || cadastrado.getStatusLimpeza() != StatusLimpeza.LIMPO) return false;
        for (Reserva reserva : reservas) {
            if (reserva.getCancelada() || !reserva.getQuarto().getNumero().equals(cadastrado.getNumero())) continue;
            // O intervalo reservado inclui as duas horas de bloqueio para limpeza após o checkout previsto/real.
            LocalDateTime fimBloqueio = reserva.getHorarioLiberacao();
            if (entrada.isBefore(fimBloqueio) && saida.isAfter(reserva.getDataEntrada())) return false;
        }
        return true;
    }

    public ArrayList<Reserva> consultarReservasPorHospede(Hospede hospede) {
        ArrayList<Reserva> resultado = new ArrayList<>();
        if (hospede == null || hospede.getCpf() == null) return resultado;
        for (Reserva reserva : reservas) if (reserva.getHospede().getCpf().equals(hospede.getCpf())) resultado.add(reserva);
        return resultado;
    }

    public ArrayList<Reserva> consultarReservasPorQuarto(Quarto quarto) {
        ArrayList<Reserva> resultado = new ArrayList<>();
        if (quarto == null || quarto.getNumero() == null) return resultado;
        for (Reserva reserva : reservas) if (reserva.getQuarto().getNumero().equals(quarto.getNumero())) resultado.add(reserva);
        return resultado;
    }

    public ArrayList<Quarto> listarQuartos() { return new ArrayList<>(quartos); }
    public ArrayList<Hospede> listarHospedes() { return new ArrayList<>(hospedes); }
    public ArrayList<Reserva> listarReservas() { return new ArrayList<>(reservas); }
    public ArrayList<Divida> listarDividas() { return new ArrayList<>(dividas); }

    public Hospede buscarHospedePorCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) return null;
        String procurado = cpf.trim();
        for (Hospede hospede : hospedes) if (hospede.getCpf().equals(procurado)) return hospede;
        return null;
    }

    public Quarto buscarQuartoPorNumero(Integer numero) {
        if (numero == null || numero <= 0) return null;
        for (Quarto quarto : quartos) if (quarto.getNumero().equals(numero)) return quarto;
        return null;
    }

    public ArrayList<Divida> consultarDividasPorHospede(Hospede hospede) {
        ArrayList<Divida> resultado = new ArrayList<>();
        if (hospede == null || hospede.getCpf() == null) return resultado;
        for (Divida divida : dividas) if (divida.getHospede().getCpf().equals(hospede.getCpf())) resultado.add(divida);
        return resultado;
    }

    public Double calcularTotalDevidoPorHospede(Hospede hospede) {
        double total = 0.0;
        for (Divida divida : consultarDividasPorHospede(hospede)) total += divida.calcularSaldo();
        return total;
    }
}
