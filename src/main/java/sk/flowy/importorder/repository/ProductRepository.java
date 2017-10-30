package sk.flowy.importorder.repository;

import org.springframework.data.repository.CrudRepository;
import sk.flowy.importorder.model.Ean;
import sk.flowy.importorder.model.Product;

import java.util.List;


public interface ProductRepository extends CrudRepository<Product, Long> {

    Product findByEans(List<Ean> eans);

}