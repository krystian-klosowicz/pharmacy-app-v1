package services;

import DAO.Faktury_produktyDAO;
import DAO.Faktury_produktyDAOImpl;
import entity.Faktury_produkty;

import java.util.List;

public class Faktury_produktyServiceImpl implements Faktury_produktyService{
    private Faktury_produktyDAO faktury_produktyDAO = new Faktury_produktyDAOImpl();
    @Override
    public void dodajFaktury_produkty(Faktury_produkty faktury_produkty) {
        faktury_produktyDAO.dodajFaktury_produkty(faktury_produkty);
    }

    @Override
    public List<Faktury_produkty> listaFaktury_produkty() {
        return faktury_produktyDAO.listaFaktury_produkty();
    }
    @Override
    public List<Faktury_produkty> wyszukajFaktury_produkty(String id_faktury) {
        return faktury_produktyDAO.wyszukajFaktury_produkty(id_faktury);
    }

    @Override
    public void usunFaktury_produkty(Integer id) {
        faktury_produktyDAO.usunFaktury_produkty(id);
    }

    @Override
    public void zaktualizujFaktury_produkty(Faktury_produkty faktury_produkty) {
        faktury_produktyDAO.zaktualizujFaktury_produkty(faktury_produkty);
    }
}
