package sk.flowy.importorder.service;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * This interface represent confirmation import delivery.
 */
public interface DeliveryConfirmationImport {

    /**
     * Represent read import csv file.
     * @param file input csv file
     * @return Stream path file
     */
    Stream<Path> readFile(String file);
}
