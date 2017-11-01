package sk.flowy.importorder.model;

import com.opencsv.bean.CsvBindByPosition;
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
    @CsvBindByPosition(position = 0)
    private Integer clientIco;
    //Supplier
    @CsvBindByPosition(position = 1)
    private Integer supplierIco;
    //Product
    @CsvBindByPosition(position = 2)
    private String ean;
    @CsvBindByPosition(position = 3)
    private Integer productCount;
    @CsvBindByPosition(position = 4)
    private Double price;
    //Order name
    @CsvBindByPosition(position = 5)
    private String orderName;
}
