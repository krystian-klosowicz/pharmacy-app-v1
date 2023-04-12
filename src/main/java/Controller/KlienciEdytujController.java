package Controller;


import app.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import entity.Klienci;
import services.KlientService;
import services.KlientServiceImpl;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class KlienciEdytujController implements Initializable {


    @FXML
    private Text alert;
    @FXML
    private ImageView exitImage;
    @FXML
    private Button deleteButton;
    //  elementy do wyszukiwania
    @FXML
    private TextField idWyszukaj;
    @FXML
    private TextField imieWyszukaj;
    @FXML
    private TextField nazwiskoWyszukaj;
    @FXML
    private TextField peselWyszukaj;
    @FXML
    private TextField adresWyszukaj;
    @FXML
    private Button wyszukajButton;
    @FXML
    private Button wszyscyButton;
    //  elementy do tabeli
    @FXML
    private TableView<Klienci> table;
    @FXML
    private TableColumn<Klienci, String> adres;
    @FXML
    private TableColumn<Klienci, Integer> idKlienta;
    @FXML
    private TableColumn<Klienci, String> imie;
    @FXML
    private TableColumn<Klienci, String> nazwisko;
    @FXML
    private TableColumn<Klienci, String> pesel;

    private KlientService klienciService = new KlientServiceImpl();
    private ObservableList<Klienci> klienciLista=FXCollections.observableArrayList();




    //Funkcje

    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }



    public ObservableList<Klienci> getKlienciLista() {
        if(klienciLista.isEmpty())
            klienciLista.clear();
            klienciLista = FXCollections.observableArrayList((List<Klienci>) klienciService.listaKlienci());

        return klienciLista;
    }

    public ObservableList<Klienci> getWyszukajKlienciLista(String id, String imie, String nazwisko,String pesel,String adres) {
        if(klienciLista.isEmpty())
            klienciLista.clear();
        klienciLista = FXCollections.observableArrayList((List<Klienci>) klienciService.wyszukajKlienci(id,imie,nazwisko,pesel,adres));

        return klienciLista;
    }

    public void usunKlienta(Integer id){
        klienciService.usunKlienta(id);
    }

    public void zaktualizujKlienta(Klienci klient){
        klienciService.zaktualizujKlienta(klient);
    }

    public void setDeleteButton(){
        try
        {
            if(table.getSelectionModel().getSelectedItem()!=null){
                usunKlienta(table.getSelectionModel().getSelectedItem().getId_klienta());
                table.setItems(getKlienciLista());
                alert.setText("Usunięto klienta! ");
                alert.setFill(Color.BLACK);
            }else {
                alert.setText("Nie wybrano klienta! ");
            }
        }catch(Exception e)
        {
            alert.setText("Nie można usunąć tego klienta, poniewaz posiada faktury!");
        }
    }

    public void pokazWszystkich()
    {
        idWyszukaj.setText("");
        imieWyszukaj.setText("");
        peselWyszukaj.setText("");
        nazwiskoWyszukaj.setText("");
        adresWyszukaj.setText("");
        table.setItems(getKlienciLista());
    }

    public void wyszukaj()
    {
        String id = idWyszukaj.getText();
        String imie = imieWyszukaj.getText();
        String nazwisko = nazwiskoWyszukaj.getText();
        String pesel = peselWyszukaj.getText();
        String adres = adresWyszukaj.getText();
        //klienciLista=FXCollections.observableArrayList();
        table.setItems(getWyszukajKlienciLista(id,imie,nazwisko,pesel,adres));
    }


    public void initialize(URL url, ResourceBundle rb) {
        KlienciEdytujController controller = new KlienciEdytujController();
        idKlienta.setCellValueFactory(new PropertyValueFactory<Klienci, Integer>("id_klienta"));
        imie.setCellValueFactory(new PropertyValueFactory<Klienci, String>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<Klienci, String>("nazwisko"));
        pesel.setCellValueFactory(new PropertyValueFactory<Klienci, String>("pesel"));
        adres.setCellValueFactory(new PropertyValueFactory<Klienci, String>("adres"));
        table.setItems(getKlienciLista());
        table.setEditable(true);

        //  EDYCJA
        Callback<TableColumn<Klienci, String>, TableCell<Klienci, String>> defaultCellFactory = TextFieldTableCell.forTableColumn();

        String r1 = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]*";
        String r2 = "[0-9]{4}[0-3]{1}[0-9]{6}";
        String r3 = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]*";
        //Edycja imienia

        imie.setCellFactory(col -> {
            TableCell<Klienci, String> cell = defaultCellFactory.call(col);
            cell.setAlignment(Pos.CENTER_LEFT);
            return cell;
        });

        imie.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Klienci, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Klienci, String> t) {

                if(t.getNewValue().matches(r3) && !t.getNewValue().isEmpty())
                {
                    //System.out.println("Zaktualizowano imie");
                    ((Klienci) t.getTableView().getItems().get(t.getTablePosition().getRow())).setImie(t.getNewValue());
                    Klienci k = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    k.setImie(t.getNewValue());
                    controller.zaktualizujKlienta(k);
                    alert.setText("");
                }else alert.setText("Błędne dane! ");
                table.setItems(getKlienciLista());
            }
        });

        //Edycja nazwiska
        nazwisko.setCellFactory(col -> {
            TableCell<Klienci, String> cell = defaultCellFactory.call(col);
            cell.setAlignment(Pos.CENTER_LEFT);
            return cell;
        });

        nazwisko.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Klienci, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Klienci, String> t) {

                if(t.getNewValue().matches(r1) && !t.getNewValue().isEmpty())
                {
                    //System.out.println("Zaktualizowano nazwisko");
                    ((Klienci) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNazwisko(t.getNewValue());
                    Klienci k = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    k.setNazwisko(t.getNewValue());
                    controller.zaktualizujKlienta(k);
                    alert.setText("");
                }else alert.setText("Błędne dane! ");
                table.setItems(getKlienciLista());

            }
        });
        //Edycja peselu
        pesel.setCellFactory(col -> {
            TableCell<Klienci, String> cell = defaultCellFactory.call(col);
            cell.setAlignment(Pos.CENTER_LEFT);
            return cell;
        });

        pesel.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Klienci, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Klienci, String> t) {

                System.out.println(t.getNewValue());
                if(t.getNewValue().matches(r2))
                {
                    KlientService klientService = new KlientServiceImpl();
                    List<Klienci> do_sprawdzenia_peselu = new ArrayList<>();
                    do_sprawdzenia_peselu = klientService.wyszukajKlienci("","","",t.getNewValue(),"");
                    if(do_sprawdzenia_peselu.isEmpty())
                    {
                        ((Klienci) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPesel(t.getNewValue());
                        Klienci k = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        k.setPesel(t.getNewValue());
                        controller.zaktualizujKlienta(k);
                        alert.setText("");
                    }else alert.setText("Ten pesel już istnieje!");

                }else alert.setText("Błędne dane! ");
                table.setItems(getKlienciLista());

            }
        });
        //Edycja adresu
        adres.setCellFactory(col -> {
            TableCell<Klienci, String> cell = defaultCellFactory.call(col);
            cell.setAlignment(Pos.CENTER_LEFT);
            return cell;
        });

        adres.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Klienci, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Klienci, String> t) {

                if(!t.getNewValue().isEmpty() && !t.getNewValue().contains("="))
                {
                    ((Klienci) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAdres(t.getNewValue());
                    Klienci k = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    k.setAdres(t.getNewValue());
                    controller.zaktualizujKlienta(k);
                    alert.setText("");
                }else alert.setText("Błędne dane! ");
                table.setItems(getKlienciLista());

            }
        });

    }



}
