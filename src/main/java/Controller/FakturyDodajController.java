package Controller;



import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import services.*;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class FakturyDodajController implements Initializable {
    @FXML
    private ImageView exitImage;
    @FXML
    private ComboBox klientCombo;
    @FXML
    private Label klientLabel;
    //TABELA PRODUKTY
    @FXML
    private TableView<Produkty> produktyTable;
    @FXML
    private TableColumn<Produkty, Integer> produktyTable_idKategorii;
    @FXML
    private TableColumn<Produkty, Integer> produktyTable_idProducenta;
    @FXML
    private TableColumn<Produkty, Integer> produktyTable_idProduktu;
    @FXML
    private TableColumn<Produkty, String> produktyTable_nazwa;
    @FXML
    private TableColumn<Produkty, Double> produktyTable_cena;
    @FXML
    private TableColumn<Produkty, Integer> produktyTable_ilośc;
    //TABELA WYBRANE PRODUKTY
    @FXML
    private TableView<Produkty> wybraneTable;
    @FXML
    private TableColumn<Produkty, Integer> wybraneTable_idKategorii;
    @FXML
    private TableColumn<Produkty, Integer> wybraneTable_idProducenta;
    @FXML
    private TableColumn<Produkty, Integer> wybraneTable_idProduktu;
    @FXML
    private TableColumn<Produkty, String> wybraneTable_nazwa;
    @FXML
    private TableColumn<Produkty, Double> wybraneTable_cena;
    @FXML
    private TableColumn<Produkty, Integer> wybraneTable_ilośc;
    @FXML
    private Text alert;
    @FXML
    private TextField wybrana_nazwaField;
    @FXML
    private TextField iloscField;
    @FXML
    private Label iloscLabel;

    private ProduktService produktService = new ProduktServiceImpl();
    private ObservableList<Produkty> produktyLista=FXCollections.observableArrayList();
    private ObservableList<Produkty> wybrane_produktyLista=FXCollections.observableArrayList();
    private Produkty selectedProduct;
    //Metody
    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }
    public ObservableList<Produkty> getProduktyLista() {
        if(produktyLista.isEmpty())
            produktyLista.clear();
        produktyLista = FXCollections.observableArrayList((List<Produkty>) produktService.listaProdukty());

        return produktyLista;
    }

    public void selectProduct()
    {
        selectedProduct = produktyTable.getSelectionModel().getSelectedItem();
        wybrana_nazwaField.setText(produktyTable.getSelectionModel().getSelectedItem().getNazwa_produktu());
    }

    public void addProduct()
    {

        if(produktyTable.getSelectionModel().getSelectedItem()!=null)
        {
            //jesli jest produkt to sprawdzamy czy jest dostepna dana ilosc
            String r1 = "[0-9]{1,}";
            if(iloscField.getText().matches(r1) && selectedProduct.getIlosc_sztuk()>=Integer.parseInt(iloscField.getText()) && selectedProduct.getIlosc_sztuk()>0 && Integer.parseInt(iloscField.getText())>0)
            {
                int ilosc = Integer.parseInt(iloscField.getText());
                int stan = selectedProduct.getIlosc_sztuk();
                iloscLabel.setTextFill(Color.BLACK);
                Produkty new_prod = new Produkty(selectedProduct.getId_produktu(),selectedProduct.getId_producenta(),selectedProduct.getId_kategorii(),selectedProduct.getNazwa_produktu(),selectedProduct.getCena(),selectedProduct.getIlosc_sztuk(),selectedProduct.getProducenci(),selectedProduct.getKategorie());
                new_prod.setIlosc_sztuk(ilosc);
                wybrane_produktyLista.addAll(new_prod);
                selectedProduct.setIlosc_sztuk(stan-ilosc);
                wybraneTable.setItems(wybrane_produktyLista);
                produktyTable.setItems(produktyLista);
                //odswiezenie tabel
                wybraneTable.refresh();
                produktyTable.refresh();
            }else iloscLabel.setTextFill(Color.RED);



        }
        else System.out.println("Nie wybrano");
    }



    public void confirmFaktura()
    {
        //sprawdzenie czy klient został wybrany
        if(klientCombo.getSelectionModel().getSelectedItem()==null)klientLabel.setTextFill(Color.RED);
        else klientLabel.setTextFill(Color.BLACK);



        //tu dopiero sprawdzenie wszystkich wararunkow i dodanie
        if(klientCombo.getSelectionModel().getSelectedItem()!=null && !wybrane_produktyLista.isEmpty())
        {
            List<Integer> list1= new ArrayList<>();
            Session s1 = (Session) HibernateUtil.getSessionFactory().openSession();
            s1.beginTransaction();
            String pelna_nazwa = klientCombo.getSelectionModel().getSelectedItem().toString();
            list1 = s1.createQuery("Select k.id_klienta from Klienci k WHERE CONCAT(k.imie,' ',k.nazwisko)='"+pelna_nazwa+"'").list();
            Integer id_klienta = list1.get(0);
            Klienci k = new Klienci(id_klienta,"","","","");
            System.out.println(pelna_nazwa+" "+id_klienta);
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            System.out.println(formatter.format(date));
            //to id i tak sie doda z autoinkremenacji
            Faktury nowa_faktura = new Faktury(0,id_klienta,date,k);
            FakturaService fakturaService= new FakturaServiceImpl();
            fakturaService.dodajFakture(nowa_faktura); //bangla
            s1.getTransaction().commit();
            s1.close();
            //  ----------------------------------------------------------------------
            Session s2 = (Session) HibernateUtil.getSessionFactory().openSession();
            s2.beginTransaction();
            List<Integer> list2= new ArrayList<>();
            list2 = s2.createQuery("select max(f.id_faktury) from Faktury f").list();
            int id_fak = list2.get(0);

            System.out.println(id_fak);
            //tu ustawiamy id biezacej faktury
            nowa_faktura.setId_faktury(id_fak);
            //aktualizujemy produkty
            for(int i=0;i<produktyLista.size();i++)
            {
                Produkty p = produktyLista.get(i);
                produktService.zaktualizujProdukt(p);
            }
            Faktury_produktyService faktury_produktyService = new Faktury_produktyServiceImpl();
            for(int i=0;i<wybrane_produktyLista.size();i++)
            {
                Produkty p = wybrane_produktyLista.get(i);
                Faktury_produkty faktury_produkty = new Faktury_produkty(0,id_fak,p.getId_produktu(),p.getNazwa_produktu(),p.getCena(),p.getIlosc_sztuk(),nowa_faktura,p);
                faktury_produktyService.dodajFaktury_produkty(faktury_produkty);
            }





            s2.getTransaction().commit();
            s2.close();
            alert.setText("Dodano fakture! ");
        }else alert.setText("Błąd! ");

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
        //  -----------------------------------------------------------------------------------------------------------------------
        //  wczytanie produktów
        produktyTable_idProduktu.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_produktu"));
        produktyTable_idProducenta.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_producenta"));
        produktyTable_idKategorii.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_kategorii"));
        produktyTable_nazwa.setCellValueFactory(new PropertyValueFactory<Produkty, String>("nazwa_produktu"));
        produktyTable_cena.setCellValueFactory(new PropertyValueFactory<Produkty, Double>("cena"));
        produktyTable_ilośc.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("ilosc_sztuk"));
        produktyTable.setItems(getProduktyLista());
        produktyTable.setEditable(true);

        // tabela wybrane produkty
        wybraneTable_idProduktu.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_produktu"));
        wybraneTable_idProducenta.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_producenta"));
        wybraneTable_idKategorii.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_kategorii"));
        wybraneTable_nazwa.setCellValueFactory(new PropertyValueFactory<Produkty, String>("nazwa_produktu"));
        wybraneTable_cena.setCellValueFactory(new PropertyValueFactory<Produkty, Double>("cena"));
        wybraneTable_ilośc.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("ilosc_sztuk"));


    }





}
