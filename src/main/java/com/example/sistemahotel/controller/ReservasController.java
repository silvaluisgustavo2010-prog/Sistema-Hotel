
package com.example.sistemahotel.controller;

import com.example.sistemahotel.model.Hospede;
import com.example.sistemahotel.model.Quarto;
import com.example.sistemahotel.model.StatusLimpeza;
import com.example.sistemahotel.service.SistemaHotel;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.text.NumberFormat;
import java.util.Locale;

public class ReservasController {

    @FXML private ComboBox<Hospede> cmbHospede;
    @FXML private ComboBox<Quarto> cmbQuarto;
    @FXML private DatePicker dataEntrada;
    @FXML private DatePicker dataSaida;
    @FXML private Label lblValorTotal;

    private final SistemaHotel sistema = SistemaHotel.getInstancia();

    @FXML
    private void initialize() {
        configurarHospedes();
        configurarQuartos();

        cmbHospede.getItems().setAll(sistema.listarHospedes());

        for (Quarto quarto : sistema.listarQuartos()) {
            if (quarto.getStatusLimpeza() == StatusLimpeza.LIMPO) {
                cmbQuarto.getItems().add(quarto);
            }
        }

        dataEntrada.valueProperty().addListener(
                (obs, antigo, novo) -> atualizarValor()
        );

        dataSaida.valueProperty().addListener(
                (obs, antigo, novo) -> atualizarValor()
        );

        cmbQuarto.valueProperty().addListener(
                (obs, antigo, novo) -> atualizarValor()
        );

        lblValorTotal.setText("R$ 0,00");
    }

    private void configurarHospedes() {
        cmbHospede.setConverter(new StringConverter<>() {
            @Override
            public String toString(Hospede hospede) {
                if (hospede == null) return "";
                return hospede.getNome() + " - CPF: " + hospede.getCpf();
            }

            @Override
            public Hospede fromString(String texto) {
                return null;
            }
        });
    }

    private void configurarQuartos() {
        cmbQuarto.setConverter(new StringConverter<>() {
            @Override
            public String toString(Quarto quarto) {
                if (quarto == null) return "";
                return "Quarto " + quarto.getNumero()
                        + " - " + quarto.getTipo()
                        + " - " + formatarMoeda(quarto.getValorDiaria())
                        + "/diária";
            }

            @Override
            public Quarto fromString(String texto) {
                return null;
            }
        });
    }

    private void atualizarValor() {
        Quarto quarto = cmbQuarto.getValue();
        LocalDate entrada = dataEntrada.getValue();
        LocalDate saida = dataSaida.getValue();

        if (quarto == null || entrada == null || saida == null
                || !entrada.isBefore(saida)) {
            lblValorTotal.setText("R$ 0,00");
            return;
        }

        long dias = Math.max(1, ChronoUnit.DAYS.between(entrada, saida));
        double total = dias * quarto.getValorDiaria();

        lblValorTotal.setText(formatarMoeda(total)
                + " (" + dias + " diária(s))");
    }

    @FXML
    private void confirmarReserva() {
        Hospede hospede = cmbHospede.getValue();
        Quarto quarto = cmbQuarto.getValue();
        LocalDate entrada = dataEntrada.getValue();
        LocalDate saida = dataSaida.getValue();

        if (hospede == null || quarto == null
                || entrada == null || saida == null) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Atenção", "Preencha todos os campos.");
            return;
        }

        if (!entrada.isBefore(saida)) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Datas inválidas",
                    "A data de entrada deve ser anterior à data de saída.");
            return;
        }

        LocalDateTime entradaCompleta = entrada.atStartOfDay();
        LocalDateTime saidaCompleta = saida.atStartOfDay();

        boolean sucesso = sistema.fazerReserva(
                hospede, quarto, entradaCompleta, saidaCompleta
        );

        if (sucesso) {
            mostrarAlerta(Alert.AlertType.INFORMATION,
                    "Sucesso", "Reserva realizada com sucesso!");

            cmbHospede.getSelectionModel().clearSelection();
            cmbQuarto.getSelectionModel().clearSelection();
            dataEntrada.setValue(null);
            dataSaida.setValue(null);
            lblValorTotal.setText("R$ 0,00");
        } else {
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Não foi possível reservar",
                    "Verifique se o quarto está disponível no período "
                            + "e se o hóspede não possui dívidas pendentes.");
        }
    }

    private String formatarMoeda(double valor) {
        return NumberFormat.getCurrencyInstance(
                new Locale("pt", "BR")
        ).format(valor);
    }

    private void mostrarAlerta(
            Alert.AlertType tipo, String titulo, String mensagem) {

        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
