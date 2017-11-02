package sk.flowy.importorder.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Client entity representing client table from database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "klient")
public class Client implements Serializable {

    private static final long serialVersionUID = 5320587843769173063L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nazov")
    private String name;

    @Column(name = "firma")
    private String company;

    @Column(name = "adresa")
    private String address;

    @Column(name = "ico")
    private Integer ico;

    @Column(name = "dic")
    private Integer dic;

    @Column(name = "ic_dph")
    private String icDph;

    @Column(name = "aktivny")
    private boolean active = true;

    @Column(name = "created_at")
    private Timestamp created;

    @Column(name = "updated_at")
    private Timestamp updated;

    @Column(name = "deleted_at")
    private Timestamp deleted;

    @ManyToMany(mappedBy = "clients")
    private List<Supplier> suppliers;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;
}
