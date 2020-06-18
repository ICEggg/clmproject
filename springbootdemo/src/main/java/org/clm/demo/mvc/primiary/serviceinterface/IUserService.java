package org.clm.demo.mvc.primiary.serviceinterface;

import org.clm.demo.mvc.primiary.entity.User;

import java.util.List;

public interface IUserService {
    public User getUserByNameAndPwd(String username,String password);
    List<User> getAllUser();
}
