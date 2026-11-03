
package com.example.sistemahotel.controller;

import com.example.sistemahotel.model.Reserva;
import com.example.sistemahotel.model.StatusLimpeza;
import com.example.sistemahotel.service.SistemaHotel;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LimpezaController {

    @FXML
    private ComboBox<Reserva> cmbReserva;

    @FXML
    private Label lblHospede;

    @FXML
    private Label lblQuarto;

    @FXML
    private Label lblLiberacao;

    @FXML
    private Label lblStatus;

    @FXML
    private Button btnFinalizar;

    private final SistemaHotel sistema = SistemaHotel.getInstancia();

    @FXML
    private void initialize() {

        configurarComboBox();

        cmbReserva.valueProperty().addListener(
                (obs, antiga, nova) -> atualizarDetalhes()
        );

        atualizarLista();
    }

    private void configurarComboBox() {

        cmbReserva.setConverter(new StringConverter<>() {

            @Override
            public String toString(Reserva reserva) {

                if (reserva == null) return "";

                return "Quarto " + reserva.getQuarto().getNumero()
                        + " - " + reserva.getHospede().getNome();
            }

            @Override
            public Reserva fromString(String texto) {
                return null;
            }
        });
    }

    @FXML
    private void atualizarLista() {

        cmbReserva.getItems().clear();

        ArrayList<Reserva> reservas = sistema.listarReservas();

        Set<Integer> quartosAdicionados = new HashSet<>();

        // Percorre do checkout mais recente para o mais antigo.
        for (int i = reservas.size() - 1; i >= 0; i--) {

            Reserva reserva = reservas.get(i);

            Integer numeroQuarto = reserva.getQuarto().getNumero();

            if (reserva.jaFezCheckout()
                    && reserva.getQuarto().getStatusLimpeza()
                    == StatusLimpeza.EM_LIMPEZA
                    && quartosAdicionados.add(numeroQuarto)) {

                cmbReserva.getItems().add(reserva);
            }
        }

        atualizarDetalhes();
    }

    private void atualizarDetalhes() {

        Reserva reserva = cmbReserva.getValue();

        if (reserva == null) {

            lblHospede.setText("-");
            lblQuarto.setText("-");
            lblLiberacao.setText("-");
            lblStatus.setText("Nenhum quarto selecionado");

            btnFinalizar.setDisable(true);

            return;
        }

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

        lblHospede.setText(reserva.getHospede().getNome());

        lblQuarto.setText(
                "Quarto " + reserva.getQuarto().getNumero()
        );

        lblLiberacao.setText(
                reserva.getHorarioLiberacao().format(formato)
        );

        boolean liberado = sistema.limpezaConcluida(reserva);

        if (liberado) {

            lblStatus.setText("Aguardando conclusão da limpeza");
            btnFinalizar.setDisable(false);

        } else {

            lblStatus.setText("Aguardando prazo de liberação");
            btnFinalizar.setDisable(true);
        }
    }

    @FXML
    private void finalizarLimpeza() {

        Reserva reserva = cmbReserva.getValue();

        if (reserva == null) {
            return;
        }

        boolean sucesso = sistema.finalizarLimpeza(reserva);

        if (sucesso) {

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Limpeza concluída");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "A limpeza do quarto "
                            + reserva.getQuarto().getNumero()
                            + " foi finalizada com sucesso!"
            );
            alerta.showAndWait();

            atualizarLista();

        } else {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Não foi possível finalizar");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "O prazo de liberação ainda não foi atingido "
                            + "ou o quarto não está em limpeza."
            );
            alerta.showAndWait();

            atualizarDetalhes();
        }
    }
}
