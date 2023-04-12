package Controller;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private ImageView exitImage;
    @FXML
    private TextField LoginField;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private Button LoginButton;
    @FXML
    private Text alert;



    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void login() throws IOException {

        if(LoginField.getText().equals("root") && PasswordField.getText().equals("root"))
        {
            alert.setVisible(false);
            //Pobranie Stage obecnego i zamkniecie
            Stage current_stage = (Stage) LoginButton.getScene().getWindow();
            current_stage.close();


            //nowy stage, otwieranie Home.fxml
            Stage home_stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/Home.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 750, 500);
            home_stage.setTitle("Home.fxml");
            home_stage.setScene(scene);
            home_stage.show();
        }else
        {
            alert.setVisible(true);
            alert.setText("Podano błędny login lub hasło! ");
        }




    }






}