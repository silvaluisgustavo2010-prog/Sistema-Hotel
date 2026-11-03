
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
    private void abrirQuartos() throws IOException {
        mensagem.setText("Área de cadastro e consulta de quartos.");

        Navegacao.abrirTela(
                "quartos.fxml",
                "Cadastro de Quartos"
        );
    }
    @FXML
    private void abrirReservas() throws IOException {
        mensagem.setText("Área de criação, consulta e cancelamento de reservas.");

        Navegacao.abrirTela(
                "reservas.fxml",
                "Cadastro de Reservas"
        );
    }

    @FXML
    private void abrirPagamentos() throws IOException {
        mensagem.setText("Área de pagamentos e consulta de dívidas.");

        Navegacao.abrirTela(
                "pagamentos.fxml",
                "Pagamentos"
        );
    }

    @FXML
    private void abrirLimpeza() {
        mensagem.setText("Área de controle de limpeza dos quartos.");
    }

}