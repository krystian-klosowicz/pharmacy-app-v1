package entity;

import javax.persistence.*;

@Entity
@Table(name="Faktury_produkty")
public class Faktury_produkty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_faktury_produkty")
    private int id_faktury_produkty;

    @Column(name="id_faktury", length=11)
    private int id_faktury;

    @Column(name="id_produktu", length=11)
    private int id_produktu;

    @Column(name="nazwa_produktu", length=100)
    private String nazwa_produktu;

    @Column(name="cena_zakupu", length=11)
    private double cena_zakupu;

    @Column(name="ilosc_sztuk", length=11)
    private int ilosc_sztuk;

    @ManyToOne
    @JoinColumn(name = "faktury_id_faktury")
    private Faktury faktury;

    @ManyToOne
    @JoinColumn(name = "produkty_id_produktu")
    private Produkty produkty;

    public Produkty getProdukty() {
        return produkty;
    }

    public void setProdukty(Produkty produkty) {
        this.produkty = produkty;
    }

    public Faktury getFaktury() {
        return faktury;
    }

    public void setFaktury(Faktury faktury) {
        this.faktury = faktury;
    }

    public Faktury_produkty() {
    }

    public Faktury_produkty(int id_faktury_produkty, int id_faktury, int id_produktu, String nazwa_produktu, double cena_zakupu, int ilosc_sztuk, Faktury faktury, Produkty produkty) {
        this.id_faktury_produkty = id_faktury_produkty;
        this.id_faktury = id_faktury;
        this.id_produktu = id_produktu;
        this.nazwa_produktu = nazwa_produktu;
        this.cena_zakupu = cena_zakupu;
        this.ilosc_sztuk = ilosc_sztuk;
        this.faktury = faktury;
        this.produkty = produkty;
    }

    public int getId_faktury_produkty() {
        return id_faktury_produkty;
    }

    public void setId_faktury_produkty(int id_faktury_produkty) {
        this.id_faktury_produkty = id_faktury_produkty;
    }

    public int getId_faktury() {
        return id_faktury;
    }

    public void setId_faktury(int id_faktury) {
        this.id_faktury = id_faktury;
    }

    public String getNazwa_produktu() {
        return nazwa_produktu;
    }

    public void setNazwa_produktu(String nazwa_produktu) {
        this.nazwa_produktu = nazwa_produktu;
    }

    public int getId_produktu() {
        return id_produktu;
    }

    public void setId_produktu(int id_produktu) {
        this.id_produktu = id_produktu;
    }

    public double getCena_zakupu() {
        return cena_zakupu;
    }

    public void setCena_zakupu(double cena_zakupu) {
        this.cena_zakupu = cena_zakupu;
    }

    public int getIlosc_sztuk() {
        return ilosc_sztuk;
    }

    public void setIlosc_sztuk(int ilosc_sztuk) {
        this.ilosc_sztuk = ilosc_sztuk;
    }

}
