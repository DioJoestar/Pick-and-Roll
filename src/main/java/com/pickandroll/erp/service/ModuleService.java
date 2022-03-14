package com.pickandroll.erp.service;

import com.pickandroll.erp.dao.ModuleDAO;
import com.pickandroll.erp.model.Module;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author santialves
 */
@Service
public class ModuleService implements ModuleServiceInterface {

    @Autowired
    private ModuleDAO module;

    @Override
    @Transactional(readOnly = true)
    public List<Module> listModules() {

        return (List<Module>) module.findAll();
    }

    @Override
    @Transactional
    public void addModule(Module module) {
        this.module.save(module);
    }

    @Override
    @Transactional
    public void deleteModule(Module module) {
        this.module.delete(module);
    }

    @Override
    @Transactional(readOnly = true)
    public Module findModule(Module module) {
        return this.module.findById(module.getId()).orElse(null);
    }
    
}
