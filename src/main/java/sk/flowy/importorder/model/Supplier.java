package sk.flowy.importorder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "dodavatel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier implements Serializable{

    private static final long serialVersionUID = -528414481097612186L;
    @GeneratedValue
    @Id
    private Long id;

    @Column(name = "nazov")
    private String name;

    @Column(name = "firma")
    private String company;

    @Column(name = "adresa")
    private String address;
    private Integer ico;
    private Integer dic;

    @Column(name = "ic_dph")
    private String icDph;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(name = "aktivny")
    private Integer active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "dodavatel_produkt", joinColumns = @JoinColumn(name = "id_dodavatel", referencedColumnName =
            "id"),
            inverseJoinColumns = @JoinColumn(name = "id_produkt", referencedColumnName = "id"))
    private List<Product> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supplier supplier = (Supplier) o;

        return id != null ? id.equals(supplier.id) : supplier.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
