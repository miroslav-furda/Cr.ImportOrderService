package sk.flowy.importorder.service;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface DeliveryConfirmationImport {

    Stream<Path> readCsvFile(String file);
}
