package sk.flowy.importorder.repository;


import org.springframework.data.repository.CrudRepository;
import sk.flowy.importorder.model.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {

    List<Client> findByIco(Integer ico);
}
