package sk.flowy.importorder.service;

import java.io.File;

/**
 * This interface represent confirmation import order.
 */
public interface OrderConfirmationDbImport {

    /**
     * Represent import csv file.
     *
     * @param file input csv
     * @return if was saved into database
     */
    boolean importFile(File file);
}
