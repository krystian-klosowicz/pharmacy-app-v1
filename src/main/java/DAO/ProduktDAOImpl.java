package DAO;

import entity.HibernateUtil;
import entity.Produkty;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class ProduktDAOImpl implements ProduktDAO {

    @Override
    public void dodajProdukt(Produkty produkt) {
        Session s= (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.save(produkt);
        s.getTransaction().commit();
        s.close();
    }

    @Override
    public List<Produkty> listaProdukty() {
        List<Produkty> list = new ArrayList<>();
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        list = s.createQuery("from Produkty").list();
        s.getTransaction().commit();
        s.close();
        return list;
    }

    @Override
    public List<Produkty> wyszukajProdukty(String id_produktu, String id_producenta, String id_kategorii, String nazwa_produktu) {
        List<Produkty> list = new ArrayList<>();
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        //Sprawdzanie czy dane sa wprowadzone
        String w1,w2,w3,w4;
        if(id_produktu=="") {
            w1 = "p.id_produktu IS NOT NULL";
        } else w1 = "p.id_produktu='"+id_produktu+"'";

        if(id_producenta=="") {
            w2 = "p.id_producenta IS NOT NULL";
        } else w2 = "p.id_producenta='"+id_producenta+"'";

        if(id_kategorii=="") {
            w3 = "p.id_kategorii IS NOT NULL";
        } else w3 = "p.id_kategorii='"+id_kategorii+"'";

        if(nazwa_produktu=="") {
            w4 = "p.nazwa_produktu IS NOT NULL";
        } else w4 = "p.nazwa_produktu='"+nazwa_produktu+"'";


        list = s.createQuery("from Produkty p where "+w1+" AND "+w2+" AND "+w3+" AND "+w4+"").list();
        s.getTransaction().commit();
        s.close();
        return list;
    }

    @Override
    public void usunProdukt(Integer id) {
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Produkty produkt = (Produkty) s.load(Produkty.class, id);
        s.delete(produkt);
        s.getTransaction().commit();
        s.close();
    }

    @Override
    public void zaktualizujProdukt(Produkty produkt) {
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.update(produkt);
        s.getTransaction().commit();
        s.close();
    }
}
