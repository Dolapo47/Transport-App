package transport.app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import transport.app.demo.service.MapValidationErrorService;
import transport.app.demo.service.AuthService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AuthService authService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

}
