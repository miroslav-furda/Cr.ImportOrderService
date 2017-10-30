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
 * Client entity representing client table from database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "klient")
public class Client implements Serializable{

    private static final long serialVersionUID = 5320587843769173063L;

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated ean ID")
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
    private Integer active = 1;

    @Column(name = "created_at")
    private Timestamp created;

    @Column(name = "updated_at")
    private Timestamp updated;

    @Column(name = "deleted_at")
    private Timestamp deleted;
}
