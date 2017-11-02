package sk.flowy.importorder.repository;


import org.springframework.data.repository.CrudRepository;
import sk.flowy.importorder.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {


}
