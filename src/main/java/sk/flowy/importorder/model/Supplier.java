package sk.flowy.importorder.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "dodavatel")
public class Supplier implements Serializable {

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "dodavatel_klient",
            joinColumns = @JoinColumn(name = "id_dodavatel", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_klient", referencedColumnName = "id"))
    private List<Client> clients;

    @OneToMany(mappedBy = "supplier")
    private List<Order> orders;
}
