package com.screen.screenshot;

import com.jfoenix.controls.JFXSnackbar;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        screenCapture = null;
        imgView.setVisible(false);
        panelSave.setVisible(false);
        
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

        try{
            ImageIO.write(screenCapture, "PNG", new File("C:\\Users\\Lenovo\\Capturas\\cap.png"));
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        setButtonDiscard();
        showSnackbar();
    }

    private void showSnackbar(){
        Label label = new Label("Captura salva com sucesso");
        JFXSnackbar snackbar = new JFXSnackbar(stackPane);
        snackbar.enqueue(new JFXSnackbar.SnackbarEvent(label, Duration.millis(2000)));

    }

    @FXML
    protected void setButtonDiscard(){
        panelSave.setVisible(false);
        imgView.setVisible(false);
        paneCapture.setVisible(true);

    }

}