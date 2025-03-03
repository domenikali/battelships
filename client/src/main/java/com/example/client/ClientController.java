package com.example.client;

import com.example.client.net.PlayerClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ClientController {
    @FXML
    public TextField userText;
    @FXML
    private Label label;

    public void setLabel(String string){
        label.setText(string);

    }

    @FXML
    protected void onJoinButtonClick() {

        String user = userText.getText();
        PlayerClient p =  new PlayerClient(user,8080);

        label.setText("Request sent!");
    }
}