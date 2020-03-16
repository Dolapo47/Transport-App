package transport.app.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import transport.app.demo.exceptions.UserNotFoundException;
import transport.app.demo.exceptions.UsernameAlreadyExistsException;
import transport.app.demo.model.user.Role;
import transport.app.demo.model.user.User;
import transport.app.demo.repository.UserRepository;
import transport.app.demo.security.JwtTokenProvider;
import transport.app.demo.util.EmailSender;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

import static transport.app.demo.security.SecurityConstant.EXPIRATION_TIME;

@Service
public class AuthService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private EmailSender emailSender;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, BCryptPasswordEncoder bCryptPasswordEncoder, EmailSender emailSender) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailSender = emailSender;
    }

    public User saveUser(User newUser){
        User foundUser = userRepository.findByUsername(newUser.getUsername());
        if(foundUser != null){
            throw new UsernameAlreadyExistsException("Username"+newUser.getUsername()+"already exists");
        }
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setRoles(Collections.singletonList(Role.ROLE_STAFF));
        String token = jwtTokenProvider.generateToken(newUser.getUsername());
        return userRepository.save(newUser);
    }

    public void signInUser(String username, String password, HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
    if(authentication == null){
        throw new UserNotFoundException("User not found");
    }
        SecurityContextHolder.getContext().setAuthentication(authentication);
    User user = userRepository.findByUsername(username);
    String token = jwtTokenProvider.createToken(user.getId(), username, user.getRoles(), EXPIRATION_TIME);
    response.addHeader("token", token);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public User createAdmin(User newUser){
//        User foundUser = userRepository.findByUsername(newUser.getUsername());
////        User adminUser = userRepository.findByUsername(username);
//
//        if(foundUser == null){
//            throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' does not exist in the application");
//        }
//
//        foundUser.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
//       //System.out.println(foundUser.getRoles());
//
//
//       //return userRepository.save(foundUser);
//        return foundUser;
//    }
}
