package services;

import DAO.KategorieDAO;
import DAO.KategorieDAOImpl;
import entity.Kategorie;

import java.util.List;

public class KategorieServiceImpl implements KategorieService{
    private KategorieDAO kategorieDAO= new KategorieDAOImpl();
    @Override
    public List<Kategorie> listaKategorie() {
        return kategorieDAO.listaKategorie();
    }
}
