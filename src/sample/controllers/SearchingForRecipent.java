package sample.controllers;

import com.mysql.jdbc.PreparedStatement;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SearchingForRecipent implements Initializable {
   // private Connection con;

    private ResultSet rs;
    private Statement st;
    private PreparedStatement pst;

    public static String recipentName;
    public  static String recipenContact;
    public  static  String recipentBloodGroup;
    public static  String RecipentArea;

    LoginController loginController=new LoginController();
    Notification notification=new Notification();


    public void searchingRecipent() {
       // System.out.println("Akaka"+ loginController.getBloodGroup());

            try {


                String yes = "Yes";
                Class.forName("com.mysql.jdbc.Driver");


                LoginController.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_group", "root", "");

                System.out.println(LoginController.con);
                st = LoginController.con.createStatement();
               // System.out.println("Akaka222"+loginController.getBloodGroup());
                String query = "select * from recipientInfo where YesNo='" + yes + "'";

                rs = st.executeQuery(query);


                while (rs.next()) {

                    if (rs.getString("Contact_Number").equals(loginController.getContactNo())) {

                    } else {
                        //System.out.println("Akaka22");
                        if (rs.getString("BloodGroup").equals(loginController.getBloodGroup())) {//B+ ashtesetana okhane jkhamela abar ja blood grp e
                            notification.setHospital(DashboardController.nameOfHospital);//Take a look Here Tahmid!
                            notification.displayTray();
                        } else {
                            if (loginController.getBloodGroup().equals("O+")) {
                                if (rs.getString("BloodGroup").equals("A+") || rs.getString("BloodGroup").equals("B+") || rs.getString("BloodGrou").equals("AB+")) {
                                   // System.out.println("Akaka22");
                                   // System.out.println("Akaka22");
                                    notification.setHospital(DashboardController.nameOfHospital);//Take a Look Here!
                                    notification.displayTray();
                                }
                            }
                        }
                    }

                }
            } catch (Exception e) {
               // System.out.println( e);

            }




    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}