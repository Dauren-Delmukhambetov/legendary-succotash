package by.itechart.api.service;

import by.itechart.api.entity.User;
import by.itechart.api.exception.UserNotFoundException;
import by.itechart.api.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO create custom method with user details?
        Optional<User> user = userRepository.findByEmail(username);
        user.orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserPrincipal(user.get());
        //not work correctly
    }
}
