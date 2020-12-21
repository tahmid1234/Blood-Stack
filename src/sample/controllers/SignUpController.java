package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.ResourceBundle;

import java.awt.*;


public class SignUpController implements Initializable {

    private Connection con;
    private ResultSet rs;
    private Statement st;
    @FXML private JFXComboBox<String> comboBox = new JFXComboBox<String>(); //gender
    @FXML private JFXComboBox<String> bloodComboBox1 = new JFXComboBox<String>();//b-group
    @FXML private JFXComboBox<String> areaComboBox2 = new JFXComboBox<String>();//area
    @FXML private DatePicker datePicker;  //calender
    @FXML private JFXTextField username;
    @FXML private JFXPasswordField userPassword;
    @FXML private JFXTextField contanctNo;
    @FXML public JFXButton Sin_main;
    @FXML public JFXButton signIn ;
    @FXML private Label errorLabel;//pop up label


    public void Signup(ActionEvent event){
        //if any fields is not filled

        if (username.getText().isEmpty() || userPassword.getText().isEmpty() || areaComboBox2.getValue() == null || datePicker.getValue() == null || bloodComboBox1.getValue() == null || comboBox.getValue() == null || contanctNo.getText().isEmpty()) {
            //determining the age
            errorLabel.setText("Fill up the form properly");
        }

        //all fields er filled properly

        else {
            //determining the age


            Calendar now = Calendar.getInstance();

            int year = now.get(Calendar.YEAR);
            int birthyear = (datePicker.getValue().getYear());
            int age = year - birthyear;
            System.out.println(age);

            //determination is completed

            try {




                Class.forName("com.mysql.jdbc.Driver");

                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_group", "root", "");
                st = con.createStatement();

                String sql = "insert into blood_data"
                        + "(Username,Password,Contact_Number,Age,Address,Gender,BloodGroup)"
                        + "values(' " + username.getText() + "','" + userPassword.getText() + "','" + contanctNo.getText() + "'," + age + ",' " + areaComboBox2.getValue() + " ', '" + comboBox.getValue() + "','"+bloodComboBox1.getValue()+"')";
                st.executeUpdate(sql);


                try{

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/dashboard.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.DECORATED);
                    stage.setTitle("ABC");
                    stage.setScene(new Scene(root1));
                    stage.show();
                    Stage hideIntro = (Stage) Sin_main.getScene().getWindow();
                    hideIntro.hide();


                }catch(Exception e){
                //    e.printStackTrace();
                }



            } catch (Exception ex) {
                errorLabel.setText("Use a new Contact Number");
                //ex.printStackTrace();
            }
            LoginController loginController=new LoginController();

            loginController.setBloodGroup(bloodComboBox1.getValue());
            loginController.setUserName(username.getText());
            loginController.setBloodGroup(bloodComboBox1.getValue());


            SearchingForRecipent searchingForRecipent=new SearchingForRecipent();
            searchingForRecipent.searchingRecipent();



            //one fxml file will be added here after getting the main page's fxml

        }

    }






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

    public void initialize(URL location, ResourceBundle resources) {

        comboBox.getItems().addAll(("Male"), ("Female"));
        bloodComboBox1.getItems().addAll(("A+"), ("B+"), ("O+"), ("AB+"), ("A-"), ("B-"), ("AB-"), ("O-"));
        areaComboBox2.getItems().addAll(("Mirpur"), ("Adabor"));

    }

}
