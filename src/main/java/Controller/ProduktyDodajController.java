package Controller;

import entity.HibernateUtil;
import entity.Kategorie;
import entity.Producenci;
import entity.Produkty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import services.ProducentServiceImpl;
import services.ProduktService;
import services.ProduktServiceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ProduktyDodajController implements Initializable {

    @FXML
    private Button addButton;
    @FXML
    private Text alert;
    @FXML
    private TextField cenaField;
    @FXML
    private Label cenaLabel;
    @FXML
    private Button clearButton;
    @FXML
    private ImageView exitImage;
    @FXML
    private TextField iloscField;
    @FXML
    private Label iloscLabel;
    @FXML
    private ComboBox<String> kategoriaCombo;
    @FXML
    private Label kategoriaLabel;
    @FXML
    private TextField nazwaField;
    @FXML
    private Label nazwaLabel;
    @FXML
    private ComboBox<String> producentCombo;
    @FXML
    private Label producentLabel;

    //Funkcje

    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }

    public void clear(){
        nazwaField.setText("");
        cenaField.setText("");
        iloscField.setText("");
        kategoriaCombo.getSelectionModel().clearSelection();
        producentCombo.getSelectionModel().clearSelection();
        nazwaLabel.setTextFill(Color.BLACK);
        cenaLabel.setTextFill(Color.BLACK);
        iloscLabel.setTextFill(Color.BLACK);
        alert.setText("");

    }

    public void add(){
        String r1 = "[a-zA-Z]{3,}.*";// do nazwy
        String r2 = "[0-9]{1,}";
        String r3 = "[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)";

        //Pokazywanie gdzie jest błąd
        if(!nazwaField.getText().matches(r1)) nazwaLabel.setTextFill(Color.RED);
        else nazwaLabel.setTextFill(Color.BLACK);
        if(producentCombo.getSelectionModel().isEmpty())producentLabel.setTextFill(Color.RED);
        else producentLabel.setTextFill(Color.BLACK);
        if(kategoriaCombo.getSelectionModel().isEmpty())kategoriaLabel.setTextFill(Color.RED);
        else kategoriaLabel.setTextFill(Color.BLACK);
        if(iloscField.getText().matches(r2) && Integer.parseInt(iloscField.getText())>0) iloscLabel.setTextFill(Color.BLACK);
        else iloscLabel.setTextFill(Color.RED);
        if(cenaField.getText().matches(r3)&& Float.parseFloat(cenaField.getText())>0)cenaLabel.setTextFill(Color.BLACK);
        else cenaLabel.setTextFill(Color.RED);

        if(nazwaField.getText().matches(r1) && !producentCombo.getSelectionModel().isEmpty() && !kategoriaCombo.getSelectionModel().isEmpty() && iloscField.getText().matches(r2) && Integer.parseInt(iloscField.getText())>0 && cenaField.getText().matches(r3) && Float.parseFloat(cenaField.getText())>0 )
        {

            //  Dane
            //  ----------------------------------------------------------------------------------------
            //double cena = Float.parseFloat(cenaField.getText());
            //cena *= 100;
            //cena = Math.round(cena);
           // cena /= 100;
            //cena *= 100;
            //int cena_in = (int) cena;
            //query zeby miec id producenta i kategorii
            List<Integer> list1 = new ArrayList<>();
            List<Integer> list2= new ArrayList<>();
            Session s = (Session) HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            list1 = s.createQuery("select id_kategorii from Kategorie k where k.nazwa_kategorii='"+kategoriaCombo.getSelectionModel().getSelectedItem()+"'").list();
            list2 = s.createQuery("select id_producenta from Producenci p where p.nazwa_producenta='"+producentCombo.getSelectionModel().getSelectedItem()+"'").list();
            int id_kat = list1.get(0);
            int id_prod = list2.get(0);
            s.getTransaction().commit();
            s.close();
            //  ----------------------------------------------------------------------------------------------
            //  Teraz przechodzimy do dodania produktu
            Producenci pr = new Producenci(id_prod,"","","");
            Kategorie kt = new Kategorie(id_kat,"");
            double cena = Double.parseDouble(cenaField.getText());
            cena = Math.round(cena * 100.0) / 100.0;
            Produkty produkt = new Produkty(0,id_prod,id_kat,nazwaField.getText(),cena, Integer.parseInt(iloscField.getText()),pr,kt);
            ProduktService produktService = new ProduktServiceImpl();
            produktService.dodajProdukt(produkt);


            alert.setText("Dodano produkt! ");

        }else alert.setText("Błędne dane! ");


    }

    public void initialize(URL url, ResourceBundle rb)
    {
        //Wczytanie nazw kategori oraz producentów do combobox
        List<String> list1 = new ArrayList<>();
        List<String> list2= new ArrayList<>();
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        list1 = s.createQuery("select nazwa_kategorii from Kategorie").list();
        list2 = s.createQuery("select nazwa_producenta from Producenci").list();
        ObservableList<String> kategorie = FXCollections.observableArrayList(list1);
        ObservableList<String> producenci = FXCollections.observableArrayList(list2);
        kategoriaCombo.setItems(kategorie);
        producentCombo.setItems(producenci);
        s.getTransaction().commit();
        s.close();

    }
}
