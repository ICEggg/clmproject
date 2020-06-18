package org.clm.demo.mvc.primiary.service;

import org.clm.demo.mvc.primiary.dao.UserRepository;
import org.clm.demo.mvc.primiary.entity.User;
import org.clm.demo.mvc.primiary.serviceinterface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    public User getUserByNameAndPwd(String username,String password){
        return userRepository.getUserByNameAndPwd(username,password);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }
}
