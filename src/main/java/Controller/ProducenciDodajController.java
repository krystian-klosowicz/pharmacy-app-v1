package Controller;

import entity.HibernateUtil;
import entity.Klienci;
import entity.Producenci;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import services.KlientService;
import services.KlientServiceImpl;
import services.ProducentService;
import services.ProducentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProducenciDodajController {

    @FXML
    private TextField nipField;
    @FXML
    private TextField nazwaField;
    @FXML
    private TextField adresField;
    @FXML
    private Button addButton;
    @FXML
    private Button clearButton;
    @FXML
    private ImageView exitImage;
    @FXML
    private Label nipLabel;
    @FXML
    private Label nazwaLabel;
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
        nipField.setText("");
        nazwaField.setText("");
        adresField.setText("");
        nipLabel.setTextFill(Color.BLACK);
        nazwaLabel.setTextFill(Color.BLACK);
        adresLabel.setTextFill(Color.BLACK);

    }

    public void add(){
        String r1 = "[0-9]{10}"; // nip
        String r2 = "[a-zA-Z]{3,}.*";

        //pokazywanie gddzie blad
        if(!nipField.getText().matches(r1)) nipLabel.setTextFill(Color.RED);
        else nipLabel.setTextFill(Color.BLACK);

        if(!nazwaField.getText().matches(r2)) nazwaLabel.setTextFill(Color.RED);
        else nazwaLabel.setTextFill(Color.BLACK);

        if(!adresField.getText().matches(r2) || adresField.getText().contains("=")) adresLabel.setTextFill(Color.RED);
        else adresLabel.setTextFill(Color.BLACK);


        if(!nipField.getText().matches(r1)  || !nazwaField.getText().matches(r2) || !adresField.getText().matches(r2)|| adresField.getText().contains("="))
        {
            alert.setText("Podano błędne dane! ");
        }else
        {
            List<Producenci> lista_prod= new ArrayList<>();
            Session s = (Session) HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            lista_prod= s.createSQLQuery("select * from Producenci p where p.NIP='"+nipField.getText()+"'").list();
            s.getTransaction().commit();
            s.close();
            if(lista_prod.isEmpty())
            {
                Producenci producent = new Producenci(0, nipField.getText(),nazwaField.getText(),adresField.getText());
                ProducentService producentService = new ProducentServiceImpl();
                producentService.dodajProducenta(producent);
                alert.setText("Dodano producenta!");
            }
            else
            {
                alert.setText("Podany NIP jest w bazie!");
                nipLabel.setTextFill(Color.RED);
            }

        }



    }
}
