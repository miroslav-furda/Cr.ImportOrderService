package sk.flowy.importorder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Product entity representing order product table from database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "objednavky_produkt")
public class OrderProduct implements Serializable {

    private static final long serialVersionUID = -876200228310806325L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "pocet")
    private Integer count;

    @Column(name = "cena")
    private Double price;

    @Column(name = "potvrdene")
    private boolean confirmation;

    @Column(name = "created_at")
    private Timestamp created;

    @Column(name = "updated_at")
    private Timestamp updated;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "objednavka_id")
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "produkt_id")
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderProduct that = (OrderProduct) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
