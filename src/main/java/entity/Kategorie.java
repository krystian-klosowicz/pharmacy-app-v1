package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Kategorie")
public class Kategorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_kategorii", length=11)
    private int id_kategorii;

    @Column(name="nazwa_kategorii", length=100)
    private String nazwa_kategorii;

    @OneToMany(mappedBy = "kategorie", orphanRemoval = true)
    private List<Produkty> produkty = new ArrayList<>();

    public List<Produkty> getProdukty() {
        return produkty;
    }

    public void setProdukty(List<Produkty> produkty) {
        this.produkty = produkty;
    }

    public Kategorie() {
    }

    public Kategorie(int id_kategorii, String nazwa_kategorii) {
        this.id_kategorii = id_kategorii;
        this.nazwa_kategorii = nazwa_kategorii;
    }

    public int getId_kategorii() {
        return id_kategorii;
    }

    public void setId_kategorii(int id_kategorii) {
        this.id_kategorii = id_kategorii;
    }

    public String getNazwa_kategorii() {
        return nazwa_kategorii;
    }

    public void setNazwa_kategorii(String nazwa_kategorii) {
        this.nazwa_kategorii = nazwa_kategorii;
    }
}
