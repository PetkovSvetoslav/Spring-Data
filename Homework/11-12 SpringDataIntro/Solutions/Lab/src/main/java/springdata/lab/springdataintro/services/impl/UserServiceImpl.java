package springdata.lab.springdataintro.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.lab.springdataintro.models.User;
import springdata.lab.springdataintro.repositories.UserRepository;
import springdata.lab.springdataintro.services.UserService;

import javax.transaction.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(User user) {
        this.userRepository.save(user);
    }
}
