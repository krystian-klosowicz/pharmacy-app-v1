package services;


import entity.Produkty;

import java.util.List;

public interface ProduktService {

    public void dodajProdukt(Produkty produkt);
    public List<Produkty> listaProdukty();
    public List<Produkty> wyszukajProdukty(String id_produktu, String id_producenta, String id_kategorii, String nazwa_produktu);
    public void usunProdukt(Integer id);
    public void zaktualizujProdukt(Produkty produkt);


}
