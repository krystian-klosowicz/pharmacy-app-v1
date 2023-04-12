package services;


import entity.Producenci;

import java.util.List;

public interface ProducentService {

    public void dodajProducenta(Producenci producent);
    public List<Producenci> listaProducenci();
    public void usunProducenta(Integer id);
    public void zaktualizujProducenta(Producenci producent);


}
