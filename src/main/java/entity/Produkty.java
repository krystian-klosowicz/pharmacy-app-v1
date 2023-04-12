package entity;

import javax.persistence.*;

@Entity
@Table(name="Produkty")
public class Produkty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_produktu", length=11)
    private int id_produktu;

    @Column(name="id_producenta", length=11)
    private int id_producenta;

    @Column(name="id_kategorii", length=11)
    private int id_kategorii;


    @Column(name="nazwa_produktu", length=100)
    private String nazwa_produktu;

    @Column(name="cena", length=11)
    private double cena;

    @Column(name="ilosc_sztuk", length=11)
    private int ilosc_sztuk;

    @ManyToOne
    @JoinColumn(name ="producenci_id_producenta" )
    private Producenci producenci;

    @ManyToOne
    @JoinColumn(name = "kategorie_id_kategorii")
    private Kategorie kategorie;

    public Kategorie getKategorie() {
        return kategorie;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

    public Producenci getProducenci() {
        return producenci;
    }

    public void setProducenci(Producenci producenci) {
        this.producenci = producenci;
    }

    public Produkty() {
    }

    public Produkty(int id_produktu, int id_producenta, int id_kategorii, String nazwa_produktu, double cena, int ilosc_sztuk, Producenci producenci, Kategorie kategorie) {
        this.id_produktu = id_produktu;
        this.id_producenta = id_producenta;
        this.id_kategorii = id_kategorii;
        this.nazwa_produktu = nazwa_produktu;
        this.cena = cena;
        this.ilosc_sztuk = ilosc_sztuk;
        this.producenci = producenci;
        this.kategorie = kategorie;
    }



    public int getId_produktu() {
        return id_produktu;
    }

    public void setId_produktu(int id_produktu) {
        this.id_produktu = id_produktu;
    }

    public int getId_producenta() {
        return id_producenta;
    }

    public void setId_producenta(int id_producenta) {
        this.id_producenta = id_producenta;
    }

    public int getId_kategorii() {
        return id_kategorii;
    }

    public void setId_kategorii(int id_kategorii) {
        this.id_kategorii = id_kategorii;
    }

    public String getNazwa_produktu() {
        return nazwa_produktu;
    }

    public void setNazwa_produktu(String nazwa_produktu) {
        this.nazwa_produktu = nazwa_produktu;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getIlosc_sztuk() {
        return ilosc_sztuk;
    }

    public void setIlosc_sztuk(int ilosc_sztuk) {
        this.ilosc_sztuk = ilosc_sztuk;
    }
}
