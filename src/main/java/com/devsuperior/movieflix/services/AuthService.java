package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.UserRepository;
import com.devsuperior.movieflix.services.exception.ForbiddenException;
import com.devsuperior.movieflix.services.exception.UnauthorazedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticated() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByEmail(username);
        } catch (Exception e) {
            throw new UnauthorazedException("Invalid user");
        }
    }

    public void validateSelfOrAdmin(Long userId) {
        var user = authenticated();
        if(!user.getId().equals(userId) && !user.hasHole("ROLE_MEMBER")) {
            throw new ForbiddenException("Access danied");
        }
    }

}
