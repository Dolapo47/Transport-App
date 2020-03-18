package transport.app.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import transport.app.demo.model.Bus;

@Repository
public interface BusRepository extends CrudRepository<Bus, Long> {
}
