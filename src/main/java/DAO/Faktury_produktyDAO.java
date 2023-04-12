package DAO;
import entity.Faktury_produkty;

import java.util.List;

public interface Faktury_produktyDAO {

    public void dodajFaktury_produkty(Faktury_produkty faktury_produkty);
    public List<Faktury_produkty> listaFaktury_produkty();
    public List<Faktury_produkty> wyszukajFaktury_produkty(String id_faktury);
    public void usunFaktury_produkty(Integer id);
    public void zaktualizujFaktury_produkty(Faktury_produkty faktury_produkty);

}
