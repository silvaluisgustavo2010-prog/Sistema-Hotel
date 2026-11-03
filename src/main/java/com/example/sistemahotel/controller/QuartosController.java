
package com.example.sistemahotel.controller;

import com.example.sistemahotel.service.SistemaHotel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class QuartosController {

    @FXML
    private ComboBox<String> cmbTipoQuarto;

    @FXML
    private TextField txtNumeroQuarto;

    @FXML
    private TextField txtCapacidade;

    @FXML
    private TextField txtValorDiaria;

    @FXML
    private void initialize() {
        cmbTipoQuarto.getItems().addAll(
                "Simples",
                "Duplo",
                "Triplo",
                "Suíte"
        );
    }

    @FXML
    private void cadastrarQuarto() {

        String tipo = cmbTipoQuarto.getValue();
        String numeroTexto = txtNumeroQuarto.getText().trim();
        String capacidadeTexto = txtCapacidade.getText().trim();
        String valorTexto = txtValorDiaria.getText().trim();

        if (tipo == null || numeroTexto.isEmpty()
                || capacidadeTexto.isEmpty() || valorTexto.isEmpty()) {

            mostrarAlerta(Alert.AlertType.WARNING,
                    "Atenção", "Preencha todos os campos.");
            return;
        }

        try {
            Integer numero = Integer.parseInt(numeroTexto);
            Integer capacidade = Integer.parseInt(capacidadeTexto);

            String valorFormatado = valorTexto
                    .replace(".", "")
                    .replace(",", ".");

            Double valorDiaria = Double.parseDouble(valorFormatado);

            boolean cadastrado = SistemaHotel.getInstancia()
                    .cadastrarQuarto(tipo, numero, capacidade, valorDiaria);

            if (cadastrado) {

                mostrarAlerta(Alert.AlertType.INFORMATION,
                        "Sucesso", "Quarto cadastrado com sucesso!");

                cmbTipoQuarto.getSelectionModel().clearSelection();
                txtNumeroQuarto.clear();
                txtCapacidade.clear();
                txtValorDiaria.clear();

            } else {

                mostrarAlerta(Alert.AlertType.ERROR,
                        "Erro", "Não foi possível cadastrar o quarto. "
                                + "Verifique os dados e se o número já está cadastrado.");
            }

        } catch (NumberFormatException e) {

            mostrarAlerta(Alert.AlertType.WARNING,
                    "Dados inválidos",
                    "Número, capacidade e valor da diária devem ser numéricos.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo,
                               String titulo, String mensagem) {

        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
