package services;


import entity.Klienci;
import java.util.List;

public interface KlientService {

    public void dodajKlienta(Klienci klient);
    public List<Klienci> listaKlienci();
    public List<Klienci> wyszukajKlienci(String id, String imie, String nazwisko,String pesel,String adres);
    public void usunKlienta(Integer id);
    public void zaktualizujKlienta(Klienci klient);


}
