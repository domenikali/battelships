package com.example.client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApp extends Application {
    @FXML
    private Label label;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("join-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        ClientController clientController = fxmlLoader.getController();
        clientController.setLabel("Set user and join the game!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}