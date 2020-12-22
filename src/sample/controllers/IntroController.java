package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class IntroController implements Initializable {
    @FXML public JFXButton signIn ;
    @FXML public JFXButton signUp ;
    public void SignIn(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/login.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("ABC");
            stage.setScene(new Scene(root1));
            stage.show();
            Stage hideIntro = (Stage) signIn.getScene().getWindow();
            hideIntro.hide();
        } catch(Exception e) {
            //e.printStackTrace();
        }
    }

    public void SignUp(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/signUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("ABC");
            stage.setScene(new Scene(root1));
            stage.show();
            Stage hideIntro = (Stage) signUp.getScene().getWindow();
            hideIntro.hide();
        } catch(Exception e) {
            //e.printStackTrace();
        }
    }


    public void initialize(URL location, ResourceBundle resources){

    }
}
