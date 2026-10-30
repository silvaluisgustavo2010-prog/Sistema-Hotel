package com.example.sistemahotel.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Navegacao {

    public static void abrirTela(String nomeArquivo, String titulo)
            throws IOException {

        URL arquivoFXML = Navegacao.class.getResource(
                "/com/example/sistemahotel/" + nomeArquivo
        );

        if (arquivoFXML == null) {
            throw new IOException(
                    "FXML não encontrado: " + nomeArquivo
            );
        }

        FXMLLoader loader = new FXMLLoader(arquivoFXML);
        Scene scene = new Scene(loader.load());

        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.setMinWidth(760);
        stage.setMinHeight(560);
        stage.show();
    }
}