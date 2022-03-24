package com.pickandroll.erp.service;

import com.pickandroll.erp.model.Role;
import java.util.List;

public interface RoleServiceInterface {

    public List<Role> listRoles();

    public void addRole(Role role);

    public void deleteRole(Role role);

    public Role findByName(String name);

}
