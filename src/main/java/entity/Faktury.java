package entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Faktury")
public class Faktury {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_faktury", length=11)
    private int id_faktury;

    @Column(name="id_klienta", length=11)
    private int id_klienta;

    @Column(name="data_wystawienia_faktury")
    private Date data_wystawienia_faktury;

    @ManyToOne
    @JoinColumn(name = "klienci_id_klienta")
    private Klienci klienci;

    public Klienci getKlienci() {
        return klienci;
    }

    public void setKlienci(Klienci klienci) {
        this.klienci = klienci;
    }

    public Faktury() {
    }

    public Faktury(int id_faktury, int id_klienta, Date data_wystawienia_faktury, Klienci klienci) {
        this.id_faktury = id_faktury;
        this.id_klienta = id_klienta;
        this.data_wystawienia_faktury = data_wystawienia_faktury;
        this.klienci = klienci;
    }

    public int getId_faktury() {
        return id_faktury;
    }

    public void setId_faktury(int id_faktury) {
        this.id_faktury = id_faktury;
    }

    public int getId_klienta() {
        return id_klienta;
    }

    public void setId_klienta(int id_klienta) {
        this.id_klienta = id_klienta;
    }

    public Date getData_wystawienia_faktury() {
        return data_wystawienia_faktury;
    }

    public void setData_wystawienia_faktury(Date data_wystawienia_faktury) {
        this.data_wystawienia_faktury = data_wystawienia_faktury;
    }
}
