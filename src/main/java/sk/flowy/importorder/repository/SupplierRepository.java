package sk.flowy.importorder.repository;


import org.springframework.data.repository.CrudRepository;
import sk.flowy.importorder.model.Supplier;

import java.util.List;

public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    List<Supplier> findByIco(Integer ico);
}
