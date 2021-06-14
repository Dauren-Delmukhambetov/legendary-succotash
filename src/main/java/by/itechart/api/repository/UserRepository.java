package by.itechart.api.repository;

import by.itechart.api.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BasicRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}
