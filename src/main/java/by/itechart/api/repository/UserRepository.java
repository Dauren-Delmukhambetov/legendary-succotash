package by.itechart.api.repository;

import by.itechart.api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BasicRepository<User, Long> {
    Optional<User> findById(Long id);

    Page<User> findAll(Pageable pageable);

    Page<User> findByDeletedAtIsNull(Pageable pageable);

    Optional<User> findByEmail(String email);
}
