package com.screen.screenshot;

import com.jfoenix.controls.JFXSnackbar;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ScreenShotController implements Initializable {
    public Button buttonCapture;
    public ImageView imgView;
    public Button buttonDiscard;
    public Button buttonSave;
    public AnchorPane panelSave;
    public StackPane stackPane;
    public AnchorPane paneCapture;
    private BufferedImage screenCapture;
    private File directory;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonCapture.setOnMouseEntered(e -> buttonCapture.setStyle("-fx-background-color: #22ea40; -fx-background-radius: 50;"));
        buttonCapture.setOnMouseExited(e -> buttonCapture.setStyle("-fx-background-color: #11cf30; -fx-background-radius: 50;"));
        buttonSave.setOnMouseEntered(e -> buttonSave.setStyle("-fx-background-color: #22ea40; -fx-background-radius: 15;"));
        buttonSave.setOnMouseExited(e -> buttonSave.setStyle("-fx-background-color: #11cf30; -fx-background-radius: 15;"));
        buttonDiscard.setOnMouseEntered(e -> buttonDiscard.setStyle("-fx-background-color: #f6b53a; -fx-background-radius: 15;"));
        buttonDiscard.setOnMouseExited(e -> buttonDiscard.setStyle("-fx-background-color: #ec770f; -fx-background-radius: 15;"));


        screenCapture = null;
        imgView.setVisible(false);
        panelSave.setVisible(false);
        directory = new File(System.getProperty("user.home"), "Capturas");
    }

    private void capture(){
        Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        try {
            screenCapture = new Robot().createScreenCapture(rectangle);
        } catch (AWTException e) {
            System.out.println(e.getMessage());
        }


    }

    @FXML
    protected void setButtonCapture(){

        try{
            Stage stage = (Stage) buttonCapture.getScene().getWindow();
            Platform.runLater(() -> {
                stage.hide();
                try{
                    Thread.sleep(500);
                    capture();
                    panelSave.setVisible(true);
                    paneCapture.setVisible(false);
                    writeImage();
                }catch (InterruptedException e){
                    System.out.println(e.getMessage());
                }finally {
                    Platform.runLater(stage::show);
                }
            });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void writeImage(){
        WritableImage img = SwingFXUtils.toFXImage(screenCapture, null);
        imgView.setImage(img);
        imgView.setVisible(true);
    }

    @FXML
    protected void saveImage(){
        LocalDateTime dateTime = LocalDateTime.now();
        String date = dateTime.getDayOfMonth() + "" + dateTime.getMonthValue() + dateTime.getYear();
        String time = dateTime.getHour() + "" +dateTime.getMinute() + dateTime.getSecond() ;
        String fileName = "cap-" + date + "-" + time + ".png";


        if(!directory.exists()){
            if(directory.mkdir()){
                System.out.println("Directorio criado");
            }
        }

        try{
            ImageIO.write(screenCapture, "PNG", new File(directory + "\\" +fileName));
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        setButtonDiscard();
        showSnackbar();
    }

    private void showSnackbar(){
        Label label = new Label("Captura salva em " + directory);
        JFXSnackbar snackbar = new JFXSnackbar(stackPane);
        snackbar.enqueue(new JFXSnackbar.SnackbarEvent(label, Duration.millis(2400)));

    }

    @FXML
    protected void setButtonDiscard(){
        panelSave.setVisible(false);
        imgView.setVisible(false);
        paneCapture.setVisible(true);

    }

    public void handleMousePressed(javafx.scene.input.MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        animateButton(button, 0.9);
    }

    public void handleMouseReleased(javafx.scene.input.MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        animateButton(button, 1.0);
    }

    private void animateButton(Button button, double scale) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
        scaleTransition.setToX(scale);
        scaleTransition.setToY(scale);
        scaleTransition.play();
    }
}