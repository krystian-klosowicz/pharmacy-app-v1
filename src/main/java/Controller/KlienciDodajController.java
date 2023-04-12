package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import entity.Klienci;
import services.KlientService;
import services.KlientServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class KlienciDodajController {

    @FXML
    private TextField imieField;
    @FXML
    private TextField nazwiskoField;
    @FXML
    private TextField peselField;
    @FXML
    private TextField adresField;
    @FXML
    private Button addButton;
    @FXML
    private Button clearButton;
    @FXML
    private ImageView exitImage;
    @FXML
    private Label imieLabel;
    @FXML
    private Label nazwiskoLabel;
    @FXML
    private Label peselLabel;
    @FXML
    private Label adresLabel;
    @FXML
    private Text alert;

    //Funkcje

    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }

    public void clear(){
        imieField.setText("");
        nazwiskoField.setText("");
        peselField.setText("");
        adresField.setText("");
        imieLabel.setTextFill(Color.BLACK);
        nazwiskoLabel.setTextFill(Color.BLACK);
        peselLabel.setTextFill(Color.BLACK);
        adresLabel.setTextFill(Color.BLACK);
        alert.setText("");

    }

    public void add(){
        String r1 = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ-]*";
        String r2 = "[0-9]{4}[0-3]{1}[0-9]{6}";
        String r3 = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]*";
        if(!imieField.getText().matches(r3) || imieField.getText().isEmpty()) imieLabel.setTextFill(Color.RED);
        else imieLabel.setTextFill(Color.BLACK);
        if(!nazwiskoField.getText().matches(r1) || nazwiskoField.getText().isEmpty()) nazwiskoLabel.setTextFill(Color.RED);
        else nazwiskoLabel.setTextFill(Color.BLACK);
        if(!peselField.getText().matches(r2)) peselLabel.setTextFill(Color.RED);
        else peselLabel.setTextFill(Color.BLACK);
        if(adresField.getText().isEmpty() || adresField.getText().contains("=")) adresLabel.setTextFill(Color.RED);
        else adresLabel.setTextFill(Color.BLACK);



        if(!imieField.getText().matches(r3) || !nazwiskoField.getText().matches(r1) ||  !peselField.getText().matches(r2) || adresField.getText().isEmpty() || adresField.getText().contains("=") )
        {
            alert.setText("Podano błędne dane! ");
        }else
        {
            KlientService klientService = new KlientServiceImpl();
            List<Klienci> do_sprawdzenia_peselu = new ArrayList<>();
            do_sprawdzenia_peselu = klientService.wyszukajKlienci("","","",peselField.getText(),"");
            if(do_sprawdzenia_peselu.isEmpty()){
                Klienci klient = new Klienci(0,imieField.getText(),nazwiskoField.getText(),peselField.getText(),adresField.getText());
                klientService.dodajKlienta(klient);
                imieField.setText("");
                nazwiskoField.setText("");
                peselField.setText("");
                adresField.setText("");
                alert.setText("Dodano klienta! ");
            }else
            {
                alert.setText("Ten pesel jest już w bazie!");
            }

        }


    }
}
