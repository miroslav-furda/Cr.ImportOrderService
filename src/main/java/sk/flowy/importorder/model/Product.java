package sk.flowy.importorder.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
@Table(name = "produkt")
public class Product implements Serializable {

    private static final long serialVersionUID = -1678839865508033288L;
    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated product ID")
    private Long id;

    @Column(name = "nazov")
    @NotNull
    @NotEmpty
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

    @OneToMany(mappedBy = "product")
    private List<Ean> eans;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id != null ? id.equals(product.id) : product.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Product(String name) {
        this.name = name;
    }
}