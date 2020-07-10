package com.example.usermanagement.port;

import com.example.sharedkernel.domain.base.DomainObjectId;
import com.example.usermanagement.application.UserService;
import com.example.usermanagement.domain.model.Role;
import com.example.usermanagement.domain.model.RoleName;
import com.example.usermanagement.domain.model.User;
import com.example.usermanagement.domain.model.UserId;
import com.example.usermanagement.domain.repository.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserManagementController {
    private final UserService userService;
    public UserManagementController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/auth/login")
    public boolean login(@RequestParam String username, @RequestParam String password ){
        return this.userService.checkAuth(username, password);
    }

    @PostMapping("/auth/register")
    public void register(@RequestParam String username, @RequestParam String password, @RequestParam String role ){
        System.out.println(username + "  " +  password);
        Optional<User> exists = this.userService.findByUserName(username);
        if (!exists.isPresent()){
            Role role1 = new Role(role);
            User newUser = new User( new UserId(DomainObjectId.randomId(UserId.class).toString()), username, password, role1);
            this.userService.register(newUser);
        }
        else {
            throw new IllegalArgumentException("Username exists");
        }

    }

    @GetMapping("/members")
    public List<User> getAllMembers(){
        Role role = new Role(RoleName.MEMBER);
        return userService.findByRole(role);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") String userId) {
        UserId userId1 = new UserId(userId);
        return userService.findById(userId1).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


}
