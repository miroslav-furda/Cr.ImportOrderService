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
public class CsvFile implements Serializable {

    private static final long serialVersionUID = -1562764096877960975L;

    private Integer ico;
    private String name;

}
