
package com.example.sistemahotel.controller;

import com.example.sistemahotel.model.Reserva;
import com.example.sistemahotel.service.SistemaHotel;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CheckoutController {

    @FXML
    private ComboBox<Reserva> cmbReserva;

    @FXML
    private Label lblHospede;

    @FXML
    private Label lblQuarto;

    @FXML
    private Label lblPeriodo;

    @FXML
    private Label lblValor;

    private final SistemaHotel sistema = SistemaHotel.getInstancia();

    @FXML
    private void initialize() {

        configurarListaReservas();

        atualizarListaReservas();

        cmbReserva.valueProperty().addListener(
                (obs, antigo, novo) -> atualizarDetalhes()
        );

        atualizarDetalhes();
    }

    private void configurarListaReservas() {

        cmbReserva.setConverter(new StringConverter<>() {

            @Override
            public String toString(Reserva reserva) {

                if (reserva == null) return "";

                return reserva.getHospede().getNome()
                        + " - Quarto "
                        + reserva.getQuarto().getNumero();
            }

            @Override
            public Reserva fromString(String texto) {
                return null;
            }
        });
    }

    private void atualizarListaReservas() {

        cmbReserva.getItems().clear();

        for (Reserva reserva : sistema.listarReservas()) {

            if (!reserva.getCancelada()
                    && !reserva.jaFezCheckout()) {

                cmbReserva.getItems().add(reserva);
            }
        }
    }

    private void atualizarDetalhes() {

        Reserva reserva = cmbReserva.getValue();

        if (reserva == null) {

            lblHospede.setText("Nenhum hóspede selecionado");
            lblQuarto.setText("-");
            lblPeriodo.setText("-");
            lblValor.setText("R$ 0,00");

            return;
        }

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        lblHospede.setText(reserva.getHospede().getNome());

        lblQuarto.setText(
                "Quarto " + reserva.getQuarto().getNumero()
                        + " - " + reserva.getQuarto().getTipo()
        );

        lblPeriodo.setText(
                reserva.getDataEntrada().format(formato)
                        + " até "
                        + reserva.getDataSaida().format(formato)
        );

        lblValor.setText(formatarMoeda(reserva.getValorTotal()));
    }

    @FXML
    private void finalizarCheckout() {

        Reserva reserva = cmbReserva.getValue();

        if (reserva == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Atenção",
                    "Selecione uma reserva."
            );

            return;
        }

        boolean sucesso = sistema.realizarCheckOut(reserva);

        if (sucesso) {

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Checkout realizado",
                    "Hospedagem finalizada com sucesso!\n"
                            + "Uma dívida foi gerada para o hóspede."
            );

            atualizarListaReservas();

            cmbReserva.getSelectionModel().clearSelection();

            atualizarDetalhes();

        } else {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Não foi possível realizar o checkout",
                    "Verifique se a data de entrada já chegou "
                            + "e se o quarto está com status LIMPO."
            );
        }
    }

    private String formatarMoeda(double valor) {

        return NumberFormat.getCurrencyInstance(
                new Locale("pt", "BR")
        ).format(valor);
    }

    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensagem) {

        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
