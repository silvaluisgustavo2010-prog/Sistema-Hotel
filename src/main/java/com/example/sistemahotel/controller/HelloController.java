
package com.example.sistemahotel.controller;

import com.example.sistemahotel.service.Navegacao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class HelloController {

    @FXML
    private Label mensagem;

    @FXML
    private void abrirHospedes() throws IOException {
        mensagem.setText("Área de cadastro e consulta de hóspedes.");
        Navegacao.abrirTela("hospedes.fxml",
                "Hóspedes");
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