package by.itechart.api.service;

import by.itechart.api.entity.User;
import by.itechart.api.exception.UserNotFoundException;
import by.itechart.api.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static java.lang.String.format;

@Data
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        return user.map(UserPrincipal::new)
                .orElseThrow(() -> new UserNotFoundException(format("User with email %s is not found", username)));
    }
}
