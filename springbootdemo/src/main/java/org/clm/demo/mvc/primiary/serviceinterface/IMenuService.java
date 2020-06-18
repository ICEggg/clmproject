package org.clm.demo.mvc.primiary.serviceinterface;

import org.clm.demo.mvc.primiary.entity.Menu;
import org.clm.demo.mvc.primiary.entity.Role;

import java.util.List;
import java.util.Set;

public interface IMenuService {
    Set<Menu> getMenuListByRoleId(int roleId);
}
