package br.com.fiap;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class PrimaryController {
    @FXML
    private TextField txtFieldNome;
    @FXML
    private TextField txtFieldTent;

    private static String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static String user = "";
    private static String password = "";

    @FXML
    public void selecionaJogada(ActionEvent e) {
        Button btn = (Button) e.getSource();
        txtFieldTent.setText(btn.getText());
    }

    @FXML
    public void jogar() {
        if (txtFieldNome.getText().trim().equals("")) {
            alertaErro("Digite um nome!");
        } else {
            String jogadaBot = selecionaJogadaBot();
            String jogadaPlayer = txtFieldTent.getText();
            String resultado = "";

            if (jogadaPlayer.equals("Pedra") && jogadaBot.equals("Tesoura")) {
                resultado = "O player " + txtFieldNome.getText() + " venceu!";
                alertaInfo(resultado);
            } else if (jogadaPlayer.equals("Pedra") && jogadaBot.equals("Papel")) {
                resultado = "A máquina venceu!";
                alertaInfo(resultado);
            } else if (jogadaPlayer.equals("Papel") && jogadaBot.equals("Tesoura")) {
                resultado = "A máquina venceu!";
                alertaInfo(resultado);
            } else if (jogadaPlayer.equals("Papel") && jogadaBot.equals("Pedra")) {
                resultado = "O player " + txtFieldNome.getText() + " venceu!";
                alertaInfo(resultado);
            } else if (jogadaPlayer.equals("Tesoura") && jogadaBot.equals("Papel")) {
                resultado = "O player " + txtFieldNome.getText() + " venceu!";
                alertaInfo(resultado);
            } else if (jogadaPlayer.equals("Tesoura") && jogadaBot.equals("Pedra")) {
                resultado = "A máquina venceu!";
                alertaInfo(resultado);
            } else if (jogadaPlayer.equals(jogadaBot)) {
                resultado = "Empate!";
                alertaInfo(resultado);
            }

            try {
                Connection con = DriverManager.getConnection(url, user, password);
                PreparedStatement stm = con.prepareStatement("insert into jokempo_ddd values (?)"); //jokempo_ddd
    
                stm.setString(1, resultado);

                stm.execute();
                con.close();
            } catch(SQLException e) {
                alertaErro("Erro: " + e.getMessage());
            }
        }

    }

    public String selecionaJogadaBot() {
        int randomNum = (int) (Math.random() * 101);

        if (randomNum <= 33) {
            return "Pedra";
        } else if (33 < randomNum && randomNum <= 66) {
            return "Papel";
        } else {
            return "Tesoura";
        }
    }

    public void alertaInfo(String msg) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setContentText(msg);
        alerta.show();
    }

    public void alertaErro(String msg) {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setContentText(msg);
        alerta.show();
    }
}
