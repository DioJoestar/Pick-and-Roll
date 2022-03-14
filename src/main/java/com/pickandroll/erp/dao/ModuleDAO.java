package com.pickandroll.erp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pickandroll.erp.model.Module;

/**
 *
 * @author santialves
 */
public interface ModuleDAO extends JpaRepository<Module, Long>{
    
}
