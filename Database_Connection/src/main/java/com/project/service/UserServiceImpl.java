package com.project.service;

//import com.project.configuration.HibernateConfiguration;
import com.project.models.User;
import com.project.repositories.UserRepository;
import com.project.repositories.UserRepositoryImpl;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    //private ApplicationContext appContext = new AnnotationConfigApplicationContext(HibernateConfiguration.class);

    private UserRepository userRepository;

    @Override
    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Autowired
    @Override
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    //@Transactional(transactionManager = "txManager")
    public boolean createUser(String email, String password) {
        System.out.println("createUser method in service");
        return userRepository.createUser(email, password);
    }

    @Override
    public List<User> selectAllUsers() {
        return userRepository.selectAllUsers();
    }

    @Override
    public Optional<User> selectUserByEmail(String email) {
        return userRepository.selectUserByEmail(email);
    }
}
