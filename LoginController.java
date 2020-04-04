package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import javafx.scene.control.ComboBox;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.ConnectionUtil;

/**
 *
 * @author khaoula
 */
public class LoginController implements Initializable {
   
    
    
      @FXML
    private Button btnSignup;

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtUsername;
    
    @FXML
    private ComboBox<String> txtRole;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSignin;

   
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    public void handleButtonAction(MouseEvent event) {

        if (event.getSource() == btnSignin) {
          
            if (logIn().equals("Success")) {
                try {

                   
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    
                    stage.close();
                    if (txtRole.getValue().equals("admin"))
                    {
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/backkhaoula.fxml")));
                    stage.setScene(scene);
                    stage.show();}
                    
                    if (txtRole.getValue().equals("livreur"))
                    {
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/frontlivreur.fxml")));
                    stage.setScene(scene);
                    stage.show();}
                     if (txtRole.getValue().equals("client"))
                    {
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/accueilfront.fxml")));
                    stage.setScene(scene);
                    stage.show();}

                    if (txtRole.getValue().equals("guide"))
                    {
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/front.fxml")));
                    stage.setScene(scene);
                    stage.show();}
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            }
        }
    }
       @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtRole.getItems().addAll("client", "admin", "guide" ,"livreur");
        txtRole.getSelectionModel().select("admin");
        
        ////connexion testtt 
      
        
         if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Erreur liée au serveur ");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("le serveur est prêt");
        }
      

    }
   @FXML
    public void signupbuttonAction(MouseEvent event) {
        if (event.getSource() == btnSignup) {
          
            
                try {

                   
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Registerr.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            
        }
    } 

    /*@Override
    public void initialize(URL url, ResourceBundle rb) {
      
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Erreur liée au serveur ");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("le serveur est prêt");
        }
    }*/

    public LoginController() {
        con = ConnectionUtil.conDB();
    }

    
    private String logIn() {
        String status = "Success";
        String email = txtUsername.getText();
        String password = txtPassword.getText();
        if(email.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "veuillez fournir vos données svp");
            status = "Error";
        } else {
        
            String sql = "SELECT * FROM fos_user Where username = ? and password = ?";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "veuillez entrer des informations valides svp");
                    status = "Error";
                } else {
                    setLblError(Color.GREEN, "Login avec succès..Redirection..");
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }
        
        return status;
    }
    
    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }
}
