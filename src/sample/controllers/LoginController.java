package sample.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;

public class LoginController implements Initializable{

    public static Connection con;
    private ResultSet rs;
    private Statement st;
    private PreparedStatement pst;
    @FXML
    private Label recipienName;
    @FXML private Label recipientBloodGroup;
    @FXML private Label recipientContct;


    @FXML private JFXTextField contact_no;
    @FXML private JFXPasswordField userInputtedpassword;
    @FXML private Label erroCathingLabel;

    @FXML public JFXButton dashboard;
    @FXML public JFXButton signUp ;

    private static String userName;
    private static String contactNo;    // static contact number
    private static String bloodGroup ;
    private static String area;



    public void Dashboard(ActionEvent event) throws Exception {




        if(contact_no.getText().isEmpty()||userInputtedpassword.getText().isEmpty()){
            erroCathingLabel.setText("Fill the form properly");}
        else {

            //this try catch uses for checking password and contact number

            try {
                Class.forName("com.mysql.jdbc.Driver");

                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_group", "root", "");
                st = con.createStatement();
                String query = "select * from blood_data where Contact_Number='" + contact_no.getText() + "'";
                rs = st.executeQuery(query);

                if (rs.next()) {

                    contactNo=contact_no.getText();       // contact number saved thakbe
                    area=rs.getString("Address");
                    bloodGroup=rs.getString("BloodGroup");
                    System.out.println(bloodGroup+"blood");
                    userName=rs.getString("Username");
                    System.out.println( userName);
                    String password = rs.getString("Password");
                    System.out.println(password);
                    if ((userInputtedpassword.getText()).equals(password)) {
                       // System.out.println("Ok");

                        //HOme page FXML
                        try{
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/dashboard.fxml"));
                            Parent root1 = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initStyle(StageStyle.DECORATED);
                            stage.setTitle("ABC");
                            stage.setScene(new Scene(root1));
                            stage.show();
                            Stage hideIntro = (Stage) dashboard.getScene().getWindow();
                            hideIntro.hide();
                        }catch(Exception e){
                            //e.printStackTrace();
                        }
                    } else {
                        erroCathingLabel.setText("Wrong PassWord");
                    }

                } else {
                    erroCathingLabel.setText("Wrong Contact Number");
                }




            } catch (Exception ex) {
                //System.out.println( ex);
            }

        }


        SearchingForRecipent searchingForRecipent=new SearchingForRecipent();
        searchingForRecipent.searchingRecipent();
        /**
         Mouse Click Event Listener will be added here
         the further code will be added here according to the event listener's Logic

         **/

    /* try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/InfoOfRecipent.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("ABC");


//            InfoOfRecipentController infoOfRecipentController= new InfoOfRecipentController();
//            infoOfRecipentController.settingInfo();
            stage.setScene(new Scene(root1));
            stage.show();

            Stage hideIntro = (Stage) dashboard.getScene().getWindow();
            hideIntro.hide();

        } catch(Exception e) {
            //e.printStackTrace();
        }*/



        //try {

       // } //catch(Exception e) {
            //e.printStackTrace();
        }

        public String getUserName(){
        System.out.println("ll"+ userName);
        return userName;
        }
    public String getContactNo(){

        return contactNo;
    }
    public String getArea(){

        return area;
    }
    public String getBloodGroup(){


        return bloodGroup;
    }



    public void setContactNo(String numberContact){
        contactNo=numberContact;
    }
    public void setBloodGroup(String groupOfBlood){
       bloodGroup=groupOfBlood;
    }
    public void setUserName(String nameOfUser){
        userName=nameOfUser;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
