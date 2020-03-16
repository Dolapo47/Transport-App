package transport.app.demo.payload.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignUpRequest {

    @NotBlank
    @Email
    private String username;

    @NotBlank
    private String fullname;

    @NotBlank
    @NotNull
    @Size(min = 6)
    private String password;

    @NotBlank
    @NotNull
    private String confirmPassword;

    public SignUpRequest(@NotBlank @Email String username, @NotBlank String fullname, @NotBlank @NotNull @Size(min = 6) String password, @NotBlank @NotNull String confirmPassword) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
