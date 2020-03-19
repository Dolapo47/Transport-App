package transport.app.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import transport.app.demo.model.Bus;

import java.util.ArrayList;

@Repository
public interface BusRepository extends CrudRepository<Bus, Long> {
  ArrayList<Bus> findBusesByOnTrip(Boolean stat);
}
