package DAO;

import entity.HibernateUtil;
import entity.Faktury;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class FakturaDAOImpl implements FakturaDAO {

    @Override
    public void dodajFakture(Faktury faktura) {
        Session s= (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.save(faktura);
        s.getTransaction().commit();
        s.close();
    }

    @Override
    public List<Faktury> listaFaktury() {
        List<Faktury> list = new ArrayList<>();
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        list = s.createQuery("from Faktury").list();
        s.getTransaction().commit();
        s.close();
        return list;
    }

    @Override
    public List<Faktury> wyszukajFaktury(String id_faktury, String id_klienta) {
        List<Faktury> list = new ArrayList<>();
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        //Sprawdzanie czy dane sa wprowadzone
        String w1,w2;
        if(id_faktury=="") {
            w1 = "f.id_faktury IS NOT NULL";
        } else w1 = "p.id_faktury='"+id_faktury+"'";

        if(id_klienta=="") {
            w2 = "f.id_klienta IS NOT NULL";
        } else w2 = "f.id_klienta='"+id_klienta+"'";



        list = s.createQuery("from Faktury f where "+w1+" AND "+w2+"").list();
        s.getTransaction().commit();
        s.close();
        return list;
    }

    @Override
    public void usunFakture(Integer id) {
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Faktury faktura = (Faktury) s.load(Faktury.class, id);
        s.delete(faktura);
        s.getTransaction().commit();
        s.close();
    }

    @Override
    public void zaktualizujFakture(Faktury faktura) {
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.update(faktura);













        s.getTransaction().commit();
        s.close();
    }
}
