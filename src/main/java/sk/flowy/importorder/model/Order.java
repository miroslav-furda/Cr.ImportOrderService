package sk.flowy.importorder.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Product entity representing order table from database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "objednavky")
public class Order implements Serializable {

    private static final long serialVersionUID = -8513852013980470146L;

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated order ID")
    private Long id;

    @Column(name = "nazov")
    private String name;

    @Column(name = "id_klient")
    private Integer clientId;

    @Column(name = "id_dodavatel")
    private Integer supplierId;

    @Column(name = "potvrdenie")
    private Integer confirmation = 0;

    @Column(name = "created_at")
    private Timestamp created;

    @Column(name = "updated_at")
    private Timestamp updated;

    @Column(name = "datum_dodania")
    private Timestamp dateOfOrder;

    @Column(name = "custom")
    private Integer custom = 0;
}
