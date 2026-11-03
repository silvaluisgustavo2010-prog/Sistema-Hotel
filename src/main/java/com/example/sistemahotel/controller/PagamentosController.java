
package com.example.sistemahotel.controller;

import com.example.sistemahotel.model.Divida;
import com.example.sistemahotel.model.FormaPagamento;
import com.example.sistemahotel.service.SistemaHotel;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.text.NumberFormat;
import java.util.Locale;

public class PagamentosController {

    @FXML private ComboBox<Divida> cmbDivida;
    @FXML private ComboBox<FormaPagamento> cmbFormaPagamento;

    @FXML private Label lblHospede;
    @FXML private Label lblValorOriginal;
    @FXML private Label lblValorPago;
    @FXML private Label lblSaldo;
    @FXML private Label lblSituacao;

    @FXML private TextField txtValorPagamento;

    private final SistemaHotel sistema = SistemaHotel.getInstancia();

    @FXML
    private void initialize() {
        configurarListaDividas();
        configurarFormasPagamento();

        cmbDivida.getItems().setAll(sistema.listarDividas());

        cmbDivida.valueProperty().addListener(
                (obs, antigo, novo) -> atualizarDetalhes()
        );

        if (!cmbDivida.getItems().isEmpty()) {
            cmbDivida.getSelectionModel().selectFirst();
        } else {
            atualizarDetalhes();
        }
    }

    private void configurarListaDividas() {
        cmbDivida.setConverter(new StringConverter<>() {
            @Override
            public String toString(Divida divida) {
                if (divida == null) return "";

                return divida.getHospede().getNome()
                        + " - "
                        + divida.getDescricao();
            }

            @Override
            public Divida fromString(String texto) {
                return null;
            }
        });
    }

    private void configurarFormasPagamento() {
        cmbFormaPagamento.getItems().setAll(FormaPagamento.values());

        cmbFormaPagamento.setConverter(new StringConverter<>() {
            @Override
            public String toString(FormaPagamento forma) {
                if (forma == null) return "";

                return switch (forma) {
                    case PIX -> "PIX";
                    case DINHEIRO -> "Dinheiro";
                    case DEBITO -> "Cartão de débito";
                    case CREDITO -> "Cartão de crédito";
                };
            }

            @Override
            public FormaPagamento fromString(String texto) {
                return null;
            }
        });
    }

    private void atualizarDetalhes() {
        Divida divida = cmbDivida.getValue();

        if (divida == null) {
            lblHospede.setText("Nenhuma dívida selecionada");
            lblValorOriginal.setText("R$ 0,00");
            lblValorPago.setText("R$ 0,00");
            lblSaldo.setText("R$ 0,00");
            lblSituacao.setText("Sem dívidas cadastradas");
            return;
        }

        lblHospede.setText(divida.getHospede().getNome());
        lblValorOriginal.setText(formatarMoeda(divida.getValor()));
        lblValorPago.setText(formatarMoeda(divida.getValorPago()));
        lblSaldo.setText(formatarMoeda(divida.calcularSaldo()));

        if (divida.getPaga()) {
            lblSituacao.setText("Paga");
        } else {
            lblSituacao.setText("Pendente");
        }

        txtValorPagamento.clear();
    }

    @FXML
    private void registrarPagamento() {
        Divida divida = cmbDivida.getValue();
        FormaPagamento forma = cmbFormaPagamento.getValue();
        String valorTexto = txtValorPagamento.getText().trim();

        if (divida == null || forma == null || valorTexto.isEmpty()) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Atenção",
                    "Selecione uma dívida, uma forma de pagamento e informe o valor."
            );
            return;
        }

        if (divida.getPaga()) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Dívida quitada",
                    "Esta dívida já foi totalmente paga."
            );
            return;
        }

        try {
            double valor = Double.parseDouble(
                    valorTexto.replace(".", "").replace(",", ".")
            );

            if (!Double.isFinite(valor) || valor <= 0) {
                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "Valor inválido",
                        "Informe um valor maior que zero."
                );
                return;
            }

            if (valor > divida.calcularSaldo() + 0.000001) {
                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "Valor acima do saldo",
                        "O pagamento não pode ser maior que a dívida restante."
                );
                return;
            }

            boolean sucesso = sistema.pagarDivida(divida, valor, forma);

            if (sucesso) {
                mostrarAlerta(
                        Alert.AlertType.INFORMATION,
                        "Pagamento registrado",
                        "Pagamento registrado com sucesso!"
                );

                atualizarDetalhes();
            } else {
                mostrarAlerta(
                        Alert.AlertType.ERROR,
                        "Erro",
                        "Não foi possível registrar o pagamento."
                );
            }

        } catch (NumberFormatException e) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Valor inválido",
                    "Digite um valor válido. Exemplo: 150,50."
            );
        }
    }

    private String formatarMoeda(double valor) {
        return NumberFormat.getCurrencyInstance(
                new Locale("pt", "BR")
        ).format(valor);
    }

    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensagem) {

        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
