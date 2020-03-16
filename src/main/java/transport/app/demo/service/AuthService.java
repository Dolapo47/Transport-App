package transport.app.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import transport.app.demo.exceptions.AppException;
import transport.app.demo.exceptions.UserNotFoundException;
import transport.app.demo.model.user.EmailVerificationStatus;
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

    @Value("${server.port}")
    private String port;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, BCryptPasswordEncoder bCryptPasswordEncoder, EmailSender emailSender) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailSender = emailSender;
    }

    public void saveUser(User newUser){
        User foundUser = userRepository.findByUsername(newUser.getUsername());
        if(foundUser != null){
            throw new AppException("Email or Username already exists", HttpStatus.CONFLICT);
        }
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setRoles(Collections.singletonList(Role.ROLE_STAFF));
        String token = jwtTokenProvider.generateToken(newUser.getUsername());
        newUser.setEmailVerificationToken(token);
        userRepository.save(newUser);

        String url = "http://localhost:"+ port + "/api/auth/verifyEmail/" + token;
        String message =
                "Hey" + newUser.getUsername() + ",\n" +
                        "You just created an account with The Transport-App \n" +
                        "You are required to use the following link to verify your account\n" + url + "\n"  +
                        " –Please disregard if it wasn't you";
        emailSender.sendEmail(newUser.getUsername(), "Transport-App Registration Verification", message);
    }

    public void signInUser(String username, String password, HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
    if(authentication == null){
        throw new UserNotFoundException("User not found");
    }
    SecurityContextHolder.getContext().setAuthentication(authentication);
    User user = userRepository.findByUsername(username);
        if (user.getEmailVerificationStatus().equals(EmailVerificationStatus.UNVERIFIED) ) {
            throw new UserNotFoundException("You haven't verified your account yet");
        }
    String token = jwtTokenProvider.createToken(user.getId(), username, user.getRoles(), EXPIRATION_TIME);
    response.addHeader("token", token);
    }

    public void verifyUser(String token){
        User user = userRepository.findByEmailVerificationToken(token);
        if(user == null){
            throw new UserNotFoundException("Unable to verify user");
        }
        System.out.println(user);
        user.setEmailVerificationStatus(EmailVerificationStatus.VERIFIED);
        userRepository.save(user);
    }
}
