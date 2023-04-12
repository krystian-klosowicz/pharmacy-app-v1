package DAO;

import entity.HibernateUtil;
import entity.Kategorie;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class KategorieDAOImpl implements KategorieDAO{
    @Override
    public List<Kategorie> listaKategorie() {
        List<Kategorie> list = new ArrayList<>();
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        list = s.createQuery("from Kategorie").list();
        s.getTransaction().commit();
        s.close();
        return list;
    }
}
