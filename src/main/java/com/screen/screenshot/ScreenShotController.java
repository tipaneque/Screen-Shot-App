package com.screen.screenshot;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ScreenShotController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}