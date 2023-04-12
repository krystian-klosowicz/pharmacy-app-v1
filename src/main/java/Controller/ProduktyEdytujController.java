package Controller;


import entity.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.hibernate.Session;
import services.KlientService;
import services.KlientServiceImpl;
import services.ProduktService;
import services.ProduktServiceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProduktyEdytujController implements Initializable {


    @FXML
    private Text alert;
    @FXML
    private TableColumn<Produkty, Double> cena;
    @FXML
    private Button deleteButton;
    @FXML
    private ImageView exitImage;
    @FXML
    private TableColumn<Produkty, Integer> idKategorii;
    @FXML
    private TableColumn<Produkty, Integer> idProducenta;
    @FXML
    private TableColumn<Produkty, Integer> idProduktu;
    @FXML
    private TableColumn<Produkty, Integer> ilośc;
    @FXML
    private ComboBox<String> kategoriaCombo;
    @FXML
    private TableColumn<Produkty, String> nazwa;
    @FXML
    private TextField nazwaField;
    @FXML
    private ComboBox<String> producentCombo;
    @FXML
    private TableView<Produkty> table;
    @FXML
    private Button wszyscyButton;
    @FXML
    private Button wyszukajButton;


    private ProduktService produktService = new ProduktServiceImpl();
    private ObservableList<Produkty> produktyLista=FXCollections.observableArrayList();





    //Funkcje

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

    public ObservableList<Produkty> getWyszukajProduktyLista(String id_produktu, String id_producenta, String id_kategorii, String nazwa_produktu) {
        if(produktyLista.isEmpty())
            produktyLista.clear();
        produktyLista = FXCollections.observableArrayList((List<Produkty>) produktService.wyszukajProdukty(id_produktu,id_producenta,id_kategorii,nazwa_produktu));

        return produktyLista;
    }




    public void pokazWszystkich()
    {
        nazwaField.setText("");
        kategoriaCombo.getSelectionModel().clearSelection();
        producentCombo.getSelectionModel().clearSelection();
        table.setItems(getProduktyLista());
    }

    public void wyszukaj()
    {
        List<Integer> list_k = new ArrayList<>();
        List<Integer> list_p= new ArrayList<>();
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        String id_producenta,id_kategorii;
        if(kategoriaCombo.getSelectionModel().getSelectedItem()!=null)
        {
            list_k = s.createQuery("select id_kategorii from Kategorie k where k.nazwa_kategorii='"+kategoriaCombo.getSelectionModel().getSelectedItem()+"'").list();
            Integer id_kat = list_k.get(0);
            id_kategorii= id_kat.toString();
        } else
        {
            id_kategorii="";
        }
        if(producentCombo.getSelectionModel().getSelectedItem()!=null)
        {
            list_p = s.createQuery("select id_producenta from Producenci p where p.nazwa_producenta='"+producentCombo.getSelectionModel().getSelectedItem()+"'").list();
            Integer id_prod = list_p.get(0);
            id_producenta = id_prod.toString();

        }else
        {
            id_producenta="";
        }


        String nazwa = nazwaField.getText();


        s.getTransaction().commit();
        s.close();
        table.setItems(getWyszukajProduktyLista("",id_producenta,id_kategorii,nazwa));

    }
    public void usunProdukt(Integer id){
        produktService.usunProdukt(id);
    }

    public void zaktualizujProdukt(Produkty produkt){
        produktService.zaktualizujProdukt(produkt);
    }

    public void setDeleteButton()
    {
        try
        {
            if(table.getSelectionModel().getSelectedItem()!=null){
                usunProdukt(table.getSelectionModel().getSelectedItem().getId_produktu());
                table.setItems(getProduktyLista());
                alert.setText("");
                System.out.println("Usunięto produkt! ");
            }else {
                alert.setText("Nie wybrano produktu! ");
            }
        }catch(Exception e)
        {
            alert.setText("Dany produkt jest na fakturze, błąd usuwania!");
        }
    }

    public void initialize(URL url, ResourceBundle rb) throws NumberFormatException {
        ProduktyEdytujController controller = new ProduktyEdytujController();
        //Wczytanie produktów
        idProduktu.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_produktu"));
        idProducenta.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_producenta"));
        idKategorii.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_kategorii"));
        nazwa.setCellValueFactory(new PropertyValueFactory<Produkty, String>("nazwa_produktu"));
        cena.setCellValueFactory(new PropertyValueFactory<Produkty, Double>("cena"));
        ilośc.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("ilosc_sztuk"));
        table.setItems(getProduktyLista());
        table.setEditable(true);
        //Wczytanie kategorii oraz producentow do combo
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

        //Edycja
        Callback<TableColumn<Produkty, String>, TableCell<Produkty, String>> defaultCellFactory = TextFieldTableCell.forTableColumn();
        String r1 = "[a-zA-Z]{3,}.*";// do nazwy
        String r2 = "[0-9]{1,}";
        String r3 = "[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)";

        //  edytowanie nazwy produktu

        nazwa.setCellFactory(col -> {
            TableCell<Produkty, String> cell = defaultCellFactory.call(col);
            cell.setAlignment(Pos.CENTER_LEFT);
            return cell;
        });

        nazwa.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Produkty, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Produkty, String> t) {

                if(t.getNewValue().matches(r1))
                {
                    System.out.println("Zaktualizowano nazwe produktu");
                    ((Produkty) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNazwa_produktu(t.getNewValue());
                    Produkty p = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    System.out.println(p.toString());
                    p.setNazwa_produktu(t.getNewValue());
                    controller.zaktualizujProdukt(p);
                    alert.setText("");
                }else alert.setText("Błędne dane! ");
                table.setItems(getProduktyLista());
            }
        });

        //  edytowanie ceny

        cena.setCellFactory(TextFieldTableCell.<Produkty, Double>forTableColumn(new DoubleStringConverter()));
        //cena.setCellFactory(col -> new EditingIntegerCell<>());

            cena.setOnEditCommit((TableColumn.CellEditEvent<Produkty, Double> t) -> {
                if (t.getNewValue().toString().matches(r3)) {
                    double cena = t.getNewValue();
                    cena = Math.round(cena * 100.0) / 100.0;
                    System.out.println(t.getNewValue());
                    ((Produkty) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setCena(cena);
                    Produkty p = t.getTableView().getItems().get(t.getTablePosition().getRow());

                    p.setCena(cena);
                    System.out.println(p.getCena());
                    controller.zaktualizujProdukt(p);
                    System.out.println(p.getCena());
                    alert.setText("");
                } else alert.setText("Błędne dane! ");
                table.setItems(getProduktyLista());

            });


        //  edytowanie ilosci

            ilośc.setCellFactory(TextFieldTableCell.<Produkty, Integer>forTableColumn(new IntegerStringConverter()));
            //cena.setCellFactory(col -> new EditingIntegerCell<>());
            ilośc.setOnEditCommit((TableColumn.CellEditEvent<Produkty, Integer> t) -> {
                if (t.getNewValue().toString().matches(r2)) {
                    ((Produkty) t.getTableView().getItems().get(
                          t.getTablePosition().getRow())
                   ).setIlosc_sztuk(t.getNewValue().intValue());
                    Produkty p = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    p.setIlosc_sztuk(t.getNewValue());
                    controller.zaktualizujProdukt(p);
                    alert.setText("");
               } else alert.setText("Błędne dane! ");
               table.setItems(getProduktyLista());

            });







    }






}
