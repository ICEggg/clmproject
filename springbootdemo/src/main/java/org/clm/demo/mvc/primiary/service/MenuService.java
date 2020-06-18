package org.clm.demo.mvc.primiary.service;

import org.clm.demo.mvc.primiary.dao.MenuRepository;
import org.clm.demo.mvc.primiary.entity.Menu;
import org.clm.demo.mvc.primiary.serviceinterface.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MenuService implements IMenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Set<Menu> getMenuListByRoleId(int roleId){
        return menuRepository.getMenuListByRoleId(roleId);
    }

}
