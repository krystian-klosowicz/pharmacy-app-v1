package services;

import DAO.ProducentDAO;
import DAO.ProducentDAOImpl;
import entity.Producenci;

import java.util.List;

public class ProducentServiceImpl implements ProducentService{
    private ProducentDAO producentDAO = new ProducentDAOImpl();
    @Override
    public void dodajProducenta(Producenci producent) {
        producentDAO.dodajProducenta(producent);
    }

    @Override
    public List<Producenci> listaProducenci() {
        return producentDAO.listaProducenci();
    }


    @Override
    public void usunProducenta(Integer id) {
        producentDAO.usunProducenta(id);
    }

    @Override
    public void zaktualizujProducenta(Producenci producent) {
        producentDAO.zaktualizujProducenta(producent);
    }
}
