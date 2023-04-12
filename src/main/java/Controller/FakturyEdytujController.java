package Controller;



import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import services.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class FakturyEdytujController implements Initializable {
    @FXML
    private Text alert;
    @FXML
    private Text alertKlient;
    @FXML
    private ImageView exitImage;
    @FXML
    private ComboBox klientCombo;
    @FXML
    private Label klientLabel;

    public FakturyEdytujController() {
    }

    //Metody
    @FXML
    private void exit() {
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }
    //Tabela faktury
    @FXML
    private TableView<Faktury> fakturyTable;
    @FXML
    private TableColumn<Faktury, Integer> faktury_id_faktury;
    @FXML
    private TableColumn<Faktury, Integer> faktury_id_kilenta;
    @FXML
    private TableColumn<Faktury, Date> faktury_data;
    //Tabela faktury_produkty
    @FXML
    private TableView<Faktury_produkty> faktury_produktyTable;
    @FXML
    private TableColumn<Faktury_produkty, Integer> fakturyPro_cena;

    @FXML
    private TableColumn<Faktury_produkty, Integer> fakturyPro_id_faktury;

    @FXML
    private TableColumn<Faktury_produkty, Integer> fakturyPro_id_glowne;

    @FXML
    private TableColumn<Faktury_produkty, Integer> fakturyPro_id_produktu;

    @FXML
    private TableColumn<Faktury_produkty, Integer> fakturyPro_ilosc;
    @FXML
    private TableColumn<Faktury_produkty, String> fakturyPro_nazwa;
    @FXML
    private TextField sumaField;

    private FakturaService fakturaService = new FakturaServiceImpl();
    private ObservableList<Faktury> fakturyLista=FXCollections.observableArrayList();

    private Faktury_produktyService faktury_produktyService = new Faktury_produktyServiceImpl();
    private ObservableList<Faktury_produkty> faktury_produktyLista = FXCollections.observableArrayList();



    //  -----------------------------------------------------------------------------------------------------------------------------

    public ObservableList<Faktury_produkty> wyszukajFaktury_produkty(String id_faktury) {
        if(faktury_produktyLista.isEmpty())
            faktury_produktyLista.clear();
        faktury_produktyLista = FXCollections.observableArrayList((List<Faktury_produkty>) faktury_produktyService.wyszukajFaktury_produkty(id_faktury));

        return faktury_produktyLista;
    }

    public ObservableList<Faktury> getFakturyLista() {
        if(fakturyLista.isEmpty())
            fakturyLista.clear();
        fakturyLista = FXCollections.observableArrayList((List<Faktury>) fakturaService.listaFaktury());

        return fakturyLista;
    }

    public ObservableList<Faktury> getWyszukajFakturyLista(String id_faktury, String id_klienta) {
        if(fakturyLista.isEmpty())
            fakturyLista.clear();
        fakturyLista = FXCollections.observableArrayList((List<Faktury>) fakturaService.wyszukajFaktury(id_faktury,id_klienta));
        if(fakturyLista.isEmpty()){
                    alertKlient.setText("Brak faktur dla tego kliena! ");
                    klientLabel.setTextFill(Color.RED);
                    return getFakturyLista();
        }
        alertKlient.setText("");
        klientLabel.setTextFill(Color.BLACK);

        return fakturyLista;
    }

    public void pokazFakturyKlienta()
    {
        if(klientCombo.getSelectionModel().getSelectedItem()!=null && !fakturyLista.isEmpty())
        {
            List<Integer> list1= new ArrayList<>();
            Session s1 = (Session) HibernateUtil.getSessionFactory().openSession();
            s1.beginTransaction();
            String pelna_nazwa = klientCombo.getSelectionModel().getSelectedItem().toString();
            list1 = s1.createQuery("Select k.id_klienta from Klienci k WHERE CONCAT(k.imie,' ',k.nazwisko)='"+pelna_nazwa+"'").list();
            Integer id_klienta = list1.get(0);
            System.out.println("Id_klienta" + id_klienta);
            s1.getTransaction().commit();
            s1.close();
            fakturyTable.setItems(getWyszukajFakturyLista("",id_klienta.toString()));



        }
    }

    public void pokazSzczegolyFaktury()
    {
        if(fakturyTable.getSelectionModel().getSelectedItem()!=null)
        {
            Integer id = fakturyTable.getSelectionModel().getSelectedItem().getId_faktury();
            System.out.println("ID FAKTURY: "+id);
            faktury_produktyTable.setItems(wyszukajFaktury_produkty(id.toString()));
            //Wyliczenie i wyswietlenie sumy
            double suma = 0;
            for(Faktury_produkty elem:faktury_produktyLista)
            {
                suma+=(double) elem.getIlosc_sztuk()*elem.getCena_zakupu();
            }
            suma = Math.round(suma * 100.0) / 100.0;
            sumaField.setText(suma+" zł");
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        //
        //  -----------------------------------------------------------------------------------------------------------------------
        //  wczytanie klientow do combo
        List<String> lista_klienci= new ArrayList<>();
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        lista_klienci= s.createQuery("select CONCAT(k.imie,' ',k.nazwisko) from Klienci k").list();
        ObservableList<String> klienci = FXCollections.observableArrayList(lista_klienci);
        klientCombo.setItems(klienci);
        s.getTransaction().commit();
        s.close();
        //Wczytanie wszystkich faktur
        faktury_id_faktury.setCellValueFactory(new PropertyValueFactory<Faktury, Integer>("id_faktury"));
        faktury_id_kilenta.setCellValueFactory(new PropertyValueFactory<Faktury, Integer>("id_klienta"));
        faktury_data.setCellValueFactory(new PropertyValueFactory<Faktury, Date>("data_wystawienia_faktury"));
        fakturyTable.setItems(getFakturyLista());
        //Faktury_produkty
        fakturyPro_id_glowne.setCellValueFactory(new PropertyValueFactory<Faktury_produkty, Integer>("id_faktury_produkty"));
        fakturyPro_id_faktury.setCellValueFactory(new PropertyValueFactory<Faktury_produkty, Integer>("id_faktury"));
        fakturyPro_id_produktu.setCellValueFactory(new PropertyValueFactory<Faktury_produkty, Integer>("id_produktu"));
        fakturyPro_nazwa.setCellValueFactory(new PropertyValueFactory<Faktury_produkty, String>("nazwa_produktu"));
        fakturyPro_cena.setCellValueFactory(new PropertyValueFactory<Faktury_produkty, Integer>("cena_zakupu"));
        fakturyPro_ilosc.setCellValueFactory(new PropertyValueFactory<Faktury_produkty, Integer>("ilosc_sztuk"));


    }





}
