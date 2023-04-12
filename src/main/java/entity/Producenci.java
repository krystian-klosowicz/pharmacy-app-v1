package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="Producenci")
public class Producenci {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_producenta", length=11)
    private int id_producenta;

    @Column(name="NIP", length=10)
    private String NIP;

    @Column(name="nazwa_producenta", length=100)
    private String nazwa_producenta;

    @Column(name="adres", length=100)
    private String adres;

    @OneToMany(mappedBy = "producenci", orphanRemoval = true)
    private List<Produkty> produkty = new ArrayList<>();

    public List<Produkty> getProdukty() {
        return produkty;
    }

    public void setProdukty(List<Produkty> produkty) {
        this.produkty = produkty;
    }

    public Producenci() {
    }

    public Producenci(int id_producenta, String NIP, String nazwa_producenta, String adres) {
        this.id_producenta = id_producenta;
        this.NIP = NIP;
        this.nazwa_producenta = nazwa_producenta;
        this.adres = adres;
    }

    public int getId_producenta() {
        return id_producenta;
    }

    public void setId_producenta(int id_producenta) {
        this.id_producenta = id_producenta;
    }

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public String getNazwa_producenta() {
        return nazwa_producenta;
    }

    public void setNazwa_producenta(String nazwa_producenta) {
        this.nazwa_producenta = nazwa_producenta;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}
