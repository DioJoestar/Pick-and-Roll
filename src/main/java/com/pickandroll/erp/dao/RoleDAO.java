package com.pickandroll.erp.dao;

import com.pickandroll.erp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role, Long> {

    public Role findByName(String name);

}
