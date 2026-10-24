package com.example.sistemahotel.controller;

import com.example.sistemahotel.model.Hospede;
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
    private void initialize(){
        txtfCpfDoCliente.textProperty().addListener((observable, valorAntigo, valorNovo) ->{
            String cpf = valorNovo.replaceAll("\\D", "");
            if (cpf.length() > 11) {
                cpf = cpf.substring(0, 11);
            }
            if (cpf.length() > 9) {
                cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
            } else if (cpf.length() > 6) {
                cpf = cpf.substring(0, 3) + "."  + cpf.substring(3, 6) + "." + cpf.substring(6);
            } else if (cpf.length() > 3) {
                cpf = cpf.substring(0, 3) + "." + cpf.substring(3);
            }
            if (!valorNovo.equals(cpf)) {
                txtfCpfDoCliente.setText(cpf);
                txtfCpfDoCliente.positionCaret(cpf.length());
            }
        });
    }


}
