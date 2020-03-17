package transport.app.demo.payload.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PasswordReset {

    @NotBlank
    @NotNull
    private String username;


    public PasswordReset(@NotBlank @NotNull String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static class LoginRequest {

        @NotBlank(message = "Username cannot be blank")
        private String username;
        @NotBlank(message = "Password cannot be blank")
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
