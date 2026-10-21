package com.example.sistemahotel.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Navegacao {

    public static void abrirTela(String nomeArquivo, String titulo)
            throws IOException {

        Path pastaResources = Paths.get("src/main/resources");

        Path arquivoEncontrado;

        try (Stream<Path> arquivos = Files.walk(pastaResources)) {
            arquivoEncontrado = arquivos
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().equals(nomeArquivo))
                    .findFirst()
                    .orElseThrow(() -> new IOException(
                            "FXML não encontrado: " + nomeArquivo));
        }

        FXMLLoader loader = new FXMLLoader(arquivoEncontrado.toUri().toURL());
        Scene scene = new Scene(loader.load());

        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }
}