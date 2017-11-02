package sk.flowy.importorder.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Ean entity representing ean table from database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "ean")
public class Ean implements Serializable {

    private static final long serialVersionUID = 5825148543795158789L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "typ")
    private String type;

    @Column(name = "hodnota")
    private String value;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @ManyToOne
    @JoinColumn(name = "id_produkt")
    private Product product;
}
