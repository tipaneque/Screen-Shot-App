package com.screen.screenshot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenShotApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ScreenShotApplication.class.getResource("screen-shot-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}