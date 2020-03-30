package transport.app.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import transport.app.demo.model.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

}
