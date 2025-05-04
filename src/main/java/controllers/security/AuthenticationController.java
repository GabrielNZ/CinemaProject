package controllers.security;

import entities.DTO.LoginDto;
import entities.DTO.RegisterDto;
import entities.User;
import entities.enums.Roles;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import repository.UserRepository;
import services.security.TokenService;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto loginDto) {
        String token = tokenService.generateToken((User) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password())).getPrincipal());
        return ResponseEntity.ok().body(token);
    }
    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto registerDto) {
        if(userRepository.findByUsername(registerDto.username()) != null) return ResponseEntity.ok().body("User already exists");
        User user = new User(registerDto.username(),new BCryptPasswordEncoder().encode(registerDto.password()),registerDto.email(),registerDto.phone());
        user.setRole(Roles.USER);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
