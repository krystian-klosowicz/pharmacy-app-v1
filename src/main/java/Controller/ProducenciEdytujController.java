package Controller;


import entity.HibernateUtil;
import entity.Producenci;
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
import services.ProducentService;
import services.ProducentServiceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProducenciEdytujController implements Initializable {


    @FXML
    private Text alert;
    @FXML
    private ImageView exitImage;
    @FXML
    private Button deleteButton;

    //  elementy do tabeli
    @FXML
    private TableView<Producenci> table;
    @FXML
    private TableColumn<Producenci, Integer> idProducenta;
    @FXML
    private TableColumn<Producenci, String> nip;
    @FXML
    private TableColumn<Producenci, String> nazwa;
    @FXML
    private TableColumn<Producenci, String> adres;

    private ProducentService producenciService = new ProducentServiceImpl();
    private ObservableList<Producenci> producenciLista=FXCollections.observableArrayList();




    //Funkcje

    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }


    public void dodajProducenta(Producenci producent) {
        producenciService.dodajProducenta(producent);
    }

    public ObservableList<Producenci> getProducenciLista() {
        if(producenciLista.isEmpty())
            producenciLista.clear();
            producenciLista = FXCollections.observableArrayList((List<Producenci>) producenciService.listaProducenci());

        return producenciLista;
    }


    public void usunProducenta(Integer id){
        producenciService.usunProducenta(id);
    }

    public void zaktualizujProducenta(Producenci producent){
        producenciService.zaktualizujProducenta(producent);
    }

    public void setDeleteButton(){
            try
            {
                if(table.getSelectionModel().getSelectedItem()!=null){
                    usunProducenta(table.getSelectionModel().getSelectedItem().getId_producenta());
                    table.setItems(getProducenciLista());
                    alert.setText("");
                    System.out.println("Usunięto producenta! ");
                }else {
                    alert.setText("Nie wybrano producenta! ");
                }
            }catch(Exception e)
            {
                alert.setText("Nie mozna usunac producenta, ponieważ istnieje jego produkt!");
            }





    }



    public void initialize(URL url, ResourceBundle rb) {
        ProducenciEdytujController controller = new ProducenciEdytujController();
        idProducenta.setCellValueFactory(new PropertyValueFactory<Producenci, Integer>("id_producenta"));
        nip.setCellValueFactory(new PropertyValueFactory<Producenci, String>("NIP"));

        nazwa.setCellValueFactory(new PropertyValueFactory<Producenci, String>("nazwa_producenta"));
        adres.setCellValueFactory(new PropertyValueFactory<Producenci, String>("adres"));
        table.setItems(getProducenciLista());
        table.setEditable(true);

        //  EDYCJA
        Callback<TableColumn<Producenci, String>, TableCell<Producenci, String>> defaultCellFactory = TextFieldTableCell.forTableColumn();

        String r1 = "[0-9]{10}"; // nip
        String r2 = "[a-zA-Z]{3,}.*"; //nazwa adres
        //Edycja nip

        nip.setCellFactory(col -> {
            TableCell<Producenci, String> cell = defaultCellFactory.call(col);
            cell.setAlignment(Pos.CENTER_LEFT);
            return cell;
        });

        nip.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Producenci, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Producenci, String> t) {

                List<Producenci> lista_prod= new ArrayList<>();
                Session s = (Session) HibernateUtil.getSessionFactory().openSession();
                s.beginTransaction();
                lista_prod= s.createSQLQuery("select * from Producenci p where p.NIP='"+t.getNewValue()+"'").list();
                s.getTransaction().commit();
                s.close();
                if(lista_prod.isEmpty()){
                    if(t.getNewValue().matches(r1) && !t.getNewValue().isEmpty())
                    {
                        System.out.println("Zaktualizowano nip");
                        ((Producenci) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNIP(t.getNewValue());
                        Producenci p = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        p.setNIP(t.getNewValue());
                        controller.zaktualizujProducenta(p);
                        alert.setText("");
                    }else alert.setText("Błędne dane! ");
                    table.setItems(getProducenciLista());
                } else
                {
                    alert.setText("Podany NIP jest w bazie!");
                    table.setItems(getProducenciLista());
                }

                }
        });

        //Edycja nazwy
        nazwa.setCellFactory(col -> {
            TableCell<Producenci, String> cell = defaultCellFactory.call(col);
            cell.setAlignment(Pos.CENTER_LEFT);
            return cell;
        });

        nazwa.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Producenci, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Producenci, String> t) {

                if(t.getNewValue().matches(r2))
                {
                    System.out.println("Zaktualizowano nazwe");
                    ((Producenci) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNazwa_producenta(t.getNewValue());
                    Producenci p = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    p.setNazwa_producenta(t.getNewValue());
                    controller.zaktualizujProducenta(p);
                    alert.setText("");
                }else alert.setText("Błędne dane! ");
                table.setItems(getProducenciLista());

            }
        });
        //Edycja adresu
        adres.setCellFactory(col -> {
            TableCell<Producenci, String> cell = defaultCellFactory.call(col);
            cell.setAlignment(Pos.CENTER_LEFT);
            return cell;
        });

        adres.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Producenci, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Producenci, String> t) {

                if(t.getNewValue().matches(r2) && !t.getNewValue().contains("="))
                {
                    ((Producenci) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAdres(t.getNewValue());
                    Producenci p = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    p.setAdres(t.getNewValue());
                    controller.zaktualizujProducenta(p);
                    alert.setText("");
                }else alert.setText("Błędne dane! ");
                table.setItems(getProducenciLista());

            }
        });


    }



}
