package sk.flowy.importorder.repository;

import org.springframework.data.repository.CrudRepository;
import sk.flowy.importorder.model.Product;


public interface ProductRepository extends CrudRepository<Product, Long> {

}