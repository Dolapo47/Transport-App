package transport.app.demo.security;

import java.util.Date;

public class SecurityConstant {
    public static final String SIGN_UP_URL = "/api/auth/**";
    public static final String ADMIN_URL = "/api/admin/**";
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 300_000_000;

    public SecurityConstant() {
        throw new IllegalStateException("Cannot create instance for this class");
    }
}
