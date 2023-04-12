package DAO;

import entity.HibernateUtil;
import entity.Producenci;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class ProducentDAOImpl implements ProducentDAO {

    @Override
    public void dodajProducenta(Producenci producent) {
        Session s= (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.save(producent);
        s.getTransaction().commit();
        s.close();
    }

    @Override
    public List<Producenci> listaProducenci() {
        List<Producenci> list = new ArrayList<>();
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        list = s.createQuery("from Producenci ").list();
        s.getTransaction().commit();
        s.close();
        return list;
    }


    @Override
    public void usunProducenta(Integer id) {
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Producenci producent = (Producenci) s.load(Producenci.class, id);
        s.delete(producent);
        s.getTransaction().commit();
        s.close();
    }

    @Override
    public void zaktualizujProducenta(Producenci producent) {
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.update(producent);
        s.getTransaction().commit();
        s.close();
    }
}
