package org.clm.demo.mvc.primiary.service;

import org.clm.demo.mvc.primiary.dao.RoleRepository;
import org.clm.demo.mvc.primiary.entity.Role;
import org.clm.demo.mvc.primiary.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getRoleIdByUserId(String userId){
        List<Role> roleList = roleRepository.getRoleIdByUserId(userId);
        return roleList;
    }

}
