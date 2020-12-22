package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.PreparedStatement;
import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Observable;
import java.util.ResourceBundle;


public class DashboardController implements Initializable {
    public Label noMatchLabel;
    public static Connection con;

    private ResultSet rs;
    private Statement st;
    private PreparedStatement pst;

    @FXML
    public JFXButton requestForBlood;

    public JFXComboBox bloodGroupCombobox;                          //update info : combo box for blood group
    public JFXTextField hospitalName;                                //update info: textfield for hospital;
    public JFXComboBox recipientArea;                                //update info : recipient area combobox


    public void RequestForBlood(ActionEvent event) throws Exception {
        try {
            if (recipientArea.getValue() == null || bloodGroupCombobox.getValue() == null || hospitalName.getText().isEmpty())
                noMatchLabel.setText("Fill the form properly");
            else {


                WebView web = new WebView();
                /**database call
                 *
                 */
                try {
                    Class.forName("com.mysql.jdbc.Driver");

                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_group", "root", "");
                    st = con.createStatement();
                    System.out.println(bloodGroupCombobox.getValue());

                    String query = "select * from blood_data where BloodGroup='" + bloodGroupCombobox.getValue() + "'";
                    rs = st.executeQuery(query);
                    int areaCheck = 1;       //areaCheck check if recipient area== donar are and
                    int bloodCheck = 1;     // bloodCheck checks if database contains the wanted blood group

                    // System.out.println(rs.next());
                    //this loop checks blood group

                    while (rs.next()) {
                        //notification


                        bloodCheck = 2;

                        // bloodCheck==2 it means the database contains the required blood group

                        //this condition checks if recipient area==donar area

                        if (rs.getString("Address").equals(recipientArea.getValue())) {
                            areaCheck = 2;

                            //areaCheck=2 it means donor area== recipient area

                            web.getEngine().load("https://www.google.com/maps/dir/" + hospitalName.getText() + "/" + rs.getString("Address"));
                            System.out.println(rs.getString("Address"));
                        }
                        if (areaCheck != 2) {

                            //areaCheck=2 it means donor area!= recipient area

                            web.getEngine().load("https://www.google.com/maps/dir/" + hospitalName.getText() + "/" + rs.getString("Address"));


                        }
                    }

                    //this condition works when required gropu is not matched

                    if (bloodCheck != 2 && (bloodGroupCombobox.getValue().equals("AB+") || bloodGroupCombobox.getValue().equals("B+") || bloodGroupCombobox.getValue().equals("A+"))) {

                        query = "select * from blood_data where BloodGroup='O+'";  //searching for o+ blood group
                        rs = st.executeQuery(query);
                        while (rs.next()) {

                            //notification

                            bloodCheck = 3;

                            // bloodCheck==2 it means the database contains the O+ blood group

                            //this condition checks if recipient area==donar area

                            if (rs.getString("Address").equals(recipientArea.getValue())) {
                                areaCheck = 2;
                                web.getEngine().load("https://www.google.com/maps/dir/" + hospitalName.getText() + "/" + rs.getString("Address"));
                                System.out.println(rs.getString("Address"));


                                //this condition checks if recipient area!=donar area

                                if (areaCheck != 2) {
                                    web.getEngine().load("https://www.google.com/maps/dir/" + hospitalName.getText() + "/" + rs.getString("Address"));

                                }
                            }
                        }
                        // this coundition applies when neither the requrred Blood group nor any alternative is found

                        if (bloodCheck == 1) {
                            System.out.println(bloodCheck);
                            bloodCheck = 4;
                            noMatchLabel.setText("Your requred blood group is not being found ");
                        }


                        //getting Distance from MAP URL
            /*JSONArray json = readJsonFromUrl("http://frozen-brook-16337.herokuapp.com/history.json");
            JSONObject jo=json.getJSONObject(json.length()-1);
            System.out.println(jo.get("data"));*/


                        //only works when blood group is matched
                        if (bloodCheck != 4) {

                            try {
                                Notification td = new Notification();
                                td.displayTray();
                                System.out.println(bloodCheck);
                                Stage stage = new Stage();
                                Scene scene = new Scene(web);
                                Screen screen = Screen.getPrimary();
                                javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
                                stage.setScene(scene);
                                stage.setX(bounds.getMinX());
                                stage.setY(bounds.getMinY());
                                stage.setWidth(bounds.getWidth());
                                stage.setHeight(bounds.getHeight());

                                stage.show();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            //inserting recipents data into donorinfo ,naam ta change kora lagbe
                            try {
                                String Yes = "Yes";
                                LoginController loginController = new LoginController();
                                String sql = "insert into recipientInfo"
                                        + "(Username,Contact_Number,Address,BloodGroup,YesNo,Hospital)"
                                        + "values(' " + loginController.getUserName() + "','" + loginController.getContactNo() + "',' " + recipientArea.getValue() + " ', '" + bloodGroupCombobox.getValue() + "','" + Yes + "','" + hospitalName.getText() + "')";
                                st.executeUpdate(sql);

                            } catch (Exception e) {
                                // System.out.println("exc insertion"+e);

                            }


                        }


                    }





                    }catch(Exception e) {
                            //System.out.println(e);
                            }
                }
            }catch (Exception e) {
                        //System.out.println(e);
                        }
        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recipientArea.getItems().addAll(("Mirpur"), ("Adabor"), ("Uttara"));
        bloodGroupCombobox.getItems().addAll(("A+"), ("B+"), ("O+"), ("AB+"), ("A-"), ("B-"), ("AB-"), ("O-"));
    }
}

