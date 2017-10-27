package sk.flowy.importorder.repository;

import org.springframework.data.repository.CrudRepository;
import sk.flowy.importorder.model.Ean;

/**
 * Simple {@link CrudRepository} or communicating with database.
 */
public interface EanRepository extends CrudRepository<Ean, Long> {

    /**
     * FInds Ean object based on ean value.
     *
     * @param value ean String value
     * @return ean object.
     */
    Ean findByValue(String value);
}
