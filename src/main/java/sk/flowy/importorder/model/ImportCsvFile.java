package sk.flowy.importorder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportCsvFile implements Serializable {

    private static final long serialVersionUID = -1562764096877960975L;

    //Client
    private Integer clientIco;
    //Supplier
    private Integer supplierIco;
    //Product
    private String ean;
    private Integer productCount;
    private Double price;
    //Order name
    private String orderName;
}
