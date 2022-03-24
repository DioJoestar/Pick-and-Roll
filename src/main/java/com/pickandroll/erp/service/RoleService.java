package com.pickandroll.erp.service;

import com.pickandroll.erp.dao.RoleDAO;
import com.pickandroll.erp.model.Role;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RoleService implements RoleServiceInterface {

    @Autowired
    private RoleDAO roleDao;

    @Transactional(readOnly = true)
    public List<Role> listRoles() {
        return (List<Role>) roleDao.findAll();
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        this.roleDao.save(role);
    }

    @Override
    @Transactional
    public void deleteRole(Role role) {
        this.roleDao.delete(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Role findByName(String name) {
        return this.roleDao.findByName(name);
    }
}
