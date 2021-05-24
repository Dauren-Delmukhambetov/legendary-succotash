package by.itechart.api.repository;

import by.itechart.api.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//TODO choose more appropriate name and package level and check optional for testing
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JPATest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldNotFindUsersIfRepositoryIsEmpty() {
        Iterable<User> users = userRepository.findAll();
        assertThat(users).isEmpty();
    }

    @Test
    void shouldStoreUser() {
        var user = userRepository.save(createSimpleUserEntity());
        assertThat(user).hasFieldOrPropertyWithValue("firstName", "testName");
        assertThat(user).hasFieldOrPropertyWithValue("email", "test@test.com");
    }

    @Test
    void shouldFindAllUsers() {
        var user1 = createUserEntityWithParameters("testOne@test.com", "testNameOne");
        var user2 = createUserEntityWithParameters("testTwo@test.com", "testNameTwo");
        testEntityManager.persist(user1);
        testEntityManager.persist(user2);
        Iterable<User> users = userRepository.findAll();
        assertThat(users)
                .hasSize(2)
                .contains(user1, user2);
    }

    @Test
    void shouldFindUserById() {
        var user = createSimpleUserEntity();
        testEntityManager.persist(user);
        var foundUser = userRepository.findById(user.getId()).get();
        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    void shouldFindUserByEmail() {
        var user = createSimpleUserEntity();
        userRepository.save(user);
        var foundUser = userRepository.findByEmail(user.getEmail());
        assertThat(foundUser).get().hasFieldOrPropertyWithValue("email", "test@test.com");
    }

    @Test
    void shouldThrowErrorWhenCreateUserWithSameEmail() {
        var user = createSimpleUserEntity();
        var userWithSameEmail = createSimpleUserEntity();
        testEntityManager.persist(user);
        assertThatThrownBy(() -> testEntityManager.persist(userWithSameEmail))
                .isInstanceOf(PersistenceException.class);
    }

    @Test
    void shouldUpdateUserById() {
        var user = createSimpleUserEntity();
        testEntityManager.persist(user);
        var userToBeUpdated = userRepository.findById(user.getId()).get();
        userToBeUpdated.setEmail("testNew@test.com");
        userToBeUpdated.setUpdatedAt(LocalDateTime.now());
        userRepository.save(userToBeUpdated);
        var checkUpdatedUser = userRepository.findById(user.getId()).get();
        assertThat(checkUpdatedUser.getId()).isEqualTo(user.getId());
        assertThat(checkUpdatedUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void shouldDeleteUserById() {
        //TODO check does I need to test deletion or check deletedAt field
        var user1 = createUserEntityWithParameters("testOne@test.com", "testNameOne");
        var user2 = createUserEntityWithParameters("testTwo@test.com", "testNameTwo");
        var user3 = createUserEntityWithParameters("testThree@test.com", "testNameThree");
        testEntityManager.persist(user1);
        testEntityManager.persist(user2);
        testEntityManager.persist(user3);
        userRepository.deleteById(user3.getId());
        Iterable<User> users = userRepository.findAll();
        assertThat(users).hasSize(2).contains(user1, user2);
    }

    private User createSimpleUserEntity() {
        var user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("testName");
        user.setPassword("secret");
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    private User createUserEntityWithParameters(String email, String firstname) {
        var user = new User();
        user.setEmail(email);
        user.setFirstName(firstname);
        user.setPassword("secret");
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }
}
