package transport.app.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import transport.app.demo.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Leave is required")
    private String leave;

    @NotBlank(message = "Arrival is required")
    private String arrival;

    private Date leaveDate;

    private Double price;

    private boolean isComplete = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Bus bus;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "trip", orphanRemoval = true)
    @Column(name = "trip_id")
    @JsonIgnore
    private List<Booking> booking = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "trip", orphanRemoval = true)
    @Column(name = "trip_id")
    @JsonIgnore
    private List <Complaint> complaint = new ArrayList<>();

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name="bus_trip",
            joinColumns={@JoinColumn(name="trip_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="bus_id", referencedColumnName="id")})
    @JsonIgnore
    private List<Bus> newBus;

    public Trip() {
    }

    public List<Bus> getNewBus() {
        return newBus;
    }

    public void setNewBus(List<Bus> newBus) {
        this.newBus = newBus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeave() {
        return leave;
    }

    public void setFrom(String leave) {
        this.leave = leave;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public List<Booking> getBooking() {
        return booking;
    }

    public void setBooking(List<Booking> booking) {
        this.booking = booking;
    }

    public List<Complaint> getComplaint() {
        return complaint;
    }

    public void setComplaint(List<Complaint> complaint) {
        this.complaint = complaint;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
