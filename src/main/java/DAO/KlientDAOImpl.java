package DAO;

import entity.HibernateUtil;
import entity.Klienci;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class KlientDAOImpl implements KlientDAO {

    @Override
    public void dodajKlienta(Klienci klient) {
        Session s= (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.save(klient);
        s.getTransaction().commit();
        s.close();
    }

    @Override
    public List<Klienci> listaKlienci() {
        List<Klienci> list = new ArrayList<>();
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        list = s.createQuery("from Klienci").list();
        s.getTransaction().commit();
        s.close();
        return list;
    }

    @Override
    public List<Klienci> wyszukajKlienci(String id, String imie, String nazwisko,String pesel,String adres) {
        List<Klienci> list = new ArrayList<>();
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        //Sprawdzanie czy dane sa wprowadzone
        String w1,w2,w3,w4,w5;
        if(id=="") {
            w1 = "k.id_klienta IS NOT NULL";
        } else w1 = "k.id_klienta='"+id+"'";

        if(imie=="") {
            w2 = "k.imie IS NOT NULL";
        } else w2 = "k.imie='"+imie+"'";

        if(nazwisko=="") {
            w3 = "k.nazwisko IS NOT NULL";
        } else w3 = "k.nazwisko='"+nazwisko+"'";

        if(pesel=="") {
            w4 = "k.pesel IS NOT NULL";
        } else w4 = "k.pesel='"+pesel+"'";

        if(adres=="") {
            w5 = "k.adres IS NOT NULL";
        } else w5 = "k.adres='"+adres+"'";

        list = s.createQuery("from Klienci k where "+w1+" AND "+w2+" AND "+w3+" AND "+w4+" AND "+w5+"").list();
        s.getTransaction().commit();
        s.close();
        return list;
    }

    @Override
    public void usunKlienta(Integer id) {
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Klienci klient = (Klienci) s.load(Klienci.class, id);
        s.delete(klient);
        s.getTransaction().commit();
        s.close();
    }

    @Override
    public void zaktualizujKlienta(Klienci klient) {
        Session s = (Session) HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.update(klient);
        s.getTransaction().commit();
        s.close();
    }
}
