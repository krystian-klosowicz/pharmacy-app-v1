package DAO;

import entity.HibernateUtil;
import entity.Faktury_produkty;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class Faktury_produktyDAOImpl implements Faktury_produktyDAO {

    @Override
    public void dodajFaktury_produkty(Faktury_produkty faktury_produkty) {
        Session s= (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.save(faktury_produkty);
        s.getTransaction().commit();
        s.close();
    }

    @Override
    public List<Faktury_produkty> listaFaktury_produkty() {
        List<Faktury_produkty> list = new ArrayList<>();
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        list = s.createQuery("from Faktury_produkty").list();
        s.getTransaction().commit();
        s.close();
        return list;
    }

    @Override
    public List<Faktury_produkty> wyszukajFaktury_produkty(String id_faktury) {
        List<Faktury_produkty> list = new ArrayList<>();
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        //Sprawdzanie czy dane sa wprowadzone
        String w1,w2,w3,w4;
        if(id_faktury=="") {
            w1 = "fp.id_faktury IS NOT NULL";
        } else w1 = "fp.id_faktury='"+id_faktury+"'";



        list = s.createQuery("from Faktury_produkty fp where "+w1+"").list();
        s.getTransaction().commit();
        s.close();
        return list;
    }

    @Override
    public void usunFaktury_produkty(Integer id) {
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Faktury_produkty faktury_produkty = (Faktury_produkty) s.load(Faktury_produkty.class, id);
        s.delete(faktury_produkty);
        s.getTransaction().commit();
        s.close();
    }

    @Override
    public void zaktualizujFaktury_produkty(Faktury_produkty faktury_produkty) {
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.update(faktury_produkty);
        s.getTransaction().commit();
        s.close();
    }
}
