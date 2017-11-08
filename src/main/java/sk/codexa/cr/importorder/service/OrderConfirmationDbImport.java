package sk.codexa.cr.importorder.service;

/**
 * This interface represent confirmation import order.
 */
public interface OrderConfirmationDbImport {

    /**
     * Represent import csv file.
     *
     * @param file input csv
     */
    void importFile(String file);
}
