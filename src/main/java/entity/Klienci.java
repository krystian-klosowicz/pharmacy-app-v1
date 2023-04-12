package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Klienci")
public class Klienci {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_klienta", length=11)
    private int id_klienta;

    @Column(name="imie", length=100)
    private String imie;

    @Column(name="nazwisko", length=100)
    private String nazwisko;

    @Column(name="pesel", length=11)
    private String pesel;


    @Column(name="adres", length=100)
    private String adres;

    @OneToMany(mappedBy = "klienci", orphanRemoval = true)
    private List<Faktury> faktury = new ArrayList<>();

    public List<Faktury> getFaktury() {
        return faktury;
    }

    public void setFakturies(List<Faktury> fakturies) {
        this.faktury = faktury;
    }

    public Klienci() {
    }

    public Klienci(int id_klienta, String imie, String nazwisko, String pesel, String adres) {
        this.id_klienta = id_klienta;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.adres = adres;
    }

    public int getId_klienta() {
        return id_klienta;
    }

    public void setId_klienta(int id_klienta) {
        this.id_klienta = id_klienta;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setFaktury(List<Faktury> faktury) {
        this.faktury = faktury;
    }
}
