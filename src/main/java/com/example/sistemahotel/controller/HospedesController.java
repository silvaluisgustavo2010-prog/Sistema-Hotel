
package com.example.sistemahotel.controller;

import com.example.sistemahotel.service.SistemaHotel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HospedesController {

    @FXML
    private TextField txtfNomeDoCliente;

    @FXML
    private TextField txtfCpfDoCliente;

    @FXML
    private Button btnCadastrar;

    @FXML
    private void initialize() {

        txtfCpfDoCliente.textProperty().addListener((observable, valorAntigo, valorNovo) -> {

            String cpf = valorNovo.replaceAll("\\D", "");

            if (cpf.length() > 11) {
                cpf = cpf.substring(0, 11);
            }

            if (cpf.length() > 9) {
                cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6)
                        + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);

            } else if (cpf.length() > 6) {
                cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6)
                        + "." + cpf.substring(6);

            } else if (cpf.length() > 3) {
                cpf = cpf.substring(0, 3) + "." + cpf.substring(3);
            }

            if (!valorNovo.equals(cpf)) {
                txtfCpfDoCliente.setText(cpf);
                txtfCpfDoCliente.positionCaret(cpf.length());
            }
        });
    }

    @FXML
    private void salvarHospede() {

        String nome = txtfNomeDoCliente.getText().trim();
        String cpf = txtfCpfDoCliente.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Atenção", "Preencha todos os campos.");
            return;
        }

        if (cpf.length() != 14) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "CPF inválido", "Digite um CPF completo.");
            return;
        }

        String cpfSemFormatacao = cpf.replaceAll("\\D", "");

        boolean cadastrado = SistemaHotel.getInstancia()
                .cadastrarHospede(nome, cpfSemFormatacao);

        if (cadastrado) {

            mostrarAlerta(Alert.AlertType.INFORMATION,
                    "Sucesso", "Hóspede cadastrado com sucesso!");

            txtfNomeDoCliente.clear();
            txtfCpfDoCliente.clear();

        } else {

            mostrarAlerta(Alert.AlertType.ERROR,
                    "Erro", "Não foi possível cadastrar. Verifique se o CPF já está cadastrado.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {

        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
