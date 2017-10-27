package sk.flowy.importorder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Table(name = "ean")
public class Ean implements Serializable {

    private static final long serialVersionUID = 5825148543795158789L;

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated ean ID")
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
    @JsonIgnore
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ean ean = (Ean) o;

        return id != null ? id.equals(ean.id) : ean.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
