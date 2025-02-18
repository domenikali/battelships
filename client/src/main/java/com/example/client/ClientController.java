package com.example.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClientController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onJoinButtonClick() {
        welcomeText.setText("Request sent!");
    }
}