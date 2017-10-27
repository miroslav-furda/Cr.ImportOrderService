package sk.flowy.importorder.service;

/**
 * This interface represent confirmation import delivery.
 */
public interface DeliveryConfirmationImport {

    /**
     * Represent read import csv file.
     *
     * @param file input csv
     */
    void readFile(String file);
}
