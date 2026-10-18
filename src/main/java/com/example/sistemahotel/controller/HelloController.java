
package com.example.sistemahotel.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {

    @FXML
    private Label mensagem;

    @FXML
    private void abrirHospedes() {
        mensagem.setText("Área de cadastro e consulta de hóspedes.");
    }

    @FXML
    private void abrirQuartos() {
        mensagem.setText("Área de cadastro e consulta de quartos.");
    }

    @FXML
    private void abrirReservas() {
        mensagem.setText("Área de criação, consulta e cancelamento de reservas.");
    }

    @FXML
    private void abrirPagamentos() {
        mensagem.setText("Área de pagamentos e consulta de dívidas.");
    }

    @FXML
    private void abrirLimpeza() {
        mensagem.setText("Área de controle de limpeza dos quartos.");
    }
}
