package transport.app.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import transport.app.demo.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "bus")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Brand of bus is required")
    private String brand;

    @NotNull(message = "Capacity of bus is required")
    private Integer capacity;

    @NotBlank(message = "Plate number of bus is required")
    private String plateNo;

    private boolean onTrip = false;

    @OneToMany(cascade = CascadeType.REFRESH,  mappedBy = "bus", orphanRemoval = true)
    @JsonIgnore
    private List <Trip> trip = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Bus() {
    }

    public List<Trip> getTrip() {
        return trip;
    }

    public void setTrip(List<Trip> trip) {
        this.trip = trip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public boolean isOnTrip() {
        return onTrip;
    }

    public void setOnTrip(boolean onTrip) {
        this.onTrip = onTrip;
    }

//    public List<Trip> getTrip() {
//        return trip;
//    }
//
//    public void setTrip(List<Trip> trip) {
//        this.trip = trip;
//    }
}
