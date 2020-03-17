package transport.app.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import transport.app.demo.model.Trip;

@Repository
public interface TripRepository extends CrudRepository<Trip, Long> {

}
