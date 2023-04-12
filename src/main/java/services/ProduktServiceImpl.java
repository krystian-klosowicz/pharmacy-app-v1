package services;

import DAO.ProduktDAO;
import DAO.ProduktDAOImpl;
import entity.Produkty;

import java.util.List;

public class ProduktServiceImpl implements ProduktService{
    private ProduktDAO produktDAO = new ProduktDAOImpl();
    @Override
    public void dodajProdukt(Produkty produkt) {
        produktDAO.dodajProdukt(produkt);
    }

    @Override
    public List<Produkty> listaProdukty() {
        return produktDAO.listaProdukty();
    }
    @Override
    public List<Produkty> wyszukajProdukty(String id_produktu, String id_producenta, String id_kategorii, String nazwa_produktu) {
        return produktDAO.wyszukajProdukty(id_produktu, id_producenta, id_kategorii, nazwa_produktu);
    }

    @Override
    public void usunProdukt(Integer id) {
        produktDAO.usunProdukt(id);
    }

    @Override
    public void zaktualizujProdukt(Produkty produkt) {
        produktDAO.zaktualizujProdukt(produkt);
    }
}
