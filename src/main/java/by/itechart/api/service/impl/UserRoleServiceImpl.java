package by.itechart.api.service.impl;

import by.itechart.api.entity.UserRole;
import by.itechart.api.repository.UserRoleRepository;
import by.itechart.api.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Override
    public UserRole create(UserRole userRole) {
       return userRoleRepository.save(userRole);
    }

    @Override
    public UserRole update(Long id, UserRole userRole) {
        return userRoleRepository.saveAndFlush(userRole);
    }

    @Override
    public void delete(Long id) {
        userRoleRepository.deleteById(id);
    }

    @Override
    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }
}
