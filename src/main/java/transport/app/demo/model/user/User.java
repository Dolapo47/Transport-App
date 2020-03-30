package transport.app.demo.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import transport.app.demo.model.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email(message = "Username needs to be an email")
    @NotBlank(message = "username is required")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Please enter your full name")
    private String fullname;

    @NotBlank(message = "Please enter your password")
    private String password;

    @Transient
    @JsonIgnore
    private String confirmPassword;

    @JsonIgnore
    private String emailVerificationToken;

    @JsonIgnore
    private EmailVerificationStatus emailVerificationStatus = EmailVerificationStatus.UNVERIFIED;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    List<Role> roles;

    @OneToMany(cascade = CascadeType.REFRESH,  mappedBy = "user", orphanRemoval = true)
//    @Column(name = "user_id")
    @JsonIgnore
    private List<Booking> booking = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH,  mappedBy = "user", orphanRemoval = true)
//    @Column(name = "user_id")
    @JsonIgnore
    private List<Trip> trip = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH,  mappedBy = "user", orphanRemoval = true)
//    @Column(name = "user_id")
    @JsonIgnore
    private List <Complaint> complaint = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private List <Bus> bus = new ArrayList<>();

    public User() {
    }

    public List<Trip> getTrip() {
        return trip;
    }

    public void setTrip(List<Trip> trip) {
        this.trip = trip;
    }

    public List<Bus> getBus() {
        return bus;
    }

    public void setBus(List<Bus> bus) {
        this.bus = bus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRoles() { return roles; }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public EmailVerificationStatus getEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    public void setEmailVerificationStatus(EmailVerificationStatus emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
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
}
