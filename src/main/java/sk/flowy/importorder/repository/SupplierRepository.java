package sk.flowy.importorder.repository;


import org.springframework.data.repository.CrudRepository;
import sk.flowy.importorder.model.Supplier;

public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    Supplier findByNameAndIco(String name, Integer ico);
}
