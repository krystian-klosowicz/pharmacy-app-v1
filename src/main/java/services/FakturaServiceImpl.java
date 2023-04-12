package services;

import DAO.FakturaDAO;
import DAO.FakturaDAOImpl;
import entity.Faktury;

import java.util.List;

public class FakturaServiceImpl implements FakturaService{
    private FakturaDAO fakturaDAO = new FakturaDAOImpl();
    @Override
    public void dodajFakture(Faktury faktura) {
        fakturaDAO.dodajFakture(faktura);
    }

    @Override
    public List<Faktury> listaFaktury() {
        return fakturaDAO.listaFaktury();
    }
    @Override
    public List<Faktury> wyszukajFaktury(String id_faktury, String id_klienta) {
        return fakturaDAO.wyszukajFaktury(id_faktury, id_klienta);
    }

    @Override
    public void usunFakture(Integer id) {
        fakturaDAO.usunFakture(id);
    }

    @Override
    public void zaktualizujFakture(Faktury faktura) {
        fakturaDAO.zaktualizujFakture(faktura);
    }
}
