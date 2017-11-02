package sk.flowy.importorder.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
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

    @Column(name = "ico")
    private Integer ico;

    @ManyToMany(mappedBy = "clients")
    private List<Supplier> suppliers;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;
}
