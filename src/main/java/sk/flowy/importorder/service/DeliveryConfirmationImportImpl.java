package sk.flowy.importorder.service;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.stream.Stream;


@Log4j
@Service
public class DeliveryConfirmationImportImpl implements DeliveryConfirmationImport {

    @Override
    public Stream<Path> readFile(String file) {
        return null;
    }
}
