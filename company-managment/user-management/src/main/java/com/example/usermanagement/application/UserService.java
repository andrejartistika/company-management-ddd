package com.example.usermanagement.application;

import com.example.usermanagement.domain.events.UserCreated;
import com.example.usermanagement.domain.model.Role;
import com.example.usermanagement.domain.model.User;
import com.example.usermanagement.domain.model.UserId;
import com.example.usermanagement.domain.repository.UsersRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
//import com.example.usermanagement.domain.model.Role;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
//import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UsersRepository usersRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    UserService(UsersRepository usersRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.usersRepository = usersRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public User getAuthenticatedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return (User) auth.getPrincipal();
        }
        else {
            throw new IllegalArgumentException();
        }
    }
    @Transactional
    public void register(@NonNull  User user){
        System.out.println(user.getId() + " " +  user.getCreatedOn());
        applicationEventPublisher.publishEvent(new UserCreated(user.getId(), user.getCreatedOn(), user.getRole().getRoleName()));
        this.usersRepository.save(user);
    }

    public boolean checkAuth(String username, String password){
        User user = this.findByUserName(username).get();
        System.out.println(user);
        return user.authenticate(password);
    }

    @NonNull
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @NonNull
    public Optional<User> findById(@NonNull UserId userId) {
        Objects.requireNonNull(userId, "userId must not be null");
        return usersRepository.findById(userId);
    }

    @NonNull
    public Optional<User> findByUserName(@NonNull String username) {
        Objects.requireNonNull(username, "userId must not be null");
        return usersRepository.findByUserName(username);
    }

    @NonNull
    public List<User> findByRole(@NonNull Role role){
        return usersRepository.findByRole(role);
    }
}
