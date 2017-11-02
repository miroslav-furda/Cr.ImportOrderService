package sk.flowy.importorder.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Product entity representing order table from database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "objednavky")
public class Order implements Serializable {

    private static final long serialVersionUID = -8513852013980470146L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nazov")
    private String name;

    @Column(name = "potvrdenie")
    private boolean confirmation;

    @Column(name = "created_at")
    private Timestamp created;

    @Column(name = "updated_at")
    private Timestamp updated;

    @Column(name = "datum_dodania")
    private Timestamp dateOfOrder;

    @Column(name = "custom")
    private boolean custom;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "objednavky_produkt",
            joinColumns = @JoinColumn(name = "objednavka_id", referencedColumnName ="id"),
            inverseJoinColumns = @JoinColumn(name = "produkt_id", referencedColumnName = "id"))
    private List<Product> ordersProducts;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_klient")
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dodavatel")
    private Supplier supplier;
}
