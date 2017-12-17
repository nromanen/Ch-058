package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.repository.UsersRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class UserService {
    @Resource
    private UsersRepository repository;

    /*@Autowired
    public void setRepository(UsersRepository repository) {
        this.repository = repository;
    }*/

    public List<User> getAll() {
        return repository.findAll();
    }
}
