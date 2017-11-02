package sk.flowy.importorder.repository;


import org.springframework.data.repository.CrudRepository;
import sk.flowy.importorder.model.OrderProduct;

public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {

}
