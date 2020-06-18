package org.clm.demo.mvc.primiary.serviceinterface;

import org.clm.demo.mvc.primiary.entity.Role;
import org.clm.demo.mvc.primiary.entity.User;

import java.util.List;

public interface IRoleService {
    List<Role> getRoleIdByUserId(String userId);
}
