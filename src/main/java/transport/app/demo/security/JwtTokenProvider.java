package transport.app.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import transport.app.demo.model.user.Role;
import transport.app.demo.service.UserDetailsServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static transport.app.demo.security.SecurityConstant.EXPIRATION_TIME;
import static transport.app.demo.security.SecurityConstant.SECRET;

@Component
public class JwtTokenProvider {
    //generate the token
    //validate the token
    //get the user id from the token
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public String createToken(Long id, String email, List<Role> roles, long validityInMilliseconds) {

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("userId", id);
        claims.put("role", roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String generateToken(String username) {

        Claims claims = Jwts.claims().setSubject(username);

        Date now = new Date();
        long validityInMilliseconds = EXPIRATION_TIME;
        Date validity = new Date(validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch(SignatureException ex){
            System.out.println("Invalid jwt signature");
        }catch(MalformedJwtException ex){
            System.out.println("Invalid JWT token");
        }catch(ExpiredJwtException ex){
            System.out.println("Expired JWT token");
        }catch(UnsupportedJwtException ex){
            System.out.println("Unsupported JWT exception");
        }catch(IllegalArgumentException ex){
            System.out.println("JWT claims is empty");
        }
        return false;
    }

    public String getUsername(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
//        String id = (String)claims.get("id");
        return claims.getSubject();
    }

}

