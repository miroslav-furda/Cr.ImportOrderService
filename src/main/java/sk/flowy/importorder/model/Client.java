package sk.flowy.importorder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return id != null ? id.equals(client.id) : client.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
