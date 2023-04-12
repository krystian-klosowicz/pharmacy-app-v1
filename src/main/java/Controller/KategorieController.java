package Controller;


import app.Main;
import entity.Klienci;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import entity.Kategorie;
import services.KategorieService;
import services.KategorieServiceImpl;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class KategorieController implements Initializable {
    @FXML
    private ImageView exitImage;
    @FXML
    private ImageView backImage;
    //  elementy tabeli
    @FXML
    private TableView<Kategorie> table;
    @FXML
    private TableColumn<Kategorie, Integer> idKategorii;
    @FXML
    private TableColumn<Kategorie, String> nazwa;


    private KategorieService kategorieService = new KategorieServiceImpl();
    private ObservableList<Kategorie> kategorieLista=FXCollections.observableArrayList();


    //  Metody

    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void back() throws IOException {
        Stage stage = (Stage) backImage.getScene().getWindow();
        FXMLLoader home = new FXMLLoader(Main.class.getResource("/fxml/Home.fxml"));
        Scene sceneHome = new Scene(home.load(), 750, 500);
        stage.setScene(sceneHome);
        stage.setTitle("Home.fxml");
    }

    public ObservableList<Kategorie> getKategorieLista() {
        if(kategorieLista.isEmpty())
            kategorieLista.clear();
        kategorieLista = FXCollections.observableArrayList((List<Kategorie>) kategorieService.listaKategorie());

        return kategorieLista;
    }





    public void initialize(URL url, ResourceBundle rb)
    {
        KategorieController controller = new KategorieController();
        idKategorii.setCellValueFactory(new PropertyValueFactory<Kategorie, Integer>("id_kategorii"));
        nazwa.setCellValueFactory(new PropertyValueFactory<Kategorie, String>("nazwa_kategorii"));
        table.setItems(getKategorieLista());
    }
}
