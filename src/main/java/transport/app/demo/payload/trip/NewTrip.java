package transport.app.demo.payload.trip;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class NewTrip {

    @NotBlank(message = "Leave is required")
    private String leave;

    @NotBlank(message = "Arrival is required")
    private String arrival;

    @NotNull(message = "Include a date for the trip")

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date leaveDate;

    @NotNull(message = "include a price for the trip")
    private Double price;

    private boolean isComplete = false;

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
