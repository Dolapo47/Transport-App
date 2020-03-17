package transport.app.demo.payload.trip;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Date;

public class NewTrip {

    @NotBlank(message = "Leave is required")
    private String leave;

    @NotBlank(message = "Arrival is required")
    private String arrival;

    private Date leaveDate;

    private boolean isComplete = false;

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
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

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
