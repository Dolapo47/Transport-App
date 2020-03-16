package transport.app.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import transport.app.demo.model.user.User;
import transport.app.demo.payload.LoginRequest;
import transport.app.demo.payload.auth.SignUpRequest;
import transport.app.demo.responses.Response;
import transport.app.demo.service.MapValidationErrorService;
import transport.app.demo.service.AuthService;
import transport.app.demo.validator.UserValidator;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    private MapValidationErrorService mapValidationErrorService;
    private ModelMapper modelMapper;

    @Autowired
    public AuthController(AuthService authService, MapValidationErrorService mapValidationErrorService, ModelMapper modelMapper) {
        this.authService = authService;
        this.mapValidationErrorService = mapValidationErrorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<?>authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                             BindingResult result, HttpServletResponse response){
        ResponseEntity<?>errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;
        authService.signInUser(loginRequest.getUsername(), loginRequest.getPassword(), response);
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUSer(@Valid @RequestBody SignUpRequest signUpRequest){
        authService.saveUser(modelMapper.map(signUpRequest, User.class));
        Response<String> response = new Response<>(HttpStatus.CREATED);
        response.setMessage("You have successfully signed up with Transport-App");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/verifyEmail/{token}")
    public ResponseEntity<Response<String>> verifyUser(@PathVariable String token){
        authService.verifyUser(token);
        Response<String> response = new Response<>(HttpStatus.ACCEPTED);
        response.setMessage("You are now a verified user of Transport-App");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
