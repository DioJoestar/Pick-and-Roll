package com.pickandroll.erp.service;

import com.pickandroll.erp.dao.RoleDAO;
import com.pickandroll.erp.model.Role;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService implements RoleServiceInterface {

    @Autowired
    private RoleDAO role;

    @Transactional(readOnly = true)
    public List<Role> listRoles() {

        return (List<Role>) role.findAll();
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        this.role.save(role);
    }

    @Override
    @Transactional
    public void deleteRole(Role role) {
        this.role.delete(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Role findRole(Role role) {
        return this.role.findById(role.getId()).orElse(null);
    }
}
