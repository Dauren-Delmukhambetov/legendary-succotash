package by.itechart.api.repository;

import by.itechart.api.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BasicRepository<User, Long>{

}
