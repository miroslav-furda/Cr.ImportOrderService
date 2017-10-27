package sk.flowy.importorder.service;

import com.opencsv.CSVReader;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;


@Log4j
@Service
public class DeliveryConfirmationImportImpl implements DeliveryConfirmationImport {

    public DeliveryConfirmationImportImpl() {
    }

    @Override
    public void readFile(String file) {
        try {
            CSVReader csvFile = new CSVReader(new FileReader(file));
            List<String[]> data = csvFile.readAll();
            log.info(data);
        } catch (IOException e) {
            log.error("Input file is not csv file ", e);
        }
    }
}
