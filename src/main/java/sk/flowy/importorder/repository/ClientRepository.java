package sk.flowy.importorder.repository;

import org.springframework.data.repository.CrudRepository;
import sk.flowy.importorder.model.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

    Client findByIco(Integer ico);
}
