package DAO;
import entity.Klienci;

import java.util.List;

public interface KlientDAO {

    public void dodajKlienta(Klienci klient);
    public List<Klienci> listaKlienci();
    public List<Klienci> wyszukajKlienci(String id, String imie, String nazwisko,String pesel,String adres);
    public void usunKlienta(Integer id);
    public void zaktualizujKlienta(Klienci klient);

}
