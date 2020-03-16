package transport.app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transport.app.demo.model.user.User;
import transport.app.demo.payload.JwtLoginSuccessResponse;
import transport.app.demo.payload.LoginRequest;
import transport.app.demo.security.JwtTokenProvider;
import transport.app.demo.service.MapValidationErrorService;
import transport.app.demo.service.AuthService;
import transport.app.demo.validator.UserValidator;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;
import static transport.app.demo.security.SecurityConstant.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @Autowired
    private AuthenticationManager authenticationmanager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?>authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
        ResponseEntity<?>errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        Authentication authentication = authenticationmanager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        System.out.println("hi");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX+jwtTokenProvider.generateToken(loginRequest.getUsername());
        return ResponseEntity.ok(new JwtLoginSuccessResponse(true, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUSer(@Valid @RequestBody User user, BindingResult result){
        //validate passwords match

        userValidator.validate(user, result);
        ResponseEntity<?>errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        User newUser = authService.saveUser(user);
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }
}
