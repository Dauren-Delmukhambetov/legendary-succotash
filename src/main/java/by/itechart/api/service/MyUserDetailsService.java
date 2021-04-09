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

@Data
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //FIXME find why stackoverflow here in debug mode
        Optional<User> user = userRepository.findByEmail(username);
        user.orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserPrincipal(user.get());
        //not work correctly
    }
}
