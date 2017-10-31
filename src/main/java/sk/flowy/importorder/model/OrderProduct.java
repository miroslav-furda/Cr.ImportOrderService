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
 * Product entity representing order product table from database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "objednavky_produkt")
public class OrderProduct implements Serializable{

    private static final long serialVersionUID = -876200228310806325L;

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated order product ID")
    private Long id;

    @Column(name = "objednavka_id")
    private Integer orderId;

    @Column(name = "produkt_id")
    private Integer productId;

    @Column(name = "pocet")
    private Integer count;

    @Column(name = "cena")
    private Double prise;

    @Column(name = "potvrdene")
    private Integer confirmation = 1;

    @Column(name = "created_at")
    private Timestamp created;

    @Column(name = "updated_at")
    private Timestamp updated;
}
