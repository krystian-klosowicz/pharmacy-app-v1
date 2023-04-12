package services;

import DAO.KlientDAO;
import DAO.KlientDAOImpl;
import entity.Klienci;

import java.util.List;

public class KlientServiceImpl implements KlientService{
    private KlientDAO klientDAO = new KlientDAOImpl();
    @Override
    public void dodajKlienta(Klienci klient) {
        klientDAO.dodajKlienta(klient);
    }

    @Override
    public List<Klienci> listaKlienci() {
        return klientDAO.listaKlienci();
    }
    @Override
    public List<Klienci> wyszukajKlienci(String id, String imie, String nazwisko,String pesel,String adres) {
        return klientDAO.wyszukajKlienci(id, imie, nazwisko, pesel, adres);
    }

    @Override
    public void usunKlienta(Integer id) {
        klientDAO.usunKlienta(id);
    }

    @Override
    public void zaktualizujKlienta(Klienci klient) {
        klientDAO.zaktualizujKlienta(klient);
    }
}
