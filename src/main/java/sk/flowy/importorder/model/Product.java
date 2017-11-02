package sk.flowy.importorder.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Product entity representing produkt table from database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "produkt")
public class Product implements Serializable {

    private static final long serialVersionUID = -1678839865508033288L;
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nazov")
    private String name;

    @Column(name = "nazov_clear")
    private String nameClear;

    @Column(name = "aktivny")
    private Integer active = 1;

    private Boolean weighted;

    @Column(name = "nonBlockable")
    private Integer nonBlockable;

    @Column(name = "created_at")
    private Timestamp created;

    @Column(name = "updated_at")
    private Timestamp updated;

    @Column(name = "deleted_at")
    private Timestamp deleted;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ean> eans;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Supplier> suppliers;

    @ManyToMany(mappedBy = "ordersProducts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts;
}