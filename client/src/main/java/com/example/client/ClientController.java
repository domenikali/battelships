package com.example.client;

import com.example.client.net.PlayerClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClientController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onJoinButtonClick() {
        PlayerClient p =  new PlayerClient("test",8080);

        welcomeText.setText("Request sent!");
    }
}