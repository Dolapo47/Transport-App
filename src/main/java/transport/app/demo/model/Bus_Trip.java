package transport.app.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "bus_trip")
public class Bus_Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
