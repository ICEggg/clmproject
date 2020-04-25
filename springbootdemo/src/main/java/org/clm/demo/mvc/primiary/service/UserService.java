package org.clm.demo.mvc.primiary.service;

import org.clm.demo.mvc.primiary.dao.UserRepository;
import org.clm.demo.mvc.primiary.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUserByNameAndPwd(String username,String password){
        return userRepository.getUserByNameAndPwd(username,password);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }
}
