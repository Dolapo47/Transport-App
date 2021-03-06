package transport.app.demo.model.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_STAFF;

    public String getAuthority() {
        return name();
    }
}
